package de.metas.acct.accounts;

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.acct.AccountTypeName;
import de.metas.invoice.acct.InvoiceAcct;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.model.MAccount;

import javax.annotation.Nullable;
import java.util.Optional;

public class InvoiceAccountProviderExtension implements AccountProviderExtension
{
	@NonNull private final IAccountDAO accountDAO;
	@NonNull private final InvoiceAcct invoiceAccounts;
	@NonNull final ClientId clientId;
	@Nullable private final InvoiceLineId invoiceLineId;

	@Builder
	private InvoiceAccountProviderExtension(
			@NonNull final IAccountDAO accountDAO,
			//
			@NonNull final InvoiceAcct invoiceAccounts,
			@NonNull final ClientId clientId,
			@Nullable final InvoiceLineId invoiceLineId)
	{
		this.accountDAO = accountDAO;
		if (invoiceLineId != null)
		{
			invoiceLineId.assertInvoiceId(invoiceAccounts.getInvoiceId());
		}

		this.invoiceAccounts = invoiceAccounts;
		this.clientId = clientId;
		this.invoiceLineId = invoiceLineId;
	}

	@Override
	public Optional<MAccount> getProductAccount(@NonNull final AcctSchemaId acctSchemaId, @Nullable final ProductId productId, @NonNull final ProductAcctType acctType)
	{
		return getProductAccount(acctSchemaId, acctType);
	}

	@Override
	public Optional<MAccount> getProductCategoryAccount(@NonNull final AcctSchemaId acctSchemaId, @NonNull final ProductCategoryId productCategoryId, @NonNull final ProductAcctType acctType)
	{
		return getProductAccount(acctSchemaId, acctType);
	}

	private Optional<MAccount> getProductAccount(final @NonNull AcctSchemaId acctSchemaId, final @NonNull ProductAcctType acctType)
	{
		final AccountTypeName accountTypeName = AccountTypeName.ofColumnName(acctType.getColumnName());

		return invoiceAccounts.getElementValueId(acctSchemaId, accountTypeName, invoiceLineId)
				.map(elementValueId -> getOrCreateAccount(elementValueId, acctSchemaId));
	}

	@Override
	public Optional<AccountId> getBPartnerCustomerAccountId(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPartnerId bpartnerId, @NonNull final BPartnerCustomerAccountType acctType)
	{
		return Optional.empty();
	}

	@Override
	public Optional<AccountId> getBPartnerVendorAccountId(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPartnerId bpartnerId, @NonNull final BPartnerVendorAccountType acctType)
	{
		return Optional.empty();
	}

	@Override
	public Optional<AccountId> getBPGroupAccountId(@NonNull final AcctSchemaId acctSchemaId, @NonNull final BPGroupId bpGroupId, @NonNull final BPartnerGroupAccountType acctType)
	{
		return Optional.empty();
	}

	private MAccount getOrCreateAccount(
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
