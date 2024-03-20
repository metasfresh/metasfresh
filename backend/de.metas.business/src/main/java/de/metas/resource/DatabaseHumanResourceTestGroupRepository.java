package de.metas.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class DatabaseHumanResourceTestGroupRepository implements HumanResourceTestGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, HumanResourceTestGroupMap> cache = CCache.<Integer, HumanResourceTestGroupMap>builder()
			.tableName(I_S_HumanResourceTestGroup.Table_Name)
			.build();

	@Override
	@NonNull
	public ImmutableList<HumanResourceTestGroup> getAll()
	{
		return getMap().getAllActive();
	}

	@Override
	@NonNull
	public HumanResourceTestGroup getById(@NonNull final HumanResourceTestGroupId id)
	{
		return getMap().getById(id);
	}

	@Override
	@NonNull
	public ImmutableList<HumanResourceTestGroup> getByIds(@NonNull final Set<HumanResourceTestGroupId> ids)
	{
		return getMap().getByIds(ids);
	}

	@NonNull
	private HumanResourceTestGroupMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	@NonNull
	private HumanResourceTestGroupMap retrieveMap()
	{
		final ImmutableList<HumanResourceTestGroup> humanResourceTestGroups = queryBL.createQueryBuilder(I_S_HumanResourceTestGroup.class)
				.create()
				.stream()
				.map(DatabaseHumanResourceTestGroupRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new HumanResourceTestGroupMap(humanResourceTestGroups);
	}

	@NonNull
	public static HumanResourceTestGroup fromRecord(@NonNull final I_S_HumanResourceTestGroup record)
	{
		return HumanResourceTestGroup.builder()
				.id(HumanResourceTestGroupId.ofRepoId(record.getS_HumanResourceTestGroup_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.groupIdentifier(record.getGroupIdentifier())
				.name(record.getName())
				.department(record.getDepartment())
				.availability(extractResourceAvailability(record))
				.isActive(record.isActive())
				.build();
	}

	private static ResourceWeeklyAvailability extractResourceAvailability(final I_S_HumanResourceTestGroup record)
	{
		final boolean timeSlot = record.isTimeSlot();
		return ResourceWeeklyAvailability.builder()
				.availableDaysOfWeek(extractAvailableDaysOfWeek(record))
				.timeSlot(timeSlot)
				.timeSlotStart(timeSlot ? TimeUtil.asLocalTime(record.getTimeSlotStart()) : null)
				.timeSlotEnd(timeSlot ? TimeUtil.asLocalTime(record.getTimeSlotEnd()) : null)
				.build();
	}

	private static ImmutableSet<DayOfWeek> extractAvailableDaysOfWeek(@NonNull final I_S_HumanResourceTestGroup record)
	{
		if (record.isDateSlot())
		{
			final ImmutableSet.Builder<DayOfWeek> days = ImmutableSet.builder();
			if (record.isOnMonday())
			{
				days.add(DayOfWeek.MONDAY);
			}
			if (record.isOnTuesday())
			{
				days.add(DayOfWeek.TUESDAY);
			}
			if (record.isOnWednesday())
			{
				days.add(DayOfWeek.WEDNESDAY);
			}
			if (record.isOnThursday())
			{
				days.add(DayOfWeek.THURSDAY);
			}
			if (record.isOnFriday())
			{
				days.add(DayOfWeek.FRIDAY);
			}
			if (record.isOnSaturday())
			{
				days.add(DayOfWeek.SATURDAY);
			}
			if (record.isOnSunday())
			{
				days.add(DayOfWeek.SUNDAY);
			}

			return days.build();
		}
		else
		{
			return ImmutableSet.copyOf(DayOfWeek.values());
		}
	}

	//
	//
	//

	private final static class HumanResourceTestGroupMap
	{
		@NonNull
		private final ImmutableList<HumanResourceTestGroup> allActive;
		@NonNull
		private final ImmutableMap<HumanResourceTestGroupId, HumanResourceTestGroup> byId;

		private HumanResourceTestGroupMap(@NonNull final List<HumanResourceTestGroup> list)
		{
			this.allActive = list.stream().filter(HumanResourceTestGroup::isActive).collect(ImmutableList.toImmutableList());
			this.byId = Maps.uniqueIndex(list, HumanResourceTestGroup::getId);
		}

		@NonNull
		public ImmutableList<HumanResourceTestGroup> getAllActive()
		{
			return allActive;
		}

		@NonNull
		public HumanResourceTestGroup getById(@NonNull final HumanResourceTestGroupId id)
		{
			final HumanResourceTestGroup group = byId.get(id);
			if (group == null)
			{
				throw new AdempiereException("No group found for " + id);
			}
			return group;
		}

		@NonNull
		public ImmutableList<HumanResourceTestGroup> getByIds(final Set<HumanResourceTestGroupId> ids)
		{
			return ids.stream()
					.map(byId::get)
					.filter(Objects::nonNull)
					.collect(ImmutableList.toImmutableList());
		}
	}
}
