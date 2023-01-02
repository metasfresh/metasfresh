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

import static org.compiere.model.POInfo.getPOInfo;

@Component
@Interceptor(AD_Ref_List.class)
public class AD_Ref_List
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeTrlChanged(final I_AD_Ref_List baseTable)
	{

	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void afterTrlChanged(final I_AD_Ref_List baseTable)
	{
		final List<String> translatedColumnNames = getPOInfo(I_AD_Ref_List.Table_Name).getTranslatedColumnNames();
		final String baseLanguage = Language.getBaseLanguage().getAD_Language();
		final int id = baseTable.getAD_Reference_ID();

		final I_AD_Ref_List_Trl trlTableToUpdate = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Ref_List_Trl.class)
				.addEqualsFilter(I_AD_Ref_List_Trl.COLUMNNAME_AD_Ref_List_ID, id)
				.addEqualsFilter(I_AD_Ref_List_Trl.COLUMNNAME_AD_Language, baseLanguage)
				.create()
				.firstOnlyNotNull(I_AD_Ref_List_Trl.class);

		final Map<String, Object> updates = new HashMap<>();
		for (final String translatedColumnName:translatedColumnNames)
		{

			final Object value = InterfaceWrapperHelper.getValueOrNull(baseTable, translatedColumnName);

			updates.put(translatedColumnName, value);

		}

		InterfaceWrapperHelper.setValues(trlTableToUpdate, updates);
		InterfaceWrapperHelper.save(trlTableToUpdate, ITrx.TRXNAME_ThreadInherited);

	}
}
