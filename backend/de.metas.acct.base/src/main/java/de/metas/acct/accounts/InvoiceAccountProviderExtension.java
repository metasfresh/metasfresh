package de.metas.acct.accounts;

import de.metas.acct.Account;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.acct.InvoiceAcct;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.Optional;

public class InvoiceAccountProviderExtension implements AccountProviderExtension
{
	@NonNull private final IAccountDAO accountDAO;
	@NonNull private final InvoiceAcct invoiceAccounts;
	@NonNull final ClientId clientId;
	@Nullable private final InvoiceAndLineId invoiceAndLineId;

	@Builder
	private InvoiceAccountProviderExtension(
			@NonNull final IAccountDAO accountDAO,
			//
			@NonNull final InvoiceAcct invoiceAccounts,
			@NonNull final ClientId clientId,
			@Nullable final InvoiceAndLineId invoiceAndLineId)
	{
		this.accountDAO = accountDAO;
		if (invoiceAndLineId != null)
		{
			invoiceAndLineId.assertInvoiceId(invoiceAccounts.getInvoiceId());
		}

		this.invoiceAccounts = invoiceAccounts;
		this.clientId = clientId;
		this.invoiceAndLineId = invoiceAndLineId;
	}

	@Override
	@NonNull
	public Optional<Account> getProductAccount(@NonNull final AcctSchemaId acctSchemaId, @Nullable final ProductId productId, @NonNull final ProductAcctType acctType)
	{
		return getProductAccount(acctSchemaId, acctType);
	}

	@Override
	@NonNull
	public Optional<Account> getProductCategoryAccount(@NonNull final AcctSchemaId acctSchemaId, @NonNull final ProductCategoryId productCategoryId, @NonNull final ProductAcctType acctType)
	{
		return getProductAccount(acctSchemaId, acctType);
	}

	@NonNull
	private Optional<Account> getProductAccount(final @NonNull AcctSchemaId acctSchemaId, final @NonNull ProductAcctType acctType)
	{
		return invoiceAccounts.getElementValueId(acctSchemaId, acctType.getAccountConceptualName(), invoiceAndLineId)
				.map(elementValueId -> getOrCreateAccount(elementValueId, acctSchemaId))
				.map(id -> Account.of(id, acctType.getAccountConceptualName()));
	}

	@Override
	@NonNull
	public Optional<Account> getBPartnerCustomerAccount(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPartnerId bpartnerId, @NonNull final BPartnerCustomerAccountType acctType)
	{
		return Optional.empty();
	}

	@Override
	@NonNull
	public Optional<Account> getBPartnerVendorAccount(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPartnerId bpartnerId, @NonNull final BPartnerVendorAccountType acctType)
	{
		return Optional.empty();
	}

	@Override
	@NonNull
	public Optional<Account> getBPGroupAccount(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPGroupId bpGroupId, @NonNull final BPartnerGroupAccountType acctType)
	{
		return Optional.empty();
	}

	@NonNull
	private AccountId getOrCreateAccount(
			@NonNull final ElementValueId elementValueId,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return accountDAO.getOrCreate(
				AccountDimension.builder()
						.setAcctSchemaId(acctSchemaId)
						.setC_ElementValue_ID(elementValueId.getRepoId())
						.setAD_Client_ID(clientId.getRepoId())
						.setAD_Org_ID(OrgId.ANY.getRepoId())
						.build());
	}
}
