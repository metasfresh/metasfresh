/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Ref_List_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.compiere.model.POInfo.getPOInfo;

@Component
@Interceptor(I_AD_Ref_List_Trl.class)
public class AD_Ref_List_Trl
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeElementTrlChanged(final I_AD_Ref_List_Trl trlTable)
	{

	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterElementTrlChanged(final I_AD_Ref_List_Trl trlTable)
	{
		final List<String> translatedColumnNames = getPOInfo(I_AD_Ref_List.Table_Name).getTranslatedColumnNames();
		final String baseLanguage = Language.getBaseLanguage().getAD_Language();
		final int id = trlTable.getAD_Ref_List_ID();
		final String language = trlTable.getAD_Language();

		if(Objects.equals(language, baseLanguage))
		{

			final I_AD_Ref_List baseTableToUpdate = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Ref_List.class)
					.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID, id)
					.create()
					.firstOnlyNotNull(I_AD_Ref_List.class);

			final Map<String, Object> updates = new HashMap<>();
			for (final String translatedColumnName:translatedColumnNames)
			{

				final Object value = InterfaceWrapperHelper.getValueOrNull(trlTable, translatedColumnName);

				updates.put(translatedColumnName, value);

			}

			InterfaceWrapperHelper.setValues(baseTableToUpdate, updates);
			InterfaceWrapperHelper.save(baseTableToUpdate, ITrx.TRXNAME_ThreadInherited);
		}
	}
}
