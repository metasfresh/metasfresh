package de.metas.document.archive.notification.delay;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DocOutboundNotificationDelayServiceTest
{
	@BeforeEach void init() { AdempiereTestHelper.get().init(); }

	private I_C_Doc_Outbound_Log inOutLog()
	{
		final I_C_Doc_Outbound_Log log = InterfaceWrapperHelper.newInstance(I_C_Doc_Outbound_Log.class);
		log.setAD_Table_ID(InterfaceWrapperHelper.getTableId(I_M_InOut.class));
		log.setRecord_ID(1);
		InterfaceWrapperHelper.save(log);
		return log;
	}

	@Test void noHandlerRegistered_doesNotDelay()
	{
		final DocOutboundNotificationDelayService service = new DocOutboundNotificationDelayService(Optional.empty());
		assertThat(service.shouldDelaySending(inOutLog())).isFalse();
	}

	@Test void matchingHandler_delegates()
	{
		final DocOutboundNotificationDelayHandler fake = new DocOutboundNotificationDelayHandler()
		{
			@Override public String getTableName() { return I_M_InOut.Table_Name; }
			@Override public boolean shouldDelaySending(I_C_Doc_Outbound_Log l) { return true; }
		};
		final DocOutboundNotificationDelayService service = new DocOutboundNotificationDelayService(Optional.of(ImmutableList.of(fake)));
		assertThat(service.shouldDelaySending(inOutLog())).isTrue();
	}
}
