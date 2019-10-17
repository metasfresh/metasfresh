package de.metas.impexp.product;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import lombok.Builder;
import lombok.Value;

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
	private final Timestamp A01GDAT; // validdate
	private final String productCategoryValue;
	private final String A00GTIN; // upc
	private final String A00PGMENG; // package size
	private final String packageUOMCode;
	private final String A05KKETTE; // IsColdChain
	private final String A02VSPFL; // IsPrescription
	private final String A02BTM; // IsNarcotic
	private final String A02TFG; // IsTFG
	//prices
	private final BigDecimal A01KAEP;
	private final int KAEP_Price_List_ID;
	private final BigDecimal A01APU;
	private final int APU_Price_List_ID;
	private final BigDecimal A01AEP;
	private final int AEP_Price_List_ID;
	private final BigDecimal A01AVP;
	private final int AVP_Price_List_ID;
	private final BigDecimal A01UVP;
	private final int UVP_Price_List_ID;
	private final BigDecimal A01ZBV;
	private final int ZBV_Price_List_ID;

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
			ifaProduct.setA01GDAT(A01GDAT);
			ifaProduct.setA00GTIN(A00GTIN);
			ifaProduct.setA00PGMENG(A00PGMENG);
			ifaProduct.setA05KKETTE(A05KKETTE);
			ifaProduct.setA02VSPFL(A02VSPFL);
			ifaProduct.setA02BTM(A02BTM);
			ifaProduct.setA02TFG(A02TFG);
			ifaProduct.setM_Product_Category(IFAProductImportTestHelper.createProductCategory(productCategoryValue));
			ifaProduct.setPackage_UOM(IFAProductImportTestHelper.createUOM(packageUOMCode));
			ifaProduct.setKAEP_Price_List_ID(KAEP_Price_List_ID);
			ifaProduct.setA01KAEP(A01KAEP);
			ifaProduct.setAEP_Price_List_ID(AEP_Price_List_ID);
			ifaProduct.setA01AEP(A01AEP);
			ifaProduct.setAPU_Price_List_ID(APU_Price_List_ID);
			ifaProduct.setA01APU(A01APU);
			ifaProduct.setAVP_Price_List_ID(AVP_Price_List_ID);
			ifaProduct.setA01AVP(A01AVP);
			ifaProduct.setUVP_Price_List_ID(UVP_Price_List_ID);
			ifaProduct.setA01UVP(A01UVP);
			ifaProduct.setZBV_Price_List_ID(ZBV_Price_List_ID);
			ifaProduct.setA01ZBV(A01ZBV);
			InterfaceWrapperHelper.save(ifaProduct);
			return ifaProduct;
		}
	}
}
