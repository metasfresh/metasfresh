package de.metas.fresh.picking.form;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.util.Check;

/**
 * Gets HU by it's barcode and matches rows which have HU's products (i.e. see {@link IHUQueryBuilder#setOnlyWithBarcode(String)}
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 * 
 */
public class BarcodeHUTableRowSearchSelectionMatcher extends AbstractHUTableRowSearchSelectionMatcher
{
	private final String barcode;

	public BarcodeHUTableRowSearchSelectionMatcher(final Properties ctx, final String barcode, final int warehouseId)
	{
		super(ctx, warehouseId);

		Check.assumeNotEmpty(barcode, "barcode not empty");
		this.barcode = barcode.trim();
	}

	@Override
	public String getName()
	{
		// H - HU scan (when scanned by M_HU.Value/M_HU_ID)
		// see http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29#TODOs_remaining_.28.5B.5BBenutzer:Tsa.7CTeo.5D.5D_07:50.2C_30._Mai_2014_.28CEST.29.29
		return "H";
	}

	@Override
	protected IHUQueryBuilder createHUQueryBuilder(IHUQueryBuilder huQueryBuilderInitial)
	{
		return huQueryBuilderInitial.setOnlyWithBarcode(barcode);
	}

}
