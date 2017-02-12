package de.metas.edi.sscc18.form;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

import de.metas.adempiere.beans.impl.UILoadingPropertyChangeListener;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.context.TerminalContextFactory;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel.SelectionMode;
import de.metas.adempiere.form.terminal.table.TerminalTableModel;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.IPrintableDesadvLineSSCC18Labels;
import de.metas.edi.sscc18.PrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;

/**
 * Form panel used to generate and print {@link I_EDI_DesadvLine_SSCC} records for a given {@link I_EDI_Desadv}.
 *
 * @author tsa
 * @task 08910
 */
public class EDI_DesadvLine_PrintSSCC18s_FormPanel implements FormPanel
{
	// services
	private final transient IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	// AD_Messages
	private static final String MSG_SelectAll = "SelectAll";
	private static final String MSG_PrintExistingSSCCs = "PrintExistingSSCCs";

	//
	// Models
	private final IPair<ITerminalContext, ITerminalContextReferences> contextAndRefs;
	private int windowNo;
	private TerminalTableModel<IPrintableDesadvLineSSCC18Labels> desadvLinesTableModel;

	//
	// UI
	private FormFrame frame;
	private ITerminalCheckboxField chkSelectAll;
	private ITerminalCheckboxField chkPrintExistingSSCCs;
	private ITerminalTable2<IPrintableDesadvLineSSCC18Labels> desadvLinesTable;

	public EDI_DesadvLine_PrintSSCC18s_FormPanel()
	{
		//
		// Create Terminal Context
		contextAndRefs = TerminalContextFactory.get().createContextAndRefs();
	}

	@Override
	public void init(final int windowNo, final FormFrame frame) throws Exception
	{
		this.frame = frame;
		this.windowNo = windowNo;

		initComponentsAndLayout();

		loadLines();

		// by default, we are selecting everything
		desadvLinesTableModel.setSelectedRowsAll();
	}

	private final void initComponentsAndLayout()
	{
		// set up terminal context
		final ITerminalContext terminalContext = contextAndRefs.getLeft();
		terminalContext.setWindowNo(windowNo);

		final ITerminalFactory terminalFactory = terminalContext.getTerminalFactory();

		frame.setLayout(new BorderLayout());
		final Container contentPanel = frame.getContentPane();

		//
		// Center panel: DESADV lines to print
		{
			desadvLinesTableModel = new TerminalTableModel<>(terminalContext, IPrintableDesadvLineSSCC18Labels.class);
			desadvLinesTableModel.setEditable(true);
			desadvLinesTableModel.setSelectionMode(SelectionMode.MULTIPLE);

			desadvLinesTable = terminalFactory.createTerminalTable2(IPrintableDesadvLineSSCC18Labels.class);
			desadvLinesTable.setRowHeight(50);
			desadvLinesTable.setModel(desadvLinesTableModel);

			contentPanel.add(SwingTerminalFactory.getUI(desadvLinesTable), BorderLayout.CENTER);
		}

		//
		// Top panel: buttons and options
		{
			chkSelectAll = terminalFactory.createTerminalCheckbox(MSG_SelectAll);
			chkSelectAll.setTextAndTranslate(MSG_SelectAll);
			desadvLinesTableModel.bindSelectAllCheckbox(chkSelectAll);

			chkPrintExistingSSCCs = terminalFactory.createTerminalCheckbox(MSG_PrintExistingSSCCs);
			chkPrintExistingSSCCs.setTextAndTranslate(MSG_PrintExistingSSCCs);

			// Layout
			final CPanel panelTop = new CPanel();
			panelTop.setLayout(new FlowLayout()); // horizontal flow
			panelTop.add(SwingTerminalFactory.getUI(chkSelectAll));
			panelTop.add(SwingTerminalFactory.getUI(chkPrintExistingSSCCs));
			contentPanel.add(panelTop, BorderLayout.NORTH);
		}

		//
		// Bottom panel: Confirm panel
		{
			final IConfirmPanel confirmPanel = terminalFactory.createConfirmPanel(true, "");
			confirmPanel.addListener(new UILoadingPropertyChangeListener(confirmPanel.getComponent())
			{

				@Override
				protected void propertyChange0(final PropertyChangeEvent evt)
				{
					if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
					{
						return;
					}

					final String action = String.valueOf(evt.getNewValue());
					if (IConfirmPanel.ACTION_OK.equals(action))
					{
						if (desadvLinesTable != null)
						{
							desadvLinesTable.stopEditing();
						}

						frame.setBusy(true);
						try
						{
							doCreateAndPrintSSCC18Labels();
						}
						finally
						{
							frame.setBusy(false);
						}

						disposeFrame();
					}
					else if (IConfirmPanel.ACTION_Cancel.equals(action))
					{
						disposeFrame();
					}
				}
			});

			// Layout
			contentPanel.add(SwingTerminalFactory.getUI(confirmPanel), BorderLayout.SOUTH);
		}
	}

	/**
	 * Load {@link PrintableDesadvLineSSCC18Labels}.
	 */
	private final void loadLines()
	{
		final I_EDI_Desadv desadv = frame.getProcessInfo().getRecord(I_EDI_Desadv.class);
		final List<I_EDI_DesadvLine> desadvLines = desadvDAO.retrieveAllDesadvLines(desadv);
		for (final I_EDI_DesadvLine desadvLine : desadvLines)
		{
			if (!desadvLine.isActive())
			{
				continue;
			}

			final PrintableDesadvLineSSCC18Labels line = PrintableDesadvLineSSCC18Labels.builder()
					.setEDI_DesadvLine(desadvLine)
					.build();
			desadvLinesTableModel.addRow(line);
		}
	}

	/**
	 * Ask containing frame to dispose.
	 */
	private final void disposeFrame()
	{
		if (frame != null)
		{
			frame.dispose();
		}
		else
		{
			dispose();
		}
	}

	/**
	 * Dispose this panel.
	 *
	 * NOTE: don't call it directly because it will called by API. Please call {@link #disposeFrame()}.
	 */
	@Override
	public final void dispose()
	{
		if (_disposing || _disposed)
		{
			return;
		}

		_disposing = true;
		try
		{
			final ITerminalContext terminaContext = contextAndRefs.getLeft();
			final ITerminalContextReferences references = contextAndRefs.getRight();
			terminaContext.deleteReferences(references);

			TerminalContextFactory.get().destroy(terminaContext);

			if (frame != null)
			{
				frame.dispose();
			}
			_disposed = true;
		}
		finally
		{
			_disposing = false;
		}
	}

	private boolean _disposing = false;
	private boolean _disposed = false;

	private final Properties getCtx()
	{
		return Env.getCtx();
	}

	private final boolean isPrintExistingSSCCs()
	{
		return chkPrintExistingSSCCs.getValue();
	}

	private void doCreateAndPrintSSCC18Labels()
	{
		new DesadvLineSSCC18Generator()
				.setContext(new PlainContextAware(getCtx()))
				.setPrintExistingLabels(isPrintExistingSSCCs())
				.generateAndEnqueuePrinting(desadvLinesTableModel.getSelectedRows())
				.printAll();
	}
}
