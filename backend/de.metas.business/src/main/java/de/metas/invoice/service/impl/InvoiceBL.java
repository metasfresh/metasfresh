package de.metas.invoice.service.impl;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.document.ICopyHandlerBL;
import de.metas.document.IDocLineCopyHandler;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;

import java.util.Properties;

public final class InvoiceBL extends AbstractInvoiceBL
{
	private static final IDocLineCopyHandler<org.compiere.model.I_C_InvoiceLine> defaultDocLineCopyHandler = new DefaultDocLineCopyHandler<>(
			org.compiere.model.I_C_InvoiceLine.class);

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

		final MInvoice fromInvoicePO = InterfaceWrapperHelper.getPO(fromInvoice);
		final MInvoice toInvoicePO = InterfaceWrapperHelper.getPO(toInvoice);

		final MInvoiceLine[] fromLines = fromInvoicePO.getLines(false);
		int count = 0;

		for (final MInvoiceLine fromLine : fromLines)
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

			final MInvoiceLine toLinePO = InterfaceWrapperHelper.getPO(toLine);
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
				toLinePO.setTax();    // recalculate
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

		final StringBuilder sb = new StringBuilder();
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
