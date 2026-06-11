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
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.order.IOrderLineBL;
import de.metas.organization.IOrgDAO;
import de.metas.process.PInstanceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
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
	public static final String SYSCONFIG_DDOrderAggregation_header_bySalesOrderId = "DDOrderAggregation.header.bySalesOrderId";
	public static final String SYSCONFIG_DDOrderAggregation_header_byPPOrderRef = "DDOrderAggregation.header.byPPOrderRef";
	public static final String SYSCONFIG_DDOrderAggregation_line_bySalesOrderLineId = "DDOrderAggregation.line.bySalesOrderLineId";

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
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public static DDOrderCandidateService newInstanceForUnitTesting()
	{
		final DDOrderCandidateRepository ddOrderCandidateRepository = new DDOrderCandidateRepository();

		return new DDOrderCandidateService(
				ddOrderCandidateRepository,
				new DDOrderCandidateAllocRepository(),
				new DDOrderCandidateEnqueueService(ddOrderCandidateRepository),
				new DDOrderLowLevelService(new DDOrderLowLevelDAO())
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
				.orgDAO(orgDAO)
				.docTypeDAO(docTypeDAO)
				.documentBL(documentBL)
				.productPlanningDAO(productPlanningDAO)
				.bpartnerOrgBL(bpartnerOrgBL)
				.warehouseBL(warehouseBL)
				.uomConversionBL(uomConversionBL)
				.orderLineBL(orderLineBL)
				.aggregationConfig(readAggregationConfig());
	}

	private DDOrderCandidateProcessCommand.AggregationConfig readAggregationConfig()
	{
		return DDOrderCandidateProcessCommand.AggregationConfig.builder()
				.aggregateBySalesOrderId(sysConfigBL.getBooleanValue(SYSCONFIG_DDOrderAggregation_header_bySalesOrderId, true))
				.aggregateByPPOrderRef(sysConfigBL.getBooleanValue(SYSCONFIG_DDOrderAggregation_header_byPPOrderRef, true))
				.aggregateBySalesOrderLineId(sysConfigBL.getBooleanValue(SYSCONFIG_DDOrderAggregation_line_bySalesOrderLineId, true))
				.build();
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
			return;
		}

		final ImmutableMap<DDOrderCandidateId, DDOrderCandidate> candidates = Maps.uniqueIndex(ddOrderCandidateRepository.getByIds(ddOrderCandidateIds), DDOrderCandidate::getIdNotNull);
		if (candidates.isEmpty())
		{
			return;
		}

		final Map<DDOrderCandidateId, DDOrderCandidateAllocList> allocationsByCandidateId = ddOrderCandidateAllocRepository.getByCandidateIds(candidates.keySet()).groupByCandidateId();

		for (final DDOrderCandidate candidate : candidates.values())
		{
			final DDOrderCandidateAllocList alloc = allocationsByCandidateId.getOrDefault(candidate.getId(), DDOrderCandidateAllocList.EMPTY);
			final Quantity qtyProcessed = alloc.getQtySum().orElseGet(() -> candidate.getQtyEntered().toZero());
			candidate.setQtyProcessed(qtyProcessed);
			ddOrderCandidateRepository.save(candidate);
		}
	}

	public List<DDOrderCandidate> list(@NonNull final DDOrderCandidateQuery query)
	{
		return ddOrderCandidateRepository.list(query);
	}

	public Collection<DDOrderCandidate> getByIds(final @NonNull Set<DDOrderCandidateId> ids) {return ddOrderCandidateRepository.getByIds(ids);}
}
