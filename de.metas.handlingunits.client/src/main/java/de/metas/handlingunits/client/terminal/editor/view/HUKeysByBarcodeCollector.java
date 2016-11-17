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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.client.terminal.editor.model.HUKeyVisitorAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.MoreHUKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUKeysByBarcodeCollector extends HUKeyVisitorAdapter
{
	private static final transient Logger logger = LogManager.getLogger(HUKeysByBarcodeCollector.class);


	private final IQueryFilter<I_M_HU> barcodeFilter;

	private final List<IHUKey> collectedHUKeys = new ArrayList<IHUKey>();

	public HUKeysByBarcodeCollector(final String barcode)
	{
		super();

		Check.assumeNotEmpty(barcode, "barcode not empty");

		//
		// Barcode matcher
		barcodeFilter = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				// Match by our barcode
				.setOnlyWithBarcode(barcode)
				// Match any HU, even if is top level or not
				// NOTE: we are not enforcing here that only top level HUs shall be matched because that's caller's responsability
				.setOnlyTopLevelHUs(false)
				// Create our filter
				.createQueryFilter();
	}

	@Override
	public VisitResult beforeVisit(final IHUKey key)
	{
		if (key instanceof MoreHUKey)
		{
			final MoreHUKey moreHUKey = (MoreHUKey)key;
			collect(moreHUKey);
			// continue searching
			return VisitResult.CONTINUE;
		}

		final HUKey huKey = HUKey.castIfPossible(key);
		if (huKey != null)
		{
			collect(huKey);
			// continue searching
			return VisitResult.CONTINUE;
		}

		// => continue searching
		return VisitResult.CONTINUE;
	}

	private final void collect(final MoreHUKey moreHUKey)
	{
		final List<IHUKey> huKeys = moreHUKey.extractAllKeys(barcodeFilter);
		collectedHUKeys.addAll(huKeys);
	}

	private final void collect(final HUKey huKey)
	{
		if (huKey == null)
		{
			return;
		}

		//
		// Get underlying HU
		final I_M_HU hu = huKey.getM_HU();

		//
		// If barcode matches then collect our key
		if (match(hu))
		{
			collectedHUKeys.add(huKey);
		}
	}

	/**
	 * Checks if HU is matching our barcode
	 *
	 * @param hu
	 * @return true if given HU is matching our barcode
	 */
	protected boolean match(final I_M_HU hu)
	{
		return barcodeFilter.accept(hu);
	}

	public List<IHUKey> getCollectedHUKeys()
	{
		logger.debug("this-ID={} returning collectedHUKeys={}; this={}", System.identityHashCode(this), collectedHUKeys, this);
		return collectedHUKeys;
	}

	@Override
	public String toString()
	{
		return "HUKeysByBarcodeCollector [barcodeFilter=" + barcodeFilter + ", collectedHUKeys=" + collectedHUKeys + "]";
	}
}
