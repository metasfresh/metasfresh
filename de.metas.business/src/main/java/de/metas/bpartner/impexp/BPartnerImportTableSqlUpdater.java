package de.metas.bpartner.impexp;

import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_ErrorMsg;
import static org.adempiere.impexp.AbstractImportProcess.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_Location;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * A helper class for {@link BPartnerImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_BPartner} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class BPartnerImportTableSqlUpdater
{
	private static final transient Logger logger = LogManager.getLogger(BPartnerImportTableSqlUpdater.class);

	public void updateBPartnerImportTable(@NonNull final String whereClause)
	{
		dbUpdateOrgs(whereClause);

		dbUpdateBPGroups(whereClause);

		dbUpdateCountries(whereClause);

		dpUpdateRegions(whereClause);

		dbUpdateGreetings(whereClause);

		dbUpdateJobs(whereClause);

		dbUpdateCbPartnerIdsFromC_BPartner_ExternalId(whereClause);
		dbUpdateCbPartnerIdsFromValue(whereClause);

		dbUpdateAdUserIdsFromAD_User_ExternalIds(whereClause);
		dbUpdateAdUserIdsFromExisting(whereClause);
		dbUpdateAdUserIdsFromContactNames(whereClause);

		dbUpdateCBPartnerLocationsFromC_BPartner_Location_ExternalIds(whereClause);
		dbUpdateCBPartnerLocationsFromGLN(whereClause);

		dbUpdateLocations(whereClause);

		dbUpdateInterestAreas(whereClause);

		dbUpdateInvoiceSchedules(whereClause);

		dbUpdatePO_PaymentTerms(whereClause);

		dbUpdateC_Aggregtions(whereClause);

		dbUpdateM_Shippers(whereClause);

		dbUpdateAD_PrintFormats(whereClause);

		dbUpdateM_PricingSystems(whereClause);

		dbUpdatePO_PricingSystems(whereClause);

	}


	private void dbUpdateOrgs(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET AD_Org_ID=(SELECT AD_Org_ID FROM AD_Org o"
				+ " WHERE i.OrgValue=o.Value AND o.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Org ={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Greeting, ' "
				+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Org={}", no);
	}

	private void dbUpdateBPGroups(final String whereClause)
	{
		StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET GroupValue=(SELECT MAX(Value) FROM C_BP_Group g WHERE g.IsDefault='Y'"
				+ " AND g.AD_Client_ID=i.AD_Client_ID) ");
		sql.append("WHERE GroupValue IS NULL AND C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		int no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Group Default={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_BP_Group_ID=(SELECT C_BP_Group_ID FROM C_BP_Group g"
				+ " WHERE i.GroupValue=g.Value AND g.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Group={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Group, ' "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Group={}", no);
	}

	private void dbUpdateCountries(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
				+ " WHERE ("
				+ " (i.CountryCode=c.CountryCode AND c.AD_Client_ID IN (0, i.AD_Client_ID))"
				+ " OR "
				+ " (i.CountryName=c.Name AND c.AD_Client_ID IN (0, i.AD_Client_ID))"
				+ " )) "
				+ "WHERE C_Country_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Country={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Country, ' "
				+ "WHERE C_Country_ID IS NULL AND (City IS NOT NULL OR Address1 IS NOT NULL)"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Country={}", no);
	}

	private void dpUpdateRegions(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "Set RegionName=(SELECT MAX(Name) FROM C_Region r"
				+ " WHERE r.IsDefault='Y' AND r.C_Country_ID=i.C_Country_ID"
				+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) ");
		sql.append("WHERE RegionName IS NULL AND C_Region_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Region Default={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r"
				+ " WHERE r.Name=i.RegionName AND r.C_Country_ID=i.C_Country_ID"
				+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_Region_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Region={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Region, ' "
				+ "WHERE C_Region_ID IS NULL "
				+ " AND EXISTS (SELECT 1 FROM C_Country c WHERE c.C_Country_ID=i.C_Country_ID AND c.HasRegion='Y')"
				+ " AND RegionName IS NOT NULL" // tolerate no region
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
						.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Region={}", no);
	}

	private void dbUpdateGreetings(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_Greeting_ID=(SELECT C_Greeting_ID FROM C_Greeting g"
				+ " WHERE i.BPContactGreeting=g.Name AND g.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Greeting={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Greeting, ' "
				+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Greeting={}", no);
	}

	private void dbUpdateJobs(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_Job_ID=(SELECT C_Job_ID FROM C_Job j"
				+ " WHERE i.JobName=j.Name AND j.AD_Client_ID IN (0, i.AD_Client_ID) AND j.AD_Org_ID IN (0, i.AD_Org_ID) ) "
				+ "WHERE C_Job_ID IS NULL AND JobName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Job={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Job, ' "
				+ "WHERE C_Job_ID IS NULL AND JobName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Job={}", no);
	}

	private void dbUpdateAdUserIdsFromExisting(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET (C_BPartner_ID,AD_User_ID)="
				+ "(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u "
				+ "WHERE i.EMail=u.EMail AND u.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE i.EMail IS NOT NULL AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(DB.convertSqlToNative(sql.toString()), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found EMail User={}", no);
	}

	private void dbUpdateCbPartnerIdsFromValue(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE i."
				+ I_I_BPartner.COLUMNNAME_BPValue
				+ "=p."
				+ I_C_BPartner.COLUMNNAME_Value
				+ " AND p.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_BPValue
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found BPartner={}", no);
	}

	private void dbUpdateCbPartnerIdsFromC_BPartner_ExternalId(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i "
				+ "SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=(SELECT "
				+ I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM "
				+ I_C_BPartner.Table_Name
				+ " p WHERE i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ExternalId
				+ "=p."
				+ I_C_BPartner.COLUMNNAME_ExternalId
				+ " AND p."
				+ I_C_BPartner.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ExternalId
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found BPartner={}", no);
	}

	private void dbUpdateAdUserIdsFromContactNames(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_AD_User_ID
				+ "=(SELECT "
				+ I_AD_User.COLUMNNAME_AD_User_ID
				+ " FROM "
				+ I_AD_User.Table_Name
				+ " c WHERE i."
				+ I_I_BPartner.COLUMNNAME_ContactName
				+ "=c."
				+ I_AD_User.COLUMNNAME_Name
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=c."
				+ I_AD_User.COLUMNNAME_C_BPartner_ID
				+ " AND c."
				+ I_AD_User.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NOT NULL AND "
				+ I_I_BPartner.COLUMNNAME_AD_User_ID
				+ " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_ContactName
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Contact={}", no);
	}

	private void dbUpdateAdUserIdsFromAD_User_ExternalIds(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_AD_User_ID
				+ "=(SELECT "
				+ I_AD_User.COLUMNNAME_AD_User_ID
				+ " FROM "
				+ I_AD_User.Table_Name
				+ " c WHERE i."
				+ I_I_BPartner.COLUMNNAME_AD_User_ExternalId
				+ "=c."
				+ I_AD_User.COLUMNNAME_ExternalId
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=c."
				+ I_AD_User.COLUMNNAME_C_BPartner_ID
				+ " AND c."
				+ I_AD_User.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NOT NULL AND "
				+ I_I_BPartner.COLUMNNAME_AD_User_ID
				+ " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_AD_User_ExternalId
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Contact={}", no);
	}

	private void dbUpdateCBPartnerLocationsFromC_BPartner_Location_ExternalIds(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ "=(SELECT "
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ " FROM "
				+ I_C_BPartner_Location.Table_Name
				+ " bpl  WHERE i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ExternalId
				+ "=bpl."
				+ I_C_BPartner_Location.COLUMNNAME_ExternalId
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=bpl."
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ " AND bpl."
				+ I_C_BPartner_Location.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NOT NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ExternalId
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Contact={}", no);
	}


	private void dbUpdateCBPartnerLocationsFromGLN(String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ "=(SELECT "
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ " FROM "
				+ I_C_BPartner_Location.Table_Name
				+ " bpl  WHERE i."
				+ I_I_BPartner.COLUMNNAME_GLN
				+ "=bpl."
				+ I_C_BPartner_Location.COLUMNNAME_GLN
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=bpl."
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ " AND bpl."
				+ I_C_BPartner_Location.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NOT NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_GLN
				+ " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Contact={}", no);

	}

	private void dbUpdateLocations(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ "=(SELECT "
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ " FROM "
				+ I_C_BPartner_Location.Table_Name
				+ " bpl INNER JOIN "
				+ I_C_Location.Table_Name
				+ " l ON (bpl."
				+ I_C_BPartner_Location.COLUMNNAME_C_Location_ID
				+ "=l."
				+ I_C_Location.COLUMNNAME_C_Location_ID
				+ ") WHERE i."
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ "=bpl."
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ " AND bpl."
				+ I_C_BPartner_Location.COLUMNNAME_AD_Client_ID
				+ "=i."
				+ I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_Address1
				+ "=l."
				+ I_C_Location.COLUMNNAME_Address1
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_Address2
				+ "=l."
				+ I_C_Location.COLUMNNAME_Address2
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_City
				+ "=l."
				+ I_C_Location.COLUMNNAME_City
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_Postal
				+ "=l."
				+ I_C_Location.COLUMNNAME_Postal
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_Postal_Add
				+ "=l."
				+ I_C_Location.COLUMNNAME_Postal_Add
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_Region_ID
				+ "=l."
				+ I_C_Location.COLUMNNAME_C_Region_ID
				+ " AND i."
				+ I_I_BPartner.COLUMNNAME_C_Country_ID
				+ "=l."
				+ I_C_Location.COLUMNNAME_C_Country_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID
				+ " IS NOT NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID
				+ " IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Found Location={}", no);
	}

	private void dbUpdateInterestAreas(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET R_InterestArea_ID=(SELECT R_InterestArea_ID FROM R_InterestArea ia "
				+ "WHERE i.InterestAreaName=ia.Name AND ia.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE R_InterestArea_ID IS NULL AND InterestAreaName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Interest Area={}", no);
	}

	private void dbUpdateInvoiceSchedules(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_InvoiceSchedule_ID=(SELECT C_InvoiceSchedule_ID FROM C_InvoiceSchedule invSched"
				+ " WHERE i.InvoiceSchedule=invSched.Name AND invSched.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_InvoiceSchedule_ID IS NULL AND InvoiceSchedule IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set InvoiceSchedule={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid InvoiceSchedule, ' "
				+ "WHERE C_InvoiceSchedule_ID IS NULL AND InvoiceSchedule IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid InvoiceSchedule={}", no);
	}

	private void dbUpdatePO_PaymentTerms(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET PO_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm pt"
				+ " WHERE i.PaymentTerm=pt.Name AND pt.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE PO_PaymentTerm_ID IS NULL AND PaymentTerm IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set PO_PaymentTerm={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid PO_PaymentTerm, ' "
				+ "WHERE PO_PaymentTerm_ID IS NULL AND PaymentTerm IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid PO_PaymentTerm={}", no);
	}

	private void dbUpdateC_Aggregtions(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET C_Aggregation_ID=(SELECT C_Aggregation_ID FROM C_Aggregation a"
				+ " WHERE i.AggregationName=a.Name AND a.AD_Client_ID IN (0, i.AD_Client_ID) AND a.AD_Org_ID IN (0, i.AD_Org_ID ) )"
				+ "WHERE C_Aggregation_ID IS NULL AND AggregationName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set C_Aggregation_ID={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid AggregationName, ' "
				+ "WHERE C_Aggregation_ID IS NULL AND AggregationName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid C_Aggregation_ID={}", no);
	}

	private void dbUpdateM_Shippers(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET M_Shipper_ID=(SELECT M_Shipper_ID FROM M_Shipper s"
				+ " WHERE i.ShipperName=s.Name AND s.AD_Client_ID IN (0, i.AD_Client_ID)), "
				+ " DeliveryViaRule = 'S' "
				+ "WHERE M_Shipper_ID IS NULL AND ShipperName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set M_Shipper_ID={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET DeliveryViaRule = 'P' "
				+ "WHERE M_Shipper_ID IS NULL AND ShipperName = 'P' "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set DeliveryViaRule={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Shipper or DeliveryViaRule, ' "
				+ "WHERE M_Shipper_ID IS NULL AND DeliveryViaRule IS NULL AND ShipperName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid Invalid Shipper or DeliveryViaRule={}", no);
	}

	private void dbUpdateAD_PrintFormats(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET AD_PrintFormat_ID=(SELECT AD_PrintFormat_ID FROM AD_PrintFormat pf"
				+ " WHERE i.PrintFormat_Name=pf.Name AND pf.AD_Client_ID IN (0, i.AD_Client_ID) AND pf.AD_Org_ID IN (0, i.AD_Org_ID ) ) "
				+ "WHERE AD_PrintFormat_ID IS NULL AND PrintFormat_Name IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set AD_PrintFormat_ID={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid PrintFormat_Name, ' "
				+ "WHERE AD_PrintFormat_ID IS NULL AND PrintFormat_Name IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid AD_PrintFormat_ID={}", no);
	}


	private void dbUpdateM_PricingSystems(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET M_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_PricingSystem ps"
				+ " WHERE i.PricingSystem_Value=ps.value AND ps.AD_Client_ID IN (0, i.AD_Client_ID) and ps.IsActive='Y' ) "
				+ "WHERE M_PricingSystem_ID IS NULL AND PricingSystem_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set M_PricingSystem_ID={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid M_PricingSystem_ID, ' "
				+ "WHERE M_PricingSystem_ID IS NULL AND PricingSystem_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid M_PricingSystem={}", no);
	}

	private void dbUpdatePO_PricingSystems(final String whereClause)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET M_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_PricingSystem ps"
				+ " WHERE i.PO_PricingSystem_Value=ps.value AND ps.AD_Client_ID IN (0, i.AD_Client_ID) and IsActive='Y') "
				+ "WHERE M_PricingSystem_ID IS NULL AND PO_PricingSystem_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set M_PricingSystem_ID={}", no);
		//
		sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid M_PricingSystem_ID, ' "
				+ "WHERE M_PricingSystem_ID IS NULL AND PO_PricingSystem_Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Invalid M_PricingSystem={}", no);
	}
}
