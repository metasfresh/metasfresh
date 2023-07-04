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
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.metas.contracts.modular.ModularContractService.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.REACTIVATED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.REVERSED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.VOIDED;

@Component
@Interceptor(I_M_InOut.class)
public class M_InOut
{
	private final ModularContractService contractService;

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);

	public M_InOut(@NonNull final ModularContractService contractService)
	{
		this.contractService = contractService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	void afterComplete(@NonNull final I_M_InOut inOutRecord)
	{
		inOutDAO.retrieveAllLines(inOutRecord)
				.forEach(this::propagateFlatrateTerm);

		invokeHandlerForEachLine(inOutRecord, COMPLETED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	void afterReverse(@NonNull final I_M_InOut inOutRecord)
	{
		invokeHandlerForEachLine(inOutRecord, REVERSED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	void afterReactivate(@NonNull final I_M_InOut inOutRecord)
	{
		invokeHandlerForEachLine(inOutRecord, REACTIVATED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID })
	void afterVoid(@NonNull final I_M_InOut inOutRecord)
	{
		invokeHandlerForEachLine(inOutRecord, VOIDED);
	}

	private void propagateFlatrateTerm(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final List<I_M_ReceiptSchedule_Alloc> lineAlloc = receiptScheduleDAO.retrieveRsaForInOutLine(inOutLineRecord);
		if (lineAlloc.size() == 1)
		{
			final I_M_ReceiptSchedule receiptScheduleRecord = receiptScheduleDAO.getById(ReceiptScheduleId.ofRepoId(lineAlloc.get(0).getM_ReceiptSchedule_ID()));
			inOutLineRecord.setC_Flatrate_Term_ID(receiptScheduleRecord.getC_Flatrate_Term_ID());
			inOutDAO.save(inOutLineRecord);
		}
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_M_InOut inOutRecord,
			@NonNull final ModularContractService.ModelAction modelAction)
	{
		inOutDAO.retrieveAllLines(inOutRecord)
				.forEach(line -> contractService.invokeWithModel(line, modelAction));
	}
}

