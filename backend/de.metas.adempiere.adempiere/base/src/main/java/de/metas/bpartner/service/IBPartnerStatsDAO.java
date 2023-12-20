package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Stats;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Iterator;

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
	 * @return the {@link BPartnerStats} object
	 */
	BPartnerStats getCreateBPartnerStats(I_C_BPartner partner);

	default BPartnerStats getCreateBPartnerStats(final int bpartnerId)
	{
		final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
		return getCreateBPartnerStats(bpartner);
	}

	default BPartnerStats getCreateBPartnerStats(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
		return getCreateBPartnerStats(bpartner);
	}


	/**
	 * Retrieve the total open balance value for the given stats using the old legacy sql
	 * Note: This will have to be re-implemented in order to not search in the db each time a new document is created.
	 */
	BigDecimal retrieveOpenItems(BPartnerStats stats);

	/**
	 * Set the given soCreditStatus value to the I_C_BPartner_Stats entry linked with the stats object
	 */
	void setSOCreditStatus(BPartnerStats stats, CreditStatus soCreditStatus);

	void setDeliveryCreditStatus(BPartnerStats stats, CreditStatus deliveryCreditStatus);

	BigDecimal retrieveSOCreditUsed(BPartnerStats bpStats);

	I_C_BPartner_Stats loadDataRecord(@NonNull BPartnerStats bpStats);

	@Nullable
	abstract BigDecimal computeActualLifeTimeValue(@NonNull BPartnerId partnerId);

}
