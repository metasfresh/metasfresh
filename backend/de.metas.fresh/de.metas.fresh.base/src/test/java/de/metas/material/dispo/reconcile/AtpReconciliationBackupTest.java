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
import de.metas.material.dispo.model.I_MD_ATP_Reconciliation_Backup;
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
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests {@link AtpReconciliationCommand#reconcileAndLog} - the durable backup and run log this reconciliation
 * needs so the pre-change values stay recoverable after the run's process has ended.
 * <p>
 * Wiring mirrors {@link AtpReconciliationCommandTest} exactly, for the same reason documented there: a real
 * {@link CandidateChangeService}, built from real {@link SupplyCandidateHandler}/{@link DemandCandiateHandler},
 * mocking only {@link PostMaterialEventService}.
 */
public class AtpReconciliationBackupTest
{
	private static final Instant D1 = Instant.parse("2026-09-08T00:00:00Z");
	private static final Instant D2 = D1.plus(1, ChronoUnit.DAYS);
	private static final Instant FAR_FUTURE = D1.plus(3650, ChronoUnit.DAYS);

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
	private AtpTargetCalculator atpTargetCalculator;
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

		atpTargetCalculator = AtpTargetCalculator.newInstanceForUnitTesting();
		atpReconciliationCommand = new AtpReconciliationCommand(
				atpTargetCalculator, candidateChangeService, candidateRepository, new AtpReconciliationBackupRepositoryImpl());
	}

	@Test
	public void reconcileAndLog_persistsARecoverableBackupAndRunLogForEveryTouchedCandidate()
	{
		final int stockId = createStockRecord(new BigDecimal("100"));
		atpReconciliationCommand.reconcile(KEY, D1, false);
		assertThat(retrieveStockQtyUpTo(D1)).isEqualByComparingTo("100");

		// an ordinary later demand, dated after D1: the engine's own propagation creates the STOCK candidate at D2
		final int shipmentScheduleId = createShipmentSchedule(false /* processed */);
		postShipmentDemand(D2, new BigDecimal("30"), shipmentScheduleId);
		assertThat(retrieveStockQtyUpTo(D2)).isEqualByComparingTo("70"); // 100 - 30

		// physical stock drifts again, out of band - exactly the divergence a second reconciliation exists to fix
		updateStockQtyOnHand(stockId, new BigDecimal("150"));

		final AtpReconciliationRunLog runLog = atpReconciliationCommand.reconcileAndLog(KEY, D1, false);

		// the correction: target(D1) = 150 (no candidate dated <= D1 contributes), stored(D1) = 100 -> delta 50
		assertThat(runLog.getDivergence().getDifference()).isEqualByComparingTo("50");
		assertThat(retrieveStockQtyUpTo(FAR_FUTURE)).isEqualByComparingTo("120"); // 70 shifted forward by the +50 delta
		assertThat(runLog.getRunUuid()).isNotNull();

		// Recoverability is proven against the PERSISTED backup, looked up fresh by the run id - never by reading
		// runLog.getEntries() itself, which would still pass even if nothing had actually been written to the store.
		final List<I_MD_ATP_Reconciliation_Backup> backedUpRows = retrieveBackupRows(runLog.getRunUuid());

		final I_MD_ATP_Reconciliation_Backup backedUpD2Candidate = backedUpRows.stream()
				.filter(row -> TimeUtil.asInstant(row.getDateProjected()).equals(D2))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no persisted backup row for the D2 STOCK candidate: " + backedUpRows));
		assertThat(backedUpD2Candidate.getQtyBefore()).isEqualByComparingTo("70");
		assertThat(backedUpD2Candidate.getQtyAfter()).isEqualByComparingTo("120");
		assertThat(backedUpD2Candidate.getM_Warehouse_ID()).isEqualTo(WAREHOUSE_ID.getRepoId());
		assertThat(backedUpD2Candidate.getM_Product_ID()).isEqualTo(PRODUCT_ID.getRepoId());

		// the correction's own new STOCK child at D1: nothing to back up (it did not exist before), but the
		// persisted row still names the run's own effect. Disambiguated by MD_Candidate_ID from the correction
		// candidate's OWN backup row below - both share DateProjected=D1 and a null QtyBefore.
		// Nullness of QtyBefore is checked via InterfaceWrapperHelper.isNull, not row.getQtyBefore() == null: the
		// generated accessor for a nullable Quantity column coalesces a null value to BigDecimal.ZERO (the standard
		// metasfresh model-generator behaviour for numeric columns), so the getter itself cannot tell "never backed
		// up because the candidate is new" apart from "backed up with quantity zero".
		final Candidate stockCandidateAtD1 = retrieveStockCandidateUpTo(D1);
		final I_MD_ATP_Reconciliation_Backup backedUpNewD1Candidate = backedUpRows.stream()
				.filter(row -> TimeUtil.asInstant(row.getDateProjected()).equals(D1)
						&& InterfaceWrapperHelper.isNull(row, I_MD_ATP_Reconciliation_Backup.COLUMNNAME_QtyBefore)
						&& row.getMD_Candidate_ID() == stockCandidateAtD1.getId().getRepoId())
				.findFirst()
				.orElseThrow(() -> new AssertionError("no persisted backup row for the newly created D1 STOCK candidate: " + backedUpRows));
		assertThat(backedUpNewD1Candidate.getQtyAfter()).isEqualByComparingTo("150");

		// the correction candidate itself (INVENTORY_UP, not its STOCK child) also gets a backup row - via its
		// ATP_RECONCILIATION business-case detail, not via backupRepository - naming its own qty, not the running
		// balance. Same (D1, null QtyBefore) signature as the STOCK child above; excluded by candidate id instead.
		final I_MD_ATP_Reconciliation_Backup backedUpCorrectionCandidate = backedUpRows.stream()
				.filter(row -> TimeUtil.asInstant(row.getDateProjected()).equals(D1)
						&& InterfaceWrapperHelper.isNull(row, I_MD_ATP_Reconciliation_Backup.COLUMNNAME_QtyBefore)
						&& row.getMD_Candidate_ID() != stockCandidateAtD1.getId().getRepoId())
				.findFirst()
				.orElseThrow(() -> new AssertionError("no persisted backup row for the correction candidate itself: " + backedUpRows));
		assertThat(backedUpCorrectionCandidate.getQtyAfter()).isEqualByComparingTo("50");
	}

	@Test
	public void reconcileAndLog_writesNothingOnADryRun()
	{
		createStockRecord(new BigDecimal("100"));

		final AtpReconciliationRunLog runLog = atpReconciliationCommand.reconcileAndLog(KEY, D1, true);

		assertThat(runLog.isEmpty()).isTrue();
		assertThat(runLog.getRunUuid()).isNull();
		assertThat(runLog.getDivergence().getExpectedAtp()).isEqualByComparingTo("100");
		assertThat(retrieveAllBackupRows()).isEmpty();
	}

	@Test
	public void reconcileAndLog_writesTheBackupBeforeTheCandidateChange_notAfter()
	{
		final int stockId = createStockRecord(new BigDecimal("100"));
		atpReconciliationCommand.reconcile(KEY, D1, false);
		assertThat(retrieveStockQtyUpTo(D1)).isEqualByComparingTo("100");

		// a fresh divergence for the crashing run to attempt to correct
		updateStockQtyOnHand(stockId, new BigDecimal("150"));

		// really persists the backup (so the test can prove it survives), then throws before the caller can
		// possibly reach writeCorrectionCandidate - if the ordering were reversed (write, then back up), the
		// STOCK candidate below would already show 150 by the time this exception is caught
		final CrashAfterBackupRepository crashingRepository = new CrashAfterBackupRepository();
		final AtpReconciliationCommand commandWithCrashingRepository = new AtpReconciliationCommand(
				atpTargetCalculator, candidateChangeService, candidateRepository, crashingRepository);

		assertThatThrownBy(() -> commandWithCrashingRepository.reconcileAndLog(KEY, D1, false))
				.hasMessageContaining("simulated crash right after the backup was written");

		assertThat(crashingRepository.backupWasCalled).isTrue();
		assertThat(crashingRepository.capturedRunUuid).isNotNull();

		// the candidate chain is UNCHANGED: the crash happened before writeCorrectionCandidate ever ran, proving
		// the backup call precedes the write rather than following it
		assertThat(retrieveStockQtyUpTo(D1)).isEqualByComparingTo("100");

		// yet the backup itself is durably there despite the crash - recoverable even though the write never happened
		final List<I_MD_ATP_Reconciliation_Backup> backedUpRows = retrieveBackupRows(crashingRepository.capturedRunUuid);
		assertThat(backedUpRows).isNotEmpty();
		assertThat(backedUpRows).allSatisfy(row -> assertThat(row.getQtyBefore()).isEqualByComparingTo("100"));
	}

	/** Really persists the backup (delegating to the real implementation), then throws - see the test above. */
	private static final class CrashAfterBackupRepository implements AtpReconciliationBackupRepository
	{
		private final AtpReconciliationBackupRepository delegate = new AtpReconciliationBackupRepositoryImpl();

		private boolean backupWasCalled = false;
		private String capturedRunUuid = null;

		@Override
		public void backupBeforeWrite(
				@NonNull final String runUuid,
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final List<Candidate> stockCandidatesInScope)
		{
			delegate.backupBeforeWrite(runUuid, key, stockCandidatesInScope);
			backupWasCalled = true;
			capturedRunUuid = runUuid;
			throw new RuntimeException("simulated crash right after the backup was written");
		}

		@Override
		public void recordAfterWrite(
				@NonNull final String runUuid,
				@NonNull final StockDataRecordIdentifier key,
				@NonNull final List<AtpReconciliationRunLog.Entry> entries)
		{
			throw new IllegalStateException("must not be reached: reconcileAndLog should have already failed after backupBeforeWrite");
		}
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

	/** Simulates a physical stock movement that never went through the material-dispo candidate chain. */
	private void updateStockQtyOnHand(final int stockId, final BigDecimal qtyOnHand)
	{
		final I_MD_Stock record = InterfaceWrapperHelper.load(stockId, I_MD_Stock.class);
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

	/** Posts a {@code DEMAND} candidate through the real engine, so its {@code STOCK} propagation is the real one. */
	private void postShipmentDemand(
			final Instant date,
			final BigDecimal qty,
			final int shipmentScheduleId)
	{
		final Candidate candidate = Candidate.builder()
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

		candidateChangeService.onCandidateNewOrChange(candidate);
	}

	/** @return the {@code Qty} of the key's youngest general {@code STOCK} candidate dated at or before {@code date}. */
	private BigDecimal retrieveStockQtyUpTo(final Instant date)
	{
		final Candidate stockCandidate = retrieveStockCandidateUpTo(date);
		return stockCandidate != null ? stockCandidate.getQuantity() : BigDecimal.ZERO;
	}

	@Nullable
	private Candidate retrieveStockCandidateUpTo(final Instant date)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(WAREHOUSE_ID)
				.productId(PRODUCT_ID.getRepoId())
				.storageAttributesKey(AttributesKey.NONE)
				.customer(BPartnerClassifier.none())
				.timeRangeEnd(DateAndSeqNo.atTimeNoSeqNo(date).withOperator(DateAndSeqNo.Operator.INCLUSIVE))
				.build();

		return candidateRepository.retrieveLatestMatchOrNull(
				CandidatesQuery.builder()
						.materialDescriptorQuery(materialDescriptorQuery)
						.matchExactStorageAttributesKey(true)
						.type(CandidateType.STOCK)
						.build());
	}

	private static List<I_MD_ATP_Reconciliation_Backup> retrieveBackupRows(@NonNull final String runUuid)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_ATP_Reconciliation_Backup.class)
				.addEqualsFilter(I_MD_ATP_Reconciliation_Backup.COLUMNNAME_ReconciliationRunUUID, runUuid)
				.create()
				.list();
	}

	private static List<I_MD_ATP_Reconciliation_Backup> retrieveAllBackupRows()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_ATP_Reconciliation_Backup.class)
				.create()
				.list();
	}
}
