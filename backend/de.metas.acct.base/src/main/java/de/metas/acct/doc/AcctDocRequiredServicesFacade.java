package de.metas.acct.doc;

import de.metas.acct.Account;
import de.metas.acct.GLCategoryId;
import de.metas.acct.GLCategoryRepository;
import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.accounts.AccountProviderFactory;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.api.impl.FactAcctDAO;
import de.metas.acct.factacct_userchanges.FactAcctChangesList;
import de.metas.acct.factacct_userchanges.FactAcctUserChangesService;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailCreateResultsList;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostingService;
import de.metas.costing.IProductCostingBL;
import de.metas.costing.MoveCostsRequest;
import de.metas.costing.MoveCostsResult;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.acct.InvoiceAcct;
import de.metas.invoice.acct.InvoiceAcctRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.location.LocationId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.sales_region.SalesRegion;
import de.metas.sales_region.SalesRegionId;
import de.metas.sales_region.SalesRegionService;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.acct.FactLine;
import org.compiere.acct.PostingStatus;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.util.TrxRunnable2;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.Optional;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@RequiredArgsConstructor
public class AcctDocRequiredServicesFacade
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private final IFactAcctListenersService factAcctListenersService = Services.get(IFactAcctListenersService.class);
	private final IPostingService postingService = Services.get(IPostingService.class);
	private final ModelCacheInvalidationService modelCacheInvalidationService;

	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final IFactAcctBL factAcctBL = Services.get(IFactAcctBL.class);
	@Getter
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final ElementValueService elementValueService;

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	@Getter private final QuantityUOMConverter uomConverter = Services.get(IUOMConversionBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IVATCodeDAO vatCodeDAO = Services.get(IVATCodeDAO.class);
	private final GLCategoryRepository glCategoryRepository;
	private final BankAccountService bankAccountService;
	private final AccountProviderFactory accountProviderFactory;
	private final InvoiceAcctRepository invoiceAcctRepository;
	@Getter
	private final MatchInvoiceService matchInvoiceService;
	@Getter
	private final OrderCostService orderCostService;
	private final FAOpenItemsService faOpenItemsService;

	//
	// Needed for DocLine:
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

	private final ICostingService costingService;
	private final DimensionService dimensionService;
	private final SalesRegionService salesRegionService;
	private final AcctDocLockService acctDocLockService;
	private final FactAcctUserChangesService factAcctUserChangesService;

	public void fireBeforePostEvent(@NonNull AcctDocModel docModel)
	{
		factAcctListenersService.fireBeforePost(docModel.unbox());
	}

	public void fireAfterPostEvent(@NonNull final AcctDocModel docModel)
	{
		factAcctListenersService.fireAfterPost(docModel.unbox());
	}

	public void fireDocumentChanged(
			final String documentTableName,
			final int documentRecordId)
	{
		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.fromTableNameAndRecordId(documentTableName, documentRecordId),
				ModelCacheInvalidationTiming.AFTER_CHANGE);
	}

	public void runInThreadInheritedTrx(@NonNull final TrxRunnable2 runnable)
	{
		trxManager.runInThreadInheritedTrx(runnable);
	}

	public void deleteFactAcctByDocumentModel(@NonNull final AcctDocModel docModel)
	{
		factAcctDAO.deleteForDocumentModel(docModel.unbox());
	}

	public boolean getSysConfigBooleanValue(@NonNull final String sysConfigName)
	{
		final boolean defaultValue = false;
		return getSysConfigBooleanValue(sysConfigName, defaultValue);
	}

	public boolean getSysConfigBooleanValue(
			@NonNull final String sysConfigName,
			final boolean defaultValue)
	{
		return sysConfigBL.getBooleanValue(sysConfigName, defaultValue);
	}

	public ITranslatableString translate(@NonNull final AdMessageKey adMessage)
	{
		return msgBL.getTranslatableMsgText(adMessage);
	}

	public AccountProvider.AccountProviderBuilder newAccountProvider()
	{
		return accountProviderFactory.newAccountProvider();
	}

	@NonNull
	public MAccount getAccountById(@NonNull final AccountId accountId)
	{
		return accountDAO.getById(accountId);
	}

	public Account getAccount(@NonNull final I_Fact_Acct factAcct)
	{
		return factAcctBL.getAccount(factAcct);
	}

	public ElementValue getElementValueById(@NonNull final ElementValueId elementValueId)
	{
		return elementValueService.getById(elementValueId);
	}

	public ElementValueId getElementValueId(@NonNull final Account account)
	{
		final MAccount validCombination = accountDAO.getById(account.getAccountId());
		return validCombination.getElementValueId();
	}

	public CurrencyPrecision getCurrencyStandardPrecision(@NonNull final CurrencyId currencyId)
	{
		return currencyDAO.getStdPrecision(currencyId);
	}

	public final CurrencyConversionContext createCurrencyConversionContext(
			@Nullable final LocalDateAndOrgId convDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId)
	{
		return currencyConversionBL.createCurrencyConversionContext(convDate, conversionTypeId, clientId);
	}

	public CurrencyRate getCurrencyRate(
			final CurrencyConversionContext conversionCtx,
			final CurrencyId currencyFromId,
			final CurrencyId currencyToId)
	{
		return currencyConversionBL.getCurrencyRate(conversionCtx, currencyFromId, currencyToId);
	}

	public BankAccount getBankAccountById(final BankAccountId bpBankAccountId)
	{
		return bankAccountService.getById(bpBankAccountId);
	}

	public void postImmediateNoFail(
			@NonNull final TableRecordReference documentRef,
			@NonNull final ClientId clientId)
	{
		postingService.newPostingRequest()
				.setClientId(clientId)
				.setDocumentRef(documentRef)
				.setFailOnError(false) // don't fail because we don't want to fail the main document posting because one of it's depending documents are failing
				.setPostImmediate(PostImmediate.Yes) // yes, post it immediate
				.setForce(false) // don't force it
				.setPostWithoutServer() // post directly (don't contact the server) because we want to post on client or server like the main document
				.postIt(); // do it!

	}

	public I_M_Product getProductById(@NonNull final ProductId productId)
	{
		return productBL.getById(productId);
	}

	public String getProductName(final ProductId productId)
	{
		return productBL.getProductName(productId);
	}

	public boolean isProductStocked(final I_M_Product product)
	{
		return productBL.isStocked(product);
	}

	public boolean isProductStocked(final ProductId productId)
	{
		return productBL.isStocked(productId);
	}

	public I_C_UOM getProductStockingUOM(final ProductId productId)
	{
		return productBL.getStockUOM(productId);
	}

	public UomId getProductStockingUOMId(final ProductId productId)
	{
		return productBL.getStockUOMId(productId);
	}

	public CostingMethod getCostingMethod(
			@NonNull final ProductId productId,
			@NonNull final AcctSchema as)
	{
		return productCostingBL.getCostingMethod(productId, as);
	}

	public CostingLevel getCostingLevel(
			@NonNull final ProductId productId,
			@NonNull final AcctSchema as)
	{
		return productCostingBL.getCostingLevel(productId, as);
	}

	public CostElement getCostElementById(@NonNull final CostElementId costElementId)
	{
		return costingService.getCostElementById(costElementId);
	}

	public CostDetailCreateResultsList createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		return costingService.createCostDetail(request);
	}

	@SuppressWarnings("UnusedReturnValue")
	public ExplainedOptional<CostDetailCreateResultsList> createCostDetailOrEmpty(@NonNull final CostDetailCreateRequest request)
	{
		return costingService.createCostDetailOrEmpty(request);
	}

	public MoveCostsResult moveCosts(@NonNull final MoveCostsRequest request)
	{
		return costingService.moveCosts(request);
	}

	public CostDetailCreateResultsList createReversalCostDetails(@NonNull final CostDetailReverseRequest request)
	{
		return costingService.createReversalCostDetails(request);
	}

	public ExplainedOptional<CostDetailCreateResultsList> createReversalCostDetailsOrEmpty(@NonNull final CostDetailReverseRequest request)
	{
		return costingService.createReversalCostDetailsOrEmpty(request);
	}

	public Optional<CostPrice> getCurrentCostPrice(
			@NonNull final CostSegment costSegment,
			@NonNull final CostingMethod costingMethod)
	{
		return costingService.getCurrentCostPrice(costSegment, costingMethod);
	}

	public AdIssueId createIssue(@NonNull final PostingException exception)
	{
		return errorManager.createIssue(exception);
	}

	public void markIssueDeprecated(@NonNull final AdIssueId adIssueId)
	{
		errorManager.markIssueAcknowledged(adIssueId);
	}

	public ZoneId getTimeZone(@NonNull final OrgId orgId)
	{
		return orgDAO.getTimeZone(orgId);
	}

	public Optional<InvoiceAcct> getInvoiceAcct(@NonNull final InvoiceId invoiceId)
	{
		return invoiceAcctRepository.getById(invoiceId);
	}

	public I_C_DocType getDocTypeById(@NonNull final DocTypeId docTypeId)
	{
		return docTypeBL.getById(docTypeId);
	}

	public Optional<GLCategoryId> getDefaultGLCategoryId(@NonNull final ClientId clientId)
	{
		return glCategoryRepository.getDefaultId(clientId);
	}

	public Optional<LocationId> getLocationId(@Nullable final BPartnerLocationId bpartnerLocationId)
	{
		if (bpartnerLocationId == null)
		{
			return Optional.empty();
		}

		final LocationId locationId = bpartnerDAO.getLocationId(bpartnerLocationId);
		return Optional.of(locationId);
	}

	public Optional<LocationId> getLocationId(@NonNull final OrgId orgId)
	{
		if (!orgId.isRegular())
		{
			return Optional.empty();
		}

		final BPartnerLocationId bpartnerLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(orgId);
		if (bpartnerLocationId == null)
		{
			return Optional.empty();
		}

		final LocationId locationId = bpartnerDAO.getLocationId(bpartnerLocationId);
		return Optional.of(locationId);
	}

	public Optional<LocationId> getLocationIdByLocatorRepoId(final int locatorRepoId)
	{
		if (locatorRepoId <= 0)
		{
			return Optional.empty();
		}

		return warehouseBL.getLocationIdByLocatorRepoId(locatorRepoId);
	}

	public Tax getTaxById(@NonNull final TaxId taxId)
	{
		return taxDAO.getTaxById(taxId);
	}

	public OrgId getOrgIdByLocatorRepoId(final int locatorId)
	{
		return warehouseBL.getOrgIdByLocatorRepoId(locatorId);
	}

	public Optional<VATCode> findVATCode(final VATCodeMatchingRequest request)
	{
		return vatCodeDAO.findVATCode(request);
	}

	public Dimension extractDimensionFromModel(final Object model)
	{
		return dimensionService.getFromRecord(model);
	}

	public Quantity convertQuantityTo(
			@NonNull final Quantity quantity,
			@Nullable final ProductId productId,
			@NonNull final UomId uomToId)
	{
		return uomConverter.convertQuantityTo(quantity, productId, uomToId);
	}

	public Optional<FAOpenItemTrxInfo> computeOpenItemTrxInfo(FactLine factLine)
	{
		return faOpenItemsService.computeTrxInfo(factLine);
	}

	public void saveNew(@NonNull final I_Fact_Acct factAcct) {factAcctDAO.save(factAcct);}

	public void saveNew(@NonNull final FactLine factLine)
	{
		if (factLine.getIdOrNull() != null)
		{
			throw new AdempiereException("Already created: " + factLine);
		}

		factLine.updateBeforeSaveNew();

		final I_Fact_Acct record = InterfaceWrapperHelper.newInstance(I_Fact_Acct.class);

		record.setCounterpart_Fact_Acct_ID(FactAcctId.toRepoId(factLine.getCounterpart_Fact_Acct_ID()));
		Check.assumeEquals(record.getAD_Client_ID(), factLine.getAD_Client_ID().getRepoId(), "AD_Client_ID");
		record.setAD_Org_ID(factLine.getOrgId().getRepoId());
		record.setDateTrx(factLine.getDateTrx());
		record.setDateAcct(factLine.getDateAcct());
		record.setC_Period_ID(factLine.getC_Period_ID());
		//
		record.setAmtAcctDr(factLine.getAmtAcctDr());
		record.setAmtAcctCr(factLine.getAmtAcctCr());
		record.setC_Currency_ID(factLine.getCurrencyId().getRepoId());
		record.setAmtSourceDr(factLine.getAmtSourceDr());
		record.setAmtSourceCr(factLine.getAmtSourceCr());
		record.setCurrencyRate(factLine.getCurrencyRate());
		//
		record.setC_Tax_ID(TaxId.toRepoId(factLine.getTaxId()));
		record.setVATCode(factLine.getVatCode());
		//
		record.setAD_Table_ID(factLine.getDocRecordRef().getAD_Table_ID());
		record.setRecord_ID(factLine.getDocRecordRef().getRecord_ID());
		record.setLine_ID(factLine.getLine_ID());
		record.setSubLine_ID(factLine.getSubLine_ID());
		record.setDocumentNo(factLine.getDocumentNo());
		record.setDocBaseType(factLine.getDocBaseType().getCode());
		record.setDocStatus(DocStatus.toCodeOrNull(factLine.getDocStatus()));
		record.setC_DocType_ID(DocTypeId.toRepoId(factLine.getC_DocType_ID()));
		//
		record.setM_Product_ID(ProductId.toRepoId(factLine.getM_Product_ID()));
		record.setM_Locator_ID(factLine.getM_Locator_ID());
		record.setQty(factLine.getQty() != null ? factLine.getQty().toBigDecimal() : null);
		record.setC_UOM_ID(factLine.getQty() != null ? factLine.getQty().getUomId().getRepoId() : -1);
		//
		record.setPostingType(factLine.getPostingType().getCode());
		record.setC_AcctSchema_ID(factLine.getAcctSchemaId().getRepoId());
		record.setAccount_ID(factLine.getAccountId().getRepoId());
		record.setC_SubAcct_ID(factLine.getC_SubAcct_ID());
		FactAcctDAO.setAccountConceptualName(record, factLine.getAccountConceptualName());
		//
		record.setM_CostElement_ID(CostElementId.toRepoId(factLine.getCostElementId()));
		//
		record.setDescription(StringUtils.trimBlankToNull(factLine.getDescription()));
		//
		record.setC_OrderSO_ID(OrderId.toRepoId(factLine.getC_OrderSO_ID()));
		record.setC_LocFrom_ID(LocationId.toRepoId(factLine.getC_LocFrom_ID()));
		record.setC_LocTo_ID(LocationId.toRepoId(factLine.getC_LocTo_ID()));
		record.setM_SectionCode_ID(SectionCodeId.toRepoId(factLine.getM_SectionCode_ID()));
		record.setAD_OrgTrx_ID(OrgId.toRepoId(factLine.getAD_OrgTrx_ID()));
		record.setC_BPartner_ID(BPartnerId.toRepoId(factLine.getC_BPartner_ID()));
		record.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(factLine.getC_BPartner_Location_ID()));
		record.setC_BPartner2_ID(BPartnerId.toRepoId(factLine.getC_BPartner2_ID()));
		record.setGL_Budget_ID(factLine.getGL_Budget_ID());
		record.setGL_Category_ID(GLCategoryId.toRepoId(factLine.getGL_Category_ID()));
		record.setC_SalesRegion_ID(SalesRegionId.toRepoId(factLine.getC_SalesRegion_ID()));
		record.setC_Project_ID(ProjectId.toRepoId(factLine.getC_Project_ID()));
		record.setC_Activity_ID(ActivityId.toRepoId(factLine.getC_Activity_ID()));
		record.setC_Campaign_ID(factLine.getC_Campaign_ID());
		record.setUser1_ID(factLine.getUser1_ID());
		record.setUser2_ID(factLine.getUser2_ID());
		record.setUserElement1_ID(factLine.getUserElement1_ID());
		record.setUserElement2_ID(factLine.getUserElement2_ID());
		record.setUserElementString1(factLine.getUserElementString1());
		record.setUserElementString2(factLine.getUserElementString2());
		record.setUserElementString3(factLine.getUserElementString3());
		record.setUserElementString4(factLine.getUserElementString4());
		record.setUserElementString5(factLine.getUserElementString5());
		record.setUserElementString6(factLine.getUserElementString6());
		record.setUserElementString7(factLine.getUserElementString7());
		record.setPOReference(StringUtils.trimBlankToNull(factLine.getPoReference()));

		record.setOI_TrxType(factLine.getOpenItemTrxInfo() != null ? factLine.getOpenItemTrxInfo().getTrxType().getCode() : null);
		record.setOpenItemKey(factLine.getOpenItemTrxInfo() != null ? factLine.getOpenItemTrxInfo().getKey().getAsString() : null);

		final YearAndCalendarId calendarAndYearId = factLine.getYearAndCalendarId();
		record.setC_Harvesting_Calendar_ID(calendarAndYearId != null ? CalendarId.toRepoId(calendarAndYearId.calendarId()) : -1);
		record.setHarvesting_Year_ID(calendarAndYearId != null ? YearId.toRepoId(calendarAndYearId.yearId()) : -1);

		factAcctDAO.save(record);
		factLine.setId(FactAcctId.ofRepoId(record.getFact_Acct_ID()));
	}

	public Optional<SalesRegionId> getSalesRegionIdBySalesRepId(final UserId salesRepId)
	{
		return salesRegionService.getBySalesRepId(salesRepId).map(SalesRegion::getId);
	}

	public Optional<SalesRegionId> getSalesRegionIdByBPartnerLocationId(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return bpartnerDAO.getSalesRegionIdByBPLocationId(bpartnerLocationId);
	}

	public boolean lock(@NonNull final AcctDocModel docModel, final boolean force, final boolean repost)
	{
		return acctDocLockService.lock(docModel, force, repost);
	}

	public boolean unlock(@NonNull final AcctDocModel docModel, @Nullable final PostingStatus newPostingStatus, @Nullable final AdIssueId postingErrorIssueId)
	{
		return acctDocLockService.unlock(docModel, newPostingStatus, postingErrorIssueId);
	}

	public FactAcctChangesList getFactAcctChanges(@NonNull final TableRecordReference docRecordRef)
	{
		return factAcctUserChangesService.getByDocRecordRef(docRecordRef);
	}
}