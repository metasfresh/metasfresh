package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupTemplateLineId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
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

import javax.annotation.Nullable;
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
 * An invoice candidate whose order line has its own base (no schema line, the category stored in {@code C_OrderLine.GroupCompensation_Product_Category_ID})
 * takes its applies-to product category from that order line column; a schema-backed line keeps the schema line's category.
 */
class InvoiceCandidateGroupRepositoryTakeOverCategoryTest
{
	private static final ProductCategoryId OWN_BASE_CATEGORY_ID = ProductCategoryId.ofRepoId(777);

	private UomId uomId;
	private ProductId discountProductId;
	private I_C_Order order;
	private GroupId groupId;
	private InvoiceCandidateGroupRepository repo;
	private ProductCategoryId goodsCategoryId;
	private IQueryBL queryBLSpy;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy); // before any repository picks up IQueryBL

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_M_Product discountProduct = newInstance(I_M_Product.class);
		discountProduct.setC_UOM_ID(uomId.getRepoId());
		saveRecord(discountProduct);
		discountProductId = ProductId.ofRepoId(discountProduct.getM_Product_ID());

		order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), groupHeader.getC_Order_CompensationGroup_ID());

		// a regular candidate, so the group is valid
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		goodsCategoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());
		final I_M_Product goodsProduct = newInstance(I_M_Product.class);
		goodsProduct.setC_UOM_ID(uomId.getRepoId());
		goodsProduct.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(goodsProduct);
		final I_C_Invoice_Candidate goodsIc = newInstance(I_C_Invoice_Candidate.class);
		goodsIc.setC_Order_ID(order.getC_Order_ID());
		goodsIc.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		goodsIc.setM_Product_ID(goodsProduct.getM_Product_ID());
		goodsIc.setNetAmtToInvoice(new BigDecimal("1000"));
		saveRecord(goodsIc);

		final OrderGroupRepository orderGroupRepository = new OrderGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.empty(),
				new GroupTemplateRepository(Optional.empty()));
		repo = new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class), orderGroupRepository);
	}

	@Test
	void ownBaseLine_takesCategoryFromOrderLine()
	{
		createDiscountCandidate(null, OWN_BASE_CATEGORY_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines()).hasSize(1);
		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(OWN_BASE_CATEGORY_ID);
	}

	@Test
	void severalOwnBaseLines_readTheirOrderLinesWithOneQuery()
	{
		createDiscountCandidate(null, OWN_BASE_CATEGORY_ID);
		createDiscountCandidate(null, goodsCategoryId);
		Mockito.clearInvocations(queryBLSpy);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines())
				.extracting(GroupCompensationLine::getAppliesToProductCategoryId)
				.containsExactlyInAnyOrder(OWN_BASE_CATEGORY_ID, goodsCategoryId);
		Mockito.verify(queryBLSpy, Mockito.times(1)).createQueryBuilder(I_C_OrderLine.class);
	}

	@Test
	void ownBaseLine_hasOwnBase()
	{
		createDiscountCandidate(null, OWN_BASE_CATEGORY_ID);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).hasOwnBase()).isTrue();
	}

	/**
	 * The group has no schema, so it is not additive (compounding); still the own-base line is computed on its category's
	 * full base (1000), not on the base reduced by the preceding fixed-amount line on the same category (1000 - 50).
	 */
	@Test
	void ownBaseLine_notAdditiveGroup_onFullBase_notCompoundedWithFixedAmountLine()
	{
		final I_C_Invoice_Candidate fixedAmountIc = createDiscountCandidate(null, null);
		fixedAmountIc.setLine(10);
		fixedAmountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);
		fixedAmountIc.setPriceEntered(new BigDecimal("-50"));
		saveRecord(fixedAmountIc);
		final I_C_OrderLine fixedAmountOrderLine = fixedAmountIc.getC_OrderLine();
		// the fixed-amount line is on the goods category, like the own line
		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setM_Product_Category_ID(goodsCategoryId.getRepoId());
		schemaLine.setM_Product_ID(discountProductId.getRepoId());
		saveRecord(schemaLine);
		fixedAmountOrderLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		saveRecord(fixedAmountOrderLine);

		final I_C_Invoice_Candidate ownLineIc = createDiscountCandidate(null, goodsCategoryId);
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
		schemaLine.setM_Product_ID(discountProductId.getRepoId());
		saveRecord(schemaLine);
		createDiscountCandidate(GroupTemplateLineId.ofRepoId(schemaLine.getC_CompensationGroup_SchemaLine_ID()), null);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId())
				.isEqualTo(ProductCategoryId.ofRepoId(888));
		assertThat(group.getCompensationLines().get(0).hasOwnBase()).isFalse();
	}

	@Test
	void lineWithoutSchemaLineAndWithoutStoredCategory_hasNoCategory()
	{
		createDiscountCandidate(null, null);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId()).isNull();
	}

	private I_C_Invoice_Candidate createDiscountCandidate(@Nullable final GroupTemplateLineId schemaLineId, @Nullable final ProductCategoryId ownBaseCategoryId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(discountProductId.getRepoId());
		orderLine.setC_UOM_ID(uomId.getRepoId());
		orderLine.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		orderLine.setIsGroupCompensationLine(true);
		orderLine.setC_CompensationGroup_SchemaLine_ID(GroupTemplateLineId.toRepoId(schemaLineId));
		orderLine.setGroupCompensation_Product_Category_ID(ProductCategoryId.toRepoId(ownBaseCategoryId));
		saveRecord(orderLine);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Order_ID(order.getC_Order_ID());
		ic.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setM_Product_ID(discountProductId.getRepoId());
		ic.setIsGroupCompensationLine(true);
		ic.setC_UOM_ID(uomId.getRepoId());
		ic.setPrice_UOM_ID(uomId.getRepoId());
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
