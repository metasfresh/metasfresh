/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
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

package org.compiere.pos;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.compiere.grid.ed.VDate;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	POS Query Product
 *	
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Jose A.Gonzalez, Conserti.
 * 
 *  @version $Id: QueryTicket.java,v 0.9 $
 * 
 *  @Colaborador $Id: Consultoria y Soporte en Redes y Tecnologias de la Informacion S.L.
 * 
 */

public class QueryTicket extends PosQuery
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7713957495649128816L;
	/**
	 * 	Constructor
	 */
	public QueryTicket (PosBasePanel posPanel)
	{
		super(posPanel);
	}	//	PosQueryProduct

	
	private PosTextField		f_documentno;
	private VDate			f_date;

	private int				m_c_order_id;
	private CCheckBox 		f_processed;
	private CButton f_refresh;
	private CButton f_ok;
	private CButton f_cancel;

	/**	Table Column Layout Info			*/
	private static ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "C_Order_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "DocumentNo"), "DocumentNo", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "TotalLines"), "TotalLines", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "GrandTotal"), "GrandTotal", BigDecimal.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "C_BPartner_ID"), "Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Processed"), "Processed", Boolean.class)
	};

	/**
	 * 	Set up Panel
	 */
	@Override
	protected void init()
	{
		CPanel panel = new CPanel();
		
		panel.setLayout(new MigLayout("fill"));
		getContentPane().add(panel);
		//	North
		northPanel = new CPanel(new MigLayout("fill","", "[50][50][]"));
		panel.add (northPanel, "north");
		northPanel.setBorder(new TitledBorder(Msg.getMsg(p_ctx, "Query")));
		
		CLabel ldoc = new CLabel(Msg.translate(p_ctx, "DocumentNo"));
		northPanel.add (ldoc, " growy");
		f_documentno = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		ldoc.setLabelFor(f_documentno);
		northPanel.add(f_documentno, "h 30, w 200");
		f_documentno.addActionListener(this);
		//
		CLabel ldate = new CLabel(Msg.translate(p_ctx, "DateOrdered"));
		northPanel.add (ldate, "growy");
		f_date = new VDate();
		f_date.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		ldate.setLabelFor(f_date);
		northPanel.add(f_date, "h 30, w 200");
		f_date.addActionListener(this);
		
		f_processed = new CCheckBox(Msg.translate(p_ctx, "Processed"));
		f_processed.setSelected(false);
		northPanel.add(f_processed, "");
		
		f_refresh = createButtonAction("Refresh", KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		northPanel.add(f_refresh, "w 50!, h 50!, wrap, alignx trailing");
		
		f_up = createButtonAction("Previous", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		northPanel.add(f_up, "w 50!, h 50!, span, split 4");
		f_down = createButtonAction("Next", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		northPanel.add(f_down, "w 50!, h 50!");
		
		f_ok = createButtonAction("Ok", KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		northPanel.add(f_ok, "w 50!, h 50!");
		
		f_cancel = createButtonAction("Cancel", KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		northPanel.add(f_cancel, "w 50!, h 50!");

		
		//	Center
		m_table = new PosTable();
		
		String sql = m_table.prepareTable (s_layout, "C_Order", 
				"C_POS_ID = " + p_pos.getC_POS_ID()
				, false, "C_Order")
			+ " ORDER BY Margin, QtyAvailable";
		m_table.addMouseListener(this);
		m_table.getSelectionModel().addListSelectionListener(this);
		enableButtons();
		centerScroll = new CScrollPane(m_table);
		panel.add (centerScroll, "growx, growy");
		m_table.growScrollbars();
		panel.setPreferredSize(new Dimension(800,600));

		f_documentno.requestFocus();
		pack();
		
		setResults(p_ctx, f_processed.isSelected(), f_documentno.getText(), f_date.getTimestamp());
	}	//	init

	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		log.info("PosQueryProduct.actionPerformed - " + e.getActionCommand());
		if ("Refresh".equals(e.getActionCommand())
			|| e.getSource() == f_processed || e.getSource() == f_documentno
			|| e.getSource() == f_date)
		{
			setResults(p_ctx, f_processed.isSelected(), f_documentno.getText(), f_date.getTimestamp());
			return;
		}
		else if ("Reset".equals(e.getActionCommand()))
		{
			reset();
			return;
		}
		else if ("Previous".equalsIgnoreCase(e.getActionCommand()))
		{
			int rows = m_table.getRowCount();
			if (rows == 0)
				return;
			int row = m_table.getSelectedRow();
			row--;
			if (row < 0)
				row = 0;
			m_table.getSelectionModel().setSelectionInterval(row, row);
			return;
		}
		else if ("Next".equalsIgnoreCase(e.getActionCommand()))
		{
			int rows = m_table.getRowCount();
			if (rows == 0)
				return;
			int row = m_table.getSelectedRow();
			row++;
			if (row >= rows)
				row = rows - 1;
			m_table.getSelectionModel().setSelectionInterval(row, row);
			return;
		}
		else if ("Cancel".equalsIgnoreCase(e.getActionCommand()))
		{
			dispose();
			return;
		}
		//	Exit
		close();
	}	//	actionPerformed
	
	
	/**
	 * 
	 */
	@Override
	public void reset()
	{
		f_processed.setSelected(false);
		f_documentno.setText(null);
		f_date.setValue(Env.getContextAsDate(Env.getCtx(), "#Date"));
		setResults(p_ctx, f_processed.isSelected(), f_documentno.getText(), f_date.getTimestamp());
	}


	/**
	 * 	Set/display Results
	 *	@param results results
	 */
	public void setResults (Properties ctx, boolean processed, String doc, Timestamp date)
	{
		String sql = "";
		try 
		{
			sql = "SELECT o.C_Order_ID, o.DocumentNo, o.TotalLines, o.GrandTotal, b.Name, o.Processed" +
					" FROM C_Order o INNER JOIN C_BPartner b ON o.C_BPartner_ID=b.C_BPartner_ID" +
					" WHERE o.C_POS_ID = " + p_pos.getC_POS_ID();
			sql += " AND o.Processed = " + ( processed ? "'Y' " : "'N' ");
			if (doc != null && !doc.equalsIgnoreCase(""))
				sql += " AND o.DocumentNo = '" + doc + "'";
			if ( date != null )
				sql += " AND o.DateOrdered = ? Order By o.DocumentNo DESC";
			
			PreparedStatement pstm = DB.prepareStatement(sql, null);
			if ( date != null )
				pstm.setTimestamp(1, date);
			ResultSet rs = pstm.executeQuery();
			m_table.loadTable(rs);
			if ( m_table.getRowCount() > 0 )
				m_table.setRowSelectionInterval(0, 0);
			enableButtons();
		}
		catch(Exception e)
		{
			log.severe("QueryTicket.setResults: " + e + " -> " + sql);
			
		}
	}	//	setResults

	/**
	 * 	Enable/Set Buttons and set ID
	 */
	protected void enableButtons()
	{
		m_c_order_id = -1;
		int row = m_table.getSelectedRow();
		boolean enabled = row != -1;
		if (enabled)
		{
			Integer ID = m_table.getSelectedRowKey();
			if (ID != null)
			{
				m_c_order_id = ID.intValue();
			}
		}
		
		f_ok.setEnabled(enabled);
		
		log.info("ID=" + m_c_order_id); 
	}	//	enableButtons

	/**
	 * 	Close.
	 * 	Set Values on other panels and close
	 */
	@Override
	protected void close()
	{
		log.info("C_Order_ID=" + m_c_order_id); 
		
		if (m_c_order_id > 0)
		{
			p_posPanel.setOrder(m_c_order_id);
			p_posPanel.updateInfo();

		}
		dispose();
	}	//	close
	
}	//	PosQueryProduct
