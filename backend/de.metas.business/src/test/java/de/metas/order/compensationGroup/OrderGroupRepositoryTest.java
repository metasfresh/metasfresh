package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLineReasonForWithoutCharge;
import de.metas.order.compensationGroup.GroupRepository.RetrieveOrCreateGroupRequest;
import de.metas.order.compensationGroup.calibration.GroupCalibrations;
import de.metas.order.compensationGroup.calibration.LineCalibration;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.limit.PriceLimitRuleResult;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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
 * Tests for {@link OrderGroupRepository#createRegularLineFromTemplate},
 * specifically the IsWithoutCharge / Reason auto-flagging logic (F00127.1).
 */
public class OrderGroupRepositoryTest
{
	private static final String REASON_TEXT = OrderLineReasonForWithoutCharge.BundleComponent.getCode();

	private UomId uomId;
	private ProductId productId;
	private I_C_Order order;
	private OrderGroupRepository repo;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// UOM
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		// Product
		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		// Order
		order = newInstance(I_C_Order.class);
		saveRecord(order);

		// Register stub IOrderLineBL (concrete class, not Mockito mock, to avoid proxy-cast issues).
		Services.registerService(IOrderLineBL.class, new StubOrderLineBL(order));

		// Build repo (no advisors needed for this test).
		repo = new OrderGroupRepository(
				Mockito.mock(GroupCompensationLineCreateRequestFactory.class),
				Optional.empty());
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 1 — template-line flag=Y → order-line IsWithoutCharge='Y' + Reason set
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void componentLineAutoFlaggedWhenTemplateLineFlagSet()
	{
		final I_C_OrderLine result = repo.createRegularLineFromTemplate(
				buildTemplateLine(true), order, minimalRequest());

		assertThat(result.isWithoutCharge()).isTrue();
		assertThat(result.getReason()).isEqualTo(REASON_TEXT);
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 2 — template-line flag=N → order-line IsWithoutCharge stays false, Reason stays null
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void componentLineNotFlaggedWhenTemplateLineFlagClear()
	{
		final I_C_OrderLine result = repo.createRegularLineFromTemplate(
				buildTemplateLine(false), order, minimalRequest());

		assertThat(result.isWithoutCharge()).isFalse();
		assertThat(result.getReason()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 3 — two template lines (one Y, one N) → flags are independent
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void mixedTemplateLinesProduceMixedFlags()
	{
		final I_C_OrderLine lineWithFlag = repo.createRegularLineFromTemplate(
				buildTemplateLine(true), order, minimalRequest());
		final I_C_OrderLine lineWithoutFlag = repo.createRegularLineFromTemplate(
				buildTemplateLine(false), order, minimalRequest());

		assertThat(lineWithFlag.isWithoutCharge()).isTrue();
		assertThat(lineWithFlag.getReason()).isEqualTo(REASON_TEXT);

		assertThat(lineWithoutFlag.isWithoutCharge()).isFalse();
		assertThat(lineWithoutFlag.getReason()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 4 — compensation order line (IsGroupCompensationLine=true) defaults to IsWithoutCharge=false.
	// The compensation code path (updateOrderLineFromCompensationLine) never touches IsWithoutCharge.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void compensationLineNeverAutoFlagged()
	{
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setIsGroupCompensationLine(true);

		assertThat(compensationLine.isWithoutCharge()).isFalse();
		assertThat(compensationLine.getReason()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 5 — retrieveGroup loads the schema's IsAdditive flag and each line's product-category base
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void retrieveGroup_loadsBaseAndAdditive()
	{
		// retrieveGroup() builds a full Group, which needs a BPartner on the order (unlike the other tests here)
		order.setC_BPartner_ID(1);
		saveRecord(order);

		// categories: parentCategory is the schema line's base; childCategory is the regular line's product's own
		// category (an ancestor of parentCategory); pfandCategory is unrelated (outside the base)
		final I_M_Product_Category parentCategory = newInstance(I_M_Product_Category.class);
		saveRecord(parentCategory);
		final ProductCategoryId parentCategoryId = ProductCategoryId.ofRepoId(parentCategory.getM_Product_Category_ID());

		final I_M_Product_Category childCategory = newInstance(I_M_Product_Category.class);
		childCategory.setM_Product_Category_Parent_ID(parentCategoryId.getRepoId());
		saveRecord(childCategory);
		final ProductCategoryId childCategoryId = ProductCategoryId.ofRepoId(childCategory.getM_Product_Category_ID());

		final I_M_Product_Category pfandCategory = newInstance(I_M_Product_Category.class);
		saveRecord(pfandCategory);

		// products
		final I_M_Product childProductRecord = newInstance(I_M_Product.class);
		childProductRecord.setC_UOM_ID(uomId.getRepoId());
		childProductRecord.setM_Product_Category_ID(childCategoryId.getRepoId());
		saveRecord(childProductRecord);

		final I_M_Product pfandProductRecord = newInstance(I_M_Product.class);
		pfandProductRecord.setC_UOM_ID(uomId.getRepoId());
		pfandProductRecord.setM_Product_Category_ID(pfandCategory.getM_Product_Category_ID());
		saveRecord(pfandProductRecord);

		// schema (IsAdditive=Y) with a schema line whose base = parentCategory
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		schema.setIsAdditive(true);
		saveRecord(schema);

		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		schemaLine.setM_Product_Category_ID(parentCategoryId.getRepoId());
		schemaLine.setM_Product_ID(productId.getRepoId());
		saveRecord(schemaLine);

		// order compensation group header, linked to the schema
		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(groupHeader);
		final int orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		// regular line: product in the child category (an ancestor of which is the base)
		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(childProductRecord.getM_Product_ID());
		regularLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);
		final OrderLineId regularLineId = OrderLineId.ofRepoId(regularLine.getC_OrderLine_ID());

		// Pfand regular line: product outside the base
		final I_C_OrderLine pfandLine = newInstance(I_C_OrderLine.class);
		pfandLine.setC_Order_ID(order.getC_Order_ID());
		pfandLine.setM_Product_ID(pfandProductRecord.getM_Product_ID());
		pfandLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		pfandLine.setLineNetAmt(new BigDecimal("20"));
		saveRecord(pfandLine);

		// compensation line pointing to the schema line
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setC_Order_ID(order.getC_Order_ID());
		compensationLine.setM_Product_ID(productId.getRepoId());
		compensationLine.setC_UOM_ID(uomId.getRepoId());
		compensationLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		compensationLine.setIsGroupCompensationLine(true);
		compensationLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		compensationLine.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		compensationLine.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		compensationLine.setGroupCompensationPercentage(BigDecimal.TEN);
		compensationLine.setQtyEntered(BigDecimal.ONE);
		compensationLine.setPriceEntered(BigDecimal.ZERO);
		compensationLine.setLineNetAmt(BigDecimal.ZERO);
		saveRecord(compensationLine);

		final GroupId groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), orderCompensationGroupId);

		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.isAdditive()).isTrue();

		final GroupCompensationLine loadedCompensationLine = group.getCompensationLines().get(0);
		assertThat(loadedCompensationLine.getAppliesToProductCategoryId()).isEqualTo(parentCategoryId);

		final GroupRegularLine loadedRegularLine = group.getRegularLines().stream()
				.filter(rl -> regularLineId.equals(rl.getRepoId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("regular line not found in group"));
		assertThat(loadedRegularLine.getProductCategoryIds()).contains(childCategoryId, parentCategoryId);
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 6 — fallback branch: a regular line whose product has no entry in the batch-resolved
	// product-category map (e.g. the product record no longer exists) gets an empty
	// productCategoryIds, instead of failing. Direct unit test of the pure mapping method — a
	// genuinely-missing product cannot be round-tripped through retrieveGroup() in this in-memory
	// test store: unlike a real `SELECT ... WHERE id IN (...)`, IProductDAO's bulk lookup
	// (POJOWrapper.loadByIds -> POJOLookupMap) throws for any id it can't find rather than
	// silently omitting it, so the DB-level scenario can't be constructed here; the code path this
	// exercises is exactly what OrderGroupRepository#toGroupRegularLine falls back on.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void toGroupRegularLine_productNotInCategoryMap_hasEmptyProductCategoryIds()
	{
		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(productId.getRepoId());
		regularLine.setLineNetAmt(new BigDecimal("50"));
		saveRecord(regularLine);

		final GroupRegularLine loadedRegularLine = OrderGroupRepository.toGroupRegularLine(regularLine, ImmutableMap.of());

		assertThat(loadedRegularLine.getProductCategoryIds()).isEmpty();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 7 — fallback branch: a manual compensation line (not linked to any schema line) has a
	// null base, same as a group with no schema at all.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void retrieveGroup_manualCompensationLineWithoutSchemaLine_hasNullBase()
	{
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		final int orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		// dedicated category + product for the regular line (the shared `productId` from beforeEach has
		// no category set, since other tests in this file don't need one)
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final I_M_Product regularLineProduct = newInstance(I_M_Product.class);
		regularLineProduct.setC_UOM_ID(uomId.getRepoId());
		regularLineProduct.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(regularLineProduct);

		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(regularLineProduct.getM_Product_ID());
		regularLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);

		// manual compensation line: IsGroupCompensationLine=true, but no C_CompensationGroup_SchemaLine_ID
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setC_Order_ID(order.getC_Order_ID());
		compensationLine.setM_Product_ID(productId.getRepoId());
		compensationLine.setC_UOM_ID(uomId.getRepoId());
		compensationLine.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		compensationLine.setIsGroupCompensationLine(true);
		compensationLine.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		compensationLine.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		compensationLine.setGroupCompensationPercentage(BigDecimal.TEN);
		compensationLine.setQtyEntered(BigDecimal.ONE);
		compensationLine.setPriceEntered(BigDecimal.ZERO);
		compensationLine.setLineNetAmt(BigDecimal.ZERO);
		saveRecord(compensationLine);

		final GroupId groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), orderCompensationGroupId);
		final Group group = repo.retrieveGroup(groupId);

		assertThat(group.getCompensationLines()).hasSize(1);
		assertThat(group.getCompensationLines().get(0).getAppliesToProductCategoryId()).isNull();
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 8 — regression: createPartialGroupFromCompensationLine (the manual-percentage-edit path,
	// C_OrderLine.onGroupCompensationLineChanged -> updateCompensationLineNoSave) must recompute a
	// BASED compensation line against its stored base amount, not zero. Before the fix, the synthetic
	// aggregated regular line had an empty productCategoryIds while the compensation line now carries
	// a non-null appliesToProductCategoryId, so Group#getRegularLinesNetAmt(appliesToProductCategoryId) filtered it out entirely.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void createPartialGroupFromCompensationLine_basedLine_recomputesAgainstStoredBase()
	{
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		final ProductCategoryId categoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());

		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);

		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		schemaLine.setM_Product_Category_ID(categoryId.getRepoId());
		schemaLine.setM_Product_ID(productId.getRepoId());
		saveRecord(schemaLine);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(groupHeader);

		// compensation line, linked to the schema line, carrying a previously-computed base of 1000
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setC_Order_ID(order.getC_Order_ID());
		compensationLine.setM_Product_ID(productId.getRepoId());
		compensationLine.setC_UOM_ID(uomId.getRepoId());
		compensationLine.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		compensationLine.setIsGroupCompensationLine(true);
		compensationLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		compensationLine.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		compensationLine.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		compensationLine.setGroupCompensationPercentage(BigDecimal.TEN);
		compensationLine.setGroupCompensationBaseAmt(new BigDecimal("1000"));
		compensationLine.setQtyEntered(BigDecimal.ONE);
		compensationLine.setPriceEntered(new BigDecimal("-100"));
		compensationLine.setLineNetAmt(new BigDecimal("-100"));
		saveRecord(compensationLine);

		// exercise the manual-edit path directly
		final Group group = repo.createPartialGroupFromCompensationLine(compensationLine);
		group.updateAllCompensationLines();

		final GroupCompensationLine recomputedLine = group.getCompensationLines().get(0);
		assertThat(recomputedLine.getAppliesToProductCategoryId()).isEqualTo(categoryId);
		assertThat(recomputedLine.getBaseAmt()).isEqualByComparingTo("1000");
		assertThat(recomputedLine.getPrice()).isEqualByComparingTo("-100.00");
		assertThat(recomputedLine.getLineNetAmt()).isEqualByComparingTo("-100.00");
	}

	@Test
	void createPartialGroupFromCompensationLine_noBase_recomputesAgainstStoredNetAmt()
	{
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);

		// manual compensation line: IsGroupCompensationLine=true, but no C_CompensationGroup_SchemaLine_ID
		final I_C_OrderLine compensationLine = newInstance(I_C_OrderLine.class);
		compensationLine.setC_Order_ID(order.getC_Order_ID());
		compensationLine.setM_Product_ID(productId.getRepoId());
		compensationLine.setC_UOM_ID(uomId.getRepoId());
		compensationLine.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		compensationLine.setIsGroupCompensationLine(true);
		compensationLine.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
		compensationLine.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		compensationLine.setGroupCompensationPercentage(BigDecimal.TEN);
		compensationLine.setGroupCompensationBaseAmt(new BigDecimal("500"));
		compensationLine.setQtyEntered(BigDecimal.ONE);
		compensationLine.setPriceEntered(new BigDecimal("-50"));
		compensationLine.setLineNetAmt(new BigDecimal("-50"));
		saveRecord(compensationLine);

		final Group group = repo.createPartialGroupFromCompensationLine(compensationLine);
		group.updateAllCompensationLines();

		final GroupCompensationLine recomputedLine = group.getCompensationLines().get(0);
		assertThat(recomputedLine.getAppliesToProductCategoryId()).isNull();
		assertThat(recomputedLine.getBaseAmt()).isEqualByComparingTo("500");
		assertThat(recomputedLine.getPrice()).isEqualByComparingTo("-50.00");
	}

	// ────────────────────────────────────────────────────────────────────────────────────────────
	// Test 9 — a compensation line is priced with PriceEntered == PriceActual, which is only correct
	// while the line carries no discount: saveGroup must reset a non-zero Discount to zero and protect
	// it from the order-line pricing recompute (IsManualDiscount). Uses PriceAndQty (not Percent): it is
	// not covered by the pre-existing setDisallowDiscount guard, unlike percent-type lines.
	// ────────────────────────────────────────────────────────────────────────────────────────────
	@Test
	void saveGroup_resetsStaleDiscountToZero()
	{
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		final int orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		// compensation line carrying a non-zero Discount (e.g. from a manual edit)
		final I_C_OrderLine compensationLinePO = newInstance(I_C_OrderLine.class);
		compensationLinePO.setC_Order_ID(order.getC_Order_ID());
		compensationLinePO.setM_Product_ID(productId.getRepoId());
		compensationLinePO.setC_UOM_ID(uomId.getRepoId());
		compensationLinePO.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		compensationLinePO.setIsGroupCompensationLine(true);
		compensationLinePO.setDiscount(new BigDecimal("15"));
		compensationLinePO.setIsManualDiscount(false);
		saveRecord(compensationLinePO);
		final OrderLineId compensationLineId = OrderLineId.ofRepoId(compensationLinePO.getC_OrderLine_ID());

		final GroupCompensationLine compensationLine = GroupCompensationLine.builder()
				.repoId(compensationLineId)
				.productId(productId)
				.uomId(uomId)
				.type(GroupCompensationType.Discount)
				.amtType(GroupCompensationAmtType.PriceAndQty)
				.qtyEntered(BigDecimal.ONE)
				.price(BigDecimal.TEN)
				.lineNetAmt(BigDecimal.TEN)
				.build();

		final GroupId groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), orderCompensationGroupId);
		final Group group = Group.builder()
				.groupId(groupId)
				.pricePrecision(CurrencyPrecision.TWO)
				.amountPrecision(CurrencyPrecision.TWO)
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.SALES)
				.regularLine(GroupRegularLine.builder().lineNetAmt(new BigDecimal("100")).build())
				.compensationLine(compensationLine)
				.build();

		final OrderLinesStorage storage = repo.createNotSaveableSingleOrderLineStorage(compensationLinePO);

		repo.saveGroup(group, storage);

		assertThat(compensationLinePO.getDiscount()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(compensationLinePO.isManualDiscount()).isTrue();
	}

	@Test
	void retrieveOrCreateGroup_allComponentsLeftOut_isRefusedAsUserValidationError()
	{
		final GroupTemplateRegularLine leftOutLine = buildTemplateLine(1, null);

		assertThatThrownBy(() -> repo.retrieveOrCreateGroup(groupRequest(
				ImmutableList.of(leftOutLine),
				ImmutableMap.of(leftOutLine.getId(), LineCalibration.SKIP))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_AllComponentsLeftOut")
				.matches(AdempiereException::isUserValidationError, "is user validation error");
	}

	@Test
	void retrieveOrCreateGroup_allComponentsLeftOut_whileOtherLineDoesNotMatchContractConditions()
	{
		final GroupTemplateRegularLine leftOutLine = buildTemplateLine(1, null);
		final GroupTemplateRegularLine contractOnlyLine = buildTemplateLine(2, ConditionsId.ofRepoId(42));

		assertThatThrownBy(() -> repo.retrieveOrCreateGroup(groupRequest(
				ImmutableList.of(leftOutLine, contractOnlyLine),
				ImmutableMap.of(
						leftOutLine.getId(), LineCalibration.SKIP,
						contractOnlyLine.getId(), LineCalibration.builder().factor(Percent.ONE_HUNDRED).build()))))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_CompensationGroup_CalibrationRule_AllComponentsLeftOut");
	}

	// ── helpers ─────────────────────────────────────────────────────────────────────────────────

	private RetrieveOrCreateGroupRequest groupRequest(
			final List<GroupTemplateRegularLine> templateLines,
			final ImmutableMap<GroupTemplateRegularLineId, LineCalibration> calibrations)
	{
		return RetrieveOrCreateGroupRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.newGroupTemplate(GroupTemplate.builder()
						.name("test-template")
						.regularLinesToAdd(templateLines)
						.build())
				.calibrations(GroupCalibrations.of(calibrations))
				.build();
	}

	private GroupTemplateRegularLine buildTemplateLine(final boolean isWithoutCharge)
	{
		final I_C_UOM uom = org.adempiere.model.InterfaceWrapperHelper.load(uomId.getRepoId(), I_C_UOM.class);
		return GroupTemplateRegularLine.builder()
				.id(GroupTemplateRegularLineId.ofRepoId(1))
				.productId(productId)
				.qty(Quantity.of(BigDecimal.ONE, uom))
				.isWithoutCharge(isWithoutCharge)
				.build();
	}

	private GroupTemplateRegularLine buildTemplateLine(final int templateLineRepoId, @Nullable final ConditionsId contractConditionsId)
	{
		final I_C_UOM uom = org.adempiere.model.InterfaceWrapperHelper.load(uomId.getRepoId(), I_C_UOM.class);
		return GroupTemplateRegularLine.builder()
				.id(GroupTemplateRegularLineId.ofRepoId(templateLineRepoId))
				.productId(productId)
				.qty(Quantity.of(BigDecimal.ONE, uom))
				.contractConditionsId(contractConditionsId)
				.build();
	}

	private RetrieveOrCreateGroupRequest minimalRequest()
	{
		// newGroupTemplate is @NonNull in the request builder.
		// createRegularLineFromTemplate only reads qtyMultiplier + contractConditionsId from the request.
		final GroupTemplate minimalTemplate = GroupTemplate.builder()
				.name("test-template")
				.regularLinesToAdd(Collections.emptyList())
				.build();
		return RetrieveOrCreateGroupRequest.builder()
				.newGroupTemplate(minimalTemplate)
				.build();
	}

	// ── stubs ────────────────────────────────────────────────────────────────────────────────────

	/**
	 * Minimal IOrderLineBL stub: createOrderLine returns a fresh POJO; save is a no-op.
	 * Using a concrete class (not a Mockito proxy) avoids class-loader/proxy issues with
	 * the Services/TestingClassInstanceProvider infrastructure.
	 */
	private static class StubOrderLineBL implements IOrderLineBL
	{
		private final I_C_Order order;

		StubOrderLineBL(final I_C_Order order) { this.order = order; }

		@Override
		public de.metas.interfaces.I_C_OrderLine createOrderLine(final org.compiere.model.I_C_Order targetOrder)
		{
			final de.metas.interfaces.I_C_OrderLine ol = newInstance(de.metas.interfaces.I_C_OrderLine.class);
			ol.setC_Order_ID(order.getC_Order_ID());
			return ol;
		}

		@Override
		public <T extends de.metas.interfaces.I_C_OrderLine> T createOrderLine(
				final org.compiere.model.I_C_Order targetOrder,
				final Class<T> orderLineClass)
		{
			@SuppressWarnings("unchecked")
			final T result = (T) createOrderLine(targetOrder);
			return result;
		}

		@Override public void save(final org.compiere.model.I_C_OrderLine orderLine) { /* no-op */ }

		// ─── all remaining abstract methods — throw to surface accidental calls ────────────────
		@Override public List<de.metas.interfaces.I_C_OrderLine> getByOrderIds(Set<de.metas.order.OrderId> orderIds) { throw new UnsupportedOperationException(); }
		@Override public List<de.metas.interfaces.I_C_OrderLine> getByIds(Set<OrderLineId> orderLineIds) { throw new UnsupportedOperationException(); }
		@Override public de.metas.interfaces.I_C_OrderLine getOrderLineById(OrderLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public de.metas.interfaces.I_C_OrderLine getOrderLineById(OrderAndLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyEntered(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyOrdered(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyOrdered(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyToDeliver(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public Quantity getQtyDelivered(OrderAndLineId orderAndLineId) { throw new UnsupportedOperationException(); }
		@Override public void setOrder(org.compiere.model.I_C_OrderLine ol, org.compiere.model.I_C_Order order) { throw new UnsupportedOperationException(); }
		@Override public void setTaxAmtInfo(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public void setShipper(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public void updatePriceActual(de.metas.interfaces.I_C_OrderLine orderLine, CurrencyPrecision precision) { throw new UnsupportedOperationException(); }
		@Override public BigDecimal calculatePriceEnteredFromPriceActualAndDiscount(BigDecimal priceActual, BigDecimal discount, int precision) { throw new UnsupportedOperationException(); }
		@Override public TaxCategoryId getTaxCategoryId(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updatePrices(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updatePrices(OrderLinePriceUpdateRequest request) { throw new UnsupportedOperationException(); }
		@Override public IPricingResult computePrices(OrderLinePriceUpdateRequest request) { throw new UnsupportedOperationException(); }
		@Override public PriceLimitRuleResult computePriceLimit(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void setProductId(org.compiere.model.I_C_OrderLine orderLine, ProductId productId, boolean setUomFromProduct) { throw new UnsupportedOperationException(); }
		@Override public I_M_PriceList_Version getPriceListVersion(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateLineNetAmtFromQtyEntered(org.compiere.model.I_C_OrderLine orderLine) { /* no-op */ }
		@Override public void updateLineNetAmtFromQty(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateQtyReserved(de.metas.interfaces.I_C_OrderLine ol) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyEnteredToPriceUOM(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyToPriceUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyToUOM(Quantity qty, org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Quantity convertQtyEnteredToStockUOM(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isTaxIncluded(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getPricePrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getAmountPrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision getTaxPrecision(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void copyOrderLineCounter(org.compiere.model.I_C_OrderLine line, org.compiere.model.I_C_OrderLine fromLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isAllowedCounterLineCopy(org.compiere.model.I_C_OrderLine fromLine) { throw new UnsupportedOperationException(); }
		@Override public ProductPrice getCostPrice(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public ProductPrice getPriceActual(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public PaymentTermId getPaymentTermId(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Map<OrderAndLineId, Quantity> getQtyToDeliver(Collection<OrderAndLineId> orderAndLineIds) { throw new UnsupportedOperationException(); }
		@Override public void updateProductDescriptionFromProductBOMIfConfigured(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void updateProductDocumentNote(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public BigDecimal computeQtyNetPriceFromOrderLine(org.compiere.model.I_C_OrderLine orderLine, Quantity qty) { throw new UnsupportedOperationException(); }
		@Override public CurrencyPrecision extractPricePrecision(org.compiere.model.I_C_OrderLine olRecord) { throw new UnsupportedOperationException(); }
		@Override public void setBPLocation(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public boolean isCatchWeight(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Optional<BPartnerId> getBPartnerId(OrderLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public Optional<BPartnerId> getBPartnerId(OrderAndLineId orderLineId) { throw new UnsupportedOperationException(); }
		@Override public void setTax(org.compiere.model.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public void setGrossWeightInKg(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
		@Override public Money getLineGrossAmt(de.metas.interfaces.I_C_OrderLine orderLine) { throw new UnsupportedOperationException(); }
	}

}
