package de.metas.acct.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaCosting;
import de.metas.acct.api.AcctSchemaDefaultAccounts;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.AcctSchemaValidCombinationOptions;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.cache.CCache;
import de.metas.costing.CostElementId;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_CostElement;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.model.MColumn;
import org.compiere.report.MReportTree;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class AcctSchemaDAO implements IAcctSchemaDAO
{
	private static final Logger logger = LogManager.getLogger(AcctSchemaDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	private final CCache<Integer, AcctSchemasMap> acctSchemasCache = CCache.<Integer, AcctSchemasMap>builder()
			.initialCapacity(1)
			.tableName(I_C_AcctSchema.Table_Name)
			.additionalTableNameToResetFor(I_C_AcctSchema_Default.Table_Name)
			.additionalTableNameToResetFor(I_C_AcctSchema_GL.Table_Name)
			.additionalTableNameToResetFor(I_C_AcctSchema_Element.Table_Name)
			.additionalTableNameToResetFor(I_C_AcctSchema_CostElement.Table_Name)
			.build();

	@Override
	public I_C_AcctSchema getRecordById(@NonNull final AcctSchemaId acctSchemaId)
	{
		return load(acctSchemaId, I_C_AcctSchema.class);
	}

	@Override
	public AcctSchema getByClientAndOrg(final Properties ctx)
	{
		final ClientId clientId = ClientId.ofRepoId(Env.getAD_Client_ID(ctx));
		final OrgId orgId = OrgId.ofRepoId(Env.getAD_Org_ID(ctx));

		return getByClientAndOrg(clientId, orgId);
	}

	@Override
	@NonNull
	public final AcctSchema getByClientAndOrg(final ClientId clientId, final OrgId orgId)
	{
		final AcctSchemaId acctSchemaId = getAcctSchemaIdByClientAndOrg(clientId, orgId);
		return getById(acctSchemaId);
	}

	@Nullable
	public final AcctSchema getByClientAndOrgOrNull(@NonNull final ClientId clientId, @NonNull final OrgId orgId)
	{
		final AcctSchemaId acctSchemaId = getAcctSchemaIdByClientAndOrgOrNull(clientId, orgId);
		if (acctSchemaId == null)
		{
			return null;
		}
		return getById(acctSchemaId);
	}

	@Override
	@NonNull
	public AcctSchemaId getAcctSchemaIdByClientAndOrg(@NonNull final ClientId clientId, @NonNull final OrgId orgId)
	{
		final AcctSchemaId acctSchemaId = getAcctSchemaIdByClientAndOrgOrNull(clientId, orgId);
		if (acctSchemaId == null)
		{
			throw new AdempiereException("No accounting schema found for AD_Client_ID=" + clientId + " and AD_Org_ID=" + orgId);
		}
		return acctSchemaId;
	}

	protected AcctSchemaId getAcctSchemaIdByClientAndOrgOrNull(@NonNull final ClientId clientId, @NonNull final OrgId orgId)
	{
		return AcctSchemaId.ofRepoIdOrNull(DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT getC_AcctSchema_ID(?,?)", clientId, orgId));
	}

	@Override
	public AcctSchema getById(@NonNull final AcctSchemaId acctSchemaId)
	{
		return getAcctSchemasMap().getById(acctSchemaId);
	}

	@Override
	public List<AcctSchema> getAllByClient(@NonNull final ClientId clientId)
	{
		final AcctSchemaId primaryAcctSchemaId = getPrimaryAcctSchemaId(clientId);
		return getAcctSchemasMap().getByClientId(clientId, primaryAcctSchemaId);
	}

	@Override
	@Nullable
	public AcctSchemaId getPrimaryAcctSchemaId(final ClientId clientId)
	{
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final I_AD_ClientInfo clientInfo = clientDAO.retrieveClientInfo(Env.getCtx(), clientId.getRepoId());
		return AcctSchemaId.ofRepoIdOrNull(clientInfo.getC_AcctSchema1_ID());
	}

	@Override
	public List<AcctSchema> getByChartOfAccountsId(@NonNull final ChartOfAccountsId chartOfAccountsId)
	{
		return getAcctSchemasMap().getByChartOfAccountsId(chartOfAccountsId);
	}

	private AcctSchemasMap getAcctSchemasMap()
	{
		return acctSchemasCache.getOrLoad(0, this::retrieveAcctSchemasMap);
	}

	private AcctSchemasMap retrieveAcctSchemasMap()
	{
		final ImmutableList<AcctSchema> acctSchemas = queryBL
				.createQueryBuilder(I_C_AcctSchema.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::toAcctSchema)
				.collect(ImmutableList.toImmutableList());

		return new AcctSchemasMap(acctSchemas);
	}

	private AcctSchema toAcctSchema(@NonNull final I_C_AcctSchema acctSchemaRecord)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID());

		final CurrencyId acctCurrencyId = CurrencyId.ofRepoId(acctSchemaRecord.getC_Currency_ID());
		final Currency acctCurrency = currencyDAO.getById(acctCurrencyId);
		final CurrencyPrecision standardPrecision = acctCurrency.getPrecision();
		final CurrencyPrecision costingPrecision = acctCurrency.getCostingPrecision();

		final I_C_AcctSchema_GL acctSchemaGL = retrieveAcctSchemaGLRecordOrNull(acctSchemaId);
		if (acctSchemaGL == null)
		{
			throw new AdempiereException("No " + I_C_AcctSchema_GL.class + " found for " + acctSchemaId);
		}

		return AcctSchema.builder()
				.id(acctSchemaId)
				.clientId(ClientId.ofRepoId(acctSchemaRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(acctSchemaRecord.getAD_Org_ID()))
				.name(acctSchemaRecord.getName())
				//
				.currencyId(acctCurrencyId)
				.standardPrecision(standardPrecision)
				//
				// Costing
				.costing(toAcctSchemaCosting(acctSchemaRecord, costingPrecision))
				//
				// Accounts
				.validCombinationOptions(toAcctSchemaValidCombinationOptions(acctSchemaRecord))
				//
				// Accounting
				.postOnlyForOrgIds(retrievePostOnlyForOrgIds(acctSchemaRecord))
				.taxCorrectionType(TaxCorrectionType.ofCode(acctSchemaRecord.getTaxCorrectionType()))
				.accrual(acctSchemaRecord.isAccrual())
				.allowNegativePosting(acctSchemaRecord.isAllowNegativePosting())
				.postTradeDiscount(acctSchemaRecord.isTradeDiscountPosted())
				.postServices(acctSchemaRecord.isPostServices())
				.postIfSameClearingAccounts(acctSchemaRecord.isPostIfClearingEqual())
				.isAllowMultiDebitAndCredit(acctSchemaRecord.isAllowMultiDebitAndCredit())
				//
				.isAutoSetDebtoridAndCreditorid(acctSchemaRecord.isAutoSetDebtoridAndCreditorid())
				.debtorIdPrefix(acctSchemaRecord.getDebtorIdPrefix())
				.creditorIdPrefix(acctSchemaRecord.getCreditorIdPrefix())
				//
				.generalLedger(toAcctSchemaGeneralLedger(acctSchemaGL))
				//
				.defaultAccounts(retrieveAcctSchemaDefaults(acctSchemaId))
				//
				.periodControl(toAcctSchemaPeriodControl(acctSchemaRecord))
				//
				.schemaElements(retrieveAcctSchemaElementsMap(acctSchemaId))
				//
				.build();
	}

	private AcctSchemaCosting toAcctSchemaCosting(final I_C_AcctSchema acctSchemaRecord, final CurrencyPrecision costingPrecision)
	{
		final ImmutableSet<CostElementId> postOnlyCostElementIds = retrievePostOnlyForCostElementIds(AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID()));

		return AcctSchemaCosting.builder()
				.costingPrecision(costingPrecision)
				.costTypeId(CostTypeId.ofRepoId(acctSchemaRecord.getM_CostType_ID()))
				.costingLevel(CostingLevel.forCode(acctSchemaRecord.getCostingLevel()))
				.costingMethod(CostingMethod.ofCode(acctSchemaRecord.getCostingMethod()))
				.postOnlyCostElementIds(postOnlyCostElementIds)
				.build();
	}

	private AcctSchemaValidCombinationOptions toAcctSchemaValidCombinationOptions(final I_C_AcctSchema acctSchemaRecord)
	{
		return AcctSchemaValidCombinationOptions.builder()
				.separator(acctSchemaRecord.getSeparator())
				.useAccountAlias(acctSchemaRecord.isHasAlias())
				.build();
	}

	private ImmutableSet<OrgId> retrievePostOnlyForOrgIds(final I_C_AcctSchema acctSchemaRecord)
	{
		final OrgId onlyOrgId = OrgId.ofRepoIdOrNull(acctSchemaRecord.getAD_OrgOnly_ID());
		if (onlyOrgId == null || onlyOrgId.isAny())
		{
			return ImmutableSet.of();
		}

		final ImmutableSet.Builder<OrgId> onlyOrgIds = ImmutableSet.builder();
		onlyOrgIds.add(onlyOrgId);

		final I_AD_Org onlyOrg = Services.get(IOrgDAO.class).getById(onlyOrgId);
		if (onlyOrg.isSummary())
		{
			onlyOrgIds.addAll(MReportTree.getChildOrgIds(onlyOrgId));
		}

		return onlyOrgIds.build();
	}

	private AcctSchemaGeneralLedger toAcctSchemaGeneralLedger(final I_C_AcctSchema_GL acctSchemaGL)
	{
		final boolean suspenseBalancing = acctSchemaGL.isUseSuspenseBalancing() && acctSchemaGL.getSuspenseBalancing_Acct() > 0;
		final AccountId suspenseBalancingAcctId = suspenseBalancing ? AccountId.ofRepoId(acctSchemaGL.getSuspenseBalancing_Acct()) : null;

		final boolean useCurrencyBalancing = acctSchemaGL.isUseCurrencyBalancing();
		final AccountId currencyBalancingAcctId = useCurrencyBalancing ? AccountId.ofRepoId(acctSchemaGL.getCurrencyBalancing_Acct()) : null;

		return AcctSchemaGeneralLedger.builder()
				.suspenseBalancing(suspenseBalancing)
				.suspenseBalancingAcctId(suspenseBalancingAcctId)
				//
				.currencyBalancing(useCurrencyBalancing)
				.currencyBalancingAcctId(currencyBalancingAcctId)
				//
				.intercompanyDueToAcctId(AccountId.ofRepoId(acctSchemaGL.getIntercompanyDueTo_Acct()))
				.intercompanyDueFromAcctId(AccountId.ofRepoId(acctSchemaGL.getIntercompanyDueFrom_Acct()))
				//
				.incomeSummaryAcctId(AccountId.ofRepoId(acctSchemaGL.getIncomeSummary_Acct()))
				.retainedEarningAcctId(AccountId.ofRepoId(acctSchemaGL.getRetainedEarning_Acct()))
				//
				.purchasePriceVarianceOffsetAcctId(AccountId.ofRepoId(acctSchemaGL.getPPVOffset_Acct()))
				//
				.build();
	}

	private AcctSchemaDefaultAccounts retrieveAcctSchemaDefaults(@NonNull final AcctSchemaId acctSchemaId)
	{
		final I_C_AcctSchema_Default record = retrieveAcctSchemaDefaultsRecordOrNull(acctSchemaId);
		if (record == null)
		{
			throw new AdempiereException("No " + I_C_AcctSchema_Default.class + " found for " + acctSchemaId);
		}
		return toAcctSchemaDefaults(record);
	}

	@Override
	@Nullable
	public I_C_AcctSchema_Default retrieveAcctSchemaDefaultsRecordOrNull(final AcctSchemaId acctSchemaId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_AcctSchema_Default.class)
				.addEqualsFilter(I_C_AcctSchema_Default.COLUMN_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnly(I_C_AcctSchema_Default.class);
	}

	private static AcctSchemaDefaultAccounts toAcctSchemaDefaults(@NonNull final I_C_AcctSchema_Default record)
	{
		return AcctSchemaDefaultAccounts.builder()
				.realizedGainAcctId(AccountId.ofRepoId(record.getRealizedGain_Acct()))
				.realizedLossAcctId(AccountId.ofRepoId(record.getRealizedLoss_Acct()))
				.unrealizedGainAcctId(AccountId.ofRepoId(record.getUnrealizedGain_Acct()))
				.unrealizedLossAcctId(AccountId.ofRepoId(record.getUnrealizedLoss_Acct()))
				.build();
	}

	private AcctSchemaPeriodControl toAcctSchemaPeriodControl(final I_C_AcctSchema acctSchemaRecord)
	{
		return AcctSchemaPeriodControl.builder()
				.automaticPeriodControl(acctSchemaRecord.isAutoPeriodControl())
				.openDaysInPast(acctSchemaRecord.getPeriod_OpenHistory())
				.openDaysInFuture(acctSchemaRecord.getPeriod_OpenFuture())
				.build();
	}

	private AcctSchemaElementsMap retrieveAcctSchemaElementsMap(@NonNull final AcctSchemaId acctSchemaId)
	{
		final List<AcctSchemaElement> elements = queryBL.createQueryBuilderOutOfTrx(I_C_AcctSchema_Element.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.create()
				.stream()
				.map(AcctSchemaDAO::toAcctSchemaElement)
				.collect(ImmutableList.toImmutableList());

		return AcctSchemaElementsMap.of(elements);
	}

	private static AcctSchemaElement toAcctSchemaElement(final I_C_AcctSchema_Element record)
	{
		final AcctSchemaElementType elementType = AcctSchemaElementType.ofCode(record.getElementType());
		final AcctSchemaElement element = AcctSchemaElement.builder()
				.elementType(elementType)
				.name(record.getName())
				.seqNo(record.getSeqNo())
				//
				.defaultValue(getDefaultValue(record))
				.chartOfAccountsId(ChartOfAccountsId.ofRepoIdOrNull(record.getC_Element_ID()))
				//
				.displayColumnName(getDisplayColumnName(elementType, record.getAD_Column_ID()))
				//
				.mandatory(record.isMandatory())
				.displayedInEditor(record.isDisplayInEditor())
				.balanced(record.isBalanced())
				//
				.build();
		if (element.isMandatory() && element.getDefaultValue() <= 0)
		{
			logger.error("No default value for {}", element);
		}
		return element;
	}

	private static int getDefaultValue(final I_C_AcctSchema_Element ase)
	{
		final AcctSchemaElementType elementType = AcctSchemaElementType.ofCode(ase.getElementType());
		final int defaultValue;

		if (elementType.equals(AcctSchemaElementType.Organization))
		{
			defaultValue = ase.getOrg_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Account))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.BPartner))
		{
			defaultValue = ase.getC_BPartner_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Product))
		{
			defaultValue = ase.getM_Product_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Activity))
		{
			defaultValue = ase.getC_Activity_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.LocationFrom))
		{
			defaultValue = ase.getC_Location_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.LocationTo))
		{
			defaultValue = ase.getC_Location_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Campaign))
		{
			defaultValue = ase.getC_Campaign_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.OrgTrx))
		{
			defaultValue = ase.getOrg_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Project))
		{
			defaultValue = ase.getC_Project_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.SalesRegion))
		{
			defaultValue = ase.getC_SalesRegion_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserList1))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserList2))
		{
			defaultValue = ase.getC_ElementValue_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElement1))
		{
			defaultValue = 0;
		}
		else if (elementType.equals(AcctSchemaElementType.UserElement2))
		{
			defaultValue = 0;
		}
		else
		{
			defaultValue = 0;
		}
		return defaultValue;
	}

	private static String getDisplayColumnName(final AcctSchemaElementType elementType, final int adColumnId)
	{
		if (AcctSchemaElementType.UserElement1.equals(elementType)
				|| AcctSchemaElementType.UserElement2.equals(elementType))
		{
			return MColumn.getColumnName(Env.getCtx(), adColumnId);
		}
		else
		{
			return elementType.getColumnName();
		}
	}

	@Override
	@Nullable
	public I_C_AcctSchema_GL retrieveAcctSchemaGLRecordOrNull(@NonNull final AcctSchemaId acctSchemaId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_AcctSchema_GL.class)
				.addEqualsFilter(I_C_AcctSchema_GL.COLUMN_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnly(I_C_AcctSchema_GL.class);
	}

	@Override
	public void changeAcctSchemaAutomaticPeriodId(@NonNull final AcctSchemaId acctSchemaId, final int periodId)
	{
		Check.assumeGreaterThanZero(periodId, "periodId");

		final I_C_AcctSchema acctSchemaRecord = InterfaceWrapperHelper.loadOutOfTrx(acctSchemaId, I_C_AcctSchema.class);
		Check.assumeNotNull(acctSchemaRecord, "Accounting schema shall exists for {}", acctSchemaId);

		acctSchemaRecord.setC_Period_ID(periodId);
		InterfaceWrapperHelper.saveRecord(acctSchemaRecord);
	}

	private ImmutableSet<CostElementId> retrievePostOnlyForCostElementIds(@NonNull final AcctSchemaId acctSchemaId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_C_AcctSchema_CostElement.class)
				.addEqualsFilter(I_C_AcctSchema_CostElement.COLUMN_C_AcctSchema_ID, acctSchemaId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_C_AcctSchema_CostElement.COLUMNNAME_M_CostElement_ID, Integer.class)
				.stream()
				.map(CostElementId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@ToString
	private static class AcctSchemasMap
	{
		private final ImmutableMap<AcctSchemaId, AcctSchema> acctSchemas;

		public AcctSchemasMap(final List<AcctSchema> acctSchemas)
		{
			this.acctSchemas = Maps.uniqueIndex(acctSchemas, AcctSchema::getId);
		}

		public AcctSchema getById(@NonNull final AcctSchemaId acctSchemaId)
		{
			final AcctSchema acctSchema = acctSchemas.get(acctSchemaId);
			if (acctSchema == null)
			{
				throw new AdempiereException("No accounting schema found for " + acctSchemaId);
			}
			return acctSchema;
		}

		public List<AcctSchema> getByClientId(@NonNull final ClientId clientId, @Nullable final AcctSchemaId primaryAcctSchemaId)
		{
			final ImmutableList.Builder<AcctSchema> result = ImmutableList.builder();

			// Primary accounting schema shall be returned first
			if (primaryAcctSchemaId != null)
			{
				result.add(getById(primaryAcctSchemaId));
			}

			for (final AcctSchema acctSchema : acctSchemas.values())
			{
				if (acctSchema.getId().equals(primaryAcctSchemaId))
				{
					continue;
				}

				if (clientId.equals(acctSchema.getClientId()))
				{
					result.add(acctSchema);
				}
			}

			return result.build();
		}

		public List<AcctSchema> getByChartOfAccountsId(@NonNull final ChartOfAccountsId chartOfAccountsId)
		{
			return acctSchemas.values()
					.stream()
					.filter(acctSchema -> ChartOfAccountsId.equals(acctSchema.getChartOfAccountsId(), chartOfAccountsId))
					.collect(ImmutableList.toImmutableList());
		}
	}
}
