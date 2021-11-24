/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.productionorder;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class PP_Order_StepDef
{

	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_PP_Product_BOM> productBOMTable;
	private final StepDefData<I_PP_Product_Planning> productPlanningTable;
	private final StepDefData<I_C_BPartner> bPartnerTable;
	private final StepDefData<I_PP_Order> ppOrderTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PP_Order_StepDef(
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_PP_Product_BOM> productBOMTable,
			@NonNull final StepDefData<I_PP_Product_Planning> productPlanningTable,
			@NonNull final StepDefData<I_C_BPartner> bPartnerTable,
			@NonNull final StepDefData<I_PP_Order> ppOrderTable)
	{
		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productPlanningTable = productPlanningTable;
		this.bPartnerTable = bPartnerTable;
		this.ppOrderTable = ppOrderTable;
	}

	@And("^after not more than (.*)s, PP_Orders are found$")
	public void validatePP_Order_Candidate(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_Order(timeoutSec, row);
		}
	}

	@And("^after not more than (.*)s, PP_Order_BomLines are found$")
	public void validatePP_Order_BomLine(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_Order_BomLine(timeoutSec, row);
		}
	}

	private void validatePP_Order_BomLine(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrderRecord = ppOrderTable.get(ppOrderIdentifier);

		final int qtyRequired = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered);
		final boolean isQtyPercentage = DataTableUtil.extractBooleanForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_IsQtyPercentage);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String componentType = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_ComponentType);
		final Supplier<Boolean> ppOrderBOMLineQueryExecutor = () -> {

			final I_PP_Order_BOMLine orderBOMLineRecord = queryBL.createQueryBuilder(I_PP_Order_BOMLine.class)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrderRecord.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered, qtyRequired)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_IsQtyPercentage, isQtyPercentage)
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, componentType)
					.create()
					.firstOnly(I_PP_Order_BOMLine.class);

			return orderBOMLineRecord != null;
		};

		final boolean bomLineFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderBOMLineQueryExecutor);

		assertThat(bomLineFound).isTrue();
	}

	private void validatePP_Order(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String productBOMIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM productBOMRecord = productBOMTable.get(productBOMIdentifier);

		final String productPlanningIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_Planning productPlanningRecord = productPlanningTable.get(productPlanningIdentifier);

		final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID);
		final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyEntered);
		final int qtyOrdered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order.COLUMNNAME_QtyOrdered);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartnerRecord = bPartnerTable.get(bPartnerIdentifier);

		final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DatePromised);

		final String orderRecordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Order.Table_Name);
		final Supplier<Boolean> ppOrderQueryExecutor = () -> {

			final I_PP_Order orderRecord = queryBL.createQueryBuilder(I_PP_Order.class)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_BOM_ID, productBOMRecord.getPP_Product_BOM_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Product_Planning_ID, productPlanningRecord.getPP_Product_Planning_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_S_Resource_ID, resourceId)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyEntered, qtyEntered)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_QtyOrdered, qtyOrdered)
					.addEqualsFilter(I_PP_Order.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_C_BPartner_ID, bPartnerRecord.getC_BPartner_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_DatePromised, datePromised)
					.create()
					.firstOnly(I_PP_Order.class);

			if (orderRecord != null)
			{
				ppOrderTable.putOrReplace(orderRecordIdentifier, orderRecord);
				return true;
			}
			else
			{
				return false;
			}
		};

		final boolean orderFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderQueryExecutor);

		assertThat(orderFound).isTrue();
	}
}