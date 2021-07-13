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

package de.metas.procurement.webui.rest.i18n;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.service.I18N;
import de.metas.procurement.webui.util.LanguageKey;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@RestController
@RequestMapping(I18NRestController.ENDPOINT)
public class I18NRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/i18n";

	private final I18N i18n;

	public I18NRestController(
			@NonNull final I18N i18n)
	{
		this.i18n = i18n;
	}

	@GetMapping("/messages")
	public JsonMessages getMessages(
			@RequestParam(name = "lang", required = false) @Nullable final String languageStr)
	{
		final LanguageKey language = LanguageKey.optionalOfNullableString(languageStr)
				.orElseGet(i18n::getCurrentLang);

		return JsonMessages.builder()
				.language(language.getAsString())
				.messages(i18n.getMessagesMap(language))
				.build();
	}
}