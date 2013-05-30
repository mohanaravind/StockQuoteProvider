/**
 * 
 */
package com.mohanaravind.entity;

import java.util.HashMap;
import java.util.List;

/**
 * @author Aravind
 *
 */
public class StockSummary {

	private String _stockName;
	
	private String _lastTradedPrice;
	private String _percentageChange;
	private String _daysHigh;
	private String _daysLow;
	private String _yearsHigh;
	private String _yearsLow;
	private String _rating;
	private String _dataTime;
	
	private ChangeType _changeType;	
	private Status _status;
	
	private HashMap<String,String> _tradeHistory;
	private List<String> _tradeHistoryDates;
	
	public enum Status{
		Ok, Error
	}
	
	public enum ChangeType{
		Green, Red
	}
	
	
	/**
	 * Setter Methods
	 * @param value
	 */
	public void setStockName(String value){_stockName = value;}
	public void setLastTradedPrice(String value){_lastTradedPrice = value;}
	public void setPercentageChange(String value){_percentageChange = value;}
	public void setDaysHigh(String value){_daysHigh = value;}
	public void setDaysLow(String value){_daysLow = value;}
	public void setYearsHigh(String value){_yearsHigh = value;}
	public void setYearsLow(String value){_yearsLow = value;}
	public void setRating(String value){_rating = value;}
	public void setDataTime(String value){_dataTime = value;}
	public void setStatus(Status value){_status = value;}
	public void setChangeType(ChangeType value){_changeType = value;}

	public void setTradeHistory(HashMap<String,String> value){_tradeHistory = value;}
	public void setTradeHistoryDates(List<String> value){_tradeHistoryDates = value;}	
	
	/**
	 * Getter Methods
	 * @return
	 */
	public String getStockName(){return _stockName;}
	public String getLastTradedPrice(){return _lastTradedPrice;}
	public String getPercentageChange(){return _percentageChange;}
	public String getDaysHigh(){return _daysHigh;}
	public String getDaysLow(){return _daysLow;}
	public String getYearsHigh(){return _yearsHigh;}
	public String getYearsLow(){return _yearsLow;}
	public String getRating(){return _rating;}
	public String getDataTime(){return _dataTime;}
	public Status getStatus(){return _status;}
	public ChangeType getChangeType(){return _changeType;}

	
	public HashMap<String,String> getTradeHistory(){return _tradeHistory;}
	public List<String> getTradeHistoryDates(){return _tradeHistoryDates;}
}
