package de.metas.project.workorder.resource;

import de.metas.calendar.CalendarResourceId;
import de.metas.product.ResourceId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceIdAndTypeTest
{
	@Test
	void toCalendarResourceId_ofCalendarResourceId()
	{
		final ResourceIdAndType resourceIdAndType = ResourceIdAndType.human(ResourceId.ofRepoId(1234));
		final CalendarResourceId calendarResourceId = resourceIdAndType.toCalendarResourceId();
		final ResourceIdAndType resourceIdAndType2 = ResourceIdAndType.ofCalendarResourceId(calendarResourceId);
		Assertions.assertThat(resourceIdAndType2).isEqualTo(resourceIdAndType);
	}
}