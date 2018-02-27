package de.metas.material.dispo.service.candidatechange;

import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class CandidateChangeHandlerTests
{
	private static final BigDecimal THREE = new BigDecimal("3");

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Timestamp t1 = TimeUtil.parseTimestamp("2017-11-22 00:00");
	private final Timestamp t2 = TimeUtil.addMinutes(t1, 10);
	private final Timestamp t3 = TimeUtil.addMinutes(t1, 20);
	private final Timestamp t4 = TimeUtil.addMinutes(t1, 30);

	private final int OTHER_WAREHOUSE_ID = WAREHOUSE_ID + 10;

	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private AvailableToPromiseRepository stockRepository;

	private CandidateChangeService candidateChangeHandler;

	@Mocked
	private PostMaterialEventService postMaterialEventService;

	private StockCandidateService stockCandidateService;

	private CandidateRepositoryWriteService candidateRepositoryCommands;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		candidateRepositoryCommands = new CandidateRepositoryWriteService();

		stockRepository = new AvailableToPromiseRepository();
		stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		candidateChangeHandler = new CandidateChangeService(
				ImmutableList.of(
						new DemandCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, postMaterialEventService, stockRepository, stockCandidateService),
						new SupplyCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, stockCandidateService)));
	}

	@Test
	public void createMapOfHandlers()
	{
		final CandidateHandler handler1 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.SUPPLY));
		final CandidateHandler handler2 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.STOCK_UP, CandidateType.UNRELATED_DECREASE));

		final Map<CandidateType, CandidateHandler> result = CandidateChangeService.createMapOfHandlers(ImmutableList.of(handler1, handler2));
		assertThat(result).hasSize(4);
		assertThat(result.get(CandidateType.DEMAND)).isSameAs(handler1);
		assertThat(result.get(CandidateType.SUPPLY)).isSameAs(handler1);
		assertThat(result.get(CandidateType.STOCK_UP)).isSameAs(handler2);
		assertThat(result.get(CandidateType.UNRELATED_DECREASE)).isSameAs(handler2);
	}

	@Test(expected = RuntimeException.class)
	public void createMapOfHandlers_when_typeColission_then_exception()
	{
		final CandidateHandler handler1 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.SUPPLY));
		final CandidateHandler handler2 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.UNRELATED_DECREASE));

		CandidateChangeService.createMapOfHandlers(ImmutableList.of(handler1, handler2));
	}

	private CandidateHandler createHandlerThatSupportsTypes(final ImmutableList<CandidateType> types)
	{
		return new CandidateHandler()
		{
			@Override
			public Candidate onCandidateNewOrChange(Candidate candidate)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public Collection<CandidateType> getHandeledTypes()
			{
				return types;
			}
		};
	}

	/**
	 * Verifies that {@link CandidateChangeService#applyDeltaToLaterStockCandidates(CandidatesQuery, BigDecimal)} applies the given delta to the right records.
	 * Only records that have a <i>different</i> M_Warenhouse_ID shall not be touched.
	 */
	@Test
	public void applyDeltaToMatchingLaterStockCandidates()
	{
		final Candidate earlierCandidate;
		final Candidate evenLaterCandidate;
		final Candidate evenLaterCandidateWithDifferentWarehouse;

		// preparations
		{
			final MaterialDescriptor materialDescriptor = createAndAddStock(t2);

			final MaterialDescriptor earlierMaterialDescriptor = materialDescriptor.withDate(t1);

			earlierCandidate = candidateRepositoryCommands
					.addOrUpdateOverwriteStoredSeqNo(Candidate.builder()
							.type(CandidateType.STOCK)
							.clientId(CLIENT_ID)
							.orgId(ORG_ID)
							.materialDescriptor(earlierMaterialDescriptor)
							.build());

			final MaterialDescriptor laterMaterialDescriptor = materialDescriptor.withDate(t3);

			final Candidate laterCandidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.materialDescriptor(laterMaterialDescriptor)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(laterCandidate);

			final MaterialDescriptor evenLatermaterialDescriptor = materialDescriptor
					.withQuantity(new BigDecimal("12"))
					.withDate(t4);

			evenLaterCandidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.materialDescriptor(evenLatermaterialDescriptor)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(evenLaterCandidate);

			final MaterialDescriptor evenLatermaterialDescrWithDifferentWarehouse = evenLatermaterialDescriptor
					.withWarehouseId(OTHER_WAREHOUSE_ID);

			evenLaterCandidateWithDifferentWarehouse = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.materialDescriptor(evenLatermaterialDescrWithDifferentWarehouse)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(evenLaterCandidateWithDifferentWarehouse);
		}

		// do the test
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.date(t2)
				.quantity(THREE)
				.build();
		final Candidate candidateWithDelta = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.groupId(earlierCandidate.getGroupId()).build();
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(candidateWithDelta);

		// assert that every stock record got some groupId
		assertThat(DispoTestUtils.retrieveAllRecords()).allSatisfy(r -> assertThatModel(r).hasValueGreaterThanZero(I_MD_Candidate.COLUMN_MD_Candidate_GroupId));
		{
			final Candidate earlierCandidateAfterChange = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(mkQueryForStockUntilDate(t1, WAREHOUSE_ID));
			assertThat(earlierCandidateAfterChange).as("Expected canddiate with Date=<%s> and warehouseId=<%s> to exist", t1, WAREHOUSE_ID).isNotNull();
			assertThat(earlierCandidateAfterChange.getQuantity()).isEqualTo(earlierCandidate.getQuantity()); // quantity shall be unchanged
			assertThat(earlierCandidateAfterChange.getGroupId()).isEqualTo(earlierCandidate.getGroupId()); // basically the same candidate

			final I_MD_Candidate candidateRecordAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t2).get(0); // candidateRepository.retrieveExact(candidate).get();
			assertThat(candidateRecordAfterChange.getQty()).isEqualByComparingTo("10"); // quantity shall be unchanged, because that method shall only update *later* records
			assertThat(candidateRecordAfterChange.getMD_Candidate_GroupId()).isNotEqualTo(earlierCandidate.getGroupId());
		}
		{
			final Candidate laterCandidateAfterChange = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(mkQueryForStockUntilDate(t3, WAREHOUSE_ID));
			assertThat(laterCandidateAfterChange).isNotNull();
			assertThat(laterCandidateAfterChange.getQuantity()).isEqualByComparingTo("13"); // quantity shall be plus 3
			assertThat(laterCandidateAfterChange.getGroupId()).isEqualTo(earlierCandidate.getGroupId());
		}
		{
			final I_MD_Candidate evenLaterCandidateRecordAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t4, PRODUCT_ID, WAREHOUSE_ID).get(0); // candidateRepository.retrieveExact(evenLaterCandidate).get();
			assertThat(evenLaterCandidateRecordAfterChange.getQty()).isEqualByComparingTo("15"); // quantity shall be plus 3 too
			assertThat(evenLaterCandidateRecordAfterChange.getMD_Candidate_GroupId()).isEqualTo(earlierCandidate.getGroupId());
		}
		{
			final I_MD_Candidate evenLaterCandidateWithDifferentWarehouseAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t4, PRODUCT_ID, OTHER_WAREHOUSE_ID).get(0); // candidateRepository.retrieveExact(evenLaterCandidateWithDifferentWarehouse).get();
			assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getQty()).isEqualByComparingTo("12"); // quantity shall be unchanged, because we changed another warehouse and this one should not have been matched
			assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getMD_Candidate_GroupId(), not(is(earlierCandidate.getGroupId())));
		}
	}

	private MaterialDescriptor createAndAddStock(
			@NonNull final Date dateProjected)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(dateProjected)
				.build();

		final I_MD_Candidate stockRecord = newInstance(I_MD_Candidate.class);
		stockRecord.setAD_Org_ID(ORG_ID);
		stockRecord.setMD_Candidate_Type(CandidateType.STOCK.toString());
		stockRecord.setM_Warehouse_ID(materialDescriptor.getWarehouseId());
		stockRecord.setQty(materialDescriptor.getQuantity());
		stockRecord.setDateProjected(new Timestamp(dateProjected.getTime()));
		stockRecord.setM_Product_ID(materialDescriptor.getProductId());
		stockRecord.setStorageAttributesKey(materialDescriptor.getStorageAttributesKey().getAsString());
		stockRecord.setM_AttributeSetInstance_ID(materialDescriptor.getAttributeSetInstanceId());
		save(stockRecord);

		stockRecord.setMD_Candidate_GroupId(stockRecord.getMD_Candidate_ID());
		save(stockRecord);

		return materialDescriptor;
	}

	private CandidatesQuery mkQueryForStockUntilDate(@NonNull final Date timestamp, final int warehouseId)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.warehouseId(warehouseId)
				.date(timestamp)
				.dateOperator(DateOperator.BEFORE_OR_AT)
				.build();
		return CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptorQuery(materialDescriptorQuery)
				.parentId(CandidatesQuery.UNSPECIFIED_PARENT_ID)
				.build();
	}

	/**
	 * Similar to testOnDemandCandidateCandidateNewOrChange_noOlderRecords, but then adds an accompanying demand and verifies the SeqNo values
	 */
	@Test
	public void onCandidateNewOrChange_demand_then_unrelated_supply()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddDemandWithQtyandDemandDetail(qty, 20);
		// we don't really check here..this first part is already verified in testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one demand, one stock
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("-23");

		createAndAddSupplyWithQtyandDemandDetail(qty, 30);
		{
			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			// we need one demand, one supply and *two* different stocks, since demand and supply are not related
			assertThat(records).hasSize(4);

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
			final I_MD_Candidate firstStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			final I_MD_Candidate secondStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(1);

			assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo() + 1);

			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, secondStockRecord.getSeqNo() + 1);  // as before

			// shall be balanced between the demand and the supply
			assertThatModel(secondStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, firstStockRecord.getDateProjected());
			assertThat(firstStockRecord.getQty()).isEqualByComparingTo("-23");
			assertThat(secondStockRecord.getQty()).isEqualByComparingTo("0");
		}
	}

	@Test
	public void onCandidateNewOrChange_demand_then_related_supply()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddDemandWithQtyandDemandDetail(qty, 20);
		// we don't really check here..this first part is already verified in testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one demand, one stock
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("-23");

		createAndAddSupplyWithQtyandDemandDetail(qty, 20);
		{
			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			// we need one demand, one supply and *two* different stocks, since demand and supply are not related
			assertThat(records).hasSize(3);

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
			final I_MD_Candidate firstStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, demandRecord.getMD_Candidate_ID());
			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, firstStockRecord.getMD_Candidate_ID());

			assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo() + 1);
			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, firstStockRecord.getSeqNo() + 1);

			// assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, secondStockRecord.getSeqNo() + 1); // as before

			// shall be balanced between the demand and the supply
			// assertThatModel(secondStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, firstStockRecord.getDateProjected());
			// assertThat(secondStockRecord.getQty()).isEqualByComparingTo("23");
			assertThat(firstStockRecord.getQty()).isEqualByComparingTo("0");
		}
	}

	private void createAndAddDemandWithQtyandDemandDetail(
			@NonNull final BigDecimal qty,
			final int shipmentScheduleIdForDemandDetail)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableStock(
				stockRepository,
				materialDescr,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.demandDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(
						shipmentScheduleIdForDemandDetail,
						0,
						0))
				.build();
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	private void createAndAddSupplyWithQtyandDemandDetail(
			@NonNull final BigDecimal qty,
			final int shipmentScheduleIdForDemandDetail)
	{
		final MaterialDescriptor supplyMaterialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(NOW)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(supplyMaterialDescriptor)
				.demandDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(
						shipmentScheduleIdForDemandDetail,
						0,
						0))
				.build();

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
	}

	/**
	 * Similar to {@link #testDemand_Then_UnrelatedSupply()}, but this time, we first add the supply candidate.
	 * Therefore its {@link I_MD_Candidate} records gets to be persisted first. still, the {@code SeqNo} needs to be "stable".
	 */
	@Test
	public void onCandidateNewOrChange_supply_then_unrelated_demand_no_initial_stock()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddSupplyWithQtyandDemandDetail(qty, 20);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("23");

		{
			assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one supply, one stock

			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, stockRecord.getMD_Candidate_ID());
			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, stockRecord.getSeqNo() + 1);
		}

		createAndAddDemandWithQtyandDemandDetail(qty, 30);
		{
			assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(4);

			final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
			assertThat(allStockCandidates).hasSize(2);

			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			final I_MD_Candidate firstStockRecord = allStockCandidates.get(0);
			assertThatModel(supplyRecord.getMD_Candidate_Parent())
					.as("the supply-record is the first stock-record's child")
					.hasSameIdAs(firstStockRecord);

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
			final I_MD_Candidate secondStockRecord = allStockCandidates.get(1);
			assertThatModel(secondStockRecord.getMD_Candidate_Parent())
					.as("the second stock-record is the demand-record's child")
					.hasSameIdAs(demandRecord);

			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, firstStockRecord.getSeqNo() + 1);  // as before
			assertThatModel(secondStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo() + 1);

			// shall both be balanced between the demand and the supply, so that in sum we have zero
			assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, secondStockRecord.getDateProjected());
			assertThat(firstStockRecord.getQty()).isEqualByComparingTo("23");
			assertThat(secondStockRecord.getQty()).isEqualByComparingTo("0");
		}
	}

	@Test
	public void onCandidateNewOrChange_supply_then_unrelated_demand_initial_stock()
	{
		createAndAddStock(BEFORE_NOW); // has qty=10

		final BigDecimal qty = new BigDecimal("23");
		createAndAddSupplyWithQtyandDemandDetail(qty, 20);
		createAndAddDemandWithQtyandDemandDetail(qty, 30);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(5);

		final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
		assertThat(allStockCandidates).hasSize(3);
		final I_MD_Candidate initialStockRecord = allStockCandidates.get(0);
		final I_MD_Candidate firstStockRecord = allStockCandidates.get(1);
		final I_MD_Candidate secondStockRecord = allStockCandidates.get(2);

		// shall be unchanged
		assertThat(initialStockRecord.getQty()).isEqualByComparingTo("10");

		// 10 + 23 from the createAndAddSupplyWithQtyandDemandDetail above
		assertThat(firstStockRecord.getQty()).isEqualByComparingTo("33");

		// 33 - 23 from the createAndAddDemandWithQtyandDemandDetail
		assertThat(secondStockRecord.getQty()).isEqualByComparingTo("10");
	}

	@Test
	public void onCandidateNewOrChange_demand_then_unrelated_demand_no_initial_stock()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddDemandWithQtyandDemandDetail(qty, 20);
		createAndAddDemandWithQtyandDemandDetail(qty, 30);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(4);

		final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
		assertThat(allStockCandidates).hasSize(2);

		final I_MD_Candidate firstStockRecord = allStockCandidates.get(0);
		final I_MD_Candidate secondStockRecord = allStockCandidates.get(1);

		final List<I_MD_Candidate> allDemandRecords = DispoTestUtils.filter(CandidateType.DEMAND);
		assertThat(allDemandRecords).hasSize(2);

		assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, secondStockRecord.getDateProjected());
		assertThat(firstStockRecord.getQty()).isEqualByComparingTo("-23");
		assertThat(secondStockRecord.getQty()).isEqualByComparingTo("-46"); // = - 23 - 23
	}

	@Test
	public void onCandidateNewOrChange_demand_then_unrelated_demand_double_initial_stock()
	{
		createAndAddStock(BEFORE_NOW); // has qty=10
		createAndAddStock(BEFORE_NOW); // has qty=10

		final BigDecimal qty = new BigDecimal("12");
		createAndAddDemandWithQtyandDemandDetail(qty, 20);
		createAndAddDemandWithQtyandDemandDetail(qty, 30);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(6);

		final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
		assertThat(allStockCandidates).hasSize(4);
		final I_MD_Candidate firstInitialStockRecord = allStockCandidates.get(0);
		final I_MD_Candidate secondInitialStockRecord = allStockCandidates.get(1);
		final I_MD_Candidate firstStockRecord = allStockCandidates.get(2);
		final I_MD_Candidate secondStockRecord = allStockCandidates.get(3);

		final List<I_MD_Candidate> allDemandRecords = DispoTestUtils.filter(CandidateType.DEMAND);
		assertThat(allDemandRecords).hasSize(2);

		// in sum, we now have -46 for this time, product, warehouse and storageAttributesKey
		assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, secondStockRecord.getDateProjected());

		// -> overall stock at BEFORE_NOW is 20
		assertThat(firstInitialStockRecord.getQty()).isEqualByComparingTo("10"); // shall be unchanged
		assertThat(secondInitialStockRecord.getQty()).isEqualByComparingTo("10"); // shall be unchanged

		// -> overall stock at NOW is (20 - 24) = -4 = (8 -12)
		assertThat(firstStockRecord.getQty()).isEqualByComparingTo("8");
		assertThat(secondStockRecord.getQty()).isEqualByComparingTo("-4");
	}
}
