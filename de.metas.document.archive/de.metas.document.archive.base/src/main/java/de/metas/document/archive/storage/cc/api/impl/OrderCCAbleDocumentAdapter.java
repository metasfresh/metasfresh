package de.metas.document.archive.storage.cc.api.impl;

import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.util.StringUtils;

public class OrderCCAbleDocumentAdapter implements ICCAbleDocument
{
	private final String fileName;

	private static final String FILENAME_PATTERN = "{0}@F201 {1}@@F211 {2}@.pdf"; // TODO hard-coded

	public OrderCCAbleDocumentAdapter(
			final String documentTitle,
			final String recipientName,
			final String fax)
	{
		super();

		fileName = StringUtils.formatMessage(OrderCCAbleDocumentAdapter.FILENAME_PATTERN,
				documentTitle == null ? "" : documentTitle,
				recipientName == null ? "" : recipientName,
				fax == null ? "" : fax);
	}

	@Override
	public String getFileName()
	{
		return fileName;
	}
}
