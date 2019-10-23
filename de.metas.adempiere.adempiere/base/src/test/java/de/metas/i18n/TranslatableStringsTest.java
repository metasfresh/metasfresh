package de.metas.i18n;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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

public class TranslatableStringsTest
{
	@Test
	public void joinObjects_WithCollections()
	{
		final ITranslatableString actual = TranslatableStrings.join(" ",
				TranslatableStrings.constant("1"),
				ImmutableList.of(
						TranslatableStrings.constant("2"),
						ImmutableList.of(
								TranslatableStrings.constant("3")),
						TranslatableStrings.constant("4")),
				TranslatableStrings.constant("5"));

		final ITranslatableString expected = TranslatableStrings.joinList(" ", ImmutableList.of(
				TranslatableStrings.constant("1"),
				TranslatableStrings.constant("2"),
				TranslatableStrings.constant("3"),
				TranslatableStrings.constant("4"),
				TranslatableStrings.constant("5")));
		
		assertThat(actual).isEqualTo(expected);
	}
}
