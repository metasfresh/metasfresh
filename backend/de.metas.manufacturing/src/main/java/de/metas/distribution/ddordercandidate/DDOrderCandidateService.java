package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddordercandidate.async.DDOrderCandidateEnqueueService;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.order.IOrderLineBL;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.quantity.Quantity;
import de.metas.resource.ResourceService;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DDOrderCandidateService
{
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	@NonNull private final DDOrderCandidateEnqueueService ddOrderCandidateEnqueueService;
	@NonNull private final DDOrderLowLevelService ddOrderLowLevelService;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final PostMaterialEventService materialEventService;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public static DDOrderCandidateService newInstanceForUnitTesting()
	{
		final DDOrderCandidateRepository ddOrderCandidateRepository = new DDOrderCandidateRepository();

		final MaterialEventConverter materialEventConverter = new MaterialEventConverter();
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService.createLocalServiceThatIsReadyToUse(
				materialEventConverter,
				PlainEventBusFactory.newInstance(),
				new MaterialEventObserver());
		final PostMaterialEventService postMaterialEventService = new PostMaterialEventService(materialEventService);

		return new DDOrderCandidateService(
				ddOrderCandidateRepository,
				new DDOrderCandidateAllocRepository(),
				new DDOrderCandidateEnqueueService(ddOrderCandidateRepository),
				new DDOrderLowLevelService(new DDOrderLowLevelDAO(), ResourceService.newInstanceForJUnitTesting()),
				new DistributionNetworkRepository(),
				postMaterialEventService,
				new ReplenishInfoRepository()
		);
	}

	public void save(@NonNull final DDOrderCandidate ddOrderCandidate)
	{
		ddOrderCandidateRepository.save(ddOrderCandidate);
	}

	public void enqueueToProcess(@NonNull final DDOrderCandidateId id)
	{
		ddOrderCandidateEnqueueService.enqueueId(id);
	}

	public void enqueueToProcess(@NonNull final Collection<DDOrderCandidateId> ids)
	{
		ddOrderCandidateEnqueueService.enqueueIds(ids);
	}

	public void enqueueToProcess(@NonNull final PInstanceId selectionId)
	{
		ddOrderCandidateEnqueueService.enqueueSelection(selectionId);
	}

	public List<DDOrderCandidate> getBySelectionId(@NonNull final PInstanceId selectionId)
	{
		return ddOrderCandidateRepository.getBySelectionId(selectionId);
	}

	public void process(@NonNull final DDOrderCandidateProcessRequest request)
	{
		newProcessCommand()
				.request(request)
				.build().execute();
	}

	private DDOrderCandidateProcessCommand.DDOrderCandidateProcessCommandBuilder newProcessCommand()
	{
		return DDOrderCandidateProcessCommand.builder()
				.ddOrderLowLevelService(ddOrderLowLevelService)
				.ddOrderCandidateService(this)
				.distributionNetworkRepository(distributionNetworkRepository)
				.materialEventService(materialEventService)
				.replenishInfoRepository(replenishInfoRepository)
				.orgDAO(orgDAO)
				.docTypeDAO(docTypeDAO)
				.documentBL(documentBL)
				.productPlanningDAO(productPlanningDAO)
				.bpartnerOrgBL(bpartnerOrgBL)
				.warehouseBL(warehouseBL)
				.uomConversionBL(uomConversionBL)
				.orderLineBL(orderLineBL);
	}

	public void saveAndUpdateCandidates(@NonNull final DDOrderCandidateAllocList list)
	{
		ddOrderCandidateAllocRepository.save(list);
		updateCandidatesOnAllocationChanges(list.getDDOrderCandidateIds());
	}

	public void deleteAndUpdateCandidatesByDDOrderId(@NonNull final DDOrderId ddOrderId)
	{
		final DDOrderCandidateAllocList list = ddOrderCandidateAllocRepository.getByDDOrderId(ddOrderId);
		deleteAndUpdateCandidates(list);
	}

	private void deleteAndUpdateCandidates(@NonNull final DDOrderCandidateAllocList list)
	{
		if (list.isEmpty())
		{
			return;
		}

		ddOrderCandidateAllocRepository.delete(list);
		updateCandidatesOnAllocationChanges(list.getDDOrderCandidateIds());
	}

	private void updateCandidatesOnAllocationChanges(final Set<DDOrderCandidateId> ddOrderCandidateIds)
	{
		if (ddOrderCandidateIds.isEmpty())
		{
			Loggables.addLog("updateCandidatesOnAllocationChanges: skip because provided IDs set is empty");
			return;
		}

		final ImmutableMap<DDOrderCandidateId, DDOrderCandidate> candidates = Maps.uniqueIndex(ddOrderCandidateRepository.getByIds(ddOrderCandidateIds), DDOrderCandidate::getIdNotNull);
		Loggables.addLog("updateCandidatesOnAllocationChanges: candidates={}", candidates);
		if (candidates.isEmpty())
		{
			return;
		}

		final Map<DDOrderCandidateId, DDOrderCandidateAllocList> allocationsByCandidateId = ddOrderCandidateAllocRepository.getByCandidateIds(candidates.keySet()).groupByCandidateId();

		for (final DDOrderCandidate candidate : candidates.values())
		{
			final DDOrderCandidateAllocList alloc = allocationsByCandidateId.getOrDefault(candidate.getId(), DDOrderCandidateAllocList.EMPTY);
			final Quantity qtyProcessed = alloc.getQtySum().orElseGet(() -> candidate.getQty().toZero());
			candidate.setQtyProcessed(qtyProcessed);
			ddOrderCandidateRepository.save(candidate);
					Loggables.addLog("updateCandidatesOnAllocationChanges: qtyProcessed={}, candidate={}, alloc={}", qtyProcessed, candidate, alloc);
				}
	}

	public List<DDOrderCandidate> list(@NonNull final DDOrderCandidateQuery query)
	{
		return ddOrderCandidateRepository.list(query);
	}
}
