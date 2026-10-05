package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.order.OrderLineId;
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
	private static final OrderLineId ORDER_LINE_ID = OrderLineId.ofRepoId(50);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void filteredConditions_packingMaterialInTheSet_isIncluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(CARTON).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isTrue();
	}

	@Test
	public void filteredConditions_packingMaterialNotInTheSet_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(PFANDSTEIGE).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isFalse();
	}

	@Test
	public void filteredConditions_lineWithoutPackingInstruction_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(null).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isFalse();
	}

	@Test
	public void filteredConditions_withoutOrderLine_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(filter(CARTON).isIncluded(CONDITIONS_ID, null)).isFalse();
	}

	@Test
	public void filteredConditions_withoutAnyProvider_isExcluded()
	{
		createPackingOption(CONDITIONS_ID, CARTON);
		assertThat(new RefundPackagingFilter(Optional.empty()).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isFalse();
	}

	@Test
	public void unfilteredConditions_areIncluded_whateverThePackaging()
	{
		createPackingOption(ConditionsId.ofRepoId(11), CARTON); // the options of other conditions don't count

		assertThat(filter(null).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isTrue();
		assertThat(filter(PFANDSTEIGE).isIncluded(CONDITIONS_ID, null)).isTrue();
		assertThat(new RefundPackagingFilter(Optional.empty()).isIncluded(CONDITIONS_ID, ORDER_LINE_ID)).isTrue();
	}

	private static RefundPackagingFilter filter(@Nullable final Integer packingMaterialId)
	{
		final RefundPackagingMaterialProvider provider = orderLineId -> Optional.ofNullable(packingMaterialId);
		return new RefundPackagingFilter(Optional.of(ImmutableList.of(provider)));
	}

	private static void createPackingOption(@NonNull final ConditionsId conditionsId, final int packingMaterialId)
	{
		final I_C_Flatrate_RefundConfig_PackingOption option = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
		option.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
		option.setM_HU_PackingMaterial_ID(packingMaterialId);
		saveRecord(option);
	}
}
