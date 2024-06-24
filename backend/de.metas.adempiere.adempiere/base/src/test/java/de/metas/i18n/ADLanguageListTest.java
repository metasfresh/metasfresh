/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.i18n;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ADLanguageListTest
{

	@SuppressWarnings("SameParameterValue")
	private static ADLanguageList newADLanguageList(String baseADLanguage, String... systemADLanguages)
	{
		final ADLanguageList.Builder languagesBuilder = ADLanguageList.builder()
				.addLanguage(baseADLanguage, baseADLanguage, true);
		Stream.of(systemADLanguages).forEach(adLanguage -> languagesBuilder.addLanguage(adLanguage, adLanguage, false));
		return languagesBuilder.build();
	}

	@Test
	void findSimilarADLanguage()
	{
		final ADLanguageList adLanguageList = ADLanguageList.builder()
				.addLanguage("de_CH", "de_CH", false)
				.addLanguage("de_DE", "de_DE", true)
				.addLanguage("en_US", "en_US", false)
				.build();

		assertThat(adLanguageList.findSimilarADLanguage("de_DE")).contains("de_DE");
		assertThat(adLanguageList.findSimilarADLanguage("de_CH")).contains("de_CH");
		assertThat(adLanguageList.findSimilarADLanguage("de_AT")).contains("de_DE");
		assertThat(adLanguageList.findSimilarADLanguage("en_US")).contains("en_US");
		assertThat(adLanguageList.findSimilarADLanguage("en_GB")).contains("en_US");
		assertThat(adLanguageList.findSimilarADLanguage("ro_RO")).isEmpty();
	}

	@Test
	void getAD_LanguageFromHttpAcceptLanguage()
	{
		final ADLanguageList adLanguageList = ADLanguageList.builder()
				.addLanguage("de_CH", "de_CH", false)
				.addLanguage("de_DE", "de_DE", true)
				.addLanguage("en_US", "en_US", false)
				.build();

		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de-DE", null)).contains("de_DE");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de-CH", null)).contains("de_CH");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de-AT", null)).contains("de_DE");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en-US", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en-GB", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en-AU", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en-GB,en;q=0.9,en-US;q=0.8,ro;q=0.7,de;q=0.6,eo;q=0.5", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("ro-RO", null)).isNull();

		//
		// AD_Language format:
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de_DE", null)).contains("de_DE");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de_CH", null)).contains("de_CH");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("de_AT", null)).contains("de_DE");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en_US", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en_GB", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("en_AU", null)).contains("en_US");
		assertThat(adLanguageList.getAD_LanguageFromHttpAcceptLanguage("ro_RO", null)).isNull();

	}
}