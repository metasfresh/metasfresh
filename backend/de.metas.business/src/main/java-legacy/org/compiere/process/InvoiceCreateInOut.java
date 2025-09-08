/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import de.metas.document.engine.DocStatus;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.exceptions.InvoiceFullyMatchedException;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;

import static java.math.BigDecimal.ZERO;

/**
 * Create (Generate) Shipment from Invoice
 *
 * @author Jorg Janke
 * @version $Id: InvoiceCreateInOut.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 1895317 ] InvoiceCreateInOut: you can create many receipts
 */
public class InvoiceCreateInOut extends JavaProcess
{
	public static final String PARAM_M_Warehouse_ID = MInOut.COLUMNNAME_M_Warehouse_ID;

	/** Warehouse */
	private int p_M_Warehouse_ID = 0;
	/** Invoice */
	private int p_C_Invoice_ID = 0;
	/** Receipt */
	private MInOut m_inout = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
			{

			}
			else if (name.equals(PARAM_M_Warehouse_ID))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
		p_C_Invoice_ID = getRecord_ID();
	}	// prepare

	@Override
	protected String doIt() throws Exception
	{
		log.info("C_Invoice_ID=" + p_C_Invoice_ID + ", M_Warehouse_ID=" + p_M_Warehouse_ID);
		if (p_C_Invoice_ID <= 0)
		{
			throw new FillMandatoryException("C_Invoice_ID");
		}
		if (p_M_Warehouse_ID == 0)
		{
			throw new FillMandatoryException(PARAM_M_Warehouse_ID);
		}
		//
		MInvoice invoice = new MInvoice(getCtx(), p_C_Invoice_ID, null);
		if (invoice.get_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Invoice_ID@");
		}

		final DocStatus invoiceDocStatus = DocStatus.ofCode(invoice.getDocStatus());
		if (!invoiceDocStatus.isCompleted())
		{
			throw new AdempiereException("@InvoiceCreateDocNotCompleted@");
		}
		//
		for (MInvoiceLine invoiceLine : invoice.getLines(false))
		{
			createLine(invoice, invoiceLine);
		}
		if (m_inout == null)
		{
			throw new InvoiceFullyMatchedException();
		}
		//
		return m_inout.getDocumentNo();
	}	// doIt

	/**
	 * Create Shipment/Receipt header
	 *
	 * @return Shipment/Receipt header
	 */
	private MInOut getCreateHeader(MInvoice invoice)
	{
		if (m_inout != null)
		{
			return m_inout;
		}
		m_inout = new MInOut(invoice, 0, null, p_M_Warehouse_ID);
		m_inout.saveEx();
		return m_inout;
	}

	/**
	 * Create shipment/receipt line
	 *
	 * @return shipment/receipt line
	 */
	private MInOutLine createLine(MInvoice invoice, MInvoiceLine invoiceLine)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		final MatchInvoiceService matchInvoiceService = MatchInvoiceService.get();

		final StockQtyAndUOMQty qtyMatched = matchInvoiceService.getMaterialQtyMatched(invoiceLine);

		final StockQtyAndUOMQty qtyInvoiced = StockQtyAndUOMQtys.create(
				invoiceLine.getQtyInvoiced(), ProductId.ofRepoId(invoiceLine.getM_Product_ID()),
				invoiceLine.getQtyEntered(), UomId.ofRepoId(invoiceLine.getC_UOM_ID()));
		final StockQtyAndUOMQty qtyNotMatched = StockQtyAndUOMQtys.subtract(qtyInvoiced, qtyMatched);

		// If is fully matched don't create anything
		if (qtyNotMatched.signum() == 0)
		{
			return null;
		}
		MInOut inout = getCreateHeader(invoice);
		MInOutLine sLine = new MInOutLine(inout);
		sLine.setInvoiceLine(invoiceLine, 0,	// Locator
				invoice.isSOTrx() ? qtyNotMatched.getStockQty().toBigDecimal() : ZERO);

		sLine.setQtyEntered(qtyNotMatched.getUOMQtyNotNull().toBigDecimal());
		sLine.setMovementQty(qtyNotMatched.getStockQty().toBigDecimal());

		if (invoiceBL.isCreditMemo(invoice))
		{
			sLine.setQtyEntered(sLine.getQtyEntered().negate());
			sLine.setMovementQty(sLine.getMovementQty().negate());
		}
		sLine.saveEx();
		//
		invoiceLine.setM_InOutLine_ID(sLine.getM_InOutLine_ID());
		invoiceLine.saveEx();
		//
		return sLine;
	}
}	// InvoiceCreateInOut
