package de.metas.distribution.mobileui.external_services.sourcedoc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocList;
import de.metas.distribution.ddordercandidate.DDOrderCandidateAllocRepository;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Order;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Builds, for a whole set of DD_Orders, one caption line per source document the DD_Orders supply:
 * <ul>
 *     <li>manufacturing order: "{doc type} {document no}: {picking instruction}" (without ": ..." if the BOM line has none)</li>
 *     <li>sales order: "{doc type} {document no}" (a sales order has no picking note)</li>
 * </ul>
 * A DD_Order_Candidate that has both a forward manufacturing order and a sales order contributes only the manufacturing order,
 * because the picking note belongs to it. Manufacturing orders are listed first, then sales orders, each sorted by document no.
 * <p>
 * Batch-loading: the number of queries is constant, i.e. independent of the number of DD_Orders / source documents:
 * <ul>
 *     <li>DD_Order_Candidate_DDOrder: 1 query (by DD_Order_ID)</li>
 *     <li>DD_Order_Candidate: 1 query (by PK)</li>
 *     <li>PP_Order: 1 query (by PK)</li>
 *     <li>PP_Order_BOMLine: 1 query (by PK, via IPPOrderBL)</li>
 *     <li>C_Order: 1 query (by PK)</li>
 *     <li>C_DocType: one table-cached lookup per distinct C_DocType_ID</li>
 * </ul>
 * PP_Order_BOMLine_Trl has no PickingInstruction column, so the picking instruction is not translated
 * (same result as {@link DistributionSourceDocService#getPickingInstruction}).
 */
@Component
@RequiredArgsConstructor
public class SourceDocPickingNotesLoader
{
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;

	@NonNull
	public SourceDocPickingNoteLines load(@NonNull final Collection<I_DD_Order> ddOrders)
	{
		if (ddOrders.isEmpty())
		{
			return SourceDocPickingNoteLines.EMPTY;
		}

		final Map<DDOrderId, SourceDocRefs> refsByDDOrderId = loadSourceDocRefs(ddOrders);
		final Set<PPOrderRef> allPPOrderRefs = refsByDDOrderId.values().stream().flatMap(refs -> refs.ppOrderRefs.stream()).collect(ImmutableSet.toImmutableSet());
		final Set<PPOrderId> ppOrderIds = allPPOrderRefs.stream().map(PPOrderRef::getPpOrderId).collect(ImmutableSet.toImmutableSet());
		final Set<OrderId> salesOrderIds = refsByDDOrderId.values().stream().flatMap(refs -> refs.salesOrderIds.stream()).collect(ImmutableSet.toImmutableSet());
		if (ppOrderIds.isEmpty() && salesOrderIds.isEmpty())
		{
			return SourceDocPickingNoteLines.EMPTY;
		}
		final Set<PPOrderBOMLineId> bomLineIds = allPPOrderRefs.stream()
				.map(PPOrderRef::getPpOrderBOMLineId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final LineBuilder lineBuilder = new LineBuilder(
				ppOrderIds.isEmpty() ? ImmutableMap.of() : Maps.uniqueIndex(ppOrderBL.getByIds(ppOrderIds), ppOrder -> PPOrderId.ofRepoId(ppOrder.getPP_Order_ID())),
				loadPickingInstructions(bomLineIds),
				salesOrderIds.isEmpty() ? ImmutableMap.of() : Maps.uniqueIndex(orderDAO.getByIds(salesOrderIds), order -> OrderId.ofRepoId(order.getC_Order_ID())));

		final ImmutableListMultimap.Builder<DDOrderId, ITranslatableString> result = ImmutableListMultimap.builder();
		refsByDDOrderId.forEach((ddOrderId, refs) -> result.putAll(ddOrderId, lineBuilder.toLines(refs)));
		return SourceDocPickingNoteLines.of(result.build());
	}

	/**
	 * The source documents of one DD_Order.
	 */
	private static class SourceDocRefs
	{
		private final Set<PPOrderRef> ppOrderRefs = new LinkedHashSet<>();
		private final Set<OrderId> salesOrderIds = new LinkedHashSet<>();

		boolean isEmpty() {return ppOrderRefs.isEmpty() && salesOrderIds.isEmpty();}
	}

	/**
	 * Builds the caption lines from already loaded data (no queries, except the table-cached C_DocType names).
	 */
	@RequiredArgsConstructor
	private class LineBuilder
	{
		@NonNull private final Map<PPOrderId, I_PP_Order> ppOrdersById;
		@NonNull private final Map<PPOrderBOMLineId, String> pickingInstructionsByBOMLineId;
		@NonNull private final Map<OrderId, I_C_Order> salesOrdersById;
		private final HashMap<DocTypeId, ITranslatableString> docTypeNames = new HashMap<>();

		ImmutableList<ITranslatableString> toLines(@NonNull final SourceDocRefs refs)
		{
			final Set<ITranslatableString> lines = new LinkedHashSet<>();
			refs.ppOrderRefs.stream()
					.filter(ref -> ppOrdersById.containsKey(ref.getPpOrderId()))
					.sorted(Comparator.comparing(this::getDocumentNo))
					.forEach(ref -> lines.add(toLine(ref)));
			refs.salesOrderIds.stream()
					.map(salesOrdersById::get)
					.filter(Objects::nonNull)
					.sorted(Comparator.comparing(I_C_Order::getDocumentNo))
					.forEach(salesOrder -> lines.add(toDocTypeAndNo(DocTypeId.ofRepoId(salesOrder.getC_DocType_ID()), salesOrder.getDocumentNo())));
			return ImmutableList.copyOf(lines);
		}

		private ITranslatableString toDocTypeAndNo(@NonNull final DocTypeId docTypeId, @NonNull final String documentNo)
		{
			final ITranslatableString docTypeName = docTypeNames.computeIfAbsent(docTypeId, docTypeBL::getNameById);
			return DistributionSourceDocService.toDocTypeAndNoCaption(docTypeName, documentNo);
		}

		private String getDocumentNo(final PPOrderRef ref)
		{
			return ppOrdersById.get(ref.getPpOrderId()).getDocumentNo();
		}

		private ITranslatableString toLine(final PPOrderRef ref)
		{
			final I_PP_Order ppOrder = ppOrdersById.get(ref.getPpOrderId());
			final ITranslatableString docTypeAndNo = toDocTypeAndNo(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()), ppOrder.getDocumentNo());

			final String pickingInstruction = ref.getPpOrderBOMLineId() != null ? pickingInstructionsByBOMLineId.get(ref.getPpOrderBOMLineId()) : null;
			if (pickingInstruction == null || pickingInstruction.trim().isEmpty())
			{
				return docTypeAndNo;
			}
			return TranslatableStrings.builder().append(docTypeAndNo).append(": ").append(pickingInstruction).build();
		}
	}

	/**
	 * @return the source documents per DD_Order. Falls back to the DD_Order header's refs if there are none via candidates.
	 */
	private Map<DDOrderId, SourceDocRefs> loadSourceDocRefs(@NonNull final Collection<I_DD_Order> ddOrders)
	{
		final Set<DDOrderId> ddOrderIds = ddOrders.stream()
				.map(ddOrder -> DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final DDOrderCandidateAllocList allocs = ddOrderCandidateAllocRepository.getByDDOrderIds(ddOrderIds);
		final Map<DDOrderCandidateId, DDOrderCandidate> candidatesById = allocs.isEmpty()
				? ImmutableMap.of()
				: ddOrderCandidateRepository.getByIds(allocs.getDDOrderCandidateIds()).stream()
				.collect(ImmutableMap.toImmutableMap(DDOrderCandidate::getId, Function.identity()));
		final Map<DDOrderId, DDOrderCandidateAllocList> allocsByDDOrderId = allocs.groupByDDOrderId();

		final Map<DDOrderId, SourceDocRefs> result = new HashMap<>();
		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

			final SourceDocRefs refs = new SourceDocRefs();
			final DDOrderCandidateAllocList ddOrderAllocs = allocsByDDOrderId.get(ddOrderId);
			if (ddOrderAllocs != null)
			{
				for (final DDOrderCandidateId candidateId : ddOrderAllocs.getDDOrderCandidateIds())
				{
					final DDOrderCandidate candidate = candidatesById.get(candidateId);
					if (candidate != null)
					{
						addRef(refs, candidate.getForwardPPOrderRef(), candidate.getSalesOrderLineId() != null ? candidate.getSalesOrderLineId().getOrderId() : null);
					}
				}
			}

			// Header-ref fallback, taken per DD_Order here; InTransit_HUs_Report_PPOrders.sql takes it per DD_OrderLine.
			// Same result for aggregated and single-order DD_Orders: both have the same forward ref on header and lines.
			if (refs.isEmpty())
			{
				final PPOrderId headerPPOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
				final PPOrderRef headerPPOrderRef = headerPPOrderId != null
						? PPOrderRef.builder()
						.ppOrderId(headerPPOrderId)
						.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_BOMLine_ID()))
						.build()
						: null;
				addRef(refs, headerPPOrderRef, OrderId.ofRepoIdOrNull(ddOrder.getC_Order_ID()));
			}

			if (!refs.isEmpty())
			{
				result.put(ddOrderId, refs);
			}
		}

		return result;
	}

	/**
	 * Adds the manufacturing order if there is one, else the sales order: the picking note belongs to the manufacturing order.
	 */
	private static void addRef(@NonNull final SourceDocRefs refs, @Nullable final PPOrderRef ppOrderRef, @Nullable final OrderId salesOrderId)
	{
		if (ppOrderRef != null && ppOrderRef.getPpOrderId() != null)
		{
			refs.ppOrderRefs.add(ppOrderRef);
		}
		else if (salesOrderId != null)
		{
			refs.salesOrderIds.add(salesOrderId);
		}
	}

	private Map<PPOrderBOMLineId, String> loadPickingInstructions(@NonNull final Set<PPOrderBOMLineId> bomLineIds)
	{
		if (bomLineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return ppOrderBL.getOrderBOMLinesByIds(bomLineIds)
				.stream()
				.filter(bomLine -> bomLine.getPickingInstruction() != null)
				.collect(ImmutableMap.toImmutableMap(
						bomLine -> PPOrderBOMLineId.ofRepoId(bomLine.getPP_Order_BOMLine_ID()),
						I_PP_Order_BOMLine::getPickingInstruction));
	}
}
