package org.adempiere.util.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ParamsTest
{
	private enum TestRegularEnum
	{
		OPTION1, OPTION2;
	}

	public enum TestReferenceListAwareEnum implements ReferenceListAwareEnum
	{
		OPTION1("O1"), OPTION2("O2");

		@Getter
		private final String code;

		private static final ValuesIndex<TestReferenceListAwareEnum> index = ReferenceListAwareEnums.index(values());

		TestReferenceListAwareEnum(final String code)
		{
			this.code = code;
		}

		public static TestReferenceListAwareEnum ofCode(final String code)
		{
			return index.ofCode(code);
		}
	}

	@Nested
	public class getParameterAsEnum
	{
		@Test
		public void regularEnum()
		{
			final Params params = Params.ofMap(ImmutableMap.<String, Object> builder()
					.put("param", TestRegularEnum.OPTION1.name())
					.build());

			assertThat(params.getParameterAsEnum("param", TestRegularEnum.class).get()).isSameAs(TestRegularEnum.OPTION1);
		}

		@Test
		public void referenceListAwareEnum()
		{
			final Params params = Params.ofMap(ImmutableMap.<String, Object> builder()
					.put("param", TestReferenceListAwareEnum.OPTION1.getCode())
					.build());

			assertThat(params.getParameterAsEnum("param", TestReferenceListAwareEnum.class).get()).isSameAs(TestReferenceListAwareEnum.OPTION1);
		}

	}

}
