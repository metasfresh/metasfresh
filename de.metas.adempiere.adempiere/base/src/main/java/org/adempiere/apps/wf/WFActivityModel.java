package org.adempiere.apps.wf;

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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_WF_Activity;
import org.compiere.model.X_AD_WF_Activity;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.wf.MWFActivity;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class WFActivityModel
{
	private static final transient Logger logger = LogManager.getLogger(WFActivityModel.class);

	public static final String SYSCONFIG_MAX_ACTIVITIES_IN_LIST = "MAX_ACTIVITIES_IN_LIST";
	public static final int SYSCONFIG_MAX_ACTIVITIES_IN_LIST_DEFAULT = 200;

	private final Properties ctx;

	public WFActivityModel(final Properties ctx)
	{
		this.ctx = ctx;
	}

	// private Query activitiesQuery;
	private IQuery<I_AD_WF_Activity> getActivitiesQuery()
	{
		// if (activitiesQuery == null)
		// {
		// activitiesQuery = createActivitiesQuery();
		// }
		// return activitiesQuery;
		return createActivitiesQuery();
	}

	private IQuery<I_AD_WF_Activity> createActivitiesQuery()
	{
		//
		// Users where clause
		final String wcUsers;
		final List<Object> paramsUsers = new ArrayList<>();
		{
			final List<Integer> userIds = getUserAndSubstitutedsIds();
			wcUsers = DB.buildSqlList(userIds, paramsUsers);
		}

		//
		// WF Responsible filter
		final StringBuilder wcResponsible = new StringBuilder();
		final List<Object> paramsResponsible = new ArrayList<>();
		{
			// Owner of Activity
			wcResponsible.append(I_AD_WF_Activity.COLUMNNAME_AD_User_ID).append(" IN ").append(wcUsers);
			paramsResponsible.addAll(paramsUsers);

			// Invoker (if no invoker = all)
			wcResponsible.append(" OR EXISTS ("
					+ " SELECT 1 FROM AD_WF_Responsible r"
					+ " WHERE"
					+ " AD_WF_Activity.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID"
					+ " AND COALESCE(r.AD_User_ID,0)=0 AND COALESCE(r.AD_Role_ID,0)=0"
					+ " AND (AD_WF_Activity.AD_User_ID IN " + wcUsers + " OR AD_WF_Activity.AD_User_ID IS NULL)"
					+ ")");
			paramsResponsible.addAll(paramsUsers);

			// Responsible User (Human)
			wcResponsible.append(" OR EXISTS ("
					+ " SELECT 1 FROM AD_WF_Responsible r"
					+ " WHERE"
					+ " AD_WF_Activity.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID"
					+ " AND r.AD_User_ID IN " + wcUsers
					+ ")");
			paramsResponsible.addAll(paramsUsers);

			// Responsible Role
			wcResponsible.append(" OR EXISTS ("
					+ " SELECT 1 FROM AD_WF_Responsible r"
					+ " INNER JOIN AD_User_Roles ur ON (r.AD_Role_ID=ur.AD_Role_ID)"
					+ " WHERE"
					+ " AD_WF_Activity.AD_WF_Responsible_ID=r.AD_WF_Responsible_ID"
					+ " AND ur.AD_User_ID IN " + wcUsers
					+ ")");
			paramsResponsible.addAll(paramsUsers);
		}

		//
		// Main Filter
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<>();
		{
			// Not Processed
			wc.append(I_AD_WF_Activity.COLUMNNAME_Processed).append("=?");
			params.add(false);

			// Suspended activities
			wc.append(" AND ");
			wc.append(I_AD_WF_Activity.COLUMNNAME_WFState).append("=?");
			params.add(X_AD_WF_Activity.WFSTATE_Suspended);

			// Responsible
			wc.append(" AND (").append(wcResponsible).append(")");
			params.addAll(paramsResponsible);
		}

		//
		// Order by
		final IQueryOrderBy queryOrderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_AD_WF_Activity.class)
				.addColumn(I_AD_WF_Activity.COLUMNNAME_Priority, false)
				.addColumn(I_AD_WF_Activity.COLUMNNAME_Created, true)
				.createQueryOrderBy();

		final IQuery<I_AD_WF_Activity> query = new TypedSqlQuery<>(ctx, I_AD_WF_Activity.class, wc.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				// .setApplyAccessFilterRW(false) // NOTE: commented out because generated SQL is not correct
				.setOrderBy(queryOrderBy);
		return query;
	}

	private List<Integer> getUserAndSubstitutedsIds()
	{
		final int loggedUserId = Env.getAD_User_ID(ctx);
		final Timestamp date = SystemTime.asDayTimestamp();
		return getUserAndSubstitutedsIds(loggedUserId, date);

	}

	private List<Integer> getUserAndSubstitutedsIds(final int userId, final Timestamp date)
	{
		final List<Integer> userIds = new ArrayList<>();
		userIds.add(userId);

		for (final I_AD_User u : Services.get(IUserDAO.class).retrieveUsersSubstitudedBy(ctx, userId, date, ITrx.TRXNAME_None))
		{
			final int substitutedUserId = u.getAD_User_ID();
			if (userIds.contains(substitutedUserId))
			{
				continue;
			}

			userIds.addAll(getUserAndSubstitutedsIds(substitutedUserId, date));
		}

		return userIds;
	}

	/**
	 * Get active activities count
	 * 
	 * @return int
	 */
	public int getActivitiesCount()
	{
		final int count = getActivitiesQuery().count();
		return count;
	}

	public List<MWFActivity> retrieveActivities()
	{
		long start = System.currentTimeMillis();

		final int maxActivities = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_MAX_ACTIVITIES_IN_LIST, SYSCONFIG_MAX_ACTIVITIES_IN_LIST_DEFAULT, Env.getAD_Client_ID(ctx));

		final List<MWFActivity> activities = getActivitiesQuery()
				.copy()
				.setLimit(maxActivities)
				.list(MWFActivity.class);

		logger.debug("#" + activities.size() + "(" + (System.currentTimeMillis() - start) + "ms)");

		return activities;
	}

}
