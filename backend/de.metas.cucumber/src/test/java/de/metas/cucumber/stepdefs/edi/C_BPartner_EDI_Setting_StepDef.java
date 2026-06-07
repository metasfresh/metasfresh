/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.edi;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.externalsystem.ExternalSystem_Config_StepDefData;
import de.metas.edi.api.EDISendingMode;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import javax.annotation.Nullable;

import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_C_BPartner_ID;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_C_BPartner_Location_ID;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDESADVDefaultItemCapacity;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDESADVSendingMode;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDesadvRecipientGLN;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiINVOICSendingMode;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_EdiInvoicRecipientGLN;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_IsEdiDesadvRecipient;
import static de.metas.esb.edi.model.I_C_BPartner_EDI_Setting.COLUMNNAME_IsEdiInvoicRecipient;

/**
 * Step definitions for creating and managing {@link I_C_BPartner_EDI_Setting} records.
 */
@RequiredArgsConstructor
public class C_BPartner_EDI_Setting_StepDef
{
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	@NonNull private final ExternalSystem_Config_StepDefData externalSystemConfigTable;
	@NonNull private final C_BPartner_EDI_Setting_StepDefData ediSettingTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates {@link I_C_BPartner_EDI_Setting} rows.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the business partner<br>
	 *   <b>C_BPartner_Location_ID</b> — (optional, identifier-ref) location; if absent the row applies to all locations<br>
	 *   <b>IsEdiDesadvRecipient</b> — (optional) Y/N, default N<br>
	 *   <b>EdiDesadvRecipientGLN</b> — (optional) GLN for DESADV recipient<br>
	 *   <b>EdiDESADVSendingMode</b> — (optional) sending mode code, default R (ReplicationInterface)<br>
	 *   <b>EdiDESADVDefaultItemCapacity</b> — (optional) default item capacity for DESADV<br>
	 *   <b>EdiDESADV_ExternalSystem_Config_ID</b> — (optional, identifier-ref) external system config for DESADV<br>
	 *   <b>IsEdiInvoicRecipient</b> — (optional) Y/N, default N<br>
	 *   <b>EdiInvoicRecipientGLN</b> — (optional) GLN for INVOIC recipient<br>
	 *   <b>EdiINVOICSendingMode</b> — (optional) sending mode code, default R (ReplicationInterface)<br>
	 *   <b>EdiINVOIC_ExternalSystem_Config_ID</b> — (optional, identifier-ref) external system config for INVOIC<br>
	 *   <b>Identifier</b> — (optional) alias for cross-step reference<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData, C_BPartner_Location_StepDefData, ExternalSystem_Config_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains C_BPartner_EDI_Setting:
	 *   | C_BPartner_ID | C_BPartner_Location_ID | IsEdiDesadvRecipient | EdiDesadvRecipientGLN | Identifier  |
	 *   | bpartner_1    | bploc_1                | Y                    | 1234567890123         | ediSetting1 |
	 * </pre>
	 */
	@Given("metasfresh contains C_BPartner_EDI_Setting:")
	public void metasfresh_contains_C_BPartner_EDI_Setting(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createEdiSetting);
	}

	private void createEdiSetting(@NonNull final DataTableRow row)
	{
		// Fix B: accept raw repo-IDs as well as registered step-def identifiers
		final I_C_BPartner bPartner = row.getAsIdentifier(COLUMNNAME_C_BPartner_ID)
				.lookupOrLoadById(bPartnerTable, id -> InterfaceWrapperHelper.loadOutOfTrx(id, I_C_BPartner.class));
		final int bPartnerId = bPartner.getC_BPartner_ID();

		final @Nullable StepDefDataIdentifier locationIdentifier = row.getAsOptionalIdentifier(COLUMNNAME_C_BPartner_Location_ID).orElse(null);
		final @Nullable Integer locationId;
		if (locationIdentifier != null)
		{
			final I_C_BPartner_Location location = locationIdentifier.lookupOrLoadById(
					bPartnerLocationTable,
					id -> InterfaceWrapperHelper.loadOutOfTrx(id, I_C_BPartner_Location.class));
			locationId = location != null ? location.getC_BPartner_Location_ID() : null;
		}
		else
		{
			locationId = null;
		}

		// Fix A: upsert — find existing row for (C_BPartner_ID, C_BPartner_Location_ID) to stay idempotent
		// across Background re-runs and pre-existing partner rows.
		final I_C_BPartner_EDI_Setting record = CoalesceUtil.coalesceSuppliers(
				() -> {
					final IQueryBuilder<I_C_BPartner_EDI_Setting> qb = queryBL
							.createQueryBuilder(I_C_BPartner_EDI_Setting.class)
							.addEqualsFilter(COLUMNNAME_C_BPartner_ID, bPartnerId);
					if (locationId != null)
					{
						qb.addEqualsFilter(COLUMNNAME_C_BPartner_Location_ID, locationId);
					}
					else
					{
						qb.addEqualsFilter(COLUMNNAME_C_BPartner_Location_ID, null);
					}
					return qb.create().firstOnlyOrNull(I_C_BPartner_EDI_Setting.class);
				},
				() -> {
					final I_C_BPartner_EDI_Setting newRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_EDI_Setting.class);
					newRecord.setC_BPartner_ID(bPartnerId);
					if (locationId != null)
					{
						newRecord.setC_BPartner_Location_ID(locationId);
					}
					return newRecord;
				});

		record.setIsEdiDesadvRecipient(row.getAsOptionalBoolean(COLUMNNAME_IsEdiDesadvRecipient).orElseFalse());
		row.getAsOptionalString(COLUMNNAME_EdiDesadvRecipientGLN).ifPresent(record::setEdiDesadvRecipientGLN);
		record.setEdiDESADVSendingMode(row.getAsOptionalEnum(COLUMNNAME_EdiDESADVSendingMode, EDISendingMode.class)
				.orElse(EDISendingMode.ReplicationInterface)
				.getCode());
		row.getAsOptionalBigDecimal(COLUMNNAME_EdiDESADVDefaultItemCapacity).ifPresent(record::setEdiDESADVDefaultItemCapacity);
		row.getAsOptionalIdentifier(COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID)
				.ifPresent(id -> record.setEdiDESADV_ExternalSystem_Config_ID(id.lookupNotNullIdIn(externalSystemConfigTable).getRepoId()));

		record.setIsEdiInvoicRecipient(row.getAsOptionalBoolean(COLUMNNAME_IsEdiInvoicRecipient).orElseFalse());
		row.getAsOptionalString(COLUMNNAME_EdiInvoicRecipientGLN).ifPresent(record::setEdiInvoicRecipientGLN);
		record.setEdiINVOICSendingMode(row.getAsOptionalEnum(COLUMNNAME_EdiINVOICSendingMode, EDISendingMode.class)
				.orElse(EDISendingMode.ReplicationInterface)
				.getCode());
		row.getAsOptionalIdentifier(COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID)
				.ifPresent(id -> record.setEdiINVOIC_ExternalSystem_Config_ID(id.lookupNotNullIdIn(externalSystemConfigTable).getRepoId()));

		InterfaceWrapperHelper.saveRecord(record);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> ediSettingTable.putOrReplace(identifier, record));
	}
}
