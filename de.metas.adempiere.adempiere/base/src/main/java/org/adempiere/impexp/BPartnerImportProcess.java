package org.adempiere.impexp;

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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MContactInterest;
import org.compiere.model.MLocation;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BPartner;
import org.compiere.util.DB;

/**
 * Imports {@link I_I_BPartner} records to {@link I_C_BPartner}.
 * 
 * @author tsa
 *
 */
public class BPartnerImportProcess extends AbstractImportProcess<I_I_BPartner>
{
	// services
	private final transient IUserBL userBL = Services.get(IUserBL.class);

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String whereClause = getWhereClause();

		// Set BP_Group
		StringBuffer sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET GroupValue=(SELECT MAX(Value) FROM C_BP_Group g WHERE g.IsDefault='Y'"
				+ " AND g.AD_Client_ID=i.AD_Client_ID) ");
		sql.append("WHERE GroupValue IS NULL AND C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		int no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Group Default=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET C_BP_Group_ID=(SELECT C_BP_Group_ID FROM C_BP_Group g"
				+ " WHERE i.GroupValue=g.Value AND g.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Group=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Group, ' "
				+ "WHERE C_BP_Group_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Invalid Group=" + no);

		// Set Country
		/**
		 * sql = new StringBuffer ("UPDATE I_BPartner i " + "SET CountryCode=(SELECT CountryCode FROM C_Country c WHERE c.IsDefault='Y'" + " AND c.AD_Client_ID IN (0, i.AD_Client_ID) AND ROWNUM=1) " +
		 * "WHERE CountryCode IS NULL AND C_Country_ID IS NULL" + " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause); no = DB.executeUpdateEx(sql.toString(), trxName);
		 * log.debug("Set Country Default=" + no);
		 **/
		//
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET C_Country_ID=(SELECT C_Country_ID FROM C_Country c"
				+ " WHERE i.CountryCode=c.CountryCode AND c.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_Country_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Country=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Country, ' "
				+ "WHERE C_Country_ID IS NULL AND (City IS NOT NULL OR Address1 IS NOT NULL)"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Invalid Country=" + no);

		// Set Region
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "Set RegionName=(SELECT MAX(Name) FROM C_Region r"
				+ " WHERE r.IsDefault='Y' AND r.C_Country_ID=i.C_Country_ID"
				+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) ");
		sql.append("WHERE RegionName IS NULL AND C_Region_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Region Default=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "Set C_Region_ID=(SELECT C_Region_ID FROM C_Region r"
				+ " WHERE r.Name=i.RegionName AND r.C_Country_ID=i.C_Country_ID"
				+ " AND r.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_Region_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Region=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Region, ' "
				+ "WHERE C_Region_ID IS NULL "
				+ " AND EXISTS (SELECT 1 FROM C_Country c WHERE c.C_Country_ID=i.C_Country_ID AND c.HasRegion='Y')"
				+ " AND RegionName IS NOT NULL" // tolerate no region
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'")
		.append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Invalid Region=" + no);

		// Set Greeting
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET C_Greeting_ID=(SELECT C_Greeting_ID FROM C_Greeting g"
				+ " WHERE i.BPContactGreeting=g.Name AND g.AD_Client_ID IN (0, i.AD_Client_ID)) "
				+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Greeting=" + no);
		//
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Greeting, ' "
				+ "WHERE C_Greeting_ID IS NULL AND BPContactGreeting IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Invalid Greeting=" + no);

		// Existing User ?
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET (C_BPartner_ID,AD_User_ID)="
				+ "(SELECT C_BPartner_ID,AD_User_ID FROM AD_User u "
				+ "WHERE i.EMail=u.EMail AND u.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE i.EMail IS NOT NULL AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(DB.convertSqlToNative(sql.toString()), trxName);
		log.debug("Found EMail User=" + no);

		// Existing BPartner ? Match Value
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
				+ " WHERE i.Value=p.Value AND p.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NULL AND Value IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Found BPartner=" + no);

		// Existing Contact ? Match Name
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET AD_User_ID=(SELECT AD_User_ID FROM AD_User c"
				+ " WHERE i.ContactName=c.Name AND i.C_BPartner_ID=c.C_BPartner_ID AND c.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE C_BPartner_ID IS NOT NULL AND AD_User_ID IS NULL AND ContactName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Found Contact=" + no);

		// Existing Location ? Exact Match
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET C_BPartner_Location_ID=(SELECT C_BPartner_Location_ID"
				+ " FROM C_BPartner_Location bpl INNER JOIN C_Location l ON (bpl.C_Location_ID=l.C_Location_ID)"
				+ " WHERE i.C_BPartner_ID=bpl.C_BPartner_ID AND bpl.AD_Client_ID=i.AD_Client_ID"
				+ " AND i.Address1=l.Address1 AND i.Address2=l.Address2"
				+ " AND i.City=l.City AND i.Postal=l.Postal AND i.Postal_Add=l.Postal_Add"
				+ " AND i.C_Region_ID=l.C_Region_ID AND i.C_Country_ID=l.C_Country_ID) "
				+ "WHERE C_BPartner_ID IS NOT NULL AND C_BPartner_Location_ID IS NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Found Location=" + no);

		// Interest Area
		sql = new StringBuffer("UPDATE I_BPartner i "
				+ "SET R_InterestArea_ID=(SELECT R_InterestArea_ID FROM R_InterestArea ia "
				+ "WHERE i.InterestAreaName=ia.Name AND ia.AD_Client_ID=i.AD_Client_ID) "
				+ "WHERE R_InterestArea_ID IS NULL AND InterestAreaName IS NOT NULL"
				+ " AND " + COLUMNNAME_I_IsImported + "='N'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.debug("Set Interest Area=" + no);

		// Value is mandatory error
		sql = new StringBuffer("UPDATE I_BPartner "
				+ "SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value is mandatory, ' "
				+ "WHERE Value IS NULL "
				+ " AND " + COLUMNNAME_I_IsImported + "<>'Y'").append(whereClause);
		no = DB.executeUpdateEx(sql.toString(), trxName);
		log.info("Value is mandatory=" + no);
	}

	private static final class BPartnerImportContext
	{
		// Remember Previous BP Value BP is only first one, others are contacts.
		// All contacts share BP location.
		// bp and bpl declarations before loop, we need them for data.
		I_I_BPartner previousImportRecord = null;
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_BPartner importRecord) throws Exception
	{
		//
		// Get previous values
		BPartnerImportContext context = (BPartnerImportContext)state.getValue();
		if (context == null)
		{
			context = new BPartnerImportContext();
			state.setValue(context);
		}
		final I_I_BPartner previousImportRecord = context.previousImportRecord;
		final int previousBPartnerId = previousImportRecord == null ? -1 : previousImportRecord.getC_BPartner_ID();
		final String previousBPValue = previousImportRecord == null ? null : previousImportRecord.getValue();
		context.previousImportRecord = importRecord; // set it early in case this method fails

		final ImportRecordResult bpartnerImportResult;
		
		// 
		// First line to import or this line does NOT have the same BP value
		// => create a new BPartner or update the existing one
		if (previousImportRecord == null || !Check.equals(importRecord.getValue(), previousBPValue))
		{
			bpartnerImportResult = importRecord.getC_BPartner_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
			createUpdateBPartner(importRecord);
		}
		//
		// Same BPValue like previous line
		else
		{
			// We don't have a previous C_BPartner_ID
			// => create or update existing BPartner from this line
			if (previousBPartnerId <= 0)
			{
				bpartnerImportResult = importRecord.getC_BPartner_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
				createUpdateBPartner(importRecord);
			}
			// Our line does not have a C_BPartner_ID or it has the same C_BPartner_ID like the previous line
			// => reuse previous BPartner
			else if (importRecord.getC_BPartner_ID() <= 0 || importRecord.getC_BPartner_ID() == previousBPartnerId)
			{
				bpartnerImportResult = ImportRecordResult.Nothing;
				importRecord.setC_BPartner(previousImportRecord.getC_BPartner());
			}
			// Our line has a C_BPartner_ID set but it's not the same like previous one, even though the BPValues are the same
			// => ERROR
			else
			{
				throw new AdempiereException("Same BPValue as previous line but not same BPartner linked");
			}
		}

		createUpdateBPartnerLocation(importRecord);
		createUpdateContact(importRecord);
		createUpdateInterestArea(importRecord);

		return bpartnerImportResult;
	}

	private I_C_BPartner createUpdateBPartner(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		final boolean insertMode;
		if (importRecord.getC_BPartner_ID() <= 0)	// Insert new BPartner
		{
			insertMode = true;
			bpartner = InterfaceWrapperHelper.create(getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
			bpartner.setAD_Org_ID(importRecord.getAD_Org_ID());
			//
			String value = importRecord.getValue();
			if (Check.isEmpty(value, true))
				value = importRecord.getEMail();
			bpartner.setValue(value);
			//
			String name = importRecord.getName();
			if (Check.isEmpty(name, true))
				name = importRecord.getEMail();
			if (Check.isEmpty(name, true))
				name = bpartner.getValue();
			bpartner.setName(name);
			bpartner.setName2(importRecord.getName2());
			bpartner.setDescription(importRecord.getDescription());
			// setHelp(impBP.getHelp());
			bpartner.setDUNS(importRecord.getDUNS());
			bpartner.setVATaxID(importRecord.getTaxID());
			bpartner.setNAICS(importRecord.getNAICS());
			bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		}
		else
		// Update existing BPartner
		{
			insertMode = false;
			bpartner = importRecord.getC_BPartner();
			// if (impBP.getValue() != null) // not to overwite
			// bp.setValue(impBP.getValue());
			if (importRecord.getName() != null)
			{
				bpartner.setName(importRecord.getName());
				bpartner.setName2(importRecord.getName2());
			}
			if (importRecord.getDUNS() != null)
				bpartner.setDUNS(importRecord.getDUNS());
			if (importRecord.getTaxID() != null)
				bpartner.setVATaxID(importRecord.getTaxID());
			if (importRecord.getNAICS() != null)
				bpartner.setNAICS(importRecord.getNAICS());
			if (importRecord.getDescription() != null)
				bpartner.setDescription(importRecord.getDescription());
			if (importRecord.getC_BP_Group_ID() != 0)
				bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		}

		//
		// CompanyName
		final String companyName = importRecord.getCompanyname();
		if (!Check.isEmpty(companyName, true))
		{
			bpartner.setIsCompany(true);
			bpartner.setCompanyName(companyName.trim());
		}
		else if (insertMode)
		{
			bpartner.setIsCompany(false);
			bpartner.setCompanyName(null);
		}

		//
		// Type (Vendor, Customer, Employee)
		setTypeOfBPartner(importRecord, bpartner);

		ModelValidationEngine.get().fireImportValidate(this, importRecord, bpartner, IImportValidator.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(bpartner);
		importRecord.setC_BPartner(bpartner);

		return bpartner;
	}

	private I_C_BPartner_Location createUpdateBPartnerLocation(final I_I_BPartner importRecord)
	{
		I_C_BPartner_Location bpartnerLocation = importRecord.getC_BPartner_Location();
		if (bpartnerLocation != null && bpartnerLocation.getC_BPartner_Location_ID() > 0)		// Update Location
		{
			final I_C_Location location = bpartnerLocation.getC_Location();
			location.setC_Country_ID(importRecord.getC_Country_ID());
			location.setC_Region_ID(importRecord.getC_Region_ID());
			location.setCity(importRecord.getCity());
			location.setAddress1(importRecord.getAddress1());
			location.setAddress2(importRecord.getAddress2());
			location.setPostal(importRecord.getPostal());
			location.setPostal_Add(importRecord.getPostal_Add());
			InterfaceWrapperHelper.save(location);
			bpartnerLocation.setC_Location(location);
			
			// also set isBillTo and IsShipTo flags
			
			bpartnerLocation.setIsShipTo(importRecord.isShipTo());
			bpartnerLocation.setIsBillTo(importRecord.isBillTo());

			if (importRecord.getPhone() != null)
				bpartnerLocation.setPhone(importRecord.getPhone());
			if (importRecord.getPhone2() != null)
				bpartnerLocation.setPhone2(importRecord.getPhone2());
			if (importRecord.getFax() != null)
				bpartnerLocation.setFax(importRecord.getFax());
			ModelValidationEngine.get().fireImportValidate(this, importRecord, bpartnerLocation, IImportValidator.TIMING_AFTER_IMPORT);
			
			
			
			InterfaceWrapperHelper.save(bpartnerLocation);
		}
		else 	// New Location
		if (importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getAddress1(), true)
				&& !Check.isEmpty(importRecord.getCity(), true))
		{
			final I_C_Location location = new MLocation(getCtx(), importRecord.getC_Country_ID(), importRecord.getC_Region_ID(), importRecord.getCity(), ITrx.TRXNAME_ThreadInherited);
			location.setAddress1(importRecord.getAddress1());
			location.setAddress2(importRecord.getAddress2());
			location.setPostal(importRecord.getPostal());
			location.setPostal_Add(importRecord.getPostal_Add());
			InterfaceWrapperHelper.save(location);

			//
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			bpartnerLocation = new MBPartnerLocation(bpartner);
			bpartnerLocation.setC_BPartner(bpartner);
			bpartnerLocation.setC_Location(location);
			bpartnerLocation.setPhone(importRecord.getPhone());
			bpartnerLocation.setPhone2(importRecord.getPhone2());
			bpartnerLocation.setFax(importRecord.getFax());
			ModelValidationEngine.get().fireImportValidate(this, importRecord, bpartnerLocation, IImportValidator.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bpartnerLocation);
			log.trace("Insert BP Location - " + bpartnerLocation.getC_BPartner_Location_ID());

			importRecord.setC_BPartner_Location(bpartnerLocation);
		}

		return bpartnerLocation;
	}

	private I_AD_User createUpdateContact(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner = importRecord.getC_BPartner();
		final I_C_BPartner_Location bpartnerLocation = importRecord.getC_BPartner_Location();
		final String importContactName = userBL.buildContactName(importRecord.getFirstname(), importRecord.getLastname());

		I_AD_User user = importRecord.getAD_User();
		if (user != null)
		{
			if (user.getC_BPartner_ID() <= 0)
				user.setC_BPartner(bpartner);
			else if (user.getC_BPartner_ID() != bpartner.getC_BPartner_ID())
			{
				throw new AdempiereException("BP of User <> BP");
			}
			if (importRecord.getC_Greeting_ID() != 0)
				user.setC_Greeting_ID(importRecord.getC_Greeting_ID());
			// String name = importRecord.getContactName();
			String name = importContactName;
			if (name == null || name.length() == 0)
				name = importRecord.getEMail();
			user.setName(name);
			user.setFirstname(importRecord.getFirstname());
			user.setLastname(importRecord.getLastname());

			if (importRecord.getTitle() != null)
				user.setTitle(importRecord.getTitle());
			if (importRecord.getContactDescription() != null)
				user.setDescription(importRecord.getContactDescription());
			if (importRecord.getComments() != null)
				user.setComments(importRecord.getComments());
			if (importRecord.getPhone() != null)
				user.setPhone(importRecord.getPhone());
			if (importRecord.getPhone2() != null)
				user.setPhone2(importRecord.getPhone2());
			if (importRecord.getFax() != null)
				user.setFax(importRecord.getFax());
			if (importRecord.getEMail() != null)
				user.setEMail(importRecord.getEMail());
			if (importRecord.getBirthday() != null)
				user.setBirthday(importRecord.getBirthday());
			if (bpartnerLocation != null)
				user.setC_BPartner_Location(bpartnerLocation);
			ModelValidationEngine.get().fireImportValidate(this, importRecord, user, IImportValidator.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(user);
		}
		else 	// New Contact
		if (!Check.isEmpty(importContactName, true)
				|| !Check.isEmpty(importRecord.getEMail(), true))
		{
			user = new MUser(bpartner);
			if (importRecord.getC_Greeting_ID() > 0)
				user.setC_Greeting_ID(importRecord.getC_Greeting_ID());

			String name = importContactName;
			if (Check.isEmpty(name, true))
				name = importRecord.getEMail();
			user.setName(name);
			user.setFirstname(importRecord.getFirstname());
			user.setLastname(importRecord.getLastname());

			user.setTitle(importRecord.getTitle());
			user.setDescription(importRecord.getContactDescription());
			user.setComments(importRecord.getComments());
			user.setPhone(importRecord.getPhone());
			user.setPhone2(importRecord.getPhone2());
			user.setFax(importRecord.getFax());
			user.setEMail(importRecord.getEMail());
			user.setBirthday(importRecord.getBirthday());
			if (bpartnerLocation != null)
				user.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
			ModelValidationEngine.get().fireImportValidate(this, importRecord, user, IImportValidator.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(user);
			log.trace("Insert BP Contact - " + user.getAD_User_ID());

			importRecord.setAD_User(user);
		}

		return user;
	}

	private final void createUpdateInterestArea(final I_I_BPartner importRecord)
	{
		final int adUserId = importRecord.getAD_User_ID();
		if (importRecord.getR_InterestArea_ID() > 0 && adUserId > 0)
		{
			MContactInterest ci = MContactInterest.get(getCtx(),
					importRecord.getR_InterestArea_ID(),
					adUserId,
					true, // active
					ITrx.TRXNAME_ThreadInherited);
			ci.save();		// don't subscribe or re-activate
		}

	}

	@Override
	public Class<I_I_BPartner> getImportModelClass()
	{
		return I_I_BPartner.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_BPartner.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		// gody: 20070113 - Order so the same values are consecutive.
		return I_I_BPartner.COLUMNNAME_Value
				+ ", " + I_I_BPartner.COLUMNNAME_I_BPartner_ID;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	protected I_I_BPartner retrieveImportRecord(Properties ctx, ResultSet rs) throws SQLException
	{
		return new X_I_BPartner(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Set type of Business Partner
	 *
	 * @param X_I_BPartner impBP
	 * @param MBPartner bp
	 */
	private void setTypeOfBPartner(I_I_BPartner impBP, I_C_BPartner bp)
	{
		if (impBP.isVendor())
		{
			bp.setIsVendor(true);
			bp.setIsCustomer(false); // It is put to false since by default in C_BPartner is true
		}
		if (impBP.isEmployee())
		{
			bp.setIsEmployee(true);
			bp.setIsCustomer(false); // It is put to false since by default in C_BPartner is true
		}
		// it has to be the last if, to subscribe the bp.setIsCustomer (false) of the other two
		if (impBP.isCustomer())
		{
			bp.setIsCustomer(true);
		}
	}	// setTypeOfBPartner

}	// ImportBPartner
