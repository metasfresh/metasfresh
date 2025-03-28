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

import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_OrderLine.class)
@RequiredArgsConstructor
public class C_OrderLine
{
	private static final AdMessageKey MSG_ERR_DELETION_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.interceptor.C_OrderLine.DeletionNotAllowed");


	@NonNull private final ModularContractLogDAO contractLogDAO;
	@NonNull private final ModularContractService contractService;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_C_OrderLine orderLine)
	{
		final TableRecordReference recordReference = TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID());

		if (contractLogDAO.hasAnyModularLogs(recordReference))
		{
			throw new AdempiereException(MSG_ERR_DELETION_NOT_ALLOWED)
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_M_Product_ID })
	public void updatePurchaseModularContractIdOnChange(@NonNull final I_C_OrderLine orderLine)
	{
		orderLine.setPurchase_Modular_Flatrate_Term_ID(-1); //prevent error if multiple eligible contracts exists and it can't be updated automatically
		contractService.updatePurchaseModularContractId(orderLine, false);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setPurchaseModularContractIdOnNew(@NonNull final I_C_OrderLine orderLine)
	{
		contractService.updatePurchaseModularContractId(orderLine, false);
	}
}
