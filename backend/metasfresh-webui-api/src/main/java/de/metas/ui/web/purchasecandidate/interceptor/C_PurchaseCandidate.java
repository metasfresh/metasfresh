package de.metas.ui.web.purchasecandidate.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.purchasecandidate.PurchaseCandidateReminder;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.reminder.PurchaseCandidateReminderScheduler;
import lombok.NonNull;

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

@Interceptor(I_C_PurchaseCandidate.class)
@Component
public class C_PurchaseCandidate
{
	private final PurchaseCandidateReminderScheduler scheduler;

	public C_PurchaseCandidate(@NonNull final PurchaseCandidateReminderScheduler scheduler)
	{
		this.scheduler = scheduler;
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, //
			ifColumnsChanged = { I_C_PurchaseCandidate.COLUMNNAME_ReminderDate, I_C_PurchaseCandidate.COLUMNNAME_Vendor_ID }, //
			afterCommit = true)
	public void scheduleReminderForWebui(final I_C_PurchaseCandidate record)
	{
		final PurchaseCandidateReminder reminder = PurchaseCandidateRepository.toPurchaseCandidateReminderOrNull(record);
		if (reminder == null)
		{
			return;
		}
		scheduler.scheduleNotification(reminder);
	}
}
