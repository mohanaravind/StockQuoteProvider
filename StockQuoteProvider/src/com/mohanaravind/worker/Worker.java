/**
 * 
 */
package com.mohanaravind.worker;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.mohanaravind.entity.StockSummary;
import com.mohanaravind.entity.StockSummary.Status;
import com.mohanaravind.utility.DataProvider;

/**
 * @author Aravind
 *
 */
public class Worker {

	public JSONObject getStockSummary(String stockName){
		//Declarations
		JSONObject jsonObject = null;
		StockSummary stockSummary = new StockSummary();
		try {			
			DataProvider dataProvider = new DataProvider();
			
			stockSummary.setStockName(stockName);
			stockSummary.setStatus(Status.Ok);

			stockSummary = dataProvider.getStockSummary(stockSummary);

			jsonObject = new JSONObject(stockSummary);
		} catch (Exception e) {
			stockSummary.setStatus(Status.Error);
		}			

		return jsonObject;
	}

}
