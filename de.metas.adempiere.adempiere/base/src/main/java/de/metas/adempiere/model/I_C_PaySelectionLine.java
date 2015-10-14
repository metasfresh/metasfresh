package de.metas.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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


/**
 * @author cg
 */
public interface I_C_PaySelectionLine extends org.compiere.model.I_C_PaySelectionLine
{
	// @formatter:off
	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
	void setC_BPartner_ID(int C_BPartner_ID);
	int getC_BPartner_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";
	void setC_BP_BankAccount_ID(int C_BP_BankAccount_ID);
	int getC_BP_BankAccount_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_Reference = "Reference";
	void setReference(String Reference);
	String getReference();
	// @formatter:on
}
