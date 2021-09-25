package com.cs.event.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.cs.event.bean.EventDetailsBean;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventCheckApp {
	
	private static final Logger logger = LogManager.getLogger(EventCheckApp.class);
	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		
		Scanner scanner=new Scanner(System.in);
		logger.info("Enter File Path:\n");
		String filePath= scanner.next(); //C:\\Users\\Rama\\Documents\\logfile.txt
		File logFile=new File(filePath);
		logger.info("filePath:"+filePath);
		ObjectMapper mapper=new ObjectMapper();
		List<EventDetailsBean> list = new ArrayList<>();
		List<EventDetailsBean> eventList = new ArrayList<>();
		Map<String,Long> map=new HashMap<>();
		try(FileReader reader=new FileReader(logFile);
				BufferedReader bufferReader=new BufferedReader(reader);){
			String currentLine;
			while((currentLine=bufferReader.readLine())!=null) {
				EventDetailsBean eventDetailsBean = mapper.readValue(currentLine, EventDetailsBean.class);
				list.add(eventDetailsBean);
			}
			for (EventDetailsBean eventDetailsBean : list) {
				if(map.containsKey(eventDetailsBean.getId())) {
					map.put(eventDetailsBean.getId(), Math.abs(map.get(eventDetailsBean.getId())-eventDetailsBean.getTimestamp()));
					eventDetailsBean.setEvenDuration(map.get(eventDetailsBean.getId()));
					if(map.get(eventDetailsBean.getId())>4){
						eventDetailsBean.setAlert(true);
					}else {
						eventDetailsBean.setAlert(false);
					}
					eventList.add(eventDetailsBean);
				}else {
					map.put(eventDetailsBean.getId(), eventDetailsBean.getTimestamp());
				}
			}
			
			for (EventDetailsBean eventDetailsBean : eventList) {
				logger.info(eventDetailsBean.getId()+" "+eventDetailsBean.getEvenDuration());
				Connection con= DataBaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("insert into EVENTDETAILS(EVENTID,EVENTDURATION,TYPE,HOST,ALERT) values(?,?,?,?,?)");
				ps.setString(1, eventDetailsBean.getId());
				ps.setLong(2, eventDetailsBean.getEvenDuration());
				ps.setString(3, eventDetailsBean.getType());
				ps.setString(4, eventDetailsBean.getHost());
				ps.setBoolean(5, eventDetailsBean.isAlert());
				ps.execute();
			}
			
		}

	}

}
