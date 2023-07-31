package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AcctSimulationViewDataService
{
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final AcctDocRegistry acctDocRegistry;
	private final MoneyService moneyService;
	private final AcctRowLookups lookups;

	public AcctSimulationViewDataService(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final AcctDocRegistry acctDocRegistry,
			@NonNull final MoneyService moneyService)
	{
		this.acctDocRegistry = acctDocRegistry;
		this.moneyService = moneyService;

		this.lookups = new AcctRowLookups(lookupDataSourceFactory);
	}

	public AcctSimulationViewData getViewData(
			final @NonNull TableRecordReference docRecordRef,
			final @NonNull ClientId clientId)
	{
		return AcctSimulationViewData.builder()
				.dataService(this)
				.docRecordRef(docRecordRef)
				.clientId(clientId)
				.build();
	}

	public List<AcctRow> retrieveRows(
			final @NonNull TableRecordReference docRecordRef,
			final @NonNull ClientId clientId)
	{
		final List<AcctSchema> acctSchemas = acctSchemaBL.getAllByClientId(clientId);
		final Doc<?> acctDoc = acctDocRegistry.get(acctSchemas, docRecordRef);
		final List<Fact> facts = acctDoc.postLogic();
		return facts.stream()
				.flatMap(this::toRowsAndStream)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<AcctRow> toRowsAndStream(final Fact fact)
	{
		final ArrayList<AcctRow> result = new ArrayList<>();
		final ImmutableList<FactLine> lines = fact.getLines();
		for (int i = 0; i < lines.size(); i++)
		{
			final FactLine factLine = lines.get(i);
			final AcctRow row = toRow(factLine, i);
			result.add(row);
		}

		return result.stream();
	}

	public AcctRow toRow(final FactLine factLine, final int index)
	{
		return AcctRow.builder()
				.lookups(lookups)
				.currencyCodeToCurrencyIdBiConverter(moneyService)
				.factLine(factLine)
				.userChanges(extractUserChanges(factLine, moneyService))
				.rowId(DocumentId.of("generated_" + index))
				.build();
	}

	private static FactLineChanges extractUserChanges(final FactLine factLine, final MoneyService moneyService)
	{
		final CurrencyId documentCurrencyId = factLine.getCurrencyId();
		final CurrencyCode documentCurrency = moneyService.getCurrencyCodeByCurrencyId(documentCurrencyId);
		final CurrencyId localCurrencyId = factLine.getAcctCurrencyId();
		final CurrencyCode localCurrency = moneyService.getCurrencyCodeByCurrencyId(localCurrencyId);
		final PostingSign postingSign;
		final Amount amount_LC; // amount (local currency)
		final Amount amount_DC; // amount (document/foreign currency)
		if (factLine.getAmtSourceCr().signum() != 0)
		{
			postingSign = PostingSign.CREDIT;
			amount_DC = Amount.of(factLine.getAmtSourceCr(), documentCurrency);
			amount_LC = Amount.of(factLine.getAmtAcctCr(), localCurrency);
		}
		else
		{
			postingSign = PostingSign.DEBIT;
			amount_DC = Amount.of(factLine.getAmtSourceDr(), documentCurrency);
			amount_LC = Amount.of(factLine.getAmtAcctDr(), localCurrency);
		}

		return FactLineChanges.builder()
				.postingSign(postingSign)
				.accountId(factLine.getAccount_ID())
				.amount_DC(amount_DC)
				.amount_LC(amount_LC)
				.taxId(factLine.getTaxId())
				.description(factLine.getDescription())
				.sectionCodeId(factLine.getM_SectionCode_ID())
				.productId(factLine.getM_Product_ID())
				.userElementString1(factLine.getUserElementString1())
				.salesOrderId(factLine.getC_OrderSO_ID())
				.activityId(factLine.getC_Activity_ID())
				.build();
	}
}
