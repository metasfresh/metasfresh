package de.metas.marketing.base.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.ModelValidator;

import de.metas.marketing.base.model.I_MKTG_Campaign_ContactPerson;
import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import lombok.NonNull;

/*
 * #%L
 * de.metas.marketing
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

@Interceptor(I_MKTG_ContactPerson.class)
public class MKTG_ContactPerson
{
	public static final MKTG_ContactPerson INSTANCE = new MKTG_ContactPerson();

	private MKTG_ContactPerson()
	{
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_MKTG_ContactPerson.COLUMNNAME_AD_User_ID)
	public void updateCampaignContactPersonAdUserId(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// note AD_User_ID=0 needs special threatment
		final Integer newAdUserID = contactPerson.getAD_User_ID() <= 0 ? null : contactPerson.getAD_User_ID();

		final IQueryUpdater<I_MKTG_Campaign_ContactPerson> updater = queryBL
				.createCompositeQueryUpdater(I_MKTG_Campaign_ContactPerson.class)
				.addSetColumnValue(I_MKTG_Campaign_ContactPerson.COLUMNNAME_AD_User_ID, newAdUserID);

		createContactPersonQuery(contactPerson)
				.update(updater);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteCampaignContactPersonAdUserId(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		createContactPersonQuery(contactPerson)
				.delete();
	}

	private IQuery<I_MKTG_Campaign_ContactPerson> createContactPersonQuery(@NonNull final I_MKTG_ContactPerson contactPerson)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MKTG_Campaign_ContactPerson.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_Campaign_ContactPerson.COLUMN_MKTG_ContactPerson_ID, contactPerson.getMKTG_ContactPerson_ID())
				.create();
	}
}
