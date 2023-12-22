/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.eevolution.api.impl;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;

class ProductBOMServiceTest
{
	private ProductBOMService productBOMService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.productBOMService = new ProductBOMService(new ProductBOMVersionsDAO());
	}

	@Nullable
	private static Timestamp ts(@Nullable final String localDateStr)
	{
		return localDateStr != null
				? Timestamp.from(LocalDate.parse(localDateStr).atStartOfDay().atZone(SystemTime.zoneId()).toInstant())
				: null;
	}

	@Nested
	class assertNoOverlapping_tests
	{
		private final ProductBOMVersionsId versionsId = ProductBOMVersionsId.ofRepoId(123);

		I_PP_Product_BOM bom(final String validFrom, @Nullable final String validTo)
		{
			final I_PP_Product_BOM record = InterfaceWrapperHelper.newInstance(I_PP_Product_BOM.class);
			record.setPP_Product_BOMVersions_ID(versionsId.getRepoId());
			record.setBOMType(X_PP_Product_BOM.BOMTYPE_CurrentActive);
			record.setValidFrom(ts(validFrom));
			record.setValidTo(ts(validTo));
			InterfaceWrapperHelper.save(record);
			return record;
		}

		void assertNoOverlapping(final I_PP_Product_BOM bom)
		{
			productBOMService.assertNoOverlapping(bom);
		}

		void assertIsOverlapping(final I_PP_Product_BOM bom)
		{
			Assertions.assertThatThrownBy(() -> productBOMService.assertNoOverlapping(bom))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith(ProductBOMService.MSG_BOM_VERSIONS_OVERLAPPING.toAD_Message());
		}

		@Test
		void noValidTo()
		{
			final I_PP_Product_BOM bom1 = bom("2023-10-01", null);
			assertNoOverlapping(bom1);

			final I_PP_Product_BOM bom2 = bom("2023-10-03", null);
			assertNoOverlapping(bom1);
			assertNoOverlapping(bom2);

			final I_PP_Product_BOM bom3 = bom("2023-10-05", null);
			assertNoOverlapping(bom1);
			assertNoOverlapping(bom2);
			assertNoOverlapping(bom3);
		}

		@Test
		void bom1_01_03____bom2_02_null()
		{
			final I_PP_Product_BOM bom1 = bom("2023-10-01", "2023-10-03");
			final I_PP_Product_BOM bom2 = bom("2023-10-02", null);
			assertIsOverlapping(bom1);
			assertIsOverlapping(bom2);
		}

		@Test
		void bom1_01_03____bom2_03_null()
		{
			final I_PP_Product_BOM bom1 = bom("2023-10-01", "2023-10-03");
			final I_PP_Product_BOM bom2 = bom("2023-10-03", null);
			assertNoOverlapping(bom1);
			assertNoOverlapping(bom2);
		}

		@Test
		void bom1_01_03____bom2_04_null()
		{
			final I_PP_Product_BOM bom1 = bom("2023-10-01", "2023-10-03");
			final I_PP_Product_BOM bom2 = bom("2023-10-04", null);
			assertNoOverlapping(bom1);
			assertNoOverlapping(bom2);
		}
	}

}