/*
 * #%L
 * de.metas.dunning
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

package de.metas.dunning.callout;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.dunning.api.impl.DunningDocDocumentLocationAdapterFactory;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.springframework.stereotype.Component;

@Callout(I_C_DunningDoc.class)
@Component
public class C_DunningDoc
{
	private final IDocumentLocationBL documentLocationBL;

	public C_DunningDoc(@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.documentLocationBL = documentLocationBL;

		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = {
			I_C_DunningDoc.COLUMNNAME_C_BPartner_ID,
			I_C_DunningDoc.COLUMNNAME_C_BPartner_Location_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_C_DunningDoc dunningDoc)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(DunningDocDocumentLocationAdapterFactory.locationAdapter(dunningDoc));
	}
}
