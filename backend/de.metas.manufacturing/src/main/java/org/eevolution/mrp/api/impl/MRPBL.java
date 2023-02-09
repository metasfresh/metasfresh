package org.eevolution.mrp.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Check;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;

public class MRPBL implements IMRPBL
{
	private boolean isQtyOnHandReservation(final I_PP_MRP mrpSupply)
	{
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");

		final String typeMRP = mrpSupply.getTypeMRP();
		if (!X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			return false;
		}

		final String orderType = mrpSupply.getOrderType();
		return X_PP_MRP.ORDERTYPE_QuantityOnHandReservation.equals(orderType);
	}

	private boolean isQtyOnHandInTransit(final I_PP_MRP mrpSupply)
	{
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");

		final String typeMRP = mrpSupply.getTypeMRP();
		if (!X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			return false;
		}

		final String orderType = mrpSupply.getOrderType();
		return X_PP_MRP.ORDERTYPE_QuantityOnHandInTransit.equals(orderType);
	}

	@Override
	public boolean isQtyOnHandAnyReservation(final I_PP_MRP mrpSupply)
	{
		return isQtyOnHandReservation(mrpSupply)
				|| isQtyOnHandInTransit(mrpSupply);
	}
}
