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

public interface DocActionHandler
{
	//
	// Document Info
	//@formatter:off
	String getSummary(DocActionFields docActionModel);
	String getDocumentInfo(DocActionFields docActionModel);
	int getDoc_User_ID(DocActionFields docActionModel);
	int getC_Currency_ID(DocActionFields docActionModel);
	BigDecimal getApprovalAmt(DocActionFields docActionModel);
	//@formatter:on

	//
	// Reporting
	File createPDF(DocActionFields docActionModel);

	//
	// Document processing
	//@formatter:off
	String prepareIt(DocActionFields docActionModel);
	String completeIt(DocActionFields docActionModel);
	void approveIt(DocActionFields docActionModel);
	void rejectIt(DocActionFields docActionModel);
	void voidIt(DocActionFields docActionModel);
	void closeIt(DocActionFields docActionModel);
	void reverseCorrectIt(DocActionFields docActionModel);
	void reverseAccrualIt(DocActionFields docActionModel);
	void reactivateIt(DocActionFields docActionModel);
	//@formatter:on
}
