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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_OrderCandidate_PP_Order;
import org.eevolution.model.I_PP_Order_Candidate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class PP_OrderCandidate_PP_Order_StepDef
{
	private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	private final PP_Order_StepDefData ppOrderTable;

	public PP_OrderCandidate_PP_Order_StepDef(
			@NonNull final PP_Order_Candidate_StepDefData ppOrderCandidateTable,
			@NonNull final PP_Order_StepDefData ppOrderTable)
	{
		this.ppOrderCandidateTable = ppOrderCandidateTable;
		this.ppOrderTable = ppOrderTable;
	}

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^after not more than (.*)s, PP_OrderCandidate_PP_Order are found$")
	public void validatePP_OrderCandidate_PP_Order(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order orderRecord = ppOrderTable.get(orderIdentifier);

			final String orderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_Candidate orderCandidateRecord = ppOrderCandidateTable.get(orderCandidateIdentifier);

			final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered);

			final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
			final Supplier<Boolean> allocationQueryExecutor = () -> {

				final I_PP_OrderCandidate_PP_Order allocationRecord = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_ID, orderRecord.getPP_Order_ID())
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, orderCandidateRecord.getPP_Order_Candidate_ID())
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered, qtyEntered)
						.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
						.create()
						.firstOnly(I_PP_OrderCandidate_PP_Order.class);

				return allocationRecord != null;
			};

			StepDefUtil.tryAndWait(timeoutSec, 500, allocationQueryExecutor);
		}
	}

	@And("^after not more than (.*)s, load PP_Order by candidate id: (.*)$")
	public void loadPPOrderByCandidateId(final int timeoutSec, @NonNull final String ppOrderCandidateIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidateTable.get(ppOrderCandidateIdentifier);
		assertThat(ppOrderCandidate).as("Missing PP_Order_Candidate for identifier %s", ppOrderCandidateIdentifier).isNotNull();

		final ItemProvider<ImmutableList<I_PP_OrderCandidate_PP_Order>> arePPOrdersCreated = () -> {

			final List<I_PP_OrderCandidate_PP_Order> allocations = queryBL.createQueryBuilder(I_PP_OrderCandidate_PP_Order.class)
					.addEqualsFilter(I_PP_OrderCandidate_PP_Order.COLUMNNAME_PP_Order_Candidate_ID, ppOrderCandidate.getPP_Order_Candidate_ID())
					.create()
					.list(I_PP_OrderCandidate_PP_Order.class);

			final boolean allOrdersArePresent = allocations.size() == dataTable.asMaps().size();

			final StringBuilder allocationsLog = new StringBuilder("PP_OrderCandidate_PP_Order records:").append("\n"); 
			
			allocations.forEach(allocation -> allocationsLog.append("PP_OrderCandidate_PP_Order.QtyEntered=").append(allocation.getQtyEntered())
					.append("; PP_OrderCandidate_PP_Order.PP_Order_ID=").append(allocation.getPP_Order_ID())
					.append("\n"));
			
			return allOrdersArePresent
					? ItemProvider.ProviderResult.resultWasFound(ImmutableList.copyOf(allocations))
					: ItemProvider.ProviderResult.resultWasNotFound(
					"Only " + allocations.size() + " orders found! Expecting " + dataTable.asMaps().size() + "\n" + allocationsLog
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

		loadPPOrders(dataTable, ppOrderAllocations);
	}

	private void loadPPOrders(@NonNull final DataTable dataTable, @NonNull final ImmutableList<I_PP_OrderCandidate_PP_Order> ppOrderAllocations)
	{
		final Set<Integer> alreadySeenAllocRecordIds = new HashSet<>();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_PP_OrderCandidate_PP_Order.COLUMNNAME_QtyEntered);

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

			final I_PP_Order ppOrder = InterfaceWrapperHelper.load(record.getPP_Order_ID(), I_PP_Order.class);

			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(row, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			ppOrderTable.putOrReplace(ppOrderIdentifier, ppOrder);
		}
	}
}