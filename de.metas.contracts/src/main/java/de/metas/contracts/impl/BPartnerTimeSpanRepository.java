package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Objects;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner_TimeSpan;
import org.compiere.model.X_C_BPartner_TimeSpan;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Repository
public class BPartnerTimeSpanRepository
{

	public void createNewBPartnerTimeSpan(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan timeSpan = newInstance(I_C_BPartner_TimeSpan.class);

		timeSpan.setC_BPartner_ID(bpartnerId.getRepoId());

		timeSpan.setC_BPartner_TimeSpan(null);

		save(timeSpan);
	}

	public void setNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);
		bpartnerTimeSpan.setC_BPartner_TimeSpan(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde);
		save(bpartnerTimeSpan);
	}

	public void setRegularCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);
		bpartnerTimeSpan.setC_BPartner_TimeSpan(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Stammkunde);
		save(bpartnerTimeSpan);
	}

	public void setNonSubscriptionCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);
		bpartnerTimeSpan.setC_BPartner_TimeSpan(null);
		save(bpartnerTimeSpan);
	}

	public I_C_BPartner_TimeSpan retrieveBPartnerTimeSpan(@NonNull final BPartnerId bpartnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_BPartner_TimeSpan.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_BPartner_TimeSpan.COLUMN_C_BPartner_ID, bpartnerId.getRepoId())
				.create()
				.firstOnly(I_C_BPartner_TimeSpan.class);
	}

	public boolean isNewCustomer(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner_TimeSpan bpartnerTimeSpan = retrieveBPartnerTimeSpan(bpartnerId);

		final String currentTimeSpan = bpartnerTimeSpan.getC_BPartner_TimeSpan();

		return Objects.equals(X_C_BPartner_TimeSpan.C_BPARTNER_TIMESPAN_Neukunde, currentTimeSpan);
	}
}
