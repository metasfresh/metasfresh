package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
 * Gives the vendor's own percentage discount lines of a purchase-order contract schema a description, like the take-over
 * lines have, e.g. {@code "3% Bonus Salemfrucht"}; otherwise two discount lines with the same discount product are told apart
 * only by their amount.
 */
@RequiredArgsConstructor
class PurchaseDiscountLineDescriber
{
	@NonNull private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;
	private final IProductBL productBL = Services.get(IProductBL.class);

	GroupTemplate describe(@NonNull final GroupTemplate schema)
	{
		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(schema.getCompensationLines().stream()
						.map(this::describeIfPercentageDiscount)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private GroupTemplateCompensationLine describeIfPercentageDiscount(@NonNull final GroupTemplateCompensationLine line)
	{
		final Percent percentage = line.getPercentage();
		if (percentage == null || percentage.isZero())
		{
			return line;
		}

		// a schema line leaves its compensation type to its product
		final boolean isPercentageDiscount = line.getCompensationType() != null
				? line.getCompensationType() == GroupCompensationType.Discount
				: compensationLineCreateRequestFactory.isPercentDiscountProduct(line.getProductId());
		if (!isPercentageDiscount)
		{
			return line;
		}

		return line.toBuilder()
				.description(ContractSettingsTakeOverService.formatPercent(percentage) + " " + productBL.getProductName(line.getProductId()))
				.build();
	}
}
