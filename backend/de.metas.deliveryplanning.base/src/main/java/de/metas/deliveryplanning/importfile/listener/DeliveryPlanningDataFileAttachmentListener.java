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

package de.metas.deliveryplanning.importfile.listener;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants;
import de.metas.common.util.Check;
import de.metas.deliveryplanning.importfile.DeliveryPlanningDataId;
import de.metas.deliveryplanning.importfile.DeliveryPlanningDataService;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_DeliveryPlanning;
import org.slf4j.Logger;

/**
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class DeliveryPlanningDataFileAttachmentListener implements AttachmentListener
{
	private static final Logger logger = LogManager.getLogger(DeliveryPlanningDataFileAttachmentListener.class);

	private final DeliveryPlanningDataService deliveryPlanningDataService = SpringContextHolder.instance.getBean(DeliveryPlanningDataService.class);
	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	@Override
	@NonNull
	public AttachmentListenerConstants.ListenerWorkStatus afterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		final DeliveryPlanningDataId deliveryPlanningDataId = tableRecordReference
				.getIdAssumingTableName(I_I_DeliveryPlanning.Table_Name, DeliveryPlanningDataId::ofRepoId);

		deliveryPlanningDataService.save(deliveryPlanningDataService.getById(deliveryPlanningDataId)
				.toBuilder()
				.filename(attachmentEntry.getFilename())
				.build());

		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}

	@Override
	@NonNull
	public AttachmentListenerConstants.ListenerWorkStatus beforeRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		Check.assume(tableRecordReference.getTableName().equals(I_I_DeliveryPlanning.Table_Name), "This is only about {}}!", I_I_DeliveryPlanning.Table_Name);

		final boolean attachmentEntryMatch = attachmentEntryService.getByReferencedRecord(tableRecordReference)
				.stream()
				.map(AttachmentEntry::getId)
				.allMatch(attachmentEntryId -> AttachmentEntryId.equals(attachmentEntryId, attachmentEntry.getId()));

		if (!attachmentEntryMatch)
		{
			logger.debug("multiple attachments not allowed for tableRecord reference ={}; -> returning FAILURE", tableRecordReference.getTableName());
			return AttachmentListenerConstants.ListenerWorkStatus.FAILURE;
		}

		return AttachmentListenerConstants.ListenerWorkStatus.SUCCESS;
	}
}
