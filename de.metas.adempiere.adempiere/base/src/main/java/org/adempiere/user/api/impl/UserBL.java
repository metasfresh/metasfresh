package org.adempiere.user.api.impl;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.user.api.UserNotificationsConfig;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_AD_User;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Client;
import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;

public class UserBL implements IUserBL
{
	private static final transient Logger logger = LogManager.getLogger(UserBL.class);

	private final String passwordCharset = "0123456789";
	private final int passwordLength = 6;

	/**
	 * @see org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_OrgCompiereUtilLogin
	 */
	private static final String CUSTOMTYPE_OrgCompiereUtilLogin = "L";

	private static final String MSG_INCORRECT_PASSWORD = "org.compiere.util.Login.IncorrectPassword";
	private static final String SYS_MIN_PASSWORD_LENGTH = "org.compiere.util.Login.MinPasswordLength";

	public UserBL()
	{
		super();
	}

	private final String generatePassword()
	{
		final Random rand = new Random(System.currentTimeMillis());

		int passwordLength = this.passwordLength;
		final int minPasswordLength = getMinPasswordLength();
		if (minPasswordLength > 0 && passwordLength < minPasswordLength)
		{
			passwordLength = minPasswordLength;
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < passwordLength; i++)
		{
			final int pos = rand.nextInt(passwordCharset.length());
			sb.append(passwordCharset.charAt(pos));
		}
		return sb.toString();
	}

	@Override
	public String generatedAndSetPassword(final I_AD_User user)
	{
		final String newPassword = generatePassword();
		user.setPassword(newPassword);
		InterfaceWrapperHelper.save(user);
		return newPassword;
	}

	@Override
	public void createResetPasswordByEMailRequest(final String userId)
	{
		final I_AD_User user = Services.get(IUserDAO.class).retrieveLoginUserByUserId(userId);
		if (user.getAD_Client_ID() == Env.CTXVALUE_AD_Client_ID_System)
		{
			throw new AdempiereException("Reseting password for system users is not allowed");
		}
		createResetPasswordByEMailRequest(user);
	}

	@Override
	public void createResetPasswordByEMailRequest(final I_AD_User user)
	{
		final String emailTo = user.getEMail();
		if (Check.isEmpty(emailTo, true))
		{
			throw new AdempiereException("@NoEMailFoundForLoginName@");
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(user);

		final I_AD_Client client = InterfaceWrapperHelper.create(
				Services.get(IClientDAO.class).retriveClient(ctx, user.getAD_Client_ID()), I_AD_Client.class);
		if (client.getPasswordReset_MailText_ID() <= 0)
		{
			logger.error("@NotFound@ @AD_Client_ID@/@PasswordReset_MailText_ID@ (@AD_User_ID@:" + user + ")");
			throw new AdempiereException("Internal Error. Please contact the System Administrator.");
		}

		final IMailBL mailService = Services.get(IMailBL.class);
		final IMailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(client.getPasswordReset_MailText());
		final String subject = mailTextBuilder.getMailHeader();

		final EMail email = mailService.createEMail(client, CUSTOMTYPE_OrgCompiereUtilLogin // mailCustomType
				, null // from email
				, emailTo // to
				, subject, null // message=null, we will set it later
				, true // html
		);

		// NOTE: don't validate at this level. If we validate it here, no error messages will be available
		// if (!email.isValid())
		// {
		// throw new AdempiereException(email.getSentMsg());
		// }

		// Generate new Activation Code:
		final String passwordResetURL = generatePasswordResetURL(user);
		mailTextBuilder.setCustomVariable("URL", passwordResetURL);

		mailTextBuilder.setAD_User(user);
		if (user.getC_BPartner_ID() > 0)
		{
			mailTextBuilder.setC_BPartner(user.getC_BPartner());
		}

		final String message = mailTextBuilder.getFullMailText();
		if (mailTextBuilder.isHtml())
		{
			email.setMessageHTML(subject, message);
		}
		else
		{
			email.setSubject(subject);
			email.setMessageText(message);
		}

		mailService.send(email);
	}

	private String generatePasswordResetURL(final I_AD_User user)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(user);
		final String webURL = Services.get(ISystemBL.class).get(ctx).getWebUI_URL();
		if (Check.isEmpty(webURL, true))
		{
			throw new AdempiereException("@NotFound@ @AD_System@/@WebUI_URL@");
		}

		final String passwordResetCode = generatePasswordResetCode(user);

		final String url = webURL + "?"
				+ PARAM_ACTION + "=" + ACTION_ResetPassword
				+ "&" + PARAM_AccountPasswordResetCode + "=" + passwordResetCode;
		return url;
	}

	/**
	 * Generates and set {@link I_AD_User#COLUMNNAME_PasswordResetCode}.
	 *
	 * @param user
	 * @return generated password reset code
	 */
	private static String generatePasswordResetCode(final I_AD_User user)
	{
		final MessageDigest digest;
		try
		{
			digest = MessageDigest.getInstance("MD5");
		}
		catch (final NoSuchAlgorithmException e)
		{
			throw new AdempiereException(e);
		}
		digest.reset();

		final String seed = user.getName() + "#" + user.getAD_User_ID() + "#" + System.currentTimeMillis();
		final byte[] arr = digest.digest(seed.getBytes());
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++)
		{
			sb.append(Util.toHex(arr[i]));
		}
		final String passwordResetCode = sb.toString();

		//
		// Update user
		final de.metas.adempiere.model.I_AD_User userExt = InterfaceWrapperHelper.create(user, de.metas.adempiere.model.I_AD_User.class);
		userExt.setPasswordResetCode(passwordResetCode);
		InterfaceWrapperHelper.save(user);

		return passwordResetCode;
	}

	@Override
	public I_AD_User resetPassword(final Properties ctx, final int adUserId, final String passwordResetCode, final String newPassword)
	{
		Check.assumeNotNull(passwordResetCode, "passwordResetCode not null");

		final IUserDAO userDAO = Services.get(IUserDAO.class);

		final de.metas.adempiere.model.I_AD_User user = InterfaceWrapperHelper.create(
				userDAO.retrieveUserByPasswordResetCode(ctx, passwordResetCode),
				de.metas.adempiere.model.I_AD_User.class);

		if (user == null)
		{
			throw new AdempiereException("@PasswordResetCodeNoLongerValid@");
		}
		if (adUserId != user.getAD_User_ID())
		{
			throw new AdempiereException("@PasswordResetCodeNoLongerValid@");
		}
		if (!passwordResetCode.equals(user.getPasswordResetCode()))
		{
			throw new AdempiereException("@PasswordResetCodeNoLongerValid@");
		}

		user.setPassword(newPassword);
		user.setPasswordResetCode(null);
		InterfaceWrapperHelper.save(user);

		return user;
	}

	@Override
	public void changePassword(final Properties ctx, final int adUserId, final String oldPassword, final String newPassword, final String newPasswordRetype)
	{
		//
		// Make sure the new password and new password retype are matching
		if (!Objects.equals(newPassword, newPasswordRetype))
		{
			throw new AdempiereException("@NewPasswordNoMatch@");
		}

		//
		// Load the user
		final I_AD_User user = InterfaceWrapperHelper.load(adUserId, I_AD_User.class);

		//
		// Make sure the old password is matching (if required)
		if (isOldPasswordRequired(ctx, adUserId))
		{
			final String userPassword = user.getPassword();
			if (Check.isEmpty(userPassword) && !Check.isEmpty(oldPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@")
						.setParameter("reason", "User does not have a password set. Please leave empty the OldPassword field.");
			}

			if (!Objects.equals(oldPassword, userPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@");
			}
		}

		assertValidPassword(newPassword);

		user.setPassword(newPassword);
		InterfaceWrapperHelper.save(user);
	}

	private boolean isOldPasswordRequired(final Properties ctx, final int adUserId)
	{
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		final IUserRolePermissions loggedInPermissions = userRolePermissionsDAO.retrieveUserRolePermissions(UserRolePermissionsKey.of(ctx));

		// Changing your own password always requires entering the old password
		if (loggedInPermissions.getAD_User_ID() == adUserId)
		{
			return true;
		}

		// If logged in as Administrator, there is no need to enter the old password
		if (userRolePermissionsDAO.isAdministrator(ctx, adUserId))
		{
			return false;
		}

		return true; // old password is required
	}

	@Override
	public void assertValidPassword(final String password)
	{
		final int minPasswordLength = getMinPasswordLength();
		if (Check.isEmpty(password))
		{
			throw new AdempiereException(MSG_INCORRECT_PASSWORD, new Object[] { minPasswordLength })
					.setParameter("reason", "empty/null password");
		}

		if (password.contains(" "))
		{
			throw new AdempiereException(MSG_INCORRECT_PASSWORD, new Object[] { minPasswordLength })
					.setParameter("reason", "spaces are not allowed");
		}

		if (password.length() < minPasswordLength)
		{
			throw new AdempiereException(MSG_INCORRECT_PASSWORD, new Object[] { minPasswordLength });
		}
	}

	private int getMinPasswordLength()
	{
		return Services.get(ISysConfigBL.class).getIntValue(SYS_MIN_PASSWORD_LENGTH, 8);
	}

	@Override
	public boolean isEmployee(final org.compiere.model.I_AD_User user)
	{
		if (user == null)
		{
			return false;
		}

		// User does not have a BP => we consider not an employee
		if (user.getC_BPartner_ID() <= 0)
		{
			return false;
		}

		final I_C_BPartner bpartner = user.getC_BPartner();
		return bpartner.isEmployee();
	}

	@Override
	public String buildContactName(final String firstName, final String lastName)
	{
		final StringBuilder contactName = new StringBuilder();
		if (!Check.isEmpty(lastName, true))
		{
			contactName.append(lastName.trim());
		}

		if (!Check.isEmpty(firstName, true))
		{
			if (contactName.length() > 0)
			{
				contactName.append(", ");
			}
			contactName.append(firstName.trim());
		}

		return contactName.toString();
	}

	@Override
	public I_AD_User createUser(final String name, final I_AD_Org org)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(org, true);
		final String trxName = InterfaceWrapperHelper.getTrxName(org);
		final I_AD_User user = InterfaceWrapperHelper.create(ctx, I_AD_User.class, trxName);
		user.setName(name);
		user.setAD_Org_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(user);

		return user;
	}

	@Override
	public boolean isNotificationEMail(final I_AD_User user)
	{
		final String s = user.getNotificationType();
		return s == null || X_AD_User.NOTIFICATIONTYPE_EMail.equals(s)
				|| X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(s);
	}

	@Override
	public boolean isNotificationNote(final I_AD_User user)
	{
		final String s = user.getNotificationType();
		return s != null && (X_AD_User.NOTIFICATIONTYPE_Notice.equals(s)
				|| X_AD_User.NOTIFICATIONTYPE_EMailPlusNotice.equals(s));
	}

	@Override
	public boolean isNotifyUserIncharge(final I_AD_User user)
	{
		return de.metas.adempiere.model.I_AD_User.NOTIFICATIONTYPE_NotifyUserInCharge.equals(user.getNotificationType());
	}

	@Override
	public boolean isEMailValid(final I_AD_User user)
	{
		// NOTE: even though AD_User.EMail is supposed to contain only one EMail and not ";" separated emails,
		// it seems that some of the users are already using like that.
		// For them we provide here this workaround which basically validates each of the email addresses,
		// and considers the AD_User.EMail valid only if all of them are valid.
		// see https://github.com/metasfresh/metasfresh/issues/1953

		final String emailsListStr = user.getEMail();
		final List<String> emails = EMail.toEMailsList(emailsListStr);
		if (emails.isEmpty())
		{
			return false;
		}

		final boolean haveInvalidEMails = emails.stream().anyMatch(email -> checkEMailValid(email) != null);
		return !haveInvalidEMails;
	}	// isEMailValid

	private static ITranslatableString checkEMailValid(final String email)
	{
		if (Check.isEmpty(email, true))
		{
			return ITranslatableString.constant("no email");
		}
		try
		{
			final InternetAddress ia = new InternetAddress(email, true);
			ia.validate();	// throws AddressException

			if (ia.getAddress() == null)
			{
				return ITranslatableString.constant("invalid email");
			}

			return null; // OK
		}
		catch (AddressException ex)
		{
			logger.warn("Invalid email address: {}", email, ex);
			return ITranslatableString.constant(ex.getLocalizedMessage());
		}
	}

	@Override
	public ITranslatableString checkCanSendEMail(final I_AD_User user)
	{
		// Email
		{
			final ITranslatableString errmsg = checkEMailValid(user.getEMail());
			if (errmsg != null)
			{
				return errmsg;
			}
		}

		// STMP user/password (if SMTP authorization is required)
		if (Services.get(IClientDAO.class).retriveClient(Env.getCtx()).isSmtpAuthorization())
		{
			// SMTP user
			final String emailUser = user.getEMailUser();
			if (Check.isEmpty(emailUser, true))
			{
				return ITranslatableString.constant("no STMP user configured");
			}

			// SMTP password
			final String emailPassword = user.getEMailUserPW();
			if (Check.isEmpty(emailPassword, false))
			{
				return ITranslatableString.constant("STMP authorization is required but no STMP password configured");
			}
		}

		return null; // OK
	}

	@Override
	public ITranslatableString checkCanSendEMail(final int adUserId)
	{
		final I_AD_User user = Services.get(IUserDAO.class).retrieveUser(adUserId);
		return checkCanSendEMail(user);
	}

	@Override
	public UserNotificationsConfig getUserNotificationsConfig(final int adUserId)
	{
		return Services.get(IUserDAO.class).getUserNotificationsConfig(adUserId);
	}

}
