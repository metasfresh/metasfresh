package de.metas.handlingunits.client.terminal.receipt.view;

/*
 * #%L
 * de.metas.handlingunits.client
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.beans.impl.UILoadingPropertyChangeListener;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel;
import de.metas.adempiere.form.terminal.table.ITerminalTableModel.SelectionMode;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyVisitor;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.empties.model.EmptiesShipReceiveModel;
import de.metas.handlingunits.client.terminal.empties.view.EmptiesShipReceivePanel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUTUConfigurationEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.view.LUTUConfigurationEditorPanel;
import de.metas.handlingunits.client.terminal.receipt.model.ReceiptCorrectHUEditorModel;
import de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleHUEditorModel;
import de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleHUSelectModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.model.HUEditorCallbackAdapter;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectPanel;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUDocumentLine;

/**
 * Wareneingang (POS).
 *
 * @author cg
 *
 */
public class ReceiptScheduleHUSelectPanel extends AbstractHUSelectPanel<ReceiptScheduleHUSelectModel>
{
	private static final String ACTION_JasperPrint = "ACTION_JasperPrint";

	/**
	 * i.e. empties (packing materials) ship (to vendor) / receive (from customer)
	 *
	 * @task http://dewiki908/mediawiki/index.php/07193_R%C3%BCcknahme_Gebinde_%28104585385527%29
	 */
	private static final String ACTION_EmptiesShipReceive = "ACTION_EmptiesShipReceive";

	private static final String ACTION_CorrectReceivedHU = "de.metas.handlingunits.client.terminal.receipt.view.ReceiptScheduleHUSelectPanel.CorrectReceivedHU";
	private static final String SYSCONFIG_CorrectReceivedHU_Disabled = ACTION_CorrectReceivedHU + ".Disabled";

	private ITerminalKeyPanel purchaseOrderPanel;

	// private ITerminalButton btnPhotoShoot;
	private ITerminalButton btnJasperPrint;
	private ITerminalButton btnEmptiesShipReceive;
	private ITerminalButton btnCorrectReceivedHU;

	public ReceiptScheduleHUSelectPanel(final ITerminalContext terminalContext)
	{
		super(terminalContext, new ReceiptScheduleHUSelectModel(terminalContext));

		final ReceiptScheduleHUSelectModel model = getModel();

		//
		// Configure Rows Model
		final ITerminalTableModel<IPOSTableRow> rowsModel = model.getRowsModel();

		// 07043: we allow only one row to be selected at a time because we generally can't aggregate
		// multiple handling units (at least not currently).
		// NOTE: configure rowsModel before doing ANYTHING else because if we change the selection mode after Rows Panel is initialized, this won't be propagated to Panel
		rowsModel.setSelectionMode(SelectionMode.MULTIPLE);

		//
		// Init components and layout
		initComponents();
		initLayout();

		//
		// Model to View Synchronization
		model.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				load();
			}
		});

		load();
	}

	@Override
	protected HUEditorPanel createHUEditorPanelInstance(final HUEditorModel huEditorModel)
	{
		final ReceiptScheduleHUEditorModel receiptScheduleHUEditorModel = ReceiptScheduleHUEditorModel.cast(huEditorModel);
		final HUEditorPanel editorPanel = new ReceiptScheduleHUEditorPanel(receiptScheduleHUEditorModel);
		return editorPanel;
	}

	/**
	 * Calls the super method, but also destroys unused HUs.
	 *
	 * @task 07367
	 *
	 **/
	@Override
	public boolean editHUs(final HUEditorModel huEditorModel)
	{
		final boolean edited = super.editHUs(huEditorModel);

		// task 07367: destroy the HUs that were not used
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				deleteUnusedHUs(huEditorModel);
			}
		});

		// Return true if user actually edited the given HUs (i.e. he did not pressed Cancel)
		return edited;
	}

	/**
	 * Iterates the editor model and calls {@link IHUAllocations#destroyAssignedHU(de.metas.handlingunits.model.I_M_HU)} for those HUs whose keys
	 * <ul>
	 * <li>are not selected and</li>
	 * <li>are assigned to a document line</li>
	 * <li>do not have the same configuration as the document line</li>
	 * </ul>
	 *
	 * @param huEditorModel
	 * @task 07367
	 */
	private void deleteUnusedHUs(final HUEditorModel huEditorModel)
	{
		huEditorModel.getRootHUKey().iterate(new IHUKeyVisitor()
		{
			@Override
			public VisitResult beforeVisit(final IHUKey key)
			{
				final HUKey huKey = HUKey.castIfPossible(key);
				if (huKey == null)
				{
					return VisitResult.CONTINUE;
				}

				if (huEditorModel.isSelected(huKey))
				{
					return VisitResult.SKIP_DOWNSTREAM;
				}

				final IHUDocumentLine line = huKey.findDocumentLineOrNull();
				if (line == null)
				{
					return VisitResult.SKIP_DOWNSTREAM;
				}

				final I_M_HU hu = huKey.getM_HU();
				if (hu.getM_HU_LUTU_Configuration_ID() == getDocumentLineLUTUConfigurationId(line))
				{
					return VisitResult.SKIP_DOWNSTREAM;
				}

				//
				// Get HUAllocations and destroy HUs in a new transaction (mandatory)
				final IHUAllocations huAllocations = line.getHUAllocations();
				huAllocations.destroyAssignedHU(hu);

				return VisitResult.CONTINUE; // keep going
			}

			private int getDocumentLineLUTUConfigurationId(final IHUDocumentLine line)
			{
				// TODO: refactor this shit!
				Check.assumeInstanceOf(line, ReceiptScheduleHUDocumentLine.class, "line");
				final ReceiptScheduleHUDocumentLine receiptScheduleLine = (ReceiptScheduleHUDocumentLine)line;

				final I_M_ReceiptSchedule trxReferencedModel = receiptScheduleLine.getTrxReferencedModel();

				final I_M_HU_LUTU_Configuration lineConfiguration = trxReferencedModel.getM_HU_LUTU_Configuration();
				return lineConfiguration.getM_HU_LUTU_Configuration_ID();
			}

			@Override
			public VisitResult afterVisit(final IHUKey key)
			{
				return VisitResult.CONTINUE; // keep going
			}

		});
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		final ReceiptScheduleHUSelectModel model = getModel();

		//
		// warehouse
		final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
		warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);

		//
		// vendors
		final IKeyLayout vendorKeyLayout = model.getVendorKeyLayout();
		bpartnersPanel = factory.createTerminalKeyPanel(vendorKeyLayout);

		//
		// Purchase orders
		final IKeyLayout purchaseOrderKeyLayout = model.getPurchaseOrderKeyLayout();
		purchaseOrderPanel = factory.createTerminalKeyPanel(purchaseOrderKeyLayout);

		//
		// Lines table/panel
		linesTable = factory.createTerminalTable2(IPOSTableRow.class);
		linesTable.setModel(model.getRowsModel());
		// resultPanel = new HUDocumentSelectResultPanel(model);

		//
		// Confirm panel & actions
		final IConfirmPanel confirmPanel = getConfirmPanel();

		btnJasperPrint = confirmPanel.addButton(ACTION_JasperPrint);
		btnJasperPrint.setEnabled(false); // Initially disabled.

		btnEmptiesShipReceive = confirmPanel.addButton(ACTION_EmptiesShipReceive);
		btnEmptiesShipReceive.setEnabled(false);

		btnCorrectReceivedHU = confirmPanel.addButton(ACTION_CorrectReceivedHU);
		btnCorrectReceivedHU.setEnabled(false);
		btnCorrectReceivedHU.setVisible(!sysConfigBL.getBooleanValue(SYSCONFIG_CorrectReceivedHU_Disabled, false));

		confirmPanel.addListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
				{
					return;
				}

				final String action = String.valueOf(evt.getNewValue());
				if (ACTION_JasperPrint.equals(action))
				{
					doJasperPrint();
				}
				else if (ACTION_EmptiesShipReceive.equals(action))
				{
					doEmptiesShipReceive();
				}
				else if (ACTION_CorrectReceivedHU.equals(action))
				{
					doCorrectReceivedHU();
				}
			}
		});
	}

	private void doJasperPrint()
	{
		final ReceiptScheduleHUSelectModel model = getModel();
		model.doJasperPrint();
	}

	private void initLayout()
	{
		final IConfirmPanel confirmPanel = getConfirmPanel();

		createPanel(warehousePanel, "dock north, growx, hmin 15%");
		createPanel(bpartnersPanel, "dock north, growx, hmin 15%");
		createPanel(purchaseOrderPanel, "dock north, growx, hmin 15%");

		// createPanel(panel, resultPanel, "dock north, hmin 40%");
		createPanel(linesTable, "dock north, hmin 40%");
		createPanel(confirmPanel, "dock south, hmax 15%");
	}

	/**
	 * Evaluates the module and set the enabled status of different buttons.
	 */
	private void load()
	{
		final ReceiptScheduleHUSelectModel model = getModel();

		final Set<IPOSTableRow> rowsSelected = model.getRowsSelected();
		final boolean haveRowsSelected = !rowsSelected.isEmpty();
		final boolean isWarehouseSelected = model.getM_Warehouse_ID() > 0;

		final ITerminalButton btnPhotoShoot = getButtonPhotoShoot();
		if (btnPhotoShoot != null)
		{
			btnPhotoShoot.setEnabled(haveRowsSelected);
		}

		final ITerminalButton btnCloseLine = getButtonCloseLines();
		if (btnCloseLine != null)
		{
			btnCloseLine.setEnabled(haveRowsSelected);
		}

		btnJasperPrint.setEnabled(model.isJasperButtonEnabled());
		btnEmptiesShipReceive.setEnabled(isWarehouseSelected);
		btnCorrectReceivedHU.setEnabled(haveRowsSelected);
	}

	@Override
	protected void doProcessSelectedLines(final ReceiptScheduleHUSelectModel model)
	{
		model.doProcessSelectedLines(new HUEditorCallbackAdapter<HUEditorModel>()
		{
			@Override
			public boolean editHUs(final HUEditorModel huEditorModel)
			{
				return ReceiptScheduleHUSelectPanel.this.editHUs(huEditorModel);
			}

			@Override
			public boolean editLUTUConfiguration(final LUTUConfigurationEditorModel lutuConfigurationModel)
			{
				return ReceiptScheduleHUSelectPanel.this.editLUTUConfiguration(lutuConfigurationModel);
			}
		});
	}

	private boolean editLUTUConfiguration(final LUTUConfigurationEditorModel lutuConfigurationModel)
	{
		final LUTUConfigurationEditorPanel lutuPanel = new LUTUConfigurationEditorPanel(lutuConfigurationModel);

		// we already have our own terminal context ref that was created when 'lutuConfigurationModel' was created in ReceiptScheduleHUSelectModel.doProcessSelectedLines()

		final ITerminalDialog editorDialog = getTerminalFactory()
				.createModalDialog(ReceiptScheduleHUSelectPanel.this, "Quantity to use", lutuPanel); // TODO ts: Hardcoded ?!?

		// Activate editor dialog and wait until user closes the window
		editorDialog.activate();

		// Check if user pressed cancel
		if (editorDialog.isCanceled())
		{
			return false;
		}
		return true;
	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/07193_R%C3%BCcknahme_Gebinde_%28104585385527%29
	 */
	private void doEmptiesShipReceive()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final ITerminalFactory terminalFactory = getTerminalFactory();

		final ReceiptScheduleHUSelectModel model = getModel();
		final int warehouseId = model.getM_Warehouse_ID(true); // failIfNotSelected			
		
		// #643 The POReference, partner and location will be taken from the selected receipt schedule
		final I_M_ReceiptSchedule selectedReceiptSchedule = model.getSelectedReceiptSchedule();


		try (

		final ITerminalContextReferences refs = terminalContext.newReferences())

		{
			final EmptiesShipReceiveModel emptiesShipReceiveModel = new EmptiesShipReceiveModel(
					terminalContext, 
					warehouseId, 
					selectedReceiptSchedule);
			final EmptiesShipReceivePanel emptiesShipReceivePanel = new EmptiesShipReceivePanel(emptiesShipReceiveModel);

			final String title = msgBL.translate(terminalContext.getCtx(), ACTION_EmptiesShipReceive);
			final ITerminalDialog dialog = terminalFactory.createModalDialog(this, title, emptiesShipReceivePanel);

			dialog.activate();
		}

	}

	/**
	 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
	 */
	private void doCorrectReceivedHU()
	{
		//
		// Create Receipt Correct Model
		final ReceiptScheduleHUSelectModel model = getModel();
		final I_M_ReceiptSchedule receiptSchedule = model.getSelectedReceiptSchedule();
		if (receiptSchedule == null)
		{
			throw new TerminalException("@NoSelection@");
		}

		final ITerminalContext terminalContext = getTerminalContext();
		try (final ITerminalContextReferences refs = terminalContext.newReferences())
		{
			final ReceiptCorrectHUEditorModel receiptCorrectModel = new ReceiptCorrectHUEditorModel(getTerminalContext());
			receiptCorrectModel.loadFromReceiptSchedule(receiptSchedule);

			// Create Receipt Correct Panel and display it to user
			final ReceiptCorrectHUEditorPanel receiptCorrectPanel = new ReceiptCorrectHUEditorPanel(receiptCorrectModel);
			final ITerminalDialog editorDialog = getTerminalFactory().createModalDialog(this, btnCorrectReceivedHU.getText(), receiptCorrectPanel);
			editorDialog.setSize(getTerminalContext().getScreenResolution());

			// Activate editor dialog and wait until user closes the window
			editorDialog.activate();

			if (editorDialog.isCanceled())
			{
				// user canceled!
				return;
			}
		}
		//
		// Refresh ALL lines, because current receipt schedule was changed now.
		// And it could be that also other receipt schedules are affected (in case of an HU which is assigned to multiple receipts).
		model.refreshLines(true);
	}
}
