package de.metas.cucumber.stepdefs.match_inv;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvCollection;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

@RequiredArgsConstructor
public class M_MatchInv_StepDef
{
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository = SpringContextHolder.instance.getBean(MatchInvoiceRepository.class);
	@NonNull private final MatchInvoiceService matchInvoiceService = SpringContextHolder.instance.getBean(MatchInvoiceService.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final C_InvoiceLine_StepDefData invoiceLineTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	@And("^M_MatchInv are found$")
	public void loadAndValidateMatchInvoices(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_M_MatchInv.COLUMNNAME_M_MatchInv_ID)
				.forEach(this::loadAndValidateMatchInvoice);
	}

	public void loadAndValidateMatchInvoice(@NonNull final DataTableRow row)
	{
		final MatchInv matchInvoice = retrieveMatchInv(row);
		SharedTestContext.put("matchInvoice", matchInvoice);
		row.getAsOptionalIdentifier().ifPresent(identifier -> matchInvTable.put(identifier, matchInvoice));

		validateMatchInvoice(row, matchInvoice);
	}

	private void validateMatchInvoice(@NonNull final DataTableRow row, @NonNull final MatchInv matchInvoice)
	{
		SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalIdentifier(I_M_MatchInv.COLUMNNAME_M_Product_ID)
				.map(productTable::getId)
				.ifPresent(productId -> softly.assertThat(matchInvoice.getProductId()).as("M_Product_ID").isEqualTo(productId));
		row.getAsOptionalQuantity("QtyInUOM", uomDAO::getByX12DE355)
				.ifPresent(qtyInUOM -> softly.assertThat(matchInvoice.getQty().getUOMQtyNotNull()).as("QtyInUOM").isEqualTo(qtyInUOM));
		row.getAsOptionalBoolean(I_M_MatchInv.COLUMNNAME_IsSOTrx)
				.map(SOTrx::ofBoolean)
				.ifPresent(soTrx -> softly.assertThat(matchInvoice.getSoTrx()).as("IsSOTrx").isEqualTo(soTrx));

		softly.assertAll();
	}

	private MatchInv retrieveMatchInv(final @NonNull DataTableRow row)
	{
		final InvoiceAndLineId invoiceLineId = invoiceLineTable.getInvoiceAndLineId(row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID));
		final InOutLineId inoutLineId = row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID).lookupNotNullIdIn(inoutLineTable);

		final MatchInvCollection matchInvoices = matchInvoiceRepository.list(MatchInvQuery.builder()
				.type(MatchInvType.Material)
				.invoiceAndLineId(invoiceLineId)
				.inoutLineId(inoutLineId)
				.build());
		if (matchInvoices.isEmpty())
		{
			throw new AdempiereException("No M_MatchInv found for " + invoiceLineId + " and " + inoutLineId);
		}
		else if (matchInvoices.size() > 1)
		{
			throw new AdempiereException("More than one M_MatchInv found for " + invoiceLineId + " and " + inoutLineId + ": " + matchInvoices);
		}

		return matchInvoices.singleElement();
	}

	/**
	 * Deletes the material matches of the given receipt line, as reversing its material receipt does.
	 *
	 * @cucumber.example
	 * <pre>
	 * When the material matches of receipt line receipt_line1 are deleted
	 * </pre>
	 */
	@When("the material matches of receipt line {string} are deleted")
	public void deleteMaterialMatches(@NonNull final String inoutLineIdentifier)
	{
		matchInvoiceService.deleteByInOutLineId(InOutLineId.ofRepoId(inoutLineTable.get(inoutLineIdentifier).getM_InOutLine_ID()));
	}

	/**
	 * Creates a material match between the (single) line of the given invoice and the given receipt line.
	 *
	 * @cucumber.example
	 * <pre>
	 * When a material match of invoice invoice and receipt line receipt_line1 is created
	 * </pre>
	 */
	@When("a material match of invoice {string} and receipt line {string} is created")
	public void createMaterialMatch(@NonNull final String invoiceIdentifier, @NonNull final String inoutLineIdentifier)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
		final I_C_InvoiceLine invoiceLine = invoiceDAO.retrieveLines(invoice).stream()
				.reduce((a, b) -> {throw new AdempiereException("More than one line in invoice " + invoiceIdentifier);})
				.orElseThrow(() -> new AdempiereException("No line in invoice " + invoiceIdentifier));
		final I_M_InOutLine inoutLine = inoutLineTable.get(inoutLineIdentifier);
		InterfaceWrapperHelper.refresh(inoutLine);

		final boolean created = matchInvoiceService.newMatchInvBuilder(MatchInvType.Material)
				.invoiceLine(invoiceLine)
				.inoutLine(inoutLine)
				.build();
		if (!created)
		{
			throw new AdempiereException("No material match was created for invoice " + invoiceIdentifier + " and receipt line " + inoutLineIdentifier);
		}
	}
}
