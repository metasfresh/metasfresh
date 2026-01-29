package de.metas.acct.impexp;

import de.metas.acct.api.AcctSchemaId;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * A helper class for {@link GLJournalImportProcess} that performs the "dirty" but efficient SQL updates on the {@link org.compiere.model.I_I_GLJournal} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class GLJournalImportTableSqlUpdater
{
	private static final Logger log = LogManager.getLogger(GLJournalImportTableSqlUpdater.class);

	private final ImportRecordsSelection selection;
	@Nullable private final AcctSchemaId acctSchemaId;
	@Nullable private final Instant dateAcct;

	@Builder
	private GLJournalImportTableSqlUpdater(
			@NonNull final ImportRecordsSelection selection,
			@Nullable final AcctSchemaId acctSchemaId,
			@Nullable final Instant dateAcct
	)
	{
		this.selection = selection;
		this.acctSchemaId = acctSchemaId;
		this.dateAcct = dateAcct;
	}

	/**
	 * Main entry point that orchestrates all SQL updates for GL Journal import.
	 * Calls individual methods for each logical section.
	 */
	public void updateGLJournal()
	{
		resetAndInitializeFields();
		setClientFromName();
		setClientAndDefaults();
		setAcctSchema();
		setDateAcct();
		setDocType();
		setGLCategory();
		setCurrency();
		setConversionType();
		setCurrencyRate();
		setPeriod();
		setPostingType();
		setOrg();
		setAccountFrom();
		setAccountTo();
		setBPartner();
		setProduct();
		setActivity();
		setProject();
		setOrgTrx();
		setTaxAccounts();
		setTaxes();
		setTaxAmounts();
		setSourceAmounts();
		calculateAccountedAmounts();
		checkAtLeastOneAccountSet();
	}

	/**
	 * Reset IsActive, Created/Updated fields and clear error messages
	 */
	private void resetAndInitializeFields()
	{
		final String sql = "UPDATE I_GLJournal "
				+ "SET IsActive = COALESCE (IsActive, 'Y'),"
				+ " Created = COALESCE (Created, now()),"
				+ " CreatedBy = COALESCE (CreatedBy, 0),"
				+ " Updated = COALESCE (Updated, now()),"
				+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
				+ " I_ErrorMsg = ' ',"
				+ " I_IsImported = 'N' "
				+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL";
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		log.info("Reset={}", no);
	}

	/**
	 * Set Client from ClientValue name
	 */
	private void setClientFromName()
	{
		final String sql = "UPDATE I_GLJournal i "
				+ "SET AD_Client_ID=(SELECT c.AD_Client_ID FROM AD_Client c WHERE c.Value=i.ClientValue) "
				+ "WHERE (AD_Client_ID IS NULL OR AD_Client_ID=0) AND ClientValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'";
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Client from Value={}", no);
	}

	/**
	 * Set Default Client, Doc Org, AcctSchema, DateAcct from process context
	 */
	private void setClientAndDefaults()
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_GLJournal "
															+ "SET AD_Client_ID = COALESCE (AD_Client_ID,").append(ClientId.METASFRESH.getRepoId()).append("),"
																																								   + " AD_OrgDoc_ID = COALESCE (AD_OrgDoc_ID,").append(OrgId.MAIN.getRepoId()).append("),");
		if (acctSchemaId != null)
		{
			sql.append(" C_AcctSchema_ID = COALESCE (C_AcctSchema_ID,").append(AcctSchemaId.toRepoId(acctSchemaId)).append("),");
		}
		if (dateAcct != null)
		{
			sql.append(" DateAcct = COALESCE (DateAcct,").append(DB.TO_DATE(dateAcct)).append("),");
		}
		sql.append(" Updated = COALESCE (Updated, now()) "
						   + "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL")
				.append(selection.toSqlWhereClause());
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Client/DocOrg/Default={}", no);
	}

	/**
	 * Set AcctSchema from name or from ClientInfo default
	 */
	private void setAcctSchema()
	{
		// Set AcctSchema from Name
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_AcctSchema_ID=(SELECT a.C_AcctSchema_ID FROM C_AcctSchema a"
													  + " WHERE i.AcctSchemaName=a.Name AND i.AD_Client_ID=a.AD_Client_ID) "
													  + "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set AcctSchema from Name={}", no);

		// Set AcctSchema from Client default
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET C_AcctSchema_ID=(SELECT c.C_AcctSchema1_ID FROM AD_ClientInfo c WHERE c.AD_Client_ID=i.AD_Client_ID) "
										+ "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set AcctSchema from Client={}", no);

		// Error AcctSchema
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid AcctSchema, '"
										+ "WHERE (C_AcctSchema_ID IS NULL OR C_AcctSchema_ID=0"
										+ " OR NOT EXISTS (SELECT * FROM C_AcctSchema a WHERE i.AD_Client_ID=a.AD_Client_ID))"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid AcctSchema={}", no);
		}
	}

	/**
	 * Set DateAcct to now() if not provided (mandatory)
	 */
	private void setDateAcct()
	{
		final String sql = "UPDATE I_GLJournal i "
				+ "SET DateAcct=now() "
				+ "WHERE DateAcct IS NULL"
				+ " AND I_IsImported<>'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		log.debug("Set DateAcct={}", no);
	}

	/**
	 * Set Document Type from DocTypeName and validate
	 */
	private void setDocType()
	{
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_DocType_ID=(SELECT d.C_DocType_ID FROM C_DocType d"
													  + " WHERE d.Name=i.DocTypeName AND d.DocBaseType='GLJ' AND i.AD_Client_ID=d.AD_Client_ID) "
													  + "WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set DocType={}", no);

		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid DocType, '"
										+ "WHERE (C_DocType_ID IS NULL OR C_DocType_ID=0"
										+ " OR NOT EXISTS (SELECT * FROM C_DocType d WHERE i.AD_Client_ID=d.AD_Client_ID AND d.DocBaseType='GLJ'))"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid DocType={}", no);
		}
	}

	/**
	 * Set GL Category from CategoryName and validate
	 */
	private void setGLCategory()
	{
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET GL_Category_ID=(SELECT c.GL_Category_ID FROM GL_Category c"
													  + " WHERE c.Name=i.CategoryName AND i.AD_Client_ID=c.AD_Client_ID) "
													  + "WHERE GL_Category_ID IS NULL AND CategoryName IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set DocType={}", no);

		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Category, '"
										+ "WHERE (GL_Category_ID IS NULL OR GL_Category_ID=0)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid GLCategory={}", no);
		}
	}

	/**
	 * Set Currency from ISO_Code or from AcctSchema default
	 */
	private void setCurrency()
	{
		// Set Currency from ISO code
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_Currency_ID=(SELECT c.C_Currency_ID FROM C_Currency c"
													  + " WHERE c.ISO_Code=i.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
													  + "WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Currency from ISO={}", no);

		// Set default Currency from AcctSchema
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET C_Currency_ID=(SELECT a.C_Currency_ID FROM C_AcctSchema a"
										+ " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.AD_Client_ID=i.AD_Client_ID)"
										+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Default Currency={}", no);

		// Error Currency
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency, '"
										+ "WHERE (C_Currency_ID IS NULL OR C_Currency_ID=0)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Currency={}", no);
		}
	}

	/**
	 * Set Conversion Type - default to 'S' (Spot) and lookup from value
	 */
	private void setConversionType()
	{
		// Set default conversion type value to 'S' (Spot)
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET ConversionTypeValue='S' "
													  + "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set CurrencyType Value to Spot ={}", no);

		// Set Conversion Type from Value
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET C_ConversionType_ID=(SELECT c.C_ConversionType_ID FROM C_ConversionType c"
										+ " WHERE c.Value=i.ConversionTypeValue AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
										+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set CurrencyType from Value={}", no);

		// Error invalid CurrencyType value
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid CurrencyType, '"
										+ "WHERE (C_ConversionType_ID IS NULL OR C_ConversionType_ID=0) AND ConversionTypeValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid CurrencyTypeValue={}", no);
		}

		// Error no ConversionType at all
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No ConversionType, '"
										+ "WHERE (C_ConversionType_ID IS NULL OR C_ConversionType_ID=0)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("No CourrencyType={}", no);
		}
	}

	/**
	 * Set Currency Rate - home currency rate = 1, otherwise lookup from conversion rates
	 */
	private void setCurrencyRate()
	{
		// Set Home Currency Rate to 1
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET CurrencyRate=1 "
													  + "WHERE EXISTS (SELECT * FROM C_AcctSchema a"
													  + " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.C_Currency_ID=i.C_Currency_ID)"
													  + " AND C_Currency_ID IS NOT NULL AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Home CurrencyRate={}", no);

		// Set Currency Rate from Org-specific conversion rate
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET CurrencyRate=(SELECT MAX(r.MultiplyRate) FROM C_Conversion_Rate r, C_AcctSchema s"
										+ " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
										+ " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
										+ " AND r.AD_Client_ID=i.AD_Client_ID AND r.AD_Org_ID=i.AD_OrgDoc_ID"
										+ " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
										+ " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo "
										// ORDER BY ValidFrom DESC
										+ ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Org Rate={}", no);

		// Set Currency Rate from Client-level conversion rate
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET CurrencyRate=(SELECT MAX(r.MultiplyRate) FROM C_Conversion_Rate r, C_AcctSchema s"
										+ " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
										+ " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
										+ " AND r.AD_Client_ID=i.AD_Client_ID"
										+ " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
										+ " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo "
										// ORDER BY ValidFrom DESC
										+ ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Client Rate={}", no);

		// Error no Rate
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Rate, '"
										+ "WHERE CurrencyRate IS NULL OR CurrencyRate=0"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("No Rate={}", no);
		}
	}

	/**
	 * Set Period from DateAcct and validate
	 */
	private void setPeriod()
	{
		// Set Period from DateAcct
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_Period_ID=(SELECT MAX(p.C_Period_ID) FROM C_Period p"
													  + " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
													  + " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID)"
													  + " WHERE c.AD_Client_ID=i.AD_Client_ID"
													  // globalqss - cruiz - Bug [ 1577712 ] Financial Period Bug
													  + " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.IsActive='Y' AND p.PeriodType='S') "
													  + "WHERE C_Period_ID IS NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Period={}", no);

		// Error Invalid Period
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Period, '"
										+ "WHERE C_Period_ID IS NULL OR C_Period_ID NOT IN"
										+ "(SELECT C_Period_ID FROM C_Period p"
										+ " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
										+ " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID) "
										+ " WHERE c.AD_Client_ID=i.AD_Client_ID"
										// globalqss - cruiz - Bug [ 1577712 ] Financial Period Bug
										+ " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.IsActive='Y' AND p.PeriodType='S')"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Period={}", no);
		}

		// Warning Period Closed
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Period Closed, ' "
										+ "WHERE C_Period_ID IS NOT NULL AND NOT EXISTS"
										+ " (SELECT * FROM C_PeriodControl pc WHERE pc.C_Period_ID=i.C_Period_ID AND DocBaseType='GLJ' AND PeriodStatus='O') "
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Period Closed={}", no);
		}
	}

	/**
	 * Set Posting Type - default to 'A' (Actual) and validate
	 */
	private void setPostingType()
	{
		// Set default PostingType to 'A' (Actual)
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET PostingType='A' "
													  + "WHERE PostingType IS NULL AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Actual PostingType={}", no);

		// Error Invalid PostingType
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid PostingType, ' "
										+ "WHERE PostingType IS NULL OR NOT EXISTS"
										+ " (SELECT * FROM AD_Ref_List r WHERE r.AD_Reference_ID=125 AND i.PostingType=r.Value)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid PostingTypee={}", no);
		}
	}

	/**
	 * Set Org from OrgValue or default to AD_OrgDoc_ID
	 */
	private void setOrg()
	{
		// Set Org from OrgValue
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET AD_Org_ID=COALESCE((SELECT o.AD_Org_ID FROM AD_Org o"
													  + " WHERE o.Value=i.OrgValue AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID),AD_Org_ID) "
													  + "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Org from Value={}", no);

		// Set Org from Doc Org
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET AD_Org_ID=AD_OrgDoc_ID "
										+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NULL AND AD_OrgDoc_ID IS NOT NULL AND AD_OrgDoc_ID<>0"
										+ "  AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Org from Doc Org={}", no);

		// Error Invalid Org
		sql = new StringBuilder("UPDATE I_GLJournal o "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
										+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
										+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("o"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Org={}", no);
		}
	}

	/**
	 * Set AccountFrom from AccountValueFrom and validate
	 */
	private void setAccountFrom()
	{
		// Set AccountFrom from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET AccountFrom_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
													  + " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
													  + " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
													  + " WHERE ev.Value=i.AccountValueFrom AND ev.IsSummary='N'"
													  + " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
													  + "WHERE AccountFrom_ID IS NULL AND AccountValueFrom IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set AccountFrom from Value={}", no);

		// Error Invalid Account
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account, '"
										+ "WHERE (AccountFrom_ID IS NULL OR AccountFrom_ID=0)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Account={}", no);
		}
	}

	/**
	 * Set AccountTo from AccountValueTo and validate
	 */
	private void setAccountTo()
	{
		// Set AccountTo from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET AccountTo_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
													  + " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
													  + " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
													  + " WHERE ev.Value=i.AccountValueTo AND ev.IsSummary='N'"
													  + " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
													  + "WHERE AccountTo_ID IS NULL AND AccountValueTo IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set AccountTo from Value={}", no);

		// Error Invalid Account
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account, '"
										+ "WHERE (AccountTo_ID IS NULL OR AccountTo_ID=0)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Account={}", no);
		}
	}

	/**
	 * Set BPartner from BPartnerValue
	 */
	private void setBPartner()
	{
		// Set BPartner from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_BPartner_ID=(SELECT bp.C_BPartner_ID FROM C_BPartner bp"
													  + " WHERE bp.Value=i.BPartnerValue AND bp.IsSummary='N' AND i.AD_Client_ID=bp.AD_Client_ID) "
													  + "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set BPartner from Value={}", no);

		// Error Invalid BPartner
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner, '"
										+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid BPartner={}", no);
		}
	}

	/**
	 * Set Product from ProductValue, UPC, or SKU
	 */
	private void setProduct()
	{
		// Set Product from Value/UPC/SKU
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET M_Product_ID=(SELECT MAX(p.M_Product_ID) FROM M_Product p"
													  + " WHERE (p.Value=i.ProductValue OR p.UPC=i.UPC OR p.SKU=i.SKU)"
													  + " AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID) "
													  + "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Product from Value={}", no);

		// Error Invalid Product
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, '"
										+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Product={}", no);
		}
	}

	/**
	 * Set Activity from ActivityValue
	 */
	private void setActivity()
	{
		// Set Activity from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_Activity_ID=(SELECT p.C_Activity_ID FROM C_Activity p"
													  + " WHERE p.Value=i.ActivityValue AND i.AD_Client_ID=p.AD_Client_ID) "
													  + "WHERE C_Activity_ID IS NULL AND ActivityValue IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Activity from Value={}", no);

		// Error Invalid Activity
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Activity, '"
										+ "WHERE C_Activity_ID IS NULL AND ActivityValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Activity={}", no);
		}
	}

	/**
	 * Set Project from ProjectValue
	 */
	private void setProject()
	{
		// Set Project from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET C_Project_ID=(SELECT p.C_Project_ID FROM C_Project p"
													  + " WHERE p.Value=i.ProjectValue AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID) "
													  + "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set Project from Value={}", no);

		// Error Invalid Project
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Project, '"
										+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid Project={}", no);
		}
	}

	/**
	 * Set OrgTrx from OrgTrxValue
	 */
	private void setOrgTrx()
	{
		// Set OrgTrx from Value
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET AD_OrgTrx_ID=(SELECT o.AD_Org_ID FROM AD_Org o"
													  + " WHERE o.Value=coalesce(i.OrgTrxValue,i.OrgValue) AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID) "
													  + "WHERE AD_OrgTrx_ID IS NULL AND (OrgTrxValue IS NOT NULL OR OrgValue IS NOT NULL)"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set OrgTrx from Value={}", no);

		// Error Invalid OrgTrx
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid OrgTrx, '"
										+ "WHERE AD_OrgTrx_ID IS NULL AND OrgTrxValue IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid OrgTrx={}", no);
		}
	}

	/**
	 * Set Tax Account IDs (DR and CR) from TaxAccountValue columns
	 */
	private void setTaxAccounts()
	{
		// Set Tax Account DR from TaxAccountValueFrom
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET DR_Tax_Acct_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
													  + " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
													  + " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
													  + " WHERE ev.Value=i.TaxAccountValueFrom AND ev.IsSummary='N'"
													  + " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
													  + "WHERE DR_Tax_Acct_ID IS NULL AND TaxAccountValueFrom IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set DR_Tax_Acct_ID from Value={}", no);

		// Set Tax Account CR from TaxAccountValueTo
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET CR_Tax_Acct_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
										+ " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
										+ " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
										+ " WHERE ev.Value=i.TaxAccountValueTo AND ev.IsSummary='N'"
										+ " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
										+ "WHERE CR_Tax_Acct_ID IS NULL AND TaxAccountValueTo IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set CR_Tax_Acct_ID from Value={}", no);

		// Error Invalid Account
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account for TaxAccountValueFrom, '"
										+ "WHERE (DR_Tax_Acct_ID IS NULL OR DR_Tax_Acct_ID=0) AND TaxAccountValueFrom IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account for TaxAccountValueTo, '"
										+ "WHERE (CR_Tax_Acct_ID IS NULL OR CR_Tax_Acct_ID=0) AND TaxAccountValueTo IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

	}

	/**
	 * Set Tax IDs (DR and CR) from TaxName columns
	 */
	private void setTaxes()
	{
		// Set DR Tax from TaxName
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal i "
													  + "SET DR_Tax_ID=(SELECT t.C_Tax_ID FROM C_Tax t"
													  + " WHERE t.Name=i.DR_TaxName AND i.AD_Client_ID=t.AD_Client_ID) "
													  + "WHERE DR_Tax_ID IS NULL AND DR_TaxName IS NOT NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set DR_Tax from DR_TaxName={}", no);

		// Warning Invalid DR Tax
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Invalid DR Tax, '"
										+ "WHERE DR_Tax_ID IS NULL AND DR_TaxName IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid DR Tax={}", no);
		}

		// Set CR Tax from TaxName
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET CR_Tax_ID=(SELECT t.C_Tax_ID FROM C_Tax t"
										+ " WHERE t.Name=i.CR_TaxName AND i.AD_Client_ID=t.AD_Client_ID) "
										+ "WHERE CR_Tax_ID IS NULL AND CR_TaxName IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set CR_Tax from CR_TaxName={}", no);

		// Warning Invalid CR Tax
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Invalid CR Tax, '"
										+ "WHERE CR_Tax_ID IS NULL AND CR_TaxName IS NOT NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Invalid CR Tax={}", no);
		}
	}

	/**
	 * Set default Tax Amounts (DR and CR) to 0 if null
	 */
	private void setTaxAmounts()
	{
		// Set DR Tax Amount to 0
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal "
													  + "SET DR_TaxTotalAmt = 0 "
													  + "WHERE DR_TaxTotalAmt IS NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause());
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set 0 Tax Amount Dr={}", no);

		// Set CR Tax Amount to 0
		sql = new StringBuilder("UPDATE I_GLJournal "
										+ "SET CR_TaxTotalAmt = 0 "
										+ "WHERE CR_TaxTotalAmt IS NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set 0 Tax Amount Cr={}", no);
	}

	/**
	 * Set default Source Amounts (DR and CR) to 0 if null and warn on zero balance
	 */
	private void setSourceAmounts()
	{
		// Set Source DR to 0
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal "
													  + "SET AmtSourceDr = 0 "
													  + "WHERE AmtSourceDr IS NULL"
													  + " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause());
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set 0 Source Dr={}", no);

		// Set Source CR to 0
		sql = new StringBuilder("UPDATE I_GLJournal "
										+ "SET AmtSourceCr = 0 "
										+ "WHERE AmtSourceCr IS NULL"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Set 0 Source Cr={}", no);

		// Warning Zero Source Balance
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Source Balance, ' "
										+ "WHERE (AmtSourceDr-AmtSourceCr)=0"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Zero Source Balance={}", no);
		}
	}

	/**
	 * Calculate Accounted Amounts from Source Amounts and Currency Rate
	 */
	private void calculateAccountedAmounts()
	{
		// Calculate Accounted DR
		StringBuilder sql = new StringBuilder("UPDATE I_GLJournal "
													  + "SET AmtAcctDr = ROUND(AmtSourceDr * CurrencyRate, 2) "    // HARDCODED rounding
													  + "WHERE AmtAcctDr IS NULL OR AmtAcctDr=0"
													  + " AND I_IsImported='N'")
				.append(selection.toSqlWhereClause());
		int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Calculate Acct Dr={}", no);

		// Calculate Accounted CR
		sql = new StringBuilder("UPDATE I_GLJournal "
										+ "SET AmtAcctCr = ROUND(AmtSourceCr * CurrencyRate, 2) "
										+ "WHERE AmtAcctCr IS NULL OR AmtAcctCr=0"
										+ " AND I_IsImported='N'")
				.append(selection.toSqlWhereClause());
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Calculate Acct Cr={}", no);

		// Warning Zero Accounted Balance
		sql = new StringBuilder("UPDATE I_GLJournal i "
										+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Acct Balance, ' "
										+ "WHERE (AmtSourceDr-AmtSourceCr)<>0 AND (AmtAcctDr-AmtAcctCr)=0"
										+ " AND I_IsImported<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("Zero Acct Balance={}", no);
		}
	}

	/**
	 * make sure that at least one account is set
	 */
	private void checkAtLeastOneAccountSet()
	{
		final String sql = "UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=At least one account shall be set, '"
				+ " WHERE AccountValueTo IS NULL AND AccountValueFrom IS NULL"
				+ " AND I_IsImported<>'Y'"
				+ selection.toSqlWhereClause("i");
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_ThreadInherited);
		if (no != 0)
		{
			log.warn("At least one account shall be set={}", no);
		}
	}

}