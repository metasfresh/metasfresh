package de.metas.handlingunits.mobileui.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.organization.OrgId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collector;

class HUManagerProfilesMap
{
	public static final HUManagerProfilesMap EMPTY = new HUManagerProfilesMap(ImmutableList.of());

	private final ImmutableMap<OrgId, HUManagerProfile> byOrgId;

	private HUManagerProfilesMap(final List<HUManagerProfile> list)
	{
		this.byOrgId = Maps.uniqueIndex(list, HUManagerProfile::getOrgId);
	}

	public static Collector<HUManagerProfile, ?, HUManagerProfilesMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(HUManagerProfilesMap::ofList);
	}

	private static HUManagerProfilesMap ofList(List<HUManagerProfile> list)
	{
		return list.isEmpty() ? EMPTY : new HUManagerProfilesMap(list);
	}

	@NonNull
	public HUManagerProfile getByOrgId(@NonNull final OrgId orgId)
	{
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> byOrgId.get(orgId),
				() -> byOrgId.get(OrgId.ANY),
				() -> HUManagerProfile.DEFAULT
		);
	}
}
