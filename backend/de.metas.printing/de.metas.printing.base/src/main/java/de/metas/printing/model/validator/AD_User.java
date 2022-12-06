/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing.model.validator;

import de.metas.printing.model.I_AD_User_Login;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_User.class)
@Component
public class AD_User
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void deleteUser(final I_AD_User userRecord)
	{
		UserId userId = UserId.ofRepoId(userRecord.getAD_User_ID());
		queryBL.createQueryBuilder(I_AD_User_Login.class)
				.addEqualsFilter(I_AD_User_Login.COLUMNNAME_AD_User_ID, userId)
				.create()
				.delete();
	}
}
