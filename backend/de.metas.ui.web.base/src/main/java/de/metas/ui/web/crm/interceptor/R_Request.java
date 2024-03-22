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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Request;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Interceptor(I_R_Request.class)
@Component
public class R_Request
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final DocumentCollection documentsCollection;

	public R_Request(@NonNull final DocumentCollection documentsCollection)
	{
		this.documentsCollection = documentsCollection;
	}

	/**
	 * If request has the ReminderDate set then update the BPartner's ReminderDateExtern or ReminderDateIntern (based on request's sales rep).
	 * <p>
	 * Task https://github.com/metasfresh/metasfresh/issues/2066
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_R_Request.COLUMNNAME_ReminderDate)
	public void updateBPartnerReminderDate(final I_R_Request request)
	{
		final Timestamp reminderDate = request.getReminderDate();
		if (reminderDate == null)
		{
			return;
		}

		final UserId adUserId = UserId.ofRepoIdOrNull(request.getSalesRep_ID());
		if (adUserId == null || !adUserId.isRegularUser())
		{
			return;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(request.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			// nothing to do
			return;
		}
		final I_C_BPartner bpartner = bpartnerDAO.getByIdInTrx(bpartnerId);
		if (bpartner == null)
		{
			// nothing to do
			return;
		}

		if (bpartner.getSalesRepIntern_ID() == adUserId.getRepoId())
		{
			bpartner.setReminderDateIntern(reminderDate);
		}
		else if (bpartner.getSalesRep_ID() == adUserId.getRepoId())
		{
			bpartner.setReminderDateExtern(reminderDate);
		}
		else
		{
			// nothing to do
			return;
		}

		bpartnerDAO.save(bpartner);
		documentsCollection.invalidateDocumentByRecordId(I_C_BPartner.Table_Name, bpartner.getC_BPartner_ID());
	}
}
