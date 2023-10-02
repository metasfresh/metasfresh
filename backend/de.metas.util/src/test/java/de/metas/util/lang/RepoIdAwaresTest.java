package de.metas.util.lang;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Value;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

public class RepoIdAwaresTest
{
	@Value
	public static class DummyId implements RepoIdAware
	{
		@JsonCreator
		public static DummyId ofRepoId(final int repoId)
		{
			return new DummyId(repoId);
		}

		public static DummyId ofRepoIdOrNull(final int repoId)
		{
			return repoId > 0 ? new DummyId(repoId) : null;
		}

		int repoId;

		private DummyId(final int repoId)
		{
			Check.assumeGreaterThanZero(repoId, "repoId");
			this.repoId = repoId;
		}

		@JsonValue
		@Override
		public int getRepoId()
		{
			return repoId;
		}
	}

	@Nested
	class ofObject
	{
		@Test
		void ofDummyId()
		{
			final DummyId id = DummyId.ofRepoId(1234);
			assertThat(RepoIdAwares.ofObject(id, DummyId.class)).isSameAs(id);
		}

		@Test
		void ofInt()
		{
			assertThat(RepoIdAwares.ofObject(1234, DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
		}

		@Test
		void ofBigDecimal()
		{
			assertThat(RepoIdAwares.ofObject(new BigDecimal("1234.00"), DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
		}

		@Test
		void ofString()
		{
			assertThat(RepoIdAwares.ofObject("1234", DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
		}
	}

	@Nested
	class ofObjectOrNull
	{
		@Nested
		class regression
		{
			@Test
			void ofDummyId()
			{
				final DummyId id = DummyId.ofRepoId(1234);
				assertThat(RepoIdAwares.ofObjectOrNull(id, DummyId.class)).isSameAs(id);
			}

			@Test
			void ofInt()
			{
				assertThat(RepoIdAwares.ofObjectOrNull(1234, DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
			}

			@Test
			void ofBigDecimal()
			{
				assertThat(RepoIdAwares.ofObjectOrNull(new BigDecimal("1234.00"), DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
			}

			@Test
			void ofString()
			{
				assertThat(RepoIdAwares.ofObjectOrNull("1234", DummyId.class)).isEqualTo(DummyId.ofRepoId(1234));
			}
		}

		@Test
		void ofNull()
		{
			assertThat(RepoIdAwares.ofObjectOrNull(null, DummyId.class)).isNull();
		}

		@Test
		void ofEmptyString()
		{
			assertThat(RepoIdAwares.ofObjectOrNull("", DummyId.class)).isNull();
		}

		@Test
		void ofBlankString()
		{
			assertThat(RepoIdAwares.ofObjectOrNull("    ", DummyId.class)).isNull();
		}

		@Test
		void ofNegativeInt()
		{
			assertThat(RepoIdAwares.ofObjectOrNull(-1, DummyId.class)).isNull();
		}

		@Test
		void ofZeroInt()
		{
			assertThat(RepoIdAwares.ofObjectOrNull(0, DummyId.class)).isNull();
		}
	}

	@Nested
	class ofCommaSeparatedList
	{
		@Test
		void nullString()
		{
			final ImmutableList<DummyId> list = RepoIdAwares.ofCommaSeparatedList(null, DummyId.class);
			assertThat(list).isEmpty();
		}

		@Test
		void emptyString()
		{
			final ImmutableList<DummyId> list = RepoIdAwares.ofCommaSeparatedList("", DummyId.class);
			assertThat(list).isEmpty();
		}

		@Test
		void validValues()
		{
			final ImmutableList<DummyId> list = RepoIdAwares.ofCommaSeparatedList("1,2,3", DummyId.class);
			assertThat(list).containsExactly(
					DummyId.ofRepoId(1),
					DummyId.ofRepoId(2),
					DummyId.ofRepoId(3));
		}

		@Test
		void someEmptyValues()
		{
			final ImmutableList<DummyId> list = RepoIdAwares.ofCommaSeparatedList(",1,,3,", DummyId.class);
			assertThat(list).containsExactly(
					DummyId.ofRepoId(1),
					DummyId.ofRepoId(3));
		}

		@Test
		void oneInvalidValue()
		{
			assertThatThrownBy(() -> RepoIdAwares.ofCommaSeparatedList("1,2,-1", DummyId.class))
					.hasMessageStartingWith("Failed invoking ");
		}
	}
}
