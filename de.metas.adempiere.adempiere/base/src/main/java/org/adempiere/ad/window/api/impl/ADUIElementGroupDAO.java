package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADUIElementDAO;
import org.adempiere.ad.window.api.IADUIElementGroupDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_ElementGroup;
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

public class ADUIElementGroupDAO implements IADUIElementGroupDAO
{
	private final IADUIElementDAO uiElementDAO = Services.get(IADUIElementDAO.class);

	private static final transient Logger logger = LogManager.getLogger(ADUIElementGroupDAO.class);

	@Override
	public void copyUIElementGroups(final I_AD_UI_Column targetUIColumn, final I_AD_UI_Column sourceUIColumn)
	{
		final Map<String, I_AD_UI_ElementGroup> existingUIElementGroups = retrieveUIElementGroups(Env.getCtx(), targetUIColumn.getAD_UI_Column_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_UI_ElementGroup> sourceUIElementGroups = retrieveUIElementGroups(Env.getCtx(), sourceUIColumn.getAD_UI_Column_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceUIElementGroups.stream()
				.forEach(sourceUIElementGroup -> {
					final I_AD_UI_ElementGroup existingUIElementGroup = existingUIElementGroups.get(sourceUIElementGroup.getName());
					copyUIElementGroup(targetUIColumn, existingUIElementGroup, sourceUIElementGroup);
				});

	}

	@Cached(cacheName = I_AD_UI_ElementGroup.Table_Name + "#by#" + I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_Column_ID)
	public Map<String, I_AD_UI_ElementGroup> retrieveUIElementGroups(@CacheCtx final Properties ctx, final int uiColumnId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_ElementGroup.class, ctx, trxName)
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_Column_ID, uiColumnId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UI_ElementGroup.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_UI_ElementGroup.class, I_AD_UI_ElementGroup::getName);
	}

	private void copyUIElementGroup(final I_AD_UI_Column targetUIColumn, final I_AD_UI_ElementGroup existingUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		logger.debug("Copying UIElementGroup {} to {}", sourceUIElementGroup, targetUIColumn);

		final I_AD_UI_ElementGroup targetUIElementGroup = createUpdateUIElementGroup(targetUIColumn, existingUIElementGroup, sourceUIElementGroup);

		uiElementDAO.copyUIElements(targetUIElementGroup, sourceUIElementGroup);
	}

	private I_AD_UI_ElementGroup createUpdateUIElementGroup(final I_AD_UI_Column targetUIColumn, final I_AD_UI_ElementGroup existingUIElementGroup, final I_AD_UI_ElementGroup sourceUIElementGroup)
	{
		final I_AD_UI_ElementGroup targetUIElementGroup = existingUIElementGroup != null ? existingUIElementGroup : newInstance(I_AD_UI_ElementGroup.class);

		copy()
				.setFrom(sourceUIElementGroup)
				.setTo(targetUIElementGroup)
				.copy();

		targetUIElementGroup.setAD_Org_ID(targetUIColumn.getAD_Org_ID());
		targetUIElementGroup.setAD_UI_Column_ID(targetUIColumn.getAD_UI_Column_ID());

		if (targetUIElementGroup.getSeqNo() <= 0)
		{
			final int targetUIColumnId = targetUIColumn.getAD_UI_Column_ID();
			final int lastSeqNo = retrieveUIElementGroupLastSeqNo(targetUIColumnId);
			targetUIElementGroup.setSeqNo(lastSeqNo + 10);
		}

		save(targetUIElementGroup);

		return targetUIElementGroup;
	}

	private int retrieveUIElementGroupLastSeqNo(int uiColumnId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_ElementGroup.class)
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_Column_ID, uiColumnId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_ElementGroup.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

}
