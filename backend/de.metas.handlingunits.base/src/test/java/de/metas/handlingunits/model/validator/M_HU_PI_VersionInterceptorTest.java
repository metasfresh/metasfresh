package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.product.PackageDimensionCalcMethod;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for the {@link M_HU_PI_Version} interceptor guard:
 * {@code PackageDimensionCalcMethod} may only be set on TU pi-versions.
 */
class M_HU_PI_VersionInterceptorTest
{
	private M_HU_PI_Version interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new M_HU_PI_Version();
	}

	@Nested
	class RejectCalcMethod
	{
		/** LU pi-version with a calc method set must throw. */
		@Test
		void whenHuUnitTypeIsLU()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(HuUnitType.LU.getCode());
			piVersion.setPackageDimensionCalcMethod(PackageDimensionCalcMethod.Strapping.getCode());
			saveRecord(piVersion);

			assertThatThrownBy(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("M_HU_PI_Version_CalcMethodOnlyOnTU");
		}

		/** VHU pi-version with a calc method set must throw — VHU is not TU. */
		@Test
		void whenHuUnitTypeIsVHU()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(HuUnitType.VHU.getCode());
			piVersion.setPackageDimensionCalcMethod(PackageDimensionCalcMethod.Strapping.getCode());
			saveRecord(piVersion);

			assertThatThrownBy(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("M_HU_PI_Version_CalcMethodOnlyOnTU");
		}

		/** Null HU_UnitType with a calc method set must throw — null unit type is not TU. */
		@Test
		void whenHuUnitTypeIsNull()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(null);
			piVersion.setPackageDimensionCalcMethod(PackageDimensionCalcMethod.Strapping.getCode());
			saveRecord(piVersion);

			assertThatThrownBy(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("M_HU_PI_Version_CalcMethodOnlyOnTU");
		}

		/**
		 * Changing HU_UnitType from TU to LU while calc method remains set must throw —
		 * the guard watches HU_UnitType changes too (escape-path coverage).
		 */
		@Test
		void whenHuUnitTypeChangedFromTUToLUWithCalcMethodStillSet()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(HuUnitType.LU.getCode());
			piVersion.setPackageDimensionCalcMethod(PackageDimensionCalcMethod.Strapping.getCode());
			saveRecord(piVersion);

			assertThatThrownBy(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("M_HU_PI_Version_CalcMethodOnlyOnTU");
		}
	}

	@Nested
	class AllowCalcMethod
	{
		/** TU pi-version with a calc method set must be accepted without error. */
		@Test
		void whenHuUnitTypeIsTU()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(HuUnitType.TU.getCode());
			piVersion.setPackageDimensionCalcMethod(PackageDimensionCalcMethod.Strapping.getCode());
			saveRecord(piVersion);

			assertThatCode(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.doesNotThrowAnyException();
		}

		/** Non-TU pi-version with no calc method set must be accepted (null calc method is OK). */
		@Test
		void whenCalcMethodIsNull()
		{
			final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
			piVersion.setHU_UnitType(HuUnitType.LU.getCode());
			piVersion.setPackageDimensionCalcMethod(null);
			saveRecord(piVersion);

			assertThatCode(() -> interceptor.rejectCalcMethodOnNonTUVersions(piVersion))
					.doesNotThrowAnyException();
		}
	}
}
