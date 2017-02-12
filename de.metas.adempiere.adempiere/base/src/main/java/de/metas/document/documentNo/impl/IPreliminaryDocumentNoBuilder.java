package de.metas.document.documentNo.impl;

import org.compiere.model.I_C_DocType;
import org.compiere.util.Util;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.documentNo.IDocumentNoBuilder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Builder used to fetch preliminary document no informations.
 * 
 * Compared with {@link IDocumentNoBuilder} this builder:
 * <ul>
 * <li>is ALWAYS fetching current document sequence number, <b>without</b> incrementing it.
 * <li>provides an {@link IDocumentNoInfo} instance which contains more informations, compared with {@link IDocumentNoBuilder} which returns only a string document no
 * <li>works only with document types, compared with {@link IDocumentNoBuilder} which also works with table based sequences.
 * <li>has specific logic which can check if there was a change of document type and decides based on that if a new documentNo shall be generated or we shall keep the old one
 * </ul>
 * 
 * It's main purpose is to be used in callouts.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPreliminaryDocumentNoBuilder
{
	IDocumentNoInfo buildOrNull() throws DocumentNoBuilderException;

	IPreliminaryDocumentNoBuilder setNewDocType(I_C_DocType newDocType);

	IPreliminaryDocumentNoBuilder setOldDocType_ID(int oldDocType_ID);

	IPreliminaryDocumentNoBuilder setOldDocumentNo(String oldDocumentNo);

	IPreliminaryDocumentNoBuilder setDocumentModel(Object documentModel);

	//
	// Preliminary DocumentNo string helper methods
	//
	String DOCUMENTNO_MARKER_BEGIN = "<";
	String DOCUMENTNO_MARKER_END = ">";

	/**
	 * Wraps given documentNo with preliminary DocumentNo markers.
	 * 
	 * NOTE: this method assumes the given documentNo was not already wrapped.
	 * 
	 * @param documentNo
	 * @return documentNo wrapped with preliminary markers
	 */
	static String withPreliminaryMarkers(final String documentNo)
	{
		return DOCUMENTNO_MARKER_BEGIN + Util.coalesce(documentNo, "") + DOCUMENTNO_MARKER_END;
	}

	/**
	 * @param documentNo
	 * @return true if the given documentNo is wrapped with preliminary markers
	 */
	static boolean hasPreliminaryMarkers(final String documentNo)
	{
		if (documentNo == null)
		{
			return false;
		}

		final String documentNoNormalized = documentNo.trim();
		if (documentNoNormalized.isEmpty())
		{
			return false;
		}

		return documentNoNormalized.startsWith(DOCUMENTNO_MARKER_BEGIN) && documentNoNormalized.endsWith(DOCUMENTNO_MARKER_END);
	}

}
