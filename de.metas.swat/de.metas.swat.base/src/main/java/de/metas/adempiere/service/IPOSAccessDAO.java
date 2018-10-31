package de.metas.adempiere.service;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.I_C_POS_Profile;
import org.adempiere.model.I_C_POS_Profile_Warehouse;

import de.metas.util.ISingletonService;

/**
 * Service that contains methods regarding POS access to data.
 * 
 * @author ad
 * 
 */
public interface IPOSAccessDAO extends ISingletonService
{
	/**
	 * Gets this role's POS profile.
	 * 
	 * @param ctx
	 * @param adRoleId
	 * @param trxName
	 * @return
	 */
	I_C_POS_Profile retrieveProfileByRole(Properties ctx, int adRoleId, String trxName);

	/**
	 * Gets the POS profile warehouses for the given POS profile.
	 * 
	 * @param ctx
	 * @param posProfileId
	 * @param trxName
	 * @return
	 */
	List<I_C_POS_Profile_Warehouse> retrieveWarehouseProfiles(Properties ctx, int posProfileId, String trxName);
}
