package pub.willow.simplespider.ws.impl;

import javax.annotation.Resource;

import pub.willow.simplespider.beans.SimpleSpiderBean;
import pub.willow.simplespider.service.SpiderService;
import pub.willow.simplespider.ws.WSSpider;

public class WSSpiderImpl implements WSSpider {
	@Resource(name = "spiderService")
	public SpiderService spiderService;

	@Override
	public SimpleSpiderBean spiderHtml(SimpleSpiderBean simpleSpiderBean) {
		try {
			int taskId = simpleSpiderBean.getTaskId();
			String url = simpleSpiderBean.getUrl();
			System.out.println("taskId->" + taskId + " url->" + url);
			simpleSpiderBean = spiderService.spiderHtml(simpleSpiderBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simpleSpiderBean;
	}

}
