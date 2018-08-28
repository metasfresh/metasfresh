package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.ad.window.api.IADTabDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;
import org.compiere.util.DB;
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

public class ADTabDAO implements IADTabDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADTabDAO.class);

	private final IADFieldDAO fieldDAO = Services.get(IADFieldDAO.class);

	@Override
	public void copyWindowTabs(final I_AD_Window targetWindow, final I_AD_Window sourceWindow)
	{

		final Map<Integer, I_AD_Tab> existingTargetTabs = retrieveWindowTabs(Env.getCtx(), targetWindow.getAD_Window_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_Tab> sourceTabs = retrieveWindowTabs(Env.getCtx(), sourceWindow.getAD_Window_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceTabs.stream()
				.forEach(sourceTab -> {
					final I_AD_Tab existingTargetTab = existingTargetTabs.get(sourceTab.getAD_Table_ID());
					copyWindowTab(targetWindow, existingTargetTab, sourceTab);
				});

	}

	@Cached(cacheName = I_AD_Tab.Table_Name + "#by#" + I_AD_Tab.COLUMNNAME_AD_Window_ID)
	public Map<Integer, I_AD_Tab> retrieveWindowTabs(@CacheCtx final Properties ctx, final int windowId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Tab.class, ctx, trxName)
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_AD_Window_ID, windowId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Tab.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_Tab.class, I_AD_Tab::getAD_Table_ID);
	}

	private void copyWindowTab(final I_AD_Window targetWindow, final I_AD_Tab existingTargetTab, final I_AD_Tab sourceTab)
	{
		logger.debug("Copying tab from {} to {}", sourceTab, targetWindow);

		final I_AD_Tab targetTab = createUpdateTab(targetWindow, existingTargetTab, sourceTab);

		copyTabTrl(targetTab.getAD_Tab_ID(), sourceTab.getAD_Tab_ID());

		fieldDAO.copyTabFields(targetTab, sourceTab);
	}

	private I_AD_Tab createUpdateTab(final I_AD_Window targetWindow, final I_AD_Tab existingTargetTab, final I_AD_Tab sourceTab)
	{
		final int targetWindowId = targetWindow.getAD_Window_ID();
		final String entityType = targetWindow.getEntityType();

		final I_AD_Tab targetTab = existingTargetTab != null ? existingTargetTab : newInstance(I_AD_Tab.class);

		copy()
				.setFrom(sourceTab)
				.setTo(targetTab)
				.copy();
		targetTab.setAD_Org_ID(targetWindow.getAD_Org_ID());
		targetTab.setAD_Window_ID(targetWindowId);
		targetTab.setEntityType(entityType);

		if (targetTab.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveWindowTabLastSeqNo(targetWindowId);
			targetTab.setSeqNo(lastSeqNo + 10);
		}
		save(targetTab);

		return targetTab;
	}

	private int retrieveWindowTabLastSeqNo(int windowId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Tab.class)
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_AD_Window_ID, windowId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Tab.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyTabTrl(final int targetTabId, final int sourceTabId)
	{
		Check.assumeGreaterThanZero(targetTabId, "targetTabId");
		Check.assumeGreaterThanZero(sourceTabId, "sourceTabId");

		final String sqlDelete = "DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = " + targetTabId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Tab_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, IsTranslated) " +
				" SELECT " + targetTabId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description, IsTranslated " +
				" FROM AD_Tab_Trl WHERE AD_Tab_ID = " + sourceTabId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Tab_Trl inserted: {}", countInsert);
	}
}
