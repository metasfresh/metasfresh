package de.metas.acct.api;

import de.metas.acct.accounts.ProductAccounts;
import de.metas.acct.accounts.ProductAccountsRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class ProductActivityProvider implements IProductActivityProvider
{
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final ProductAccountsRepository productAccountsRepository;

	public ProductActivityProvider(
			@NonNull final ProductAccountsRepository productAccountsRepository)
	{
		this.productAccountsRepository = productAccountsRepository;
	}

	@Override
	@Nullable
	public ActivityId getActivityForAcct(final ClientId clientId, final OrgId orgId, final ProductId productId)
	{
		final AcctSchemaId acctSchemaId = acctSchemaDAO.getAcctSchemaIdByClientAndOrg(clientId, orgId);
		if (acctSchemaId == null)
		{
			return null;
		}

		return productAccountsRepository.getAccountsIfExists(productId, acctSchemaId)
				.flatMap(ProductAccounts::getActivityId)
				.orElse(null);
	}
}
