package de.metas.resource.callout;

import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_S_HumanResourceTestGroup;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;

@Callout(I_S_HumanResourceTestGroup.class)
@Component
public class S_HumanResourceTestGroup
{
	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_S_HumanResourceTestGroup.COLUMNNAME_IsTimeSlot)
	public void onIsTimeSlot(final I_S_HumanResourceTestGroup group)
	{
		if (group.isTimeSlot())
		{
			if (group.getTimeSlotStart() == null)
			{
				group.setTimeSlotStart(TimeUtil.asTimestamp(LocalTime.MIN));
			}
			if (group.getTimeSlotEnd() == null)
			{
				group.setTimeSlotEnd(TimeUtil.asTimestamp(LocalTime.MAX));
			}
		}
	}
}
