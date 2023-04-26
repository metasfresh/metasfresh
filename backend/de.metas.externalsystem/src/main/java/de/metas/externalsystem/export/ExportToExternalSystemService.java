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

package de.metas.externalsystem.export;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.audit.data.model.DataExportAudit;
import de.metas.audit.data.model.DataExportAuditId;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public abstract class ExportToExternalSystemService
{
	private static final Logger logger = LogManager.getLogger(ExportToExternalSystemService.class);

	protected final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	protected final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	protected final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final DataExportAuditRepository dataExportAuditRepository;
	private final DataExportAuditLogRepository dataExportAuditLogRepository;
	private final ExternalSystemMessageSender externalSystemMessageSender;

	protected ExportToExternalSystemService(
			@NonNull final DataExportAuditRepository dataExportAuditRepository,
			@NonNull final DataExportAuditLogRepository dataExportAuditLogRepository,
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender)
	{
		this.dataExportAuditRepository = dataExportAuditRepository;
		this.dataExportAuditLogRepository = dataExportAuditLogRepository;
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalSystemMessageSender = externalSystemMessageSender;
	}

	/**
	 * Sends a {@link JsonExternalSystemRequest} that requests metasfresh-externalsystems to export the given {@code recordReference}.
	 */
	public void exportToExternalSystem(
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId,
			@NonNull final TableRecordReference recordReference,
			@Nullable final PInstanceId pInstanceId)
	{
		runPreExportHook(recordReference);

		final PInstanceId pInstanceIdWithFallback = Optional.ofNullable(pInstanceId)
				.orElseGet(this::createPInstanceId);

		getExportExternalSystemRequest(externalSystemChildConfigId, recordReference, pInstanceIdWithFallback)
				.ifPresent(externalSystemMessageSender::send);
	}

	public void exportToExternalSystemIfRequired(
			@NonNull final TableRecordReference recordToExportReference,
			@Nullable final Supplier<Optional<Set<IExternalSystemChildConfigId>>> additionalExternalSystemIdsProvider)
	{
		final ImmutableSet.Builder<IExternalSystemChildConfigId> externalSysChildConfigCollector = ImmutableSet.builder();

		externalSysChildConfigCollector.addAll(getExternalSysConfigIdsFromExportAudit(recordToExportReference));

		if (additionalExternalSystemIdsProvider != null)
		{
			additionalExternalSystemIdsProvider.get().ifPresent(externalSysChildConfigCollector::addAll);
		}

		externalSysChildConfigCollector.build().forEach(id -> exportToExternalSystem(id, recordToExportReference, null));
	}

	@NonNull
	protected ImmutableSet<IExternalSystemChildConfigId> getExternalSysConfigIdsFromExportAudit(@NonNull final TableRecordReference tableRecordReference)
	{
		final Optional<DataExportAudit> dataExportAudit = dataExportAuditRepository.getByTableRecordReference(tableRecordReference);
		if (!dataExportAudit.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No dataExportAudit found for tableRecordReference: {}! No action is performed!", tableRecordReference);
			return ImmutableSet.of();
		}

		final DataExportAuditId dataExportAuditId = dataExportAudit.get().getId();

		final ImmutableSet<IExternalSystemChildConfigId> externalSystemConfigIds = getExternalSystemConfigsToSyncWith(dataExportAuditId);

		if (externalSystemConfigIds.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No externalSystemConfigIds found for DataExportAuditId: {}! No action is performed!", dataExportAuditId);
		}

		return externalSystemConfigIds;
	}

	@NonNull
	private ImmutableSet<IExternalSystemChildConfigId> getExternalSystemConfigsToSyncWith(@NonNull final DataExportAuditId dataExportAuditId)
	{
		return dataExportAuditLogRepository.getExternalSystemConfigIds(dataExportAuditId)
				.stream()
				.map(id -> externalSystemConfigRepo.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(id), getExternalSystemType()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(IExternalSystemChildConfig::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private PInstanceId createPInstanceId()
	{
		return Optional.ofNullable(getExportADProcessClassname())
				.map(adProcessDAO::retrieveProcessIdByClassIfUnique)
				.map(adPInstanceDAO::createAD_PInstance)
				.map(I_AD_PInstance::getAD_PInstance_ID)
				.map(PInstanceId::ofRepoId)
				.orElse(null);
	}

	@Nullable
	protected String getExportADProcessClassname()
	{
		return null;
	}

	protected abstract ExternalSystemType getExternalSystemType();

	protected abstract Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			IExternalSystemChildConfigId externalSystemChildConfigId,
			TableRecordReference recordReference,
			PInstanceId pInstanceId);

	protected abstract void runPreExportHook(TableRecordReference recordReferenceToExport);

	public abstract int getCurrentPendingItems();
}