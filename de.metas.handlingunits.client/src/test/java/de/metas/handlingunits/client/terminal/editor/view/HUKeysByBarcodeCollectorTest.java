package de.metas.handlingunits.client.terminal.editor.view;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;

public class HUKeysByBarcodeCollectorTest
{
	private Properties ctx;
	private PlainContextAware contextProvider;
	private AttributesTestHelper attributesTestHelper;

	@Before
	public void initialize()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();

		contextProvider = 	PlainContextAware.newOutOfTrx(ctx);
		attributesTestHelper = new AttributesTestHelper();
	}

	private I_M_HU createHU(final String barcode)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, contextProvider);
		// NOTE: matcher is matching the HU's Barcode by M_HU.Value
		hu.setValue(barcode);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	private I_M_HU_Attribute createHUAttribute(final I_M_HU hu, final I_M_Attribute attr, final String value, final BigDecimal valueNumber, final Timestamp valueDate)
	{
		final I_M_HU_Attribute hu_att = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class, contextProvider);

		hu_att.setM_Attribute_ID(attr.getM_Attribute_ID());
		hu_att.setM_HU(hu);
		hu_att.setValue(value);
		hu_att.setValueNumber(valueNumber);
		hu_att.setValueDate(valueDate);

		InterfaceWrapperHelper.save(hu_att);
		return hu_att;
	}

	private I_DIM_Dimension_Spec createDimensionSpec(final String internalName)
	{
		final I_DIM_Dimension_Spec dim = InterfaceWrapperHelper.newInstance(I_DIM_Dimension_Spec.class, contextProvider);
		dim.setInternalName(internalName);
		InterfaceWrapperHelper.save(dim);
		return dim;
	}

	private I_DIM_Dimension_Spec_Attribute createDimensionSpecAttribute(final I_DIM_Dimension_Spec dim, final I_M_Attribute attr)
	{
		final I_DIM_Dimension_Spec_Attribute dim_att = InterfaceWrapperHelper.newInstance(I_DIM_Dimension_Spec_Attribute.class, contextProvider);

		dim_att.setM_Attribute(attr);
		dim_att.setDIM_Dimension_Spec(dim);

		InterfaceWrapperHelper.save(dim_att);
		return dim_att;
	}

	private void assertBarcodeMatches(final boolean matchedExpected, final HUKeysByBarcodeCollector barcodeMatcher, final I_M_HU hu)
	{
		final boolean matchedActual = barcodeMatcher.match(hu);
		final String message = "Invalid matching result"
				+ "\nMatcher=" + barcodeMatcher
				+ "\nHU=" + hu
				+ "\n";
		Assert.assertEquals(message, matchedExpected, matchedActual);
	}

	@Test(expected = AdempiereException.class)
	public void init_Error_NullBarcode()
	{
		// shall throw exception because null barcode is not allowed
		new HUKeysByBarcodeCollector(ctx, null);
	}

	@Test(expected = AdempiereException.class)
	public void init_Error_EmptyBarcode()
	{
		// shall throw exception because empty barcode is not allowed
		new HUKeysByBarcodeCollector(ctx, "   ");
	}

	@Test
	public void matcher_Matched()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_Matched_BarcodeWithWhitespaces()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345     ");
		final I_M_HU hu = createHU("12345");

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_Matched_NotTopLevelHU()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		// Make our HU to be a not top level HU
		// i.e. setting some dummy value on M_HU_Item_Parent_ID
		hu.setM_HU_Item_Parent_ID(1000000);
		InterfaceWrapperHelper.save(hu);

		assertBarcodeMatches(true, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched_InactiveHU()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("12345");

		// Inactivate our HU

		// i.e. setting some dummy value on M_HU_Item_Parent_ID
		hu.setIsActive(false);
		InterfaceWrapperHelper.save(hu);

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU("11111");

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}

	@Test
	public void matcher_NotMatched_NullHUBarcode()
	{
		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "12345");
		final I_M_HU hu = createHU(null);

		assertBarcodeMatches(false, barcodeMatcher, hu);
	}

	@Test
	public void matcher_StringAttribute()
	{
		// barcode matches a string attribute
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute("String_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attr);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attr, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(true, barcodeMatcher, hu);

	}

	@Test
	public void matcher_StringAttribute_NotTopLevelHU()
	{

		// barcode matches a string attribute even on not top level hu
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute("String_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attr);

		final I_M_HU hu = createHU("12345");
		// Make our HU to be a not top level HU
		// i.e. setting some dummy value on M_HU_Item_Parent_ID
		hu.setM_HU_Item_Parent_ID(1000000);
		InterfaceWrapperHelper.save(hu);

		createHUAttribute(hu, attr, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(true, barcodeMatcher, hu);

	}

	@Test
	public void matcher_notMatched_StringAttribute()
	{
		// barcode has to match a string attribute
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute("String_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attr);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attr, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "Test321");
		assertBarcodeMatches(false, barcodeMatcher, hu);

	}

	@Test
	public void matcher_NonStringAttribute()
	{
		// barcode matches a string attribute only. Should not work for a number or date attribute
		final I_M_Attribute attrNumber = attributesTestHelper.createM_Attribute("Number_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_Number,
				true // isInstanceAttribute
		);
		final I_M_Attribute attrDate = attributesTestHelper.createM_Attribute("Date_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_Date,
				true // isInstanceAttribute
		);

		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attrNumber);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attrNumber, null, new BigDecimal("10"), null);
		createHUAttribute(hu, attrDate, null, null, Timestamp.valueOf("2017-01-01 00:00:00"));

		final HUKeysByBarcodeCollector barcodeMatcherNumber = new HUKeysByBarcodeCollector(ctx, "10");
		assertBarcodeMatches(false, barcodeMatcherNumber, hu);

		final HUKeysByBarcodeCollector barcodeMatcherDate = new HUKeysByBarcodeCollector(ctx, "2017-01-01 00:00:00");
		assertBarcodeMatches(false, barcodeMatcherDate, hu);

	}

	@Test
	public void matcher_incorrectDimensionType()
	{
		// barcode matches a string attribute
		// dimension spec must have a specific internal name!
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute("String_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final String dimBarcodeNonAttributesInternalName = HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeNonAttributesInternalName);

		createDimensionSpecAttribute(dim, attr);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attr, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(false, barcodeMatcher, hu);

	}

	@Test
	public void matcher_missingDimensionType()
	{
		// barcode matches a string attribute
		// dimension spec must exist and have a specific internal name!
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute("String_Attribute",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attr, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(false, barcodeMatcher, hu);

	}

	@Test
	public void matcher_multipleStringAttributes()
	{
		// barcode matches multiple string attributes and also HU's value
		final I_M_Attribute attr1 = attributesTestHelper.createM_Attribute("String_Attribute1",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);
		final I_M_Attribute attr2 = attributesTestHelper.createM_Attribute("String_Attribute2",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);
		final I_M_Attribute attr3 = attributesTestHelper.createM_Attribute("String_Attribute3",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);
		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attr1);
		createDimensionSpecAttribute(dim, attr2);
		createDimensionSpecAttribute(dim, attr3);

		final I_M_HU hu = createHU("12345");

		createHUAttribute(hu, attr1, "Test123", null, null);
		createHUAttribute(hu, attr2, "Test2", null, null);
		createHUAttribute(hu, attr3, "Test3", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher1 = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(true, barcodeMatcher1, hu);

		final HUKeysByBarcodeCollector barcodeMatcher2 = new HUKeysByBarcodeCollector(ctx, "Test2");
		assertBarcodeMatches(true, barcodeMatcher2, hu);

		final HUKeysByBarcodeCollector barcodeMatcher3 = new HUKeysByBarcodeCollector(ctx, "Test3");
		assertBarcodeMatches(true, barcodeMatcher3, hu);

		final HUKeysByBarcodeCollector barcodeMatcherHUVal = new HUKeysByBarcodeCollector(ctx, "12345");
		assertBarcodeMatches(true, barcodeMatcherHUVal, hu);

	}

	@Test
	public void matcher_multipleStringAttributes_multipleHUs()
	{
		// barcode matches string attributes and also HU's value, for multiple HUs
		final I_M_Attribute attr1 = attributesTestHelper.createM_Attribute("String_Attribute1",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);
		final I_M_Attribute attr2 = attributesTestHelper.createM_Attribute("String_Attribute2",
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true // isInstanceAttribute
		);

		final String dimBarcodeAttributesInternalName = HUConstants.DIM_Barcode_Attributes;
		final I_DIM_Dimension_Spec dim = createDimensionSpec(dimBarcodeAttributesInternalName);

		createDimensionSpecAttribute(dim, attr1);
		createDimensionSpecAttribute(dim, attr2);

		final I_M_HU hu1 = createHU("12345");
		final I_M_HU hu2 = createHU("23456");

		createHUAttribute(hu1, attr1, "Test123", null, null);
		createHUAttribute(hu2, attr2, "Test123", null, null);

		final HUKeysByBarcodeCollector barcodeMatcher1 = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(true, barcodeMatcher1, hu1);

		final HUKeysByBarcodeCollector barcodeMatcher2 = new HUKeysByBarcodeCollector(ctx, "Test123");
		assertBarcodeMatches(true, barcodeMatcher2, hu2);

		final HUKeysByBarcodeCollector barcodeMatcher3 = new HUKeysByBarcodeCollector(ctx, "12345");
		assertBarcodeMatches(true, barcodeMatcher3, hu1);

		final HUKeysByBarcodeCollector barcodeMatcherHUVal = new HUKeysByBarcodeCollector(ctx, "23456");
		assertBarcodeMatches(true, barcodeMatcherHUVal, hu2);

	}

}
