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

package de.metas.procurement.webui.rest.session;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SessionRestController.ENDPOINT)
public class SessionRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/session";

	private final ILoginService loginService;
	private final IProductSuppliesService productSuppliesService;

	public SessionRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IProductSuppliesService productSuppliesService)
	{
		this.loginService = loginService;
		this.productSuppliesService = productSuppliesService;
	}

	@GetMapping("/")
	public JsonSessionInfo getSessionInfo()
	{
		final User user = loginService.getLoggedInUser();

		final long countUnconfirmedDailyReports = productSuppliesService.countUnconfirmedUserEnteredQtys(user.getBpartner());

		return JsonSessionInfo.builder()
				.loggedIn(true)
				.email(user.getEmail())
				.language(loginService.getLanguage().getAsString())
				.countUnconfirmed(countUnconfirmedDailyReports)
				.build();
	}

	@PostMapping("/login")
	public JsonSessionInfo login(@RequestBody @NonNull final JsonLoginRequest request)
	{
		try
		{
			loginService.login(request.getEmail(), request.getPassword());
			return getSessionInfo();
		}
		catch (final Exception ex)
		{
			return JsonSessionInfo.builder()
					.loggedIn(false)
					.loginError(ex.getLocalizedMessage())
					.build();
		}
	}

	@GetMapping("/logout")
	public void logout()
	{
		loginService.logout();
	}

	@GetMapping("/resetUserPassword")
	public void resetUserPasswordRequest(@RequestParam("email") final String email)
	{
		final String passwordResetToken = loginService.generatePasswordResetKey(email);
		loginService.sendPasswordResetKey(email, passwordResetToken);
	}

	@GetMapping("/resetUserPasswordConfirm")
	public JsonPasswordResetResponse resetUserPasswordConfirm(@RequestParam("token") final String token)
	{
		final User user = loginService.resetPassword(token);
		loginService.login(user);

		return JsonPasswordResetResponse.builder()
				.email(user.getEmail())
				.language(user.getLanguageKeyOrDefault().getAsString())
				.newPassword(user.getPassword())
				.build();
	}

	@PostMapping("/confirmDataEntry")
	public void confirmDataEntry()
	{
		final BPartner bpartner = loginService.getLoggedInUser().getBpartner();
		productSuppliesService.confirmUserEnteredQtys(bpartner);
	}
}
