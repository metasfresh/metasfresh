package de.metas.acct.api.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MAccount;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
	final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	public @NonNull MAccount getById(@CacheCtx final Properties ctx, final int validCombinationId)
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
	public @NonNull MAccount getById(final Properties ctx, @NonNull final AccountId accountId)
	{
		return getById(ctx, accountId.getRepoId());
	}

	@Override
	public ElementValueId getElementValueIdByAccountId(@NonNull final AccountId accountId)
	{
		return getById(Env.getCtx(), accountId).getElementValueId();
	}

	@Override
	public MAccount retrieveAccount(final Properties ctx, final AccountDimension dimension)
	{
		final IQueryBuilder<I_C_ValidCombination> queryBuilder = queryBL.createQueryBuilder(I_C_ValidCombination.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ValidCombination.COLUMNNAME_C_AcctSchema_ID, dimension.getAcctSchemaId());

		for (final Map.Entry<AcctSegmentType, String> e : segmentType2column.entrySet())
		{
			final AcctSegmentType segmentType = e.getKey();
			final String columnName = e.getValue();

			final Object value = dimension.getSegmentValue(segmentType);

			if (value instanceof String)
			{
				final String valueStr = value.toString();
				if (!valueStr.isEmpty())
				{
					queryBuilder.addEqualsFilter(columnName, valueStr);
				}
				else
				{
					queryBuilder.addEqualsFilter(columnName, null);
				}
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

	@Override
	@NonNull
	public AccountId getOrCreate(@NonNull final AccountDimension dimension)
	{
		// Existing
		final MAccount existingAccount = retrieveAccount(Env.getCtx(), dimension);
		if (existingAccount != null)
		{
			return AccountId.ofRepoId(existingAccount.getC_ValidCombination_ID());
		}

		final I_C_ValidCombination vc = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_ValidCombination.class);
		vc.setAD_Org_ID(dimension.getAD_Org_ID());
		vc.setC_AcctSchema_ID(AcctSchemaId.toRepoId(dimension.getAcctSchemaId()));
		vc.setAccount_ID(dimension.getC_ElementValue_ID());
		vc.setC_SubAcct_ID(dimension.getC_SubAcct_ID());
		vc.setM_Product_ID(dimension.getM_Product_ID());
		vc.setC_BPartner_ID(dimension.getC_BPartner_ID());
		vc.setAD_OrgTrx_ID(dimension.getAD_OrgTrx_ID());
		vc.setC_LocFrom_ID(dimension.getC_LocFrom_ID());
		vc.setC_LocTo_ID(dimension.getC_LocTo_ID());
		vc.setC_SalesRegion_ID(dimension.getC_SalesRegion_ID());
		vc.setC_Project_ID(dimension.getC_Project_ID());
		vc.setC_Campaign_ID(dimension.getC_Campaign_ID());
		vc.setC_Activity_ID(dimension.getC_Activity_ID());
		vc.setUser1_ID(dimension.getUser1_ID());
		vc.setUser2_ID(dimension.getUser2_ID());
		vc.setUserElement1_ID(dimension.getUserElement1_ID());
		vc.setUserElement2_ID(dimension.getUserElement2_ID());
		vc.setUserElementNumber1(dimension.getUserElementNumber1());
		vc.setUserElementNumber2(dimension.getUserElementNumber2());
		vc.setUserElementString1(dimension.getUserElementString1());
		vc.setUserElementString2(dimension.getUserElementString2());
		vc.setUserElementString3(dimension.getUserElementString3());
		vc.setUserElementString4(dimension.getUserElementString4());
		vc.setUserElementString5(dimension.getUserElementString5());
		vc.setUserElementString6(dimension.getUserElementString6());
		vc.setUserElementString7(dimension.getUserElementString7());
		InterfaceWrapperHelper.save(vc);

		return AccountId.ofRepoId(vc.getC_ValidCombination_ID());
	}    // get

}
