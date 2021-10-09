package org.eevolution.mrp.api;

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

import de.metas.material.planning.IMRPSegment;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;

import java.math.BigDecimal;

public interface IMRPBL extends ISingletonService
{
	String toString(final I_PP_MRP mrp);

	/**
	 * Sets MRP's Quantity
	 * 
	 * @param uom qty's UOM; if uom is <code>null</code> then no UOM conversions will be performed, but this shall be avoided
	 */
	void setQty(final I_PP_MRP mrp, final BigDecimal qtyTarget, final BigDecimal qty, final I_C_UOM uom);

	/**
	 * Checks if the mrp's document is released (i.e. the Demand is Firm).
	 * 
	 * @return true if the document is released (i.e. see {@link MRPFirmType#Firm#hasDocStatus(String)}).
	 */
	boolean isReleased(final I_PP_MRP mrp);

	/**
	 * @return true if given <code>mrp</code> is not null and it's a Demand
	 */
	boolean isDemand(I_PP_MRP mrp);

	/**
	 * @return true if given <code>mrp</code> is not null and it's a Supply
	 */
	boolean isSupply(I_PP_MRP mrp);

	/**
	 * Gets MRP's UOM (i.e. Product's stocking UOM)
	 * 
	 * @return uom; never return null
	 */
	I_C_UOM getC_UOM(I_PP_MRP mrp);

	/**
	 * Gets MRP Alternative's UOM (i.e. Product's stocking UOM)
	 * 
	 * @return uom; never return null
	 */
	I_C_UOM getC_UOM(I_PP_MRP_Alternative mrpAlternative);

	/**
	 * Creates an {@link IMRPSegment} based on given {@link I_PP_MRP} record.
	 * 
	 * @return MRP segment; never return null
	 */
	IMRPSegment createMRPSegment(I_PP_MRP mrp);

	/**
	 * @return true if given MRP supply record is not an actual supply but an QtyOnHand reservation
	 */
	boolean isQtyOnHandReservation(I_PP_MRP mrpSupply);

	boolean isQtyOnHandInTransit(I_PP_MRP mrpSupply);

	boolean isQtyOnHandAnyReservation(I_PP_MRP mrpSupply);
}
