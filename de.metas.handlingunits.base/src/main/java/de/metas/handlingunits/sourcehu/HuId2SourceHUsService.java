package de.metas.handlingunits.sourcehu;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class HuId2SourceHUsService
{
	private final HUTraceRepository huTraceRepository;

	public HuId2SourceHUsService(@NonNull final HUTraceRepository huTraceRepository)
	{
		this.huTraceRepository = huTraceRepository;
	}

	/**
	 * Uses HU-tracing to identify and return the (top-level) HUs that are the given {@code huIds}' source HUs.
	 * 
	 * @param huIds
	 * @return
	 */
	public Collection<I_M_HU> retrieveActualSourceHUs(@NonNull final List<Integer> huIds)
	{
		final Set<Integer> vhuSourceIds = retrieveVhus(huIds);

		final Set<I_M_HU> topLevelSourceHus = retrieveTopLevelSourceHus(vhuSourceIds);

		return topLevelSourceHus;
	}

	private Set<Integer> retrieveVhus(@NonNull final List<Integer> huIds)
	{
		final Set<Integer> vhuSourceIds = new HashSet<>();

		for (final int huId : huIds)
		{
			final HUTraceEventQuery query = HUTraceEventQuery.builder()
					.type(HUTraceType.TRANSFORM_LOAD)
					.topLevelHuId(huId)
					.build();

			final List<HUTraceEvent> traceEvents = huTraceRepository.query(query);
			for (final HUTraceEvent traceEvent : traceEvents)
			{
				vhuSourceIds.add(traceEvent.getVhuSourceId());
			}
		}
		return vhuSourceIds;
	}

	/**
	 * Don't use Services.get(IHandlingUnitsBL.class).getTopLevelHUs() because the sourceHUs might already be destroyed.f
	 * 
	 * @param vhuSourceIds
	 * @return
	 */
	private Set<I_M_HU> retrieveTopLevelSourceHus(@NonNull final Set<Integer> vhuSourceIds)
	{
		final Set<I_M_HU> topLevelSourceHus = new TreeSet<>(Comparator.comparing(I_M_HU::getM_HU_ID));
		for (int vhuSourceId : vhuSourceIds)
		{
			final HUTraceEventQuery query = HUTraceEventQuery.builder()
					.type(HUTraceType.TRANSFORM_LOAD)
					.vhuId(vhuSourceId)
					.build();
			final List<HUTraceEvent> traceEvents = huTraceRepository.query(query);
			for (final HUTraceEvent traceEvent : traceEvents)
			{
				final I_M_HU topLevelSourceHu = load(traceEvent.getTopLevelHuId(), I_M_HU.class);
				topLevelSourceHus.add(topLevelSourceHu);
			}
		}
		return topLevelSourceHus;
	}

	public Collection<Integer> retrieveMatchingSourceHUIds(final int huId)
	{
		final MatchingSourceHusQuery query = MatchingSourceHusQuery.fromHuId(huId);

		final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);
		return sourceHuDAO.retrieveActiveSourceHUIds(query);
	}
}
