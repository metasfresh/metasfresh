package org.adempiere.ad.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_Sequence;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.Properties;

public class SequenceDAO implements ISequenceDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Nullable
	@Override
	public I_AD_Sequence retrieveTableSequenceOrNull(@NonNull final Properties ctx, @NonNull final String tableName, @Nullable final String trxName)
	{
		final IQueryBuilder<I_AD_Sequence> queryBuilder = queryBL
				.createQueryBuilder(I_AD_Sequence.class, ctx, trxName);

		final ICompositeQueryFilter<I_AD_Sequence> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, tableName, UpperCaseQueryFilterModifier.instance);
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, true);
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, IClientDAO.SYSTEM_CLIENT_ID);

		return queryBuilder.create()
				.firstOnly(I_AD_Sequence.class);
	}

	@Nullable
	@Override
	public I_AD_Sequence retrieveTableSequenceOrNull(@NonNull final Properties ctx, @NonNull final String tableName)
	{
		final String trxName = ITrx.TRXNAME_None;
		return retrieveTableSequenceOrNull(ctx, tableName, trxName);
	}

	@Override
	public ITableSequenceChecker createTableSequenceChecker(final Properties ctx)
	{
		return new TableSequenceChecker(ctx);
	}

	@Override
	public void renameTableSequence(final Properties ctx, final String tableNameOld, final String tableNameNew)
	{
		//
		// Rename the AD_Sequence
		final I_AD_Sequence adSequence = retrieveTableSequenceOrNull(ctx, tableNameOld, ITrx.TRXNAME_ThreadInherited);
		if (adSequence != null)
		{
			adSequence.setName(tableNameNew);
			InterfaceWrapperHelper.save(adSequence);
		}

		//
		// Rename the database native sequence
		{
			final String dbSequenceNameOld = DB.getTableSequenceName(tableNameOld);
			final String dbSequenceNameNew = DB.getTableSequenceName(tableNameNew);
			DB.getDatabase().renameSequence(dbSequenceNameOld, dbSequenceNameNew);
		}
	}

	@Override
	@NonNull
	public I_AD_Sequence retrieveSequenceByName(@NonNull final String sequenceName)
	{
		return queryBL
				.createQueryBuilder(I_AD_Sequence.class)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, sequenceName)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_AD_Sequence.COLUMNNAME_AD_Client_ID)
				.create()
				.firstNotNull(I_AD_Sequence.class);
	}
}
