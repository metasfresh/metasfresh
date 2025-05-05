package de.metas.acct.accounts;

import de.metas.acct.Account;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.doc.PostingException;
import de.metas.banking.BankAccountId;
import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.costing.ChargeId;
import de.metas.costing.CostElementId;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.acct.Doc;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

public class AccountProvider
{
	@NonNull private final IBPartnerDAO bpartnerDAO;
	@NonNull private final BPartnerAccountsRepository bpartnerAccountsRepository;
	@NonNull private final BPartnerGroupAccountsRepository bpartnerGroupAccountsRepository;
	@NonNull private final BankAccountAcctRepository bankAccountAcctRepository;
	@NonNull private final IProductBL productBL;
	@NonNull private final ProductAccountsRepository productAccountsRepository;
	@NonNull private final ProductCategoryAccountsRepository productCategoryAccountsRepository;
	@NonNull private final TaxAccountsRepository taxAccountsRepository;
	@NonNull private final ChargeAccountsRepository chargeAccountsRepository;
	@NonNull private final WarehouseAccountsRepository warehouseAccountsRepository;
	@NonNull private final ProjectAccountsRepository projectAccountsRepository;
	@NonNull private final CostElementAccountsRepository costElementAccountsRepository;
	@Nullable private final AccountProviderExtension extension;

	@Builder(toBuilder = true)
	public AccountProvider(
			@NonNull final IBPartnerDAO bpartnerDAO,
			@NonNull final BPartnerAccountsRepository bpartnerAccountsRepository,
			@NonNull final BPartnerGroupAccountsRepository bpartnerGroupAccountsRepository,
			@NonNull final BankAccountAcctRepository bankAccountAcctRepository,
			@NonNull final IProductBL productBL,
			@NonNull final ProductAccountsRepository productAccountsRepository,
			@NonNull final ProductCategoryAccountsRepository productCategoryAccountsRepository,
			@NonNull final TaxAccountsRepository taxAccountsRepository,
			@NonNull final ChargeAccountsRepository chargeAccountsRepository,
			@NonNull final WarehouseAccountsRepository warehouseAccountsRepository,
			@NonNull final ProjectAccountsRepository projectAccountsRepository,
			@NonNull final CostElementAccountsRepository costElementAccountsRepository,
			@Nullable final AccountProviderExtension extension)
	{
		this.bpartnerDAO = bpartnerDAO;
		this.bpartnerAccountsRepository = bpartnerAccountsRepository;
		this.bpartnerGroupAccountsRepository = bpartnerGroupAccountsRepository;
		this.bankAccountAcctRepository = bankAccountAcctRepository;
		this.productBL = productBL;
		this.productAccountsRepository = productAccountsRepository;
		this.productCategoryAccountsRepository = productCategoryAccountsRepository;
		this.taxAccountsRepository = taxAccountsRepository;
		this.chargeAccountsRepository = chargeAccountsRepository;
		this.warehouseAccountsRepository = warehouseAccountsRepository;
		this.projectAccountsRepository = projectAccountsRepository;
		this.costElementAccountsRepository = costElementAccountsRepository;
		this.extension = extension;
	}

	private static PostingException newPostingException()
	{
		return new PostingException((Doc<?>)null);
	}

	@NonNull
	public Account getBPartnerVendorAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerVendorAccountType acctType)
	{
		if (extension != null)
		{
			final Account account = extension.getBPartnerVendorAccount(acctSchemaId, bpartnerId, acctType).orElse(null);
			if (account != null)
			{
				return account;
			}
		}

		return bpartnerAccountsRepository.getVendorAccounts(bpartnerId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getBPartnerCustomerAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerCustomerAccountType acctType)
	{
		if (extension != null)
		{
			final Account account = extension.getBPartnerCustomerAccount(acctSchemaId, bpartnerId, acctType).orElse(null);
			if (account != null)
			{
				return account;
			}
		}

		return bpartnerAccountsRepository.getCustomerAccounts(bpartnerId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getBPGroupAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerGroupAccountType acctType)
	{
		final BPGroupId bpGroupId = bpartnerDAO.getBPGroupIdByBPartnerId(bpartnerId);

		if (extension != null)
		{
			final Account account = extension.getBPGroupAccount(acctSchemaId, bpGroupId, acctType).orElse(null);
			if (account != null)
			{
				return account;
			}
		}

		return bpartnerGroupAccountsRepository.getAccounts(bpGroupId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getChargeAccount(
			@NonNull final ChargeId chargeId,
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final BigDecimal chargeAmt)
	{
		return chargeAccountsRepository
				.getAccounts(chargeId, acctSchemaId)
				.getAccountByAmt(chargeAmt);
	}

	@NonNull
	public Account getBankAccountAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BankAccountId bankAccountId,
			@NonNull final BankAccountAcctType acctType)
	{
		return getBankAccountAccountIfSet(acctSchemaId, bankAccountId, acctType)
				.orElseThrow(() -> new AdempiereException("No account found for " + acctType + ", " + bankAccountId + ", " + acctSchemaId));
	}

	@NonNull
	public Optional<Account> getBankAccountAccountIfSet(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BankAccountId bankAccountId,
			@NonNull final BankAccountAcctType acctType)
	{
		return bankAccountAcctRepository.getAccounts(bankAccountId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getCashAccount(
			@NonNull final AcctSchemaId ignoredAcctSchemaId,
			final int ignoredCashBookId,
			@NonNull final CashAccountType ignoredAcctType)
	{
		throw new AdempiereException("Cash accounts are no longer supported");
	}

	@NonNull
	public Account getWarehouseAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final WarehouseAccountType acctType)
	{
		return warehouseAccountsRepository.getAccounts(warehouseId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getProjectAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProjectId projectId,
			@NonNull final ProjectAccountType acctType)
	{
		return projectAccountsRepository.getAccounts(projectId, acctSchemaId).getAccount(acctType);
	}

	@NonNull
	public Account getGLAccount(
			@NonNull final AcctSchema as,
			@NonNull final GLAccountType acctType)
	{
		if (acctType == GLAccountType.PPVOffset)
		{
			return as.getGeneralLedger().getPurchasePriceVarianceOffsetAcct();
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown AcctType=" + acctType);
		}
	}

	@NonNull
	public static Account getDefaultAccount(
			@NonNull final AcctSchema as,
			@NonNull final DefaultAccountType acctType)
	{
		if (acctType == DefaultAccountType.RealizedGain)
		{
			return as.getDefaultAccounts().getRealizedGainAcct();
		}
		else if (acctType == DefaultAccountType.RealizedLoss)
		{
			return as.getDefaultAccounts().getRealizedLossAcct();
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown AcctType=" + acctType);
		}
	}

	@NonNull
	public Optional<Account> getProductCategoryAccount(
			final @NonNull AcctSchemaId acctSchemaId,
			final @NonNull ProductCategoryId productCategoryId,
			final @NonNull ProductAcctType acctType)
	{
		if (extension != null)
		{
			final Optional<Account> account = extension.getProductCategoryAccount(acctSchemaId, productCategoryId, acctType);
			if (account.isPresent())
			{
				return account;
			}
		}

		return productCategoryAccountsRepository.getAccounts(productCategoryId, acctSchemaId)
				.map(accounts -> accounts.getAccount(acctType));
	}

	@NonNull
	public Account getTaxAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final TaxId taxId,
			@NonNull final TaxAcctType acctType)
	{
		return getTaxAccounts(acctSchemaId, taxId)
				.getAccount(acctType)
				.orElseThrow(() -> new AdempiereException("Tax account not set: " + acctType + ", " + taxId + ", " + acctSchemaId));
	}

	@NonNull
	public TaxAccounts getTaxAccounts(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final TaxId taxId)
	{
		return taxAccountsRepository.getAccounts(taxId, acctSchemaId);
	}

	@NonNull
	public Account getProductAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final ProductId productId,
			@Nullable final TaxId taxId,
			@NonNull final ProductAcctType acctType)
	{
		if (extension != null)
		{
			final Account account = extension.getProductAccount(acctSchemaId, productId, acctType).orElse(null);
			if (account != null)
			{
				return account;
			}
		}

		//
		// Product Revenue: check/use the override defined on tax level
		if (acctType == ProductAcctType.P_Revenue_Acct && taxId != null)
		{
			final Account productRevenueAcct = taxAccountsRepository.getAccounts(taxId, acctSchemaId)
					.getT_Revenue_Acct()
					.orElse(null);
			if (productRevenueAcct != null)
			{
				return productRevenueAcct;
			}
		}

		if (productId == null)
		{
			return productCategoryAccountsRepository
					.getAccounts(productBL.getDefaultProductCategoryId(), acctSchemaId)
					.map(accounts -> accounts.getAccount(acctType))
					.orElseThrow(() -> newPostingException().setDetailMessage("No Default Account for account type: " + acctType));
		}
		else
		{
			return productAccountsRepository.getAccounts(productId, acctSchemaId).getAccount(acctType);
		}
	}

	public Account getCostElementAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final CostElementAccountType acctType)
	{
		final AccountId accountId = costElementAccountsRepository
				.getAccounts(costElementId, acctSchemaId)
				.getAccountId(acctType);

		return Account.of(accountId, acctType.getAccountConceptualName());
	}
}
