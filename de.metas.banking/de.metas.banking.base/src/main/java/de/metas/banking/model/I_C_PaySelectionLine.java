package de.metas.banking.model;

/*
 * #%L
 * de.metas.banking.base
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


import org.compiere.model.I_C_BankStatementLine;

public interface I_C_PaySelectionLine extends de.metas.adempiere.model.I_C_PaySelectionLine
{
	// @formatter:off
	String COLUMNNAME_C_BankStatementLine_ID = "C_BankStatementLine_ID";
	//void setC_BankStatementLine_ID(int C_BankStatementLine_ID);
	int getC_BankStatementLine_ID();	
	void setC_BankStatementLine(I_C_BankStatementLine C_BankStatementLine);
	I_C_BankStatementLine getC_BankStatementLine();
	// @formatter:on
	
	// @formatter:off
	String COLUMNNAME_C_BankStatementLine_Ref_ID = "C_BankStatementLine_Ref_ID";
	//void setC_BankStatementLine_Ref_ID(int C_BankStatementLine_Ref_ID);
	int getC_BankStatementLine_Ref_ID();	
	void setC_BankStatementLine_Ref(I_C_BankStatementLine_Ref C_BankStatementLine_Ref);
	I_C_BankStatementLine_Ref getC_BankStatementLine_Ref();
	// @formatter:on

}
