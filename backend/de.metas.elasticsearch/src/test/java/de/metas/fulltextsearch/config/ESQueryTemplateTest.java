package de.metas.fulltextsearch.config;

import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ESQueryTemplateTest
{
	@Nested
	class isOrgFilterParameterRequired
	{
		@Test
		void required()
		{
			final ESQueryTemplate esQueryTemplate = ESQueryTemplate.ofJsonString("aaa @OrgFilter@ bbb");
			assertThat(esQueryTemplate.isOrgFilterParameterRequired()).isTrue();
		}

		@Test
		void notRequired()
		{
			final ESQueryTemplate esQueryTemplate = ESQueryTemplate.ofJsonString("aaa @SomeOtherParam@ bbb");
			assertThat(esQueryTemplate.isOrgFilterParameterRequired()).isFalse();
		}
	}

	@Nested
	class resolve
	{
		@Test
		void query_param_gets_quoted()
		{
			final ESQueryTemplate esQueryTemplate = ESQueryTemplate.ofJsonString("@query@");
			assertThat(esQueryTemplate.resolve(Evaluatees.ofSingleton(ESQueryTemplate.PARAM_query.getName(), "test")))
					.isEqualTo("\"test\"");
		}

		@Test
		void queryStartsWith_param_gets_quoted()
		{
			final ESQueryTemplate esQueryTemplate = ESQueryTemplate.ofJsonString("@queryStartsWith@");
			assertThat(esQueryTemplate.resolve(Evaluatees.ofSingleton(ESQueryTemplate.PARAM_queryStartsWith.getName(), "test*")))
					.isEqualTo("\"test*\"");
		}

		@Test
		void orgFilter_param_is_replaced_as_is()
		{
			final ESQueryTemplate esQueryTemplate = ESQueryTemplate.ofJsonString("@OrgFilter@");
			assertThat(esQueryTemplate.resolve(Evaluatees.ofSingleton(ESQueryTemplate.PARAM_orgFilter.getName(), "MyOrgFilter")))
					.isEqualTo("MyOrgFilter");
		}
	}
}