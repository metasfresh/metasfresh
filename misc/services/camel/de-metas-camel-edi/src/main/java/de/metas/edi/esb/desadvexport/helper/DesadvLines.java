/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.edi.esb.desadvexport.helper;

import com.google.common.collect.ImmutableList;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Value
@Builder
public class DesadvLines
{
	@NonNull
	Map<Integer, EDIExpDesadvLineType> lineId2LineWithNoPacks;

	@NonNull
	Map<Integer, DesadvLineWithPacks> lineId2LineWithPacks;

	public boolean isEmpty()
	{
		return lineId2LineWithNoPacks.isEmpty() && lineId2LineWithPacks.isEmpty();
	}

	@NonNull
	public List<EDIExpDesadvLineType> getAllLines()
	{
		return Stream.concat(lineId2LineWithNoPacks.values().stream(),
							 lineId2LineWithPacks.values().stream().map(DesadvLineWithPacks::getDesadvLine))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<EDIExpDesadvLineType> getAllSortedByLine()
	{
		return Stream.concat(lineId2LineWithNoPacks.values().stream(),
							 lineId2LineWithPacks.values().stream().map(DesadvLineWithPacks::getDesadvLine))
				.sorted(Comparator.comparing(EDIExpDesadvLineType::getLine))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<SinglePack> getPacksForLine(@NonNull final Integer desadvLineId)
	{
		return Optional.ofNullable(lineId2LineWithPacks.get(desadvLineId))
				.map(DesadvLineWithPacks::getSinglePacks)
				.orElseGet(ImmutableList::of);
	}
}
