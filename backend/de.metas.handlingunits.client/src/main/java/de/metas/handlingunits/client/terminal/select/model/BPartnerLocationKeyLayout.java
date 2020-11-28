package de.metas.handlingunits.client.terminal.select.model;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_C_BPartner_Location;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class BPartnerLocationKeyLayout extends DefaultKeyLayout
{
	static final Color DEFAULT_Color = VendorKeyLayout.DEFAULT_Color;
	static final int DEFAULT_Columns = VendorKeyLayout.DEFAULT_Columns;
	static final int DEFAULT_Rows = 2;

	private static final String MSG_NoBPLocationSelectedError = "NoBPLocationSelectedError";

	public BPartnerLocationKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		setRows(DEFAULT_Rows);
		setColumns(DEFAULT_Columns);
		setDefaultColor(DEFAULT_Color);
	}

	/**
	 *
	 * @param bpartnerLocations
	 */
	public void createAndSetKeysFromBPartnerLocations(final Collection<? extends I_C_BPartner_Location> bpartnerLocations)
	{
		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					if (bpartnerLocations == null || bpartnerLocations.isEmpty())
					{
						final List<ITerminalKey> keys = Collections.emptyList();
						setKeys(keys);
						return null;
					}

					//
					// Create Keys
					final ITerminalContext terminalContext = getTerminalContext();
					final List<ITerminalKey> keys = new ArrayList<ITerminalKey>();
					for (final I_C_BPartner_Location bpartnerLocation : bpartnerLocations)
					{
						final BPartnerLocationKey key = new BPartnerLocationKey(terminalContext, bpartnerLocation);
						keys.add(key);
					}

					//
					// Sort the keys by their Name(asc).
					Collections.sort(keys, ITerminalKey.COMPARATOR_ByName);

					//
					// Set new Keys list
					setKeys(keys);
					return null;
				});
	}

	public BPartnerLocationKey getSelectedKey()
	{
		return getKeyLayoutSelectionModel()
				.getSelectedKey(BPartnerLocationKey.class, MSG_NoBPLocationSelectedError);
	}
}
