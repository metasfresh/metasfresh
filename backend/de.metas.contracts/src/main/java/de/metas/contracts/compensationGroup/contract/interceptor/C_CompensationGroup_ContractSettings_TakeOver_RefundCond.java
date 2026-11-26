/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverId;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverRefundCondId;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverRepository;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * A refund condition is listed on at most one take-over of the same settings, so that its refund percentage is never counted twice
 * (no DB index can span parent and child).
 */
@Interceptor(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class)
@Component
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_RefundCond
{
	private static final AdMessageKey MSG_TakeOverRefundCondNotUnique = AdMessageKey.of("ContractCompensationGroup_TakeOverRefundCondNotUnique");

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.COLUMNNAME_C_Flatrate_Conditions_ID,
					I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID,
					I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.COLUMNNAME_IsActive })
	public void assertRefundConditionUniquePerSettings(final I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond record)
	{
		if (!record.isActive())
		{
			return;
		}

		final ConditionsId conditionsId = ConditionsId.ofRepoId(record.getC_Flatrate_Conditions_ID());
		final boolean isAlreadyListed = takeOverRepository.isRefundConditionOfSameSettings(
				ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID()),
				conditionsId,
				ContractSettingsTakeOverRefundCondId.ofRepoIdOrNull(record.getC_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID()));

		if (isAlreadyListed)
		{
			throw new AdempiereException(MSG_TakeOverRefundCondNotUnique, flatrateDAO.getConditionsById(conditionsId).getName());
		}
	}
}
