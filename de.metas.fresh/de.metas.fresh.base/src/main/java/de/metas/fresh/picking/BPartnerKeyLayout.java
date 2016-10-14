package de.metas.fresh.picking;

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Plain key layout for BPartners
 *
 * To add keys to this layout you can use {@link #createAndSetKeysFromBPartnerKNPs(Set)}.
 *
 * @author tsa
 *
 */
public class BPartnerKeyLayout extends DefaultKeyLayout
{
	public static final Color DEFAULT_Color = Color.CYAN;

	public BPartnerKeyLayout(final ITerminalContext tc)
	{
		super(tc);
		setDefaultColor(DEFAULT_Color);
		setColumns(3);
	}

	/**
	 * Currently set BPartner {@link KeyNamePair}s
	 */
	private Set<KeyNamePair> _bpartnerKNPs = Collections.emptySet();

	public void createAndSetKeysFromBPartnerKNPs(final Set<KeyNamePair> bpartners)
	{
		//
		// Normalize and sort input parameter
		final Set<KeyNamePair> bpartnersSorted;
		if (bpartners == null || bpartners.isEmpty())
		{
			bpartnersSorted = Collections.emptySet();
		}
		else
		{
			bpartnersSorted = new TreeSet<KeyNamePair>(bpartners);
		}

		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					//
					// Check if our keys are actually changed
					if (Check.equals(this._bpartnerKNPs, bpartnersSorted))
					{
						return null;
					}

					//
					// Create Keys
					final List<ITerminalKey> keys = new ArrayList<ITerminalKey>(bpartnersSorted.size());
					for (final KeyNamePair bpartner : bpartnersSorted)
					{
						final BPartnerKey key = new BPartnerKey(getTerminalContext(), bpartner);
						keys.add(key);
					}

					//
					// Set new Keys list
					setKeys(keys);
					return null;
				});
		this._bpartnerKNPs = bpartnersSorted;
	}

	public BPartnerKey getKeyByBPartnerId(final int bpartnerId)
	{
		final List<ITerminalKey> keys = getKeysOrNull();
		if (keys == null || keys.isEmpty())
		{
			return null;
		}

		for (final ITerminalKey key : keys)
		{
			final BPartnerKey bpartnerKey = (BPartnerKey)key;
			if (bpartnerKey.getC_BPartner_ID() == bpartnerId)
			{
				return bpartnerKey;
			}
		}

		return null;
	}
}
