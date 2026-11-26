package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
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

/** The description of the vendor's own percentage discount lines on a purchase order. */
class PurchaseDiscountLineDescriberTest
{
	private PurchaseDiscountLineDescriber describer;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		describer = new PurchaseDiscountLineDescriber(new GroupCompensationLineCreateRequestFactory());
	}

	@Test
	void schemaLineWithoutType_percentageDiscountProduct_isDescribed()
	{
		final ProductId bonusId = product("Bonus Vendor", null, null); // product defaults: discount, percent

		final GroupTemplateCompensationLine line = describeSingle(line(bonusId, null, new BigDecimal("3")));

		assertThat(line.getDescription()).isEqualTo("3% Bonus Vendor");
	}

	@Test
	void percentageStoredWithTrailingZeros_isDescribedWithoutThem()
	{
		final ProductId bonusId = product("Bonus Vendor", null, null);

		final GroupTemplateCompensationLine line = describeSingle(line(bonusId, null, new BigDecimal("3.00")));

		assertThat(line.getDescription()).isEqualTo("3% Bonus Vendor");
	}

	@Test
	void lineTypedAsDiscount_isDescribed()
	{
		final ProductId bonusId = product("Bonus Vendor", X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, null);

		final GroupTemplateCompensationLine line = describeSingle(line(bonusId, GroupCompensationType.Discount, new BigDecimal("2.5")));

		assertThat(line.getDescription()).isEqualTo("2.5% Bonus Vendor");
	}

	@Test
	void surchargeProduct_isNotDescribed()
	{
		final ProductId surchargeId = product("Surcharge", X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, null);

		final GroupTemplateCompensationLine line = describeSingle(line(surchargeId, null, new BigDecimal("3")));

		assertThat(line.getDescription()).isNull();
	}

	@Test
	void priceAndQtyDiscountProduct_isNotDescribed()
	{
		final ProductId priceAndQtyId = product("Price And Qty Bonus", null, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);

		final GroupTemplateCompensationLine line = describeSingle(line(priceAndQtyId, null, new BigDecimal("3")));

		assertThat(line.getDescription()).isNull();
	}

	@Test
	void zeroOrMissingPercentage_isNotDescribed()
	{
		final ProductId bonusId = product("Bonus Vendor", null, null);

		final GroupTemplate described = describer.describe(schema(
				line(bonusId, null, BigDecimal.ZERO),
				line(bonusId, null, null)));

		assertThat(described.getCompensationLines())
				.extracting(GroupTemplateCompensationLine::getDescription)
				.containsExactly(null, null);
	}

	private GroupTemplateCompensationLine describeSingle(final GroupTemplateCompensationLine line)
	{
		final GroupTemplate described = describer.describe(schema(line));
		assertThat(described.getCompensationLines()).hasSize(1);
		return described.getCompensationLines().get(0);
	}

	private static GroupTemplate schema(final GroupTemplateCompensationLine... lines)
	{
		return GroupTemplate.builder()
				.name("vendor schema")
				.regularLinesToAdd(ImmutableList.of())
				.compensationLines(ImmutableList.copyOf(lines))
				.build();
	}

	private static GroupTemplateCompensationLine line(
			final ProductId productId,
			@Nullable final GroupCompensationType type,
			@Nullable final BigDecimal percentage)
	{
		return GroupTemplateCompensationLine.builder()
				.productId(productId)
				.compensationType(type)
				.percentage(percentage != null ? Percent.of(percentage) : null)
				.build();
	}

	private static ProductId product(final String name, @Nullable final String compensationType, @Nullable final String amtType)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setGroupCompensationType(compensationType);
		product.setGroupCompensationAmtType(amtType);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}
}
