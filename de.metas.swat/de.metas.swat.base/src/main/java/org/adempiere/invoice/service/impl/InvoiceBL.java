package org.adempiere.invoice.service.impl;

import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocLineCopyHandler;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;

public final class InvoiceBL extends AbstractInvoiceBL
{
	@Override
	public MInvoice createAndCompleteForInOut(final I_M_InOut inOut,
			final Timestamp dateInvoiced,
			final String trxName)
	{
		if (inOut == null)
		{
			throw new IllegalArgumentException("Param 'inOut' may not be null");
		}

		final MInOut inOutPO = InterfaceWrapperHelper.getPO(inOut);
		// setting the trxName to be used inside the MInvoice constructor
		inOutPO.set_TrxName(trxName);

		final MInvoice invoice = new MInvoice(inOutPO, dateInvoiced);
		invoice.setIsSOTrx(inOut.isSOTrx());
		invoice.setM_PriceList_ID(IPriceListDAO.M_PriceList_ID_None); // US1184

		invoice.saveEx(trxName);

		for (final MInOutLine inOutLine : inOutPO.getLines())
		{
			final MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
			invoiceLine.setShipLine(inOutLine);
			invoiceLine.setQty(inOutLine.getMovementQty());
			// metas: cg: task 04868 start
			if (inOutLine.getC_OrderLine_ID() > 0)
			{
				final I_C_InvoiceLine il = InterfaceWrapperHelper.create(invoiceLine, I_C_InvoiceLine.class);
				il.setDiscount(inOutLine.getC_OrderLine().getDiscount());

				final Properties iolCtx = InterfaceWrapperHelper.getCtx(inOutLine);
				final String iolTrxName = InterfaceWrapperHelper.getTrxName(inOutLine);

				final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(iolCtx, inOutLine.getC_OrderLine_ID(), I_C_OrderLine.class, iolTrxName);

				// 07442
				// make sure we don't change this if it was already set in the candidate
				final I_C_Tax tax = il.getC_Tax();
				if (tax != null)
				{
					il.setC_TaxCategory(tax.getC_TaxCategory());
				}
				else
				{
					il.setC_TaxCategory(orderLine.getC_TaxCategory());
				}
				InterfaceWrapperHelper.save(il);
			}
			else
			{
				invoiceLine.saveEx();
			}
			// metas: cg: task 04868 end

			inOutLine.setIsInvoiced(true);
			inOutLine.saveEx();
		}

		// metas: Neunumerierung der Rechnungszeilen vor Fertigstellung
		this.renumberLines(InterfaceWrapperHelper.create(invoice, de.metas.adempiere.model.I_C_Invoice.class), 10);

		Services.get(IDocumentBL.class).processEx(invoice, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return invoice;
	}

	private static final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> defaultDocLineCopyHandler = new DefaultDocLineCopyHandler<>(
			org.compiere.model.I_C_InvoiceLine.class);

	@Override
	public int copyLinesFrom(final I_C_Invoice fromInvoice, final I_C_Invoice toInvoice,
			final boolean counter, final boolean setOrderRef, final boolean setInvoiceRef) // settings
	{
		return copyLinesFrom(fromInvoice, toInvoice, counter, setOrderRef, setInvoiceRef, InvoiceBL.defaultDocLineCopyHandler);
	}

	@Override
	public int copyLinesFrom(final I_C_Invoice fromInvoice,
			final I_C_Invoice toInvoice,
			final boolean counter,
			final boolean setOrderRef,
			final boolean setInvoiceRef,      // settings
			final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> additionalDocLineHandler)
	{
		if (toInvoice.isProcessed() || toInvoice.isPosted() || fromInvoice == null)
		{
			return 0;
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(fromInvoice);

		final MInvoice fromInvoicePO = (MInvoice)InterfaceWrapperHelper.getPO(fromInvoice);
		final MInvoice toInvoicePO = (MInvoice)InterfaceWrapperHelper.getPO(toInvoice);

		final MInvoiceLine[] fromLines = fromInvoicePO.getLines(false);
		int count = 0;

		for (int i = 0; i < fromLines.length; i++)
		{
			final I_C_InvoiceLine toLine;
			if (counter)
			{
				toLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, toInvoice);
			}
			else
			{
				toLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, fromInvoice);
			}

			final MInvoiceLine fromLine = fromLines[i];

			// copy original values using the specified handler algorithm
			if (additionalDocLineHandler != null)
			{
				additionalDocLineHandler.copyPreliminaryValues(fromLine, toLine);
			}
			Services.get(ICopyHandlerBL.class).copyPreliminaryValues(fromLine, toLine);

			toLine.setC_Invoice_ID(toInvoice.getC_Invoice_ID());

			// can't assume that the lines are renumbered 10, 20, 30,
			// ...for that reason, and because on reversal, the reversal line is found using Line, we need to explicitly copy the Line value
			toLine.setLine(fromLine.getLine());

			final MInvoiceLine toLinePO = (MInvoiceLine)InterfaceWrapperHelper.getPO(toLine);
			toLinePO.setInvoice(toInvoicePO);

			// 04109: this is a trick to cause the MInvoiceLine.m_priceSet to be "true" and therefore omit a recalculation of the price when the new line is saved.
			// the goal of this is to have the same PriceActual and PriceEntered that we have in fromLine.
			// Note that 'setPrice()' sets both PriceActual and PriceEntered to the given parameter. For this reason we also do an explicit call to setPriceEntered afterwards.
			toLinePO.setPrice(fromLine.getPriceActual());
			toLine.setPriceEntered(fromLine.getPriceEntered());
			// 04109 end

			if (setOrderRef)
			{
				// task 50176: explicitly setting the C_OrderLine_ID. We need it in the commission system
				toLine.setC_OrderLine_ID(fromLine.getC_OrderLine_ID());
			}
			else
			{
				// Reset
				toLine.setC_OrderLine_ID(0);
			}
			toLine.setRef_InvoiceLine_ID(0);
			toLine.setM_InOutLine_ID(0);
			toLine.setA_Asset_ID(0);
			toLine.setM_AttributeSetInstance_ID(0);
			toLine.setS_ResourceAssignment_ID(0);
			// New Tax
			if (toInvoice.getC_BPartner_ID() != fromInvoice.getC_BPartner_ID())
			{
				toLinePO.setTax();	// recalculate
			}
			//
			if (counter || setInvoiceRef)
			{
				toLine.setRef_InvoiceLine_ID(fromLine.getC_InvoiceLine_ID());
			}

			if (counter)
			{
				if (fromLine.getC_OrderLine_ID() != 0)
				{
					final I_C_OrderLine peer = fromLine.getC_OrderLine();
					if (peer.getRef_OrderLine_ID() != 0)
					{
						toLine.setC_OrderLine_ID(peer.getRef_OrderLine_ID());
					}
				}
				toLine.setM_InOutLine_ID(0);
				if (fromLine.getM_InOutLine_ID() != 0)
				{
					final I_M_InOutLine peer = fromLine.getM_InOutLine();
					if (peer.getRef_InOutLine_ID() != 0)
					{
						toLine.setM_InOutLine_ID(peer.getRef_InOutLine_ID());
					}
				}
			}
			//
			toLine.setProcessed(false);
			InterfaceWrapperHelper.save(toLine);

			count++;
			// Cross Link
			if (counter || setInvoiceRef)
			{
				fromLine.setRef_InvoiceLine_ID(toLine.getC_InvoiceLine_ID());
				fromLine.save(trxName);
			}

			// MZ Goodwill
			// copy the landed cost
			toLinePO.copyLandedCostFrom(fromLine);
			toLinePO.allocateLandedCosts();
			// end MZ

			// refresh after modifying the PO (workaround)
			InterfaceWrapperHelper.refresh(toLine);

			// copyValues override of the handler
			if (additionalDocLineHandler != null)
			{
				additionalDocLineHandler.copyValues(fromLine, toLine);
			}
			Services.get(ICopyHandlerBL.class).copyValues(fromLine, toLine);

			// save again
			InterfaceWrapperHelper.save(toLine);
		}
		Check.errorUnless(fromLines.length == count, "Line difference - From=" + fromLines.length + " <> Saved=" + count);

		return count;
	}

	@Override
	public String getSummary(final I_C_Invoice invoice)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoice);

		final StringBuffer sb = new StringBuffer();
		sb.append(invoice.getDocumentNo());
		// : Grand Total = 123.00 (#1)
		sb.append(": ").append(Services.get(IMsgBL.class).translate(ctx, "GrandTotal")).append("=").append(invoice.getGrandTotal());
		// sb.append(" (#").append(getLines(false).length).append(")");
		// - Description
		if (!Check.isEmpty(invoice.getDescription(), true))
		{
			sb.append(" - ").append(invoice.getDescription());
		}
		return sb.toString();
	}
}
