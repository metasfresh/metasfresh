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

package de.metas.deliveryplanning.impexp;

import de.metas.impexp.DataImportRunId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_DeliveryPlanning;
import org.compiere.model.I_I_DeliveryPlanning_Data;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class DeliveryPlanningDataRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public DeliveryPlanningData getById(@NonNull final DeliveryPlanningDataId deliveryPlanningDataId)
	{
		return ofRecord(getRecordById(deliveryPlanningDataId));
	}

	public void save(@NonNull final DeliveryPlanningData deliveryPlanningData)
	{
		final I_I_DeliveryPlanning_Data deliveryPlanningRecord = getRecordById(deliveryPlanningData.getDeliveryPlanningDataId());

		deliveryPlanningRecord.setProcessed(deliveryPlanningData.isProcessed());
		deliveryPlanningRecord.setIsReadyForProcessing(deliveryPlanningData.isReadyForProcessing());
		deliveryPlanningRecord.setFileName(deliveryPlanningData.getFilename());
		deliveryPlanningRecord.setImported(deliveryPlanningData.getImportedTimestamp());

		saveRecord(deliveryPlanningRecord);
	}

	@NonNull
	private I_I_DeliveryPlanning_Data getRecordById(@NonNull final DeliveryPlanningDataId deliveryPlanningDataId)
	{
		final I_I_DeliveryPlanning_Data record = InterfaceWrapperHelper.load(deliveryPlanningDataId, I_I_DeliveryPlanning_Data.class);
		if (record == null)
		{
			throw new AdempiereException("No I_I_DeliveryPlanning_Data found for id!")
					.appendParametersToMessage()
					.setParameter("I_DeliveryPlanning_Data_ID", deliveryPlanningDataId);
		}

		return record;
	}

	@NonNull
	private static DeliveryPlanningData ofRecord(@NonNull final I_I_DeliveryPlanning_Data record)
	{
		return DeliveryPlanningData.builder()
				.deliveryPlanningDataId(DeliveryPlanningDataId.ofRepoId(record.getI_DeliveryPlanning_Data_ID()))
				.filename(record.getFileName())
				.importedTimestamp(record.getImported())
				.processed(record.isProcessed())
				.readyForProcessing(record.isReadyForProcessing())
				.build();
	}

	public int assignDeliveryPlanningDataIdToDataImportRunId(@NonNull final DeliveryPlanningDataId deliveryPlanningDataId, @NonNull final DataImportRunId dataImportRunId)
	{
		return queryBL.createQueryBuilder(I_I_DeliveryPlanning.class)
				.addEqualsFilter(I_I_DeliveryPlanning.COLUMNNAME_C_DataImport_Run_ID, dataImportRunId)
				.create()
				.updateDirectly(queryBL.createCompositeQueryUpdater(I_I_DeliveryPlanning.class)
						.addSetColumnValue(I_I_DeliveryPlanning.COLUMNNAME_I_DeliveryPlanning_Data_ID, deliveryPlanningDataId));
	}
}
