/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.process;

import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ASIQueryFilterModifier;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.List;
import java.util.stream.Collectors;

public class QtyDemand_QtySupply_V_to_ReceiptSchedule extends JavaProcess implements IProcessPrecondition
{
	IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_QtyDemand_QtySupply_V currentRow = InterfaceWrapperHelper.load(getRecord_ID(), I_QtyDemand_QtySupply_V.class);

		final List<TableRecordReference> recordReferences = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID, currentRow.getM_Product_ID())
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID, currentRow.getM_Warehouse_ID())
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_AD_Org_ID, currentRow.getAD_Org_ID())
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_AttributeSetInstance_ID, currentRow.getAttributesKey(), ASIQueryFilterModifier.instance)
				.addNotEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_QtyToMove, 0)
				.create()
				.listIds(ReceiptScheduleId::ofRepoId)
				.stream()
				.map(id -> TableRecordReference.of(I_M_ReceiptSchedule.Table_Name, id))
				.collect(Collectors.toList());
		getResult().setRecordsToOpen(recordReferences);
		return MSG_OK;
	}

}
