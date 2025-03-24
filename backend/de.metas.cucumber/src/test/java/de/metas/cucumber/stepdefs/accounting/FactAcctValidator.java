package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.FactAcctQuery;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_Fact_Acct;

import javax.annotation.Nullable;
import java.util.List;

@Builder
public class FactAcctValidator
{
	@NonNull private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	@Nullable private final DataTable expectations;
	@NonNull private final FactAcctQuery query;

	public static class FactAcctValidatorBuilder
	{
		public void validate() throws Throwable
		{
			build().validate();
		}
	}

	public void validate() throws Throwable
	{
		final FactAcctMatchers matchers = factAcctMatchersFactory.ofDataTable(expectations);
		final FactAcctRecords factAcctRecords = retrieveFactAcctRecords();

		SharedTestContext.run(() -> {
			SharedTestContext.put("query", query);
			SharedTestContext.put("expectations", "\n" + matchers);
			SharedTestContext.put("actual", "\n" + factAcctRecords);

			matchers.assertValid(factAcctRecords);
		});
	}

	private @NonNull FactAcctRecords retrieveFactAcctRecords()
	{
		final List<I_Fact_Acct> list = factAcctDAO.list(query);
		return FactAcctRecords.builder()
				.list(ImmutableList.copyOf(list))
				.tabularStringConverter(factAcctTabularStringConverter)
				.build();
	}
}
