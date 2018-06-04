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
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 * 
 */
public class MFreightCostDetail extends X_M_FreightCostDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8752324165768518156L;

	public MFreightCostDetail(Properties ctx, int M_FreightCostDetail_ID,
			String trxName) {
		super(ctx, M_FreightCostDetail_ID, trxName);
	}

	public MFreightCostDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public String toString() {

		final StringBuffer sb = new StringBuffer("MFreightCostDetail[") //
				.append(get_ID()) //
				.append(", M_FreightCost_Shipper_ID").append(
						getM_FreightCostShipper_ID()) //
				.append(", FreightAmt").append(getFreightAmt()) //
				.append(", C_Country_ID").append(getC_Country_ID()) //
				.append(", ShipmentValueAmt=").append(getShipmentValueAmt()) //
				.append("]");
		return sb.toString();
	}

	/**
	 * Loads the freight cost details for a given
	 * {@link I_M_FreightCostDetail#COLUMNNAME_M_FreightCostShipper_ID}
	 * M_FreightCost_ID}.
	 * 
	 * @param freightCostShipper
	 * @return the freight cost details with the given M_FreightCost_ID, ordered
	 *         by {@link I_M_FreightCostDetail#COLUMNNAME_ShipmentValueAmt
	 *         ShipmentValueAmt} (ascending).
	 */
	public static List<MFreightCostDetail> retrieveFor(
			final MFreightCostShipper freightCostShipper) {

		final Object[] parameters = { freightCostShipper.get_ID() };

		final String whereClause = COLUMNNAME_M_FreightCostShipper_ID
				+ "=?";

		final String orderBy = COLUMNNAME_ShipmentValueAmt;
		
		final Query query = new Query(freightCostShipper.getCtx(), Table_Name,
				whereClause, freightCostShipper.get_TrxName());
		
		query.setOnlyActiveRecords(true);
		query.setParameters(parameters);
		query.setOrderBy(orderBy);
	
		return query.list(MFreightCostDetail.class);
	}
}
