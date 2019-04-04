package com.incedo.service;

import com.incedo.commandVOs.EventSubmitRequestVO;
import com.incedo.commandVOs.ExperimentVariantVo;

/**
 * Created by Deb
 */
public interface EventService {
	ExperimentVariantVo getEventJsonFromServiceAPI(String userId, String layerId, String channelId);
	
	public EventSubmitRequestVO incedoEvent(String userId, String variantToken, int variantId, int expId, String layerId, String channelId, String stage);

	void pushNewEvent(EventSubmitRequestVO eventSubmit);
}
