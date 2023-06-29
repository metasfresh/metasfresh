/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.settings.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_ModCntr_Module.class)
public class ModCntr_Module
{
	public static final String MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED = "@ModCntr_Settings_cannot_be_changed@";
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE, ModelValidator.TYPE_BEFORE_NEW })
	public void validateSettingsNotUsedAlready(@NonNull final I_ModCntr_Module type)
	{
		if (queryBL.createQueryBuilder(I_ModCntr_Settings.class)
				.addEqualsFilter(I_ModCntr_Settings.COLUMN_ModCntr_Settings_ID, type.getModCntr_Settings_ID())
				.andCollectChildren(I_C_Flatrate_Conditions.COLUMN_ModCntr_Settings_ID)
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_DocStatus, X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.anyMatch())
		{
			throw new AdempiereException(MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED);
		}
	}
}
