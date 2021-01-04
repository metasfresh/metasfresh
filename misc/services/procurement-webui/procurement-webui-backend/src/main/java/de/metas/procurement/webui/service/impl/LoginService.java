package de.metas.procurement.webui.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

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

@Service
public class LoginService implements ILoginService
{
	// @Autowired
    // private I18N i18n;
	
	@Autowired
	private UserRepository userRepository;

	// @Autowired
	// @Lazy
	// private JavaMailSender emailSender;
	
	@Value("${mfprocurement.mail.from:}")
	private String emailFrom;
	
	private static final String PASSWORD_CHARS = "0123456789";
	private static final int PASSWORD_Length = 4; 

	private final User getUserByMail(final String email)
	{
		final List<User> users = userRepository.findByEmailAndDeletedFalse(email);
		if (users.isEmpty())
		{
			return null;
		}

		if (users.size() > 1)
		{
			return null;
		}
		final User user = users.get(0);
		return user;
	}
	
	private final User getUserByPasswordResetKey(final String passwordResetKey)
	{
		final List<User> users = userRepository.findByPasswordResetKeyAndDeletedFalse(passwordResetKey);
		if (users.isEmpty())
		{
			return null;
		}

		if (users.size() > 1)
		{
			return null;
		}
		final User user = users.get(0);
		return user;
	}


	@Override
	public User login(final String email, final String password)
	{
		//
		// Get the user
		final User user = getUserByMail(email);
		// if (user == null)
		// {
		// 	throw new LoginFailedException(email);
		// }
		//
		// //
		// // Validate the password
		// final String userPassword = user.getPassword();
		// if (Strings.isNullOrEmpty(userPassword))
		// {
		// 	// empty user passwords are considered as no password was set
		// 	throw new LoginFailedException(email);
		// }
		// if (!Objects.equals(userPassword, password)
		// 		&& !Objects.equals(createPasswordHash(userPassword), password) // support for hashed password (also used by remember-me functionality
		// 	)
		// {
		// 	throw new LoginFailedException(email);
		// }

		return user;
	}
	
	@Override
	public final String createPasswordHash(final String password)
	{
		return Hashing.sha256()
				.hashString(password, StandardCharsets.UTF_8)
				.toString();
		
	}

	@Override
	public void sendPasswordResetKey(final String email, final URI passwordResetURI)
	{
		Preconditions.checkNotNull(passwordResetURI, "passwordResetURI is null");
		
		// MimeMessage mail = emailSender.createMimeMessage();
		// try
		// {
		// 	MimeMessageHelper helper = new MimeMessageHelper(mail, true); // multipart=true
		//
		// 	if(emailFrom != null && !emailFrom.trim().isEmpty())
		// 	{
		// 		helper.setFrom(emailFrom.trim());
		// 	}
		// 	helper.setTo(email);
		// 	helper.setSubject(i18n.get("PasswordReset.email.subject"));
		// 	helper.setText(i18n.get("PasswordReset.email.content", passwordResetURI));
		// }
		// catch (MessagingException e)
		// {
		// 	e.printStackTrace();
		// }
		// finally
		// {
		// }
		// emailSender.send(mail);
	}

	@Override
	@Transactional
	public String generatePasswordResetKey(final String email)
	{
		final User user = getUserByMail(email);
		if (user == null)
		{
			// throw new PasswordResetFailedException(email, i18n.get("LoginService.error.emailNotRegistered"));
		}

		//
		// Generate a password reset key and set it on user
		final String passwordResetKey = UUID.randomUUID().toString();
		user.setPasswordResetKey(passwordResetKey);
		userRepository.save(user);

		return passwordResetKey;
	}

	@Override
	@Transactional
	public User resetPassword(final String passwordResetKey)
	{
		final User user = getUserByPasswordResetKey(passwordResetKey);
		if (user == null)
		{
			// throw new PasswordResetFailedException(null, i18n.get("LoginService.error.invalidPasswordResetKey"));
		}
		
		final String passwordNew = generatePassword(PASSWORD_Length);
		user.setPassword(passwordNew);
		user.setPasswordResetKey(null);
		userRepository.save(user);
		
		return user;
	}
	
	private final String generatePassword(final int passwordLength)
	{
		Preconditions.checkArgument(passwordLength >= 4, "paswordLength shall be at least 4");
		final StringBuilder password = new StringBuilder();
		final SecureRandom random = new SecureRandom();
		final int poolLength = PASSWORD_CHARS.length();
		for (int i = 0; i < passwordLength; i++)
		{
			final int charIndex = random.nextInt(poolLength);
			final char ch = PASSWORD_CHARS.charAt(charIndex);
			password.append(ch);
		}
		
		return password.toString();
	}
}
