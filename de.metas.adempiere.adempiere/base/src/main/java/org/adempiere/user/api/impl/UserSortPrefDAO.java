package org.adempiere.user.api.impl;

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


import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.user.api.IUserSortPrefDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_SortPref_Hdr;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;
import org.compiere.model.X_AD_User_SortPref_Hdr;

/**
 * User default sorting preferences for sequencing in Application Windows (applies to all types of windows)
 *
 * @author al
 */
public class UserSortPrefDAO implements IUserSortPrefDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserSortPrefDAO.class);

	// @Cached don't cache: it's a small table so we won't loose much performance and until we have distributed cache invalidation, caching always introduces the risk of stale data.
	@Override
	public I_AD_User_SortPref_Hdr retrieveSortPreferenceHdr(final I_AD_User user, final String action, final int recordId)
	{
		Check.assumeNotNull(user, "user not null");

		final Object contextProvider = user;

		final ICompositeQueryFilter<I_AD_User_SortPref_Hdr> userOrNullFilter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_AD_User_SortPref_Hdr.class)
				.setJoinOr()
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_User_ID, user.getAD_User_ID())
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_User_ID, null);

		final IQueryBuilder<I_AD_User_SortPref_Hdr> queryBuilder = createSortPreferenceHdrQueryBuilder(action, recordId, contextProvider)
				.addOnlyActiveRecordsFilter()
				.filter(userOrNullFilter)

				// task 08672: one AD_User will have two AD_User_SortPref_Hdrs, one of them is the "global" conference setting's one;
				// to be specific, we explicitly need to exclude the conference record.
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_IsConference, false);

		queryBuilder.orderBy()
				.addColumn(I_AD_User_SortPref_Hdr.COLUMN_AD_User_ID, Direction.Ascending, Nulls.Last);

		//
		return queryBuilder.create()
				.first(I_AD_User_SortPref_Hdr.class);
	}

	// @Cached don't cache: it's a small table so we won't loose much performance and until we have distributed cache invalidation, caching always introduces the risk of stale data.
	@Override
	public I_AD_User_SortPref_Hdr retrieveConferenceSortPreferenceHdr(final Properties ctx, final String action, final int recordId)
	{
		final IContextAware contextProvider = new PlainContextAware(ctx);

		final IQueryBuilder<I_AD_User_SortPref_Hdr> queryBuilder = createSortPreferenceHdrQueryBuilder(action, recordId, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_IsConference, true); // note: there is just one

		return queryBuilder.create()
				.firstOnly(I_AD_User_SortPref_Hdr.class);
	}

	@Override
	public I_AD_User_SortPref_Hdr retrieveDefaultSortPreferenceHdrOrNull(final Properties ctx, String action, int recordId)
	{
		final IContextAware contextProvider = new PlainContextAware(ctx);

		final IQueryBuilder<I_AD_User_SortPref_Hdr> queryBuilder = createSortPreferenceHdrQueryBuilder(action, recordId, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_IsConference, false)
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_User_ID, null);

		return queryBuilder.create()
				.firstOnly(I_AD_User_SortPref_Hdr.class);
	}

	private IQueryBuilder<I_AD_User_SortPref_Hdr> createSortPreferenceHdrQueryBuilder(final String action,
			final int recordId,
			final Object contextProvider)
	{
		Check.assumeNotEmpty(action, "action not empty");

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_AD_User_SortPref_Hdr> queryBuilder = queryBL.createQueryBuilder(I_AD_User_SortPref_Hdr.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_Action, action);

		if (X_AD_User_SortPref_Hdr.ACTION_Fenster.equals(action))
		{
			queryBuilder.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_Tab_ID, recordId);
		}
		else if (X_AD_User_SortPref_Hdr.ACTION_FensterNichtDynamisch.equals(action))
		{
			queryBuilder.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_Form_ID, recordId);
		}
		else if (X_AD_User_SortPref_Hdr.ACTION_Info_Fenster.equals(action))
		{
			queryBuilder.addEqualsFilter(I_AD_User_SortPref_Hdr.COLUMN_AD_InfoWindow_ID, recordId);
		}
		else
		{
			throw new AdempiereException("Action not recognized: {}", new Object[] { action });
		}
		return queryBuilder;
	}

	// @Cached don't cache: it's a small table so we won't loose much performance and until we have distributed cache invalidation, caching always introduces the risk of stale data.
	@Override
	public List<I_AD_User_SortPref_Line> retrieveSortPreferenceLines(final I_AD_User_SortPref_Hdr hdr)
	{
		if (hdr == null)
		{
			return Collections.emptyList();
		}

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Object contextProvider = hdr;

		final IQueryBuilder<I_AD_User_SortPref_Line> queryBuilder = queryBL.createQueryBuilder(I_AD_User_SortPref_Line.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SortPref_Line.COLUMN_AD_User_SortPref_Hdr_ID, hdr.getAD_User_SortPref_Hdr_ID());

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_AD_User_SortPref_Line.class)
				.addColumn(I_AD_User_SortPref_Line.COLUMN_SeqNo)
				.createQueryOrderBy();

		return queryBuilder.create()
				.setOrderBy(orderBy)
				.list(I_AD_User_SortPref_Line.class);
	}

	@Override
	public List<I_AD_User_SortPref_Line> retrieveSortPreferenceLines(final I_AD_User user, final String action, final int recordId)
	{
		final I_AD_User_SortPref_Hdr hdr = retrieveSortPreferenceHdr(user, action, recordId);
		return retrieveSortPreferenceLines(hdr);
	}

	@Override
	public List<I_AD_User_SortPref_Line> retrieveConferenceSortPreferenceLines(final Properties ctx, final String action, final int recordId)
	{
		final I_AD_User_SortPref_Hdr hdr = retrieveConferenceSortPreferenceHdr(ctx, action, recordId);
		return retrieveSortPreferenceLines(hdr);
	}

	@Override
	public List<I_AD_User_SortPref_Line_Product> retrieveSortPreferenceLineProducts(final I_AD_User_SortPref_Line sortPreferenceLine)
	{
		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Object contextProvider = sortPreferenceLine;
		final IQueryBuilder<I_AD_User_SortPref_Line_Product> queryBuilder = queryBL.createQueryBuilder(I_AD_User_SortPref_Line_Product.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_SortPref_Line_Product.COLUMN_AD_User_SortPref_Line_ID, sortPreferenceLine.getAD_User_SortPref_Line_ID());

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_AD_User_SortPref_Line_Product.class)
				.addColumn(I_AD_User_SortPref_Line_Product.COLUMN_SeqNo)
				.createQueryOrderBy();

		return queryBuilder.create()
				.setOrderBy(orderBy)
				.list(I_AD_User_SortPref_Line_Product.class);
	}

	@Override
	public int clearSortPreferenceLines(final I_AD_User_SortPref_Hdr hdr)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		int count = 0;

		final List<I_AD_User_SortPref_Line> linesToDelete = retrieveSortPreferenceLines(hdr);
		for (final I_AD_User_SortPref_Line line : linesToDelete)
		{
			final int countProductLinesDeleted = queryBL.createQueryBuilder(I_AD_User_SortPref_Line_Product.class, hdr)
					.addEqualsFilter(I_AD_User_SortPref_Line_Product.COLUMN_AD_User_SortPref_Line_ID, line.getAD_User_SortPref_Line_ID())
					.create()
					.deleteDirectly();
			count += countProductLinesDeleted;

			InterfaceWrapperHelper.delete(line);
			count++;
		}
		logger.info("Deleted {} records in sum", count);
		return count;
	}
}
