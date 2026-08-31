/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.reservation.interceptor;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the manual sales order line path: setting or changing the product on an order line must only
 * default a Standard-Packvorschrift ({@code M_HU_PI_Item_Product.IsDefaultForProduct}) when a product
 * price of the order's price list version references it. When none does, the line gets the Virtual PI
 * ("no packing"), which prices correctly.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class C_OrderLineTest
{
	private static final ZonedDateTime DATE = LocalDate.of(2026, 8, 12).atStartOfDay(ZoneId.of("UTC"));

	private C_OrderLine interceptor;

	private ProductId productId;
	private I_M_PriceList_Version priceListVersion;
	private de.metas.interfaces.I_C_OrderLine orderLine;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		interceptor = new C_OrderLine();

		createVirtualPackingInstruction();

		productId = createProduct();
		final PriceListId priceListId = createPriceList();
		priceListVersion = createPriceListVersion(priceListId);
		orderLine = createOrderLine(priceListId);
	}

	//
	// Tests
	//

	@Test
	public void enforceOn_noProductPriceReferencesTheDefault_setsNoPacking()
	{
		setEnforcePrecisePrice(true);
		createDefaultForProductPackingInstruction();
		createProductPrice(null); // a price for the product, but with no packing instruction

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(HUPIItemProductId.VIRTUAL_HU.getRepoId());
	}

	/**
	 * The regression a blanket skip would have caused: this site has no price-list step in front of it, so
	 * suppressing the lookup outright would strip the default from an installation whose product price
	 * legitimately references the Standard-Packvorschrift.
	 */
	@Test
	public void enforceOn_aProductPriceReferencesTheDefault_setsThatDefault()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(piip);

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	@Test
	public void enforceOff_noProductPriceReferencesTheDefault_setsThatDefault()
	{
		setEnforcePrecisePrice(false);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * Not an acceptance criterion — it pins a deliberate choice: when the price list version cannot be
	 * resolved we leave the lookup unrestricted rather than silently strip the packing instruction, so an
	 * incomplete order keeps today's behaviour.
	 */
	@Test
	public void enforceOn_orderHasNoPriceList_keepsTodaysBehaviour()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);
		clearOrderPriceList();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * {@code C_OrderLine.DatePromised} is nullable and a freshly created line commonly has none yet, so
	 * reading it unguarded blew up here — for every caller, whether or not the rule is enforced. The date
	 * falls back to the order header, as the shared price-date resolution does.
	 */
	@Test
	public void lineHasNoDatePromised_enforceOn_fallsBackToTheOrderHeaderDate()
	{
		setEnforcePrecisePrice(true);
		createDefaultForProductPackingInstruction();
		createProductPrice(null);
		clearOrderLineDatePromised();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(HUPIItemProductId.VIRTUAL_HU.getRepoId());
	}

	@Test
	public void lineHasNoDatePromised_enforceOff_fallsBackToTheOrderHeaderDate()
	{
		setEnforcePrecisePrice(false);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);
		clearOrderLineDatePromised();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * The price requirement is scoped to sales order lines — the same scope the quick-input helper applies,
	 * where the IsDefaultForProduct fallback sits behind an {@code SOTrx.SALES} guard. A purchase order line
	 * must keep today's behaviour even with the rule enforced and no product price referencing the default.
	 */
	@Test
	public void purchaseOrder_enforceOn_noProductPriceReferencesTheDefault_stillSetsThatDefault()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);
		makeOrderAPurchaseOrder();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * Neither the line nor the order header carries a DatePromised. An AD {@code Mandatory} flag is only
	 * enforced on persist, so a pre-save interceptor/callout can genuinely see both unset — and must not
	 * throw. With no date there is no price list version to restrict against either, so this falls back
	 * to today's unrestricted behaviour rather than stripping the packing instruction.
	 */
	@Test
	public void noDatePromisedAnywhere_enforceOn_doesNotThrowAndKeepsTodaysBehaviour()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);
		clearOrderLineDatePromised();
		clearOrderDatePromised();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	@Test
	public void noDatePromisedAnywhere_purchaseOrder_doesNotThrowAndKeepsTodaysBehaviour()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);
		clearOrderLineDatePromised();
		clearOrderDatePromised();
		makeOrderAPurchaseOrder();

		interceptor.onProductSetOrChanged(orderLine);

		assertThat(orderLine.getM_HU_PI_Item_Product_ID()).isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	//
	// Fixture
	//

	private void clearOrderDatePromised()
	{
		final I_C_Order order = load(orderLine.getC_Order_ID(), I_C_Order.class);
		order.setDatePromised(null);
		saveRecord(order);
	}

	private void makeOrderAPurchaseOrder()
	{
		final I_C_Order order = load(orderLine.getC_Order_ID(), I_C_Order.class);
		order.setIsSOTrx(false);
		saveRecord(order);
	}

	private void clearOrderLineDatePromised()
	{
		orderLine.setDatePromised(null);
		saveRecord(orderLine);
	}

	private void setEnforcePrecisePrice(final boolean value)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(IHUPIItemProductBL.SYSCONFIG_EnforcePrecisePricePerHUItemProduct);
		sysConfig.setValue(value ? "Y" : "N");
		sysConfig.setIsActive(true);
		saveRecord(sysConfig);
	}

	private ProductId createProduct()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("TestProduct");
		product.setName("Test Product");
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private PriceListId createPriceList()
	{
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setName("Test Sales Price List");
		priceList.setIsSOPriceList(true);
		saveRecord(priceList);
		return PriceListId.ofRepoId(priceList.getM_PriceList_ID());
	}

	private I_M_PriceList_Version createPriceListVersion(@NonNull final PriceListId priceListId)
	{
		final I_M_PriceList_Version plv = newInstance(I_M_PriceList_Version.class);
		plv.setM_PriceList_ID(priceListId.getRepoId());
		plv.setValidFrom(TimeUtil.asTimestamp(DATE.minusYears(1)));
		plv.setIsActive(true);
		saveRecord(plv);
		return plv;
	}

	private de.metas.interfaces.I_C_OrderLine createOrderLine(@NonNull final PriceListId priceListId)
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("Test Partner");
		saveRecord(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setM_PriceList_ID(priceListId.getRepoId());
		order.setIsSOTrx(true);
		order.setDatePromised(TimeUtil.asTimestamp(DATE));
		saveRecord(order);

		final de.metas.interfaces.I_C_OrderLine line = newInstance(de.metas.interfaces.I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		line.setM_Product_ID(productId.getRepoId());
		line.setDatePromised(TimeUtil.asTimestamp(DATE));
		saveRecord(line);
		return line;
	}

	private void clearOrderPriceList()
	{
		final I_C_Order order = load(orderLine.getC_Order_ID(), I_C_Order.class);
		order.setM_PriceList_ID(0);
		saveRecord(order);
	}

	/**
	 * The Virtual PI ("no packing") that {@code retrieveVirtualPIMaterialItemProduct} resolves by its fixed
	 * id — the interceptor falls back to it whenever no Standard-Packvorschrift applies.
	 */
	private void createVirtualPackingInstruction()
	{
		final I_M_HU_PI huPI = newInstance(I_M_HU_PI.class);
		huPI.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		huPI.setName("VirtualPI");
		saveRecord(huPI);

		final I_M_HU_PI_Version huPIVersion = newInstance(I_M_HU_PI_Version.class);
		huPIVersion.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		huPIVersion.setM_HU_PI_ID(huPI.getM_HU_PI_ID());
		huPIVersion.setIsCurrent(true);
		saveRecord(huPIVersion);

		final I_M_HU_PI_Item huPIItem = newInstance(I_M_HU_PI_Item.class);
		huPIItem.setM_HU_PI_Item_ID(HuPackingInstructionsItemId.VIRTUAL.getRepoId());
		huPIItem.setM_HU_PI_Version_ID(huPIVersion.getM_HU_PI_Version_ID());
		huPIItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(huPIItem);

		final I_M_HU_PI_Item_Product piip = newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
		piip.setM_HU_PI_Item_ID(huPIItem.getM_HU_PI_Item_ID());
		piip.setIsInfiniteCapacity(true);
		piip.setIsAllowAnyProduct(true);
		saveRecord(piip);
	}

	/**
	 * The reported shape: a Standard-Packvorschrift with infinite capacity and no business partner,
	 * created so that mobileUI production works.
	 */
	private I_M_HU_PI_Item_Product createDefaultForProductPackingInstruction()
	{
		final I_M_HU_PI huPI = newInstance(I_M_HU_PI.class);
		huPI.setName("Test Frame PI");
		saveRecord(huPI);

		final I_M_HU_PI_Version huPIVersion = newInstance(I_M_HU_PI_Version.class);
		huPIVersion.setM_HU_PI_ID(huPI.getM_HU_PI_ID());
		huPIVersion.setIsCurrent(true);
		saveRecord(huPIVersion);

		final I_M_HU_PI_Item huPIItem = newInstance(I_M_HU_PI_Item.class);
		huPIItem.setM_HU_PI_Version_ID(huPIVersion.getM_HU_PI_Version_ID());
		huPIItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(huPIItem);

		final I_M_HU_PI_Item_Product piip = newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_Product_ID(productId.getRepoId());
		piip.setM_HU_PI_Item_ID(huPIItem.getM_HU_PI_Item_ID());
		piip.setIsDefaultForProduct(true);
		piip.setIsInfiniteCapacity(true);
		piip.setQty(BigDecimal.ZERO);
		piip.setValidFrom(TimeUtil.asTimestamp(DATE.minusYears(1)));
		saveRecord(piip);
		return piip;
	}

	private void createProductPrice(@Nullable final I_M_HU_PI_Item_Product huPiItemProduct)
	{
		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_PriceList_Version_ID(priceListVersion.getM_PriceList_Version_ID());
		productPrice.setM_Product_ID(productId.getRepoId());
		if (huPiItemProduct != null)
		{
			productPrice.setM_HU_PI_Item_Product(huPiItemProduct);
		}
		saveRecord(productPrice);
	}
}
