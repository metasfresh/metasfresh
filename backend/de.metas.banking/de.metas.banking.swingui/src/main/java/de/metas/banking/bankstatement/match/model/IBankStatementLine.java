package de.metas.banking.bankstatement.match.model;

import java.math.BigDecimal;
import java.util.Date;

import org.compiere.swing.table.ColumnInfo;
import org.compiere.swing.table.TableInfo;

/*
 * #%L
 * de.metas.banking.swingui
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

@TableInfo(defaultHideAll = true)
public interface IBankStatementLine
{
	//@formatter:off
	@ColumnInfo(columnName = "StatementDate", seqNo = 10)
	Date getStatementDate();
	//@formatter:on

	//@formatter:off
	@ColumnInfo(columnName = "DocumentNo", seqNo = 20)
	String getDocumentNo();
	//@formatter:on

	//@formatter:off
	@ColumnInfo(columnName = "C_BP_BankAccount_ID", seqNo = 30)
	BankAccount getBankAccount();
	//@formatter:on

	//@formatter:off
	@ColumnInfo(columnName = "TrxAmt", seqNo = 40)
	BigDecimal getTrxAmt();
	//@formatter:on

	//@formatter:off
	@ColumnInfo(columnName = "C_BPartner_ID", seqNo = 50)
	String getBPartnerName();
	int getC_BPartner_ID();
	//@formatter:on

	//@formatter:off
	//@formatter:on

	//@formatter:off
	//@formatter:on

	//
	// Other (not displayable) columns

	int getC_BankStatementLine_ID();

	int getAD_Org_ID();

	//
	boolean isMatchable(IPayment payment);
}
