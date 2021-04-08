/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.IOrgDAO;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;

import java.time.Instant;
import java.util.List;

/*
 * This process is scheduled to be run each night to deactivate bpartners
 * (and their locations, contacts and bank accounts)
 * that were moved to another organization and are scheduled to be deactivated
 * at a given date via AD_OrgChange_History
 */
public class C_BPartner_DeactivateAfterOrgChange extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected String doIt() throws Exception
	{
		deactivateBPartnersAndRelatedEntries();

		return MSG_OK;
	}

	private void deactivateBPartnersAndRelatedEntries()
	{
		final Instant now = SystemTime.asInstant();

		final List<BPartnerId> partnerIdsToDeactivate = queryBL.createQueryBuilder(I_AD_OrgChange_History.class)
				.addCompareFilter(I_AD_OrgChange_History.COLUMNNAME_Date_OrgChange, CompareQueryFilter.Operator.LESS_OR_EQUAL, now)
				.andCollect(I_AD_OrgChange_History.COLUMNNAME_C_BPartner_From_ID, I_C_BPartner.class)
				.create()
				.listIds(BPartnerId::ofRepoId)
				.asList();

		partnerIdsToDeactivate.stream()
				.forEach(
						partnerId -> addLog("Business Partner {} was deactivated because it was moved to another organization.",
											partnerId));

		if (Check.isEmpty(partnerIdsToDeactivate))
		{
			// nothing to do
			return;
		}
		deactivateBankAccounts(partnerIdsToDeactivate, now);
		deactivateUsers(partnerIdsToDeactivate, now);
		deactivateLocations(partnerIdsToDeactivate, now);
		deactivatePartners(partnerIdsToDeactivate, now);
	}

	private void deactivateBankAccounts(final List<BPartnerId> partnerIds, final Instant date)
	{
		final ICompositeQueryUpdater<I_C_BP_BankAccount> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_BP_BankAccount.class)
				.addSetColumnValue(I_C_BP_BankAccount.COLUMNNAME_IsActive, false)
				.addSetColumnValue(I_C_BP_BankAccount.COLUMNNAME_Updated, date)
				.addSetColumnValue(I_C_BP_BankAccount.COLUMNNAME_UpdatedBy, Env.getAD_User_ID());
		queryBL
				.createQueryBuilder(I_C_BP_BankAccount.class)
				.addInArrayFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, partnerIds)
				.create()
				.update(queryUpdater);
	}

	private void deactivateUsers(final List<BPartnerId> partnerIds, final Instant date)
	{
		final ICompositeQueryUpdater<I_AD_User> queryUpdater = queryBL.createCompositeQueryUpdater(I_AD_User.class)
				.addSetColumnValue(I_AD_User.COLUMNNAME_IsActive, false)
				.addSetColumnValue(I_AD_User.COLUMNNAME_Updated, date)
				.addSetColumnValue(I_AD_User.COLUMNNAME_UpdatedBy, Env.getAD_User_ID());
		queryBL
				.createQueryBuilder(I_AD_User.class)
				.addInArrayFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, partnerIds)
				.create()
				.update(queryUpdater);
	}

	private void deactivateLocations(final List<BPartnerId> partnerIds, final Instant date)
	{
		final ICompositeQueryUpdater<I_C_BPartner_Location> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_BPartner_Location.class)
				.addSetColumnValue(I_C_BPartner_Location.COLUMNNAME_IsActive, false)
				.addSetColumnValue(I_C_BPartner_Location.COLUMNNAME_Updated, date)
				.addSetColumnValue(I_C_BPartner_Location.COLUMNNAME_UpdatedBy, Env.getAD_User_ID());
		queryBL
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addInArrayFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, partnerIds)
				.create()
				.update(queryUpdater);
	}

	private void deactivatePartners(final List<BPartnerId> partnerIds, final Instant date)
	{
		final ICompositeQueryUpdater<I_C_BPartner> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_BPartner.class)
				.addSetColumnValue(I_C_BPartner.COLUMNNAME_IsActive, false)
				.addSetColumnValue(I_C_BPartner.COLUMNNAME_Updated, date)
				.addSetColumnValue(I_C_BPartner.COLUMNNAME_UpdatedBy, Env.getAD_User_ID());
		queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addInArrayFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, partnerIds)
				.create()
				.update(queryUpdater);
	}
}
