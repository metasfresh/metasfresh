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

import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User_Occupation_Specialization;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_AD_User_Occupation_Specialization.class)
public class AD_User_Occupation_Specialization
{
	private final UserOccupationRepository userOccupationRepository;

	public AD_User_Occupation_Specialization(@NonNull final UserOccupationRepository userOccupationRepository)
	{
		this.userOccupationRepository = userOccupationRepository;
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_DELETE}
	)
	public void onDeleteUserOccupationJob(final I_AD_User_Occupation_Specialization userOccupationSpecialization) {
		userOccupationRepository.deleteUserAdditionalSpecializationByUserSpecialization(userOccupationSpecialization.getCRM_Occupation_ID(), userOccupationSpecialization.getAD_User_ID());
	}
}
