package de.metas.ui.web.window.model.lookup.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.model.DocumentQuery;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DocumentZoomIntoService
{
	private static final Logger logger = LogManager.getLogger(DocumentZoomIntoService.class);
	private final DocumentDescriptorFactory documentDescriptorFactory;

	/**
	 * Retrieves document path for given ZoomInto info.
	 */
	public DocumentPath getDocumentPath(
			@NonNull final DocumentZoomIntoInfo zoomIntoInfo)
	{
		//
		// Find the root window ID
		final WindowId zoomIntoWindowIdEffective = getWindowId(zoomIntoInfo);

		if (!zoomIntoInfo.isRecordIdPresent())
		{
			return DocumentPath.newWindowRecord(zoomIntoWindowIdEffective);
		}

		final DocumentEntityDescriptor rootEntityDescriptor = getDocumentEntityDescriptor(zoomIntoWindowIdEffective);
		final String zoomIntoTableName = zoomIntoInfo.getTableName();

		//
		// We are dealing with a root document
		// (i.e. root descriptor's table is matching record's table)
		if (Objects.equals(rootEntityDescriptor.getTableName(), zoomIntoTableName))
		{
			final DocumentId rootDocumentId = DocumentId.of(zoomIntoInfo.getRecordId());
			return DocumentPath.rootDocumentPath(zoomIntoWindowIdEffective, rootDocumentId);
		}
		//
		// We are dealing with an included document
		else
		{
			// Search the root descriptor for any child entity descriptor which would match record's TableName
			final List<DocumentEntityDescriptor> childEntityDescriptors = rootEntityDescriptor.getIncludedEntities().stream()
					.filter(includedEntityDescriptor -> Objects.equals(includedEntityDescriptor.getTableName(), zoomIntoTableName))
					.collect(ImmutableList.toImmutableList());
			if (childEntityDescriptors.isEmpty())
			{
				throw new EntityNotFoundException("Cannot find the detail tab to zoom into")
						.setParameter("zoomIntoInfo", zoomIntoInfo)
						.setParameter("zoomIntoWindowId", zoomIntoWindowIdEffective)
						.setParameter("rootEntityDescriptor", rootEntityDescriptor);
			}
			else if (childEntityDescriptors.size() > 1)
			{
				logger.warn("More then one child descriptors matched our root descriptor. Picking the fist one. \nRoot descriptor: {} \nChild descriptors: {}", rootEntityDescriptor, childEntityDescriptors);
			}
			//
			final DocumentEntityDescriptor childEntityDescriptor = childEntityDescriptors.get(0);

			// Find the root DocumentId
			final DocumentId rowId = DocumentId.of(zoomIntoInfo.getRecordId());
			final DocumentId rootDocumentId = DocumentQuery.ofRecordId(childEntityDescriptor, rowId)
					.retrieveParentDocumentId(rootEntityDescriptor);

			//
			return DocumentPath.includedDocumentPath(zoomIntoWindowIdEffective, rootDocumentId, Check.assumeNotNull(childEntityDescriptor.getDetailId(), "Expected childEntityDescriptor.getDetailId not null"), rowId);
		}
	}

	@NonNull
	public WindowId getWindowId(@NonNull final DocumentZoomIntoInfo zoomIntoInfo)
	{
		if (zoomIntoInfo.getWindowId() != null)
		{
			return zoomIntoInfo.getWindowId();
		}

		final AdWindowId zoomInto_adWindowId;
		if (zoomIntoInfo.isRecordIdPresent())
		{
			zoomInto_adWindowId = RecordWindowFinder.newInstance(zoomIntoInfo.getTableName(), zoomIntoInfo.getRecordId())
					.checkRecordPresentInWindow()
					.checkParentRecord()
					.findAdWindowId()
					.orElse(null);
		}
		else
		{
			zoomInto_adWindowId = RecordWindowFinder.findAdWindowId(zoomIntoInfo.getTableName()).orElse(null);
		}

		if (zoomInto_adWindowId == null)
		{
			throw new EntityNotFoundException("No windowId found")
					.setParameter("zoomIntoInfo", zoomIntoInfo);
		}

		return WindowId.of(zoomInto_adWindowId);
	}

	private DocumentEntityDescriptor getDocumentEntityDescriptor(final WindowId windowId)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
		return descriptor.getEntityDescriptor();
	}
}
