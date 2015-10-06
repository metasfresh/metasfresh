package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.MProductCategory;

import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;

public interface IContractBL extends ISingletonService
{

	/**
	 * 
	 * @param type
	 * @param sponsor
	 * @return the commission relevant product category or <code>null</code> if there is no restriction.
	 */
	MProductCategory retrieveProductCategory(I_C_AdvCommissionTerm term, I_C_Sponsor sponsor);

	Object retrieveInstanceParam(Properties ctx, I_C_AdvComSystem_Type systemType, String name, String trxName);

	boolean hasInstanceParam(Properties ctx, I_C_AdvComSystem_Type systemType, String name, String trxName);

	boolean hasSponsorParam(ICommissionContext commissionCtx, String name);

	Object retrieveSponsorParam(ICommissionContext commissionCtx, String name);

}
