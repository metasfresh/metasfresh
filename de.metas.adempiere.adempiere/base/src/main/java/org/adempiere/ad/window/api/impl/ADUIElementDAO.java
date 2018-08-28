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
import org.adempiere.ad.window.api.IADUIElementDAO;
import org.adempiere.ad.window.api.IADUIElementFieldDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
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

public class ADUIElementDAO implements IADUIElementDAO
{

	IADUIElementFieldDAO uiElementFieldDAO = Services.get(IADUIElementFieldDAO.class);

	private static final transient Logger logger = LogManager.getLogger(ADUIElementDAO.class);

	@Override
	public void copyUIElements(final I_AD_UI_ElementGroup targetUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		final Map<String, I_AD_UI_Element> existingTargetUIElements = retrieveUIElements(Env.getCtx(), targetUIElementGroup.getAD_UI_ElementGroup_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_UI_Element> sourceUIElements = retrieveUIElements(Env.getCtx(), sourceUIElementGroup.getAD_UI_ElementGroup_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceUIElements.stream()
				.forEach(sourceUIElement -> {
					final I_AD_UI_Element existingTargetElement = existingTargetUIElements.get(sourceUIElement.getName());
					copyUIElement(targetUIElementGroup, existingTargetElement, sourceUIElement);
				});
	}

	@Cached(cacheName = I_AD_UI_Element.Table_Name + "#by#" + I_AD_UI_Element.COLUMNNAME_AD_UI_ElementGroup_ID)
	public Map<String, I_AD_UI_Element> retrieveUIElements(@CacheCtx final Properties ctx, final int uiElementGroupId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Element.class, ctx, trxName)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_UI_ElementGroup_ID, uiElementGroupId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UI_Element.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_UI_Element.class, I_AD_UI_Element::getName);
	}

	private void copyUIElement(final I_AD_UI_ElementGroup targetElementGroup, final I_AD_UI_Element existingTargetElement, final I_AD_UI_Element sourceUIElement)
	{
		logger.debug("Copying UI Element from {} to {}", sourceUIElement, targetElementGroup);

		final I_AD_UI_Element targetUIElement = createUpdateUIElement(targetElementGroup, existingTargetElement, sourceUIElement);

		uiElementFieldDAO.copyUIElementFields(targetUIElement, sourceUIElement);
	}

	private I_AD_UI_Element createUpdateUIElement(final I_AD_UI_ElementGroup targetElementGroup, final I_AD_UI_Element existingTargetElement, final I_AD_UI_Element sourceElement)
	{
		final int targetElementGroupId = targetElementGroup.getAD_UI_ElementGroup_ID();

		final I_AD_UI_Element targetElement = existingTargetElement != null ? existingTargetElement : newInstance(I_AD_UI_Element.class);

		copy()
				.setFrom(sourceElement)
				.setTo(targetElement)
				.copy();
		targetElement.setAD_Org_ID(targetElementGroup.getAD_Org_ID());
		targetElement.setAD_UI_ElementGroup_ID(targetElementGroupId);

		final int targetFieldId = getTargetFieldId(sourceElement, targetElementGroup);

		targetElement.setAD_Field_ID(targetFieldId);
		if (targetElement.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveElementGroupElementLastSeqNo(targetElementGroupId);
			targetElement.setSeqNo(lastSeqNo + 10);
		}
		save(targetElement);

		return targetElement;
	}

	private int getTargetFieldId(final I_AD_UI_Element sourceElement, final I_AD_UI_ElementGroup targetElementGroup)
	{
		if (sourceElement.getAD_Field_ID() <= 0)
		{
			return -1;
		}
		final I_AD_Field sourceField = sourceElement.getAD_Field();

		final int columnId = sourceField.getAD_Column_ID();

		final I_AD_UI_Column uiColumn = targetElementGroup.getAD_UI_Column();

		final I_AD_UI_Section uiSection = uiColumn.getAD_UI_Section();

		final I_AD_Tab tab = uiSection.getAD_Tab();

		final Optional<I_AD_Field> fieldForColumn = Services.get(IADFieldDAO.class).retrieveFields(tab)
				.stream()
				.filter(field -> field.getAD_Column_ID() == columnId)
				.findFirst();

		return fieldForColumn.isPresent() ? fieldForColumn.get().getAD_Field_ID() : -1;
	}

	private int retrieveElementGroupElementLastSeqNo(int uiElementGroupId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_AD_UI_ElementGroup_ID, uiElementGroupId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_Element.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

}
