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

package de.metas.bpartner.user.role.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_User_Assigned_Role;
import org.compiere.model.I_C_User_Role;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_User_Assigned_Role.class)
@Component
public class C_User_Assigned_Role
{
	private static final AdMessageKey MSG_USER_ROLE_ALREADY_ASSIGNED = AdMessageKey.of("UserRoleAlreadyAssigned");
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW, ifColumnsChanged = { I_C_User_Assigned_Role.COLUMNNAME_C_User_Role_ID, I_C_User_Assigned_Role.COLUMNNAME_AD_User_ID })
	public void validateRoleUniqueForBPartner(@NonNull final I_C_User_Assigned_Role assignedRole)
	{
		final IQuery<I_AD_User> currentBpartnerQuery = queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_User_ID, assignedRole.getAD_User_ID())
				.create();

		final IQuery<I_C_User_Assigned_Role> uniqueForBpartnerQuery = queryBL.createQueryBuilder(I_C_User_Role.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_User_Role.COLUMNNAME_C_User_Role_ID, assignedRole.getC_User_Role_ID())
				.addEqualsFilter(I_C_User_Role.COLUMNNAME_IsUniqueForBPartner, true)
				.andCollectChildren(I_C_User_Assigned_Role.COLUMN_C_User_Role_ID)
				.addOnlyActiveRecordsFilter().create();

		final boolean anyMatch = queryBL.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_AD_User.COLUMNNAME_AD_User_ID, I_C_User_Assigned_Role.COLUMNNAME_AD_User_ID, uniqueForBpartnerQuery)
				.addInSubQueryFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, I_AD_User.COLUMNNAME_C_BPartner_ID, currentBpartnerQuery)
				.create()
				.anyMatch();

		if (anyMatch)
		{
			throw new AdempiereException(MSG_USER_ROLE_ALREADY_ASSIGNED).markAsUserValidationError();
		}
	}
}
