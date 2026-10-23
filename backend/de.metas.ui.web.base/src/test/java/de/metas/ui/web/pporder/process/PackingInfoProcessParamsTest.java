package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.ui.web.pporder.PPOrderLineRow;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The start of the production receipt dialog derives the quantity per TU from the packing instruction; "TU Anzahl" is then the only
 * count that cannot be derived. The dialog does not let the user start with it empty, so this path is reached only by other callers.
 */
public class PackingInfoProcessParamsTest
{
	private static final BigDecimal QTY_PER_TU = new BigDecimal("15");

	private PackingInfoProcessParams params;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		final I_M_HU_PI_Item_Product fixedCapacityPIP = createPackingInstruction(uom, QTY_PER_TU);

		final I_M_HU_LUTU_Configuration defaultConfig = newInstance(I_M_HU_LUTU_Configuration.class);
		defaultConfig.setM_HU_PI_Item_Product_ID(fixedCapacityPIP.getM_HU_PI_Item_Product_ID());

		final IDocumentLUTUConfigurationManager configManager = Mockito.mock(IDocumentLUTUConfigurationManager.class);
		Mockito.when(configManager.getCreateLUTUConfiguration()).thenReturn(defaultConfig);

		params = PackingInfoProcessParams.builder()
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
