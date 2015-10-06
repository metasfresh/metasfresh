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

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.compiere.apps.ADialog;
import org.compiere.apps.AppsAction;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;

public class VCreateFromDialog extends CDialog implements ActionListener, TableModelListener
{
	private static final long serialVersionUID = 1L;
	
	private CreateFrom createFrom;
	private int windowNo;
	
	private CPanel parameterPanel = new CPanel();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private StatusBar statusBar = new StatusBar();
	private MiniTable dataTable = new MiniTable();
	
	private static final String SELECT_ALL = "SelectAll";
	
	public VCreateFromDialog(CreateFrom createFrom, int windowNo, boolean modal)
	{
		super(Env.getWindow(windowNo), modal);
		
		this.createFrom = createFrom;
		this.windowNo = windowNo;
		
		try
		{
			jbInit();
			confirmPanel.addActionListener(this);
	    	
	    	statusBar.setStatusDB("");
			tableChanged(null);
			createFrom.setInitOK(true);
		}
		catch(Exception e)
		{
			createFrom.setInitOK(false);
		}
    }
	
	protected void jbInit() throws Exception
	{
		getContentPane().add(parameterPanel, BorderLayout.NORTH);

		JScrollPane dataPane = new JScrollPane();
		getContentPane().add(dataPane, BorderLayout.CENTER);
    	dataPane.getViewport().add(dataTable, null);
    	
    	AppsAction selectAllAction = new AppsAction (SELECT_ALL, KeyStroke.getKeyStroke(KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK), null);
    	CButton selectAllButton = (CButton)selectAllAction.getButton();
    	selectAllButton.setMargin(new Insets (0, 10, 0, 10));
    	selectAllButton.setDefaultCapable(true);
    	selectAllButton.addActionListener(this);
    	confirmPanel.addButton(selectAllButton);

    	CPanel southPanel = new CPanel();
    	getContentPane().add(southPanel, BorderLayout.SOUTH);
    	BorderLayout southLayout = new BorderLayout();
    	southPanel.setLayout(southLayout);
    	southPanel.add(confirmPanel, BorderLayout.CENTER);
    	southPanel.add(statusBar, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			try
			{
				Trx.run(new TrxRunnable()
				{
					public void run(String trxName)
					{
						if (save(trxName))
						{
							dispose();
						}
					}
				});
			}
			catch (Exception ex)
			{
				ADialog.error(windowNo, this, "Error", ex.getLocalizedMessage());
			}
		}
		//  Cancel
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		// Select All
		// Trifon
		else if (e.getActionCommand().equals(SELECT_ALL))
		{
			TableModel model = dataTable.getModel();
			int rows = model.getRowCount();
			for (int i = 0; i < rows; i++)
			{
				model.setValueAt(new Boolean(true), i, 0);
			}
			info();
		}
	}
	
	public boolean save(String trxName)
	{
		dataTable.stopEditor(true);

		TableModel model = dataTable.getModel();
		int rows = model.getRowCount();	
		if (rows == 0)
			return false;
				
		return createFrom.save(dataTable, trxName);
	}
	
	public void tableChanged (TableModelEvent e)
	{
		int type = -1;
		if (e != null)
		{
			type = e.getType();
			if (type != TableModelEvent.UPDATE)
				return;
		}
		info();
		dataTable.repaint();
	}

	public void info()
	{
		TableModel model = dataTable.getModel();
		int rows = model.getRowCount();
		int count = 0;
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean)model.getValueAt(i, 0)).booleanValue())
				count++;
		}
		setStatusLine(count, null);
		
		createFrom.info();
	}
	
	public void setStatusLine(int selectedRowCount, String text) 
	{
		StringBuffer sb = new StringBuffer(String.valueOf(selectedRowCount));
		if (text != null && text.trim().length() > 0) {
			sb.append(" - ").append(text);
		}
		statusBar.setStatusLine(sb.toString());
		//
		confirmPanel.getOKButton().setEnabled(selectedRowCount > 0);
	}
	
	public MiniTable getMiniTable()
	{
		return dataTable;
	}
	
	public CPanel getParameterPanel()
	{
		return parameterPanel;
	}
	
	public ConfirmPanel getConfirmPanel()
	{
		return confirmPanel;
	}
}