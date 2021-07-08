/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.procurement.base.model.I_PMM_Product.COLUMNNAME_M_Product_ID;

public class PMM_Product_StepDef
{
	private final StepDefData<I_M_Product> productStepDefData;
	private final StepDefData<I_PMM_Product> pmmProductStepDefData;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PMM_Product_StepDef(
			@NonNull final StepDefData<I_M_Product> productStepDefData,
			@NonNull final StepDefData<I_PMM_Product> pmmProductStepDefData)
	{
		this.productStepDefData = productStepDefData;
		this.pmmProductStepDefData = pmmProductStepDefData;
	}

	@Given("metasfresh contains PMM_Products:")
	public void metasfresh_contains_PMM_Products(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String productIdentifier = tableRow.get(COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product productRecord = productStepDefData.get(productIdentifier);

			final I_PMM_Product pmmProductRecord = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_PMM_Product.class)
							.addEqualsFilter(COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
							.create().firstOnly(I_PMM_Product.class),
					() -> InterfaceWrapperHelper.newInstance(I_PMM_Product.class));

			pmmProductRecord.setM_Product_ID(productRecord.getM_Product_ID());

			pmmProductRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			pmmProductRecord.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());

			try (final IAutoCloseable ignored = Services.get(IWebuiPush.class).disable())
			{ // don't fire a message towards the procurement webui, because we don't want the queue be messed up with and unexpected message
				InterfaceWrapperHelper.save(pmmProductRecord);
			}
			pmmProductStepDefData.put(
					DataTableUtil.extractRecordIdentifier(tableRow, "PMM_Product"),
					pmmProductRecord);
		}
	}
}
