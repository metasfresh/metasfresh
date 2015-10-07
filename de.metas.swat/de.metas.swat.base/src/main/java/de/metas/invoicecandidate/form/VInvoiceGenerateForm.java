package de.metas.invoicecandidate.form;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.adempiere.util.Services;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.IStatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.form.VGenPanel;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.Lookup;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_GenerateInvoice;

public class VInvoiceGenerateForm
		extends InvoiceGenerate
		implements FormPanel
{
	// services
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);
	
	private VGenPanel panel;

	/** FormFrame */
	private FormFrame m_frame;

	private Map<String, VEditor> editors = new HashMap<String, VEditor>();

	private VLookup fOrg;
	private VLookup fBPartner;
	private VLookup fOrder;

	private VCheckBox fIgnoreInvoiceSchedule;
	
	private class ParameterChangeListener implements VetoableChangeListener
	{
		@Override
		public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
		{
			final String name = evt.getPropertyName();
			final Object valueNew = evt.getNewValue();
			onParameterChanged(name, valueNew);
		}
	}

	private final ParameterChangeListener parameterChangeListener = new ParameterChangeListener();

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");

		panel = new VGenPanel(this, WindowNo, frame);

		try
		{
			super.dynInit();
			dynInit();
			jbInit();
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "init", ex);
		}
	} // init

	private void onParameterChanged(String name, Object valueNew)
	{
		final VEditor editor = editors.get(name);
		if (editor != null)
			editor.setValue(valueNew);
		executeQuery();
	}

	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	} // dispose

	void jbInit() throws Exception
	{
		addParameter(fOrg);
		addParameter(fBPartner);
		addParameter(fOrder);

		// Note: our "ignore" checkbox needs no label
		panel.getParameterPanel().add(fIgnoreInvoiceSchedule, null);
		
		// create and add a custom refresh button for this form
		final CButton refreshButton = ConfirmPanel.createRefreshButton(false);
		panel.getConfirmPanelSel().addButton(refreshButton);
		refreshButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				executeQuery();
			}
		});

		// OK button is only enabled if a value is selected in the DocAction field
		panel.getConfirmPanelSel().getOKButton().setEnabled(true);
	} // jbInit

	@Override
	public void dynInit() throws Exception
	{
		super.dynInit();

		fOrg = createParameter(I_C_Invoice_Candidate.COLUMNNAME_AD_Org_ID);
		fBPartner = createParameter(I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID);
		fOrder = createParameter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID);

		fIgnoreInvoiceSchedule = 
				new VCheckBox(C_Invoice_Candidate_GenerateInvoice.CHECKBOX_IGNORE_INVOICE_SCHEDULE, true, false, true, 
						Msg.getMsg(Env.getCtx(), C_Invoice_Candidate_GenerateInvoice.CHECKBOX_IGNORE_INVOICE_SCHEDULE), null, false);
		fIgnoreInvoiceSchedule.addVetoableChangeListener(parameterChangeListener);
		editors.put(C_Invoice_Candidate_GenerateInvoice.CHECKBOX_IGNORE_INVOICE_SCHEDULE, fIgnoreInvoiceSchedule);
		
		panel.getStatusBar().setStatusLine(Msg.getMsg(Env.getCtx(), "InvGenerateSel"));// @@
	} // fillPicks

	private VLookup createParameter(String columnName)
	{
		Lookup lookup = getLookup(columnName);
		if (lookup == null)
			return null;
		VLookup editor = new VLookup(columnName, false, false, true, lookup);
		editor.addVetoableChangeListener(parameterChangeListener);
		editors.put(columnName, editor);
		return editor;
	}

	private void addParameter(VEditor editor)
	{
		if (editor == null)
			return;
		Component editorComp = swingEditorFactory.getEditorComponent(editor);
		CLabel label = new CLabel();
		label.setLabelFor(editorComp);
		label.setText(Msg.translate(Env.getCtx(), editor.getName()));

		panel.getParameterPanel().add(label, null);
		panel.getParameterPanel().add(editorComp, null);

	}

	@Override
	public void executeQuery()
	{
		final StringBuffer whereClause = new StringBuffer();
		final List<Object> params = new ArrayList<Object>();

		for (final Map.Entry<String, VEditor> e : editors.entrySet())
		{
			final String columnName = e.getKey();
			final VEditor editor = e.getValue();
			if (editor == null)
				continue;
			
			final Object value = editor.getValue();
						
			if(C_Invoice_Candidate_GenerateInvoice.CHECKBOX_IGNORE_INVOICE_SCHEDULE.equals(e.getKey()))
			{
				// Note: the default behavior is *not* to ignore the invoice schedule
				final boolean ignore = value != null && (Boolean)value;
				if(!ignore)
				{
					if (whereClause.length() > 0)
						whereClause.append(" AND ");
					
					whereClause.append(getColumnSQL(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective) + "<=?");
					params.add(Env.getContextAsDate(Env.getCtx(), "#Date"));
					
					whereClause.append(" AND " + getColumnSQL(I_C_Invoice_Candidate.COLUMNNAME_InvoiceScheduleAmtStatus) + "='OK'");
				}
				continue;
			}
			
			if (value == null)
				continue;
			
			if ((editor instanceof VLookup) && (value instanceof Integer) && ((Integer)value) < 0)
				continue;
			
			final String columnSQL = getColumnSQL(columnName);
			if (columnSQL == null)
				continue;
			if (whereClause.length() > 0)
				whereClause.append(" AND ");
			
			whereClause.append("(").append(columnSQL).append(")=?");
			params.add(value);
		}

		executeQuery(panel.getMiniTable(), whereClause.toString(), params);
		getStatusBar().setStatusDB(Integer.toString(panel.getMiniTable().getRowCount()));
	}

	/**
	 * Action Listener
	 * 
	 * @param e
	 *            event
	 */
	public void actionPerformed(ActionEvent e)
	{
		validate();
	} // actionPerformed

	@Override
	public void validate()
	{
		super.validate();

		panel.saveSelection();

		ArrayList<Integer> selection = getSelection();
		if (selection != null && selection.size() > 0 && isSelectionActive())
			panel.generate();
		else
			panel.dispose();
	}

	@Override
	public IStatusBar getStatusBar()
	{
		return panel.getStatusBar();
	}
}
