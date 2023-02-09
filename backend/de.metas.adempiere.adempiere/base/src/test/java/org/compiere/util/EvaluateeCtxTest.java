package org.compiere.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class EvaluateeCtxTest
{
	@Test
	public void test_get_ValueIfExists_Object_fromEmptyContext()
	{
		final Properties ctx = new Properties();
		final Evaluatee evaluatee = Evaluatees.ofCtx(ctx);

		assertThat(
				evaluatee.get_ValueIfExists("C_BPartner_ID", Object.class)
		).isEmpty();
	}

}