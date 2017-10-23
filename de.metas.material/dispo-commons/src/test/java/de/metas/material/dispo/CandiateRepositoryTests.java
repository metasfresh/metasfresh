package de.metas.material.dispo;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
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

import de.metas.material.dispo.CandidateSpecification.SubType;
import de.metas.material.dispo.CandidateSpecification.Type;
import de.metas.material.dispo.CandidatesQuery.DateOperator;
import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.dispo.candidate.DistributionDetail;
import de.metas.material.dispo.candidate.ProductionDetail;
import de.metas.material.dispo.candidate.TransactionDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
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

	private static final int TRANSACTION_ID = 60;

	private static final int WAHREHOUSE_ID = 51;

	private static final int PRODUCT_ID = 24;

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

	@Test
	public void addOrUpdateOverwriteStoredSeqNo_returns_equal_candidate()
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(now)
				.productId(23)
				.quantity(BigDecimal.TEN)
				.warehouseId(50)
				.build();

		final Candidate originalCandidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(org.getAD_Client_ID())
				.orgId(org.getAD_Org_ID())
				.materialDescr(materialDescriptor)
				.build();
		final Candidate candidateReturnedfromRepo = candidateRepository.addOrUpdateOverwriteStoredSeqNo(originalCandidate);
		assertThat(candidateReturnedfromRepo
				.withId(0)
				.withGroupId(0)
				.withSeqNo(0))
						.as("the candidate reeturned from the repository shall equal the orgininal, apart from id, groupId and seqNo")
						.isEqualTo(originalCandidate);
	}

	@Test
	public void retrieveLatestMatch_returns_equal_candidate()
	{
		final CandidatesQuery query = CandidatesQuery.fromCandidate(laterStockCandidate);
		final Candidate candidate = candidateRepository.retrieveLatestMatchOrNull(query);
		assertThat(candidate).isNotNull();

		assertThat(candidate).isEqualTo(laterStockCandidate);
	}

	@Test
	public void fromCandidateRecord_record_to_candidate()
	{
		final Timestamp dateProjected = SystemTime.asTimestamp();
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(dateProjected);
		candidateRecord.setM_Warehouse_ID(10);
		candidateRecord.setM_Product_ID(20);
		candidateRecord.setQty(BigDecimal.TEN);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		save(candidateRecord);

		final Optional<Candidate> result = candidateRepository.fromCandidateRecord(candidateRecord);

		assertThat(result.isPresent());
		final Candidate candidate = result.get();
		assertThat(candidate.getParentId()).isEqualTo(0);
		assertThat(candidate.getDate()).isEqualTo(dateProjected);
	}

	@Test
	public void fromCandidateRecord_record_and_transactiondetails_to_candidate()
	{
		final Timestamp dateProjected = SystemTime.asTimestamp();
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(dateProjected);
		candidateRecord.setM_Warehouse_ID(10);
		candidateRecord.setM_Product_ID(20);
		candidateRecord.setQty(BigDecimal.TEN);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		save(candidateRecord);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = newInstance(I_MD_Candidate_Transaction_Detail.class);
		transactionDetailRecord.setMD_Candidate(candidateRecord);
		transactionDetailRecord.setM_Transaction_ID(30);
		transactionDetailRecord.setMovementQty(BigDecimal.TEN);
		save(transactionDetailRecord);

		final Optional<Candidate> result = candidateRepository.fromCandidateRecord(candidateRecord);

		assertThat(result.isPresent());
		final Candidate candidate = result.get();
		assertThat(candidate.getParentId()).isEqualTo(0);
		assertThat(candidate.getDate()).isEqualTo(dateProjected);
		assertThat(candidate.getTransactionDetail()).isNotNull();
		assertThat(candidate.getTransactionDetail().getQuantity()).isEqualByComparingTo("10");
		assertThat(candidate.getTransactionDetail().getTransactionId()).isEqualByComparingTo(30);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void addOrReplace_update()
	{
		// guard
		assertThat(candidateRepository.retrieveLatestMatchOrNull(mkQueryForStockUntilDate(now))).isNotNull();
		assertThat(candidateRepository.retrieveLatestMatchOrNull(mkQueryForStockUntilDate(now))).isEqualTo(stockCandidate);
		final List<Candidate> stockBeforeReplacement = candidateRepository.retrieveOrderedByDateAndSeqNo(mkQueryForStockFromDate(now));
		assertThat(stockBeforeReplacement).containsOnly(stockCandidate, laterStockCandidate);

		final Candidate replacementCandidate = stockCandidate.withQuantity(BigDecimal.ONE);
		candidateRepository.addOrUpdateOverwriteStoredSeqNo(replacementCandidate);

		assertThat(candidateRepository.retrieveLatestMatchOrNull(mkQueryForStockUntilDate(now))).isEqualTo(replacementCandidate);
		final List<Candidate> stockAfterReplacement = candidateRepository.retrieveOrderedByDateAndSeqNo(mkQueryForStockFromDate(now));
		assertThat(stockAfterReplacement).containsOnly(replacementCandidate, laterStockCandidate);
	}

	/**
	 * Verifies that {@link ProductionDetail} data is also persisted
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
				.productionDetail(ProductionDetail.builder()
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
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId());
		assertThat(record.getMD_Candidate_SubType()).isEqualTo(productionCandidate.getSubType().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescr().getProductId());

		final I_MD_Candidate_Prod_Detail productionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Prod_Detail.class).create().firstOnly(I_MD_Candidate_Prod_Detail.class);
		assertThat(productionDetailRecord).isNotNull();
		assertThat(productionDetailRecord.getDescription()).isEqualTo("description");
		assertThat(productionDetailRecord.getPP_Plant_ID()).isEqualTo(60);
		assertThat(productionDetailRecord.getPP_Product_BOMLine_ID()).isEqualTo(70);
		assertThat(productionDetailRecord.getPP_Product_Planning_ID()).isEqualTo(80);
		assertThat(productionDetailRecord.getC_UOM_ID()).isEqualTo(90);
		assertThat(productionDetailRecord.getPP_Order_ID()).isEqualTo(100);
		assertThat(productionDetailRecord.getPP_Order_BOMLine_ID()).isEqualTo(110);
		assertThat(productionDetailRecord.getPP_Order_DocStatus()).isEqualTo("ppOrderDocStatus");
	}

	@Test
	public void retrieve_with_ProductionDetail()
	{
		perform_retrieve_with_ProductionDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_ProductionDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(PRODUCT_ID);
		record.setM_Warehouse_ID(WAHREHOUSE_ID);
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

		final Candidate cand = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(record.getMD_Candidate_ID()));
		assertThat(cand).isNotNull();
		assertThat(cand.getMaterialDescr().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescr().getWarehouseId()).isEqualTo(WAHREHOUSE_ID);
		assertThat(cand.getMaterialDescr().getDate()).isEqualTo(now);
		assertThat(cand.getProductionDetail()).isNotNull();
		assertThat(cand.getProductionDetail().getDescription()).isEqualTo("description1");
		assertThat(cand.getProductionDetail().getProductBomLineId()).isEqualTo(71);
		assertThat(cand.getProductionDetail().getProductPlanningId()).isEqualTo(81);
		assertThat(cand.getProductionDetail().getUomId()).isEqualTo(91);
		assertThat(cand.getProductionDetail().getPpOrderId()).isEqualTo(101);
		assertThat(cand.getProductionDetail().getPpOrderLineId()).isEqualTo(111);
		assertThat(cand.getProductionDetail().getPpOrderDocStatus()).isEqualTo("ppOrderDocStatus1");

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_ProductionDetail()} works.
	 */
	@Test
	public void retrieveExact_with_ProductionDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_ProductionDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a proeductionDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(PRODUCT_ID);
		otherRecord.setM_Warehouse_ID(WAHREHOUSE_ID);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(otherRecord);

		final Candidate expectedRecordWithProdDetails = candidateRepository
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand));
		assertThat(expectedRecordWithProdDetails).isNotNull();
		assertThat(expectedRecordWithProdDetails.getId()).isEqualTo(record.getMD_Candidate_ID());

		final CandidatesQuery querqWithoutProdDetails = CandidatesQuery
				.fromCandidate(cand)
				.withId(0)
				.withProductionDetail(CandidatesQuery.NO_PRODUCTION_DETAIL);
		final Candidate expectedRecordWithoutProdDetails = candidateRepository
				.retrieveLatestMatchOrNull(querqWithoutProdDetails);
		assertThat(expectedRecordWithoutProdDetails).isNotNull();
		assertThat(expectedRecordWithoutProdDetails.getId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	/**
	 * Verifies that {@link DistributionDetail} data is also persisted
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_with_DistributionDetail()
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
				.materialDescr(materialDescr)
				.distributionDetail(DistributionDetail.builder()
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
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId());
		assertThat(record.getMD_Candidate_SubType()).isEqualTo(distributionCandidate.getSubType().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(distributionCandidate.getMaterialDescr().getProductId());

		final I_MD_Candidate_Dist_Detail distributionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Dist_Detail.class).create().firstOnly(I_MD_Candidate_Dist_Detail.class);
		assertThat(distributionDetailRecord).isNotNull();
		assertThat(distributionDetailRecord.getPP_Product_Planning_ID()).isEqualTo(80);
		assertThat(distributionDetailRecord.getPP_Plant_ID()).isEqualTo(85);
		assertThat(distributionDetailRecord.getDD_NetworkDistributionLine_ID()).isEqualTo(90);
		assertThat(distributionDetailRecord.getDD_Order_ID()).isEqualTo(100);
		assertThat(distributionDetailRecord.getDD_OrderLine_ID()).isEqualTo(110);
		assertThat(distributionDetailRecord.getM_Shipper_ID()).isEqualTo(120);
		assertThat(distributionDetailRecord.getDD_Order_DocStatus()).isEqualTo("ddOrderDocStatus");
	}

	@Test
	public void retrieve_with_DistributionDetail()
	{
		perform_retrieve_with_DistributionDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_DistributionDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(PRODUCT_ID);
		record.setM_Warehouse_ID(WAHREHOUSE_ID);
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

		final Candidate cand = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(record.getMD_Candidate_ID()));
		assertThat(cand).isNotNull();
		assertThat(cand.getMaterialDescr().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescr().getWarehouseId()).isEqualTo(WAHREHOUSE_ID);
		assertThat(cand.getMaterialDescr().getDate()).isEqualTo(now);
		assertThat(cand.getProductionDetail()).isNull();
		assertThat(cand.getDistributionDetail()).isNotNull();
		assertThat(cand.getDistributionDetail().getNetworkDistributionLineId()).isEqualTo(71);
		assertThat(cand.getDistributionDetail().getProductPlanningId()).isEqualTo(81);
		assertThat(cand.getDistributionDetail().getDdOrderId()).isEqualTo(101);
		assertThat(cand.getDistributionDetail().getDdOrderLineId()).isEqualTo(111);
		assertThat(cand.getDistributionDetail().getShipperId()).isEqualTo(121);
		assertThat(cand.getDistributionDetail().getDdOrderDocStatus()).isEqualTo("ddOrderDocStatus1");

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_DistributionDetail()} works.
	 */
	@Test
	public void retrieveExact_with_DistributionDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_DistributionDetail();
		final Candidate cand = pair.getLeft();
		assertThat(cand.getDistributionDetail()).isNotNull();

		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a distributionDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(PRODUCT_ID);
		otherRecord.setM_Warehouse_ID(WAHREHOUSE_ID);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_DISTRIBUTION);
		save(otherRecord);

		final Candidate expectedRecordWithDistDetails = candidateRepository
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand));
		assertThat(expectedRecordWithDistDetails).isNotNull();
		assertThat(expectedRecordWithDistDetails.getDistributionDetail()).isNotNull();
		assertThat(expectedRecordWithDistDetails.getId()).isEqualTo(record.getMD_Candidate_ID());

		final CandidatesQuery withoutdistDetailsQuery = CandidatesQuery
				.fromCandidate(cand)
				.withId(0)
				.withDistributionDetail(CandidatesQuery.NO_DISTRIBUTION_DETAIL);
		final Candidate expectedRecordWithoutDistDetails = candidateRepository
				.retrieveLatestMatchOrNull(withoutdistDetailsQuery);

		assertThat(expectedRecordWithoutDistDetails).isNotNull();
		assertThat(expectedRecordWithoutDistDetails.getDistributionDetail()).isNull();
		assertThat(expectedRecordWithoutDistDetails.getId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	@Test
	public void retrieve_with_TransactionDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(PRODUCT_ID);
		record.setM_Warehouse_ID(WAHREHOUSE_ID);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_DECREASE);
		save(record);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = newInstance(I_MD_Candidate_Transaction_Detail.class);
		transactionDetailRecord.setMD_Candidate(record);
		transactionDetailRecord.setM_Transaction_ID(TRANSACTION_ID);
		transactionDetailRecord.setMovementQty(BigDecimal.TEN);
		save(transactionDetailRecord);

		final CandidatesQuery query = CandidatesQuery
				.builder().transactionDetail(TransactionDetail.forQuery(TRANSACTION_ID))
				.build();

		final List<Candidate> expectedCandidates = candidateRepository.retrieveOrderedByDateAndSeqNo(query);
		assertThat(expectedCandidates).hasSize(1);
		assertThat(expectedCandidates.get(0).getId()).isEqualTo(record.getMD_Candidate_ID());
		assertThat(expectedCandidates.get(0).getTransactionDetail().getTransactionId()).isEqualTo(TRANSACTION_ID);
		assertThat(expectedCandidates.get(0).getTransactionDetail().getQuantity()).isEqualByComparingTo("10");
	}

	@Test
	public void addOrUpdateOverwriteStoredSeqNo_with_DemandDetail()
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
				.demandDetail(DemandDetail.forOrderLineIdOrNull(61))
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrUpdateOverwriteStoredSeqNo(productionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId());
		assertThat(record.getMD_Candidate_SubType()).isEqualTo(productionCandidate.getSubType().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescr().getProductId());

		final I_MD_Candidate_Demand_Detail demandDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Demand_Detail.class).create().firstOnly(I_MD_Candidate_Demand_Detail.class);
		assertThat(demandDetailRecord).isNotNull();
		assertThat(demandDetailRecord.getC_OrderLine_ID()).isEqualTo(61);
	}

	@Test
	public void retrieve_with_id_and_demandDetail()
	{
		perform_retrieve_with_id_and_demandDetail();
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_DemandDetail()} works
	 */
	@Test
	public void retrieveExact_with_demandDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_id_and_demandDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a demandDetailRecord
		final I_MD_Candidate otherRecord = newInstance(I_MD_Candidate.class);
		otherRecord.setM_Product_ID(PRODUCT_ID);
		otherRecord.setM_Warehouse_ID(WAHREHOUSE_ID);
		otherRecord.setDateProjected(new Timestamp(now.getTime()));
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(otherRecord);

		final I_MD_Candidate_Demand_Detail otherDemandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		otherDemandDetailRecord.setMD_Candidate(otherRecord);
		otherDemandDetailRecord.setC_OrderLine_ID(64);
		otherDemandDetailRecord.setM_ForecastLine_ID(74); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(otherDemandDetailRecord);

		final Candidate expectedRecordWithDemandDetails = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand));
		assertThat(expectedRecordWithDemandDetails).isNotNull();
		assertThat(expectedRecordWithDemandDetails.getId()).isEqualTo(record.getMD_Candidate_ID());

		final Candidate expectedRecordWithoutDemandDetails = candidateRepository
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand.withId(0).withDemandDetail(DemandDetail.forForecastLineId(74))));

		assertThat(expectedRecordWithoutDemandDetails).isNotNull();
		assertThat(expectedRecordWithoutDemandDetails.getId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_id_and_demandDetail()
	{
		final I_MD_Candidate record = newInstance(I_MD_Candidate.class);
		record.setM_Product_ID(PRODUCT_ID);
		record.setM_Warehouse_ID(WAHREHOUSE_ID);
		record.setDateProjected(new Timestamp(now.getTime()));
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_SubType(X_MD_Candidate.MD_CANDIDATE_SUBTYPE_PRODUCTION);
		save(record);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(record);
		demandDetailRecord.setC_OrderLine_ID(62);
		demandDetailRecord.setM_ForecastLine_ID(72); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(demandDetailRecord);

		final Candidate cand = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(record.getMD_Candidate_ID()));
		assertThat(cand).isNotNull();
		assertThat(cand.getId()).isEqualTo(record.getMD_Candidate_ID());
		assertThat(cand.getMaterialDescr().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescr().getWarehouseId()).isEqualTo(WAHREHOUSE_ID);
		assertThat(cand.getMaterialDescr().getDate()).isEqualTo(now);
		assertThat(cand.getProductionDetail()).isNull();
		assertThat(cand.getDemandDetail().getOrderLineId()).isEqualTo(62);
		assertThat(cand.getDemandDetail().getForecastLineId()).isEqualTo(72);

		return ImmutablePair.of(cand, record);
	}

	/**
	 * Verifies that if a candidate with a groupID is persisted, then that candidate's groupId is also persisted.
	 * And if the respective candidate does no yet have a groupId, then the persisted candidate's Id is assigned to be its groupId.
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_nonStockCandidate_receives_groupId()
	{
		final Candidate candidateWithOutGroupId = stockCandidate
				.withType(Type.DEMAND)
				.withDate(TimeUtil.addMinutes(later, 1)) // pick a different time from the other candidates
				.withGroupId(-1);

		final Candidate result1 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId);
		// result1 was assigned an id and a groupId
		assertThat(result1.getId()).isGreaterThan(0);
		assertThat(result1.getGroupId()).isEqualTo(result1.getId());

		final Candidate candidateWithGroupId = candidateWithOutGroupId
				.withDate(TimeUtil.addMinutes(later, 2)) // pick a different time from the other candidates
				.withGroupId(result1.getGroupId());

		final Candidate result2 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithGroupId);
		// result2 also has id & groupId, but its ID is unique whereas its groupId is the same as result1's groupId
		assertThat(result2.getId()).isGreaterThan(0);
		assertThat(result2.getGroupId()).isNotEqualTo(result2.getId());
		assertThat(result2.getGroupId()).isEqualTo(result1.getGroupId());

		final I_MD_Candidate result1Record = load(result1.getId(), I_MD_Candidate.class);
		assertThat(result1Record.getMD_Candidate_ID()).isEqualTo(result1.getId());
		assertThat(result1Record.getMD_Candidate_GroupId()).isEqualTo(result1.getGroupId());

		final I_MD_Candidate result2Record = load(result2.getId(), I_MD_Candidate.class);
		assertThat(result2Record.getMD_Candidate_ID()).isEqualTo(result2.getId());
		assertThat(result2Record.getMD_Candidate_GroupId()).isEqualTo(result2.getGroupId());
	}

	/**
	 * Verifies that candidates with type {@link Type#STOCK} do receive a groupId
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_stockCandidate_receives_groupId()
	{
		final Candidate candidateWithOutGroupId = stockCandidate
				.withDate(TimeUtil.addMinutes(later, 1)) // pick a different time from the other candidates
				.withGroupId(-1);

		final Candidate result1 = candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId);
		assertThat(result1.getId()).isGreaterThan(0);
		assertThat(result1.getGroupId()).isGreaterThan(0);

		final I_MD_Candidate result1Record = load(result1.getId(), I_MD_Candidate.class);
		assertThat(result1Record.getMD_Candidate_ID()).isEqualTo(result1.getId());
		assertThat(result1Record.getMD_Candidate_GroupId()).isEqualTo(result1.getGroupId());
	}

	@Test
	public void retrieveLatestMatch_until_earlier_date()
	{
		final CandidatesQuery earlierQuery = mkQueryForStockUntilDate(earlier);
		final Candidate earlierStock = candidateRepository.retrieveLatestMatchOrNull(earlierQuery);
		assertThat(earlierStock).isNull();
	}

	@Test
	public void retrieveLatestMatch_until_now_date()
	{
		final CandidatesQuery sameTimeQuery = mkQueryForStockUntilDate(now);
		final Candidate sameTimeStock = candidateRepository.retrieveLatestMatchOrNull(sameTimeQuery);
		assertThat(sameTimeStock).isNotNull();
		assertThat(sameTimeStock).isEqualTo(stockCandidate);
	}

	@Test
	public void retrieveLatestMatch_until_later_date()
	{
		final CandidatesQuery laterQuery = mkQueryForStockUntilDate(later);
		final Candidate laterStock = candidateRepository.retrieveLatestMatchOrNull(laterQuery);
		assertThat(laterStock).isNotNull();
		assertThat(laterStock).isEqualTo(laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_from_earlier_date()
	{
		final CandidatesQuery earlierQuery = mkQueryForStockFromDate(earlier);

		final List<Candidate> stockFrom = candidateRepository.retrieveOrderedByDateAndSeqNo(earlierQuery);

		assertThat(stockFrom).containsExactly(stockCandidate, laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_DateOperator_from_now_date()
	{
		final CandidatesQuery sameTimeQuery = mkQueryForStockFromDate(now);

		final List<Candidate> stockFrom = candidateRepository.retrieveOrderedByDateAndSeqNo(sameTimeQuery);

		assertThat(stockFrom).containsExactly(stockCandidate, laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_DateOperator_from_later_date()
	{
		final CandidatesQuery laterQuery = mkQueryForStockFromDate(later);

		final List<Candidate> stockFrom = candidateRepository.retrieveOrderedByDateAndSeqNo(laterQuery);

		assertThat(stockFrom).containsExactly(laterStockCandidate);
	}

	private CandidatesQuery mkQueryForStockUntilDate(final Date date)
	{
		return CandidatesQuery.builder()
				.type(Type.STOCK)
				.materialDescr(MaterialDescriptor.builderForQuery()
						.productId(product.getM_Product_ID())
						.warehouseId(warehouse.getM_Warehouse_ID())
						.date(date).build())
				.dateOperator(DateOperator.UNTIL)
				.build();
	}

	private CandidatesQuery mkQueryForStockFromDate(final Date date)
	{
		return CandidatesQuery.builder()
				.type(Type.STOCK)
				.materialDescr(MaterialDescriptor.builderForQuery()
						.productId(product.getM_Product_ID())
						.warehouseId(warehouse.getM_Warehouse_ID())
						.date(date).build())
				.dateOperator(DateOperator.FROM)
				.build();
	}

	@Test
	public void retrieveMatchesOrderByDateAndSeqNo_only_by_warehouse_id()
	{
		final int warehouseId = 20;
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(warehouseId);
		createCandidateRecordWithWarehouseId(30);

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescr(MaterialDescriptor.builderForQuery()
						.warehouseId(warehouseId)
						.build())
				.build();
		final List<Candidate> result = candidateRepository.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getWarehouseId()).isEqualTo(warehouseId);
	}

	@Test
	public void retrieveMatches_by_shipmentScheduleId()
	{
		final I_MD_Candidate candidateRecord = createCandiateRecordWithShipmentScheduleId(25);
		createCandiateRecordWithShipmentScheduleId(35);

		final CandidatesQuery query = CandidatesQuery.builder()
				.demandDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(25, -1))
				.build();
		final List<Candidate> result = candidateRepository.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getDemandDetail()).isNotNull();
		assertThat(resultCandidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(25);
	}

	private I_MD_Candidate createCandiateRecordWithShipmentScheduleId(final int shipmentScheduleId)
	{
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(30);
		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(candidateRecord);
		demandDetailRecord.setM_ShipmentSchedule_ID(shipmentScheduleId);
		save(demandDetailRecord);
		return candidateRecord;
	}

	@Test
	public void addOrRecplaceDemandDetail()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.materialDescr(MaterialDescriptor.builderForCandidateOrQuery()
						.productId(PRODUCT_ID)
						.warehouseId(WAHREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.date(SystemTime.asTimestamp())
						.build())
				.demandDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(20, -1))
				.build();

		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		save(candidateRecord);

		candidateRepository.addOrRecplaceDemandDetail(candidate, candidateRecord);

		final List<I_MD_Candidate_Demand_Detail> allDemandDetails = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Demand_Detail.class).create().list();
		assertThat(allDemandDetails).hasSize(1);
		assertThat(allDemandDetails.get(0).getM_ShipmentSchedule_ID()).isEqualTo(20);
		assertThat(allDemandDetails.get(0).getM_ForecastLine_ID()).isLessThanOrEqualTo(0);
		assertThat(allDemandDetails.get(0).getC_OrderLine_ID()).isLessThanOrEqualTo(0);
	}

	@Test
	public void addOrRecplaceTransactionDetail()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.materialDescr(MaterialDescriptor.builderForCandidateOrQuery()
						.productId(PRODUCT_ID)
						.warehouseId(WAHREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.date(SystemTime.asTimestamp())
						.build())
				.transactionDetail(TransactionDetail.forCandidateOrQuery(BigDecimal.ONE, 15))
				.build();

		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		save(candidateRecord);

		candidateRepository.addOrReplaceTransactionDetail(candidate, candidateRecord);

		final List<I_MD_Candidate_Transaction_Detail> allTransactionDetails = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Transaction_Detail.class).create().list();
		assertThat(allTransactionDetails).hasSize(1);
		assertThat(allTransactionDetails.get(0).getM_Transaction_ID()).isEqualTo(15);
		assertThat(allTransactionDetails.get(0).getMovementQty()).isEqualByComparingTo("1");
	}

	@Test
	public void retrieveMatches_by_forecastLineId()
	{
		final I_MD_Candidate candidateRecord = createCandiateRecordWithForecastLineId(25);
		createCandiateRecordWithForecastLineId(35);

		final CandidatesQuery query = CandidatesQuery.builder()
				.demandDetail(DemandDetail.forForecastLineId(25))
				.build();
		final List<Candidate> result = candidateRepository.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getDemandDetail()).isNotNull();
		assertThat(resultCandidate.getDemandDetail().getForecastLineId()).isEqualTo(25);

	}

	private I_MD_Candidate createCandiateRecordWithForecastLineId(final int forecastLineId)
	{
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(30);
		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(candidateRecord);
		demandDetailRecord.setM_ForecastLine_ID(forecastLineId);
		save(demandDetailRecord);
		return candidateRecord;
	}

	private I_MD_Candidate createCandidateRecordWithWarehouseId(final int warehouseId)
	{
		final int productId = 10;
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		candidateRecord.setDateProjected(new Timestamp(now.getTime()));
		candidateRecord.setM_Product_ID(productId);
		candidateRecord.setM_Warehouse_ID(warehouseId);
		save(candidateRecord);
		return candidateRecord;
	}

	@Test
	public void addOrUpdateOverwriteStoredSeqNo_with_TransactionDetail()
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
				.demandDetail(DemandDetail.forOrderLineIdOrNull(61))
				.transactionDetail(TransactionDetail.forCandidateOrQuery(BigDecimal.ONE, 33))
				.build();
		final Candidate addOrReplaceResult = candidateRepository.addOrUpdateOverwriteStoredSeqNo(productionCandidate);

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(Type.DEMAND, now, 23);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId());
		assertThat(record.getMD_Candidate_SubType()).isEqualTo(productionCandidate.getSubType().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescr().getProductId());

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Transaction_Detail.class).create().firstOnly(I_MD_Candidate_Transaction_Detail.class);
		assertThat(transactionDetailRecord).isNotNull();
		assertThat(transactionDetailRecord.getMovementQty()).isEqualByComparingTo("1");
		assertThat(transactionDetailRecord.getM_Transaction_ID()).isEqualTo(33);
	}
}
