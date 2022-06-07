package org.adempiere.service;

import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_AD_Preference;

import java.util.Properties;

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

/**
 * We moved this code from a legacy codebase's IValuePreferenceBL implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/1771
 */
public interface IValuePreferenceDAO extends ISingletonService
{
	boolean remove(Properties ctx, String attribute, int adClientId, int adOrgId, int adUserId, int adWindowId);

	/**
	 * Store the given {@code value} as {@link I_AD_Preference} and also invoke {@link org.compiere.util.Env#setPreference(Properties, org.adempiere.service.IValuePreferenceBL.IUserValuePreference)}.
	 * 
	 * @param ctx
	 * @param attribute
	 * @param value
	 * @param adClientId
	 * @param adOrgId
	 * @param adUserId
	 * @param adWindowId
	 */
	void save(Properties ctx, String attribute, Object value, int adClientId, int adOrgId, int adUserId, int adWindowId);

	/**
	 *
	 * @param userId
	 */
	void deleteUserPreferenceByUserId(final UserId userId);
}
