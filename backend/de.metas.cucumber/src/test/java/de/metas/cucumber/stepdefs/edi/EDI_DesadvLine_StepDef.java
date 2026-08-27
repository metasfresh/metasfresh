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
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class EDI_DesadvLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final EDI_DesadvLine_StepDefData desadvLineTable;
	private final EDI_Desadv_StepDefData desadvTable;
	private final M_Product_StepDefData productTable;

	public EDI_DesadvLine_StepDef(
			@NonNull final EDI_DesadvLine_StepDefData desadvLineTable,
			@NonNull final EDI_Desadv_StepDefData desadvTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.desadvLineTable = desadvLineTable;
		this.desadvTable = desadvTable;
		this.productTable = productTable;
	}

	/**
	 * Finds EDI_DesadvLine records by EDI_Desadv_ID and registers each under the given identifier.
	 * Useful when a test needs to reference an auto-created DESADV line by identifier (e.g. to mutate it).
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_DesadvLine_ID} – identifier under which the found record is registered</li>
	 *   <li>{@code EDI_Desadv_ID} – identifier of the parent DESADV (registered in {@link EDI_Desadv_StepDefData})</li>
	 * </ul>
	 * Optional disambiguator columns (use when the DESADV has more than one line):
	 * <ul>
	 *   <li>{@code M_Product_ID} – narrows the match to the line with this product</li>
	 *   <li>{@code Line} – narrows the match to the line with this line number</li>
	 * </ul>
	 * When no disambiguator is provided the query must return exactly one result.
	 * <p>
	 * Optional assertion columns (each asserted only when the column is present):
	 * <ul>
	 *   <li>{@code QtyEntered} – expected {@code EDI_DesadvLine.QtyEntered}</li>
	 *   <li>{@code QtyDeliveredInUOM} – expected {@code EDI_DesadvLine.QtyDeliveredInUOM}</li>
	 *   <li>{@code QtyDeliveredInStockingUOM} – expected {@code EDI_DesadvLine.QtyDeliveredInStockingUOM}</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then EDI_DesadvLine records are found:
	 *   | EDI_DesadvLine_ID | EDI_Desadv_ID | OPT.QtyDeliveredInStockingUOM |
	 *   | desadvLine        | myDesadv      | 3                             |
	 * </pre>
	 */
	@Then("EDI_DesadvLine records are found:")
	public void edi_desadv_line_records_are_found(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::findAndRegisterDesadvLine);
	}

	private void findAndRegisterDesadvLine(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier desadvIdentifier = row.getAsIdentifier(I_EDI_DesadvLine.COLUMNNAME_EDI_Desadv_ID);
		final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);

		final IQueryBuilder<I_EDI_DesadvLine> queryBuilder = queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_EDI_Desadv_ID, desadvRecord.getEDI_Desadv_ID());

		row.getAsOptionalIdentifier(I_EDI_DesadvLine.COLUMNNAME_M_Product_ID)
				.ifPresent(productIdentifier -> {
					final I_M_Product productRecord = productTable.get(productIdentifier);
					queryBuilder.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID());
				});

		row.getAsOptionalInt(I_EDI_DesadvLine.COLUMNNAME_Line)
				.ifPresent(line -> queryBuilder.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_Line, line));

		final I_EDI_DesadvLine desadvLine = queryBuilder.create().firstOnlyNotNull(I_EDI_DesadvLine.class);

		assertOptionalDesadvLineQtys(row, desadvLine);

		final StepDefDataIdentifier lineIdentifier = row.getAsIdentifier(I_EDI_DesadvLine.COLUMNNAME_EDI_DesadvLine_ID);
		desadvLineTable.putOrReplace(lineIdentifier, desadvLine);
	}

	/**
	 * Asserts the optional {@code QtyEntered} / {@code QtyDeliveredInUOM} /
	 * {@code QtyDeliveredInStockingUOM} DataTable columns against the given line. A column that is
	 * absent from the row is skipped entirely, so callers that never supply it keep today's behaviour.
	 */
	private static void assertOptionalDesadvLineQtys(@NonNull final DataTableRow row, @NonNull final I_EDI_DesadvLine desadvLine)
	{
		row.getAsOptionalBigDecimal(I_EDI_DesadvLine.COLUMNNAME_QtyEntered)
				.ifPresent(expected -> assertThat(desadvLine.getQtyEntered())
						.as(I_EDI_DesadvLine.COLUMNNAME_QtyEntered)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM)
				.ifPresent(expected -> assertThat(desadvLine.getQtyDeliveredInUOM())
						.as(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM)
						.isEqualByComparingTo(expected));

		row.getAsOptionalBigDecimal(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInStockingUOM)
				.ifPresent(expected -> assertThat(desadvLine.getQtyDeliveredInStockingUOM())
						.as(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInStockingUOM)
						.isEqualByComparingTo(expected));
	}

	/**
	 * Updates fields of an EDI_DesadvLine previously registered under an identifier.
	 *
	 * <p>Real-world trigger: a user edits the DESADV line in the WebUI (window 540256) after the
	 * delivery was "emptied" — e.g. zeroing the delivered quantity or deactivating the line. Such a
	 * line drops out of the pack export and must still surface in the no-pack export section.
	 *
	 * <p>Required column:
	 * <ul>
	 *   <li>{@code EDI_DesadvLine_ID} – identifier of the line to update</li>
	 * </ul>
	 * At least one optional field column must be present:
	 * <ul>
	 *   <li>{@code IsActive} – new IsActive flag (Y/N)</li>
	 *   <li>{@code QtyDeliveredInUOM} – new delivered quantity in the line's UOM</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * Then EDI_DesadvLine records are updated:
	 *   | EDI_DesadvLine_ID | QtyDeliveredInUOM |
	 *   | desadvLine        | 0                 |
	 * </pre>
	 */
	@Then("EDI_DesadvLine records are updated:")
	public void edi_desadv_line_records_are_updated(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateDesadvLine);
	}

	private void updateDesadvLine(@NonNull final DataTableRow row)
	{
		final I_EDI_DesadvLine desadvLine = row.getAsIdentifier(I_EDI_DesadvLine.COLUMNNAME_EDI_DesadvLine_ID)
				.lookupNotNullIn(desadvLineTable);

		row.getAsOptionalBoolean(I_EDI_DesadvLine.COLUMNNAME_IsActive).ifPresent(desadvLine::setIsActive);
		row.getAsOptionalBigDecimal(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM).ifPresent(desadvLine::setQtyDeliveredInUOM);

		saveRecord(desadvLine);
	}

	@Then("validate created edi desadv line")
	public void validate_edi_desadv_line(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateEdiDesadvLine(tableRow);
		}
	}

	private void validateEdiDesadvLine(@NonNull final Map<String, String> tableRow)
	{
		final String desadvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_EDI_Desadv_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedDesadvId = desadvTable.getOptional(desadvIdentifier)
				.map(I_EDI_Desadv::getEDI_Desadv_ID)
				.orElseGet(() -> Integer.parseInt(desadvIdentifier));

		final I_EDI_DesadvLine ediDesadvLineRecord = queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID, expectedDesadvId)
				.create()
				.firstOnlyNotNull(I_EDI_DesadvLine.class);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final int line = DataTableUtil.extractIntForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_Line);
		final BigDecimal qtyDeliveredInStockingUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInStockingUOM);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyEntered);
		final BigDecimal qtyDeliveredInUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM);
		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyOrdered);

		final String invoiceUOMCode = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_C_UOM_Invoice_ID + "." + X12DE355.class.getSimpleName());
		final UomId invoiceUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(invoiceUOMCode));

		final BigDecimal qtyDeliveredInInvoiceUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInInvoiceUOM);
		final BigDecimal qtyItemCapacity = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyItemCapacity);

		final String bPartnerUOMCode = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_C_UOM_BPartner_ID + "." + X12DE355.class.getSimpleName());
		final UomId bPartnerUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(bPartnerUOMCode));

		final BigDecimal qtyEnteredInBPartnerUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_QtyEnteredInBPartnerUOM);
		final BigDecimal bPartnerQtyItemCapacity = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_DesadvLine.COLUMNNAME_BPartner_QtyItemCapacity);

		assertThat(ediDesadvLineRecord.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		assertThat(ediDesadvLineRecord.getLine()).isEqualTo(line);
		assertThat(ediDesadvLineRecord.getQtyDeliveredInStockingUOM()).isEqualByComparingTo(qtyDeliveredInStockingUOM);
		assertThat(ediDesadvLineRecord.getM_Product_ID()).isEqualTo(expectedProductId);
		assertThat(ediDesadvLineRecord.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		assertThat(ediDesadvLineRecord.getQtyDeliveredInUOM()).isEqualByComparingTo(qtyDeliveredInUOM);
		assertThat(ediDesadvLineRecord.getQtyOrdered()).isEqualByComparingTo(qtyOrdered);
		assertThat(ediDesadvLineRecord.getC_UOM_Invoice_ID()).isEqualTo(invoiceUOMId.getRepoId());
		assertThat(ediDesadvLineRecord.getQtyDeliveredInInvoiceUOM()).isEqualByComparingTo(qtyDeliveredInInvoiceUOM);
		assertThat(ediDesadvLineRecord.getQtyItemCapacity()).isEqualByComparingTo(qtyItemCapacity);
		assertThat(ediDesadvLineRecord.getC_UOM_BPartner_ID()).isEqualTo(bPartnerUOMId.getRepoId());
		assertThat(ediDesadvLineRecord.getQtyEnteredInBPartnerUOM()).isEqualByComparingTo(qtyEnteredInBPartnerUOM);
		assertThat(ediDesadvLineRecord.getBPartner_QtyItemCapacity()).isEqualByComparingTo(bPartnerQtyItemCapacity);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "EDI_DesadvLine");
		desadvLineTable.put(recordIdentifier, ediDesadvLineRecord);
	}
}
