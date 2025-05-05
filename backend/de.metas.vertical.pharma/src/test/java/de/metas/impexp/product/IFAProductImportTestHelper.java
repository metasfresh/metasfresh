package de.metas.impexp.product;

import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product_Category;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * A collection of IFA product import test helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
/* package */class IFAProductImportTestHelper
{

	final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public I_M_Product_Category createProductCategory(@NonNull final String value)
	{
		final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		category.setValue(value);
		category.setName(value);
		InterfaceWrapperHelper.save(category);
		return category;
	}

	public I_C_UOM createUOM(@NonNull final String value)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setX12DE355(value);
		uom.setIsActive(true);
		uom.setUOMSymbol(value);
		uom.setName(value);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	public int createPriceList(@NonNull final String value)
	{
		final I_M_PriceList pl = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		pl.setName(value);
		InterfaceWrapperHelper.save(pl);
		return pl.getM_PriceList_ID();
	}


	public void assertIFAProductImported(@NonNull final I_I_Pharma_Product ifaProduct) {
		final I_M_Product product = InterfaceWrapperHelper.create(ifaProduct.getM_Product(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.getValue()).isNotNull().isEqualTo(ifaProduct.getA00PZN());
		assertThat(product.getName()).isNotNull().isEqualTo(ifaProduct.getA00PNAM());
		assertThat(product.getDescription()).isEqualTo(ifaProduct.getA00PBEZ());
		assertThat(product.getUPC()).isEqualTo(ifaProduct.getA00GTIN());
		assertThat(product.getPackageSize()).isEqualTo(ifaProduct.getA00PGMENG());
		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());
		assertThat(uom).isNotNull();
		assertThat(uom.getX12DE355()).isEqualTo("PCE");
		final I_C_UOM pckUom = uomDAO.getById(product.getPackage_UOM_ID());
		assertThat(pckUom).isNotNull();
		assertThat(pckUom.getX12DE355()).isEqualTo(ifaProduct.getPackage_UOM().getX12DE355());
	}

	@Builder
	@Value
	public static class IFAFlags
	{
		boolean isColdChain;
		boolean isPrescription;
		boolean isNarcotic;
		boolean isTFG;
	}

	public void assertIFAProductFlags(@NonNull final I_I_Pharma_Product ifaProduct, final @NonNull IFAFlags flags)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ifaProduct.getM_Product(), I_M_Product.class);
		assertThat(product).isNotNull();
		assertThat(product.isColdChain()).isEqualTo(flags.isColdChain());
		assertThat(product.isPrescription()).isEqualTo(flags.isPrescription());
		assertThat(product.isNarcotic()).isEqualTo(flags.isNarcotic());
		assertThat(product.isTFG()).isEqualTo(flags.isTFG());
	}


	public void assertPrices(@NonNull final I_I_Pharma_Product ifaProduct, @NonNull final PriceListId priceListId, @NonNull final BigDecimal price)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ifaProduct.getM_Product(), I_M_Product.class);
		assertThat(product).isNotNull();

		final Optional<PriceListVersionId> plvId = Services.get(IPriceListDAO.class).retrieveNewestPriceListVersionId(priceListId);
		assertThat(plvId).isNotEmpty();

		final I_M_ProductPrice productPrice = ProductPrices.newQuery(plvId.get())
				.setProductId(ProductId.ofRepoId(product.getM_Product_ID()))
				.retrieveDefault(I_M_ProductPrice.class);

		assertThat(productPrice).isNotNull();
		assertThat(productPrice.getPriceStd()).isEqualByComparingTo(price);
	}
}
