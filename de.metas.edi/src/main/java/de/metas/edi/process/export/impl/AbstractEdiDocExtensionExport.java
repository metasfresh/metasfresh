package de.metas.edi.process.export.impl;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import de.metas.edi.model.I_EDI_Document_Extension;

/**
 *
 * Abstract subclass of {@link AbstractExport} that contains code to check if a given {@link I_EDI_Document_Extension} can be exported after all.
 *
 * @param <T>
 */
public abstract class AbstractEdiDocExtensionExport<T extends I_EDI_Document_Extension>
		extends AbstractExport<I_EDI_Document_Extension>
{
	public AbstractEdiDocExtensionExport(I_EDI_Document_Extension document, String tableIdentifier, ClientId expClientId)
	{
		super(document, tableIdentifier, expClientId);
	}

	protected void assertEligible(final T document)
	{
		if (!document.isEdiEnabled())
		{
			throw new AdempiereException("@" + I_EDI_Document_Extension.COLUMNNAME_IsEdiEnabled + "@=@N@");
		}

		// Assume document is completed/closed
		final String docStatus = document.getDocStatus();
		if (!I_EDI_Document_Extension.DOCSTATUS_Completed.equals(docStatus) && !I_EDI_Document_Extension.DOCSTATUS_Closed.equals(docStatus))
		{
			throw new AdempiereException("@Invalid@ @DocStatus@");
		}

		// Assume EDI Status is "Ready for processing"
		if (!I_EDI_Document_Extension.EDI_EXPORTSTATUS_Pending.equals(document.getEDI_ExportStatus())
				&& !I_EDI_Document_Extension.EDI_EXPORTSTATUS_Enqueued.equals(document.getEDI_ExportStatus()) // if enqueued, assume pending; we're just flagging it to avoid collisions in async
				&& !I_EDI_Document_Extension.EDI_EXPORTSTATUS_Error.equals(document.getEDI_ExportStatus()))
		{
			throw new AdempiereException("@Invalid@ @" + I_EDI_Document_Extension.COLUMNNAME_EDI_ExportStatus + "@");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getDocument()
	{
		return (T)super.getDocument();
	}
}
