/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatus;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.bpartner.blockstatus.BlockStatus;
import de.metas.bpartner.blockstatus.CreateBPartnerBlockStatusRequest;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_BlockStatus;
import org.compiere.util.Env;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID;

public class C_BPartner_BlockStatus_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_BlockStatus_StepDefData bPartnerBlockStatusTable;

	public C_BPartner_BlockStatus_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_BlockStatus_StepDefData bPartnerBlockStatusTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.bPartnerBlockStatusTable = bPartnerBlockStatusTable;
	}

	private final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);

	@And("^C_BPartner is (blocked|unblocked)$")
	public void bPartner_update_blockStatus(
			@NonNull final String status,
			@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataRows = dataTable.asMaps();
		final BlockStatus blockStatus = getBlockStatus(status);
		for (final Map<String, String> row : dataRows)
		{

			createBPartnerBlockStatus(row, blockStatus);
		}
	}
	
	@And("load latest C_BPartner_BlockStatus for C_BPartner:")
	public void load_latest_C_Bpartner_BlockStatus_for_C_BPartner(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataRows = dataTable.asMaps();
		for (final Map<String, String> row : dataRows)
		{
			loadLatestBPartnerBlockStatus(row);
		}
	}

	@And("validate C_BPartner_BlockStatus:")
	public void validate_C_Bpartner_BlockStatus(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> dataRows = dataTable.asMaps();
		for (final Map<String, String> row : dataRows)
		{
			validateBPartnerBlockStatus(row);
		}
	}

	private void validateBPartnerBlockStatus(@NonNull final Map<String, String> row)
	{
		final String bPartnerBlockStatusIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_BlockStatus_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BPartnerBlockStatus bPartnerBlockStatus = bPartnerBlockStatusTable.get(bPartnerBlockStatusIdentifier);

		final SoftAssertions softly = new SoftAssertions();

		final String reason = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BPartner_BlockStatus.COLUMNNAME_Reason);
		if (Check.isNotBlank(reason))
		{
			softly.assertThat(bPartnerBlockStatus.getReason()).as(I_C_BPartner_BlockStatus.COLUMNNAME_Reason).isEqualTo(reason);
		}

		final String status = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_BlockStatus.COLUMNNAME_BlockStatus);
		final BlockStatus blockStatus = BlockStatus.ofCode(status);
		softly.assertThat(bPartnerBlockStatus.getStatus()).as(I_C_BPartner_BlockStatus.COLUMNNAME_BlockStatus).isEqualTo(blockStatus);

		softly.assertAll();
	}

	private void loadLatestBPartnerBlockStatus(@NonNull final Map<String, String> row)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerTable.get(bpartnerIdentifier).getC_BPartner_ID());

		final Optional<BPartnerBlockStatus> bPartnerBlockStatus = bPartnerBlockStatusService.retrieveBlockedByBPartnerId(bPartnerId);
		assertThat(bPartnerBlockStatus).isPresent();

		final String bPartnerBlockStatusIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_BlockStatus.COLUMNNAME_C_BPartner_BlockStatus_ID + "." + TABLECOLUMN_IDENTIFIER);
		bPartnerBlockStatusTable.put(bPartnerBlockStatusIdentifier, bPartnerBlockStatus.get());
	}

	@NonNull
	private BlockStatus getBlockStatus(@NonNull final String status)
	{
		switch (status)
		{
			case "blocked":
				return BlockStatus.Blocked;
			case "unblocked":
				return BlockStatus.Unblocked;
			default:
				throw new AdempiereException("Unsupported block status !");
		}
	}

	private void createBPartnerBlockStatus(
			@NonNull final Map<String, String> tableRow,
			@NonNull final BlockStatus blockStatus)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerTable.get(bpartnerIdentifier).getC_BPartner_ID());

		final Optional<String> reason = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_BlockStatus.COLUMNNAME_Reason));

		final CreateBPartnerBlockStatusRequest createBPartnerBlockStatusRequest = CreateBPartnerBlockStatusRequest.builder()
				.bPartnerId(bPartnerId)
				.blockStatus(blockStatus)
				.orgId(Env.getOrgId())
				.reason(reason.orElse(null))
				.build();

		bPartnerBlockStatusService.createBPartnerBlockStatus(createBPartnerBlockStatusRequest);
	}
}
