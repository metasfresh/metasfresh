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

import de.metas.payment.sepa.api.ISEPADocument;
import de.metas.util.Check;

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
		return AD_Client_ID;
	}

	public SEPADocument(final int aD_Client_ID,
			final int aD_Org_ID,
			final BigDecimal amt,
			final String description,
			final String iBAN,
			final String swiftCode,
			final String tableName,
			final String mandateRefNo,
			final int recordId,
			final int c_BPartner_ID)
	{
		Check.assume(!Check.isEmpty(tableName, true), "tableName not empty");
		Check.assume(recordId > 0, "record_id > 0");
		Check.assume(aD_Client_ID > 0, "AD_Client_ID > 0");
		Check.assume(aD_Org_ID > 0, "AD_Org_ID > 0");

		AD_Client_ID = aD_Client_ID;
		AD_Org_ID = aD_Org_ID;
		Amt = amt;
		Description = description;
		IBAN = iBAN;
		SwiftCode = swiftCode;
		TableName = tableName;
		this.mandateRefNo = mandateRefNo;
		RecordId = recordId;
		C_BPartner_ID = c_BPartner_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	@Override
	public BigDecimal getAmt()
	{
		return Amt;
	}

	@Override
	public String getDescription()
	{
		return Description;
	}

	@Override
	public String getIBAN()
	{
		return IBAN;
	}

	@Override
	public String getSwiftCode()
	{
		return SwiftCode;
	}

	@Override
	public String getMandateRefNo()
	{
		return mandateRefNo;
	}

	@Override
	public String getTableName()
	{
		return TableName;
	}

	@Override
	public int getRecordId()
	{
		return RecordId;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

}
