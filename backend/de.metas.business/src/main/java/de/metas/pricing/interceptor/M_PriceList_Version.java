/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.pricing.interceptor;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Callout(I_M_PriceList_Version.class)
@Interceptor(I_M_PriceList_Version.class)
@Component
public class M_PriceList_Version
{
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);


	@Init
	public void registerCallouts()
	{
		CopyRecordFactory.enableForTableName(I_M_PriceList_Version.Table_Name);
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_M_PriceList_Version.COLUMNNAME_ValidFrom)
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = { I_M_PriceList_Version.COLUMNNAME_ValidFrom })
	public void updatePLVName(@NonNull final I_M_PriceList_Version priceListVersion)
	{
		final PriceListId priceListId = PriceListId.ofRepoId(priceListVersion.getM_PriceList_ID());
		final I_M_PriceList priceList = priceListDAO.getById(priceListId);
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(priceListVersion.getAD_Org_ID()));
		final LocalDate date = TimeUtil.asLocalDate(priceListVersion.getValidFrom(), timeZone);

		if (date == null)
		{
			return;
		}

		final String plvName = priceListBL.createPLVName(priceList.getName(), date);
		priceListVersion.setName(plvName);
	}
}
