package de.metas.acct.accounts;

import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import de.metas.acct.Account;

import javax.annotation.Nullable;
import java.util.Optional;

public interface AccountProviderExtension
{
	Optional<Account> getProductAccount(@NonNull AcctSchemaId acctSchemaId, @Nullable ProductId productId, @NonNull ProductAcctType acctType);

	Optional<Account> getProductCategoryAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull ProductCategoryId productCategoryId, @NonNull ProductAcctType acctType);

	Optional<Account> getBPartnerCustomerAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull BPartnerId bpartnerId, @NonNull BPartnerCustomerAccountType acctType);

	Optional<Account> getBPartnerVendorAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull BPartnerId bpartnerId, @NonNull BPartnerVendorAccountType acctType);

	Optional<Account> getBPGroupAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull BPGroupId bpGroupId, @NonNull BPartnerGroupAccountType acctType);

}
