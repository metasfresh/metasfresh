/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.ordercandidate.spi.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class OLCandProductFromPIIPvalidatorTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void validate_supplement_product_from_piip()
	{
		// given
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("testUOM");
		final I_M_Product productRecord = BusinessTestHelper.createProduct("testProduct", uomRecord);

		final I_M_HU_PI_Item_Product piipRecord = newInstance(I_M_HU_PI_Item_Product.class);
		piipRecord.setM_Product_ID(productRecord.getM_Product_ID());
		saveRecord(piipRecord);

		final I_C_OLCand olCandRecord = newInstance(I_C_OLCand.class);
		olCandRecord.setM_HU_PI_Item_Product_ID(piipRecord.getM_HU_PI_Item_Product_ID());
		saveRecord(olCandRecord);

		// when
		new OLCandProductFromPIIPvalidator().validate(olCandRecord);

		// then
		assertThat(olCandRecord.isError()).isFalse();
		assertThat(olCandRecord.getM_Product_ID()).isEqualTo(productRecord.getM_Product_ID());
	}

	/**
	 * The date-blind barcode-lookup view can stamp an OLCand with an <em>older</em> packing-instruction
	 * version that is still valid on the delivery date (a superseded row stays valid forever without a
	 * ValidTo). On/after the newer version's switch date the validator must re-resolve to the latest
	 * valid version — being "valid on DatePromised" is not enough to keep the stamped one.
	 */
	@Test
	void switchesToLatestValidVersion_whenStampedRowIsValidButSuperseded()
	{
		// given: one product, one shared barcode, two versions — old (product default, valid from 2019)
		// and new (valid from 2023). The stamped incumbent is set directly below, so the records' relative
		// id order is irrelevant here (unlike the production EDI view, which picks by MAX id) — this test
		// isolates the validator's re-resolution, which must key off ValidFrom, not id or the default flag.
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("testUOM");
		final I_M_Product productRecord = BusinessTestHelper.createProduct("testProduct", uomRecord);
		final GTIN sharedGtin = GTIN.ofString("3333333333336");

		final I_M_HU_PI_Item_Product oldVersion = piip(productRecord, sharedGtin, "2019-01-01", true);
		final I_M_HU_PI_Item_Product newVersion = piip(productRecord, sharedGtin, "2023-01-01", false);

		// an OLCand the barcode view stamped with the OLD (still-valid, default) version, delivery date
		// on/after the new version's switch date
		final I_C_OLCand olCandRecord = newInstance(I_C_OLCand.class);
		olCandRecord.setM_HU_PI_Item_Product_ID(oldVersion.getM_HU_PI_Item_Product_ID());
		olCandRecord.setM_Product_ID(productRecord.getM_Product_ID());
		olCandRecord.setDatePromised(TimeUtil.asTimestamp(LocalDate.parse("2024-06-01")));
		saveRecord(olCandRecord);

		// when
		new OLCandProductFromPIIPvalidator().validate(olCandRecord);

		// then: re-resolved to the latest valid version (new), not kept on the still-valid old default
		assertThat(olCandRecord.getM_HU_PI_Item_Product_ID()).isEqualTo(newVersion.getM_HU_PI_Item_Product_ID());
	}

	private I_M_HU_PI_Item_Product piip(
			@NonNull final I_M_Product product,
			@NonNull final GTIN gtin,
			@NonNull final String validFrom,
			final boolean defaultForProduct)
	{
		final I_M_HU_PI_Item_Product piip = newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_Product_ID(product.getM_Product_ID());
		piip.setIsInfiniteCapacity(false);
		piip.setGTIN(gtin.getAsString());
		piip.setValidFrom(TimeUtil.asTimestamp(LocalDate.parse(validFrom)));
		piip.setIsDefaultForProduct(defaultForProduct);
		saveRecord(piip);
		return piip;
	}
}
