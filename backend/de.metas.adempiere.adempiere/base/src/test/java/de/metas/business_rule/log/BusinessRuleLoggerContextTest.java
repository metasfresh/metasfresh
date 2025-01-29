package de.metas.business_rule.log;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRuleLoggerContextTest
{
	@Nested
	class isLogLevelEnabled
	{
		@Test
		void root()
		{
			assertThat(BusinessRuleLoggerContext.ROOT.getEffectiveLogLevel()).isEqualTo(BusinessRuleLogLevel.WARN);
			assertThat(BusinessRuleLoggerContext.ROOT.isLogLevelEnabled(BusinessRuleLogLevel.WARN)).isTrue();
			assertThat(BusinessRuleLoggerContext.ROOT.isLogLevelEnabled(BusinessRuleLogLevel.INFO)).isFalse();
			assertThat(BusinessRuleLoggerContext.ROOT.isLogLevelEnabled(BusinessRuleLogLevel.DEBUG)).isFalse();
		}

		@Test
		void child_noLogLevel()
		{
			final BusinessRuleLoggerContext context = BusinessRuleLoggerContext.ROOT.newChild().build();
			assertThat(context.getEffectiveLogLevel()).isEqualTo(BusinessRuleLogLevel.WARN);
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.WARN)).isTrue();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.INFO)).isFalse();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.DEBUG)).isFalse();
		}

		@Test
		void child_INFO()
		{
			final BusinessRuleLoggerContext context = BusinessRuleLoggerContext.ROOT.newChild().logLevel(BusinessRuleLogLevel.INFO).build();
			assertThat(context.getEffectiveLogLevel()).isEqualTo(BusinessRuleLogLevel.INFO);
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.WARN)).isTrue();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.INFO)).isTrue();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.DEBUG)).isFalse();
		}

		@Test
		void child_DEBUG()
		{
			final BusinessRuleLoggerContext context = BusinessRuleLoggerContext.ROOT.newChild().logLevel(BusinessRuleLogLevel.DEBUG).build();
			assertThat(context.getEffectiveLogLevel()).isEqualTo(BusinessRuleLogLevel.DEBUG);
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.WARN)).isTrue();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.INFO)).isTrue();
			assertThat(context.isLogLevelEnabled(BusinessRuleLogLevel.DEBUG)).isTrue();
		}

	}
}