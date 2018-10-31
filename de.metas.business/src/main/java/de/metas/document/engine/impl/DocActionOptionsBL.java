package de.metas.document.engine.impl;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;

import de.metas.document.DocTypeId;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.logging.LogManager;
import de.metas.util.Services;

public class DocActionOptionsBL implements IDocActionOptionsBL
{
	private static final Logger logger = LogManager.getLogger(DocActionOptionsBL.class);

	private final Supplier<Map<String, IDocActionOptionsCustomizer>> _docActionOptionsCustomizerByTableName = Suppliers.memoize(() -> retrieveDocActionOptionsCustomizer());

	@Override
	public void updateDocActions(final DocActionOptionsContext optionsCtx)
	{
		//
		// First, run the default customizer
		DefaultDocActionOptionsCustomizer.instance.customizeValidActions(optionsCtx);

		//
		// Apply specific customizer
		final String tableName = optionsCtx.getTableName();
		final IDocActionOptionsCustomizer customizer = getDocActionOptionsCustomizerOrNull(tableName);
		if (customizer != null)
		{
			customizer.customizeValidActions(optionsCtx);
		}

		//
		// Apply role access
		final DocTypeId docTypeId = optionsCtx.getDocTypeId();
		if (docTypeId != null)
		{
			final UserRolePermissionsKey permissionsKey = optionsCtx.getUserRolePermissionsKey();
			final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class).retrieveUserRolePermissions(permissionsKey);
			role.applyActionAccess(optionsCtx);
		}
	}
	
	private IDocActionOptionsCustomizer getDocActionOptionsCustomizerOrNull(final String tableName)
	{
		return _docActionOptionsCustomizerByTableName.get().get(tableName);
	}

	private static final ImmutableMap<String, IDocActionOptionsCustomizer> retrieveDocActionOptionsCustomizer()
	{
		final ImmutableMap<String, IDocActionOptionsCustomizer> customizers = Adempiere.getSpringApplicationContext().getBeansOfType(IDocActionOptionsCustomizer.class)
				.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(IDocActionOptionsCustomizer::getAppliesToTableName, Function.identity()));

		logger.info("Loaded {}(s): {}", IDocActionOptionsCustomizer.class, customizers);

		return customizers;
	}
}
