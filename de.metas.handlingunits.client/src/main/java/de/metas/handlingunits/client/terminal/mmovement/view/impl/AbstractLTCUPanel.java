package de.metas.handlingunits.client.terminal.mmovement.view.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DisplayType;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.PositiveNumericFieldConstraint;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.client.terminal.mmovement.view.ILTCUPanel;
import de.metas.util.Check;

/**
 * Loading / Trading / Customer Unit Panel. Used to easily define CU-TU-LU panels.
 *
 * @author al
 *
 * @param <T> model type
 */
public abstract class AbstractLTCUPanel<T extends ILTCUModel> extends AbstractMaterialMovementPanel<T> implements ILTCUPanel
{
	protected static final String PANEL_CU_QTY = "CU_Qty";
	private ITerminalKeyPanel cuKeyLayoutPanel = null;
	private ITerminalNumericField cuQtyField = null;

	protected static final String PANEL_TU_QTY = "TU_Qty";
	private ITerminalKeyPanel tuKeyLayoutPanel = null;
	private ITerminalNumericField tuQtyField = null;

	protected static final String PANEL_LU_QTY = "LU_Qty";
	private ITerminalKeyPanel luKeyLayoutPanel = null;
	private ITerminalNumericField luQtyField = null;

	protected static final String LANECELL_Label = "LANECELL_Label";
	protected static final String LANECELL_KeysPanel = "LANECELL_KeysPanel";
	protected static final String LANECELL_QtyField = "LANECELL_QtyField";
	protected static final String LANECELL_ANY = "LANECELL_ANY";

	private Properties _laneCellConstraints;

	public AbstractLTCUPanel(final T model)
	{
		super(model);

		final ITerminalContext terminalContext = model.getTerminalContext();
		final ITerminalFactory factory = terminalContext.getTerminalFactory();

		//
		// Initialize Components
		initComponents(factory);

		//
		// Initialize Layout
		initLayout(factory);

		//
		// Refresh status from model
		getVMSynchronizer().loadFromModel();
	}

	/**
	 * Initialize components (if any)
	 *
	 * @param factory
	 */
	protected void initComponents(final ITerminalFactory terminalFactory)
	{
		// nothing at this level
	}

	/**
	 * Initialize layout. To add CU-TU-LU fields, see {@link #addLane(ITerminalFactory, String, ITerminalKeyPanel, ITerminalNumericField)}.
	 *
	 * @param factory
	 */
	protected abstract void initLayout(final ITerminalFactory terminalFactory);

	protected final void setLaneCellConstraints(final String laneType, final String laneCellType, final String cellConstraints)
	{
		Check.assumeNotNull(laneType, "laneType not null");
		Check.assumeNotNull(laneCellType, "laneCellType not null");

		final String key = laneType + "#" + laneCellType;
		if (_laneCellConstraints == null)
		{
			_laneCellConstraints = new Properties();
		}
		_laneCellConstraints.setProperty(key, cellConstraints);
	}

	protected final String getLaneCellConstraints(final String laneType, final String laneCellType)
	{
		if (_laneCellConstraints == null)
		{
			return "";
		}

		Check.assumeNotNull(laneType, "laneType not null");
		Check.assumeNotNull(laneCellType, "laneCellType not null");

		//
		// Get the constraints for our Lane Type / Cell Type
		final String key = laneType + "#" + laneCellType;
		final String constraints = _laneCellConstraints.getProperty(key, "");
		if (constraints != null && constraints.isEmpty())
		{
			return constraints;
		}

		//
		// Fallback: get the contraints for ANY Cell
		if (LANECELL_ANY != laneCellType)
		{
			return getLaneCellConstraints(laneType, LANECELL_ANY);
		}
		else
		{
			return "";
		}
	}

	protected static final String joinConstraints(final String... constraints)
	{
		if (constraints == null || constraints.length == 0)
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder();
		for (String c : constraints)
		{
			if (c == null)
			{
				continue;
			}
			c = c.trim();
			if (c.isEmpty())
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append(",");
			}
			sb.append(c);
		}

		return sb.toString();
	}

	@Override
	protected void loadFromModel()
	{
		// NOTE: null checks are because even if components are final,
		// it might be that load() is triggered from some event before those fields are constructed

		if (model == null)
		{
			return;
		}

		final ILTCUModel model = this.model;
		if (cuQtyField != null)
		{
			cuQtyField.setValue(model.getQtyCU(), false); // fireEvent=false
			cuQtyField.setEditable(!model.isQtyCUReadonly());
		}
		if (tuQtyField != null)
		{
			tuQtyField.setValue(model.getQtyTU(), false); // fireEvent=false
			tuQtyField.setEditable(!model.isQtyTUReadonly());
		}
		if (luQtyField != null)
		{
			luQtyField.setValue(model.getQtyLU(), false); // fireEvent=false
			luQtyField.setEditable(!model.isQtyLUReadonly());
		}
	}

	protected final void addCULane(final boolean useQtyField)
	{
		final List<LayoutComponent> other = Collections.emptyList();
		addCULane(useQtyField, other);
	}

	protected final void addCULane(final boolean useQtyField, final List<LayoutComponent> other)
	{
		if (cuKeyLayoutPanel != null)
		{
			throw new TerminalException("CULayout already initialized!");
		}

		final IKeyLayout cuKeyLayout = model.getCUKeyLayout();
		cuKeyLayoutPanel = createTerminalKeyPanel(cuKeyLayout);

		if (useQtyField)
		{
			cuQtyField = createQtyField(PANEL_CU_QTY);
			cuQtyField.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					model.setQtyCU(cuQtyField.getValue());
				}
			});
		}
		addLane(PANEL_CU_QTY, cuKeyLayoutPanel, cuQtyField, other);
	}

	protected final void addTULane(final boolean useQtyField)
	{
		final List<LayoutComponent> other = Collections.emptyList();
		addTULane(useQtyField, other);
	}

	protected final void addTULane(final boolean useQtyField, final List<LayoutComponent> other)
	{
		if (tuKeyLayoutPanel != null)
		{
			throw new TerminalException("TULayout already initialized!");
		}

		final IKeyLayout tuKeyLayout = model.getTUKeyLayout();
		tuKeyLayoutPanel = createTerminalKeyPanel(tuKeyLayout);

		if (useQtyField)
		{
			tuQtyField = createQtyField(PANEL_TU_QTY);
			tuQtyField.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					model.setQtyTU(tuQtyField.getValue());
				}
			});
		}

		addLane(PANEL_TU_QTY, tuKeyLayoutPanel, tuQtyField, other);
	}

	protected final void addLULane(final boolean useQtyField)
	{
		final List<LayoutComponent> other = Collections.emptyList();
		addLULane(useQtyField, other);
	}

	protected final void addLULane(final boolean useQtyField, final List<LayoutComponent> other)
	{
		if (luKeyLayoutPanel != null)
		{
			throw new TerminalException("LULayout already initialized!");
		}

		final IKeyLayout luKeyLayout = model.getLUKeyLayout();
		luKeyLayoutPanel = createTerminalKeyPanel(luKeyLayout);

		if (useQtyField)
		{
			luQtyField = createQtyField(PANEL_LU_QTY);
			luQtyField.addListener(new PropertyChangeListener()
			{
				@Override
				public void propertyChange(final PropertyChangeEvent evt)
				{
					model.setQtyLU(luQtyField.getValue());
				}
			});
		}

		addLane(PANEL_LU_QTY, luKeyLayoutPanel, luQtyField, other);
	}
	
	protected final void addCustomLane(final String laneType, final ITerminalKeyPanel keyPanel)
	{
		final ITerminalNumericField qtyField = null;
		final List<LayoutComponent> otherLCs = null;
		addLane(laneType, keyPanel, qtyField, otherLCs);
	}

	private final void addLane(final String laneType,
			final ITerminalKeyPanel keyPanel,
			final ITerminalNumericField qtyField,
			final List<LayoutComponent> otherLCs)
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();

		//
		// Add lane label
		final ITerminalLabel labelComp = terminalFactory.createLabel(laneType);
		final String labelConstraints = joinConstraints(
				"grow 0, shrink 0"
				, getLaneCellConstraints(laneType, LANECELL_Label)
				);
		panel.add(labelComp, labelConstraints);

		final boolean isQtyFieldSupported = qtyField != null;
		final boolean hasAdditionalComponents = otherLCs != null && !otherLCs.isEmpty();

		//
		// Add KeyPanel
		final String keyPanelConstraints = joinConstraints(
				"grow, push"
				, getWrap(!isQtyFieldSupported && !hasAdditionalComponents)
				, getLaneCellConstraints(laneType, LANECELL_KeysPanel)
				);
		panel.add(keyPanel, keyPanelConstraints);

		//
		// Add Qty Field (if applicable)
		if (isQtyFieldSupported)
		{
			final String qtyFieldConstraints = joinConstraints(
					"grow 0, shrink 0"
					, getWrap(!hasAdditionalComponents)
					, getLaneCellConstraints(laneType, LANECELL_QtyField)
					);
			panel.add(qtyField, qtyFieldConstraints);
		}

		//
		// Add additional components (if any)
		if (hasAdditionalComponents)
		{
			final Iterator<LayoutComponent> other = otherLCs.iterator();
			while (other.hasNext())
			{
				final LayoutComponent layoutComponent = other.next();
				final boolean lastElement = !other.hasNext();
				final String constraints = joinConstraints(
						layoutComponent.getLayoutConstraints()
						, getWrap(lastElement)
						);
				panel.add(layoutComponent.getComponent(), constraints);
			}
		}
	}

	protected final Properties getLayoutConstants()
	{
		final T model = getModel();
		final Properties layoutConstants = model.getLayoutConstants();
		return layoutConstants;
	}

	protected ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keyLayout)
	{
		final Properties layoutConstants = getLayoutConstants();
		final String fixedHeight = layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyFixedHeight);
		final String fixedWidth = layoutConstants.getProperty(IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyFixedWidth);

		final ITerminalFactory factory = getTerminalFactory();
		return factory.createTerminalKeyPanel(keyLayout, fixedHeight, fixedWidth);
	}

	protected ITerminalNumericField createQtyField(final String label)
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();
		final ITerminalNumericField qtyField = terminalFactory.createTerminalNumericField(
				label,
				DisplayType.Quantity,
				true, // withButtons=true
				false); // withLabel=false - we'll have a custom Label setup
		qtyField.setEditable(false);
		qtyField.addConstraint(PositiveNumericFieldConstraint.instance);
		return qtyField;
	}

	/**
	 * Add horizontal lane
	 *
	 * @param laneComponent
	 * @param laneConstraints
	 */
	protected final void addHorizontalLane(final IComponent laneComponent, final String laneConstraints)
	{
		final String constraints = "spanx" // Spans the current cell (merges) over all cells.
				+ ", wrap" // Wraps to a new column/row after the component has been put in the next available cell.
				+ ", " + (laneConstraints == null ? "" : laneConstraints);
		panel.add(laneComponent, constraints);
	}

	/**
	 * In place of the labels, mock space on the horizontal lanes (i.e lane will contain configuration options for the LTCU panel).
	 *
	 * @param terminalFactory
	 */
	protected final void mockHorizontalLaneLabelSpace()
	{
		final ITerminalLabel labelComp = getTerminalFactory().createLabel("");
		final String labelConstraints = joinConstraints(
				"grow 0, shrink 0"
				, getLaneCellConstraints("", LANECELL_Label)
				);
		panel.add(labelComp, labelConstraints);
	}

	private final String getWrap(final boolean lastElement)
	{
		if (lastElement)
		{
			return ", wrap";
		}
		return "";
	}

	protected final ITerminalKeyPanel getCUKeyLayoutPanel()
	{
		return getElement(cuKeyLayoutPanel);
	}

	protected final ITerminalNumericField getCUQtyField()
	{
		return getElement(cuQtyField);
	}

	protected final ITerminalKeyPanel getTUKeyLayoutPanel()
	{
		return getElement(tuKeyLayoutPanel);
	}

	protected final ITerminalNumericField getTUQtyField()
	{
		return getElement(tuQtyField);
	}

	protected final ITerminalKeyPanel getLUKeyLayoutPanel()
	{
		return getElement(luKeyLayoutPanel);
	}

	protected final ITerminalNumericField getLUQtyField()
	{
		return getElement(luQtyField);
	}

	private final <CT extends IComponent> CT getElement(final CT component)
	{
		Check.assumeNotNull(component, "component not null on retrieval. Check element instantiation");
		return component;
	}

	/**
	 * Used to group {@link IComponent} and <code>MigLayoutConstraints</code><br>
	 * <br>
	 * TODO <b>Note: pls extract this in the future if you need something similar.</b>
	 *
	 * @author al
	 */
	protected static class LayoutComponent
	{
		private final IComponent component;
		private final String layoutConstraints;

		public LayoutComponent(final IComponent component, final String layoutConstraints)
		{
			super();
			this.component = component;
			this.layoutConstraints = layoutConstraints;
		}

		public IComponent getComponent()
		{
			return component;
		}

		public String getLayoutConstraints()
		{
			return layoutConstraints;
		}
	}
}
