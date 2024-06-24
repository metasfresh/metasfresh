/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.util.web.security;

import de.metas.i18n.ADLanguageList;
import de.metas.i18n.ILanguageDAO;
import de.metas.i18n.Language;
import de.metas.security.UserAuthTokenRepository;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class UserAuthTokenFilterTest
{
	private UserAuthTokenFilter userAuthTokenFilter;

	private static HttpServletRequest httpRequest(final String acceptLanguage)
	{
		final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.doReturn(acceptLanguage).when(request).getHeader("Accept-Language");
		return request;
	}

	@SuppressWarnings("SameParameterValue")
	private static UserAuthTokenFilter newUserAuthTokenFilter(String baseADLanguage, String... systemADLanguages)
	{
		final ADLanguageList.Builder languagesBuilder = ADLanguageList.builder()
				.addLanguage(baseADLanguage, baseADLanguage, true);
		Stream.of(systemADLanguages).forEach(adLanguage -> languagesBuilder.addLanguage(adLanguage, adLanguage, false));
		final ADLanguageList languagesList = languagesBuilder.build();

		final ILanguageDAO languageDAO = Mockito.mock(ILanguageDAO.class);
		Mockito.doReturn(languagesList).when(languageDAO).retrieveAvailableLanguages();

		//Services.registerService(ILanguageDAO.class, languageDAO); // not needed
		Language.setBaseLanguage(() -> languageDAO.retrieveAvailableLanguages().getBaseADLanguage());

		return new UserAuthTokenFilter(new UserAuthTokenService(new UserAuthTokenRepository()), new UserAuthTokenFilterConfiguration(), languageDAO);
	}

	OptionalAssert<String> assert_extractAdLanguage(final String acceptLanguage)
	{
		return assertThat(userAuthTokenFilter.extractAdLanguage(httpRequest(acceptLanguage)));
	}

	@Test
	void extractAdLanguage()
	{
		this.userAuthTokenFilter = newUserAuthTokenFilter("de_DE", "en_US", "de_CH");

		assert_extractAdLanguage("de-DE").contains("de_DE");
		assert_extractAdLanguage("de-CH").contains("de_CH");
		assert_extractAdLanguage("de-AT").contains("de_DE");
		assert_extractAdLanguage("en-US").contains("en_US");
		assert_extractAdLanguage("en-GB").contains("en_US");
		assert_extractAdLanguage("en-AU").contains("en_US");
		assert_extractAdLanguage("en-GB,en;q=0.9,en-US;q=0.8,ro;q=0.7,de;q=0.6,eo;q=0.5").contains("en_US");
		assert_extractAdLanguage("ro-RO").contains("de_DE");
	}
}