package de.metas.process;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

	public static class ProcessImpl extends JavaProcess
	{
		@Override
		protected String doIt() throws Exception
		{
			return MSG_OK;
		}
	}

	@Test
	public void test_ProcessImpl()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl.class);
		assertThat(processClassInfo)
			.as("A new instance shall be created, and not the default one")
			.isNotSameAs(ProcessClassInfo.NULL);
		assertThat(processClassInfo.isRunPrepareOutOfTransaction())
			.as("RunPrepareOutOfTransaction: " + processClassInfo)
			.isFalse();
		assertThat(processClassInfo.isRunDoItOutOfTransaction())
			.as("RunDoItOutOfTransaction: " + processClassInfo)
			.isFalse();
	}

	public static class ProcessImpl2 extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected String doIt() throws Exception
		{
			return MSG_OK;
		}
	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} not implemented</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * 
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void test_RunOutOfTrx_ProcessImpl2()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl2.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl3 extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected String doIt() throws Exception
		{
			return MSG_OK;
		}

		@Override
		protected void prepare()
		{
			super.prepare();
		}

	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented but not annotated</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * 
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void test_RunOutOfTrx_ProcessImpl3()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl3.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl4 extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected String doIt() throws Exception
		{
			return MSG_OK;
		}

		@RunOutOfTrx
		@Override
		protected void prepare()
		{
			super.prepare();
		}
	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented and annotated</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * 
	 * I.e. both are annotated explicitly.
	 * <p>
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void test_RunOutOfTrx_ProcessImpl4()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl4.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl5 extends JavaProcess
	{
		@Override
		protected String doIt() throws Exception
		{
			return MSG_OK;
		}

		@RunOutOfTrx
		@Override
		protected void prepare()
		{
			super.prepare();
		}
	}

	/**
	 * Tests with a process class
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented and annotated</li>
	 * <li>{@link JavaProcess#doIt()}: not annotated</li>
	 * </ul>
	 * 
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void test_RunOutOfTrx_ProcessImpl5()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl5.class);

		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isFalse();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	@Test
	public void test_JavaProcess()
	{
		// just to notify that we are not going to test the JavaProcess here...
		assumeThat(false)
			.as("Other JavaProcess tests are already tested by " + JavaProcessTests.class)
			.isTrue();
	}
}