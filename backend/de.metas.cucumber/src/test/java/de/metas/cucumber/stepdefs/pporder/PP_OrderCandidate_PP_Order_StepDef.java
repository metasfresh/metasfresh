/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class PP_OrderCandidate_PP_Order_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;

	@And("^after not more than (.*)s, PP_OrderCandidate_PP_Order are found$")
	public void validatePP_OrderCandidate_PP_Order(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> StepDefUtil.tryAndWaitForItem(timeoutSec, 500, toPPOrderCandidateQuery(row)));
	}

	private IQuery<I_PP_OrderCandidate_PP_Order> toPPOrderCandidateQuery(final DataTableRow row)
	{
		final PPOrderId ppOrderId = row.getAsIdentifier(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID).lookupIdIn(ppOrderTable);
		final PPOrderCandidateId ppOrderCandidateId = row.getAsIdentifier(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID).lookupIdIn(ppOrderCandidateTable);
		final Quantity qtyEntered = row.getAsQuantity(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered, I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID, uomDAO::getByX12DE355);
		return queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID, ppOrderId)
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateId)
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered, qtyEntered.toBigDecimal())
				.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID, qtyEntered.getUomId())
				.create();
	}

	@And("^after not more than (.*)s, load PP_Order by candidate id: (.*)$")
	public void loadPPOrderByCandidateId(final int timeoutSec, @NonNull final String ppOrderCandidateIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final DataTableRows dataTableRows = DataTableRows.of(dataTable);

		final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidateTable.get(ppOrderCandidateIdentifier);
		assertThat(ppOrderCandidate).as("Missing PP_Order_Candidate for identifier %s", ppOrderCandidateIdentifier).isNotNull();

		final ItemProvider<ImmutableList<I_PP_OrderCandidate_PP_Order>> arePPOrdersCreated = () -> {

			final List<I_PP_OrderCandidate_PP_Order> allocations = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
					.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
					.create()
					.list(I_PP_OrderCandidate_PP_Order.class);

			final boolean allOrdersArePresent = allocations.size() == dataTableRows.size();

			final StringBuilder allocationsLog = new StringBuilder("PP_OrderCandidate_PP_Order records:").append("\n");

			allocations.forEach(allocation -> allocationsLog.append("PP_OrderCandidate_PP_Order.QtyEntered=").append(allocation.getQtyEntered())
					.append("; PP_OrderCandidate_PP_Order.PP_Order_ID=").append(allocation.getPP_Order_ID())
					.append("\n"));

			return allOrdersArePresent
					? ItemProvider.ProviderResult.resultWasFound(ImmutableList.copyOf(allocations))
					: ItemProvider.ProviderResult.resultWasNotFound(
					"Only " + allocations.size() + " orders found! Expecting " + dataTableRows.size() + "\n" + allocationsLog
							+ "PP_OrderCandidate.SeqNo=" + ppOrderCandidate.getSeqNo());
		};

		final Supplier<String> getLogContext = () -> {
			final StringBuilder context = new StringBuilder("Found the following allocations for PP_Order_Candidate_ID: ")
					.append(ppOrderCandidate.getPP_Order_Candidate_ID())
					.append(" (Identifier=").append(ppOrderCandidateIdentifier).append(")");

			queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
					.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMN_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
					.create()
					.stream()
					.forEach(ppOrderCandAlloc -> {
						context.append("\n\tPP_Order_ID=").append(ppOrderCandAlloc.getPP_Order_ID());
						context.append("\n\tQtyOrdered=").append(ppOrderCandAlloc.getQtyEntered());
					});

			return context.toString();
		};

		final ImmutableList<I_PP_OrderCandidate_PP_Order> ppOrderAllocations = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, arePPOrdersCreated, getLogContext);

		loadPPOrders(dataTableRows, ppOrderAllocations);
	}

	/**
	 * Loads the PP_Orders that the given candidates were allocated to, without depending on which candidate was allocated first.
	 * <p>
	 * Candidates of one aggregation group are allocated in {@code PP_Order_Candidate_ID} order (see {@code GeneratePPOrderFromPPOrderCandidate}),
	 * and material-dispo does not pin which of the candidates it splits a demand into is created first.
	 * So when the candidates share a PP_Order, how each single candidate is split differs between runs,
	 * while each PP_Order's allocated quantity and number of allocated candidates do not.
	 * <p>
	 * Each row is matched to a distinct PP_Order by the sum of the given candidates' allocations to it ({@code QtyEntered})
	 * and by how many of the given candidates were allocated to it ({@code NumberOfCandidates}).
	 * The step waits until every row is matched and no other PP_Order was allocated from the given candidates.
	 */
	@And("^after not more than (.*)s, load PP_Orders allocated from candidates: (.*)$")
	public void loadPPOrdersAllocatedFromCandidates(
			final int timeoutSec,
			@NonNull final String ppOrderCandidateIdentifiers,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final ImmutableList<Integer> ppOrderCandidateRepoIds = StepDefUtil.extractIdentifiers(ppOrderCandidateIdentifiers)
				.stream()
				.map(identifier -> ppOrderCandidateTable.get(identifier).getPP_Order_Candidate_ID())
				.collect(ImmutableList.toImmutableList());
		assertThat(ppOrderCandidateRepoIds).as("PP_Order_Candidate identifiers").isNotEmpty();

		final ImmutableList<DataTableRow> rows = DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_PP_Order.COLUMNNAME_PP_Order_ID)
				.toList();

		final ItemProvider<ImmutableMap<StepDefDataIdentifier, PPOrderId>> allocationsMatch = () -> {
			final Map<PPOrderId, List<I_PP_OrderCandidate_PP_Order>> allocationsByPPOrderId = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
					.addInArrayFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidateRepoIds)
					.orderBy(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID)
					.create()
					.stream()
					.collect(Collectors.groupingBy(allocation -> PPOrderId.ofRepoId(allocation.getPP_Order_ID()), LinkedHashMap::new, Collectors.toList()));

			final StringBuilder allocationsLog = new StringBuilder("PP_OrderCandidate_PP_Order records of PP_Order_Candidate_IDs ")
					.append(ppOrderCandidateRepoIds).append(":\n");
			allocationsByPPOrderId.values().stream().flatMap(List::stream)
					.forEach(allocation -> allocationsLog.append("PP_Order_ID=").append(allocation.getPP_Order_ID())
							.append("; PP_Order_Candidate_ID=").append(allocation.getPP_Order_Candidate_ID())
							.append("; QtyEntered=").append(allocation.getQtyEntered())
							.append("\n"));

			final Set<PPOrderId> unmatchedPPOrderIds = new LinkedHashSet<>(allocationsByPPOrderId.keySet());
			final ImmutableMap.Builder<StepDefDataIdentifier, PPOrderId> identifier2PPOrderId = ImmutableMap.builder();
			for (final DataTableRow row : rows)
			{
				final BigDecimal expectedQty = row.getAsBigDecimal(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered);
				final int expectedNumberOfCandidates = row.getAsInt("NumberOfCandidates");

				final PPOrderId matchingPPOrderId = unmatchedPPOrderIds.stream()
						.filter(ppOrderId -> {
							final List<I_PP_OrderCandidate_PP_Order> allocations = allocationsByPPOrderId.get(ppOrderId);
							final BigDecimal allocatedQty = allocations.stream().map(I_PP_OrderCandidate_PP_Order::getQtyEntered).reduce(BigDecimal.ZERO, BigDecimal::add);
							final long numberOfCandidates = allocations.stream().map(I_PP_OrderCandidate_PP_Order::getPP_Order_Candidate_ID).distinct().count();
							return allocatedQty.compareTo(expectedQty) == 0 && numberOfCandidates == expectedNumberOfCandidates;
						})
						.findFirst()
						.orElse(null);
				if (matchingPPOrderId == null)
				{
					return ItemProvider.ProviderResult.resultWasNotFound("No PP_Order with QtyEntered=" + expectedQty + " from " + expectedNumberOfCandidates + " candidate(s) for row " + row + "\n" + allocationsLog);
				}
				unmatchedPPOrderIds.remove(matchingPPOrderId);
				identifier2PPOrderId.put(row.getAsIdentifier(), matchingPPOrderId);
			}

			if (!unmatchedPPOrderIds.isEmpty())
			{
				return ItemProvider.ProviderResult.resultWasNotFound("Unexpected PP_Orders " + unmatchedPPOrderIds + "\n" + allocationsLog);
			}
			return ItemProvider.ProviderResult.resultWasFound(identifier2PPOrderId.build());
		};

		final Supplier<String> logContext = () -> "PP_Orders allocated from candidates " + ppOrderCandidateIdentifiers + " (PP_Order_Candidate_IDs " + ppOrderCandidateRepoIds + ")";
		final ImmutableMap<StepDefDataIdentifier, PPOrderId> identifier2PPOrderId = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, allocationsMatch, logContext);

		identifier2PPOrderId.forEach((identifier, ppOrderId) -> ppOrderTable.putOrReplace(identifier, ppOrderBL.getById(ppOrderId)));
	}

	private void loadPPOrders(@NonNull final DataTableRows dataTable, @NonNull final ImmutableList<I_PP_OrderCandidate_PP_Order> ppOrderAllocations)
	{
		final Set<Integer> alreadySeenAllocRecordIds = new HashSet<>();
		dataTable
				.setAdditionalRowIdentifierColumnName(I_PP_Order.COLUMNNAME_PP_Order_ID)
				.forEach(row -> {
					final BigDecimal qtyEntered = row.getAsBigDecimal(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered);

					final I_PP_OrderCandidate_PP_Order record = ppOrderAllocations
							.stream()
							.filter(ppOrderAlloc -> !alreadySeenAllocRecordIds.contains(ppOrderAlloc.getPP_OrderCandidate_PP_Order_ID()))
							.filter(ppOrderAlloc -> ppOrderAlloc.getQtyEntered().compareTo(qtyEntered) == 0)
							.findFirst()
							.orElse(null);

					if (record == null)
					{
						throw new RuntimeException("No PP_OrderCandidate_PP_Order record found for qtyEntered=" + qtyEntered);
					}

					alreadySeenAllocRecordIds.add(record.getPP_OrderCandidate_PP_Order_ID());

					final PPOrderId ppOrderId = PPOrderId.ofRepoId(record.getPP_Order_ID());
					final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

					ppOrderTable.putOrReplace(row.getAsIdentifier(), ppOrder);
				});
	}
}