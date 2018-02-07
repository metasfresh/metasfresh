package org.adempiere.acct.api.impl;

import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IAccountDAO;
import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AccountDAO implements IAccountDAO
{
	/** Maps {@link AcctSegmentType} to {@link I_C_ValidCombination}'s column name */
	private static final Map<AcctSegmentType, ModelColumn<I_C_ValidCombination, ?>> segmentType2column = ImmutableMap.<AcctSegmentType, ModelColumn<I_C_ValidCombination, ?>> builder()
			.put(AcctSegmentType.Client, I_C_ValidCombination.COLUMN_AD_Client_ID)
			.put(AcctSegmentType.Organization, I_C_ValidCombination.COLUMN_AD_Org_ID)
			.put(AcctSegmentType.Account, I_C_ValidCombination.COLUMN_Account_ID)
			.put(AcctSegmentType.SubAccount, I_C_ValidCombination.COLUMN_C_SubAcct_ID)
			.put(AcctSegmentType.Product, I_C_ValidCombination.COLUMN_M_Product_ID)
			.put(AcctSegmentType.BPartner, I_C_ValidCombination.COLUMN_C_BPartner_ID)
			.put(AcctSegmentType.OrgTrx, I_C_ValidCombination.COLUMN_AD_OrgTrx_ID)
			.put(AcctSegmentType.LocationFrom, I_C_ValidCombination.COLUMN_C_LocFrom_ID)
			.put(AcctSegmentType.LocationTo, I_C_ValidCombination.COLUMN_C_LocTo_ID)
			.put(AcctSegmentType.SalesRegion, I_C_ValidCombination.COLUMN_C_SalesRegion_ID)
			.put(AcctSegmentType.Project, I_C_ValidCombination.COLUMN_C_Project_ID)
			.put(AcctSegmentType.Campaign, I_C_ValidCombination.COLUMN_C_Campaign_ID)
			.put(AcctSegmentType.Activity, I_C_ValidCombination.COLUMN_C_Activity_ID)
			.put(AcctSegmentType.UserList1, I_C_ValidCombination.COLUMN_User1_ID)
			.put(AcctSegmentType.UserList2, I_C_ValidCombination.COLUMN_User2_ID)
			.put(AcctSegmentType.UserElement1, I_C_ValidCombination.COLUMN_UserElement1_ID)
			.put(AcctSegmentType.UserElement2, I_C_ValidCombination.COLUMN_UserElement2_ID)
			.build();

	@Override
	@Cached(cacheName = MAccount.Table_Name)
	public MAccount retrieveAccountById(@CacheCtx final Properties ctx, final int validCombinationId)
	{
		Check.assume(validCombinationId > 0, "validCombinationId > 0");
		final MAccount account = new MAccount(ctx, validCombinationId, ITrx.TRXNAME_None);
		if (account.getC_ValidCombination_ID() <= 0)
		{
			throw new AdempiereException("No account found for C_ValidCombination_ID=" + validCombinationId);
		}
		return account;
	}

	@Override
	public MAccount retrieveAccount(final Properties ctx, final IAccountDimension dimension)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_ValidCombination> queryBuilder = queryBL.createQueryBuilder(I_C_ValidCombination.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ValidCombination.COLUMN_C_AcctSchema_ID, dimension.getC_AcctSchema_ID());

		for (final Map.Entry<AcctSegmentType, org.adempiere.model.ModelColumn<I_C_ValidCombination, ?>> e : segmentType2column.entrySet())
		{
			final AcctSegmentType segmentType = e.getKey();
			final ModelColumn<I_C_ValidCombination, ?> column = e.getValue();
			final int valueInt = dimension.getSegmentValue(segmentType);

			if (valueInt > 0)
			{
				queryBuilder.addEqualsFilter(column, valueInt);
			}
			else
			{
				final boolean mandatorySegment = segmentType == AcctSegmentType.Client
						|| segmentType == AcctSegmentType.Organization
						|| segmentType == AcctSegmentType.Account;
				if (mandatorySegment)
				{
					queryBuilder.addEqualsFilter(column, valueInt);
				}
				else
				{
					queryBuilder.addEqualsFilter(column, null);
				}
			}
		}

		final I_C_ValidCombination existingAccount = queryBuilder.create().firstOnly(I_C_ValidCombination.class);
		return LegacyAdapters.convertToPO(existingAccount);
	}
}
