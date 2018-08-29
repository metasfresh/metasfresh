package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.window.api.IADUIColumnDAO;
import org.adempiere.ad.window.api.IADUIElementGroupDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Section;
import org.slf4j.Logger;

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

public class ADUIColumnDAO implements IADUIColumnDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADUIColumnDAO.class);

	private final IADUIElementGroupDAO uiElementGroupDAO = Services.get(IADUIElementGroupDAO.class);

	@Override
	public void copyUIColumns(final I_AD_UI_Section targetUISection, final I_AD_UI_Section sourceUISection)
	{
		final Map<Integer, I_AD_UI_Column> existingUIColumns = retrieveUIColumns(targetUISection.getAD_UI_Section_ID());
		final Collection<I_AD_UI_Column> sourceUIColumns = retrieveUIColumns(sourceUISection.getAD_UI_Section_ID()).values();

		sourceUIColumns.stream()
				.forEach(sourceUIColumn -> {
					final I_AD_UI_Column existingUIColumn = existingUIColumns.get(sourceUIColumn.getSeqNo());
					copyUIColumn(targetUISection, existingUIColumn, sourceUIColumn);
				});

	}

	@Cached(cacheName = I_AD_UI_Column.Table_Name + "#by#" + I_AD_UI_Column.COLUMNNAME_AD_UI_Section_ID)
	public Map<Integer, I_AD_UI_Column> retrieveUIColumns(final int sectionId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Column.class)
				.addEqualsFilter(I_AD_UI_Column.COLUMNNAME_AD_UI_Section_ID, sectionId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UI_Column.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_UI_Column.class, I_AD_UI_Column::getSeqNo);
	}

	private void copyUIColumn(final I_AD_UI_Section targetUISection, final I_AD_UI_Column existingUIColumn, final I_AD_UI_Column sourceUIColumn)
	{
		logger.debug("Copying UIColumn {} to {}", sourceUIColumn, targetUISection);

		final I_AD_UI_Column targetUIColumn = createUpdateUIColumn(targetUISection, existingUIColumn, sourceUIColumn);

		uiElementGroupDAO.copyUIElementGroups(targetUIColumn, sourceUIColumn);
	}

	private I_AD_UI_Column createUpdateUIColumn(final I_AD_UI_Section targetUISection, final I_AD_UI_Column existingUIColumn, final I_AD_UI_Column sourceUIColumn)
	{

		final I_AD_UI_Column targetUIColumn = existingUIColumn != null ? existingUIColumn : newInstance(I_AD_UI_Column.class);

		copy()
				.addTargetColumnNameToSkip(I_AD_UI_Column.COLUMNNAME_SeqNo)
				.setFrom(sourceUIColumn)
				.setTo(targetUIColumn)
				.copy();

		targetUIColumn.setAD_Org_ID(targetUISection.getAD_Org_ID());
		targetUIColumn.setAD_UI_Section_ID(targetUISection.getAD_UI_Section_ID());
		targetUIColumn.setSeqNo(sourceUIColumn.getSeqNo());

		save(targetUIColumn);

		return targetUIColumn;
	}
}
