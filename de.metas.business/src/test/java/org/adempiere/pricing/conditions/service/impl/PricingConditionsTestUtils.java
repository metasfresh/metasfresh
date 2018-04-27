package org.adempiere.pricing.conditions.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.pricing.conditions.PricingConditionsBreakQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_DiscountSchema;
import org.junit.Ignore;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.business
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

@Ignore
class PricingConditionsTestUtils
{
	public static I_M_Product createM_Product(final String value)
	{
		final I_M_Product_Category productCateogory = createM_ProductCategory(value + "-category");
		return createM_Product(value, productCateogory);
	}

	public static I_M_Product createM_Product(final String value, final I_M_Product_Category category)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(value);
		product.setM_Product_Category(category);
		save(product);
		return product;
	}

	public static I_M_Product_Category createM_ProductCategory(final String value)
	{
		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setValue(value);
		save(productCategory);
		return productCategory;
	}

	public static I_M_DiscountSchema createSchema()
	{
		final I_M_DiscountSchema schema = newInstance(I_M_DiscountSchema.class);
		schema.setIsQuantityBased(true);
		save(schema);
		return schema;
	}

	public static I_M_DiscountSchemaBreak createBreak(final I_M_DiscountSchema schema, final int seqNo)
	{
		schema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		save(schema);

		final I_M_DiscountSchemaBreak schemaBreak = newInstance(I_M_DiscountSchemaBreak.class);
		schemaBreak.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaBreak.setSeqNo(seqNo);
		schemaBreak.setIsValid(true);
		save(schemaBreak);
		return schemaBreak;
	}

	public static PricingConditionsBreakQuery createQueryForQty(final I_M_Product product, final int qty)
	{
		return PricingConditionsBreakQuery.builder()
				.productId(product.getM_Product_ID())
				.productCategoryId(product.getM_Product_Category_ID())
				.qty(BigDecimal.valueOf(qty))
				.amt(BigDecimal.valueOf(qty * 2)) // does not matter
				.build();
	}

	public static PricingConditionsBreakQuery createQueryForQtyAndAttributeValues(final I_M_Product product, final int qty, final I_M_AttributeValue... attributeValues)
	{
		final List<I_M_AttributeInstance> attributeInstances = Stream.of(attributeValues)
				.map(attributeValue -> createAttributeInstance(attributeValue.getM_Attribute(), attributeValue))
				.collect(ImmutableList.toImmutableList());

		return PricingConditionsBreakQuery.builder()
				.productId(product.getM_Product_ID())
				.productCategoryId(product.getM_Product_Category_ID())
				.attributeInstances(attributeInstances)
				.qty(BigDecimal.valueOf(qty))
				.amt(BigDecimal.valueOf(qty * 2)) // does not matter
				.build();
	}

	public static I_M_AttributeInstance createAttributeInstance(final I_M_Attribute attr, final I_M_AttributeValue attrValue)
	{
		final I_M_AttributeInstance attrInstance = newInstance(I_M_AttributeInstance.class);
		attrInstance.setM_Attribute(attr);
		attrInstance.setM_AttributeValue(attrValue);
		save(attrInstance);
		return attrInstance;
	}

	public static I_M_AttributeValue createAttrValue(final I_M_Attribute attr, final String attrValueName)
	{
		final I_M_AttributeValue attrValue = newInstance(I_M_AttributeValue.class);
		attrValue.setM_Attribute(attr);
		attrValue.setName(attrValueName);
		save(attrValue);
		return attrValue;
	}

	public static I_M_Attribute createAttr(final String attrName)
	{
		final I_M_Attribute attr = newInstance(I_M_Attribute.class);
		attr.setName(attrName);
		save(attr);
		return attr;
	}

	public static I_M_DiscountSchemaLine createLine(final I_M_DiscountSchema schema, final int seqNo)
	{
		final I_M_DiscountSchemaLine schemaLine = newInstance(I_M_DiscountSchemaLine.class);
		schemaLine.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		schemaLine.setSeqNo(seqNo);
		save(schemaLine);
		return schemaLine;
	}
}
