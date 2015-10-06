package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 */
public class MFreightCostShipper extends X_M_FreightCostShipper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -828372390456233509L;

	public MFreightCostShipper(Properties ctx, int M_FreightCost_ID,
			String trxName) {
		super(ctx, M_FreightCost_ID, trxName);
	}

	public MFreightCostShipper(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static Collection<MFreightCostShipper> retrieveForLocation(
			final MFreightCost fc, final int locationId) {

		return Collections.emptyList();
	}

	public static MFreightCostShipper retrieveForShipper(final MFreightCost fc,
			final int shipperId) {

		final String whereClause = COLUMNNAME_M_FreightCost_ID + "=? AND "
				+ COLUMNNAME_M_Shipper_ID + "=?";
		
		final Object[] params = { fc.get_ID(), shipperId };

		return new Query(fc.getCtx(), Table_Name, whereClause, fc.get_TrxName())
				.setOnlyActiveRecords(true).setParameters(params).firstOnly();
	}

	@Override
	public String toString() {

		final StringBuffer sb = new StringBuffer("MFreightCost_Shipper[") //
				.append(get_ID()) //
				.append(", M_FreightCost_ID=").append(getM_FreightCost_ID()) //
				.append(", M_Shipper_ID=").append(getM_Shipper_ID()) //
				.append("]");
		return sb.toString();
	}
}
