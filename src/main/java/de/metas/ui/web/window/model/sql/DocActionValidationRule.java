package de.metas.ui.web.window.model.sql;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.DefaultDocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsBL;
import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.ui.web.window.WindowConstants;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link IValidationRule} implementation which filters only those DocActions on which are suitable for current document status and user's role has access to them.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocActionValidationRule extends AbstractJavaValidationRule
{
	public static final transient DocActionValidationRule instance = new DocActionValidationRule();

	private static final Set<String> PARAMETERS = ImmutableSet.<String> builder()
			.add(WindowConstants.FIELDNAME_DocStatus)
			.add(WindowConstants.FIELDNAME_C_DocType_ID)
			.add(WindowConstants.FIELDNAME_C_DocTypeTarget_ID)
			.add(WindowConstants.FIELDNAME_IsSOTrx)
			.add(WindowConstants.FIELDNAME_Processing)
			.add(WindowConstants.FIELDNAME_OrderType)
			.add(IValidationContext.PARAMETER_ContextTableName)
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
		final Set<String> availableDocActions = getAvailableDocActions(evalCtx);

		return availableDocActions.contains(docAction);
	}

	private final Set<String> getAvailableDocActions(final IValidationContext evalCtx)
	{
		final Properties ctx = Env.getCtx();
		final IDocActionOptionsContext optionsCtx = DefaultDocActionOptionsContext.builder(ctx)
				.setTableName(extractContextTableName(evalCtx))
				.setDocStatus(extractDocStatus(evalCtx))
				.setC_DocType_ID(extractC_DocType_ID(evalCtx))
				.setProcessing(extractProcessing(evalCtx))
				.setOrderType(extractOrderType(evalCtx))
				.setIsSOTrx(extractIsSOTrx(evalCtx))
				.build();
		Services.get(IDocActionOptionsBL.class).updateDocActions(optionsCtx);

		final Set<String> availableDocActions = optionsCtx.getDocActions();
		return availableDocActions;
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
		final String value = evalCtx.get_ValueAsString(WindowConstants.FIELDNAME_DocStatus);
		return value;
	}

	private static int extractC_DocType_ID(final IValidationContext evalCtx)
	{
		final int docTypeId = evalCtx.get_ValueAsInt(WindowConstants.FIELDNAME_C_DocType_ID, -1);
		if (docTypeId > 0)
		{
			return docTypeId;
		}

		final int doctTypeId = evalCtx.get_ValueAsInt(WindowConstants.FIELDNAME_C_DocTypeTarget_ID, -1);
		if (doctTypeId > 0)
		{
			return doctTypeId;
		}

		return -1;
	}

	private static boolean extractIsSOTrx(final IValidationContext evalCtx)
	{
		return evalCtx.get_ValueAsBoolean(WindowConstants.FIELDNAME_IsSOTrx, false);
	}

	private static boolean extractProcessing(final IValidationContext evalCtx)
	{
		return evalCtx.get_ValueAsBoolean(WindowConstants.FIELDNAME_Processing, false);
	}

	private static String extractOrderType(final IValidationContext evalCtx)
	{
		return evalCtx.get_ValueAsString(WindowConstants.FIELDNAME_OrderType);
	}

	@Override
	public Set<String> getParameters()
	{
		return PARAMETERS;
	}

}
