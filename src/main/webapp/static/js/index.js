$(document).ready(function() {
 
  /*评论*/
  $(".comment-carousel-carousel").owlCarousel({
      autoPlay: 6000,
      items : 4,
	  navigation:false,
	  pagination:true,
	  itemsDesktop : [1199,4],
      itemsDesktopSmall : [979,3],
	  itemsTablet : [768,2],
	  itemsMobile: [479,1],
  });
  
})