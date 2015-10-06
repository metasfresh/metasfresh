package de.metas.handlingunits.client.terminal.inventory.view;

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


import java.awt.Color;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.adempiere.form.terminal.DefaultKeyPanelRenderer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.BPartnerLocationKey;

/**
 * {@link BPartnerLocationKey}s renderer which colors the keys differently if we have Locked HUs on those locations.
 *
 * The implementation relies on the fact that the {@link I_C_BPartner_Location} has their {@link #DYNATTR_CountLockedHUs} set.
 *
 * @author tsa
 *
 */
public final class BPartnerLocationKeyLayoutRenderer extends DefaultKeyPanelRenderer
{
	public static final transient BPartnerLocationKeyLayoutRenderer instance = new BPartnerLocationKeyLayoutRenderer();

	private static final ModelDynAttributeAccessor<I_C_BPartner_Location, Integer> DYNATTR_CountLockedHUs = InventoryHUSelectModel.DYNATTR_CountLockedHUs;

	public static final Color COLOR_DEFAULT = null; // use upper level defaults
	public static final Color COLOR_HaveLockedHUs = HUEditorPanel.COLOR_HUKeyBackground_ReadOnly;

	private BPartnerLocationKeyLayoutRenderer()
	{
		super();
	}

	@Override
	protected Color getBackgroundColor(final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		if (terminalKey instanceof BPartnerLocationKey)
		{
			final BPartnerLocationKey bpLocationKey = (BPartnerLocationKey)terminalKey;
			final Color backgroundColor = getBackgroundColor(bpLocationKey);
			if (backgroundColor != null)
			{
				return backgroundColor;
			}
		}

		// Fallback to parent/default
		return super.getBackgroundColor(terminalKey, keyLayout);
	}

	// public for testing purposes only
	public final Color getBackgroundColor(final BPartnerLocationKey bpLocationKey)
	{
		//
		// Get Locked HUs counter
		final I_C_BPartner_Location bpLocation = bpLocationKey.getC_BPartner_Location();
		final Integer countLockedHUs = DYNATTR_CountLockedHUs.getValue(bpLocation);

		//
		// IF we have locked HUs, color those keys differently
		if (countLockedHUs != null && countLockedHUs > 0)
		{
			return COLOR_HaveLockedHUs;
		}

		// Fallback
		return COLOR_DEFAULT;
	}

}
