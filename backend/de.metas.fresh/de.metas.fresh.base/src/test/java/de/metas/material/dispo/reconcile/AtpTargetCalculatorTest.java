package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class AtpTargetCalculatorTest
{
	private static final Instant D = Instant.parse("2026-09-08T00:00:00Z");
	private static final Instant BEFORE_D = D.minus(1, ChronoUnit.DAYS);
	private static final Instant AFTER_D = D.plus(1, ChronoUnit.DAYS);

	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000003);
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(1000004);

	/** The key every fixture of this test belongs to. */
	private static final StockDataRecordIdentifier KEY = StockDataRecordIdentifier.builder()
			.clientId(CLIENT_ID)
			.orgId(ORG_ID)
			.warehouseId(WAREHOUSE_ID)
			.productId(PRODUCT_ID)
			.storageAttributesKey(AttributesKey.NONE)
			.build();

	private AtpTargetCalculator atpTargetCalculator;
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		candidateRepositoryWriteService = new CandidateRepositoryWriteService(
				dimensionService,
				stockChangeDetailRepo,
				candidateRepository,
				new CandidateQtyDetailsRepository());

		atpTargetCalculator = AtpTargetCalculator.newInstanceForUnitTesting();
	}

	@Test
	public void noCandidates_targetIsThePhysicalQtyOnHand()
	{
		createStockRecord(new BigDecimal("100"));

		assertThat(atpTargetCalculator.computeTarget(KEY, D)).isEqualByComparingTo("100");
	}

	@Test
	public void openShipmentDemandDatedBeforeD_isSubtracted()
	{
		createStockRecord(new BigDecimal("200"));
		addShipmentDemand(BEFORE_D, new BigDecimal("30"), createShipmentSchedule(false /* processed */));

		assertThat(atpTargetCalculator.computeTarget(KEY, D)).isEqualByComparingTo("170");
	}

	@Test
	public void openShipmentDemandDatedAfterD_isExcluded()
	{
		createStockRecord(new BigDecimal("200"));
		addShipmentDemand(AFTER_D, new BigDecimal("30"), createShipmentSchedule(false /* processed */));

		assertThat(atpTargetCalculator.computeTarget(KEY, D)).isEqualByComparingTo("200");
	}

	@Test
	public void productionDemandOfAClosedPPOrder_isExcluded()
	{
		createStockRecord(new BigDecimal("80"));
		addProductionDemand(BEFORE_D, new BigDecimal("20"), createPPOrder(DocStatus.Closed));

		assertThat(atpTargetCalculator.computeTarget(KEY, D)).isEqualByComparingTo("80");
	}

	@Test
	public void partlyFulfilledShipmentDemand_contributesOnlyItsOpenRemainder()
	{
		createStockRecord(new BigDecimal("200"));
		final CandidateId candidateId = addShipmentDemand(BEFORE_D, new BigDecimal("30"), createShipmentSchedule(false /* processed */));
		setQtyFulfilled(candidateId, new BigDecimal("10"));

		assertThat(atpTargetCalculator.computeTarget(KEY, D)).isEqualByComparingTo("180");
	}

	@Test
	public void driftedKey_divergenceIsExpectedMinusStored()
	{
		createStockRecord(new BigDecimal("200"));
		addShipmentDemand(BEFORE_D, new BigDecimal("30"), createShipmentSchedule(false /* processed */));
		// the stored projection says 500 although the key's target is 200 - 30 = 170
		addStockCandidate(D, new BigDecimal("500"));

		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(KEY, D);

		assertThat(divergence.getExpectedAtp()).isEqualByComparingTo("170");
		assertThat(divergence.getStoredAtp()).isEqualByComparingTo("500");
		assertThat(divergence.getDifference())
				.isEqualByComparingTo(divergence.getExpectedAtp().subtract(divergence.getStoredAtp()))
				.isEqualByComparingTo("-330");
	}

	/**
	 * The stored balance has to come from the key's <b>general</b> chain. A {@code STOCK} candidate that
	 * {@code StockCandidateService} created for a customer-reserved position carries that customer's id and
	 * its {@code Qty} is the balance over that customer's rows plus the null-customer rows only - reading it
	 * as "the stored balance" would compare it against an {@code expectedAtp} that is anchored on
	 * {@code MD_Stock.QtyOnHand}, which has no customer dimension.
	 */
	@Test
	public void youngestStockCandidateIsReservedForACustomer_storedAtpIsTakenFromTheGeneralChain()
	{
		createStockRecord(new BigDecimal("200"));
		addShipmentDemand(BEFORE_D, new BigDecimal("30"), createShipmentSchedule(false /* processed */));
		// the general chain's running balance, and the only STOCK candidate that may be read as "stored": 200 - 30
		addStockCandidate(BEFORE_D, new BigDecimal("170"));
		// ...younger than that, but a *customer's* reserved balance rather than the general one
		addCustomerReservedStockCandidate(D, new BigDecimal("500"));

		final AtpDivergence divergence = atpTargetCalculator.computeDivergence(KEY, D);

		assertThat(divergence.getStoredAtp()).isEqualByComparingTo("170");
		// the sum stays customer-agnostic: 200 - 30
		assertThat(divergence.getExpectedAtp()).isEqualByComparingTo("170");
		assertThat(divergence.getDifference()).isEqualByComparingTo("0");
	}

	private void createStockRecord(final BigDecimal qtyOnHand)
	{
		final I_MD_Stock record = InterfaceWrapperHelper.newInstance(I_MD_Stock.class);
		// AD_Client_ID has no setter on the model interface
		InterfaceWrapperHelper.setValue(record, I_MD_Stock.COLUMNNAME_AD_Client_ID, CLIENT_ID.getRepoId());
		record.setAD_Org_ID(ORG_ID.getRepoId());
		record.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		record.setM_Product_ID(PRODUCT_ID.getRepoId());
		record.setAttributesKey(AttributesKey.NONE.getAsString());
		record.setQtyOnHand(qtyOnHand);
		InterfaceWrapperHelper.save(record);
	}

	private int createShipmentSchedule(final boolean processed)
	{
		final I_M_ShipmentSchedule record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		record.setProcessed(processed);
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);
		return record.getM_ShipmentSchedule_ID();
	}

	private int createPPOrder(final DocStatus docStatus)
	{
		final I_PP_Order record = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		record.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.save(record);
		return record.getPP_Order_ID();
	}

	/**
	 * {@code QtyFulfilled} is not part of {@link Candidate}, so it is set on the persisted record.
	 */
	private void setQtyFulfilled(final CandidateId candidateId, final BigDecimal qtyFulfilled)
	{
		final I_MD_Candidate record = InterfaceWrapperHelper.load(candidateId.getRepoId(), I_MD_Candidate.class);
		record.setQtyFulfilled(qtyFulfilled);
		InterfaceWrapperHelper.save(record);
	}

	private CandidateId addShipmentDemand(final Instant date, final BigDecimal qty, final int shipmentScheduleId)
	{
		return addCandidate(
				CandidateType.DEMAND,
				CandidateBusinessCase.SHIPMENT,
				DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, -1, -1, qty),
				date,
				qty);
	}

	private CandidateId addProductionDemand(final Instant date, final BigDecimal qty, final int ppOrderId)
	{
		final ProductionDetail productionDetail = ProductionDetail.builder()
				.plantId(ResourceId.ofRepoId(1))
				.productBomLineId(1)
				.description("test")
				.ppOrderRef(PPOrderRef.ofPPOrderId(ppOrderId))
				.ppOrderDocStatus(DocStatus.Completed) // deliberately NOT the live status; the service has to read the PP_Order
				.advised(Flag.FALSE)
				.pickDirectlyIfFeasible(Flag.FALSE)
				.qty(qty)
				.build();

		return addCandidate(CandidateType.DEMAND, CandidateBusinessCase.PRODUCTION, productionDetail, date, qty);
	}

	private CandidateId addStockCandidate(final Instant date, final BigDecimal qty)
	{
		return addCandidate(CandidateType.STOCK, null, null, date, qty, null /* customerId */);
	}

	/**
	 * A {@code STOCK} candidate as {@code StockCandidateService} writes it for a position reserved for a
	 * customer: it carries {@code C_BPartner_Customer_ID} and {@code IsReservedForCustomer='Y'}.
	 */
	private CandidateId addCustomerReservedStockCandidate(final Instant date, final BigDecimal qty)
	{
		return addCandidate(CandidateType.STOCK, null, null, date, qty, CUSTOMER_ID);
	}

	private CandidateId addCandidate(
			final CandidateType type,
			@Nullable final CandidateBusinessCase businessCase,
			@Nullable final BusinessCaseDetail businessCaseDetail,
			final Instant date,
			final BigDecimal qty)
	{
		return addCandidate(type, businessCase, businessCaseDetail, date, qty, null /* customerId */);
	}

	private CandidateId addCandidate(
			final CandidateType type,
			@Nullable final CandidateBusinessCase businessCase,
			@Nullable final BusinessCaseDetail businessCaseDetail,
			final Instant date,
			final BigDecimal qty,
			@Nullable final BPartnerId customerId)
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID.getRepoId(), ORG_ID.getRepoId()))
				.type(type)
				.businessCase(businessCase)
				.businessCaseDetail(businessCaseDetail)
				.materialDescriptor(MaterialDescriptor.builder()
						.date(date)
						.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(PRODUCT_ID.getRepoId()))
						.warehouseId(WAREHOUSE_ID)
						.customerId(customerId)
						.reservedForCustomer(customerId != null)
						.quantity(qty)
						.build())
				.build();

		return candidateRepositoryWriteService.add(candidate).getCandidate().getId();
	}
}
