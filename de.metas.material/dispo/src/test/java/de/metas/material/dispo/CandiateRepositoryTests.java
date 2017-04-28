package de.metas.material.dispo;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;

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

public class CandiateRepositoryTests
{

	/** Watches the current tests and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private final Date now = SystemTime.asDate();
	private final Date earlier = TimeUtil.addMinutes(now, -10);
	private final Date later = TimeUtil.addMinutes(now, +10);

	private I_M_Product product;

	private I_M_Warehouse warehouse;

	private I_C_UOM uom;

	private Candidate stockCandidate;
	private Candidate laterStockCandidate;

	private CandidateRepository candidateRepository = new CandidateRepository();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.save(product);

		warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		InterfaceWrapperHelper.save(warehouse);

		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.save(uom);

		// this not-stock candidate needs to be ignored
		final Candidate someOtherCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("11"))
				.date(now)
				.build();
		candidateRepository.addOrReplace(someOtherCandidate);

		stockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(now)
				.build();
		stockCandidate = candidateRepository.addOrReplace(stockCandidate);

		laterStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.orgId(1)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(later)
				.build();
		laterStockCandidate = candidateRepository.addOrReplace(laterStockCandidate);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void addOrReplace_update()
	{
		// guard
		assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).isPresent(), is(true));
		assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).get(), is(stockCandidate));
		final List<Candidate> stockBeforeReplacement = candidateRepository.retrieveMatches(mkStockFromSegment(now));
		assertThat(stockBeforeReplacement.size(), is(2));
		assertThat(stockBeforeReplacement.stream().collect(Collectors.toList()), contains(stockCandidate, laterStockCandidate));

		final Candidate replacementCandidate = stockCandidate.withQuantity(BigDecimal.ONE);
		candidateRepository.addOrReplace(replacementCandidate);

		assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).get(), is(replacementCandidate));
		final List<Candidate> stockAfterReplacement = candidateRepository.retrieveMatches(mkStockFromSegment(now));
		assertThat(stockAfterReplacement.size(), is(2));
		assertThat(stockAfterReplacement.stream().collect(Collectors.toList()), contains(replacementCandidate, laterStockCandidate));
	}

	/**
	 * Verifies that {@link ProductionCandidateDetail} data is also persisted
	 */
	@Test
	public void addOrReplace_with_ProductionDetail()
	{
		final Candidate productionCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.PRODUCTION)
				.date(now)
				.orgId(20)
				.productId(23)
				.attributeSetInstanceId(35)
				.quantity(BigDecimal.TEN)
				.reference(TableRecordReference.of("someTable", 40))
				.warehouseId(50)
				.productionDetail(ProductionCandidateDetail.builder()
						.description("description")
						.plantId(60)
						.productBomLineId(70)
						.productPlanningId(80)
						.build())
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrReplace(productionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		assertThat(filtered.size(), is(1));

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID(), is(addOrReplaceResult.getId()));
		assertThat(record.getMD_Candidate_SubType(), is(productionCandidate.getSubType().toString()));
		assertThat(record.getM_Product_ID(), is(productionCandidate.getProductId()));

		final I_MD_Candidate_Prod_Detail productionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Prod_Detail.class).create().firstOnly(I_MD_Candidate_Prod_Detail.class);
		assertThat(productionDetailRecord, notNullValue());
		assertThat(productionDetailRecord.getDescription(), is("description"));
		assertThat(productionDetailRecord.getPP_Plant_ID(), is(60));
		assertThat(productionDetailRecord.getPP_Product_BOMLine_ID(), is(70));
		assertThat(productionDetailRecord.getPP_Product_Planning_ID(), is(80));
	}

	@Test
	public void retrieve_with_ProductionDetail()
	{
		final I_MD_Candidate record = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(24);
		record.setM_Warehouse_ID(51);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		InterfaceWrapperHelper.save(record);
		
		final I_MD_Candidate_Prod_Detail productionDetailRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate_Prod_Detail.class);
		productionDetailRecord.setDescription("description1");
		productionDetailRecord.setPP_Plant_ID(61);
		productionDetailRecord.setPP_Product_BOMLine_ID(71);
		productionDetailRecord.setPP_Product_Planning_ID(81);
		productionDetailRecord.setMD_Candidate(record);
		InterfaceWrapperHelper.save(productionDetailRecord);
		
		final Candidate cand = candidateRepository.retrieve(record.getMD_Candidate_ID());
		assertThat(cand, notNullValue());
		assertThat(cand.getProductId(), is(24));
		assertThat(cand.getWarehouseId(), is(51));
		assertThat(cand.getDate(), is(now));
		assertThat(cand.getProductionDetail(), notNullValue());
		assertThat(cand.getProductionDetail().getDescription(), is("description1"));
		assertThat(cand.getProductionDetail().getProductBomLineId(), is(71));
		assertThat(cand.getProductionDetail().getProductPlanningId(), is(81));
	}

	/**
	 * Verifies that if a candidate with a groupID is persisted, then that candidate groupId is also persisted.
	 * And if the respective candidate does no yet have a groupId, then the persisted candidate's Id is assigned to be its groupId.
	 */
	@Test
	public void addOrReplace_groupId_nonStockCandidate()
	{
		final Candidate candidateWithOutGroupId = stockCandidate
				.withType(Type.DEMAND)
				.withDate(TimeUtil.addMinutes(later, 1)) // pick a different time from the other candidates
				.withGroupId(null);

		final Candidate result1 = candidateRepository.addOrReplace(candidateWithOutGroupId);
		// result1 was assigned an id and a groupId
		assertThat(result1.getId(), greaterThan(0));
		assertThat(result1.getGroupId(), is(result1.getId()));

		final Candidate candidateWithGroupId = candidateWithOutGroupId
				.withDate(TimeUtil.addMinutes(later, 2)) // pick a different time from the other candidates
				.withGroupId(result1.getGroupId());

		final Candidate result2 = candidateRepository.addOrReplace(candidateWithGroupId);
		// result2 also has id & groupId, but its ID is unique whereas its groupId is the same as result1's groupId
		assertThat(result2.getId(), greaterThan(0));
		assertThat(result2.getGroupId(), not(is(result2.getId())));
		assertThat(result2.getGroupId(), is(result1.getGroupId()));

		final I_MD_Candidate result1Record = InterfaceWrapperHelper.create(Env.getCtx(), result1.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		assertThat(result1Record.getMD_Candidate_ID(), is(result1.getId()));
		assertThat(result1Record.getMD_Candidate_GroupId(), is(result1.getGroupId()));

		final I_MD_Candidate result2Record = InterfaceWrapperHelper.create(Env.getCtx(), result2.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		assertThat(result2Record.getMD_Candidate_ID(), is(result2.getId()));
		assertThat(result2Record.getMD_Candidate_GroupId(), is(result2.getGroupId()));
	}

	/**
	 * Verifies that candidates with type {@link Type#STOCK} do receive a groupId
	 */
	@Test
	public void addOrReplace_groupId_stockCandidate()
	{
		final Candidate candidateWithOutGroupId = stockCandidate
				.withDate(TimeUtil.addMinutes(later, 1)) // pick a different time from the other candidates
				.withGroupId(null);

		final Candidate result1 = candidateRepository.addOrReplace(candidateWithOutGroupId);
		assertThat(result1.getId(), greaterThan(0));
		assertThat(result1.getGroupId(), greaterThan(0));

		final I_MD_Candidate result1Record = InterfaceWrapperHelper.create(Env.getCtx(), result1.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		assertThat(result1Record.getMD_Candidate_ID(), is(result1.getId()));
		assertThat(result1Record.getMD_Candidate_GroupId(), is(result1.getGroupId()));
	}

	/**
	 * Verifies that {@link CandidateRepository#retrieveStockAt(CandidatesSegment)} returns the oldest stock candidate with a date before the given {@link CandidatesSegment}'s date
	 */
	@Test
	public void retrieveStockAt()
	{
		final CandidatesSegment earlierQuery = mkStockUntilSegment(earlier);
		final Optional<Candidate> earlierStock = candidateRepository.retrieveLatestMatch(earlierQuery);
		assertThat(earlierStock.isPresent(), is(false));

		final CandidatesSegment sameTimeQuery = mkStockUntilSegment(now);
		final Optional<Candidate> sameTimeStock = candidateRepository.retrieveLatestMatch(sameTimeQuery);
		assertThat(sameTimeStock.isPresent(), is(true));
		assertThat(sameTimeStock.get(), is(stockCandidate));

		final CandidatesSegment laterQuery = mkStockUntilSegment(later);
		final Optional<Candidate> laterStock = candidateRepository.retrieveLatestMatch(laterQuery);
		assertThat(laterStock.isPresent(), is(true));
		assertThat(laterStock.get(), is(laterStockCandidate));
	}

	// /**
	// *
	// * @param candidate
	// * @return returns a version of the given candidate that has {@code null}-Ids; background: we need to "dump it down" to be comparable with the "original"
	// */
	// private Candidate toCandidateWithoutIds(final Candidate candidate)
	// {
	// return candidate.withId(null).withParentId(null);
	// }

	@Test
	public void retrieveStockFrom()
	{
		{
			final CandidatesSegment earlierQuery = mkStockFromSegment(earlier);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatches(earlierQuery);
			assertThat(stockFrom.size(), is(2));

			// what we retrieved, but without the IDs. To be used to compare with our "originals"
			final List<Candidate> stockFromWithOutIds = stockFrom.stream().collect(Collectors.toList());

			assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}
		{
			final CandidatesSegment sameTimeQuery = mkStockFromSegment(now);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatches(sameTimeQuery);
			assertThat(stockFrom.size(), is(2));

			final List<Candidate> stockFromWithOutIds = stockFrom.stream().collect(Collectors.toList());
			assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}

		{
			final CandidatesSegment laterQuery = mkStockFromSegment(later);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatches(laterQuery);
			assertThat(stockFrom.size(), is(1));

			assertThat(stockFrom.contains(stockCandidate), is(false));
			assertThat(stockFrom.contains(laterStockCandidate), is(true));
		}
	}

	private CandidatesSegment mkStockUntilSegment(final Date date)
	{
		return CandidatesSegment.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(date).dateOperator(DateOperator.until)
				.build();
	}

	private CandidatesSegment mkStockFromSegment(final Date date)
	{
		return CandidatesSegment.builder()
				.type(Type.STOCK)
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.date(date).dateOperator(DateOperator.from)
				.build();
	}
}
