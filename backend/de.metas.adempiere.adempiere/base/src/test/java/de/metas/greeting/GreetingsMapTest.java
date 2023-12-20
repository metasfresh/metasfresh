/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.greeting;

import de.metas.i18n.TranslatableStrings;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

class GreetingsMapTest
{
	@Nested
	class getComposite
	{
		private final AtomicInteger nextGreetingId = new AtomicInteger(1001);
		private Greeting greeting_MR;
		private Greeting greeting_MRS;
		private Greeting greeting_MR_AND_MRS;
		private Greeting greeting_MRS_AND_MR;
		private Greeting greeting_withoutStandardType;
		private Greeting greeting_notPartOfAComposite;
		private GreetingsMap greetingsMap;

		@BeforeEach
		void beforeEach()
		{
			greeting_MR = greeting(GreetingStandardType.MR);
			greeting_MRS = greeting(GreetingStandardType.MRS);
			greeting_MR_AND_MRS = greeting(GreetingStandardType.MR_AND_MRS);
			greeting_MRS_AND_MR = greeting(GreetingStandardType.MRS_AND_MR);
			greeting_withoutStandardType = Greeting.builder()
					.id(GreetingId.ofRepoId(nextGreetingId.getAndIncrement()))
					.name("greeting_withoutStandardType")
					.orgId(OrgId.MAIN)
					.greeting(TranslatableStrings.anyLanguage("greeting_withoutStandardType"))
					.active(true)
					.build();

			greeting_notPartOfAComposite = Greeting.builder()
					.id(GreetingId.ofRepoId(nextGreetingId.getAndIncrement()))
					.name("greeting_notPartOfAComposite")
					.orgId(OrgId.MAIN)
					.greeting(TranslatableStrings.anyLanguage("greeting_notPartOfAComposite"))
					.standardType(GreetingStandardType.ofCode("greeting_notPartOfAComposite"))
					.active(true)
					.build();

			greetingsMap = new GreetingsMap(Arrays.asList(
					greeting_MR,
					greeting_MRS,
					greeting_MR_AND_MRS,
					greeting_MRS_AND_MR,
					greeting_withoutStandardType,
					greeting_notPartOfAComposite));
		}

		private Greeting greeting(@NonNull final GreetingStandardType standardType)
		{
			return Greeting.builder()
					.id(GreetingId.ofRepoId(nextGreetingId.getAndIncrement()))
					.orgId(OrgId.MAIN)
					.name(standardType.getCode())
					.greeting(TranslatableStrings.anyLanguage(standardType.getCode()))
					.standardType(standardType)
					.active(true)
					.build();
		}

		@Test
		void greeting1_and_greeting2_is_null()
		{
			Assertions.assertThat(greetingsMap.getComposite(null, null)).isEmpty();
		}

		@Test
		void greeting1_is_null()
		{
			Assertions.assertThat(greetingsMap.getComposite(null, greeting_MRS.getId()))
					.contains(greeting_MRS);

		}

		@Test
		void greeting2_is_null()
		{
			Assertions.assertThat(greetingsMap.getComposite(greeting_MR.getId(), null))
					.contains(greeting_MR);
		}

		@Test
		void mr_and_mrs()
		{
			Assertions.assertThat(greetingsMap.getComposite(greeting_MR.getId(), greeting_MRS.getId()))
					.contains(greeting_MR_AND_MRS);
		}

		@Test
		void mrs_and_mr()
		{
			Assertions.assertThat(greetingsMap.getComposite(greeting_MRS.getId(), greeting_MR.getId()))
					.contains(greeting_MRS_AND_MR);
		}

		@Test
		void greeting_withoutStandardType()
		{
			Assertions.assertThat(greetingsMap.getComposite(greeting_MR.getId(), greeting_withoutStandardType.getId()))
					.isEmpty();
		}

		@Test
		void greeting_notPartOfAComposite()
		{
			Assertions.assertThat(greetingsMap.getComposite(greeting_MR.getId(), greeting_notPartOfAComposite.getId()))
					.isEmpty();
		}

	}
}