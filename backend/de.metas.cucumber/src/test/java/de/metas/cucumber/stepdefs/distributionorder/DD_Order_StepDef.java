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
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.product.ResourceId;
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
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class DD_Order_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final S_Resource_StepDefData resourceTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;
	@NonNull private final C_Order_StepDefData orderTable;

	@And("metasfresh contains DD_Orders:")
	public void metasfresh_contains_dd_orders(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DD_Order.COLUMNNAME_DD_Order_ID)
				.forEach(row -> {
					final BPartnerId bpartnerId = row.getAsIdentifier(I_DD_Order.COLUMNNAME_C_BPartner_ID).lookupIdIn(bPartnerTable);

					final WarehouseId fromWarehouseId = row.getAsIdentifier("M_Warehouse_ID.From").lookupIdIn(warehouseTable);
					final WarehouseId toWarehouseId = row.getAsIdentifier("M_Warehouse_ID.To").lookupIdIn(warehouseTable);
					final WarehouseId transitWarehouseId = row.getAsIdentifier("M_Warehouse_ID.Transit").lookupIdIn(warehouseTable);

					final StepDefDataIdentifier plantIdentifier = row.getAsIdentifier("S_Resource_ID");
					final ResourceId plantId = resourceTable.getIdOptional(plantIdentifier).orElseGet(() -> plantIdentifier.getAsId(ResourceId.class));

					final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstanceOutOfTrx(I_DD_Order.class);

					ddOrder.setC_BPartner_ID(bpartnerId.getRepoId());
					ddOrder.setM_Warehouse_From_ID(fromWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_To_ID(toWarehouseId.getRepoId());
					ddOrder.setM_Warehouse_ID(transitWarehouseId.getRepoId());
					ddOrder.setPP_Plant_ID(plantId.getRepoId());
					ddOrder.setIsInDispute(false);
					ddOrder.setIsSOTrx(false);
					ddOrder.setIsInTransit(false);
					ddOrder.setDeliveryRule(X_DD_Order.DELIVERYRULE_Availability);

					row.getAsOptionalString(I_DD_Order.COLUMNNAME_C_DocType_ID + "." + I_C_DocType.COLUMNNAME_Name)
							.ifPresent(docTypeDistributionName -> {
								final DocTypeId docTypeId = queryBL.createQueryBuilder(I_C_DocType.class)
										.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, docTypeDistributionName)
										.create()
										.firstId(DocTypeId::ofRepoIdOrNull);

								assertThat(docTypeId).isNotNull();
								ddOrder.setC_DocType_ID(docTypeId.getRepoId());
							});

					ddOrderService.save(ddOrder);

					row.getAsOptionalIdentifier().ifPresent(identifier -> ddOrderTable.putOrReplace(identifier, ddOrder));
				});
	}

	@And("^the dd_order identified by (.*) is (completed)$")
	public void order_action(@NonNull final String orderIdentifier, @NonNull final String actionStr)
	{
		final I_DD_Order order = ddOrderTable.get(orderIdentifier);

		final StepDefDocAction action = StepDefDocAction.valueOf(actionStr);
		if (action == StepDefDocAction.completed)
		{
			order.setDocAction(IDocument.ACTION_Complete); // we need this because otherwise MOrder.completeIt() won't complete it
			documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
		else
		{
			throw new AdempiereException("Unhandled DD_Order action")
					.appendParametersToMessage()
					.setParameter("action", actionStr);
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
}
