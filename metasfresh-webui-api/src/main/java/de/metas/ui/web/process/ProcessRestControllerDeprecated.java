package de.metas.ui.web.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.process.json.JSONProcessInstance;
import de.metas.ui.web.process.json.JSONProcessInstanceResult;
import de.metas.ui.web.process.json.JSONProcessLayout;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import io.swagger.annotations.Api;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Api
@RestController
@RequestMapping(value = ProcessRestController.ENDPOINT)
@Deprecated
public class ProcessRestControllerDeprecated
{
	@Autowired
	private UserSession userSession;

	@Autowired
	private ProcessRestController processRestController;

	private static final int AD_Process_ID_Unknown = -1;

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	@Deprecated
	public JSONProcessLayout getLayout_DEPRECATED(
			@RequestParam(name = "processId", required = true) final int adProcessId //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.getLayout(adProcessId);
	}

	@RequestMapping(value = "/instance", method = RequestMethod.PUT)
	@Deprecated
	public JSONProcessInstance createInstance_DEPRECATED(
			@RequestParam(name = "processId", required = true) final int adProcessId //
			, @RequestParam(name = WebConfig.PARAM_WindowId, required = false, defaultValue = "0") final int adWindowId //
			, @RequestParam(name = WebConfig.PARAM_DocumentId, required = false) final String idStr //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		final JSONCreateProcessInstanceRequest request = JSONCreateProcessInstanceRequest.of(adProcessId, adWindowId, idStr);
		return processRestController.createInstanceFromRequest(adProcessId, request);
	}

	@RequestMapping(value = "/instance", method = RequestMethod.POST)
	@Deprecated
	public JSONProcessInstance createInstanceFromRequest_DEPRECATED(@RequestBody final JSONCreateProcessInstanceRequest request)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.createInstanceFromRequest(request.getAD_Process_ID(), request);
	}

	@RequestMapping(value = "/instance/{pinstanceId}", method = RequestMethod.GET)
	@Deprecated
	public JSONProcessInstance getInstance_DEPRECATED(@PathVariable("pinstanceId") final int pinstanceId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.getInstance(AD_Process_ID_Unknown, pinstanceId);
	}

	@RequestMapping(value = "/instance/{pinstanceId}/start", method = RequestMethod.GET)
	@Deprecated
	public JSONProcessInstanceResult startProcess_DEPRECATED(@PathVariable("pinstanceId") final int pinstanceId)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.startProcess(AD_Process_ID_Unknown, pinstanceId);
	}

	@RequestMapping(value = "/instance/{pinstanceId}/print/{filename:.*}", method = RequestMethod.GET)
	@Deprecated
	public ResponseEntity<byte[]> getReport_DEPRECATED(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("filename") final String filename //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.getReport(AD_Process_ID_Unknown, pinstanceId, filename);
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters/{parameterName}/typeahead", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getParameterTypeahead_DEPRECATED(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.getParameterTypeahead(-1, pinstanceId, parameterName, query);
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters/{parameterName}/dropdown", method = RequestMethod.GET)
	@Deprecated
	public JSONLookupValuesList getParameterDropdown_DEPRECATED(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		userSession.assertDeprecatedRestAPIAllowed();
		return processRestController.getParameterDropdown(AD_Process_ID_Unknown, pinstanceId, parameterName);
	}
}
