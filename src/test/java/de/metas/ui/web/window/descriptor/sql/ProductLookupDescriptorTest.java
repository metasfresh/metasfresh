package de.metas.ui.web.window.descriptor.sql;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeValue;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.Type;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

public class ProductLookupDescriptorTest
{
	private I_C_UOM uom;

	private I_M_AttributeValue attributeValue1;
	private I_M_AttributeValue attributeValue2;
	private I_M_AttributeValue attributeValue3;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = createUOM("uom1");
		attributeValue1 = createAttributeValue("attributeValue1");
		attributeValue2 = createAttributeValue("attributeValue2");
		attributeValue3 = createAttributeValue("attributeValue3");
	}

	private I_C_UOM createUOM(final String uomSymbol)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(uomSymbol);
		uom.setUOMSymbol(uomSymbol);
		saveRecord(uom);
		return uom;
	}

	private I_M_AttributeValue createAttributeValue(final String value)
	{
		final I_M_AttributeValue av = newInstanceOutOfTrx(I_M_AttributeValue.class);
		av.setName(value);
		av.setValue(value);
		saveRecord(av);
		return av;
	}

	private List<IdAndDisplayName> toIdAndDisplayNamesList(LookupValuesList list)
	{
		return list.getValues()
				.stream()
				.map(this::toIdAndDisplayName)
				.collect(ImmutableList.toImmutableList());
	}

	private IdAndDisplayName toIdAndDisplayName(final LookupValue lookupValue)
	{
		return IdAndDisplayName.of(lookupValue.getIdAsInt(), lookupValue.getDisplayName());
	}

	@Test
	public void explodeLookupValuesByAvailableStockGroups_standardScenario()
	{
		final LookupValuesList initialLookupValues = LookupValuesList.fromCollection(ImmutableList.of(
				IntegerLookupValue.of(1, "Product1"),
				IntegerLookupValue.of(2, "Product2"),
				IntegerLookupValue.of(3, "Product3"),
				IntegerLookupValue.of(4, "Product4")));

		final List<Group> availableStockGroups = ImmutableList.of(
				Group.builder()
						.productId(1)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue1))
						.qty(Quantity.of(100, uom))
						.build(),
				Group.builder()
						.productId(2)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue1))
						.qty(Quantity.of(0, uom))
						.build(),
				Group.builder()
						.productId(3)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue1))
						.qty(Quantity.of(1, uom))
						.build(),
				Group.builder()
						.productId(4)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue1))
						.qty(Quantity.of(-30, uom))
						.build());

		//
		// Test displaying only positive ATP
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */true);

			assertThat(toIdAndDisplayNamesList(result))
					.containsExactly(
							IdAndDisplayName.of(1, "Product1: 100 uom1 (attributeValue1)"),
							IdAndDisplayName.of(2, "Product2"),
							IdAndDisplayName.of(3, "Product3: 1 uom1 (attributeValue1)"),
							IdAndDisplayName.of(4, "Product4"));
		}

		//
		// Test displaying all ATPs (including zero or negative)
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */false);

			assertThat(toIdAndDisplayNamesList(result))
					.containsExactly(
							IdAndDisplayName.of(1, "Product1: 100 uom1 (attributeValue1)"),
							IdAndDisplayName.of(2, "Product2: 0 uom1 (attributeValue1)"),
							IdAndDisplayName.of(3, "Product3: 1 uom1 (attributeValue1)"),
							IdAndDisplayName.of(4, "Product4: -30 uom1 (attributeValue1)"));
		}
	}

	@Test
	public void explodeLookupValuesByAvailableStockGroups_multipleNegativeATPsForSameProduct()
	{
		final LookupValuesList initialLookupValues = LookupValuesList.fromCollection(ImmutableList.of(
				IntegerLookupValue.of(1, "Product1")));

		final List<Group> availableStockGroups = ImmutableList.of(
				Group.builder()
						.productId(1)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue1))
						.qty(Quantity.of(-1, uom))
						.build(),
				Group.builder()
						.productId(1)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue2))
						.qty(Quantity.of(-2, uom))
						.build(),
				Group.builder()
						.productId(1)
						.type(Type.ATTRIBUTE_SET)
						.attributeValues(ImmutableList.of(attributeValue3))
						.qty(Quantity.of(-3, uom))
						.build());

		//
		// Test displaying only positive ATP
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */true);

			assertThat(toIdAndDisplayNamesList(result))
					.containsExactly(IdAndDisplayName.of(1, "Product1"));
		}

		//
		// Test displaying all ATPs (including zero or negative)
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */false);

			assertThat(toIdAndDisplayNamesList(result))
					.containsExactly(
							IdAndDisplayName.of(1, "Product1: -1 uom1 (attributeValue1)"),
							IdAndDisplayName.of(1, "Product1: -2 uom1 (attributeValue2)"),
							IdAndDisplayName.of(1, "Product1: -3 uom1 (attributeValue3)"));
		}
	}

	@Value(staticConstructor = "of")
	private static class IdAndDisplayName
	{
		int id;
		String displayName;
	}
}
