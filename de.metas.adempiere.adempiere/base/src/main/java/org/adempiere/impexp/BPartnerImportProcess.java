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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerBL;
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
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_BPartner;

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
		final String whereClause = getWhereClause();
		BPartnerImportTableSqlUpdater.updateBPartnerImtortTable(whereClause);
	}

	private static final class BPartnerImportContext
	{
		// Remember Previous BP Value BP is only first one, others are contacts.
		// All contacts share BP location.
		// bp and bpl declarations before loop, we need them for data.
		I_I_BPartner previousImportRecord = null;

		public I_I_BPartner getPreviousImportRecord()
		{
			return previousImportRecord;
		}

		public void setPreviousImportRecord(final I_I_BPartner previousImportRecord)
		{
			this.previousImportRecord = previousImportRecord;
		}

		public int getPreviousC_BPartner_ID()
		{
			return previousImportRecord == null ? -1 : previousImportRecord.getC_BPartner_ID();
		}

		public String getPreviousBPValue()
		{
			return previousImportRecord == null ? null : previousImportRecord.getValue();
		}

		//
		// gather a list with all imported lines for same partner
		private List<I_I_BPartner> sameBPpreviousImportRecords = new ArrayList<>();

		public List<I_I_BPartner> getSameBPpreviousImportRecords()
		{
			return sameBPpreviousImportRecords;
		}

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
		final I_I_BPartner previousImportRecord = context.getPreviousImportRecord();
		final int previousBPartnerId = context.getPreviousC_BPartner_ID();
		final String previousBPValue = context.getPreviousBPValue();
		context.setPreviousImportRecord(importRecord); // set it early in case this method fails
		final List<I_I_BPartner> sameBPpreviousImportRecords = new ArrayList<>();
		sameBPpreviousImportRecords.addAll(context.getSameBPpreviousImportRecords());

		final ImportRecordResult bpartnerImportResult;

		//
		// First line to import or this line does NOT have the same BP value
		// => create a new BPartner or update the existing one
		if (previousImportRecord == null || !Objects.equals(importRecord.getValue(), previousBPValue))
		{
			// create a new list because we are passing to a new partner
			context.sameBPpreviousImportRecords = new ArrayList<>();
			context.sameBPpreviousImportRecords.add(importRecord);

			bpartnerImportResult = importRecord.getC_BPartner_ID() <= 0 ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
			createUpdateBPartner(importRecord);
		}
		//
		// Same BPValue like previous line
		else
		{
			context.getSameBPpreviousImportRecords().add(importRecord); // set in the context the new line

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

		createOrUpdateBPartnerLocation(importRecord, sameBPpreviousImportRecords);
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
			bpartner = createNewBPartner(importRecord);
		}
		else
		// Update existing BPartner
		{
			insertMode = false;
			bpartner = updateExistingBPartner(importRecord);
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

	private I_C_BPartner createNewBPartner(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		bpartner = InterfaceWrapperHelper.create(getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		bpartner.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		String value = importRecord.getValue();
		bpartner.setValue(Check.isEmpty(value, true) ? importRecord.getEMail() : value);
		//
		String name = importRecord.getName();
		if (Check.isEmpty(name, true))
		{
			name = importRecord.getEMail();
		}
		if (Check.isEmpty(name, true))
		{
			name = bpartner.getValue();
		}
		bpartner.setName(name);
		bpartner.setName2(importRecord.getName2());
		bpartner.setDescription(importRecord.getDescription());
		// setHelp(impBP.getHelp());
		bpartner.setDUNS(importRecord.getDUNS());
		bpartner.setVATaxID(importRecord.getTaxID());
		bpartner.setNAICS(importRecord.getNAICS());
		bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		bpartner.setAD_Language(importRecord.getAD_Language());
		return bpartner;
	}

	private I_C_BPartner updateExistingBPartner(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		bpartner = importRecord.getC_BPartner();
		// if (impBP.getValue() != null) // not to overwite
		// bp.setValue(impBP.getValue());
		if (importRecord.getName() != null)
		{
			bpartner.setName(importRecord.getName());
			bpartner.setName2(importRecord.getName2());
		}
		if (importRecord.getDUNS() != null)
		{
			bpartner.setDUNS(importRecord.getDUNS());
		}
		if (importRecord.getTaxID() != null)
		{
			bpartner.setVATaxID(importRecord.getTaxID());
		}
		if (importRecord.getNAICS() != null)
		{
			bpartner.setNAICS(importRecord.getNAICS());
		}
		if (importRecord.getDescription() != null)
		{
			bpartner.setDescription(importRecord.getDescription());
		}
		if (importRecord.getC_BP_Group_ID() != 0)
		{
			bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		}
		
		if (importRecord.getAD_Language() != null)
		{
			bpartner.setAD_Language(importRecord.getAD_Language());
		}
		
		bpartner.setAD_Language(importRecord.getAD_Language());
		return bpartner;
	}

	private static Predicate<I_I_BPartner> isSameAddress(final I_I_BPartner importRecord)
	{
		return p -> p.getC_BPartner_Location_ID() > 0 
				&& importRecord.getC_Country_ID() == p.getC_Country_ID()
				&& importRecord.getC_Region_ID() == p.getC_Region_ID()
				&& Objects.equals(importRecord.getCity(), p.getCity())
				&& Objects.equals(importRecord.getAddress1(), p.getAddress1())
				&& Objects.equals(importRecord.getAddress2(), p.getAddress2())
				&& Objects.equals(importRecord.getPostal(), p.getPostal())
				&& Objects.equals(importRecord.getPostal_Add(), p.getPostal_Add());
	}

	private I_C_BPartner_Location createOrUpdateBPartnerLocation(final I_I_BPartner importRecord, final List<I_I_BPartner> sameBPpreviousImportRecords)
	{
		I_C_BPartner_Location bpartnerLocation = importRecord.getC_BPartner_Location();

		final List<I_I_BPartner> alreadyImportedBPAddresses = sameBPpreviousImportRecords.stream()
				.filter(isSameAddress(importRecord))
				.collect(Collectors.<I_I_BPartner> toList());

		final boolean isAlreadyImportedBPAddresses = alreadyImportedBPAddresses.isEmpty() ? false : true;
		if (isAlreadyImportedBPAddresses
				|| bpartnerLocation != null && bpartnerLocation.getC_BPartner_Location_ID() > 0)// Update Location
		{
			if (isAlreadyImportedBPAddresses)
			{
				bpartnerLocation = alreadyImportedBPAddresses.get(0).getC_BPartner_Location();
			}

			updateExistingBPartnerLocation(importRecord, bpartnerLocation);
		}
		else 	// New Location
		if (importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getAddress1(), true)
				&& !Check.isEmpty(importRecord.getCity(), true))
		{
			bpartnerLocation = createNewBPartnerLocation(importRecord);
		}

		return bpartnerLocation;
	}

	private void updateExistingBPartnerLocation(final I_I_BPartner importRecord, final I_C_BPartner_Location bpartnerLocation)
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
		bpartnerLocation.setIsShipToDefault(importRecord.isShipToDefault());
		bpartnerLocation.setIsShipTo(importRecord.isShipToDefault() ? importRecord.isShipToDefault() : importRecord.isShipTo()); // if isShipToDefault='Y', then use that value
		bpartnerLocation.setIsBillToDefault(importRecord.isBillToDefault());
		bpartnerLocation.setIsBillTo(importRecord.isBillToDefault() ? importRecord.isBillToDefault() : importRecord.isBillTo()); // if isBillToDefault='Y', the use that value

		if (importRecord.getPhone() != null)
		{
			bpartnerLocation.setPhone(importRecord.getPhone());
		}
		if (importRecord.getPhone2() != null)
		{
			bpartnerLocation.setPhone2(importRecord.getPhone2());
		}
		if (importRecord.getFax() != null)
		{
			bpartnerLocation.setFax(importRecord.getFax());
		}
		ModelValidationEngine.get().fireImportValidate(this, importRecord, bpartnerLocation, IImportValidator.TIMING_AFTER_IMPORT);

		InterfaceWrapperHelper.save(bpartnerLocation);

		importRecord.setC_BPartner_Location(bpartnerLocation);
	}

	private I_C_BPartner_Location createNewBPartnerLocation(final I_I_BPartner importRecord)
	{
		I_C_BPartner_Location bpartnerLocation;
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

		// set isShipTo and isBillTo
		bpartnerLocation.setIsShipToDefault(importRecord.isShipToDefault());
		bpartnerLocation.setIsShipTo(importRecord.isShipToDefault() ? importRecord.isShipToDefault() : importRecord.isShipTo()); // if isShipToDefault='Y', then use that value
		bpartnerLocation.setIsBillToDefault(importRecord.isBillToDefault());
		bpartnerLocation.setIsBillTo(importRecord.isBillToDefault() ? importRecord.isBillToDefault() : importRecord.isBillTo()); // if isBillToDefault='Y', the use that value

		ModelValidationEngine.get().fireImportValidate(this, importRecord, bpartnerLocation, IImportValidator.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(bpartnerLocation);
		log.trace("Insert BP Location - " + bpartnerLocation.getC_BPartner_Location_ID());

		importRecord.setC_BPartner_Location(bpartnerLocation);
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
			{
				user.setC_BPartner(bpartner);
			}
			else if (user.getC_BPartner_ID() != bpartner.getC_BPartner_ID())
			{
				throw new AdempiereException("BP of User <> BP");
			}
			if (importRecord.getC_Greeting_ID() != 0)
			{
				user.setC_Greeting_ID(importRecord.getC_Greeting_ID());
			}
			// String name = importRecord.getContactName();
			String name = importContactName;
			if (name == null || name.length() == 0)
			{
				name = importRecord.getEMail();
			}
			user.setName(name);

			updateWithAvailablemportRecordFields(importRecord, user);

			if (bpartnerLocation != null)
			{
				user.setC_BPartner_Location(bpartnerLocation);
			}

			ModelValidationEngine.get().fireImportValidate(this, importRecord, user, IImportValidator.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(user);
		}
		else 	// New Contact
		if (!Check.isEmpty(importContactName, true)
				|| !Check.isEmpty(importRecord.getEMail(), true))
		{
			user = Services.get(IBPartnerBL.class).createDraftContact(bpartner);
			if (importRecord.getC_Greeting_ID() > 0)
			{
				user.setC_Greeting_ID(importRecord.getC_Greeting_ID());
			}

			String name = importContactName;
			if (Check.isEmpty(name, true))
			{
				name = importRecord.getEMail();
			}
			user.setName(name);
			updateWithImportRecordFields(importRecord, user);
			if (bpartnerLocation != null)
			{
				user.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
			}
			ModelValidationEngine.get().fireImportValidate(this, importRecord, user, IImportValidator.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(user);
			log.trace("Insert BP Contact - " + user.getAD_User_ID());

			importRecord.setAD_User(user);
		}

		return user;
	}

	/**
	 * Similar to {@link #updateWithAvailablemportRecordFields(I_I_BPartner, I_AD_User)}, but also {@code null} values are copied from the given {@code importRecord}.
	 * 
	 * @param importRecord
	 * @param user
	 */
	private void updateWithImportRecordFields(final I_I_BPartner importRecord, final I_AD_User user)
	{
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
		user.setIsDefaultContact(importRecord.isDefaultContact());
		
		user.setIsBillToContact_Default(importRecord.isBillToContact_Default());
		user.setIsShipToContact_Default(importRecord.isShipToContact_Default());
	}

	/**
	 * If a particular field is set in the given {@code importRecord}, the given {@code user} user's respective file is updated.
	 * 
	 * @param importRecord
	 * @param user
	 */
	private void updateWithAvailablemportRecordFields(final I_I_BPartner importRecord, final I_AD_User user)
	{
		user.setFirstname(importRecord.getFirstname());
		user.setLastname(importRecord.getLastname());

		if (importRecord.getTitle() != null)
		{
			user.setTitle(importRecord.getTitle());
		}
		if (importRecord.getContactDescription() != null)
		{
			user.setDescription(importRecord.getContactDescription());
		}
		if (importRecord.getComments() != null)
		{
			user.setComments(importRecord.getComments());
		}
		if (importRecord.getPhone() != null)
		{
			user.setPhone(importRecord.getPhone());
		}
		if (importRecord.getPhone2() != null)
		{
			user.setPhone2(importRecord.getPhone2());
		}
		if (importRecord.getFax() != null)
		{
			user.setFax(importRecord.getFax());
		}
		if (importRecord.getEMail() != null)
		{
			user.setEMail(importRecord.getEMail());
		}
		if (importRecord.getBirthday() != null)
		{
			user.setBirthday(importRecord.getBirthday());
		}
		user.setIsDefaultContact(importRecord.isDefaultContact());
		
		user.setIsBillToContact_Default(importRecord.isBillToContact_Default());
		user.setIsShipToContact_Default(importRecord.isShipToContact_Default());
	}

	private final void createUpdateInterestArea(final I_I_BPartner importRecord)
	{
		final int adUserId = importRecord.getAD_User_ID();
		if (importRecord.getR_InterestArea_ID() > 0 && adUserId > 0)
		{
			final MContactInterest ci = MContactInterest.get(getCtx(),
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
	protected I_I_BPartner retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_BPartner(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Set type of Business Partner
	 *
	 * @param X_I_BPartner impBP
	 * @param MBPartner bp
	 */
	private void setTypeOfBPartner(final I_I_BPartner impBP, final I_C_BPartner bp)
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
