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
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_M_InOutLine.class)
public class M_InOutLine
{
	private final ModularContractLogService modularContractLogService;
	private final ModularContractSettingsDAO modularContractSettingsDAO;
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	public M_InOutLine(@NonNull final ModularContractLogService modularContractLogService,
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractLogService = modularContractLogService;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final TableRecordReference inOutLineRecordRef = TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID());
		modularContractLogService.throwErrorIfLogExistsForDocumentLine(inOutLineRecordRef);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			de.metas.inout.model.I_M_InOutLine.COLUMNNAME_C_Flatrate_Term_ID })
	public void propagateHarvestingDetails(@NonNull final I_M_InOutLine inOutLineRecord)
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
	}
}
