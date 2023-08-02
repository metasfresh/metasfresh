package de.metas.acct.acct_simulation;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaBL;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.factacct_userchanges.FactAcctChanges;
import de.metas.acct.factacct_userchanges.FactAcctChangesList;
import de.metas.acct.factacct_userchanges.FactAcctChangesType;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesService;
import de.metas.acct.factacct_userchanges.FactLineMatchKey;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.costing.CostElementId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyRate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
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
class AcctSimulationViewDataService
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

	public AcctSimulationViewData getViewData(final @NonNull AcctSimulationDocInfo docInfo, final @NonNull ViewId viewId)
	{
		return AcctSimulationViewData.builder()
				.dataService(this)
				.docInfo(docInfo)
				.viewId(viewId)
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

		final CurrencyId documentCurrencyId = acctDoc.getCurrencyIdOptional().orElseThrow(() -> new AdempiereException("Cannot determine document currency"));

		return AcctSimulationDocInfo.builder()
				.recordRef(acctDoc.getRecordRef())
				.clientId(clientId)
				.orgId(orgId)
				.acctSchemaId(acctSchemaId)
				.documentCurrencyId(documentCurrencyId)
				.documentCurrencyCode(moneyService.getCurrencyCodeByCurrencyId(documentCurrencyId))
				.localCurrencyId(localCurrencyId)
				.localCurrencyCode(moneyService.getCurrencyCodeByCurrencyId(localCurrencyId))
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
		final FactAcctChangesList userChangesList = factAcctUserChangesService.getByDocRecordRef(docInfo.getRecordRef());
		return retrieveRows(docInfo, userChangesList);
	}

	public List<AcctRow> retrieveRows(@NonNull final AcctSimulationDocInfo docInfo, @NonNull final FactAcctChangesList userChangesList)
	{
		final TableRecordReference docRecordRef = docInfo.getRecordRef();
		final ClientId clientId = docInfo.getClientId();
		final Doc<?> acctDoc = getAcctDoc(docRecordRef, clientId);
		acctDoc.setFactAcctChangesList(userChangesList);
		final List<Fact> facts = acctDoc.postLogic();

		final ArrayList<AcctRow> result = new ArrayList<>();

		//
		// First, add removals as "hidden" rows
		// We have to do this because they are not present in Fact/FactLine(s)
		final AcctRowCurrencyRate docCurrencyRate = getCurrencyRate(docInfo);
		userChangesList.forEachRemove(remove -> result.add(toRow(remove, docCurrencyRate)));

		//
		// Convert fact lines -> user changes -> rows
		for (final Fact fact : facts)
		{
			for (final FactLine factLine : fact.getLines())
			{
				result.add(toRow(factLine));
			}
		}

		return result;
	}

	private AcctRow toRow(@NonNull final FactLine factLine)
	{
		final FactAcctChanges userChanges = extractUserChanges(factLine);
		final AcctRowCurrencyRate currencyRate = AcctRowCurrencyRate.ofCurrencyRate(factLine.getCurrencyRateFromDocumentToAcctCurrency());
		return toRow(userChanges, currencyRate);
	}

	private AcctRow toRow(@NonNull final FactAcctChanges userChanges, @NonNull final AcctRowCurrencyRate currencyRate)
	{
		final DocumentId rowId;
		if (userChanges.getType().isAdd())
		{
			rowId = DocumentId.ofString(UUID.randomUUID().toString());
		}
		else
		{
			final FactLineMatchKey matchKey = Check.assumeNotNull(userChanges.getMatchKey(), "match key shall not be null: {}", userChanges);
			rowId = DocumentId.of(matchKey.getAsString());
		}

		return AcctRow.builder()
				.lookups(lookups)
				.currencyIdToCurrencyCodeConverter(moneyService)
				.userChanges(userChanges)
				.rowId(rowId)
				.currencyRate(currencyRate)
				.build();
	}

	private static FactAcctChanges extractUserChanges(final FactLine factLine)
	{
		if (factLine.getAppliedUserChanges() != null)
		{
			return factLine.getAppliedUserChanges();
		}

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
				.type(FactAcctChangesType.Change)
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

	public void save(@NonNull final FactAcctChangesList factAcctChangesList, @NonNull final AcctSimulationDocInfo docInfo)
	{
		factAcctUserChangesService.save(factAcctChangesList, docInfo.getRecordRef());
	}

	public AcctRow newRow(
			@NonNull final AcctSimulationDocInfo docInfo,
			@NonNull final PostingSign postingSign,
			@NonNull final Amount amount)
	{
		final AcctRowCurrencyRate currencyRate = getCurrencyRate(docInfo);
		final Money amount_DC = amount.toMoney(moneyService::getCurrencyIdByCurrencyCode).assertCurrencyId(docInfo.getDocumentCurrencyId());
		final Money amount_LC = currencyRate.convertToLocalCurrency(amount_DC).assertCurrencyId(docInfo.getLocalCurrencyId());

		return AcctRow.builder()
				.lookups(lookups)
				.currencyIdToCurrencyCodeConverter(moneyService)
				.userChanges(FactAcctChanges.builder()
						.type(FactAcctChangesType.Add)
						.matchKey(null)
						.acctSchemaId(docInfo.getAcctSchemaId())
						.postingSign(postingSign)
						.accountId(null)
						.amount_DC(amount_DC)
						.amount_LC(amount_LC)
						.build())
				.rowId(DocumentId.ofString(UUID.randomUUID().toString()))
				.currencyRate(currencyRate)
				.build();
	}

	private AcctRowCurrencyRate getCurrencyRate(final AcctSimulationDocInfo docInfo)
	{
		final CurrencyRate currencyRate = moneyService.getCurrencyRate(docInfo.getDocumentCurrencyId(), docInfo.getLocalCurrencyId(), docInfo.getCurrencyConversionContext());
		return AcctRowCurrencyRate.ofCurrencyRate(currencyRate);
	}
}
