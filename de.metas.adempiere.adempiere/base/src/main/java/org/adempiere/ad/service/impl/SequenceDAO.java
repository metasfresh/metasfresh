package org.adempiere.ad.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Sequence;

public class SequenceDAO implements ISequenceDAO
{
	@Override
	public I_AD_Sequence retrieveTableSequenceOrNull(Properties ctx, String tableName, String trxName)
	{
		final IQueryBuilder<I_AD_Sequence> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Sequence.class)
				.setContext(ctx, trxName);

		final ICompositeQueryFilter<I_AD_Sequence> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, tableName, UpperCaseQueryFilterModifier.instance);
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_IsTableID, true);
		filters.addEqualsFilter(I_AD_Sequence.COLUMNNAME_AD_Client_ID, IClientDAO.SYSTEM_CLIENT_ID);

		final I_AD_Sequence sequence = queryBuilder.create()
				.firstOnly(I_AD_Sequence.class);

		return sequence;
	}

	@Override
	public I_AD_Sequence retrieveTableSequenceOrNull(Properties ctx, String tableName)
	{
		final String trxName = ITrx.TRXNAME_None;
		return retrieveTableSequenceOrNull(ctx, tableName, trxName);
	}

	@Override
	public ITableSequenceChecker createTableSequenceChecker(final Properties ctx)
	{
		return new TableSequenceChecker(ctx);
	}
}
