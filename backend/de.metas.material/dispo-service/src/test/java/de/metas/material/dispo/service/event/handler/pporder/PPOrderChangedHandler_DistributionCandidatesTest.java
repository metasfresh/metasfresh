package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateQtyDetailsRepository;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.ProductPlanningId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Changing e.g. the qty of a manufacturing order deletes and re-creates its BOM lines (with new IDs).
 * Distribution candidates which supply a BOM line must follow the new line,
 * else the DD_Order_Candidate created from them references a deleted PP_Order_BOMLine.
 */
public class PPOrderChangedHandler_DistributionCandidatesTest
{
	private static final PPOrderId PP_ORDER_ID = PPOrderId.ofRepoId(123);
	private static final PPOrderAndBOMLineId OLD_BOM_LINE_ID = PPOrderAndBOMLineId.ofRepoIds(123, 1001);
	private static final PPOrderAndBOMLineId NEW_BOM_LINE_ID = PPOrderAndBOMLineId.ofRepoIds(123, 1002);

	private CandidateRepositoryWriteService candidateRepositoryWriteService;
	private PPOrderChangedHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo, candidateRepositoryRetrieval, new CandidateQtyDetailsRepository());

		handler = new PPOrderChangedHandler(
				candidateRepositoryRetrieval,
				candidateRepositoryWriteService,
				Mockito.mock(CandidateChangeService.class));
	}

	@Test
	public void distributionCandidateFollowsRecreatedBOMLine()
	{
		saveCandidate(CandidateType.SUPPLY, ProductionDetail.builder()
				.qty(TEN).advised(Flag.FALSE).pickDirectlyIfFeasible(Flag.FALSE)
				.ppOrderRef(PPOrderRef.ofPPOrderId(PP_ORDER_ID))
				.build());
		saveCandidate(CandidateType.DEMAND, ProductionDetail.builder()
				.qty(TEN).advised(Flag.FALSE).pickDirectlyIfFeasible(Flag.FALSE)
				.ppOrderRef(PPOrderRef.ofPPOrderBOMLineId(OLD_BOM_LINE_ID))
				.build());
		final CandidateId distributionSupplyId = saveCandidate(CandidateType.SUPPLY, DistributionDetail.builder()
				.qty(TEN)
				.productPlanningId(ProductPlanningId.ofRepoId(220))
				.forwardPPOrderRef(PPOrderRef.ofPPOrderBOMLineId(OLD_BOM_LINE_ID))
				.build());

		handler.handleEvent(PPOrderChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 1))
				.oldDocStatus(DocStatus.Drafted)
				.newDocStatus(DocStatus.Completed)
				.oldDatePromised(NOW)
				.newDatePromised(NOW)
				.oldQtyRequired(TEN)
				.newQtyRequired(TEN)
				.oldQtyDelivered(ZERO)
				.newQtyDelivered(ZERO)
				.ppOrderAfterChanges(createPPOrder())
				.ppOrderLineChange(ChangedPPOrderLineDescriptor.builder()
						.oldPPOrderLineId(OLD_BOM_LINE_ID)
						.newPPOrderLineId(NEW_BOM_LINE_ID)
						.productDescriptor(createProductDescriptorWithOffSet(20))
						.issueOrReceiveDate(NOW)
						.oldQtyRequired(TEN)
						.newQtyRequired(TEN)
						.oldQtyDelivered(ZERO)
						.newQtyDelivered(ZERO)
						.build())
				.build());

		final I_MD_Candidate_Dist_Detail distDetail = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMNNAME_MD_Candidate_ID, distributionSupplyId)
				.create()
				.firstOnlyNotNull(I_MD_Candidate_Dist_Detail.class);
		assertThat(distDetail.getPP_Order_ID()).isEqualTo(PP_ORDER_ID.getRepoId());
		assertThat(distDetail.getPP_Order_BOMLine_ID()).isEqualTo(NEW_BOM_LINE_ID.getLineRepoId());
	}

	private CandidateId saveCandidate(
			final CandidateType type,
			final BusinessCaseDetail businessCaseDetail)
	{
		final Candidate candidate = Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
				.type(type)
				.businessCase(businessCaseDetail.getCandidateBusinessCase())
				.businessCaseDetail(businessCaseDetail)
				.materialDescriptor(MaterialDescriptor.builder()
						.date(SystemTime.asInstant())
						.productDescriptor(createProductDescriptor())
						.warehouseId(WarehouseId.ofRepoId(150))
						.quantity(TEN)
						.build())
				.build();
		return candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate).getId();
	}

	private static PPOrder createPPOrder()
	{
		return PPOrder.builder()
				.ppOrderId(PP_ORDER_ID)
				.ppOrderData(PPOrderData.builder()
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 1))
						.datePromised(NOW)
						.dateStartSchedule(NOW)
						.plantId(ResourceId.ofRepoId(110))
						.productDescriptor(createProductDescriptor())
						.productPlanningId(130)
						.qtyRequired(TEN)
						.qtyDelivered(ZERO)
						.warehouseId(WarehouseId.ofRepoId(150))
						.build())
				.build();
	}
}
