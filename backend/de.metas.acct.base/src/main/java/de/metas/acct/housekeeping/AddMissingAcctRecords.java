package de.metas.acct.housekeeping;

import de.metas.acct.accounts.AccountProviderFactory;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddMissingAcctRecords implements IStartupHouseKeepingTask
{
	private final AccountProviderFactory accountProviderFactory;

	@Override
	public void executeTask()
	{
		accountProviderFactory.addMissingAcctRecords();
	}
}
