package de.metas.handlingunits.sourcehu;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceRepository;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.util.Check;
import de.metas.util.Services;
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
	public Collection<I_M_HU> retrieveActualSourceHUs(@NonNull final Set<HuId> huIds)
	{
		final Set<HuId> vhuSourceIds = retrieveVhuSourceIds(huIds);
		return retrieveTopLevelSourceHus(vhuSourceIds);
	}

	private Set<HuId> retrieveVhuSourceIds(@NonNull final Set<HuId> huIds)
	{
		Check.assumeNotEmpty(huIds, "huIds is not empty");

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.type(HUTraceType.TRANSFORM_LOAD)
				.topLevelHuIds(huIds)
				.build();

		return huTraceRepository.query(query)
				.stream()
				.map(HUTraceEvent::getVhuSourceId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Don't use Services.get(IHandlingUnitsBL.class).getTopLevelHUs() because the sourceHUs might already be destroyed.f
	 *
	 * @param vhuSourceIds
	 * @return
	 */
	private List<I_M_HU> retrieveTopLevelSourceHus(@NonNull final Set<HuId> vhuSourceIds)
	{
		if (vhuSourceIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final HUTraceEventQuery query = HUTraceEventQuery.builder()
				.type(HUTraceType.TRANSFORM_LOAD)
				.vhuIds(vhuSourceIds)
				.build();

		final Set<HuId> topLevelSourceHuIds = huTraceRepository.query(query)
				.stream()
				.map(HUTraceEvent::getTopLevelHuId)
				.collect(ImmutableSet.toImmutableSet());
		if (topLevelSourceHuIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		return handlingUnitsDAO.getByIds(topLevelSourceHuIds);
	}

	public Collection<HuId> retrieveMatchingSourceHUIds(final HuId huId)
	{
		final MatchingSourceHusQuery query = MatchingSourceHusQuery.fromHuId(huId);

		final ISourceHuDAO sourceHuDAO = Services.get(ISourceHuDAO.class);
		return sourceHuDAO.retrieveActiveSourceHUIds(query);
	}
}
