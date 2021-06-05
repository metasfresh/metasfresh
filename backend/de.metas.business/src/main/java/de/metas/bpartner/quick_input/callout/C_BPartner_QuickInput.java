/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.quick_input.callout;

import de.metas.bpartner.quick_input.service.BPartnerQuickInputService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Callout(I_C_BPartner_QuickInput.class)
public class C_BPartner_QuickInput
{
	private final BPartnerQuickInputService bpartnerQuickInputService;

	public C_BPartner_QuickInput(
			@NonNull final BPartnerQuickInputService bpartnerQuickInputService)
	{
		this.bpartnerQuickInputService = bpartnerQuickInputService;
	}

	@PostConstruct
	void postConstruct()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_BPartner_QuickInput.COLUMNNAME_IsCompany)
	public void onIsCompanyFlagChanged(@NonNull final I_C_BPartner_QuickInput record)
	{
		bpartnerQuickInputService.updateNameAndGreetingNoSave(record);
	}
}
