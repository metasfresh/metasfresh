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

import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.i18n.AdMessageKey;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_ModCntr_Module.class)
@AllArgsConstructor
public class ModCntr_Module
{
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	public static final AdMessageKey MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED = AdMessageKey.of("ModCntr_Settings_cannot_be_changed");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE, ModelValidator.TYPE_BEFORE_NEW })
	public void validateSettingsNotUsedAlready(@NonNull final I_ModCntr_Module type)
	{
		final ModularContractSettingsId modCntrSettingsId = ModularContractSettingsId.ofRepoId(type.getModCntr_Settings_ID());

		if (modularContractSettingsDAO.isSettingsUsedInCompletedFlatrateConditions(modCntrSettingsId))
		{
			throw new AdempiereException(MOD_CNTR_SETTINGS_CANNOT_BE_CHANGED);
		}
	}

}
