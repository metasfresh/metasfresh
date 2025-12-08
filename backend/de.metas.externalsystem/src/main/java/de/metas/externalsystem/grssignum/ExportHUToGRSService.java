/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.grssignum;

import com.google.common.collect.ImmutableList;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.document.engine.DocStatus;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.export.hu.ExportHUCandidate;
import de.metas.externalsystem.export.hu.ExportHUToExternalSystemService;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.handlingunits.trace.HUTraceType.MATERIAL_RECEIPT;
import static de.metas.handlingunits.trace.HUTraceType.PRODUCTION_RECEIPT;
import static de.metas.handlingunits.trace.HUTraceType.TRANSFORM_LOAD;
import static de.metas.handlingunits.trace.HUTraceType.TRANSFORM_PARENT;

@Service
public class ExportHUToGRSService extends ExportHUToExternalSystemService
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_HU = "exportHU";
	private static final AdMessageKey MSG_HU_LOCKED_CLEARANCE_STATUS_NOTE = AdMessageKey.of("HULockedClearanceStatusNote");

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	protected ExportHUToGRSService(
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender, externalSystemConfigService);
	}

	public void exportHUToGRS(final I_M_HU_Trace huTrace)
	{
		if (!externalSystemConfigRepo.isAnyConfigActive(ExternalSystemType.GRSSignum))
		{
			return;
		}

		if (shouldExportDirectly(huTrace))
		{
			directlyExportToAllMatchingConfigs(huTrace);
			return;
		}

		if (shouldExportIfAlreadyExportedOnce(HUTraceType.ofCode(huTrace.getHUTraceType())))
		{
			exportIfAlreadyExportedOnce(huTrace);
		}
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.GRSSignum;
	}

	@Override
	protected Map<String, String> buildParameters(@NonNull final IExternalSystemChildConfig childConfig, @NonNull final HuId huId)
	{
		final ExternalSystemGRSSignumConfig grsSignumConfig = ExternalSystemGRSSignumConfig.cast(childConfig);

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_HTTP_URL, grsSignumConfig.getBaseUrl());
		parameters.put(ExternalSystemConstants.PARAM_TENANT_ID, grsSignumConfig.getTenantId());
		parameters.put(ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE, grsSignumConfig.getValue());
		parameters.put(ExternalSystemConstants.PARAM_EXTERNAL_SYSTEM_AUTH_TOKEN, grsSignumConfig.getAuthToken());
		parameters.put(ExternalSystemConstants.PARAM_HU_ID, String.valueOf(huId.getRepoId()));

		return parameters;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_HU;
	}

	@NonNull
	protected Optional<Set<IExternalSystemChildConfigId>> getAdditionalExternalSystemConfigIds(@NonNull final ExportHUCandidate exportHUCandidate)
	{
		if (exportHUCandidate.getLinkedSourceVHuId() == null)
		{
			return Optional.empty();
		}

		final I_M_HU sourceTopLevel = handlingUnitsBL.getTopLevelParent(exportHUCandidate.getLinkedSourceVHuId());
		final HuId sourceTopLevelId = HuId.ofRepoId(sourceTopLevel.getM_HU_ID());

		final TableRecordReference sourceTopLevelHUTableRecordRef = TableRecordReference.of(I_M_HU.Table_Name, sourceTopLevelId);

		return Optional.of(getExternalSysConfigIdsFromExportAudit(sourceTopLevelHUTableRecordRef));
	}

	@Override
	protected void runPreExportHook(final TableRecordReference recordReferenceToExport)
	{
	}

	private void exportIfAlreadyExportedOnce(@NonNull final I_M_HU_Trace huTrace)
	{
		final ExportHUCandidate exportHUCandidate = ExportHUCandidate.builder()
				.huId(HuId.ofRepoId(huTrace.getM_HU_ID()))
				.linkedSourceVHuId(HuId.ofRepoIdOrNull(huTrace.getVHU_Source_ID()))
				.build();

		enqueueHUExport(exportHUCandidate);
	}

	private void directlyExportToAllMatchingConfigs(@NonNull final I_M_HU_Trace huTrace)
	{
		final ImmutableList<ExternalSystemParentConfig> configs = externalSystemConfigRepo.getActiveByType(ExternalSystemType.GRSSignum);

		for (final ExternalSystemParentConfig config : configs)
		{
			final ExternalSystemGRSSignumConfig grsConfig = ExternalSystemGRSSignumConfig.cast(config.getChildConfig());

			if (!shouldExportToExternalSystem(grsConfig, HUTraceType.ofCode(huTrace.getHUTraceType())))
			{
				continue;
			}

			final I_M_HU topLevelHU = handlingUnitsBL.getTopLevelParent(HuId.ofRepoId(huTrace.getM_HU_ID()));

			final TableRecordReference topLevelHURecordRef = TableRecordReference.of(topLevelHU);

			exportToExternalSystem(grsConfig.getId(), topLevelHURecordRef, null);
		}
	}

	private boolean shouldExportDirectly(@NonNull final I_M_HU_Trace huTrace)
	{
		final HUTraceType huTraceType = HUTraceType.ofCode(huTrace.getHUTraceType());

		final boolean purchasedOrProduced = huTraceType.equals(MATERIAL_RECEIPT) || huTraceType.equals(PRODUCTION_RECEIPT);

		final boolean docCompleted = DocStatus.ofCodeOptional(huTrace.getDocStatus())
				.map(DocStatus::isCompleted)
				.orElse(false);

		return purchasedOrProduced && docCompleted;
	}

	private boolean shouldExportIfAlreadyExportedOnce(@NonNull final HUTraceType huTraceType)
	{
		return huTraceType.equals(TRANSFORM_LOAD) || huTraceType.equals(TRANSFORM_PARENT);
	}

	private boolean shouldExportToExternalSystem(@NonNull final ExternalSystemGRSSignumConfig grsSignumConfig, @NonNull final HUTraceType huTraceType)
	{
		switch (huTraceType)
		{
			case MATERIAL_RECEIPT:
				return grsSignumConfig.isSyncHUsOnMaterialReceipt();
			case PRODUCTION_RECEIPT:
				return grsSignumConfig.isSyncHUsOnProductionReceipt();
			default:
				return false;
		}
	}
}
