/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ToString
public class CustomizedWindowInfoMap
{
	public static CustomizedWindowInfoMap ofList(final List<CustomizedWindowInfo> list)
	{
		return !list.isEmpty() ? new CustomizedWindowInfoMap(list) : EMPTY;
	}

	public static CustomizedWindowInfoMap empty()
	{
		return EMPTY;
	}

	private static final CustomizedWindowInfoMap EMPTY = new CustomizedWindowInfoMap();

	private final ImmutableMap<AdWindowId, CustomizedWindowInfo> effectiveCustomizedWindowInfos;

	private CustomizedWindowInfoMap()
	{
		this.effectiveCustomizedWindowInfos = ImmutableMap.of();
	}

	private CustomizedWindowInfoMap(@NonNull final List<CustomizedWindowInfo> list)
	{
		effectiveCustomizedWindowInfos = computeEffectiveCustomizedWindowInfos(list);
	}

	private static ImmutableMap<AdWindowId, CustomizedWindowInfo> computeEffectiveCustomizedWindowInfos(@NonNull final List<CustomizedWindowInfo> list)
	{
		final ImmutableMap<AdWindowId, CustomizedWindowInfo> customizedWindowsByCustomizedWindowId = Maps.uniqueIndex(list, CustomizedWindowInfo::getCustomizationWindowId);

		//
		// Create the groups
		final ArrayList<ArrayList<AdWindowId>> groups = new ArrayList<>();
		for (final CustomizedWindowInfo link : list)
		{
			boolean linkMerged = false;
			for (final ArrayList<AdWindowId> group : groups)
			{
				if (mergeLinkToGroup(link, group))
				{
					linkMerged = true;
					break;
				}
			}

			if (!linkMerged)
			{
				final ArrayList<AdWindowId> newGroup = new ArrayList<>();
				mergeLinkToGroup(link, newGroup);
				groups.add(newGroup);
			}
		}

		//
		// Merge groups
		boolean someMergesWerePerformed;
		do
		{
			someMergesWerePerformed = false;

			for (int i = 0; i < groups.size(); i++)
			{
				final ArrayList<AdWindowId> groupToMerge = groups.get(i);
				if (groupToMerge.isEmpty())
				{
					continue;
				}

				for (int j = i + 1; j < groups.size(); j++)
				{
					final ArrayList<AdWindowId> targetGroup = groups.get(j);
					if (targetGroup.isEmpty())
					{
						continue;
					}

					if (mergeGroups(targetGroup, groupToMerge))
					{
						someMergesWerePerformed = true;
						break;
					}
				}
			}
		}
		while (someMergesWerePerformed);

		//
		// Remove empty groups
		groups.removeIf(ArrayList::isEmpty);

		//
		// Create result
		final ImmutableMap.Builder<AdWindowId, CustomizedWindowInfo> result = ImmutableMap.builder();
		for (final ArrayList<AdWindowId> group : groups)
		{
			final int groupSize = group.size();
			final AdWindowId baseWindowId = group.get(0);
			final AdWindowId customizationWindowId = group.get(groupSize - 1);
			final ImmutableList<AdWindowId> previousCustomizationWindowIds = groupSize > 2
					? ImmutableList.copyOf(group.subList(1, groupSize - 1))
					: ImmutableList.of();

			final CustomizedWindowInfo initialCustomizedWindowInfo = customizedWindowsByCustomizedWindowId.get(customizationWindowId);
			final CustomizedWindowInfo customizedWindowInfo = initialCustomizedWindowInfo.toBuilder()
					.previousCustomizationWindowIds(previousCustomizationWindowIds)
					.baseWindowId(baseWindowId)
					.build();

			result.put(baseWindowId, customizedWindowInfo);
			result.put(customizationWindowId, customizedWindowInfo);
			previousCustomizationWindowIds.forEach(previousCustomizationWindowId -> result.put(previousCustomizationWindowId, customizedWindowInfo));
		}

		return result.build();
	}

	private static boolean mergeLinkToGroup(@NonNull final CustomizedWindowInfo link, @NonNull final ArrayList<AdWindowId> group)
	{
		Check.assume(link.getPreviousCustomizationWindowIds().isEmpty(), "previous customization windowIds shall be empty: {}", link);

		if (group.isEmpty())
		{
			group.add(link.getBaseWindowId());
			group.add(link.getCustomizationWindowId());
			return true;
		}
		else if (AdWindowId.equals(link.getCustomizationWindowId(), first(group)))
		{
			group.add(0, link.getBaseWindowId());
			return true;
		}
		else if (AdWindowId.equals(link.getBaseWindowId(), last(group)))
		{
			group.add(link.getCustomizationWindowId());
			return true;
		}
		else
		{
			return false;
		}
	}

	private static boolean mergeGroups(@NonNull final ArrayList<AdWindowId> targetGroup, @NonNull final ArrayList<AdWindowId> group)
	{
		if (AdWindowId.equals(last(targetGroup), first(group)))
		{
			targetGroup.addAll(group.subList(1, group.size()));
			group.clear();
			return true;
		}
		else if (AdWindowId.equals(last(group), first(targetGroup)))
		{
			targetGroup.addAll(0, group.subList(0, group.size() - 1));
			group.clear();
			return true;
		}
		else
		{
			return false;
		}
	}

	private static AdWindowId first(@NonNull final ArrayList<AdWindowId> group) { return group.get(0); }

	private static AdWindowId last(@NonNull final ArrayList<AdWindowId> group) { return group.get(group.size() - 1); }

	public Optional<CustomizedWindowInfo> getCustomizedWindowInfo(@NonNull final AdWindowId baseWindowId)
	{
		return Optional.ofNullable(effectiveCustomizedWindowInfos.get(baseWindowId));
	}

	public boolean isTopLevelCustomizedWindow(@NonNull final AdWindowId adWindowId)
	{
		final CustomizedWindowInfo customizedWindowInfo = effectiveCustomizedWindowInfos.get(adWindowId);
		return customizedWindowInfo == null || AdWindowId.equals(customizedWindowInfo.getCustomizationWindowId(), adWindowId);
	}
}
