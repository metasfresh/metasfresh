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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
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
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class EDI_DesadvLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final EDI_DesadvLine_StepDefData desadvLineTable;
	private final Edi_Desadv_StepDefData desadvTable;
	private final M_Product_StepDefData productTable;

	public EDI_DesadvLine_StepDef(
			@NonNull final EDI_DesadvLine_StepDefData desadvLineTable,
			@NonNull final Edi_Desadv_StepDefData desadvTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.desadvLineTable = desadvLineTable;
		this.desadvTable = desadvTable;
		this.productTable = productTable;
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
