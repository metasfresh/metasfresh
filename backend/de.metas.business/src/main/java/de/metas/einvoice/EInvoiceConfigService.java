package de.metas.einvoice;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EInvoiceConfigService
{
	public Optional<EInvoiceRecipientConfig> resolveForInvoice(@NonNull final InvoiceId invoiceId)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.load(invoiceId, I_C_Invoice.class);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);

		if (!bpartner.isEInvoiceRecipeint())
		{
			return Optional.empty();
		}

		final EInvoiceFormat format = EInvoiceFormat.ofNullableCode(bpartner.getEInvoiceType());
		if (format == null)
		{
			return Optional.empty();
		}

		return Optional.of(EInvoiceRecipientConfig.builder()
				.format(format)
				.buyerReference(bpartner.getEInvoice_BuyerReference())
				.build());
	}
}
