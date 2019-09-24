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

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.order.compensationGroup.GroupCompensationLine.GroupCompensationLineBuilder;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;

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
}
