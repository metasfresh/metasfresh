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
import de.metas.common.util.StringUtils;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.process.export.enqueue.DesadvEnqueuer;
import de.metas.edi.process.export.enqueue.EnqueueDesadvRequest;
import de.metas.edi.process.export.enqueue.EnqueueDesadvResult;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class EDI_Desadv_StepDef
{
	private static final String EDI_EXP_DESADV_PACK_TAGNAME = "EDI_Exp_Desadv_Pack";
	private static final String IPA_SSCC18_TAGNAME = "IPA_SSCC18";
	private static final String EDI_EXP_DESADV_PACK_ITEM_TAGNAME = "EDI_Exp_Desadv_Pack_Item";
	private static final String QTY_CU_TAGNAME = "QtyCUsPerTU";
	private static final String QTY_CUS_PER_LU_TAGNAME = "QtyCUsPerLU";
	private static final String QTY_TU_TAGNAME = "QtyTU";

	private final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

	private final DesadvEnqueuer desadvEnqueuer = SpringContextHolder.instance.getBean(DesadvEnqueuer.class);

	private final EDI_Desadv_StepDefData desadvTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_Order_StepDefData orderTable;
	private final EDI_Exp_Desadv_StepDefData ediExpDesadvTable;

	public EDI_Desadv_StepDef(
			@NonNull final EDI_Desadv_StepDefData desadvTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final EDI_Exp_Desadv_StepDefData ediExpDesadvTable)
	{
		this.desadvTable = desadvTable;
		this.bpartnerTable = bpartnerTable;
		this.orderTable = orderTable;
		this.ediExpDesadvTable = ediExpDesadvTable;
	}

	@Then("validate created edi desadv")
	public void validate_edi_desadv(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateEdiDesadv(tableRow);
		}
	}

	@Then("EDI_Desadv is found:")
	public void find_desadv(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			findDesadv(row);
		}
	}

	@Then("EDI_Desadv is enqueued for export")
	public void enqueue_desadv_for_export(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			enqueueDesadvForExport(row);
		}
	}

	@Then("EDI_Exp_Desadv_Pack of the following EDI_Exp_Desadv is validated")
	public void validate_EDI_Exp_Desadv_Pack(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateEDIExpDesadvPack(tableRow);
		}
	}

	@Then("^after not more than (.*)s, EDI_Desadv records have the following export status$")
	public void validate_export_status(final int timeoutSec, @NonNull final DataTable table) throws InterruptedException
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			validateExportStatus(timeoutSec, row);
		}
	}

	private void validateEdiDesadv(@NonNull final Map<String, String> tableRow)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Order.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);

		final de.metas.edi.model.I_C_Order orderRecord = InterfaceWrapperHelper.create(
				CoalesceUtil.coalesceSuppliers(
						() -> orderTable.get(orderIdentifier),
						() -> InterfaceWrapperHelper.load(StringUtils.toIntegerOrZero(orderIdentifier), I_C_Order.class)), de.metas.edi.model.I_C_Order.class);

		final I_EDI_Desadv ediDesadvRecord = InterfaceWrapperHelper.load(orderRecord.getEDI_Desadv_ID(), I_EDI_Desadv.class);

		final BigDecimal sumDeliveredInStockingUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_SumDeliveredInStockingUOM);
		final BigDecimal sumOrderedInStockingUOM = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_SumOrderedInStockingUOM);

		assertThat(ediDesadvRecord.getSumDeliveredInStockingUOM()).isEqualByComparingTo(sumDeliveredInStockingUOM);
		assertThat(ediDesadvRecord.getSumOrderedInStockingUOM()).isEqualByComparingTo(sumOrderedInStockingUOM);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "EDI_Desadv");
		desadvTable.put(recordIdentifier, ediDesadvRecord);
	}

	private void validateEDIExpDesadvPack(@NonNull final Map<String, String> tableRow)
	{
		final String expDesadvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "EDI_Exp_Desadv_ID" + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Document ediExpDesadv = ediExpDesadvTable.get(expDesadvIdentifier);

		assertThat(ediExpDesadv).isNotNull();

		final Element desadvPackElement = getElement(ediExpDesadv, EDI_EXP_DESADV_PACK_TAGNAME);

		assertThat(desadvPackElement).isNotNull();

		final Element sscc18Element = getElement(desadvPackElement, IPA_SSCC18_TAGNAME);
		assertThat(sscc18Element).isNotNull();

		final Element desadvPackItem = getElement(desadvPackElement, EDI_EXP_DESADV_PACK_ITEM_TAGNAME);
		assertThat(desadvPackItem).isNotNull();

		final String qtyCuExpected = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerTU");
		if (Check.isNotBlank(qtyCuExpected))
		{
			final Element qtyCu = getElement(desadvPackElement, QTY_CU_TAGNAME);
			assertThat(qtyCu).isNotNull();

			assertThat(qtyCu.getTextContent()).isEqualTo(qtyCuExpected);
		}

		final String qtyCusPerLuExpected = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.EDI_Exp_Desadv_Pack_Item.QtyCUsPerLU");
		if (Check.isNotBlank(qtyCusPerLuExpected))
		{
			final Element qtyCusPerLu = getElement(desadvPackElement, QTY_CUS_PER_LU_TAGNAME);
			assertThat(qtyCusPerLu).isNotNull();

			assertThat(qtyCusPerLu.getTextContent()).isEqualTo(qtyCusPerLuExpected);
		}

		final String qtyTuExpected = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.EDI_Exp_Desadv_Pack_Item.QtyTU");
		if (Check.isNotBlank(qtyTuExpected))
		{
			final Element qtyTu = getElement(desadvPackElement, QTY_TU_TAGNAME);
			assertThat(qtyTu).isNotNull();

			assertThat(qtyTu.getTextContent()).isEqualTo(qtyTuExpected);
		}
	}

	@Nullable
	private static Element getElement(@NonNull final Node node, @NonNull final String tagName)
	{
		final Element element = node instanceof Document
				? ((Document)node).getDocumentElement()
				: (Element)node;

		final NodeList nodeList = element.getElementsByTagName(tagName);

		if (nodeList.getLength() == 0)
		{
			return null;
		}

		final Node childNode = nodeList.item(0);

		assertThat(childNode.getNodeType()).isEqualTo(Node.ELEMENT_NODE);

		return (Element)childNode;
	}

	private void validateExportStatus(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String desadvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);

		final String exportStatus = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus);

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> {
			InterfaceWrapperHelper.refresh(desadvRecord);

			return exportStatus.equals(desadvRecord.getEDI_ExportStatus());
		});
	}

	private void enqueueDesadvForExport(@NonNull final Map<String, String> tableRow)
	{
		final String desadvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID + "." + TABLECOLUMN_IDENTIFIER);

		final I_EDI_Desadv desadvRecord = desadvTable.get(desadvIdentifier);

		final EnqueueDesadvRequest enqueueDesadvRequest = EnqueueDesadvRequest.builder()
				.ctx(Env.getCtx())
				.desadvIterator(ImmutableList.of(desadvRecord).iterator())
				.build();

		final EnqueueDesadvResult enqueueDesadvResult = desadvEnqueuer.enqueue(enqueueDesadvRequest);

		assertThat(enqueueDesadvResult.getSkippedDesadvList()).isEmpty();
	}

	private void findDesadv(@NonNull final Map<String, String> tableRow)
	{
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bpartnerID = bpartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_C_Order_ID + ".Identifier");
		final I_C_Order order = orderTable.get(orderIdentifier);

		final I_EDI_Desadv desadvRecord = desadvDAO.retrieveMatchingDesadvOrNull(order.getPOReference(), BPartnerId.ofRepoId(order.getC_BPartner_ID()), InterfaceWrapperHelper.getContextAware(order));

		assertThat(desadvRecord).isNotNull();
		assertThat(desadvRecord.getC_BPartner_ID()).isEqualTo(bpartnerID);

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID + "." + TABLECOLUMN_IDENTIFIER);
		desadvTable.putOrReplace(identifier, desadvRecord);
	}
}
