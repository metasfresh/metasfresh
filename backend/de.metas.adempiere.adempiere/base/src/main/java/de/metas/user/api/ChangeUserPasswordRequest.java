package de.metas.user.api;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;

import de.metas.user.UserId;
import de.metas.util.hash.HashableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@ToString(exclude = { "newPassword", "newPasswordRetype" })
@Builder
public class ChangeUserPasswordRequest
{
	/** The user of which password we are changing */
	@NonNull
	final UserId userId;

	/** Old password entered by user */
	@Nullable
	final HashableString oldPassword;

	/** New password entered by user */
	@Nullable
	final String newPassword;

	/** New password retyped by user */
	@Nullable
	final String newPasswordRetype;

	//
	// Context
	@NonNull
	final ClientId contextClientId;
	/** i.e. logged in user */
	@NonNull
	final UserId contextUserId;
	@NonNull
	final LocalDate contextDate;
}
