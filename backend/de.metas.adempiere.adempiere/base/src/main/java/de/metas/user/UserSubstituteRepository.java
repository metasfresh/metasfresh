/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.user;

import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_User_Substitute;
import org.springframework.stereotype.Repository;

@Repository
public class UserSubstituteRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void deleteUserSubstituteByUserId(final UserId userId)
	{
		queryBL.createQueryBuilder(I_AD_User_Substitute.class)
				.addEqualsFilter(I_AD_User_Substitute.COLUMNNAME_AD_User_ID, userId)
				.create()
				.delete();
	}
}
