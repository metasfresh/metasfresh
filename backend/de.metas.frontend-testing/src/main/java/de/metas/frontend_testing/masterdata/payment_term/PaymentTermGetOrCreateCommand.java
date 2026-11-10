package de.metas.frontend_testing.masterdata.payment_term;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.paymentterm.repository.PaymentTermQuery;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/**
 * Resolves the {@code C_PaymentTerm} with the given {@code Value} (of the masterdata org or of org {@code *}); creates it
 * when a fresh E2E database has none.
 * <p>
 * Creating it is only a fallback so a spec can mirror a real customer invoice whose payment term is identified by
 * its value (for example the till's "pay an open invoice" reference invoice, term {@code 1000}); real databases
 * already carry such terms as migrated data and keep them untouched. The {@code Value} is kept as requested, because
 * mirroring it is the point; only the {@code Name} is made unique per run.
 */
@Builder
public class PaymentTermGetOrCreateCommand
{
	@NonNull private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);

	@NonNull private final String value;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;

	public PaymentTermId execute()
	{
		return paymentTermRepository.firstIdOnly(PaymentTermQuery.builder().orgId(orgId).value(value).build())
				.orElseGet(this::create);
	}

	private PaymentTermId create()
	{
		return paymentTermRepository.createPaymentTerm(orgId, value, Identifier.ofString("Term " + value).toUniqueString());
	}
}
