package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.TRANSACTION_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import de.metas.common.util.time.SystemTime;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;

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
public class CandiateRepositoryRetrievalTests
{
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private RepositoryTestHelper repositoryTestHelper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval();

		repositoryTestHelper = new RepositoryTestHelper(new CandidateRepositoryWriteService());
	}

	@Test
	public void retrieveLatestMatch_returns_equal_candidate()
	{
		final CandidatesQuery query = CandidatesQuery.fromCandidate(repositoryTestHelper.laterStockCandidate, false);
		final Candidate candidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
		assertThat(candidate).isNotNull();

		assertThat(candidate).isEqualTo(repositoryTestHelper.laterStockCandidate);
	}

	@Test
	public void fromCandidateRecord_record_to_candidate()
	{
		final Timestamp dateProjected = de.metas.common.util.time.SystemTime.asTimestamp();
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(dateProjected);
		candidateRecord.setM_Warehouse_ID(WAREHOUSE_ID.getRepoId());
		candidateRecord.setM_Product_ID(PRODUCT_ID);
		candidateRecord.setC_BPartner_Customer_ID(BPARTNER_ID.getRepoId());
		candidateRecord.setM_AttributeSetInstance_ID(ATTRIBUTE_SET_INSTANCE_ID);
		candidateRecord.setStorageAttributesKey(STORAGE_ATTRIBUTES_KEY.getAsString());
		candidateRecord.setQty(TEN);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		save(candidateRecord);

		final Optional<Candidate> result = candidateRepositoryRetrieval.fromCandidateRecord(candidateRecord);

		assertThat(result.isPresent());
		final Candidate candidate = result.get();
		assertThat(candidate.getParentId().isNull()).isTrue();
		assertThat(candidate.getDate()).isEqualTo(TimeUtil.asInstant(dateProjected));

		final MaterialDescriptor materialDescriptor = candidate.getMaterialDescriptor();

		assertThat(materialDescriptor.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(materialDescriptor.getCustomerId()).isEqualTo(BPARTNER_ID);
		assertThat(materialDescriptor.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY);
		assertThat(materialDescriptor.getAttributeSetInstanceId()).isEqualTo(ATTRIBUTE_SET_INSTANCE_ID);
	}

	@Test
	public void fromCandidateRecord_RecordAndTransactiondetails_to_candidate()
	{
		final Timestamp dateProjected = SystemTime.asTimestamp();
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		candidateRecord.setDateProjected(dateProjected);
		candidateRecord.setQty(TEN);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		save(candidateRecord);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = newInstance(I_MD_Candidate_Transaction_Detail.class);
		transactionDetailRecord.setMD_Candidate(candidateRecord);
		transactionDetailRecord.setM_Transaction_ID(30);
		transactionDetailRecord.setMovementQty(TEN);
		transactionDetailRecord.setTransactionDate(TimeUtil.asTimestamp(NOW));
		save(transactionDetailRecord);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord2 = newInstance(I_MD_Candidate_Transaction_Detail.class);
		transactionDetailRecord2.setMD_Candidate(candidateRecord);
		transactionDetailRecord2.setM_Transaction_ID(31);
		transactionDetailRecord2.setMovementQty(ONE);
		transactionDetailRecord2.setTransactionDate(TimeUtil.asTimestamp(AFTER_NOW));
		save(transactionDetailRecord2);

		final Optional<Candidate> result = candidateRepositoryRetrieval.fromCandidateRecord(candidateRecord);

		assertThat(result.isPresent());
		final Candidate candidate = result.get();
		assertThat(candidate.getParentId().isNull()).isTrue();
		assertThat(candidate.getDate()).isEqualTo(TimeUtil.asInstant(dateProjected));
		assertThat(candidate.getTransactionDetails()).hasSize(2);

		assertThat(candidate.getTransactionDetails()).anySatisfy(transactionDetail -> {
			assertThat(transactionDetail.getTransactionId()).isEqualByComparingTo(30);
			assertThat(transactionDetail.getQuantity()).isEqualByComparingTo("10");
			assertThat(transactionDetail.getTransactionDate()).isEqualTo(NOW);
		});

		assertThat(candidate.getTransactionDetails()).anySatisfy(transactionDetail -> {
			assertThat(transactionDetail.getTransactionId()).isEqualByComparingTo(31);
			assertThat(transactionDetail.getQuantity()).isEqualByComparingTo("1");
			assertThat(transactionDetail.getTransactionDate()).isEqualTo(AFTER_NOW);
		});
	}

	@Test
	public void retrieve_with_ProductionDetail()
	{
		perform_retrieve_with_ProductionDetail();
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_ProductionDetail()
	{
		final I_MD_Candidate_Prod_Detail productionDetailRecord = createProdDetailRecord(101, 111);

		final CandidatesQuery query = CandidatesQuery.fromId(CandidateId.ofRepoId(productionDetailRecord.getMD_Candidate().getMD_Candidate_ID()));

		final Candidate cand = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
		assertThat(cand).isNotNull();

		final MaterialDescriptor materialDescriptor = cand.getMaterialDescriptor();
		assertThat(materialDescriptor.getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(materialDescriptor.getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(materialDescriptor.getDate()).isEqualTo(NOW);

		final ProductionDetail productionDetail = ProductionDetail.cast(cand.getBusinessCaseDetail());
		assertThat(productionDetail).isNotNull();
		assertThat(productionDetail.getQty()).isZero();
		assertThat(productionDetail.getDescription()).isEqualTo("description1");
		assertThat(productionDetail.getProductBomLineId()).isEqualTo(71);
		assertThat(productionDetail.getProductPlanningId()).isEqualTo(81);
		assertThat(productionDetail.getPpOrderId()).isEqualTo(101);
		assertThat(productionDetail.getPpOrderLineId()).isEqualTo(111);
		assertThat(productionDetail.getPpOrderDocStatus()).isEqualTo(DocStatus.Completed);

		return ImmutablePair.of(cand, productionDetailRecord.getMD_Candidate());
	}

	private I_MD_Candidate_Prod_Detail createProdDetailRecord(final int ppOrderId, final int ppOrderLineId)
	{
		final I_MD_Candidate record = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PRODUCTION);
		save(record);

		final I_MD_Candidate_Prod_Detail productionDetailRecord = newInstance(I_MD_Candidate_Prod_Detail.class);
		productionDetailRecord.setDescription("description1");
		productionDetailRecord.setPP_Plant_ID(61);
		productionDetailRecord.setPP_Product_BOMLine_ID(71);
		productionDetailRecord.setPP_Product_Planning_ID(81);
		productionDetailRecord.setMD_Candidate(record);
		productionDetailRecord.setPP_Order_ID(ppOrderId);
		productionDetailRecord.setPP_Order_BOMLine_ID(ppOrderLineId);
		productionDetailRecord.setPP_Order_DocStatus(DocStatus.Completed.getCode());
		save(productionDetailRecord);

		return productionDetailRecord;
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

		// make another record, just like "record", but without a productionDetailRecord
		final I_MD_Candidate otherRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PRODUCTION);
		save(otherRecord);

		final Candidate expectedRecordWithProdDetails = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand, false));
		assertThat(expectedRecordWithProdDetails).isNotNull();
		assertThat(expectedRecordWithProdDetails.getId().getRepoId()).isEqualTo(record.getMD_Candidate_ID());

		final CandidatesQuery querqWithoutProdDetails = CandidatesQuery
				.fromCandidate(cand.withId(null), false)
				.withProductionDetailsQuery(ProductionDetailsQuery.NO_PRODUCTION_DETAIL);
		final Candidate expectedRecordWithoutProdDetails = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(querqWithoutProdDetails);
		assertThat(expectedRecordWithoutProdDetails).isNotNull();
		assertThat(expectedRecordWithoutProdDetails.getId().getRepoId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	@Test
	public void retrieveExact_with_ProductionDetail_ppOrderId_only()
	{

		final I_MD_Candidate_Prod_Detail prodDetailRecord = createProdDetailRecord(101, 0);
		createProdDetailRecord(101, 112);
		createProdDetailRecord(101, 113);

		final ProductionDetailsQuery poductionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderId(101)
				.ppOrderLineId(ProductionDetailsQuery.NO_PP_ORDER_LINE_ID)
				.build();
		final CandidatesQuery candidatesQuery = CandidatesQuery.builder().productionDetailsQuery(poductionDetailsQuery).build();

		final List<Candidate> retrievedCandidates = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(candidatesQuery);
		assertThat(retrievedCandidates).hasSize(1);
		assertThat(retrievedCandidates.get(0).getId().getRepoId()).isEqualTo(prodDetailRecord.getMD_Candidate_ID());
	}

	@Test
	public void retrieveExact_with_ProductionDetail_ppOrderLineId()
	{
		createProdDetailRecord(101, 0);
		final I_MD_Candidate_Prod_Detail prodDetailRecord = createProdDetailRecord(101, 112);
		createProdDetailRecord(101, 113);

		final ProductionDetailsQuery poductionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderId(101)
				.ppOrderLineId(112)
				.build();
		final CandidatesQuery candidatesQuery = CandidatesQuery.builder().productionDetailsQuery(poductionDetailsQuery).build();

		final List<Candidate> retrievedCandidates = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(candidatesQuery);
		assertThat(retrievedCandidates).hasSize(1);
		assertThat(retrievedCandidates.get(0).getId().getRepoId()).isEqualTo(prodDetailRecord.getMD_Candidate_ID());
	}

	@Test
	public void retrieve_with_DistributionDetail()
	{
		perform_retrieve_with_DistributionDetail();
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, I recommend to first check if {@link #retrieve_with_DistributionDetail()} works.
	 */
	@Test
	public void retrieveExact_with_DistributionDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_DistributionDetail();

		final Candidate candidateWithDistributionDetail = pair.getLeft();
		assertThat(distrDetailOrNull(candidateWithDistributionDetail)).isNotNull();

		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a distributionDetailRecord
		final I_MD_Candidate otherRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_DISTRIBUTION);
		save(otherRecord);

		final Candidate expectedResultWithDistDetails = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(candidateWithDistributionDetail, false));
		assertThat(expectedResultWithDistDetails).isNotNull();
		assertThat(distrDetailOrNull(expectedResultWithDistDetails)).isNotNull();
		assertThat(expectedResultWithDistDetails.getId().getRepoId()).isEqualTo(record.getMD_Candidate_ID());

		final CandidatesQuery withoutdistDetailsQuery = CandidatesQuery
				.fromCandidate(candidateWithDistributionDetail.withId(null), false)
				.withDistributionDetailsQuery(DistributionDetailsQuery.NO_DISTRIBUTION_DETAIL);
		final Candidate expectedRecordWithoutDistDetails = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(withoutdistDetailsQuery);

		assertThat(expectedRecordWithoutDistDetails).isNotNull();
		assertThat(distrDetailOrNull(expectedRecordWithoutDistDetails)).isNull();
		assertThat(expectedRecordWithoutDistDetails.getId().getRepoId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_DistributionDetail()
	{
		final I_MD_Candidate record = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_DISTRIBUTION);
		save(record);

		final I_MD_Candidate_Dist_Detail distributionDetailRecord = newInstance(I_MD_Candidate_Dist_Detail.class);
		distributionDetailRecord.setDD_NetworkDistributionLine_ID(71);
		distributionDetailRecord.setPP_Product_Planning_ID(81);
		distributionDetailRecord.setMD_Candidate(record);
		distributionDetailRecord.setDD_Order_ID(101);
		distributionDetailRecord.setDD_OrderLine_ID(111);
		distributionDetailRecord.setDD_Order_DocStatus(DocStatus.Completed.getCode());
		distributionDetailRecord.setM_Shipper_ID(121);
		save(distributionDetailRecord);

		final Candidate cand = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(CandidateId.ofRepoId(record.getMD_Candidate_ID())));
		assertThat(cand).isNotNull();
		assertThat(cand.getMaterialDescriptor().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescriptor().getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(cand.getMaterialDescriptor().getDate()).isEqualTo(NOW);

		final BusinessCaseDetail businessCaseDetail = cand.getBusinessCaseDetail();
		assertThat(ProductionDetail.castOrNull(businessCaseDetail)).isNull();

		final DistributionDetail distributionDetail = DistributionDetail.castOrNull(businessCaseDetail);
		assertThat(distributionDetail).isNotNull();
		assertThat(distributionDetail.getNetworkDistributionLineId()).isEqualTo(71);
		assertThat(distributionDetail.getProductPlanningId()).isEqualTo(81);
		assertThat(distributionDetail.getDdOrderId()).isEqualTo(101);
		assertThat(distributionDetail.getDdOrderLineId()).isEqualTo(111);
		assertThat(distributionDetail.getShipperId()).isEqualTo(121);
		assertThat(distributionDetail.getDdOrderDocStatus()).isEqualTo(DocStatus.Completed.getCode());

		return ImmutablePair.of(cand, record);
	}

	@Test
	public void retrieve_with_PurchaseDetail()
	{
		final I_MD_Candidate record = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_SUPPLY);
		record.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PURCHASE);
		save(record);

		final I_MD_Candidate_Purchase_Detail distributionDetailRecord = newInstance(I_MD_Candidate_Purchase_Detail.class);
		distributionDetailRecord.setMD_Candidate(record);
		save(distributionDetailRecord);

		// invoke the method under test
		final Candidate cand = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(CandidateId.ofRepoId(record.getMD_Candidate_ID())));

		assertThat(cand).isNotNull();
		assertThat(cand.getMaterialDescriptor().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescriptor().getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(cand.getMaterialDescriptor().getDate()).isEqualTo(NOW);

		final BusinessCaseDetail businessCaseDetail = cand.getBusinessCaseDetail();
		assertThat(ProductionDetail.castOrNull(businessCaseDetail)).isNull();

		final PurchaseDetail distributionDetail = PurchaseDetail.castOrNull(businessCaseDetail);
		assertThat(distributionDetail).isNotNull();
	}

	private DistributionDetail distrDetailOrNull(final Candidate candidateWithDistributionDetail)
	{
		final DistributionDetail distributionDetail = DistributionDetail.castOrNull(candidateWithDistributionDetail.getBusinessCaseDetail());
		return distributionDetail;
	}

	@Test
	public void retrieve_with_TransactionDetail()
	{
		final I_MD_Candidate record = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_DECREASE);
		save(record);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = newInstance(I_MD_Candidate_Transaction_Detail.class);
		transactionDetailRecord.setMD_Candidate(record);
		transactionDetailRecord.setM_Transaction_ID(TRANSACTION_ID);
		transactionDetailRecord.setMovementQty(TEN);
		transactionDetailRecord.setTransactionDate(TimeUtil.asTimestamp(NOW));
		save(transactionDetailRecord);

		final CandidatesQuery query = CandidatesQuery
				.builder().transactionDetail(TransactionDetail.forQuery(TRANSACTION_ID))
				.build();

		final List<Candidate> expectedCandidates = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		assertThat(expectedCandidates).hasSize(1);
		final Candidate expectedCandidate = expectedCandidates.get(0);
		assertThat(expectedCandidate.getId().getRepoId()).isEqualTo(record.getMD_Candidate_ID());
		assertThat(expectedCandidate.getTransactionDetails()).hasSize(1);

		final TransactionDetail transactionDetail = expectedCandidate.getTransactionDetails().get(0);
		assertThat(transactionDetail.getTransactionId()).isEqualTo(TRANSACTION_ID);
		assertThat(transactionDetail.getQuantity()).isEqualByComparingTo("10");
		assertThat(transactionDetail.getTransactionDate()).isEqualTo(NOW);
	}

	@Test
	public void retrieve_with_id_and_demandDetail()
	{
		perform_retrieve_with_id_and_demandDetail();
	}

	/**
	 * Verifies that demand details are also used as filter criterion
	 * If this one fails, i recommend to first check if {@link #retrieve_with_id_and_demandDetail()} works
	 */
	@Test
	public void retrieveExact_with_demandDetail_filtered()
	{
		final IPair<Candidate, I_MD_Candidate> pair = perform_retrieve_with_id_and_demandDetail();
		final Candidate cand = pair.getLeft();
		final I_MD_Candidate record = pair.getRight();

		// make another record, just like "record", but without a demandDetailRecord
		final I_MD_Candidate otherRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		otherRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		otherRecord.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PRODUCTION);
		save(otherRecord);

		final I_MD_Candidate_Demand_Detail otherDemandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		otherDemandDetailRecord.setMD_Candidate(otherRecord);
		otherDemandDetailRecord.setC_OrderLine_ID(64);
		otherDemandDetailRecord.setM_ForecastLine_ID(74); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(otherDemandDetailRecord);

		final Candidate expectedRecordWithDemandDetails = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(cand, false));
		assertThat(expectedRecordWithDemandDetails).isNotNull();
		assertThat(expectedRecordWithDemandDetails.getId().getRepoId()).isEqualTo(record.getMD_Candidate_ID());

		final Candidate expectedRecordWithoutDemandDetails = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(CandidatesQuery.fromCandidate(
						cand
								.withId(null)
								.withBusinessCaseDetail(DemandDetail.forForecastLineId(74, 84, TEN)),
						false));

		assertThat(expectedRecordWithoutDemandDetails).isNotNull();
		assertThat(expectedRecordWithoutDemandDetails.getId().getRepoId()).isEqualTo(otherRecord.getMD_Candidate_ID());
	}

	private IPair<Candidate, I_MD_Candidate> perform_retrieve_with_id_and_demandDetail()
	{
		final I_MD_Candidate record = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		record.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		record.setMD_Candidate_BusinessCase(X_MD_Candidate.MD_CANDIDATE_BUSINESSCASE_PRODUCTION);
		save(record);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(record);
		demandDetailRecord.setC_OrderLine_ID(62);
		demandDetailRecord.setM_ForecastLine_ID(72); // in production it doesn't make sense to set both, but here we get two for the price of one
		save(demandDetailRecord);

		final Candidate cand = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(CandidateId.ofRepoId(record.getMD_Candidate_ID())));
		assertThat(cand).isNotNull();
		assertThat(cand.getId().getRepoId()).isEqualTo(record.getMD_Candidate_ID());
		assertThat(cand.getMaterialDescriptor().getProductId()).isEqualTo(PRODUCT_ID);
		assertThat(cand.getMaterialDescriptor().getWarehouseId()).isEqualTo(WAREHOUSE_ID);
		assertThat(cand.getMaterialDescriptor().getDate()).isEqualTo(NOW);
		assertThat(ProductionDetail.castOrNull(cand.getBusinessCaseDetail())).isNull();
		assertThat(cand.getDemandDetail().getOrderLineId()).isEqualTo(62);
		assertThat(cand.getDemandDetail().getForecastLineId()).isEqualTo(72);

		return ImmutablePair.of(cand, record);
	}

	@Test
	public void retrieveLatestMatch_until_earlier_date()
	{
		final CandidatesQuery earlierQuery = repositoryTestHelper.mkQueryForStockUntilDate(BEFORE_NOW);
		final Candidate earlierStock = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(earlierQuery);
		assertThat(earlierStock).isNull();
	}

	@Test
	public void retrieveLatestMatch_until_now_date()
	{
		final CandidatesQuery sameTimeQuery = repositoryTestHelper.mkQueryForStockUntilDate(NOW);
		final Candidate sameTimeStock = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(sameTimeQuery);
		assertThat(sameTimeStock).isNotNull();
		assertThat(sameTimeStock).isEqualTo(repositoryTestHelper.stockCandidate);
	}

	@Test
	public void retrieveLatestMatch_until_later_date()
	{
		final CandidatesQuery laterQuery = repositoryTestHelper.mkQueryForStockUntilDate(AFTER_NOW);
		final Candidate laterStock = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(laterQuery);
		assertThat(laterStock).isNotNull();
		assertThat(laterStock).isEqualTo(repositoryTestHelper.laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_from_earlier_date()
	{
		final CandidatesQuery earlierQuery = repositoryTestHelper.mkQueryForStockFromDate(BEFORE_NOW);

		final List<Candidate> stockFrom = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(earlierQuery);

		assertThat(stockFrom).containsExactly(repositoryTestHelper.stockCandidate, repositoryTestHelper.laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_DateOperator_from_now_date()
	{
		final CandidatesQuery sameTimeQuery = repositoryTestHelper.mkQueryForStockFromDate(NOW);

		final List<Candidate> stockFrom = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(sameTimeQuery);

		assertThat(stockFrom).containsExactly(repositoryTestHelper.stockCandidate, repositoryTestHelper.laterStockCandidate);
	}

	@Test
	public void retrieveOrderedByDateAndSeqNo_DateOperator_from_later_date()
	{
		final CandidatesQuery laterQuery = repositoryTestHelper.mkQueryForStockFromDate(AFTER_NOW);

		final List<Candidate> stockFrom = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(laterQuery);

		assertThat(stockFrom).containsExactly(repositoryTestHelper.laterStockCandidate);
	}

	@Test
	public void retrieveMatchesOrderByDateAndSeqNo_only_by_warehouse_id()
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(20);
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(warehouseId);
		createCandidateRecordWithWarehouseId(WarehouseId.ofRepoId(30));

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(MaterialDescriptorQuery.builder().warehouseId(warehouseId).build())
				.build();
		final List<Candidate> result = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId().getRepoId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getWarehouseId()).isEqualTo(warehouseId);
	}

	@Test
	public void retrieveMatches_by_shipmentScheduleId()
	{
		final I_MD_Candidate candidateRecord = createCandiateRecordWithShipmentScheduleId(25);
		createCandiateRecordWithShipmentScheduleId(35);

		final CandidatesQuery query = CandidatesQuery.builder()
				.demandDetailsQuery(DemandDetailsQuery.ofShipmentScheduleId(25))
				.build();
		final List<Candidate> result = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId().getRepoId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getDemandDetail()).isNotNull();
		assertThat(resultCandidate.getDemandDetail().getShipmentScheduleId()).isEqualTo(25);
	}

	private I_MD_Candidate createCandiateRecordWithShipmentScheduleId(final int shipmentScheduleId)
	{
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(candidateRecord);
		demandDetailRecord.setM_ShipmentSchedule_ID(shipmentScheduleId);
		save(demandDetailRecord);
		return candidateRecord;
	}

	@Test
	public void retrieveMatches_by_forecastLineId()
	{
		final I_MD_Candidate candidateRecord = createCandiateRecordWithForecastLineId(25, 26);
		createCandiateRecordWithForecastLineId(35, 36); // create another one

		final CandidatesQuery query = CandidatesQuery.builder()
				.demandDetailsQuery(DemandDetailsQuery.ofForecastLineId(25))
				.build();
		final List<Candidate> result = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);

		assertThat(result).hasSize(1);
		final Candidate resultCandidate = result.get(0);
		assertThat(resultCandidate.getId().getRepoId()).isEqualTo(candidateRecord.getMD_Candidate_ID());
		assertThat(resultCandidate.getDemandDetail()).isNotNull();
		assertThat(resultCandidate.getDemandDetail().getForecastLineId()).isEqualTo(25);

	}

	private static I_MD_Candidate createCandiateRecordWithForecastLineId(
			final int forecastLineId,
			final int forecastId)
	{
		final I_MD_Candidate candidateRecord = createCandidateRecordWithWarehouseId(WAREHOUSE_ID);
		candidateRecord.setM_Forecast_ID(forecastId);
		save(candidateRecord);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = newInstance(I_MD_Candidate_Demand_Detail.class);
		demandDetailRecord.setMD_Candidate(candidateRecord);
		demandDetailRecord.setM_ForecastLine_ID(forecastLineId);
		save(demandDetailRecord);
		return candidateRecord;
	}

	private static I_MD_Candidate createCandidateRecordWithWarehouseId(final WarehouseId warehouseId)
	{
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(NOW));
		candidateRecord.setM_Product_ID(PRODUCT_ID);
		candidateRecord.setM_AttributeSetInstance_ID(ATTRIBUTE_SET_INSTANCE_ID);
		candidateRecord.setStorageAttributesKey(STORAGE_ATTRIBUTES_KEY.getAsString());
		candidateRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		save(candidateRecord);

		return candidateRecord;
	}
}
