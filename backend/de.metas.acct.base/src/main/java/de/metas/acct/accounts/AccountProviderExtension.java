package de.metas.acct.accounts;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.MAccount;

import javax.annotation.Nullable;
import java.util.Optional;

public interface AccountProviderExtension
{
	Optional<MAccount> getProductAccount(@NonNull AcctSchemaId acctSchemaId, @Nullable ProductId productId, @NonNull ProductAcctType acctType);

	Optional<MAccount> getProductCategoryAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull ProductCategoryId productCategoryId, @NonNull ProductAcctType acctType);

	Optional<AccountId> getBPartnerCustomerAccountId(@NonNull AcctSchemaId acctSchemaId, @NonNull BPartnerId bpartnerId, @NonNull BPartnerCustomerAccountType acctType);

	Optional<AccountId> getBPartnerVendorAccountId(@NonNull AcctSchemaId acctSchemaId, @NonNull BPartnerId bpartnerId, @NonNull BPartnerVendorAccountType acctType);

	Optional<AccountId> getBPGroupAccountId(@NonNull AcctSchemaId acctSchemaId, @NonNull BPGroupId bpGroupId, @NonNull BPartnerGroupAccountType acctType);

}
