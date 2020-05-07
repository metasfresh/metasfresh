package de.metas.purchasecandidate.reminder;

import java.time.ZonedDateTime;
import java.util.List;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.purchasecandidate.PurchaseCandidateReminder;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;

/*
 * #%L
 * metasfresh-webui-api
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
 * {@link PurchaseCandidateReminderScheduler} REST controller.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@RestController
@RequestMapping(PurchaseCandidateReminderSchedulerRestController.ENDPOINT)
public class PurchaseCandidateReminderSchedulerRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/purchaseCandidates/reminders";

	@Autowired
	private PurchaseCandidateReminderScheduler reminderScheduler;
	@Autowired
	private UserSession userSession;

	private void assertAuth()
	{
		userSession.assertLoggedIn();

		final AdWindowId purchaseCandidatesWindowId = RecordZoomWindowFinder.findAdWindowId(I_C_PurchaseCandidate.Table_Name).get();
		if (!userSession.getUserRolePermissions().checkWindowPermission(purchaseCandidatesWindowId).hasWriteAccess())
		{
			throw new AdempiereException("No read/write access to purchase candidates window");
		}
	}

	@GetMapping
	public List<PurchaseCandidateReminder> getReminders()
	{
		assertAuth();

		return reminderScheduler.getReminders();
	}

	@PostMapping
	public synchronized void addReminder(@RequestBody final PurchaseCandidateReminder reminder)
	{
		assertAuth();

		reminderScheduler.scheduleNotification(reminder);
	}

	@GetMapping("/nextDispatchTime")
	public ZonedDateTime getNextDispatchTime()
	{
		assertAuth();

		return reminderScheduler.getNextDispatchTime();
	}

	@PostMapping("/reinitialize")
	public List<PurchaseCandidateReminder> reinitialize()
	{
		assertAuth();

		reminderScheduler.initialize();
		return reminderScheduler.getReminders();
	}
}
