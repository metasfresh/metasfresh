package de.metas.ui.web.window.descriptor.sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.exceptions.ExpressionCompileException;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.upload.WebuiImageId;
import de.metas.util.lang.RepoIdAware;

/**
 * Type-handling coverage for {@link SqlDefaultValueExpression#of(IStringExpression, Class)}.
 * <p>
 * Regression: process parameters typed as a {@link RepoIdAware} (e.g. {@link WebuiImageId}) used to fail
 * with {@code ExpressionCompileException("Value type ... is not supported")} when their default was
 * rewritten by {@code mf15-private-extensions/5783321_UpdateDefaultValues.sql} to {@code @SQL=...}.
 * The fix accepts any {@link RepoIdAware} subclass and wraps the int from the result set via the
 * typed {@code ofRepoId} factory.
 * <p>
 * The deserialization lambda itself requires DB access to test end-to-end; here we cover the type
 * acceptance path which is what the regression hinges on.
 */
class SqlDefaultValueExpressionTest
{
	private static final IStringExpression FAKE_SQL = mock(IStringExpression.class);

	@Test
	void of_supports_RepoIdAware_subclass_WebuiImageId()
	{
		final SqlDefaultValueExpression<?> expr = SqlDefaultValueExpression.of(FAKE_SQL, WebuiImageId.class);
		assertThat(expr).isNotNull();
		assertThat(expr.getValueClass()).isEqualTo(WebuiImageId.class);
	}

	@Test
	void of_unsupported_value_type_still_throws()
	{
		// A value class that is neither RepoIdAware nor in the explicit type list should still throw —
		// graceful degradation is intentional, blanket acceptance is not.
		assertThatThrownBy(() -> SqlDefaultValueExpression.of(FAKE_SQL, NotARepoId.class))
				.isInstanceOf(ExpressionCompileException.class)
				.hasMessageContaining("not supported by");
	}

	/** Plain class — neither in the explicit list nor a {@link RepoIdAware}. */
	private static final class NotARepoId
	{
	}
}
