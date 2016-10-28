package de.metas.ui.web.dashboard;

import java.util.Objects;
import java.util.Properties;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.MoreObjects;

import de.metas.i18n.IModelTranslationMap;
import de.metas.ui.web.base.model.I_WEBUI_Dashboard;
import de.metas.ui.web.base.model.I_WEBUI_DashboardItem;
import de.metas.ui.web.session.UserSession;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Service
public class UserDashboardRepository
{
	//
	// Services
	@Autowired
	private UserSession userSession;
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserDashboardKey, UserDashboard> userDashboadCache = CCache.newLRUCache(I_WEBUI_Dashboard.Table_Name + "#UserDashboard", Integer.MAX_VALUE, 0);

	public UserDashboard getUserDashboard()
	{
		final UserDashboardKey userDashboardKey = UserDashboardKey.of(userSession.getCtx());
		return userDashboadCache.getOrLoad(userDashboardKey, () -> retrieveUserDashboard(userDashboardKey));
	}

	private UserDashboard retrieveUserDashboard(final UserDashboardKey key)
	{
		final Properties ctx = userSession.getCtx();
		final int adClientId = key.getAD_Client_ID();

		final I_WEBUI_Dashboard webuiDashboard = queryBL
				.createQueryBuilder(I_WEBUI_Dashboard.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System, adClientId)
				//
				.orderBy()
				.addColumn(I_WEBUI_Dashboard.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_WEBUI_Dashboard.COLUMN_WEBUI_Dashboard_ID)
				.endOrderBy()
				//
				.create()
				.first(I_WEBUI_Dashboard.class);

		if (webuiDashboard == null)
		{
			return UserDashboard.EMPTY;
		}

		return createUserDashboard(ctx, webuiDashboard);
	}

	private UserDashboard createUserDashboard(final Properties ctx, final I_WEBUI_Dashboard webuiDashboard)
	{
		final UserDashboard.Builder userDashboardBuilder = UserDashboard.builder()
				.setId(webuiDashboard.getWEBUI_Dashboard_ID());

		queryBL
				.createQueryBuilder(I_WEBUI_DashboardItem.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_WEBUI_DashboardItem.COLUMN_WEBUI_Dashboard_ID, webuiDashboard.getWEBUI_Dashboard_ID())
				//
				.orderBy()
				.addColumn(I_WEBUI_DashboardItem.COLUMN_Y, Direction.Ascending, Nulls.First)
				.addColumn(I_WEBUI_DashboardItem.COLUMN_X, Direction.Ascending, Nulls.First)
				.addColumn(I_WEBUI_DashboardItem.COLUMN_WEBUI_DashboardItem_ID)
				.endOrderBy()
				//
				.create()
				.list(I_WEBUI_DashboardItem.class)
				.stream()
				.map(webuiDashboardItem -> createUserDashboardItem(webuiDashboardItem))
				.forEach(userDashboardItem -> userDashboardBuilder.addItem(userDashboardItem));

		return userDashboardBuilder.build();
	}

	private static UserDashboardItem createUserDashboardItem(final I_WEBUI_DashboardItem webuiDashboardItem)
	{
		final IModelTranslationMap trlsMap = InterfaceWrapperHelper.getModelTranslationMap(webuiDashboardItem);
		return UserDashboardItem.builder()
				.setId(webuiDashboardItem.getWEBUI_DashboardItem_ID())
				.setCaption(trlsMap.getColumnTrl(I_WEBUI_DashboardItem.COLUMNNAME_Name, webuiDashboardItem.getName()))
				.setUrl(webuiDashboardItem.getURL())
				.setGridX(webuiDashboardItem.getX())
				.setGridY(webuiDashboardItem.getY())
				.build();
	}

	@Immutable
	private static final class UserDashboardKey
	{
		public static final UserDashboardKey of(final Properties ctx)
		{
			final int adClientId = Env.getAD_Client_ID(ctx);
			return new UserDashboardKey(adClientId);
		}

		private final int adClientId;

		private UserDashboardKey(final int adClientId)
		{
			super();
			this.adClientId = adClientId < 0 ? -1 : adClientId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Client_ID", adClientId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(adClientId);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (obj == this)
			{
				return true;
			}
			if (obj instanceof UserDashboardKey)
			{
				final UserDashboardKey other = (UserDashboardKey)obj;
				return adClientId == other.adClientId;
			}
			return false;
		}

		public int getAD_Client_ID()
		{
			return adClientId;
		}
	}
}
