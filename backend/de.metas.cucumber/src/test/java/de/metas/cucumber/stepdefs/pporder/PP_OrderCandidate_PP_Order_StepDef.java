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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ItemProvider;
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
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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