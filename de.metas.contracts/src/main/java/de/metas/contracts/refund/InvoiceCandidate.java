package de.metas.contracts.refund;


import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;

public interface InvoiceCandidate
{
	InvoiceCandidateId getId();

	Money getMoney();
}
