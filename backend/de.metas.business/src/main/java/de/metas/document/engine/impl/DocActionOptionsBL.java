package de.metas.document.engine.impl;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.UserRolePermissionsKey;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class DocActionOptionsBL implements IDocActionOptionsBL
{
	private static final Logger logger = LogManager.getLogger(DocActionOptionsBL.class);

	private final Supplier<Map<String, IDocActionOptionsCustomizer>> _docActionOptionsCustomizerByTableName = Suppliers.memoize(DocActionOptionsBL::retrieveDocActionOptionsCustomizer);

	@Override
	public Set<String> getRequiredParameters(@Nullable final String contextTableName)
	{
		final HashSet<String> requiredParameters = new HashSet<>(DefaultDocActionOptionsCustomizer.instance.getParameters());

		if (contextTableName != null)
		{
			final IDocActionOptionsCustomizer tableSpecificCustomizer = _docActionOptionsCustomizerByTableName.get().get(contextTableName);
			if (tableSpecificCustomizer != null)
			{
				requiredParameters.addAll(tableSpecificCustomizer.getParameters());
			}
		}
		else
		{
			// NOTE: in case contextTableName is not provided
			// then we shall consider all params as (possibly) required.

			_docActionOptionsCustomizerByTableName.get()
					.values()
					.forEach(tableSpecificCustomizer -> requiredParameters.addAll(tableSpecificCustomizer.getParameters()));
		}

		return requiredParameters;
	}

	@Override
	public void updateDocActions(final DocActionOptionsContext optionsCtx)
	{
		final IDocActionOptionsCustomizer customizers = getDocActionOptionsCustomizers(optionsCtx.getTableName());
		customizers.customizeValidActions(optionsCtx);

		//
		// Apply role access
		final DocTypeId docTypeId = optionsCtx.getDocTypeId();
		if (docTypeId != null)
		{
			final UserRolePermissionsKey permissionsKey = optionsCtx.getUserRolePermissionsKey();
			final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class).getUserRolePermissions(permissionsKey);
			role.applyActionAccess(optionsCtx);
		}
	}

	private IDocActionOptionsCustomizer getDocActionOptionsCustomizers(@Nullable final String contextTableName)
	{
		final ArrayList<IDocActionOptionsCustomizer> customizers = new ArrayList<>(2);
		customizers.add(DefaultDocActionOptionsCustomizer.instance);

		if (contextTableName != null)
		{
			final IDocActionOptionsCustomizer tableSpecificCustomizer = _docActionOptionsCustomizerByTableName.get().get(contextTableName);
			if (tableSpecificCustomizer != null)
			{
				customizers.add(tableSpecificCustomizer);
			}
		}

		return CompositeDocActionOptionsCustomizer.ofList(customizers);
	}

	private static ImmutableMap<String, IDocActionOptionsCustomizer> retrieveDocActionOptionsCustomizer()
	{
		final ImmutableMap<String, IDocActionOptionsCustomizer> customizers = SpringContextHolder.instance.getBeansOfType(IDocActionOptionsCustomizer.class)
				.stream()
				.collect(ImmutableMap.toImmutableMap(IDocActionOptionsCustomizer::getAppliesToTableName, Function.identity()));

		logger.info("Loaded {}(s): {}", IDocActionOptionsCustomizer.class, customizers);

		return customizers;
	}
}
