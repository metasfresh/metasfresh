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
	String getSummary(DocumentFields docActionModel);
	String getDocumentInfo(DocumentFields docActionModel);
	int getDoc_User_ID(DocumentFields docActionModel);
	int getC_Currency_ID(DocumentFields docActionModel);
	BigDecimal getApprovalAmt(DocumentFields docActionModel);
	//@formatter:on

	//
	// Reporting
	File createPDF(DocumentFields docActionModel);

	//
	// Document processing
	//@formatter:off
	String prepareIt(DocumentFields docActionModel);
	String completeIt(DocumentFields docActionModel);
	void approveIt(DocumentFields docActionModel);
	void rejectIt(DocumentFields docActionModel);
	void voidIt(DocumentFields docActionModel);
	void closeIt(DocumentFields docActionModel);
	void reverseCorrectIt(DocumentFields docActionModel);
	void reverseAccrualIt(DocumentFields docActionModel);
	void reactivateIt(DocumentFields docActionModel);
	//@formatter:on
}
