package org.adempiere.bpartner.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Set;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BPartner;

/**
 * Service used to update {@link I_C_BPartner#COLUMN_TotalOpenBalance} and {@link I_C_BPartner#COLUMN_SO_CreditUsed}.
 * 
 * @author tsa
 *
 */
public interface IBPartnerTotalOpenBalanceUpdater extends ISingletonService
{
	ModelDynAttributeAccessor<I_C_AllocationHdr, Boolean> DYNATTR_DisableUpdateTotalOpenBalances = new ModelDynAttributeAccessor<>("org.adempiere.bpartner.service.IBPartnerTotalOpenBalanceUpdater.DisableUpdateTotalOpenBalances", Boolean.class);
	
	void updateTotalOpenBalances(final Properties ctx, final Set<Integer> bpartnerToBalances, final String trxName);

}
