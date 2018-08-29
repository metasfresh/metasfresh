package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADUIColumnDAO;
import org.adempiere.ad.window.api.IADUISectionDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Section;
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

public class ADUISectionDAO implements IADUISectionDAO
{

	private static final transient Logger logger = LogManager.getLogger(ADUISectionDAO.class);

	private final IADUIColumnDAO uiColumnDAO = Services.get(IADUIColumnDAO.class);

	@Override
	public void copyUISections(final I_AD_Tab targetTab, final I_AD_Tab sourceTab)
	{
		final Map<String, I_AD_UI_Section> existingUISections = retrieveUISections(Env.getCtx(), targetTab.getAD_Tab_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_UI_Section> sourceUISections = retrieveUISections(Env.getCtx(), sourceTab.getAD_Tab_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceUISections.stream()
				.forEach(sourceUISection -> {
					final I_AD_UI_Section existingUISection = existingUISections.get(sourceUISection.getValue());
					copyUISection(targetTab, existingUISection, sourceUISection);
				});

	}

	@Cached(cacheName = I_AD_UI_Section.Table_Name + "#by#" + I_AD_UI_Section.COLUMNNAME_AD_Tab_ID)
	public Map<String, I_AD_UI_Section> retrieveUISections(@CacheCtx final Properties ctx, final int tabId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Section.class, ctx, trxName)
				.addEqualsFilter(I_AD_UI_Section.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_UI_Section.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_UI_Section.class, I_AD_UI_Section::getValue);
	}

	private void copyUISection(final I_AD_Tab targetTab, final I_AD_UI_Section existingUISection, final I_AD_UI_Section sourceUISection)
	{
		logger.debug("Copying UISection {} to {}", sourceUISection, targetTab);

		final I_AD_UI_Section targetUISection = createUpdateUISection(targetTab, existingUISection, sourceUISection);

		copyUISectionTrl(targetUISection.getAD_UI_Section_ID(), sourceUISection.getAD_UI_Section_ID());

		uiColumnDAO.copyUIColumns(targetUISection, sourceUISection);
	}

	private I_AD_UI_Section createUpdateUISection(final I_AD_Tab targetTab, final I_AD_UI_Section existingUISection, final I_AD_UI_Section sourceUISection)
	{
		final I_AD_UI_Section targetUISection = existingUISection != null ? existingUISection : newInstance(I_AD_UI_Section.class);

		copy()
				.setFrom(sourceUISection)
				.setTo(targetUISection)
				.copy();

		targetUISection.setAD_Org_ID(sourceUISection.getAD_Org_ID());

		final int targetTabId = targetTab.getAD_Tab_ID();
		targetUISection.setAD_Tab_ID(targetTabId);

		if (targetUISection.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveUISectionLastSeqNo(targetTabId);
			targetUISection.setSeqNo(lastSeqNo + 10);
		}

		save(targetUISection);

		return targetUISection;
	}

	private int retrieveUISectionLastSeqNo(int tabId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_UI_Section.class)
				.addEqualsFilter(I_AD_UI_Section.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_UI_Section.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyUISectionTrl(final int targetUISectionId, final int sourceUISectionId)
	{
		Check.assumeGreaterThanZero(targetUISectionId, "targetUISectionId");
		Check.assumeGreaterThanZero(sourceUISectionId, "sourceUISectionId");

		final String sqlDelete = "DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = " + targetUISectionId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_UI_Section_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, IsTranslated) " +
				" SELECT " + targetUISectionId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, IsTranslated " +
				" FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = " + sourceUISectionId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_UI_Section_Trl inserted: {}", countInsert);
	}

}
