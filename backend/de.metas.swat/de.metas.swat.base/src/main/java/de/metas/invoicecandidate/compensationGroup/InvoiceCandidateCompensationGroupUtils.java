package de.metas.invoicecandidate.compensationGroup;

import org.adempiere.exceptions.AdempiereException;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/*
 * #%L
 * de.metas.swat.base
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

public class InvoiceCandidateCompensationGroupUtils
{
	public static void assertInGroup(final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (!isInGroup(invoiceCandidate))
		{
			throw new AdempiereException("Invoice candidate " + invoiceCandidate + " shall be part of a compensation group");
		}
	}

	public static void assertNotInGroup(final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (isInGroup(invoiceCandidate))
		{
			throw new AdempiereException("Invoice candidate " + invoiceCandidate + " shall NOT be part of a compensation group");
		}
	}

	public static boolean isInGroup(final I_C_Invoice_Candidate invoiceCandidate)
	{
		return invoiceCandidate.getC_Order_CompensationGroup_ID() > 0;
	}

	public static void assertCompensationLine(final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (!invoiceCandidate.isGroupCompensationLine())
		{
			throw new AdempiereException("Invoice candidate " + invoiceCandidate.getLine() + " shall be a compensation line");
		}
	}

}
