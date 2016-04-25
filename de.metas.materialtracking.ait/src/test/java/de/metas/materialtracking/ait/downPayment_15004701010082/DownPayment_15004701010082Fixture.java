package de.metas.materialtracking.ait.downPayment_15004701010082;

import org.concordion.api.MultiValueResult;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import de.metas.materialtracking.ait.helpers.InvoiceCandidateDriver;
import de.metas.materialtracking.ait.helpers.MaterialReceiptDriver;
import de.metas.materialtracking.ait.helpers.MaterialTrackingDriver;
import de.metas.materialtracking.ait.helpers.PPOrderDriver;
import de.metas.materialtracking.ait.helpers.PurchaseOrderDriver;

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
@RunWith(ConcordionRunner.class)
public class DownPayment_15004701010082Fixture
{
	public String setupMaterialTrackingFromTemplate(
			final String templateLot,
			final String newLot) throws Throwable
	{
		return MaterialTrackingDriver.setupMaterialTrackingFromTemplate(templateLot, newLot);
	}

	public void setupPurchaseOrders(
			final String nameCountry,
			final String strDate,
			final String valueProduct,
			final String strQty,
			final String strTuQty,
			final String lotMaterialTracking) throws Throwable
	{
		PurchaseOrderDriver.setupPurchaseOrders(nameCountry, strDate, valueProduct, strQty, strTuQty, lotMaterialTracking);
	}

	public void setupMaterialReceipts(
			final String documentNo,
			final int line,
			final String nameCountry,
			final String strDate,
			final String valueProduct,
			final String strQty,
			final String strTuQty,
			final String lotMaterialTracking) throws Throwable
	{
		MaterialReceiptDriver.setupMaterialReceipts(documentNo, line, nameCountry, strDate, valueProduct, strQty, strTuQty, lotMaterialTracking);
	}

	public void setupPPOrderHeader(
			final String nameDocType,
			final String documentNo,
			final String lotMaterialTracking,
			final String strDate) throws Throwable
	{
		PPOrderDriver.setupPPOrderHeader(nameDocType, documentNo, lotMaterialTracking, strDate);
	}

	public String setupPPOrderItems(final String documentNo,
			final String strType,
			final String valueProduct,
			final String strQty,
			final String nameReceipt)
	{
		return PPOrderDriver.setupPPOrderItems(documentNo, strType, valueProduct, strQty, nameReceipt);
	}

	public void updatePPOrderQualityFields(final String documentNo)
	{
		PPOrderDriver.updatePPOrderQualityFields(documentNo);
	}

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
}
