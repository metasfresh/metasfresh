package org.adempiere.product.service.impl;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Product;
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

	private static void createBPProduct(final ProductId product1Id, final String ean13ProductCode)
	{
		final I_C_BPartner_Product bpProduct1 = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpProduct1.setM_Product_ID(product1Id.getRepoId());
		bpProduct1.setC_BPartner_ID(1);
		bpProduct1.setEAN13_ProductCode(ean13ProductCode);
		save(bpProduct1);
	}

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		productBL = Services.get(IProductBL.class);
	}

	private I_M_Product createProductWithEAN13(final String value, final String ean13ProductCode)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setEAN13_ProductCode(ean13ProductCode);
		save(product);
		return product;
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

		void createUOMConversion(final UomId fromUomId, final UomId toUomId, final boolean isCatchWeight)
		{
			final I_C_UOM_Conversion record = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class);
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

	@Nested
	class getProductIdByEAN13ProductCode
	{
		@Test
		void ean13CodeFromProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			final I_M_Product product2 = createProductWithEAN13("101506", "4889");
			final ProductId product2Id = ProductId.ofRepoId(product2.getM_Product_ID());

			assertThat(productBL.getProductIdByEAN13ProductCode("4888", clientId)).contains(product1Id);
			assertThat(productBL.getProductIdByEAN13ProductCode("4889", clientId)).contains(product2Id);
		}

		@Test
		void ean13CodeFromBPartnerProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			final I_M_Product product2 = createProductWithEAN13("101506", "4889");
			final ProductId product2Id = ProductId.ofRepoId(product2.getM_Product_ID());

			createBPProduct(product1Id, "1234");
			createBPProduct(product2Id, "4321");

			assertThat(productBL.getProductIdByEAN13ProductCode("1234", clientId)).contains(product1Id);
			assertThat(productBL.getProductIdByEAN13ProductCode("4321", clientId)).contains(product2Id);
		}

		@Test
		void ean13CodeDoesNotFit()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			final I_M_Product product2 = createProductWithEAN13("101506", "4889");
			final ProductId product2Id = ProductId.ofRepoId(product2.getM_Product_ID());

			createBPProduct(product1Id, "1234");
			createBPProduct(product2Id, "4321");

			assertThat(productBL.getProductIdByEAN13ProductCode("48882", clientId)).isEmpty();
			assertThat(productBL.getProductIdByEAN13ProductCode("10150", clientId)).isEmpty();
		}

		@Test
		void ean13CodeDuplicateInProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			createProductWithEAN13("101506", "4888");

			assertThat(productBL.getProductIdByEAN13ProductCode("4888", clientId)).isEmpty();
		}

		@Test
		void ean13CodeDuplicateInBPartnerProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			final I_M_Product product2 = createProductWithEAN13("101506", "4889");
			final ProductId product2Id = ProductId.ofRepoId(product2.getM_Product_ID());

			createBPProduct(product1Id, "9999");
			createBPProduct(product2Id, "9999");

			assertThat(productBL.getProductIdByEAN13ProductCode("9999", clientId)).isEmpty();
		}

		@Test
		void ean13CodeDuplicateInProductAndBPartnerProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());
			final I_M_Product product2 = createProductWithEAN13("101506", "4889");
			ProductId.ofRepoId(product2.getM_Product_ID());

			createBPProduct(product1Id, "4889");

			assertThat(productBL.getProductIdByEAN13ProductCode("4889", clientId)).isEmpty();
		}

		@Test
		void ean13CodeSameInBPartnerProductAndProduct()
		{
			final I_M_Product product1 = createProductWithEAN13("101505", "4888");
			final ProductId product1Id = ProductId.ofRepoId(product1.getM_Product_ID());
			final ClientId clientId = ClientId.ofRepoId(product1.getAD_Client_ID());

			createBPProduct(product1Id, "4888");

			assertThat(productBL.getProductIdByEAN13ProductCode("4888", clientId)).contains(product1Id);
		}
	}

}
