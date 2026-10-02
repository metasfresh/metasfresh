package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

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
 * The pure merge-vs-append decision for a take-over: which schema compensation line (if any) a take-over for a given
 * category merges into. Schema lines leave {@code compensationType} null; their real (effective) type/amt-type come from the
 * product at creation — here modelled by the injected {@code isEffectiveDiscountPercent} predicate. The percentage math,
 * description composition and end-to-end candidate wiring are proven by TS1 ({@code compensationGroupContract_dropshipTakeOver.feature}).
 */
class ContractCompensationGroupServiceTakeOverInjectionTest
{
	private static final ProductCategoryId WARE = ProductCategoryId.ofRepoId(101);
	private static final ProductCategoryId VERPACKUNG = ProductCategoryId.ofRepoId(102);

	// stand-ins for products whose effective (product-resolved) compensation type/amt-type differs
	private static final ProductId DISCOUNT_PERCENT_PRODUCT = ProductId.ofRepoId(201); // effective Discount/Percent -> merge target
	private static final ProductId SURCHARGE_PRODUCT = ProductId.ofRepoId(202);        // effective Surcharge -> NOT a merge target
	private static final ProductId NON_PERCENT_PRODUCT = ProductId.ofRepoId(203);      // effective Discount but amtType != Percent -> NOT a merge target

	/** Models the product-resolved effective type: only {@link #DISCOUNT_PERCENT_PRODUCT} is an effective percentage discount. */
	private static final Predicate<GroupTemplateCompensationLine> EFFECTIVE_DISCOUNT_PERCENT =
			line -> DISCOUNT_PERCENT_PRODUCT.equals(line.getProductId());

	/** A schema line as loaded by the repository: {@code compensationType} left null (its real type comes from the product). */
	private static GroupTemplateCompensationLine schemaLine(
			@Nullable final Percent percentage,
			@Nullable final ProductCategoryId categoryId,
			final ProductId productId)
	{
		return GroupTemplateCompensationLine.builder()
				.productId(productId)
				.percentage(percentage)
				.appliesToProductCategoryId(categoryId)
				.build();
	}

	private static int findMergeableLineIndex(final List<GroupTemplateCompensationLine> lines, final ProductCategoryId categoryId)
	{
		return ContractCompensationGroupService.findMergeableLineIndex(lines, categoryId, EFFECTIVE_DISCOUNT_PERCENT);
	}

	@Test
	void mergesIntoThePercentDiscountLineOfTheSameCategory()
	{
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(schemaLine(Percent.of(3), WARE, DISCOUNT_PERCENT_PRODUCT));

		assertThat(findMergeableLineIndex(lines, WARE)).isZero();
	}

	@Test
	void appendsWhenTheSchemaHasNoLineAtAll()
	{
		assertThat(findMergeableLineIndex(ImmutableList.of(), WARE)).isEqualTo(-1);
	}

	@Test
	void appendsWhenTheOnlyCategoryLineHasNoPercentage()
	{
		// a fixed-amount discount line (no percentage) on the category is never a merge target
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(schemaLine(null, WARE, DISCOUNT_PERCENT_PRODUCT));

		assertThat(findMergeableLineIndex(lines, WARE)).isEqualTo(-1);
	}

	@Test
	void appendsWhenThePercentLineIsOnAnotherCategory()
	{
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(schemaLine(Percent.of(3), VERPACKUNG, DISCOUNT_PERCENT_PRODUCT));

		assertThat(findMergeableLineIndex(lines, WARE)).isEqualTo(-1);
	}

	@Test
	void appendsWhenTheCategoryLineProductResolvesToSurcharge()
	{
		// I1: a schema line on the category with a percentage, but whose PRODUCT is configured as a Surcharge, would collapse
		// to 0% at creation -> it must NOT absorb the taken-over percentage; the take-over is appended as an own line instead.
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(schemaLine(Percent.of(3), WARE, SURCHARGE_PRODUCT));

		assertThat(findMergeableLineIndex(lines, WARE)).isEqualTo(-1);
	}

	@Test
	void appendsWhenTheCategoryLineProductResolvesToNonPercentAmtType()
	{
		// same reasoning as the Surcharge case: a non-Percent amt-type line would not carry a percentage discount
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(schemaLine(Percent.of(3), WARE, NON_PERCENT_PRODUCT));

		assertThat(findMergeableLineIndex(lines, WARE)).isEqualTo(-1);
	}

	@Test
	void mergesIntoTheFirstEffectiveDiscountPercentLineSkippingNonMatchingOnes()
	{
		final List<GroupTemplateCompensationLine> lines = ImmutableList.of(
				schemaLine(Percent.of(1), VERPACKUNG, DISCOUNT_PERCENT_PRODUCT), // 0: other category
				schemaLine(null, WARE, DISCOUNT_PERCENT_PRODUCT),               // 1: category, but no percentage
				schemaLine(Percent.of(3), WARE, SURCHARGE_PRODUCT),             // 2: category + percentage, but surcharge-by-product
				schemaLine(Percent.of(3), WARE, DISCOUNT_PERCENT_PRODUCT),      // 3: first effective discount-percent on the category
				schemaLine(Percent.of(5), WARE, DISCOUNT_PERCENT_PRODUCT));     // 4: a later one, ignored

		assertThat(findMergeableLineIndex(lines, WARE)).isEqualTo(3);
	}
}
