package de.metas.material.dispo.service.candidatechange;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.DateAndSeqNo.Operator;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThatModel;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.compiere.util.TimeUtil.asTimestamp;

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

@ExtendWith(AdempiereTestWatcher.class)
public class CandidateChangeHandlerTests
{
	private static final BigDecimal FIFTEEN = new BigDecimal("15");

	private static final BigDecimal THIRTYFIVE = new BigDecimal("35");

	private static final BigDecimal SEVENTEEN = new BigDecimal("17");

	private static final BigDecimal THIRTEEN = new BigDecimal("13");

	private static final BigDecimal THREE = new BigDecimal("3");

	private final Instant t1 = Instant.parse("2017-11-22T00:00:00Z");
	private final Instant t2 = t1.plus(10, ChronoUnit.MINUTES);
	private final Instant t3 = t1.plus(20, ChronoUnit.MINUTES);
	private final Instant t4 = t1.plus(30, ChronoUnit.MINUTES);

	private final WarehouseId OTHER_WAREHOUSE_ID = WarehouseId.ofRepoId(WAREHOUSE_ID.getRepoId() + 10);

	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private AvailableToPromiseRepository stockRepository;
	private CandidateChangeService candidateChangeHandler;
	private StockCandidateService stockCandidateService;
	private CandidateRepositoryWriteService candidateRepositoryCommands;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		candidateRepositoryCommands = new CandidateRepositoryWriteService();

		final PostMaterialEventService postMaterialEventService = Mockito.mock(PostMaterialEventService.class);

		stockRepository = Mockito.spy(AvailableToPromiseRepository.class);
		stockCandidateService = new StockCandidateService(
				candidateRepositoryRetrieval,
				candidateRepositoryCommands);

		final SupplyCandidateHandler supplyCandidateHandler = new SupplyCandidateHandler(candidateRepositoryCommands, stockCandidateService);
		candidateChangeHandler = new CandidateChangeService(
				ImmutableList.of(
						new DemandCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, postMaterialEventService, stockRepository, stockCandidateService, supplyCandidateHandler),
						supplyCandidateHandler));
	}

	@Test
	public void createMapOfHandlers()
	{
		final CandidateHandler handler1 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.SUPPLY));
		final CandidateHandler handler2 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.STOCK_UP, CandidateType.UNEXPECTED_DECREASE));

		final Map<CandidateType, CandidateHandler> result = CandidateChangeService.createMapOfHandlers(ImmutableList.of(handler1, handler2));
		assertThat(result).hasSize(4);
		assertThat(result.get(CandidateType.DEMAND)).isSameAs(handler1);
		assertThat(result.get(CandidateType.SUPPLY)).isSameAs(handler1);
		assertThat(result.get(CandidateType.STOCK_UP)).isSameAs(handler2);
		assertThat(result.get(CandidateType.UNEXPECTED_DECREASE)).isSameAs(handler2);
	}

	@Test
	public void createMapOfHandlers_when_typeColission_then_exception()
	{
		final CandidateHandler handler1 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.SUPPLY));
		final CandidateHandler handler2 = createHandlerThatSupportsTypes(ImmutableList.of(CandidateType.DEMAND, CandidateType.UNEXPECTED_DECREASE));

		assertThatThrownBy(() -> CandidateChangeService.createMapOfHandlers(ImmutableList.of(handler1, handler2)))
				.isInstanceOf(RuntimeException.class);
	}

	private CandidateHandler createHandlerThatSupportsTypes(final ImmutableList<CandidateType> types)
	{
		return new CandidateHandler()
		{

			@Override
			public Collection<CandidateType> getHandeledTypes()
			{
				return types;
			}

			@Override
			public Candidate onCandidateNewOrChange(Candidate candidate, OnNewOrChangeAdvise advise)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public void onCandidateDelete(Candidate candidate)
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Verifies that {@link StockCandidateService#applyDeltaToLaterStockCandidates(SaveResult)} applies the given delta to the right records.
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
							.clientAndOrgId(CLIENT_AND_ORG_ID)
							.materialDescriptor(earlierMaterialDescriptor)
							.build())
					.getCandidate();

			final MaterialDescriptor laterMaterialDescriptor = materialDescriptor.withDate(t3);

			final Candidate laterCandidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientAndOrgId(CLIENT_AND_ORG_ID)
					.materialDescriptor(laterMaterialDescriptor)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(laterCandidate);

			final MaterialDescriptor evenLatermaterialDescriptor = materialDescriptor
					.withQuantity(new BigDecimal("12"))
					.withDate(t4);

			evenLaterCandidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientAndOrgId(CLIENT_AND_ORG_ID)
					.materialDescriptor(evenLatermaterialDescriptor)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(evenLaterCandidate);

			final MaterialDescriptor evenLatermaterialDescrWithDifferentWarehouse = evenLatermaterialDescriptor
					.withWarehouseId(OTHER_WAREHOUSE_ID);

			evenLaterCandidateWithDifferentWarehouse = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientAndOrgId(CLIENT_AND_ORG_ID)
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
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescriptor)
				.groupId(earlierCandidate.getGroupId()).build();
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(SaveResult.builder().candidate(candidateWithDelta).build());

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
			assertThat(evenLaterCandidateRecordAfterChange.getMD_Candidate_GroupId()).isEqualTo(earlierCandidate.getGroupId().toInt());
		}
		{
			final I_MD_Candidate evenLaterCandidateWithDifferentWarehouseAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t4, PRODUCT_ID, OTHER_WAREHOUSE_ID).get(0); // candidateRepository.retrieveExact(evenLaterCandidateWithDifferentWarehouse).get();
			assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getQty()).isEqualByComparingTo("12"); // quantity shall be unchanged, because we changed another warehouse and this one should not have been matched
			assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getMD_Candidate_GroupId()).isNotEqualTo(earlierCandidate.getGroupId());
		}
	}

	private MaterialDescriptor createAndAddStock(
			@NonNull final Instant dateProjected)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(dateProjected)
				.build();

		final I_MD_Candidate stockRecord = newInstance(I_MD_Candidate.class);
		stockRecord.setAD_Org_ID(ORG_ID.getRepoId());
		stockRecord.setMD_Candidate_Type(CandidateType.STOCK.toString());
		stockRecord.setM_Warehouse_ID(materialDescriptor.getWarehouseId().getRepoId());
		stockRecord.setQty(materialDescriptor.getQuantity());
		stockRecord.setDateProjected(asTimestamp(dateProjected));
		stockRecord.setM_Product_ID(materialDescriptor.getProductId());
		stockRecord.setStorageAttributesKey(materialDescriptor.getStorageAttributesKey().getAsString());
		stockRecord.setM_AttributeSetInstance_ID(materialDescriptor.getAttributeSetInstanceId());
		save(stockRecord);

		stockRecord.setMD_Candidate_GroupId(stockRecord.getMD_Candidate_ID());
		save(stockRecord);

		return materialDescriptor;
	}

	private CandidatesQuery mkQueryForStockUntilDate(
			@NonNull final Instant date,
			final WarehouseId warehouseId)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.warehouseId(warehouseId)
				.timeRangeEnd(DateAndSeqNo.builder().date(date).operator(Operator.INCLUSIVE).build())
				.build();
		return CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptorQuery(materialDescriptorQuery)
				.parentId(CandidateId.UNSPECIFIED)
				.build();
	}

	/**
	 * Similar to testOnDemandCandidateCandidateNewOrChange_noOlderRecords, but then adds an accompanying demand and verifies the SeqNo values
	 */
	@Test
	public void onCandidateNewOrChange_demand_then_unrelated_supply()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddDemandWithQtyAndDemandDetail(qty, 20);
		// we don't really check here..this first part is already verified in testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one demand, one stock
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("-23");

		createAndAddSupplyWithQtyAndDemandDetail(qty, 30);
		{
			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			// we need one demand, one supply and *two* different stocks, since demand and supply are not related
			assertThat(records).hasSize(4);

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
			final I_MD_Candidate firstStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);

			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			final I_MD_Candidate secondStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(1);

			assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo());

			// note that now, the stock record shall have the same SeqNo as it's "actual" record
			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, secondStockRecord.getSeqNo());  // as before

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

		createAndAddDemandWithQtyAndDemandDetail(qty, 20);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one demand, one stock
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("-23");

		createAndAddSupplyWithQtyAndDemandDetail(qty, 20);

		// verify
		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records).hasSize(4);

		final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
		final I_MD_Candidate firstStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
		final I_MD_Candidate secondStockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(1);

		assertThatModel(firstStockRecord).as("firstStockRecord is the child of demandRecord")
				.hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, demandRecord.getMD_Candidate_ID());
		assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo());
		assertThat(firstStockRecord.getQty()).isEqualByComparingTo("-23");

		assertThatModel(supplyRecord).as("supplyRecord is the child of secondStockRecord")
				.hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, secondStockRecord.getMD_Candidate_ID());

		// note that now, the stock record shall have the same SeqNo as it's "actual" record
		assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, secondStockRecord.getSeqNo());
		assertThat(secondStockRecord.getQty()).isEqualByComparingTo("0");
	}

	/**
	 * Similar to {@link #testDemand_Then_UnrelatedSupply()}, but this time, we first add the supply candidate.
	 * Therefore its {@link I_MD_Candidate} records gets to be persisted first. still, the {@code SeqNo} needs to be "stable".
	 */
	@Test
	public void onCandidateNewOrChange_supply_then_unrelated_demand_no_initial_stock()
	{
		final BigDecimal qty = new BigDecimal("23");

		createAndAddSupplyWithQtyAndDemandDetail(qty, 20);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK)).hasSize(1);
		assertThat(DispoTestUtils.filter(CandidateType.STOCK).get(0).getQty()).isEqualByComparingTo("23");

		{
			assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one supply, one stock

			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, stockRecord.getMD_Candidate_ID());

			// note that now, the stock record shall have the same SeqNo as it's "actual" record
			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, stockRecord.getSeqNo());
		}

		createAndAddDemandWithQtyAndDemandDetail(qty, 30);
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

			assertThatModel(supplyRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, firstStockRecord.getSeqNo());  // as before
			assertThatModel(secondStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_SeqNo, demandRecord.getSeqNo());

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
		createAndAddSupplyWithQtyAndDemandDetail(qty, 20);
		createAndAddDemandWithQtyAndDemandDetail(qty, 30);

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

		createAndAddDemandWithQtyAndDemandDetail(qty, 20);
		createAndAddDemandWithQtyAndDemandDetail(qty, 30);

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
		// note: those two account for an overall stock of 10; they are not summed!
		// those two stock records might be the result of some unrelated increase of zero or something similarly unplausible!

		final BigDecimal qty = new BigDecimal("12");
		createAndAddDemandWithQtyAndDemandDetail(qty, 20);
		createAndAddDemandWithQtyAndDemandDetail(qty, 30);

		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(6);

		final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.filter(CandidateType.STOCK);
		assertThat(allStockCandidates).hasSize(4);
		final I_MD_Candidate firstInitialStockRecord = allStockCandidates.get(0);
		final I_MD_Candidate secondInitialStockRecord = allStockCandidates.get(1);
		final I_MD_Candidate firstStockRecord = allStockCandidates.get(2);
		final I_MD_Candidate secondStockRecord = allStockCandidates.get(3);

		final List<I_MD_Candidate> allDemandRecords = DispoTestUtils.filter(CandidateType.DEMAND);
		assertThat(allDemandRecords).hasSize(2);

		assertThatModel(firstStockRecord).hasNonNullValue(I_MD_Candidate.COLUMN_DateProjected, secondStockRecord.getDateProjected());

		// -> overall stock at BEFORE_NOW is 10 !
		assertThat(firstInitialStockRecord.getQty()).isEqualByComparingTo("10"); // shall be unchanged
		assertThat(secondInitialStockRecord.getQty()).isEqualByComparingTo("10"); // shall be unchanged

		assertThat(firstStockRecord.getQty()).isEqualByComparingTo("-2"); // 10 - 12
		assertThat(secondStockRecord.getQty()).isEqualByComparingTo("-14"); // -2 - 12
	}

	/**
	 * verifies that "moving" a candidate to an earlier time works
	 */
	@Test
	public void onCandidateNewOrChange_demand_then_demand_then_supply_then_supplyTrx_after_1st_demand()
	{
		createAndAddDemandWithQtyAndDemandDetail(THIRTEEN, BEFORE_BEFORE_NOW, 20);
		createAndAddDemandWithQtyAndDemandDetail(SEVENTEEN, NOW, 30);
		final Candidate supplyCandidate = createAndAddSupplyWithQtyAndDemandDetail(THIRTYFIVE, AFTER_NOW, 40);

		{ // guards prior to the actual test
			final List<I_MD_Candidate> allSupplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
			assertThat(allSupplyCandidates).hasSize(1);
			assertThat(allSupplyCandidates.get(0).getMD_Candidate_ID()).isEqualTo(supplyCandidate.getId().getRepoId());
			assertThat(allSupplyCandidates.get(0).getQty()).isEqualByComparingTo("35");

			final List<I_MD_Candidate> allDemandCandidates = DispoTestUtils.filter(CandidateType.DEMAND);
			assertThat(allDemandCandidates).hasSize(2);
			assertThat(allDemandCandidates.get(0).getQty()).isEqualByComparingTo("13");
			assertThat(allDemandCandidates.get(1).getQty()).isEqualByComparingTo("17");

			final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(allStockCandidates).hasSize(3);
			assertThat(allStockCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_BEFORE_NOW));
			assertThat(allStockCandidates.get(0).getQty()).isEqualByComparingTo("-13");
			assertThat(allStockCandidates.get(1).getDateProjected()).isEqualTo(asTimestamp(NOW));
			assertThat(allStockCandidates.get(1).getQty()).isEqualByComparingTo("-30"); // -13-17
			assertThat(allStockCandidates.get(2).getDateProjected()).isEqualTo(asTimestamp(AFTER_NOW));
			assertThat(allStockCandidates.get(2).getQty()).isEqualByComparingTo("5");// -13-17+35
		}

		// this is more or less what our transaction-event-handlers do: load an existing candidate, update it and store it.
		// change the time of the supply candidate with AFTER_NOW to BEFORE_NOW
		final Candidate candidate = supplyCandidate.toBuilder()
				.materialDescriptor(supplyCandidate.getMaterialDescriptor().withDate(BEFORE_NOW))
				.transactionDetail(TransactionDetail.builder()
						.transactionId(50)
						.quantity(FIFTEEN) // sidenote: this is not the candidate's Qty..it just contributes to the candidate's *fullFilledQty*
						.transactionDate(BEFORE_NOW)
						.complete(true)
						.build())
				.build();

		candidateChangeHandler.onCandidateNewOrChange(candidate);
		{
			// validate the changed *SUPPLY* candidate
			final List<I_MD_Candidate> allSupplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
			assertThat(allSupplyCandidates).hasSize(1);
			assertThat(allSupplyCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_NOW)); // was moved to the date of the (earliest) transaction
			assertThat(allSupplyCandidates.get(0).getQty()).isEqualByComparingTo("35");

			// validate the changed *STOCK* candidate
			final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(allStockCandidates).hasSize(3);
			assertThat(allStockCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_BEFORE_NOW));
			assertThat(allStockCandidates.get(0).getQty()).isEqualByComparingTo("-13"); // -13
			assertThat(allStockCandidates.get(1).getDateProjected()).isEqualTo(asTimestamp(BEFORE_NOW));
			assertThat(allStockCandidates.get(1).getQty()).isEqualByComparingTo("22");  // -13+35
			assertThat(allStockCandidates.get(2).getDateProjected()).isEqualTo(asTimestamp(NOW));
			assertThat(allStockCandidates.get(2).getQty()).isEqualByComparingTo("5"); // -13+35-17
		}
	}

	@Test
	public void onCandidateNewOrChange_supply_then_supply_then_demand_then_demandTrx_after_1st_supply()
	{
		createAndAddSupplyWithQtyAndDemandDetail(THIRTEEN, BEFORE_BEFORE_NOW, 20);
		createAndAddSupplyWithQtyAndDemandDetail(SEVENTEEN, NOW, 30);
		final Candidate supplyCandidate = createAndAddDemandWithQtyAndDemandDetail(THIRTYFIVE, AFTER_NOW, 40);

		{ // guards prior to the actual test
			final List<I_MD_Candidate> allDemandCandidates = DispoTestUtils.filter(CandidateType.DEMAND);
			assertThat(allDemandCandidates).hasSize(1);
			assertThat(allDemandCandidates.get(0).getMD_Candidate_ID()).isEqualTo(supplyCandidate.getId().getRepoId());
			assertThat(allDemandCandidates.get(0).getQty()).isEqualByComparingTo("35");

			final List<I_MD_Candidate> allSupplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
			assertThat(allSupplyCandidates).hasSize(2);
			assertThat(allSupplyCandidates.get(0).getQty()).isEqualByComparingTo("13");
			assertThat(allSupplyCandidates.get(1).getQty()).isEqualByComparingTo("17");

			final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(allStockCandidates).hasSize(3);
			assertThat(allStockCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_BEFORE_NOW));
			assertThat(allStockCandidates.get(0).getQty()).isEqualByComparingTo("13");
			assertThat(allStockCandidates.get(1).getDateProjected()).isEqualTo(asTimestamp(NOW));
			assertThat(allStockCandidates.get(1).getQty()).isEqualByComparingTo("30"); // 13+17
			assertThat(allStockCandidates.get(2).getDateProjected()).isEqualTo(asTimestamp(AFTER_NOW));
			assertThat(allStockCandidates.get(2).getQty()).isEqualByComparingTo("-5");// 13+17-35
		}

		// this is more or less what our transaction-event-handlers do: load an existing candidate, update it and store it.
		// change the time of the supply candidate with AFTER_NOW to BEFORE_NOW
		final Candidate candidate = supplyCandidate.toBuilder()
				.materialDescriptor(supplyCandidate.getMaterialDescriptor().withDate(BEFORE_NOW))
				.transactionDetail(TransactionDetail.builder()
						.transactionId(50)
						.quantity(FIFTEEN) // sidenote: this is not the candidate's Qty..it just contributes to the candidate's *fullFilledQty*
						.transactionDate(BEFORE_NOW)
						.complete(true)
						.build())
				.build();

		candidateChangeHandler.onCandidateNewOrChange(candidate);
		{
			// validate the changed *SUPPLY* candidate
			// final List<I_MD_Candidate> allSupplyCandidates = DispoTestUtils.filter(CandidateType.SUPPLY);
			final List<I_MD_Candidate> allDemandCandidates = DispoTestUtils.filter(CandidateType.DEMAND);
			assertThat(allDemandCandidates).hasSize(1);
			assertThat(allDemandCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_NOW)); // was moved to the date of the (earliest) transaction
			assertThat(allDemandCandidates.get(0).getQty()).isEqualByComparingTo("35");

			// validate the changed *STOCK* candidate
			final List<I_MD_Candidate> allStockCandidates = DispoTestUtils.sortByDateProjected(DispoTestUtils.filter(CandidateType.STOCK));
			assertThat(allStockCandidates).hasSize(3);
			assertThat(allStockCandidates.get(0).getDateProjected()).isEqualTo(asTimestamp(BEFORE_BEFORE_NOW));
			assertThat(allStockCandidates.get(0).getQty()).isEqualByComparingTo("13"); // 13
			assertThat(allStockCandidates.get(1).getDateProjected()).isEqualTo(asTimestamp(BEFORE_NOW));
			assertThat(allStockCandidates.get(1).getQty()).isEqualByComparingTo("-22");  // 13-35
			assertThat(allStockCandidates.get(2).getDateProjected()).isEqualTo(asTimestamp(NOW));
			assertThat(allStockCandidates.get(2).getQty()).isEqualByComparingTo("-5"); // 13-35+17
		}
	}

	private void createAndAddDemandWithQtyAndDemandDetail(
			@NonNull final BigDecimal qty,

			final int shipmentScheduleIdForDemandDetail)
	{
		createAndAddDemandWithQtyAndDemandDetail(qty, NOW, shipmentScheduleIdForDemandDetail);
	}

	private Candidate createAndAddDemandWithQtyAndDemandDetail(
			@NonNull final BigDecimal qty,
			@NonNull final Instant date,
			final int shipmentScheduleIdForDemandDetail)
	{
		final MaterialDescriptor materialDescr = createMaterialDescriptor(qty, date);

		RepositoryTestHelper.setupMockedRetrieveAvailableToPromise(
				stockRepository,
				materialDescr,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(materialDescr)

				.businessCase(CandidateBusinessCase.SHIPMENT)
				.businessCaseDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(
						shipmentScheduleIdForDemandDetail,
						0,
						0,
						TEN))
				.build();
		return candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	private MaterialDescriptor createMaterialDescriptor(
			@NonNull final BigDecimal qty,
			@NonNull final Instant date)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(date)
				.build();
		return materialDescr;
	}

	private Candidate createAndAddSupplyWithQtyAndDemandDetail(
			@NonNull final BigDecimal qty,
			final int shipmentScheduleIdForDemandDetail)
	{
		return createAndAddSupplyWithQtyAndDemandDetail(qty, NOW, shipmentScheduleIdForDemandDetail);
	}

	private Candidate createAndAddSupplyWithQtyAndDemandDetail(
			@NonNull final BigDecimal qty,
			@NonNull final Instant date,
			final int receiptScheduleIdForSupplyDetail)
	{
		final MaterialDescriptor supplyMaterialDescriptor = createMaterialDescriptor(qty, date);

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(supplyMaterialDescriptor)

				.businessCase(CandidateBusinessCase.PURCHASE)
				.businessCaseDetail(PurchaseDetail.builder()
						.qty(qty)
						.advised(Flag.TRUE)
						.receiptScheduleRepoId(receiptScheduleIdForSupplyDetail)
						.build())
				.build();

		return candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
	}
}
