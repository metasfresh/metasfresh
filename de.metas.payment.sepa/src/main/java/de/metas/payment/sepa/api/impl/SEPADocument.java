package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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


import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.payment.sepa.api.ISEPADocument;

public class SEPADocument implements ISEPADocument
{

	private final int AD_Client_ID;
	private final int AD_Org_ID;
	private final BigDecimal Amt;
	private final String Description;
	private final String IBAN;
	private final String SwiftCode;
	private final String TableName;
	private final String mandateRefNo;
	private final int RecordId;
	private final int C_BPartner_ID;
	
	@Override
	public int getAD_Client_ID()
	{
		return this.AD_Client_ID;
	}

	public SEPADocument(int aD_Client_ID, int aD_Org_ID, BigDecimal amt, String description, 
			String iBAN, String swiftCode, String tableName, String mandateRefNo, int recordId, int c_BPartner_ID)
	{
		Check.assume(!Check.isEmpty(tableName, true), "tableName not empty");
		Check.assume(recordId > 0, "record_id > 0");
		Check.assume(aD_Client_ID > 0, "AD_Client_ID > 0");
		Check.assume(aD_Org_ID > 0, "AD_Org_ID > 0");

		this.AD_Client_ID = aD_Client_ID;
		this.AD_Org_ID = aD_Org_ID;
		this.Amt = amt;
		this.Description = description;
		this.IBAN = iBAN;
		this.SwiftCode = swiftCode;
		this.TableName = tableName;
		this.mandateRefNo = mandateRefNo;
		this.RecordId = recordId;
		this.C_BPartner_ID = c_BPartner_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return this.AD_Org_ID;
	}

	@Override
	public BigDecimal getAmt()
	{
		return this.Amt;
	}

	@Override
	public String getDescription()
	{
		return this.Description;
	}

	@Override
	public String getIBAN()
	{
		return this.IBAN;
	}

	@Override
	public String getSwiftCode()
	{
	return this.SwiftCode;
	}

	@Override
	public String getMandateRefNo()
	{
		return this.mandateRefNo;
	}

	@Override
	public String getTableName()
	{
		return this.TableName;
	}

	@Override
	public int getRecordId()
	{
		return this.RecordId;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return this.C_BPartner_ID;
	}

}
