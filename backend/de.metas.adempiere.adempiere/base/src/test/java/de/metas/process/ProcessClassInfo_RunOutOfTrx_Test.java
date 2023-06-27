package de.metas.process;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NewClassNamingConvention")
public class ProcessClassInfo_RunOutOfTrx_Test
{
	@BeforeEach
	@AfterEach
	public void resetCache()
	{
		ProcessClassInfo.resetCache();
	}

	public static class ProcessImpl_prepareItNotImplemented_doItNotAnnotated extends JavaProcess
	{
		@Override
		protected String doIt() {return MSG_OK;}
	}

	@Test
	public void prepareItNotImplemented_doItNotAnnotated()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_prepareItNotImplemented_doItNotAnnotated.class);
		Assertions.assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isFalse();
		Assertions.assertThat(processClassInfo.isRunDoItOutOfTransaction()).isFalse();
	}

	public static class ProcessImpl_prepareNotImplemented_doItOutOfTrx extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected String doIt() {return MSG_OK;}
	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} not implemented</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * <p>
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void prepareNotImplemented_doItOutOfTrx()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_prepareNotImplemented_doItOutOfTrx.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl_prepareItNotAnnotated_doItOutOfTrx extends JavaProcess
	{
		@Override
		protected void prepare() {super.prepare();}

		@RunOutOfTrx
		@Override
		protected String doIt() {return MSG_OK;}
	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented but not annotated</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * <p>
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void prepareItNotAnnotated_doItOutOfTrx()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_prepareItNotAnnotated_doItOutOfTrx.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl_prepareItOutOfTrx_doItOutOfTrx extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected void prepare() {super.prepare();}

		@RunOutOfTrx
		@Override
		protected String doIt() {return MSG_OK;}
	}

	/**
	 * Verifies the {@link RunOutOfTrx} annotation with a process class that is annotated as follows:
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented and annotated</li>
	 * <li>{@link JavaProcess#doIt()}: annotated</li>
	 * </ul>
	 * <p>
	 * I.e. both are annotated explicitly.
	 * <p>
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void prepareItOutOfTrx_doItOutOfTrx()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_prepareItOutOfTrx_doItOutOfTrx.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isTrue();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

	public static class ProcessImpl_prepareItOutOfTrx_doItNotAnnotated extends JavaProcess
	{
		@RunOutOfTrx
		@Override
		protected void prepare() {super.prepare();}

		@Override
		protected String doIt() {return MSG_OK;}
	}

	/**
	 * Tests with a process class
	 * <ul>
	 * <li>{@link JavaProcess#prepare()} implemented and annotated</li>
	 * <li>{@link JavaProcess#doIt()}: not annotated</li>
	 * </ul>
	 * <p>
	 * Note: this behavior is not necessarily written in stone, but this is how it currently is, and other code might rely on it.
	 */
	@Test
	public void prepareItOutOfTrx_doItNotAnnotated()
	{
		final ProcessClassInfo processClassInfo = ProcessClassInfo.of(ProcessImpl_prepareItOutOfTrx_doItNotAnnotated.class);
		assertThat(processClassInfo.isRunDoItOutOfTransaction()).isFalse();
		assertThat(processClassInfo.isRunPrepareOutOfTransaction()).isTrue();
	}

}
