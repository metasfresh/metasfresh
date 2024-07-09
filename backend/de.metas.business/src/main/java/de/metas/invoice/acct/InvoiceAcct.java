package de.metas.invoice.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class InvoiceAcct
{
	@Getter @NonNull private final InvoiceId invoiceId;
	@Getter @NonNull private final OrgId orgId;
	@Getter @NonNull private final ImmutableList<InvoiceAcctRule> rulesOrdered;

	@Builder
	private InvoiceAcct(
			final @NonNull InvoiceId invoiceId,
			final @NonNull OrgId orgId,
			final @NonNull ImmutableList<InvoiceAcctRule> rules)
	{
		Check.assumeNotEmpty(rules, "rulesOrdered not empty");
		Check.assume(orgId.isRegular(), "org is set");
		rules.forEach(rule -> rule.assertInvoiceId(invoiceId));

		this.invoiceId = invoiceId;
		this.orgId = orgId;
		this.rulesOrdered = rules.stream()
				.sorted(Comparator.comparing(InvoiceAcctRule::getMatcher, InvoiceAcctRuleMatcher.ORDER_FROM_SPECIFIC_TO_GENERIC))
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<ElementValueId> getElementValueId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final AccountConceptualName accountConceptualName,
			@Nullable final InvoiceAndLineId invoiceAndLineId)
	{
		return this.rulesOrdered.stream()
				.filter(rule -> rule.matches(acctSchemaId, accountConceptualName, invoiceAndLineId))
				.findFirst()
				.map(InvoiceAcctRule::getElementValueId);
	}
}
