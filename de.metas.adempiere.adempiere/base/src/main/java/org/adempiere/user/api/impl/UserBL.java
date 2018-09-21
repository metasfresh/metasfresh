package org.adempiere.user.api.impl;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.api.IUserBL;
import org.adempiere.user.api.IUserDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.email.EMail;
import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;
import de.metas.hash.HashableString;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.WebuiURLs;
import de.metas.util.Check;
import de.metas.util.Services;

public class UserBL implements IUserBL
{
	private static final transient Logger logger = LogManager.getLogger(UserBL.class);

	private final String passwordCharset = "0123456789";
	private final int passwordLength = 6;

	/**
	 * @see org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_OrgCompiereUtilLogin
	 */
	private static final String MAILCONFIG_CUSTOMTYPE_UserPasswordReset = "L";

	private static final String MSG_INCORRECT_PASSWORD = "org.compiere.util.Login.IncorrectPassword";
	private static final String SYS_MIN_PASSWORD_LENGTH = "org.compiere.util.Login.MinPasswordLength";

	@Override
	public HashableString getUserPassword(final I_AD_User user)
	{
		return HashableString.fromString(user.getPassword());
	}

	@Override
	public boolean isPasswordMatching(final I_AD_User user, final HashableString password)
	{
		if (password == null || password.isEmpty())
		{
			return false;
		}

		final HashableString userPassword = getUserPassword(user);
		if (userPassword == null || userPassword.isEmpty())
		{
			return false;
		}

		return userPassword.isMatching(password);
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
		changePasswordAndSave(user, newPassword);
		return newPassword;
	}

	@Override
	public void createResetPasswordByEMailRequest(final String userId)
	{
		final IUserDAO usersRepo = Services.get(IUserDAO.class);

		final I_AD_User user = usersRepo.retrieveLoginUserByUserId(userId);
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

		final IClientDAO adClientsRepo = Services.get(IClientDAO.class);
		final int adClientId = user.getAD_Client_ID();
		final I_AD_Client adClient = adClientsRepo.getById(adClientId);
		if (adClient.getPasswordReset_MailText_ID() <= 0)
		{
			logger.error("@NotFound@ @AD_Client_ID@/@PasswordReset_MailText_ID@ (@AD_User_ID@:" + user + ", @AD_Client_ID@: " + adClientId + ")");
			throw new AdempiereException("Internal Error. Please contact the System Administrator.");
		}

		// Generate new Activation Code:
		final String passwordResetCode = generateAndSetPasswordResetCode(user);
		final String passwordResetURL = WebuiURLs.newInstance()
				.getResetPasswordUrl(passwordResetCode);

		final IMailBL mailService = Services.get(IMailBL.class);
		final IMailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(adClient.getPasswordReset_MailText());
		mailTextBuilder.setCustomVariable("URL", passwordResetURL);
		mailTextBuilder.setAD_User(user);
		if (user.getC_BPartner_ID() > 0)
		{
			mailTextBuilder.setC_BPartner(user.getC_BPartner());
		}

		final String subject = mailTextBuilder.getMailHeader();
		final EMail email = mailService.createEMail(
				adClient,
				MAILCONFIG_CUSTOMTYPE_UserPasswordReset, // mailCustomType
				null, // from email
				emailTo, // to
				subject,
				null, // message=null, we will set it later
				true // html
		);

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

	/**
	 * Generates and set user's password reset code.
	 * 
	 * @return generated password reset code
	 */
	private static String generateAndSetPasswordResetCode(final I_AD_User user)
	{
		final String passwordResetCode = UUID.randomUUID().toString();

		//
		// Update user
		user.setPasswordResetCode(passwordResetCode);
		InterfaceWrapperHelper.save(user);

		return passwordResetCode;
	}

	@Override
	public I_AD_User resetPassword(final String passwordResetCode, final String newPassword)
	{
		Check.assumeNotNull(passwordResetCode, "passwordResetCode not null");

		final IUserDAO usersRepo = Services.get(IUserDAO.class);
		final I_AD_User user = usersRepo.getByPasswordResetCode(passwordResetCode);

		user.setPasswordResetCode(null);
		changePasswordAndSave(user, newPassword);

		return user;
	}

	@Override
	public void changePassword(final Properties ctx, final int adUserId, final HashableString oldPassword, final String newPassword, final String newPasswordRetype)
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
			final HashableString userPassword = getUserPassword(user);
			if (HashableString.isEmpty(userPassword) && !HashableString.isEmpty(oldPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@")
						.setParameter("reason", "User does not have a password set. Please leave empty the OldPassword field.");
			}

			if (!Objects.equals(oldPassword, userPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@");
			}
		}

		changePasswordAndSave(user, newPassword);
	}

	@Override
	public void changePasswordAndSave(final I_AD_User user, final String newPassword)
	{
		assertValidPassword(newPassword);

		final String newPasswordEncrypted = HashableString.ofPlainValue(newPassword).hash().getValue();
		user.setPassword(newPasswordEncrypted);

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
	public void assertValidPassword(final String passwordPlain)
	{
		final int minPasswordLength = getMinPasswordLength();
		if (Check.isEmpty(passwordPlain))
		{
			throw new AdempiereException(MSG_INCORRECT_PASSWORD, new Object[] { minPasswordLength })
					.setParameter("reason", "empty/null password");
		}

		if (passwordPlain.contains(" "))
		{
			throw new AdempiereException(MSG_INCORRECT_PASSWORD, new Object[] { minPasswordLength })
					.setParameter("reason", "spaces are not allowed");
		}

		if (passwordPlain.length() < minPasswordLength)
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
}
