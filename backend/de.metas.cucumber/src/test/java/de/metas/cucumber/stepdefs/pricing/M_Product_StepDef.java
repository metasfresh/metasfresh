/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * This is not the "normal" product stepDef, but one that creates just a static product with a given {@code M_Product_ID}.
 * To set up actual product-data, see {@link de.metas.cucumber.stepdefs.product.M_Product_StepDef}.
 */
public class M_Product_StepDef
{
	public final ProductCategoryId standardCategoryId = StepDefConstants.PRODUCT_CATEGORY_STANDARD_ID;
	public final UomId PCEUOMId = StepDefConstants.PCE_UOM_ID;

	@And("metasfresh contains M_Product with M_Product_ID {string}")
	public void add_M_Product(@NonNull final String productIdentifier)
	{
		final I_M_Product mockedProduct = newInstance(I_M_Product.class);
		mockedProduct.setM_Product_ID(Integer.parseInt(productIdentifier));
		mockedProduct.setValue("product_value" + productIdentifier);
		mockedProduct.setName("product_name_" + productIdentifier);
		mockedProduct.setM_Product_Category_ID(standardCategoryId.getRepoId());
		mockedProduct.setC_UOM_ID(PCEUOMId.getRepoId());
		mockedProduct.setProductType("I");
		mockedProduct.setIsPurchased(false);
		mockedProduct.setIsSold(false);
		saveRecord(mockedProduct);
	}
}
