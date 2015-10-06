/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.grid;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 *  Create Transactions for RMA
 * @author ashley
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2007837 ] VCreateFrom.save() should run in trx
 */
public class CreateFromRMA extends CreateFrom {

	public CreateFromRMA(GridTab mTab)
	{
		super(mTab);
		log.info(mTab.toString());
	}
	
	@Override
	public boolean dynInit() throws Exception 
	{
		log.config("");
        setTitle("Customer RMA - Create Lines From");

		return true;
	}
	
	protected Vector<Vector<Object>> getRMAData()
	{
		int M_InOut_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "InOut_ID");
		int M_RMA_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "M_RMA_ID");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		
		/**
         * 1 M_InOutLine_ID
         * 2 Line
         * 3 Product Name
         * 4 Qty Entered
         * 5 Movement Qty
         * 6 ASI
         */
        StringBuffer sqlStmt = new StringBuffer();
        
        sqlStmt.append("SELECT iol.M_InOutLine_ID, iol.Line, "); 
        sqlStmt.append("CASE WHEN iol.M_Product_ID IS NOT NULL THEN (Select p.Name from M_Product p where p.M_Product_ID = iol.M_Product_ID) END as ProductName, "); 
        sqlStmt.append("iol.QtyEntered, "); 
        sqlStmt.append("iol.movementQty, "); 
        sqlStmt.append("CASE WHEN iol.M_AttributeSetInstance_ID IS NOT NULL THEN (SELECT SerNo FROM M_AttributeSetInstance asi where asi.M_AttributeSetInstance_ID=iol.M_AttributeSetInstance_ID) END as ASI ");
        sqlStmt.append("from M_InOutLine iol where M_InOut_ID=? ");
        sqlStmt.append("and iol.M_InOutLine_ID not in (select rmal.M_InOutLine_ID from M_RMALine rmal where rmal.M_RMA_ID=?)");
        
        try
        {
            PreparedStatement pstmt = DB.prepareStatement(sqlStmt.toString(), null);
            pstmt.setInt(1, M_InOut_ID);
            pstmt.setInt(2, M_RMA_ID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                Vector<Object> line = new Vector<Object>(7);
                line.add(new Boolean(false));           //  0-Selection
                
                KeyNamePair lineKNPair = new KeyNamePair(rs.getInt(1), rs.getString(2)); // 1-Line
                line.add(lineKNPair);
                line.add(rs.getString(3)); //2-Product
                line.add(rs.getString(6)); //3-ASI
                
                BigDecimal qtyEntered = rs.getBigDecimal(4); 
                BigDecimal movementQty = rs.getBigDecimal(5);
                
                line.add(qtyEntered);  //4-Qty
                line.add(movementQty); //5-Movement Qty
                
                
                data.add(line);
            }
            rs.close();
            pstmt.close();
        }
        catch (SQLException e)
        {
            log.log(Level.SEVERE, sqlStmt.toString(), e);
        }
        
        return data;
	}

	@Override
	public void info() 
	{

	}
	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);      //  0-Selection
		miniTable.setColumnClass(1, String.class, true);        //  1-Line
		miniTable.setColumnClass(2, String.class, true);        //  2-Product 
		miniTable.setColumnClass(3, String.class, true);        //  3-ASI
		miniTable.setColumnClass(4, BigDecimal.class, true);        //  4-Qty
		miniTable.setColumnClass(5, BigDecimal.class, true);        //  5-Delivered Qty
        
        //  Table UI
		miniTable.autoSize();
	}

	@Override
	public boolean save(IMiniTable miniTable, String trxName) 
	{
		log.config("");
		int M_RMA_ID = Env.getContextAsInt(Env.getCtx(), getGridTab().getWindowNo(), "M_RMA_ID");
        
//        Integer bpId = (Integer)bPartnerField.getValue();
        MRMA rma = new MRMA(Env.getCtx(), M_RMA_ID, trxName);
        //update BP
//        rma.setC_BPartner_ID(bpId);
        
        for (int i = 0; i < miniTable.getRowCount(); i++)
        {
            if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue())
            {
                BigDecimal d = (BigDecimal)miniTable.getValueAt(i, 5);              //  5-Movement Qty
                KeyNamePair pp = (KeyNamePair)miniTable.getValueAt(i, 1);   //  1-Line
                
                int inOutLineId = pp.getKey();
                
                MRMALine rmaLine = new MRMALine(rma.getCtx(), 0, rma.get_TrxName());
                rmaLine.setM_RMA_ID(M_RMA_ID);
                rmaLine.setM_InOutLine_ID(inOutLineId);
                rmaLine.setQty(d);
                rmaLine.setAD_Org_ID(rma.getAD_Org_ID());
                if (!rmaLine.save())
                {
                    throw new IllegalStateException("Could not create RMA Line");
                }
            }
        }
        rma.saveEx();
        return true;
	}
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
        Vector<String> columnNames = new Vector<String>(7);
        columnNames.add(Msg.getMsg(Env.getCtx(), "Select"));
        columnNames.add("Line");
        columnNames.add(Msg.translate(Env.getCtx(), "M_Product_ID"));
        columnNames.add("ASI");
        columnNames.add(Msg.translate(Env.getCtx(), "Quantity"));
        columnNames.add(Msg.getElement(Env.getCtx(), "QtyDelivered", false));
	    
	    return columnNames;
	}
}
