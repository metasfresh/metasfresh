/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.grssignum.export.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.externalsystem.grssignum.ExportBPartnerToGRSService;
import de.metas.job.JobId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_Job;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Job.class)
@Component
public class C_Job
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExportBPartnerToGRSService exportBPartnerToGRSService;

	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	public C_Job(@NonNull final ExportBPartnerToGRSService exportBPartnerToGRSService)
	{
		this.exportBPartnerToGRSService = exportBPartnerToGRSService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void triggerSyncBPartnerLocationWithExternalSystem(@NonNull final I_C_Job cJob)
	{
		final ImmutableSet<UserId> contactIds = userDAO.retrieveUsersByJobId(JobId.ofRepoId(cJob.getC_Job_ID()));

		for(final UserId contactId: contactIds){
			final BPartnerId bpartnerId = userDAO.getBPartnerIdByUserId(contactId);

			if (bpartnerId != null)
			{
				trxManager.runAfterCommit(() -> exportBPartnerToGRSService.enqueueBPartnerSync(bpartnerId));
			}
		}
	}
}
