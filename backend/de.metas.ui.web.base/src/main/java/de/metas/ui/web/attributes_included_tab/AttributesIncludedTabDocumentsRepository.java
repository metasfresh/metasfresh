package de.metas.ui.web.attributes_included_tab;

import de.metas.attributes_included_tab.AttributesIncludedTabService;
import de.metas.attributes_included_tab.data.AttributesIncludedTabData;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataField;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataKey;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.OrderedDocumentsList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AttributesIncludedTabDocumentsRepository implements DocumentsRepository
{
	@NonNull private final AttributesIncludedTabService attributesIncludedTabService;

	@Override
	public OrderedDocumentsList retrieveDocuments(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		final Document document = retrieveDocument(query, changesCollector);
		return OrderedDocumentsList.of(document, query.getOrderBys());
	}

	@Override
	public Document retrieveDocument(final DocumentQuery query, final IDocumentChangesCollector changesCollector)
	{
		return retrieveDocumentIfExists(query, changesCollector)
				.orElseGet(() -> createNewDocument(query.getEntityDescriptor(), query.getParentDocument(), changesCollector));
	}

	private Optional<Document> retrieveDocumentIfExists(
			@NonNull final DocumentQuery query,
			@NonNull final IDocumentChangesCollector changesCollector)
	{
		final AttributesIncludedTabData data = attributesIncludedTabService.getData(extractKey(query));

		Document document = null;
		if (query.getExistingDocumentsSupplier() != null)
		{
			final DocumentId documentId = AttributesIncludedTabDataAsDocumentValuesSupplier.extractDocumentId(data);
			document = query.getExistingDocumentsSupplier().apply(documentId);
		}
		if (document == null)
		{
			document = Document.builder(query.getEntityDescriptor())
					.setParentDocument(query.getParentDocument())
					.setChangesCollector(changesCollector)
					.initializeAsExistingRecord(new AttributesIncludedTabDataAsDocumentValuesSupplier(data));
		}

		return Optional.of(document);
	}

	private static AttributesIncludedTabDataKey extractKey(@NonNull final DocumentQuery query)
	{
		return AttributesIncludedTabDataKey.of(
				extractRecordRef(query.getParentDocument()),
				extractEntityBinding(query.getEntityDescriptor()).getAttributesIncludedTabId()
		);
	}

	private static AttributesIncludedTabDataKey extractKey(@NonNull final Document document)
	{
		return AttributesIncludedTabDataKey.of(
				extractRecordRef(document.getParentDocument()),
				extractEntityBinding(document.getEntityDescriptor()).getAttributesIncludedTabId()
		);
	}

	private static AttributesIncludedTabEntityBinding extractEntityBinding(final Document document)
	{
		return extractEntityBinding(document.getEntityDescriptor());
	}

	private static AttributesIncludedTabEntityBinding extractEntityBinding(final DocumentEntityDescriptor entityDescriptor)
	{
		return entityDescriptor.getDataBinding(AttributesIncludedTabEntityBinding.class);
	}

	@NonNull
	private static AttributesIncludedTabFieldBinding extractFieldBinding(final Document document, final String fieldName)
	{
		return document.getEntityDescriptor().getField(fieldName).getDataBindingNotNull(AttributesIncludedTabFieldBinding.class);
	}

	private static TableRecordReference extractRecordRef(@NonNull final Document document)
	{
		return TableRecordReference.of(document.getEntityDescriptor().getTableName(), document.getDocumentIdAsInt());
	}

	@Override
	public DocumentId retrieveParentDocumentId(final DocumentEntityDescriptor parentEntityDescriptor, final DocumentQuery childDocumentQuery)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public Document createNewDocument(final DocumentEntityDescriptor entityDescriptor, @Nullable final Document parentDocument, final IDocumentChangesCollector changesCollector)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void refresh(final Document document)
	{
		final AttributesIncludedTabDataKey key = extractKey(document);
		final AttributesIncludedTabData data = attributesIncludedTabService.getData(key);
		refreshDocumentFromData(document, data);
	}

	@Override
	public SaveResult save(final Document document)
	{
		final AttributesIncludedTabEntityBinding entityBinding = extractEntityBinding(document);

		final AttributesIncludedTabData data = attributesIncludedTabService.updateByKey(
				extractKey(document),
				entityBinding.getAttributeIds(),
				(attributeId, field) -> {
					final String fieldName = entityBinding.getFieldNameByAttributeId(attributeId);
					final IDocumentFieldView documentField = document.getFieldView(fieldName);
					if (!documentField.hasChangesToSave())
					{
						return field;
					}

					final AttributesIncludedTabFieldBinding fieldBinding = extractFieldBinding(document, fieldName);

					return fieldBinding.updateData(
							field != null ? field : newDataField(fieldBinding),
							documentField);
				});

		refreshDocumentFromData(document, data);

		// Notify the parent document that one of its children were saved (copied from SqlDocumentsRepository)
		document.getParentDocument().onChildSaved(document);

		return SaveResult.SAVED;
	}

	private static AttributesIncludedTabDataField newDataField(final AttributesIncludedTabFieldBinding fieldBinding)
	{
		return AttributesIncludedTabDataField.builder()
				.attributeId(fieldBinding.getAttributeId())
				.valueType(fieldBinding.getAttributeValueType())
				.build();
	}

	private void refreshDocumentFromData(@NonNull final Document document, @NonNull final AttributesIncludedTabData data)
	{
		document.refreshFromSupplier(new AttributesIncludedTabDataAsDocumentValuesSupplier(data));

	}

	@Override
	public void delete(final Document document)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String retrieveVersion(final DocumentEntityDescriptor entityDescriptor, final int documentIdAsInt) {return AttributesIncludedTabDataAsDocumentValuesSupplier.VERSION_DEFAULT;}

	@Override
	public int retrieveLastLineNo(final DocumentQuery query)
	{
		throw new UnsupportedOperationException();
	}
}

