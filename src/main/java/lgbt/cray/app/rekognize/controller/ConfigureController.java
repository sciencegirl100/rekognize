package lgbt.cray.app.rekognize.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lgbt.cray.app.rekognize.configuration.Settings;

@Controller
public class ConfigureController {

	@RequestMapping(value="/configure")
	public String display(HttpServletRequest r, Model m) {
		String keys[] = {"awsId", "awsSecret", "awsRegion", "awsKeep"};
		// Handle synchronous settings changes
		String statusStr = "";
		String pushState = "";
		String query = r.getQueryString();
		if( query != null && query.length() > 0) {
			// query has data in it
			query += "&";
			for (String k : keys) {
				if(query.contains(k)) {
					String value = query.replaceAll(".*"+k+"=(.{0,}?)&.*", "$1");
					if (value != null && value.length() > 0) {
						if(k == "awsKeep") {
							Settings.setSetting(k, "checked");
						}else {
							Settings.setSetting(k, value);
						}
					}
				}
			}
			// Can remove the following after JS data building implemented
			if(!query.contains("awsKeep")) {
				Settings.setSetting("awsKeep",  " ");
			}
			statusStr = "Configuration Saved 👍";
			pushState = "configure";
		}
		
		// Apply dynamic data to page response
		for(String k : keys) {
			String storedSetting = Settings.getSetting(k);
			if(k.equals("awsRegion")) {
				String defaultOption = "";
				if(Settings.awsRegions.containsKey(storedSetting)) {
					defaultOption = "<option value=\"awsRegion\" selected>Name</option>";
					defaultOption = defaultOption.replaceAll("awsRegion", storedSetting);
					String storedName = Settings.awsRegions.get(storedSetting);
					defaultOption = defaultOption.replaceAll("Name", storedName);
				}
				m.addAttribute(k, defaultOption);
			}else {
				m.addAttribute(k, (storedSetting != null)?storedSetting:"");
			}
		}
		m.addAttribute("status", statusStr);
		m.addAttribute("pushState", pushState);
		return "configure";
	}
}
