package com.incedo.controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.incedo.commandVOs.EventSubmitRequestVO;
import com.incedo.commandVOs.ExperimentVariantVo;
import com.incedo.service.EventService;
import com.incedo.service.EventUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Deb.
 */
@Slf4j
@Controller
public class EventController {

	@Value("${cart.page}")
    private String cartPage;
	
	@Value("${pdp.page}")
    private String pdpPage;
	
	@Value("${upgrade.page}")
    private String upgradeLine;
	
	@Value("${device.selection.page}")
    private String deviceSelection;
	
	@Value("${protection.page}")
    private String protection;
	
	@Value("${accessory.bundle.page}")
    private String accessoryBundle;
	
	@Value("${offer.page}")
    private String offerPage;
	
	@Value("${checkout.page}")
    private String checkoutPage;
	
	@Value("${gridwall.page}")
    private String griwallPage;
	
	@Value("${layer.id}")
    private String layerId;
	
	@Value("${channel.id}")
    private String channelId;
	
	@Value("${layer.id.ui}")
    private String layerName;
	
	@Value("${channel.id.ui}")
    private String channelName;
	
    private final EventService eventService;
    private final EventUtil eventUtilService;

	/**
	 * @param eventService
	 * @param eventUtilService
	 */
	public EventController(EventService eventService, EventUtil eventUtilService) {
		this.eventService = eventService;
		this.eventUtilService = eventUtilService;
	}

	@RequestMapping({"/home","/",""})
	public String getHomePage() {
		return "home";
	}
	
	
	@RequestMapping("/getGridwall")
    public String getGridwallPageWithoutParam(HttpServletRequest request, @RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent, Model model) {
    	System.out.println("With in get gridwall details");
    	String userId = request.getParameter("userId");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "gridwall");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "gridwall");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "gridwall");
    		} else {
    			showNormalHeader(model, "gridwall");
    		}
    		setMessageAndRecos(model, experimentVariantVo);
    		setModelAttribute(model, experimentVariantVo, userId, pdpPage, "gridwall", "grid_wall", null);
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "grid_wall");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "gridwall";
    }
	
	/**
	 * This is the controller method to get the event id based on user id
	 * 
	 * @param model
	 * @return
	 */
    
    @RequestMapping("/getGridwallPage/{userId}")
    public String getGridwallPage(HttpServletRequest request, @RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent, @PathVariable String userId, Model model) {
    	if(!StringUtils.isEmpty(userId)) {
    		// Get experiment variant from service API
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		
    		// Display header info
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "gridwall");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "gridwall");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "gridwall");
    		} else {
    			showNormalHeader(model, "gridwall");
    		}
    		setMessageAndRecos(model, experimentVariantVo);
    		setModelAttribute(model, experimentVariantVo, userId, pdpPage, "gridwall", "grid_wall", null);
    		
    		//Create new experiment VO
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "grid_wall");
    		
    		// Push new event
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "gridwall";
    	
    }
    
    @RequestMapping("/getPdpPage/{userId}")
    public String getPDPPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get PDP page details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "pdp");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "pdp");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "pdp");
    		} else {
    			showNormalHeader(model, "pdp");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, upgradeLine, "pdp", "pdp", "/getGridwallPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "pdp");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "pdp";
    }
    
    @RequestMapping("/getUpgradePage/{userId}")
    public String getUpgradeLinePage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get upgrade line page details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "upgrade");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "upgrade");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "upgrade");
    		} else {
    			showNormalHeader(model, "upgrade");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, deviceSelection, "upgrade", "upgradeline", "/getPdpPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "upgradeline");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "upgradeline";
    }
    
    @RequestMapping("/getDeviceSelectionPage/{userId}")
    public String getDeviceSelectionPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get device selection page details");
    	
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "deviceselection");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "deviceselection");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "deviceselection");
    		} else {
    			showNormalHeader(model, "deviceselection");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, protection, "deviceselection", "deviceselection", "/getUpgradePage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "deviceselection");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "deviceselection";
        
    }
    
    @RequestMapping("/getProtectionPage/{userId}")
    public String getProtectionPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get protection page details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "protection");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "protection");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "protection");
    		} else {
    			showNormalHeader(model, "protection");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, accessoryBundle, "protection", "protection", "/getDeviceSelectionPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "protection");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "protection";
    }
    
    @RequestMapping("/getAccessoryBundlePage/{userId}")
    public String getAccessoryBundlePage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get accessory bundle page details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "accessorybundle");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "accessorybundle");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "accessorybundle");
    		} else {
    			showNormalHeader(model, "accessorybundle");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, offerPage, "accessorybundle", "acessorybundle", "/getProtectionPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "acessorybundle");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "acessorybundle";
    }
    
    @RequestMapping("/getOfferPage/{userId}")
    public String getOfferPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get Offer page details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "offerpage");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "offerpage");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "offerpage");
    		} else {
    			showNormalHeader(model, "offerpage");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, cartPage, "offerpage", "offer", "/getAccessoryBundlePage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "offer");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "offer";
    }
    
    @RequestMapping("/getCartPage/{userId}")
    public String getCartPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get cart details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "cart");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "cart");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "cart");
    		} else {
    			showNormalHeader(model, "cart");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, checkoutPage, "cart", "cart","/getOfferPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "cart");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "cart";
    }
    
    @RequestMapping("/getCheckoutPage/{userId}")
    public String getCheckoutPage(@RequestHeader(value="User-Agent", defaultValue="mobile") String userAgent,@PathVariable String userId, Model model) {
    	System.out.println("With in get checkout details");
    	if(!StringUtils.isEmpty(userId)) {
    		ExperimentVariantVo experimentVariantVo = eventService.getEventJsonFromServiceAPI(userId, layerId, channelId);
    		if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_GREEN_EXP")) {
    			showGreenHeader(model, "checkout");
    		} else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_BLUE_EXP")) {
    			showBlueHeader(model, "checkout");
    		}else if(eventUtilService.incedoGetExperimentName(experimentVariantVo).equalsIgnoreCase("UI_RED_EXP")) {
    			showRedHeader(model, "checkout");
    		} else {
    			showNormalHeader(model, "checkout");
    		}
    		setModelAttribute(model, experimentVariantVo, userId, null, "checkout", "checkout", "/getCartPage/");
    		EventSubmitRequestVO eventSubmit = eventService.incedoEvent(userId, experimentVariantVo.getVariantToken(), experimentVariantVo.getVariantId(), experimentVariantVo.getExpId(), layerId, channelId, "checkout");
    		eventService.pushNewEvent(eventSubmit);
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "checkout";
    }
    
    public void showBlueHeader(Model model, String pageHeading) {
		String eventColor = null;
		String color = "blue";
		if(!StringUtils.isEmpty(color) && "default".equals(color)) {
			eventColor = "default";
		} else {
			eventColor = pageHeading + "_" + color;
		}
		model.addAttribute("eventColor", eventColor);
    }
    public void showRedHeader(Model model, String pageHeading) {
		String eventColor = null;
		String color = "red";
		if(!StringUtils.isEmpty(color) && "default".equals(color)) {
			eventColor = "default";
		} else {
			eventColor = pageHeading + "_" + color;
		}
		model.addAttribute("eventColor", eventColor);
    }
    public void showGreenHeader(Model model, String pageHeading) {
		String eventColor = "green";
		String color = "green";
		if(!StringUtils.isEmpty(color) && "default".equals(color)) {
			eventColor = "default";
		} else {
			eventColor = pageHeading + "_" + color;
		}
		model.addAttribute("eventColor", eventColor);
    }
    
    public void showNormalHeader(Model model, String pageHeading) {
    	showBlueHeader(model, pageHeading);
    }
    
    public void setModelAttribute(Model model, ExperimentVariantVo experimentVariantVo, String userId, String nextPage, String pageHeading, String stage, String previousPage) {
    	if(!StringUtils.isEmpty(experimentVariantVo.getVariantToken())) {
        	model.addAttribute("userId", userId);
        	model.addAttribute("expToken", experimentVariantVo.getVariantToken());
        	model.addAttribute("expId", experimentVariantVo.getExpId());
        	model.addAttribute("expName", experimentVariantVo.getExptName());
        	model.addAttribute("channelName", channelName);
        	model.addAttribute("layerName", layerName);
        	model.addAttribute("pageHeading", pageHeading);
        	if(!StringUtils.isEmpty(nextPage)) {
        		model.addAttribute("nextPage", nextPage+"/"+userId);
        	}
        	if(!StringUtils.isEmpty(previousPage)) {
        		model.addAttribute("previousPage", previousPage+userId);
        	}
        }
    }
    public void setMessageAndRecos(Model model, ExperimentVariantVo experimentVariantVo) {
    	String variantToken = experimentVariantVo.getVariantToken();
    	if(!StringUtils.isEmpty(variantToken)) {
    		if("message_var1".equalsIgnoreCase(variantToken)) {
    			model.addAttribute("eventColor", "Message_123");
    		} else if("message_var2".equalsIgnoreCase(variantToken)) {
    			model.addAttribute("eventColor", "Message_456");
    		} else if("message_var3".equalsIgnoreCase(variantToken)) {
    			model.addAttribute("eventColor", "Message_789");
    		} else if("recos_2".equalsIgnoreCase(variantToken)) {
    			model.addAttribute("eventColor", "recos2");
    		} else if("recos_3".equalsIgnoreCase(variantToken)) {
    			model.addAttribute("eventColor", "recos3");
    		}
    	}
    }
    
}
