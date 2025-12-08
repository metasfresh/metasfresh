package de.metas.ui.web.window.model.sql;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.lang.SOTrx;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link IValidationRule} implementation which filters only those DocActions on which are suitable for current document status and user's role has access to them.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class DocActionValidationRule extends AbstractJavaValidationRule
{
	public static final DocActionValidationRule instance = new DocActionValidationRule();
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.<String>builder()
			.add(WindowConstants.FIELDNAME_DocStatus)
			.add(WindowConstants.FIELDNAME_C_DocType_ID)
			.add(WindowConstants.FIELDNAME_C_DocTypeTarget_ID)
			.add(WindowConstants.FIELDNAME_IsSOTrx)
			.add(WindowConstants.FIELDNAME_Processing)
			.add(WindowConstants.FIELDNAME_OrderType)
			.add(IValidationContext.PARAMETER_ContextTableName)
			.add(LookupDataSourceContext.PARAM_UserRolePermissionsKey.getName())
			.build();

	private DocActionValidationRule()
	{
		super();
	}

	@Override
	public boolean accept(final IValidationContext evalCtx, final NamePair item)
	{
		if (item == null)
		{
			return false;
		}

		final String docAction = item.getID();
		final ImmutableSet<String> availableDocActions = getAvailableDocActions(evalCtx);

		return availableDocActions.contains(docAction);
	}

	private ImmutableSet<String> getAvailableDocActions(final IValidationContext evalCtx)
	{
		final DocActionOptionsContext optionsCtx = DocActionOptionsContext.builder()
				.userRolePermissionsKey(extractUserRolePermissionsKey(evalCtx))
				.tableName(extractContextTableName(evalCtx))
				.docStatus(extractDocStatus(evalCtx))
				.docTypeId(extractDocTypeId(evalCtx))
				.processing(extractProcessing(evalCtx))
				.orderType(extractOrderType(evalCtx))
				.soTrx(extractSOTrx(evalCtx))
				.validationContext(evalCtx)
				.build();

		final IDocActionOptionsBL docActionOptionsBL = Services.get(IDocActionOptionsBL.class);
		docActionOptionsBL.updateDocActions(optionsCtx);

		return optionsCtx.getDocActions();
	}

	private static UserRolePermissionsKey extractUserRolePermissionsKey(final IValidationContext evalCtx)
	{
		return UserRolePermissionsKey.fromEvaluatee(evalCtx, LookupDataSourceContext.PARAM_UserRolePermissionsKey.getName());
	}

	private static String extractContextTableName(final IValidationContext evalCtx)
	{
		final String contextTableName = evalCtx.get_ValueAsString(IValidationContext.PARAMETER_ContextTableName);
		if (Check.isEmpty(contextTableName))
		{
			throw new AdempiereException("Failed getting " + IValidationContext.PARAMETER_ContextTableName + " from " + evalCtx);
		}

		return contextTableName;
	}

	private static String extractDocStatus(final IValidationContext evalCtx)
	{
		return evalCtx.get_ValueAsString(WindowConstants.FIELDNAME_DocStatus);
	}

	private static DocTypeId extractDocTypeId(final IValidationContext evalCtx)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(evalCtx.get_ValueAsInt(WindowConstants.FIELDNAME_C_DocType_ID, -1));
		if (docTypeId != null)
		{
			return docTypeId;
		}

		return DocTypeId.ofRepoIdOrNull(evalCtx.get_ValueAsInt(WindowConstants.FIELDNAME_C_DocTypeTarget_ID, -1));
	}

	private static SOTrx extractSOTrx(final IValidationContext evalCtx)
	{
		return SOTrx.ofBoolean(evalCtx.get_ValueAsBoolean(WindowConstants.FIELDNAME_IsSOTrx, false));
	}

	private static boolean extractProcessing(final IValidationContext evalCtx)
	{
		final Boolean valueAsBoolean = evalCtx.get_ValueAsBoolean(WindowConstants.FIELDNAME_Processing, false);
		return valueAsBoolean != null && valueAsBoolean;
	}

	private static String extractOrderType(final IValidationContext evalCtx)
	{
		return evalCtx.get_ValueAsString(WindowConstants.FIELDNAME_OrderType);
	}

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		final HashSet<String> parameters = new HashSet<>(PARAMETERS);

		final IDocActionOptionsBL docActionOptionsBL = Services.get(IDocActionOptionsBL.class);
		parameters.addAll(docActionOptionsBL.getRequiredParameters(contextTableName));

		return parameters;
	}

}
