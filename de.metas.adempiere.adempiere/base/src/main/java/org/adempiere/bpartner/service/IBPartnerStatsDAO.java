package org.adempiere.bpartner.service;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface IBPartnerStatsDAO extends ISingletonService
{

	/**
	 * Retrieve the {@link I_C_BPartner_Stats} entry for the given partner if it exists and creates a new one if it doesn't exist.
	 * Note: Do not return the {@link I_C_BPartner_Stats} directly. This class shall be the only one accessing that table.
	 * Instead, create a new IBPartnerStats object based on the found bp stats and return it.
	 *
	 * @param partner
	 * @return the {@link BPartnerStats} object
	 */
	BPartnerStats getCreateBPartnerStats(I_C_BPartner partner);
	
	default BPartnerStats getCreateBPartnerStats(final int bpartnerId)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.loadOutOfTrx(bpartnerId, I_C_BPartner.class);
		return getCreateBPartnerStats(bpartner);
	}

	/**
	 * Retrieve the total open balance value for the given stats using the old legacy sql
	 * Note: This will have to be re-implemented in order to not search in the db each time a new document is created.
	 *
	 * @param stats
	 * @return
	 */
	BigDecimal retrieveOpenItems(BPartnerStats stats);


	/**
	 * Set the given soCreditStatus value to the I_C_BPartner_Stats entry linked with the stats object
	 *
	 * @param stats
	 */
	void setSOCreditStatus(BPartnerStats stats, String soCreditStatus);

	BigDecimal retrieveSOCreditUsed(BPartnerStats bpStats);

	void updateBPartnerStatistics(BPartnerStats bpStats);
}
