/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/ 

package org.adempiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Generate invoice for Vendor RMA
 * @author  Ashley Ramdass
 * 
 * Based on org.compiere.process.InvoiceGenerate
 */
public class InvoiceGenerateRMA extends JavaProcess
{
    /** Manual Selection        */
    private boolean     p_Selection = false;
    /** Invoice Document Action */
    private String      p_docAction = DocAction.ACTION_Complete;
    
    /** Number of Invoices      */
    private int         m_created = 0;
    /** Invoice Date            */
    private Timestamp   m_dateinvoiced = null;

    /**
     *  Prepare - e.g., get Parameters.
     */
    protected void prepare()
    {
        
        ProcessInfoParameter[] para = getParametersAsArray();
        for (int i = 0; i < para.length; i++)
        {
            String name = para[i].getParameterName();
            if (para[i].getParameter() == null)
                ;
            else if (name.equals("Selection"))
                p_Selection = "Y".equals(para[i].getParameter());
            else if (name.equals("DocAction"))
                p_docAction = (String)para[i].getParameter();
            else
                log.error("Unknown Parameter: " + name);
        }
        
        m_dateinvoiced = Env.getContextAsDate(getCtx(), "#Date");
        if (m_dateinvoiced == null)
        {
            m_dateinvoiced = new Timestamp(System.currentTimeMillis());
        }
    }

    protected String doIt() throws Exception
    {
        if (!p_Selection)
        {
            throw new IllegalStateException("Shipments can only be generated from selection");
        }
        
        String sql = "SELECT rma.M_RMA_ID FROM M_RMA rma, T_Selection "
            + "WHERE rma.DocStatus='CO' AND rma.IsSOTrx='Y' AND rma.AD_Client_ID=? "
            + "AND rma.M_RMA_ID = T_Selection.T_Selection_ID " 
            + "AND T_Selection.AD_PInstance_ID=? ";
        
        PreparedStatement pstmt = null;
        
        try
        {
            pstmt = DB.prepareStatement(sql, get_TrxName());
            pstmt.setInt(1, Env.getAD_Client_ID(getCtx()));
            pstmt.setInt(2, getAD_PInstance_ID());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next())
            {
                generateInvoice(rs.getInt(1));
            }
        }
        catch (Exception ex)
        {
            log.error(sql, ex);
        }
        finally
        {
            try
            {
                pstmt.close();
            }
            catch (Exception ex)
            {
                log.error("Could not close prepared statement");
            }
        }
        return "@Created@ = " + m_created;
    }
    
    private int getInvoiceDocTypeId(int M_RMA_ID)
    {
        String docTypeSQl = "SELECT dt.C_DocTypeInvoice_ID FROM C_DocType dt "
            + "INNER JOIN M_RMA rma ON dt.C_DocType_ID=rma.C_DocType_ID "
            + "WHERE rma.M_RMA_ID=?";
        
        int docTypeId = DB.getSQLValue(null, docTypeSQl, M_RMA_ID);
        
        return docTypeId;
    }
    
    private MInvoice createInvoice(MRMA rma)
    {
        int docTypeId = getInvoiceDocTypeId(rma.get_ID());
            
        if (docTypeId == -1)
        {
            throw new IllegalStateException("Could not get invoice document type for Vendor RMA");
        }
        
        MInvoice invoice = new MInvoice(getCtx(), 0, get_TrxName());
        invoice.setRMA(rma);
        
        invoice.setC_DocTypeTarget_ID(docTypeId);
        if (!invoice.save())
        {
            throw new IllegalStateException("Could not create invoice");
        }
        
        return invoice;
    }
    
    private MInvoiceLine[] createInvoiceLines(MRMA rma, MInvoice invoice)
    {
        ArrayList<MInvoiceLine> invLineList = new ArrayList<MInvoiceLine>();
        
        MRMALine rmaLines[] = rma.getLines(true);
        
        for (MRMALine rmaLine : rmaLines)
        {
            if (rmaLine.getM_InOutLine_ID() == 0)
            {
                throw new IllegalStateException("No customer return line - RMA = " 
                        + rma.getDocumentNo() + ", Line = " + rmaLine.getLine());
            }
            
            MInvoiceLine invLine = new MInvoiceLine(invoice);
            invLine.setRMALine(rmaLine);
            
            if (!invLine.save())
            {
                throw new IllegalStateException("Could not create invoice line");
            }
            
            invLineList.add(invLine);
        }
        
        MInvoiceLine invLines[] = new MInvoiceLine[invLineList.size()];
        invLineList.toArray(invLines);
        
        return invLines;
    }
    
    
    private void generateInvoice(int M_RMA_ID)
    {
        MRMA rma = new MRMA(getCtx(), M_RMA_ID, get_TrxName());
        
        MInvoice invoice = createInvoice(rma);
        MInvoiceLine invoiceLines[] = createInvoiceLines(rma, invoice);
        
        if (invoiceLines.length == 0)
        {
            log.warn("No invoice lines created: M_RMA_ID="
                    + M_RMA_ID + ", M_Invoice_ID=" + invoice.get_ID());
        }
        
        StringBuffer processMsg = new StringBuffer(invoice.getDocumentNo());
        
        if (!invoice.processIt(p_docAction))
        {
            processMsg.append(" (NOT Processed)");
            log.warn("Invoice Processing failed: " + invoice + " - " + invoice.getProcessMsg());
        }
        
        if (!invoice.save())
        {
            throw new IllegalStateException("Could not update invoice");
        }
        
        // Add processing information to process log
        addLog(invoice.getC_Invoice_ID(), invoice.getDateInvoiced(), null, processMsg.toString());
        m_created++;
    }
}
