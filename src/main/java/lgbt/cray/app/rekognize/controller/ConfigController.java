package lgbt.cray.app.rekognize.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfigController {

	@RequestMapping(value="/config")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("config");
	}
}
