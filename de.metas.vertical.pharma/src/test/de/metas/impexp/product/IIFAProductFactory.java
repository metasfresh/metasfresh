package de.metas.impexp.product;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product_Category;

import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Fluent {@link I_I_Pharma_Product} factory
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
/* package */class IIFAProductFactory
{

	private final String A00SSATZ; // operation code
	private final String A00PZN; // value
	private final String A00PNAM; // name
	private final String A00PBEZ; // description
	private final String productCategoryValue;
	private final String A00GTIN; // upc
	private final String A00PGMENG; // package size
	private final String packageUOMCode;
	private final String A05KKETTE; // IsColdChain
	private final String A02VSPFL; // IsPrescription
	private final String A02BTM; // IsNarcotic
	private final String A02TFG; // IsTFG

	public static class IIFAProductFactoryBuilder
	{
		public I_I_Pharma_Product build()
		{
			return createImportRecord();
		}

		private I_I_Pharma_Product createImportRecord()
		{
			final I_I_Pharma_Product ifaProduct = InterfaceWrapperHelper.newInstance(I_I_Pharma_Product.class);
			ifaProduct.setA00SSATZ(A00SSATZ);
			ifaProduct.setA00PZN(A00PZN);
			ifaProduct.setA00PNAM(A00PNAM);
			ifaProduct.setA00PBEZ(A00PBEZ);
			ifaProduct.setA00GTIN(A00GTIN);
			ifaProduct.setA00PGMENG(A00PGMENG);
			ifaProduct.setA05KKETTE(A05KKETTE);
			ifaProduct.setA02VSPFL(A02VSPFL);
			ifaProduct.setA02BTM(A02BTM);
			ifaProduct.setA02TFG(A02TFG);
			ifaProduct.setM_Product_Category(createProductCategory(productCategoryValue));
			ifaProduct.setPackage_UOM(createUOM(packageUOMCode));
			InterfaceWrapperHelper.save(ifaProduct);
			return ifaProduct;
		}
	}
	
	public static I_M_Product_Category createProductCategory(@NonNull final String value)
	{
		final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		category.setValue(value);
		category.setName(value);
		InterfaceWrapperHelper.save(category);
		return category;
	}
	
	public static I_C_UOM createUOM(@NonNull final String value)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setX12DE355(value);
		uom.setIsActive(true);
		uom.setUOMSymbol(value);
		uom.setName(value);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}
}
