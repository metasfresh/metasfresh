/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Locator;

public class M_Locator_StepDefData extends StepDefData<I_M_Locator>
		implements StepDefDataGetIdAware<LocatorId, I_M_Locator>
{
	public M_Locator_StepDefData()
	{
		super(I_M_Locator.class);
	}

	@Override
	public LocatorId extractIdFromRecord(final I_M_Locator record) {return LocatorId.ofRecord(record);}
}
