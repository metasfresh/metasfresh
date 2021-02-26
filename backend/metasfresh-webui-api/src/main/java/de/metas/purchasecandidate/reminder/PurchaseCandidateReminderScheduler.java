package de.metas.purchasecandidate.reminder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ScheduledFuture;

import de.metas.i18n.AdMessageKey;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetViewAction;
import de.metas.purchasecandidate.PurchaseCandidateReminder;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Class responsible with notifying all users which have {@link #NOTIFICATION_GROUP_NAME} in one of their roles about which purchase candidates are due.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public class PurchaseCandidateReminderScheduler implements InitializingBean
{
	// services
	private static final Logger logger = LogManager.getLogger(PurchaseCandidateReminderScheduler.class);
	private final TaskScheduler taskScheduler;
	private final IViewsRepository viewsRepo;
	private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final IBPartnerBL bpartnersService;

	private static final NotificationGroupName NOTIFICATION_GROUP_NAME = NotificationGroupName.of("de.metas.purchasecandidate.UserNotifications.Due");
	private static final AdMessageKey MSG_PurchaseCandidatesDue = AdMessageKey.of("de.metas.purchasecandidates.PurchaseCandidatesDueNotification");

	private final RemindersQueue reminders = new RemindersQueue();
	private NextDispatch nextDispatch;

	public PurchaseCandidateReminderScheduler(
			@Qualifier(WebRestApiApplication.BEANNAME_WebuiTaskScheduler) @NonNull final TaskScheduler taskScheduler,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepo,
			@NonNull final IBPartnerBL bpartnersService)
	{
		this.taskScheduler = taskScheduler;
		this.viewsRepo = viewsRepo;
		this.purchaseCandidateRepo = purchaseCandidateRepo;
		this.bpartnersService = bpartnersService;
	}

	@Override
	public void afterPropertiesSet()
	{
		initialize();
	}

	public synchronized void initialize()
	{
		final Set<PurchaseCandidateReminder> reminders = purchaseCandidateRepo.retrieveReminders();
		this.reminders.setReminders(reminders);

		if (nextDispatch != null)
		{
			nextDispatch.cancel();
			nextDispatch = null;
		}

		scheduleNextDispatch();
	}

	public synchronized List<PurchaseCandidateReminder> getReminders()
	{
		return reminders.toList();
	}

	public synchronized ZonedDateTime getNextDispatchTime()
	{
		return nextDispatch != null ? nextDispatch.getNotificationTime() : null;
	}

	public void scheduleNotification(final BPartnerId vendorBPartnerId, final ZonedDateTime notificationTime)
	{
		scheduleNotification(PurchaseCandidateReminder.builder()
				.vendorBPartnerId(vendorBPartnerId)
				.notificationTime(notificationTime)
				.build());
	}

	public synchronized void scheduleNotification(@NonNull final PurchaseCandidateReminder reminder)
	{
		if (!reminders.add(reminder))
		{
			return;
		}

		scheduleNextDispatch();
	}

	private synchronized void scheduleNextDispatch()
	{
		final ZonedDateTime minNotificationTime = reminders.getMinNotificationTime();
		if (minNotificationTime == null)
		{
			return;
		}

		if (nextDispatch == null)
		{
			nextDispatch = NextDispatch.schedule(this::dispatchNotificationsAndReschedule, minNotificationTime, taskScheduler);
		}
		else
		{
			nextDispatch = nextDispatch.rescheduleIfAfter(minNotificationTime, taskScheduler);
		}
	}

	private void dispatchNotificationsAndReschedule()
	{
		try
		{
			final List<PurchaseCandidateReminder> remindersToDispatch = removeAllRemindersUntil(ZonedDateTime.now());
			remindersToDispatch.forEach(this::dispatchNotificationNoFail);
		}
		finally
		{
			scheduleNextDispatch();
		}
	}

	private synchronized List<PurchaseCandidateReminder> removeAllRemindersUntil(final ZonedDateTime maxNotificationTime)
	{
		return reminders.removeAllUntil(maxNotificationTime);
	}

	private void dispatchNotificationNoFail(final PurchaseCandidateReminder reminder)
	{
		try
		{
			dispatchNotification(reminder);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed dispatching notifications for {}. Skipped", reminder, ex);
		}
	}

	private void dispatchNotification(final PurchaseCandidateReminder reminder)
	{
		final IView purchaseCandidatesView = createPurchaseCandidatesView(reminder);
		if (purchaseCandidatesView == null)
		{
			logger.trace("No records found for {}. Nothing to notify.", reminder);
			return;
		}

		final ViewId viewId = purchaseCandidatesView.getViewId();
		final long count = purchaseCandidatesView.size();

		final UserNotificationRequest notification = UserNotificationRequest.builder()
				.notificationGroupName(NOTIFICATION_GROUP_NAME)
				.recipient(Recipient.allRolesContainingGroup(NOTIFICATION_GROUP_NAME))
				.contentADMessage(MSG_PurchaseCandidatesDue)
				.contentADMessageParam(count)
				.contentADMessageParam(TableRecordReference.of(I_C_BPartner.Table_Name, reminder.getVendorBPartnerId().getRepoId()))
				.targetAction(TargetViewAction.builder()
						.adWindowId(viewId.getWindowId().toAdWindowIdOrNull())
						.viewId(viewId.toJson())
						.build())
				.build();
		Services.get(INotificationBL.class).send(notification);
	}

	private final IView createPurchaseCandidatesView(final PurchaseCandidateReminder reminder)
	{
		final IView view = viewsRepo.createView(CreateViewRequest.builder(getWindowId(I_C_PurchaseCandidate.Table_Name))
				.addStickyFilters(createViewStickyFilter(reminder))
				.applySecurityRestrictions(false) // don't apply security restrictions because at this point the AD_Client_ID and AD_Org_ID are ZERO
				.build());
		if (view.size() <= 0)
		{
			viewsRepo.closeView(view.getViewId(), ViewCloseAction.CANCEL);
			return null;
		}
		else
		{
			return view;
		}
	}

	private final DocumentFilter createViewStickyFilter(final PurchaseCandidateReminder reminder)
	{
		final BPartnerId vendorBPartnerId = reminder.getVendorBPartnerId();
		final String vendorName = bpartnersService.getBPartnerValueAndName(vendorBPartnerId);
		final ZonedDateTime notificationTime = reminder.getNotificationTime();

		final ITranslatableString caption = TranslatableStrings.join(" / ",
				vendorName,
				TranslatableStrings.dateAndTime(notificationTime));

		return DocumentFilter.builder()
				.setFilterId("filterByVendorIdAndReminderDate")
				.setCaption(caption)
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_C_PurchaseCandidate.COLUMNNAME_Vendor_ID, Operator.EQUAL, vendorBPartnerId.getRepoId()))
				.addParameter(DocumentFilterParam.ofNameOperatorValue(I_C_PurchaseCandidate.COLUMNNAME_ReminderDate, Operator.LESS_OR_EQUAL, notificationTime))
				.build();
	}

	private WindowId getWindowId(final String tableName)
	{
		final AdWindowId adWindowId = RecordZoomWindowFinder.findAdWindowId(tableName).get();
		return WindowId.of(adWindowId);
	}

	private static class RemindersQueue
	{
		private final SortedSet<PurchaseCandidateReminder> reminders = new TreeSet<>(Comparator.comparing(PurchaseCandidateReminder::getNotificationTime));

		public List<PurchaseCandidateReminder> toList()
		{
			return ImmutableList.copyOf(reminders);
		}

		public boolean add(@NonNull final PurchaseCandidateReminder reminder)
		{
			return reminders.add(reminder);
		}

		public void setReminders(final Collection<PurchaseCandidateReminder> reminders)
		{
			this.reminders.clear();
			this.reminders.addAll(reminders);
		}

		public List<PurchaseCandidateReminder> removeAllUntil(final ZonedDateTime maxNotificationTime)
		{
			final List<PurchaseCandidateReminder> result = new ArrayList<>();
			for (final Iterator<PurchaseCandidateReminder> it = reminders.iterator(); it.hasNext();)
			{
				final PurchaseCandidateReminder reminder = it.next();
				final ZonedDateTime notificationTime = reminder.getNotificationTime();
				if (notificationTime.compareTo(maxNotificationTime) <= 0)
				{
					it.remove();
					result.add(reminder);
				}
			}

			return result;
		}

		public ZonedDateTime getMinNotificationTime()
		{
			if (reminders.isEmpty())
			{
				return null;
			}

			return reminders.first().getNotificationTime();
		}
	}

	@lombok.Value
	@lombok.Builder
	private static class NextDispatch
	{
		public static NextDispatch schedule(final Runnable task, final ZonedDateTime date, final TaskScheduler taskScheduler)
		{
			final ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, TimeUtil.asDate(date));
			return builder()
					.task(task)
					.scheduledFuture(scheduledFuture)
					.notificationTime(date)
					.build();
		}

		Runnable task;
		ScheduledFuture<?> scheduledFuture;
		ZonedDateTime notificationTime;

		public void cancel()
		{
			final boolean canceled = scheduledFuture.cancel(false);
			logger.trace("Cancel requested for {} (result was: {})", this, canceled);
		}

		public NextDispatch rescheduleIfAfter(final ZonedDateTime date, final TaskScheduler taskScheduler)
		{
			if (!notificationTime.isAfter(date) && !scheduledFuture.isDone())
			{
				logger.trace("Skip rescheduling {} because it's not after {}", date);
				return this;
			}

			cancel();

			final ScheduledFuture<?> nextScheduledFuture = taskScheduler.schedule(task, TimeUtil.asTimestamp(date));
			NextDispatch nextDispatch = NextDispatch.builder()
					.task(task)
					.scheduledFuture(nextScheduledFuture)
					.notificationTime(date)
					.build();

			logger.trace("Rescheduled {} to {}", this, nextDispatch);
			return nextDispatch;
		}
	}
}
