package de.metas.pricing.conditions.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_DiscountSchema;
import org.junit.Ignore;

import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.util.Services;

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
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
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
		schema.setBreakValueType(X_M_DiscountSchema.BREAKVALUETYPE_Quantity);
		schema.setValidFrom(Timestamp.valueOf("2017-01-01 10:10:10.0"));
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
				.product(extractProductAndCategoryId(product))
				.qty(BigDecimal.valueOf(qty))
				.price(BigDecimal.valueOf(2)) // does not matter
				.build();
	}

	public static PricingConditionsBreakQuery createQueryForQtyAndAttributeValues(final I_M_Product product, final int qty, final AttributeListValue... attributeValues)
	{
		return PricingConditionsBreakQuery.builder()
				.product(extractProductAndCategoryId(product))
				.attributes(ImmutableAttributeSet.builder()
						.attributeValues(attributeValues)
						.build())
				.qty(BigDecimal.valueOf(qty))
				.price(BigDecimal.valueOf(2)) // does not matter
				.build();
	}

	private static ProductAndCategoryAndManufacturerId extractProductAndCategoryId(final I_M_Product product)
	{
		return ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), product.getM_Product_Category_ID(), product.getManufacturer_ID());
	}

	public static AttributeListValue createAttrValue(final I_M_Attribute attr, final String attrValueName)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		return attributesRepo.createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(AttributeId.ofRepoId(attr.getM_Attribute_ID()))
				.name(attrValueName)
				.build());
	}

	public static I_M_Attribute createAttr(final String attrName)
	{
		final I_M_Attribute attr = newInstance(I_M_Attribute.class);
		attr.setValue(attrName);
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
