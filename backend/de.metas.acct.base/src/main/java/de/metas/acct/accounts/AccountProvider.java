package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.doc.PostingException;
import de.metas.banking.BankAccountId;
import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.banking.accounting.BankAccountAcctType;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.costing.ChargeId;
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
import org.compiere.model.MAccount;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

public class AccountProvider
{
	@NonNull private final IAccountDAO accountDAO;
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
	@Nullable private final AccountProviderExtension extension;

	@Builder(toBuilder = true)
	public AccountProvider(
			@NonNull final IAccountDAO accountDAO,
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
			//
			@Nullable final AccountProviderExtension extension)
	{
		this.accountDAO = accountDAO;
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
		this.extension = extension;
	}

	private PostingException newPostingException()
	{
		return new PostingException((Doc<?>)null);
	}

	@NonNull
	public MAccount getBPartnerVendorAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerVendorAccountType acctType)
	{
		return accountDAO.getById(getBPartnerVendorAccountId(acctSchemaId, bpartnerId, acctType));
	}

	@NonNull
	public AccountId getBPartnerVendorAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerVendorAccountType acctType)
	{
		if (extension != null)
		{
			final AccountId accountId = extension.getBPartnerVendorAccountId(acctSchemaId, bpartnerId, acctType).orElse(null);
			if (accountId != null)
			{
				return accountId;
			}
		}

		return bpartnerAccountsRepository.getVendorAccounts(bpartnerId, acctSchemaId).getAccountId(acctType);
	}

	@NonNull
	public MAccount getBPartnerCustomerAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerCustomerAccountType acctType)
	{
		return accountDAO.getById(getBPartnerCustomerAccountId(acctSchemaId, bpartnerId, acctType));
	}

	@NonNull
	public AccountId getBPartnerCustomerAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerCustomerAccountType acctType)
	{
		if (extension != null)
		{
			final AccountId accountId = extension.getBPartnerCustomerAccountId(acctSchemaId, bpartnerId, acctType).orElse(null);
			if (accountId != null)
			{
				return accountId;
			}
		}

		return bpartnerAccountsRepository.getCustomerAccounts(bpartnerId, acctSchemaId).getAccountId(acctType);
	}

	@NonNull
	public MAccount getBPGroupAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerGroupAccountType acctType)
	{
		return accountDAO.getById(getBPGroupAccountId(acctSchemaId, bpartnerId, acctType));
	}

	@NonNull
	public AccountId getBPGroupAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPartnerGroupAccountType acctType)
	{
		final BPGroupId bpGroupId = bpartnerDAO.getBPGroupIdByBPartnerId(bpartnerId);

		if (extension != null)
		{
			final AccountId accountId = extension.getBPGroupAccountId(acctSchemaId, bpGroupId, acctType).orElse(null);
			if (accountId != null)
			{
				return accountId;
			}
		}

		return bpartnerGroupAccountsRepository.getAccounts(bpGroupId, acctSchemaId).getAccountId(acctType);
	}

	@NonNull
	public MAccount getChargeAccount(
			@NonNull final ChargeId chargeId,
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final BigDecimal chargeAmt)
	{
		return accountDAO.getById(getChargeAccountId(chargeId, acctSchemaId, chargeAmt));
	}

	@NonNull
	private AccountId getChargeAccountId(
			@NonNull final ChargeId chargeId,
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final BigDecimal chargeAmt)
	{
		final ChargeAccounts accounts = chargeAccountsRepository.getAccounts(chargeId, acctSchemaId);

		final int chargeAmtSign = chargeAmt != null ? chargeAmt.signum() : 0;
		return chargeAmtSign >= 0
				? accounts.getCh_Expense_Acct() //  Expense (positive amt)
				: accounts.getCh_Revenue_Acct(); //  Revenue (negative amt)
	}

	@NonNull
	public AccountId getBankAccountAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BankAccountId bankAccountId,
			@NonNull final BankAccountAcctType acctType)
	{
		return getBankAccountAccountIdIfSet(acctSchemaId, bankAccountId, acctType)
				.orElseThrow(() -> new AdempiereException("No account found for " + acctType + ", " + bankAccountId + ", " + acctSchemaId));
	}

	public Optional<AccountId> getBankAccountAccountIdIfSet(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final BankAccountId bankAccountId,
			@NonNull final BankAccountAcctType acctType)
	{
		return bankAccountAcctRepository.getAccounts(bankAccountId, acctSchemaId).getAccountId(acctType);
	}

	public AccountId getCashAccountId(
			@NonNull final AcctSchemaId ignoredAcctSchemaId,
			final int ignoredCashBookId,
			@NonNull final CashAccountType ignoredAcctType)
	{
		throw new AdempiereException("Cash accounts are no longer supported");
	}

	public AccountId getWarehouseAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final WarehouseAccountType acctType)
	{
		return warehouseAccountsRepository.getAccounts(warehouseId, acctSchemaId).getAccountId(acctType);
	}

	public AccountId getProjectAccountId(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final ProjectId projectId,
			@NonNull final ProjectAccountType acctType)
	{
		return projectAccountsRepository.getAccounts(projectId, acctSchemaId).getAccountId(acctType);
	}

	public AccountId getGLAccountId(
			@NonNull final AcctSchema as,
			@NonNull final GLAccountType acctType)
	{
		if (acctType == GLAccountType.PPVOffset)
		{
			return as.getGeneralLedger().getPurchasePriceVarianceOffsetAcctId();
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown AcctType=" + acctType);
		}
	}

	public AccountId getDefaultAccountId(
			@NonNull final AcctSchema as,
			@NonNull final DefaultAccountType acctType)
	{
		if (acctType == DefaultAccountType.RealizedGain)
		{
			return as.getDefaultAccounts().getRealizedGainAcctId();
		}
		else if (acctType == DefaultAccountType.RealizedLoss)
		{
			return as.getDefaultAccounts().getRealizedLossAcctId();
		}
		else
		{
			throw newPostingException().setDetailMessage("Unknown AcctType=" + acctType);
		}
	}

	public Optional<MAccount> getProductCategoryAccount(
			final @NonNull AcctSchemaId acctSchemaId,
			final @NonNull ProductCategoryId productCategoryId,
			final @NonNull ProductAcctType acctType)
	{
		if (extension != null)
		{
			final Optional<MAccount> account = extension.getProductCategoryAccount(acctSchemaId, productCategoryId, acctType);
			if (account.isPresent())
			{
				return account;
			}
		}

		return productCategoryAccountsRepository.getAccounts(productCategoryId, acctSchemaId)
				.map(accounts -> accounts.getAccountId(acctType))
				.map(accountDAO::getById);
	}

	public MAccount getTaxAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final TaxId taxId,
			@NonNull final TaxAcctType acctType)
	{
		return taxAccountsRepository.getAccounts(taxId, acctSchemaId).getAccountId(acctType)
				.map(accountDAO::getById)
				.orElseThrow(() -> new AdempiereException("Tax account not set: " + acctType + ", " + taxId + ", " + acctSchemaId));
	}

	@NonNull
	public MAccount getProductAccount(
			@NonNull final AcctSchemaId acctSchemaId,
			@Nullable final ProductId productId,
			@Nullable final TaxId taxId,
			@NonNull final ProductAcctType acctType)
	{
		if (extension != null)
		{
			final MAccount account = extension.getProductAccount(acctSchemaId, productId, acctType).orElse(null);
			if (account != null)
			{
				return account;
			}
		}

		//
		// Product Revenue: check/use the override defined on tax level
		if (acctType == ProductAcctType.P_Revenue_Acct && taxId != null)
		{
			final MAccount productRevenueAcct = taxAccountsRepository.getAccounts(taxId, acctSchemaId)
					.getT_Revenue_Acct()
					.map(accountDAO::getById)
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
					.map(accounts -> accounts.getAccountId(acctType))
					.map(accountDAO::getById)
					.orElseThrow(() -> newPostingException().setDetailMessage("No Default Account for account type: " + acctType));
		}
		else
		{
			final AccountId accountId = productAccountsRepository.getAccounts(productId, acctSchemaId).getAccountId(acctType);
			return accountDAO.getById(accountId);
		}
	}

}
