package de.metas.acct.api.impl;

import java.util.Map;
import java.util.Properties;

import de.metas.util.NumberUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;

import com.google.common.collect.ImmutableMap;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.IAccountDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
	private static final Map<AcctSegmentType, String> segmentType2column = ImmutableMap.<AcctSegmentType, String> builder()
			.put(AcctSegmentType.Client, I_C_ValidCombination.COLUMNNAME_AD_Client_ID)
			.put(AcctSegmentType.Organization, I_C_ValidCombination.COLUMNNAME_AD_Org_ID)
			.put(AcctSegmentType.Account, I_C_ValidCombination.COLUMNNAME_Account_ID)
			.put(AcctSegmentType.SubAccount, I_C_ValidCombination.COLUMNNAME_C_SubAcct_ID)
			.put(AcctSegmentType.Product, I_C_ValidCombination.COLUMNNAME_M_Product_ID)
			.put(AcctSegmentType.BPartner, I_C_ValidCombination.COLUMNNAME_C_BPartner_ID)
			.put(AcctSegmentType.OrgTrx, I_C_ValidCombination.COLUMNNAME_AD_OrgTrx_ID)
			.put(AcctSegmentType.LocationFrom, I_C_ValidCombination.COLUMNNAME_C_LocFrom_ID)
			.put(AcctSegmentType.LocationTo, I_C_ValidCombination.COLUMNNAME_C_LocTo_ID)
			.put(AcctSegmentType.SalesRegion, I_C_ValidCombination.COLUMNNAME_C_SalesRegion_ID)
			.put(AcctSegmentType.Project, I_C_ValidCombination.COLUMNNAME_C_Project_ID)
			.put(AcctSegmentType.Campaign, I_C_ValidCombination.COLUMNNAME_C_Campaign_ID)
			.put(AcctSegmentType.Activity, I_C_ValidCombination.COLUMNNAME_C_Activity_ID)
			.put(AcctSegmentType.UserList1, I_C_ValidCombination.COLUMNNAME_User1_ID)
			.put(AcctSegmentType.UserList2, I_C_ValidCombination.COLUMNNAME_User2_ID)
			.put(AcctSegmentType.UserElement1, I_C_ValidCombination.COLUMNNAME_UserElement1_ID)
			.put(AcctSegmentType.UserElement2, I_C_ValidCombination.COLUMNNAME_UserElement2_ID)
			.build();

	@Override
	@Cached(cacheName = MAccount.Table_Name)
	public MAccount getById(@CacheCtx final Properties ctx, final int validCombinationId)
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
	public MAccount getById(final Properties ctx, @NonNull final AccountId accountId)
	{
		return getById(ctx, accountId.getRepoId());
	}

	@Override
	public MAccount retrieveAccount(final Properties ctx, final AccountDimension dimension)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_ValidCombination> queryBuilder = queryBL.createQueryBuilder(I_C_ValidCombination.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ValidCombination.COLUMNNAME_C_AcctSchema_ID, dimension.getAcctSchemaId());

		for (final Map.Entry<AcctSegmentType, String> e : segmentType2column.entrySet())
		{
			final AcctSegmentType segmentType = e.getKey();
			final String columnName = e.getValue();

			final Object value = dimension.getSegmentValue(segmentType);

			if(value instanceof String)
			{
				queryBuilder.addEqualsFilter(columnName, String.valueOf(value));
			}

			else if (value instanceof Integer)
			{
				final int valueInt = NumberUtils.asInt(value, 0);

				if (valueInt > 0)
				{
					queryBuilder.addEqualsFilter(columnName, valueInt);
				}

				else
				{
					final boolean mandatorySegment = segmentType == AcctSegmentType.Client
							|| segmentType == AcctSegmentType.Organization
							|| segmentType == AcctSegmentType.Account;
					if (mandatorySegment)
					{
						queryBuilder.addEqualsFilter(columnName, valueInt);
					}
					else
					{
						queryBuilder.addEqualsFilter(columnName, null);
					}
				}
			}
		}

		final I_C_ValidCombination existingAccount = queryBuilder.create().firstOnly(I_C_ValidCombination.class);
		return LegacyAdapters.convertToPO(existingAccount);
	}
}
