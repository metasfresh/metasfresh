package org.adempiere.server.rpl.interfaces;

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


import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;

/**
 * @author ts
 */
public interface I_EXP_Format extends org.compiere.model.I_EXP_Format
{
	// @formatter:off
	String RplImportMode_RecordExists = "L"; 
	String RplImportMode_RecordIsNew = "I";
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_RplImportMode = "RplImportMode";
	String getRplImportMode();
	void setRplImportMode(String RplImportMode);
	// @formatter:on

	// t.schoneberg@metas.de, 03132: adding handler reference
	// @formatter:off
	String COLUMNNAME_IMP_RequestHandler_ID = "IMP_RequestHandler_ID";
	I_IMP_RequestHandler getIMP_RequestHandler();
	int getIMP_RequestHandler_ID();
	void setIMP_RequestHandler_ID(int IMP_RequestHandler_ID);
	// @formatter:on
	// end of t.schoneberg@metas.de, 03132

	// @formatter:off
	String COLUMNNAME_IsAlwaysFlagWithIssue = "IsAlwaysFlagWithIssue";
	boolean isAlwaysFlagWithIssue();
	void setIsAlwaysFlagWithIssue(boolean IsAlwaysFlagWithIssue);
	// @formatter:on
}
