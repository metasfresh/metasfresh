package de.metas.impexp.processing;

import de.metas.pricing.rules.campaign_price.impexp.CampaignPriceImportProcess;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.model.I_I_Campaign_Price;
import org.compiere.model.I_I_DiscountSchema;
import org.compiere.model.I_I_Postal;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_I_Replenish;
import org.compiere.model.I_I_Request;
import org.compiere.model.I_I_User;
import org.springframework.context.annotation.Configuration;

import de.metas.bpartner.impexp.BPartnerImportProcess;
import de.metas.dataentry.data.impexp.DataEntryRecordsImportProcess;
import de.metas.dataentry.model.I_I_DataEntry_Record;
import de.metas.globalid.impexp.BPartnerGlobalIDImportProcess;
import de.metas.location.impexp.PostalCodeImportProcess;
import de.metas.pricing.impexp.DiscountSchemaImportProcess;
import de.metas.product.impexp.ProductImportProcess;
import de.metas.replenishment.impexp.ReplenishmentImportProcess;
import de.metas.request.impexp.RequestImportProcess;
import de.metas.user.impexp.ADUserImportProcess;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Configuration
public class StandardImportProcessesConfiguration
{
	public StandardImportProcessesConfiguration()
	{
		registerStandardImportProcesses();
	}

	private void registerStandardImportProcesses()
	{
		final IImportProcessFactory importProcessesFactory = Services.get(IImportProcessFactory.class);
		importProcessesFactory.registerImportProcess(I_I_BPartner.class, BPartnerImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_User.class, ADUserImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_Product.class, ProductImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_Request.class, RequestImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_DiscountSchema.class, DiscountSchemaImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_BPartner_GlobalID.class, BPartnerGlobalIDImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_Replenish.class, ReplenishmentImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_Postal.class, PostalCodeImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_DataEntry_Record.class, DataEntryRecordsImportProcess.class);
		importProcessesFactory.registerImportProcess(I_I_Campaign_Price.class, CampaignPriceImportProcess.class);
	}
}
