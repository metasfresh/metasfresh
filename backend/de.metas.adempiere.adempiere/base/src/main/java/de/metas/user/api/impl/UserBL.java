package de.metas.user.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailCustomType;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.ui.web.WebuiURLs;
import de.metas.user.UserId;
import de.metas.user.api.ChangeUserPasswordRequest;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.hash.HashableString;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserBL implements IUserBL
{
	private static final transient Logger logger = LogManager.getLogger(UserBL.class);

	/**
	 * @see org.compiere.model.X_AD_MailConfig.CUSTOMTYPE_OrgCompiereUtilLogin
	 */
	private static final EMailCustomType MAILCONFIG_CUSTOMTYPE_UserPasswordReset = EMailCustomType.ofCode("L");

	private static final AdMessageKey MSG_INCORRECT_PASSWORD = AdMessageKey.of("org.compiere.util.Login.IncorrectPassword");
	private static final String SYS_MIN_PASSWORD_LENGTH = "org.compiere.util.Login.MinPasswordLength";

	private MailService mailService()
	{
		return SpringContextHolder.instance.getBean(MailService.class);
	}

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

	@Override
	public void createResetPasswordByEMailRequest(final String userId)
	{
		final IUserDAO usersRepo = Services.get(IUserDAO.class);

		final I_AD_User user = usersRepo.retrieveLoginUserByUserId(userId);
		try (final MDCCloseable userRecordMDC = TableRecordMDC.putTableRecordReference(user))
		{
			if (user.getAD_Client_ID() == ClientId.SYSTEM.getRepoId())
			{
				throw new AdempiereException("Reseting password for system users is not allowed");
			}
			createResetPasswordByEMailRequest(user);
		}
	}

	private void createResetPasswordByEMailRequest(@NonNull final I_AD_User user)
	{
		final EMailAddress emailTo = EMailAddress.ofNullableString(user.getEMail());
		if (emailTo == null)
		{
			logger.debug("AD_User.Email={} is empty", user.getEMail());
			throw new AdempiereException("@NoEMailFoundForLoginName@");
		}

		final IClientDAO adClientsRepo = Services.get(IClientDAO.class);
		final ClientId adClientId = ClientId.ofRepoId(user.getAD_Client_ID());
		final ClientEMailConfig tenantEmailConfig = adClientsRepo.getEMailConfigById(adClientId);

		final MailTemplateId mailTemplateId = tenantEmailConfig.getPasswordResetMailTemplateId().orElse(null);
		if (mailTemplateId == null)
		{
			logger.error("ClientEMailConfig for AD_Client_ID={} has no password reset mail template; ClientEMailConfig={}", adClientId.getRepoId(), tenantEmailConfig);
			throw new AdempiereException("Internal Error. Please contact the System Administrator.");
		}

		// Generate new Activation Code:
		final String passwordResetCode = generateAndSetPasswordResetCode(user);
		final String passwordResetURL = WebuiURLs.newInstance()
				.getResetPasswordUrl(passwordResetCode);

		final MailService mailService = mailService();
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);
		mailTextBuilder.customVariable("URL", passwordResetURL);
		mailTextBuilder.bpartnerContact(user);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(user.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
			mailTextBuilder.bpartner(bpartner);
		}

		final String subject = mailTextBuilder.getMailHeader();
		final EMail email = mailService.createEMail(
				tenantEmailConfig,
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
	public void changePassword(@NonNull final ChangeUserPasswordRequest request)
	{
		//
		// Make sure the new password and new password retype are matching
		if (!Objects.equals(request.getNewPassword(), request.getNewPasswordRetype()))
		{
			throw new AdempiereException("@NewPasswordNoMatch@");
		}

		//
		// Load the user
		final I_AD_User user = Services.get(IUserDAO.class).getByIdInTrx(request.getUserId());

		//
		// Make sure the old password is matching (if required)
		if (isOldPasswordRequired(request))
		{
			final HashableString oldPassword = request.getOldPassword();
			final HashableString userPassword = getUserPassword(user);
			if (HashableString.isEmpty(userPassword) && !HashableString.isEmpty(oldPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@")
						.setParameter("reason", "User does not have a password set. Please leave empty the OldPassword field.");
			}

			if (!userPassword.isMatching(oldPassword))
			{
				throw new AdempiereException("@OldPasswordNoMatch@");
			}
		}

		changePasswordAndSave(user, request.getNewPassword());
	}

	@Override
	public void changePasswordAndSave(final I_AD_User user, final String newPassword)
	{
		assertValidPassword(newPassword);

		final String newPasswordEncrypted = HashableString.ofPlainValue(newPassword).hash().getValue();
		user.setPassword(newPasswordEncrypted);

		InterfaceWrapperHelper.save(user);
	}

	private boolean isOldPasswordRequired(final ChangeUserPasswordRequest request)
	{
		// Changing your own password always requires entering the old password
		if (UserId.equals(request.getContextUserId(), request.getUserId()))
		{
			return true;
		}

		// If logged in as Administrator, there is no need to enter the old password
		final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
		if (userRolePermissionsDAO.isAdministrator(request.getContextClientId(), request.getContextUserId(), request.getContextDate()))
		{
			return false;
		}

		return true; // old password is required
	}

	private void assertValidPassword(final String passwordPlain)
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
	public boolean isEMailValid(final I_AD_User user)
	{
		// NOTE: even though AD_User.EMail is supposed to contain only one EMail and not ";" separated emails,
		// it seems that some of the users are already using like that.
		// For them we provide here this workaround which basically validates each of the email addresses,
		// and considers the AD_User.EMail valid only if all of them are valid.
		// see https://github.com/metasfresh/metasfresh/issues/1953

		try
		{
			final List<EMailAddress> emails = EMailAddress.ofSemicolonSeparatedList(user.getEMail());
			if (emails.isEmpty())
			{
				return false;
			}

			final boolean haveInvalidEMails = emails.stream().anyMatch(email -> EMailAddress.checkEMailValid(email.getAsString()) != null);
			return !haveInvalidEMails;
		}
		catch (Exception ex)
		{
			return false;
		}
	}    // isEMailValid

	@Override
	public ITranslatableString checkCanSendEMail(final UserEMailConfig userEmailConfig)
	{
		// Email
		{
			final ITranslatableString errmsg = EMailAddress.checkEMailValid(userEmailConfig.getEmail());
			if (errmsg != null)
			{
				return errmsg;
			}
		}

		// STMP user/password (if SMTP authorization is required)
		final ClientEMailConfig clientEmailConfig = Services.get(IClientDAO.class).getEMailConfigById(Env.getClientId());
		if (clientEmailConfig.isSmtpAuthorization())
		{
			// SMTP user
			final String emailUser = userEmailConfig.getUsername();
			if (Check.isEmpty(emailUser, true))
			{
				return TranslatableStrings.constant("no STMP user configured");
			}

			// SMTP password
			final String emailPassword = userEmailConfig.getPassword();
			if (Check.isEmpty(emailPassword, false))
			{
				return TranslatableStrings.constant("STMP authorization is required but no STMP password configured");
			}
		}

		return null; // OK
	}

	@Override
	public void assertCanSendEMail(@NonNull final UserId adUserId)
	{
		final UserEMailConfig userEmailConfig = getEmailConfigById(adUserId);
		final ITranslatableString errmsg = checkCanSendEMail(userEmailConfig);
		if (errmsg != null)
		{
			throw new AdempiereException(TranslatableStrings.builder()
					.append("User cannot send emails: ")
					.append(errmsg)
					.build())
					.appendParametersToMessage()
					.setParameter("AD_User_ID", adUserId.getRepoId())
					.setParameter("UserEMailConfig", userEmailConfig);
		}
	}

	@Override
	public Language getUserLanguage(@NonNull final I_AD_User userRecord)
	{
		final int bPartnerId = userRecord.getC_BPartner_ID();

		final String languageStr = CoalesceUtil.coalesceSuppliers(
				() -> userRecord.getAD_Language(),
				() -> bPartnerId > 0 ? userRecord.getC_BPartner().getAD_Language() : null,
				() -> Env.getADLanguageOrBaseLanguage());

		return Language.getLanguage(languageStr);
	}

	@Override
	public UserEMailConfig getEmailConfigById(@NonNull final UserId userId)
	{
		final I_AD_User userRecord = Services.get(IUserDAO.class).getById(userId);
		return toUserEMailConfig(userRecord);
	}

	public static UserEMailConfig toUserEMailConfig(@NonNull final I_AD_User userRecord)
	{
		return UserEMailConfig.builder()
				.userId(UserId.ofRepoId(userRecord.getAD_User_ID()))
				.email(EMailAddress.ofNullableString(userRecord.getEMail()))
				.username(userRecord.getEMailUser())
				.password(userRecord.getEMailUserPW())
				.build();
	}

}
