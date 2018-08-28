package org.adempiere.ad.window.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADFieldDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;

public class ADFieldDAO implements IADFieldDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADFieldDAO.class);

	@Override
	public List<I_AD_Field> retrieveFields(final I_AD_Tab adTab)
	{
		Check.assumeNotNull(adTab, "adTab not null");

		final IQueryBuilder<I_AD_Field> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Field.class, adTab)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, adTab.getAD_Tab_ID());

		queryBuilder.orderBy()
				.addColumn(I_AD_Field.COLUMNNAME_AD_Field_ID);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public void copyTabFields(final I_AD_Tab targetTab, final I_AD_Tab sourceTab)
	{
		final Map<Integer, I_AD_Field> existingTargetFields = retrieveFields(Env.getCtx(), targetTab.getAD_Tab_ID(), ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_Field> sourceFields = retrieveFields(Env.getCtx(), sourceTab.getAD_Tab_ID(), ITrx.TRXNAME_ThreadInherited).values();

		sourceFields.stream()
				.forEach(sourceField -> {
					final I_AD_Field existingTargetField = existingTargetFields.get(sourceField.getAD_Column_ID());
					copyField(targetTab, existingTargetField, sourceField);
				});

	}

	@Cached(cacheName = I_AD_Field.Table_Name + "#by#" + I_AD_Field.COLUMNNAME_AD_Tab_ID)
	public Map<Integer, I_AD_Field> retrieveFields(@CacheCtx final Properties ctx, final int tabId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Field.class, ctx, trxName)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Field.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_Field.class, I_AD_Field::getAD_Column_ID);
	}

	private void copyField(final I_AD_Tab targetTab, final I_AD_Field existingTargetField, final I_AD_Field sourceField)
	{
		logger.debug("Copying field from {} to {}", sourceField, targetTab);

		final int targetTabId = targetTab.getAD_Tab_ID();
		final String entityType = targetTab.getEntityType();

		final I_AD_Field targetField = existingTargetField != null ? existingTargetField : newInstance(I_AD_Field.class);

		copy()
				.setFrom(sourceField)
				.setTo(targetField)
				.copy();
		targetField.setAD_Org_ID(targetTab.getAD_Org_ID());
		targetField.setAD_Tab_ID(targetTabId);
		targetField.setEntityType(entityType);

		if (targetField.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveFieldLastSeqNo(targetTabId);
			targetField.setSeqNo(lastSeqNo + 10);
		}

		save(targetField);

		copyFieldTrl(targetField.getAD_Field_ID(), sourceField.getAD_Field_ID());
	}

	private int retrieveFieldLastSeqNo(int tabId)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, tabId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Field.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	private void copyFieldTrl(final int targetFieldId, final int sourceFieldId)
	{
		Check.assumeGreaterThanZero(targetFieldId, "targetFieldId");
		Check.assumeGreaterThanZero(sourceFieldId, "sourceFieldId");

		final String sqlDelete = "DELETE FROM AD_Field_Trl WHERE AD_Field_ID = " + targetFieldId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Field_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, Help, IsTranslated) " +
				" SELECT " + targetFieldId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
				" FROM AD_Field_Trl WHERE AD_Field_ID = " + sourceFieldId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Field_Trl inserted: {}", countInsert);
	}

}
