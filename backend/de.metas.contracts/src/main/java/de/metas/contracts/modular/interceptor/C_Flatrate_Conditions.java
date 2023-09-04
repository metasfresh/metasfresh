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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Flatrate_Conditions.class)
public class C_Flatrate_Conditions
{
	private static final AdMessageKey MSG_ERROR_INVALID_MODULAR_CONTRACT_SETTINGS = AdMessageKey.of("de.metas.contracts.modular.interceptor.C_Flatrate_Conditions.INVALID_MODULAR_CONTRACT_SETTINGS");

	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public C_Flatrate_Conditions(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(@NonNull final I_C_Flatrate_Conditions record)
	{
		final TypeConditions typeConditions = TypeConditions.ofCode(record.getType_Conditions());

		if (!typeConditions.isModularContractType())
		{
			return;
		}

		validateModularContractSettings(ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID()));
	}

	private void validateModularContractSettings(@NonNull final ConditionsId conditionsId)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateConditionsId(conditionsId);
		if (settings.getModuleConfigs().isEmpty())
		{
			throw new AdempiereException(MSG_ERROR_INVALID_MODULAR_CONTRACT_SETTINGS)
					.markAsUserValidationError();
		}
	}
}
