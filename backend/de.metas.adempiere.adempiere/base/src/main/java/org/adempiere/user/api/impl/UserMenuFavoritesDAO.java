package org.adempiere.user.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserMenuFavoritesDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_TreeBar;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class UserMenuFavoritesDAO implements IUserMenuFavoritesDAO
{
	private static final Logger logger = LogManager.getLogger(UserMenuFavoritesDAO.class);

	@Override
	public void add(final int adUserId, final int adMenuId)
	{
		final boolean exists = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_TreeBar.class)
				.addEqualsFilter(I_AD_TreeBar.COLUMN_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_TreeBar.COLUMN_Node_ID, adMenuId)
				.create()
				.match();
		if (exists)
		{
			logger.warn("Not creating a favorite record for adUserId={}, adMenuId={} because another one already exists", adUserId, adMenuId);
			return;
		}
		
		final I_AD_TreeBar favorite = InterfaceWrapperHelper.newInstance(I_AD_TreeBar.class);
		favorite.setAD_User_ID(adUserId);
		favorite.setNode_ID(adMenuId);
		InterfaceWrapperHelper.save(favorite);
	}

	@Override
	public boolean remove(final int adUserId, final int adMenuId)
	{
		final int deleted = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_TreeBar.class)
				.addEqualsFilter(I_AD_TreeBar.COLUMN_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_TreeBar.COLUMN_Node_ID, adMenuId)
				.create()
				.deleteDirectly();
		return deleted > 0;
	}

	@Override
	public List<Integer> retrieveMenuIdsForUser(final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_TreeBar.class)
				.addEqualsFilter(I_AD_TreeBar.COLUMN_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_AD_TreeBar.COLUMNNAME_Node_ID, Integer.class);

	}
}
