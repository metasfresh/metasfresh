package org.adempiere.ad.modelvalidator;

import lombok.NonNull;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DocTimingTypeTest
{
	@ParameterizedTest
	@EnumSource(DocTimingType.class)
	void valueOfInt(@NonNull final DocTimingType timing)
	{
		assertThat(DocTimingType.valueOf(timing.toInt())).isSameAs(timing);
	}

	@ParameterizedTest
	@EnumSource(DocTimingType.class)
	void forAction(@NonNull final DocTimingType timing)
	{
		assertThat(DocTimingType.forAction(timing.getDocAction(), timing.getBeforeAfter())).isSameAs(timing);
	}

	@ParameterizedTest
	@EnumSource(DocTimingType.class)
	void isDocAction(@NonNull final DocTimingType timing)
	{
		assertThat(timing.isDocAction(timing.getDocAction())).isTrue();
	}

	@Nested
	public class isVoid
	{
		@ParameterizedTest
		@EnumSource(value = DocTimingType.class, names = { "BEFORE_VOID", "AFTER_VOID" })
		void returns_true(@NonNull final DocTimingType timing)
		{
			assertThat(timing.isVoid()).isTrue();
		}

		@ParameterizedTest
		@EnumSource(value = DocTimingType.class, names = { "BEFORE_VOID", "AFTER_VOID" }, mode = EnumSource.Mode.EXCLUDE)
		void returns_false(@NonNull final DocTimingType timing)
		{
			assertThat(timing.isVoid()).isFalse();
		}
	}

	@Nested
	public class isReverse
	{
		@ParameterizedTest
		@EnumSource(value = DocTimingType.class, names = {
				"BEFORE_REVERSECORRECT",
				"AFTER_REVERSECORRECT",
				"BEFORE_REVERSEACCRUAL",
				"AFTER_REVERSEACCRUAL" })
		void returns_true(@NonNull final DocTimingType timing)
		{
			assertThat(timing.isReverse()).isTrue();
		}

		@ParameterizedTest
		@EnumSource(value = DocTimingType.class,
				mode = EnumSource.Mode.EXCLUDE,
				names = {
				"BEFORE_REVERSECORRECT",
				"AFTER_REVERSECORRECT",
				"BEFORE_REVERSEACCRUAL",
				"AFTER_REVERSEACCRUAL" })
		void returns_false(@NonNull final DocTimingType timing)
		{
			assertThat(timing.isReverse()).isFalse();
		}
	}

}
