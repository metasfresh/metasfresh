package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.factacct_userchanges.FactAcctChanges;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesService;
import de.metas.acct.factacct_userchanges.FactLineMatchKey;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.costing.CostElementId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcctSimulationViewDataService
{
	private final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
	private final AcctDocRegistry acctDocRegistry;
	private final MoneyService moneyService;
	private final FactAcctUserChangesService factAcctUserChangesService;
	private final AcctRowLookups lookups;

	public AcctSimulationViewDataService(
			@NonNull final LookupDataSourceFactory lookupDataSourceFactory,
			@NonNull final AcctDocRegistry acctDocRegistry,
			@NonNull final MoneyService moneyService,
			@NonNull final FactAcctUserChangesService factAcctUserChangesService)
	{
		this.acctDocRegistry = acctDocRegistry;
		this.moneyService = moneyService;

		this.lookups = new AcctRowLookups(lookupDataSourceFactory);
		this.factAcctUserChangesService = factAcctUserChangesService;
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

		final ImmutableMap<FactLineMatchKey, FactAcctChanges> userChangesByMatchKey = factAcctUserChangesService.getByDocRecordRef(docRecordRef)
				.stream()
				.filter(lineChanges -> lineChanges.getMatchKey() != null)
				.collect(ImmutableMap.toImmutableMap(FactAcctChanges::getMatchKey, lineChanges -> lineChanges));

		final ArrayList<AcctRow> result = new ArrayList<>();
		for (final Fact fact : facts)
		{
			final ImmutableList<FactLine> lines = fact.getLines();
			for (int i = 0; i < lines.size(); i++)
			{
				final FactLine factLine = lines.get(i);

				final FactLineMatchKey matchKey = extractMatchKey(factLine);
				FactAcctChanges userChanges = userChangesByMatchKey.get(matchKey);
				if (userChanges == null)
				{
					userChanges = extractUserChanges(factLine);
				}

				result.add(
						AcctRow.builder()
								.lookups(lookups)
								.currencyIdToCurrencyCodeConverter(moneyService)
								.factLine(factLine)
								.userChanges(userChanges)
								.rowId(DocumentId.of(matchKey.getAsString()))
								.build()
				);
			}
		}

		return result;
	}

	private static FactAcctChanges extractUserChanges(final FactLine factLine)
	{
		final PostingSign postingSign;
		final Money amount_LC; // amount (local currency)
		final Money amount_DC; // amount (document/foreign currency)
		if (factLine.getAmtSourceCr().signum() != 0)
		{
			postingSign = PostingSign.CREDIT;
			amount_DC = factLine.getAmtSourceCrAsMoney();
			amount_LC = factLine.getAmtAcctCrAsMoney();
		}
		else
		{
			postingSign = PostingSign.DEBIT;
			amount_DC = factLine.getAmtSourceDrAsMoney();
			amount_LC = factLine.getAmtAcctDrAsMoney();
		}

		return FactAcctChanges.builder()
				.matchKey(extractMatchKey(factLine))
				.acctSchemaId(factLine.getAcctSchemaId())
				.postingSign(postingSign)
				.accountId(factLine.getAccountId())
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

	private static FactLineMatchKey extractMatchKey(@NonNull final FactLine factLine)
	{
		return FactLineMatchKey.ofString(
				Util.ArrayKey.builder()
						// no need to include AD_Table_ID/Record_ID because we always match on document level
						.append(Math.max(factLine.getLine_ID(), 0))
						.append(factLine.getAccountConceptualName())
						.append(CostElementId.toRepoId(factLine.getCostElementId()))
						.append(TaxId.toRepoId(factLine.getTaxId()))
						.append(Math.max(factLine.getM_Locator_ID(), 0))
						.build()
						.toString()
		);
	}

	public void save(final List<AcctRow> rows, final TableRecordReference docRecordRef)
	{
		final ImmutableList<FactAcctChanges> factAcctChanges = rows.stream()
				.map(AcctRow::getUserChanges)
				.collect(ImmutableList.toImmutableList());

		factAcctUserChangesService.save(factAcctChanges, docRecordRef);
	}
}
