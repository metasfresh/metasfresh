package de.metas.vertical.pharma.model.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.vertical.pharma.PharmaModulo11Validator;
import de.metas.vertical.pharma.model.I_M_Product;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_M_Product.class)
@Component
public class M_Product
{
	private final static AdMessageKey ERR_Invalid_PZN = AdMessageKey.of("de.metas.vertical.pharma.model.interceptor.M_Product.Invalid_PZN");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_M_Product.COLUMNNAME_Value,
			I_M_Product.COLUMNNAME_IsPharmaProduct
	})
	public void ValidatePZN(final I_M_Product product)
	{

		if (!product.isPharmaProduct())
		{
			// nothing to do
			return;
		}

		final String pzn = product.getValue();

		final boolean isValidPZN = PharmaModulo11Validator.isValid(pzn);

		if (!isValidPZN)
		{
			throw new AdempiereException(ERR_Invalid_PZN, pzn)
					.markAsUserValidationError();
		}
	}

}
