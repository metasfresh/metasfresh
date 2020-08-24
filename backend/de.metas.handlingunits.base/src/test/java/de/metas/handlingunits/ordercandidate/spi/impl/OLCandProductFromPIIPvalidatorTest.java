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
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ordercandidate.model.I_C_OLCand;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
