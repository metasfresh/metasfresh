package de.metas.document.engine;

import de.metas.organization.InstantAndOrgId;

import java.io.File;
import java.math.BigDecimal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Instances of this SPI are supposed to be created by their respective {@link DocumentHandlerProvider}.
 */
public interface DocumentHandler
{
	//
	// Document Info
	//@formatter:off
	String getSummary(DocumentTableFields docFields);
	String getDocumentInfo(DocumentTableFields docFields);
	int getDoc_User_ID(DocumentTableFields docFields);
	InstantAndOrgId getDocumentDate(DocumentTableFields docFields);
	//@formatter:on

	default int getC_Currency_ID(DocumentTableFields docFields)
	{
		return -1; // no default currency for pay selections
	}

	default BigDecimal getApprovalAmt(DocumentTableFields docFields)
	{
		return BigDecimal.ZERO;
	}

	//
	// Reporting
	default File createPDF(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException();
	}

	//
	// Document processing
	/** @return the resulting document status */
	default String prepareIt(DocumentTableFields docFields)
	{
		return IDocument.STATUS_InProgress;
	}

	default void approveIt(DocumentTableFields docFields)
	{
		// nothing
	}

	/** @return the resulting document status */
	String completeIt(DocumentTableFields docFields);

	default void rejectIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action RejectIt is not implemented by default");

	}

	default void voidIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action VoidIt is not implemented by default");
	}

	default void unCloseIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action UnCloseIt is not implemented by default");
	}

	default void reverseCorrectIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action ReverseCorrectIt is not implemented by default");
	}

	default void reverseAccrualIt(DocumentTableFields docFields)
	{
		throw new UnsupportedOperationException("The action ReverseAccrual It is not implemented by default");
	}

	default void closeIt(DocumentTableFields docFields)
	{
		docFields.setProcessed(true);
		docFields.setDocAction(IDocument.ACTION_None);
	}

	//@formatter:off
	void reactivateIt(DocumentTableFields docFields);
	//@formatter:on

}
