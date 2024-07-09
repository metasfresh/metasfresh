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

import de.metas.dunning.api.IDunnableDoc;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Immutable plain {@link IDunnableDoc}
 *
 * @author cg
 *
 */

@Getter
public class DunnableDoc implements IDunnableDoc
{
	private final String tableName;
	private final int recordId;
	// FRESH-504: DocumentNo
	private final String documentNo;
	private final int AD_Client_ID;
	private final int AD_Org_ID;
	private final int C_BPartner_ID;
	private final int C_BPartner_Location_ID;
	private final int Contact_ID;
	private final int C_Currency_ID;
	private final BigDecimal totalAmt;
	private final BigDecimal openAmt;
	private final LocalDateAndOrgId dueDate;
	private final LocalDateAndOrgId graceDate;
	private final int daysDue;
	private final boolean inDispute;
	private final int M_SectionCode_ID;
	private final String poReference;

	public DunnableDoc( // NOPMD by tsa on 3/20/13 8:44 PM, allow long parameters list
			@NonNull final String tableName, final int recordId,
			final String documentNo, // FRESH-504: Also add the documentNo
			final int adClientId, final int adOrgId,
			final int C_BPartner_ID, final int C_BPartner_Location_ID, final int Contact_ID,
			final int C_Currency_ID,
			@NonNull final BigDecimal totalAmt, 
			@NonNull final BigDecimal openAmt,
			@NonNull final LocalDateAndOrgId dueDate, 
			final LocalDateAndOrgId graceDate,
			final int daysDue,
			final int M_SectionCode_ID,
			final boolean isInDispute,
			@Nullable final String poReference)
	{
		Check.assume(Check.isNotBlank(tableName), "tableName not blank");
		Check.assume(recordId > 0, "record_id > 0");
		Check.assume(adClientId > 0, "AD_Client_ID > 0");
		Check.assume(adOrgId > 0, "AD_Org_ID > 0");
		Check.assume(C_BPartner_ID > 0, "C_BPartner_ID > 0");
		Check.assume(C_BPartner_Location_ID > 0, "C_BPartner_ID > 0");
		Check.assume(C_Currency_ID > 0, "C_Currency_ID > 0");
		// Util.assume(graceDate != null, "dueDate not null"); not sure if grace date is mandatory

		this.tableName = tableName;
		this.recordId = recordId;
		this.documentNo = documentNo; // FRESH-504: Add documentNo
		this.AD_Client_ID = adClientId;
		this.AD_Org_ID = adOrgId;
		this.C_BPartner_ID = C_BPartner_ID;
		this.C_BPartner_Location_ID = C_BPartner_Location_ID;
		this.C_Currency_ID = C_Currency_ID;
		this.Contact_ID = Contact_ID;
		this.totalAmt = totalAmt;
		this.openAmt = openAmt;
		this.dueDate = dueDate;
		this.graceDate = graceDate;
		this.daysDue = daysDue;
		this.inDispute = isInDispute;
		this.M_SectionCode_ID = M_SectionCode_ID;
		this.poReference = poReference;
	}

	@Override
	public String toString()
	{
		return "DunnableDoc [tableName=" + tableName + ", record_id=" + recordId + ", C_BPartner_ID=" + C_BPartner_ID + ", C_BPatner_Location_ID=" + C_BPartner_Location_ID + ", Contact_ID="
				+ Contact_ID + ", C_Currency_ID=" + C_Currency_ID + ", totalAmt=" + totalAmt + ", openAmt=" + openAmt + ", dueDate=" + dueDate + ", graceDate=" + graceDate + ", daysDue=" + daysDue
				+ ", inDispute=" + inDispute + ", M_SectionCode_ID=" + M_SectionCode_ID + "]";
	}

}
