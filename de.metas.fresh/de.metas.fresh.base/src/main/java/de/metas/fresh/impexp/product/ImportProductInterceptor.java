package de.metas.fresh.impexp.product;

import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.impexp.IImportProcess;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Allergen;
import org.compiere.model.I_M_Product;

import de.metas.fresh.model.I_I_Product;
import de.metas.printing.esb.base.util.Check;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ImportProductInterceptor implements IImportInterceptor
{

	@Override
	public void onImport(final IImportProcess<?> process, final Object importModel, final Object targetModel, final int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT)
		{
			return;
		}

		final I_I_Product iProduct = InterfaceWrapperHelper.create(importModel, I_I_Product.class);
		final I_M_Product product = InterfaceWrapperHelper.create(targetModel, I_M_Product.class);

		updateProductIngredients(product, iProduct);
		updateProductCustomerName(product, iProduct);

		createUpdateProductAllergen(product, iProduct);

	}



	private void updateProductCustomerName(I_M_Product product, I_I_Product iProduct)
	{
		// TODO Auto-generated method stub

	}

	private void updateProductIngredients(I_M_Product product, I_I_Product iProduct)
	{
		// TODO Auto-generated method stub

	}

	private void createProductAllergen(I_M_Product product, I_M_Allergen allergen)
	{
		// TODO Auto-generated method stub

	}

	private void translateAllergenToFrench(I_M_Allergen allergen, String allergenName_FR)
	{
		// TODO Auto-generated method stub

	}

	private I_M_Allergen createUpdateProductAllergen(I_M_Product product, I_I_Product iProduct)
	{
		final String allergenName = iProduct.getAllergenName();
		if (!Check.isEmpty(allergenName))
		{
			final I_M_Allergen allergen = createUpdateAllergen(allergenName);

			final String allergenName_FR = iProduct.getAllergenName_FR();
			if (!Check.isEmpty(allergenName_FR))
			{
				translateAllergenToFrench(allergen, allergenName_FR);
			}

		}

	}

	private I_M_Allergen createUpdateAllergen(String allergenName)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
