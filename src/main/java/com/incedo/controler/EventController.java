package com.incedo.controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, pdpPage, "gridwall", "grid_wall", null);
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, pdpPage, "gridwall", "grid_wall", null);
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, upgradeLine, "pdp", "pdp", "/getGridwallPage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, deviceSelection, "upgrade", "upgradeline", "/getPdpPage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, protection, "deviceselection", "deviceselection", "/getUpgradePage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, accessoryBundle, "protection", "protection", "/getDeviceSelectionPage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, offerPage, "accessorybundle", "acessorybundle", "/getProtectionPage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, cartPage, "offerpage", "offer", "/getAccessoryBundlePage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, checkoutPage, "cart", "cart","/getOfferPage/");
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
    		String eventJson = eventService.getEventJsonFromServiceAPI(userId, layerName, channelName);
    		setModelAttribute(model, eventJson, userId, null, "checkout", "checkout", "/getCartPage/");
    	}else {
    		model.addAttribute("error", "Missing User Id. Please provide User Id to proceed further.");
    		return "home";
    	}
        return "checkout";
    }
    
    public void setModelAttribute(Model model, String eventJson, String userId, String nextPage, String pageHeading, String stage, String previousPage) {
		int channelId = eventUtilService.getChannelIdFromAPI(eventJson);
		int layerId = eventUtilService.getLayerIdFromAPI(eventJson);
		int variantId = eventUtilService.getVariantIdFromAPI(eventJson);
		String eventColor = null;
		String color = eventUtilService.getEventColor(eventJson);
		if(!StringUtils.isEmpty(color) && "default".equals(color)) {
			eventColor = "default";
		} else {
			eventColor = pageHeading + "_" + color;
		}
		int expId = eventUtilService.getExperimentId(eventJson);
		String expToken = eventUtilService.getExperimentToken(eventJson);
		String bucket = eventUtilService.getBucket(eventJson);
		System.out.println("expToken: " + expToken);
        if(!StringUtils.isEmpty(expToken)) {
        	model.addAttribute("userId", userId);
        	model.addAttribute("eventColor", eventColor);
        	model.addAttribute("expToken", expToken);
        	model.addAttribute("expId", expId);
        	model.addAttribute("bucket", bucket);
        	model.addAttribute("channelName", channelName);
        	model.addAttribute("layerName", layerName);
        	model.addAttribute("userId", userId);
        	model.addAttribute("pageHeading", pageHeading);
        	if(!StringUtils.isEmpty(nextPage)) {
        		model.addAttribute("nextPage", nextPage+"/"+userId);
        	}
        	if(!StringUtils.isEmpty(previousPage)) {
        		model.addAttribute("previousPage", previousPage+userId);
        	}
        }
        eventService.pushNewEvent(userId, variantId, expId, layerId, channelId, stage);
	}
}
