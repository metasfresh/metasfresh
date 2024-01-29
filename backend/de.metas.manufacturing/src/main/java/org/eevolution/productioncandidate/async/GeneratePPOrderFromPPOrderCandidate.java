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

import com.google.common.collect.ImmutableList;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.api.IParams;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.service.PPOrderCandidateProcessRequest;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.eevolution.productioncandidate.service.produce.PPOrderCandidateToAllocate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer.WP_AUTO_CLOSE_CANDIDATES_AFTER_PRODUCTION;
import static org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer.WP_AUTO_PROCESS_CANDIDATES_AFTER_PRODUCTION;
import static org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer.WP_COMPLETE_DOC_PARAM;
import static org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer.WP_PINSTANCE_ID_PARAM;

public class GeneratePPOrderFromPPOrderCandidate extends WorkpackageProcessorAdapter
{
	private final PPOrderCandidateService ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final PPOrderCandidateProcessRequest ppOrderCandidateProcessRequest = getProcessPPOrderCandRequest();

		final OrderGenerateResult result = ppOrderCandidateService.processCandidates(ppOrderCandidateProcessRequest);

		Loggables.addLog("Generated: {}", result);

		return Result.SUCCESS;
	}

	@NonNull
	private PPOrderCandidateProcessRequest getProcessPPOrderCandRequest()
	{
		final IParams params = getParameters();

		final PInstanceId pInstanceId = params.getParameterAsId(WP_PINSTANCE_ID_PARAM, PInstanceId.class);
		final Boolean isDocCompleteOverride = params.getParameterAsBoolean(WP_COMPLETE_DOC_PARAM);
		final boolean autoProcessCandidatesAfterProduction = params.getParameterAsBool(WP_AUTO_PROCESS_CANDIDATES_AFTER_PRODUCTION);
		final boolean autoCloseCandidatesAfterProduction = params.getParameterAsBool(WP_AUTO_CLOSE_CANDIDATES_AFTER_PRODUCTION);

		Check.assumeNotNull(pInstanceId, "adPInstanceId is not null");

		final Iterator<I_PP_Order_Candidate> orderCandidates = ppOrderCandidateService.retrieveOCForSelection(pInstanceId);

		final Stream<I_PP_Order_Candidate> candidateStream = StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(orderCandidates, Spliterator.ORDERED), false);

		return PPOrderCandidateProcessRequest.builder()
				.isDocCompleteOverride(isDocCompleteOverride)
				.autoProcessCandidatesAfterProduction(autoProcessCandidatesAfterProduction)
				.autoCloseCandidatesAfterProduction(autoCloseCandidatesAfterProduction)
				.sortedCandidates(getSortedCandidates(candidateStream))
				.build();
	}

	@NonNull
	private ImmutableList<PPOrderCandidateToAllocate> getSortedCandidates(@NonNull final Stream<I_PP_Order_Candidate> candidateStream)
	{
		final Map<String, PPOrderCandidatesGroup> headerAgg2PPOrderCandGroup = new HashMap<>();

		candidateStream
				.filter(orderCandidate -> !orderCandidate.isProcessed())
				.sorted(Comparator.comparingInt(I_PP_Order_Candidate::getPP_Order_Candidate_ID))
				.map(PPOrderCandidateToAllocate::of)
				.forEach(cand -> addPPOrderCandidateToGroup(headerAgg2PPOrderCandGroup, cand));

		final ImmutableList.Builder<PPOrderCandidateToAllocate> sortedCandidates = new ImmutableList.Builder<>();

		headerAgg2PPOrderCandGroup.values()
				.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.nullsLast(Comparator.comparing(PPOrderCandidatesGroup::getGroupSeqNo)))
				.map(PPOrderCandidatesGroup::getPpOrderCandidateToAllocateList)
				.forEach(sortedCandidates::addAll);

		return sortedCandidates.build();
	}

	private static void addPPOrderCandidateToGroup(
			@NonNull final Map<String, PPOrderCandidatesGroup> headerAgg2PPOrderCandGroup,
			@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
	{
		if (headerAgg2PPOrderCandGroup.get(ppOrderCandidateToAllocate.getHeaderAggregationKey()) == null)
		{
			headerAgg2PPOrderCandGroup.put(ppOrderCandidateToAllocate.getHeaderAggregationKey(), PPOrderCandidatesGroup.of(ppOrderCandidateToAllocate));
		}
		else
		{
			final PPOrderCandidatesGroup group = headerAgg2PPOrderCandGroup.get(ppOrderCandidateToAllocate.getHeaderAggregationKey());
			group.addToGroup(ppOrderCandidateToAllocate);
		}
	}

	@Getter
	@EqualsAndHashCode
	private static class PPOrderCandidatesGroup
	{
		@Nullable
		private Integer groupSeqNo;

		@NonNull
		private final List<PPOrderCandidateToAllocate> ppOrderCandidateToAllocateList;

		private PPOrderCandidatesGroup(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
		{
			this.ppOrderCandidateToAllocateList = new ArrayList<>();

			this.ppOrderCandidateToAllocateList.add(ppOrderCandidateToAllocate);
			this.groupSeqNo = ppOrderCandidateToAllocate.getPpOrderCandidate().getSeqNo() > 0
					? ppOrderCandidateToAllocate.getPpOrderCandidate().getSeqNo()
					: null;
		}

		@NonNull
		public static PPOrderCandidatesGroup of(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
		{
			return new PPOrderCandidatesGroup(ppOrderCandidateToAllocate);
		}

		public void addToGroup(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
		{
			ppOrderCandidateToAllocateList.add(ppOrderCandidateToAllocate);
			updateSeqNo(ppOrderCandidateToAllocate.getPpOrderCandidate().getSeqNo());
		}

		private void updateSeqNo(final int newSeqNo)
		{
			if (newSeqNo <= 0 || (groupSeqNo != null && groupSeqNo <= newSeqNo))
			{
				return;
			}

			this.groupSeqNo = newSeqNo;
		}
	}
}