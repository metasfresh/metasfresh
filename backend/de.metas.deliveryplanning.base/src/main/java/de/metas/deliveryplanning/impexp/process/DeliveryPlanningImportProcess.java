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

package de.metas.deliveryplanning.impexp.process;

import de.metas.deliveryplanning.DeliveryPlanningRepository;
import de.metas.deliveryplanning.impexp.DeliveryPlanningDataId;
import de.metas.deliveryplanning.impexp.DeliveryPlanningDataRepository;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_DeliveryPlanning;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.X_I_DeliveryPlanning;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Import {@link org.compiere.model.I_I_DeliveryPlanning} to {@link org.compiere.model.I_M_Delivery_Planning}.
 */
public class DeliveryPlanningImportProcess extends SimpleImportProcessTemplate<I_I_DeliveryPlanning>
{
	DeliveryPlanningRepository deliveryPlanningRepository = SpringContextHolder.instance.getBean(DeliveryPlanningRepository.class);
	DeliveryPlanningDataRepository deliveryPlanningDataRepository = SpringContextHolder.instance.getBean(DeliveryPlanningDataRepository.class);

	Set<DeliveryPlanningDataId> deliveryPlanningDataIds = new HashSet<>();

	@Override
	public Class<I_I_DeliveryPlanning> getImportModelClass()
	{
		return I_I_DeliveryPlanning.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_DeliveryPlanning.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_M_Delivery_Planning.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_DeliveryPlanning.COLUMNNAME_I_LineNo;
	}

	@Override
	protected I_I_DeliveryPlanning retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_DeliveryPlanning(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected ImportRecordResult importRecord(
			@NonNull final IMutable<Object> state_NOTUSED,
			@NonNull final I_I_DeliveryPlanning importRecord,
			final boolean isInsertOnly_NOTUSED)
	{
		deliveryPlanningDataIds.add(DeliveryPlanningDataId.ofRepoId(importRecord.getI_DeliveryPlanning_Data_ID()));
		return importDeliveryPlanning(importRecord);
	}

	private ImportRecordResult importDeliveryPlanning(@NonNull final I_I_DeliveryPlanning importRecord)
	{
		ImportRecordResult importResult = ImportRecordResult.Nothing;
		final String documentNo = importRecord.getDocumentNo();
		if (Check.isBlank(documentNo))
		{
			importRecord.setI_ErrorMsg("No Delivery Instruction Document No");
		}
		else
		{
			final List<I_M_Delivery_Planning> deliveryPlannings = deliveryPlanningRepository.getByReleaseNo(documentNo);
			if (deliveryPlannings.size() == 0)
			{
				importRecord.setI_ErrorMsg("No Delivery Instruction Document No matching");
			}
			else if (deliveryPlannings.size() > 1)
			{
				importRecord.setI_ErrorMsg("Multiple Delivery Instruction Document No matching");
			}
			else
			{
				final I_M_Delivery_Planning deliveryPlanning = deliveryPlannings.get(0);
				if (deliveryPlanning.isProcessed())
				{
					importRecord.setI_ErrorMsg("Delivery planning is already processed");
				}
				else
				{
					updateDeliveryPlanning(importRecord, deliveryPlanning);
					importRecord.setM_Delivery_Planning_ID(deliveryPlanning.getM_Delivery_Planning_ID());
					importResult = ImportRecordResult.Updated;
				}
			}
		}
		InterfaceWrapperHelper.save(importRecord);

		return importResult;
	}

	private void updateDeliveryPlanning(final I_I_DeliveryPlanning importRecord, final I_M_Delivery_Planning deliveryPlanning)
	{
		deliveryPlanning.setActualLoadingDate(importRecord.getActualLoadingDate());
		deliveryPlanning.setActualLoadQty(importRecord.getActualLoadQty());
		deliveryPlanning.setActualDeliveryDate(importRecord.getActualDeliveryDate());
		deliveryPlanning.setActualDischargeQuantity(importRecord.getActualDischargeQuantity());
		InterfaceWrapperHelper.save(deliveryPlanning);
	}

	protected void afterImport()
	{
		deliveryPlanningDataIds.forEach((id) ->
				deliveryPlanningDataRepository.save(deliveryPlanningDataRepository.getById(id)
						.toBuilder()
						.processed(true)
						.build()));
	}
}
