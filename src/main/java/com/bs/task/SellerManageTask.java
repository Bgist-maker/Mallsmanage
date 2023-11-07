package com.bs.task;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bs.IDao.BuyerMapper;
import com.bs.IDao.MessageMapper;
import com.bs.IDao.OperationMapper;
import com.bs.IDao.SellerMapper;
import com.bs.IDao.UserMapper;
import com.bs.domain.Buyer;
import com.bs.domain.Commodity;
import com.bs.domain.Message;
import com.bs.domain.Operation;
import com.bs.domain.Seller;
import com.bs.domain.User;
import com.bs.util.CommonUtils;
import com.bs.util.PropertiesUtil;
 

@Component
public class SellerManageTask {
	
	@Resource
	private SellerMapper sellerDao;
	@Resource
	private BuyerMapper buyerDao;
	@Resource
	private MessageMapper messageDao;
	@Resource
	private OperationMapper OperationDao;
	@Resource
	private UserMapper UserDao;
	
	
	@Scheduled(cron = "0 1 0 * * ? ")//每天晚上0点一分钟执行一次
    public void test() throws Exception {
		List<Seller> Sellers = sellerDao.getAllSellers();
		for(Seller seller:Sellers){
			if(seller.getIsimportant()==0){
				continue;
			}
			Date sellerBrithday =seller.getBirthday();
			Date sellerBriafterone=new Date();
			Calendar cal1=Calendar.getInstance();
			cal1.setTime(sellerBrithday);
			cal1.add(Calendar.DATE, 1);
			sellerBriafterone=cal1.getTime();
			if(new Date(System.currentTimeMillis()).after(seller.getBirthday())){
				if(new Date(System.currentTimeMillis()).before(sellerBriafterone)){
				Message message = new Message();
				message.setUserid(seller.getUserid());
				message.setMessage("时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"   事件："+seller.getUsername()+"今天生日，记得给祝福哦！");//时间："+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"
				message.setObjecttype(0);
				message.setSellerid(seller.getId());
				message.setIsdeal(0);
				messageDao.insertSelective(message);
				}
			}
		}
    }
	@Scheduled(cron = "0 0 1 * * ? ")//每天晚上12点执行一次
	//@Scheduled(cron = "0 0 */4 * * ?")//每两小时执行一次
	public void testForImportant() throws Exception {
		PropertiesUtil.reduceleaveDays(PropertiesUtil.getleaveDays()-1);
		SimpleDateFormat  simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", simpleDateFormat.format(CommonUtils.getDateBefore(new Date(),5))+ " 00:00:00");
		map.put("endDate", simpleDateFormat.format(new Date())+ " 24:00:00");
		int size = OperationDao.getOperationCount(map);
		if(size==0){
			size =5;
		}
		map.put("pageIndex", "0");
		map.put("pageSize", String.valueOf(size));
		List<Operation> operations =  OperationDao.getOperationList(map);
		
		Map<Integer,Integer> buyerMap = new HashMap<Integer,Integer>();
		Map<Integer,Integer> sellerMap = new HashMap<Integer,Integer>();
		for(Operation operation:operations){
			if(operation.getOperatetype()==0){
				Commodity tempCommodity = operation.getCommodity();
				int key = tempCommodity.getBuyerid();
				if(buyerMap.containsKey(key)){
					int amount = buyerMap.get(key);
					buyerMap.put(key, operation.getAmount()+amount);
				}else{
					buyerMap.put(key, operation.getAmount());
				}
			}else{
				int key = operation.getSellerid();
				if(sellerMap.containsKey(key)){
					int amount = sellerMap.get(key);
					sellerMap.put(key, operation.getAmount()+amount);
				}else{
					sellerMap.put(key, operation.getAmount());
				}
			}
		}
		List<User> users = UserDao.getAllUser();
		List<User> admins = new ArrayList<User>();
		for(User user:users){
			if(user.getRoleid()==1){
				admins.add(user);
			}
		}
		for(Entry<Integer,Integer> entry:buyerMap.entrySet()){
			int key = entry.getKey();
			int value = entry.getValue();
			
			if(value>1000){
				
				Buyer buyer = buyerDao.selectByPrimaryKey(key);
				for(User user:admins){
					Message message = new Message();
					message.setUserid(user.getId());
					message.setMessage(buyer.getLeader()+"--("+buyer.getName()+")近五天提供的物品交易达到1000！请注意是否调整为 vip");
					message.setObjecttype(1);
					message.setBuyid(key);
					message.setIsdeal(0);
					messageDao.insertSelective(message);
				}
			}
		}
		for(Entry<Integer,Integer> entry:sellerMap.entrySet()){
			int key = entry.getKey();
			int value = entry.getValue();
			
			if(value>500){
				Seller seller = sellerDao.selectByPrimaryKey(key);
				for(User user:admins){
					Message message = new Message();
					message.setUserid(user.getId());
					message.setMessage(seller.getUsername()+"--("+seller.getCompany()+")近五天购买交易达到500！请注意是否调整为VIP");
					message.setObjecttype(0);
					message.setSellerid(key);
					message.setIsdeal(0);
					messageDao.insertSelective(message);
				}
			}
		}
	}
}