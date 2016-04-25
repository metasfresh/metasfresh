package org.adempiere.ui.notifications.manualtest;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.junit.Ignore;

import de.metas.event.Event;
import de.metas.event.EventBusConstants;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.logging.LogManager;

@Ignore
public class SwingEventNotifier_EventProducer
{
	public static final Topic EVENTBUS_TOPIC = EventBusConstants.TOPIC_GeneralNotifications;
	
	public static void main(String[] args)
	{
		Adempiere.enableUnitTestMode();
		Services.get(ISysConfigBL.class).setValue(EventBusConstants.SYSCONFIG_Enabled, true, 0); // we need to manually enable it

		LogManager.initialize(true);
		//EventBusConstants.getLogger().setLevel(Level.FINEST);

		new Thread()
		{
			private int cnt = 0;

			@Override
			public void run()
			{
				while (true)
				{
					// final NotificationItem notification = NotificationItem.builder()
					final Event event = Event.builder()
							.setSummary("Notification " + (cnt++))
							.setDetailPlain("This is some additional text about that notification."
									+ "\nAnd another line...")
							.build();

					// notificationsFrame.addNotificationItem(notification);
					// System.out.println("Added " + notification);

					Services.get(IEventBusFactory.class)
							.getEventBus(EVENTBUS_TOPIC)
							.postEvent(event);
					// System.out.println("Posted event: " + event);

					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			};
		}.start();
	}
}
