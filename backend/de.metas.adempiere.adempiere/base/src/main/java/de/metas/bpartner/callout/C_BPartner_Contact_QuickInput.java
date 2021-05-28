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

package de.metas.bpartner.callout;

import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Callout(I_C_BPartner_Contact_QuickInput.class)
public class C_BPartner_Contact_QuickInput
{
	private final IUserBL userBL = Services.get(IUserBL.class);

	@PostConstruct
	void postConstruct()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_BPartner_Contact_QuickInput.COLUMNNAME_Firstname)
	public void onFirstNameChange(final I_C_BPartner_Contact_QuickInput record)
	{
		updateContactName(record);
	}

	@CalloutMethod(columnNames = I_C_BPartner_Contact_QuickInput.COLUMNNAME_Firstname)
	public void onLastNameChange(final I_C_BPartner_Contact_QuickInput record)
	{
		updateContactName(record);
	}

	private void updateContactName(final I_C_BPartner_Contact_QuickInput record)
	{
		final String name = userBL.buildContactName(record.getFirstname(), record.getLastname());
		record.setName(name);
	}
}
