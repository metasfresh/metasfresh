package de.metas.migration.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.jknack.semver.Semver;

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
	 * Verifies that {@link Semver} also works for four number versions
	 */
	@Test
	public void test()
	{
		assertTrue(Semver.create("5.20.3.4").compareTo(Semver.create("5.20.3.5")) < 0);
		assertTrue(Semver.create("5.20.3.4").compareTo(Semver.create("5.20.3.3")) > 0);
		assertTrue(Semver.create("5.20.3.4").compareTo(Semver.create("5.20.3.4")) == 0);
	}
}
