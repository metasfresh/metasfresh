package de.metas.jmx;

import java.util.Arrays;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.compiere.dbPort.Convert;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;

@Service
@ManagedResource(objectName = "de.metas:type=metasfresh", description = "Provides basic operations on the running metasfresh instance")
public class Metasfresh
{

	@ManagedOperation
	public String[] getActiveTrxNames()
	{
		return Services.get(ITrxManager.class)
				.getActiveTransactionsList()
				.stream()
				.map((trx) -> trx.getTrxName())
				.toArray((size) -> new String[size]);
	}

	@ManagedOperation
	public String getStrackTrace(String trxName)
	{
		return Services.get(IOpenTrxBL.class).getCreationStackTrace(trxName);
	}

	@ManagedOperation
	public String[] getServerContext()
	{
		final Properties ctx = Env.getCtx();
		String[] context = Env.getEntireContext(ctx);
		Arrays.sort(context);
		return context;
	}

	@ManagedOperation
	public void setLogLevel(String levelName)
	{
		LogManager.setLevel(levelName);
	}

	@ManagedOperation
	public String getLogLevel()
	{
		final Level level = LogManager.getLevel();
		return level == null ? null : level.toString();
	}

	@ManagedOperation
	public void runFinalization()
	{
		System.runFinalization();
	}

	@ManagedOperation
	public void resetLocalCache()
	{
		CacheMgt.get().reset();
	}
	
	@ManagedOperation
	public void rotateMigrationScriptFile()
	{
		Convert.closeMigrationScriptFiles();
	}
}
