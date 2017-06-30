package de.metas.handlingunits.trace;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.util.Services;
import org.compiere.Adempiere;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@UtilityClass
public class HUTraceUtil
{

	/**
	 * Takes the given builder and stream of model instances and creates {@link HUTraceEvent}s for the models'
	 * {@link I_M_HU_Assignment}s' top delve HUs.
	 * 
	 * @param builder a pre fabricated builder. {@code huId}s and {@code eventTime} will be set by this method.
	 * @param models
	 */
	public void createAndAddEvents(
			@NonNull final HUTraceEventBuilder builder,
			@NonNull final Stream<?> models)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final HUTraceRepository huTraceRepository = getHUTraceRepository();

		final Stream<I_M_HU_Assignment> huAssignments = models
				.flatMap(model -> huAssignmentDAO.retrieveHUAssignmentsForModel(model).stream());

		final List<Integer> assgnedTopLevelHuIds = huAssignments
				.map(huAssignment -> huAssignment.getM_HU_ID())
				.sorted()
				.distinct()
				.collect(Collectors.toList());

		final Instant eventTime = Instant.now();

		for (final int assgnedTopLevelHuId : assgnedTopLevelHuIds)
		{
			final HUTraceEvent event = builder
					.huId(assgnedTopLevelHuId)
					.eventTime(eventTime)
					.build();
			huTraceRepository.addEvent(event);
		}
	}

	private HUTraceRepository getHUTraceRepository()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new HUTraceRepository(); // no need to fire up spring just to unit-test this class
		}

		final HUTraceRepository huTraceRepository = Adempiere.getBean(HUTraceRepository.class);
		return huTraceRepository;
	}
}
