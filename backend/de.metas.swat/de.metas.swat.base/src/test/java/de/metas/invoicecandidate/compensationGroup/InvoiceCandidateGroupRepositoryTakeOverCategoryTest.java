package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.compensationGroup.TakeOverCategoryProvider;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductCategoryId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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
 * An invoice candidate whose order line is an own take-over line (no schema line, but a take-over record id)
 * resolves its applies-to product category from the take-over record; a schema-backed line keeps the schema line's category.
 */
class InvoiceCandidateGroupRepositoryTakeOverCategoryTest
{
	private static final int TAKE_OVER_ID = 540123;
	private static final int TAKE_OVER_CATEGORY_ID = 777;
	/** a take-over record on the goods candidate's own category */
	private static final int GOODS_TAKE_OVER_ID = 540124;

	private int uomId;
	private int discountProductId;
	private I_C_Order order;
	private int orderCompensationGroupId;
	private GroupId groupId;
	private InvoiceCandidateGroupRepository repo;
	private ProductCategoryId goodsCategoryId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = uom.getC_UOM_ID();

		final I_M_Product discountProduct = newInstance(I_M_Product.class);
		discountProduct.setC_UOM_ID(uomId);
		saveRecord(discountProduct);
		discountProductId = discountProduct.getM_Product_ID();

		order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), orderCompensationGroupId);

		// a regular candidate, so the group is valid
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		goodsCategoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());
		final I_M_Product goodsProduct = newInstance(I_M_Product.class);
		goodsProduct.setC_UOM_ID(uomId);
		goodsProduct.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(goodsProduct);
		final I_C_Invoice_Candidate goodsIc = newInstance(I_C_Invoice_Candidate.class);
		goodsIc.setC_Order_ID(order.getC_Order_ID());
		goodsIc.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		goodsIc.setM_Product_ID(goodsProduct.getM_Product_ID());
		goodsIc.setNetAmtToInvoice(new BigDecimal("1000"));
		saveRecord(goodsIc);

		final TakeOverCategoryProvider provider = takeOverId -> {
			if (takeOverId == TAKE_OVER_ID)
			{
				return Optional.of(ProductCategoryId.ofRepoId(TAKE_OVER_CATEGORY_ID));
			}
			if (takeOverId == GOODS_TAKE_OVER_ID)
			{
				return Optional.of(goodsCategoryId);
			}
			return Optional.empty();
		};
		repo = new InvoiceCandidateGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.of(provider));
	}

	@Test
	void ownTakeOverLine_resolvesCategoryFromTakeOverRecord()
	{
		createDiscountCandidate(0, TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines()).hasSize(1);
		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(ProductCategoryId.ofRepoId(TAKE_OVER_CATEGORY_ID));
	}

	@Test
	void ownTakeOverLine_carriesTakeOverId()
	{
		createDiscountCandidate(0, TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getTakeOverId()).isEqualTo(TAKE_OVER_ID);
		assertThat(group.getCompensationLines().get(0).isTakeOverOwnLine()).isTrue();
	}

	/**
	 * The group has no schema, so it is not additive (compounding); still the own take-over line is computed on its category's
	 * full base (1000), not on the base reduced by the preceding fixed-amount line on the same category (1000 - 50).
	 */
	@Test
	void ownTakeOverLine_notAdditiveGroup_onFullBase_notCompoundedWithFixedAmountLine()
	{
		final I_C_Invoice_Candidate fixedAmountIc = createDiscountCandidate(0, 0);
		fixedAmountIc.setLine(10);
		fixedAmountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);
		fixedAmountIc.setPriceEntered(new BigDecimal("-50"));
		saveRecord(fixedAmountIc);
		final I_C_OrderLine fixedAmountOrderLine = fixedAmountIc.getC_OrderLine();
		// the fixed-amount line is on the goods category, like the own line
		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setM_Product_Category_ID(goodsCategoryId.getRepoId());
		schemaLine.setM_Product_ID(discountProductId);
		saveRecord(schemaLine);
		fixedAmountOrderLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		saveRecord(fixedAmountOrderLine);

		final I_C_Invoice_Candidate ownLineIc = createDiscountCandidate(0, GOODS_TAKE_OVER_ID);
		ownLineIc.setLine(20);
		ownLineIc.setGroupCompensationPercentage(new BigDecimal("3"));
		saveRecord(ownLineIc);

		final Group group = repo.retrieveGroup(groupId);
		group.updateAllCompensationLines();

		assertThat(group.isAdditive()).isFalse();
		assertThat(group.getCompensationLines()).extracting(GroupCompensationLine::getLineNetAmt)
				.usingElementComparator(BigDecimal::compareTo)
				.containsExactly(new BigDecimal("-50"), new BigDecimal("-30"));
		assertThat(group.getCompensationLines().get(1).getBaseAmt()).isEqualByComparingTo(new BigDecimal("1000"));
	}

	@Test
	void schemaBackedLine_keepsSchemaLineCategory()
	{
		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setM_Product_Category_ID(888);
		schemaLine.setM_Product_ID(discountProductId);
		saveRecord(schemaLine);
		createDiscountCandidate(schemaLine.getC_CompensationGroup_SchemaLine_ID(), TAKE_OVER_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(ProductCategoryId.ofRepoId(888));
	}

	@Test
	void ownLine_withoutTakeOverId_hasNoCategory()
	{
		createDiscountCandidate(0, 0);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId()).isNull();
	}

	private I_C_Invoice_Candidate createDiscountCandidate(final int schemaLineId, final int takeOverId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(discountProductId);
		orderLine.setC_UOM_ID(uomId);
		orderLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		orderLine.setIsGroupCompensationLine(true);
		orderLine.setC_CompensationGroup_SchemaLine_ID(schemaLineId);
		orderLine.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId);
		saveRecord(orderLine);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Order_ID(order.getC_Order_ID());
		ic.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setM_Product_ID(discountProductId);
		ic.setIsGroupCompensationLine(true);
		ic.setC_UOM_ID(uomId);
		ic.setPrice_UOM_ID(uomId);
		ic.setQtyToInvoice(BigDecimal.ONE);
		ic.setPriceEntered(BigDecimal.ZERO);
		ic.setLineNetAmt(BigDecimal.ZERO);
		ic.setGroupCompensationType(X_C_Invoice_Candidate.GROUPCOMPENSATIONTYPE_Discount);
		ic.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		ic.setGroupCompensationPercentage(BigDecimal.TEN);
		saveRecord(ic);
		return ic;
	}
}
