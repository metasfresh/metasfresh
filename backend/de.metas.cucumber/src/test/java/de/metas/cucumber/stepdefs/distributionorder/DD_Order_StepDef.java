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
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.cucumber.stepdefs.ddordercandidate.DD_Order_Candidate_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocList;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
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
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

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
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository = SpringContextHolder.instance.getBean(DDOrderCandidateAllocRepository.class);
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;
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
					final OrgId orgId = Env.getOrgId();
					final BPartnerId bpartnerId = Optionals.firstPresentOfSuppliers(
									() -> row.getAsOptionalIdentifier(I_DD_Order.COLUMNNAME_C_BPartner_ID).map(bPartnerTable::getId),
									() -> bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
							)
							.orElse(null);

					final BPartnerLocationId bpartnerLocationId = bpartnerId != null
							? bpartnerDAO.retrieveBPartnerLocationId(BPartnerLocationQuery.builder().bpartnerId(bpartnerId).type(BPartnerLocationQuery.Type.SHIP_TO).build())
							: null;

					final WarehouseId fromWarehouseId = row.getAsIdentifier("M_Warehouse_ID.From").lookupNotNullIdIn(warehouseTable);
					final WarehouseId toWarehouseId = row.getAsIdentifier("M_Warehouse_ID.To").lookupNotNullIdIn(warehouseTable);
					final WarehouseId transitWarehouseId = row.getAsIdentifier("M_Warehouse_ID.Transit").lookupNotNullIdIn(warehouseTable);

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

	/**
	 * Waits until the given DD_Order_Candidate has been turned into a DD_Order and stores that DD_Order.
	 * If the row's Identifier already names a DD_Order (from an earlier row), the candidate's DD_Order must be that same one,
	 * which is how a test asserts that several candidates were generated into one DD_Order.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias under which the DD_Order is stored<br>
	 *   <b>DD_Order_Candidate_ID</b> — (required, identifier-ref) the candidate the DD_Order was generated from<br>
	 *   <b>DocStatus</b> — (optional) expected doc status<br>
	 *   <b>Forward_PP_Order_ID</b>, <b>Forward_PP_Order_BOMLine_ID</b>, <b>C_Order_ID</b> — (optional, identifier-ref) expected header references<br>
	 *   <b>DD_OrderLine_ID</b> — (optional) alias under which the DD_OrderLine generated from the candidate is stored<br>
	 *   <b>DD_OrderLine_QtyEntered_Total</b> — (optional) expected sum of the DD_Order's line quantities<br>
	 * @cucumber.depends StepDefData: DD_Order_Candidate_StepDefData, DD_Order_StepDefData, DD_OrderLine_StepDefData, PP_Order_StepDefData, PP_Order_BOMLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, DD_Orders are generated from the following DD_Order_Candidates
	 *   | Identifier | DD_Order_Candidate_ID | DocStatus | Forward_PP_Order_BOMLine_ID |
	 *   | ddOrder    | ddOrderCandidate      | DR        | newBOMLine                  |
	 * </pre>
	 */
	@And("^after not more than (.*)s, DD_Orders are generated from the following DD_Order_Candidates$")
	public void validateDDOrdersGeneratedFromCandidates(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> validateDDOrderGeneratedFromCandidate(timeoutSec, row));
	}

	private void validateDDOrderGeneratedFromCandidate(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final DDOrderCandidateId candidateId = row.getAsIdentifier("DD_Order_Candidate_ID").lookupIdIn(ddOrderCandidateTable);
		final StepDefDataIdentifier ddOrderIdentifier = row.getAsIdentifier();

		final DDOrderCandidateAllocList[] allocsHolder = new DDOrderCandidateAllocList[1];
		StepDefUtil.tryAndWait(timeoutSec, 500, () -> rememberAllocsIfFound(candidateId, allocsHolder));

		final DDOrderId ddOrderId = allocsHolder[0].getDDOrderIds().iterator().next();
		final DDOrderLineId ddOrderLineId = allocsHolder[0].getDDOrderLineIds().iterator().next();
		final DDOrderId knownDDOrderId = ddOrderTable.getIdOptional(ddOrderIdentifier).orElse(null);
		if (knownDDOrderId != null)
		{
			assertThat(ddOrderId).as("DD_Order of DD_Order_Candidate " + candidateId + " vs. " + ddOrderIdentifier).isEqualTo(knownDDOrderId);
		}

		final I_DD_Order ddOrder = ddOrderService.getById(ddOrderId);
		validateDDOrder(ddOrder, row);
		row.getAsOptionalBigDecimal("DD_OrderLine_QtyEntered_Total")
				.ifPresent(expectedTotal -> assertTotalLineQty(ddOrderId, ddOrderIdentifier, expectedTotal));
		ddOrderTable.putOrReplace(ddOrderIdentifier, ddOrder);
		row.getAsOptionalIdentifier("DD_OrderLine_ID")
				.ifPresent(lineIdentifier -> ddOrderLineTable.putOrReplace(lineIdentifier, ddOrderService.getLineById(ddOrderLineId)));
	}

	private boolean rememberAllocsIfFound(@NonNull final DDOrderCandidateId candidateId, @NonNull final DDOrderCandidateAllocList[] allocsHolder)
	{
		final DDOrderCandidateAllocList allocs = ddOrderCandidateAllocRepository.getByCandidateIds(Collections.singleton(candidateId));
		if (allocs.isEmpty())
		{
			return false;
		}
		allocsHolder[0] = allocs;
		return true;
	}

	private void assertTotalLineQty(@NonNull final DDOrderId ddOrderId, @NonNull final StepDefDataIdentifier ddOrderIdentifier, @NonNull final BigDecimal expectedTotal)
	{
		final BigDecimal actualTotal = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrderId)
				.create()
				.list()
				.stream()
				.map(I_DD_OrderLine::getQtyEntered)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		assertThat(actualTotal).as("Total QtyEntered of the DD_OrderLines of " + ddOrderIdentifier).isEqualByComparingTo(expectedTotal);
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
}
