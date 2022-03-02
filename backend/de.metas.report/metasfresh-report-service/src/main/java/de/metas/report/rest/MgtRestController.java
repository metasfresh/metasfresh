package de.metas.report.rest;

import de.metas.Profiles;
import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.report.server.ReportConstants;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This servlet was introduced for managing the actions that the other Jasper servlets can take.
 *
 * @author RC
 *
 */
@RestController
@RequestMapping(value = MgtRestController.ENDPOINT)
@Profile(Profiles.PROFILE_ReportService)
public class MgtRestController
{
	static final String ENDPOINT = ReportRestController.SERVLET_ROOT + "/MgtServlet";

	@GetMapping
	public String handleAction(@RequestParam(ReportConstants.MGTSERVLET_PARAM_Action) final String action)
	{
		if (ReportConstants.MGTSERVLET_ACTION_CacheReset.equalsIgnoreCase(action))
		{
			CacheMgt.get().reset();
			return "OK";
		}
		else
		{
			throw new RuntimeException("Action not supported: " + action);
		}
	}

	@GetMapping("/logger")
	public void setLogLevel(@RequestParam("level") final String levelStr)
	{
		LogManager.setLoggerLevel("de.metas.report", LogManager.asLogbackLevel(levelStr));
	}
}
