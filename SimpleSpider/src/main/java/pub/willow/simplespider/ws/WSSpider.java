package pub.willow.simplespider.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import pub.willow.simplespider.beans.SimpleSpiderBean;

/**
 * 抓取任务服务
 * @author albert.zhang
 * on 2014-6-9
 *
 */
@WebService
public interface WSSpider {
	@WebMethod(operationName="spiderHtml")
	public SimpleSpiderBean spiderHtml(@WebParam(name="simpleSpiderBean") SimpleSpiderBean simpleSpiderBean);
}
