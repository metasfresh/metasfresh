package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.MQuery;
import org.compiere.util.Evaluatee;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class GenericRelatedDocumentsProviderTest
{
	static final String RECORD_ID = "Record_ID";

	@Builder(builderMethodName = "descriptor", builderClassName = "$DescriptorBuilder")
	GenericRelatedDocumentDescriptor createDescriptor(
			@NonNull final String targetTableName,
			@Nullable final String tabSqlWhereClause,
			@NonNull @Singular final ImmutableList<String> columnNames)
	{
		final GenericTargetWindowInfo window = GenericTargetWindowInfo.builder()
				.name(ImmutableTranslatableString.builder().defaultValue("test").build())
				.targetWindowId(AdWindowId.ofRepoId(123))
				.targetWindowInternalName("test")
				.targetTableName(targetTableName)
				.tabSqlWhereClause(tabSqlWhereClause)
				.build();

		final ArrayList<GenericTargetColumnInfo> columns = new ArrayList<>();
		for (final String columnName : columnNames)
		{
			columns.add(
					GenericTargetColumnInfo.builder()
							.caption(TranslatableStrings.anyLanguage(columnName))
							.columnName(columnName)
							.isDynamic(RECORD_ID.equals(columnName))
							.build()
			);
		}

		return GenericRelatedDocumentDescriptor.builder()
				.targetWindow(window)
				.targetColumns(columns)
				.build();
	}

	public IZoomSource fromDocument(final int adTableId, final int recordId)
	{
		return new IZoomSource()
		{
			@Override
			public Properties getCtx() {throw new UnsupportedOperationException();}

			@Override
			public Evaluatee createEvaluationContext() {throw new UnsupportedOperationException();}

			@Override
			public String getTrxName() {throw new UnsupportedOperationException();}

			@Override
			public AdWindowId getAD_Window_ID() {throw new UnsupportedOperationException();}

			@Override
			public String getTableName() {throw new UnsupportedOperationException();}

			@Override
			public int getAD_Table_ID() {return adTableId;}

			@Override
			public String getKeyColumnNameOrNull() {throw new UnsupportedOperationException();}

			@Override
			public boolean isGenericZoomOrigin() {throw new UnsupportedOperationException();}

			@Override
			public int getRecord_ID() {return recordId;}

			@Override
			public boolean hasField(final String columnName) {throw new UnsupportedOperationException();}

			@Override
			public Object getFieldValue(final String columnName) {throw new UnsupportedOperationException();}
		};
	}

	@Nested
	class buildMQuery
	{
		@Test
		void C_Flatrate_Term_ID()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.columnName("C_Flatrate_Term_ID")
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName("C_Flatrate_Term_ID"), descriptor, fromDocument);
			assertThat(query).isNotNull();
			assertThat(query.getTableName()).isEqualTo("MyTable");
			assertThat(query.getZoomTableName()).isEqualTo("MyTable");
			assertThat(query.getZoomColumnName()).isEqualTo("C_Flatrate_Term_ID");
			assertThat(query.getZoomValue()).isEqualTo(999);
			assertThat(query.getWhereClause()).isEqualTo("C_Flatrate_Term_ID=999");
		}

		@Test
		void C_Flatrate_Term_ID_and_TabWhereClause()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.tabSqlWhereClause("AD_User_ID=@#AD_User_ID@ OR IsShowAll='Y'")
					.columnName("C_Flatrate_Term_ID")
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName("C_Flatrate_Term_ID"), descriptor, fromDocument);
			assertThat(query).isNotNull();
			assertThat(query.getWhereClause()).isEqualTo("C_Flatrate_Term_ID=999 AND (AD_User_ID=@#AD_User_ID@ OR IsShowAll='Y')");
		}

		@Test
		void Record_ID()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.columnName(RECORD_ID)
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName(RECORD_ID), descriptor, fromDocument);
			assertThat(query).isNotNull();
			assertThat(query.getWhereClause()).isEqualTo("AD_Table_ID=540320 AND Record_ID=999");
		}

		@Test
		void Record_ID_and_TabWhereClause()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.tabSqlWhereClause("AD_User_ID=@#AD_User_ID@ OR IsShowAll='Y'")
					.columnName(RECORD_ID)
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName(RECORD_ID), descriptor, fromDocument);
			assertThat(query).isNotNull();
			assertThat(query.getWhereClause()).isEqualTo("AD_Table_ID=540320 AND Record_ID=999 AND (AD_User_ID=@#AD_User_ID@ OR IsShowAll='Y')");
		}

		@Test
		void C_Flatrate_Term_ID_and_Record_ID()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.columnName("C_Flatrate_Term_ID")
					.columnName(RECORD_ID)
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			{
				final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName("C_Flatrate_Term_ID"), descriptor, fromDocument);
				assertThat(query).isNotNull();
				assertThat(query.getWhereClause()).isEqualTo("((C_Flatrate_Term_ID=999) OR (AD_Table_ID=540320 AND Record_ID=999))");
			}

			{
				final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName(RECORD_ID), descriptor, fromDocument);
				assertThat(query).isNull();
			}
		}

		@Test
		void C_BPartner_ID_and_Bill_BPartner_ID_and_Record_ID()
		{
			final GenericRelatedDocumentDescriptor descriptor = descriptor()
					.targetTableName("MyTable")
					.columnName("C_BPartner_ID")
					.columnName("Bill_BPartner_ID")
					.columnName(RECORD_ID)
					.build();

			final IZoomSource fromDocument = fromDocument(540320, 999);

			{
				final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName("C_BPartner_ID"), descriptor, fromDocument);
				assertThat(query).isNotNull();
				assertThat(query.getWhereClause()).isEqualTo("C_BPartner_ID=999");
			}
			{
				final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName("Bill_BPartner_ID"), descriptor, fromDocument);
				assertThat(query).isNotNull();
				assertThat(query.getWhereClause()).isEqualTo("Bill_BPartner_ID=999");
			}

			{
				final MQuery query = GenericRelatedDocumentsProvider.buildMQuery(descriptor.getColumnByName(RECORD_ID), descriptor, fromDocument);
				assertThat(query).isNotNull();
				assertThat(query.getWhereClause()).isEqualTo("((AD_Table_ID=540320 AND Record_ID=999) AND (NOT (C_BPartner_ID=999)) AND (NOT (Bill_BPartner_ID=999)))");
			}
		}
	}
}