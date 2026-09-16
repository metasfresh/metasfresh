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
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link AtpReconciliationCommand#reconcile}.
 * <p>
 * The wiring below deliberately builds its own {@link CandidateChangeService} (real {@link SupplyCandidateHandler}
 * and {@link DemandCandiateHandler}, mocked only {@link PostMaterialEventService}) instead of resolving it through a
 * {@code newInstanceForUnitTesting()} on {@link AtpReconciliationCommand} itself: {@link DemandCandiateHandler}
 * needs a working {@link PostMaterialEventService}, and outside an active transaction
 * {@code ITrxManager#accumulateAndProcessAfterCommit} runs its callback <b>eagerly</b> (see
 * {@code ITrxManager.java:530-538}) - so a real one would attempt a genuine event-bus publish from inside this
 * test. A main-source factory can't depend on Mockito, so {@link AtpReconciliationCommand} does not get a
 * {@code newInstanceForUnitTesting()} of its own; this is the established pattern this package's engine tests
 * already use for the same reason (e.g. {@code ShipmentScheduleCreatedHandlerTests}).
 */
public class AtpReconciliationCommandTest
{
	private static final Instant D = Instant.parse("2026-09-08T00:00:00Z");
	private static final Instant BEFORE_D = D.minus(1, ChronoUnit.DAYS);

	/** Far enough after every date used in this test to read the chain's current, fully-propagated balance. */
	private static final Instant FAR_FUTURE = D.plus(3650, ChronoUnit.DAYS);

	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000001);
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000003);

	private static final StockDataRecordIdentifier KEY = StockDataRecordIdentifier.builder()
			.clientId(CLIENT_ID)
			.orgId(ORG_ID)
			.warehouseId(WAREHOUSE_ID)
			.productId(PRODUCT_ID)
			.storageAttributesKey(AttributesKey.NONE)
			.build();

	private CandidateRepositoryRetrieval candidateRepository;
	private CandidateChangeService candidateChangeService;
	private AtpReconciliationCommand atpReconciliationCommand;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		candidateRepository = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(
				dimensionService, stockChangeDetailRepo, candidateRepository, new CandidateQtyDetailsRepository());
		final StockCandidateService stockCandidateService = new StockCandidateService(candidateRepository, candidateRepositoryWriteService);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryWriteService, stockCandidateService);
		final DemandCandiateHandler demandCandiateHandler = new DemandCandiateHandler(
				candidateRepository,
				candidateRepositoryWriteService,
				Mockito.mock(PostMaterialEventService.class),
				new AvailableToPromiseRepository(),
				stockCandidateService,
				supplyCandidateHandler);

		candidateChangeService = new CandidateChangeService(ImmutableList.of(supplyCandidateHandler, demandCandiateHandler));

		final AtpTargetCalculator atpTargetCalculator = AtpTargetCalculator.newInstanceForUnitTesting();
		atpReconciliationCommand = new AtpReconciliationCommand(atpTargetCalculator, candidateChangeService, candidateRepository);
	}

	@Test
	public void chainZeroedWithPhysicalStock_reconcileWritesThePhysicalStockAsTheStockCandidate()
	{
		final int stockId = createStockRecord(new BigDecimal("100"));

		final AtpDivergence divergence = atpReconciliationCommand.reconcile(KEY, D, false);

		assertThat(divergence.getExpectedAtp()).isEqualByComparingTo("100");
		assertThat(divergence.getStoredAtp()).isEqualByComparingTo("0");
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");
		// reconciling never moves physical stock or posts a transaction: both are asserted unchanged here
		assertThat(retrieveStockQtyOnHand(stockId)).isEqualByComparingTo("100");
		assertThat(countTransactionRecords()).isZero();
	}

	@Test
	public void reconcilingTwiceInSuccession_leavesTheStoredValueUnchanged()
	{
		createStockRecord(new BigDecimal("100"));

		atpReconciliationCommand.reconcile(KEY, D, false);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");
		final int candidateCountAfterFirstRun = countCandidateRecords();

		final AtpDivergence secondRun = atpReconciliationCommand.reconcile(KEY, D, false);

		assertThat(secondRun.getDifference()).isEqualByComparingTo("0");
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");
		// the second run has nothing to correct (the delta is zero) - it must not write a phantom zero-qty
		// candidate (and its paired STOCK child) on top of the first run's rows
		assertThat(countCandidateRecords()).isEqualTo(candidateCountAfterFirstRun);
	}

	@Test
	public void secondCorrectionWithANonZeroDelta_composesOnTopOfTheFirstInsteadOfReplacingIt()
	{
		final int stockId = createStockRecord(new BigDecimal("100"));

		atpReconciliationCommand.reconcile(KEY, D, false);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");

		// physical stock moves by another +100 without going through the candidate chain at all - exactly the
		// kind of divergence this feature exists to catch (unlike laterDemandDatedBeforeD_..., where the engine's
		// own propagation already keeps the projection correct without any help from this class). The second
		// correction's own delta (100) deliberately comes out equal to the first correction's own quantity: that
		// is precisely the case a natural-key match keyed on "same qty, same reset-pinstance value" cannot tell
		// apart from "the same candidate as before" (see buildCandidate's Javadoc) - a same-key, differently-sized
		// second correction would not exercise that trap.
		updateStockQtyOnHand(stockId, new BigDecimal("200"));

		// a *fresh* AtpReconciliationCommand instance for the second call - sharing no in-memory state with the
		// first (e.g. after an app-server restart, or handled by a second node): correctness must not depend on
		// any in-process continuity between two reconcile() calls for the same key and date. A per-instance
		// counter (as opposed to reading the disambiguator back from the store) would pass this test if the same
		// instance were reused for both calls, without covering the actual failure this finding is about.
		final AtpReconciliationCommand secondInstance = new AtpReconciliationCommand(
				AtpTargetCalculator.newInstanceForUnitTesting(), candidateChangeService, candidateRepository);

		final AtpDivergence secondRun = secondInstance.reconcile(KEY, D, false);

		assertThat(secondRun.getDifference()).isEqualByComparingTo("100");
		// if the second correction natural-key-matched the first one and replaced its qty instead of composing,
		// this would read 100 (the second correction's own qty, same as the first's) instead of 100 + 100
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("200");
	}

	@Test
	public void aDryRun_computesTheDivergenceButWritesNothing()
	{
		createStockRecord(new BigDecimal("100"));

		final AtpDivergence divergence = atpReconciliationCommand.reconcile(KEY, D, true);

		assertThat(divergence.getExpectedAtp()).isEqualByComparingTo("100");
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("0");
		assertThat(countTransactionRecords()).isZero();
	}

	@Test
	public void laterDemandDatedBeforeD_shiftsTheReconciledProjectionByExactlyItsOwnQty()
	{
		createStockRecord(new BigDecimal("100"));
		atpReconciliationCommand.reconcile(KEY, D, false);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");

		final int shipmentScheduleId = createShipmentSchedule(false /* processed */);
		postShipmentDemand(null, BEFORE_D, new BigDecimal("30"), shipmentScheduleId);

		// the reconciled 100 is not discarded - it is adjusted by exactly the new demand's -30
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("70");
	}

	@Test
	public void laterQuantityChangeOfAPreDCandidate_shiftsTheReconciledProjectionByExactlyTheDifference()
	{
		createStockRecord(new BigDecimal("100"));
		final int shipmentScheduleId = createShipmentSchedule(false /* processed */);
		final Candidate demand = postShipmentDemand(null, BEFORE_D, new BigDecimal("20"), shipmentScheduleId);

		atpReconciliationCommand.reconcile(KEY, D, false);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("80"); // 100 - 20

		// bump the same pre-D candidate's qty up: 20 -> 50, a further -30
		postShipmentDemand(demand.getId(), BEFORE_D, new BigDecimal("50"), shipmentScheduleId);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("50"); // 80 - 30

		// ...and back down: 50 -> 10, a +40 relative to the last value
		postShipmentDemand(demand.getId(), BEFORE_D, new BigDecimal("10"), shipmentScheduleId);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("90"); // 50 + 40, i.e. 100 - 10
	}

	@Test
	public void voidingAPreDCandidate_shiftsTheProjectionBackByExactlyItsFormerContribution()
	{
		createStockRecord(new BigDecimal("100"));
		final int shipmentScheduleId = createShipmentSchedule(false /* processed */);
		final Candidate demand = postShipmentDemand(null, BEFORE_D, new BigDecimal("30"), shipmentScheduleId);

		atpReconciliationCommand.reconcile(KEY, D, false);
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("70"); // 100 - 30

		candidateChangeService.onCandidateDelete(demand);

		// the demand's former -30 contribution is gone; the reconciled balance shifts back by exactly that
		assertThat(retrieveCurrentProjectedAtp()).isEqualByComparingTo("100");
	}

	private int createStockRecord(final BigDecimal qtyOnHand)
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
		return record.getMD_Stock_ID();
	}

	private BigDecimal retrieveStockQtyOnHand(final int stockId)
	{
		return InterfaceWrapperHelper.load(stockId, I_MD_Stock.class).getQtyOnHand();
	}

	/** Simulates a physical stock movement that never went through the material-dispo candidate chain. */
	private void updateStockQtyOnHand(final int stockId, final BigDecimal qtyOnHand)
	{
		final I_MD_Stock record = InterfaceWrapperHelper.load(stockId, I_MD_Stock.class);
		record.setQtyOnHand(qtyOnHand);
		InterfaceWrapperHelper.save(record);
	}

	private int countTransactionRecords()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Transaction.class)
				.create()
				.list()
				.size();
	}

	/** @return how many {@code MD_Candidate} rows (of any type) exist for {@link #KEY}'s product and warehouse. */
	private int countCandidateRecords()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, PRODUCT_ID.getRepoId())
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID.getRepoId())
				.create()
				.list()
				.size();
	}

	private int createShipmentSchedule(final boolean processed)
	{
		final I_M_ShipmentSchedule record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		record.setProcessed(processed);
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);
		return record.getM_ShipmentSchedule_ID();
	}

	/**
	 * Posts a {@code DEMAND} candidate through the real {@link CandidateChangeService}, so the resulting
	 * {@code STOCK} propagation is the engine's real one - not a direct write, matching how an ordinary
	 * subsequent material event reaches the chain in production.
	 *
	 * @param id when {@code null}, creates a new candidate; when set, updates the existing candidate with that id
	 * (mirrors how {@code CandidatesQuery.fromCandidate} dispatches - see its {@code candidate.getId()} check)
	 */
	private Candidate postShipmentDemand(
			@Nullable final CandidateId id,
			final Instant date,
			final BigDecimal qty,
			final int shipmentScheduleId)
	{
		final Candidate candidate = Candidate.builder()
				.id(id)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(CLIENT_ID.getRepoId(), ORG_ID.getRepoId()))
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.businessCaseDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, -1, -1, qty))
				.materialDescriptor(MaterialDescriptor.builder()
						.date(date)
						.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(PRODUCT_ID.getRepoId()))
						.warehouseId(WAREHOUSE_ID)
						.quantity(qty)
						.build())
				.build();

		return candidateChangeService.onCandidateNewOrChange(candidate).getCandidate();
	}

	/**
	 * @return the {@code Qty} of the key's youngest general {@code STOCK} candidate, i.e. the chain's current,
	 * fully-propagated running balance - mirrors {@code AtpTargetCalculator#retrieveStoredAtp}, which is private.
	 */
	private BigDecimal retrieveCurrentProjectedAtp()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(WAREHOUSE_ID)
				.productId(PRODUCT_ID.getRepoId())
				.storageAttributesKey(AttributesKey.NONE)
				.customer(BPartnerClassifier.none())
				.timeRangeEnd(DateAndSeqNo.atTimeNoSeqNo(FAR_FUTURE).withOperator(DateAndSeqNo.Operator.INCLUSIVE))
				.build();

		final Candidate stockCandidate = candidateRepository.retrieveLatestMatchOrNull(
				CandidatesQuery.builder()
						.materialDescriptorQuery(materialDescriptorQuery)
						.matchExactStorageAttributesKey(true)
						.type(CandidateType.STOCK)
						.build());

		return stockCandidate != null ? stockCandidate.getQuantity() : BigDecimal.ZERO;
	}
}
