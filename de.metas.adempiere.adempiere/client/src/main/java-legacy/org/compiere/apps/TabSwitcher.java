/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *  
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ]                  *
 *****************************************************************************/


package org.compiere.apps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.compiere.grid.GridController;

/**
 * 
 * @author Gunther Hoppe, 21.08.2005
 *
 */
public class TabSwitcher extends FocusAdapter implements ActionListener, ListSelectionListener{
	
	private APanel panel;
	private GridController gc;
	
	public TabSwitcher(GridController g, APanel p) {
	
		panel = p;
		gc = g;
	}

	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
		
			performSwitch();
		}
	};		

	public void actionPerformed(ActionEvent e) {
		if(!(e.getSource() instanceof JTextComponent)) {
			if(gc.getMTab().getRecord_ID() != -1)
				performSwitch();
		}
	}

	public void focusGained(FocusEvent e) {
		performSwitch();
	}

	private void performSwitch() {
		//gc.transferFocus();
		//panel.dispatchTabSwitch(gc);
	}
	
	public void addTabSwitchingSupport(JComponent c) {

		if(c instanceof JTable) {
			
			((JTable)c).getSelectionModel().addListSelectionListener(this);
			return;
		}
		else if(  //c instanceof org.compiere.grid.ed.VEditor ||  				  
				   c instanceof JTextComponent ||
				   //c instanceof ItemSelectable ||
				   c instanceof org.compiere.grid.ed.VCheckBox ||
				   //c instanceof org.compiere.grid.ed.VLookup ||
				   //c instanceof org.compiere.swing.CLabel ||
				   c instanceof AbstractButton) 
		{
					c.addFocusListener(this);
					//c.addKeyListener(new MovementAdapter());
					return;
		}
		else if(c instanceof org.compiere.grid.ed.VDate) 
		{
					org.compiere.grid.ed.VDate d = ((org.compiere.grid.ed.VDate)c);
					//d.addFocusListener(this);
					d.addActionListener(this);
					//d.addKeyListener(new MovementAdapter());
					return;
		}
		else if(c instanceof org.compiere.grid.ed.VLookup) 
		{
					org.compiere.grid.ed.VLookup l = ((org.compiere.grid.ed.VLookup)c);
					//l.addFocusListener(this);
					l.addActionListener(this);
					//l.addKeyListener(new MovementAdapter());
					return;
		}
	}
	
	class MovementAdapter extends KeyAdapter 
	{
         public void keyPressed(KeyEvent event) 
         {        	  	
             // look for tab keys
             if(event.getKeyCode() == KeyEvent.VK_TAB
             || event.getKeyCode() == KeyEvent.VK_ENTER) 
             {              	
            	 ((JComponent)event.getSource()).transferFocus();
             }	 
         }
	}     
}
