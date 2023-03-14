package de.metas.acct.accounts;

import de.metas.banking.accounting.BankAccountAcctRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AccountProviderFactory
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ProductAccountsRepository productAccountsRepository;
	private final ProductCategoryAccountsRepository productCategoryAccountsRepository;
	private final TaxAccountsRepository taxAccountsRepository;
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final BPartnerAccountsRepository bpartnerAccountsRepository;
	private final BPartnerGroupAccountsRepository bpartnerGroupAccountsRepository;
	private final BankAccountAcctRepository bankAccountAcctRepository;
	private final ChargeAccountsRepository chargeAccountsRepository;
	private final WarehouseAccountsRepository warehouseAccountsRepository;
	private final ProjectAccountsRepository projectAccountsRepository;
	private final CostElementAccountsRepository costElementAccountsRepository;

	public AccountProviderFactory(
			@NonNull final ProductAccountsRepository productAccountsRepository,
			@NonNull final ProductCategoryAccountsRepository productCategoryAccountsRepository,
			@NonNull final TaxAccountsRepository taxAccountsRepository,
			@NonNull final BPartnerAccountsRepository bpartnerAccountsRepository,
			@NonNull final BPartnerGroupAccountsRepository bpartnerGroupAccountsRepository,
			@NonNull final BankAccountAcctRepository bankAccountAcctRepository,
			@NonNull final ChargeAccountsRepository chargeAccountsRepository,
			@NonNull final WarehouseAccountsRepository warehouseAccountsRepository,
			@NonNull final ProjectAccountsRepository projectAccountsRepository,
			@NonNull final CostElementAccountsRepository costElementAccountsRepository)
	{
		this.productAccountsRepository = productAccountsRepository;
		this.productCategoryAccountsRepository = productCategoryAccountsRepository;
		this.taxAccountsRepository = taxAccountsRepository;
		this.bpartnerAccountsRepository = bpartnerAccountsRepository;
		this.bpartnerGroupAccountsRepository = bpartnerGroupAccountsRepository;
		this.bankAccountAcctRepository = bankAccountAcctRepository;
		this.chargeAccountsRepository = chargeAccountsRepository;
		this.warehouseAccountsRepository = warehouseAccountsRepository;
		this.projectAccountsRepository = projectAccountsRepository;
		this.costElementAccountsRepository = costElementAccountsRepository;
	}

	public AccountProvider.AccountProviderBuilder newAccountProvider()
	{
		return AccountProvider.builder()
				.bpartnerDAO(bpartnerDAO)
				.bpartnerAccountsRepository(bpartnerAccountsRepository)
				.bpartnerGroupAccountsRepository(bpartnerGroupAccountsRepository)
				.bankAccountAcctRepository(bankAccountAcctRepository)
				.productBL(productBL)
				.productAccountsRepository(productAccountsRepository)
				.productCategoryAccountsRepository(productCategoryAccountsRepository)
				.taxAccountsRepository(taxAccountsRepository)
				.chargeAccountsRepository(chargeAccountsRepository)
				.warehouseAccountsRepository(warehouseAccountsRepository)
				.projectAccountsRepository(projectAccountsRepository)
				.costElementAccountsRepository(costElementAccountsRepository)
				;
	}

}
