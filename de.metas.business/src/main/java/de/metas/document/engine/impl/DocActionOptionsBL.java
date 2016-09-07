package de.metas.document.engine.impl;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.logging.LogManager;

public class DocActionOptionsBL implements IDocActionOptionsBL
{
	private static final Logger logger = LogManager.getLogger(DocActionOptionsBL.class);

	private final Map<String, IDocActionOptionsCustomizer> docActionOptionsCustomizerByTableName = new ConcurrentHashMap<>();

	@Override
	public void updateDocActions(final IDocActionOptionsContext optionsCtx)
	{
		//
		// First, run the default customizer
		DefaultDocActionOptionsCustomizer.instance.customizeValidActions(optionsCtx);

		//
		// Apply specific customizer
		final String tableName = optionsCtx.getTableName();
		final IDocActionOptionsCustomizer customizer = docActionOptionsCustomizerByTableName.get(tableName);
		if(customizer != null)
		{
			customizer.customizeValidActions(optionsCtx);
		}

		//
		// Apply role access
		final int docTypeId = optionsCtx.getC_DocType_ID();
		if (docTypeId > 0)
		{
			final Properties ctx = optionsCtx.getCtx();
			final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
			role.applyActionAccess(optionsCtx);
		}
	}

	@Override
	public void setDocActionOptionsCustomizer(final String tableName, final IDocActionOptionsCustomizer customizer)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		Check.assumeNotNull(customizer, "Parameter customizer is not null");

		final IDocActionOptionsCustomizer customizerPrev = docActionOptionsCustomizerByTableName.put(tableName, customizer);
		if (customizerPrev != null)
		{
			logger.warn("Replaced {} with {} for {}", customizerPrev, customizer, tableName);
		}
		
		logger.info("Registered {} for {}", customizer, tableName);
	}
}