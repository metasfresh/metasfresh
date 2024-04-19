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

package de.metas.contracts.modular.interim.invoice.service.impl;

import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.command.InterimInvoiceFlatrateTermCreateCommand;
import de.metas.contracts.modular.interim.invoice.service.IInterimFlatrateTermService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsBL;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.logging.LogManager;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.sql.Timestamp;

public class InterimFlatrateTermService implements IInterimFlatrateTermService
{
	private final ModularContractSettingsDAO modularContractSettingsDAO = SpringContextHolder.instance.getBean(ModularContractSettingsDAO.class);
	private final ModularContractSettingsBL modularContractSettingsBL = SpringContextHolder.instance.getBean(ModularContractSettingsBL.class);

	private static final Logger logger = LogManager.getLogger(InterimFlatrateTermService.class);

	@Override
	public void create(
			@NonNull final I_C_Flatrate_Term modularFlatrateTermRecord,
			@NonNull final Timestamp startDate,
			@NonNull final Timestamp endDate)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(modularFlatrateTermRecord.getC_Flatrate_Term_ID());

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(modularFlatrateTermRecord.getC_OrderLine_Term_ID());
		if (orderLineId == null)
		{
			logger.debug("On create skipped C_Flatrate_Term_ID={}, because of missing C_OrderLine_Term_ID", flatrateTermId);
			return;
		}

		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermId(flatrateTermId);
		final ConditionsId interimConditionsId = modularContractSettingsBL.retrieveFlatrateConditionId(modularContractSettings, TypeConditions.INTERIM_INVOICE);

		InterimInvoiceFlatrateTermCreateCommand.builder()
				.modulareFlatrateTermId(flatrateTermId)
				.conditionsId(interimConditionsId)
				.orderLineId(orderLineId)
				.dateFrom(TimeUtil.asInstantNonNull(startDate))
				.dateTo(TimeUtil.asInstantNonNull(endDate))
				.yearAndCalendarId(YearAndCalendarId.ofRepoId(modularFlatrateTermRecord.getHarvesting_Year_ID(), modularFlatrateTermRecord.getC_Harvesting_Calendar_ID()))
				.build()
				.execute();
	}
}
