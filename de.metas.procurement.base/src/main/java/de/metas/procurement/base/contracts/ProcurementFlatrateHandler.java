package de.metas.procurement.base.contracts;

import de.metas.flatrate.api.impl.DefaultFlatrateHandler;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_C_Flatrate_Conditions;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProcurementFlatrateHandler extends DefaultFlatrateHandler
{
	public static final String TYPE_CONDITIONS = I_C_Flatrate_Conditions.TYPE_CONDITIONS_Procuremnt;

	@Override
	protected void deleteFlatrateTermDataEntriesOnReactivate(final I_C_Flatrate_Term term)
	{
		// do not delete the data entries on reactivate!
	}
}
