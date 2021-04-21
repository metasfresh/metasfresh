package de.metas.invoicecandidate.exceptions;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.Language;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;

/**
 * Model validators can throw this exception if they want to prohibit an update that would lead to inconsistencies. If
 * the update is performed by a scheduled process, that process can use the given <code>adUserToNotifyId</code> to
 * notify whoever is in charge of the problem.
 *
 * @author ts
 */
public class InconsistentUpdateException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7452910227523552339L;

	@NonNull private final I_AD_User userToNotify;

	@Getter
	private final AdMessageKey adMessageHeadLine;

	public InconsistentUpdateException(
			@NonNull final AdMessageKey adMessageHeadLine,
			@NonNull final AdMessageKey adMessage,
			final Object[] messageParams,
			@NonNull final I_AD_User userToNotify)
	{
		super(getLanguage(userToNotify), adMessage, messageParams);

		this.adMessageHeadLine = adMessageHeadLine;
		this.userToNotify = userToNotify;
	}

	private static String getLanguage(@NonNull final I_AD_User userToNotify)
	{
		final IUserBL userBL = Services.get(IUserBL.class);
		final Language adLanguage = userBL.getUserLanguage(userToNotify);
		return adLanguage.getAD_Language();
	}

	public int getAdUserToNotifyId()
	{
		return userToNotify.getAD_User_ID();
	}
}
