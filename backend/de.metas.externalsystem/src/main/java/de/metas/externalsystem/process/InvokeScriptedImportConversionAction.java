/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.externalservice.ExternalServices;
import de.metas.externalsystem.externalservice.process.AlterExternalSystemServiceStatusAction;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedImportConversion;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfig;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfigId;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionService;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionService.ResolvedChildCommand;
import de.metas.externalsystem.scriptedimportconversion.ScriptedImportConversionCommand;
import de.metas.externalsystem.scriptedimportconversion.ScriptedImportConversionIntent;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * "Call" process for scripted-import conversion ({@code AD_Process 585512}, attached at the parent
 * {@code ExternalSystem_Config} and the child {@code ExternalSystem_Config_ScriptedImportConversion}).
 * <p>
 * The {@code External_Request} parameter is a Start/Stop <i>intent</i>; the concrete camel command
 * (REST vs SFTP) is derived per child from that child's endpoint transport, because a parent may
 * have several import children with different transports. Run on the parent it iterates all active
 * children; run on a child (or via {@code childConfigId}) it targets that single child.
 */
public class InvokeScriptedImportConversionAction extends AlterExternalSystemServiceStatusAction
{
	private static final Logger logger = LogManager.getLogger(InvokeScriptedImportConversionAction.class);

	private final ExternalSystemScriptedImportConversionService externalSystemScriptedImportConversionService = SpringContextHolder.instance
			.getBean(ExternalSystemScriptedImportConversionService.class);
	private final ExternalServices externalServices = SpringContextHolder.instance.getBean(ExternalServices.class);
	private final ExternalSystemConfigService externalSystemConfigService = SpringContextHolder.instance.getBean(ExternalSystemConfigService.class);
	private final ExternalSystemMessageSender externalSystemMessageSender = SpringContextHolder.instance.getBean(ExternalSystemMessageSender.class);

	@Override
	protected String doIt() throws Exception
	{
		final ScriptedImportConversionIntent intent = resolveIntent(externalRequest);

		final ImmutableList<ResolvedChildCommand> resolvedCommands = resolveChildCommands(intent);
		if (resolvedCommands.isEmpty())
		{
			addLog("No active ScriptedImportConversion child config found; nothing to " + intent.getCode());
			return MSG_OK;
		}

		// All resolved children belong to the same parent; load it once (type + audit endpoint are parent-level).
		final ExternalSystemParentConfig parentConfig = externalSystemConfigDAO.getById(resolvedCommands.get(0).getConfig().getId());
		final ExternalSystemParentConfigId parentId = parentConfig.getId();

		int succeeded = 0;
		for (final ResolvedChildCommand resolved : resolvedCommands)
		{
			final ExternalSystemScriptedImportConversionConfig child = resolved.getConfig();
			final String command = resolved.getCommand().getValue();
			try
			{
				// Record the expected status (Active/Inactive) FIRST -- it is the source of truth the startup
				// reconciler acts on -- then trigger the concrete route (enable/disable REST or SFTP polling).
				externalServices.handleStatusUpdateIfRequired(parentId, command);
				externalSystemMessageSender.send(buildRequest(parentConfig, child, command));

				addLog("Sent '" + command + "' for ScriptedImportConversion child " + child.getId().getRepoId() + " (" + child.getValue() + ")");
				succeeded++;
			}
			catch (final Exception e)
			{
				// Isolate per-child failures: one bad child must not abort (and roll back) the others. The
				// already-sent messages are not transactional, so we keep going; the recorded expected status
				// lets the startup reconciler self-heal a child whose send did not go through.
				logger.warn("Failed to {} ScriptedImportConversion child {} ({}) -- continuing",
						intent.getCode(), child.getId().getRepoId(), child.getValue(), e);
				addLog("FAILED to '" + command + "' child " + child.getId().getRepoId() + " (" + child.getValue() + "): " + e.getMessage());
			}
		}

		return MSG_OK + " (" + succeeded + "/" + resolvedCommands.size() + ")";
	}

	/**
	 * The manual process passes a Start/Stop intent, but the generic infra + {@code ExternalSystem_Service}
	 * enable/disable commands pass the CONCRETE transport command here — so accept both.
	 */
	@VisibleForTesting
	static ScriptedImportConversionIntent resolveIntent(@NonNull final String externalRequest)
	{
		final ScriptedImportConversionIntent intent = ScriptedImportConversionIntent.ofCodeOrNull(externalRequest);
		if (intent != null)
		{
			return intent;
		}

		final ScriptedImportConversionCommand command = ScriptedImportConversionCommand.ofCodeOrNull(externalRequest);
		if (command != null)
		{
			return command.getIntent();
		}

		throw new AdempiereException("No ScriptedImportConversion intent or command for External_Request")
				.appendParametersToMessage()
				.setParameter("External_Request", externalRequest)
				.setParameter("acceptedIntents", "start, stop")
				.setParameter("acceptedCommands", "enableRestAPI, disableRestAPI, enableSftpPolling, disableSftpPolling");
	}

	private ImmutableList<ResolvedChildCommand> resolveChildCommands(final ScriptedImportConversionIntent intent)
	{
		if (this.childConfigId > 0)
		{
			return externalSystemScriptedImportConversionService.resolveCommands(
					null, ExternalSystemScriptedImportConversionConfigId.ofRepoId(this.childConfigId), intent);
		}
		if (I_ExternalSystem_Config_ScriptedImportConversion.Table_Name.equals(getTableName()))
		{
			// invoked from the scripted-import child tab/window: the selected record IS the child
			return externalSystemScriptedImportConversionService.resolveCommands(
					null, ExternalSystemScriptedImportConversionConfigId.ofRepoId(getRecord_ID()), intent);
		}
		// invoked from the parent ExternalSystem_Config: iterate all active children
		return externalSystemScriptedImportConversionService.resolveCommands(
				ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), null, intent);
	}

	private JsonExternalSystemRequest buildRequest(
			final ExternalSystemParentConfig parentConfig,
			final ExternalSystemScriptedImportConversionConfig child,
			final String command)
	{
		final Map<String, String> parameters = new HashMap<>(externalSystemScriptedImportConversionService.getParameters(child));
		runtimeParametersRepository.getByConfigIdAndRequest(parentConfig.getId(), command)
				.forEach(runtimeParameter -> parameters.put(runtimeParameter.getName(), runtimeParameter.getValue()));

		return JsonExternalSystemRequest.builder()
				.externalSystemConfigId(JsonMetasfreshId.of(parentConfig.getId().getRepoId()))
				.externalSystemName(JsonExternalSystemName.of(parentConfig.getType().getValue()))
				.parameters(parameters)
				.orgCode(orgDAO.getById(getOrgId()).getValue())
				.command(command)
				.adPInstanceId(JsonMetasfreshId.of(PInstanceId.toRepoId(getPinstanceId())))
				.traceId(externalSystemConfigService.getTraceId())
				.writeAuditEndpoint(parentConfig.getAuditEndpointIfEnabled())
				.externalSystemChildConfigValue(child.getValue())
				.build();
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (childConfigId > 0)
		{
			return ProcessPreconditionsResolution.accept();
		}

		// preserve the base contract: reject a multi-row selection with a clean, translated message
		if (getSelectedRecordCount(context) > 1)
		{
			return ProcessPreconditionsResolution.reject(MSG_ERR_MULTIPLE_EXTERNAL_SELECTION, getTabName());
		}

		if (I_ExternalSystem_Config_ScriptedImportConversion.Table_Name.equals(context.getTableName()))
		{
			// child tab/window: applies to the selected child config
			return ProcessPreconditionsResolution.accept();
		}

		// parent ExternalSystem_Config: applicable only if it has >=1 active scripted-import child
		final ExternalSystemParentConfigId parentId = ExternalSystemParentConfigId.ofRepoId(context.getSingleSelectedRecordId());
		if (externalSystemConfigDAO.getScriptedImportConversionChildrenByParentId(parentId).isEmpty())
		{
			return ProcessPreconditionsResolution.reject(MSG_ERR_NO_EXTERNAL_SELECTION, getTabName());
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		// retained for the abstract contract; the doIt()/checkPreconditions overrides no longer route
		// through this single-child accessor (a parent may have several children).
		if (this.childConfigId > 0)
		{
			return ExternalSystemScriptedImportConversionConfigId.ofRepoId(this.childConfigId);
		}
		final IExternalSystemChildConfig childConfig = externalSystemConfigDAO
				.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
				.orElseThrow(() -> new AdempiereException("No childConfig found for type Invoke Scripted Import Conversion and parent config")
						.appendParametersToMessage()
						.setParameter("externalSystemParentConfigId", getRecord_ID()));
		return childConfig.getId();
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemScriptedImportConversionConfig config = ExternalSystemScriptedImportConversionConfig
				.cast(externalSystemParentConfig.getChildConfig());

		return externalSystemScriptedImportConversionService.getParameters(config);
	}

	@Override
	protected String getTabName()
	{
		return ExternalSystemType.ScriptedImportConversion.getValue();
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.ScriptedImportConversion;
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_ScriptedImportConversion.Table_Name.equals(recordRef.getTableName()))
				.count();
	}
}
