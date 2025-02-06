package de.metas.ui.web.window.descriptor.factory.standard;

import org.adempiere.ad.element.api.AdUIElementId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LabelFieldNameFactoryTest
{
	@Test
	@SuppressWarnings("JoinAssertThatStatements")
	void createFieldName()
	{
		final LabelFieldNameFactory labelFieldNameFactory = new LabelFieldNameFactory();

		assertThat(labelFieldNameFactory.createFieldName(AdUIElementId.ofRepoId(1), "MyTableName")).isEqualTo("Labels_MyTableName");
		assertThat(labelFieldNameFactory.createFieldName(AdUIElementId.ofRepoId(1), "MyTableName")).isEqualTo("Labels_MyTableName");

		assertThat(labelFieldNameFactory.createFieldName(AdUIElementId.ofRepoId(2), "MyTableName")).isEqualTo("Labels_MyTableName_2");
		assertThat(labelFieldNameFactory.createFieldName(AdUIElementId.ofRepoId(3), "MyTableName")).isEqualTo("Labels_MyTableName_3");
	}
}