/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.distributionorder;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DD_Order_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final DD_Order_StepDefData ddOrderTable;
	private final S_Resource_StepDefData resourceTable;

	private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	public DD_Order_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final DD_Order_StepDefData ddOrderTable,
			@NonNull final S_Resource_StepDefData resourceTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.warehouseTable = warehouseTable;
		this.ddOrderTable = ddOrderTable;
		this.resourceTable = resourceTable;
	}

	@And("metasfresh contains DD_Orders:")
	public void metasfresh_contains_dd_orders(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);

			final String fromWareHouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Warehouse.COLUMNNAME_M_Warehouse_ID + ".From." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse fromWarehouse = warehouseTable.get(fromWareHouseIdentifier);

			final String toWareHouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Warehouse.COLUMNNAME_M_Warehouse_ID + ".To." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse toWarehouse = warehouseTable.get(toWareHouseIdentifier);

			final String transitWareHouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Warehouse.COLUMNNAME_M_Warehouse_ID + ".Transit." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Warehouse transitWarehouse = warehouseTable.get(transitWareHouseIdentifier);

			final String docTypeDistributionName = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_DD_Order.COLUMNNAME_C_DocType_ID + "." + I_C_DocType.COLUMNNAME_Name);

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Resource testResource = resourceTable.get(resourceIdentifier);
			Assertions.assertThat(testResource).isNotNull();
			final ResourceId resourceId = ResourceId.ofRepoId(testResource.getS_Resource_ID());

			final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstanceOutOfTrx(I_DD_Order.class);

			ddOrder.setC_BPartner_ID(bPartner.getC_BPartner_ID());
			ddOrder.setM_Warehouse_From_ID(fromWarehouse.getM_Warehouse_ID());
			ddOrder.setM_Warehouse_To_ID(toWarehouse.getM_Warehouse_ID());
			ddOrder.setM_Warehouse_ID(transitWarehouse.getM_Warehouse_ID());
			ddOrder.setPP_Plant_ID(resourceId.getRepoId());
			ddOrder.setIsInDispute(false);
			ddOrder.setIsSOTrx(false);
			ddOrder.setIsInTransit(false);
			ddOrder.setDeliveryRule(X_DD_Order.DELIVERYRULE_Availability);

			if (Check.isNotBlank(docTypeDistributionName))
			{
				final DocTypeId docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
						.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, docTypeDistributionName)
						.create()
						.firstId(DocTypeId::ofRepoIdOrNull);

				assertThat(docTypeId).isNotNull();
				ddOrder.setC_DocType_ID(docTypeId.getRepoId());
			}

			ddOrderService.save(ddOrder);

			final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, StepDefConstants.TABLECOLUMN_IDENTIFIER);
			ddOrderTable.putOrReplace(recordIdentifier, ddOrder);
		}
	}

	@And("^the dd_order identified by (.*) is (completed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String action)
	{
		final I_DD_Order order = ddOrderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed:
				order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
				documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			default:
				throw new AdempiereException("Unhandled DD_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}
}
