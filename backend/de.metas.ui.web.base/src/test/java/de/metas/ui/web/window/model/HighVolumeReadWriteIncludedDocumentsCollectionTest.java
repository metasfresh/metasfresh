package de.metas.ui.web.window.model;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository.SaveResult;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Regression test for ConcurrentModificationException in
 * {@link HighVolumeReadWriteIncludedDocumentsCollection#saveIfHasChanges()}.
 *
 * <p>The bug: {@code saveIfHasChanges()} iterates the live map view returned by
 * {@code getChangedDocuments()} (== {@code _documentsWithChanges.values()}). Inside the loop,
 * {@code document.saveIfHasChanges()} eventually calls back into the collection's
 * {@code onChildSaved} → {@code forgetChangedDocument} → {@code _documentsWithChanges.remove(...)}.
 * Removing a non-last entry mid-iteration trips the LinkedHashMap iterator's modCount check
 * and throws {@link java.util.ConcurrentModificationException}.</p>
 */
class HighVolumeReadWriteIncludedDocumentsCollectionTest
{
	private static final WindowId WINDOW_ID = WindowId.of(9999);
	private static final DetailId DETAIL_ID = DetailId.fromAD_Tab_ID(AdTabId.ofRepoId(1));

	/** Auto-increment to give each new child document a unique {@link DocumentId}. */
	private final AtomicInteger nextChildId = new AtomicInteger(1);

	/** The stub repository used for child documents. */
	private DocumentsRepository childRepository;

	/** The parent document that owns the included-documents collection under test. */
	private Document parentDocument;

	/** The collection under test. */
	private HighVolumeReadWriteIncludedDocumentsCollection collection;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// Register the bean DocumentFilterDescriptorsProvidersService so that
		// DocumentEntityDescriptor.Builder.createFilterDescriptors() can resolve it from Spring.
		final DocumentFilterDescriptorsProviderFactory nullFactory = new DocumentFilterDescriptorsProviderFactory()
		{
			@Nullable
			@Override
			public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
			{
				return null;
			}
		};
		SpringContextHolder.registerJUnitBean(new DocumentFilterDescriptorsProvidersService(ImmutableList.of(nullFactory)));

		// -----------------------------------------------------------------------
		// Build the stub DocumentsRepository.
		//
		// Faithful reproduction of SqlDocumentsRepository.save():
		//   1. Make the child report no remaining changes (markAsSaved).
		//   2. Notify the parent: child.getParentDocument().onChildSaved(child).
		//   3. Return SAVED.
		//
		// Step 2 is what removes the child from _documentsWithChanges mid-iteration,
		// triggering the ConcurrentModificationException when >=2 children are present.
		// -----------------------------------------------------------------------
		childRepository = new DocumentsRepository()
		{
			@Override
			public Document createNewDocument(
					@NonNull final DocumentEntityDescriptor entityDescriptor,
					@Nullable final Document parentDoc,
					@NonNull final IDocumentChangesCollector changesCollector)
			{
				return Document.builder(entityDescriptor)
						.setParentDocument(parentDoc)
						.setChangesCollector(changesCollector)
						.initializeAsNewDocument(DocumentId.of(nextChildId.getAndIncrement()), "v1");
			}

			@Override
			public SaveResult save(@NonNull final Document document)
			{
				// Step 1: clear the "new / has-changes" flag so hasChangesRecursivelly() → false
				document.markAsSaved();

				// Step 2: notify parent — this triggers onChildSaved → forgetChangedDocument
				//         → _documentsWithChanges.remove() while the iterator is live → CME
				if (!document.isRootDocument())
				{
					document.getParentDocument().onChildSaved(document);
				}

				return SaveResult.SAVED;
			}

			// ---- methods below are never called in this test ----

			@Override
			public OrderedDocumentsList retrieveDocuments(
					@NonNull final DocumentQuery query,
					@NonNull final IDocumentChangesCollector changesCollector)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public Document retrieveDocument(
					@NonNull final DocumentQuery query,
					@NonNull final IDocumentChangesCollector changesCollector)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public DocumentId retrieveParentDocumentId(
					@NonNull final DocumentEntityDescriptor parentEntityDescriptor,
					@NonNull final DocumentQuery childDocumentQuery)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public void refresh(@NonNull final Document document)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public void delete(@NonNull final Document document)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public String retrieveVersion(
					@NonNull final DocumentEntityDescriptor entityDescriptor,
					final int documentIdAsInt)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}

			@Override
			public int retrieveLastLineNo(@NonNull final DocumentQuery query)
			{
				throw new UnsupportedOperationException("not needed in this test");
			}
		};

		// -----------------------------------------------------------------------
		// Build the stub DocumentEntityDataBindingDescriptor for child documents.
		// Its only job is to vend the stub childRepository.
		// -----------------------------------------------------------------------
		final DocumentEntityDataBindingDescriptor childDataBinding = () -> childRepository;

		// -----------------------------------------------------------------------
		// Build the child DocumentEntityDescriptor.
		// - setHighVolume(true) → getIncludedDocumentsCollectionFactory() returns
		//   HighVolumeReadWriteIncludedDocumentsCollection::newInstance (not SingleRow).
		// - disableDefaultTableCallouts() → ITabCallout.NULL (no Spring ITabCalloutFactory needed).
		// -----------------------------------------------------------------------
		final DocumentEntityDescriptor childEntityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(WINDOW_ID.toAdWindowId())
				.setDetailId(DETAIL_ID)
				.setHighVolume(true)
				.setDataBinding(childDataBinding)
				.disableDefaultTableCallouts()
				.build();

		// -----------------------------------------------------------------------
		// Build the parent DocumentEntityDescriptor.
		// The parent itself does NOT need a real data binding for this test because
		// the parent's saveIfHasChanges() is never called — we drive the collection
		// directly.  We must still provide *some* binding (the NULL builder) which
		// yields a null dataBinding; that is fine as long as the parent's repository
		// is never invoked.
		// -----------------------------------------------------------------------
		final DocumentEntityDescriptor parentEntityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(WINDOW_ID.toAdWindowId())
				.addIncludedEntity(childEntityDescriptor)
				.disableDefaultTableCallouts()
				.build();

		// -----------------------------------------------------------------------
		// Build the parent Document as a new (writable) document.
		// -----------------------------------------------------------------------
		parentDocument = Document.builder(parentEntityDescriptor)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.initializeAsNewDocument(DocumentId.of(1), "v1");

		// Retrieve the collection that was created for our detail ID.
		collection = (HighVolumeReadWriteIncludedDocumentsCollection)
				parentDocument.getIncludedDocumentsCollection(DETAIL_ID);
	}

	/**
	 * Reproduces the ConcurrentModificationException.
	 *
	 * <p>With the unfixed code the assertion fails:
	 * <pre>
	 * java.util.ConcurrentModificationException
	 *   at java.util.LinkedHashMap$LinkedHashIterator.nextNode(LinkedHashMap.java:756)
	 *   at ...HighVolumeReadWriteIncludedDocumentsCollection.saveIfHasChanges(...)
	 * </pre>
	 * After applying the fix (iterating a defensive {@code new ArrayList<>(getChangedDocuments())}
	 * instead of the live map view), the test passes.</p>
	 */
	@Test
	void saveIfHasChanges_withMultipleChangedChildren_doesNotThrowConcurrentModificationException()
	{
		// Arrange — add 3 changed child documents directly to _documentsWithChanges
		// via the public onChildChanged() hook (same package → accessible here).
		// Three children guarantee that even if the first remove is "last in the
		// iteration order" we still have a non-last remove to trigger the CME.
		addNewChildToCollection();
		addNewChildToCollection();
		addNewChildToCollection();

		// Act + Assert
		assertThatCode(() -> collection.saveIfHasChanges())
				.doesNotThrowAnyException();
	}

	// ---- helpers ----------------------------------------------------------------

	/**
	 * Builds a fresh child document and registers it as changed in the collection.
	 * Uses {@link IIncludedDocumentsCollection#onChildChanged(Document)} (public interface
	 * method) rather than {@link HighVolumeReadWriteIncludedDocumentsCollection#createNewDocument()}
	 * to avoid the "parent document is new → create-new disallowed" guard.
	 */
	private void addNewChildToCollection()
	{
		final Document child = Document.builder(getChildEntityDescriptor())
				.setParentDocument(parentDocument)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.initializeAsNewDocument(DocumentId.of(nextChildId.getAndIncrement()), "v1");

		collection.onChildChanged(child);
	}

	private DocumentEntityDescriptor getChildEntityDescriptor()
	{
		// The collection holds a reference to its entityDescriptor; reach it through
		// one of the public factory methods to avoid duplicating descriptor state.
		// Since the collection was created from childEntityDescriptor (added as included
		// entity on the parent), we can retrieve it from the parent's entity descriptor.
		return parentDocument.getEntityDescriptor()
				.getIncludedEntityByDetailId(DETAIL_ID);
	}
}
