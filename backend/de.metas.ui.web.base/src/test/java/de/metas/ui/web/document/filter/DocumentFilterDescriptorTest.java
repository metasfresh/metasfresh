package de.metas.ui.web.document.filter;

import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterParam;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DocumentFilterDescriptorTest
{
	@Test
	void unwrap_ParameterWithNullOperator()
	{
		final DocumentFilterDescriptor descriptor = DocumentFilterDescriptor.builder()
				.setFilterId("filterId")
				.setDisplayName("my filter")
				.addParameter(DocumentFilterParamDescriptor.builder()
						.mandatory(true)
						.fieldName("param1")
						.displayName("parameter 1")
						.widgetType(DocumentFieldWidgetType.Text)
						.operator(null) // intentionally we keep this null to make sure we recover from it
				)
				.build();

		final DocumentFilter filter = descriptor.unwrap(JSONDocumentFilter.builder()
				.filterId("filterId")
				.parameter(JSONDocumentFilterParam.builder()
						.parameterName("param1")
						.value("1234")
						.build())
				.build());

		Assertions.assertThat(filter)
				.usingRecursiveComparison()
				.isEqualTo(DocumentFilter.builder()
						.filterId("filterId")
						.parameter(DocumentFilterParam.builder()
								.setJoinAnd(true)
								.setFieldName("param1")
								.setOperator(DocumentFilterParam.Operator.EQUAL) // expect to be EQUAL when in descriptor is not set
								.setValue("1234")
								.build())
						.build());
	}
}