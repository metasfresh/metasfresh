package de.metas.adempiere.report.jasper.servlet;

import org.compiere.util.CacheMgt;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.Profiles;
import de.metas.adempiere.report.jasper.JasperConstants;
import de.metas.adempiere.report.jasper.JasperServerConstants;

/**
 * This servlet was introduced for managing the actions that the other Jasper servlets can take.
 *
 * @author RC
 *
 */
@RestController
@RequestMapping(value = MgtRestController.ENDPOINT)
@Profile(Profiles.PROFILE_JasperService)
public class MgtRestController
{
	public static final String ENDPOINT = JasperServerConstants.SERVLET_ROOT + "/MgtServlet";

	@GetMapping
	public String handleAction(@RequestParam(JasperConstants.MGTSERVLET_PARAM_Action) final String action)
	{
		if (JasperConstants.MGTSERVLET_ACTION_CacheReset.equalsIgnoreCase(action))
		{
			CacheMgt.get().reset();
			return "OK";
		}
		else
		{
			throw new RuntimeException("Action not supported: " + action);
		}
	}
}
