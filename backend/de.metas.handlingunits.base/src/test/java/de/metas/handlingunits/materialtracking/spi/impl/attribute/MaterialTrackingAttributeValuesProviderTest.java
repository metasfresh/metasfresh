package de.metas.handlingunits.materialtracking.spi.impl.attribute;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.NonNull;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.NamePair;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MaterialTrackingAttributeValuesProviderTest
{
	private MaterialTrackingAttributeValuesProvider materialTrackingAttributeValuesProvider;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_M_Attribute attributeRecord = newInstance(I_M_Attribute.class);
		attributeRecord.setValue(I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID);
		saveRecord(attributeRecord);

		materialTrackingAttributeValuesProvider = new MaterialTrackingAttributeValuesProvider(attributeRecord);
	}

	@Test
	public void getAvailableValues_two_materialTrackings()
	{
		final ProductId productId1 = ProductId.ofRepoId(10);
		final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(20);
		final MaterialTrackingId materialTrackingId1 = createMaterialTrackingRecord(productId1, bpartnerId1);

		// create another MaterialTracking with different product and partner
		final ProductId productId2 = ProductId.ofRepoId(30);
		final BPartnerId bpartnerId2 = BPartnerId.ofRepoId(40);
		final MaterialTrackingId materialTrackingId2 = createMaterialTrackingRecord(productId2, bpartnerId2);

		final Evaluatee evaluatee = createEvaluatee(productId1, bpartnerId1, materialTrackingId2);

		// invoke the method under test
		final List<? extends NamePair> result = materialTrackingAttributeValuesProvider.getAvailableValues(evaluatee);

		// the result shall contain both the material tracking that matches the evaluatee's product and partner *and* the one with materialTrackingId2.
		assertThat(result).hasSize(2);

		final ImmutableMap<String, ? extends NamePair> resultAsMap = Maps.uniqueIndex(result, NamePair::getID);

		final NamePair namePair1 = resultAsMap.get(asValueNameKey(materialTrackingId1));
		assertThat(namePair1.getID()).isEqualTo(asValueNameKey(materialTrackingId1));
		assertThat(namePair1.getName()).isEqualTo(createLot(productId1, bpartnerId1));

		final NamePair namePair2 = resultAsMap.get(asValueNameKey(materialTrackingId2));
		assertThat(namePair2.getID()).isEqualTo(asValueNameKey(materialTrackingId2));
		assertThat(namePair2.getName()).isEqualTo(createLot(productId2, bpartnerId2));
	}

	@Test
	public void getAvailableValues_one_materialTracking()
	{
		final ProductId productId1 = ProductId.ofRepoId(10);
		final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(20);
		final MaterialTrackingId materialTrackingId1 = createMaterialTrackingRecord(productId1, bpartnerId1);
		final Evaluatee evaluatee = createEvaluatee(productId1, bpartnerId1, materialTrackingId1);

		// invoke the method under test
		final List<? extends NamePair> result = materialTrackingAttributeValuesProvider.getAvailableValues(evaluatee);

		// the method under test can obtain the same material tracking wither via it's ID or via it's partner and product;
		// still there shall be just one result
		assertThat(result).hasSize(1);

		final ImmutableMap<String, ? extends NamePair> resultAsMap = Maps.uniqueIndex(result, NamePair::getID);

		final NamePair namePair1 = resultAsMap.get(asValueNameKey(materialTrackingId1));
		assertThat(namePair1.getID()).isEqualTo(asValueNameKey(materialTrackingId1));
		assertThat(namePair1.getName()).isEqualTo(createLot(productId1, bpartnerId1));
	}


	@Test
	public void getAvailableValue()
	{
		final ProductId productId1 = ProductId.ofRepoId(10);
		final BPartnerId bpartnerId1 = BPartnerId.ofRepoId(20);
		final MaterialTrackingId materialTrackingId1 = createMaterialTrackingRecord(productId1, bpartnerId1);

		// create another MaterialTracking with different product and partner
		final ProductId productId2 = ProductId.ofRepoId(30);
		final BPartnerId bpartnerId2 = BPartnerId.ofRepoId(40);
		final MaterialTrackingId materialTrackingId2 = createMaterialTrackingRecord(productId2, bpartnerId2);

		final Evaluatee evaluatee = createEvaluatee(productId1, bpartnerId1, materialTrackingId2);

		// invoke the method under test
		final NamePair resultForId1 = materialTrackingAttributeValuesProvider.getAttributeValueOrNull(evaluatee, materialTrackingId1.getRepoId());

		// invoke the method under test
		final NamePair resultForId2 = materialTrackingAttributeValuesProvider.getAttributeValueOrNull(evaluatee, materialTrackingId2.getRepoId());

		assertThat(resultForId1.getID()).isEqualTo(asValueNameKey(materialTrackingId1));
		assertThat(resultForId1.getName()).isEqualTo(createLot(productId1, bpartnerId1));

		assertThat(resultForId2.getID()).isEqualTo(asValueNameKey(materialTrackingId2));
		assertThat(resultForId2.getName()).isEqualTo(createLot(productId2, bpartnerId2));
	}

	private Evaluatee createEvaluatee(
			final ProductId productId,
			final BPartnerId bpartnerId,
			final MaterialTrackingId materialTrackingId)
	{
		final Evaluatee evaluatee = Evaluatees.mapBuilder()
				// .put(MaterialTrackingAttributeValuesProvider.CTXNAME_M_HU_ID, idOrMinusOne(huId))
				.put(MaterialTrackingAttributeValuesProvider.CTXNAME_C_BPartner_ID, BPartnerId.toRepoId(bpartnerId))
				.put(MaterialTrackingAttributeValuesProvider.CTXNAME_M_Product_ID, ProductId.toRepoId(productId))
				.put(MaterialTrackingAttributeValuesProvider.CTXNAME_M_Material_Tracking, MaterialTrackingId.toRepoId(materialTrackingId))
				.build();
		return evaluatee;
	}

	private MaterialTrackingId createMaterialTrackingRecord(
			@NonNull final ProductId productId,
			@NonNull final BPartnerId bpartnerId)
	{

		final String lot = createLot(productId, bpartnerId);

		final I_M_Material_Tracking materialTrackingRecord = newInstance(I_M_Material_Tracking.class);
		materialTrackingRecord.setM_Product_ID(productId.getRepoId());
		materialTrackingRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		materialTrackingRecord.setLot(lot);
		materialTrackingRecord.setProcessed(false);
		saveRecord(materialTrackingRecord);

		final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoId(materialTrackingRecord.getM_Material_Tracking_ID());
		return materialTrackingId;
	}

	private String createLot(
			@NonNull final ProductId productId,
			@NonNull final BPartnerId bpartnerId)
	{
		return productId.toString() + "_" + bpartnerId.toString();
	}

	private String asValueNameKey(@NonNull final MaterialTrackingId materialTrackingId)
	{
		return Integer.toString(materialTrackingId.getRepoId());
	}

}
