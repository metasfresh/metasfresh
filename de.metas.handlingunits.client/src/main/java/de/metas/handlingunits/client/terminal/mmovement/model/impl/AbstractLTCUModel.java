package de.metas.handlingunits.client.terminal.mmovement.model.impl;

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

import java.math.BigDecimal;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.editor.model.HUKeyVisitorAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.model.I_M_HU;

/**
 * Abstract LU/TU/CU editor model.
 *
 */
public abstract class AbstractLTCUModel extends AbstractMaterialMovementModel implements ILTCUModel
{
	public static final String ERR_SELECT_CU_KEY = "SelectCUKey";
	public static final String ERR_SELECT_TU_KEY = "SelectTUKey";
	public static final String ERR_SELECT_LU_KEY = "SelectLUKey";

	private static final String PROPERTY_QtyCU = "QtyCU";
	private static final String PROPERTY_QtyCUReadonly = "QtyCUReadonly";
	private static final String PROPERTY_QtyTU = "QtyTU";
	private static final String PROPERTY_QtyTUReadonly = "QtyTUReadonly";
	private static final String PROPERTY_QtyLU = "QtyLU";
	private static final String PROPERTY_QtyLUReadonly = "QtyLUReadonly";

	/**
	 * Customer Unit Layout (aka Products)
	 */
	private final IKeyLayout _cuKeyLayout;
	/**
	 * Transport Unit Layout (e.g. IFCO)
	 */
	private final IKeyLayout _tuKeyLayout;
	/**
	 * Load Unit Layout (e.g. Palette)
	 */
	private final IKeyLayout _luKeyLayout;

	private BigDecimal _qtyCU = BigDecimal.ZERO;
	private BigDecimal _qtyTU = BigDecimal.ZERO;
	private BigDecimal _qtyLU = BigDecimal.ZERO;

	private boolean _qtyCUReadonly = false;
	private boolean _qtyTUReadonly = true;
	private boolean _qtyLUReadonly = true;

	// private boolean _qtyCUReadonlyAlways = false;
	// private boolean _qtyTUReadonlyAlways = false;
	// private boolean _qtyLUReadonlyAlways = false;

	//
	// Services
	protected final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	protected final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public AbstractLTCUModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		//
		// Initialize Components
		_cuKeyLayout = createKeyLayout(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				onCUPressed(key);
			}
		});

		_tuKeyLayout = createKeyLayout(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				onTUPressed(key);
			}
		});

		_luKeyLayout = createKeyLayout(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				onLUPressed(key);
			}
		});
	}

	/**
	 * Does something when pressing a CU-Key
	 *
	 * @param key
	 */
	protected abstract void onCUPressed(final ITerminalKey key);

	/**
	 * Does something when pressing a TU-Key
	 *
	 * @param key
	 */
	protected abstract void onTUPressed(final ITerminalKey key);

	/**
	 * Does something when pressing a LU-Key
	 *
	 * @param key
	 */
	protected abstract void onLUPressed(final ITerminalKey key);

	@Override
	public final void setCUKey(final ITerminalKey key)
	{
		onCUPressed(key);
	}

	@Override
	public final void setTUKey(final ITerminalKey key)
	{
		onTUPressed(key);
	}

	@Override
	public final void setLUKey(final ITerminalKey key)
	{
		onLUPressed(key);
	}

	private final IKeyLayout createKeyLayout(final ITerminalKeyListener listener)
	{
		final DefaultKeyLayout keyLayout = new DefaultKeyLayout(getTerminalContext());
		keyLayout.setRows(1);

		//
		// Add rows as needed for LTCU-layouts so that the user always sees all available options
		keyLayout.setAddRowsIfColumnsExceeded(true);

		keyLayout.setColumns(layoutConstantsBL.getConstantAsInt(getLayoutConstants(), IHUPOSLayoutConstants.PROPERTY_HUSplit_KeyColumns));
		keyLayout.addTerminalKeyListener(listener);

		final IKeyLayoutSelectionModel selectionModel = keyLayout.getKeyLayoutSelectionModel();
		selectionModel.setAllowKeySelection(true);
		selectionModel.setAutoSelectIfOnlyOne(true);

		//
		// 07618: Resize font on HU keys to make them fit more stuff in them
		HUTerminalHelper.decreaseKeyLayoutFont(keyLayout);

		return keyLayout;
	}

	@Override
	public final IKeyLayout getCUKeyLayout()
	{
		return _cuKeyLayout;
	}

	@Override
	public final IKeyLayout getTUKeyLayout()
	{
		return _tuKeyLayout;
	}

	@Override
	public final IKeyLayout getLUKeyLayout()
	{
		return _luKeyLayout;
	}

	@Override
	public final void setQtyCU(final BigDecimal qtyCU)
	{
		final BigDecimal qtyCUOld = _qtyCU;
		_qtyCU = qtyCU;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyCU, qtyCUOld, qtyCU);

		onQtyCUChanged(qtyCU, qtyCUOld);
	}

	/**
	 * Method called after CU Qty was changed.
	 *
	 * @param qtyCU
	 * @param qtyCUOld
	 */
	protected void onQtyCUChanged(final BigDecimal qtyCU, final BigDecimal qtyCUOld)
	{
		// nothing at this level
	}

	@Override
	public final void setQtyTU(final BigDecimal qtyTU)
	{
		final BigDecimal qtyTUOld = _qtyTU;
		_qtyTU = qtyTU;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyTU, qtyTUOld, qtyTU);

		onQtyTUChanged(qtyTU, qtyTUOld);
	}

	@Override
	public final void setQtyTU(final int qtyTU)
	{
		setQtyTU(BigDecimal.valueOf(qtyTU));
	}

	/**
	 * Method called after TU Qty was changed.
	 *
	 * @param qtyTU
	 * @param qtyTUOld
	 */
	protected void onQtyTUChanged(final BigDecimal qtyTU, final BigDecimal qtyTUOld)
	{
		// nothing
	}

	@Override
	public final void setQtyLU(final BigDecimal qtyLU)
	{
		final BigDecimal qtyLUOld = _qtyLU;
		_qtyLU = qtyLU;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyLU, qtyLUOld, qtyLU);

		onQtyLUChanged(qtyLU, qtyLUOld);
	}

	@Override
	public final void setQtyLU(final int qtyLU)
	{
		setQtyLU(BigDecimal.valueOf(qtyLU));
	}

	/**
	 * Method called after LU Qty was changed.
	 *
	 * @param qtyLU
	 * @param qtyLUOld
	 */
	protected void onQtyLUChanged(final BigDecimal qtyLU, final BigDecimal qtyLUOld)
	{
		// nothing
	}

	@Override
	public final BigDecimal getQtyCU()
	{
		return _qtyCU;
	}

	@Override
	public final BigDecimal getQtyTU()
	{
		return _qtyTU;
	}

	public final int getQtyTUAsInt()
	{
		return getQtyTU().intValueExact();
	}

	@Override
	public final BigDecimal getQtyLU()
	{
		return _qtyLU;
	}

	public final int getQtyLUAsInt()
	{
		return getQtyTU().intValueExact();
	}

	/**
	 * Calculates total CU quantity (i.e. QtyCU x QtyTU x QtyLU)
	 *
	 * @return total CU quantity
	 */
	public final BigDecimal getTotalQtyCU()
	{
		final BigDecimal qtyCU = getQtyCU();
		final BigDecimal qtyTU = getQtyTU();

		BigDecimal totalQtyCU = qtyCU.multiply(qtyTU);

		final BigDecimal qtyLU = getQtyLU();
		if (qtyLU.signum() != 0)
		{
			totalQtyCU = totalQtyCU.multiply(qtyLU);
		}

		return totalQtyCU;
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public boolean isQtyCUReadonly()
	{
		return _qtyCUReadonly;
	}

	@Override
	public final void setQtyCUReadonly(final boolean qtyCUReadonly)
	{
		final boolean qtyCUReadonlyOld = _qtyCUReadonly;
		_qtyCUReadonly = qtyCUReadonly;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyCUReadonly, qtyCUReadonlyOld, qtyCUReadonly);
	}

	@Override
	public final boolean isQtyTUReadonly()
	{
		return _qtyTUReadonly;
	}

	@Override
	public final void setQtyTUReadonly(final boolean qtyTUReadonly)
	{
		final boolean qtyTUReadonlyOld = _qtyTUReadonly;
		_qtyTUReadonly = qtyTUReadonly;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyTUReadonly, qtyTUReadonlyOld, qtyTUReadonly);
	}

	@Override
	public final boolean isQtyLUReadonly()
	{
		return _qtyLUReadonly;
	}

	@Override
	public final void setQtyLUReadonly(final boolean qtyLUReadonly)
	{
		final boolean qtyLUReadonlyOld = _qtyLUReadonly;
		_qtyLUReadonly = qtyLUReadonly;
		pcs.firePropertyChange(AbstractLTCUModel.PROPERTY_QtyLUReadonly, qtyLUReadonlyOld, qtyLUReadonly);
	}

	/**
	 * Helper method which is:
	 * <ul>
	 * <li>removing all top level HUs which were destroyed
	 * <li>recreating new instance of those {@link HUKey}s which were not destroyed
	 * </ul>
	 *
	 * @param rootKey
	 */
	protected void rebuildRootKey(final IHUKey rootKey)
	{
		Check.assumeNotNull(rootKey, "rootKey not null");
		Check.assume(rootKey.getParent() == null, "rootKey shall have no children: {}", rootKey);

		//
		// Reset Key Factories cache
		// NOTE: this affects cached attribute storages which were changed
		final IHUKeyFactory keyFactory = rootKey.getKeyFactory();
		keyFactory.clearCache();

		rootKey.iterate(new HUKeyVisitorAdapter()
		{
			@Override
			public VisitResult beforeVisit(final IHUKey keyOld)
			{
				final HUKey huKeyOld = HUKey.castIfPossible(keyOld);
				if (huKeyOld == null)
				{
					return VisitResult.CONTINUE;
				}
				final I_M_HU hu = huKeyOld.getM_HU();

				// remove old key
				rootKey.removeChild(keyOld);

				// recreate the key if not already destroyed and it's still top level
				if (!handlingUnitsBL.isDestroyedRefreshFirst(hu) // re-add keys which have their HUs were not destroyed
						&& handlingUnitsBL.isTopLevel(hu))   // AND which are not attached to a parent
				{
					final IHUKey keyNew = keyFactory.createKey(hu, huKeyOld.findDocumentLineOrNull());
					rootKey.addChild(keyNew);
				}

				// skip downstream, because we are iterating only the root's direct children
				return VisitResult.SKIP_DOWNSTREAM;
			}
		});
	}
}
