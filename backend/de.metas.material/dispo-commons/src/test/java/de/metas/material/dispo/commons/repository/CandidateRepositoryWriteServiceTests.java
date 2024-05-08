package de.metas.material.dispo.commons.repository;

import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.ForecastLineDimensionFactory;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.DispoTestUtils;
import de.metas.material.dispo.commons.RepositoryTestHelper;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ForecastLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static de.metas.material.event.EventTestHelper.AFTER_NOW;
import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.CLIENT_AND_ORG_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
public class CandidateRepositoryWriteServiceTests
{
	private CandidateRepositoryWriteService candidateRepositoryWriteService;

	private RepositoryTestHelper repositoryTestHelper;

	private I_M_ForecastLine forecastLine;

	private DimensionService dimensionService;
	private StockChangeDetailRepo stockChangeDetailRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();



		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionFactories.add(new ForecastLineDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		stockChangeDetailRepo = new StockChangeDetailRepo();

		candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);
		repositoryTestHelper = new RepositoryTestHelper(candidateRepositoryWriteService);
		forecastLine = createForecastLine(61);
	}

	private I_M_ForecastLine createForecastLine(final int forecastLineId)
	{
		final I_M_ForecastLine forecastLine = newInstance(I_M_ForecastLine.class);
		forecastLine.setM_ForecastLine_ID(61);
		save(forecastLine);

		return forecastLine;

	}

	@Test
	public void updateCandidateRecordFromCandidate()
	{
		final int shipmentScheduleId = 20;
		final int orderId = 40;

		final MaterialDescriptor materialDescriptorWithAlotOfDigits = createMaterialDescriptor()
				.withQuantity(new BigDecimal("0.00000000000000"));

		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(CandidateType.DEMAND)
				.materialDescriptor(materialDescriptorWithAlotOfDigits)
				.businessCaseDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, 30, orderId, TEN))
				.build();
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);

		candidateRepositoryWriteService.updateCandidateRecordFromCandidate(candidateRecord, candidate, false);

		assertThat(candidateRecord.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND);
		assertThat(candidateRecord.getM_Product_ID()).isEqualTo(PRODUCT_ID);
		assertThat(candidateRecord.getC_BPartner_Customer_ID()).isEqualTo(BPARTNER_ID.getRepoId());
		assertThat(candidateRecord.getStorageAttributesKey()).isEqualTo(STORAGE_ATTRIBUTES_KEY.getAsString());
		assertThat(candidateRecord.getM_AttributeSetInstance_ID()).isEqualTo(ATTRIBUTE_SET_INSTANCE_ID);
		assertThat(candidateRecord.getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleId);
		assertThat(candidateRecord.getC_OrderSO_ID()).isEqualTo(orderId);
		assertThat(candidateRecord.getQty()).isEqualTo(ZERO);
	}

	@Test
	public void addOrRecplaceDemandDetail()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor())
				.businessCaseDetail(DemandDetail.forShipmentScheduleIdAndOrderLineId(20, -1, -1, TEN))
				.build();

		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		save(candidateRecord);

		candidateRepositoryWriteService.addOrReplaceDemandDetail(candidate, candidateRecord);

		final List<I_MD_Candidate_Demand_Detail> allDemandDetails = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Demand_Detail.class).create().list();
		assertThat(allDemandDetails).hasSize(1);
		assertThat(allDemandDetails.get(0).getM_ShipmentSchedule_ID()).isEqualTo(20);
		assertThat(allDemandDetails.get(0).getM_ForecastLine_ID()).isLessThanOrEqualTo(0);
		assertThat(allDemandDetails.get(0).getC_OrderLine_ID()).isLessThanOrEqualTo(0);
	}

	@Test
	public void addOrRecplaceTransactionDetails()
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(CandidateType.DEMAND)
				.materialDescriptor(createMaterialDescriptor())
				.transactionDetail(TransactionDetail.builder().quantity(ONE).storageAttributesKey(AttributesKey.ALL).transactionId(15).transactionDate(NOW).complete(true).build())
				.transactionDetail(TransactionDetail.builder().quantity(TEN).storageAttributesKey(AttributesKey.ALL).transactionId(16).transactionDate(NOW).complete(true).build())
				.build();

		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		save(candidateRecord);

		candidateRepositoryWriteService.addOrReplaceTransactionDetail(candidate, candidateRecord);

		final List<I_MD_Candidate_Transaction_Detail> allTransactionDetailRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Transaction_Detail.class).create().list();
		assertThat(allTransactionDetailRecords).hasSize(2);

		assertThat(allTransactionDetailRecords).anySatisfy(transactionDetailRecord -> {
			assertThat(transactionDetailRecord.getM_Transaction_ID()).isEqualTo(15);
			assertThat(transactionDetailRecord.getMovementQty()).isEqualByComparingTo("1");
		});
		assertThat(allTransactionDetailRecords).anySatisfy(transactionDetailRecord -> {
			assertThat(transactionDetailRecord.getM_Transaction_ID()).isEqualTo(16);
			assertThat(transactionDetailRecord.getMovementQty()).isEqualByComparingTo("10");
		});
	}

	@Test
	public void addOrUpdateOverwriteStoredSeqNo_returns_equal_candidate()
	{
		final Candidate originalCandidate = repositoryTestHelper.stockCandidate;

		final Candidate candidateReturnedfromRepo = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(originalCandidate)
				.getCandidate();

		assertThat(candidateReturnedfromRepo)
				.isEqualTo(originalCandidate);
	}

	/**
	 * Verifies that a candidate can be replaced with another candidate that has the same product, type, timestamp and locator.
	 */
	@Test
	public void addOrReplace_update()
	{
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);

		// guard
		final CandidatesQuery queryForStockUntilDate = repositoryTestHelper.mkQueryForStockUntilDate(NOW);
		assertThat(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(queryForStockUntilDate)).isNotNull();
		assertThat(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(queryForStockUntilDate)).isEqualTo(repositoryTestHelper.stockCandidate);

		final CandidatesQuery queryForStockFromDate = repositoryTestHelper.mkQueryForStockFromDate(NOW);

		final List<Candidate> stockBeforeReplacement = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(queryForStockFromDate);
		assertThat(stockBeforeReplacement).containsOnly(
				repositoryTestHelper.stockCandidate,
				repositoryTestHelper.laterStockCandidate);

		final Candidate replacementCandidate = repositoryTestHelper.stockCandidate
				.withQuantity(ONE);
		candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(replacementCandidate);

		assertThat(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(queryForStockUntilDate)).isEqualTo(replacementCandidate);
		final List<Candidate> stockAfterReplacement = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(queryForStockFromDate);
		assertThat(stockAfterReplacement).containsOnly(replacementCandidate, repositoryTestHelper.laterStockCandidate);
	}

	/**
	 * Verifies that {@link ProductionDetail} data is also persisted
	 */
	@Test
	public void addOrReplace_with_ProductionDetail()
	{
		final Candidate productionCandidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.materialDescriptor(createMaterialDescriptor())
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.businessCaseDetail(ProductionDetail.builder()
											.description("description")
											.plantId(ResourceId.ofRepoId(60))
											.productBomLineId(70)
											.productPlanningId(80)
											.ppOrderId(100)
											.ppOrderLineId(110)
											.ppOrderDocStatus(DocStatus.Completed)
											.advised(Flag.TRUE)
											.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE)
											.qty(TEN)
											.build())
				.build();
		final Candidate addOrReplaceResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(productionCandidate)
				.getCandidate();

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(CandidateType.DEMAND, NOW, PRODUCT_ID);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId().getRepoId());
		assertThat(record.getMD_Candidate_BusinessCase()).isEqualTo(productionCandidate.getBusinessCase().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescriptor().getProductId());

		final I_MD_Candidate_Prod_Detail productionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Prod_Detail.class).create().firstOnly(I_MD_Candidate_Prod_Detail.class);
		assertThat(productionDetailRecord).isNotNull();
		assertThat(productionDetailRecord.getDescription()).isEqualTo("description");
		assertThat(productionDetailRecord.getPP_Plant_ID()).isEqualTo(60);
		assertThat(productionDetailRecord.getPP_Product_BOMLine_ID()).isEqualTo(70);
		assertThat(productionDetailRecord.getPP_Product_Planning_ID()).isEqualTo(80);
		assertThat(productionDetailRecord.getPP_Order_ID()).isEqualTo(100);
		assertThat(productionDetailRecord.getPP_Order_BOMLine_ID()).isEqualTo(110);
		assertThat(productionDetailRecord.getPP_Order_DocStatus()).isEqualTo(DocStatus.Completed.getCode());
	}

	/**
	 * Verifies that if a candidate with a groupID is persisted, then that candidate's groupId is also persisted.
	 * And if the respective candidate does no yet have a groupId, then the persisted candidate's Id is assigned to be its groupId.
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_nonStockCandidate_receives_groupId()
	{
		final Candidate candidateWithOutGroupId = repositoryTestHelper.stockCandidate
				.withType(CandidateType.DEMAND)
				.withDate(AFTER_NOW.plus(1, ChronoUnit.MINUTES)) // pick a different time from the other candidates
				.withGroupId(null);

		final Candidate result1 = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId)
				.getCandidate();
		// result1 was assigned an id and a groupId
		assertThat(result1.getId().getRepoId()).isGreaterThan(0);
		assertThat(result1.getGroupId().toInt()).isEqualTo(result1.getId().getRepoId());

		final Candidate candidateWithGroupId = candidateWithOutGroupId
				.withId(null)
				.withDate(AFTER_NOW.plus(2, ChronoUnit.MINUTES)) // pick a different time from the other candidates
				.withGroupId(result1.getGroupId());

		final Candidate result2 = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(candidateWithGroupId)
				.getCandidate();
		// result2 also has id & groupId, but its ID is unique whereas its groupId is the same as result1's groupId
		assertThat(result2.getId()).isNotNull();
		assertThat(result2.getGroupId()).isNotEqualTo(result2.getId().getRepoId());
		assertThat(result2.getGroupId()).isEqualTo(result1.getGroupId());

		final I_MD_Candidate result1Record = load(result1.getId().getRepoId(), I_MD_Candidate.class);
		assertThat(result1Record.getMD_Candidate_ID()).isEqualTo(result1.getId().getRepoId());
		assertThat(result1Record.getMD_Candidate_GroupId()).isEqualTo(result1.getGroupId().toInt());

		final I_MD_Candidate result2Record = load(result2.getId().getRepoId(), I_MD_Candidate.class);
		assertThat(result2Record.getMD_Candidate_ID()).isEqualTo(result2.getId().getRepoId());
		assertThat(result2Record.getMD_Candidate_GroupId()).isEqualTo(result2.getGroupId().toInt());
	}

	/**
	 * Verifies that candidates with type {@link CandidateType#STOCK} do receive a groupId
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_stockCandidate_receives_groupId()
	{
		final Candidate candidateWithOutGroupId = repositoryTestHelper.stockCandidate
				.withDate(AFTER_NOW.plus(1, ChronoUnit.MINUTES)) // pick a different time from the other candidates
				.withGroupId(null);

		final Candidate result1 = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(candidateWithOutGroupId)
				.getCandidate();
		assertThat(result1.getId().getRepoId()).isGreaterThan(0);
		assertThat(result1.getGroupId()).isNotNull();

		final I_MD_Candidate result1Record = load(result1.getId().getRepoId(), I_MD_Candidate.class);
		assertThat(result1Record.getMD_Candidate_ID()).isEqualTo(result1.getId().getRepoId());
		assertThat(result1Record.getMD_Candidate_GroupId()).isEqualTo(result1.getGroupId().toInt());
	}

	/**
	 * Verifies that {@link DistributionDetail} data is also persisted
	 */
	@Test
	public void addOrUpdateOverwriteStoredSeqNo_with_DistributionDetail()
	{
		final Candidate distributionCandidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(createMaterialDescriptor())
				.businessCaseDetail(DistributionDetail.builder()
											.productPlanningId(80)
											.plantId(85)
											.networkDistributionLineId(90)
											.ddOrderId(100)
											.ddOrderLineId(110)
											.shipperId(120)
											.ddOrderDocStatus("ddOrderDocStatus")
											.qty(TEN)
											.build())
				.build();
		final Candidate addOrReplaceResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(distributionCandidate)
				.getCandidate();

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(CandidateType.DEMAND, NOW, PRODUCT_ID);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId().getRepoId());
		assertThat(record.getMD_Candidate_BusinessCase()).isEqualTo(distributionCandidate.getBusinessCase().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(distributionCandidate.getMaterialDescriptor().getProductId());

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
	public void addOrUpdateOverwriteStoredSeqNo_with_DemandDetail()
	{
		final Candidate productionCandidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(createMaterialDescriptor())
				.businessCaseDetail(DemandDetail.forForecastLineId(61, 71, TEN))
				.build();
		final Candidate addOrReplaceResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(productionCandidate)
				.getCandidate();

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(CandidateType.DEMAND, NOW, PRODUCT_ID);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId().getRepoId());
		assertThat(record.getMD_Candidate_BusinessCase()).isEqualTo(productionCandidate.getBusinessCase().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescriptor().getProductId());
		assertThat(record.getM_Forecast_ID()).isEqualTo(71);

		final I_MD_Candidate_Demand_Detail demandDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Demand_Detail.class).create().firstOnly(I_MD_Candidate_Demand_Detail.class);
		assertThat(demandDetailRecord).isNotNull();
		assertThat(demandDetailRecord.getM_ForecastLine_ID()).isEqualTo(61);
	}

	@Test
	public void addOrUpdateOverwriteStoredSeqNo_with_TransactionDetail()
	{
		final int productIdOffSet = 10;
		final Candidate productionCandidate = Candidate.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.clientAndOrgId(CLIENT_AND_ORG_ID)
				.materialDescriptor(createMaterialDescriptor()
											.withProductDescriptor(createProductDescriptorWithOffSet(productIdOffSet)))
				.businessCaseDetail(DemandDetail.forForecastLineId(61, 62, TEN))
				.transactionDetail(TransactionDetail.builder().quantity(ONE).storageAttributesKey(AttributesKey.ALL).transactionId(33).transactionDate(NOW).complete(true).build())
				.build();
		final Candidate addOrReplaceResult = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(productionCandidate)
				.getCandidate();

		final List<I_MD_Candidate> filtered = DispoTestUtils.filter(CandidateType.DEMAND, NOW, PRODUCT_ID + productIdOffSet);
		assertThat(filtered).hasSize(1);

		final I_MD_Candidate record = filtered.get(0);
		assertThat(record.getMD_Candidate_ID()).isEqualTo(addOrReplaceResult.getId().getRepoId());
		assertThat(record.getMD_Candidate_BusinessCase()).isEqualTo(productionCandidate.getBusinessCase().toString());
		assertThat(record.getM_Product_ID()).isEqualTo(productionCandidate.getMaterialDescriptor().getProductId());
		assertThat(record.getM_Forecast_ID()).as("The demandDetail's forecastId shall be set to the MD_Candidate").isEqualTo(62);

		final I_MD_Candidate_Transaction_Detail transactionDetailRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate_Transaction_Detail.class).create().firstOnly(I_MD_Candidate_Transaction_Detail.class);
		assertThat(transactionDetailRecord).isNotNull();
		assertThat(transactionDetailRecord.getMovementQty()).isEqualByComparingTo("1");
		assertThat(transactionDetailRecord.getM_Transaction_ID()).isEqualTo(33);
	}
}
