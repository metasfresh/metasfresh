package de.metas.materialtracking.ait.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.concordion.api.MultiValueResult;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Detail;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.PP_Order_MaterialTracking_Handler;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;

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

public class InvoiceCandidateDriver
{
	private static final Map<Integer, I_C_Invoice_Candidate> icNumberToIc = new HashMap<>();

	private static final Map<Pair<Integer, Integer>, I_C_Invoice_Detail> icIdNumberToInvoiceDetail = new HashMap<>();

	private static final Map<Integer, Integer> icNumberToInvoiceDetailCount = new HashMap<>();

	public static int invokePP_Order_MaterialTracking_Handler_For_PP_Order(final String ppOrderDocumentNo)
	{
		//
		// run the handler and create the invoice candidates
		final I_PP_Order ppOrder = Helper.retrieveExisting(ppOrderDocumentNo, I_PP_Order.class);

		final I_C_ILCandHandler handlerRecord = InterfaceWrapperHelper.newInstance(I_C_ILCandHandler.class, Helper.context);
		InterfaceWrapperHelper.save(handlerRecord);
		final PP_Order_MaterialTracking_Handler handler = new PP_Order_MaterialTracking_Handler();
		handler.setHandlerRecord(handlerRecord);

		final InvoiceCandidateGenerateRequest request = InvoiceCandidateGenerateRequest.of(handler, ppOrder);

		handler.createCandidatesFor(request);

		return loadIcsForPPOrder(ppOrderDocumentNo);
	}


	public static int loadIcsForPPOrder(final String ppOrderDocumentNo)
	{
		icNumberToIc.clear();
		icNumberToInvoiceDetailCount.clear();
		icIdNumberToInvoiceDetail.clear();

		final I_PP_Order ppOrder = Helper.retrieveExisting(ppOrderDocumentNo, I_PP_Order.class);

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		int icCounter = 0;
		final List<I_C_Invoice_Candidate> ics = invoiceCandDAO.retrieveReferencing(ppOrder);
		for (final I_C_Invoice_Candidate ic : ics)
		{
			icCounter++;
			icNumberToIc.put(icCounter, ic);

			int idCounter = 0;
			final List<I_C_Invoice_Detail> invoiceDetails = invoiceCandDAO.retrieveInvoiceDetails(ic);
			for (final I_C_Invoice_Detail id : invoiceDetails)
			{
				idCounter++;
				icIdNumberToInvoiceDetail.put(new Pair<>(icCounter, idCounter), id);
			}
			icNumberToInvoiceDetailCount.put(icCounter, idCounter);
		}

		// TODO doesn't work
		Services.get(IInvoiceCandBL.class).updateInvalid()
				.setContext(InterfaceWrapperHelper.getContextAware(ppOrder))
				.setOnlyC_Invoice_Candidates(ics.iterator())
				.update();

		return ics.size();
	}

	public static MultiValueResult verifyInvoiceCandidate(final int no)
	{
		final de.metas.handlingunits.model.I_C_Invoice_Candidate ic =
				InterfaceWrapperHelper.create(icNumberToIc.get(no), de.metas.handlingunits.model.I_C_Invoice_Candidate.class);

		final QualityInvoiceLineGroupType groupType = QualityInvoiceLineGroupType.ofAD_Ref_List_Value(ic.getQualityInvoiceLineGroupType());

		return MultiValueResult.multiValueResult()
				.with("namePLV", ic.getM_PriceList_Version().getName())
				.with("groupType", groupType)
				.with("line", ic.getLine())
				.with("nameDocType", ic.getC_DocTypeInvoice().getName())
				.with("valueProduct", ic.getM_Product().getValue())
				.with("qty", ic.getQtyOrdered())
				.with("price", ic.getPriceActual())
				.with("invoiceDetailsCount", icNumberToInvoiceDetailCount.get(no))
				.with("lineNetAmt", ic.getLineNetAmt()) // lineNetAmt is currently no set as we currently don't update the ICs after we created them
		;
	}

	public static MultiValueResult verifyInvoiceDetail(
			final int noInvoiceCandidate,
			final int noInvoiceDetail)
	{

		final de.metas.handlingunits.model.I_C_Invoice_Detail id =
				InterfaceWrapperHelper.create(
						icIdNumberToInvoiceDetail.get(new Pair<>(noInvoiceCandidate, noInvoiceDetail)),
						de.metas.handlingunits.model.I_C_Invoice_Detail.class);

		final I_M_Product product = id.getM_Product();
		final String note = id.getNote();
		final I_PP_Order ppOrder = id.getPP_Order();
		return MultiValueResult.multiValueResult()
				.with("line", id.getSeqNo())
				.with("valueProduct", product == null ? "" : product.getValue())
				.with("nameProduct", product == null ? "" : product.getName())
				.with("note", note == null ? "" : note)
				.with("qty", id.getQty())
				.with("percentage", id.getPercentage())
				.with("tuQty", id.getQtyTU())
				.with("priceActual", id.getPriceActual())
				.with("printed", id.isPrinted())
				.with("overridesLine", id.isDetailOverridesLine())
				.with("printBefore", id.isPrintBefore())
				.with("documentNoPPOrder", ppOrder == null ? "" : ppOrder.getDocumentNo());
	}
}
