package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.ATTRIBUTE_SET_INSTANCE_ID;
import static de.metas.material.event.EventTestHelper.BEFORE_NOW;
import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Stock_v;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AvailableToPromiseRepositoryTest
{
	private static final AttributesKey STORAGE_ATTRIBUTES_KEY = AttributesKey.ofAttributeValueIds(1, 2);

	@Rule
	public AdempiereTestWatcher adempiereTestWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private MaterialDescriptor createMaterialDescriptor()
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				PRODUCT_ID,
				STORAGE_ATTRIBUTES_KEY,
				ATTRIBUTE_SET_INSTANCE_ID);
		final MaterialDescriptor materialDescriptor = EventTestHelper
				.createMaterialDescriptor()
				.withProductDescriptor(productDescriptor);
		return materialDescriptor;
	}

	@Test
	public void retrieveAvailableStock_for_material_descriptor()
	{
		createStockRecord(BPARTNER_ID);
		createStockRecord(0); // belongs to "any" bpartner
		createStockRecord(BPARTNER_ID + 10); // belongs to an unrelated bPartner

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();

		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery.forDescriptorAndAllPossibleBPartnerIds(materialDescriptor);

		final BigDecimal result = new AvailableToPromiseRepository().retrieveAvailableStockQtySum(query);
		assertThat(result).isEqualByComparingTo("20");
	}

	private I_MD_Candidate_Stock_v createStockRecord(int bPartnerId)
	{
		// set only the values we need
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(new Timestamp(BEFORE_NOW.getTime()));
		candidateRecord.setIsActive(true);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		save(candidateRecord);

		final I_MD_Candidate_Stock_v viewRecord = newInstance(I_MD_Candidate_Stock_v.class);
		viewRecord.setM_Product_ID(PRODUCT_ID);
		viewRecord.setM_Warehouse_ID(WAREHOUSE_ID);
		viewRecord.setC_BPartner_ID(bPartnerId);
		viewRecord.setDateProjected(new Timestamp(BEFORE_NOW.getTime()));
		viewRecord.setStorageAttributesKey(STORAGE_ATTRIBUTES_KEY.getAsString());
		viewRecord.setQty(BigDecimal.TEN);
		save(viewRecord);

		return viewRecord;
	}
}
