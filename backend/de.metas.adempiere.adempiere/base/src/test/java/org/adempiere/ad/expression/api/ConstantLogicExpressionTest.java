package org.adempiere.ad.expression.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConstantLogicExpressionTest
{
	@Test
	void negate()
	{
		Assertions.assertThat(ConstantLogicExpression.TRUE.negate()).isSameAs(ConstantLogicExpression.FALSE);
		Assertions.assertThat(ConstantLogicExpression.FALSE.negate()).isSameAs(ConstantLogicExpression.TRUE);
	}
}