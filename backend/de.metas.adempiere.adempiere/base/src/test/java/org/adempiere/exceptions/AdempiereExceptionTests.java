package org.adempiere.exceptions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.TranslatableStrings;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.Null;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class AdempiereExceptionTests
{
	private static final List<Class<?>> boxingExceptionClasses = ImmutableList.of(ExecutionException.class, InvocationTargetException.class);
	private final Random random = new Random(System.currentTimeMillis());

	private AdempiereException newAdempiereException()
	{
		return new AdempiereException(TranslatableStrings.empty());
	}

	@Test
	public void testExtractCauseFrom_ExecutionException()
	{
		final AdempiereException expectedCause = newAdempiereException();

		final Throwable actualCause = AdempiereException.extractCause(new ExecutionException(expectedCause));
		Assertions.assertSame(expectedCause, actualCause);
	}

	@Test
	public void testExtractCauseFrom_InvocationTargetException()
	{
		final AdempiereException expectedCause = newAdempiereException();

		final Throwable actualCause = AdempiereException.extractCause(new InvocationTargetException(expectedCause));
		Assertions.assertSame(expectedCause, actualCause);
	}

	@Test
	public void testWrapIfNeeded_AdempiereException()
	{
		final AdempiereException expected = newAdempiereException();
		final AdempiereException actual = AdempiereException.wrapIfNeeded(expected);
		Assertions.assertSame(expected, actual);
	}

	@Test
	public void testWrapIfNeeded_NotAdempiereException()
	{
		final Throwable rootEx = new RuntimeException("error-" + random.nextInt());
		final AdempiereException actual = AdempiereException.wrapIfNeeded(rootEx);
		Assertions.assertNotSame(rootEx, actual);
		Assertions.assertSame(rootEx, actual.getCause());
		Assertions.assertEquals(rootEx.getLocalizedMessage(), actual.getCause().getLocalizedMessage());
	}

	@Test
	public void testWrapIfNeeded_AdempiereException_Boxed() throws Exception
	{
		final Throwable rootEx = new AdempiereException("error-" + random.nextInt());
		final Throwable rootExBoxed = box(rootEx, 10); // box it 10 times
		final AdempiereException actual = AdempiereException.wrapIfNeeded(rootExBoxed);
		Assertions.assertSame(rootEx, actual);
	}

	@Test
	public void testWrapIfNeeded_NotAdempiereException_Boxed() throws Exception
	{
		final Throwable rootEx = new RuntimeException("error-" + random.nextInt());
		final Throwable rootExBoxed = box(rootEx, 10); // box it 10 times
		final AdempiereException actual = AdempiereException.wrapIfNeeded(rootExBoxed);
		Assertions.assertNotSame(rootEx, actual);
		Assertions.assertNotSame(rootEx, actual);
		Assertions.assertSame(rootEx, actual.getCause());
		Assertions.assertEquals(rootEx.getLocalizedMessage(), actual.getCause().getLocalizedMessage());
	}

	private Throwable box(final Throwable throwable, final int depth) throws Exception
	{
		if (depth == 0)
		{
			return throwable;
		}

		final int idx = random.nextInt(boxingExceptionClasses.size());
		final Class<?> exceptionClass = boxingExceptionClasses.get(idx);

		final Throwable throwableBoxed = (Throwable)exceptionClass.getConstructor(Throwable.class).newInstance(throwable);
		return box(throwableBoxed, depth - 1);
	}

	@Test
	public void test_setParameter_null()
	{
		final AdempiereException ex = newAdempiereException()
				.setParameter("param", null);
		Assertions.assertEquals(ImmutableMap.of("param", Null.NULL), ex.getParameters());
	}

	@Test
	public void test_setParameters_nullAndNotNull()
	{
		final AdempiereException ex = newAdempiereException()
				.setParameter("param1", null)
				.setParameter("param2", "value2")
				.setParameter("param3", null)
				.setParameter("param4", "value4");

		Assertions.assertEquals(ImmutableMap.of(
				"param1", Null.NULL,
				"param2", "value2",
				"param3", Null.NULL,
				"param4", "value4"), ex.getParameters());
	}

	@Test
	public void test_setParameters_removeParameter()
	{
		final AdempiereException ex = newAdempiereException()
				.setParameter("param1", "value1")
				.setParameter("param2", "value2");
		Assertions.assertEquals(ImmutableMap.of(
				"param1", "value1",
				"param2", "value2"), ex.getParameters());

		// Remove "param1" and test
		ex.setParameter("param1", null);
		Assertions.assertEquals(ImmutableMap.of(
				"param1", Null.NULL,
				"param2", "value2"), ex.getParameters());
	}

	@Test
	public void test_hasParameters_null()
	{
		final AdempiereException adempiereException = newAdempiereException();
		assertThat(adempiereException.hasParameter("hasnt")).isFalse();
	}

	@Test
	public void test_hasParameters_different()
	{
		final AdempiereException adempiereException = newAdempiereException()
				.setParameter("someParam", "test");
		assertThat(adempiereException.hasParameter("hasnt")).isFalse();
	}

	@Test
	public void test_hasParameters_same()
	{
		final AdempiereException adempiereException = newAdempiereException()
				.setParameter("someParam", "test");
		assertThat(adempiereException.hasParameter("someParam")).isTrue();
	}

	@Test
	public void test_setRecord()
	{
		final TableRecordReference recordRef = TableRecordReference.of("MyTable", 1234);

		final AdempiereException adempiereException = newAdempiereException()
				.setRecord(recordRef);

		assertThat(adempiereException.getRecord()).isSameAs(recordRef);

		assertThat(adempiereException.getParameters())
				.isEqualTo(ImmutableMap.<String, Object> builder()
						.put(AdempiereException.PARAMETER_RecordRef, recordRef)
						.build());
	}

	@Nested
	class getParameter
	{
		@Test
		void setParameterAsNull_thenGetIt()
		{
			final AdempiereException ex = new AdempiereException("exception");
			ex.setParameter("param", null);
			assertThat(ex.getParameter("param")).isNull();
		}
	}
}
