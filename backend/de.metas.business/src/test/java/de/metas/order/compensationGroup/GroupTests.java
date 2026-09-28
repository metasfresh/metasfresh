package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.order.compensationGroup.GroupCompensationLine.GroupCompensationLineBuilder;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class GroupTests
{
	private int nextSeqNo = 1;

	private static final int C_Order_ID = 123;

	private UomId uomId;

	private ProductId productId;


	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
	}

	@Test
	void updateAllPercentageLines_twoPercentDiscountLines()
	{
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO)
				.amountPrecision(CurrencyPrecision.TWO)
				.bpartnerId(BPartnerId.ofRepoId(3))
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(480).build())
				.regularLine(regularLine(260).build())
				.compensationLine(percentageDiscountLine(30).build())
				.compensationLine(percentageDiscountLine(10).build())
				.build();

		group.updateAllCompensationLines();
		// System.out.println(group);

		//
		// Check compensation line 1: 30%
		{
			final GroupCompensationLine compensationLine = group.getCompensationLines().get(0);
			assertThat(compensationLine.getBaseAmt()).isEqualByComparingTo(BigDecimal.valueOf(480 + 260)); // 740
			assertThat(compensationLine.getQtyEntered()).isEqualByComparingTo(BigDecimal.ONE);
			assertThat(compensationLine.getPrice()).isEqualByComparingTo(new BigDecimal("-222.00")); // - (480+260) * 30%
		}

		//
		// Check compensation line 2: 10%
		{
			final GroupCompensationLine compensationLine = group.getCompensationLines().get(1);
			assertThat(compensationLine.getBaseAmt()).isEqualByComparingTo(BigDecimal.valueOf(480 + 260 - 222)); // 518
			assertThat(compensationLine.getQtyEntered()).isEqualByComparingTo(BigDecimal.ONE);
			assertThat(compensationLine.getPrice()).isEqualByComparingTo(new BigDecimal("-51.80")); // - (480+260-222) * 10%
		}
	}

	@Test
	void addNewCompensationLine()
	{
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO)
				.amountPrecision(CurrencyPrecision.TWO)
				.bpartnerId(BPartnerId.ofRepoId(3))
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(480).build())
				.regularLine(regularLine(260).build())
				.build();

		//
		// Check compensation line 1: 30%
		{
			group.addNewCompensationLine(newPercentageDiscountRequest(30));

			final GroupCompensationLine compensationLine = group.getCompensationLines().get(0);
			assertThat(compensationLine.getBaseAmt()).isEqualByComparingTo(BigDecimal.valueOf(480 + 260)); // 740
			assertThat(compensationLine.getQtyEntered()).isEqualByComparingTo(BigDecimal.ONE);
			assertThat(compensationLine.getPrice()).isEqualByComparingTo(new BigDecimal("-222.00")); // - (480+260) * 30%
		}

		//
		// Check compensation line 2: 10%
		{
			group.addNewCompensationLine(newPercentageDiscountRequest(10));

			final GroupCompensationLine compensationLine = group.getCompensationLines().get(1);
			assertThat(compensationLine.getBaseAmt()).isEqualByComparingTo(BigDecimal.valueOf(480 + 260 - 222)); // 518
			assertThat(compensationLine.getQtyEntered()).isEqualByComparingTo(BigDecimal.ONE);
			assertThat(compensationLine.getPrice()).isEqualByComparingTo(new BigDecimal("-51.80")); // - (480+260-222) * 10%
		}

	}

	private GroupRegularLine.GroupRegularLineBuilder regularLine(int lineNetAmt)
	{
		return GroupRegularLine.builder().lineNetAmt(BigDecimal.valueOf(lineNetAmt));
	}

	private GroupRegularLine regularLine(final int lineNetAmt, @NonNull final ProductCategoryId... productCategoryIds)
	{
		return regularLine(lineNetAmt)
				.productCategoryIds(ImmutableSet.copyOf(productCategoryIds))
				.build();
	}

	private GroupCompensationLineBuilder percentageDiscountLine(final int discountPerc)
	{
		final int seqNo = nextSeqNo++;
		return GroupCompensationLine.builder()
				.seqNo(seqNo)
				.type(GroupCompensationType.Discount)
				.amtType(GroupCompensationAmtType.Percent)
				.percentage(Percent.of(discountPerc))
				// does not matter but needs to be filled
				.productId(productId)
				.uomId(uomId);
	}

	private GroupCompensationLineCreateRequest newPercentageDiscountRequest(final int discountPerc)
	{
		return GroupCompensationLineCreateRequest.builder()
				.type(GroupCompensationType.Discount)
				.amtType(GroupCompensationAmtType.Percent)
				.percentage(Percent.of(discountPerc))
				// does not matter but needs to be filled
				.productId(productId)
				.uomId(uomId)
				.build();
	}

	private GroupCompensationLineCreateRequest newPercentageDiscountRequest(final double discountPerc, @Nullable final ProductCategoryId appliesToProductCategoryId)
	{
		return GroupCompensationLineCreateRequest.builder()
				.type(GroupCompensationType.Discount)
				.amtType(GroupCompensationAmtType.Percent)
				.percentage(Percent.of(BigDecimal.valueOf(discountPerc)))
				.appliesToProductCategoryId(appliesToProductCategoryId)
				// does not matter but needs to be filled
				.productId(productId)
				.uomId(uomId)
				.build();
	}

	private GroupCompensationLineCreateRequest newFixedAmountRequest(
			@NonNull final BigDecimal price,
			@NonNull final BigDecimal qtyEntered,
			@Nullable final ProductCategoryId appliesToProductCategoryId)
	{
		return GroupCompensationLineCreateRequest.builder()
				.type(GroupCompensationType.Discount)
				.amtType(GroupCompensationAmtType.PriceAndQty)
				.price(price)
				.qtyEntered(qtyEntered)
				.appliesToProductCategoryId(appliesToProductCategoryId)
				// does not matter but needs to be filled
				.productId(productId)
				.uomId(uomId)
				.build();
	}

	@Test
	void additive_twoLinesSameBase_eachOnBase()
	{
		final ProductCategoryId goods = ProductCategoryId.ofRepoId(10);
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.additive(true)
				.regularLine(regularLine(1000, goods))
				.regularLine(regularLine(200, ProductCategoryId.ofRepoId(20))) // Pfand, outside base
				.build();
		group.addNewCompensationLine(newPercentageDiscountRequest(3.15, goods));
		group.addNewCompensationLine(newPercentageDiscountRequest(0.25, goods));
		group.updateAllCompensationLines();

		assertThat(group.getCompensationLines()).extracting(GroupCompensationLine::getLineNetAmt)
				.containsExactly(new BigDecimal("-31.50"), new BigDecimal("-2.50"));
	}

	@Test
	void notAdditive_withoutBase_compoundsAsToday()
	{
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000).build())
				.build();
		group.addNewCompensationLine(newPercentageDiscountRequest(10, null));
		group.addNewCompensationLine(newPercentageDiscountRequest(10, null));
		group.updateAllCompensationLines();

		assertThat(group.getCompensationLines()).extracting(GroupCompensationLine::getLineNetAmt)
				.containsExactly(new BigDecimal("-100.00"), new BigDecimal("-90.00"));
	}

	@Test
	void base_includesSubCategory()
	{
		final ProductCategoryId parent = ProductCategoryId.ofRepoId(10);
		final ProductCategoryId child = ProductCategoryId.ofRepoId(11);

		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000, child, parent))
				.build();

		assertThat(group.getRegularLinesNetAmt(parent)).isEqualByComparingTo(BigDecimal.valueOf(1000));
	}

	@Test
	void notAdditive_withBase_compoundsWithinBase()
	{
		final ProductCategoryId goods = ProductCategoryId.ofRepoId(10);
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000, goods))
				.regularLine(regularLine(200, ProductCategoryId.ofRepoId(20))) // Pfand, outside base
				.build();
		group.addNewCompensationLine(newPercentageDiscountRequest(3.0, goods));

		// provisional amount computed by addNewCompensationLine alone, before updateAllCompensationLines recomputes it —
		// this is the value C_Order_AddDiscountCompensationLine persists right after adding the line
		assertThat(group.getCompensationLines().get(0).getLineNetAmt()).isEqualByComparingTo(new BigDecimal("-30.00"));

		group.addNewCompensationLine(newPercentageDiscountRequest(0.6, goods));
		group.updateAllCompensationLines();

		assertThat(group.getCompensationLines()).extracting(GroupCompensationLine::getLineNetAmt)
				.containsExactly(new BigDecimal("-30.00"), new BigDecimal("-5.82"));
	}

	@Test
	void fixedAmount_underBase_unaffectedByBase()
	{
		final ProductCategoryId goods = ProductCategoryId.ofRepoId(10);
		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000, goods))
				.build();

		group.addNewCompensationLine(newFixedAmountRequest(new BigDecimal("-5.00"), BigDecimal.ONE, goods));

		final GroupCompensationLine compensationLine = group.getCompensationLines().get(0);
		assertThat(compensationLine.getLineNetAmt()).isEqualByComparingTo(new BigDecimal("-5.00"));

		// a base does not change a fixed-amount line's own value, whatever its baseAmt is
		group.updateAllCompensationLines();
		assertThat(compensationLine.getLineNetAmt()).isEqualByComparingTo(new BigDecimal("-5.00"));
	}

	@Test
	void base_excludesAncestorCategoryOfADifferentLine()
	{
		final ProductCategoryId parent = ProductCategoryId.ofRepoId(10);
		final ProductCategoryId child = ProductCategoryId.ofRepoId(11);

		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000, parent)) // only the parent category, not the child
				.build();

		assertThat(group.getRegularLinesNetAmt(child)).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Test
	void notAdditive_twoDifferentBases_eachOnOwnBase()
	{
		final ProductCategoryId goods = ProductCategoryId.ofRepoId(10);
		final ProductCategoryId packaging = ProductCategoryId.ofRepoId(30);
		final ProductCategoryId pfandCategory = ProductCategoryId.ofRepoId(20);

		final Group group = Group.builder()
				.groupId(GroupId.of(I_C_Order.Table_Name, C_Order_ID, 1))
				.pricePrecision(CurrencyPrecision.TWO).amountPrecision(CurrencyPrecision.TWO)
				.soTrx(SOTrx.SALES)
				.regularLine(regularLine(1000, goods))
				.regularLine(regularLine(500, packaging))
				.regularLine(regularLine(200, pfandCategory)) // Pfand, in neither base
				.build();
		group.addNewCompensationLine(newPercentageDiscountRequest(3.0, goods));
		group.addNewCompensationLine(newPercentageDiscountRequest(0.6, packaging));
		group.updateAllCompensationLines();

		assertThat(group.getCompensationLines()).extracting(GroupCompensationLine::getLineNetAmt)
				.containsExactly(new BigDecimal("-30.00"), new BigDecimal("-3.00"));
	}
}
