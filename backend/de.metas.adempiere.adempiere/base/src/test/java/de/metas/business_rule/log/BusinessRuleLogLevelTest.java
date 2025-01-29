package de.metas.business_rule.log;

import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRuleLogLevelTest
{

	@Test
	void extractMaxLogLevel()
	{
		assetThatExtractMaxLogLevel().isEmpty();
		assetThatExtractMaxLogLevel(null, null, null).isEmpty();
		assetThatExtractMaxLogLevel(BusinessRuleLogLevel.WARN).contains(BusinessRuleLogLevel.WARN);
		assetThatExtractMaxLogLevel(BusinessRuleLogLevel.WARN, BusinessRuleLogLevel.INFO).contains(BusinessRuleLogLevel.INFO);
		assetThatExtractMaxLogLevel(BusinessRuleLogLevel.WARN, BusinessRuleLogLevel.INFO, BusinessRuleLogLevel.DEBUG).contains(BusinessRuleLogLevel.DEBUG);
		assetThatExtractMaxLogLevel(BusinessRuleLogLevel.WARN, null, BusinessRuleLogLevel.DEBUG).contains(BusinessRuleLogLevel.DEBUG);
	}

	private OptionalAssert<BusinessRuleLogLevel> assetThatExtractMaxLogLevel(final BusinessRuleLogLevel... logLevels)
	{
		return assertThat(BusinessRuleLogLevel.extractMaxLogLevel(Arrays.asList(logLevels), Function.identity()));
	}

}