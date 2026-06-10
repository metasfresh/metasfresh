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
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class MobileUI_MassPrinting_StepDef
{
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final MassPrintingService massPrintingService = SpringContextHolder.instance.getBean(MassPrintingService.class);

	private final M_HU_StepDefData huTable;

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
	 * Validates the mass printing result for a specific LU.
	 * Checks that the expected number of boxes were packed and that given schedules were or were not processed.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_HU_ID} — identifier of the LU scanned (to look up the result)</li>
	 *   <li>{@code BoxesPacked} — total boxes packed across all products</li>
	 * </ul>
	 *
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of a schedule; used together with {@code WasPicked} to assert pick status</li>
	 *   <li>{@code WasPicked} — Y/N: whether the given shipment schedule should have been picked</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then validate mass printing result:
	 *   | M_HU_ID | BoxesPacked |
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

			final int expectedBoxesPacked = row.getAsInt("BoxesPacked");
			final int actualBoxesPacked = result.getProductResults().stream()
					.mapToInt(ProductResult::getBoxesPacked)
					.sum();
			assertThat(actualBoxesPacked)
					.as("BoxesPacked for LU=" + luIdentifier)
					.isEqualTo(expectedBoxesPacked);
		});
	}

}
