package de.metas.material.dispo;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateChangeHandler;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.model.I_MD_Candidate;

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
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
public class CandidateChangeHandlerTests
{
	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date t1 = SystemTime.asDate();
	private final Date t2 = TimeUtil.addMinutes(t1, 10);
	private final Date t3 = TimeUtil.addMinutes(t1, 20);
	private final Date t4 = TimeUtil.addMinutes(t1, 30);

	private I_M_Product product;

	private I_M_Warehouse warehouse;
	private I_M_Warehouse otherWarehouse;

	private I_C_UOM uom;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CandidateChangeHandler candidateChangeHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.save(product);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		otherWarehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(otherWarehouse);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);
	}

	/**
	 * Verifies that {@link CandidateChangeHandler#applyDeltaToLaterStockCandidates(CandidatesSegment, BigDecimal)} applies the given delta to the right records.
	 * Only records that have a <i>different</i> M_Warenhouse_ID shall not be touched.
	 */
	@Test
	public void testApplyDeltaToLaterStockCandidates()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(t2)
				.build();
		candidateRepository.addOrReplace(candidate);

		final Candidate earlierCandidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(t1)
				.build();
		candidateRepository.addOrReplace(earlierCandidate);

		final Candidate laterCandidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(t3)
				.build();
		candidateRepository.addOrReplace(laterCandidate);

		final Candidate evenLaterCandidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("12"))
				.date(t4)
				.build();
		candidateRepository.addOrReplace(evenLaterCandidate);

		final Candidate evenLaterCandidateWithDifferentWarehouse = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(otherWarehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("12"))
				.date(t4)
				.build();
		candidateRepository.addOrReplace(evenLaterCandidateWithDifferentWarehouse);

		candidateChangeHandler.applyDeltaToLaterStockCandidates(
				mkSegment(t2, warehouse),
				new BigDecimal("3"));

		final Optional<Candidate> earlierCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegment(t1, warehouse));
		assertThat(earlierCandidateAfterChange.isPresent(), is(true));
		assertThat(earlierCandidateAfterChange.get().getQuantity(), is(earlierCandidate.getQuantity())); // quantity shall be unchanged

		final I_MD_Candidate candidateRecordAfterChange = candidateRepository.retrieveExact(candidate).get();
		assertThat(candidateRecordAfterChange.getQty(), is(new BigDecimal("10"))); // quantity shall be unchanged, because that method shall only update *later* records

		final Optional<Candidate> laterCandidateAfterChange = candidateRepository.retrieveStockAt(mkSegment(t3, warehouse));
		assertThat(laterCandidateAfterChange.isPresent(), is(true));
		assertThat(laterCandidateAfterChange.get().getQuantity(), is(new BigDecimal("13"))); // quantity shall be plus 3

		final I_MD_Candidate evenLaterCandidateWithOutLocatorRecordAfterChange = candidateRepository.retrieveExact(evenLaterCandidate).get();
		assertThat(evenLaterCandidateWithOutLocatorRecordAfterChange.getQty(), is(new BigDecimal("15"))); // quantity shall be plus 3 too

		final I_MD_Candidate evenLaterCandidateWithDifferentLocatorRecordAfterChange = candidateRepository.retrieveExact(evenLaterCandidateWithDifferentWarehouse).get();
		assertThat(evenLaterCandidateWithDifferentLocatorRecordAfterChange.getQty(), is(new BigDecimal("12"))); // quantity shall be unchanged, because we changed another warehouse and this one should not have been matched
	}

	private CandidatesSegment mkSegment(final Date timestamp, final I_M_Warehouse warehouse)
	{
		return CandidatesSegment.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(timestamp)
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
	 * <td>t2,w1,<strong>l2</strong> =&gt; - 3</td>
	 * <td>(t1,w1,l1) 10<br>
	 * (t3,w1,l1) 7<br>
	 * (t4,w1,l1) 9<br>
	 * <strong>(t2,w1,l2) -3</strong></td>
	 * <td>t1 10<br>
	 * t2 7<br>
	 * t3 4<br>
	 * t4 6</td>
	 * <td></td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	@Test
	public void testOnStockCandidateNewOrChanged()
	{
		invokeOnStockCandidateNewOrChangedWithCandidate(t1, new BigDecimal("10"));
		invokeOnStockCandidateNewOrChangedWithCandidate(t4, new BigDecimal("2"));
		invokeOnStockCandidateNewOrChangedWithCandidate(t3, new BigDecimal("-3"));
		invokeOnStockCandidateNewOrChangedWithCandidate(t2, new BigDecimal("-3"));

		final List<I_MD_Candidate> records = retriveRecords();
		assertThat(records.size(), is(4));

		assertThat(records.get(0).getDateProjected().getTime(), is(t1.getTime()));
		assertThat(records.get(0).getQty(), comparesEqualTo(new BigDecimal("10")));

		assertThat(records.get(1).getDateProjected().getTime(), is(t2.getTime()));
		assertThat(records.get(1).getQty(), comparesEqualTo(new BigDecimal("7")));

		assertThat(records.get(2).getDateProjected().getTime(), is(t3.getTime()));
		assertThat(records.get(2).getQty(), comparesEqualTo(new BigDecimal("4")));

		assertThat(records.get(3).getDateProjected().getTime(), is(t4.getTime()));
		assertThat(records.get(3).getQty(), comparesEqualTo(new BigDecimal("6")));
	}

	@Test
	public void testOnStockCandidateNewOrChangedNotStockType()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(t2)
				.build();
		
		final Candidate processedCandidate = candidateChangeHandler.updateStock(candidate);
		assertThat(processedCandidate.getType(), is(Type.STOCK));
		assertThat(processedCandidate.getDate().getTime(), is(t2.getTime()));
		assertThat(processedCandidate.getQuantity(), comparesEqualTo(BigDecimal.TEN));
		assertThat(processedCandidate.getProductId(), is(product.getM_Product_ID()));
		assertThat(processedCandidate.getWarehouseId(), is(warehouse.getM_Warehouse_ID()));
	}
	
	private Candidate invokeOnStockCandidateNewOrChangedWithCandidate(Date t, BigDecimal qty)
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		final Candidate processedCandidate = candidateChangeHandler.updateStock(candidate);
		return processedCandidate;
	}

	private List<I_MD_Candidate> retriveRecords()
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

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = retriveRecords();
		assertThat(records.size(), is(2));
		final I_MD_Candidate stockRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.STOCK.toString())).findFirst().get();
		final I_MD_Candidate supplyRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.SUPPLY.toString())).findFirst().get();

		assertThat(supplyRecord.getQty(), comparesEqualTo(qty));
		assertThat(stockRecord.getQty(), comparesEqualTo(qty)); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));
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
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(olderStockQty)
				.date(t1)
				.build();
		final Candidate persistedOlderStockCandidate = candidateChangeHandler.updateStock(olderStockCandidate);

		final BigDecimal supplyQty = new BigDecimal("23");

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.PRODUCTION)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(supplyQty)
				.date(t2)
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = retriveRecords();
		assertThat(records.size(), is(3));
		final I_MD_Candidate stockRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.STOCK.toString()) && r.getMD_Candidate_ID() != persistedOlderStockCandidate.getId()).findFirst().get();
		final I_MD_Candidate supplyRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.SUPPLY.toString())).findFirst().get();

		assertThat(supplyRecord.getQty(), comparesEqualTo(supplyQty));
		assertThat(supplyRecord.getMD_Candidate_SubType(), is(SubType.PRODUCTION.toString()));
		assertThat(stockRecord.getQty(), comparesEqualTo(new BigDecimal("34")));
		assertThat(supplyRecord.getMD_Candidate_Parent_ID(), is(stockRecord.getMD_Candidate_ID()));
	}
	
	@Test
	public void testOnDemandCandidateCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> records = retriveRecords();
		assertThat(records.size(), is(2));
		final I_MD_Candidate stockRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.STOCK.toString())).findFirst().get();
		final I_MD_Candidate demandRecord = records.stream().filter(r -> r.getMD_Candidate_Type().equals(Type.DEMAND.toString())).findFirst().get();

		assertThat(demandRecord.getQty(), comparesEqualTo(qty));
		assertThat(stockRecord.getQty(), comparesEqualTo(qty.negate())); // ..because there was no older record, the "delta" we provided is the current quantity
		assertThat(stockRecord.getMD_Candidate_Parent_ID(), is(demandRecord.getMD_Candidate_ID()));
	}

}
