package de.metas.document.engine;

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

public interface DocumentHandler
{
	//
	// Document Info
	//@formatter:off
	String getSummary(DocumentTableFields docActionModel);
	String getDocumentInfo(DocumentTableFields docActionModel);
	int getDoc_User_ID(DocumentTableFields docActionModel);
	int getC_Currency_ID(DocumentTableFields docActionModel);
	BigDecimal getApprovalAmt(DocumentTableFields docActionModel);
	//@formatter:on

	//
	// Reporting
	File createPDF(DocumentTableFields docActionModel);

	//
	// Document processing
	//@formatter:off
	String prepareIt(DocumentTableFields docActionModel);
	String completeIt(DocumentTableFields docActionModel);
	void approveIt(DocumentTableFields docActionModel);
	void rejectIt(DocumentTableFields docActionModel);
	void voidIt(DocumentTableFields docActionModel);
	void closeIt(DocumentTableFields docActionModel);
	void reverseCorrectIt(DocumentTableFields docActionModel);
	void reverseAccrualIt(DocumentTableFields docActionModel);
	void reactivateIt(DocumentTableFields docActionModel);
	//@formatter:on
}
