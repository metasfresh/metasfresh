package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.handlingunits.HUPIItemProductId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class RefundPackagingFilterTest
{
	private static final ConditionsId CONDITIONS_ID = ConditionsId.ofRepoId(10);
	private static final int CARTON = 101;
	private static final int PFANDSTEIGE = 102;
	private static final HUPIItemProductId PI_ITEM_PRODUCT_ID = HUPIItemProductId.ofRepoId(50);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void filteredConditions_packingMaterialInTheSet_isIncluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(CARTON).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isTrue();
	}

	@Test
	public void filteredConditions_packingMaterialNotInTheSet_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(PFANDSTEIGE).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isFalse();
	}

	@Test
	public void filteredConditions_lineWithoutPackingInstruction_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(null).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isFalse();
	}

	@Test
	public void filteredConditions_withoutPackingInstructionId_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(CARTON).isIncluded(CONDITIONS_ID, null, null)).isFalse();
	}

	@Test
	public void filteredConditions_withoutAnyProvider_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(new RefundPackagingFilter(Optional.empty()).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isFalse();
	}

	@Test
	public void unfilteredConditions_areIncluded_whateverThePackaging()
	{
		createPackingOption(ConditionsId.ofRepoId(11), CARTON); // the options of other conditions don't count
		createConfig(CONDITIONS_ID, false);

		assertThat(filter(null).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isTrue();
		assertThat(filter(PFANDSTEIGE).isIncluded(CONDITIONS_ID, null, null)).isTrue();
		assertThat(new RefundPackagingFilter(Optional.empty()).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isTrue();
	}

	private static RefundPackagingFilter filter(@Nullable final Integer packingMaterialId)
	{
		final RefundPackagingMaterialProvider provider = (piItemProductId, bpartnerId) -> Optional.ofNullable(packingMaterialId);
		return new RefundPackagingFilter(Optional.of(ImmutableList.of(provider)));
	}

	/** a config of the conditions that is restricted to packaging options (or not), with the given options */
	private static void createPackingOption(@NonNull final ConditionsId conditionsId, final int packingMaterialId)
	{
		final I_C_Flatrate_RefundConfig config = createConfig(conditionsId, true);
		createPackingOption(config, packingMaterialId);
	}

	private static I_C_Flatrate_RefundConfig createConfig(@NonNull final ConditionsId conditionsId, final boolean isPackingOptionFiltered)
	{
		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		config.setIsPackingOptionFiltered(isPackingOptionFiltered);
		saveRecord(config);
		return config;
	}

	private static void createPackingOption(@NonNull final I_C_Flatrate_RefundConfig config, final int packingMaterialId)
	{
		final I_C_Flatrate_RefundConfig_PackingOption option = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
		option.setC_Flatrate_Conditions_ID(config.getC_Flatrate_Conditions_ID());
		option.setC_Flatrate_RefundConfig_ID(config.getC_Flatrate_RefundConfig_ID());
		option.setM_HU_PackingMaterial_ID(packingMaterialId);
		saveRecord(option);
	}

	/** the options only count if the config says that it is restricted to them */
	@Test
	public void optionsOfAnUnrestrictedConfig_areIgnored()
	{
		createPackingOption(createConfig(CONDITIONS_ID, false), CARTON);
		assertThat(filter(PFANDSTEIGE).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isTrue();
	}

	/** flagged, but no option listed: an empty set means all packaging */
	@Test
	public void restrictedConfigWithoutOptions_includesEveryLine()
	{
		createConfig(CONDITIONS_ID, true);
		assertThat(filter(CARTON).isIncluded(CONDITIONS_ID, PI_ITEM_PRODUCT_ID, null)).isTrue();
		assertThat(filter(null).isIncluded(CONDITIONS_ID, null, null)).isTrue();
	}
}
