/**
 * 
 */
package com.mohanaravind.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import com.mohanaravind.entity.StockSummary;
import com.mohanaravind.entity.StockSummary.ChangeType;
import com.mohanaravind.entity.StockSummary.Status;

/**
 * @author Aravind
 *
 */
public class DataProvider {

	private final String STOCK_NAME = "STOCKNAME";
	private final int INTERVALS = 5;

	private String _realTimeURL = "http://www.nasdaq.com/symbol/STOCKNAME/real-time";
	private String _historicalURL = "http://www.nasdaq.com/symbol/STOCKNAME/historical#.UVKA5Mr_GKY";


	/**
	 * Constructor
	 */
	public DataProvider(){


	}


	public StockSummary getStockSummary(StockSummary stockSummary){			
		try {						
			//Construct the sales data
			stockSummary = constructSalesData(stockSummary);

			if(stockSummary.getStatus() == Status.Ok)
				//Construct the real time data
				stockSummary = constructRealTimeData(stockSummary);
				
		} catch (Exception e) {
			stockSummary.setStatus(Status.Error);
		}

		return stockSummary;
	}

	/**
	 * Constructs the sales data
	 * @param stockSummary
	 * @return
	 */
	private StockSummary constructSalesData(StockSummary stockSummary){
		Document document;
		try{
			String url = _historicalURL.replace(STOCK_NAME, stockSummary.getStockName());

			HashMap<String, String> tradeHistory = new HashMap<String,String>();
			ArrayList<String> tradeHistoryDates = new ArrayList<String>();
			

			//Get the DOM
			document = Jsoup.connect(url).get();

			//Reinitialize
			String data = "NA";
			String date = "NA";


			Element root = document.select("#historicalContainer").first().children().first().child(1).children().eq(1).first(); 
			
			for (int index = 1; index < INTERVALS + 1; index++) {				
				date = root.child(index).children().eq(0).text();
				data = root.child(index).children().eq(1).text();
				if(!data.equals("NA")){
					tradeHistory.put(date, data);
					tradeHistoryDates.add(date);
				}
			}
			
			stockSummary.setTradeHistoryDates(tradeHistoryDates);
			stockSummary.setTradeHistory(tradeHistory);
		}catch(IOException e){
			stockSummary.setStatus(Status.Error);
		}

		return stockSummary;
	}

	/**
	 * Constructs the real time data
	 * @param stockSummary
	 * @return
	 */
	private StockSummary constructRealTimeData(StockSummary stockSummary){
		Document document;
		try{
			String url = _realTimeURL.replace(STOCK_NAME, stockSummary.getStockName());

			//Get the DOM
			document = Jsoup.connect(url).get();

			//Extract the data
			stockSummary.setLastTradedPrice(document.select("#qwidget_lastsale").text());
			stockSummary.setPercentageChange(document.select("#qwidget_percent").text());
						
			if(document.select("#qwidget_percent").attr("class").contains("Green"))
				stockSummary.setChangeType(ChangeType.Green);
			else
				stockSummary.setChangeType(ChangeType.Red);
									 			
			stockSummary.setDataTime(document.select("#qwidget_markettime").text());
						
			stockSummary.setDaysHigh(document.select("#quotes_content_left__TodaysHigh").text().replace(" ", "").trim());
			stockSummary.setDaysLow(document.select("#quotes_content_left__TodaysLow").text().replace(" ", "").trim());	
			
			String data =  document.select("#quotes_content_left__52WeekHigh").text().trim();			
			data = data.replace(String.valueOf(data.charAt(1)), "");			
			stockSummary.setYearsHigh(data);	
			
			data =  document.select("#quotes_content_left__52WeekLow").text().trim();			
			data = data.replace(String.valueOf(data.charAt(1)), "");
			stockSummary.setYearsLow(data);	
	
			stockSummary.setRating(document.select("#quotes_content_left_OverallStockRating1_lblPercentage").text().replace(" ", "").trim());
		}catch(IOException e){
			stockSummary.setStatus(Status.Error);
		}

		return stockSummary;
	}


}
