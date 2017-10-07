package de.metas.material.dispo;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialDescriptor;

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

	private I_AD_Org org;

	private Candidate stockCandidate;
	private Candidate laterStockCandidate;

	private CandidateRepository candidateRepository = new CandidateRepository();

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

		uom = newInstance(I_C_UOM.class);
		save(uom);

		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("11"))
				.date(now)
				.build();

		// this not-stock candidate needs to be ignored
		final Candidate someOtherCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.build();
		candidateRepository.addOrUpdateOverwriteStoredSeqNo(someOtherCandidate);

		stockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.build();
		stockCandidate = candidateRepository.addOrUpdateOverwriteStoredSeqNo(stockCandidate);

		final MaterialDescriptor laterMaterialDescrt = MaterialDescriptor.builder()
				.productId(product.getM_Product_ID())
				.warehouseId(warehouse.getM_Warehouse_ID())
				.quantity(new BigDecimal("10"))
				.date(later)
				.build();

		laterStockCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(laterMaterialDescrt)
				.build();
		laterStockCandidate = candidateRepository.addOrUpdateOverwriteStoredSeqNo(laterStockCandidate);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void addOrReplace_update()
	{
		// guard
		Assert.assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).isPresent(), is(true));
		Assert.assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).get(), is(stockCandidate));
		final List<Candidate> stockBeforeReplacement = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(mkStockFromSegment(now));
		Assert.assertThat(stockBeforeReplacement.size(), is(2));
		Assert.assertThat(stockBeforeReplacement.stream().collect(Collectors.toList()), contains(stockCandidate, laterStockCandidate));

		final Candidate replacementCandidate = stockCandidate.withQuantity(BigDecimal.ONE);
		candidateRepository.addOrUpdateOverwriteStoredSeqNo(replacementCandidate);

		Assert.assertThat(candidateRepository.retrieveLatestMatch(mkStockUntilSegment(now)).get(), is(replacementCandidate));
		final List<Candidate> stockAfterReplacement = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(mkStockFromSegment(now));
		Assert.assertThat(stockAfterReplacement.size(), is(2));
		Assert.assertThat(stockAfterReplacement.stream().collect(Collectors.toList()), contains(replacementCandidate, laterStockCandidate));
	}

	/**
	 * Verifies that {@link ProductionCandidateDetail} data is also persisted
	 */
	@Test
	public void addOrReplace_with_ProductionDetail()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.date(now)
				.productId(23)
				.quantity(BigDecimal.TEN)
				.warehouseId(50)
				.build();

		final Candidate productionCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.PRODUCTION)
				.materialDescr(materialDescr)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.reference(TableRecordReference.of("someTable", 40))
				.productionDetail(ProductionCandidateDetail.builder()
						.description("description")
						.plantId(60)
						.productBomLineId(70)
						.productPlanningId(80)
						.uomId(90)
						.ppOrderId(100)
						.ppOrderLineId(110)
						.ppOrderDocStatus("ppOrderDocStatus")
						.build())
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrUpdateOverwriteStoredSeqNo(productionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		Assert.assertThat(filtered.size(), is(1));

		final I_MD_Candidate record = filtered.get(0);
		Assert.assertThat(record.getMD_Candidate_ID(), is(addOrReplaceResult.getId()));
		Assert.assertThat(record.getMD_Candidate_SubType(), is(productionCandidate.getSubType().toString()));
		Assert.assertThat(record.getM_Product_ID(), is(productionCandidate.getMaterialDescr().getProductId()));

		final I_MD_Candidate_Prod_Detail productionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Prod_Detail.class).create().firstOnly(I_MD_Candidate_Prod_Detail.class);
		Assert.assertThat(productionDetailRecord, notNullValue());
		Assert.assertThat(productionDetailRecord.getDescription(), is("description"));
		Assert.assertThat(productionDetailRecord.getPP_Plant_ID(), is(60));
		Assert.assertThat(productionDetailRecord.getPP_Product_BOMLine_ID(), is(70));
		Assert.assertThat(productionDetailRecord.getPP_Product_Planning_ID(), is(80));
		Assert.assertThat(productionDetailRecord.getC_UOM_ID(), is(90));
		Assert.assertThat(productionDetailRecord.getPP_Order_ID(), is(100));
		Assert.assertThat(productionDetailRecord.getPP_Order_BOMLine_ID(), is(110));
		Assert.assertThat(productionDetailRecord.getPP_Order_DocStatus(), is("ppOrderDocStatus"));
	}

	@Test
	public void retrieve_with_ProductionDetail()
	{
		perform_retrieve_with_ProductionDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_ProductionDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(24);
		record.setM_Warehouse_ID(51);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(record);

		final I_MD_Candidate_Prod_Detail productionDetailRecord = newInstance(I_MD_Candidate_Prod_Detail.class);
		productionDetailRecord.setDescription("description1");
		productionDetailRecord.setPP_Plant_ID(61);
		productionDetailRecord.setPP_Product_BOMLine_ID(71);
		productionDetailRecord.setPP_Product_Planning_ID(81);
		productionDetailRecord.setC_UOM_ID(91);
		productionDetailRecord.setMD_Candidate(record);
		productionDetailRecord.setPP_Order_ID(101);
		productionDetailRecord.setPP_Order_BOMLine_ID(111);
		productionDetailRecord.setPP_Order_DocStatus("ppOrderDocStatus1");
		save(productionDetailRecord);

		final Candidate cand = candidateRepository.retrieve(record.getMD_Candidate_ID());
		Assert.assertThat(cand, notNullValue());
		Assert.assertThat(cand.getMaterialDescr().getProductId(), is(24));
		Assert.assertThat(cand.getMaterialDescr().getWarehouseId(), is(51));
		Assert.assertThat(cand.getMaterialDescr().getDate(), is(now));
		Assert.assertThat(cand.getProductionDetail(), notNullValue());
		Assert.assertThat(cand.getProductionDetail().getDescription(), is("description1"));
		Assert.assertThat(cand.getProductionDetail().getProductBomLineId(), is(71));
		Assert.assertThat(cand.getProductionDetail().getProductPlanningId(), is(81));
		Assert.assertThat(cand.getProductionDetail().getUomId(), is(91));
		Assert.assertThat(cand.getProductionDetail().getPpOrderId(), is(101));
		Assert.assertThat(cand.getProductionDetail().getPpOrderLineId(), is(111));
		Assert.assertThat(cand.getProductionDetail().getPpOrderDocStatus(), is("ppOrderDocStatus1"));

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_ProductionDetail()} works.
	 */
	@Test
	public void retrieve_with_ProductionDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_ProductionDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a demandDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(24);
		otherRecord.setM_Warehouse_ID(51);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(otherRecord);

		final Optional<I_MD_Candidate> expectedRecordWithProdDetails = candidateRepository.retrieveExact(cand);
		Assert.assertThat(expectedRecordWithProdDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithProdDetails.get().getMD_Candidate_ID(), is(record.getMD_Candidate_ID()));

		final Optional<I_MD_Candidate> expectedRecordWithoutProdDetails = candidateRepository.retrieveExact(cand.withProductionDetail(null));
		Assert.assertThat(expectedRecordWithoutProdDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithoutProdDetails.get().getMD_Candidate_ID(), is(otherRecord.getMD_Candidate_ID()));
	}

	/**
	 * Verifies that {@link DistributionCandidateDetail} data is also persisted
	 */
	@Test
	public void addOrReplace_with_DistributionDetail()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.date(now)
				.productId(23)
				.quantity(BigDecimal.TEN)
				.warehouseId(50)
				.build();

		final Candidate distributionCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.DISTRIBUTION)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.reference(TableRecordReference.of("someTable", 40))
				.materialDescr(materialDescr)
				.distributionDetail(DistributionCandidateDetail.builder()
						.productPlanningId(80)
						.plantId(85)
						.networkDistributionLineId(90)
						.ddOrderId(100)
						.ddOrderLineId(110)
						.shipperId(120)
						.ddOrderDocStatus("ddOrderDocStatus")
						.build())
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrUpdateOverwriteStoredSeqNo(distributionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		Assert.assertThat(filtered.size(), is(1));

		final I_MD_Candidate record = filtered.get(0);
		Assert.assertThat(record.getMD_Candidate_ID(), is(addOrReplaceResult.getId()));
		Assert.assertThat(record.getMD_Candidate_SubType(), is(distributionCandidate.getSubType().toString()));
		Assert.assertThat(record.getM_Product_ID(), is(distributionCandidate.getMaterialDescr().getProductId()));

		final I_MD_Candidate_Dist_Detail distributionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Dist_Detail.class).create().firstOnly(I_MD_Candidate_Dist_Detail.class);
		Assert.assertThat(distributionDetailRecord, notNullValue());
		Assert.assertThat(distributionDetailRecord.getPP_Product_Planning_ID(), is(80));
		Assert.assertThat(distributionDetailRecord.getPP_Plant_ID(), is(85));
		Assert.assertThat(distributionDetailRecord.getDD_NetworkDistributionLine_ID(), is(90));
		Assert.assertThat(distributionDetailRecord.getDD_Order_ID(), is(100));
		Assert.assertThat(distributionDetailRecord.getDD_OrderLine_ID(), is(110));
		Assert.assertThat(distributionDetailRecord.getM_Shipper_ID(), is(120));
		Assert.assertThat(distributionDetailRecord.getDD_Order_DocStatus(), is("ddOrderDocStatus"));
	}

	@Test
	public void retrieve_with_DistributionDetail()
	{
		perform_retrieve_with_DistributionDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_DistributionDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(24);
		record.setM_Warehouse_ID(51);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_DISTRIBUTION);
		save(record);

		final I_MD_Candidate_Dist_Detail distributionDetailRecord = newInstance(I_MD_Candidate_Dist_Detail.class);
		distributionDetailRecord.setDD_NetworkDistributionLine_ID(71);
		distributionDetailRecord.setPP_Product_Planning_ID(81);
		distributionDetailRecord.setMD_Candidate(record);
		distributionDetailRecord.setDD_Order_ID(101);
		distributionDetailRecord.setDD_OrderLine_ID(111);
		distributionDetailRecord.setDD_Order_DocStatus("ddOrderDocStatus1");
		distributionDetailRecord.setM_Shipper_ID(121);
		save(distributionDetailRecord);

		final Candidate cand = candidateRepository.retrieve(record.getMD_Candidate_ID());
		Assert.assertThat(cand, notNullValue());
		Assert.assertThat(cand.getMaterialDescr().getProductId(), is(24));
		Assert.assertThat(cand.getMaterialDescr().getWarehouseId(), is(51));
		Assert.assertThat(cand.getMaterialDescr().getDate(), is(now));
		Assert.assertThat(cand.getProductionDetail(), nullValue());
		Assert.assertThat(cand.getDistributionDetail(), notNullValue());
		Assert.assertThat(cand.getDistributionDetail().getNetworkDistributionLineId(), is(71));
		Assert.assertThat(cand.getDistributionDetail().getProductPlanningId(), is(81));
		Assert.assertThat(cand.getDistributionDetail().getDdOrderId(), is(101));
		Assert.assertThat(cand.getDistributionDetail().getDdOrderLineId(), is(111));
		Assert.assertThat(cand.getDistributionDetail().getShipperId(), is(121));
		Assert.assertThat(cand.getDistributionDetail().getDdOrderDocStatus(), is("ddOrderDocStatus1"));

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_DistributionDetail()} works.
	 */
	@Test
	public void retrieve_with_DistributionDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_ProductionDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a demandDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(24);
		otherRecord.setM_Warehouse_ID(51);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_DISTRIBUTION);
		save(otherRecord);

		final Optional<I_MD_Candidate> expectedRecordWithDistDetails = candidateRepository.retrieveExact(cand);
		Assert.assertThat(expectedRecordWithDistDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithDistDetails.get().getMD_Candidate_ID(), is(record.getMD_Candidate_ID()));

		final Optional<I_MD_Candidate> expectedRecordWithoutDistDetails = candidateRepository.retrieveExact(cand.withProductionDetail(null));
		Assert.assertThat(expectedRecordWithoutDistDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithoutDistDetails.get().getMD_Candidate_ID(), is(otherRecord.getMD_Candidate_ID()));
	}

	/**
	 * Verifies that also the candidate's {@link DemandCandidateDetailTests} is persisted.
	 */
	@Test
	public void addOrReplace_with_DemandDetail()
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.date(now)
				.productId(23)
				.quantity(BigDecimal.TEN)
				.warehouseId(50)
				.build();
		
		final Candidate productionCandidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.SHIPMENT)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescr)
				.reference(TableRecordReference.of("someTable", 40))
				.demandDetail(DemandCandidateDetail.forOrderLineId(61))
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrUpdateOverwriteStoredSeqNo(productionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		Assert.assertThat(filtered.size(), is(1));

		final I_MD_Candidate record = filtered.get(0);
		Assert.assertThat(record.getMD_Candidate_ID(), is(addOrReplaceResult.getId()));
		Assert.assertThat(record.getMD_Candidate_SubType(), is(productionCandidate.getSubType().toString()));
		Assert.assertThat(record.getM_Product_ID(), is(productionCandidate.getMaterialDescr().getProductId()));

		final I_MD_Candidate_Demand_Detail demandDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Demand_Detail.class).create().firstOnly(I_MD_Candidate_Demand_Detail.class);
		Assert.assertThat(demandDetailRecord, notNullValue());
		Assert.assertThat(demandDetailRecord.getC_OrderLine_ID(), is(61));
	}

	@Test
	public void retrieve_with_DemandDetail()
	{
		perform_retrieve_with_DemandDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_DemandDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(24);
		record.setM_Warehouse_ID(51);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(record);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(record);
		demandDetailRecord.setC_OrderLine_ID(62);
		demandDetailRecord.setM_ForecastLine_ID(72); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(demandDetailRecord);

		final Candidate cand = candidateRepository.retrieve(record.getMD_Candidate_ID());
		assertThat(cand).isNotNull();
		assertThat(cand.getMaterialDescr().getProductId()).isEqualTo(24);
		assertThat(cand.getMaterialDescr().getWarehouseId()).isEqualTo(51);
		assertThat(cand.getMaterialDescr().getDate()).isEqualTo(now);
		assertThat(cand.getProductionDetail()).isNull();
		assertThat(cand.getDemandDetail().getOrderLineId()).isEqualTo(62);
		assertThat(cand.getDemandDetail().getForecastLineId()).isEqualTo(72);

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_DemandDetail()} works
	 */
	@Test
	public void retrieve_with_DemandDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_DemandDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a demandDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(24);
		otherRecord.setM_Warehouse_ID(51);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(otherRecord);

		final I_MD_Candidate_Demand_Detail otherDemandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		otherDemandDetailRecord.setMD_Candidate(otherRecord);
		otherDemandDetailRecord.setC_OrderLine_ID(64);
		otherDemandDetailRecord.setM_ForecastLine_ID(74); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(otherDemandDetailRecord);

		final Optional<I_MD_Candidate> expectedRecordWithDemandDetails = candidateRepository.retrieveExact(cand);
		Assert.assertThat(expectedRecordWithDemandDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithDemandDetails.get().getMD_Candidate_ID(), is(record.getMD_Candidate_ID()));

		final Optional<I_MD_Candidate> expectedRecordWithoutDemandDetails = candidateRepository
				.retrieveExact(cand.withDemandDetail(DemandCandidateDetail.forForecastLineId(74)));

		Assert.assertThat(expectedRecordWithoutDemandDetails.isPresent(), is(true));
		Assert.assertThat(expectedRecordWithoutDemandDetails.get().getMD_Candidate_ID(), is(otherRecord.getMD_Candidate_ID()));
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

		final Candidate result1 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId);
		// result1 was assigned an id and a groupId
		Assert.assertThat(result1.getId(), greaterThan(0));
		Assert.assertThat(result1.getGroupId(), is(result1.getId()));

		final Candidate candidateWithGroupId = candidateWithOutGroupId
				.withDate(TimeUtil.addMinutes(later, 2)) // pick a different time from the other candidates
				.withGroupId(result1.getGroupId());

		final Candidate result2 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithGroupId);
		// result2 also has id & groupId, but its ID is unique whereas its groupId is the same as result1's groupId
		Assert.assertThat(result2.getId(), greaterThan(0));
		Assert.assertThat(result2.getGroupId(), not(is(result2.getId())));
		Assert.assertThat(result2.getGroupId(), is(result1.getGroupId()));

		final I_MD_Candidate result1Record = InterfaceWrapperHelper.create(Env.getCtx(), result1.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		Assert.assertThat(result1Record.getMD_Candidate_ID(), is(result1.getId()));
		Assert.assertThat(result1Record.getMD_Candidate_GroupId(), is(result1.getGroupId()));

		final I_MD_Candidate result2Record = InterfaceWrapperHelper.create(Env.getCtx(), result2.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		Assert.assertThat(result2Record.getMD_Candidate_ID(), is(result2.getId()));
		Assert.assertThat(result2Record.getMD_Candidate_GroupId(), is(result2.getGroupId()));
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

		final Candidate result1 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId);
		Assert.assertThat(result1.getId(), greaterThan(0));
		Assert.assertThat(result1.getGroupId(), greaterThan(0));

		final I_MD_Candidate result1Record = InterfaceWrapperHelper.create(Env.getCtx(), result1.getId(), I_MD_Candidate.class, ITrx.TRXNAME_None);
		Assert.assertThat(result1Record.getMD_Candidate_ID(), is(result1.getId()));
		Assert.assertThat(result1Record.getMD_Candidate_GroupId(), is(result1.getGroupId()));
	}

	/**
	 * Verifies that {@link CandidateRepository#retrieveStockAt(CandidatesSegment)} returns the oldest stock candidate with a date before the given {@link CandidatesSegment}'s date
	 */
	@Test
	public void retrieveStockAt()
	{
		final CandidatesSegment earlierQuery = mkStockUntilSegment(earlier);
		final Optional<Candidate> earlierStock = candidateRepository.retrieveLatestMatch(earlierQuery);
		Assert.assertThat(earlierStock.isPresent(), is(false));

		final CandidatesSegment sameTimeQuery = mkStockUntilSegment(now);
		final Optional<Candidate> sameTimeStock = candidateRepository.retrieveLatestMatch(sameTimeQuery);
		Assert.assertThat(sameTimeStock.isPresent(), is(true));
		Assert.assertThat(sameTimeStock.get(), is(stockCandidate));

		final CandidatesSegment laterQuery = mkStockUntilSegment(later);
		final Optional<Candidate> laterStock = candidateRepository.retrieveLatestMatch(laterQuery);
		Assert.assertThat(laterStock.isPresent(), is(true));
		Assert.assertThat(laterStock.get(), is(laterStockCandidate));
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

	// TODO: make sure that AD_Client_ID is set correctly from AD_Org

	@Test
	public void retrieveStockFrom()
	{
		{
			final CandidatesSegment earlierQuery = mkStockFromSegment(earlier);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(earlierQuery);
			Assert.assertThat(stockFrom.size(), is(2));

			// what we retrieved, but without the IDs. To be used to compare with our "originals"
			final List<Candidate> stockFromWithOutIds = stockFrom.stream().collect(Collectors.toList());

			Assert.assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			Assert.assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}
		{
			final CandidatesSegment sameTimeQuery = mkStockFromSegment(now);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(sameTimeQuery);
			Assert.assertThat(stockFrom.size(), is(2));

			final List<Candidate> stockFromWithOutIds = stockFrom.stream().collect(Collectors.toList());
			Assert.assertThat(stockFromWithOutIds.contains(stockCandidate), is(true));
			Assert.assertThat(stockFromWithOutIds.contains(laterStockCandidate), is(true));
		}

		{
			final CandidatesSegment laterQuery = mkStockFromSegment(later);

			final List<Candidate> stockFrom = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(laterQuery);
			Assert.assertThat(stockFrom.size(), is(1));

			Assert.assertThat(stockFrom.contains(stockCandidate), is(false));
			Assert.assertThat(stockFrom.contains(laterStockCandidate), is(true));
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
