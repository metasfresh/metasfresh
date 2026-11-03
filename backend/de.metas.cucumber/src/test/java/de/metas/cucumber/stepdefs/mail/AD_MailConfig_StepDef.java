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

package de.metas.cucumber.stepdefs.mail;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;

/**
 * Step definitions for {@link I_AD_MailConfig} — routes a document mail to a mailbox.
 *
 * <p>The routing keys read by {@code MailboxRepository.fromRecord} / matched in
 * {@code MailWorkpackageProcessor} are: {@code AD_Client_ID}, {@code AD_Org_ID},
 * {@code AD_Process_ID} and {@code DocBaseType}/{@code DocSubType}. For a sales-invoice mail the
 * matching {@code DocBaseType} is {@code ARI}.
 */
@RequiredArgsConstructor
public class AD_MailConfig_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_MailConfig_StepDefData mailConfigTable;
	@NonNull private final AD_MailBox_StepDefData mailBoxTable;
	@NonNull private final AD_Org_StepDefData orgTable;

	/**
	 * Create / upsert an {@link I_AD_MailConfig} per data-table row, routing a document type to a mailbox.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_MailConfig_ID.Identifier</b> — (optional) alias for cross-step reference<br>
	 *   <b>AD_MailBox_ID.Identifier</b> — (required, identifier-ref) the target mailbox<br>
	 *   <b>DocBaseType</b> — (required) e.g. {@code ARI} for sales invoice<br>
	 *   <b>DocSubType</b> / <b>AD_Process_ID</b> / <b>CustomType</b> / <b>ColumnUserTo</b> — (optional) routing keys<br>
	 *   <b>AD_Org_ID</b> — (optional) org id; defaults to the login context<br>
	 * @cucumber.depends StepDefData: AD_MailBox_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains AD_MailConfig:
	 *   | AD_MailBox_ID.Identifier | DocBaseType |
	 *   | billingMailbox           | ARI         |
	 * </pre>
	 */
	@Given("metasfresh contains AD_MailConfig:")
	public void metasfresh_contains_AD_MailConfig(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrUpdateMailConfig);
	}

	/**
	 * Deactivates every active {@link I_AD_MailConfig} for the given {@code DocBaseType} that does NOT
	 * route to the given mailbox. Use in a feature's Background to guarantee mail routing for that
	 * document type resolves to the test's own mailbox, regardless of any seed/customer mail configs
	 * (e.g. pre-existing {@code ARI} configs pointing at an unreachable SMTP host).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: AD_MailBox_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given AD_MailConfig routing for DocBaseType "ARI" is restricted to mailbox "billingMailbox"
	 * </pre>
	 */
	@Given("AD_MailConfig routing for DocBaseType {string} is restricted to mailbox {string}")
	public void restrictMailConfigRouting(@NonNull final String docBaseType, @NonNull final String mailBoxIdentifier)
	{
		final I_AD_MailBox keepMailBox = mailBoxTable.get(mailBoxIdentifier);

		queryBL.createQueryBuilder(I_AD_MailConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_DocBaseType, docBaseType)
				.addNotEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_MailBox_ID, keepMailBox.getAD_MailBox_ID())
				.create()
				.list(I_AD_MailConfig.class)
				.forEach(mailConfig -> {
					mailConfig.setIsActive(false);
					InterfaceWrapperHelper.save(mailConfig);
				});
	}

	private void createOrUpdateMailConfig(@NonNull final DataTableRow row)
	{
		final I_AD_MailBox mailBox = mailBoxTable.get(row.getAsIdentifier(I_AD_MailConfig.COLUMNNAME_AD_MailBox_ID));

		final String docBaseType = row.getAsString(I_AD_MailConfig.COLUMNNAME_DocBaseType);

		final I_AD_MailConfig mailConfig = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_AD_MailConfig.class)
						.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_MailBox_ID, mailBox.getAD_MailBox_ID())
						.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_DocBaseType, docBaseType)
						.create()
						.firstOnlyOrNull(I_AD_MailConfig.class),
				() -> InterfaceWrapperHelper.newInstance(I_AD_MailConfig.class));

		mailConfig.setAD_MailBox_ID(mailBox.getAD_MailBox_ID());
		mailConfig.setDocBaseType(docBaseType);

		row.getAsOptionalString(I_AD_MailConfig.COLUMNNAME_DocSubType).ifPresent(mailConfig::setDocSubType);
		row.getAsOptionalInt(I_AD_MailConfig.COLUMNNAME_AD_Process_ID).ifPresent(mailConfig::setAD_Process_ID);
		row.getAsOptionalString(I_AD_MailConfig.COLUMNNAME_CustomType).ifPresent(mailConfig::setCustomType);
		row.getAsOptionalString(I_AD_MailConfig.COLUMNNAME_ColumnUserTo).ifPresent(mailConfig::setColumnUserTo);

		// AD_Org_ID may be given either as an AD_Org identifier (the `.Identifier`-suffixed column,
		// resolved via the org table) or as a raw int (the bare column). Keep these two paths distinct:
		// the org-table lookup requires a registered identifier and must not see a raw repo-id.
		final StepDefDataIdentifier orgIdentifier = row.getAsOptionalIdentifier(
				I_AD_MailConfig.COLUMNNAME_AD_Org_ID + "." + StepDefDataIdentifier.SUFFIX).orElse(null);
		if (orgIdentifier != null)
		{
			mailConfig.setAD_Org_ID(orgTable.getIdAsInt(orgIdentifier));
		}
		else
		{
			row.getAsOptionalInt(I_AD_MailConfig.COLUMNNAME_AD_Org_ID).ifPresent(mailConfig::setAD_Org_ID);
		}

		InterfaceWrapperHelper.save(mailConfig);

		row.getAsOptionalIdentifier(I_AD_MailConfig.COLUMNNAME_AD_MailConfig_ID)
				.ifPresent(identifier -> identifier.putOrReplace(mailConfigTable, mailConfig));
	}
}
