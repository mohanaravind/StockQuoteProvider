package com.mohanaravind.stockquoteprovider;

import java.io.IOException;
import javax.servlet.http.*;


import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mohanaravind.worker.Worker;

@SuppressWarnings("serial")
public class StockSummaryServlet extends HttpServlet {

	private String _stockName;
	private boolean _isAuthorizedRequest;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		JSONObject output = null;
		resp.setContentType("application/JSON");

		//Get the inputs
		retrieveInputs(req);

		try {
			if(_isAuthorizedRequest){
				Worker worker = new Worker();

				output = worker.getStockSummary(_stockName);			
			}
		} catch (Exception e) {
			
		}

		//Send back the response
		resp.getWriter().println(output);
	}


	/***

	 * Retrieves the inputs which were passed 
	 */
	private void retrieveInputs(HttpServletRequest req){

		//Initialize 
		_isAuthorizedRequest = false;

		try {
			String apiKey = req.getParameter("apiKey");

			//If its an authorized request
			if(apiKey != null && getServletContext().getInitParameter("apiKey").equals(apiKey)){
				_isAuthorizedRequest = true;
				_stockName = req.getParameter("stockName");
			}else			
				_isAuthorizedRequest = false;
		} catch (Exception e) {
			_isAuthorizedRequest = false;
		}			
	}

}
