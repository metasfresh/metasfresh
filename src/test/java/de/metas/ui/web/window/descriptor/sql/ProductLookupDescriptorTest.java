package de.metas.ui.web.window.descriptor.sql;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.Type;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
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
	private final String adLanguage = "en_US";
	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = createUOM("uom1");
	}

	private I_C_UOM createUOM(final String uomSymbol)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(uomSymbol);
		uom.setUOMSymbol(uomSymbol);
		saveRecord(uom);
		return uom;
	}

	private AttributeId createAttribute(final String attributeKey)
	{
		final I_M_Attribute attribute = newInstanceOutOfTrx(I_M_Attribute.class);
		attribute.setValue(attributeKey);
		attribute.setName(attributeKey + "-name");
		saveRecord(attribute);
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	private AttributeListValue createAttributeValue(final AttributeId attributeId, final String attributeValue)
	{
		return Services.get(IAttributeDAO.class).createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(attributeId)
				.value(attributeValue)
				.name(attributeValue)
				.build());
	}

	private static List<IdAndDisplayName> toIdAndDisplayNamesList(final LookupValuesList list)
	{
		return list.getValues()
				.stream()
				.map(lookupValue -> IdAndDisplayName.of(lookupValue.getIdAsInt(), lookupValue.getDisplayName()))
				.collect(ImmutableList.toImmutableList());
	}

	public static ImmutableAttributeSet toAttributeSet(final AttributeListValue attributeValue)
	{
		return ImmutableAttributeSet.builder()
				.attributeValues(attributeValue)
				.build();
	}

	@Test
	public void explodeLookupValuesByAvailableStockGroups_standardScenario()
	{
		final AttributeId attributeId = createAttribute("attribute");
		final AttributeListValue attributeValue1 = createAttributeValue(attributeId, "attributeValue1");

		final LookupValuesList initialLookupValues = LookupValuesList.fromCollection(ImmutableList.of(
				IntegerLookupValue.of(1, "Product1"),
				IntegerLookupValue.of(2, "Product2"),
				IntegerLookupValue.of(3, "Product3"),
				IntegerLookupValue.of(4, "Product4")));

		final List<Group> availableStockGroups = ImmutableList.of(
				Group.builder()
						.productId(ProductId.ofRepoId(1))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue1))
						.qty(Quantity.of(100, uom))
						.build(),
				Group.builder()
						.productId(ProductId.ofRepoId(2))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue1))
						.qty(Quantity.of(0, uom))
						.build(),
				Group.builder()
						.productId(ProductId.ofRepoId(3))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue1))
						.qty(Quantity.of(1, uom))
						.build(),
				Group.builder()
						.productId(ProductId.ofRepoId(4))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue1))
						.qty(Quantity.of(-30, uom))
						.build());

		//
		// Test displaying only positive ATP
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */true,
					adLanguage);

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
					/* displayATPOnlyIfPositive */false,
					adLanguage);

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
		final AttributeId attributeId = createAttribute("attribute");
		final AttributeListValue attributeValue1 = createAttributeValue(attributeId, "attributeValue1");
		final AttributeListValue attributeValue2 = createAttributeValue(attributeId, "attributeValue2");
		final AttributeListValue attributeValue3 = createAttributeValue(attributeId, "attributeValue3");

		final LookupValuesList initialLookupValues = LookupValuesList.fromCollection(ImmutableList.of(
				IntegerLookupValue.of(1, "Product1")));

		final List<Group> availableStockGroups = ImmutableList.of(
				Group.builder()
						.productId(ProductId.ofRepoId(1))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue1))
						.qty(Quantity.of(-1, uom))
						.build(),
				Group.builder()
						.productId(ProductId.ofRepoId(1))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue2))
						.qty(Quantity.of(-2, uom))
						.build(),
				Group.builder()
						.productId(ProductId.ofRepoId(1))
						.type(Type.ATTRIBUTE_SET)
						.attributes(toAttributeSet(attributeValue3))
						.qty(Quantity.of(-3, uom))
						.build());

		//
		// Test displaying only positive ATP
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */true,
					adLanguage);

			assertThat(toIdAndDisplayNamesList(result))
					.containsExactly(IdAndDisplayName.of(1, "Product1"));
		}

		//
		// Test displaying all ATPs (including zero or negative)
		{
			final LookupValuesList result = ProductLookupDescriptor.explodeLookupValuesByAvailableStockGroups(
					initialLookupValues,
					availableStockGroups,
					/* displayATPOnlyIfPositive */false,
					adLanguage);

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
