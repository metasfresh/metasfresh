/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.inout.IInOutBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Validator(I_M_InOut.class)
@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private final IInterimInvoiceFlatrateTermBL interimInvoiceBL = Services.get(IInterimInvoiceFlatrateTermBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	private final ModularContractLogService modularContractLogService;

	public M_InOut(@NonNull final ModularContractLogService modularContractLogService)
	{
		this.modularContractLogService = modularContractLogService;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_COMPLETE
	})
	public void addToInterimContractIfNeeded(final I_M_InOut inOut)
	{
		if (inOut.getC_Order_ID() <= 0)
		{
			return;
		}
		interimInvoiceBL.updateInterimInvoiceFlatrateTermForInOut(inOut);
	}

	@ModelChange(
			timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_M_InOut.COLUMNNAME_IsInterimInvoiceable)
	public void updateLogBillableStatus(@NonNull final I_M_InOut inOut)
	{
		if (inOut.isSOTrx())
		{
			return;
		}

		modularContractLogService.changeBillableStatus(createModularContractLogQuery(inOut), inOut.isInterimInvoiceable());
	}
	
	@NonNull
	private ModularContractLogQuery createModularContractLogQuery(@NonNull final I_M_InOut inOut)
	{
		final ImmutableList<TableRecordReference> tableRecordReferences = inOutBL.getLines(inOut)
				.stream()
				.map(TableRecordReference::of)
				.collect(ImmutableList.toImmutableList());

		return ModularContractLogQuery.builder()
				.referenceSet(TableRecordReferenceSet.of(tableRecordReferences))
				.contractType(LogEntryContractType.INTERIM)
				.build();
	}
}
