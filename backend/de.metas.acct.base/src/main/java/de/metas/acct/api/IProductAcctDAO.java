package de.metas.acct.api;

import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import java.util.Optional;

/**
 * @author al
 */
public interface IProductAcctDAO extends IProductActivityProvider, ISingletonService
{
	/**
	 * Gets the product activity from product accounting record using the matching accounting schema for given client/organization.
	 *
	 * @return activity or null
	 */
	@Override
	ActivityId retrieveActivityForAcct(ClientId clientId, OrgId orgId, ProductId productId);

	Optional<AccountId> getProductAccount(AcctSchemaId acctSchemaId, ProductId productId, ProductAcctType acctType);

	Optional<AccountId> getProductCategoryAccount(@NonNull AcctSchemaId acctSchemaId, @NonNull ProductCategoryId productCategoryId, @NonNull ProductAcctType acctType);
}
