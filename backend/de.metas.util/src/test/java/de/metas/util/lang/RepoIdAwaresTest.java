package de.metas.util.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.Value;

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
	public static class DummyRepoIdAware implements RepoIdAware
	{
		@JsonCreator
		public static DummyRepoIdAware ofRepoId(final int repoId)
		{
			return new DummyRepoIdAware(repoId);
		}

		public static DummyRepoIdAware ofRepoIdOrNull(final int repoId)
		{
			return repoId > 0 ? new DummyRepoIdAware(repoId) : null;
		}

		private int repoId;

		private DummyRepoIdAware(final int repoId)
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
	public class ofCommaSeparatedList
	{
		@Test
		public void nullString()
		{
			final ImmutableList<DummyRepoIdAware> list = RepoIdAwares.ofCommaSeparatedList(null, DummyRepoIdAware.class);
			assertThat(list).isEmpty();
		}

		@Test
		public void emptyString()
		{
			final ImmutableList<DummyRepoIdAware> list = RepoIdAwares.ofCommaSeparatedList("", DummyRepoIdAware.class);
			assertThat(list).isEmpty();
		}

		@Test
		public void validValues()
		{
			final ImmutableList<DummyRepoIdAware> list = RepoIdAwares.ofCommaSeparatedList("1,2,3", DummyRepoIdAware.class);
			assertThat(list).containsExactly(
					DummyRepoIdAware.ofRepoId(1),
					DummyRepoIdAware.ofRepoId(2),
					DummyRepoIdAware.ofRepoId(3));
		}

		@Test
		public void someEmptyValues()
		{
			final ImmutableList<DummyRepoIdAware> list = RepoIdAwares.ofCommaSeparatedList(",1,,3,", DummyRepoIdAware.class);
			assertThat(list).containsExactly(
					DummyRepoIdAware.ofRepoId(1),
					DummyRepoIdAware.ofRepoId(3));
		}

		@Test
		public void oneInvalidValue()
		{
			assertThatThrownBy(() -> RepoIdAwares.ofCommaSeparatedList("1,2,-1", DummyRepoIdAware.class))
					.hasMessageStartingWith("Failed invoking ");
		}
	}
}
