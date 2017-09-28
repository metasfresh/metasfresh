package org.compiere.process;

import java.io.File;

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

	String prepareIt(DocActionFields docActionModel);

	String completeIt(DocActionFields docActionModel);

	void approveIt(DocActionFields docActionModel);

	void rejectIt(DocActionFields docActionModel);

	void voidIt(DocActionFields docActionModel);

	void closeIt(DocActionFields docActionModel);

	void reverseCorrectIt(DocActionFields docActionModel);

	void reverseAccrualIt(DocActionFields docActionModel);

	void reactivateIt(DocActionFields docActionModel);

	String getSummary(DocActionFields docActionModel);

	String getDocumentInfo(DocActionFields docActionModel);

	File createPDF(DocActionFields docActionModel);

}
