package de.metas.resource;

import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseHumanResourceTestGroupRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void getById()
	{
		@NonNull final I_S_HumanResourceTestGroup record = InterfaceWrapperHelper.newInstance(I_S_HumanResourceTestGroup.class);
		record.setS_HumanResourceTestGroup_ID(123);
		record.setAD_Org_ID(4);
		record.setGroupIdentifier("groupIdentifier111");
		record.setName("name222");
		record.setDepartment("department333");
		//record.setIsActive(true); // not needed
		record.setIsDateSlot(true);
		record.setOnMonday(true);
		record.setOnWednesday(true);
		record.setOnFriday(true);
		record.setIsTimeSlot(true);
		record.setTimeSlotStart(TimeUtil.asTimestamp(LocalTime.parse("09:00")));
		record.setTimeSlotEnd(TimeUtil.asTimestamp(LocalTime.parse("17:00")));
		InterfaceWrapperHelper.saveRecord(record);

		final HumanResourceTestGroup humanResourceTestGroup = DatabaseHumanResourceTestGroupRepository.fromRecord(record);
		assertThat(humanResourceTestGroup)
				.usingRecursiveComparison()
				.isEqualTo(HumanResourceTestGroup.builder()
						.id(HumanResourceTestGroupId.ofRepoId(123))
						.orgId(OrgId.ofRepoId(4))
						.groupIdentifier("groupIdentifier111")
						.name("name222")
						.department("department333")
						.isActive(true)
						.availability(ResourceWeeklyAvailability.builder()
								.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))
								.timeSlot(true)
								.timeSlotStart(LocalTime.parse("09:00"))
								.timeSlotEnd(LocalTime.parse("17:00"))
								.build())
						.build());
	}
}