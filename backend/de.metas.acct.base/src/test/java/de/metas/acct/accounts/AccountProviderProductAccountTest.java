/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
package de.metas.acct.accounts;

import de.metas.acct.Account;
import de.metas.acct.AcctSchemaTestHelper;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_Tax_Acct;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountProviderProductAccountTest
{
	private static final AccountId PRODUCT_REVENUE_ACCT = AccountId.ofRepoId(200);
	private static final AccountId PRODUCT_EXPENSE_ACCT = AccountId.ofRepoId(300);
	private static final AccountId TAX_REVENUE_OVERRIDE = AccountId.ofRepoId(400);
	private static final AccountId TAX_EXPENSE_OVERRIDE = AccountId.ofRepoId(500);

	private AcctSchemaId acctSchemaId;
	private AccountProvider accountProvider;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);

		accountProvider = AccountProvider.builder()
				.bpartnerDAO(Services.get(IBPartnerDAO.class))
				.bpartnerAccountsRepository(new BPartnerAccountsRepository())
				.bpartnerGroupAccountsRepository(new BPartnerGroupAccountsRepository())
				.bankAccountAcctRepository(new BankAccountAcctRepository())
				.productBL(Services.get(IProductBL.class))
				.productAccountsRepository(new ProductAccountsRepository())
				.productCategoryAccountsRepository(new ProductCategoryAccountsRepository())
				.taxAccountsRepository(new TaxAccountsRepository())
				.chargeAccountsRepository(new ChargeAccountsRepository())
				.warehouseAccountsRepository(new WarehouseAccountsRepository())
				.projectAccountsRepository(new ProjectAccountsRepository())
				.costElementAccountsRepository(new CostElementAccountsRepository())
				.build();
	}

	private ProductId createProductWithDefaultAccounts()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		saveRecord(product);

		final I_M_Product_Acct productAcct = newInstance(I_M_Product_Acct.class);
		productAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		productAcct.setM_Product_ID(product.getM_Product_ID());
		productAcct.setP_Revenue_Acct(PRODUCT_REVENUE_ACCT.getRepoId());
		productAcct.setP_Expense_Acct(PRODUCT_EXPENSE_ACCT.getRepoId());
		productAcct.setP_Asset_Acct(1);
		productAcct.setP_COGS_Acct(1);
		productAcct.setP_PurchasePriceVariance_Acct(1);
		productAcct.setP_InvoicePriceVariance_Acct(1);
		productAcct.setP_TradeDiscountRec_Acct(1);
		productAcct.setP_TradeDiscountGrant_Acct(1);
		productAcct.setP_CostAdjustment_Acct(1);
		productAcct.setP_InventoryClearing_Acct(1);
		productAcct.setP_WIP_Acct(1);
		productAcct.setP_MethodChangeVariance_Acct(1);
		productAcct.setP_UsageVariance_Acct(1);
		productAcct.setP_RateVariance_Acct(1);
		productAcct.setP_MixVariance_Acct(1);
		productAcct.setP_FloorStock_Acct(1);
		productAcct.setP_CostOfProduction_Acct(1);
		productAcct.setP_Labor_Acct(1);
		productAcct.setP_Burden_Acct(1);
		productAcct.setP_OutsideProcessing_Acct(1);
		productAcct.setP_Overhead_Acct(1);
		productAcct.setP_Scrap_Acct(1);
		saveRecord(productAcct);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private TaxId createTaxWithOverrides(@Nullable final AccountId revenueOverride, @Nullable final AccountId expenseOverride)
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		saveRecord(tax);

		final I_C_Tax_Acct taxAcct = newInstance(I_C_Tax_Acct.class);
		taxAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		taxAcct.setC_Tax_ID(tax.getC_Tax_ID());
		taxAcct.setT_Due_Acct(1);
		taxAcct.setT_Liability_Acct(1);
		taxAcct.setT_Credit_Acct(1);
		taxAcct.setT_Receivables_Acct(1);
		if (expenseOverride != null)
		{
			taxAcct.setT_Expense_Acct(expenseOverride.getRepoId());
		}
		if (revenueOverride != null)
		{
			taxAcct.setT_Revenue_Acct(revenueOverride.getRepoId());
		}
		saveRecord(taxAcct);

		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

	@Test
	void test_revenueOverride_isUsedWhenSet()
	{
		final ProductId productId = createProductWithDefaultAccounts();
		final TaxId taxId = createTaxWithOverrides(TAX_REVENUE_OVERRIDE, null);

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, taxId, ProductAcctType.P_Revenue_Acct);

		assertThat(account.getAccountId()).isEqualTo(TAX_REVENUE_OVERRIDE);
	}

	@Test
	void test_expenseOverride_isUsedWhenSet()
	{
		final ProductId productId = createProductWithDefaultAccounts();
		final TaxId taxId = createTaxWithOverrides(null, TAX_EXPENSE_OVERRIDE);

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, taxId, ProductAcctType.P_Expense_Acct);

		assertThat(account.getAccountId()).isEqualTo(TAX_EXPENSE_OVERRIDE);
	}

	@Test
	void test_fallthrough_whenRevenueOverrideNull_productRevenueReturned()
	{
		final ProductId productId = createProductWithDefaultAccounts();
		final TaxId taxId = createTaxWithOverrides(null, null);

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, taxId, ProductAcctType.P_Revenue_Acct);

		assertThat(account.getAccountId()).isEqualTo(PRODUCT_REVENUE_ACCT);
	}

	@Test
	void test_fallthrough_whenExpenseOverrideNull_productExpenseReturned()
	{
		final ProductId productId = createProductWithDefaultAccounts();
		final TaxId taxId = createTaxWithOverrides(null, null);

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, taxId, ProductAcctType.P_Expense_Acct);

		assertThat(account.getAccountId()).isEqualTo(PRODUCT_EXPENSE_ACCT);
	}

	@Test
	void test_noTax_productRevenueReturned()
	{
		final ProductId productId = createProductWithDefaultAccounts();

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, null, ProductAcctType.P_Revenue_Acct);

		assertThat(account.getAccountId()).isEqualTo(PRODUCT_REVENUE_ACCT);
	}

	@Test
	void test_taxWithoutAcctSetup_throwsException()
	{
		final ProductId productId = createProductWithDefaultAccounts();
		// Create a tax with NO C_Tax_Acct row — newly-created tax that has not been wired into the schema yet.
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		saveRecord(tax);
		final TaxId taxId = TaxId.ofRepoId(tax.getC_Tax_ID());

		assertThatThrownBy(() -> accountProvider.getProductAccount(
				acctSchemaId, productId, taxId, ProductAcctType.P_Revenue_Acct))
				.isInstanceOf(org.adempiere.exceptions.AdempiereException.class)
				.hasMessageContaining("No Tax accounts defined");
	}

	@Test
	void test_noTax_productExpenseReturned()
	{
		final ProductId productId = createProductWithDefaultAccounts();

		final Account account = accountProvider.getProductAccount(
				acctSchemaId, productId, null, ProductAcctType.P_Expense_Acct);

		assertThat(account.getAccountId()).isEqualTo(PRODUCT_EXPENSE_ACCT);
	}
}
