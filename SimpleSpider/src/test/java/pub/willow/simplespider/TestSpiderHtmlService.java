package pub.willow.simplespider;

import javax.annotation.Resource;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import pub.willow.simplespider.beans.SimpleSpiderBean;
import pub.willow.simplespider.service.SpiderService;

public class TestSpiderHtmlService extends AbstractDependencyInjectionSpringContextTests {
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] { 
				"applicationContext-init.xml","applicationContext-service.xml"
//				"applicationContext-*.xml"
	  			  };
	}
	
	@Resource(name="spiderService")
	public SpiderService spiderService;
	
	
	public void testSpiderHtml(){
		SimpleSpiderBean taskBean = new SimpleSpiderBean();
		taskBean.setUrl("http://mp.weixin.qq.com/s?src=3&timestamp=1477546497&ver=1&signature=seQT3i3rPMs63GvH5gC9fr-GLs5xwF8MATOJvTa*0QS986*3jxwJ2KIQpvyBkWQegmyc1dAuxK3Oi-UQRWVShjDesQwND9IWwHGE8-aAHkQsrEmVIt0OamYNttrCMTjTqNs0-*1YyA7N46alb39vefL-IOPOAy2dktg137Nox68=&ascene=1&uin=MjYxNjgzNjMzNg%3D%3D&key=cde9f53f8128acbd933e53744129b839238845e28c1614b5669badaaba32ddfdd6015079f7fc24e998dfe2c86e1964a2&devicetype=android-19&version=26031b31&lang=zh_CN&nettype=WIFI&pass_ticket=OzmhlKMxrhffnCI0mKVhcLDoRUM6ciUtQSH0jDV6Fbq6mWOIdjW3IxryUHBM%2B4jP&wx_header=1");
		taskBean.setCharset("UTF-8");
		try {
			System.out.println(spiderService);
			spiderService.spiderHtml(taskBean );
			String source = taskBean.getSource();
			System.out.println(source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
