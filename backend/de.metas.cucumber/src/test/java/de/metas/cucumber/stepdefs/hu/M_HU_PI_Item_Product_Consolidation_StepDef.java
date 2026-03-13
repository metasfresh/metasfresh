/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.hu;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.SQLValueStringResult;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for testing the {@code m_hu_pi_item_product_consolidate()} and
 * {@code m_hu_pi_item_product_consolidate_report()} SQL functions.
 *
 * <p>These tests require a running database (tagged {@code @ignore} in the feature file)
 * because the SQL functions operate directly on the DB tables.</p>
 *
 * <h3>Steps provided:</h3>
 * <ul>
 *   <li>{@code When M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean=... and p_consolidate=...}
 *       — Calls the consolidation SQL function</li>
 *   <li>{@code When M_HU_PI_Item_Product consolidation report is called}
 *       — Calls the report SQL function</li>
 *   <li>{@code Then M_HU_PI_Item_Product identified by '...' has GTIN='...' and EAN_TU=...}
 *       — Validates GTIN and EAN_TU fields after normalization</li>
 *   <li>{@code Then M_HU_PI_Item_Product identified by '...' has IsActive='...'}
 *       — Validates active/inactive state after consolidation</li>
 *   <li>{@code Then the surviving M_HU_PI_Item_Product for GTIN '...' has ValidFrom='...' and ValidTo='...'}
 *       — Validates date range on the surviving record</li>
 *   <li>{@code Then the consolidation report contains a conflict row for GTIN '...'}
 *       — Validates that the report function returns conflict rows for a given GTIN</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_HU_PI_Item_Product_Consolidation_StepDef
{
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;

	private List<ReportRow> lastReportResult;

	// -------------------------------------------------------------------
	// When steps
	// -------------------------------------------------------------------

	@When("M_HU_PI_Item_Product consolidation is called with p_normalize_gtin_ean={string} and p_consolidate={string}")
	public void consolidation_called(@NonNull final String normalizeGtinEanStr, @NonNull final String consolidateStr)
	{
		final boolean normalizeGtinEan = Boolean.parseBoolean(normalizeGtinEanStr);
		final boolean consolidate = Boolean.parseBoolean(consolidateStr);

		final String sql = "SELECT m_hu_pi_item_product_consolidate(?, ?, 0)";
		final SQLValueStringResult result = DB.getSQLValueStringWithWarningEx(
				ITrx.TRXNAME_None,
				sql,
				normalizeGtinEan,
				consolidate);
		assertThat(result).isNotNull();
	}

	@When("M_HU_PI_Item_Product consolidation report is called")
	public void consolidation_report_called()
	{
		lastReportResult = new ArrayList<>();
		final String sql = "SELECT * FROM m_hu_pi_item_product_consolidate_report(?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setString(1, "de_DE");
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				lastReportResult.add(ReportRow.builder()
						.issueType(rs.getString("issue_type"))
						.gtin(rs.getString("gtin"))
						.productValue(rs.getString("product_value"))
						.conflictingField(rs.getString("conflicting_field"))
						.build());
			}
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed to call m_hu_pi_item_product_consolidate_report", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	// -------------------------------------------------------------------
	// Then steps
	// -------------------------------------------------------------------

	@Then("M_HU_PI_Item_Product identified by {string} has GTIN={string} and EAN_TU=null")
	public void verify_gtin_and_ean_null(
			@NonNull final String identifier,
			@NonNull final String expectedGtin)
	{
		final I_M_HU_PI_Item_Product record = refreshRecord(identifier);
		assertThat(record.getGTIN()).as("GTIN of " + identifier).isEqualTo(expectedGtin);
		assertThat(record.getEAN_TU()).as("EAN_TU of " + identifier).isNull();
	}

	@Then("M_HU_PI_Item_Product identified by {string} has GTIN={string} and EAN_TU={string}")
	public void verify_gtin_and_ean(
			@NonNull final String identifier,
			@NonNull final String expectedGtin,
			@NonNull final String expectedEan)
	{
		final I_M_HU_PI_Item_Product record = refreshRecord(identifier);
		assertThat(record.getGTIN()).as("GTIN of " + identifier).isEqualTo(expectedGtin);
		assertThat(record.getEAN_TU()).as("EAN_TU of " + identifier).isEqualTo(expectedEan);
	}

	@Then("M_HU_PI_Item_Product identified by {string} has IsActive={string}")
	public void verify_is_active(
			@NonNull final String identifier,
			@NonNull final String expectedIsActive)
	{
		final I_M_HU_PI_Item_Product record = refreshRecord(identifier);
		final boolean expectedActive = "Y".equalsIgnoreCase(expectedIsActive);
		assertThat(record.isActive()).as("IsActive of " + identifier).isEqualTo(expectedActive);
	}

	@Then("the surviving M_HU_PI_Item_Product for GTIN {string} has ValidFrom={string} and ValidTo={string}")
	public void verify_survivor_date_range(
			@NonNull final String gtin,
			@NonNull final String expectedValidFromStr,
			@NonNull final String expectedValidToStr)
	{
		final LocalDate expectedValidFrom = LocalDate.parse(expectedValidFromStr);
		final LocalDate expectedValidTo = LocalDate.parse(expectedValidToStr);

		// Find the surviving (active) record for the given GTIN
		final String sql = "SELECT M_HU_PI_Item_Product_ID, ValidFrom, ValidTo"
				+ " FROM M_HU_PI_Item_Product"
				+ " WHERE GTIN = ? AND IsActive = 'Y'"
				+ " ORDER BY M_HU_PI_Item_Product_ID"
				+ " LIMIT 1";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setString(1, gtin);
			rs = pstmt.executeQuery();
			assertThat(rs.next()).as("Expected at least one active record for GTIN " + gtin).isTrue();

			final Timestamp validFrom = rs.getTimestamp("ValidFrom");
			final Timestamp validTo = rs.getTimestamp("ValidTo");

			assertThat(validFrom).as("ValidFrom for GTIN " + gtin).isNotNull();
			assertThat(validFrom.toLocalDateTime().toLocalDate()).isEqualTo(expectedValidFrom);

			assertThat(validTo).as("ValidTo for GTIN " + gtin).isNotNull();
			assertThat(validTo.toLocalDateTime().toLocalDate()).isEqualTo(expectedValidTo);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed to query survivor for GTIN " + gtin, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Then("the consolidation report contains a conflict row for GTIN {string}")
	public void verify_report_contains_conflict_for_gtin(@NonNull final String expectedGtin)
	{
		assertThat(lastReportResult)
				.as("Report should contain rows")
				.isNotEmpty();

		final boolean found = lastReportResult.stream()
				.anyMatch(row -> expectedGtin.equals(row.gtin));

		assertThat(found)
				.as("Report should contain a conflict row for GTIN " + expectedGtin)
				.isTrue();
	}

	// -------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------

	/**
	 * Refresh the record from the database to pick up changes made by the SQL function.
	 */
	private I_M_HU_PI_Item_Product refreshRecord(@NonNull final String identifier)
	{
		final I_M_HU_PI_Item_Product record = huPiItemProductTable.get(identifier);
		InterfaceWrapperHelper.refresh(record);
		return record;
	}

	@lombok.Value
	@lombok.Builder
	private static class ReportRow
	{
		String issueType;
		String gtin;
		String productValue;
		String conflictingField;
	}
}
