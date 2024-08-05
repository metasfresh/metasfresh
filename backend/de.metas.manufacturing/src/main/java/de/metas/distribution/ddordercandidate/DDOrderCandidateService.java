package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddordercandidate.async.DDOrderCandidateEnqueueService;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.order.IOrderLineBL;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DDOrderCandidateService
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	@NonNull private final DDOrderCandidateEnqueueService ddOrderCandidateEnqueueService;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public void save(@NonNull final DDOrderCandidate ddOrderCandidate)
	{
		ddOrderCandidateRepository.save(ddOrderCandidate);
	}

	public void enqueueToProcess(@NonNull final DDOrderCandidate ddOrderCandidate)
	{
		ddOrderCandidateEnqueueService.enqueueId(ddOrderCandidate.getIdNotNull());
	}

	public List<DDOrderCandidate> getBySelectionId(@NonNull final PInstanceId selectionId)
	{
		return ddOrderCandidateRepository.getBySelectionId(selectionId);
	}

	public void process(@NonNull final DDOrderCandidateProcessRequest request)
	{
		DDOrderCandidateProcessCommand.builder()
				.ddOrderLowLevelService(ddOrderLowLevelService)
				.ddOrderCandidateService(this)
				.orgDAO(orgDAO)
				.docTypeDAO(docTypeDAO)
				.documentBL(documentBL)
				.productPlanningDAO(productPlanningDAO)
				.bpartnerOrgBL(bpartnerOrgBL)
				.warehouseBL(warehouseBL)
				.uomConversionBL(uomConversionBL)
				.orderLineBL(orderLineBL)
				//
				.request(request)
				//
				.build()
				.execute();
	}

	public void saveAndUpdateCandidates(@NonNull final DDOrderCandidateAllocList list)
	{
		ddOrderCandidateAllocRepository.save(list);
		updateCandidatesOnAllocationChanges(list.getDDOrderCandidateIds());
	}

	private void updateCandidatesOnAllocationChanges(final Set<DDOrderCandidateId> ddOrderCandidateIds)
	{
		if (ddOrderCandidateIds.isEmpty())
		{
			return;
		}

		final ImmutableMap<DDOrderCandidateId, DDOrderCandidate> candidates = Maps.uniqueIndex(ddOrderCandidateRepository.getByIds(ddOrderCandidateIds), DDOrderCandidate::getIdNotNull);

		ddOrderCandidateAllocRepository.getByCandidateIds(ddOrderCandidateIds)
				.groupByCandidateId()
				.forEach((candidateId, alloc) -> {
					final DDOrderCandidate candidate = candidates.get(candidateId);
					final Quantity qtyProcessed = alloc.getQtySum().orElseGet(() -> candidate.getQty().toZero());
					candidate.setQtyProcessed(qtyProcessed);
					ddOrderCandidateRepository.save(candidate);
				});

	}
}
