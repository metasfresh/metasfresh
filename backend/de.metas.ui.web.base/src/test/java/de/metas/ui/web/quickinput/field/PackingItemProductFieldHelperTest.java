/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.quickinput.field;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.lang.SOTrx;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
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

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the rule that a Standard-Packvorschrift ({@code M_HU_PI_Item_Product.IsDefaultForProduct}) is
 * only offered as the sales order line quick-entry default when a product price of the order's price list
 * version references it.
 * <p>
 * Note on the fixture: the criteria carry an explicit {@code priceListId}. Resolving a price list from the
 * pricing system + partner country is a separate concern of {@code IPriceListDAO} and is not what these
 * tests are about — the branch under test sits downstream of that resolution.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PackingItemProductFieldHelperTest
{
	private static final ZonedDateTime DATE = LocalDate.of(2026, 8, 12).atStartOfDay(ZoneId.of("UTC"));

	private PackingItemProductFieldHelper helper;

	private ClientId clientId;
	private ProductId productId;
	private BPartnerLocationAndCaptureId bpartnerLocationId;
	private PriceListId priceListId;
	private I_M_PriceList_Version priceListVersion;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new PackingItemProductFieldHelper();

		clientId = ClientId.ofRepoId(1000000);
		productId = createProduct();
		bpartnerLocationId = createBPartnerLocation();
		priceListId = createPriceList();
		priceListVersion = createPriceListVersion(priceListId);
	}

	//
	// Tests
	//

	@Test
	public void salesOrder_enforceOn_noProductPriceReferencesTheDefault_returnsEmpty()
	{
		setEnforcePrecisePrice(true);
		createDefaultForProductPackingInstruction();
		createProductPrice(null); // a price for the product, but with no packing instruction

		assertThat(helper.getDefaultPackingMaterial(criteria(SOTrx.SALES))).isEmpty();
	}

	@Test
	public void salesOrder_enforceOff_noProductPriceReferencesTheDefault_returnsTheDefault()
	{
		setEnforcePrecisePrice(false);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(null);

		assertThat(helper.getDefaultPackingMaterial(criteria(SOTrx.SALES)))
				.get()
				.extracting(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	@Test
	public void salesOrder_enforceOn_aProductPriceReferencesThePackingInstruction_returnsIt()
	{
		setEnforcePrecisePrice(true);
		final I_M_HU_PI_Item_Product piip = createDefaultForProductPackingInstruction();
		createProductPrice(piip);

		assertThat(helper.getDefaultPackingMaterial(criteria(SOTrx.SALES)))
				.get()
				.extracting(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.isEqualTo(piip.getM_HU_PI_Item_Product_ID());
	}

	@Test
	public void purchaseOrder_enforceOn_neverReachesTheDefaultForProductFallback()
	{
		setEnforcePrecisePrice(true);
		createDefaultForProductPackingInstruction();
		createProductPrice(null);

		assertThat(helper.getDefaultPackingMaterial(criteria(SOTrx.PURCHASE))).isEmpty();
	}

	/**
	 * The shape used by the invoice line and distribution order line quick inputs: they build their
	 * criteria without a {@code soTrx}, so they never reach the sales-only fallback.
	 */
	@Test
	public void noSoTrx_enforceOn_neverReachesTheDefaultForProductFallback()
	{
		setEnforcePrecisePrice(true);
		createDefaultForProductPackingInstruction();
		createProductPrice(null);

		assertThat(helper.getDefaultPackingMaterial(criteria(null))).isEmpty();
	}

	//
	// Fixture
	//

	private DefaultPackingItemCriteria criteria(@Nullable final SOTrx soTrx)
	{
		return DefaultPackingItemCriteria.builder()
				.productId(productId)
				.bPartnerLocationId(bpartnerLocationId)
				.date(DATE)
				.priceListId(priceListId)
				.soTrx(soTrx)
				.clientId(clientId)
				.build();
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
		product.setValue("100024");
		product.setName("Emmentaler AOP extra");
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private BPartnerLocationAndCaptureId createBPartnerLocation()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("Fromagerie");
		saveRecord(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(bpLocation);

		return BPartnerLocationAndCaptureId.ofRepoId(bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	private PriceListId createPriceList()
	{
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setName("Fromagerie CHF");
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

	/**
	 * The customer's shape: a Standard-Packvorschrift with infinite capacity and no business partner,
	 * created so that mobileUI production works.
	 */
	private I_M_HU_PI_Item_Product createDefaultForProductPackingInstruction()
	{
		final I_M_HU_PI huPI = newInstance(I_M_HU_PI.class);
		huPI.setName("SBB Rahmen");
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
