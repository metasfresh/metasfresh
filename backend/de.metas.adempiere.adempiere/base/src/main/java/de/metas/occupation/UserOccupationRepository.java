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

package de.metas.occupation;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User_Occupation_AdditionalSpecialization;
import org.compiere.model.I_AD_User_Occupation_Specialization;
import org.compiere.model.I_CRM_Occupation;
import org.springframework.stereotype.Repository;

@Repository
public class UserOccupationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void deleteUserSpecializationByUserJob(@NonNull final Integer userJobId, @NonNull final Integer userId)
	{
		final IQuery<I_CRM_Occupation> occupationList =
				queryBL.createQueryBuilder(I_CRM_Occupation.class)
				.addEqualsFilter(I_CRM_Occupation.COLUMN_CRM_Occupation_Parent_ID, userJobId)
				.create();

		queryBL.createQueryBuilder(I_AD_User_Occupation_Specialization.class)
				.addEqualsFilter(I_AD_User_Occupation_Specialization.COLUMNNAME_AD_User_ID, userId)
				.addInSubQueryFilter(I_AD_User_Occupation_Specialization.COLUMN_CRM_Occupation_ID, I_CRM_Occupation.COLUMN_CRM_Occupation_ID, occupationList)
				.create()
				.delete();
	}

	public void deleteUserAdditionalSpecializationByUserSpecialization(@NonNull final Integer userSpecializationId, @NonNull final Integer userId)
	{
		final IQuery<I_CRM_Occupation> occupationList =
				queryBL.createQueryBuilder(I_CRM_Occupation.class)
						.addEqualsFilter(I_CRM_Occupation.COLUMN_CRM_Occupation_Parent_ID, userSpecializationId)
						.create();

		queryBL.createQueryBuilder(I_AD_User_Occupation_AdditionalSpecialization.class)
				.addEqualsFilter(I_AD_User_Occupation_AdditionalSpecialization.COLUMNNAME_AD_User_ID, userId)
				.addInSubQueryFilter(I_AD_User_Occupation_AdditionalSpecialization.COLUMN_CRM_Occupation_ID, I_CRM_Occupation.COLUMN_CRM_Occupation_ID, occupationList)
				.create()
				.delete();
	}
}
