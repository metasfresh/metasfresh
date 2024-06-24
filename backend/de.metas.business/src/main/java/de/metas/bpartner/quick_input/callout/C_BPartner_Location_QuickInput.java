/*
 * #%L
 * de.metas.business
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

import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.quick_input.service.BPartnerQuickInputRepository;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.model.I_C_Location;
import org.compiere.model.MakeUniqueLocationNameCommand;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Callout(I_C_BPartner_Location_QuickInput.class)
public class C_BPartner_Location_QuickInput
{
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

	private final BPartnerQuickInputRepository repo;
	public C_BPartner_Location_QuickInput(final BPartnerQuickInputRepository repo)
	{
		this.repo = repo;
	}

	@PostConstruct
	void postConstruct()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_BPartner_Location_QuickInput.COLUMNNAME_C_Location_ID)
	public void onLocationChanged(@NonNull final I_C_BPartner_Location_QuickInput record)
	{
		final I_C_Location locationRecord = locationDAO.getById(LocationId.ofRepoIdOrNull(record.getC_Location_ID()));

		if(locationRecord == null)
		{
			// location not yet created. Nothing to do yet
			return;
		}

		final I_C_BPartner_QuickInput bpartnerQuickInputRecord = repo.getById(BPartnerQuickInputId.ofRepoId(record.getC_BPartner_QuickInput_ID()));
		final POInfo poInfo = POInfo.getPOInfo(I_C_BPartner_Location_QuickInput.Table_Name);

		// gh12157: Please, keep in sync with org.compiere.model.MBPartnerLocation.beforeSave
		final String name = MakeUniqueLocationNameCommand.builder()
				.name(record.getName())
				.address(locationRecord)
				.companyName(bpartnerQuickInputRecord.getCompanyname())
				.existingNames(repo.getOtherLocationNames(record.getC_BPartner_QuickInput_ID(), record.getC_BPartner_Location_QuickInput_ID()))
				.maxLength(poInfo.getFieldLength(I_C_BPartner_Location_QuickInput.COLUMNNAME_Name))
				.build()
				.execute();

		record.setName(name);
	}
}
