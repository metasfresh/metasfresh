package de.metas.acct.doc;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.api.IFactAcctListenersService;
import de.metas.acct.api.IPostingRequestBuilder.PostImmediate;
import de.metas.acct.api.IPostingService;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.acct.api.ProductAcctType;
import de.metas.acct.tax.ITaxAcctBL;
import de.metas.acct.tax.TaxAcctType;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountAcct;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankAccountService;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.costing.AggregatedCostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
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
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAccount;
import org.compiere.model.PO;
import org.compiere.util.TrxRunnable2;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
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
	private final IModelCacheInvalidationService modelCacheInvalidationService = Services.get(IModelCacheInvalidationService.class);

	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final IFactAcctBL factAcctBL = Services.get(IFactAcctBL.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);

	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final BankAccountService bankAccountService;

	//
	// Needed for DocLine:
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final ITaxAcctBL taxAcctBL = Services.get(ITaxAcctBL.class);

	private final ICostingService costingService;

	public AcctDocRequiredServicesFacade(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final ICostingService costingService)
	{
		this.bankAccountService = bankAccountService;
		this.costingService = costingService;
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
				ModelCacheInvalidationTiming.CHANGE);
	}

	public void runInThreadInheritedTrx(@NonNull final TrxRunnable2 runnable)
	{
		trxManager.runInThreadInheritedTrx(runnable);
	}

	public int deleteFactAcctByDocumentModel(@NonNull final Object documentPO)
	{
		return factAcctDAO.deleteForDocumentModel(documentPO);
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

	@NonNull
	public MAccount getAccountById(@NonNull final AccountId accountId)
	{
		return accountDAO.getById(accountId);
	}

	public MAccount getAccount(@NonNull final I_Fact_Acct factAcct)
	{
		return factAcctBL.getAccount(factAcct);
	}

	public CurrencyPrecision getCurrencyStandardPrecision(@NonNull final CurrencyId currencyId)
	{
		return currencyDAO.getStdPrecision(currencyId);
	}

	public final CurrencyConversionContext createCurrencyConversionContext(
			@Nullable final LocalDate convDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return currencyConversionBL.createCurrencyConversionContext(convDate, conversionTypeId, clientId, orgId);
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

	public BankAccountAcct getBankAccountAcct(
			final BankAccountId bankAccountId,
			final AcctSchemaId acctSchemaId)
	{
		return bankAccountService.getBankAccountAcct(bankAccountId, acctSchemaId);
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

	public Optional<AccountId> getProductAcct(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProductId productId,
			@NonNull final ProductAcctType acctType)
	{
		return productAcctDAO.getProductAccount(acctSchemaId, productId, acctType);
	}

	public Optional<AccountId> getProductDefaultAcct(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProductAcctType acctType)
	{
		final ProductCategoryId defaultProductCategoryId = productBL.getDefaultProductCategoryId();
		return productAcctDAO.getProductCategoryAccount(acctSchemaId, defaultProductCategoryId, acctType);
	}

	public Optional<MAccount> getTaxAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final TaxId taxId,
			@NonNull final TaxAcctType taxAcctType)
	{
		return taxId != null
				? taxAcctBL.getAccountIfExists(taxId, acctSchemaId, taxAcctType)
				: Optional.empty();
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

	public AggregatedCostAmount createCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		return costingService.createCostDetail(request);
	}

	public MoveCostsResult moveCosts(@NonNull final MoveCostsRequest request)
	{
		return costingService.moveCosts(request);
	}

	public AggregatedCostAmount createReversalCostDetails(@NonNull final CostDetailReverseRequest request)
	{
		return costingService.createReversalCostDetails(request);
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

}
