package de.metas.cucumber.stepdefs.match_inv;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.invoice.C_InvoiceLine_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOutLine_StepDefData;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_MatchInv;

import java.util.List;

@RequiredArgsConstructor
public class M_MatchInv_StepDef
{
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository = SpringContextHolder.instance.getBean(MatchInvoiceRepository.class);

	@NonNull private final M_MatchInv_StepDefData matchInvTable;
	@NonNull private final C_InvoiceLine_StepDefData invoiceLineTable;
	@NonNull private final M_InOutLine_StepDefData inoutLineTable;

	@And("^M_MatchInv are found$")
	public void loadMatchInvoices(@NonNull final DataTable table)
	{
		DataTableRows.of(table)
				.setAdditionalRowIdentifierColumnName(I_M_MatchInv.COLUMNNAME_M_MatchInv_ID)
				.forEach(this::loadMatchInvoice);
	}

	public void loadMatchInvoice(@NonNull final DataTableRow row)
	{
		@NonNull final StepDefDataIdentifier identifier = row.getAsIdentifier();

		final InvoiceAndLineId invoiceLineId = invoiceLineTable.getInvoiceAndLineId(row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID));
		final InOutLineId inoutLineId = row.getAsIdentifier(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID).lookupNotNullIdIn(inoutLineTable);

		final List<MatchInv> matchInvoices = matchInvoiceRepository.list(MatchInvQuery.builder()
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

		final MatchInv matchInvoice = CollectionUtils.singleElement(matchInvoices);
		matchInvTable.put(identifier, matchInvoice);
	}
}
