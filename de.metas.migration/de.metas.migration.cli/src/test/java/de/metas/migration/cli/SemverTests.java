package de.metas.migration.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.zafarkhaja.semver.Version;

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

public class SemverTests
{
	/**
	 * 
	 */
	@Test
	public void test()
	{
		assertThat(Version.valueOf("5.20.4")).isLessThan(Version.valueOf("5.20.5"));
		assertThat(Version.valueOf("5.20.4-1")).isLessThan(Version.valueOf("5.20.4-2"));
		assertThat(Version.valueOf("5.20.4")).isGreaterThan(Version.valueOf("5.20.3"));
	}
}
