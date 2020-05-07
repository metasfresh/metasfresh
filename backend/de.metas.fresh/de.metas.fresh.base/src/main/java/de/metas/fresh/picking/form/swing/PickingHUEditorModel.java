package de.metas.fresh.picking.form.swing;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.document.impl.NullHUDocumentLineFinder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Picking HU editor (model).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */class PickingHUEditorModel extends HUEditorModel
{
	private static final String SYSCONFIG_AllowPickingDifferentAttributes = "de.metas.fresh.picking.form.swing.PickingHUEditorModel.AllowPickingDifferentAttributes";
	
	private IHUSupplier huSupplier;
	private I_M_HU _huToSelect;

	private final boolean showConsiderAttributesCheckbox;
	private Boolean _considerAttributes = null;

	public PickingHUEditorModel(final ITerminalContext terminalContext, final I_M_HU huToSelect, final IHUSupplier huSupplier)
	{
		super(terminalContext);

		Check.assumeNotNull(huSupplier, "huSupplier not null");
		this.huSupplier = huSupplier;

		_huToSelect = huToSelect;
		
		final IHUKeyFactory huKeyFactory = getHUKeyFactory();
		final IHUKey rootHUKey = huKeyFactory.createRootKey();
		setRootHUKey(rootHUKey);
		
		//
		// Check if user is allowed to pick without considering the attributes.
		// Then query for the matching HUs.
		final boolean allowPickingDifferentAttributes = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_AllowPickingDifferentAttributes, true, Env.getAD_Client_ID(terminalContext.getCtx()), Env.getAD_Org_ID(terminalContext.getCtx()));
		if(allowPickingDifferentAttributes)
		{
			this.showConsiderAttributesCheckbox = true;
			setConsiderAttributes(true); // query
		}
		else
		{
			this.showConsiderAttributesCheckbox = false;
			setConsiderAttributes(true); // query
			
			// If there were no HUs found and user cannot play with "consider attributes" checkbox,
			// then there is no need to go forward because there is nothing that user can do.
			if(!getRootHUKey().hasChildren())
			{
				dispose();
				throw new HUException("@NotFound@ @M_HU@");
			}
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
		huSupplier = null;
		_huToSelect = null;
	}

	private I_M_HU getHUToSelect()
	{
		return _huToSelect;
	}
	
	public boolean isShowConsiderAttributesCheckbox()
	{
		return showConsiderAttributesCheckbox;
	}

	/**
	 * Sets if we shall consider the attributes while searching for matching HUs.
	 *
	 * This method is also automatically fetching the matching HUs.
	 *
	 * @param considerAttributes
	 * @task FRESH-578 #275
	 */
	/* package */void setConsiderAttributes(final boolean considerAttributes)
	{
		_considerAttributes = considerAttributes;
		refreshHUKeys();
	}

	public boolean isConsiderAttributes()
	{
		final Boolean considerAttributes = _considerAttributes;
		return considerAttributes != null && considerAttributes.booleanValue();
	}

	private void refreshHUKeys()
	{
		//
		// Find available HUs
		final List<I_M_HU> hus = huSupplier.retrieveHUs(isConsiderAttributes());

		//
		// Update the root HU key with available HUs
		final IHUKeyFactory huKeyFactory = getHUKeyFactory();
		final List<IHUKey> huKeys = huKeyFactory.createKeys(hus, NullHUDocumentLineFinder.instance); // documentLine = null
		
		final IHUKey rootHUKey = getRootHUKey();
		for (final IHUKey child : new ArrayList<>(rootHUKey.getChildren()))
		{
			rootHUKey.removeChild(child);
		}
		rootHUKey.addChildren(huKeys);
		//
		clearSelectedKeyIds();
		setCurrentHUKey(rootHUKey);

		//
		// Auto-select the provided HU if any
		final I_M_HU huToSelect = getHUToSelect();
		if (huToSelect != null)
		{
			setSelected(huToSelect);
		}
	}

	/**
	 * Matching HUs supplier.
	 */
	public static interface IHUSupplier
	{
		/**
		 *
		 * @param considerAttributes true if we shall consider the HU attributes while searching for matching HUs
		 * @return matching HUs
		 */
		List<I_M_HU> retrieveHUs(final boolean considerAttributes);
	}
}
