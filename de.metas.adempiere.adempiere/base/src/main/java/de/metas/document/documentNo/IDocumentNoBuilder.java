/**
 *
 */
package de.metas.document.documentNo;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.compiere.model.PO;

import com.google.common.base.Supplier;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;

/**
 * @author cg
 *
 */
public interface IDocumentNoBuilder
{
	public static final String NO_DOCUMENTNO = null;

	/**
	 * Builds the DocumentNo string.
	 *
	 * @return DocumentNo string or {@link #NO_DOCUMENTNO}
	 * @throws DocumentNoBuilderException in case building fails
	 */
	String build() throws DocumentNoBuilderException;

	IDocumentNoBuilder setAD_Client_ID(int adClientId);

	IDocumentNoBuilder setDocumentSequenceInfo(DocumentSequenceInfo documentSeqInfo);

	IDocumentNoBuilder setDocumentSequenceInfo(Supplier<DocumentSequenceInfo> documentSeqInfoSupplier);

	IDocumentNoBuilder setDocumentSequenceInfoByTableName(String tableName, int adClientId, int adOrgId);

	IDocumentNoBuilder setFailOnError(boolean failOnError);

	IDocumentNoBuilder setPO(PO po);

	IDocumentNoBuilder setSequenceByDocTypeId(int C_DocType_ID, boolean useDefiniteSequence);

	/**
	 * Explicitly set which is the sequence no to be used. In this case, the builder won't fetch and increment current sequence number but it will just use this one.
	 *
	 * @param sequenceNo
	 * @return
	 */
	IDocumentNoBuilder setSequenceNo(int sequenceNo);

	IDocumentNoBuilder setTrxName(String trxName);
}
