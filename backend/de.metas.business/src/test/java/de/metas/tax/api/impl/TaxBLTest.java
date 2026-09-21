/*
 * #%L
 * de.metas.business
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

package de.metas.tax.api.impl;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.SOPOType;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxQuery;
import de.metas.tax.api.TypeOfDestCountry;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit coverage for {@link TaxBL#enforceExclusiveFlags(I_C_Tax)} — the at-most-one-of
 * {@code IsTaxExempt}/{@code IsReverseCharge}/{@code IsWholeTax} invariant plus the
 * {@code IsWholeTax} cascade (Rate=100, IsDocumentLevel=Y, other flags cleared).
 */
class TaxBLTest
{
	private TaxBL taxBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		taxBL = new TaxBL();
	}

	// ---------------------------------------------------------------------
	// Group A — Zero or one flag set (no conflict)
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("A. Zero or one flag set (no conflict)")
	class GroupA_NoConflict
	{
		@Test
		@DisplayName("A1: no flag set → untouched")
		void a1_noFlag()
		{
			final I_C_Tax tax = newTax();
			tax.setRate(BigDecimal.valueOf(19));
			save(tax);

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.isWholeTax()).isFalse();
			assertThat(tax.getRate()).isEqualByComparingTo("19");
		}

		@Test
		@DisplayName("A2: only IsTaxExempt=Y → untouched")
		void a2_onlyTaxExempt()
		{
			final I_C_Tax tax = newTax();
			tax.setIsTaxExempt(true);
			save(tax);

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isTrue();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("A3: only IsReverseCharge=Y → untouched")
		void a3_onlyReverseCharge()
		{
			final I_C_Tax tax = newTax();
			tax.setIsReverseCharge(true);
			save(tax);

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isTrue();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("A4: only IsWholeTax=Y on new record → cascade applied")
		void a4_onlyWholeTaxOnNew()
		{
			final I_C_Tax tax = newTax();
			tax.setRate(BigDecimal.valueOf(19));
			tax.setIsDocumentLevel(false);
			tax.setIsWholeTax(true);
			// no save — brand-new record, dirty flags carry through

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
		}
	}

	// ---------------------------------------------------------------------
	// Group B — Multi-flag conflict, exactly one flag changed (user edit wins)
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("B. Multi-flag conflict, one flag changed (user edit wins)")
	class GroupB_OneChanged
	{
		@Test
		@DisplayName("B1: existing IsTaxExempt=Y; user just set IsReverseCharge=Y → RC wins")
		void b1_rcJustChanged()
		{
			final I_C_Tax tax = newTax();
			tax.setIsTaxExempt(true);
			save(tax);

			tax.setIsReverseCharge(true); // user edit — only this flag is "changed"
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isTrue();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("B2: existing IsReverseCharge=Y; user just set IsTaxExempt=Y → TE wins")
		void b2_teJustChanged()
		{
			final I_C_Tax tax = newTax();
			tax.setIsReverseCharge(true);
			save(tax);

			tax.setIsTaxExempt(true);
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isTrue();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("B3: existing IsReverseCharge=Y; user just set IsWholeTax=Y → WholeTax wins + cascade")
		void b3_wholeTaxJustChanged()
		{
			final I_C_Tax tax = newTax();
			tax.setRate(BigDecimal.valueOf(19));
			tax.setIsReverseCharge(true);
			save(tax);

			tax.setIsWholeTax(true);
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
		}
	}

	// ---------------------------------------------------------------------
	// Group C — Multi-flag conflict, no flag changed (bad existing data)
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("C. Multi-flag conflict, no flag changed (static priority kicks in)")
	class GroupC_NoneChanged
	{
		@Test
		@DisplayName("C1: existing TE=Y + RC=Y; unrelated save → RC wins by priority")
		void c1_teAndRc_rcWins()
		{
			final I_C_Tax tax = newTaxBypassingInterceptor();
			tax.setIsTaxExempt(true);
			tax.setIsReverseCharge(true);
			save(tax); // flags now "clean" — not changed anymore

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isTrue();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("C2: existing TE=Y + WholeTax=Y; unrelated save → WholeTax wins + cascade")
		void c2_teAndWholeTax_wholeTaxWins()
		{
			final I_C_Tax tax = newTaxBypassingInterceptor();
			tax.setIsTaxExempt(true);
			tax.setIsWholeTax(true);
			save(tax);

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
		}

		@Test
		@DisplayName("C3: existing all-three=Y; unrelated save → WholeTax wins + cascade")
		void c3_allThree_wholeTaxWins()
		{
			final I_C_Tax tax = newTaxBypassingInterceptor();
			tax.setIsTaxExempt(true);
			tax.setIsReverseCharge(true);
			tax.setIsWholeTax(true);
			save(tax);

			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
		}
	}

	// ---------------------------------------------------------------------
	// Group D — Multi-flag conflict, multiple flags changed at once (rare)
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("D. Multi-flag conflict, multiple flags changed (priority fallback)")
	class GroupD_MultipleChanged
	{
		@Test
		@DisplayName("D1: TE and RC toggled to Y in the same patch → RC wins by priority")
		void d1_teAndRc_bothChanged()
		{
			final I_C_Tax tax = newTax();
			save(tax); // clean state — both flags N

			tax.setIsTaxExempt(true);
			tax.setIsReverseCharge(true);
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isTrue();
			assertThat(tax.isWholeTax()).isFalse();
		}

		@Test
		@DisplayName("D2: all three toggled to Y in the same patch → WholeTax wins + cascade")
		void d2_allThree_allChanged()
		{
			final I_C_Tax tax = newTax();
			tax.setRate(BigDecimal.valueOf(7));
			save(tax);

			tax.setIsTaxExempt(true);
			tax.setIsReverseCharge(true);
			tax.setIsWholeTax(true);
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
		}
	}

	// ---------------------------------------------------------------------
	// Group E — Cascade correctness on pre-existing state
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("E. Cascade correctness")
	class GroupE_Cascade
	{
		@Test
		@DisplayName("E1: IsWholeTax=Y toggled on an existing Rate=19/IsDocumentLevel=N tax → cascade overrides")
		void e1_cascadeOverridesPreviousRate()
		{
			final I_C_Tax tax = newTax();
			tax.setRate(BigDecimal.valueOf(19));
			tax.setIsDocumentLevel(false);
			save(tax);

			tax.setIsWholeTax(true);
			taxBL.enforceExclusiveFlags(tax);

			assertThat(tax.isWholeTax()).isTrue();
			assertThat(tax.getRate()).isEqualByComparingTo("100");
			assertThat(tax.isDocumentLevel()).isTrue();
			// explicit belt-and-braces checks on the non-winner flags
			assertThat(tax.isTaxExempt()).isFalse();
			assertThat(tax.isReverseCharge()).isFalse();
		}
	}

	// ---------------------------------------------------------------------
	// Group F — buildTaxQuery seam parity with getTaxNotNull
	// ---------------------------------------------------------------------
	@Nested
	@DisplayName("F. buildTaxQuery seam parity with getTaxNotNull")
	class BuildTaxQuery
	{
		private static final int COUNTRY_ID = 2000;
		private final OrgId orgId = OrgId.ofRepoId(20);
		private final WarehouseId warehouseId = WarehouseId.ofRepoId(200);
		private final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoId(42);

		private ITaxDAO taxDAO;
		private BPartnerLocationAndCaptureId shipBPartnerLocationId;

		@BeforeEach
		void buildTaxQueryFixture()
		{
			taxDAO = Services.get(ITaxDAO.class);

			final I_AD_Org org = newInstance(I_AD_Org.class);
			org.setAD_Org_ID(orgId.getRepoId());
			save(org);

			final I_C_Country country = newInstance(I_C_Country.class);
			country.setAD_Org_ID(orgId.getRepoId());
			country.setC_Country_ID(COUNTRY_ID);
			country.setCountryCode("BQ");
			save(country);

			final I_C_Location location = newInstance(I_C_Location.class);
			location.setAD_Org_ID(orgId.getRepoId());
			location.setC_Country_ID(COUNTRY_ID);
			save(location);

			// warehouse's own address — drives IWarehouseBL#getCountryId
			final I_C_BPartner warehouseBPartner = newInstance(I_C_BPartner.class);
			warehouseBPartner.setAD_Org_ID(orgId.getRepoId());
			save(warehouseBPartner);

			final I_C_BPartner_Location warehouseBPartnerLocation = newInstance(I_C_BPartner_Location.class);
			warehouseBPartnerLocation.setAD_Org_ID(orgId.getRepoId());
			warehouseBPartnerLocation.setC_BPartner_ID(warehouseBPartner.getC_BPartner_ID());
			warehouseBPartnerLocation.setC_Location_ID(location.getC_Location_ID());
			warehouseBPartnerLocation.setIsBillTo(true);
			save(warehouseBPartnerLocation);

			final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
			warehouse.setAD_Org_ID(orgId.getRepoId());
			warehouse.setM_Warehouse_ID(warehouseId.getRepoId());
			warehouse.setC_BPartner_ID(warehouseBPartner.getC_BPartner_ID());
			warehouse.setC_BPartner_Location_ID(warehouseBPartnerLocation.getC_BPartner_Location_ID());
			save(warehouse);

			// ship-to bpartner location — same country as the warehouse ⇒ domestic
			final I_C_BPartner shipBPartner = newInstance(I_C_BPartner.class);
			shipBPartner.setAD_Org_ID(orgId.getRepoId());
			save(shipBPartner);

			final I_C_BPartner_Location shipBPartnerLocation = newInstance(I_C_BPartner_Location.class);
			shipBPartnerLocation.setAD_Org_ID(orgId.getRepoId());
			shipBPartnerLocation.setC_BPartner_ID(shipBPartner.getC_BPartner_ID());
			shipBPartnerLocation.setC_Location_ID(location.getC_Location_ID());
			shipBPartnerLocation.setIsBillTo(true);
			save(shipBPartnerLocation);

			shipBPartnerLocationId = BPartnerLocationAndCaptureId.ofRepoId(
					shipBPartnerLocation.getC_BPartner_ID(),
					shipBPartnerLocation.getC_BPartner_Location_ID(),
					shipBPartnerLocation.getC_Location_ID());

			final I_C_Tax tax = newInstance(I_C_Tax.class);
			tax.setName("buildTaxQuery-parity");
			tax.setAD_Org_ID(orgId.getRepoId());
			tax.setC_Country_ID(COUNTRY_ID);
			tax.setTo_Country_ID(COUNTRY_ID);
			tax.setTypeOfDestCountry(TypeOfDestCountry.DOMESTIC.getCode());
			tax.setRequiresTaxCertificate(null);
			tax.setIsSmallbusiness(null);
			tax.setValidFrom(TimeUtil.addDays(new Date(), -1));
			tax.setSOPOType(SOPOType.BOTH.getCode());
			tax.setC_TaxCategory_ID(taxCategoryId.getRepoId());
			tax.setRate(BigDecimal.valueOf(19));
			save(tax);
		}

		@Test
		void buildTaxQuery_producesTheSameTaxAsGetTaxNotNull()
		{
			final Timestamp shipDate = TimeUtil.asTimestamp(new Date());

			final TaxQuery query = taxBL.buildTaxQuery(taxCategoryId, shipDate, orgId, warehouseId, shipBPartnerLocationId, SOTrx.SALES);
			final TaxId viaQuery = taxDAO.getBy(query).getTaxId();

			final TaxId viaEngine = taxBL.getTaxNotNull(null, taxCategoryId, 0, shipDate, orgId, warehouseId, shipBPartnerLocationId, SOTrx.SALES);

			assertThat(viaQuery).isEqualTo(viaEngine);
			assertThat(viaEngine).isNotEqualTo(TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND));
		}
	}

	// ---------------------------------------------------------------------
	// Helpers
	// ---------------------------------------------------------------------
	@NonNull
	private static I_C_Tax newTax()
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("tax-under-test-" + System.nanoTime());
		return tax;
	}

	/**
	 * Same as {@link #newTax()}; the name is kept for readability in Group-C tests where the
	 * tax is intentionally seeded with an invariant-violating flag combination to simulate
	 * bad existing data that somehow entered the DB without going through the interceptor.
	 * In unit tests, nothing actually runs the validator — so both helpers behave the same.
	 */
	@NonNull
	private static I_C_Tax newTaxBypassingInterceptor()
	{
		return newTax();
	}
}
