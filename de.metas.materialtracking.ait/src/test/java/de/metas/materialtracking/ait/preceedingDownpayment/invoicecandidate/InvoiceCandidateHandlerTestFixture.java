package de.metas.materialtracking.ait.preceedingDownpayment.invoicecandidate;

import java.math.BigDecimal;

import org.concordion.api.MultiValueResult;

import de.metas.materialtracking.ait.helpers.InvoiceCandidateDriver;
import de.metas.materialtracking.ait.helpers.MockedInvoicedSumProvider;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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
public abstract class InvoiceCandidateHandlerTestFixture
{

	/**
	 *
	 * @param ppOrderDocumentNo
	 * @return the number of ICs that reference the given <code>ppOrderDocumentNo</code>.
	 * @throws Throwable
	 */
	public int invokePP_Order_MaterialTracking_Handler_For_PP_Order(final String ppOrderDocumentNo)
	{
		return InvoiceCandidateDriver.invokePP_Order_MaterialTracking_Handler_For_PP_Order(ppOrderDocumentNo);
	}

	public int loadIcsForPPOrder(final String ppOrderDocumentNo)
	{
		return InvoiceCandidateDriver.loadIcsForPPOrder(ppOrderDocumentNo);
	}

	public MultiValueResult verifyInvoiceCandidate(final int no)
	{
		return InvoiceCandidateDriver.verifyInvoiceCandidate(no);
	}

	public MultiValueResult verifyInvoiceDetail(
			final int noInvoiceCandidate,
			final int noInvoiceDetail)
	{
		return InvoiceCandidateDriver.verifyInvoiceDetail(noInvoiceCandidate, noInvoiceDetail);
	}

	public void putAlreadyInvoicedNetSum(final String lotMaterialTracking,
			final String strAlreadyInvoicedNetSum)
	{
		MockedInvoicedSumProvider.instance.putAlreadyInvoicedNetSum(lotMaterialTracking, new BigDecimal(strAlreadyInvoicedNetSum));
	}
}
