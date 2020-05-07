package org.compiere.model;

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
import java.util.Properties;

import org.adempiere.pricing.api.IPriceListDAO;

public class MPricingSystem extends X_M_PricingSystem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3696311474689460049L;

	@Deprecated
	public static final int M_PricingSystem_ID_None = IPriceListDAO.M_PricingSystem_ID_None;

	public MPricingSystem(Properties ctx, int M_PricingSystem_ID, String trxName) {
		super(ctx, M_PricingSystem_ID, trxName);
	}

	public MPricingSystem(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}
