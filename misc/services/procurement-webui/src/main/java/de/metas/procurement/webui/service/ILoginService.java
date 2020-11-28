package de.metas.procurement.webui.service;

import java.net.URI;

import de.metas.procurement.webui.exceptions.LoginFailedException;
import de.metas.procurement.webui.model.User;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface ILoginService
{
	/**
	 * @param email
	 * @param password
	 * @return logged in user
	 * @throws LoginFailedException in case of failure
	 */
	User login(String email, String password);

	/**
	 * Generate a password reset key and link it to the user.
	 * 
	 * @param email
	 * @return generated password reset key
	 */
	String generatePasswordResetKey(String email);

	/**
	 * Send an email to given user about how he/she can reset his/her password.
	 * 
	 * @param email
	 * @param passwordResetURI
	 */
	void sendPasswordResetKey(String email, URI passwordResetURI);

	/**
	 * Reset user's password for given password reset key.
	 * 
	 * @param resetKey
	 * @return user of which password was reset.
	 */
	User resetPassword(String resetKey);

	String createPasswordHash(String password);
}
