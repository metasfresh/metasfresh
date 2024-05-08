/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.contracts.model.I_ModCntr_Settings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_ModCntr_Settings.class)
@RequiredArgsConstructor
public class ModCntr_Settings
{
	@NonNull
	private final ModularContractSettingsBL modularContractSettingsBL;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void validateSettings(@NonNull final I_ModCntr_Settings record)
	{
		modularContractSettingsBL.validateModularContractSettingsNotUsed(ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {I_ModCntr_Settings.COLUMNNAME_M_Raw_Product_ID})
	public void upsertInvormativeLogsModule(@NonNull final  I_ModCntr_Settings record)
	{
		final ProductId rawProductId = ProductId.ofRepoIdOrNull(record.getM_Raw_Product_ID());

		if(rawProductId == null)
		{
			// nothing to do
			return;
		}

		final ModularContractSettingsId modularContractSettingsId = ModularContractSettingsId.ofRepoId(record.getModCntr_Settings_ID());
		final I_ModCntr_Module existingModuleConfig = modularContractSettingsBL.retrieveInformativeLogModule(modularContractSettingsId);

		if(existingModuleConfig== null)
		{
			createInformativeLogsModule(modularContractSettingsId);
		}

		if(!ProductId.ofRepoId(existingModuleConfig.getM_Product_ID()).equals(rawProductId))
		{
existingModuleConfig.setM_Product_ID(rawProductId.getRepoId());
sa
		}
	}

	private void createInformativeLogsModule(final ModularContractSettingsId modularContractSettingsId)
	{
		modularContractSettingsBL.createInformativeLogsModule(modularContractSettingsId);
	}

}
