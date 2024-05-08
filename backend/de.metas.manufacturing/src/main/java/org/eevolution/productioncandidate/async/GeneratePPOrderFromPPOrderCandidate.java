/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer.WP_PINSTANCE_ID_PARAM;

public class GeneratePPOrderFromPPOrderCandidate extends WorkpackageProcessorAdapter
{
	private final PPOrderCandidateService ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final PInstanceId pInstanceId = getParameters().getParameterAsId(WP_PINSTANCE_ID_PARAM, PInstanceId.class);

		Check.assumeNotNull(pInstanceId, "adPInstanceId is not null");

		final Iterator<I_PP_Order_Candidate> orderCandidates = ppOrderCandidateService.retrieveOCForSelection(pInstanceId);

		final Stream<I_PP_Order_Candidate> candidateStream = StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(orderCandidates, Spliterator.ORDERED), false);

		final OrderGenerateResult result = ppOrderCandidateService.processCandidates(candidateStream);

		Loggables.addLog("Generated: {}", result);

		return Result.SUCCESS;
	}
}