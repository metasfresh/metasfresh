package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.i18n.IMsgBL;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotificationUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import net.miginfocom.swing.MigLayout;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.SwingEventNotifierUI;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Frame used to display notifications in the bottom right corner.
 *
 * @author tsa
 */
class SwingEventNotifierFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	/**
	 * Default {@link NotificationItem#getSummary()} to be used when no {@link Event#getSummary()} was provided
	 */
	private static final String MSG_Notification_Summary_Default = "EVENT_Notification_Summary_Default";

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final Set<Topic> topicsSubscribed = new HashSet<>();

	/**
	 * Currently displayed notifications
	 */
	private final LinkedHashMap<NotificationItem, NotificationItemPanel> notification2panel = new LinkedHashMap<>();
	private final int maxNotifications;
	private final int autoFadeAwayTimeMillis;
	/**
	 * Defines the gap (in pixels) between last notification and screen bottom (see FRESH-441)
	 */
	private final int frameBottomGap;

	private boolean disposed = false;

	/**
	 * {@link NotificationItem} callback. Called when user do some action on a displayed notification (e.g. close).
	 */
	private final INotificationItemPanelCallback notificationItemPanelCallback = item -> executeInEDTAfter(0, () -> fadeAwayAndDisposeNotificationItemNow(item));

	/**
	 * Listen on {@link IEventBus} and fetched notifications to be displayed
	 */
	private IEventListener eventListener = (eventBus, event) -> SwingEventNotifierFrame.this.onEvent(event);

	/**
	 * UI scheduled commands executor. Don't use it directly, but use {@link #executeInEDTAfter(long, Runnable)}.
	 */
	private final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(
			1 // core pool size = 1; NOTE: per ScheduledThreadPoolExecutor javadoc, the core pool size is also used for maximumPoolSize
			, CustomizableThreadFactory.builder()
					.setThreadNamePrefix(getClass().getName() + "-ScheduledExecutor")
					.setDaemon(true)
					.build());

	SwingEventNotifierFrame()
	{
		//
		// Max displayed notifications
		maxNotifications = AdempierePLAF.getInt(SwingEventNotifierUI.NOTIFICATIONS_MaxDisplayed, SwingEventNotifierUI.NOTIFICATIONS_MaxDisplayed_Default);
		autoFadeAwayTimeMillis = AdempierePLAF.getInt(SwingEventNotifierUI.NOTIFICATIONS_AutoFadeAwayTimeMillis, SwingEventNotifierUI.NOTIFICATIONS_AutoFadeAwayTimeMillis_Default);
		frameBottomGap = AdempierePLAF.getInt(SwingEventNotifierUI.NOTIFICATIONS_BottomGap, SwingEventNotifierUI.NOTIFICATIONS_BottomGap_Default);

		//
		// Setup UI
		{
			final JPanel contentPanel = new JPanel();
			contentPanel.setBorder(BorderFactory.createEmptyBorder());

			setContentPane(contentPanel);
			setUndecorated(true);
			setAlwaysOnTop(true);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			setType(JFrame.Type.UTILITY); // i.e. hide it from taskbar
			setAutoRequestFocus(false); // don't request focus when showing because that would annoy the user which is working in other window.

			// Make the background transparent, if possible
			// NOTE: we need to check if is supported, to avoid an UnsupportedOperationException
			if (getGraphicsConfiguration().isTranslucencyCapable())
			{
				setBackground(SwingEventNotifierUI.COLOR_Transparent);
				contentPanel.setBackground(SwingEventNotifierUI.COLOR_Transparent);
			}
			else
			{
				setBackground(Color.WHITE);
				contentPanel.setBackground(Color.WHITE);
			}

			setLayout(new MigLayout("fill, gapx 0!, gapy 1!, wrap 1")); // layout the items top-down
		}

		//
		// Subscribe to user notifications
		Services.get(IEventBusFactory.class).registerUserNotificationsListener(eventListener);

		//
		// Schedule and UI update of this frame
		executeInEDTAfter(0, this::updateUI);
	}

	public Set<String> getSubscribedTopicNames()
	{
		final Set<String> result = new HashSet<>();
		for (final Topic topic : topicsSubscribed)
		{
			result.add(topic.getFullName());
		}
		return result;
	}

	@Override
	public synchronized void dispose()
	{
		disposed = true;

		Services.get(IEventBusFactory.class).unregisterUserNotificationsListener(eventListener);
		eventListener = null;

		notification2panel.clear();

		scheduledExecutor.shutdownNow();

		super.dispose();
	}

	public synchronized boolean isDisposed()
	{
		return disposed;
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private void assertEventDispatchThread()
	{
		Check.assume(SwingUtilities.isEventDispatchThread(), "current thread is the EventDispatchThread");
	}

	/**
	 * Execute given command, asynchronously, in swing EventDispatchThread.
	 *
	 * @param delayMillis after how many milliseconds to execute it; If less or equals with zero, it will be scheduled to be executed right now.
	 */
	private void executeInEDTAfter(final long delayMillis, final Runnable command)
	{
		if (isDisposed())
		{
			return;
		}
		final long delayMillisOrZero = delayMillis < 0 ? 0 : delayMillis;
		scheduledExecutor.schedule(() -> {
			if (isDisposed())
			{
				return;
			}
			SwingUtilities.invokeLater(command);
		}, delayMillisOrZero, TimeUnit.MILLISECONDS);
	}

	/**
	 * Create and add
	 */
	private synchronized void addNotificationItemNow(final NotificationItem item)
	{
		assertEventDispatchThread();

		// do nothing if disposed
		if (disposed)
		{
			return;
		}

		// do nothing if notification was already added
		if (notification2panel.containsKey(item))
		{
			return;
		}

		//
		// If we have more notifications than allowed, remove the old ones to make space for the one which is coming now.
		while (notification2panel.size() > maxNotifications)
		{
			// Get the oldest item
			final Entry<NotificationItem, NotificationItemPanel> entryToDelete = notification2panel.entrySet().iterator().next();
			final NotificationItem itemToDelete = entryToDelete.getKey();
			final NotificationItemPanel panelToDelete = entryToDelete.getValue();

			panelToDelete.fadeAwayAndDispose();
			notification2panel.remove(itemToDelete);
			this.remove(panelToDelete);
		}

		//
		// Create and add the notification item component
		{
			final NotificationItemPanel notificationPanel = new NotificationItemPanel(item, notificationItemPanelCallback);
			notification2panel.put(item, notificationPanel);
			getContentPane().add(notificationPanel, "growx 100, wrap");

			// Schedule this notifications to automatically fade away.
			if (autoFadeAwayTimeMillis > 0)
			{
				executeInEDTAfter(autoFadeAwayTimeMillis, () -> fadeAwayAndDisposeNotificationItemNow(item));
			}
		}

		updateUI();
	}

	private synchronized void fadeAwayAndDisposeNotificationItemNow(final NotificationItem item)
	{
		assertEventDispatchThread();

		// do nothing if disposed
		if (disposed)
		{
			return;
		}

		final NotificationItemPanel panelToDelete = notification2panel.remove(item);
		if (panelToDelete == null)
		{
			return;
		}

		panelToDelete.fadeAwayAndDispose();
		this.remove(panelToDelete); // remove it from container component

		updateUI();
	}

	private void updateUI()
	{
		assertEventDispatchThread();

		final boolean visibleNew = !notification2panel.isEmpty();

		if (visibleNew)
		{
			revalidate();
			pack();

			final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// size of the screen
			final Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());// height of the task bar
			this.setLocation(
					screenSize.width - getWidth(),
					screenSize.height - screenInsets.bottom - getHeight() - (Math.max(frameBottomGap, 0)));
		}

		setVisible(visibleNew);

		if (visibleNew)
		{
			repaint();
		}
	}

	/**
	 * Called when a new event is received from bus
	 */
	private void onEvent(final Event event)
	{
		if (!isEligibleToBeDisplayed(event))
		{
			return;
		}

		final NotificationItem notificationItem = toNotificationItem(event);

		// Add the notification item component (in EDT)
		executeInEDTAfter(0, () -> addNotificationItemNow(notificationItem));
	}

	private boolean isEligibleToBeDisplayed(final Event event)
	{
		final int loginUserId = Env.getAD_User_ID(getCtx());
		return event.hasRecipient(loginUserId);
	}

	private NotificationItem toNotificationItem(final Event event)
	{
		//
		// Build summary text
		final String summaryTrl = msgBL.getMsg(getCtx(), MSG_Notification_Summary_Default, new Object[] { Adempiere.getName() });

		final UserNotification notification = UserNotificationUtils.toUserNotification(event);
		//
		// Build detail message
		final StringBuilder detailBuf = new StringBuilder();
		{
			// Add plain detail if any
			final String detailPlain = notification.getDetailPlain();
			if (!Check.isEmpty(detailPlain, true))
			{
				detailBuf.append(detailPlain.trim());
			}

			// Translate, parse and add detail (AD_Message).
			final String detailADMessage = notification.getDetailADMessage();
			if (!Check.isEmpty(detailADMessage, true))
			{
				final String detailTrl = msgBL.getMsg(getCtx(), detailADMessage);
				final String detailTrlParsed = EventHtmlMessageFormat.newInstance()
						.setArguments(notification.getDetailADMessageParams())
						.format(detailTrl);
				if (!Check.isEmpty(detailTrlParsed, true))
				{
					if (detailBuf.length() > 0)
					{
						detailBuf.append("<br>");
					}
					detailBuf.append(detailTrlParsed);
				}
			}
		}

		return NotificationItem.builder()
				.setSummary(summaryTrl)
				.setDetail(detailBuf.toString())
				.build();
	}
}
