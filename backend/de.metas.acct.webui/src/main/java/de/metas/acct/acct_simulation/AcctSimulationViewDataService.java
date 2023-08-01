package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.factacct_userchanges.FactAcctChanges;
import de.metas.acct.factacct_userchanges.FactAcctChangesList;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesService;
import de.metas.acct.factacct_userchanges.FactLineMatchKey;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyRate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

	public AcctSimulationViewData getViewData(final @NonNull AcctSimulationDocInfo docInfo)
	{
		return AcctSimulationViewData.builder()
				.dataService(this)
				.docInfo(docInfo)
				.build();
	}

	public AcctSimulationDocInfo getDocInfo(
			final @NonNull TableRecordReference docRecordRef,
			final @NonNull ClientId clientId)
	{
		final Doc<?> acctDoc = getAcctDoc(docRecordRef, clientId);
		return extractAcctSimulationDocInfo(acctDoc);
	}

	private AcctSimulationDocInfo extractAcctSimulationDocInfo(@NonNull final Doc<?> acctDoc)
	{
		final ClientId clientId = acctDoc.getClientId();
		final OrgId orgId = acctDoc.getOrgId();
		final AcctSchemaId acctSchemaId = acctSchemaBL.getAcctSchemaIdByClientAndOrg(clientId, orgId);
		final AcctSchema acctSchema = acctSchemaBL.getById(acctSchemaId);
		final CurrencyId localCurrencyId = acctSchema.getCurrencyId();

		return AcctSimulationDocInfo.builder()
				.recordRef(acctDoc.getRecordRef())
				.clientId(clientId)
				.orgId(orgId)
				.acctSchemaId(acctSchemaId)
				.documentCurrencyId(acctDoc.getCurrencyIdOptional()
						.orElseThrow(() -> new AdempiereException("Cannot determine document currency")))
				.localCurrencyId(localCurrencyId)
				.currencyConversionContext(acctDoc.getCurrencyConversionContext(acctSchema))
				.build();
	}

	@NonNull
	private Doc<?> getAcctDoc(final @NonNull TableRecordReference docRecordRef, final @NonNull ClientId clientId)
	{
		final List<AcctSchema> acctSchemas = acctSchemaBL.getAllByClientId(clientId);
		return acctDocRegistry.get(acctSchemas, docRecordRef);
	}

	public List<AcctRow> retrieveRows(final @NonNull AcctSimulationDocInfo docInfo)
	{
		final TableRecordReference docRecordRef = docInfo.getRecordRef();
		final ClientId clientId = docInfo.getClientId();
		final List<Fact> facts = getAcctDoc(docRecordRef, clientId).postLogic();

		final FactAcctChangesList userChangesList = factAcctUserChangesService.getByDocRecordRef(docRecordRef);

		final ArrayList<AcctRow> result = new ArrayList<>();
		for (final Fact fact : facts)
		{
			final ImmutableList<FactLine> lines = fact.getLines();
			for (int i = 0; i < lines.size(); i++)
			{
				final FactLine factLine = lines.get(i);

				final FactLineMatchKey matchKey = extractMatchKey(factLine);
				final FactAcctChanges userChanges = userChangesList.getByMatchKey(matchKey)
						.orElseGet(() -> extractUserChanges(factLine));

				result.add(
						AcctRow.builder()
								.lookups(lookups)
								.currencyIdToCurrencyCodeConverter(moneyService)
								.userChanges(userChanges)
								.rowId(DocumentId.of(matchKey.getAsString()))
								.currencyRate(AcctRowCurrencyRate.ofCurrencyRate(factLine.getCurrencyRateFromDocumentToAcctCurrency()))
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

	public void save(@NonNull final List<AcctRow> rows, @NonNull final AcctSimulationDocInfo docInfo)
	{
		final FactAcctChangesList factAcctChanges = rows.stream()
				.map(AcctRow::getUserChanges)
				.collect(FactAcctChangesList.collect());

		factAcctUserChangesService.save(factAcctChanges, docInfo.getRecordRef());
	}

	public AcctRow newRow(final AcctSimulationDocInfo docInfo)
	{
		final FactLineMatchKey matchKey = FactLineMatchKey.ofString(UUID.randomUUID().toString());

		final CurrencyRate currencyRate = moneyService.getCurrencyRate(docInfo.getDocumentCurrencyId(), docInfo.getLocalCurrencyId(), docInfo.getCurrencyConversionContext());

		return AcctRow.builder()
				.lookups(lookups)
				.currencyIdToCurrencyCodeConverter(moneyService)
				.userChanges(FactAcctChanges.builder()
						.matchKey(matchKey)
						.acctSchemaId(docInfo.getAcctSchemaId())
						.postingSign(PostingSign.DEBIT)
						.accountId(null)
						.amount_DC(Money.zero(docInfo.getDocumentCurrencyId()))
						.amount_LC(Money.zero(docInfo.getLocalCurrencyId()))
						.build())
				.rowId(DocumentId.of(matchKey.getAsString()))
				.currencyRate(AcctRowCurrencyRate.ofCurrencyRate(currencyRate))
				.build();
	}
}
