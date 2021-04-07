package de.metas.procurement.webui.service.impl;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import de.metas.procurement.webui.config.ProcurementWebuiProperties;
import de.metas.procurement.webui.exceptions.LoginFailedException;
import de.metas.procurement.webui.exceptions.NotLoggedInException;
import de.metas.procurement.webui.exceptions.PasswordResetFailedException;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.repository.UserRepository;
import de.metas.procurement.webui.service.I18N;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.util.LanguageKey;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nullable;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
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
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	private final UserRepository userRepository;
	private final JavaMailSender emailSender;
	private final I18N i18n;

	private final String emailFrom;
	private final String passwordResetUrl;

	private static final String HTTP_SESSION_loggedIn = "loggedIn";
	private static final String HTTP_SESSION_loggedUserId = "loggedUserId";
	private static final String PASSWORD_CHARS = "0123456789";
	private static final int PASSWORD_Length = 4;

	public LoginService(
			@NonNull final ProcurementWebuiProperties config,
			@NonNull final UserRepository userRepository,
			@NonNull final JavaMailSender emailSender,
			@NonNull final I18N i18n)
	{
		this.userRepository = userRepository;
		this.emailSender = emailSender;
		this.i18n = i18n;

		this.emailFrom = config.getMail().getFrom();
		logger.info("emailFrom={}", emailFrom);

		this.passwordResetUrl = config.getPasswordResetUrl();
		logger.info("passwordResetUrl={}", passwordResetUrl);
	}

	private HttpSession getCurrentHttpSession()
	{
		final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return servletRequestAttributes.getRequest().getSession();
	}

	@Nullable
	private User getUserByMail(final String email)
	{
		final List<User> users = userRepository.findByEmailAndDeletedFalse(email);
		if (users.isEmpty())
		{
			return null;
		}
		else if (users.size() > 1)
		{
			return null;
		}
		else
		{
			return users.get(0);
		}
	}

	@Nullable
	private User getUserByPasswordResetKey(final String passwordResetKey)
	{
		final List<User> users = userRepository.findByPasswordResetKeyAndDeletedFalse(passwordResetKey);
		if (users.isEmpty())
		{
			return null;
		}
		else if (users.size() > 1)
		{
			return null;
		}
		else
		{
			return users.get(0);
		}
	}

	@Override
	public User login(final String email, final String password)
	{
		if (isLoggedIn())
		{
			throw new IllegalStateException("Already logged in");
		}

		final User user = authenticate(email, password);
		login(user);

		return user;
	}

	private User authenticate(final String email, final String password)
	{
		final User user = getUserByMail(email);
		if (user == null)
		{
			throw new LoginFailedException(i18n.get("LoginService.error.userOrPasswordInvalid"));
		}

		//
		// Validate the password
		final String userPassword = user.getPassword();
		if (Strings.isNullOrEmpty(userPassword))
		{
			// empty user passwords are considered as no password was set
			throw new LoginFailedException(i18n.get("LoginService.error.userOrPasswordInvalid"));
		}
		if (!Objects.equals(userPassword, password)
				&& !Objects.equals(createPasswordHash(userPassword), password) // support for hashed password (also used by remember-me functionality)
		)
		{
			throw new LoginFailedException(i18n.get("LoginService.error.userOrPasswordInvalid"));
		}

		return user;
	}

	@Override
	public void login(@NonNull final User user)
	{
		final HttpSession httpSession = getCurrentHttpSession();

		logout(httpSession);

		httpSession.setAttribute(HTTP_SESSION_loggedIn, true);
		httpSession.setAttribute(HTTP_SESSION_loggedUserId, user.getId());
		i18n.setSessionLanguage(user.getLanguageKeyOrDefault());
	}

	@Override
	public void logout()
	{
		final HttpSession httpSession = getCurrentHttpSession();
		logout(httpSession);
	}

	private void logout(final HttpSession httpSession)
	{
		httpSession.removeAttribute(HTTP_SESSION_loggedIn);
		httpSession.removeAttribute(HTTP_SESSION_loggedUserId);
	}

	public boolean isLoggedIn()
	{
		final HttpSession httpSession = getCurrentHttpSession();
		final Boolean loggedIn = (Boolean)httpSession.getAttribute(HTTP_SESSION_loggedIn);
		return loggedIn != null && loggedIn;
	}

	@Override
	public void assertLoggedIn()
	{
		if (!isLoggedIn())
		{
			throw new NotLoggedInException(i18n.get("LoginService.error.notLoggedIn"));
		}
	}

	@Override
	public User getLoggedInUser()
	{
		assertLoggedIn();
		final HttpSession httpSession = getCurrentHttpSession();
		final Long loggedInUserId = (Long)httpSession.getAttribute(HTTP_SESSION_loggedUserId);
		if (loggedInUserId == null || loggedInUserId <= 0)
		{
			// shall not happen
			throw new NotLoggedInException(i18n.get("LoginService.error.notLoggedIn"));
		}

		return userRepository.getOne(loggedInUserId);
	}

	@Override
	public Locale getLocale()
	{
		return i18n.getCurrentLocale();
	}

	@Override
	public String createPasswordHash(final String password)
	{
		return Hashing.sha256()
				.hashString(password, StandardCharsets.UTF_8)
				.toString();

	}

	@Override
	public void sendPasswordResetKey(
			@NonNull final String email,
			@NonNull final String passwordResetToken)
	{
		try
		{
			final User user = getUserByMail(email);
			if (user == null)
			{
				throw new PasswordResetFailedException(i18n.get("LoginService.error.emailNotRegistered"));
			}

			final LanguageKey language = user.getLanguageKeyOrDefault();

			final URI passwordResetURI = new URI(passwordResetUrl + "?token=" + passwordResetToken);

			final MimeMessage mail = emailSender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(mail, true); // multipart=true

			if (emailFrom != null && !emailFrom.isBlank())
			{
				helper.setFrom(emailFrom.trim());
			}
			helper.setTo(email);
			helper.setSubject(i18n.get(language, "PasswordReset.email.subject"));
			helper.setText(i18n.get(language, "PasswordReset.email.content", passwordResetURI));

			emailSender.send(mail);
		}
		catch (final MessagingException | URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public String generatePasswordResetKey(final String email)
	{
		final User user = getUserByMail(email);
		if (user == null)
		{
			throw new PasswordResetFailedException(i18n.get("LoginService.error.emailNotRegistered"));
		}

		//
		// Generate a password reset key and set it on user
		final String passwordResetKey = UUID.randomUUID().toString().replace("-", "");
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
			throw new PasswordResetFailedException(i18n.get("LoginService.error.invalidPasswordResetKey"));
		}

		final String passwordNew = generatePassword();
		user.setPassword(passwordNew);
		user.setPasswordResetKey(null);
		userRepository.save(user);

		return user;
	}

	private String generatePassword()
	{
		final StringBuilder password = new StringBuilder();
		final SecureRandom random = new SecureRandom();
		final int poolLength = PASSWORD_CHARS.length();
		for (int i = 0; i < PASSWORD_Length; i++)
		{
			final int charIndex = random.nextInt(poolLength);
			final char ch = PASSWORD_CHARS.charAt(charIndex);
			password.append(ch);
		}

		return password.toString();
	}
}
