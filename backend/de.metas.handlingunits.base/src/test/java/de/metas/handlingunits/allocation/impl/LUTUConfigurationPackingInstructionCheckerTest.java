package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The quantity per TU of an LU/TU configuration may be expressed in another UOM than the packing instruction's quantity;
 * the comparison must be done in the same UOM.
 */
public class LUTUConfigurationPackingInstructionCheckerTest
{
	private UomId piUom;
	private UomId configUom;
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(103);

	private LUTUConfigurationPackingInstructionChecker checker;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		piUom = createUom();
		configUom = createUom();

		final HUPIItemProduct piItemProduct = HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(1000))
				.name(TranslatableStrings.anyLanguage("pip"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(1001))
				.qtyCUsPerTU(Quantitys.of(new BigDecimal("2"), piUom))
				.build();
		final IHUPIItemProductBL huPIItemProductBL = Mockito.mock(IHUPIItemProductBL.class);
		Mockito.when(huPIItemProductBL.getById(piItemProduct.getId())).thenReturn(piItemProduct);
		Services.registerService(IHUPIItemProductBL.class, huPIItemProductBL);

		// 1 PI-UOM = 1000 config-UOM
		final IUOMConversionBL uomConversionBL = Mockito.mock(IUOMConversionBL.class);
		Mockito.when(uomConversionBL.convertQuantityTo(Mockito.any(Quantity.class), Mockito.any(ProductId.class), Mockito.eq(piUom)))
				.thenAnswer(invocation -> {
					final Quantity qty = invocation.getArgument(0);
					return Quantitys.of(qty.toBigDecimal().divide(new BigDecimal("1000")), piUom);
				});
		Services.registerService(IUOMConversionBL.class, uomConversionBL);

		checker = new LUTUConfigurationPackingInstructionChecker();
	}

	private static UomId createUom()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.saveRecord(uom);
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	private I_M_HU_LUTU_Configuration config(final String qtyCUsPerTUInConfigUOM)
	{
		final I_M_HU_LUTU_Configuration config = InterfaceWrapperHelper.newInstance(I_M_HU_LUTU_Configuration.class);
		config.setM_HU_PI_Item_Product_ID(1000);
		config.setM_Product_ID(PRODUCT_ID.getRepoId());
		config.setC_UOM_ID(configUom.getRepoId());
		config.setQtyCUsPerTU(new BigDecimal(qtyCUsPerTUInConfigUOM));
		config.setQtyTU(BigDecimal.ONE);
		return config;
	}

	@Test
	public void qtyInOtherUOM_equalToPackingQty_accepted()
	{
		assertThatCode(() -> checker.assertLUTUConfigurationMatchesPackingInstruction(config("2000"))).doesNotThrowAnyException();
	}

	@Test
	public void qtyInOtherUOM_differentFromPackingQty_refused()
	{
		assertThatThrownBy(() -> checker.assertLUTUConfigurationMatchesPackingInstruction(config("2500")))
				.isInstanceOf(AdempiereException.class);
	}
}
