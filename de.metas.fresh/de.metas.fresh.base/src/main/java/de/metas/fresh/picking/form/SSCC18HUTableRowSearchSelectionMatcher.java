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

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;

/**
 * Gets HU by SSCC18 attribute and matches rows which have HU's products
 * <p>
 * Interesting detail: currently only matches HUs that have a C_BPartner set
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 *
 */
public class SSCC18HUTableRowSearchSelectionMatcher extends AbstractHUTableRowSearchSelectionMatcher
{
	private final String sscc18;

	public SSCC18HUTableRowSearchSelectionMatcher(final Properties ctx, final String sscc18, final int warehouseId)
	{
		super(ctx, warehouseId);

		Check.assumeNotEmpty(sscc18, "sscc18 not empty");
		this.sscc18 = sscc18.trim();
	}

	/**
	 * @return the letter <code>S</code>
	 */
	@Override
	public String getName()
	{
		// SSCC18 matched
		// see http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29#TODOs_remaining_.28.5B.5BBenutzer:Tsa.7CTeo.5D.5D_07:50.2C_30._Mai_2014_.28CEST.29.29
		return "S";
	}

	@Override
	protected IHUQueryBuilder createHUQueryBuilder(IHUQueryBuilder huQueryBuilderInitial)
	{
		final I_M_Attribute attr_sscc18 = Services.get(ISSCC18CodeDAO.class).retrieveSSCC18Attribute(getCtx());
		if (attr_sscc18 == null)
		{
			// SSCC18 attribute was not defined => invalid matcher
			return null;
		}

		return huQueryBuilderInitial
				// match SSCC18 attribute
				.addOnlyWithAttribute(attr_sscc18, sscc18)
				// only HU's with BPartner set shall be considered (06821)
				.setOnlyIfAssignedToBPartner(true);
	}
}
