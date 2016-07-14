/**
 * 
 */
package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.Check;

import de.metas.dunning.api.IDunnableDoc;

/**
 * Immutable plain {@link IDunnableDoc}
 * 
 * @author cg
 * 
 */
public class DunnableDoc implements IDunnableDoc
{
	private final String tableName;
	private final int record_id;
	// FRESH-504: DocumentNo
	private final String documentNo;
	private final int AD_Client_ID;
	private final int AD_Org_ID;
	private final int C_BPartner_ID;
	private final int C_BPatner_Location_ID;
	private final int Contact_ID;
	private final int C_Currency_ID;
	private final BigDecimal totalAmt;
	private final BigDecimal openAmt;
	private final Date dueDate;
	private final Date graceDate;
	private final int daysDue;
	private final boolean inDispute;

	/**
	 * create a dunnable doc
	 * 
	 * @param tableName
	 * @param record_id
	 * @param C_BPartner_ID
	 * @param C_BPartner_Location_ID
	 * @param Contact_ID
	 * @param C_Currency_ID
	 * @param totalAmt
	 * @param openAmt
	 * @param dueDate
	 * @param daysDue
	 * @param inDispute
	 */
	public DunnableDoc( // NOPMD by tsa on 3/20/13 8:44 PM, allow long parameters list
			final String tableName, final int record_id,
			final String documentNo, // FRESH-504: Also add the documentNo
			final int adClientId, final int adOrgId,
			final int C_BPartner_ID, final int C_BPartner_Location_ID, final int Contact_ID,
			final int C_Currency_ID,
			final BigDecimal totalAmt, final BigDecimal openAmt,
			final Date dueDate, final Date graceDate,
			final int daysDue,
			boolean isInDispute
			)
	{
		Check.assume(!Check.isEmpty(tableName, true), "tableName not empty");
		Check.assume(record_id > 0, "record_id > 0");
		Check.assume(adClientId > 0, "AD_Client_ID > 0");
		Check.assume(adOrgId > 0, "AD_Org_ID > 0");
		Check.assume(C_BPartner_ID > 0, "C_BPartner_ID > 0");
		Check.assume(C_BPartner_Location_ID > 0, "C_BPartner_ID > 0");
		Check.assume(C_Currency_ID > 0, "C_Currency_ID > 0");
		Check.assume(totalAmt != null, "totalAmt not null");
		Check.assume(openAmt != null, "openAmt not null");
		Check.assume(dueDate != null, "dueDate not null");
		// Util.assume(graceDate != null, "dueDate not null"); not sure if grace date is mandatory

		this.tableName = tableName;
		this.record_id = record_id;
		this.documentNo = documentNo; // FRESH-504: Add documentNo
		this.AD_Client_ID = adClientId;
		this.AD_Org_ID = adOrgId;
		this.C_BPartner_ID = C_BPartner_ID;
		this.C_BPatner_Location_ID = C_BPartner_Location_ID;
		this.C_Currency_ID = C_Currency_ID;
		this.Contact_ID = Contact_ID;
		this.totalAmt = totalAmt;
		this.openAmt = openAmt;
		this.dueDate = dueDate;
		this.graceDate = graceDate;
		this.daysDue = daysDue;
		this.inDispute = isInDispute;
	
	}

	@Override
	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return C_BPatner_Location_ID;
	}

	@Override
	public int getContact_ID()
	{
		return Contact_ID;
	}

	@Override
	public int getC_Currency_ID()
	{
		return C_Currency_ID;
	}

	@Override
	public BigDecimal getTotalAmt()
	{
		return totalAmt;
	}

	@Override
	public BigDecimal getOpenAmt()
	{
		return openAmt;
	}

	@Override
	public Date getDueDate()
	{
		return (Date)dueDate.clone();
	}

	@Override
	public Date getGraceDate()
	{
		if (graceDate == null)
		{
			return null;
		}
		return (Date)graceDate.clone();
	}

	@Override
	public int getDaysDue()
	{
		return daysDue;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public int getRecordId()
	{
		return record_id;
	}

	@Override
	public boolean isInDispute()
	{
		return inDispute;
	}

	@Override
	public String toString()
	{
		return "DunnableDoc [tableName=" + tableName + ", record_id=" + record_id + ", C_BPartner_ID=" + C_BPartner_ID + ", C_BPatner_Location_ID=" + C_BPatner_Location_ID + ", Contact_ID="
				+ Contact_ID + ", C_Currency_ID=" + C_Currency_ID + ", totalAmt=" + totalAmt + ", openAmt=" + openAmt + ", dueDate=" + dueDate + ", graceDate=" + graceDate + ", daysDue=" + daysDue
				+ ", inDispute=" + inDispute + "]";
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

}
