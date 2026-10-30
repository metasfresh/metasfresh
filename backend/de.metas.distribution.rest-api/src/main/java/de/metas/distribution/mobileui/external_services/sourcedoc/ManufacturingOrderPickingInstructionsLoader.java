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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Builds, for a whole set of DD_Orders, the caption lines "{doc type} {document no}: {picking instruction}"
 * of every manufacturing order the DD_Orders supply.
 * <p>
 * Batch-loading: the number of queries is constant, i.e. independent of the number of DD_Orders / PP_Orders:
 * <ul>
 *     <li>DD_Order_Candidate_DDOrder: 1 query (by DD_Order_ID)</li>
 *     <li>DD_Order_Candidate: 1 query (by PK)</li>
 *     <li>PP_Order: 1 query (by PK)</li>
 *     <li>PP_Order_BOMLine: 1 query (by PK, via IPPOrderBOMDAO)</li>
 *     <li>C_DocType: one table-cached lookup per distinct C_DocType_ID</li>
 * </ul>
 * PP_Order_BOMLine_Trl has no PickingInstruction column, so the picking instruction is not translated
 * (same result as {@link DistributionSourceDocService#getPickingInstruction}).
 */
@Component
@RequiredArgsConstructor
public class ManufacturingOrderPickingInstructionsLoader
{
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final DDOrderCandidateAllocRepository ddOrderCandidateAllocRepository;
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;

	@NonNull
	public ManufacturingOrderPickingInstructionLines load(@NonNull final Collection<I_DD_Order> ddOrders)
	{
		if (ddOrders.isEmpty())
		{
			return ManufacturingOrderPickingInstructionLines.EMPTY;
		}

		final Map<DDOrderId, Set<PPOrderRef>> refsByDDOrderId = loadPPOrderRefs(ddOrders);
		final Set<PPOrderRef> allRefs = refsByDDOrderId.values().stream().flatMap(Set::stream).collect(ImmutableSet.toImmutableSet());
		final Set<PPOrderId> ppOrderIds = allRefs.stream().map(PPOrderRef::getPpOrderId).collect(ImmutableSet.toImmutableSet());
		if (ppOrderIds.isEmpty())
		{
			return ManufacturingOrderPickingInstructionLines.EMPTY;
		}
		final Set<PPOrderBOMLineId> bomLineIds = allRefs.stream()
				.map(PPOrderRef::getPpOrderBOMLineId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final LineBuilder lineBuilder = new LineBuilder(
				Maps.uniqueIndex(ppOrderBL.getByIds(ppOrderIds), ppOrder -> PPOrderId.ofRepoId(ppOrder.getPP_Order_ID())),
				loadPickingInstructions(bomLineIds));

		final ImmutableListMultimap.Builder<DDOrderId, ITranslatableString> result = ImmutableListMultimap.builder();
		refsByDDOrderId.forEach((ddOrderId, refs) -> result.putAll(ddOrderId, lineBuilder.toLines(refs)));
		return ManufacturingOrderPickingInstructionLines.of(result.build());
	}

	/**
	 * Builds the caption lines from already loaded data (no queries, except the table-cached C_DocType names).
	 */
	@RequiredArgsConstructor
	private class LineBuilder
	{
		@NonNull private final Map<PPOrderId, I_PP_Order> ppOrdersById;
		@NonNull private final Map<PPOrderBOMLineId, String> pickingInstructionsByBOMLineId;
		private final HashMap<DocTypeId, ITranslatableString> docTypeNames = new HashMap<>();

		ImmutableList<ITranslatableString> toLines(@NonNull final Set<PPOrderRef> refs)
		{
			final List<PPOrderRef> sortedRefs = refs.stream()
					.filter(ref -> ppOrdersById.containsKey(ref.getPpOrderId()))
					.sorted(Comparator.comparing(this::getDocumentNo))
					.collect(ImmutableList.toImmutableList());

			final Set<ITranslatableString> lines = new LinkedHashSet<>();
			for (final PPOrderRef ref : sortedRefs)
			{
				lines.add(toLine(ref));
			}
			return ImmutableList.copyOf(lines);
		}

		private String getDocumentNo(final PPOrderRef ref)
		{
			return ppOrdersById.get(ref.getPpOrderId()).getDocumentNo();
		}

		private ITranslatableString toLine(final PPOrderRef ref)
		{
			final I_PP_Order ppOrder = ppOrdersById.get(ref.getPpOrderId());
			final ITranslatableString docTypeName = docTypeNames.computeIfAbsent(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()), docTypeBL::getNameById);
			final ITranslatableString docTypeAndNo = DistributionSourceDocService.toDocTypeAndNoCaption(docTypeName, ppOrder.getDocumentNo());

			final String pickingInstruction = ref.getPpOrderBOMLineId() != null ? pickingInstructionsByBOMLineId.get(ref.getPpOrderBOMLineId()) : null;
			if (pickingInstruction == null || pickingInstruction.trim().isEmpty())
			{
				return docTypeAndNo;
			}
			return TranslatableStrings.builder().append(docTypeAndNo).append(": ").append(pickingInstruction).build();
		}
	}

	/**
	 * @return PP_Order refs (with PP_Order_ID set) per DD_Order. Falls back to the DD_Order header's forward ref if there is none via candidates.
	 */
	private Map<DDOrderId, Set<PPOrderRef>> loadPPOrderRefs(@NonNull final Collection<I_DD_Order> ddOrders)
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

		final Map<DDOrderId, Set<PPOrderRef>> result = new HashMap<>();
		for (final I_DD_Order ddOrder : ddOrders)
		{
			final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrder.getDD_Order_ID());

			final Set<PPOrderRef> refs = new LinkedHashSet<>();
			final DDOrderCandidateAllocList ddOrderAllocs = allocsByDDOrderId.get(ddOrderId);
			if (ddOrderAllocs != null)
			{
				for (final DDOrderCandidateId candidateId : ddOrderAllocs.getDDOrderCandidateIds())
				{
					final DDOrderCandidate candidate = candidatesById.get(candidateId);
					final PPOrderRef ref = candidate != null ? candidate.getForwardPPOrderRef() : null;
					if (ref != null && ref.getPpOrderId() != null)
					{
						refs.add(ref);
					}
				}
			}

			if (refs.isEmpty())
			{
				final PPOrderId headerPPOrderId = PPOrderId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_ID());
				if (headerPPOrderId != null)
				{
					refs.add(PPOrderRef.builder()
							.ppOrderId(headerPPOrderId)
							.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(ddOrder.getForward_PP_Order_BOMLine_ID()))
							.build());
				}
			}

			if (!refs.isEmpty())
			{
				result.put(ddOrderId, refs);
			}
		}

		return result;
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
