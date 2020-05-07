package org.adempiere.bank;

import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Bank;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.util.Services;

@Repository
public class BankRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private CCache<String, Optional<BankId>> bankIdsBySwiftCode = CCache.<String, Optional<BankId>> builder()
			.tableName(I_C_Bank.Table_Name)
			.build();

	public Optional<BankId> getBankIdBySwiftCode(String swiftCode)
	{
		return bankIdsBySwiftCode.getOrLoad(swiftCode, this::findBankIdBySwiftCode);
	}

	public Optional<BankId> findBankIdBySwiftCode(String swiftCode)
	{
		final int bankRepoId = queryBL.createQueryBuilderOutOfTrx(I_C_Bank.class)
				.addEqualsFilter(I_C_Bank.COLUMNNAME_SwiftCode, swiftCode)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Bank.COLUMNNAME_C_Bank_ID)
				.create()
				.firstId();

		return BankId.optionalOfRepoId(bankRepoId);
	}

}
