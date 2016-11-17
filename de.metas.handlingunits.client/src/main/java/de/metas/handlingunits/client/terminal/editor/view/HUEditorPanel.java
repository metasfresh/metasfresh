package de.metas.handlingunits.client.terminal.editor.view;

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

import java.awt.Color;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.collections.Predicate;
import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IFocusableComponent;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IPropertiesPanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalField;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.TerminalDialogListenerAdapter;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUFilterPropertiesModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.model.assign.impl.HUAssignTULUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.distribute.impl.HUDistributeCUTUModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.HUJoinModel;
import de.metas.handlingunits.client.terminal.mmovement.model.split.impl.HUSplitModel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.HUAssignTULUPanel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.HUDistributeCUTUPanel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.HUJoinPanel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.HUSplitPanel;
import de.metas.handlingunits.client.terminal.report.model.HUReportModel;
import de.metas.handlingunits.client.terminal.report.view.HUReportPanel;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_M_HU;

public class HUEditorPanel
		extends TerminalDialogListenerAdapter
		implements IFocusableComponent
{
	// Services
	private final transient IHUPOSLayoutConstants layoutConstantsBL = Services.get(IHUPOSLayoutConstants.class);
	protected final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	//
	// Messages and texts (translate-able)
	private static final String MSG_BARCODE = "Barcode";
	private static final String MSG_NoHUSelected = "NoHUSelected";
	private static final String MSG_InvalidKeySelected = "InvalidKeySelected";
	private static final String MSG_CloseHUEditorWithChanges = "CloseHUEditorWithChanges?";

	//
	// HU Key Colors
	private static final Color COLOR_HUKeyBackground_Default = Color.YELLOW;
	public static final Color COLOR_HUKeyBackground_ReadOnly = Color.GRAY;
	private static final Color COLOR_HUKeyBackground_Selected = Color.GREEN;
	private static final Color COLOR_HUKeyBackground_JustChildrenSelected = new Color(181, 230, 29); // i.e. pistachio green
	private static final Color COLOR_HUKeyBackground_VirtualPI = Color.RED;

	//
	// Fonts
	private static final float DEFAULT_FONT_SIZE = 12f;

	private final Properties layoutConstants;

	private HUEditorModel model;
	private PropertyChangeListener modelListener;

	/** Main panel */
	private IContainer panel;

	/** Breadcrumb panel **/
	private ITerminalKeyPanel breadcrumbKeyLayoutPanel;

	/** HU Key filter panel **/
	private final IPropertiesPanel huKeyFilterPanel;

	/** Main HU key panel **/
	private ITerminalKeyPanel handlingUnitsKeyLayoutPanel;

	/** HU Attributes panel */
	private IPropertiesPanel propertiesPanel;

	//
	// Options
	private boolean _askUserWhenCancelingChanges = false; // default=false (backward compatibility)

	/**
	 * Opens TU->LU Assignment dialog
	 */
	private final ITerminalButton bAssignTULU;
	private static final String ACTION_AssignTULU = "AssignTULU";

	/**
	 * Opens CU->TU Distribution dialog
	 */
	private final ITerminalButton bDistributeCUTU;
	private static final String ACTION_DistributeCUTU = "DistributeCUTU";

	/**
	 * Opens HU Split dialog
	 */
	private final ITerminalButton bSplit;
	private static final String ACTION_Split = "Split";

	/**
	 * Opens HU Join dialog
	 */
	private final ITerminalButton bJoin;
	private static final String ACTION_Join = "Join";

	/**
	 * Button used to select/deselect a handling unit
	 */
	private final ITerminalButton bToggleSelect;
	private static final String ACTION_Select = "SelectKey";
	private static final String ACTION_Deselect = "DeselectKey";

	/**
	 * Button used to select/deselect all same-level handling units
	 */
	private final ITerminalButton bToggleSelectAll;
	public static final String ACTION_SelectAll = "SelectAllKey";
	public static final String ACTION_DeselectAll = "DeselectAllKey";

	/**
	 * Button used to execute Jasper print
	 */
	private final ITerminalButton bPrintLabel;
	private static final String ACTION_PrintLabel = "PrintLabel";

	/**
	 * Barcode search field
	 */
	private ITerminalTextField barcodeField;
	private ITerminalLabel barcodeLabel;

	/**
	 * Button used to mark selected HUs as scheduled for Quality Inspection.
	 *
	 * @task 08639
	 */
	private ITerminalButton bMarkAsQualityInspection;
	private boolean markAsQualityInspectionButtonDisplayed = false; // default: false - will be enabled only in some particular HU Editors

	private boolean disposed = false;

	public static final String ACTION_MarkAsQualityInspection = "de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.MarkAsQualityInspection";
	public static final String ACTION_UnMarkAsQualityInspection = "de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.UnMarkAsQualityInspection";
	/** Flag used to completely hide/disable the Quality Inspection button */
	private static final String SYSCONFIG_QualityInspectionButtonDisabled = "de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel.MarkAsQualityInspection.Disabled";

	/**
	 * Crates a new panel, with the already prepared model (lutu-config and stuff)
	 *
	 * @param model
	 */
	public HUEditorPanel(final HUEditorModel model)
	{
		super();

		Check.assumeNotNull(model, "model not null");
		this.model = model;

		final ITerminalContext terminalContext = model.getTerminalContext();
		final ITerminalFactory factory = terminalContext.getTerminalFactory();
		layoutConstants = layoutConstantsBL.getConstants(terminalContext);

		//
		// Init Components
		{
			panel = factory.createContainer();

			final IKeyLayout breadcrumbKeyLayout = model.getBreadcrumbKeyLayout();
			breadcrumbKeyLayout.setDefaultColor(Color.LIGHT_GRAY);
			breadcrumbKeyLayout.setColumns(layoutConstantsBL.getConstantAsInt(layoutConstants, IHUPOSLayoutConstants.PROPERTY_HUEditor_BreadcrumbKeyColumns));
			breadcrumbKeyLayoutPanel = factory.createTerminalKeyPanel(breadcrumbKeyLayout);
			breadcrumbKeyLayoutPanel.setKeyFixedWidth(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_KeyBreadcrumbFixedWidth));

			//
			final HUFilterPropertiesModel huKeyFilterModel = model.getHUKeyFilterModel();
			if (huKeyFilterModel != null)
			{
				huKeyFilterPanel = factory.createPropertiesPanel(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUKeyFilter_Container_Constraints));
				huKeyFilterPanel.disableFireValueChangedOnFocusLost(); // 07636: On key typed, do not set internal value on models yet (Performance issues).
				huKeyFilterPanel.setLabelConstraints(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUKeyFilter_Label_Constraints));
				huKeyFilterPanel.setEditorConstraints(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUKeyFilter_Editor_Constraints));
				huKeyFilterPanel.setModel(huKeyFilterModel);
			}
			else
			{
				huKeyFilterPanel = null;
			}

			//
			final IKeyLayout handlingUnitsKeyLayout = model.getHandlingUnitsKeyLayout();
			handlingUnitsKeyLayout.setDefaultColor(COLOR_HUKeyBackground_Selected);
			handlingUnitsKeyLayout.setColumns(layoutConstantsBL.getConstantAsInt(layoutConstants, IHUPOSLayoutConstants.PROPERTY_HUEditor_KeyColumns));
			handlingUnitsKeyLayout.setRows(5);

			handlingUnitsKeyLayoutPanel = factory.createTerminalKeyPanel(handlingUnitsKeyLayout);
			handlingUnitsKeyLayoutPanel.setKeyFixedWidth(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_KeyFixedWidth));
			handlingUnitsKeyLayoutPanel.setRenderer(new HUKeyLayoutRenderer(this));

			propertiesPanel = factory.createPropertiesPanel();
			propertiesPanel.disableFireValueChangedOnFocusLost(); // 07636: On key typed, do not set internal value on models yet (Performance issues).
			propertiesPanel.setLabelConstraints(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_Attributes_Label_Constraints));
			propertiesPanel.setEditorConstraints(layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_Attributes_Editor_Constraints));
			propertiesPanel.setModel(model.getPropertiesPanelModel());

			bAssignTULU = factory.createButton(HUEditorPanel.ACTION_AssignTULU);
			bAssignTULU.setTextAndTranslate(HUEditorPanel.ACTION_AssignTULU);
			bAssignTULU.addListener(new UIPropertyChangeListener(factory, bAssignTULU)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doAssignTULU();
				}
			});

			bDistributeCUTU = factory.createButton(HUEditorPanel.ACTION_DistributeCUTU);
			bDistributeCUTU.setTextAndTranslate(HUEditorPanel.ACTION_DistributeCUTU);
			bDistributeCUTU.addListener(new UIPropertyChangeListener(factory, bDistributeCUTU)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doDistributeCUTU();
				}
			});

			bSplit = factory.createButton(HUEditorPanel.ACTION_Split);
			bSplit.setTextAndTranslate(HUEditorPanel.ACTION_Split);
			bSplit.addListener(new UIPropertyChangeListener(factory, bSplit)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doSplit();
				}
			});

			bJoin = factory.createButton(HUEditorPanel.ACTION_Join);
			bJoin.setTextAndTranslate(HUEditorPanel.ACTION_Join);
			bJoin.addListener(new UIPropertyChangeListener(factory, bJoin)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doJoin();
				}
			});

			// NOTE: we need to set something as text, else Action will be "" which will not trigger events
			bToggleSelect = factory.createButton("-");
			bToggleSelect.setEnabled(false); // disable by default
			bToggleSelect.setFocusable(false);
			bToggleSelect.addListener(new UIPropertyChangeListener(factory, bToggleSelect)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doToggleSelect();
				}
			});

			bToggleSelectAll = factory.createButton("-");
			bToggleSelectAll.setEnabled(false); // disable by default
			bToggleSelectAll.setFocusable(false);
			bToggleSelectAll.addListener(new UIPropertyChangeListener(factory, bToggleSelectAll)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doToggleSelectAll();
				}
			});

			bPrintLabel = factory.createButton(HUEditorPanel.ACTION_PrintLabel);
			bPrintLabel.setTextAndTranslate(HUEditorPanel.ACTION_PrintLabel);
			bPrintLabel.setEnabled(true); // print label enabled by default
			bPrintLabel.setFocusable(true); // print label focusable by default
			bPrintLabel.addListener(new UIPropertyChangeListener(factory, bPrintLabel)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doPrintLabel();
				}
			});

			//
			// Barcode field and label
			final boolean isDisplayBarcode = model.isDisplayBarcode();
			if (isDisplayBarcode)
			{
				barcodeField = factory.createTerminalTextField(MSG_BARCODE, DisplayType.String, HUEditorPanel.DEFAULT_FONT_SIZE);
				barcodeField.setShowKeyboardButton(false);
				barcodeField.setKeyLayout(null);

				barcodeLabel = factory.createLabel(MSG_BARCODE, true); // true = translated

				barcodeField.addListener(ITerminalField.ACTION_ValueChanged, new PropertyChangeListener()
				{
					@Override
					public void propertyChange(final PropertyChangeEvent evt)
					{
						doBarcodeScan();
					}
				});
			}

			//
			// Button: Mark as scheduled for Quality Inspection (task 08639)
			{
				this.bMarkAsQualityInspection = factory.createButton(ACTION_MarkAsQualityInspection);
				this.bMarkAsQualityInspection.setTextAndTranslate(ACTION_MarkAsQualityInspection);
				bMarkAsQualityInspection.setEnabled(false);
				bMarkAsQualityInspection.setVisible(false);
				bMarkAsQualityInspection.addListener(new UIPropertyChangeListener(factory, bMarkAsQualityInspection)
				{
					@Override
					public void propertyChangeEx(final PropertyChangeEvent evt)
					{
						doMarkOrUnMarkAsQualityInspection();
					}
				});
			}
		}

		modelListener = new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				load();
			}
		};
		model.addPropertyChangeListener(HUEditorModel.PROPERTY_CurrentKey, modelListener);

		//
		// Init Layout
		initLayout();

		//
		// Load from model (initially)
		load();

		terminalContext.addToDisposableComponents(this);
	}

	protected final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return model.getTerminalContext();
	}

	protected HUEditorModel getHUEditorModel()
	{
		return model;
	}

	@Override
	public final Object getComponent()
	{
		return panel.getComponent();
	}

	private void initLayout()
	{
		// NOTE: in dock layout, the order of adding components matters!

		//
		// North: Breadcrumb panel
		panel.add(breadcrumbKeyLayoutPanel, "dock north, grow, wrap, "
				+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_BreadcrumbFixedHeight)
				+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_BreadcrumbdFixedWidth));

		//
		// North: HU Key Filter Panel
		if (huKeyFilterPanel != null)
		{
			panel.add(huKeyFilterPanel, "dock north, grow, wrap, "
					+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUKeyFilter_FixedHeight)
					+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUKeyFilter_FixedWidth));
		}

		//
		// South: Buttons panel
		{
			final IContainer buttonsPanel = getTerminalFactory().createContainer(
					// layoutConstraints:
					"nogrid" // we will use flow horizontally layout
							+ ", hidemode 3" // 3 - Invisible components will not participate in the layout at all and it will for instance not take up a grid cell.
			);
			panel.add(buttonsPanel, "dock south,"
					+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_ButtonsPanel, ""));

			buttonsPanel.add(bAssignTULU, "");
			buttonsPanel.add(bDistributeCUTU, "");
			buttonsPanel.add(bSplit, "");
			buttonsPanel.add(bJoin, "");
			buttonsPanel.add(bToggleSelect, "");
			buttonsPanel.add(bToggleSelectAll, "");
			buttonsPanel.add(bPrintLabel, "");

			if (barcodeField != null)
			{
				buttonsPanel.add(barcodeLabel, "wmin 50px");
				buttonsPanel.add(barcodeField, "wmin 150px");
			}

			buttonsPanel.add(bMarkAsQualityInspection, "");

			createAndAddActionButtons(buttonsPanel);
		}

		//
		// West: HU Key Panel
		panel.add(handlingUnitsKeyLayoutPanel, "dock west, grow, "
				+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_HUPanelFixedWidth, ""));

		//
		// East: Attributes Editor
		panel.add(propertiesPanel, "dock east, "
				+ layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUEditor_Attributes_FixedWidth));
	}

	/**
	 * Create and add more action buttons on buttons panel.
	 *
	 * If you want to also control the created buttons status (e.g. enable/disable them in some circumstances), please also implement {@link #updateActionButtonsStatus()}.
	 *
	 * @param buttonsPanel
	 */
	protected void createAndAddActionButtons(final IContainer buttonsPanel)
	{
		// nothing on this level
	}

	/**
	 * Called when editor status changed (i.e. user selected/deselected something, current key changed etc).
	 *
	 * The intention is to allow the extending classes to update their custom action buttons status.
	 *
	 * @see #createAndAddActionButtons(IContainer)
	 */
	protected void updateActionButtonsStatus()
	{
		// nothing on this level
	}

	/**
	 * Load from model
	 */
	private final void load()
	{
		// Model status
		final boolean allowSelectingReadonlyKeys = model.isAllowSelectingReadonlyKeys();
		final IHUKey currentHUKey = model.getCurrentHUKey();
		final boolean currentHUKeyReadonly = currentHUKey == null || currentHUKey.isReadonly();
		final boolean currentHUKeySelectable = model.isSelectable(currentHUKey);

		//
		// Update Toggle Selected Button
		if (currentHUKeySelectable)
		{
			final boolean selected = model.isSelected(currentHUKey);
			final String toggleButtonMsg = selected ? HUEditorPanel.ACTION_Deselect : HUEditorPanel.ACTION_Select;
			bToggleSelect.setTextAndTranslate(toggleButtonMsg);
			bToggleSelect.setPressed(selected);
			bToggleSelect.setEnabled(true);
			bToggleSelect.setVisible(true);
		}
		else
		{
			bToggleSelect.setText("-");
			bToggleSelect.setEnabled(false);
			bToggleSelect.setVisible(false);
		}

		// bPrintLabel shall always be editable, until we change the logic regarding the composite keys
		bPrintLabel.setEnabled(true);
		bPrintLabel.setFocusable(true);

		boolean selectableAll = false;
		boolean selectAll = false;

		final List<IHUKey> children = currentHUKey.getChildren();
		for (final IHUKey child : children)
		{
			// Don't count readonly/locked HUs when counting selectable/selected HUs
			if (child.isReadonly() && !allowSelectingReadonlyKeys)
			{
				continue;
			}

			if (model.isSelectable(child))
			{
				selectableAll = true;
			}
			if (!model.isSelected(child))
			{
				selectAll = true;
			}
		}

		if (selectableAll)
		{
			final String toggleAllButtonMsg = selectAll ? HUEditorPanel.ACTION_SelectAll : HUEditorPanel.ACTION_DeselectAll;
			bToggleSelectAll.setTextAndTranslate(toggleAllButtonMsg);
			bToggleSelectAll.setPressed(selectAll);
			bToggleSelectAll.setEnabled(true);
			bToggleSelectAll.setVisible(true);
		}
		else
		{
			bToggleSelectAll.setText("-");
			bToggleSelectAll.setEnabled(false);
			bToggleSelectAll.setVisible(false);
		}

		bSplit.setEnabled(!currentHUKeyReadonly);
		bJoin.setEnabled(!currentHUKeyReadonly);

		updateMarkAsQualityInspectionButton();

		updateActionButtonsStatus();
	}

	private void doAssignTULU()
	{
		model.doAssignTULU(new Predicate<HUAssignTULUModel>()
		{
			@Override
			public boolean evaluate(final HUAssignTULUModel assignTULUModel)
			{
				final HUAssignTULUPanel assignTULUPanel = new HUAssignTULUPanel(assignTULUModel);

				final ITerminalFactory factory = getTerminalFactory();

				// The dialog can't maintain its own context references, because its model is basically this editor's model.
				// It's going to create new HUKeys which are the result of the split and which will be displayed in this editor.
				// For that reason we don't want to dispose them once the split editor is closed.
				final ITerminalDialog assignTULUDialog = factory.createModalDialog(HUEditorPanel.this, HUEditorPanel.ACTION_AssignTULU, assignTULUPanel);

				//
				// Activate TU->LU Assignment Dialog and wait for user answer
				assignTULUDialog.activate();

				//
				// Return true if user really pressed OK
				final boolean edited = !assignTULUDialog.isCanceled();
				return edited;
			}
		});

		load(); // refresh window (i.e toggle select) after operation
	}

	protected void doDistributeCUTU()
	{
		model.doDistributeCUTU(new Predicate<HUDistributeCUTUModel>()
		{
			@Override
			public boolean evaluate(final HUDistributeCUTUModel distributeCUTUModel)
			{
				final HUDistributeCUTUPanel distributeCUTUPanel = new HUDistributeCUTUPanel(distributeCUTUModel);

				final ITerminalFactory factory = getTerminalFactory();

				// The dialog can't maintain its own context references, because its model is basically this editor's model.
				// It's going to create new HUKeys which are the result of the split and which will be displayed in this editor.
				// For that reason we don't want to dispose them once the split editor is closed.
				final ITerminalDialog distributeCUTUDialog = factory.createModalDialog(HUEditorPanel.this, HUEditorPanel.ACTION_DistributeCUTU, distributeCUTUPanel);

				//
				// Activate CU->TU Distribution Dialog and wait for user answer
				distributeCUTUDialog.activate();

				//
				// Return true if user really pressed OK
				final boolean edited = !distributeCUTUDialog.isCanceled();
				return edited;
			}
		});

		load(); // refresh window (i.e toggle select) after operation
	}

	private void doSplit()
	{
		final Predicate<HUSplitModel> huSplitModel = new Predicate<HUSplitModel>()
		{
			@Override
			public boolean evaluate(final HUSplitModel splitModel)
			{
				final HUSplitPanel splitPanel = new HUSplitPanel(splitModel);

				final ITerminalFactory factory = getTerminalFactory();

				// The dialog can't maintain its own context references, because its model is basically this editor's model.
				// It's going to create new HUKeys which are the result of the split and which will be displayed in this editor.
				// For that reason we don't want to dispose them once the split editor is closed.
				final ITerminalDialog splitDialog = factory.createModalDialog(HUEditorPanel.this, HUEditorPanel.ACTION_Split, splitPanel);

				//
				// Activate Split Dialog and wait for user answer
				splitDialog.activate();

				//
				// Return true if user really pressed OK
				final boolean edited = !splitDialog.isCanceled();
				return edited;
			}
		};

		model.doSplit(huSplitModel);
		load(); // refresh window (i.e toggle select) after operation
	}

	private void doJoin()
	{
		model.doJoin(new Predicate<HUJoinModel>()
		{
			@Override
			public boolean evaluate(final HUJoinModel joinModel)
			{
				final HUJoinPanel joinPanel = new HUJoinPanel(joinModel);

				final ITerminalFactory factory = getTerminalFactory();

				// The dialog can't maintain its own context references, because its model is basically this editor's model.
				// It's going to create new HUKeys which are the result of the split and which will be displayed in this editor.
				// For that reason we don't want to dispose them once the split editor is closed.
				final ITerminalDialog joinDialog = factory.createModalDialog(HUEditorPanel.this, HUEditorPanel.ACTION_Join, joinPanel);

				//
				// Activate Join Dialog and wait for user answer
				joinDialog.activate();

				//
				// Return true if user really pressed OK
				final boolean edited = !joinDialog.isCanceled();
				return edited;
			}
		});

		load(); // refresh window (i.e toggle select) after operation
	}

	private void doToggleSelect()
	{
		final IHUKey currentHUKey = model.getCurrentHUKey();
		model.toggleSelected(currentHUKey);

		load();
	}

	private void doToggleSelectAll()
	{
		//
		// shall be enabled/visible if any of "currentHUKey" children are selectable
		final IHUKey currentHUKey = model.getCurrentHUKey();
		if (currentHUKey == null)
		{
			return;
		}

		boolean selectableAll = false;
		boolean selectAll = false;

		final List<IHUKey> children = new ArrayList<IHUKey>(currentHUKey.getChildren());
		for (final IHUKey child : children)
		{
			// Don't count readonly/locked HUs when counting selectable/selected HUs
			if (child.isReadonly() && !model.isAllowSelectingReadonlyKeys())
			{
				continue;
			}

			if (model.isSelectable(child))
			{
				selectableAll = true;
			}
			if (!model.isSelected(child))
			{
				selectAll = true;
			}
		}

		if (!selectableAll)
		{
			load();
			return; // should not happen, nothing to do here
		}

		for (final IHUKey child : children)
		{
			if (!model.isSelected(child) && selectAll // SelectAll
					|| model.isSelected(child) && !selectAll)    // DeselectAll
			{
				model.toggleSelected(child);
			}
		}

		load();
	}

	private void doPrintLabel()
	{
		// 07779: Make sure all our HU properties/attributes are saved
		model.saveHUProperties();

		final ITerminalFactory factory = getTerminalFactory();

		final IHUKey currentHUKey = model.getCurrentHUKey();
		if (currentHUKey == null)
		{
			factory.showWarning(this, ITerminalFactory.TITLE_ERROR, new TerminalException("@" + HUEditorPanel.MSG_NoHUSelected + "@"));
			return;
		}

		final boolean currentHUKeySelectable = model.isSelectable(currentHUKey);
		final Set<I_M_HU> selectedHUs = model.getSelectedHUs();

		if (!currentHUKeySelectable && selectedHUs.isEmpty())
		{
			factory.showWarning(this, ITerminalFactory.TITLE_ERROR, new TerminalException("@" + HUEditorPanel.MSG_InvalidKeySelected + "@"));
			return;
		}
		I_M_HU currentHU = null;

		if (currentHUKeySelectable)
		{
			currentHU = ((HUKey)currentHUKey).getM_HU();
		}

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			final HUReportModel reportModel = new HUReportModel(getTerminalContext(), currentHU, selectedHUs);
			final HUReportPanel reportPanel = new HUReportPanel(reportModel);

			final String reportDialogTitle = msgBL.translate(getTerminalContext().getCtx(), HUEditorPanel.ACTION_PrintLabel);

			// this dialog works with the reportModel and reportPanel which we just created and which we will want to dispose after the dialog closed, to avoid memory issues.
			final ITerminalDialog reportDialog = getTerminalFactory().createModalDialog(this, reportDialogTitle, reportPanel);

			try
			{
				reportDialog.activate();
			}
			catch (final Exception e)
			{
				throw new AdempiereException("@Error@: " + reportDialogTitle, e);
			}
		}
	}

	private void doBarcodeScan()
	{
		if (barcodeField == null)
		{
			// shall not happen
			return;
		}

		//
		// If the Barcode field is not filled, do nothing
		final String barcode = barcodeField.getText();
		if (Check.isEmpty(barcode, true))
		{
			return;
		}

		final HUKeysByBarcodeCollector huKeysCollector = new HUKeysByBarcodeCollector(barcode);
		model.getRootHUKey().iterate(huKeysCollector);

		final List<IHUKey> collectedHUKeys = huKeysCollector.getCollectedHUKeys();

		if (collectedHUKeys.isEmpty())
		{
			// No HU was found => beep to user
			Toolkit.getDefaultToolkit().beep();
		}
		else
		{
			for (final IHUKey huKey : collectedHUKeys)
			{
				model.setSelected(huKey);
			}

			load();
			model.setCurrentHUKey(model.getRootHUKey());
		}

		//
		// Always Reset Barcode field after doing something
		barcodeField.setValue(null);
		barcodeField.requestFocus();
	}

	protected Color getHUKeyBackgroundColor(final IHUKey huKey)
	{
		if (model.isSelectedOrParentSelected(huKey))
		{
			return COLOR_HUKeyBackground_Selected;
		}
		else if (huKey.isReadonly())
		{
			return COLOR_HUKeyBackground_ReadOnly;
		}
		else if (model.isSelectedOrParentSelected(huKey))
		{
			return COLOR_HUKeyBackground_Selected;
		}
		else if (huKey.isVirtualPI())
		{
			return COLOR_HUKeyBackground_VirtualPI;
		}
		else if (hasSomeChildrenSelected(huKey))
		{
			return COLOR_HUKeyBackground_JustChildrenSelected;
		}
		else
		{
			return COLOR_HUKeyBackground_Default;
		}
	}

	/**
	 * @param huKey
	 * @return true if any of the {@link IHUKey} children are selected, false otherwise
	 */
	private boolean hasSomeChildrenSelected(final IHUKey huKey)
	{
		// NOTE: no need to load children, we check only which are loaded, because if it's not loaded then it's not selected ;)
		final List<IHUKey> children = huKey.getChildrenNoLoad();

		for (final IHUKey child : children)
		{
			if (model.isSelected(child)
					|| hasSomeChildrenSelected(child))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public final void onDialogOk(final ITerminalDialog dialog)
	{
		onDialogOkBeforeSave(dialog);

		saveToDatabase();

		onDialogOkAfterSave(dialog);
	}

	/**
	 * To be overridden instead of {@link #onDialogOk(ITerminalDialog)}.
	 *
	 * @param dialog
	 */
	protected void onDialogOkBeforeSave(final ITerminalDialog dialog)
	{
		// nothing at this level
	}

	/**
	 * To be overridden instead of {@link #onDialogOk(ITerminalDialog)}.
	 *
	 * @param dialog
	 */
	protected void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		if (!model.hasSelectedKeys())
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	protected void onDialogBeforeCancel()
	{
		// nothing at this level
	}

	@Override
	public final boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		onDialogBeforeCancel();

		//
		// Check if there are changes and if yes, ask the user if (s)he really wants to close this dialog
		if (isAskUserWhenCancelingChanges())
		{
			final HUEditorModel model = getHUEditorModel();
			if (model.hasChanges())
			{
				final boolean closeHUEditor = getTerminalFactory().ask(this)
						.setAD_Message(MSG_CloseHUEditorWithChanges)
						.setDefaultAnswer(false) // cancel by default, to prevent user mistakes
						.getAnswer();
				if (!closeHUEditor)
				{
					return false;
				}
			}
		}

		//
		// Save all changes to model and then back to database
		// NOTE: even if the editor is canceled we need to save back to database
		// because we want to have everything valid (in case we will re-use this HU).
		try
		{
			saveToDatabase();
		}
		catch (final Exception e)
		{
			// just log the exception and move on, because else user will not be able to close the window
			getTerminalFactory().showWarning(this, ITerminalFactory.TITLE_INTERNAL_ERROR, e);
		}

		dispose();
		return true;
	}

	/**
	 * Save changes to database.
	 */
	private final void saveToDatabase()
	{
		// NOTE: we are doing it in a long operation because it could take a while
		// and we want to prevent user from clicking other things or user to believe the interface is frozen
		getTerminalFactory().executeLongOperation(this, new Runnable()
		{
			@Override
			public void run()
			{
				model.save();
			}
		});
	}

	@Override
	protected final void finalize() throws Throwable
	{
		super.finalize();
		dispose();
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		if (isDisposed())
		{
			return; // nothing to do
		}
		modelListener = null; // because it's the last reference, we expect this listener to expire in model

		if (model != null)
		{
			// NOTE: we are not disposing it because we did not create it
			model = null;
		}

		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	public final void setAskUserWhenCancelingChanges(final boolean askUserWhenCancelingChanges)
	{
		_askUserWhenCancelingChanges = askUserWhenCancelingChanges;
	}

	public final boolean isAskUserWhenCancelingChanges()
	{
		return _askUserWhenCancelingChanges;
	}

	@Override
	public void requestFocus()
	{
		// Focus on barcode field by default, if exists (see 07861)
		if (barcodeField != null)
		{
			barcodeField.requestFocus();
		}
	}

	/**
	 * Enabled {@link #bMarkAsQualityInspection} button if not disabled from sysconfig.
	 */
	public void enableMarkAsQualityInspectionButton()
	{
		// Make sure we are allowed to enable it
		final boolean disabled = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_QualityInspectionButtonDisabled, false);
		if (disabled)
		{
			setMarkAsQualityInspectionButtonDisplayed(false);
			return;
		}

		// Enable it
		setMarkAsQualityInspectionButtonDisplayed(true);
	}

	/**
	 * Sets if {@link #bMarkAsQualityInspection} button shall be displayed.
	 *
	 * @param markAsQualityInspectionButtonDisplayed
	 * @task 08639
	 */
	private void setMarkAsQualityInspectionButtonDisplayed(boolean markAsQualityInspectionButtonDisplayed)
	{
		if (this.markAsQualityInspectionButtonDisplayed == markAsQualityInspectionButtonDisplayed)
		{
			return;
		}
		this.markAsQualityInspectionButtonDisplayed = markAsQualityInspectionButtonDisplayed;
		updateMarkAsQualityInspectionButton();
	}

	/**
	 * Updates {@link #bMarkAsQualityInspection} button status, text and action based on current selected HUs.
	 *
	 * @task 08639
	 */
	private final void updateMarkAsQualityInspectionButton()
	{
		// Functionality is not enabled
		// => hide the button, stop here
		if (!markAsQualityInspectionButtonDisplayed)
		{
			bMarkAsQualityInspection.setVisible(false);
			bMarkAsQualityInspection.setEnabled(false);
			bMarkAsQualityInspection.setAction(null);
			return;
		}

		boolean haveHUsWithoutSupport = false;
		boolean haveHUsMarked = false;
		boolean haveHUsNotMarked = false;
		for (final HUKey huKey : model.getSelectedHUKeys())
		{
			final IQualityInspectionSchedulable qualityInspectionAware = huKey.asQualityInspectionSchedulable().orNull();
			if (qualityInspectionAware == null)
			{
				haveHUsWithoutSupport = true;
			}
			else if (qualityInspectionAware.isQualityInspection())
			{
				haveHUsMarked = true;
			}
			else
			{
				haveHUsNotMarked = true;
			}
		}

		// Case:
		// * we have no HUs with quality inspection support
		// * or we have some HUs which are marked and some HUs which are not marked
		// * or we have some HUs WITHOUT quality inspection support
		if (haveHUsMarked == haveHUsNotMarked || haveHUsWithoutSupport)
		{
			bMarkAsQualityInspection.setVisible(false);
			bMarkAsQualityInspection.setEnabled(false);
			bMarkAsQualityInspection.setAction(null);
		}
		// Case: our HUs are marked as quality inspection
		else if (haveHUsMarked)
		{
			bMarkAsQualityInspection.setVisible(true);
			bMarkAsQualityInspection.setEnabled(true);
			bMarkAsQualityInspection.setAction(ACTION_UnMarkAsQualityInspection);
			bMarkAsQualityInspection.setTextAndTranslate(ACTION_UnMarkAsQualityInspection);
		}
		// Case: our HUs are NOT marked as quality inspection
		else if (haveHUsNotMarked)
		{
			bMarkAsQualityInspection.setVisible(true);
			bMarkAsQualityInspection.setEnabled(true);
			bMarkAsQualityInspection.setAction(ACTION_MarkAsQualityInspection);
			bMarkAsQualityInspection.setTextAndTranslate(ACTION_MarkAsQualityInspection);
		}
		else
		{
			// shall no happen
		}
	}

	/**
	 * Mark/UnMark selected HUs as scheduled for Quality Inspection.
	 *
	 * @task 08639
	 */
	private final void doMarkOrUnMarkAsQualityInspection()
	{
		final String action = bMarkAsQualityInspection.getAction();
		if (ACTION_MarkAsQualityInspection.equals(action))
		{
			getHUEditorModel().setQualityInspectionFlagForSelectedHUs(true);
		}
		else if (ACTION_UnMarkAsQualityInspection.equals(action))
		{
			getHUEditorModel().setQualityInspectionFlagForSelectedHUs(false);
		}
		else
		{
			// no action => nothing to do
		}

		updateMarkAsQualityInspectionButton();
	}
}
