/**
 * 
 */
package org.adempiere.impexp;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_I_GLJournal;
import org.compiere.model.MAccount;
import org.compiere.model.MJournal;
import org.compiere.model.MJournalBatch;
import org.compiere.model.MJournalLine;
import org.compiere.model.X_I_GLJournal;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 * Import {@link I_I_GLJournal} records to {@link I_GLJournal}.
 */
public class GLJournalImportProcess extends AbstractImportProcess<I_I_GLJournal>
{
	private int m_AD_Client_ID = -1;
	private int m_AD_Org_ID = -1;
	private int m_C_AcctSchema_ID = -1;
	private Timestamp m_DateAcct = null;

	private void getGLJournalImportProcessParameter()
	{
		m_AD_Client_ID = getParameters().getParameterAsInt("AD_Client_ID");
		m_AD_Org_ID = getParameters().getParameterAsInt("AD_Org_ID");
		m_C_AcctSchema_ID = getParameters().getParameterAsInt("C_AcctSchema_ID");
		m_DateAcct = getParameters().getParameterAsTimestamp("DateAcct");
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String whereClause = getWhereClause();

		// get process parameters
		getGLJournalImportProcessParameter();

		// Set IsActive, Created/Updated
		StringBuffer sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET IsActive = COALESCE (IsActive, 'Y'),"
				+ " Created = COALESCE (Created, now()),"
				+ " CreatedBy = COALESCE (CreatedBy, 0),"
				+ " Updated = COALESCE (Updated, now()),"
				+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
				+ " I_ErrorMsg = ' ',"
				+ " I_IsImported = 'N' "
				+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		int no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Reset=" + no);

		// Set Client from Name
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AD_Client_ID=(SELECT c.AD_Client_ID FROM AD_Client c WHERE c.Value=i.ClientValue) "
				+ "WHERE (AD_Client_ID IS NULL OR AD_Client_ID=0) AND ClientValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'");
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Client from Value=" + no);

		// Set Default Client, Doc Org, AcctSchema, DatAcct
		sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET AD_Client_ID = COALESCE (AD_Client_ID,").append(m_AD_Client_ID).append("),"
				+ " AD_OrgDoc_ID = COALESCE (AD_OrgDoc_ID,").append(m_AD_Org_ID).append("),");
		if (m_C_AcctSchema_ID != 0)
			sql.append(" C_AcctSchema_ID = COALESCE (C_AcctSchema_ID,").append(m_C_AcctSchema_ID).append("),");
		if (m_DateAcct != null)
			sql.append(" DateAcct = COALESCE (DateAcct,").append(DB.TO_DATE(m_DateAcct)).append("),");
		sql.append(" Updated = COALESCE (Updated, now()) "
				+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Client/DocOrg/Default=" + no);

		// Error Doc Org
		sql = new StringBuffer("UPDATE I_GLJournal o "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Doc Org, '"
				+ "WHERE (AD_OrgDoc_ID IS NULL OR AD_OrgDoc_ID=0"
				+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_OrgDoc_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Doc Org=" + no);

		// Set AcctSchema
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_AcctSchema_ID=(SELECT a.C_AcctSchema_ID FROM C_AcctSchema a"
				+ " WHERE i.AcctSchemaName=a.Name AND i.AD_Client_ID=a.AD_Client_ID) "
				+ "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set AcctSchema from Name=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_AcctSchema_ID=(SELECT c.C_AcctSchema1_ID FROM AD_ClientInfo c WHERE c.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_AcctSchema_ID IS NULL AND AcctSchemaName IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set AcctSchema from Client=" + no);
		// Error AcctSchema
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid AcctSchema, '"
				+ "WHERE (C_AcctSchema_ID IS NULL OR C_AcctSchema_ID=0"
				+ " OR NOT EXISTS (SELECT * FROM C_AcctSchema a WHERE i.AD_Client_ID=a.AD_Client_ID))"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid AcctSchema=" + no);

		// Set DateAcct (mandatory)
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET DateAcct=now() "
				+ "WHERE DateAcct IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set DateAcct=" + no);

		// Document Type
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_DocType_ID=(SELECT d.C_DocType_ID FROM C_DocType d"
				+ " WHERE d.Name=i.DocTypeName AND d.DocBaseType='GLJ' AND i.AD_Client_ID=d.AD_Client_ID) "
				+ "WHERE C_DocType_ID IS NULL AND DocTypeName IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set DocType=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid DocType, '"
				+ "WHERE (C_DocType_ID IS NULL OR C_DocType_ID=0"
				+ " OR NOT EXISTS (SELECT * FROM C_DocType d WHERE i.AD_Client_ID=d.AD_Client_ID AND d.DocBaseType='GLJ'))"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid DocType=" + no);

		// GL Category
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET GL_Category_ID=(SELECT c.GL_Category_ID FROM GL_Category c"
				+ " WHERE c.Name=i.CategoryName AND i.AD_Client_ID=c.AD_Client_ID) "
				+ "WHERE GL_Category_ID IS NULL AND CategoryName IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set DocType=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Category, '"
				+ "WHERE (GL_Category_ID IS NULL OR GL_Category_ID=0)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid GLCategory=" + no);

		// Set Currency
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_Currency_ID=(SELECT c.C_Currency_ID FROM C_Currency c"
				+ " WHERE c.ISO_Code=i.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Currency from ISO=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_Currency_ID=(SELECT a.C_Currency_ID FROM C_AcctSchema a"
				+ " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.AD_Client_ID=i.AD_Client_ID)"
				+ "WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Default Currency=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Currency, '"
				+ "WHERE (C_Currency_ID IS NULL OR C_Currency_ID=0)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Currency=" + no);

		// Set Conversion Type
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET ConversionTypeValue='S' "
				+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NULL"
				+ " AND I_IsImported='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set CurrencyType Value to Spot =" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_ConversionType_ID=(SELECT c.C_ConversionType_ID FROM C_ConversionType c"
				+ " WHERE c.Value=i.ConversionTypeValue AND c.AD_Client_ID IN (0,i.AD_Client_ID)) "
				+ "WHERE C_ConversionType_ID IS NULL AND ConversionTypeValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set CurrencyType from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid CurrencyType, '"
				+ "WHERE (C_ConversionType_ID IS NULL OR C_ConversionType_ID=0) AND ConversionTypeValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid CurrencyTypeValue=" + no);

		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No ConversionType, '"
				+ "WHERE (C_ConversionType_ID IS NULL OR C_ConversionType_ID=0)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("No CourrencyType=" + no);

		// Set/Overwrite Home Currency Rate
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET CurrencyRate=1"
				+ "WHERE EXISTS (SELECT * FROM C_AcctSchema a"
				+ " WHERE a.C_AcctSchema_ID=i.C_AcctSchema_ID AND a.C_Currency_ID=i.C_Currency_ID)"
				+ " AND C_Currency_ID IS NOT NULL AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Home CurrencyRate=" + no);
		// Set Currency Rate
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET CurrencyRate=(SELECT MAX(r.MultiplyRate) FROM C_Conversion_Rate r, C_AcctSchema s"
				+ " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
				+ " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
				+ " AND r.AD_Client_ID=i.AD_Client_ID AND r.AD_Org_ID=i.AD_OrgDoc_ID"
				+ " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
				+ " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo "
				// ORDER BY ValidFrom DESC
				+ ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Org Rate=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET CurrencyRate=(SELECT MAX(r.MultiplyRate) FROM C_Conversion_Rate r, C_AcctSchema s"
				+ " WHERE s.C_AcctSchema_ID=i.C_AcctSchema_ID AND s.AD_Client_ID=i.AD_Client_ID"
				+ " AND r.C_Currency_ID=i.C_Currency_ID AND r.C_Currency_ID_TO=s.C_Currency_ID"
				+ " AND r.AD_Client_ID=i.AD_Client_ID"
				+ " AND r.C_ConversionType_ID=i.C_ConversionType_ID"
				+ " AND i.DateAcct BETWEEN r.ValidFrom AND r.ValidTo "
				// ORDER BY ValidFrom DESC
				+ ") WHERE CurrencyRate IS NULL OR CurrencyRate=0 AND C_Currency_ID>0"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Client Rate=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Rate, '"
				+ "WHERE CurrencyRate IS NULL OR CurrencyRate=0"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("No Rate=" + no);

		// Set Period
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_Period_ID=(SELECT MAX(p.C_Period_ID) FROM C_Period p"
				+ " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
				+ " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID)"
				+ " WHERE c.AD_Client_ID=i.AD_Client_ID"
				// globalqss - cruiz - Bug [ 1577712 ] Financial Period Bug
				+ " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.IsActive='Y' AND p.PeriodType='S') "
				+ "WHERE C_Period_ID IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Period=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Period, '"
				+ "WHERE C_Period_ID IS NULL OR C_Period_ID NOT IN"
				+ "(SELECT C_Period_ID FROM C_Period p"
				+ " INNER JOIN C_Year y ON (y.C_Year_ID=p.C_Year_ID)"
				+ " INNER JOIN AD_ClientInfo c ON (c.C_Calendar_ID=y.C_Calendar_ID) "
				+ " WHERE c.AD_Client_ID=i.AD_Client_ID"
				// globalqss - cruiz - Bug [ 1577712 ] Financial Period Bug
				+ " AND i.DateAcct BETWEEN p.StartDate AND p.EndDate AND p.IsActive='Y' AND p.PeriodType='S')"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Period=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Period Closed, ' "
				+ "WHERE C_Period_ID IS NOT NULL AND NOT EXISTS"
				+ " (SELECT * FROM C_PeriodControl pc WHERE pc.C_Period_ID=i.C_Period_ID AND DocBaseType='GLJ' AND PeriodStatus='O') "
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Period Closed=" + no);

		// Posting Type
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET PostingType='A' "
				+ "WHERE PostingType IS NULL AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Actual PostingType=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid PostingType, ' "
				+ "WHERE PostingType IS NULL OR NOT EXISTS"
				+ " (SELECT * FROM AD_Ref_List r WHERE r.AD_Reference_ID=125 AND i.PostingType=r.Value)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid PostingTypee=" + no);

		// Set Org from Name (* is overwritten and default)
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AD_Org_ID=COALESCE((SELECT o.AD_Org_ID FROM AD_Org o"
				+ " WHERE o.Value=i.OrgValue AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID),AD_Org_ID) "
				+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'");
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Org from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AD_Org_ID=AD_OrgDoc_ID "
				+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0) AND OrgValue IS NULL AND AD_OrgDoc_ID IS NOT NULL AND AD_OrgDoc_ID<>0"
				+ "  AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Org from Doc Org=" + no);
		// Error Org
		sql = new StringBuffer("UPDATE I_GLJournal o "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Org, '"
				+ "WHERE (AD_Org_ID IS NULL OR AD_Org_ID=0"
				+ " OR EXISTS (SELECT * FROM AD_Org oo WHERE o.AD_Org_ID=oo.AD_Org_ID AND (oo.IsSummary='Y' OR oo.IsActive='N')))"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Org=" + no);

		// Set AccountFrom
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AccountFrom_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
				+ " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
				+ " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
				+ " WHERE ev.Value=i.AccountValueFrom AND ev.IsSummary='N'"
				+ " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
				+ "WHERE AccountFrom_ID IS NULL AND AccountValueFrom IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set AccountFrom from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account, '"
				+ "WHERE (AccountFrom_ID IS NULL OR AccountFrom_ID=0)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Account=" + no);

		// Set AccountTo
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AccountTo_ID=(SELECT MAX(ev.C_ElementValue_ID) FROM C_ElementValue ev"
				+ " INNER JOIN C_Element e ON (e.C_Element_ID=ev.C_Element_ID)"
				+ " INNER JOIN C_AcctSchema_Element ase ON (e.C_Element_ID=ase.C_Element_ID AND ase.ElementType='AC')"
				+ " WHERE ev.Value=i.AccountValueTo AND ev.IsSummary='N'"
				+ " AND i.C_AcctSchema_ID=ase.C_AcctSchema_ID AND i.AD_Client_ID=ev.AD_Client_ID) "
				+ "WHERE AccountTo_ID IS NULL AND AccountValueTo IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set AccountTo from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Account, '"
				+ "WHERE (AccountTo_ID IS NULL OR AccountTo_ID=0)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Account=" + no);

		// Set BPartner
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_BPartner_ID=(SELECT bp.C_BPartner_ID FROM C_BPartner bp"
				+ " WHERE bp.Value=i.BPartnerValue AND bp.IsSummary='N' AND i.AD_Client_ID=bp.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set BPartner from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner, '"
				+ "WHERE C_BPartner_ID IS NULL AND BPartnerValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid BPartner=" + no);

		// Set Product
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET M_Product_ID=(SELECT MAX(p.M_Product_ID) FROM M_Product p"
				+ " WHERE (p.Value=i.ProductValue OR p.UPC=i.UPC OR p.SKU=i.SKU)"
				+ " AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Product from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Product, '"
				+ "WHERE M_Product_ID IS NULL AND (ProductValue IS NOT NULL OR UPC IS NOT NULL OR SKU IS NOT NULL)"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Product=" + no);

		// Set Project
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET C_Project_ID=(SELECT p.C_Project_ID FROM C_Project p"
				+ " WHERE p.Value=i.ProjectValue AND p.IsSummary='N' AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Project from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid Project, '"
				+ "WHERE C_Project_ID IS NULL AND ProjectValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid Project=" + no);

		// Set TrxOrg
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET AD_OrgTrx_ID=(SELECT o.AD_Org_ID FROM AD_Org o"
				+ " WHERE o.Value=i.OrgValue AND o.IsSummary='N' AND i.AD_Client_ID=o.AD_Client_ID) "
				+ "WHERE AD_OrgTrx_ID IS NULL AND OrgTrxValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set OrgTrx from Value=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid OrgTrx, '"
				+ "WHERE AD_OrgTrx_ID IS NULL AND OrgTrxValue IS NOT NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Invalid OrgTrx=" + no);

		// Source Amounts
		sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET AmtSourceDr = 0 "
				+ "WHERE AmtSourceDr IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set 0 Source Dr=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET AmtSourceCr = 0 "
				+ "WHERE AmtSourceCr IS NULL"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set 0 Source Cr=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Source Balance, ' "
				+ "WHERE (AmtSourceDr-AmtSourceCr)=0"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
			log.warn("Zero Source Balance=" + no);

		// Accounted Amounts (Only if No Error)
		sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET AmtAcctDr = ROUND(AmtSourceDr * CurrencyRate, 2) "	// HARDCODED rounding
				+ "WHERE AmtAcctDr IS NULL OR AmtAcctDr=0"
				+ " AND I_IsImported='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Calculate Acct Dr=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal "
				+ "SET AmtAcctCr = ROUND(AmtSourceCr * CurrencyRate, 2) "
				+ "WHERE AmtAcctCr IS NULL OR AmtAcctCr=0"
				+ " AND I_IsImported='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Calculate Acct Cr=" + no);
		sql = new StringBuffer("UPDATE I_GLJournal i "
				+ "SET I_ErrorMsg=I_ErrorMsg||'WARN=Zero Acct Balance, ' "
				+ "WHERE (AmtSourceDr-AmtSourceCr)<>0 AND (AmtAcctDr-AmtAcctCr)=0"
				+ " AND I_IsImported<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		if (no != 0)
		{
			log.warn("Zero Acct Balance=" + no);
		}

		checkBalance();
	}

	private void checkBalance()
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String whereClause = getWhereClause();

		// Get Balance
		StringBuffer sql = new StringBuffer("SELECT SUM(AmtSourceDr)-SUM(AmtSourceCr), SUM(AmtAcctDr)-SUM(AmtAcctCr) "
				+ "FROM I_GLJournal "
				+ "WHERE I_IsImported='N'").append(whereClause);
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				BigDecimal source = rs.getBigDecimal(1);
				BigDecimal acct = rs.getBigDecimal(2);
				if (source != null && source.signum() == 0
						&& acct != null && acct.signum() == 0)
				{
					log.info("Import Balance = 0");
				}
				else
				{
					log.warn("Balance Source=" + source + ", Acct=" + acct);
				}
				if (source != null)
				{
					getLoggable().addLog("@AmtSourceDr@ - @AmtSourceCr@");
				}
				if (acct != null)
				{
					getLoggable().addLog("@AmtAcctDr@ - @AmtAcctCr@");
				}
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.error(sql.toString(), ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
	}

	public static final class GLJournalImportContext
	{
		MJournal journal = null;
		MJournalBatch batch = null;
		String BatchDocumentNo = "";
		String JournalDocumentNo = "";
		Timestamp DateAcct = null;
	}

	@Override
	protected org.adempiere.impexp.AbstractImportProcess.ImportRecordResult importRecord(IMutable<Object> state, I_I_GLJournal importRecord) throws Exception
	{

		GLJournalImportContext context = (GLJournalImportContext)state.getValue();
		if (context == null)
		{
			context = new GLJournalImportContext();
			state.setValue(context);
		}

		log.debug("I_GLJournal_ID=" + importRecord.getI_GLJournal_ID()
				+ ", GL_JournalBatch_ID=" + importRecord.getGL_JournalBatch_ID()
				+ ", GL_Journal_ID=" + importRecord.getGL_Journal_ID()
				+ ", Line=" + importRecord.getLine());

		final Properties ctx = InterfaceWrapperHelper.getCtx(importRecord);
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		boolean wasInsert = false;

		// New Batch if Batch Document No changes
		String impBatchDocumentNo = importRecord.getBatchDocumentNo();
		if (impBatchDocumentNo == null)
			impBatchDocumentNo = "";
		if (context.batch == null
				|| importRecord.isCreateNewBatch()
				|| context.journal.getC_AcctSchema_ID() != importRecord.getC_AcctSchema_ID()
				|| !context.BatchDocumentNo.equals(impBatchDocumentNo))
		{
			context.BatchDocumentNo = impBatchDocumentNo;	// cannot compare real DocumentNo
			context.batch = new MJournalBatch(ctx, 0, null);
			context.batch.setClientOrg(importRecord.getAD_Client_ID(), importRecord.getAD_OrgDoc_ID());
			if (importRecord.getBatchDocumentNo() != null && importRecord.getBatchDocumentNo().length() > 0)
			{
				context.batch.setDocumentNo(importRecord.getBatchDocumentNo());
			}
			context.batch.setDateAcct(m_DateAcct);
			context.batch.setDateDoc(m_DateAcct);
			context.batch.setC_DocType_ID(importRecord.getC_DocType_ID());
			context.batch.setPostingType(importRecord.getPostingType());
			String description = importRecord.getBatchDescription();
			if (description == null || description.length() == 0)
			{
				description = "*Import-";
			}
			else
			{
				description += " *Import-";
			}
			description += new Timestamp(System.currentTimeMillis());
			context.batch.setDescription(description);
			try
			{
				InterfaceWrapperHelper.save(context.batch);
			}
			catch (Exception ex)
			{
				getLoggable().addLog(ex.getLocalizedMessage());
			}

			context.journal = null;
		}
		// Journal
		String impJournalDocumentNo = importRecord.getJournalDocumentNo();
		if (impJournalDocumentNo == null)
			impJournalDocumentNo = "";
		Timestamp impDateAcct = TimeUtil.getDay(importRecord.getDateAcct());
		if (context.journal == null
				|| importRecord.isCreateNewJournal()
				|| !context.JournalDocumentNo.equals(impJournalDocumentNo)
				|| context.journal.getC_DocType_ID() != importRecord.getC_DocType_ID()
				|| context.journal.getGL_Category_ID() != importRecord.getGL_Category_ID()
				|| !context.journal.getPostingType().equals(importRecord.getPostingType())
				|| context.journal.getC_Currency_ID() != importRecord.getC_Currency_ID()
				|| !impDateAcct.equals(context.DateAcct))
		{
			context.JournalDocumentNo = impJournalDocumentNo;	// cannot compare real DocumentNo
			context.DateAcct = impDateAcct;
			context.journal = new MJournal(ctx, 0, trxName);
			context.journal.setGL_JournalBatch_ID(context.batch.getGL_JournalBatch_ID());
			context.journal.setClientOrg(importRecord.getAD_Client_ID(), importRecord.getAD_OrgDoc_ID());
			//
			String description = importRecord.getBatchDescription();
			if (description == null || description.length() == 0)
				description = "(Import)";
			context.journal.setDescription(description);
			if (importRecord.getJournalDocumentNo() != null && importRecord.getJournalDocumentNo().length() > 0)
				context.journal.setDocumentNo(importRecord.getJournalDocumentNo());
			//
			context.journal.setC_AcctSchema_ID(importRecord.getC_AcctSchema_ID());
			context.journal.setC_DocType_ID(importRecord.getC_DocType_ID());
			context.journal.setGL_Category_ID(importRecord.getGL_Category_ID());
			context.journal.setPostingType(importRecord.getPostingType());
			context.journal.setGL_Budget_ID(importRecord.getGL_Budget_ID());
			//

			//
			context.journal.setCurrency(importRecord.getC_Currency_ID(), importRecord.getC_ConversionType_ID(), importRecord.getCurrencyRate());
			//
			context.journal.setDateAcct(importRecord.getDateAcct());		// sets Period if not defined
			context.journal.setDateDoc(importRecord.getDateAcct());
			//
			try
			{
				InterfaceWrapperHelper.save(context.journal);
			}
			catch (Exception ex)
			{
				getLoggable().addLog(ex.getLocalizedMessage());
				throw AdempiereException.wrapIfNeeded(ex);
			}
		}

		// Lines
		MJournalLine line = new MJournalLine(context.journal);
		//
		line.setDescription(importRecord.getDescription());
		line.setCurrency(importRecord.getC_Currency_ID(), importRecord.getC_ConversionType_ID(), importRecord.getCurrencyRate());
		
		//
		line.setLine(importRecord.getLine());
		line.setAmtSourceCr(importRecord.getAmtSourceCr());
		line.setAmtSourceDr(importRecord.getAmtSourceDr());
		line.setAmtAcct(importRecord.getAmtAcctDr(), importRecord.getAmtAcctCr());	// only if not 0
		line.setDateAcct(importRecord.getDateAcct());
		//
		// Set/Get Account Combination
		if (importRecord.getC_ValidCombinationFrom_ID() == 0)
		{
			final IAccountDimension acctDim = newAccountDimension(importRecord, importRecord.getAccountFrom_ID());
			final MAccount acct = MAccount.get(getCtx(), acctDim);
			if (acct != null && acct.get_ID() == 0)
				acct.save();
			if (acct == null || acct.get_ID() == 0)
			{
				importRecord.setI_ErrorMsg("ERROR creating Account");
				importRecord.setI_IsImported(false);
				InterfaceWrapperHelper.save(importRecord);
			}
			else
			{
				importRecord.setC_ValidCombinationFrom_ID(acct.get_ID());
			}
		}

		// Set/Get Account Combination
		if (importRecord.getC_ValidCombinationTo_ID() == 0)
		{
			final IAccountDimension acctDim = newAccountDimension(importRecord, importRecord.getAccountTo_ID());
			final MAccount acct = MAccount.get(getCtx(), acctDim);
			if (acct != null && acct.get_ID() == 0)
				acct.save();
			if (acct == null || acct.get_ID() == 0)
			{
				importRecord.setI_ErrorMsg("ERROR creating Account");
				importRecord.setI_IsImported(false);
				InterfaceWrapperHelper.save(importRecord);
			}
			else
			{
				importRecord.setC_ValidCombinationTo_ID(acct.get_ID());
			}
		}
		//
		line.setAccount_DR_ID(importRecord.getC_ValidCombinationFrom_ID());
		line.setAccount_CR_ID(importRecord.getC_ValidCombinationTo_ID());
		//
		line.setC_UOM_ID(importRecord.getC_UOM_ID());
		line.setQty(importRecord.getQty());
		//
		
		if (line.save())
		{
			importRecord.setGL_JournalBatch_ID(context.batch.getGL_JournalBatch_ID());
			importRecord.setGL_Journal_ID(context.journal.getGL_Journal_ID());
			importRecord.setGL_JournalLine_ID(line.getGL_JournalLine_ID());
			
			importRecord.setI_IsImported(true);
			importRecord.setProcessed(true);
			importRecord.setProcessing(false);
			InterfaceWrapperHelper.save(importRecord);
			wasInsert = true;
			
		}

		return wasInsert ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}
	
	private IAccountDimension newAccountDimension(final I_I_GLJournal importRecord, final int accountId)
	{
		return AccountDimension.builder()
				.setC_AcctSchema_ID(importRecord.getC_AcctSchema_ID())
				.setAD_Client_ID(importRecord.getAD_Client_ID())
				.setAD_Org_ID(importRecord.getAD_Org_ID())
				.setC_ElementValue_ID(accountId)
				//.setC_SubAcct_ID(importRecord.getC_SubAcct_ID())
				.setM_Product_ID(importRecord.getM_Product_ID())
				.setC_BPartner_ID(importRecord.getC_BPartner_ID())
				.setAD_OrgTrx_ID(importRecord.getAD_OrgTrx_ID())
				.setC_LocFrom_ID(importRecord.getC_LocFrom_ID())
				.setC_LocTo_ID(importRecord.getC_LocTo_ID())
				.setC_SalesRegion_ID(importRecord.getC_SalesRegion_ID())
				.setC_Project_ID(importRecord.getC_Project_ID())
				.setC_Campaign_ID(importRecord.getC_Campaign_ID())
				.setC_Activity_ID(importRecord.getC_Activity_ID())
				.setUser1_ID(importRecord.getUser1_ID())
				.setUser2_ID(importRecord.getUser2_ID())
				//.setUserElement1_ID(importRecord.getUserElement1_ID())
				//.setUserElement2_ID(importRecord.getUserElement2_ID())
				.build();
	}

	@Override
	public Class<I_I_GLJournal> getImportModelClass()
	{
		return I_I_GLJournal.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_GLJournal.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_GL_Journal.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return "COALESCE(BatchDocumentNo, I_GLJournal_ID ||' '), COALESCE(JournalDocumentNo, " +
				"I_GLJournal_ID ||' '), C_AcctSchema_ID, PostingType, C_DocType_ID, GL_Category_ID, " +
				"C_Currency_ID, TRUNC(DateAcct), Line, I_GLJournal_ID";
	}

	@Override
	protected I_I_GLJournal retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_GLJournal(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}
}
