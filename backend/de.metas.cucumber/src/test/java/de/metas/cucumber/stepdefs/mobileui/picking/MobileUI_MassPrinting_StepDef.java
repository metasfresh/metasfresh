/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.mobileui.picking;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingResult.ProductResult;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingScanRequest;
import de.metas.handlingunits.picking.job.massprinting.MassPrintingService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for the MobileUI mass-printing flow.
 *
 * <p>Triggers a mass-printing LU scan and asserts the result.
 * Results are stored by LU identifier and can be asserted after scanning.
 *
 * @see MassPrintingService
 */
@RequiredArgsConstructor
public class MobileUI_MassPrinting_StepDef
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final MassPrintingService massPrintingService = SpringContextHolder.instance.getBean(MassPrintingService.class);

	@NonNull private final M_HU_StepDefData huTable;

	/** last scan result per LU identifier */
	private final Map<String, MassPrintingResult> resultsByLuIdentifier = new HashMap<>();

	/**
	 * Triggers a mass-printing scan for the given LU as the specified user.
	 * Stores the result for later assertion via {@link #validateMassPrintingResult(DataTable)}.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_HU_ID} — identifier of the LU to scan</li>
	 *   <li>{@code Login} — login of the user performing the scan (resolves to picker ID)</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And mass printing scan:
	 *   | M_HU_ID | Login      |
	 *   | lu      | metasfresh |
	 * </pre>
	 */
	@And("mass printing scan:")
	public void massPrintingScan(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final HuId luId = huTable.getId(row.getAsIdentifier("M_HU_ID"));
			final String login = row.getAsString(I_AD_User.COLUMNNAME_Login);
			final UserId pickerId = userDAO.retrieveUserIdByLogin(login);
			assertThat(pickerId).as("User exists for login=" + login).isNotNull();

			final MassPrintingResult result = massPrintingService.scan(
					MassPrintingScanRequest.builder()
							.luId(luId)
							.pickerId(pickerId)
							.build());

			resultsByLuIdentifier.put(row.getAsIdentifier("M_HU_ID").getAsString(), result);
		});
	}

	/**
	 * Triggers a mass-printing scan that is expected to be rejected, and asserts the rejection carries the
	 * given {@code AD_Message}. Used for the case where the scanned LU is located outside the picking-warehouse
	 * group of the picker's workplace, which the scan must refuse rather than silently pack nothing.
	 *
	 * <p>The expected message is the {@code AD_Message} value (key). The thrown {@link AdempiereException}'s
	 * resolved text is matched against the message resolved via {@link IMsgBL}, so the assertion is robust to
	 * the actual translation in the DB.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_HU_ID} — identifier of the LU to scan</li>
	 *   <li>{@code Login} — login of the user performing the scan (resolves to picker ID)</li>
	 *   <li>{@code AD_Message} — the AD_Message value (key) the rejection must carry</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then mass printing scan is rejected:
	 *   | M_HU_ID | Login      | AD_Message                                                              |
	 *   | lu      | metasfresh | de.metas.handlingunits.picking.massprinting.LUNotInWorkplacePickingGroup |
	 * </pre>
	 */
	@Then("mass printing scan is rejected:")
	public void massPrintingScanIsRejected(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final HuId luId = huTable.getId(row.getAsIdentifier("M_HU_ID"));
			final String login = row.getAsString(I_AD_User.COLUMNNAME_Login);
			final UserId pickerId = userDAO.retrieveUserIdByLogin(login);
			assertThat(pickerId).as("User exists for login=" + login).isNotNull();

			final AdMessageKey expectedMessage = AdMessageKey.of(row.getAsString("AD_Message"));
			final String expectedMessageText = msgBL.getMsg(Env.getCtx(), expectedMessage);

			assertThatThrownBy(() -> massPrintingService.scan(
					MassPrintingScanRequest.builder()
							.luId(luId)
							.pickerId(pickerId)
							.build()))
					.as("Mass printing scan for LU=%s located outside the workplace's picking group must be rejected with AD_Message %s",
							row.getAsIdentifier("M_HU_ID").getAsString(), expectedMessage.toAD_Message())
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(expectedMessageText);
		});
	}

	/**
	 * Validates the mass printing result for a specific LU.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_HU_ID} — identifier of the LU scanned (to look up the result)</li>
	 *   <li>{@code UnitsPacked} — total units packed across all products (in product UOM)</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then validate mass printing result:
	 *   | M_HU_ID | UnitsPacked |
	 *   | lu      | 4           |
	 * </pre>
	 */
	@Then("validate mass printing result:")
	public void validateMassPrintingResult(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String luIdentifier = row.getAsIdentifier("M_HU_ID").getAsString();
			final MassPrintingResult result = resultsByLuIdentifier.get(luIdentifier);
			assertThat(result).as("Mass printing result must exist for LU=" + luIdentifier + ". Was 'mass printing scan:' called?").isNotNull();

			final int expectedUnitsPacked = row.getAsInt("UnitsPacked");
			final int actualUnitsPacked = result.getProductResults().stream()
					.mapToInt(ProductResult::getUnitsPacked)
					.sum();
			assertThat(actualUnitsPacked)
					.as("UnitsPacked for LU=" + luIdentifier)
					.isEqualTo(expectedUnitsPacked);
		});
	}

}
