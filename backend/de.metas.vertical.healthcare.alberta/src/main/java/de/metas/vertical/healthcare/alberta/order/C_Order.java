/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.order;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_C_Order.class)
@Callout(I_C_Order.class)
@Component
public class C_Order
{
	private final IBPRelationDAO bPartnerRelationDAO = Services.get(IBPRelationDAO.class);

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class)
				.registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void onBusinessPartnerChange(@NonNull final I_C_Order order)
	{
		updatePharmacyIdFromBPartner(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_C_BPartner_ID)
	public void newOrderBusinessPartnerSet(@NonNull final I_C_Order order)
	{
		if (order.getC_BPartner_ID() <= 0)
		{
			return; // not yet saved; nothing to do
		}
		updatePharmacyIdFromBPartner(order);
	}

	private void updatePharmacyIdFromBPartner(@NonNull final I_C_Order order)
	{
		final Optional<BPartnerId> optionalPreferedPharmacyID = bPartnerRelationDAO
				.getLastUpdatedPreferredPharmacyByPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()));

		optionalPreferedPharmacyID.ifPresent(bPartnerId -> order.setC_BPartner_Pharmacy_ID(bPartnerId.getRepoId()));
	}
}
