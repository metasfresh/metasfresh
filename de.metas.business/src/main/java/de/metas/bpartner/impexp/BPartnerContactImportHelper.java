package de.metas.bpartner.impexp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.impexp.BPartnersCache.BPartner;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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

/* package */ class BPartnerContactImportHelper
{
	public static BPartnerContactImportHelper newInstance()
	{
		return new BPartnerContactImportHelper();
	}

	// services
	private final IUserBL userBL = Services.get(IUserBL.class);
	private BPartnerImportProcess process;

	private BPartnerContactImportHelper()
	{
	}

	public BPartnerContactImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	public void importRecord(@NonNull final BPartnerImportContext context)
	{
		final I_I_BPartner importRecord = context.getCurrentImportRecord();
		final String importContactName = userBL.buildContactName(importRecord.getFirstname(), importRecord.getLastname());

		final BPartner bpartner = context.getCurrentBPartner();

		final BPartnerContactId existingContactId = context.getCurrentBPartnerContactIdOrNull();
		I_AD_User contact = existingContactId != null
				? bpartner.getContactById(existingContactId).orElse(null)
				: null;

		//
		// Existing contact
		if (contact != null)
		{
			if (contact.getC_BPartner_ID() <= 0)
			{
				contact.setC_BPartner_ID(bpartner.getIdOrNull().getRepoId());
			}
			else if (contact.getC_BPartner_ID() != bpartner.getIdOrNull().getRepoId())
			{
				throw new AdempiereException("BP of User <> BP");
			}

			if (importRecord.getC_Greeting_ID() > 0)
			{
				contact.setC_Greeting_ID(importRecord.getC_Greeting_ID());
			}
			if (importRecord.getC_Job_ID() > 0)
			{
				contact.setC_Job_ID(importRecord.getC_Job_ID());
			}
			contact.setName(Check.isEmpty(importContactName, true) ? importRecord.getEMail() : importContactName);
			updateWithAvailableImportRecordFields(importRecord, contact);
		}
		//
		// New Contact
		else if (!Check.isEmpty(importContactName, true)
				|| !Check.isEmpty(importRecord.getEMail(), true))
		{
			contact = InterfaceWrapperHelper.newInstance(I_AD_User.class);
			contact.setAD_Org_ID(bpartner.getOrgId());
			contact.setC_BPartner_ID(bpartner.getIdOrNull().getRepoId());

			if (importRecord.getC_Greeting_ID() > 0)
			{
				contact.setC_Greeting_ID(importRecord.getC_Greeting_ID());
			}
			if (importRecord.getC_Job_ID() > 0)
			{
				contact.setC_Job_ID(importRecord.getC_Job_ID());
			}
			contact.setName(Check.isEmpty(importContactName, true) ? importRecord.getEMail() : importContactName);

			updateWithImportRecordFields(importRecord, contact);
		}

		//
		//
		if (contact != null)
		{
			final BPartnerLocationId bpLocationId = context.getCurrentBPartnerLocationIdOrNull();
			if (bpLocationId != null)
			{
				contact.setC_BPartner_Location_ID(bpLocationId.getRepoId());
			}

			ModelValidationEngine.get().fireImportValidate(process, importRecord, contact, IImportInterceptor.TIMING_AFTER_IMPORT);

			final BPartnerContactId contactId = bpartner.addAndSaveContact(contact);
			context.setCurrentBPartnerContactId(contactId);
		}
	}

	/**
	 * Similar to {@link #updateWithAvailableImportRecordFields(I_I_BPartner, I_AD_User)}, but also {@code null} values are copied from the given {@code importRecord}.
	 *
	 * @param importRecord
	 * @param user
	 */
	private static void updateWithImportRecordFields(final I_I_BPartner importRecord, final I_AD_User user)
	{
		user.setExternalId(importRecord.getAD_User_ExternalId());
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
		setUserMemoFields(importRecord, user);
	}

	/**
	 * If a particular field is set in the given {@code importRecord}, the given {@code user} user's respective file is updated.
	 *
	 * @param importRecord
	 * @param user
	 */
	private static void updateWithAvailableImportRecordFields(@NonNull final I_I_BPartner importRecord, @NonNull final I_AD_User user)
	{
		user.setFirstname(importRecord.getFirstname());
		user.setLastname(importRecord.getLastname());

		final String userExternalId = importRecord.getAD_User_ExternalId();
		if (userExternalId != null)
		{
			user.setExternalId(userExternalId);
		}
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

		setUserMemoFields(importRecord, user);
		setDefaultFlagsForContact(importRecord, user);
	}

	private static void setUserMemoFields(@NonNull final I_I_BPartner importRecord, @NonNull final I_AD_User user)
	{
		setUserMemo(user, importRecord.getAD_User_Memo1());
		setUserMemo(user, importRecord.getAD_User_Memo2());
		setUserMemo(user, importRecord.getAD_User_Memo3());
		setUserMemo(user, importRecord.getAD_User_Memo4());
	}

	private static void setUserMemo(@NonNull final I_AD_User user, final String newMemoText)
	{
		if (!Check.isEmpty(newMemoText, true))
		{
			if (Check.isEmpty(user.getMemo(), true))
			{
				user.setMemo(newMemoText);
			}
			else
			{
				user.setMemo(user.getMemo()
						.concat("\n")
						.concat(newMemoText));
			}
		}
	}

	private static void setDefaultFlagsForContact(@NonNull final I_I_BPartner importRecord, @NonNull final I_AD_User user)
	{
		user.setIsDefaultContact(importRecord.isDefaultContact());
		user.setIsBillToContact_Default(importRecord.isBillToContact_Default());
		user.setIsShipToContact_Default(importRecord.isShipToContact_Default());
	}
}
