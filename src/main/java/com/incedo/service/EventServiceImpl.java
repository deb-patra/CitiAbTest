package com.incedo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.uuid.Generators;
import com.incedo.commandVOs.EventSubmitRequestVO;
import com.incedo.commandVOs.ExperimentVariantVo;

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
	public ExperimentVariantVo getEventJsonFromServiceAPI(String userId, String layerId, String channelId) {
		//log.debug("EventServiceImpl : getEventId : userId - "+userId);
		URL url;
		String jsonString = null;
		String apiUrl = serviceApi +"?channel_id="+channelId+"&layer_id="+layerId+"&user_id="+userId;
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
		String variantToken = null;
		String exptName = null;
		String bucket = null;
		int expId = 1;
		int variantId = 0;
		JSONObject obj = new JSONObject(jsonString);
		if(obj.has("variant_token")) {
			variantToken = obj.get("variant_token").toString();
		}
		if(obj.has("bucket")) {
			bucket = obj.get("bucket").toString();
		}
		if(obj.has("exp_id")) {
			expId = (Integer) obj.get("exp_id");
		}
		if(obj.has("variant_id")) {
			variantId = (Integer) obj.get("variant_id");
		}
		if(obj.has("expt_name")) {
			exptName = (String) obj.get("expt_name");
		}
		ExperimentVariantVo experimentVariantVo = new ExperimentVariantVo();
		experimentVariantVo.setBucket(bucket);
		experimentVariantVo.setVariantToken(variantToken);
		experimentVariantVo.setExpId(expId);
		experimentVariantVo.setVariantId(variantId);
		experimentVariantVo.setExptName(exptName);
		return experimentVariantVo;
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

	@Override
	public void pushNewEvent(EventSubmitRequestVO eventSubmit ) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String requestJSON = mapper.writeValueAsString(eventSubmit);
			System.out.println("requestJSON ::"+requestJSON);
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
	
	@Override
	public EventSubmitRequestVO incedoEvent(String userId, int variantId, int expId, String layerId, String channelId, String stage) {
		int channelIdPush = Integer.parseInt(channelId);
		int layerIdPush = Integer.parseInt(layerId);
		UUID uuid = Generators.timeBasedGenerator().generate();
		EventSubmitRequestVO eventSubmit = new EventSubmitRequestVO();
		eventSubmit.setUser_id(userId);
		eventSubmit.setEvt_id(uuid.toString());
		eventSubmit.setVariant_id(variantId);
		eventSubmit.setExp_id(expId);
		eventSubmit.setLayer_id(layerIdPush);
		eventSubmit.setChannel_id(channelIdPush);
		eventSubmit.setStage(stage);
		Instant instant = Instant.now();
		Long timeStampSeconds = instant.getEpochSecond();
		System.out.println("now ::"+timeStampSeconds.intValue());
		if(null != timeStampSeconds) {
			eventSubmit.setTime(timeStampSeconds.intValue());
		}
		return eventSubmit;
	}
}