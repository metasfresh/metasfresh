package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.material.planning.event.MaterialPlanningContextHelper;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.material.planning.pporder.PPOrderCandidateRepository;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.impl.WarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.X_AD_OrgInfo.STORECREDITCARDDATA_Speichern;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Voiding a PP_Order posts a {@link PPOrderDeletedEvent} (snapshot taken before the void, with the original BOM-line IDs)
 * and a {@link PPOrderChangedEvent} (qty 0; the BOM lines get recreated with new IDs).
 * Material-dispo usually handles the deleted event first; but if it handles the changed event first, the deleted event must still leave no candidate of the PP_Order behind.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PPOrderDeletedHandlerTest
{
	private static final int PP_ORDER_ID = 30;
	private static final int RAW_PRODUCT_1_ID = 50;
	private static final int RAW_PRODUCT_2_ID = 55;
	private static final int OLD_BOM_LINE_1_ID = 150;
	private static final int OLD_BOM_LINE_2_ID = 180;
	private static final int NEW_BOM_LINE_1_ID = 151;
	private static final int NEW_BOM_LINE_2_ID = 181;

	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(20);

	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private PPOrderCreatedHandler ppOrderCreatedHandler;
	private PPOrderChangedHandler ppOrderChangedHandler;
	private PPOrderDeletedHandler ppOrderDeletedHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(20);
		orgInfo.setStoreCreditCardData(STORECREDITCARDDATA_Speichern);
		saveRecord(orgInfo);

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		SpringContextHolder.registerJUnitBean(dimensionService);
		SpringContextHolder.registerJUnitBean(IWarehouseBL.class, new WarehouseBL());
		// Pre-create the warehouse so WarehouseBL.isIgnoreInMaterialDispo can load it.
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		setValue(warehouse, I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID.getRepoId());
		saveRecord(warehouse);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval, new CandidateQtyDetailsRepository());
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryWriteService);
		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);
		final CandidateChangeService candidateChangeService = new CandidateChangeService(ImmutableList.of(
				supplyCandidateHandler,
				new DemandCandidateHandler(
						candidateRepositoryRetrieval,
						candidateRepositoryWriteService,
						Mockito.mock(PostMaterialEventService.class),
						new AvailableToPromiseRepository(),
						stockCandidateService,
						supplyCandidateHandler,
						Mockito.mock(MaterialPlanningContextHelper.class),
						new PPOrderCandidateDemandMatcher(),
						new PPOrderCandidateRepository())));

		ppOrderCreatedHandler = new PPOrderCreatedHandler(candidateChangeService, new MainDataRequestHandler(), candidateRepositoryRetrieval);
		ppOrderChangedHandler = new PPOrderChangedHandler(candidateRepositoryRetrieval, candidateChangeService);
		ppOrderDeletedHandler = new PPOrderDeletedHandler(candidateChangeService, candidateRepositoryRetrieval);
	}

	@Test
	public void voided_deletedEventOnly_deletesAllCandidates()
	{
		final PPOrder ppOrderBeforeVoid = createPPOrderBeforeVoid();
		createCandidates(ppOrderBeforeVoid);

		ppOrderDeletedHandler.handleEvent(createDeletedEvent(ppOrderBeforeVoid));

		assertNoCandidatesLeft();
	}

	/**
	 * The order seen in failing CI runs (flaky case 47, variant 3): the change event relinks the line candidates to the new BOM-line IDs
	 * before the deleted event arrives with the old BOM-line IDs.
	 */
	@Test
	public void voided_changedEventBeforeDeletedEvent_deletesAllCandidates()
	{
		final PPOrder ppOrderBeforeVoid = createPPOrderBeforeVoid();
		createCandidates(ppOrderBeforeVoid);

		ppOrderChangedHandler.handleEvent(createVoidChangedEvent(ppOrderBeforeVoid));
		assertThat(DispoTestUtils.filterExclStock()).as("precondition: the changed event updates, but does not delete").hasSize(3);
		assertThat(candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(ppOrderBeforeVoid.getPpOrderId()))
				.filteredOn(candidate -> candidate.getType() == CandidateType.DEMAND)
				.extracting(candidate -> PPOrderBOMLineId.toRepoId(ProductionDetail.cast(candidate.getBusinessCaseDetail()).getPpOrderBOMLineId()))
				.as("precondition: the changed event relinked the BOM-line candidates to the recreated BOM lines")
				.containsExactlyInAnyOrder(NEW_BOM_LINE_1_ID, NEW_BOM_LINE_2_ID);

		ppOrderDeletedHandler.handleEvent(createDeletedEvent(ppOrderBeforeVoid));

		assertNoCandidatesLeft();
	}

	private void createCandidates(@NonNull final PPOrder ppOrder)
	{
		final PPOrderCreatedEvent createdEvent = PPOrderCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ppOrder(ppOrder)
				.build();
		ppOrderCreatedHandler.validateEvent(createdEvent);
		ppOrderCreatedHandler.handleEvent(createdEvent);

		assertThat(DispoTestUtils.filter(CandidateType.SUPPLY)).as("precondition: header supply").hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.DEMAND)).as("precondition: one demand per BOM line").hasSize(2);
	}

	private static PPOrderDeletedEvent createDeletedEvent(@NonNull final PPOrder ppOrderBeforeVoid)
	{
		return PPOrderDeletedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ppOrder(ppOrderBeforeVoid)
				.build();
	}

	/**
	 * Mirrors what {@code PP_Order.updateAndPostEventOnQtyEnteredChange} posts during {@code MPPOrder.voidIt()}:
	 * qty 0, and every old BOM line mapped to its recreated line with a new ID.
	 */
	private static PPOrderChangedEvent createVoidChangedEvent(@NonNull final PPOrder ppOrderBeforeVoid)
	{
		final PPOrderId ppOrderId = ppOrderBeforeVoid.getPpOrderId();
		return PPOrderChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_AND_ORG_ID))
				.ppOrderAfterChanges(createPPOrder(ZERO, NEW_BOM_LINE_1_ID, NEW_BOM_LINE_2_ID))
				.oldDocStatus(DocStatus.Completed)
				.newDocStatus(DocStatus.Completed)
				.oldDatePromised(AFTER_NOW)
				.newDatePromised(AFTER_NOW)
				.oldQtyRequired(TEN)
				.newQtyRequired(ZERO)
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(ZERO)
				.ppOrderLineChange(createVoidLineChange(ppOrderId, OLD_BOM_LINE_1_ID, NEW_BOM_LINE_1_ID, RAW_PRODUCT_1_ID))
				.ppOrderLineChange(createVoidLineChange(ppOrderId, OLD_BOM_LINE_2_ID, NEW_BOM_LINE_2_ID, RAW_PRODUCT_2_ID))
				.build();
	}

	private static ChangedPPOrderLineDescriptor createVoidLineChange(
			@NonNull final PPOrderId ppOrderId,
			final int oldBOMLineId,
			final int newBOMLineId,
			final int productId)
	{
		return ChangedPPOrderLineDescriptor.builder()
				.oldPPOrderLineId(PPOrderAndBOMLineId.ofRepoIds(ppOrderId, oldBOMLineId))
				.newPPOrderLineId(PPOrderAndBOMLineId.ofRepoIds(ppOrderId, newBOMLineId))
				.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId))
				.issueOrReceiveDate(NOW)
				.oldQtyRequired(TEN)
				.newQtyRequired(ZERO)
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(ZERO)
				.build();
	}

	private static PPOrder createPPOrderBeforeVoid()
	{
		return createPPOrder(TEN, OLD_BOM_LINE_1_ID, OLD_BOM_LINE_2_ID);
	}

	private static PPOrder createPPOrder(@NonNull final BigDecimal qtyRequired, final int bomLine1Id, final int bomLine2Id)
	{
		return PPOrder.builder()
				.ppOrderId(PPOrderId.ofRepoId(PP_ORDER_ID))
				.docStatus(DocStatus.Completed)
				.ppOrderData(PPOrderData.builder()
						.clientAndOrgId(CLIENT_AND_ORG_ID)
						.datePromised(AFTER_NOW)
						.dateStartSchedule(NOW)
						.productDescriptor(createProductDescriptor())
						.qtyRequired(qtyRequired)
						.qtyDelivered(ZERO)
						.warehouseId(WAREHOUSE_ID)
						.bpartnerId(BPARTNER_ID)
						.plantId(ResourceId.ofRepoId(120))
						.productPlanningId(140)
						.build())
				.line(createBOMLine(bomLine1Id, qtyRequired, RAW_PRODUCT_1_ID, 1020))
				.line(createBOMLine(bomLine2Id, qtyRequired, RAW_PRODUCT_2_ID, 1030))
				.build();
	}

	private static PPOrderLine createBOMLine(final int ppOrderBOMLineId, @NonNull final BigDecimal qtyRequired, final int productId, final int productBOMLineId)
	{
		return PPOrderLine.builder()
				.ppOrderLineId(ppOrderBOMLineId)
				.ppOrderLineData(PPOrderLineData.builder()
						.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId))
						.issueOrReceiveDate(NOW)
						.qtyRequired(qtyRequired)
						.qtyDelivered(ZERO)
						.productBomLineId(productBOMLineId)
						.receipt(false)
						.build())
				.build();
	}

	private static void assertNoCandidatesLeft()
	{
		assertThat(DispoTestUtils.filterExclStock()).as("no SUPPLY/DEMAND candidate of the PP_Order shall remain").isEmpty();
		assertThat(Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMNNAME_PP_Order_ID, PP_ORDER_ID)
				.create()
				.list())
				.as("no production detail of the PP_Order shall remain")
				.isEmpty();
	}
}
