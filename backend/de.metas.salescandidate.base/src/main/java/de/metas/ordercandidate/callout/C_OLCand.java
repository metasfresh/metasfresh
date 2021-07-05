/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.callout;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.location.OLCandLocationsUpdaterService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Callout(I_C_OLCand.class)
@Component
public class C_OLCand
{
	private final OLCandLocationsUpdaterService olCandLocationsUpdaterService;

	public C_OLCand(@NonNull final OLCandLocationsUpdaterService olCandLocationsUpdaterService)
	{
		this.olCandLocationsUpdaterService = olCandLocationsUpdaterService;
	}

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID })
	public void onBPartnerOverride(final I_C_OLCand olCand)
	{
		olCandLocationsUpdaterService.updateBPartnerLocationOverride(olCand);
	}

	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_DropShip_BPartner_Override_ID })
	public void onDropShipPartnerOverride(final I_C_OLCand olCand)
	{
		olCandLocationsUpdaterService.updateDropShipLocationOverride(olCand);
	}

	@CalloutMethod(columnNames = { I_C_OLCand.COLUMNNAME_HandOver_Partner_Override_ID })
	public void onHandOverPartnerOverrideCallout(@NonNull final I_C_OLCand olCand)
	{
		olCandLocationsUpdaterService.updateHandoverLocationOverride(olCand);
	}

}
