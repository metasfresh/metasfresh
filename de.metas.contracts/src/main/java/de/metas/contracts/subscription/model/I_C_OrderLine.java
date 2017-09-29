package de.metas.contracts.subscription.model;

import org.adempiere.model.ModelColumn;

import de.metas.contracts.flatrate.interfaces.IFlatrateConditionsAware;
import de.metas.contracts.model.I_C_Flatrate_Conditions;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_C_OrderLine extends de.metas.interfaces.I_C_OrderLine, IFlatrateConditionsAware
{
	public static final ModelColumn<I_C_OrderLine, I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(
			I_C_OrderLine.class,
			COLUMNNAME_C_Flatrate_Conditions_ID,
			I_C_Flatrate_Conditions.class);

}
