package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import de.metas.util.Check;
import lombok.NonNull;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

@Service
public class ProductPlanningService
{
	/**
	 * Duration to have this Qty available (i.e. Lead Time + Transfer Time)
	 *
	 * @return return duration [days]
	 */
	public int calculateDurationDays(final int leadTimeDays, @NonNull final I_PP_Product_Planning productPlanningData)
	{
		Check.assume(leadTimeDays >= 0, "leadTimeDays >= 0");

		final int transferTimeDays = productPlanningData.getTransfertTime().intValueExact();
		Check.assume(transferTimeDays >= 0, "transferTimeDays >= 0");

		return leadTimeDays + transferTimeDays;
	}
}
