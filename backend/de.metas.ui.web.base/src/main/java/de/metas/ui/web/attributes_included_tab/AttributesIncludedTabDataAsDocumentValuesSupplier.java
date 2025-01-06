package de.metas.ui.web.attributes_included_tab;

import de.metas.attributes_included_tab.data.AttributesIncludedTabData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.model.Document;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;

@RequiredArgsConstructor
final class AttributesIncludedTabDataAsDocumentValuesSupplier implements Document.DocumentValuesSupplier
{
	static final String VERSION_DEFAULT = "0";

	@NonNull private final AttributesIncludedTabData data;

	@Override
	public DocumentId getDocumentId() {return extractDocumentId(data);}

	public static DocumentId extractDocumentId(final AttributesIncludedTabData data)
	{
		final TableRecordReference recordRef = data.getKey().getRecordRef();
		return DocumentId.of("T" + recordRef.getTableName() + "-R" + recordRef.getRecord_ID());
	}

	@Override
	public String getVersion()
	{
		return VERSION_DEFAULT;
	}

	@Override
	public Object getValue(@NonNull final DocumentFieldDescriptor fieldDescriptor)
	{
		return fieldDescriptor.getDataBindingNotNull(AttributesIncludedTabFieldBinding.class).getValue(data);
	}
}
