package de.metas.procurement.base;

import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.procurement.base.model.I_AD_User;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IPMMContractsBL extends ISingletonService
{
	/**
	 * Gets default AD_User_ID in charge for a new procurement contract.
	 * 
	 * @param ctx
	 * @return AD_User_ID or <code>-1</code> if there is no default configured
	 */
	int getDefaultContractUserInCharge_ID(Properties ctx);

	/**
	 * Gets default user in charge for a new procurement contract.
	 * 
	 * @param ctx
	 * @return user in charge or <code>null</code> if there is no default configured
	 */
	I_AD_User getDefaultContractUserInChargeOrNull(Properties ctx);

	/**
	 * @param dataEntry
	 * @return true if given data entry has the price or qty planned set
	 * @task FRESH-568
	 */
	boolean hasPriceOrQty(I_C_Flatrate_DataEntry dataEntry);
}
