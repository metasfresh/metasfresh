package de.metas.ui.web.window.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Objects;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends EntityNotFoundException
{
	private final DocumentPath documentPath;

	public DocumentNotFoundException(final DocumentPath documentPath)
	{
		super("No document found for " + documentPath);
		this.documentPath = documentPath;
	}

	public DocumentNotFoundException(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
	{
		this(DocumentPath.rootDocumentPath(documentType, documentTypeId, documentId));
	}

	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	public void rethrowIfNotMatching(final DocumentPath documentPath)
	{
		if (!Objects.equal(this.documentPath, documentPath))
		{
			throw this;
		}
	}

}
