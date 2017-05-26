package de.metas.material.dispo.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.MaterialEventService;
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

	private I_AD_Org org;

	private I_M_Product product;

	private I_M_Warehouse warehouse;
	private I_M_Warehouse otherWarehouse;

	private I_C_UOM uom;

	private CandidateRepository candidateRepository;

	private CandidateChangeHandler candidateChangeHandler;

	@Mocked
	private MaterialEventService materialEventService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.save(product);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		otherWarehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(otherWarehouse);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		candidateRepository = new CandidateRepository();
		candidateChangeHandler = new CandidateChangeHandler(
				candidateRepository,
				new CandidateFactory(candidateRepository),
				materialEventService);
	}

	/**
	 * Verifies that {@link CandidateChangeHandler#applyDeltaToLaterStockCandidates(CandidatesSegment, BigDecimal)} applies the given delta to the right records.
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
			candidate = Candidate.builder()
					.type(Type.STOCK)
					.clientId(org.getAD_Client_ID())
					.orgId(org.getAD_Org_ID())
					.productId(product.getM_Product_ID())
					.warehouseId(warehouse.getM_Warehouse_ID())
					.quantity(new BigDecimal("10"))
					.date(t2)
					.build();
			candidateRepository.addOrUpdate(candidate);

			earlierCandidate = candidateRepository
					.addOrUpdate(Candidate.builder()
							.type(Type.STOCK)
							.clientId(org.getAD_Client_ID())
							.orgId(org.getAD_Org_ID())
							.productId(product.getM_Product_ID())
							.warehouseId(warehouse.getM_Warehouse_ID())
							.quantity(new BigDecimal("10"))
							.date(t1)
							.build());

			final Candidate laterCandidate = Candidate.builder()
					.type(Type.STOCK)
					.clientId(org.getAD_Client_ID())
					.orgId(org.getAD_Org_ID())
					.productId(product.getM_Product_ID())
					.warehouseId(warehouse.getM_Warehouse_ID())
					.quantity(new BigDecimal("10"))
					.date(t3)
					.build();
			candidateRepository.addOrUpdate(laterCandidate);

			evenLaterCandidate = Candidate.builder()
					.type(Type.STOCK)
					.clientId(org.getAD_Client_ID())
					.orgId(org.getAD_Org_ID())
					.productId(product.getM_Product_ID())
					.warehouseId(warehouse.getM_Warehouse_ID())
					.quantity(new BigDecimal("12"))
					.date(t4)
					.build();
			candidateRepository.addOrUpdate(evenLaterCandidate);

			evenLaterCandidateWithDifferentWarehouse = Candidate.builder()
					.type(Type.STOCK)
					.clientId(org.getAD_Client_ID())
					.orgId(org.getAD_Org_ID())
					.productId(product.getM_Product_ID())
					.warehouseId(otherWarehouse.getM_Warehouse_ID())
					.quantity(new BigDecimal("12"))
					.date(t4)
					.build();
			candidateRepository.addOrUpdate(evenLaterCandidateWithDifferentWarehouse);
		}

		// do the test
		candidateChangeHandler.applyDeltaToLaterStockCandidates(
				product.getM_Product_ID(),
				warehouse.getM_Warehouse_ID(),
				t2,
				earlierCandidate.getGroupId(),
				new BigDecimal("3"));

		// assert that every stock record got some groupId
		DispoTestUtils.retrieveAllRecords().forEach(r -> assertThat(r.getMD_Candidate_GroupId(), greaterThan(0)));

		final Optional<Candidate> earlierCandidateAfterChange = candidateRepository.retrieveLatestMatch(mkStockUntilSegment(t1, warehouse));
		assertThat(earlierCandidateAfterChange.isPresent(), is(true));
		assertThat(earlierCandidateAfterChange.get().getQuantity(), is(earlierCandidate.getQuantity())); // quantity shall be unchanged
		assertThat(earlierCandidateAfterChange.get().getGroupId(), is(earlierCandidate.getGroupId())); // basically the same candidate

		final I_MD_Candidate candidateRecordAfterChange = DispoTestUtils.filter(Type.STOCK, t2).get(0); // candidateRepository.retrieveExact(candidate).get();
		assertThat(candidateRecordAfterChange.getQty(), is(new BigDecimal("10"))); // quantity shall be unchanged, because that method shall only update *later* records
		assertThat(candidateRecordAfterChange.getMD_Candidate_GroupId(), not(is(earlierCandidate.getGroupId())));

		final Optional<Candidate> laterCandidateAfterChange = candidateRepository.retrieveLatestMatch(mkStockUntilSegment(t3, warehouse));
		assertThat(laterCandidateAfterChange.isPresent(), is(true));
		assertThat(laterCandidateAfterChange.get().getQuantity(), is(new BigDecimal("13"))); // quantity shall be plus 3
		assertThat(laterCandidateAfterChange.get().getGroupId(), is(earlierCandidate.getGroupId()));

		final I_MD_Candidate evenLaterCandidateRecordAfterChange = DispoTestUtils.filter(Type.STOCK, t4, product.getM_Product_ID(), warehouse.getM_Warehouse_ID()).get(0); // candidateRepository.retrieveExact(evenLaterCandidate).get();
		assertThat(evenLaterCandidateRecordAfterChange.getQty(), is(new BigDecimal("15"))); // quantity shall be plus 3 too
		assertThat(evenLaterCandidateRecordAfterChange.getMD_Candidate_GroupId(), is(earlierCandidate.getGroupId()));

		final I_MD_Candidate evenLaterCandidateWithDifferentWarehouseAfterChange = DispoTestUtils.filter(Type.STOCK, t4, product.getM_Product_ID(), otherWarehouse.getM_Warehouse_ID()).get(0); //candidateRepository.retrieveExact(evenLaterCandidateWithDifferentWarehouse).get();
		assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getQty(), is(new BigDecimal("12"))); // quantity shall be unchanged, because we changed another warehouse and this one should not have been matched
		assertThat(evenLaterCandidateWithDifferentWarehouseAfterChange.getMD_Candidate_GroupId(), not(is(earlierCandidate.getGroupId())));

	}

	private CandidatesSegment mkStockUntilSegment(@NonNull final Date timestamp, @NonNull final I_M_Warehouse warehouse)
	{
		return CandidatesSegment.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(timestamp)
				.dateOperator(DateOperator.until)
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
		assertThat(records.size(), is(4));

		assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
		assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));

		assertThat(records.get(1).getDateProjected().getTime(), is(t2.getTime()));
		assertThat(records.get(1).getQty(), comparesEqualTo(new BigDecimal("6")));

		assertThat(records.get(2).getDateProjected().getTime(), is(t3.getTime()));
		assertThat(records.get(2).getQty(), comparesEqualTo(new BigDecimal("3")));

		assertThat(records.get(3).getDateProjected().getTime(), is(t4.getTime()));
		assertThat(records.get(3).getQty(), comparesEqualTo(new BigDecimal("5")));

		// all these stock records need to have the same group-ID
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId, greaterThan(0));
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId(), is(groupId)));
	}

	private Candidate invokeUpdateStock(final Date t, final BigDecimal qty)
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		final Candidate processedCandidate = candidateChangeHandler.addOrUpdateStock(candidate);
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
			assertThat(records.size(), is(1));
			assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
			assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));
		}

		{
			invokeUpdateStock(t4, new BigDecimal("2"));

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records.size(), is(2));
			assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
			assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));
			assertThat(records.get(1).getDateProjected().getTime(), is(t4.getTime()));
			assertThat(records.get(1).getQty(), comparesEqualTo(new BigDecimal("12")));
		}

		{
			invokeUpdateStock(t3, new BigDecimal("-3"));

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records.size(), is(3));

			assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
			assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));
			assertThat(records.get(1).getDateProjected().getTime(), is(t3.getTime()));
			assertThat(records.get(1).getQty(), comparesEqualTo(new BigDecimal("7")));
			assertThat(records.get(2).getDateProjected().getTime(), is(t4.getTime()));
			assertThat(records.get(2).getQty(), comparesEqualTo(new BigDecimal("9")));
		}

		{
			invokeUpdateStock(t3, new BigDecimal("-4")); // same time again!

			final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
			assertThat(records.size(), is(3));

			assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
			assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));

			assertThat(records.get(1).getDateProjected().getTime(), is(t3.getTime()));
			assertThat(records.get(1).getQty(), comparesEqualTo(new BigDecimal("3")));

			assertThat(records.get(2).getDateProjected().getTime(), is(t4.getTime()));
			assertThat(records.get(2).getQty(), comparesEqualTo(new BigDecimal("5")));
		}

		// all these stock records need to have the same group-ID
		final List<I_MD_Candidate> records = retrieveAllRecordsSorted();
		final int groupId = records.get(0).getMD_Candidate_GroupId();
		assertThat(groupId, greaterThan(0));
		records.forEach(r -> assertThat(r.getMD_Candidate_GroupId(), is(groupId)));
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
	 * Verifies that {@link CandidateChangeHandler#addOrUpdateStock(Candidate)} also works if the candidate we update with is not a stock candidate.
	 */
	@Test
	public void testOnStockCandidateNewOrChangedNotStockType()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(t2)
				.build();

		final Candidate processedCandidate = candidateChangeHandler.addOrUpdateStock(candidate);
		assertThat(processedCandidate.getType(), is(Type.STOCK));
		assertThat(processedCandidate.getDate().getTime(), is(t2.getTime()));
		assertThat(processedCandidate.getQuantity(), comparesEqualTo(BigDecimal.TEN));
		assertThat(processedCandidate.getProductId(), is(product.getM_Product_ID()));
		assertThat(processedCandidate.getWarehouseId(), is(warehouse.getM_Warehouse_ID()));
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records.size(), is(2));
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);

		assertThat(supplyRecord.getQty(), comparesEqualTo(qty));
		assertThat(stockRecord.getQty(), comparesEqualTo(qty)); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));
		assertThat(supplyRecord.getSeqNo() - 1, is(stockRecord.getSeqNo())); // when we sort by SeqNo, the stock needs to be first
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords_invokeTwiceWithSame()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidatee = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();

		final Consumer<Candidate> doTest = candidate -> {

			candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records.size(), is(2));
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);

			assertThat(supplyRecord.getQty(), comparesEqualTo(qty));
			assertThat(stockRecord.getQty(), comparesEqualTo(qty)); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));

			assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1)); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
		};

		doTest.accept(candidatee); // 1st invocation
		doTest.accept(candidatee); // 2nd invocation, same candidate
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords_invokeTwiceWithDifferent()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidatee = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidate, exptectedQty) -> {

			candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records.size(), is(2));
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);

			assertThat(supplyRecord.getQty(), comparesEqualTo(exptectedQty));
			assertThat(stockRecord.getQty(), comparesEqualTo(exptectedQty)); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));

			assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1)); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
		};

		doTest.accept(candidatee, qty); // 1st invocation
		doTest.accept(candidatee.withQuantity(qty.add(BigDecimal.ONE)), qty.add(BigDecimal.ONE)); // 2nd invocation, same candidate
	}

	/**
	 * If this test fails, please first verify whether {@link #testOnStockCandidateNewOrChanged()} and {@link #testOnSupplyCandidateNewOrChange_noOlderRecords()} work.
	 */
	@Test
	public void testOnSupplyCandidateNewOrChange()
	{
		final BigDecimal olderStockQty = new BigDecimal("11");

		final Candidate olderStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(olderStockQty)
				.date(t1)
				.build();
		candidateChangeHandler.addOrUpdateStock(olderStockCandidate);

		final BigDecimal supplyQty = new BigDecimal("23");

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.subType(SubType.PRODUCTION)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(supplyQty)
				.date(t2)
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records.size(), is(3));
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK, t2).get(0);
		final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);

		assertThat(supplyRecord.getQty(), comparesEqualTo(supplyQty));
		assertThat(supplyRecord.getMD_Candidate_SubType(), is(SubType.PRODUCTION.toString()));
		assertThat(stockRecord.getQty(), comparesEqualTo(new BigDecimal("34")));
		assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));

		assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1)); // when we sort by SeqNo, the stock needs to be first and thus have the smaller value
	}

	@Test
	public void testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
		assertThat(records.size(), is(2));
		final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
		final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);

		assertThat(demandRecord.getQty(), comparesEqualTo(qty));
		assertThat(stockRecord.getQty(), comparesEqualTo(qty.negate())); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

		assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1)); // when we sort by SeqNo, the demand needs to be first and thus have the smaller value
	}

	/**
	 * Similar to {@link #testOnDemandCandidateCandidateNewOrChange_noOlderRecords()}, but then adds an accompanying demand and verifies the SeqNo values
	 */
	@Test
	public void testDemandCandidateThenSupplyCandidate()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(candidate);
		// we don't really check here..this first part is already verified in testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
		assertThat(DispoTestUtils.retrieveAllRecords().size(), is(2)); // one demand, one stock

		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();

		candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);
		{
			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records.size(), is(3)); // one demand, one supply and one shared stock

			final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);

			// first the the demand then the stock, then the supply; i.e. the demand has the smallest SeqNo, the supply has the biggest
			assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1));
			assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1));

			assertThat(stockRecord.getQty(), comparesEqualTo(BigDecimal.ZERO)); // shall be balanced between the demand and the supply
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

		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);

		{
			assertThat(DispoTestUtils.retrieveAllRecords().size(), is(2)); // one supply, one stock

			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);
			assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));
			assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1));
		}

		final Candidate demandCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(demandCandidate);

		{
			assertThat(DispoTestUtils.retrieveAllRecords().size(), is(3)); // one demand, one supply and one shared stock

			final I_MD_Candidate supplyRecord = DispoTestUtils.filter(Type.SUPPLY).get(0);
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);

			assertThat(supplyRecord.getSeqNo(), is(stockRecord.getSeqNo() + 1)); // as before
			assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1));

			assertThat(stockRecord.getQty(), comparesEqualTo(BigDecimal.ZERO)); // shall be balanced between the demand and the supply
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

		final Candidate candidatee = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();

		final Consumer<Candidate> doTest = candidate -> {
			candidateChangeHandler.onDemandCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records.size(), is(2));
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);

			assertThat(demandRecord.getQty(), comparesEqualTo(qty));
			assertThat(stockRecord.getQty(), comparesEqualTo(qty.negate())); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

			assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1)); // when we sort by SeqNo, the demand needs to be first and thus have a smaller value
		};

		doTest.accept(candidatee); // first invocation
		doTest.accept(candidatee); // second invocation
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

		final Candidate candidatee = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.reference(TableRecordReference.of(1, 2))
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidate, expectedQty) -> {
			candidateChangeHandler.onDemandCandidateNewOrChange(candidate);

			final List<I_MD_Candidate> records = DispoTestUtils.retrieveAllRecords();
			assertThat(records.size(), is(2));
			final I_MD_Candidate stockRecord = DispoTestUtils.filter(Type.STOCK).get(0);
			final I_MD_Candidate demandRecord = DispoTestUtils.filter(Type.DEMAND).get(0);

			assertThat(demandRecord.getQty(), comparesEqualTo(expectedQty));
			assertThat(stockRecord.getQty(), comparesEqualTo(expectedQty.negate())); // ..because there was no older record, the "delta" we provided is the current quantity
			assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));

			assertThat(stockRecord.getSeqNo(), is(demandRecord.getSeqNo() + 1)); // when we sort by SeqNo, the demand needs to be first and thus have the smaller number
		};

		doTest.accept(candidatee, qty); // first invocation
		doTest.accept(candidatee.withQuantity(qty.add(BigDecimal.ONE)), qty.add(BigDecimal.ONE)); // second invocation
	}

}
