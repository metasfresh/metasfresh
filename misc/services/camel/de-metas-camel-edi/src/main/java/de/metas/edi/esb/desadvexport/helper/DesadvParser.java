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

import com.google.common.collect.ImmutableMap;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineWithNoPackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvPackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@UtilityClass
public class DesadvParser
{
	@NonNull
	public static DesadvLines getDesadvLinesEnforcingSinglePacks(@NonNull final EDIExpDesadvType desadvType)
	{
		return DesadvLines.builder()
				.lineId2LineWithPacks(getLinesWithPacks(desadvType.getEDIExpDesadvPack()))
				.lineId2LineWithNoPacks(getLinesWithNoPacks(desadvType.getEDIExpDesadvLineWithNoPack()))
				.build();
	}

	@NonNull
	private static Map<Integer, DesadvLineWithPacks> getLinesWithPacks(@NonNull final List<EDIExpDesadvPackType> packs)
	{
		final Map<Integer, List<SinglePack>> lineIdToPacksCollector = new HashMap<>();
		final Map<Integer, EDIExpDesadvLineType> lineIdToLine = new HashMap<>();

		packs.forEach(pack -> {
			final SinglePack singlePack = SinglePack.of(pack);

			final List<SinglePack> packsForLine = new ArrayList<>();
			packsForLine.add(singlePack);
			lineIdToPacksCollector.merge(singlePack.getDesadvLineID(), packsForLine, (old, newList) -> {
				old.addAll(newList);
				return old;
			});

			lineIdToLine.put(singlePack.getDesadvLineID(), singlePack.getDesadvLine());
		});

		final ImmutableMap.Builder<Integer, DesadvLineWithPacks> lineWithPacksCollector = ImmutableMap.builder();

		lineIdToLine.forEach((desadvLineID, desadvLine) -> {
			final DesadvLineWithPacks lineWithPacks = DesadvLineWithPacks.builder()
					.desadvLine(desadvLine)
					.singlePacks(lineIdToPacksCollector.get(desadvLineID))
					.build();

			lineWithPacksCollector.put(desadvLineID, lineWithPacks);
		});

		return lineWithPacksCollector.build();
	}

	@NonNull
	private static Map<Integer, EDIExpDesadvLineType> getLinesWithNoPacks(@NonNull final List<EDIExpDesadvLineWithNoPackType> lineWithNoPacks)
	{
		return lineWithNoPacks.stream()
				.map(EDIExpDesadvLineWithNoPackType::getEDIDesadvLineID)
				.collect(ImmutableMap.toImmutableMap(desadvLine -> desadvLine.getEDIDesadvLineID().intValue(), Function.identity()));
	}
}
