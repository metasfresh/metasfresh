/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning.impexp.listener;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.common.util.Check;
import de.metas.common.util.time.SystemTime;
import de.metas.deliveryplanning.impexp.DeliveryPlanningDataId;
import de.metas.deliveryplanning.impexp.DeliveryPlanningDataService;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfig;
import de.metas.impexp.config.DataImportConfigId;
import de.metas.impexp.process.AttachmentImportCommand;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_DeliveryPlanning;
import org.compiere.model.I_I_DeliveryPlanning_Data;
import org.slf4j.Logger;

/**
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class DeliveryPlanningDataFileAttachmentListener implements AttachmentListener
{
	private static final Logger logger = LogManager.getLogger(DeliveryPlanningDataFileAttachmentListener.class);
	private static final String DATA_IMPORT_IMPORT_FORMAT_INTERNAL_NAME = "Delivery Planning";

	private final DeliveryPlanningDataService deliveryPlanningDataService = SpringContextHolder.instance.getBean(DeliveryPlanningDataService.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final DataImportService dataImportService = SpringContextHolder.instance.getBean(DataImportService.class);
	final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();

	@Override
	@NonNull
	public AttachmentListenerConstants.ListenerWorkStatus afterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		final DeliveryPlanningDataId deliveryPlanningDataId = tableRecordReference
				.getIdAssumingTableName(I_I_DeliveryPlanning_Data.Table_Name, DeliveryPlanningDataId::ofRepoId);
		deliveryPlanningDataService.save(deliveryPlanningDataService.getById(deliveryPlanningDataId)
				.toBuilder()
				.filename(attachmentEntry.getFilename())
				.importedTimestamp(SystemTime.asTimestamp())
				.readyForProcessing(true)
				.processed(false)
				.build());

		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}

	private DataImportResult tryImport(final DeliveryPlanningDataId deliveryPlanningDataId, final AttachmentEntry attachmentEntry)
	{
		final DataImportResult dataImportResult = AttachmentImportCommand.builder()
				.attachmentEntryId(attachmentEntry.getId())
				.dataImportConfigId(getDataImportConfigId())
				.build()
				.execute();
		deliveryPlanningDataService.assignDeliveryPlanningDataIdToDataImportRunId(deliveryPlanningDataId, dataImportResult.getInsertIntoImportTable().getDataImportRunId());

		return dataImportResult;
	}

	@NonNull
	private DataImportConfigId getDataImportConfigId()
	{
		return dataImportService.getDataImportConfigByInternalName(DATA_IMPORT_IMPORT_FORMAT_INTERNAL_NAME).map(DataImportConfig::getId)
				.orElseThrow(() -> new AdempiereException("No DataImport Format with internalName " + DATA_IMPORT_IMPORT_FORMAT_INTERNAL_NAME));
	}

	@Override
	@NonNull
	public AttachmentListenerConstants.ListenerWorkStatus beforeRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		Check.assume(tableRecordReference.getTableName().equals(I_I_DeliveryPlanning_Data.Table_Name), "This is only about {}}!", I_I_DeliveryPlanning_Data.Table_Name);

		final boolean attachmentAlreadySaved = attachmentEntryService.getByReferencedRecord(tableRecordReference)
				.stream()
				.map(AttachmentEntry::getId)
				.anyMatch(attachmentEntryId -> AttachmentEntryId.equals(attachmentEntryId, attachmentEntry.getId()));
		if (attachmentAlreadySaved)
		{
			//Don't revalidate
			return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
		}
		if (deliveryPlanningDataService.getById(DeliveryPlanningDataId.ofRepoId(tableRecordReference.getRecord_ID())).isReadyForProcessing())
		{
			logger.debug("Already processed");

			return AttachmentListenerConstants.ListenerWorkStatus.FAILURE;
		}
		final DeliveryPlanningDataId deliveryPlanningDataId = tableRecordReference.getIdAssumingTableName(I_I_DeliveryPlanning_Data.Table_Name, DeliveryPlanningDataId::ofRepoId);
		try
		{
			tryImport(deliveryPlanningDataId, attachmentEntry);
			final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.allChildRecords(I_I_DeliveryPlanning_Data.Table_Name, tableRecordReference.getRecord_ID(), I_I_DeliveryPlanning.Table_Name);
			modelCacheInvalidationService.invalidate(request, ModelCacheInvalidationTiming.AFTER_CHANGE);
		}
		catch (final Exception ex)
		{
			logger.debug("Could not import Delivery planning", ex);
			return AttachmentListenerConstants.ListenerWorkStatus.FAILURE;
		}
		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}
}
