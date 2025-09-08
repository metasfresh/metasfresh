/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleExternalInfo;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.function.Function;

@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_CreateReceipt_MovementDateParam
		extends WEBUI_M_HU_CreateReceipt_Base
		implements IProcessPrecondition
{
	private final static String MOVEMENT_DATE_PARAM_NAME = I_M_ReceiptSchedule.COLUMNNAME_MovementDate;
	@Param(parameterName = MOVEMENT_DATE_PARAM_NAME, mandatory = true)
	private LocalDate movementDate;

	@Override
	protected void customizeParametersBuilder(@NonNull final CreateReceiptsParametersBuilder parametersBuilder)
	{
		final ReceiptScheduleExternalInfo scheduleExternalInfo = ReceiptScheduleExternalInfo.builder()
				.movementDate(movementDate)
				.build();

		final ImmutableMap<ReceiptScheduleId, ReceiptScheduleExternalInfo> externalInfoByScheduleId = getM_ReceiptSchedules()
				.stream()
				.map(I_M_ReceiptSchedule::getM_ReceiptSchedule_ID)
				.map(ReceiptScheduleId::ofRepoId)
				.distinct()
				.collect(ImmutableMap.toImmutableMap(Function.identity(), (ignored) -> scheduleExternalInfo));

		parametersBuilder
				.movementDateRule(ReceiptMovementDateRule.EXTERNAL_DATE_IF_AVAIL)
				.externalInfoByReceiptScheduleId(externalInfoByScheduleId);
	}
}
