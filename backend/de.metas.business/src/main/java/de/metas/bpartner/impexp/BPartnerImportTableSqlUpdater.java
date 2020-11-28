package de.metas.bpartner.impexp;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryViaRule;
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

	public void updateBPartnerImportTable(@NonNull final ImportRecordsSelection selection)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		dbUpdateOrgs(selection);

		dbUpdateBPGroups(selection);

		dbUpdateCountries(selection);

		dpUpdateRegions(selection);

		dbUpdateGreetings(selection);

		dbUpdateJobs(selection);

		dbUpdateCbPartnerIdsFromC_BPartner_ExternalId(selection);
		dbUpdateCbPartnerIdsFromC_BPartner_GlobalId(selection);
		dbUpdateCbPartnerIdsFromValue(selection);

		updateNames(selection);

		dbUpdateAdUserIdsFromAD_User_ExternalIds(selection);
		dbUpdateAdUserIdsFromExisting(selection);
		dbUpdateAdUserIdsFromContactNames(selection);

		dbUpdateCBPartnerLocationsFromC_BPartner_Location_ExternalIds(selection);
		dbUpdateCBPartnerLocationsFromGLN(selection);

		dbUpdateLocations(selection);

		dbUpdateInterestAreas(selection);

		dbUpdateInvoiceSchedules(selection);

		dbUpdatePaymentTerms(selection);

		dbUpdatePO_PaymentTerms(selection);

		dbUpdateC_Aggregtions(selection);

		dbUpdateM_Shippers(selection);

		dbUpdateAD_PrintFormats(selection);

		dbUpdateM_PricingSystems(selection);

		dbUpdatePO_PricingSystems(selection);

		stopwatch.stop();
		logger.info("Took {} to update I_BPartner records ({})", stopwatch, selection);
	}

	private final void executeUpdate(@NonNull final String description, @NonNull final CharSequence sql)
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final int no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		stopwatch.stop();

		logger.info("{}: Updated {} records in {}", description, no, stopwatch);
	}

	private void dbUpdateOrgs(final ImportRecordsSelection selection)
	{
		{
			final String sql = "UPDATE I_BPartner i "
					+ "SET AD_Org_ID=(SELECT AD_Org_ID FROM AD_Org o"
					+ " WHERE i.OrgValue=o.Value AND o.AD_Client_ID IN (0, i.AD_Client_ID)) "
					+ "WHERE OrgValue IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Set Org", sql);
		}

		//
		{
			final String sql = "UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Org, ' "
					+ "WHERE AD_Org_ID IS NULL AND OrgValue IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Flag records with invalid Org", sql);
		}
	}

	private void dbUpdateBPGroups(final ImportRecordsSelection selection)
	{
		{
			final String sql = "UPDATE I_BPartner i "
					+ "SET GroupValue=(SELECT MAX(Value) FROM C_BP_Group g WHERE g.IsDefault='Y' AND g.AD_Client_ID=i.AD_Client_ID AND g.AD_Org_ID=i.AD_Org_ID) "
					+ " WHERE GroupValue IS NULL AND C_BP_Group_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Set Default BP Group", sql);
		}

		//
		{
			final String sql = "UPDATE I_BPartner i "
					+ "SET C_BP_Group_ID=(SELECT C_BP_Group_ID FROM C_BP_Group g"
					+ " WHERE i.GroupValue=g.Value AND g.AD_Client_ID=i.AD_Client_ID AND g.AD_Org_ID=i.AD_Org_ID) "
					+ "WHERE C_BP_Group_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Set BP Group", sql);
		}

		//
		{
			final String sql = "UPDATE I_BPartner "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Group, ' "
					+ "WHERE C_BP_Group_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause();

			executeUpdate("Flag records with invalid BP Group", sql);
		}
	}

	private void dbUpdateCountries(final ImportRecordsSelection selection)
	{
		{
			final String countryNameSql = "UPDATE I_BPartner i "
					+ "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
					+ " WHERE ("
					+ " (i.CountryName=c.Name AND c.AD_Client_ID IN (0, i.AD_Client_ID))"
					+ " )) "
					+ "WHERE C_Country_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Set Country", countryNameSql);
		}

		{
			final String countryCodeSql = "UPDATE I_BPartner i "
					+ "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
					+ " WHERE ("
					+ " (i.CountryCode=c.CountryCode AND c.AD_Client_ID IN (0, i.AD_Client_ID))"
					+ " )) "
					+ "WHERE C_Country_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y' "
					+ selection.toSqlWhereClause("i");

			executeUpdate("Set Country", countryCodeSql);
		}

		//
		{
			final String sql = new StringBuilder("UPDATE I_BPartner "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Country, ' "
					+ "WHERE C_Country_ID IS NULL AND (City IS NOT NULL OR Address1 IS NOT NULL)"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause()).toString();

			executeUpdate("Flag records with invalid Country", sql);
		}
	}

	private void dpUpdateRegions(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "Set RegionName=(SELECT MAX(Name) FROM C_Region r"
					+ " WHERE r.IsDefault='Y' AND r.C_Country_ID=i.C_Country_ID"
					+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID) AND r.AD_Org_ID = i.AD_Org_ID) ");
			sql.append("WHERE RegionName IS NULL AND C_Region_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
					.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set Default Region", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r"
					+ " WHERE r.Name=i.RegionName AND r.C_Country_ID=i.C_Country_ID"
					+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) "
					+ "WHERE C_Region_ID IS NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set Region", sql.toString());
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Region, ' "
					+ "WHERE C_Region_ID IS NULL "
					+ " AND EXISTS (SELECT 1 FROM C_Country c WHERE c.C_Country_ID=i.C_Country_ID AND c.HasRegion='Y')"
					+ " AND RegionName IS NOT NULL" // tolerate no region
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid Region", sql.toString());
		}
	}

	private void dbUpdateGreetings(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET C_Greeting_ID=(SELECT C_Greeting_ID FROM C_Greeting g"
					+ " WHERE i.BPContactGreeting=g.Name AND g.AD_Client_ID IN (0, i.AD_Client_ID) AND g.AD_Org_ID = i.AD_Org_ID) "
					+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set Greeting", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Greeting, ' "
					+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid Greeting", sql);
		}
	}

	private void dbUpdateJobs(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET C_Job_ID=(SELECT C_Job_ID FROM C_Job j"
					+ " WHERE i.JobName=j.Name AND j.AD_Client_ID IN (0, i.AD_Client_ID) AND j.AD_Org_ID IN (0, i.AD_Org_ID) ) "
					+ "WHERE C_Job_ID IS NULL AND JobName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set Job", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Job, ' "
					+ "WHERE C_Job_ID IS NULL AND JobName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid Job", sql);
		}
	}

	private void dbUpdateAdUserIdsFromExisting(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET (C_BPartner_ID,AD_User_ID)="
				+ "(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u "
				+ "WHERE i.EMail=u.EMail AND u.AD_Client_ID=i.AD_Client_ID AND u.AD_Org_ID=i.AD_Org_ID) "
				+ "WHERE i.EMail IS NOT NULL AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set BPartner/User by EMail", DB.convertSqlToNative(sql.toString()));
	}

	private void dbUpdateCbPartnerIdsFromValue(final ImportRecordsSelection selection)
	{

		final StringBuilder sql = new StringBuilder("UPDATE " + I_I_BPartner.Table_Name + " i " + "SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID + "=bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name + " bp"
				+ " WHERE i." + I_I_BPartner.COLUMNNAME_C_BPartner_ID + " IS NULL "
				+ " AND i." + I_I_BPartner.COLUMNNAME_BPValue + " IS NOT NULL "
				+ " AND bp." + I_C_BPartner.COLUMNNAME_Value + " = i." + I_I_BPartner.COLUMNNAME_BPValue
				+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + " = i." + I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Org_ID + " = i." + I_I_BPartner.COLUMNNAME_AD_Org_ID
				+ " AND bp." + I_C_BPartner.COLUMNNAME_IsActive + " ='Y' "
				+ " AND i." + I_I_BPartner.COLUMNNAME_I_IsImported + " = 'N'")

						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set BPartner by Value", sql);
	}

	private void dbUpdateCbPartnerIdsFromC_BPartner_ExternalId(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + I_I_BPartner.Table_Name + " i "
				+ "SET " + I_I_BPartner.COLUMNNAME_C_BPartner_ID + "=(SELECT " + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name
				+ " p WHERE i." + I_I_BPartner.COLUMNNAME_C_BPartner_ExternalId + "=p." + I_C_BPartner.COLUMNNAME_ExternalId
				+ " AND p." + I_C_BPartner.COLUMNNAME_AD_Client_ID + "=i." + I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ ") "
				+ "WHERE "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID + " IS NULL AND "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ExternalId + " IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set BPartner by ExternalId", sql);
	}

	private void dbUpdateCbPartnerIdsFromC_BPartner_GlobalId(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE " + I_I_BPartner.Table_Name + " i " + "SET "
				+ I_I_BPartner.COLUMNNAME_C_BPartner_ID + "=bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
				+ " FROM " + I_C_BPartner.Table_Name + " bp"
				+ " WHERE i." + I_I_BPartner.COLUMNNAME_C_BPartner_ID + " IS NULL "
				+ " AND i." + I_I_BPartner.COLUMNNAME_GlobalId + " IS NOT NULL "
				+ " AND bp." + I_C_BPartner.COLUMNNAME_GlobalId + " IS NOT NULL "
				+ " AND bp." + I_C_BPartner.COLUMNNAME_GlobalId + " = i." + I_I_BPartner.COLUMNNAME_GlobalId
				+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + " = i." + I_I_BPartner.COLUMNNAME_AD_Client_ID
				+ " AND bp." + I_C_BPartner.COLUMNNAME_IsActive + " ='Y' "
				+ " AND i." + I_I_BPartner.COLUMNNAME_I_IsImported + " = 'N'")

						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set BPartner by GlobalId", sql);
	}

	private void updateNames(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
				+ I_I_BPartner.Table_Name
				+ " i SET "
				+ I_I_BPartner.COLUMNNAME_Name
				+ " = i." + I_I_BPartner.COLUMNNAME_Companyname
				+ " WHERE i." + I_I_BPartner.COLUMNNAME_Name
				+ " IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set Name by CompanyName", sql);
	}

	private void dbUpdateAdUserIdsFromContactNames(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
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
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set User by ContactName", sql);
	}

	private void dbUpdateAdUserIdsFromAD_User_ExternalIds(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
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
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set User by ExternalId", sql);
	}

	private void dbUpdateCBPartnerLocationsFromC_BPartner_Location_ExternalIds(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
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
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set Location by ExternalId", sql);
	}

	private void dbUpdateCBPartnerLocationsFromGLN(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
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
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set Location by GLN", sql);
	}

	private void dbUpdateLocations(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE "
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
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set Location by Address matching", sql);
	}

	private void dbUpdateInterestAreas(final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
				+ "SET R_InterestArea_ID=(SELECT R_InterestArea_ID FROM R_InterestArea ia "
				+ "WHERE i.InterestAreaName=ia.Name AND ia.AD_Client_ID=i.AD_Client_ID AND ia.AD_Org_ID = i.AD_Org_ID) "
				+ "WHERE R_InterestArea_ID IS NULL AND InterestAreaName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'")
						.append(selection.toSqlWhereClause("i"));

		executeUpdate("Set Interest Area", sql);
	}

	private void dbUpdateInvoiceSchedules(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET C_InvoiceSchedule_ID=(SELECT C_InvoiceSchedule_ID FROM C_InvoiceSchedule invSched"
					+ " WHERE i.InvoiceSchedule=invSched.Name AND invSched.AD_Client_ID IN (0, i.AD_Client_ID)) "
					+ "WHERE C_InvoiceSchedule_ID IS NULL AND InvoiceSchedule IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set Invoice Schedule", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid InvoiceSchedule, ' "
					+ "WHERE C_InvoiceSchedule_ID IS NULL AND InvoiceSchedule IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid Invoice Schedule", sql);
		}
	}

	private void dbUpdatePaymentTerms(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET C_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm pt"
					+ " WHERE i.PaymentTermValue=pt.Name AND pt.AD_Client_ID IN (0, i.AD_Client_ID)) "
					+ "WHERE C_PaymentTerm_ID IS NULL AND PaymentTerm IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set SO Payment Term", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid C_PaymentTerm_ID, ' "
					+ "WHERE C_PaymentTerm_ID IS NULL AND PaymentTermValue IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid SO Payment Terms", sql);
		}
	}

	private void dbUpdatePO_PaymentTerms(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET PO_PaymentTerm_ID=(SELECT C_PaymentTerm_ID FROM C_PaymentTerm pt"
					+ " WHERE i.PaymentTerm=pt.Name AND pt.AD_Client_ID IN (0, i.AD_Client_ID)) "
					+ "WHERE PO_PaymentTerm_ID IS NULL AND PaymentTerm IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set PO Payment Term", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid PO_PaymentTerm, ' "
					+ "WHERE PO_PaymentTerm_ID IS NULL AND PaymentTerm IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid PO Payment Terms", sql);
		}
	}

	private void dbUpdateC_Aggregtions(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET C_Aggregation_ID=(SELECT C_Aggregation_ID FROM C_Aggregation a"
					+ " WHERE i.AggregationName=a.Name AND a.AD_Client_ID IN (0, i.AD_Client_ID) AND a.AD_Org_ID IN (0, i.AD_Org_ID ) )"
					+ "WHERE C_Aggregation_ID IS NULL AND AggregationName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set C_Aggregation_ID", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid AggregationName, ' "
					+ "WHERE C_Aggregation_ID IS NULL AND AggregationName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid AggregationName", sql);
		}
	}

	private void dbUpdateM_Shippers(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET M_Shipper_ID=(SELECT M_Shipper_ID FROM M_Shipper s"
					+ " WHERE i.ShipperName=s.Name AND s.AD_Client_ID IN (0, i.AD_Client_ID)), "
					+ " DeliveryViaRule = 'S' "
					+ "WHERE M_Shipper_ID IS NULL AND ShipperName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set M_Shipper_ID", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET DeliveryViaRule = " + DB.TO_STRING(DeliveryViaRule.Pickup.getCode())
					+ "WHERE M_Shipper_ID IS NULL AND ShipperName = 'P' "
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set DeliveryViaRule=Pickup when M_Shipper_ID is not set", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Shipper or DeliveryViaRule, ' "
					+ "WHERE M_Shipper_ID IS NULL AND DeliveryViaRule IS NULL AND ShipperName IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid Invalid Shipper or DeliveryViaRule", sql);
		}
	}

	private void dbUpdateAD_PrintFormats(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET AD_PrintFormat_ID=(SELECT AD_PrintFormat_ID FROM AD_PrintFormat pf"
					+ " WHERE i.PrintFormat_Name=pf.Name AND pf.AD_Client_ID IN (0, i.AD_Client_ID) AND pf.AD_Org_ID IN (0, i.AD_Org_ID ) ) "
					+ "WHERE AD_PrintFormat_ID IS NULL AND PrintFormat_Name IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set AD_PrintFormat_ID", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid PrintFormat_Name, ' "
					+ "WHERE AD_PrintFormat_ID IS NULL AND PrintFormat_Name IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid AD_PrintFormat_ID", sql);
		}
	}

	private void dbUpdateM_PricingSystems(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET M_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_PricingSystem ps"
					+ " WHERE i.PricingSystem_Value=ps.value AND ps.AD_Client_ID IN (0, i.AD_Client_ID) and ps.IsActive='Y' ) "
					+ "WHERE M_PricingSystem_ID IS NULL AND PricingSystem_Value IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set M_PricingSystem_ID={}", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid M_PricingSystem_ID, ' "
					+ "WHERE M_PricingSystem_ID IS NULL AND PricingSystem_Value IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid M_PricingSystem", sql);
		}
	}

	private void dbUpdatePO_PricingSystems(final ImportRecordsSelection selection)
	{
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET PO_PricingSystem_ID=(SELECT M_PricingSystem_ID FROM M_PricingSystem ps"
					+ " WHERE i.PO_PricingSystem_Value=ps.value AND ps.AD_Client_ID IN (0, i.AD_Client_ID) and IsActive='Y') "
					+ "WHERE PO_PricingSystem_ID IS NULL AND PO_PricingSystem_Value IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Set PO_PricingSystem_ID", sql);
		}

		//
		{
			final StringBuilder sql = new StringBuilder("UPDATE I_BPartner i "
					+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid M_PricingSystem_ID, ' "
					+ "WHERE PO_PricingSystem_ID IS NULL AND PO_PricingSystem_Value IS NOT NULL"
					+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
							.append(selection.toSqlWhereClause("i"));

			executeUpdate("Flag records with invalid PO_PricingSystem_ID", sql);
		}
	}
}
