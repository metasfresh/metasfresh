package de.metas.acct.doc;

import de.metas.acct.GLCategoryId;
import de.metas.acct.GLCategoryRepository;
import de.metas.acct.accounts.AccountProvider;
import de.metas.acct.accounts.AccountProviderFactory;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostDetailCreateRequest;
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
import de.metas.order.costs.OrderCostService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.PO;
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
	@Getter
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
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

	//
	// Needed for DocLine:
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);

	private final ICostingService costingService;
	private final DimensionService dimensionService;

	public AcctDocRequiredServicesFacade(
			@NonNull final ModelCacheInvalidationService modelCacheInvalidationService,
			@NonNull final GLCategoryRepository glCategoryRepository,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final ICostingService costingService,
			@NonNull final AccountProviderFactory accountProviderFactory,
			@NonNull final InvoiceAcctRepository invoiceAcctRepository,
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final OrderCostService orderCostService,
			@NonNull final DimensionService dimensionService)
	{
		this.modelCacheInvalidationService = modelCacheInvalidationService;
		this.glCategoryRepository = glCategoryRepository;
		this.bankAccountService = bankAccountService;
		this.costingService = costingService;
		this.accountProviderFactory = accountProviderFactory;
		this.invoiceAcctRepository = invoiceAcctRepository;
		this.matchInvoiceService = matchInvoiceService;
		this.orderCostService = orderCostService;
		this.dimensionService = dimensionService;
	}

	public void fireBeforePostEvent(@NonNull final PO po)
	{
		factAcctListenersService.fireBeforePost(po);
	}

	public void fireAfterPostEvent(@NonNull final PO po)
	{
		factAcctListenersService.fireAfterPost(po);
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

	public void deleteFactAcctByDocumentModel(@NonNull final Object documentPO)
	{
		factAcctDAO.deleteForDocumentModel(documentPO);
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

	@NonNull
	public AccountId getOrCreateAccountId(@NonNull final AccountDimension accountDimension)
	{
		return accountDAO.getOrCreateAccountId(accountDimension);
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

	public AggregatedCostAmount createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		return costingService.createCostDetail(request);
	}

	@SuppressWarnings("UnusedReturnValue")
	public ExplainedOptional<AggregatedCostAmount> createCostDetailOrEmpty(@NonNull final CostDetailCreateRequest request)
	{
		return costingService.createCostDetailOrEmpty(request);
	}

	public MoveCostsResult moveCosts(@NonNull final MoveCostsRequest request)
	{
		return costingService.moveCosts(request);
	}

	public AggregatedCostAmount createReversalCostDetails(@NonNull final CostDetailReverseRequest request)
	{
		return costingService.createReversalCostDetails(request);
	}

	public ExplainedOptional<AggregatedCostAmount> createReversalCostDetailsOrEmpty(@NonNull final CostDetailReverseRequest request)
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
}
