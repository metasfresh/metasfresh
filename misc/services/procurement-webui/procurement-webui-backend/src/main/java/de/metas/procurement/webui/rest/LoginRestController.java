/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.rest;

import de.metas.procurement.webui.Constants;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LoginRestController.ENDPOINT)
public class LoginRestController
{
	static final String ENDPOINT = Constants.ENDPOINT_ROOT + "/login";

	//private final static Logger logger = LoggerFactory.getLogger(LoginRestController.class);
	private final ILoginService loginService;

	public LoginRestController(
			@NonNull final ILoginService loginService)
	{
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public JsonLoginResponse login(@RequestBody @NonNull final JsonLoginRequest request)
	{
		try
		{
			final User user = loginService.login(request.getEmail(), request.getPassword());

			return JsonLoginResponse.builder()
					.ok(true)
					.language(user.getLanguageKeyOrDefault().getAsString())
					.build();
		}
		catch (final Exception ex)
		{
			return JsonLoginResponse.error(ex);
		}
	}

	@GetMapping("/logout")
	public void logout()
	{
		loginService.logout();
	}

	@GetMapping("/resetPassword")
	public void resetPasswordRequest(@RequestParam("email") final String email)
	{
		final String passwordResetToken = loginService.generatePasswordResetKey(email);
		loginService.sendPasswordResetKey(email, passwordResetToken);
	}

	@GetMapping("/resetPasswordConfirm")
	public JsonPasswordResetResponse resetPasswordConfirm(@RequestParam("token") final String token)
	{
		final User user = loginService.resetPassword(token);
		loginService.login(user);

		return JsonPasswordResetResponse.builder()
				.email(user.getEmail())
				.language(user.getLanguageKeyOrDefault().getAsString())
				.newPassword(user.getPassword())
				.build();
	}
}
