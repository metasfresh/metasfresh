package de.metas.resource;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_S_ResourceType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceTypeRepositoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void getById()
	{
		@NonNull final I_S_ResourceType record = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class);
		record.setS_ResourceType_ID(123);
		//record.setIsActive(true); // not needed
		record.setName("resource type");
		record.setM_Product_Category_ID(444);
		record.setC_UOM_ID(555);
		record.setIsDateSlot(true);
		record.setOnMonday(true);
		record.setOnWednesday(true);
		record.setOnFriday(true);
		record.setIsTimeSlot(true);
		record.setTimeSlotStart(TimeUtil.asTimestamp(LocalTime.parse("09:00")));
		record.setTimeSlotEnd(TimeUtil.asTimestamp(LocalTime.parse("17:00")));
		InterfaceWrapperHelper.saveRecord(record);

		final ResourceType resourceType = ResourceTypeRepository.fromRecord(record);
		assertThat(resourceType)
				.usingRecursiveComparison()
				.ignoringFieldsMatchingRegexes("caption")
				.isEqualTo(ResourceType.builder()
						.id(ResourceTypeId.ofRepoId(123))
						.active(true)
						.caption(TranslatableStrings.empty()) // will be ignored and checked below
						.productCategoryId(ProductCategoryId.ofRepoId(444))
						.durationUomId(UomId.ofRepoId(555))
						.availability(ResourceWeeklyAvailability.builder()
								.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))
								.timeSlot(true)
								.timeSlotStart(LocalTime.parse("09:00"))
								.timeSlotEnd(LocalTime.parse("17:00"))
								.build())
						.build());
		assertThat(resourceType.getCaption().getDefaultValue()).isEqualTo("resource type");
	}

}