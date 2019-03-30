package com.incedo.service;

/**
 * Created by Deb
 */
public interface EventService {
	String getEventJsonFromServiceAPI(String userId, String layerId, String channelId);
	
	void pushNewEvent(String userId, int variantId, int expId, int layerId, int channelId, String stage);
}
