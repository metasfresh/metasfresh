package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/** The four weight rules of the label (moved unchanged from the former static {@code LabelQtyAndWeight.of}). */
class LabelQtyAndWeightServiceTest
{
	private static final BigDecimal LABEL_WEIGHT = new BigDecimal("12.345");

	private LabelQtyAndWeightService service;
	private I_C_UOM kg;
	private I_C_UOM each;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		service = new LabelQtyAndWeightService(Services.get(IProductBL.class), Services.get(IUOMDAO.class));
		kg = newUom(X12DE355.KILOGRAM, X_C_UOM.UOMTYPE_Weigth);
		each = newUom(X12DE355.EACH, X_C_UOM.UOMTYPE_Other);
	}

	@Test
	void kgProduct_labelWeightIsTheQuantity()
	{
		final LabelQtyAndWeight result = service.compute(newProduct("P-KG", kg), LABEL_WEIGHT);

		assertThat(result.getQty()).isEqualByComparingTo(LABEL_WEIGHT);
		assertThat(result.getStockUom()).isEqualTo(X12DE355.KILOGRAM);
		assertThat(result.getWeightKgForCU()).isNull();
		assertThat(result.isWeightNotUsed()).isFalse();
	}

	@Test
	void catchWeightProduct_quantityOne_weightForCU()
	{
		final ProductId productId = newProduct("P-CW", each);
		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.newInstance(I_C_UOM_Conversion.class);
		conversion.setM_Product_ID(productId.getRepoId());
		conversion.setC_UOM_ID(each.getC_UOM_ID());
		conversion.setC_UOM_To_ID(kg.getC_UOM_ID());
		conversion.setMultiplyRate(BigDecimal.TEN);
		conversion.setDivideRate(new BigDecimal("0.1"));
		conversion.setIsCatchUOMForProduct(true);
		conversion.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(conversion);

		final LabelQtyAndWeight result = service.compute(productId, LABEL_WEIGHT);

		assertThat(result.getQty()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(result.getWeightKgForCU()).isEqualByComparingTo(LABEL_WEIGHT);
		assertThat(result.isWeightNotUsed()).isFalse();
	}

	@Test
	void otherProduct_weightNotUsed()
	{
		final LabelQtyAndWeight result = service.compute(newProduct("P-PCE", each), LABEL_WEIGHT);

		assertThat(result.getQty()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(result.getWeightKgForCU()).isNull();
		assertThat(result.isWeightNotUsed()).isTrue();
	}

	@Test
	void noWeightOnLabel_quantityOne()
	{
		final LabelQtyAndWeight result = service.compute(newProduct("P-KG2", kg), null);

		assertThat(result.getQty()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(result.getWeightKgForCU()).isNull();
		assertThat(result.isWeightNotUsed()).isFalse();
	}

	private static I_C_UOM newUom(final X12DE355 code, final String uomType)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setX12DE355(code.getCode());
		uom.setName(code.getCode());
		uom.setUOMType(uomType);
		uom.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(uom);
		return uom;
	}

	private static ProductId newProduct(final String value, final I_C_UOM stockUom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setName(value);
		product.setC_UOM_ID(stockUom.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}
}
