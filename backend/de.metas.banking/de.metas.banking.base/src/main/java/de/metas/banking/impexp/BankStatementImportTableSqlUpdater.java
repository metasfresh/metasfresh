package de.metas.banking.impexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_I_BankStatement;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
@UtilityClass
public class BankStatementImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(BankStatementImportTableSqlUpdater.class);

	public void updateBankStatementImportTable(
			@NonNull final ImportRecordsSelection selection,
			@Nullable final BankAccountId orgBankAccountId,
			@Nullable final String bankStatementName,
			@Nullable final LocalDate bankStatementDate,
			@Nullable final BankStatementId bankStatementId)
	{
		updateOrgBankAccount(orgBankAccountId, selection);
		updateBankAccountTo(selection);
		updateStatementDate(selection, bankStatementDate);
		updateName(selection, bankStatementName);
		updateCurrency(selection);
		updateAmount(selection);
		updateValutaDate(selection);
		updateC_BPartner(selection);
		checkInvoiceBPartnerCombination(selection);

		updateBankStatement(selection, bankStatementId);

		detectDuplicates(selection);
	}

	private void updateName(final ImportRecordsSelection selection, @Nullable final String bankStatementName)
	{
		StringBuilder sql;
		if (bankStatementName != null)
		{
			sql = new StringBuilder("UPDATE ")
					.append(I_I_BankStatement.Table_Name + " i ")
					.append(" SET Name = "
							+ "'" + bankStatementName + "'")
					.append(" WHERE NAME IS NULL ")
					.append(" AND i.I_IsImported<>'Y' ")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		sql = new StringBuilder("UPDATE ")
				.append(I_I_BankStatement.Table_Name + " i ")
				.append(" SET Name =  COALESCE ("
						+ I_I_BankStatement.COLUMNNAME_StatementDate + ", "
						+ "current_date :: timestamp without time zone ) :: character varying")
				.append(" WHERE NAME IS NULL ")
				.append(" AND i.I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void updateStatementDate(final ImportRecordsSelection selection, @Nullable final LocalDate bankStatementDate)
	{
		if (bankStatementDate != null)
		{
			final StringBuilder sql = new StringBuilder("UPDATE ")
					.append(I_I_BankStatement.Table_Name + " i ")
					.append(" SET "
							+ I_I_BankStatement.COLUMNNAME_StatementDate
							+ " = " + DB.TO_DATE(bankStatementDate))
					.append(" WHERE StatementDate IS NULL ")
					.append(" AND i.I_IsImported<>'Y' ")
					.append(selection.toSqlWhereClause("i"));

			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(I_I_BankStatement.Table_Name + " i ")
				.append(" SET "
						+ I_I_BankStatement.COLUMNNAME_StatementDate
						+ " = current_date :: timestamp without time zone ")
				.append(" WHERE StatementDate IS NULL ")
				.append(" AND i.I_IsImported<>'Y' ")
				.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

	}

	private void updateBankStatement(final ImportRecordsSelection selection, @Nullable final BankStatementId bankStatementId)
	{
		if (bankStatementId != null)
		{
			final StringBuilder sql = new StringBuilder("UPDATE ")
					.append(I_I_BankStatement.Table_Name + " i ")
					.append(" SET " + I_I_BankStatement.COLUMNNAME_C_BankStatement_ID + " = " + bankStatementId.getRepoId())
					.append(" WHERE " + I_I_BankStatement.COLUMNNAME_C_BankStatement_ID + " IS NULL ")
					.append(" AND i.I_IsImported<>'Y' ")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			final StringBuilder sql = new StringBuilder("UPDATE ")
					.append(I_I_BankStatement.Table_Name + " i ")
					.append(" SET "
							+ I_I_BankStatement.COLUMNNAME_C_BankStatement_ID
							+ " = (SELECT " + I_C_BankStatement.COLUMNNAME_C_BankStatement_ID
							+ " FROM " + I_C_BankStatement.Table_Name + " s ")
					.append(" WHERE i." + I_I_BankStatement.COLUMNNAME_C_BP_BankAccount_ID
							+ "=s." + I_C_BankStatement.COLUMNNAME_C_BP_BankAccount_ID)
					.append(" AND i." + I_I_BankStatement.COLUMNNAME_AD_Client_ID
							+ " =s." + I_C_BankStatement.COLUMNNAME_AD_Client_ID)
					.append(" AND i.name = s.Name ")
					.append(" AND COALESCE(i.EftStatementReference, 'X') = COALESCE(s.EftStatementReference, 'X') ")
					.append(" AND i.StatementDate = s.StatementDate  limit 1)")
					.append(" WHERE " + I_I_BankStatement.COLUMNNAME_C_BankStatement_ID + " IS NULL ")
					.append(" AND i.I_IsImported<>'Y' ")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}
	}

	private void updateOrgBankAccount(final BankAccountId orgBankAccountId, final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement i "
					+ "SET C_BP_BankAccount_ID="
					+ "( "
					+ " SELECT C_BP_BankAccount_ID "
					+ " FROM C_BP_BankAccount a "
					+ " WHERE a." + I_C_BP_BankAccount.COLUMNNAME_IBAN
					+ " = i." + I_I_BankStatement.COLUMNNAME_IBAN
					+ " AND a.AD_Client_ID=i.AD_Client_ID "
					+ " )"
					+ "WHERE i.C_BP_BankAccount_ID IS NULL "
					+ "AND i.I_IsImported<>'Y' "
					+ "OR i.I_IsImported IS NULL")
							.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement i "
					+ "SET C_BP_BankAccount_ID="
					+ "( "
					+ " SELECT C_BP_BankAccount_ID "
					+ " FROM C_BP_BankAccount a, C_Bank b "
					+ " WHERE b.IsOwnBank='Y' "
					+ " AND a.AD_Client_ID=i.AD_Client_ID "
					+ " AND a.C_Bank_ID=b.C_Bank_ID "
					+ " AND a.AccountNo=i.BankAccountNo "
					+ " AND (b.RoutingNo=i.RoutingNo "
					+ " OR b.SwiftCode=i.RoutingNo) "
					+ ") "
					+ "WHERE i.C_BP_BankAccount_ID IS NULL "
					+ "AND i.I_IsImported<>'Y' "
					+ "OR i.I_IsImported IS NULL")
							.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		if (orgBankAccountId != null)
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement i "
					+ "SET C_BP_BankAccount_ID=(SELECT C_BP_BankAccount_ID FROM C_BP_BankAccount a WHERE a.C_BP_BankAccount_ID=").append(orgBankAccountId.getRepoId());
			sql.append(" and a.AD_Client_ID=i.AD_Client_ID) "
					+ "WHERE i.C_BP_BankAccount_ID IS NULL "
					+ "AND i.BankAccountNo IS NULL "
					+ "AND i.I_isImported<>'Y' "
					+ "OR i.I_isImported IS NULL")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement "
					+ "SET I_isImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Bank Account, ' "
					+ "WHERE C_BP_BankAccount_ID IS NULL "
					+ "AND I_isImported<>'Y' "
					+ "OR I_isImported IS NULL")
							.append(selection.toSqlWhereClause());
			DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}
	}

	private void updateC_BPartner(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET " + I_I_BankStatement.COLUMNNAME_C_BPartner_ID
				+ " = (SELECT " + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name + " bp "
				+ " WHERE " + "(i." + I_I_BankStatement.COLUMNNAME_Bill_BPartner_Name
				+ " = bp." + I_C_BPartner.COLUMNNAME_Name
				+ " OR i." + I_I_BankStatement.COLUMNNAME_BPartnerValue
				+ " = bp." + I_C_BPartner.COLUMNNAME_Value
				+ " )) "
				+ "WHERE i." + I_I_BankStatement.COLUMNNAME_C_BPartner_ID + " IS NULL "
				+ "AND i.I_IsImported<>'Y' "
				+ "OR i.I_IsImported IS NULL")
						.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void updateBankAccountTo(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET " + I_I_BankStatement.COLUMNNAME_C_BP_BankAccountTo_ID + " = "
				+ "( SELECT " + I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID
				+ " FROM " + I_C_BP_BankAccount.Table_Name + " ba "
				+ " WHERE ba." + I_C_BP_BankAccount.COLUMNNAME_IBAN + " = i."
				+ I_I_BankStatement.COLUMNNAME_IBAN_To
				+ " )"
				+ "WHERE i." + I_I_BankStatement.COLUMNNAME_C_BP_BankAccountTo_ID + " IS NULL "
				+ "AND i.I_IsImported<>'Y' "
				+ "OR i.I_IsImported IS NULL")
						.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void updateCurrency(final ImportRecordsSelection selection)
	{
		StringBuilder sql;

		sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c"
				+ " WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL"
				+ " AND I_IsImported<>'Y'")
						.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE I_BankStatement i "
				+ "SET C_Currency_ID=(SELECT C_Currency_ID FROM C_BP_BankAccount WHERE C_BP_BankAccount_ID=i.C_BP_BankAccount_ID) "
				+ "WHERE i.C_Currency_ID IS NULL "
				+ "AND i.ISO_Code IS NULL")
						.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency,' "
				+ "WHERE C_Currency_ID IS NULL "
				+ "AND I_IsImported<>'E' "
				+ " AND I_IsImported<>'Y'")
						.append(selection.toSqlWhereClause());

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void updateAmount(final ImportRecordsSelection selection)
	{
		StringBuilder sql;

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET ChargeAmt=0 "
				+ "WHERE ChargeAmt IS NULL "
				+ "AND I_IsImported<>'Y'")
						.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET InterestAmt=0 "
				+ "WHERE InterestAmt IS NULL "
				+ "AND I_IsImported<>'Y'")
						.append(selection.toSqlWhereClause());
		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);


	}

	private void updateValutaDate(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET ValutaDate=StatementLineDate "
				+ "WHERE ValutaDate IS NULL "
				+ "AND I_isImported<>'Y'")
						.append(selection.toSqlWhereClause());

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void checkInvoiceBPartnerCombination(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BankStatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Invalid Invoice<->BPartner, ' "
				+ "WHERE I_BankStatement_ID IN "
				+ "(SELECT I_BankStatement_ID "
				+ "FROM I_BankStatement i"
				+ " INNER JOIN C_Invoice v ON (i.C_Invoice_ID=v.C_Invoice_ID) "
				+ "WHERE i.C_BPartner_ID IS NOT NULL "
				+ " AND v.C_BPartner_ID IS NOT NULL "
				+ " AND v.C_BPartner_ID<>i.C_BPartner_ID) ")
						.append(selection.toSqlWhereClause());

		DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void detectDuplicates(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("SELECT i.I_BankStatement_ID, l.C_BankStatementLine_ID, i.EftTrxID "
				+ "FROM I_BankStatement i, C_BankStatement s, C_BankStatementLine l "
				+ "WHERE i.I_isImported='N' "
				+ "AND s.C_BankStatement_ID=l.C_BankStatement_ID "
				+ "AND i.EftTrxID IS NOT NULL AND "
				// Concatenate EFT Info
				+ "(l.EftTrxID||l.EftAmt||l.EftStatementLineDate||l.EftValutaDate||l.EftTrxType||l.EftCurrency||l.EftReference||s.EftStatementReference "
				+ "||l.EftCheckNo||l.EftMemo||l.EftPayee||l.EftPayeeAccount) "
				+ "= "
				+ "(i.EftTrxID||i.EftAmt||i.EftStatementLineDate||i.EftValutaDate||i.EftTrxType||i.EftCurrency||i.EftReference||i.EftStatementReference "
				+ "||i.EftCheckNo||i.EftMemo||i.EftPayee||i.EftPayeeAccount) ");

		final StringBuilder updateSql = new StringBuilder("UPDATE I_Bankstatement "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'Err=Duplicate['||?||']' "
				+ "WHERE I_BankStatement_ID=?")
						.append(selection.toSqlWhereClause());

		PreparedStatement pupdt = DB.prepareStatement(updateSql.toString(), ITrx.TRXNAME_ThreadInherited);

		PreparedStatement pstmtDuplicates = null;

		int no = 0;
		try
		{
			pstmtDuplicates = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			ResultSet rs = pstmtDuplicates.executeQuery();
			while (rs.next())
			{
				String info = "Line_ID=" + rs.getInt(2) // l.C_BankStatementLine_ID
						+ ",EDTTrxID=" + rs.getString(3); // i.EftTrxID
				pupdt.setString(1, info);
				pupdt.setInt(2, rs.getInt(1)); // i.I_BankStatement_ID
				pupdt.executeUpdate();
				no++;
			}
			rs.close();
			pstmtDuplicates.close();
			pupdt.close();

			rs = null;
			pstmtDuplicates = null;
			pupdt = null;
		}
		catch (Exception e)
		{
			logger.error("DetectDuplicates {}", e.getMessage());
		}

		logger.warn("Duplicates= {}", no);
	}
}
