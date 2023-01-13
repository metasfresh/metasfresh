package de.metas.invoice.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Comparator;

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
}

@Value
@Builder
class InvoiceAcctRuleMatcher
{
	public static final Comparator<InvoiceAcctRuleMatcher> ORDER_FROM_SPECIFIC_TO_GENERIC = Comparator.comparing(InvoiceAcctRuleMatcher::getAcctSchemaId)
			.thenComparing(InvoiceAcctRuleMatcher::getAccountTypeName, Comparator.nullsLast(Comparator.naturalOrder()))
			.thenComparing(InvoiceAcctRuleMatcher::getInvoiceLineId, Comparator.nullsLast(Comparator.naturalOrder()));

	@NonNull AcctSchemaId acctSchemaId;
	@Nullable InvoiceLineId invoiceLineId;
	@Nullable AccountTypeName accountTypeName;

	void assertInvoiceId(@NonNull InvoiceId expectedInvoiceId)
	{
		if (invoiceLineId != null)
		{
			invoiceLineId.assertInvoiceId(expectedInvoiceId);
		}
	}
}

@Value
@Builder
class InvoiceAcctRule
{
	@NonNull InvoiceAcctRuleMatcher matcher;

	@NonNull ElementValueId elementValueId;

	void assertInvoiceId(@NonNull InvoiceId expectedInvoiceId)
	{
		matcher.assertInvoiceId(expectedInvoiceId);
	}
}