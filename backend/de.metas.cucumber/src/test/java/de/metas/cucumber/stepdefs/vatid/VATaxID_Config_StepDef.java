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

package de.metas.cucumber.stepdefs.vatid;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDOnServiceUnavailableAction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_VATaxID_Config;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;

/**
 * Sets up the {@code VATaxID_Config} record of the test organisation.
 *
 * <p>Deliberately an <b>upsert</b>, not a plain insert: the table allows only one active record per
 * organisation (partial unique index), and the local cucumber database is not reset between runs, so a
 * second run of the same feature would otherwise violate that index.
 */
public class VATaxID_Config_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Upserts the organisation's VAT-ID configuration. An UPSERT rather than an insert on purpose: one
	 * active row per organisation is enforced by a partial unique index, and the local cucumber DB is
	 * never reset between runs, so a second run would otherwise collide on that index.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>IsFormatCheckEnabled</b>     — (required) run the offline format + check-digit validation<br>
	 *   <b>IsVIESCheckEnabled</b>       — (required) run the online check<br>
	 *   <b>RecheckAfterDays</b>         — (required) how long a result stays good, i.e. the de-duplication window<br>
	 *   <b>OnServiceUnavailable</b>     — (required) {@code ServiceUnavailable} (fail open) or {@code Invalid} (fail closed)<br>
	 *   <b>RestApiBaseURL</b>           — (optional) unused while the online checker is stubbed<br>
	 *   <b>RequesterMemberStateCode</b> — (optional) our own VAT-ID's country<br>
	 *   <b>RequesterNumber</b>          — (optional) our own VAT-ID's number
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains VATaxID_Config:
	 *   | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
	 *   | true                 | true               | 30               | ServiceUnavailable   |
	 * </pre>
	 */
	@Given("metasfresh contains VATaxID_Config:")
	public void metasfresh_contains_VATaxID_Config(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();

		final I_VATaxID_Config existingRecord = queryBL
				.createQueryBuilder(I_VATaxID_Config.class)
				.addEqualsFilter(I_VATaxID_Config.COLUMNNAME_AD_Org_ID, ORG_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_VATaxID_Config.class);

		final I_VATaxID_Config record = existingRecord != null
				? existingRecord
				: InterfaceWrapperHelper.newInstance(I_VATaxID_Config.class);

		record.setAD_Org_ID(ORG_ID.getRepoId());
		record.setIsActive(true);
		record.setIsFormatCheckEnabled(row.getAsBoolean(I_VATaxID_Config.COLUMNNAME_IsFormatCheckEnabled));
		record.setIsVIESCheckEnabled(row.getAsBoolean(I_VATaxID_Config.COLUMNNAME_IsVIESCheckEnabled));
		record.setRecheckAfterDays(row.getAsInt(I_VATaxID_Config.COLUMNNAME_RecheckAfterDays));
		record.setOnServiceUnavailable(row
				.getAsEnum(I_VATaxID_Config.COLUMNNAME_OnServiceUnavailable, VATaxIDOnServiceUnavailableAction.class)
				.getCode());

		record.setRestApiBaseURL(row.getAsOptionalString(I_VATaxID_Config.COLUMNNAME_RestApiBaseURL).orElse(null));
		record.setRequesterMemberStateCode(row.getAsOptionalString(I_VATaxID_Config.COLUMNNAME_RequesterMemberStateCode).orElse(null));
		record.setRequesterNumber(row.getAsOptionalString(I_VATaxID_Config.COLUMNNAME_RequesterNumber).orElse(null));

		InterfaceWrapperHelper.saveRecord(record);
	}
}
