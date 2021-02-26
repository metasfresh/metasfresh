/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.crm.interceptor;

import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Request;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.ui.web.window.model.DocumentCollection;

@Interceptor(I_R_Request.class)
@Component
public class R_Request
{
	@Autowired
	private DocumentCollection documentsCollection;

	/**
	 * If request has the ReminderDate set then update the BPartner's ReminderDateExtern or ReminderDateIntern (based on request's sales rep).
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/2066
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_R_Request.COLUMNNAME_ReminderDate)
	public void updateBPartnerReminderDate(final I_R_Request request)
	{
		final Timestamp reminderDate = request.getReminderDate();
		if (reminderDate == null)
		{
			return;
		}

		final int adUserId = request.getSalesRep_ID();
		if (adUserId < 0)
		{
			return;
		}

		final I_C_BPartner bpartner = request.getC_BPartner();
		if (bpartner == null)
		{
			// nothing to do
			return;
		}

		if (bpartner.getSalesRepIntern_ID() == adUserId)
		{
			bpartner.setReminderDateIntern(reminderDate);
		}
		else if (bpartner.getSalesRep_ID() == adUserId)
		{
			bpartner.setReminderDateExtern(reminderDate);
		}
		else
		{
			// nothing to do
			return;
		}

		InterfaceWrapperHelper.save(bpartner);
		documentsCollection.invalidateDocumentByRecordId(I_C_BPartner.Table_Name, bpartner.getC_BPartner_ID());
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = { I_R_Request.COLUMNNAME_StartTime, I_R_Request.COLUMNNAME_StartDate })
	public void updateStartDateAndStartTime(final I_R_Request request)
	{
		if (request.getStartDate() == null)
		{
			request.setStartDate(request.getStartTime());
		}

		if (request.getStartTime() == null)
		{
			request.setStartTime(request.getStartDate());
		}
	}
}
