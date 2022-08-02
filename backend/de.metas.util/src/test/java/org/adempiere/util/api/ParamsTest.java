package org.adempiere.util.api;

import com.google.common.collect.ImmutableMap;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import de.metas.util.lang.RepoIdAwaresTest.DummyId;
import lombok.Getter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
		OPTION1, OPTION2
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

	@SuppressWarnings("SameParameterValue")
	private static Params singleParam(final String parameterName, final Object value)
	{
		return Params.ofMap(ImmutableMap.<String, Object>builder()
				.put(parameterName, value)
				.build());
	}

	@Test
	public void nullParameters()
	{
		final Params params = Params.builder()
				.value("param1", (String)null)
				.value("param2", (String)null)
				.build();

		assertThat(params.getParameterNames()).containsExactly("param1", "param2");
		assertThat(params.getParameterAsObject("param1")).isNull();
		assertThat(params.getParameterAsObject("param2")).isNull();
	}

	@Nested
	public class getParameterAsEnum
	{
		@Test
		public void regularEnum()
		{
			final Params params = singleParam("param", TestRegularEnum.OPTION1.name());
			assertThat(params.getParameterAsEnum("param", TestRegularEnum.class).orElse(null)).isSameAs(TestRegularEnum.OPTION1);
		}

		@Test
		public void referenceListAwareEnum()
		{
			final Params params = singleParam("param", TestReferenceListAwareEnum.OPTION1.getCode());
			assertThat(params.getParameterAsEnum("param", TestReferenceListAwareEnum.class).orElse(null)).isSameAs(TestReferenceListAwareEnum.OPTION1);
		}
	}

	@Nested
	public class getParameterAsId
	{
		@Test
		public void fromNegativeInt()
		{
			final Params params = singleParam("param", -1);
			assertThat(params.getParameterAsId("param", DummyId.class)).isNull();
		}

		@Test
		public void fromPositiveInt()
		{
			final Params params = singleParam("param", 1234);
			assertThat(params.getParameterAsId("param", DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
		}

		@Test
		public void fromMissingValue()
		{
			final Params params = Params.EMPTY;
			assertThat(params.getParameterAsId("param", DummyId.class)).isNull();
		}

		@Test
		public void fromRepoIdAware()
		{
			final DummyId id = DummyId.ofRepoId(111);
			final Params params = singleParam("param", id);
			assertThat(params.getParameterAsId("param", DummyId.class)).isSameAs(id);
		}

		@Test
		public void fromPositiveString()
		{
			final Params params = singleParam("param", "1234");
			assertThat(params.getParameterAsId("param", DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
		}

		@Test
		public void fromNegativeString()
		{
			final Params params = singleParam("param", "-1234");
			assertThat(params.getParameterAsId("param", DummyId.class)).isNull();
		}

		@Test
		public void fromInvalidString()
		{
			final Params params = singleParam("param", "invalid_number");
			assertThat(params.getParameterAsId("param", DummyId.class)).isNull();
		}

	}

}
