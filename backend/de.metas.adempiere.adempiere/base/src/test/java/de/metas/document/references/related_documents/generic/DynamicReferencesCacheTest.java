package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableList;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicReferencesCacheTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void changeLog_isNeverAnsweredFromTheCachedReferenceSet()
	{
		// the cached reference set of AD_ChangeLog was loaded before C_Doc_Outbound_Config had any change-log row
		Services.registerService(ITableRecordIdDAO.class, new ITableRecordIdDAO()
		{
			@Override
			public List<TableRecordIdDescriptor> getTableRecordIdReferences(final String tableName) { return ImmutableList.of(); }

			@Override
			public List<TableRecordIdDescriptor> retrieveAllTableRecordIdReferences() { return ImmutableList.of(); }
		});

		final OptionalBoolean hasReferences = new DynamicReferencesCache().hasReferences("AD_ChangeLog", "C_Doc_Outbound_Config");

		assertThat(hasReferences).isEqualTo(OptionalBoolean.UNKNOWN);
	}
}
