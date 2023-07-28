package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.edi.model.I_C_Order;
import de.metas.money.MoneyService;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
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
	private final LookupDataSource elementValueLookup;
	private final LookupDataSource taxLookup;
	private final LookupDataSource sectionCodeLookup;
	private final LookupDataSource productLookup;
	private final LookupDataSource orderLookup;
	private final LookupDataSource activityLookup;

	public AcctSimulationViewDataService(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final AcctDocRegistry acctDocRegistry,
			@NonNull final MoneyService moneyService)
	{
		this.acctDocRegistry = acctDocRegistry;
		this.moneyService = moneyService;

		this.elementValueLookup = lookupDataSourceFactory.searchInTableLookup(I_C_ElementValue.Table_Name);
		this.taxLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Tax.Table_Name);
		this.sectionCodeLookup = lookupDataSourceFactory.searchInTableLookup(I_M_SectionCode.Table_Name);
		this.productLookup = lookupDataSourceFactory.searchInTableLookup(I_M_Product.Table_Name);
		this.orderLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Order.Table_Name);
		this.activityLookup = lookupDataSourceFactory.searchInTableLookup(I_C_Activity.Table_Name);
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
		final CurrencyCode documentCurrency = moneyService.getCurrencyCodeByCurrencyId(factLine.getCurrencyId());
		final CurrencyCode localCurrency = moneyService.getCurrencyCodeByCurrencyId(factLine.getAcctCurrencyId());
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

		return AcctRow.builder()
				.rowId(DocumentId.of("generated_" + index))
				.postingSign(postingSign)
				.account(elementValueLookup.findById(factLine.getAccount_ID()))
				.amount_DC(amount_DC)
				.amount_LC(amount_LC)
				.tax(taxLookup.findById(factLine.getTaxId()))
				.description(factLine.getDescription())
				.sectionCode(sectionCodeLookup.findById(factLine.getM_SectionCode_ID()))
				.product(productLookup.findById(factLine.getM_Product_ID()))
				.userElementString1(factLine.getUserElementString1())
				.orderSO(orderLookup.findById(factLine.getC_OrderSO_ID()))
				.activity(activityLookup.findById(factLine.getC_Activity_ID()))
				.build();
	}
}
