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
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

		final Optional<OrderId> orderId = inOutDAO.getOrderIdForLineId(InOutLineId.ofRepoId(inOutLineRecord.getM_InOutLine_ID()));


		if (inOutLineRecord.getC_Flatrate_Term_ID() > 0)
		{
			final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(inOutLineRecord.getC_Flatrate_Term_ID());
			final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
			final YearAndCalendarId harvestingYearAndCalendarId = modularContractSettings.getYearAndCalendarId();
			if (harvestingYearAndCalendarId != null)
			{
				inOutLineRecord.setC_Harvesting_Calendar_ID(harvestingYearAndCalendarId.calendarId().getRepoId());
				inOutLineRecord.setHarvesting_Year_ID(harvestingYearAndCalendarId.yearId().getRepoId());
			}
		}
		else if (orderId.isPresent())
		{
			final I_C_Order order = orderDAO.getById(orderId.get());
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
