package de.metas.user.api;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import de.metas.util.hash.HashableString;
import lombok.NonNull;

public interface IUserBL extends ISingletonService
{
	HashableString getUserPassword(I_AD_User user);

	boolean isPasswordMatching(I_AD_User user, HashableString password);

	void createResetPasswordByEMailRequest(I_AD_User user);

	void createResetPasswordByEMailRequest(String userId);

	I_AD_User resetPassword(String passwordResetCode, String newPassword);

	/**
	 * Change given user's password.
	 */
	void changePassword(ChangeUserPasswordRequest request);

	void changePasswordAndSave(I_AD_User user, String newPassword);

	String buildContactName(final String firstName, final String lastName);

	/**
	 * Is the email valid
	 *
	 * @return return true if email is valid (artificial check)
	 */
	boolean isEMailValid(I_AD_User user);

	/**
	 * Could we send an email from this user
	 *
	 * @return <code>null</code> if OK, error message if not ok
	 */
	ITranslatableString checkCanSendEMail(I_AD_User user);

	ITranslatableString checkCanSendEMail(int adUserId);

	default void assertCanSendEMail(@NonNull final UserId adUserId)
	{
		final ITranslatableString errmsg = checkCanSendEMail(adUserId.getRepoId());
		if (errmsg != null)
		{
			throw new AdempiereException(TranslatableStringBuilder.newInstance()
					.append("User cannot send emails: ")
					.append(errmsg)
					.build());
		}
	}

	/** @return the user's language or fallbacks; never returns {@code null}. */
	Language getUserLanguage(I_AD_User userRecord);
}
