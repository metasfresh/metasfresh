package de.metas.handlingunits.inout.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.PackageDimensions;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class HUPackingMaterialDAOTest
{
	private HUPackingMaterialDAO huPackingMaterialDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huPackingMaterialDAO = new HUPackingMaterialDAO();
	}

	/**
	 * A packing material with no dimension UOM (C_UOM_Dimension_ID = 0) has unknown, optional dimensions.
	 * {@code retrievePackageDimensions} must return UNSPECIFIED — resolving UomId.ofRepoId(0) would throw
	 * "C_UOM_ID > 0 but it was 0" and abort package creation, leaving the Transportauftrag without lines.
	 */
	@Test
	void retrievePackageDimensions_noDimensionUom_returnsUnspecified()
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		packingMaterial.setName("no dimension UOM");
		// C_UOM_Dimension_ID intentionally left at 0 (many packing materials have no dimension UOM set).
		saveRecord(packingMaterial);

		// toUomId is irrelevant here — the guard returns UNSPECIFIED before it is read.
		final PackageDimensions dimensions = huPackingMaterialDAO.retrievePackageDimensions(packingMaterial, UomId.ofRepoId(1));

		assertThat(dimensions).isEqualTo(PackageDimensions.UNSPECIFIED);
	}
}
