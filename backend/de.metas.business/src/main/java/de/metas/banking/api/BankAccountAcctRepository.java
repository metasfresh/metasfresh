<<<<<<< HEAD
package de.metas.banking.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.BankAccountAcct;
import de.metas.banking.BankAccountId;
import de.metas.cache.CCache;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
=======
/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
@Repository
public class BankAccountAcctRepository
{
	private static final String TABLENAME_C_BP_BankAccount_Acct = "C_BP_BankAccount_Acct";
	private final CCache<BankAccountId, BPBankAccountAcctBySchemasMap> cache = CCache.<BankAccountId, BPBankAccountAcctBySchemasMap> builder()
			.tableName(TABLENAME_C_BP_BankAccount_Acct)
			.build();

	public BankAccountAcct getByBankAccountIdAndAcctSchemaId(
			final BankAccountId bankAccountId,
			final AcctSchemaId acctSchemaId)
	{
		return getBPBankAccounts(bankAccountId).getByAcctSchemaId(acctSchemaId);
	}

	private BPBankAccountAcctBySchemasMap getBPBankAccounts(final BankAccountId bankAccountId)
	{
		return cache.getOrLoad(bankAccountId, this::retrieveBPBankAccountAcctBySchemasMap);
	}

	private BPBankAccountAcctBySchemasMap retrieveBPBankAccountAcctBySchemasMap(final BankAccountId bankAccountId)
	{
		final List<BankAccountAcct> accts = DB.retrieveRows(
				"SELECT * FROM " + TABLENAME_C_BP_BankAccount_Acct + " WHERE C_BP_BankAccount_ID=? AND IsActive=?",
				Arrays.asList(bankAccountId, true),
				this::retrieveBPBankAccountAcct);

		return new BPBankAccountAcctBySchemasMap(accts);
	}

	private BankAccountAcct retrieveBPBankAccountAcct(final ResultSet rs) throws SQLException
	{
		return BankAccountAcct.builder()
				.bankAccountId(BankAccountId.ofRepoId(rs.getInt("C_BP_BankAccount_ID")))
				.acctSchemaId(AcctSchemaId.ofRepoId(rs.getInt("C_AcctSchema_ID")))
				//
				.bankAssetAcct(AccountId.ofRepoId(rs.getInt("B_Asset_Acct")))
				.unallocatedCashAcct(AccountId.ofRepoId(rs.getInt("B_UnallocatedCash_Acct")))
				.bankInTransitAcct(AccountId.ofRepoId(rs.getInt("B_InTransit_Acct")))
				.paymentSelectAcct(AccountId.ofRepoId(rs.getInt("B_PaymentSelect_Acct")))
				.interestRevenueAcct(AccountId.ofRepoId(rs.getInt("B_InterestRev_Acct")))
				.interestExpenseAcct(AccountId.ofRepoId(rs.getInt("B_InterestExp_Acct")))
				.paymentBankFeeAcct(AccountId.ofRepoId(rs.getInt("PayBankFee_Acct")))
				.paymentWriteOffAcct(AccountId.optionalOfRepoId(rs.getInt("Payment_WriteOff_Acct")))
=======
package de.metas.banking.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.BankAccountId;
import de.metas.banking.accounting.BankAccountAcct;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_BP_BankAccount_Acct;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BankAccountAcctRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CCache<BankAccountId, BPBankAccountAcctBySchemasMap> cache = CCache.<BankAccountId, BPBankAccountAcctBySchemasMap>builder()
			.tableName(I_C_BP_BankAccount_Acct.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public BankAccountAcct getAccounts(
			final BankAccountId bankAccountId,
			final AcctSchemaId acctSchemaId)
	{
		return getAccountsMap(bankAccountId).getByAcctSchemaId(acctSchemaId);
	}

	private BPBankAccountAcctBySchemasMap getAccountsMap(final BankAccountId bankAccountId)
	{
		return cache.getOrLoad(bankAccountId, this::retrieveAccountsMap);
	}

	private BPBankAccountAcctBySchemasMap retrieveAccountsMap(final BankAccountId bankAccountId)
	{
		final List<BankAccountAcct> list = queryBL.createQueryBuilder(I_C_BP_BankAccount_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_BankAccount_Acct.COLUMNNAME_C_BP_BankAccount_ID, bankAccountId)
				.create()
				.stream()
				.map(BankAccountAcctRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new BPBankAccountAcctBySchemasMap(list);
	}

	private static BankAccountAcct fromRecord(@NonNull final I_C_BP_BankAccount_Acct record)
	{
		return BankAccountAcct.builder()
				.bankAccountId(BankAccountId.ofRepoId(record.getC_BP_BankAccount_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				//
				.B_Asset_Acct(Account.of(AccountId.ofRepoId(record.getB_Asset_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_Asset_Acct))
				.B_UnallocatedCash_Acct(Account.of(AccountId.ofRepoId(record.getB_UnallocatedCash_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_UnallocatedCash_Acct))
				.B_InTransit_Acct(Account.of(AccountId.ofRepoId(record.getB_InTransit_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_InTransit_Acct))
				.B_PaymentSelect_Acct(Account.of(AccountId.ofRepoId(record.getB_PaymentSelect_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_PaymentSelect_Acct))
				.B_InterestRev_Acct(Account.of(AccountId.ofRepoId(record.getB_InterestRev_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_InterestRev_Acct))
				.B_InterestExp_Acct(Account.of(AccountId.ofRepoId(record.getB_InterestExp_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_B_InterestExp_Acct))
				.PayBankFee_Acct(Account.of(AccountId.ofRepoId(record.getPayBankFee_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_PayBankFee_Acct))
				.RealizedGain_Acct(Account.of(AccountId.ofRepoId(record.getRealizedGain_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_RealizedGain_Acct))
				.RealizedLoss_Acct(Account.of(AccountId.ofRepoId(record.getRealizedLoss_Acct()), I_C_BP_BankAccount_Acct.COLUMNNAME_RealizedLoss_Acct))
				.Payment_WriteOff_Acct(AccountId.optionalOfRepoId(record.getPayment_WriteOff_Acct()).
											   map(accountId -> Account.of(accountId, I_C_BP_BankAccount_Acct.COLUMNNAME_Payment_WriteOff_Acct)))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				//
				.build();
	}

	public int updateAllFromAcctSchemaDefault(@NonNull final I_C_AcctSchema_Default acctSchemaDefault)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(acctSchemaDefault.getC_AcctSchema_ID());

<<<<<<< HEAD
		final String sql = "UPDATE " + TABLENAME_C_BP_BankAccount_Acct + " a "
=======
		final String sql = "UPDATE " + I_C_BP_BankAccount_Acct.Table_Name + " a "
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				+ "SET B_InTransit_Acct=" + acctSchemaDefault.getB_InTransit_Acct()
				+ ", B_Asset_Acct=" + acctSchemaDefault.getB_Asset_Acct()
				+ ", B_Expense_Acct=" + acctSchemaDefault.getB_Expense_Acct()
				+ ", B_InterestRev_Acct=" + acctSchemaDefault.getB_InterestRev_Acct()
				+ ", B_InterestExp_Acct=" + acctSchemaDefault.getB_InterestExp_Acct()
				+ ", B_Unidentified_Acct=" + acctSchemaDefault.getB_Unidentified_Acct()
				+ ", B_UnallocatedCash_Acct=" + acctSchemaDefault.getB_UnallocatedCash_Acct()
				+ ", B_PaymentSelect_Acct=" + acctSchemaDefault.getB_PaymentSelect_Acct()
				+ ", B_SettlementGain_Acct=" + acctSchemaDefault.getB_SettlementGain_Acct()
				+ ", B_SettlementLoss_Acct=" + acctSchemaDefault.getB_SettlementLoss_Acct()
				+ ", B_RevaluationGain_Acct=" + acctSchemaDefault.getB_RevaluationGain_Acct()
				+ ", B_RevaluationLoss_Acct=" + acctSchemaDefault.getB_RevaluationLoss_Acct()
				+ ", Updated=now(), UpdatedBy=0 "
				+ " WHERE a.C_AcctSchema_ID=?"
				+ " AND EXISTS (SELECT 1 FROM C_BP_BankAccount_Acct x WHERE x.C_BP_BankAccount_ID=a.C_BP_BankAccount_ID)";

<<<<<<< HEAD
		return DB.executeUpdateEx(
=======
		return DB.executeUpdateAndThrowExceptionOnFail(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				sql,
				new Object[] { acctSchemaId },
				ITrx.TRXNAME_ThreadInherited);
	}

	public int createMissing(@NonNull final AcctSchemaId acctSchemaId)
	{
<<<<<<< HEAD
		final String sql = "INSERT INTO " + TABLENAME_C_BP_BankAccount_Acct
=======
		final String sql = "INSERT INTO " + I_C_BP_BankAccount_Acct.Table_Name
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				+ " (C_BP_BankAccount_ID, C_AcctSchema_ID,"
				+ " AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,"
				+ " B_InTransit_Acct, B_Asset_Acct, B_Expense_Acct, B_InterestRev_Acct, B_InterestExp_Acct,"
				+ " B_Unidentified_Acct, B_UnallocatedCash_Acct, B_PaymentSelect_Acct,"
				+ " B_SettlementGain_Acct, B_SettlementLoss_Acct,"
				+ " B_RevaluationGain_Acct, B_RevaluationLoss_Acct) "
				//
				+ " SELECT"
				+ " x.C_BP_BankAccount_ID, acct.C_AcctSchema_ID,"
				+ " x.AD_Client_ID, x.AD_Org_ID, 'Y', now(), 0, now(), 0,"
				+ " acct.B_InTransit_Acct, acct.B_Asset_Acct, acct.B_Expense_Acct, acct.B_InterestRev_Acct, acct.B_InterestExp_Acct,"
				+ " acct.B_Unidentified_Acct, acct.B_UnallocatedCash_Acct, acct.B_PaymentSelect_Acct,"
				+ " acct.B_SettlementGain_Acct, acct.B_SettlementLoss_Acct,"
				+ " acct.B_RevaluationGain_Acct, acct.B_RevaluationLoss_Acct "
				+ " FROM C_BP_BankAccount x"
				+ " INNER JOIN C_AcctSchema_Default acct ON (x.AD_Client_ID=acct.AD_Client_ID) "
				+ " WHERE acct.C_AcctSchema_ID=?"
				+ " AND NOT EXISTS (SELECT 1 FROM C_BP_BankAccount_Acct a WHERE a.C_BP_BankAccount_ID=x.C_BP_BankAccount_ID AND a.C_AcctSchema_ID=acct.C_AcctSchema_ID)";

<<<<<<< HEAD
		return DB.executeUpdateEx(
=======
		return DB.executeUpdateAndThrowExceptionOnFail(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				sql,
				new Object[] { acctSchemaId },
				ITrx.TRXNAME_ThreadInherited);
	}

	//
	//
	//
	//
	//

	@ToString
	private static class BPBankAccountAcctBySchemasMap
	{
		private final ImmutableMap<@NonNull AcctSchemaId, BankAccountAcct> byAcctSchemaId;

<<<<<<< HEAD
		private BPBankAccountAcctBySchemasMap(@NonNull final List<BankAccountAcct> accts)
		{
			this.byAcctSchemaId = Maps.uniqueIndex(accts, BankAccountAcct::getAcctSchemaId);
=======
		private BPBankAccountAcctBySchemasMap(@NonNull final List<BankAccountAcct> list)
		{
			this.byAcctSchemaId = Maps.uniqueIndex(list, BankAccountAcct::getAcctSchemaId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		public BankAccountAcct getByAcctSchemaId(final AcctSchemaId acctSchemaId)
		{
			final BankAccountAcct acct = byAcctSchemaId.get(acctSchemaId);
			if (acct == null)
			{
				throw new AdempiereException("No BP Bank Account Accounting settings found for " + acctSchemaId);
			}
			return acct;
		}
	}
}
