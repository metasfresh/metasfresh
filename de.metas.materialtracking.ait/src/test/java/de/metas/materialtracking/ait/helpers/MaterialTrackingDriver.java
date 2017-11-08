package de.metas.materialtracking.ait.helpers;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.text.ParseException;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.materialtracking.ch.lagerkonf.impl.HardCodedQualityBasedConfig;
import de.metas.materialtracking.ch.lagerkonf.impl.HardcodedConfigProvider;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfigProvider;

/*
 * #%L
 * de.metas.materialtracking.ait
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MaterialTrackingDriver
{
	public static String setupConfigNumberOfInvoicings(final int overallNumberOfInvoicings)
	{
		assertHardCodedConfig();

		HardCodedQualityBasedConfig.setOverallNumberOfInvoicings(overallNumberOfInvoicings);
		return "OK";
	}

	public static String setupConfigQualityAdjustment(final String qualityAdjustmentOnOff)
	{
		assertHardCodedConfig();

		final boolean qualityAdjustmentOn = qualityAdjustmentOnOff.equalsIgnoreCase("ON");
		HardCodedQualityBasedConfig.setQualityAdjustmentActive(qualityAdjustmentOn);
		return "OK";
	}

	public static void assertHardCodedConfig()
	{
		final IQualityBasedSpiProviderService qualityBasedSpiProviderService = Services.get(IQualityBasedSpiProviderService.class);
		final IQualityBasedConfigProvider qualityBasedConfigProvider = qualityBasedSpiProviderService.getQualityBasedConfigProvider();
		assertThat(qualityBasedConfigProvider instanceof HardcodedConfigProvider, is(true));
	}

	public static void setupMaterialTracking(final String lot,
			final String nameFC,
			final String valueProduct,
			final String strValidFrom,
			final String strValidTo) throws ParseException
	{
		final I_M_Product product = Helper.retrieveExisting(valueProduct, I_M_Product.class);

		final Timestamp dateValidFrom = Helper.parseTimestamp(strValidFrom);
		final Timestamp dateValidTo = Helper.parseTimestamp(strValidTo);

		final I_C_Flatrate_Conditions fc = Helper.retrieveExisting(nameFC, I_C_Flatrate_Conditions.class);

		final I_C_Flatrate_Term ft = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, Helper.context);
		ft.setStartDate(dateValidFrom);
		ft.setEndDate(dateValidTo);
		ft.setC_Flatrate_Conditions_ID(fc.getC_Flatrate_Conditions_ID());
		ft.setM_PricingSystem(fc.getM_PricingSystem());
		InterfaceWrapperHelper.save(ft);

		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, Helper.context);
		materialTracking.setLot(lot);
		materialTracking.setM_Product(product);
		materialTracking.setValidFrom(dateValidFrom);
		materialTracking.setValidTo(dateValidTo);
		materialTracking.setProcessed(false); // otherwise the ppOrder which we will link later on is not invoiceable
		materialTracking.setC_Flatrate_Term(ft);
		InterfaceWrapperHelper.save(materialTracking);

		Helper.storeFirstTime(lot, materialTracking);
	}

	public static String setupMaterialTrackingFromTemplate(final String templateLot, final String newLot)
	{
		final I_M_Material_Tracking templateMt = Helper.retrieveExisting(templateLot, I_M_Material_Tracking.class);

		final I_M_Material_Tracking newMt = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, Helper.context);
		newMt.setLot(newLot);
		newMt.setM_Product(templateMt.getM_Product());
		newMt.setValidFrom(templateMt.getValidFrom());
		newMt.setValidTo(templateMt.getValidTo());
		newMt.setProcessed(false); // otherwise the ppOrder which we will link later on is not invoiceable
		newMt.setC_Flatrate_Term(templateMt.getC_Flatrate_Term());
		InterfaceWrapperHelper.save(newMt);

		Helper.storeFirstTime(newLot, newMt);
		return "OK";
	}
}
