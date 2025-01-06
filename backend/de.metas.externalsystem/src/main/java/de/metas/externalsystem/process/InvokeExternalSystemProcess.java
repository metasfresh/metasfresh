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

import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.process.runtimeparameters.RuntimeParametersRepository;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
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
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
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
	private final ExternalSystemConfigService externalSystemConfigService = SpringContextHolder.instance.getBean(ExternalSystemConfigService.class);

	public final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);

	public static final String PARAM_CHILD_CONFIG_ID = "ChildConfigId";
	@Param(parameterName = PARAM_CHILD_CONFIG_ID)
	protected int childConfigId;

	private static final String PARAM_SINCE = "Since";
	@Param(parameterName = PARAM_SINCE)
	private Timestamp since;

	public static final String PARAM_EXTERNAL_REQUEST = "External_Request";
	@Param(parameterName = PARAM_EXTERNAL_REQUEST)
	protected String externalRequest;

	protected final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final Timestamp sinceEff = extractEffectiveSinceTimestamp();

		addLog("Calling with params: childConfigId {}, since {}, command {}", childConfigId, sinceEff.toInstant(), externalRequest);

		final JsonExternalSystemRequest externalSystemRequest = getRequest();

		SpringContextHolder.instance.getBean(ExternalSystemMessageSender.class).send(externalSystemRequest);

		return MSG_OK;
	}

	@NonNull
	protected JsonExternalSystemRequest getRequest()
	{
		final ExternalSystemParentConfig config = externalSystemConfigDAO.getById(getExternalChildConfigId());

		return JsonExternalSystemRequest.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(config.getId().getRepoId()))
				.externalSystemName(JsonExternalSystemName.of(config.getType().getName()))
				.parameters(extractParameters(config))
				.orgCode(getOrgCode(config))
				.command(externalRequest)
				.adPInstanceId(JsonMetasfreshId.of(PInstanceId.toRepoId(getPinstanceId())))
				.traceId(externalSystemConfigService.getTraceId())
				.writeAuditEndpoint(config.getAuditEndpointIfEnabled())
				.externalSystemChildConfigValue(config.getChildConfig().getValue())
				.build();
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
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_MULTIPLE_EXTERNAL_SELECTION, getTabName()));
		}
		else if (selectedRecordsCount == 0)
		{
			final Optional<IExternalSystemChildConfig> childConfig =
					externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId()), getExternalSystemType());

			if (!childConfig.isPresent())
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_ERR_NO_EXTERNAL_SELECTION, getTabName()));
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
	 * Needed so we also have a "since" when the process is run via AD_Scheduler.
	 * This might be the process's last invocation time. Note that oftentimes, there is also a runtime-parameter with the actual value used by the external system.
	 */
	@NonNull
	protected Timestamp extractEffectiveSinceTimestamp()
	{
		return CoalesceUtil.coalesceSuppliersNotNull(() -> since, this::retrieveSinceValue, () -> Timestamp.from(Instant.ofEpochSecond(0)));
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

	protected String getOrgCode(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		return orgDAO.getById(getOrgId()).getValue();
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

