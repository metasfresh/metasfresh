package de.metas.flatrate.callout;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;

import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Term;

public class C_Flatrate_Term extends CalloutEngine
{
	public String onC_Flatrate_Conditions_ID(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_C_Flatrate_Term term = GridTabWrapper.create(mTab, I_C_Flatrate_Term.class);
		Services.get(IFlatrateBL.class).updateFromConditions(term);
		return NO_ERROR;
	}
}
