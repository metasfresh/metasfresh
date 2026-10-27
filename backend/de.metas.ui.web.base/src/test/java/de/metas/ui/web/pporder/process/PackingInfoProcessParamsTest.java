package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.allocation.impl.LUTUConfigurationPackingInstructionChecker;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The start of the production receipt dialog derives the quantity per TU from the packing instruction; "TU Anzahl" is then the only
 * count that cannot be derived. The dialog does not let the user start with TU Anzahl empty; the method still refuses it.
 */
public class PackingInfoProcessParamsTest
{
	private static final BigDecimal QTY_PER_TU = new BigDecimal("15");
	private static final BigDecimal ORDER_UOM_PER_PIP_UOM = new BigDecimal("6");
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(103);

	private PackingInfoProcessParams params;
	private I_M_HU_PI_Item_Product fixedCapacityPIP;
	private I_M_HU_LUTU_Configuration defaultConfig;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM pipUom = newInstance(I_C_UOM.class);
		saveRecord(pipUom);
		final I_C_UOM orderUom = newInstance(I_C_UOM.class);
		saveRecord(orderUom);

		final IUOMConversionBL uomConversionBL = Mockito.mock(IUOMConversionBL.class);
		Mockito.when(uomConversionBL.convertQuantityTo(Mockito.any(Quantity.class), Mockito.eq(PRODUCT_ID), Mockito.eq(UomId.ofRepoId(orderUom.getC_UOM_ID()))))
				.thenAnswer(invocation -> {
					final Quantity qty = invocation.getArgument(0);
					return Quantitys.of(qty.toBigDecimal().multiply(ORDER_UOM_PER_PIP_UOM), UomId.ofRepoId(orderUom.getC_UOM_ID()));
				});
		Services.registerService(IUOMConversionBL.class, uomConversionBL);

		fixedCapacityPIP = createPackingInstruction(pipUom, QTY_PER_TU);

		// like the order's default configuration: expressed in the order's UOM
		defaultConfig = newInstance(I_M_HU_LUTU_Configuration.class);
		defaultConfig.setM_HU_PI_Item_Product_ID(fixedCapacityPIP.getM_HU_PI_Item_Product_ID());
		defaultConfig.setM_Product_ID(PRODUCT_ID.getRepoId());
		defaultConfig.setC_UOM_ID(orderUom.getC_UOM_ID());
		defaultConfig.setQtyTU(new BigDecimal("7"));

		final IDocumentLUTUConfigurationManager configManager = Mockito.mock(IDocumentLUTUConfigurationManager.class);
		Mockito.when(configManager.getCreateLUTUConfiguration()).thenReturn(defaultConfig);

		params = PackingInfoProcessParams.builder()
				.packingInstructionChecker(new LUTUConfigurationPackingInstructionChecker())
				.defaultLUTUConfigManager(configManager)
				.selectedRow(Mockito.mock(PPOrderLineRow.class))
				.build();
		params.setTU_HU_PI_Item_Product_ID(fixedCapacityPIP.getM_HU_PI_Item_Product_ID());
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = "0")
	public void fixedCapacity_withoutPositiveTUCount_isRefused(@Nullable final String qtyTU)
	{
		params.setQtyTU(qtyTU != null ? new BigDecimal(qtyTU) : null);

		assertThatThrownBy(() -> params.createAndSaveNewLUTUConfig())
				.isInstanceOf(FillMandatoryException.class)
				.hasMessageContaining(PackingInfoProcessParams.PARAM_QtyTU);
	}

	/**
	 * The packing instruction's quantity is in its own UOM (15 PIP-UOM); the configuration (and "Menge") is in the order's UOM (1 PIP-UOM = 6 order UOM).
	 */
	@Test
	public void fixedCapacity_packingInstructionInOtherUOMThanOrder_isConvertedIntoOrderUOM()
	{
		params.setQtyTU(new BigDecimal("4"));

		final I_M_HU_LUTU_Configuration config = params.createAndSaveNewLUTUConfig();

		assertThat(config.getQtyCUsPerTU()).isEqualByComparingTo("90"); // 15 x 6, not 15
		assertThat(config.getC_UOM_ID()).isEqualTo(defaultConfig.getC_UOM_ID());
		assertThat(config.getQtyTU()).isEqualByComparingTo("4");

		assertThat((BigDecimal)params.getParameterDefaultValue(PackingInfoProcessParams.PARAM_QtyTotalReceived)).isEqualByComparingTo("630"); // 90 x 7
	}

	@Test
	public void moreTUsThanThePalletHolds_isRefusedWithoutStoringAConfiguration()
	{
		params.setLuPiItemId(createPallet(new BigDecimal("4")).getM_HU_PI_Item_ID());
		params.setQtyLU(new BigDecimal("2"));
		params.setQtyTU(new BigDecimal("5"));
		final int configurationsBefore = countLUTUConfigurations();

		assertThatThrownBy(() -> params.createAndSaveNewLUTUConfig())
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.getErrorCode()).isEqualTo("M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity"));
		assertThat(countLUTUConfigurations()).isEqualTo(configurationsBefore);
	}

	private static int countLUTUConfigurations()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_LUTU_Configuration.class).create().count();
	}

	private I_M_HU_PI_Item createPallet(final BigDecimal tuCapacity)
	{
		final I_M_HU_PI palletPI = newInstance(I_M_HU_PI.class);
		palletPI.setName("Pallet");
		saveRecord(palletPI);

		final I_M_HU_PI_Version palletPIVersion = newInstance(I_M_HU_PI_Version.class);
		palletPIVersion.setM_HU_PI_ID(palletPI.getM_HU_PI_ID());
		palletPIVersion.setIsCurrent(true);
		saveRecord(palletPIVersion);

		final I_M_HU_PI_Item palletItem = newInstance(I_M_HU_PI_Item.class);
		palletItem.setM_HU_PI_Version_ID(palletPIVersion.getM_HU_PI_Version_ID());
		palletItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit);
		palletItem.setIncluded_HU_PI_ID(fixedCapacityPIP.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI_ID());
		palletItem.setQty(tuCapacity);
		saveRecord(palletItem);
		return palletItem;
	}

	private static I_M_HU_PI_Item_Product createPackingInstruction(final I_C_UOM uom, final BigDecimal qtyPerTU)
	{
		final I_M_HU_PI huPI = newInstance(I_M_HU_PI.class);
		huPI.setName("TU");
		saveRecord(huPI);

		final I_M_HU_PI_Version huPIVersion = newInstance(I_M_HU_PI_Version.class);
		huPIVersion.setM_HU_PI_ID(huPI.getM_HU_PI_ID());
		huPIVersion.setIsCurrent(true);
		saveRecord(huPIVersion);

		final I_M_HU_PI_Item huPIItem = newInstance(I_M_HU_PI_Item.class);
		huPIItem.setM_HU_PI_Version_ID(huPIVersion.getM_HU_PI_Version_ID());
		huPIItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(huPIItem);

		final I_M_HU_PI_Item_Product piip = newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_HU_PI_Item_ID(huPIItem.getM_HU_PI_Item_ID());
		piip.setIsAllowAnyProduct(true);
		piip.setIsInfiniteCapacity(false);
		piip.setQty(qtyPerTU);
		piip.setC_UOM_ID(uom.getC_UOM_ID());
		piip.setValidFrom(TimeUtil.asTimestamp(LocalDate.of(2026, 1, 1).atStartOfDay(ZoneId.of("UTC"))));
		saveRecord(piip);
		return piip;
	}
}
