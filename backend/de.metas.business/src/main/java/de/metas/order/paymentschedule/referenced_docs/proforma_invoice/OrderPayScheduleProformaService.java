package de.metas.order.paymentschedule.referenced_docs.proforma_invoice;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleProformaService
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final ProformaOrderAllocRepository proformaAllocRepo;

	public static OrderPayScheduleProformaService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				OrderPayScheduleProformaService.class,
				() -> new OrderPayScheduleProformaService(
						ProformaOrderAllocRepository.newInstanceForUnitTesting()
				)
		);
	}

	@NonNull
	public ProformaInvoice getById(@NonNull final InvoiceId proformaInvoiceId)
	{
		final I_C_Invoice proforma = invoiceBL.getById(proformaInvoiceId);
		final CurrencyId proformaCurrencyId = CurrencyId.ofRepoId(proforma.getC_Currency_ID());

		return ProformaInvoice.builder()
				.id(InvoiceId.ofRepoId(proforma.getC_Invoice_ID()))
				.grandTotal(Money.of(proforma.getGrandTotal(), proformaCurrencyId))
				.dateInvoiced(TimeUtil.asLocalDate(proforma.getDateInvoiced()))
				.dueDate(Objects.requireNonNull(TimeUtil.asLocalDate(proforma.getDueDate())))
				.build();
	}

	@NonNull
	public Optional<ProformaInvoice> getByOrderId(@NonNull final OrderId orderId)
	{
		return getIdByOrderId(orderId).map(this::getById);
	}

	@NonNull
	public Optional<InvoiceId> getIdByOrderId(final @NotNull OrderId orderId)
	{
		return proformaAllocRepo.findProformaInvoiceIdByOrderId(orderId);
	}
}
