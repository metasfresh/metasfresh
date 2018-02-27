package org.adempiere.bank;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Bank;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepository
{
	public I_C_Bank findBankBySwiftCode(String swiftCode)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Bank.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Bank.COLUMNNAME_SwiftCode, swiftCode)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy()
				.addColumn(I_C_Bank.COLUMNNAME_SwiftCode)
				.endOrderBy()
				.create()
				.first();
	}

}
