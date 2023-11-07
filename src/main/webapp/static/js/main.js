;(function () {
	'use strict';
	var sliderMain = function() {
		
	  	$('.flexslider').flexslider({
			animation: "fade",
			slideshowSpeed: 5000, //展示时间间隔ms
		    animationSpeed: 1000, //滚动时间ms
		    touch: true, //是否支持触屏滑动
			directionNav: true
	  	});

	};

	$(function(){

		sliderMain();
		
		/*侧栏导航*/
		$(".side ul li").hover(function(){
		$(this).find(".sidebox").stop().animate({"width":"160px"},100).css({"opacity":"1","filter":"Alpha(opacity=100)","background":"#FF0014"})	
	},function(){
		$(this).find(".sidebox").stop().animate({"width":"54px"},100).css({"opacity":"1","filter":"Alpha(opacity=100)","background":"#FF0014"})	
	   });
	   
	   /*友情链接*/
	   $(".friend_link").hover(function(){
		   $(this).parent().find("ul").show();
		},function(){
		    $(this).parent().find("ul").hide();
	   })

	});


}());

//回到顶部
function goTop(){
	$('html,body').animate({'scrollTop':0},500);
}