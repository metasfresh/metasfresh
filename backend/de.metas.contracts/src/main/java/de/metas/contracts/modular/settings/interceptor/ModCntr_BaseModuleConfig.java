/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.contracts.ModularContractSettingsId;
import de.metas.contracts.model.I_ModCntr_BaseModuleConfig;
import de.metas.contracts.modular.settings.ModularContractSettingsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_ModCntr_BaseModuleConfig.class)
@AllArgsConstructor
public class ModCntr_BaseModuleConfig
{
	@NonNull private final ModularContractSettingsService modularContractSettingsService;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void validateBaseModuleConfig(@NonNull final I_ModCntr_BaseModuleConfig baseModuleConfigRecord)
	{
		modularContractSettingsService.validateModularContractSettingsNotUsed(ModularContractSettingsId.ofRepoId(baseModuleConfigRecord.getModCntr_Settings_ID()));
	}
}
