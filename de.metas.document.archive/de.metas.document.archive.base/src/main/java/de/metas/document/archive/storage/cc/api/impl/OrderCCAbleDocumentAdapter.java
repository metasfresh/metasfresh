package de.metas.document.archive.storage.cc.api.impl;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.StringUtils;

import de.metas.document.archive.storage.cc.api.ICCAbleDocument;

public class OrderCCAbleDocumentAdapter implements ICCAbleDocument
{
	private final String fileName;

	private static final String FILENAME_PATTERN = "{}@F201 {}@@F211 {}@.pdf"; // TODO hard-coded

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
