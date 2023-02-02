package de.metas.process;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assumptions.assumeThat;

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

public class ProcessClassInfoTests
{
	@BeforeEach
	@AfterEach
	public void resetCache()
	{
		ProcessClassInfo.resetCache();
	}

	public static class SimpleProcessImplWithoutAnnotations extends JavaProcess
	{
		@Override
		protected String doIt() {return MSG_OK;}
	}

	@Test
	public void testSimpleProcessImplWithoutAnnotations()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(SimpleProcessImplWithoutAnnotations.class);
		Assertions.assertThat(processClassInfo).as("A new instance shall be created, and not the default one").isNotSameAs(ProcessClassInfo.NULL);
	}

	@Test
	public void test_JavaProcess()
	{
		// just to notify that we are not going to test the JavaProcess here...
		assumeThat(false).as("Other JavaProcess tests are already tested by " + JavaProcessTests.class).isTrue();
	}

}
