package org.adempiere.product.service.impl;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class ProductBLTest
{
	private IProductBL productBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		productBL = Services.get(IProductBL.class);
	}

	@Nested
	class getAttributeSetId
	{
		/**
		 * Verifies that we do not return the product's attribute set.
		 * The product's attribute set is there just for the product's ASI.
		 */
		@Test
		public void Product()
		{

			final I_M_AttributeSet as1 = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
			save(as1);

			final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product1.setM_AttributeSet_ID(as1.getM_AttributeSet_ID());
			save(product1);

			assertThat(productBL.getAttributeSetId(product1)).isEqualTo(AttributeSetId.NONE);
		}

		@Test
		public void ProductCategory()
		{
			final I_M_AttributeSet as1 = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
			save(as1);

			final I_M_Product_Category category1 = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
			category1.setM_AttributeSet(as1);
			save(category1);

			final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
			save(product1);

			assertThat(productBL.getAttributeSetId(product1).getRepoId()).isEqualTo(category1.getM_AttributeSet_ID());
		}

		/**
		 * Verifies that we do not fall back to the product's attribute set.
		 * The product's attribute set is there just for the product's ASI.
		 */
		@Test
		public void ProductCategory_And_Product()
		{
			final I_M_AttributeSet as1 = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
			save(as1);

			final I_M_AttributeSet as2 = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
			save(as2);

			final I_M_Product_Category category1 = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
			category1.setM_AttributeSet(as1);
			save(category1);

			final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product1.setM_AttributeSet_ID(as2.getM_AttributeSet_ID());
			product1.setM_Product_Category_ID(category1.getM_Product_Category_ID());
			save(product1);

			assertThat(productBL.getAttributeSetId(product1).getRepoId()).isEqualTo(category1.getM_AttributeSet_ID());
		}
	}

	@Nested
	class getCatchUOMId
	{
		private UomId productUOMId;
		private UomId weightUOMId1;
		private UomId weightUOMId2;
		private UomId weightUOMId3;
		private ProductId productId;

		@BeforeEach
		void beforeEach()
		{
			productUOMId = createUomId(100, UOMType.Other);
			productId = createProduct(productUOMId);

			weightUOMId1 = createUomId(201, UOMType.Weight);
			weightUOMId2 = createUomId(202, UOMType.Weight);
			weightUOMId3 = createUomId(203, UOMType.Weight);
		}

		private ProductId createProduct(final UomId uomId)
		{
			final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product.setC_UOM_ID(uomId.getRepoId());
			InterfaceWrapperHelper.saveRecord(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		UomId createUomId(int id, UOMType type)
		{
			final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
			uom.setC_UOM_ID(id);
			uom.setName("UOM-" + id);
			uom.setUOMSymbol("UOM-" + id);
			uom.setX12DE355("UOM-" + id);
			uom.setUOMType(type.getCode());
			InterfaceWrapperHelper.saveRecord(uom);

			return UomId.ofRepoId(uom.getC_UOM_ID());
		}

		void createUOMConversion(final UomId fromUomId, final UomId toUomId, boolean isCatchWeight)
		{
			I_C_UOM_Conversion record = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class);
			record.setM_Product_ID(productId.getRepoId());
			record.setC_UOM_ID(fromUomId.getRepoId());
			record.setC_UOM_To_ID(toUomId.getRepoId());
			record.setIsCatchUOMForProduct(isCatchWeight);
			record.setMultiplyRate(BigDecimal.ONE);
			record.setDivideRate(BigDecimal.ONE);
			InterfaceWrapperHelper.saveRecord(record);
		}

		@Test
		void noConversion()
		{
			assertThat(productBL.getCatchUOMId(productId)).isEmpty();
		}

		@Test
		void noCatchWeightConversion()
		{
			createUOMConversion(productUOMId, weightUOMId1, false);
			assertThat(productBL.getCatchUOMId(productId)).isEmpty();
		}

		@Test
		void singleCatchWeightConversion()
		{
			createUOMConversion(productUOMId, weightUOMId1, false); // noise
			createUOMConversion(productUOMId, weightUOMId2, true);
			createUOMConversion(productUOMId, weightUOMId3, false);// noise
			assertThat(productBL.getCatchUOMId(productId)).contains(weightUOMId2);
		}

		@Test
		void multipleCatchWeightConversions()
		{
			createUOMConversion(productUOMId, weightUOMId3, true);
			createUOMConversion(productUOMId, weightUOMId2, true);
			createUOMConversion(productUOMId, weightUOMId1, true);
			assertThat(productBL.getCatchUOMId(productId)).contains(weightUOMId1); // min C_UOM_ID is chosen
		}

	}
}
