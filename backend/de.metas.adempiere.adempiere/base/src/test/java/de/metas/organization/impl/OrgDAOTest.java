package de.metas.organization.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.X_AD_OrgInfo;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import de.metas.image.AdImageId;
import de.metas.organization.OrgId;
import de.metas.organization.OrgImagesMap;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.organization.impl.OrgDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

class OrgDAOTest
{

	private OrgDAO orgDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgDAO = new OrgDAO();
	}

	@Test
	void retrieveOrgIdBy_non_existing_fail()
	{
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(true)
				.orgValue("orgValue")
				.build();

		assertThrows(OrgIdNotFoundException.class, () -> orgDAO.retrieveOrgIdBy(query));
	}

	@Test
	void retrieveOrgIdBy_non_existing_dont_fail()
	{
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(false)
				.orgValue("orgValue")
				.build();

		final Optional<OrgId> result = orgDAO.retrieveOrgIdBy(query);
		assertThat(result).isEmpty();
	}

	@Test
	void toOrgInfo_imagesMap_contains_all_image_columns()
	{
		final I_AD_OrgInfo record = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
		record.setAD_Org_ID(1000000);
		record.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern);
		record.setLogo_ID(101);
		record.setReportBottom_Logo_ID(102);
		record.setReportBackground_Image_ID(103);
		InterfaceWrapperHelper.saveRecord(record);

		final OrgImagesMap imagesMap = OrgDAO.toOrgInfo(record).getImagesMap();

		assertThat(imagesMap.getLogoId()).contains(AdImageId.ofRepoId(101));
		assertThat(imagesMap.getImageId(I_AD_OrgInfo.COLUMNNAME_ReportBottom_Logo_ID)).contains(AdImageId.ofRepoId(102));
		assertThat(imagesMap.getImageId(I_AD_OrgInfo.COLUMNNAME_ReportBackground_Image_ID)).contains(AdImageId.ofRepoId(103));
	}

	@Nested
	class getOrgWarehouseIdIfExists
	{
		private void orgInfo(final int orgRepoId, final int warehouseRepoId)
		{
			final I_AD_OrgInfo record = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
			record.setAD_Org_ID(orgRepoId);
			record.setStoreCreditCardData(X_AD_OrgInfo.STORECREDITCARDDATA_NichtSpeichern);
			record.setM_Warehouse_ID(warehouseRepoId);
			InterfaceWrapperHelper.saveRecord(record);
		}

		@Test
		void orgInfoWithWarehouse_returnsIt()
		{
			orgInfo(1000000, 555);

			assertThat(orgDAO.getOrgWarehouseIdIfExists(OrgId.ofRepoId(1000000))).contains(WarehouseId.ofRepoId(555));
		}

		@Test
		void orgInfoWithoutWarehouse_isEmpty()
		{
			orgInfo(1000000, -1);

			assertThat(orgDAO.getOrgWarehouseIdIfExists(OrgId.ofRepoId(1000000))).isEmpty();
		}

		@Test
		void orgWithoutOrgInfo_isEmpty_notAnException()
		{
			assertThat(orgDAO.getOrgWarehouseIdIfExists(OrgId.ofRepoId(1000001))).isEmpty();
		}

		@Test
		void anyOrg_isEmpty()
		{
			assertThat(orgDAO.getOrgWarehouseIdIfExists(OrgId.ANY)).isEmpty();
		}
	}

}
