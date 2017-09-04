package de.metas.migration.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DBVersionSetterTests
{

	@Test
	public void testWithoutSuffix()
	{
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0", "")).isEqualTo("1.0.0");
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0", null)).isEqualTo("1.0.0");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc", "")).isEqualTo("1.0.0-rc");
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc", null)).isEqualTo("1.0.0-rc");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0+md", "")).isEqualTo("1.0.0+md");
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0+md", null)).isEqualTo("1.0.0+md");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc+md", "")).isEqualTo("1.0.0-rc+md");
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc+md", null)).isEqualTo("1.0.0-rc+md");
	}

	@Test
	public void testWithSuffix()
	{
		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0", "suffix")).isEqualTo("1.0.0+xsuffix");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc", "suffix")).isEqualTo("1.0.0-rc+xsuffix");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0+md", "suffix")).isEqualTo("1.0.0+mdxsuffix");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.0.0-rc+md", "suffix")).isEqualTo("1.0.0-rc+mdxsuffix");

		assertThat(DBVersionSetter.createVersionWithOptionalSuffix("1.1.1", RolloutMigrate.UPDATE_IN_PROGRESS_VERSION_SUFFIX)).isEqualTo("1.1.1" + "+x" + RolloutMigrate.UPDATE_IN_PROGRESS_VERSION_SUFFIX);
	}
}
