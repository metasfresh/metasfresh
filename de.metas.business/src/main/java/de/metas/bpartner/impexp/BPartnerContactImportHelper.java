package de.metas.bpartner.impexp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;
import org.slf4j.Logger;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.logging.LogManager;
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
	private static final Logger logger = LogManager.getLogger(BPartnerContactImportHelper.class);
	private final transient IUserBL userBL = Services.get(IUserBL.class);

	private BPartnerImportProcess process;

	private BPartnerContactImportHelper()
	{
	}

	public BPartnerContactImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	public I_AD_User importRecord(final I_I_BPartner importRecord)
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
			if (importRecord.getC_Job_ID() > 0)
			{
				user.setC_Job_ID(importRecord.getC_Job_ID());
			}
			user.setName(Check.isEmpty(importContactName, true) ? importRecord.getEMail() : importContactName);
			updateWithAvailableImportRecordFields(importRecord, user);

			if (bpartnerLocation != null)
			{
				user.setC_BPartner_Location(bpartnerLocation);
			}

			ModelValidationEngine.get().fireImportValidate(process, importRecord, user, IImportInterceptor.TIMING_AFTER_IMPORT);
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
			if (importRecord.getC_Job_ID() > 0)
			{
				user.setC_Job_ID(importRecord.getC_Job_ID());
			}
			user.setName(Check.isEmpty(importContactName, true) ? importRecord.getEMail() : importContactName);
			updateWithImportRecordFields(importRecord, user);
			if (bpartnerLocation != null)
			{
				user.setC_BPartner_Location_ID(bpartnerLocation.getC_BPartner_Location_ID());
			}
			ModelValidationEngine.get().fireImportValidate(process, importRecord, user, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(user);
			logger.trace("Insert BP Contact - {}", user);

			importRecord.setAD_User(user);
		}

		return user;
	}

	/**
	 * Similar to {@link #updateWithAvailableImportRecordFields(I_I_BPartner, I_AD_User)}, but also {@code null} values are copied from the given {@code importRecord}.
	 *
	 * @param importRecord
	 * @param user
	 */
	private static void updateWithImportRecordFields(final I_I_BPartner importRecord, final I_AD_User user)
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