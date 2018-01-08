package org.adempiere.ui.notifications.manualtest;

import javax.swing.UIManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.SwingEventNotifierUI;
import org.adempiere.ui.notifications.SwingEventNotifierService;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.junit.Ignore;

import de.metas.event.Event;
import de.metas.event.EventBusConstants;
import de.metas.event.IEventBusFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.logging.LogManager;

@Ignore
public class SwingEventNotifier_Client
{
	public static void main(String[] args) throws Exception
	{
		Adempiere.enableUnitTestMode();

		LogManager.initialize(true);
		UIManager.getDefaults().putDefaults(SwingEventNotifierUI.getUIDefaults());
		//EventBusConstants.getLogger().setLevel(Level.FINEST);

		final SwingEventNotifierService notificationsService = SwingEventNotifierService.getInstance();
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(SwingEventNotifier_EventProducer.EVENTBUS_TOPIC);
		notificationsService.start();

		//
		// Send some local notifications, just to see how the UI looks
		Services.get(IEventBusFactory.class)
				.getEventBus(EventBusConstants.TOPIC_GeneralNotifications)
				.postEvent(Event.builder()
						.setSummary("Hello !")
						.setDetailPlain("hello darkness my old friend"
								+ "\n i've come to talk with you again"
								+ "\n because!")
						.build());
		for (int i = 1; i <= 20; i++)
		{
			Services.get(IEventBusFactory.class)
					.getEventBus(EventBusConstants.TOPIC_GeneralNotifications)
					.postEvent(Event.builder()
							.setSummary("Message " + i)
							.setDetailADMessage("Lieferung {} für Partner {}&{} wurde erstellt.",
									TableRecordReference.of(createM_InOut("doc000" + i)),
									"G0007",
									"AgroShit"
							)
							.build());
			if (i >= 9)
			{
				Thread.sleep(1000);
			}
		}
	}

	private static final I_M_InOut createM_InOut(final String documentNo)
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(Env.getCtx(), I_M_InOut.class, ITrx.TRXNAME_None);
		inout.setDocumentNo(documentNo);
		InterfaceWrapperHelper.save(inout);
		return inout;
	}

}
