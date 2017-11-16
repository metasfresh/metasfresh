package de.metas.material.dispo.service.candidatechange;

import static de.metas.material.event.EventTestHelper.CLIENT_ID;
import static de.metas.material.event.EventTestHelper.ORG_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateHandler;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.DateOperator;
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
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date t1 = SystemTime.asDate();
	private final Date t2 = TimeUtil.addMinutes(t1, 10);
	private final Date t3 = TimeUtil.addMinutes(t1, 20);
	private final Date t4 = TimeUtil.addMinutes(t1, 30);

	private final int OTHER_WAREHOUSE_ID = WAREHOUSE_ID + 10;

	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private CandidateChangeService candidateChangeHandler;

	@Mocked
	private MaterialEventService materialEventService;

	private StockCandidateService stockCandidateService;

	private CandidateRepositoryCommands candidateRepositoryCommands;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();
		candidateRepositoryCommands = new CandidateRepositoryCommands();

		stockCandidateService = new StockCandidateService(candidateRepositoryRetrieval, candidateRepositoryCommands);

		candidateChangeHandler = new CandidateChangeService(
				ImmutableList.of(
						new DemandCandiateHandler(candidateRepositoryRetrieval, candidateRepositoryCommands, materialEventService, stockCandidateService),
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
	public void testApplyDeltaToLaterStockCandidates()
	{
		final Candidate earlierCandidate;
		final Candidate candidate;
		final Candidate evenLaterCandidate;
		final Candidate evenLaterCandidateWithDifferentWarehouse;

		// preparations
		{
			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.complete(true)
					.productDescriptor(createProductDescriptor())
					.warehouseId(WAREHOUSE_ID)
					.quantity(new BigDecimal("10"))
					.date(t2)
					.build();

			candidate = Candidate.builder()
					.type(CandidateType.STOCK)
					.clientId(CLIENT_ID)
					.orgId(ORG_ID)
					.materialDescriptor(materialDescriptor)
					.build();
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(candidate);

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
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(
				createProductDescriptor(),
				WAREHOUSE_ID,
				t2,
				earlierCandidate.getGroupId(),
				new BigDecimal("3"));

		// assert that every stock record got some groupId
		DispoTestUtils.retrieveAllRecords().forEach(r -> assertThat(r.getMD_Candidate_GroupId(), greaterThan(0)));

		final Candidate earlierCandidateAfterChange = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(mkStockUntilSegment(t1, WAREHOUSE_ID));
		assertThat(earlierCandidateAfterChange).isNotNull();
		assertThat(earlierCandidateAfterChange.getQuantity()).isEqualTo(earlierCandidate.getQuantity()); // quantity shall be unchanged
		assertThat(earlierCandidateAfterChange.getGroupId()).isEqualTo(earlierCandidate.getGroupId()); // basically the same candidate

		final I_MD_Candidate candidateRecordAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t2).get(0); // candidateRepository.retrieveExact(candidate).get();
		assertThat(candidateRecordAfterChange.getQty()).isEqualByComparingTo("10"); // quantity shall be unchanged, because that method shall only update *later* records
		assertThat(candidateRecordAfterChange.getMD_Candidate_GroupId(), not(is(earlierCandidate.getGroupId())));

		final Candidate laterCandidateAfterChange = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(mkStockUntilSegment(t3, WAREHOUSE_ID));
		assertThat(laterCandidateAfterChange).isNotNull();
		assertThat(laterCandidateAfterChange.getQuantity()).isEqualByComparingTo("13"); // quantity shall be plus 3
		assertThat(laterCandidateAfterChange.getGroupId()).isEqualTo(earlierCandidate.getGroupId());

		final I_MD_Candidate evenLaterCandidateRecordAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t4, PRODUCT_ID, WAREHOUSE_ID).get(0); // candidateRepository.retrieveExact(evenLaterCandidate).get();
		assertThat(evenLaterCandidateRecordAfterChange.getQty()).isEqualByComparingTo("15"); // quantity shall be plus 3 too
		assertThat(evenLaterCandidateRecordAfterChange.getMD_Candidate_GroupId()).isEqualTo(earlierCandidate.getGroupId());

		final I_MD_Candidate evenLaterCandidateWithDifferentWarehouseAfterChange = DispoTestUtils.filter(CandidateType.STOCK, t4, PRODUCT_ID, OTHER_WAREHOUSE_ID).get(0); // candidateRepository.retrieveExact(evenLaterCandidateWithDifferentWarehouse).get();
		assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getQty()).isEqualByComparingTo("12"); // quantity shall be unchanged, because we changed another warehouse and this one should not have been matched
		assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getMD_Candidate_GroupId(), not(is(earlierCandidate.getGroupId())));

	}

	private CandidatesQuery mkStockUntilSegment(@NonNull final Date timestamp, final int warehouseId)
	{
		return CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptor(MaterialDescriptor.builderForQuery()
						.productDescriptor(createProductDescriptor())
						.warehouseId(warehouseId)
						.date(timestamp)
						.dateOperator(DateOperator.BEFORE_OR_AT)
						.build())
				.build();
	}

	/**
	 * <table border="1">
	 * <thead>
	 * <tr>
	 * <th>#</th>
	 * <th>event</th>
	 * <th>candidates</th>
	 * <th>onHandQty</th>
	 * <th>comment</th>
	 * </tr>
	 * </thead>
	 * <tbody>
	 * <tr>
	 * <td>1</td>
	 * <td>t1,w1,l1 =&gt; + 10</td>
	 * <td><strong>(t1,w1,l1) 10</strong></td>
	 * <td>t1 10</td>
	 * <td></td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>t4,w1,l1 =&gt; + 2</td>
	 * <td>(t1,w1,l1) 10<br>
	 * <strong>(t4,w1,l1) 12</strong></td>
	 * <td>t1 10<br>
	 * t4 12</td>
	 * <td></td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>t3,w1,l1 =&gt; - 3</td>
	 * <td>(t1,w1,l1) 10<br>
	 * <strong>(t3,w1,l1) 7</strong><br>
	 * (t4,w1,l1) 9</td>
	 * <td>t1 10<br>
	 * t3 7<br>
	 * t4 9</td>
	 * <td>the event causes a new record to be squeezed<br>
	 * between the records of events 1 and 2</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td>t2,w1,<strong>l2</strong> =&gt; - 4</td>
	 * <td>(t1,w1,l1) 10<br>
	 * (t3,w1,l1) 7<br>
	 * (t4,w1,l1) 9<br>
	 * <strong>(t2,w1,l2) -4</strong></td>
	 * <td>t1 10<br>
	 * t2 6<br>
	 * t3 4<br>
	 * t4 5</td>
	 * <td></td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	@Test
	public void testUpdateStockDifferentTimes()
	{
		invokeUpdateStock(t1, new BigDecimal("10"));
		invokeUpdateStock(t4, new BigDecimal("2"));
		invokeUpdateStock(t3, new BigDecimal("-3"));
		invokeUpdateStock(t2, new BigDecimal("-4"));

		final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
		assertThat(records).hasSize(4);

		assertThat(records.get(0).getDateProjected().getTime()).isEqualTo(t1.getTime());
		assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));

		assertThat(records.get(1).getDateProjected().getTime()).isEqualTo(t2.getTime());
		assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("6"));

		assertThat(records.get(2).getDateProjected().getTime()).isEqualTo(t3.getTime());
		assertThat(records.get(2).getQty()).isEqualByComparingTo(new BigDecimal("3"));

		assertThat(records.get(3).getDateProjected().getTime()).isEqualTo(t4.getTime());
		assertThat(records.get(3).getQty()).isEqualByComparingTo(new BigDecimal("5"));

		// all these stock records need to have the same group-ID
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId, greaterThan(0));
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	private Candidate invokeUpdateStock(final Date t, final BigDecimal qty)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		final Candidate processedCandidate = stockCandidateService.addOrUpdateStock(candidate);
		return processedCandidate;
	}

	/**
	 * Similar to {@link #testUpdateStockDifferentTimes()}, but two invocations have the same timestamp.
	 */
	@Test
	public void testUpdateStockWithOverlappingTime()
	{
		{
			invokeUpdateStock(t1, new BigDecimal("10"));

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records).hasSize(1);
			assertThat(records.get(0).getDateProjected().getTime()).isEqualTo(t1.getTime());
			assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));
		}

		{
			invokeUpdateStock(t4, new BigDecimal("2"));

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records).hasSize(2);
			assertThat(records.get(0).getDateProjected().getTime()).isEqualTo(t1.getTime());
			assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));
			assertThat(records.get(1).getDateProjected().getTime()).isEqualTo(t4.getTime());
			assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("12"));
		}

		{
			invokeUpdateStock(t3, new BigDecimal("-3"));

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records).hasSize(3);

			assertThat(records.get(0).getDateProjected().getTime()).isEqualTo(t1.getTime());
			assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));
			assertThat(records.get(1).getDateProjected().getTime()).isEqualTo(t3.getTime());
			assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("7"));
			assertThat(records.get(2).getDateProjected().getTime()).isEqualTo(t4.getTime());
			assertThat(records.get(2).getQty()).isEqualByComparingTo(new BigDecimal("9"));
		}

		{
			invokeUpdateStock(t3, new BigDecimal("-4")); // same time again!

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records).hasSize(3);

			assertThat(records.get(0).getDateProjected().getTime()).isEqualTo(t1.getTime());
			assertThat(records.get(0).getQty()).isEqualByComparingTo(new BigDecimal("10"));

			assertThat(records.get(1).getDateProjected().getTime()).isEqualTo(t3.getTime());
			assertThat(records.get(1).getQty()).isEqualByComparingTo(new BigDecimal("3"));

			assertThat(records.get(2).getDateProjected().getTime()).isEqualTo(t4.getTime());
			assertThat(records.get(2).getQty()).isEqualByComparingTo(new BigDecimal("5"));
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId, greaterThan(0));
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId()).isEqualTo(groupId));
	}

	public List<I_MD_Candidate> retrieveAllRecordsSorted()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_MD_Candidate.class)
				.orderBy()
				.addColumn(I_MD_Candidate.COLUMN_DateProjected)
				.addColumn(I_MD_Candidate.COLUMN_MD_Candidate_ID)
				.endOrderBy()
				.create()
				.list();
	}

	/**
	 * Verifies that {@link CandidateChangeService#addOrUpdateStock(Candidate)} also works if the candidate we update with is not a stock candidate.
	 */
	@Test
	public void testOnStockCandidateNewOrChangedNotStockType()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(new BigDecimal("10"))
				.date(t2)
				.build();

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();

		final Candidate processedCandidate = stockCandidateService.addOrUpdateStock(candidate);
		assertThat(processedCandidate.getType()).isEqualTo(CandidateType.STOCK);
		assertThat(processedCandidate.getMaterialDescriptor().getDate().getTime()).isEqualTo(t2.getTime());
		assertThat(processedCandidate.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(processedCandidate.getMaterialDescriptor().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(processedCandidate.getMaterialDescriptor().getWarehouseId()).isEqualTo(WAREHOUSE_ID);
	}

	/**
	 * Similar to {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords()}, but then adds an accompanying demand and verifies the SeqNo values
	 */
	@Test
	public void testDemandCandidateThenSupplyCandidate()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableStock(
				candidateRepositoryRetrieval,
				materialDescr,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescr)
				.build();
		candidateChangeHandler.onCandidateNewOrChange(candidate);
		// we don't really check here..this first part is already verified in testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
		assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one demand, one stock

		final MaterialDescriptor supplyMaterialDescriptor = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(supplyMaterialDescriptor)
				.build();

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
		{
			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(3); // one demand, one supply and one shared stock

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);

			// first the the demand then the stock, then the supply; i.e. the demand has the smallest SeqNo, the supply has the biggest
			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1);
			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1);

			assertThat(stockRecord.getQty()).isEqualByComparingTo(BigDecimal.ZERO); // shall be balanced between the demand and the supply
		}
	}

	/**
	 * Similar to {@link #testDemandCandidateThenSupplyCandidate()}, but this time, we first add the supply candidate.
	 * Therefore its {@link I_MD_Candidate} records gets to be persisted first. still, the {@code SeqNo} needs to be "stable".
	 */
	@Test
	public void testSupplyCandidateThenDemandCandidate()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor supplyMaterialDescriptor = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(supplyMaterialDescriptor)
				.build();
		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);

		{
			assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(2); // one supply, one stock

			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			assertThat(supplyRecord.getMD_Candidate_Parent_ID()).isEqualTo(stockRecord.getMD_Candidate_ID());
			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1);
		}

		final MaterialDescriptor demandMaterialDescriptor = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();
		RepositoryTestHelper.setupMockedRetrieveAvailableStock(
				candidateRepositoryRetrieval,
				demandMaterialDescriptor,
				"0");
		final Candidate demandCandidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(demandMaterialDescriptor)
				.build();
		candidateChangeHandler.onCandidateNewOrChange(demandCandidate);

		{
			assertThat(DispoTestUtils.retrieveAllRecords()).hasSize(3); // one demand, one supply and one shared stock

			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(CandidateType.SUPPLY).get(0);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

			assertThat(supplyRecord.getSeqNo()).isEqualTo(stockRecord.getSeqNo() + 1); // as before
			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1);

			assertThat(stockRecord.getQty()).isEqualByComparingTo(BigDecimal.ZERO); // shall be balanced between the demand and the supply
		}
	}

	/**
	 * Like {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords()},
	 * but the method under test is called two times. We expect the code to recognize this and not count the 2nd invocation.
	 */
	@Test
	public void testOnDemandCandidateCandidateNewOrChange_noOlderRecords_invokeTwiceWithSame()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableStock(
				candidateRepositoryRetrieval,
				materialDescriptor,
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final Consumer<Candidate> doTest = candidateUnderTest -> {

			candidateChangeHandler.onCandidateNewOrChange(candidateUnderTest);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

			assertThat(demandRecord.getQty()).isEqualByComparingTo(qty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(qty.negate()); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have a smaller value
		};

		doTest.accept(candidate); // first invocation
		doTest.accept(candidate); // second invocation
	}

	/**
	 * like {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords_invokeTwiceWitDifferent()},
	 * but on the 2nd invocation, a different demand-quantity is used.
	 */
	@Test
	public void testOnDemandCandidateCandidateNewOrChange_noOlderRecords_invokeTwiceWitDifferent()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.complete(true)
				.productDescriptor(createProductDescriptor())
				.warehouseId(WAREHOUSE_ID)
				.quantity(qty)
				.date(t)
				.build();

		RepositoryTestHelper.setupMockedRetrieveAvailableStock(
				candidateRepositoryRetrieval,
				null, // null=any
				"0");

		final Candidate candidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.clientId(CLIENT_ID)
				.orgId(ORG_ID)
				.materialDescriptor(materialDescriptor)
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidateUnderTest, expectedQty) -> {
			candidateChangeHandler.onCandidateNewOrChange(candidateUnderTest);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records).hasSize(2);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(CandidateType.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(CandidateType.DEMAND).get(0);

			assertThat(demandRecord.getQty()).isEqualByComparingTo(expectedQty);
			assertThat(stockRecord.getQty()).isEqualByComparingTo(expectedQty.negate()); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(stockRecord.getMD_Candidate_Parent_ID()).isEqualTo(demandRecord.getMD_Candidate_ID());

			assertThat(stockRecord.getSeqNo()).isEqualTo(demandRecord.getSeqNo() + 1); // when we sort by SeqNo, the demand needs to be first and thus have the smaller number
		};

		doTest.accept(candidate, qty); // first invocation
		doTest.accept(candidate.withQuantity(qty.add(BigDecimal.ONE)), qty.add(BigDecimal.ONE)); // second invocation
	}

}
