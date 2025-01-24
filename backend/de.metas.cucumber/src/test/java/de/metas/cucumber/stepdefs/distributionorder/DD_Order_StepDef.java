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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import javax.annotation.Nullable;

@RequiredArgsConstructor
public class DD_Order_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final S_Resource_StepDefData resourceTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("DD_Orders are found:")
	public void find_DDOrders(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findDDOrder(tableRow);
		}
	}

	@And("metasfresh contains DD_Orders:")
	public void metasfresh_contains_dd_orders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> {
					final OrgId orgId = Env.getOrgId();
					final BPartnerId bpartnerId = Optionals.firstPresentOfSuppliers(
									() -> row.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_C_BPartner_ID).map(bPartnerTable::getId),
									() -> bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
							)
							.orElse(null);

					final BPartnerLocationId bpartnerLocationId = bpartnerId != null
							? bpartnerDAO.retrieveBPartnerLocationId(BPartnerLocationQuery.builder().bpartnerId(bpartnerId).type(BPartnerLocationQuery.Type.SHIP_TO).build())
							: null;

					final WarehouseId fromWarehouseId = row.getAsIdentifier("M_Warehouse_ID.From").lookupIdIn(warehouseTable);
					final WarehouseId toWarehouseId = row.getAsIdentifier("M_Warehouse_ID.To").lookupIdIn(warehouseTable);
					final WarehouseId transitWarehouseId = row.getAsIdentifier("M_Warehouse_ID.Transit").lookupIdIn(warehouseTable);

					final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstanceOutOfTrx(I_DD_Order.class);
					ddOrder.setAD_Org_ID(orgId.getRepoId());
					ddOrder.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
					ddOrder.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
					ddOrder.setM_Warehouse_From_ID(fromWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_To_ID(toWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_ID(transitWarehouseId.getRepoId());
					ddOrder.setIsInDispute(false);
					ddOrder.setIsSOTrx(false);
					ddOrder.setIsInTransit(false);
					ddOrder.setDeliveryRule(X_DD_Order.DELIVERYRULE_Availability);

					row.getAsOptionalIdentifier("S_Resource_ID")
							.map(plantIdentifier -> resourceTable.getIdOptional(plantIdentifier).orElseGet(() -> plantIdentifier.getAsId(ResourceId.class)))
							.ifPresent(plantId -> ddOrder.setPP_Plant_ID(plantId.getRepoId()));

					final String docTypeName = row.getAsOptionalString(I_DD_Order.COLUMNNAME_C_DocType_ID + "." + I_C_DocType.COLUMNNAME_Name).orElse(null);
					final DocTypeId docTypeId = findDocTypeId(orgId, docTypeName);
					ddOrder.setC_DocType_ID(docTypeId.getRepoId());

					ddOrderService.save(ddOrder);

					row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
				});
	}

	private DocTypeId findDocTypeId(@NonNull final OrgId orgId, @Nullable final String docTypeName)
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.DistributionOrder)
				.name(docTypeName)
				.clientAndOrgId(Env.getClientId(), orgId)
				.build());
	}


	@And("^the dd_order identified by (.*) is (completed|reactivated|reversed|voided|closed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String action)
	{
		final I_DD_Order order = ddOrderTable.get(orderIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			case reactivated:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				break;
			case reversed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
				break;
			case voided:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			case closed:
				order.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(order, IDocument.ACTION_Close, IDocument.STATUS_Closed);
				break;
			default:
				throw new AdempiereException("Unhandled DD_Order action")
						.appendParametersToMessage()
						.setParameter("action:", action);
		}
	}

	@And("^after not more than (.*)s, following DD_Orders are found$")
	public void validateDDOrders(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> validateDDOrder(timeoutSec, row));
	}

	private void validateDDOrder(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_DD_Order ddOrder = StepDefUtil.tryAndWaitForItem(toSqlQuery(row))
				.validateUsingConsumer(record -> validateDDOrder(record, row))
				.maxWaitSeconds(timeoutSec)
				.execute();

		row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
	}

	private void validateDDOrder(final I_DD_Order actual, @NonNull final DataTableRow expected)
	{
		final SoftAssertions softly = new SoftAssertions();

		expected.getAsOptionalEnum("DocStatus", DocStatus.class)
				.ifPresent(expectedDocStatus -> {
					final DocStatus actualDocStatus = DocStatus.ofNullableCodeOrUnknown(actual.getDocStatus());
					softly.assertThat(actualDocStatus).as("DocStatus").isEqualTo(expectedDocStatus);
				});

		final StepDefDataIdentifier ppOrderIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_Forward_PP_Order_ID).orElse(null);
		if (ppOrderIdentifier != null)
		{
			final PPOrderId expectedPPOrderId = ppOrderIdentifier.lookupIdIn(ppOrderTable);
			final PPOrderId actualPPOrderId = PPOrderId.ofRepoIdOrNull(actual.getForward_PP_Order_ID());
			softly.assertThat(actualPPOrderId).as("Forward_PP_Order_ID").isEqualTo(expectedPPOrderId);
		}

		final StepDefDataIdentifier ppOrderBOMLineIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_Forward_PP_Order_BOMLine_ID).orElse(null);
		if (ppOrderBOMLineIdentifier != null)
		{
			final PPOrderBOMLineId expectedPPOrderBOMLineId = ppOrderBOMLineIdentifier.lookupIdIn(ppOrderBOMLineTable);
			final PPOrderBOMLineId actualPPOrderBOMLineId = PPOrderBOMLineId.ofRepoIdOrNull(actual.getForward_PP_Order_BOMLine_ID());
			softly.assertThat(actualPPOrderBOMLineId).as("Forward_PP_Order_BOMLine_ID").isEqualTo(expectedPPOrderBOMLineId);
		}

		final StepDefDataIdentifier salesOrderIdentifier = expected.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_C_Order_ID).orElse(null);
		if (salesOrderIdentifier != null)
		{
			final OrderId expectedOrderId = salesOrderIdentifier.lookupIdIn(orderTable);
			final OrderId actualOrderId = OrderId.ofRepoIdOrNull(actual.getC_Order_ID());
			softly.assertThat(actualOrderId).as("C_Order_ID").isEqualTo(expectedOrderId);
		}

		softly.assertAll();
	}

	private IQuery<I_DD_Order> toSqlQuery(final DataTableRow row)
	{
		final DDOrderId ddOrderId = row.getAsIdentifier().lookupIdIn(ddOrderTable);
		return queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderId)
				.create();
	}

	@And("DD_Orders are validated:")
	public void validate_DD_Orders(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateDDOrder(tableRow);
		}
	}

	private void validateDDOrder(@NonNull final Map<String, String> tableRow)
	{
		final SoftAssertions softly = new SoftAssertions();

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_Order ddOrderRecord = ddOrderTable.get(ddOrderIdentifier);
		softly.assertThat(ddOrderRecord).isNotNull();

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseIdentifier))
		{
			final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
			softly.assertThat(warehouseRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_ID()).isEqualTo(warehouseRecord.getM_Warehouse_ID());
		}

		final String warehouseFromIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_From_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseFromIdentifier))
		{
			final I_M_Warehouse warehouseFromRecord = warehouseTable.get(warehouseFromIdentifier);
			softly.assertThat(warehouseFromRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_From_ID()).isEqualTo(warehouseFromRecord.getM_Warehouse_ID());
		}

		final String warehouseToIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Warehouse_To_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(warehouseToIdentifier))
		{
			final I_M_Warehouse warehouseToRecord = warehouseTable.get(warehouseToIdentifier);
			softly.assertThat(warehouseToRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Warehouse_To_ID()).isEqualTo(warehouseToRecord.getM_Warehouse_ID());
		}

		final String shipperIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipperIdentifier))
		{
			final I_M_Shipper shipperRecord = shipperTable.get(shipperIdentifier);
			softly.assertThat(shipperRecord).isNotNull();
			softly.assertThat(ddOrderRecord.getM_Shipper_ID()).isEqualTo(shipperRecord.getM_Shipper_ID());
		}

		final String docStatus = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_Order.COLUMNNAME_DocStatus);
		if (Check.isNotBlank(docStatus))
		{
			softly.assertThat(ddOrderRecord.getDocStatus()).isEqualTo(docStatus);
		}

		softly.assertAll();
	}

	private void findDDOrder(@NonNull final Map<String, String> tableRow)
	{
		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		assertThat(ddOrderLineRecord).isNotNull();

		final I_DD_Order ddOrderRecord = InterfaceWrapperHelper.load(ddOrderLineRecord.getDD_Order_ID(), I_DD_Order.class);

		final String ddOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_Order.COLUMNNAME_DD_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		ddOrderTable.putOrReplace(ddOrderIdentifier, ddOrderRecord);
	}
}
