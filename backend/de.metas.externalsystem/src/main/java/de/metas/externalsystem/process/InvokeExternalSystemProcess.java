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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.process.runtimeparameters.RuntimeParametersRepository;
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
import de.metas.util.Services;
import lombok.NonNull;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InvokeExternalSystemProcess extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	public final static AdMessageKey MSG_ERR_NO_EXTERNAL_SELECTION = AdMessageKey.of("NoExternalSelection");
	public final static AdMessageKey MSG_ERR_MULTIPLE_EXTERNAL_SELECTION = AdMessageKey.of("MultipleExternalSelection");

	public final ExternalSystemConfigRepo externalSystemConfigDAO = SpringContextHolder.instance.getBean(ExternalSystemConfigRepo.class);
	public final RuntimeParametersRepository runtimeParametersRepository = SpringContextHolder.instance.getBean(RuntimeParametersRepository.class);
	public final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	public static final String PARAM_CHILD_CONFIG_ID = "ChildConfigId";
	@Param(parameterName = PARAM_CHILD_CONFIG_ID)
	protected int childConfigId;

	private static final String PARAM_SINCE = "since";
	@Param(parameterName = PARAM_SINCE)
	private Timestamp since;

	public static final String PARAM_EXTERNAL_REQUEST = "External_Request";
	@Param(parameterName = PARAM_EXTERNAL_REQUEST)
	protected String externalRequest;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final Timestamp sinceEff = extractEffectiveSinceTimestamp();

		addLog("Calling with params: childConfigId {}, since {}, command {}", childConfigId, sinceEff.toInstant(), externalRequest);
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

	protected HttpPut getRequest() throws UnsupportedEncodingException, JsonProcessingException
	{
		final ExternalSystemParentConfig config = externalSystemConfigDAO.getById(getExternalChildConfigId());

		final JsonExternalSystemRequest jsonExternalSystemRequest = JsonExternalSystemRequest.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
				.externalSystemName(JsonExternalSystemName.of(config.getType().getName()))
				.parameters(extractParameters(config))
				.orgCode(orgDAO.getById(getOrgId()).getValue())
				.command(externalRequest)
				.adPInstanceId(JsonMetasfreshId.of(PInstanceId.toRepoId(getPinstanceId())))
				.build();

		final HttpPut request = new HttpPut(config.getCamelUrl());

		// attempt to encode the request body as UTF-8. Oftherwise camel might fail to deserialize the JSON
		// by default http-client encodes the content as ISO-8859-1, https://hc.apache.org/httpclient-legacy/charencodings.html
		request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		
		final String jsonExternalSystemRequestString = new ObjectMapper().writeValueAsString(jsonExternalSystemRequest);

		request.setEntity(new StringEntity(jsonExternalSystemRequestString));

		return request;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (childConfigId > 0)
		{
			return ProcessPreconditionsResolution.accept();
		}

		final long selectedRecordsCount = getSelectedRecordCount(context);
		if (selectedRecordsCount > 1)
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ERR_MULTIPLE_EXTERNAL_SELECTION, getTabName()));
		}
		else if (selectedRecordsCount == 0)
		{
			final Optional<IExternalSystemChildConfig> childConfig =
					externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId()), getExternalSystemType());

			if (!childConfig.isPresent())
			{
				return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ERR_NO_EXTERNAL_SELECTION, getTabName()));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAM_SINCE.equals(parameter.getColumnName()))
		{
			return retrieveSinceValue();
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	/**
	 * Needed so we also have a "since" when the process is run via AD_Scheduler
	 */
	@NonNull
	protected Timestamp extractEffectiveSinceTimestamp()
	{
		return CoalesceUtil.coalesceSuppliers(() -> since, () -> retrieveSinceValue(), () -> Timestamp.from(Instant.ofEpochSecond(0)));
	}

	private Timestamp retrieveSinceValue()
	{
		final ProcessInfo processInfo = getProcessInfo();
		return pInstanceDAO.getLastRunDate(processInfo.getAdProcessId(), processInfo.getPinstanceId());
	}

	private Map<String, String> extractParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final Map<String, String> parameters = new HashMap<>();

		final Map<String, String> childSpecificParams = extractExternalSystemParameters(externalSystemParentConfig);

		if (childSpecificParams != null && !childSpecificParams.isEmpty())
		{
			parameters.putAll(childSpecificParams);
		}

		runtimeParametersRepository.getByConfigIdAndRequest(externalSystemParentConfig.getId(), externalRequest)
				.forEach(runtimeParameter -> parameters.put(runtimeParameter.getName(), runtimeParameter.getValue()));

		return parameters;
	}

	@Nullable
	protected Timestamp getSinceParameterValue()
	{
		return since;
	}

	protected abstract IExternalSystemChildConfigId getExternalChildConfigId();

	protected abstract Map<String, String> extractExternalSystemParameters(ExternalSystemParentConfig externalSystemParentConfig);

	protected abstract String getTabName();

	protected abstract ExternalSystemType getExternalSystemType();

	protected abstract long getSelectedRecordCount(final IProcessPreconditionsContext context);

}

