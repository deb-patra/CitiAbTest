package com.incedo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import com.incedo.commandVOs.EventSubmitRequestVO;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Deb.
 */
@Slf4j
@Service
public class EventServiceImpl implements EventService {
	
	 @Value("${service.api.url}")
     private String serviceApi;
	 
	 @Value("${postevent.api.url}")
     private String postEventserviceApi;
	 
	@Override
	public String getEventJsonFromServiceAPI(String userId, String layerId, String channelId) {
		//log.debug("EventServiceImpl : getEventId : userId - "+userId);
		URL url;
		String jsonString = null;
		String apiUrl = serviceApi +"?channelId="+channelId+"&layerId="+layerId+"&userId="+userId;
		System.out.println("apiUrl ::"+apiUrl);
		try {
			url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				jsonString = output;
			}
			conn.disconnect();
		} catch (IOException e) {
			System.out.println("IO Exception : "+e.getMessage());
		}	
		return jsonString;
	}

	/*
	@Override
	public void pushNewEvent(String userId, String channelId, String layerId, String expId, String variantId, String stage) {
		EventSubmitRequestVO eventSubmit = new EventSubmitRequestVO();
		eventSubmit.setUser_id(userId);
		UUID uuid = Generators.timeBasedGenerator().generate();
		//eventSubmit.setEvt_id(uuid.clockSequence());
		eventSubmit.setEvt_id(uuid.toString());
		eventSubmit.setVariant(variantId);
		eventSubmit.setChannel_id(channelId);
		eventSubmit.setExp_id(expId);
		
		eventSubmit.setStage(stage);
		eventSubmit.setTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String requestJSON = mapper.writeValueAsString(eventSubmit);
			System.out.println("requestJSON ::"+requestJSON);
			pushEvent(requestJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	*/
	public void pushEvent(String requestJSON) {
		try {
			URL url = new URL(postEventserviceApi);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStream os = conn.getOutputStream();
			os.write(requestJSON.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();
		  } catch (IOException e) {
			e.printStackTrace();
		 }

		}

	public void pushNewEvent(String userId, String variantId, String expId, String layerId, String channelId,
			String stage) {
		
		
	}

	@Override
	public void pushNewEvent(String userId, int variantId, int expId, int layerId, int channelId, String stage) {
		UUID uuid = Generators.timeBasedGenerator().generate();
		EventSubmitRequestVO eventSubmit = new EventSubmitRequestVO();
		eventSubmit.setUser_id(userId);
		//eventSubmit.setEvt_id(uuid.clockSequence());
		eventSubmit.setEvt_id(uuid.toString());
		eventSubmit.setVariant_id(variantId);
		eventSubmit.setExp_id(expId);
		eventSubmit.setLayer_id(layerId);
		eventSubmit.setChannel_id(channelId);
		eventSubmit.setStage(stage);
		eventSubmit.setTime(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		ObjectMapper mapper = new ObjectMapper();
		try {
			String requestJSON = mapper.writeValueAsString(eventSubmit);
			System.out.println("requestJSON ::"+requestJSON);
			pushEvent(requestJSON);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}
}