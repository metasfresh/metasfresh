/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.externalsystem.process;

import com.google.gson.Gson;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemChildConfig;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.service.IExternalSystemConfigDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.organization.IOrgDAO;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Services;
import lombok.NonNull;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public abstract class InvokeExternalSystemProcess extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	public final static AdMessageKey MSG_ERR_NO_EXTERNAL_SELECTION = AdMessageKey.of("NoExternalSelection");
	public final static AdMessageKey MSG_ERR_MULTIPLE_EXTERNAL_SELECTION = AdMessageKey.of("MultipleExternalSelection");

	public final IExternalSystemConfigDAO externalSystemConfigDAO = Services.get(IExternalSystemConfigDAO.class);
	public final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	private static final String PARAM_CONFIG_ID = "configId";
	@Param(parameterName = PARAM_CONFIG_ID)
	protected MetasfreshId configId;

	private static final String PARAM_SINCE = "since";
	@Param(parameterName = PARAM_SINCE)
	protected Timestamp since;

	private static final String PARAM_EXTERNAL_REQUEST = "External_Request";
	@Param(parameterName = PARAM_EXTERNAL_REQUEST)
	protected String externalRequest;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		addLog("Calling with params: configId {}, since {}, command {}", configId, since.toInstant(), externalRequest);
		try (final CloseableHttpClient aDefault = HttpClients.createDefault())
		{
			return aDefault.execute(getRequest(), response -> {
				final int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != 200)
				{
					addLog("Camel request error message: {}", response);
				}
				else
				{
					addLog("Status code from camel: {}", statusCode);
				}
				return statusCode == 200 ? JavaProcess.MSG_OK : JavaProcess.MSG_Error + " request returned code: " + response.toString();
			});
		}
	}

	protected HttpPut getRequest() throws UnsupportedEncodingException
	{
		final ExternalSystemChildConfig config = getExternalChildConfig();
		final ExternalSystemParentConfig parentConfig = externalSystemConfigDAO.getById(config.getParentId());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_API_KEY, config.getApiKey());
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, config.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_TENANT, config.getTenant());
		parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, since.toInstant().toString());

		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonExternalSystemRequest.builder()
				.externalSystemName(JsonExternalSystemName.of(parentConfig.getName()))
				.parameters(parameters)
				.orgCode(orgDAO.getById(getOrgId()).getValue())
				.command(externalRequest)
				.adPInstanceId(JsonMetasfreshId.of(PInstanceId.toRepoId(getPinstanceId())))
				.build();

		final HttpPut request = new HttpPut(parentConfig.getCamelUrl());
		final String jsonExternalSystemRequestString = new Gson().toJson(jsonExternalSystemRequest);
		request.setEntity(new StringEntity(jsonExternalSystemRequestString));
		return request;
	}

	protected abstract ExternalSystemChildConfig getExternalChildConfig();

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (configId != null)
		{
			return ProcessPreconditionsResolution.accept();
		}

		final long selectedRecordsCount = getSelectedRecordCount(context);
		if (selectedRecordsCount == 0)
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ERR_NO_EXTERNAL_SELECTION, getTabName()));
		}
		if (selectedRecordsCount > 1)
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ERR_MULTIPLE_EXTERNAL_SELECTION, getTabName()));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_SINCE.equals(parameter.getColumnName()))
		{
			final ProcessInfo processInfo = getProcessInfo();
			return pInstanceDAO.getLastRunDate(processInfo.getAdProcessId(), processInfo.getPinstanceId());
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	protected abstract String getTabName();

	protected abstract long getSelectedRecordCount(final IProcessPreconditionsContext context);

}

