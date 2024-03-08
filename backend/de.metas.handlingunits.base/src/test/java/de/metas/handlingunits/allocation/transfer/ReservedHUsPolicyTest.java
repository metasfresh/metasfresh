package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		assertThat(ReservedHUsPolicy.CONSIDER_ALL.isConsiderVHU(createVHU(false))).isTrue();
		assertThat(ReservedHUsPolicy.CONSIDER_ALL.isConsiderVHU(createVHU(true))).isTrue();
	}

	@Test
	void considerOnlyNotReserved()
	{
		final I_M_HU vhu = createVHU(true);
		assertThat(ReservedHUsPolicy.CONSIDER_ONLY_NOT_RESERVED.isConsiderVHU(vhu)).isFalse();
	}

	@Nested
	class onlyNotReservedExceptVhuIds
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
			final I_M_HU vhu3 = createVHU(false);

			final ReservedHUsPolicy reservedVHUsPolicy = ReservedHUsPolicy.onlyNotReservedExceptVhuIds(ImmutableSet.of(HuId.ofRepoId(vhu1.getM_HU_ID())));
			assertThat(reservedVHUsPolicy.isConsiderVHU(vhu1)).isTrue();
			assertThat(reservedVHUsPolicy.isConsiderVHU(vhu2)).isFalse();
			assertThat(reservedVHUsPolicy.isConsiderVHU(vhu3)).isTrue();
		}
	}

	@Nested
	class onlyVHUIds
	{
		@Test
		void empty()
		{
			assertThatThrownBy(() -> ReservedHUsPolicy.onlyVHUIds(ImmutableSet.of()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("onlyVHUIds shall not be empty");
		}

		@Test
		void only_one_not_reserved_VHU()
		{
			final I_M_HU vhu1 = createVHU(false);
			final I_M_HU vhu2 = createVHU(true);

			final ReservedHUsPolicy policy = ReservedHUsPolicy.onlyVHUIds(ImmutableSet.of(HuId.ofRepoId(vhu1.getM_HU_ID())));
			assertThat(policy.isConsiderVHU(vhu1)).isTrue();
			assertThat(policy.isConsiderVHU(vhu2)).isFalse();
		}

		@Test
		void only_one_reserved_VHU()
		{
			final I_M_HU vhu1 = createVHU(true);
			final I_M_HU vhu2 = createVHU(false);

			final ReservedHUsPolicy policy = ReservedHUsPolicy.onlyVHUIds(ImmutableSet.of(HuId.ofRepoId(vhu1.getM_HU_ID())));
			assertThat(policy.isConsiderVHU(vhu1)).isTrue();
			assertThat(policy.isConsiderVHU(vhu2)).isFalse();
		}
	}
}