package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Takes over, onto a drop-ship purchase order's compensation group schema, the percentages of the linked sales order's
 * contract discount lines that the vendor's take-overs list.
 */
@Service
@RequiredArgsConstructor
public class ContractSettingsTakeOverService
{
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;
	@NonNull private final OrderGroupRepository orderGroupRepository;
	@NonNull private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	/** @return the schema with each matching take-over merged into, or appended to, its compensation lines; the given schema when nothing is taken over */
	public GroupTemplate applyToSchema(
			@NonNull final GroupTemplate schema,
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final ImmutableList<ContractSettingsTakeOverMatch> matches = computeMatches(order, settings);
		if (matches.isEmpty())
		{
			return schema;
		}

		final List<GroupTemplateCompensationLine> lines = new ArrayList<>(schema.getCompensationLines());
		matches.forEach(match -> mergeOrAppend(lines, match));

		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(lines)
				.build();
	}

	/** Merges into the first percentage discount line on the take-over's category (nominal sum: 3% + 3% is one 6% line), else appends an own line. */
	private void mergeOrAppend(
			@NonNull final List<GroupTemplateCompensationLine> lines,
			@NonNull final ContractSettingsTakeOverMatch match)
	{
		final ProductCategoryId categoryId = match.getTakeOver().getProductCategoryId();
		for (final ListIterator<GroupTemplateCompensationLine> it = lines.listIterator(); it.hasNext(); )
		{
			final GroupTemplateCompensationLine line = it.next();
			final Percent linePercent = line.getPercentage();
			if (linePercent != null && categoryId.equals(line.getAppliesToProductCategoryId()) && isPercentDiscountLine(line))
			{
				it.set(line.toBuilder()
						.percentage(linePercent.add(match.getSummedPercent()))
						.description(createMergedLineDescription(linePercent, line.getProductId(), match))
						.build());
				return;
			}
		}

		final ContractSettingsTakeOver takeOver = match.getTakeOver();
		lines.add(GroupTemplateCompensationLine.builder()
				.productId(takeOver.getOwnLineProductId())
				.compensationType(GroupCompensationType.Discount)
				.percentage(match.getSummedPercent())
				.appliesToProductCategoryId(categoryId)
				.takeOverId(takeOver.getId())
				.description(createOwnLineDescription(match))
				.build());
	}

	/** A line whose product makes it a surcharge or a non-percentage discount would compute 0%, losing the taken-over percentage. */
	private boolean isPercentDiscountLine(@NonNull final GroupTemplateCompensationLine line)
	{
		return compensationLineCreateRequestFactory.isPercentDiscountLine(line.getCompensationType(), line.getProductId());
	}

	/** e.g. {@code "3% Bonus Vendor + 3% Bonus Ware"} */
	private String createMergedLineDescription(
			@NonNull final Percent linePercent,
			@NonNull final ProductId lineProductId,
			@NonNull final ContractSettingsTakeOverMatch match)
	{
		return formatPercent(linePercent) + " " + productBL.getProductName(lineProductId)
				+ " + " + createOwnLineDescription(match);
	}

	/** e.g. {@code "3% Bonus Ware"} */
	private String createOwnLineDescription(@NonNull final ContractSettingsTakeOverMatch match)
	{
		final String takenOverProductNames = productBL.getProductNames(match.getTakenOverProductIds())
				.values()
				.stream()
				.sorted()
				.collect(Collectors.joining(", "));
		return formatPercent(match.getSummedPercent()) + " " + takenOverProductNames;
	}

	private static String formatPercent(@NonNull final Percent percent)
	{
		return percent.toBigDecimal().toPlainString() + "%";
	}

	/**
	 * @return one match per take-over whose customer discount products are on the percentage discount lines of the linked
	 * sales order's contract-created groups, with their nominal percentages summed; take-overs summing to 0 are dropped
	 */
	@VisibleForTesting
	ImmutableList<ContractSettingsTakeOverMatch> computeMatches(
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final OrderId salesOrderId = order.getDropShipLinkedSalesOrderId().orElse(null);
		if (salesOrderId == null)
		{
			return ImmutableList.of();
		}

		final ImmutableList<GroupCompensationLine> salesOrderDiscountLines = orderGroupRepository.retrieveContractCreatedGroupsByOrderId(salesOrderId)
				.stream()
				.flatMap(group -> group.getCompensationLines().stream())
				.filter(line -> GroupCompensationLineCreateRequestFactory.isPercentDiscount(line.getType(), line.getAmtType()))
				.collect(ImmutableList.toImmutableList());

		return takeOverRepository.getBySettingsId(settings.getSettingsId())
				.stream()
				.map(takeOver -> computeMatch(takeOver, salesOrderDiscountLines))
				.filter(match -> !match.getSummedPercent().isZero())
				.collect(ImmutableList.toImmutableList());
	}

	private static ContractSettingsTakeOverMatch computeMatch(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final List<GroupCompensationLine> salesOrderDiscountLines)
	{
		Percent sum = Percent.ZERO;
		final ImmutableSet.Builder<ProductId> takenOverProductIds = ImmutableSet.builder();
		for (final GroupCompensationLine line : salesOrderDiscountLines)
		{
			if (takeOver.getCustomerDiscountProductIds().contains(line.getProductId()) && !line.getPercentage().isZero())
			{
				sum = sum.add(line.getPercentage());
				takenOverProductIds.add(line.getProductId());
			}
		}
		return new ContractSettingsTakeOverMatch(takeOver, sum, takenOverProductIds.build());
	}
}
