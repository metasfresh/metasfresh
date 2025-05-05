/*
 * #%L
 * de-metas-salesorder
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.salesorder.candidate;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import static de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer.WP_PARAM_CLOSE_ORDER;
import static de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer.WP_PARAM_INVOICE;
import static de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer.WP_PARAM_SHIP;
import static de.metas.salesorder.candidate.ProcessOLCandsWorkpackageEnqueuer.WP_PARAM_VALID_OLCANDIDS_SELECTIONID;

public class ProcessOLCandsWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final AutoProcessingOLCandService autoProcessingOlCandService = SpringContextHolder.instance.getBean(AutoProcessingOLCandService.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, final String localTrxName)
	{
		final PInstanceId validOLCandIdsPInstance = getParameters().getParameterAsId(WP_PARAM_VALID_OLCANDIDS_SELECTIONID, PInstanceId.class);
		Check.assumeNotNull(validOLCandIdsPInstance, "PInstance for olCandIds must be not null");

		final ProcessOLCandsRequest request = ProcessOLCandsRequest.builder()
				.pInstanceId(validOLCandIdsPInstance)
				.ship(getParameters().getParameterAsBool(WP_PARAM_SHIP))
				.invoice(getParameters().getParameterAsBool(WP_PARAM_INVOICE))
				.closeOrder(getParameters().getParameterAsBool(WP_PARAM_CLOSE_ORDER))
				.build();

		autoProcessingOlCandService.processOLCands(request);

		return Result.SUCCESS;
	}
}
