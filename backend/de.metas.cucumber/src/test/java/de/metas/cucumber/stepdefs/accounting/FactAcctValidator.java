package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;

import java.util.List;
import java.util.Set;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.waitUtilPosted;

@Builder
public class FactAcctValidator
{
	@NonNull private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	@NonNull private final FactAcctMatchers matchers;

	public static class FactAcctValidatorBuilder
	{
		public void validate() throws Throwable
		{
			build().validate();
		}
	}

	public void validate() throws Throwable
	{
		waitUtilPosted(matchers.getDocumentRefs());

		SharedTestContext.run(() -> {
			SharedTestContext.put("expectations", "\n" + matchers);

			final FactAcctRecords factAcctRecords = retrieveFactAcctRecords();
			SharedTestContext.put("actual", "\n" + factAcctRecords);

			matchers.assertValid(factAcctRecords);
		});
	}

	private @NonNull FactAcctRecords retrieveFactAcctRecords()
	{
		final List<FactAcctQuery> queries = toFactAcctQueryList(matchers.getDocumentRefs());
		Check.assumeNotEmpty(queries, "queries is not empty");
		SharedTestContext.put("queries", "\n" + queries);

		final List<I_Fact_Acct> list = factAcctDAO.list(queries);
		return FactAcctRecords.builder()
				.list(ImmutableList.copyOf(list))
				.tabularStringConverter(factAcctTabularStringConverter)
				.build();
	}

	private static List<FactAcctQuery> toFactAcctQueryList(final Set<TableRecordReference> documentRefs)
	{
		return documentRefs.stream()
				.map(recordRef -> FactAcctQuery.builder().recordRef(recordRef).build())
				.collect(ImmutableList.toImmutableList());
	}

}
