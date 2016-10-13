package de.metas.fresh.picking.form.swing;

/*
 * #%L
 * de.metas.fresh.base
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

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.form.terminal.DefaultPropertiesPanelModel;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IPropertiesPanel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModel;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalDialogListener;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.adempiere.form.terminal.field.constraint.PositiveNumericFieldConstraint;
import de.metas.fresh.picking.model.DistributeQtyToNewHUsRequest;
import de.metas.fresh.picking.model.DistributeQtyToNewHUsResult;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * UI panel which starts from a given initial {@link DistributeQtyToNewHUsRequest}, allows the user to edit it and at the end provides the final {@link DistributeQtyToNewHUsResult}.
 *
 * To create a new instance, use {@link #builder()}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08754_Kommissionierung_Erweiterung_Verteilung_%28103380135151%29
 */
public class DistributeQtyToNewHUsReadPanel implements IComponent, ITerminalDialogListener
{
	/** Starts creating a new dialog which will allow the user the edit the request and it will provide the result */
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final String PROPERTYNAME_M_HU_PI_Item_Product_ID = I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
	private static final String PROPERTYNAME_QtyToDistribute = "QtyToDistribute";
	private static final String PROPERTYNAME_QtyToDistributePerHU = "QtyToDistributePerHU";
	private static final String PROPERTYNAME_QtyTU = "QtyTU";
	private static final String PROPERTYNAME_IsCloseCurrentHU = "IsCloseCurrentHU";
	private static final String PROPERTYNAME_Result = "Result";
	private static final Set<String> PROPERTYNAMES_Qtys = ImmutableSet.of(PROPERTYNAME_QtyToDistribute, PROPERTYNAME_QtyToDistributePerHU, PROPERTYNAME_QtyTU);

	private static final String SYSCONFIG_EnforceStandardQtyPerHU = "de.metas.customer.picking.form.swing.DistributeQtyToNewHUsReadPanel.EnforceStandardQtyPerHU";
	private static final boolean DEFAULT_EnforceStandardQtyPerHU = true;

	//
	// Parameters
	private final ITerminalContext _terminalContext;
	private final DefaultPropertiesPanelModel propertiesModel;
	private I_M_HU_PI_Item_Product _piItemProduct;
	private I_C_UOM qtyToDistributeUOM;
	private DistributeQtyToNewHUsResult _result;

	// UI
	private final IPropertiesPanel propertiesPanel;
	private final AtomicBoolean modelSyncRunning = new AtomicBoolean(false);

	private boolean disposed = false;

	private DistributeQtyToNewHUsReadPanel(final Builder builder)
	{
		_terminalContext = builder.getTerminalContext();

		// Model
		propertiesModel = new DefaultPropertiesPanelModel(_terminalContext);
		propertiesModel.newProperty(PROPERTYNAME_M_HU_PI_Item_Product_ID, DisplayType.String)
				.setEditable(false)
				.createAndAdd();
		propertiesModel.newProperty(PROPERTYNAME_QtyToDistribute, DisplayType.Quantity)
				.setEditable(false)
				.setValue(BigDecimal.ZERO)
				.createAndAdd();
		propertiesModel.newProperty(PROPERTYNAME_QtyToDistributePerHU, DisplayType.Quantity)
				.setEditable(true)
				.setValue(BigDecimal.ZERO)
				.setConstraint(PositiveNumericFieldConstraint.instance)
				.createAndAdd();
		propertiesModel.newProperty(PROPERTYNAME_QtyTU, DisplayType.Integer)
				.setEditable(true)
				.setValue(BigDecimal.ZERO)
				.setConstraint(PositiveNumericFieldConstraint.instance)
				.createAndAdd();
		if (builder.requestCloseCurrentHUConfirmation)
		{
			propertiesModel.newProperty(PROPERTYNAME_IsCloseCurrentHU, DisplayType.YesNo)
					.setEditable(true)
					.setValue(false)
					.createAndAdd();
		}

		propertiesModel.newProperty(PROPERTYNAME_Result, DisplayType.TextLong)
				.setEditable(false)
				.setValue(null)
				.createAndAdd();

		propertiesModel.addPropertyChangeListener(IPropertiesPanelModel.PROPERTY_ValueChanged, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				final String propertyName = (String)evt.getNewValue();
				onValueChanged(propertyName);
			}
		});

		// UI
		propertiesPanel = builder.getTerminalFactory().createPropertiesPanel();
		propertiesPanel.setVerticalScrollBarPolicy(ScrollPolicy.NEVER);
		propertiesPanel.setModel(propertiesModel);

		_terminalContext.addToDisposableComponents(this);
	}

	public DistributeQtyToNewHUsResult getResult()
	{
		return _result;
	}

	private I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		Check.assumeNotNull(_piItemProduct, "_piItemProduct not null");
		return _piItemProduct;
	}

	private BigDecimal getQtyToDistributePerHU()
	{
		final BigDecimal qtyToDistributePerHU = (BigDecimal)propertiesModel.getPropertyValue(PROPERTYNAME_QtyToDistributePerHU);
		return qtyToDistributePerHU == null ? BigDecimal.ZERO : qtyToDistributePerHU;
	}

	private void setQtyToDistributePerHU(final BigDecimal qtyToDistributePerHU)
	{
		propertiesModel.setPropertyValue(PROPERTYNAME_QtyToDistributePerHU, qtyToDistributePerHU);
	}

	private void setQtyToDistributePerHUReadOnly(final boolean readonly)
	{
		propertiesModel.setEditable(PROPERTYNAME_QtyToDistributePerHU, !readonly);
	}

	private BigDecimal getQtyToDistribute()
	{
		final BigDecimal qtyToDistribute = (BigDecimal)propertiesModel.getPropertyValue(PROPERTYNAME_QtyToDistribute);
		return qtyToDistribute == null ? BigDecimal.ZERO : qtyToDistribute;
	}

	private I_C_UOM getQtyToDistributeUOM()
	{
		return qtyToDistributeUOM;
	}

	private void setQtyToDistribute(final BigDecimal qtyToDistribute, final I_C_UOM qtyToDistributeUOM)
	{
		propertiesModel.setPropertyValue(PROPERTYNAME_QtyToDistribute, qtyToDistribute);
		this.qtyToDistributeUOM = qtyToDistributeUOM;
	}

	private void setQtyTU(final BigDecimal qtyTU)
	{
		propertiesModel.setPropertyValue(PROPERTYNAME_QtyTU, qtyTU);
	}

	private BigDecimal getQtyTU()
	{
		final BigDecimal qtyTU = (BigDecimal)propertiesModel.getPropertyValue(PROPERTYNAME_QtyTU);
		if (qtyTU == null)
		{
			return BigDecimal.ZERO;
		}
		return qtyTU;
	}

	private int getQtyTUAsInt()
	{
		return getQtyTU().intValueExact();
	}

	private boolean isCloseCurrentHU()
	{
		if (!propertiesModel.getPropertyNames().contains(PROPERTYNAME_IsCloseCurrentHU))
		{
			return true;
		}

		final Boolean closeCurrentHU = (Boolean)propertiesModel.getPropertyValue(PROPERTYNAME_IsCloseCurrentHU);
		return closeCurrentHU != null && closeCurrentHU;
	}

	public void setRequest(final DistributeQtyToNewHUsRequest request)
	{
		Check.assumeNotNull(request, "request not null");
		this._piItemProduct = request.getM_HU_PI_Item_Product();
		Check.assumeNotNull(_piItemProduct, "_piItemProduct not null");

		//
		// Load from request
		final String piItemProductName = Services.get(IHUPIItemProductBL.class).buildDisplayName()
				.setM_HU_PI_Item_Product(_piItemProduct)
				.buildItemProductDisplayName();

		modelSyncRunning.set(true);
		try
		{
			final BigDecimal qtyPerHU;
			final boolean qtyPerHUReadonly;
			if (isEnforceStandardQtyPerHU(request))
			{
				qtyPerHU = _piItemProduct.getQty();
				qtyPerHUReadonly = true;
			}
			else
			{
				qtyPerHU = request.getQtyToDistributePerHU();
				qtyPerHUReadonly = false;
			}

			propertiesModel.setPropertyValue(PROPERTYNAME_M_HU_PI_Item_Product_ID, piItemProductName);
			setQtyToDistribute(request.getQtyToDistribute(), request.getQtyToDistributeUOM());
			setQtyToDistributePerHU(qtyPerHU);
			setQtyToDistributePerHUReadOnly(qtyPerHUReadonly);

			calculateAndSetQtyTU();
			updateResultInfo();
		}
		finally
		{
			modelSyncRunning.set(false);
		}
	}

	private final void onValueChanged(final String propertyName)
	{
		if (modelSyncRunning.getAndSet(true))
		{
			return;
		}

		try
		{
			//
			// Quantity fields interdependencies
			if (PROPERTYNAMES_Qtys.contains(propertyName))
			{
				if (!PROPERTYNAME_QtyTU.equals(propertyName))
				{
					calculateAndSetQtyTU();
				}
				else if (!PROPERTYNAME_QtyToDistributePerHU.equals(propertyName))
				{
					// nothing
				}
			}

			updateResultInfo();
		}
		finally
		{
			modelSyncRunning.set(false);
		}
	}

	private void calculateAndSetQtyTU()
	{
		final BigDecimal qtyToDistribute = getQtyToDistribute();
		final BigDecimal qtyToDistributePerHU = getQtyToDistributePerHU();

		final BigDecimal qtyTU;
		if (qtyToDistributePerHU.signum() <= 0)
		{
			qtyTU = BigDecimal.ZERO;
		}
		else
		{
			qtyTU = qtyToDistribute.divide(qtyToDistributePerHU, 0, RoundingMode.UP);
		}

		setQtyTU(qtyTU);
	}

	private void updateResultInfo()
	{
		final DistributeQtyToNewHUsResult currentResult = createResult();
		final String displayInfo = currentResult.getDisplayInfo();
		propertiesModel.setPropertyValue(PROPERTYNAME_Result, displayInfo);
	}

	/**
	 * Creates the request based on current user input.
	 *
	 * @return
	 */
	private final DistributeQtyToNewHUsRequest createRequest()
	{
		return DistributeQtyToNewHUsRequest.builder()
				.setM_HU_PI_Item_Product(getM_HU_PI_Item_Product())
				.setQtyToDistribute(getQtyToDistribute())
				.setQtyToDistributeUOM(getQtyToDistributeUOM())
				.setQtyToDistributePerHU(getQtyToDistributePerHU())
				.setQtyTU(getQtyTUAsInt())
				.build();
	}

	/**
	 * Calculates the result based on current user input.
	 *
	 * @return
	 */
	private final DistributeQtyToNewHUsResult createResult()
	{
		final DistributeQtyToNewHUsRequest request = createRequest();
		return DistributeQtyToNewHUsResult.calculateResult(request);
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return _terminalContext;
	}

	@Override
	public Object getComponent()
	{
		return propertiesPanel.getComponent();
	}

	@Override
	public void dispose()
	{
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public void onDialogOk(final ITerminalDialog dialog)
	{
		validate();
		_result = createResult();
		_result.validate();
	}

	/**
	 * Validate input fields
	 */
	private final void validate()
	{
		final BigDecimal qtyToDistribute = getQtyToDistribute();
		if (qtyToDistribute == null || qtyToDistribute.signum() <= 0)
		{
			throw new TerminalException("@FillMandatory@ @" + PROPERTYNAME_QtyToDistribute + "@");
		}

		final BigDecimal qtyToDistributePerHU = getQtyToDistributePerHU();
		if (qtyToDistributePerHU == null || qtyToDistributePerHU.signum() <= 0)
		{
			throw new TerminalException("@FillMandatory@ @" + PROPERTYNAME_QtyToDistributePerHU + "@");
		}

		final int qtyTU = getQtyTUAsInt();
		if (qtyTU <= 0)
		{
			throw new TerminalException("@FillMandatory@ @" + PROPERTYNAME_QtyTU + "@");
		}

		if (qtyToDistributePerHU.compareTo(qtyToDistribute) > 0)
		{
			throw new TerminalException("@Invalid@ @" + PROPERTYNAME_QtyToDistributePerHU + "@ > @" + PROPERTYNAME_QtyToDistribute + "@");
		}

		if (!isCloseCurrentHU())
		{
			throw new TerminalException("@FillMandatory@ @" + PROPERTYNAME_IsCloseCurrentHU + "@");
		}
	}

	@Override
	public boolean onDialogCanceling(final ITerminalDialog dialog)
	{
		return true;
	}

	@Override
	public void onDialogOpened(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogActivated(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogDeactivated(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogClosing(final ITerminalDialog dialog)
	{
		// nothing
	}

	@Override
	public void onDialogClosed(final ITerminalDialog dialog)
	{
		// nothing
	}

	/**
	 * Checks if the Qty/HU from PI Item Product shall be enforced.
	 *
	 * @param request
	 * @return true if capacity stall be enforced.
	 */
	private static final boolean isEnforceStandardQtyPerHU(final DistributeQtyToNewHUsRequest request)
	{
		// We are enforcing it only if the SysConfig says so
		if (!Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_EnforceStandardQtyPerHU, DEFAULT_EnforceStandardQtyPerHU))
		{
			return false;
		}

		// we cannot enforce it for infinite capacity
		final I_M_HU_PI_Item_Product piItemProduct = request.getM_HU_PI_Item_Product();
		if (piItemProduct == null)
		{
			return false;
		}
		if (piItemProduct.isInfiniteCapacity())
		{
			return false;
		}

		// TODO: handle the UOMs

		return true;
	}

	public static class Builder
	{
		private boolean requestCloseCurrentHUConfirmation = false;
		private ITerminalContext _terminalContext;
		private IComponent _parentComponent;
		private String _title;
		private DistributeQtyToNewHUsRequest _request;

		private Builder()
		{
			super();
		}

		/**
		 * Show the dialog to user, wait for his input and return the result.
		 * If user had canceled the UI dialog, null will be returned.
		 *
		 * @return result or <code>null</code> if user canceled
		 */
		public final DistributeQtyToNewHUsResult getResultOrNull()
		{
			final DistributeQtyToNewHUsResult result;
			try (final ITerminalContextReferences references = getTerminalContext().newReferences())
			{
				final DistributeQtyToNewHUsReadPanel readPanel = new DistributeQtyToNewHUsReadPanel(this);
				readPanel.setRequest(getRequest());

				final ITerminalDialog readPanelDialog = getTerminalFactory().createModalDialog(getParentComponent(), getTitle(), readPanel);

				readPanelDialog.setSize(new Dimension(450, 350));
				readPanelDialog.activate();

				if (readPanelDialog.isCanceled())
				{
					return null;
				}

				result = readPanel.getResult();
			}
			return result;
		}

		public Builder setRequestCloseCurrentHUConfirmation(final boolean requestCloseCurrentHUConfirmation)
		{
			this.requestCloseCurrentHUConfirmation = requestCloseCurrentHUConfirmation;
			return this;
		}

		public Builder setTerminalContext(final ITerminalContext terminalContext)
		{
			this._terminalContext = terminalContext;
			return this;
		}

		private final ITerminalContext getTerminalContext()
		{
			Check.assumeNotNull(_terminalContext, "_terminalContext not null");
			return _terminalContext;
		}

		private final ITerminalFactory getTerminalFactory()
		{
			return getTerminalContext().getTerminalFactory();
		}

		public Builder setParentComponent(final IComponent parentComponent)
		{
			this._parentComponent = parentComponent;
			return this;
		}

		public final IComponent getParentComponent()
		{
			Check.assumeNotNull(_parentComponent, "_parentComponent not null");
			return this._parentComponent;
		}

		public final Builder setTitle(final String title)
		{
			this._title = title;
			return this;
		}

		private final String getTitle()
		{
			return _title;
		}

		public Builder setRequest(final DistributeQtyToNewHUsRequest request)
		{
			this._request = request;
			return this;
		}

		private final DistributeQtyToNewHUsRequest getRequest()
		{
			Check.assumeNotNull(_request, "_request not null");
			return _request;
		}
	}
}
