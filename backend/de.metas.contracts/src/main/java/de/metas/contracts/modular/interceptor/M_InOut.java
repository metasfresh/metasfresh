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

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REACTIVATED;
import static de.metas.contracts.modular.ModelAction.REVERSED;
import static de.metas.contracts.modular.ModelAction.VOIDED;

@Component
@Interceptor(I_M_InOut.class)
public class M_InOut
{
	private final ModularContractService contractService;
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	public M_InOut(@NonNull final ModularContractService contractService, @NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.contractService = contractService;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_M_InOut inOutRecord)
	{
		inOutDAO.retrieveAllLines(inOutRecord)
				.forEach(line -> {
					propagateFlatrateTerm(line);
					propagateHarvestingDetails(line);
				});

		invokeHandlerForEachLine(inOutRecord, COMPLETED);

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void afterReverse(@NonNull final I_M_InOut inOutRecord)
	{
		invokeHandlerForEachLine(inOutRecord, REVERSED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void afterReactivate(@NonNull final I_M_InOut inOutRecord)
	{
		invokeHandlerForEachLine(inOutRecord, REACTIVATED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID })
	public void afterVoid(@NonNull final I_M_InOut inOutRecord)
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
			@NonNull final ModelAction modelAction)
	{
		inOutDAO.retrieveAllLines(inOutRecord).forEach(line -> contractService.scheduleLogCreation(
				DocStatusChangedEvent.builder()
						.tableRecordReference(TableRecordReference.of(line))
						.modelAction(modelAction)
						.userInChargeId(Env.getLoggedUserId())
						.build())
		);
	}

	private void propagateHarvestingDetails(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final OrderId orderId = inOutDAO.getOrderIdForLineId(InOutLineId.ofRepoId(inOutLineRecord.getM_InOutLine_ID())).orElse(null);

		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoIdOrNull(inOutLineRecord.getC_Flatrate_Term_ID());
		if (flatrateTermId != null)
		{
			final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
			if (modularContractSettings != null)
			{
				final YearAndCalendarId harvestingYearAndCalendarId = modularContractSettings.getYearAndCalendarId();
				inOutLineRecord.setC_Harvesting_Calendar_ID(harvestingYearAndCalendarId.calendarId().getRepoId());
				inOutLineRecord.setHarvesting_Year_ID(harvestingYearAndCalendarId.yearId().getRepoId());
			}
		}
		else if (orderId != null)
		{
			final I_C_Order order = orderDAO.getById(orderId);
			inOutLineRecord.setC_Harvesting_Calendar_ID(order.getC_Harvesting_Calendar_ID());
			inOutLineRecord.setHarvesting_Year_ID(order.getHarvesting_Year_ID());
		}
		else // make sure we reset the value
		{
			inOutLineRecord.setC_Harvesting_Calendar_ID(-1);
			inOutLineRecord.setHarvesting_Year_ID(-1);
		}

		inOutDAO.save(inOutLineRecord);
	}
}

