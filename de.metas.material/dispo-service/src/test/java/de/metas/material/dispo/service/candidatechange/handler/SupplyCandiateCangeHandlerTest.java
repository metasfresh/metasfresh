package de.metas.material.dispo.service.candidatechange.handler;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
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
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandiateCangeHandler;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
import mockit.Mocked;

/*
 * #%L
 * metasfresh-material-dispo-service
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

public class SupplyCandiateCangeHandlerTest
{

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date t1 = SystemTime.asDate();
	private final Date t2 = TimeUtil.addMinutes(t1, 10);

	private I_AD_Org org;

	private I_M_Product product;

	private I_M_Warehouse warehouse;

	@Mocked
	private MaterialEventService materialEventService;

	private SupplyCandiateCangeHandler supplyCandiateCangeHandler;

	private StockCandidateService stockCandidateService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		org = newInstance(I_AD_Org.class);
		save(org);

		product = newInstance(I_M_Product.class);
		save(product);

		warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final CandidateRepository candidateRepository = new CandidateRepository();

		stockCandidateService = new StockCandidateService(candidateRepository);

		supplyCandiateCangeHandler = SupplyCandiateCangeHandler.builder()
				.candidateRepository(candidateRepository)
				.materialEventService(materialEventService)
				.stockCandidateService(stockCandidateService)
				.build();
	}

	@Test
	public void testOnSupplyCandidateNewOrChange_noOlderRecords()
	{
		final BigDecimal qty = new BigDecimal("23");
		final Date t = t1;

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		
		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.build();
		supplyCandiateCangeHandler.onSupplyCandidateNewOrChange(candidate);

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

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		
		final Candidate candidatee = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.build();

		final Consumer<Candidate> doTest = candidate -> {

			supplyCandiateCangeHandler.onSupplyCandidateNewOrChange(candidate);

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

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(qty)
				.date(t)
				.build();
		
		final Candidate candidatee = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.build();

		final BiConsumer<Candidate, BigDecimal> doTest = (candidate, exptectedQty) -> {

			supplyCandiateCangeHandler.onSupplyCandidateNewOrChange(candidate);

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

		final MaterialDescriptor olderMaterialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(olderStockQty)
				.date(t1)
				.build();
		
		final Candidate olderStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(olderMaterialDescr)
				.build();
		stockCandidateService.addOrUpdateStock(olderStockCandidate);

		final BigDecimal supplyQty = new BigDecimal("23");

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(supplyQty)
				.date(t2)
				.build();
		
		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.subType(SubType.PRODUCTION)
				.build();
		supplyCandiateCangeHandler.onSupplyCandidateNewOrChange(candidate);

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

}
