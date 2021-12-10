package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReservedHUsPolicyTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@SuppressWarnings("SameParameterValue")
	I_M_HU createVHU(final boolean reserved)
	{
		final I_M_HU vhu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		vhu.setIsReserved(reserved);
		InterfaceWrapperHelper.save(vhu);
		return vhu;
	}

	@Test
	void considerAll()
	{
		final I_M_HU vhu = createVHU(true);
		assertThat(ReservedHUsPolicy.CONSIDER_ALL.isConsiderVHU(vhu)).isTrue();
	}

	@Test
	void considerOnlyNotReserved()
	{
		final I_M_HU vhu = createVHU(true);
		assertThat(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED.isConsiderVHU(vhu)).isFalse();
	}

	@Nested
	class considerOnlyNotReservedExceptVhuIds
	{
		@Test
		void empty_except_list()
		{
			assertThat(ReservedHUsPolicy.onlyNotReservedExceptVhuIds(ImmutableSet.of())).isSameAs(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED);
		}

		@Test
		void except_one_vhu()
		{
			final I_M_HU vhu1 = createVHU(true);
			final I_M_HU vhu2 = createVHU(true);

			final ReservedHUsPolicy reservedVHUsPolicy = ReservedHUsPolicy.onlyNotReservedExceptVhuIds(ImmutableSet.of(HuId.ofRepoId(vhu1.getM_HU_ID())));
			assertThat(reservedVHUsPolicy.isConsiderVHU(vhu1)).isTrue();
			assertThat(reservedVHUsPolicy.isConsiderVHU(vhu2)).isFalse();
		}

	}
}