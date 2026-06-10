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

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.api.impl.pack.EDIDesadvPackService;
import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.PrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.sscc18.impl.SSCC18CodeBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class EDI_DesadvLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final DesadvBL desadvBL = SpringContextHolder.instance.getBean(DesadvBL.class);
	private final SSCC18CodeBL sscc18CodeBL = SpringContextHolder.instance.getBean(SSCC18CodeBL.class);
	private final EDIDesadvPackService ediDesadvPackService = SpringContextHolder.instance.getBean(EDIDesadvPackService.class);

	@NonNull private final EDI_DesadvLine_StepDefData desadvLineTable;
	@NonNull private final EDI_Desadv_StepDefData desadvTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Finds EDI_DesadvLine records by EDI_Desadv_ID and registers each under the given identifier.
	 * Useful when a test needs to reference an auto-created DESADV line by identifier,
	 * e.g. to inject pack items referencing that line.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_DesadvLine_ID} – identifier under which the found record is registered</li>
	 *   <li>{@code EDI_Desadv_ID} – identifier of the parent DESADV (must be registered in {@link EDI_Desadv_StepDefData})</li>
	 * </ul>
	 *
	 * <p>Optional disambiguator columns (use when the DESADV has more than one line):
	 * <ul>
	 *   <li>{@code OPT.M_Product_ID.Identifier} – narrows the match to the line with this product</li>
	 *   <li>{@code OPT.Line} – narrows the match to the line with this line number</li>
	 * </ul>
	 * When no disambiguator is provided the query must return exactly one result
	 * ({@code firstOnlyNotNull} throws if the DESADV has more than one line).
	 * Use a disambiguator column whenever the DESADV has multiple lines.
	 *
	 * <p>Example (single-line DESADV):
	 * <pre>
	 * Then EDI_DesadvLine records are found:
	 *   | EDI_DesadvLine_ID | EDI_Desadv_ID |
	 *   | desadvLine        | myDesadv      |
	 * </pre>
	 *
	 * <p>Example (multi-line DESADV, disambiguated by product):
	 * <pre>
	 * Then EDI_DesadvLine records are found:
	 *   | EDI_DesadvLine_ID | EDI_Desadv_ID | OPT.M_Product_ID.Identifier |
	 *   | lineA             | myDesadv      | productA                    |
	 *   | lineB             | myDesadv      | productB                    |
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

		// Optional disambiguator: filter by product identifier when provided
		row.getAsOptionalIdentifier(I_EDI_DesadvLine.COLUMNNAME_M_Product_ID)
				.ifPresent(productIdentifier -> {
					final I_M_Product productRecord = productTable.get(productIdentifier);
					queryBuilder.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID());
				});

		// Optional disambiguator: filter by line number when provided
		row.getAsOptionalInt(I_EDI_DesadvLine.COLUMNNAME_Line)
				.ifPresent(line -> queryBuilder.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_Line, line));

		final I_EDI_DesadvLine desadvLine = queryBuilder.create().firstOnlyNotNull(I_EDI_DesadvLine.class);

		final StepDefDataIdentifier lineIdentifier = row.getAsIdentifier(I_EDI_DesadvLine.COLUMNNAME_EDI_DesadvLine_ID);
		desadvLineTable.putOrReplace(lineIdentifier, desadvLine);
	}

	/**
	 * Generates SSCC labels for a single EDI_DesadvLine by invoking the real
	 * {@link DesadvLineSSCC18Generator} with an explicit label count.
	 *
	 * <p>This reproduces the production path of {@code EDI_Desadv_GenerateSSCCLabels}:
	 * it calls {@link PrintableDesadvLineSSCC18Labels} which internally builds a
	 * {@link de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator}.
	 * When the desadv line has no proper LU/TU configuration (e.g. the order-line uses
	 * "No Packing Item" / M_HU_PI_Item_Product_ID=101), the calculator falls back to
	 * {@link de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator#NULL},
	 * and every {@code subtractOneLU()} call returns {@code LUQtys.NULL} (qtyCUsPerLU=0).
	 * Requesting more labels than the breakdown supports is the birth mechanism of an
	 * orphan Qty-0/NULL-M_InOutLine pack item.
	 * After the fix, the generator skips Qty-0 LUs so no orphan is ever created.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code EDI_DesadvLine_ID} – identifier of the desadv line (registered via
	 *       {@link #edi_desadv_line_records_are_found})</li>
	 *   <li>{@code LabelCount} – explicit number of SSCC labels (i.e. LUs) to generate</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * When SSCC labels are generated for EDI_DesadvLine:
	 *   | EDI_DesadvLine_ID | LabelCount |
	 *   | desadvLine        | 2          |
	 * </pre>
	 */
	@When("SSCC labels are generated for EDI_DesadvLine:")
	public void generate_sscc_labels_for_desadv_line(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::generateSSCCLabelsForDesadvLine);
	}

	private void generateSSCCLabelsForDesadvLine(@NonNull final DataTableRow row)
	{
		final I_EDI_DesadvLine desadvLine = row.getAsIdentifier(I_EDI_DesadvLine.COLUMNNAME_EDI_DesadvLine_ID).lookupNotNullIn(desadvLineTable);
		final int labelCount = row.getAsInt("LabelCount");

		final I_EDI_Desadv desadvRecord = desadvLine.getEDI_Desadv();
		final int bpartnerRepoId = CoalesceUtil.firstGreaterThanZero(
				desadvRecord.getDropShip_BPartner_ID(),
				desadvRecord.getC_BPartner_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRepoId);

		final DesadvLineSSCC18Generator generator = DesadvLineSSCC18Generator.builder()
				.sscc18CodeService(sscc18CodeBL)
				.desadvBL(desadvBL)
				.ediDesadvPackService(ediDesadvPackService)
				.printExistingLabels(false)
				.bpartnerId(bpartnerId)
				.build();

		// PrintableDesadvLineSSCC18Labels uses the real shipment-schedule / LU-TU config lookup.
		// When the order-line has no proper packing instructions (e.g. "No Packing Item" / 101),
		// the builder falls back to TotalQtyCUBreakdownCalculator.NULL, so every subtractOneLU()
		// returns LUQtys.NULL (qtyCUsPerLU=0).  With labelCount > available LUs the generator
		// exhausts the calculator — this is the orphan birth mechanism.
		final PrintableDesadvLineSSCC18Labels labelsSpec = PrintableDesadvLineSSCC18Labels.builder()
				.setEDI_DesadvLine(desadvLine)
				.setRequiredSSCC18Count(labelCount)
				.build();

		generator.generateAndEnqueuePrinting(labelsSpec);
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
