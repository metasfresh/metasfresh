package de.metas.invoice.acct;

import de.metas.invoice.InvoiceId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceAcctService
{
	@NonNull private final InvoiceAcctRepository invoiceAcctRepository;

	public InvoiceAcctService(final @NonNull InvoiceAcctRepository invoiceAcctRepository) {this.invoiceAcctRepository = invoiceAcctRepository;}

	public Optional<InvoiceAcct> getById(@NonNull final InvoiceId invoiceId)
	{
		return invoiceAcctRepository.getById(invoiceId);
	}

	public void save(@NonNull final InvoiceAcct invoiceAcct)
	{
		invoiceAcctRepository.save(invoiceAcct);
	}

}
