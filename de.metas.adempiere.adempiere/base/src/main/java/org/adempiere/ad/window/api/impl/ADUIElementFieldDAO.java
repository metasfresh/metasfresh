package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.ad.window.api.IADUIElementFieldDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ADUIElementFieldDAO implements IADUIElementFieldDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADUIElementFieldDAO.class);

	@Override
	public void copyUIElementFields(final I_AD_UI_Element targetUIElement, final I_AD_UI_Element sourceUIElement)
	{
		final Map<Integer, I_AD_UI_ElementField> existingTargetUIElementFields = retrieveUIElementFields(Env.getCtx(), targetUIElement.getAD_UI_Element_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_UI_ElementField> sourceUIElementFields = retrieveUIElementFields(Env.getCtx(), sourceUIElement.getAD_UI_Element_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceUIElementFields.stream()
				.forEach(sourceUIElementField -> {
					final I_AD_UI_ElementField existingTargetElementField = existingTargetUIElementFields.get(sourceUIElementField.getSeqNo());
					copyUIElementField(targetUIElement, existingTargetElementField, sourceUIElementField);
				});
	}

	@Cached(cacheName = I_AD_UI_ElementField.Table_Name + "#by#" + I_AD_UI_ElementField.COLUMNNAME_AD_UI_Element_ID)
	public Map<Integer, I_AD_UI_ElementField> retrieveUIElementFields(@CacheCtx final Properties ctx, final int uiElementId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_ElementField.class, ctx, trxName)
				.addEqualsFilter(I_AD_UI_ElementField.COLUMNNAME_AD_UI_Element_ID, uiElementId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UI_ElementField.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_UI_ElementField.class, I_AD_UI_ElementField::getSeqNo);
	}

	private void copyUIElementField(final I_AD_UI_Element targetUIElement, final I_AD_UI_ElementField existingTargetElementField, final I_AD_UI_ElementField sourceUIElementField)
	{
		logger.debug("Copying UI Element Field {} to {}", sourceUIElementField, targetUIElement);

		createUpdateUIElementField(targetUIElement, existingTargetElementField, sourceUIElementField);
	}

	private void createUpdateUIElementField(final I_AD_UI_Element targetUIElement, final I_AD_UI_ElementField existingTargetElementField, final I_AD_UI_ElementField sourceUIElementField)
	{
		final I_AD_UI_ElementField targetUIElementField = existingTargetElementField != null ? existingTargetElementField : newInstance(I_AD_UI_ElementField.class);

		copy()
				.setFrom(sourceUIElementField)
				.setTo(targetUIElementField)
				.copy();

		targetUIElementField.setAD_Org_ID(targetUIElement.getAD_Org_ID());
		targetUIElementField.setSeqNo(sourceUIElementField.getSeqNo());

		final int targetUIElementId = targetUIElement.getAD_UI_Element_ID();
		targetUIElementField.setAD_UI_Element_ID(targetUIElementId);

		final int targetFieldId = getTargetFieldId(sourceUIElementField, targetUIElement);
		targetUIElementField.setAD_Field_ID(targetFieldId);

		save(targetUIElementField);
	}

	private int getTargetFieldId(final I_AD_UI_ElementField sourceUIElementField, final I_AD_UI_Element targetElement)
	{
		if (sourceUIElementField.getAD_Field_ID() <= 0)
		{
			return -1;
		}

		final I_AD_Field sourceField = sourceUIElementField.getAD_Field();

		final int columnId = sourceField.getAD_Column_ID();

		final I_AD_UI_ElementGroup uiElementGroup = targetElement.getAD_UI_ElementGroup();

		final I_AD_UI_Column uiColumn = uiElementGroup.getAD_UI_Column();

		final I_AD_UI_Section uiSection = uiColumn.getAD_UI_Section();

		final I_AD_Tab tab = uiSection.getAD_Tab();

		final Optional<I_AD_Field> fieldForColumn = Services.get(IADFieldDAO.class).retrieveFields(tab)
				.stream()
				.filter(field -> field.getAD_Column_ID() == columnId)
				.findFirst();

		return fieldForColumn.isPresent() ? fieldForColumn.get().getAD_Field_ID() : -1;
	}

}
