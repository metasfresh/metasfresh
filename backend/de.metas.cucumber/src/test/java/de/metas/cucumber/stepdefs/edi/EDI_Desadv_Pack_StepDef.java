/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_HU_PackagingCode_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.esb.edi.model.I_EDI_Desadv_Pack;
import de.metas.handlingunits.HuId;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.text.tabular.Cell;
import de.metas.util.text.tabular.Row;
import de.metas.util.text.tabular.Table;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_GTIN_LU_PackingMaterial;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_IPA_SSCC18;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_IsManual_IPA_SSCC18;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_Line;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID;
import static de.metas.esb.edi.model.I_EDI_Desadv_Pack.COLUMNNAME_M_HU_PackagingCode_LU_ID;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class EDI_Desadv_Pack_StepDef
{
	private final static Logger logger = LogManager.getLogger(EDI_Desadv_Pack_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EDI_Desadv_Pack_StepDefData ediDesadvPackTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public EDI_Desadv_Pack_StepDef(
			@NonNull final EDI_Desadv_Pack_StepDefData ediDesadvPackTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.ediDesadvPackTable = ediDesadvPackTable;
		this.huTable = huTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
	}

	@Given("metasfresh initially has no EDI_Desadv_Pack data")
	public void setupMD_Stock_Data()
	{
		deleteAllFromEDIDesadvPack();
	}

	@Then("^after not more than (.*)s, EDI_Desadv_Pack records are found:$")
	public void packsAreFound(final int timeoutSec, @NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_EDI_Desadv_Pack.COLUMNNAME_EDI_Desadv_Pack_ID)
				.forEach(row -> packIsFound(row, timeoutSec));
	}

	@Then("EDI_Desadv_Pack records are updated")
	public void update_EDI_Desadv_Pack(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			updatePack(row);
		}
	}

	@Then("^after not more than (.*)s, there are no records in EDI_Desadv_Pack$")
	public void tableIsEmpty(final int timeoutSec) throws InterruptedException
	{
		StepDefUtil.tryAndWait(
				timeoutSec,
				500,
				() -> queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class).create().count() == 0,
				() -> logger.error(getCurrentContext())
		);
	}

	private void updatePack(@NonNull final Map<String, String> tableRow)
	{
		final String packIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_EDI_Desadv_Pack_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_EDI_Desadv_Pack packRecord = ediDesadvPackTable.get(packIdentifier);

		final String ipaSSCC18 = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + COLUMNNAME_IPA_SSCC18);
		if (Check.isNotBlank(ipaSSCC18))
		{
			packRecord.setIPA_SSCC18(DataTableUtil.nullToken2Null(ipaSSCC18));
		}

		saveRecord(packRecord);
	}

	private void deleteAllFromEDIDesadvPack()
	{
		DB.executeUpdateEx("DELETE FROM EDI_Desadv_Pack", ITrx.TRXNAME_None);
	}

	private void packIsFound(
			@NonNull final DataTableRow row,
			final int timeoutSec) throws InterruptedException
	{
		final boolean isManualSSCC18 = row.getAsOptionalBoolean(COLUMNNAME_IsManual_IPA_SSCC18).orElseFalse();

		final IQueryBuilder<I_EDI_Desadv_Pack> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.orderByDescending(COLUMNNAME_EDI_Desadv_Pack_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_IsManual_IPA_SSCC18, isManualSSCC18);

		row.getAsOptionalIdentifier(I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID)
				.ifPresent(huIdentifier -> queryBuilder.addEqualsFilter(I_EDI_Desadv_Pack.COLUMNNAME_M_HU_ID, getHuId(huIdentifier)));

		row.getAsOptionalIdentifier(COLUMNNAME_M_HU_PackagingCode_LU_ID)
				.ifPresent(huPackagingCodeLuIdentifier -> queryBuilder.addEqualsFilter(COLUMNNAME_M_HU_PackagingCode_LU_ID, getHuPackagingCodeId(huPackagingCodeLuIdentifier)));

		row.getAsOptionalString(COLUMNNAME_GTIN_LU_PackingMaterial)
				.ifPresent(gtinLuPackingMaterial -> queryBuilder.addEqualsFilter(COLUMNNAME_GTIN_LU_PackingMaterial, DataTableUtil.nullToken2Null(gtinLuPackingMaterial)));

		row.getAsOptionalInt(COLUMNNAME_Line)
				.ifPresent(line -> queryBuilder.addEqualsFilter(COLUMNNAME_Line, line));

		final I_EDI_Desadv_Pack desadvPack = StepDefUtil.tryAndWaitForItem(
				timeoutSec,
				500,
				queryBuilder.create(),
				this::getCurrentContext
		);

		row.getAsOptionalIdentifier().ifPresent(packIdentifier -> ediDesadvPackTable.put(packIdentifier, desadvPack));
	}

	private String getCurrentContext()
	{
		final List<I_EDI_Desadv_Pack> list = queryBL.createQueryBuilder(I_EDI_Desadv_Pack.class)
				.orderBy(COLUMNNAME_EDI_Desadv_Pack_ID)
				.create()
				.list();

		return "EDI_Desadv_Pack records:"
				+ "\n" + toTabular(list).toPrint().ident(1);
	}

	@Nullable
	private HuId getHuId(@NonNull final StepDefDataIdentifier huIdentifier)
	{
		if (huIdentifier.isNullPlaceholder())
		{
			return null;
		}
		return huTable.getId(huIdentifier);
	}

	@Nullable
	private Integer getHuPackagingCodeId(@NonNull final StepDefDataIdentifier huPackagingCodeIdentifier)
	{
		if (huPackagingCodeIdentifier.isNullPlaceholder())
		{
			return null;
		}
		return huPackagingCodeTable.get(huPackagingCodeIdentifier).getM_HU_PackagingCode_ID();
	}

	private Table toTabular(final List<I_EDI_Desadv_Pack> list)
	{
		final Table table = new Table();
		list.stream().map(this::toRow).forEach(table::addRow);
		table.updateHeaderFromRows();
		table.removeColumnsWithBlankValues();
		return table;
	}

	private Row toRow(@NonNull final I_EDI_Desadv_Pack record)
	{
		final Row row = new Row();
		row.put("Identifier", toCell(EDIDesadvPackId.ofRepoIdOrNull(record.getEDI_Desadv_Pack_ID()), ediDesadvPackTable));
		row.put(COLUMNNAME_IsManual_IPA_SSCC18, record.isManual_IPA_SSCC18());
		row.put(COLUMNNAME_M_HU_ID, toCell(HuId.ofRepoIdOrNull(record.getM_HU_ID()), huTable));
		row.put(COLUMNNAME_M_HU_PackagingCode_LU_ID, record.getM_HU_PackagingCode_LU_ID());
		row.put(COLUMNNAME_GTIN_LU_PackingMaterial, record.getGTIN_LU_PackingMaterial());
		row.put(COLUMNNAME_Line, record.getLine());
		return row;
	}

	private static <T extends RepoIdAware> Cell toCell(
			@Nullable final T id,
			@NonNull StepDefDataGetIdAware<T, ?> lookupTable)
	{
		if (id == null)
		{
			return Cell.NULL;
		}

		final StepDefDataIdentifier identifier = lookupTable.getFirstIdentifierById(id).orElse(null);
		if (identifier != null)
		{
			return Cell.ofNullable(identifier + "(" + id.getRepoId() + ")");
		}
		else
		{
			return Cell.ofNullable(id.getRepoId());
		}
	}
}
