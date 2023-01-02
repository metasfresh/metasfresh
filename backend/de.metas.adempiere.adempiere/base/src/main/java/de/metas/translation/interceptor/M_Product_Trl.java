/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.translation.interceptor;

import de.metas.i18n.Language;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.compiere.model.POInfo.getPOInfo;

@Component
@Interceptor(I_M_Product_Trl.class)
public class M_Product_Trl
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeElementTrlChanged(final I_M_Product_Trl mProductTrl)
	{

	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterElementTrlChanged(final I_M_Product_Trl mProductTrl)
	{
		final List<String> translatedColumnNames = getPOInfo(I_M_Product.Table_Name).getTranslatedColumnNames();
		final String baseLanguage = Language.getBaseLanguage().getAD_Language();
		final int mProductId = mProductTrl.getM_Product_ID();
		final String mProductLanguage = mProductTrl.getAD_Language();

		if(Objects.equals(mProductLanguage, baseLanguage))
		{

			final I_M_Product productToUpdate = Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, mProductId)
					.create()
					.firstOnlyNotNull(I_M_Product.class);

			final Map<String, Object> productUpdates = new HashMap<>();
			for (final String translatedColumnName:translatedColumnNames)
			{

				final Object value = InterfaceWrapperHelper.getValueOrNull(mProductTrl, translatedColumnName);

				productUpdates.put(translatedColumnName, value);

			}

			InterfaceWrapperHelper.setValues(productToUpdate, productUpdates);
			InterfaceWrapperHelper.save(productToUpdate, ITrx.TRXNAME_ThreadInherited);
		}
	}
}
