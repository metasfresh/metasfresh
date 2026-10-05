package de.metas.contracts.compensationGroup.contract;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

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

/** The take-over computation: drop-ship detection and the per-record nominal percentage sum. */
class ContractSettingsTakeOverServiceTest
{
	private static final ProductCategoryId CATEGORY_ID = ProductCategoryId.ofRepoId(101);
	private static final ProductId OWN_PRODUCT_ID = ProductId.ofRepoId(201); // own-line discount product
	private static final ProductId BONUS_WARE_ID = ProductId.ofRepoId(202); // listed
	private static final ProductId BONUS_VERPACKUNG_ID = ProductId.ofRepoId(203); // not listed
	private static final ProductId OTHER_LISTED_ID = ProductId.ofRepoId(204); // listed, on no SO line

	private ContractCompensationGroupSettingsRepository settingsRepository;
	private ContractSettingsTakeOverService service;
	private UomId uomId;
	private ProductId goodsProductId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		settingsRepository = new ContractCompensationGroupSettingsRepository();
		service = new ContractSettingsTakeOverService(new ContractSettingsTakeOverRepository(), OrderGroupRepository.newInstanceForUnitTesting());

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_M_Product_Category goodsCategory = newInstance(I_M_Product_Category.class);
		saveRecord(goodsCategory);
		final I_M_Product goodsProduct = newInstance(I_M_Product.class);
		goodsProduct.setC_UOM_ID(uomId.getRepoId());
		goodsProduct.setM_Product_Category_ID(goodsCategory.getM_Product_Category_ID());
		saveRecord(goodsProduct);
		goodsProductId = ProductId.ofRepoId(goodsProduct.getM_Product_ID());
	}

	@Test
	void dropShipPurchaseOrder_sumsOnlyListedProductsOfLinkedSalesOrder()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakeOver().getListedCustomerProductIds()).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("3.0");
	}

	@Test
	void sumIsNominalArithmeticSum()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(OTHER_LISTED_ID, "3"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("6");
	}

	@Test
	void takenOverProducts_areOnlyTheListedProductsActuallyOnTheSalesOrder()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakenOverProductIds()).containsExactly(BONUS_WARE_ID);
	}

	@Test
	void onlyPercentDiscountLinesOfContractCreatedGroupsAreTakenOver()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, BONUS_VERPACKUNG_ID, OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(BONUS_VERPACKUNG_ID, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, "0"),
				new LineSpec(OTHER_LISTED_ID, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "2"));
		addManualGroupWithDiscountLine(salesOrder, new LineSpec(OTHER_LISTED_ID, "9"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakenOverProductIds()).containsExactly(BONUS_WARE_ID);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("3");
	}

	@Test
	void nonDropShipPurchaseOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(false, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void dropShipSalesOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order linkedSalesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final I_C_Order dropShipSalesOrder = createPurchaseOrder(true, OrderId.ofRepoId(linkedSalesOrder.getC_Order_ID()));
		dropShipSalesOrder.setIsSOTrx(true);
		saveRecord(dropShipSalesOrder);

		assertThat(service.computeMatches(dropShipSalesOrder, settings)).isEmpty();
	}

	@Test
	void dropShipWithoutLinkedOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order purchaseOrder = createPurchaseOrder(true, null);

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void recordWhoseListedProductsAreNotOnTheSalesOrder_isDropped()
	{
		final ContractCompensationGroupSettings settings = createSettings(OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	private ContractCompensationGroupSettings createSettings(final ProductId... listedProductIds)
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(settings);

		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		takeOver.setC_CompensationGroup_ContractSettings_ID(settings.getC_CompensationGroup_ContractSettings_ID());
		takeOver.setM_Product_Category_ID(CATEGORY_ID.getRepoId());
		takeOver.setM_Product_ID(OWN_PRODUCT_ID.getRepoId());
		saveRecord(takeOver);

		for (final ProductId productId : listedProductIds)
		{
			final I_C_CompensationGroup_ContractSettings_TakeOver_Product listed = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
			listed.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
			listed.setM_Product_ID(productId.getRepoId());
			saveRecord(listed);
		}

		return settingsRepository.getBySettingsId(ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID()));
	}

	private static I_C_Order createPurchaseOrder(final boolean dropShip, @Nullable final OrderId linkOrderId)
	{
		final I_C_Order po = newInstance(I_C_Order.class);
		po.setIsSOTrx(false);
		po.setIsDropShip(dropShip);
		po.setLink_Order_ID(OrderId.toRepoId(linkOrderId));
		saveRecord(po);
		return po;
	}

	private I_C_Order createSalesOrderWithLines(final LineSpec... lines)
	{
		final I_C_Order so = newInstance(I_C_Order.class);
		so.setIsSOTrx(true);
		so.setC_BPartner_ID(1);
		saveRecord(so);

		final I_C_Order_CompensationGroup group = createGroupWithRegularLine(so, FlatrateTermId.ofRepoId(5));
		for (final LineSpec spec : lines)
		{
			createCompensationLine(so, group, spec);
		}
		return so;
	}

	private void addManualGroupWithDiscountLine(final I_C_Order so, final LineSpec spec)
	{
		final I_C_Order_CompensationGroup manualGroup = createGroupWithRegularLine(so, null);
		createCompensationLine(so, manualGroup, spec);
	}

	private I_C_Order_CompensationGroup createGroupWithRegularLine(final I_C_Order so, @Nullable final FlatrateTermId flatrateTermId)
	{
		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(so.getC_Order_ID());
		group.setC_Flatrate_Term_ID(flatrateTermId != null ? flatrateTermId.getRepoId() : -1);
		saveRecord(group);

		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(so.getC_Order_ID());
		regularLine.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		regularLine.setM_Product_ID(goodsProductId.getRepoId());
		regularLine.setC_UOM_ID(uomId.getRepoId());
		regularLine.setLine(10);
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);
		return group;
	}

	private void createCompensationLine(final I_C_Order so, final I_C_Order_CompensationGroup group, final LineSpec spec)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(so.getC_Order_ID());
		line.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		line.setM_Product_ID(spec.productId.getRepoId());
		line.setC_UOM_ID(uomId.getRepoId());
		line.setLine(20);
		line.setIsGroupCompensationLine(true);
		line.setGroupCompensationType(spec.compensationType);
		line.setGroupCompensationAmtType(spec.amtType);
		line.setGroupCompensationPercentage(new BigDecimal(spec.percentage));
		saveRecord(line);
	}

	private static final class LineSpec
	{
		final ProductId productId;
		final String compensationType;
		final String amtType;
		final String percentage;

		LineSpec(final ProductId productId, final String percentage)
		{
			this(productId, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, percentage);
		}

		LineSpec(final ProductId productId, final String compensationType, final String amtType, final String percentage)
		{
			this.productId = productId;
			this.compensationType = compensationType;
			this.amtType = amtType;
			this.percentage = percentage;
		}
	}
}
