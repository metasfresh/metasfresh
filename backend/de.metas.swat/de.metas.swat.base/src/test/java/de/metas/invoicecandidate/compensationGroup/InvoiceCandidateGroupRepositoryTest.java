package de.metas.invoicecandidate.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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
 * Tests for {@link InvoiceCandidateGroupRepository}: a discount invoice candidate linked (via its
 * {@code C_OrderLine.C_CompensationGroup_SchemaLine_ID}) to a base product category shall have its
 * percentage recomputed against only the regular invoice candidates whose product falls under that
 * base -- not against the whole group.
 */
class InvoiceCandidateGroupRepositoryTest
{
	private UomId uomId;
	private InvoiceCandidateGroupRepository repo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		repo = new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class));
	}

	@Test
	void discountInvoiceCandidate_recomputesOnlyAgainstInBaseRegularCandidates()
	{
		// order
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// categories: goodsCategory is the schema line's base; pfandCategory is outside the base
		final I_M_Product_Category goodsCategory = newInstance(I_M_Product_Category.class);
		saveRecord(goodsCategory);
		final ProductCategoryId goodsCategoryId = ProductCategoryId.ofRepoId(goodsCategory.getM_Product_Category_ID());

		final I_M_Product_Category pfandCategory = newInstance(I_M_Product_Category.class);
		saveRecord(pfandCategory);

		// products
		final I_M_Product goodsProduct = newInstance(I_M_Product.class);
		goodsProduct.setC_UOM_ID(uomId.getRepoId());
		goodsProduct.setM_Product_Category_ID(goodsCategoryId.getRepoId());
		saveRecord(goodsProduct);

		final I_M_Product pfandProduct = newInstance(I_M_Product.class);
		pfandProduct.setC_UOM_ID(uomId.getRepoId());
		pfandProduct.setM_Product_Category_ID(pfandCategory.getM_Product_Category_ID());
		saveRecord(pfandProduct);

		final I_M_Product discountProduct = newInstance(I_M_Product.class);
		discountProduct.setC_UOM_ID(uomId.getRepoId());
		saveRecord(discountProduct);

		// schema (IsAdditive=N, i.e. compounding -- irrelevant here, single compensation line) with a schema
		// line whose base = goodsCategory
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		schema.setIsAdditive(false);
		saveRecord(schema);

		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		schemaLine.setM_Product_Category_ID(goodsCategoryId.getRepoId());
		schemaLine.setM_Product_ID(discountProduct.getM_Product_ID());
		saveRecord(schemaLine);

		// order compensation group header, linked to the schema
		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(groupHeader);
		final int orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		// the "commercial" order line behind the discount IC, linked to the schema line
		final I_C_OrderLine compensationOrderLine = newInstance(I_C_OrderLine.class);
		compensationOrderLine.setC_Order_ID(order.getC_Order_ID());
		compensationOrderLine.setM_Product_ID(discountProduct.getM_Product_ID());
		compensationOrderLine.setC_UOM_ID(uomId.getRepoId());
		compensationOrderLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		compensationOrderLine.setIsGroupCompensationLine(true);
		compensationOrderLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		saveRecord(compensationOrderLine);

		// regular IC: goods, net 1000 -- inside the base
		final I_C_Invoice_Candidate goodsIc = newInstance(I_C_Invoice_Candidate.class);
		goodsIc.setC_Order_ID(order.getC_Order_ID());
		goodsIc.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		goodsIc.setM_Product_ID(goodsProduct.getM_Product_ID());
		goodsIc.setNetAmtToInvoice(new BigDecimal("1000"));
		saveRecord(goodsIc);

		// regular IC: Pfand, net 200 -- outside the base
		final I_C_Invoice_Candidate pfandIc = newInstance(I_C_Invoice_Candidate.class);
		pfandIc.setC_Order_ID(order.getC_Order_ID());
		pfandIc.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		pfandIc.setM_Product_ID(pfandProduct.getM_Product_ID());
		pfandIc.setNetAmtToInvoice(new BigDecimal("200"));
		saveRecord(pfandIc);

		// discount IC: 3%, linked to the compensation order line (hence to the schema line's base)
		final I_C_Invoice_Candidate discountIc = newInstance(I_C_Invoice_Candidate.class);
		discountIc.setC_Order_ID(order.getC_Order_ID());
		discountIc.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		discountIc.setC_OrderLine_ID(compensationOrderLine.getC_OrderLine_ID());
		discountIc.setM_Product_ID(discountProduct.getM_Product_ID());
		discountIc.setIsGroupCompensationLine(true);
		discountIc.setC_UOM_ID(uomId.getRepoId());
		discountIc.setPrice_UOM_ID(uomId.getRepoId());
		discountIc.setQtyToInvoice(BigDecimal.ONE);
		discountIc.setPriceEntered(BigDecimal.ZERO);
		discountIc.setLineNetAmt(BigDecimal.ZERO);
		discountIc.setGroupCompensationType(X_C_Invoice_Candidate.GROUPCOMPENSATIONTYPE_Discount);
		discountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		discountIc.setGroupCompensationPercentage(new BigDecimal("3"));
		saveRecord(discountIc);

		final GroupId groupId = OrderGroupRepository.createGroupId(orderId, orderCompensationGroupId);

		// exercise
		final Group group = repo.retrieveGroup(groupId);
		group.updateAllCompensationLines();

		// verify: the discount is 3% of the goods (base) line only (1000), not of the whole group (1200)
		assertThat(group.getCompensationLines()).hasSize(1);
		final GroupCompensationLine discountLine = group.getCompensationLines().get(0);
		assertThat(discountLine.getBaseProductCategoryId()).isEqualTo(goodsCategoryId);
		assertThat(discountLine.getBaseAmt()).isEqualByComparingTo("1000");
		assertThat(discountLine.getLineNetAmt()).isEqualByComparingTo("-30.00");
		assertThat(discountLine.getPrice()).isEqualByComparingTo("-30.00");
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Regression: createPartialGroupFromCompensationLine (the manual-percentage-edit path,
	// C_Invoice_Candidate.onGroupCompensationPercentageChanged) must recompute a BASED discount
	// invoice candidate against its stored base amount, not zero. Before the fix, the synthetic
	// aggregated regular line had an empty productCategoryIds while the compensation line now
	// carries a non-null baseProductCategoryId, so Group#getRegularLinesNetAmt(base) filtered it
	// out entirely.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void createPartialGroupFromCompensationLine_basedLine_recomputesAgainstStoredBase()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final ProductCategoryId categoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());

		final I_M_Product discountProduct = newInstance(I_M_Product.class);
		discountProduct.setC_UOM_ID(uomId.getRepoId());
		saveRecord(discountProduct);

		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);

		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		schemaLine.setM_Product_Category_ID(categoryId.getRepoId());
		schemaLine.setM_Product_ID(discountProduct.getM_Product_ID());
		saveRecord(schemaLine);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(groupHeader);
		final int orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		// the "commercial" order line behind the discount IC, linked to the schema line
		final I_C_OrderLine compensationOrderLine = newInstance(I_C_OrderLine.class);
		compensationOrderLine.setC_Order_ID(order.getC_Order_ID());
		compensationOrderLine.setM_Product_ID(discountProduct.getM_Product_ID());
		compensationOrderLine.setC_UOM_ID(uomId.getRepoId());
		compensationOrderLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		compensationOrderLine.setIsGroupCompensationLine(true);
		compensationOrderLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		saveRecord(compensationOrderLine);

		// discount IC carrying a previously-computed base of 1000
		final I_C_Invoice_Candidate discountIc = newInstance(I_C_Invoice_Candidate.class);
		discountIc.setC_Order_ID(order.getC_Order_ID());
		discountIc.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		discountIc.setC_OrderLine_ID(compensationOrderLine.getC_OrderLine_ID());
		discountIc.setM_Product_ID(discountProduct.getM_Product_ID());
		discountIc.setIsGroupCompensationLine(true);
		discountIc.setC_UOM_ID(uomId.getRepoId());
		discountIc.setPrice_UOM_ID(uomId.getRepoId());
		discountIc.setQtyToInvoice(BigDecimal.ONE);
		discountIc.setPriceEntered(new BigDecimal("-100"));
		discountIc.setLineNetAmt(new BigDecimal("-100"));
		discountIc.setGroupCompensationType(X_C_Invoice_Candidate.GROUPCOMPENSATIONTYPE_Discount);
		discountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		discountIc.setGroupCompensationPercentage(BigDecimal.TEN);
		discountIc.setGroupCompensationBaseAmt(new BigDecimal("1000"));
		saveRecord(discountIc);

		// exercise the manual-edit path directly
		final Group group = repo.createPartialGroupFromCompensationLine(discountIc);
		group.updateAllCompensationLines();

		final GroupCompensationLine recomputedLine = group.getCompensationLines().get(0);
		assertThat(recomputedLine.getBaseProductCategoryId()).isEqualTo(categoryId);
		assertThat(recomputedLine.getBaseAmt()).isEqualByComparingTo("1000");
		assertThat(recomputedLine.getPrice()).isEqualByComparingTo("-100.00");
		assertThat(recomputedLine.getLineNetAmt()).isEqualByComparingTo("-100.00");
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// The no-base (manual/no-order-line-link) case must keep working exactly as before (AC16):
	// createPartialGroupFromCompensationLine recomputes against the stored net amount unfiltered.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void createPartialGroupFromCompensationLine_noBase_recomputesAgainstStoredNetAmt()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_M_Product discountProduct = newInstance(I_M_Product.class);
		discountProduct.setC_UOM_ID(uomId.getRepoId());
		saveRecord(discountProduct);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);

		// discount IC, not linked to any order line -> no schema-line link -> base stays null
		final I_C_Invoice_Candidate discountIc = newInstance(I_C_Invoice_Candidate.class);
		discountIc.setC_Order_ID(order.getC_Order_ID());
		discountIc.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		discountIc.setM_Product_ID(discountProduct.getM_Product_ID());
		discountIc.setIsGroupCompensationLine(true);
		discountIc.setC_UOM_ID(uomId.getRepoId());
		discountIc.setPrice_UOM_ID(uomId.getRepoId());
		discountIc.setQtyToInvoice(BigDecimal.ONE);
		discountIc.setPriceEntered(new BigDecimal("-50"));
		discountIc.setLineNetAmt(new BigDecimal("-50"));
		discountIc.setGroupCompensationType(X_C_Invoice_Candidate.GROUPCOMPENSATIONTYPE_Discount);
		discountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		discountIc.setGroupCompensationPercentage(BigDecimal.TEN);
		discountIc.setGroupCompensationBaseAmt(new BigDecimal("500"));
		saveRecord(discountIc);

		final Group group = repo.createPartialGroupFromCompensationLine(discountIc);
		group.updateAllCompensationLines();

		final GroupCompensationLine recomputedLine = group.getCompensationLines().get(0);
		assertThat(recomputedLine.getBaseProductCategoryId()).isNull();
		assertThat(recomputedLine.getBaseAmt()).isEqualByComparingTo("500");
		assertThat(recomputedLine.getPrice()).isEqualByComparingTo("-50.00");
	}
}
