/**
 *
 */
package de.metas.handlingunits.client.terminal.receipt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUTUConfigurationEditorModel;
import de.metas.handlingunits.client.terminal.receiptschedule.model.ReceiptScheduleTableRow;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.client.terminal.select.api.impl.ReceiptScheduleFiltering;
import de.metas.handlingunits.client.terminal.select.model.AbstractHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.IHUEditorCallback;
import de.metas.handlingunits.client.terminal.select.model.WarehouseKey;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUDocumentLine;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.handlingunits.report.HUReceiptScheduleReportExecutor;
import de.metas.quantity.Quantity;

/**
 * Wareneingang (POS).
 *
 * @author cg
 * @author tsa
 */
public class ReceiptScheduleHUSelectModel extends AbstractHUSelectModel
{
	// Services
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PROPERTY_JasperButtonEnabled = "JasperButtonEnabled";


	private static final String SYSCONFIG_QtyCUReadonlyAlwaysIfNotVirtualPI = "de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleHUSelectModel.QtyCUReadonlyAlwaysIfNotVirtualPI";
	private static final boolean DEFAULT_QtyCUReadonlyAlwaysIfNotVirtualPI = false; // false, according to 08310

	private final PurchaseOrderKeyLayout purchaseOrderKeyLayout;

	public ReceiptScheduleHUSelectModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
		setDisplayPhotoShootButton(true);
		setDisplayCloseLinesButton(true);

		//
		//
		purchaseOrderKeyLayout = new PurchaseOrderKeyLayout(terminalContext);
		//
		// Configure purchaseOrderKeyLayout selectionModel
		{
			final IKeyLayoutSelectionModel purchaseOrderKeyLayoutModel = purchaseOrderKeyLayout.getKeyLayoutSelectionModel();
			purchaseOrderKeyLayoutModel.setAllowKeySelection(true);
			purchaseOrderKeyLayoutModel.setToggleableSelection(true);
		}

		purchaseOrderKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				final PurchaseOrderKey purchaseOrderKey = (PurchaseOrderKey)key;
				onPurchaseOrderKeyPressed(purchaseOrderKey);
			}
		});

		load();
	}

	@Override
	protected final ReceiptScheduleFiltering getService()
	{
		final IPOSFiltering service = super.getService();
		Check.assumeInstanceOf(service, ReceiptScheduleFiltering.class, "service");
		return (ReceiptScheduleFiltering)service;
	}

	public PurchaseOrderKeyLayout getPurchaseOrderKeyLayout()
	{
		return purchaseOrderKeyLayout;
	}

	private void onPurchaseOrderKeyPressed(final PurchaseOrderKey key)
	{
		refreshLines(false); // forceRefresh=false
	}

	/**
	 *
	 * @return C_Order_ID of currently pressed {@link PurchaseOrderKey}
	 */
	public int getC_Order_ID()
	{
		final PurchaseOrderKey purchaseOrderKey = (PurchaseOrderKey)purchaseOrderKeyLayout.getKeyLayoutSelectionModel().getSelectedKeyOrNull();
		if (purchaseOrderKey == null)
		{
			return -1;
		}
		return purchaseOrderKey.getC_Order_ID();
	}

	/**
	 * Loads purchase order keys from the given <code>line</code>.
	 */
	@Override
	protected void loadKeysFromLines(final List<IPOSTableRow> lines)
	{
		final IPOSFiltering service = getService();
		final List<I_C_Order> purchaseOrders = service.getOrders(lines);
		purchaseOrderKeyLayout.createAndSetKeysFromOrders(purchaseOrders);
	}

	@Override
	protected void onWarehouseKeyPressed(final WarehouseKey key)
	{
		final int warehouseId = key == null ? -1 : key.getM_Warehouse_ID();
		final ITerminalContext terminalCtx = getTerminalContext();

		// task FRESH-305 keep the selected warehouse id as property in the context
		terminalCtx.setContextValue(I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, warehouseId);

		getVendorKeyLayout().getKeyLayoutSelectionModel().onKeySelected(null);
		purchaseOrderKeyLayout.getKeyLayoutSelectionModel().onKeySelected(null);

		setActionButtonsEnabled();
	}

	/**
	 * Predicate used to filter retrieved rows ( {@link IPOSTableRow} ) based on current pressed Keys
	 */
	private final Predicate<IPOSTableRow> rowsFilter = new Predicate<IPOSTableRow>()
	{
		@Override
		public boolean test(final IPOSTableRow row)
		{
			if (row == null)
			{
				return false;
			}

			final int currentBPartnerId = getC_BPartner_ID();
			if (currentBPartnerId > 0 && row.getC_BPartner().getC_BPartner_ID() != currentBPartnerId)
			{
				return false;
			}

			final int currentOrderId = getC_Order_ID();
			if (currentOrderId > 0 && row.getC_Order().getC_Order_ID() != currentOrderId)
			{
				return false;
			}

			return true;
		}
	};

	@Override
	protected Predicate<IPOSTableRow> getRowsFilter()
	{
		return rowsFilter;
	}

	@Override
	protected HUEditorModel createHUEditorModel(final Collection<IPOSTableRow> rows, final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		Check.assumeNotEmpty(rows, "rows not null");

		//
		// Case: user selected one row
		if (rows.size() == 1)
		{
			final IPOSTableRow row = rows.iterator().next();
			return createHUEditorModel_SingleRow(row, editorCallback);
		}
		//
		// Case: user selected more then one row
		else
		{
			return createHUEditorModel_MultiRow(rows, editorCallback);
		}
	}

	/**
	 * Creates HUs and shows the HU Editor to user for the case when user is selecting ONLY one receipt schedule.
	 *
	 * @param row
	 * @param editorCallback
	 * @return
	 */
	private final HUEditorModel createHUEditorModel_SingleRow(final IPOSTableRow row, final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		Check.assumeNotNull(row, "row not null");

		final ReceiptScheduleFiltering service = getService();

		//
		// Create HU generator
		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(getTerminalContext())
				.addM_ReceiptSchedule(service.getReferencedObject(row));

		//
		// Get/Create and Edit LU/TU configuration
		final IDocumentLUTUConfigurationManager lutuConfigurationManager = huGenerator.getLUTUConfigurationManager();

		final I_M_HU_LUTU_Configuration lutuConfigurationEffective = lutuConfigurationManager.createAndEdit(lutuConfiguration -> {
			final List<I_M_HU_LUTU_Configuration> altConfigurations = lutuConfigurationManager.getCurrentLUTUConfigurationAlternatives();

			//
			// Ask user to edit the configuration in another dialog
			try (final ITerminalContextReferences refs = getTerminalContext().newReferences())
			{
				final LUTUConfigurationEditorModel lutuConfigurationEditorModel = createLUTUConfigurationEditorModel(lutuConfiguration, altConfigurations);

				if (!editorCallback.editLUTUConfiguration(lutuConfigurationEditorModel))
				{
					return null;// User cancelled => do nothing
				}

				//
				// Update the LU/TU configuration on which we are working using what user picked
				lutuConfigurationEditorModel.save(lutuConfiguration); // FIXME: pick the config which was edited
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}

			return lutuConfiguration;
		});

		//
		// No configuration => user cancelled => don't open editor
		if (lutuConfigurationEffective == null)
		{
			return null;
		}

		//
		// Calculate the target CUs that we want to allocate
		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new TerminalException("LU/TU configuration is resulting to infinite quantity: " + lutuConfigurationEffective);
		}
		huGenerator.setQtyToAllocateTarget(qtyCUsTotal);

		//
		// Generate the HUs
		final List<I_M_HU> hus = huGenerator.generateWithinOwnTransaction();

		//
		// Create & return the HUEditor
		final HUEditorModel huEditorModel = createHUEditorModel(hus, Collections.singleton(row));
		return huEditorModel;
	}

	/**
	 * Creates HUs and shows the HU Editor to user for the case when user is selecting more then one receipt schedule.
	 *
	 * @param rows
	 * @param editorCallback
	 * @return
	 * @task http://dewiki908/mediawiki/index.php/08270_Wareneingang_POS_multiple_lines_in_1_TU_%28107035315495%29
	 */
	private final HUEditorModel createHUEditorModel_MultiRow(final Collection<IPOSTableRow> rows, final IHUEditorCallback<HUEditorModel> editorCallback)
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final ReceiptScheduleFiltering service = getService();

		final List<I_M_HU> hus = new ArrayList<>(); // this will hold the LUTU-Editor's result

		try (final ITerminalContextReferences refs = getTerminalContext().newReferences())
		{
			//
			// Create LU/TU configuration panel
			// NOTE: we will create one CU Key for each receipt schedule
			final LUTUConfigurationEditorModel lutuConfigurationEditingModel = new LUTUConfigurationEditorModel(terminalContext);
			lutuConfigurationEditingModel.setQtyCUReadonly(false);
			final List<ReceiptScheduleCUKey> cuKeys = new ArrayList<>();
			Integer bpartnerId = null;
			for (final IPOSTableRow row : rows)
			{
				final ReceiptScheduleTableRow receiptScheduleRow = service.getReceiptScheduleTableRow(row);

				// Make sure all receipt schedules are about same BPartner
				final int receiptScheduleBPartnerId = receiptScheduleRow.getC_BPartner_ID();
				if (bpartnerId != null && bpartnerId != receiptScheduleBPartnerId)
				{
					throw new TerminalException("@NotMatched@: @C_BPartner_ID@");
				}
				bpartnerId = receiptScheduleBPartnerId;

				// Create CUKey for receipt schedule
				final ReceiptScheduleCUKey cuKey = new ReceiptScheduleCUKey(terminalContext, receiptScheduleRow);
				cuKeys.add(cuKey);
			}
			lutuConfigurationEditingModel.setCUKeys(cuKeys);

			//
			// Ask the user to customize it
			final boolean edited = editorCallback.editLUTUConfiguration(lutuConfigurationEditingModel);
			if (!edited)
			{
				// User cancelled => do nothing
				return null;
			}

			//
			// Create one VHU for each CU Key were user entered some quantity
			for (final ReceiptScheduleCUKey cuKey : cuKeys)
			{
				final I_M_HU vhu = cuKey.createVHU();
				if (vhu != null)
				{
					hus.add(vhu);
				}
			}
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		//
		// Create & return the HUEditor
		final HUEditorModel huEditorModel = createHUEditorModel(hus, rows);
		return huEditorModel;
	}

	private final HUEditorModel createHUEditorModel(final List<I_M_HU> hus, final Collection<IPOSTableRow> rows)
	{
		if (hus.isEmpty())
		{
			throw new HUException("@NotCreated@ @M_HU_ID@");
		}

		final ReceiptScheduleFiltering service = getService();
		final ITerminalContext terminalContext = getTerminalContext();

		//
		// Get HUDocumentLine if any
		final IHUDocumentLine documentLine;
		final boolean attributesEditableOnlyIfVHU;
		if (rows.size() == 1)
		{
			final I_M_ReceiptSchedule singleReceiptSchedule = service.getReferencedObject(rows.iterator().next());
			documentLine = new ReceiptScheduleHUDocumentLine(singleReceiptSchedule);
			attributesEditableOnlyIfVHU = false; // default
		}
		else
		{
			documentLine = null;
			// Attributes shall be editable ONLY for VHUs (08270)
			attributesEditableOnlyIfVHU = true;
		}

		//
		// Create a Root HU Key from HUs that were created
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final IHUKey rootHUKey = keyFactory.createRootKey();
		final List<IHUKey> huKeys = keyFactory.createKeys(hus, documentLine);
		rootHUKey.addChildren(huKeys);

		//
		// Create HU Editor Model
		final ReceiptScheduleHUEditorModel huEditorModel = new ReceiptScheduleHUEditorModel(terminalContext);
		huEditorModel.setRootHUKey(rootHUKey);
		huEditorModel.setOriginalTopLevelHUs(hus);
		huEditorModel.setAttributesEditableOnlyIfVHU(attributesEditableOnlyIfVHU);

		return huEditorModel;
	}

	public final void doJasperPrint()
	{
		final I_M_ReceiptSchedule selectedReceiptSchedule = getSelectedReceiptSchedule();

		HUReceiptScheduleReportExecutor
				.get(selectedReceiptSchedule)
				.withWindowNo(getTerminalContext().getWindowNo())
				.executeHUReport();
	}

	/**
	 * Read-only logic for the jasper button.
	 *
	 * @return
	 */
	public boolean isJasperButtonEnabled()
	{
		// We only allow the button to be active if we have only one row selected.
		return getRowsSelected().size() == 1;
	}

	@Override
	public void setActionButtonsEnabled()
	{
		final boolean enabledNew = isJasperButtonEnabled();
		firePropertyChange(PROPERTY_JasperButtonEnabled, !enabledNew, enabledNew);
	}

	/**
	 * Gets selected {@link I_M_ReceiptSchedule}. If there is no selected receipt schedule, exception is thrown.
	 *
	 * @return selected receipt schedule; never return null
	 * @throws TerminalException if there is no selected receipt schedule
	 */
	public I_M_ReceiptSchedule getSelectedReceiptSchedule()
	{
		return getSelectedObject(I_M_ReceiptSchedule.class);
	}

	private final LUTUConfigurationEditorModel createLUTUConfigurationEditorModel(final I_M_HU_LUTU_Configuration lutuConfiguration, final List<I_M_HU_LUTU_Configuration> altConfigurations)
	{
		Check.assumeNotNull(lutuConfiguration, "lutuConfiguration not null");

		final LUTUConfigurationEditorModel lutuConfigurationEditingModel = new LUTUConfigurationEditorModel(getTerminalContext());

		final boolean isQtyCUReadonlyAlwaysIfNotVirtualPI = sysConfigBL.getBooleanValue(
				SYSCONFIG_QtyCUReadonlyAlwaysIfNotVirtualPI,
				DEFAULT_QtyCUReadonlyAlwaysIfNotVirtualPI); // default fallback if not configure
		if (isQtyCUReadonlyAlwaysIfNotVirtualPI)
		{
			lutuConfigurationEditingModel.setQtyCUReadonlyAlwaysIfNotVirtualPI(); // 07501, 08310: Qty CU shall be configurable R-O in Receipt Schedule POS
		}
		lutuConfigurationEditingModel.load(lutuConfiguration, altConfigurations);

		return lutuConfigurationEditingModel;
	}

	@Override
	public String toString()
	{
		return "ReceiptScheduleHUSelectModel [purchaseOrderKeyLayout=" + purchaseOrderKeyLayout + ", rowsFilter=" + rowsFilter + "]";
	}
}
