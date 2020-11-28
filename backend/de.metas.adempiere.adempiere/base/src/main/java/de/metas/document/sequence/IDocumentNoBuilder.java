/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.document.sequence;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.sequence.impl.DocumentNoParts;
import de.metas.document.sequence.impl.IPreliminaryDocumentNoBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;

public interface IDocumentNoBuilder
{
	String NO_DOCUMENTNO = null;

	/** Sequence name prefix for Table Document Nos (also used for Value) */
	String PREFIX_DOCSEQ = "DocumentNo_";

	/**
	 * Builds the DocumentNo string.
	 *
	 * @return DocumentNo string or {@link #NO_DOCUMENTNO}
	 * @throws DocumentNoBuilderException in case building fails
	 */
	@Nullable
	String build() throws DocumentNoBuilderException;

	@Nullable
	DocumentNoParts buildParts();

	IDocumentNoBuilder setClientId(ClientId clientId);

	IDocumentNoBuilder setDocumentSequenceInfo(DocumentSequenceInfo documentSeqInfo);

	IDocumentNoBuilder setDocumentSequenceInfoBySequenceId(final DocSequenceId sequenceId);

	IDocumentNoBuilder setFailOnError(boolean failOnError);

	IDocumentNoBuilder setEvaluationContext(Evaluatee context);

	/**
	 * Sets the document/model for which we are building the DocumentNo.
	 * The builder can use is to get {@code AD_Client_ID}, {@code AD_Org_ID}, {@code DocumentDate} and maybe more, in future.
	 *
	 * @param documentModel document/model or null
	 */
	@Deprecated
	default IDocumentNoBuilder setDocumentModel(final Object documentModel)
	{
		return setEvaluationContext(documentModel != null ? InterfaceWrapperHelper.getEvaluatee(documentModel) : Evaluatees.empty());
	}

	/**
	 * Explicitly set which is the sequence number to be used.
	 * If set, then the builder won't fetch and increment current sequence number but it will just use this one.
	 */
	IDocumentNoBuilder setSequenceNo(String sequenceNo);

	/**
	 * Advises the builder to use a preliminary DocumentNo.
	 *
	 * If enabled, the builder:
	 * <ul>
	 * <li>will just fetch the current next DocumentNo without incrementing it
	 * <li>will return a DocumentNo which is wrapped with preliminary markers (see {@link IPreliminaryDocumentNoBuilder#withPreliminaryMarkers(String)}).
	 * </ul>
	 */
	IDocumentNoBuilder setUsePreliminaryDocumentNo(boolean usePreliminaryDocumentNo);
}
