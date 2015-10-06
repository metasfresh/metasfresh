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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.MBPartnerInfo;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTextField;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *	POS Query BPartner
 *	
 *  @author Comunidad de Desarrollo OpenXpertya 
 *         *Basado en Codigo Original Modificado, Revisado y Optimizado de:
 *         *Copyright (c) Jorg Janke
 *  @version $Id: QueryBPartner.java,v 1.1 2004/07/12 04:10:04 jjanke Exp $
 */
public class QueryBPartner extends PosQuery
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7109518709654253628L;

	/**
	 * 	Constructor
	 */
	public QueryBPartner (PosBasePanel posPanel)
	{
		super(posPanel);
	}	//	PosQueryBPartner
	
	private PosTextField		f_value;
	private PosTextField		f_name;
	private PosTextField		f_contact;
	private PosTextField		f_email;
	private PosTextField		f_phone;
	private CTextField		f_city;

	private int				m_C_BPartner_ID;
	private CButton f_refresh;
	private CButton f_ok;
	private CButton f_cancel;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(QueryBPartner.class);
	
	
	/**	Table Column Layout Info			*/
	private static ColumnInfo[] s_layout = new ColumnInfo[] 
	{
		new ColumnInfo(" ", "C_BPartner_ID", IDColumn.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Value"), "Value", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Name"), "Name", String.class),
		new ColumnInfo(Msg.translate(Env.getCtx(), "Email"), "Email", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "Phone"), "Phone", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "Postal"), "Postal", String.class), 
		new ColumnInfo(Msg.translate(Env.getCtx(), "City"), "City", String.class) 
	};
	/**	From Clause							*/
	private static String s_sqlFrom = "RV_BPartner";
	/** Where Clause						*/
	private static String s_sqlWhere = "IsActive='Y'"; 

	/**
	 * 	Set up Panel
	 */
	protected void init()
	{
		CPanel panel = new CPanel();
		
		panel.setLayout(new MigLayout("fill"));
		getContentPane().add(panel);
		//	North
		northPanel = new CPanel(new MigLayout("fill","", "[50][50][]"));
		panel.add (northPanel, "north");
		northPanel.setBorder(new TitledBorder(Msg.getMsg(p_ctx, "Query")));
		
		CLabel lvalue = new CLabel(Msg.translate(p_ctx, "Value"));
		northPanel.add (lvalue, " growy");
		f_value = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lvalue.setLabelFor(f_value);
		northPanel.add(f_value, "h 30, w 200");
		f_value.addActionListener(this);
		
		//
		CLabel lcontact = new CLabel(Msg.translate(p_ctx, "Contact"));
		northPanel.add (lcontact, " growy");
		f_contact = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lcontact.setLabelFor(f_contact);
		northPanel.add(f_contact, "h 30, w 200");
		f_contact.addActionListener(this);
		
		//
		CLabel lphone = new CLabel(Msg.translate(p_ctx, "Phone"));
		northPanel.add (lphone, " growy");
		f_phone = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lphone.setLabelFor(f_phone);
		northPanel.add(f_phone, "h 30, w 200, wrap");
		f_phone.addActionListener(this);
		
		//
		CLabel lname = new CLabel(Msg.translate(p_ctx, "Name"));
		northPanel.add (lname, " growy");
		f_name = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lname.setLabelFor(f_name);
		northPanel.add(f_name, "h 30, w 200");
		f_name.addActionListener(this);
		//
		CLabel lemail = new CLabel(Msg.translate(p_ctx, "Email"));
		northPanel.add (lemail, " growy");
		f_email = new PosTextField("", p_posPanel, p_pos.getOSK_KeyLayout_ID());
		lemail.setLabelFor(f_email);
		northPanel.add(f_email, "h 30, w 200");
		f_email.addActionListener(this);
		//
		CLabel lcity = new CLabel(Msg.translate(p_ctx, "City"));
		northPanel.add (lcity, " growy");
		f_city = new CTextField(10);
		lcity.setLabelFor(f_city);
		northPanel.add(f_city, "h 30, w 200");
		f_city.addActionListener(this);
		//
		
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
		String sql = m_table.prepareTable (s_layout, s_sqlFrom, 
			s_sqlWhere, false, "RV_BPartner")
			+ " ORDER BY Value";
		m_table.addMouseListener(this);
		m_table.getSelectionModel().addListSelectionListener(this);
		enableButtons();
		centerScroll = new CScrollPane(m_table);
		panel.add (centerScroll, "growx, growy");
		m_table.growScrollbars();
		panel.setPreferredSize(new Dimension(800,600));
		f_value.requestFocus();
	}	//	init
	
	/**
	 * 	Action Listener
	 *	@param e event
	 */
	public void actionPerformed (ActionEvent e)
	{
		log.info(e.getActionCommand());
		if ("Refresh".equals(e.getActionCommand())
			|| e.getSource() == f_value // || e.getSource() == f_upc
			|| e.getSource() == f_name // || e.getSource() == f_sku
			)
		{
			setResults(MBPartnerInfo.find (p_ctx,
				f_value.getText(), f_name.getText(), 
				null, f_email.getText(),
				f_phone.getText(), f_city.getText()));
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
		//	Exit
		close();
	}	//	actionPerformed
	
	
	/**
	 * 	Set/display Results
	 *	@param results results
	 */
	public void setResults (MBPartnerInfo[] results)
	{
		m_table.loadTable(results);
		enableButtons();
	}	//	setResults

	/**
	 * 	Enable/Set Buttons and set ID
	 */
	protected void enableButtons()
	{
		m_C_BPartner_ID = -1;
		int row = m_table.getSelectedRow();
		boolean enabled = row != -1;
		if (enabled)
		{
			Integer ID = m_table.getSelectedRowKey();
			if (ID != null)
			{
				m_C_BPartner_ID = ID.intValue();
			//	m_BPartnerName = (String)m_table.getValueAt(row, 2);
			//	m_Price = (BigDecimal)m_table.getValueAt(row, 7);
			}
		}
		f_ok.setEnabled(enabled);
		log.fine("C_BPartner_ID=" + m_C_BPartner_ID); 
	}	//	enableButtons

	/**
	 * 	Close.
	 * 	Set Values on other panels and close
	 */
	protected void close()
	{
		log.fine("C_BPartner_ID=" + m_C_BPartner_ID); 
		
		if (m_C_BPartner_ID > 0)
		{
			p_posPanel.f_order.setC_BPartner_ID(m_C_BPartner_ID);
		//	p_posPanel.f_curLine.setCurrency(m_Price);
		}
		else
		{
			p_posPanel.f_order.setC_BPartner_ID(0);
		//	p_posPanel.f_curLine.setPrice(Env.ZERO);
		}
		dispose();
	}	//	close


	@Override
	public void reset() {
		f_value.setText(null);
		f_name.setText(null);
		f_contact.setText(null);
		f_email.setText(null);
		f_phone.setText(null);
		f_city.setText(null);
		setResults(new MBPartnerInfo[0]);
	}
	
}	//	PosQueryBPartner
