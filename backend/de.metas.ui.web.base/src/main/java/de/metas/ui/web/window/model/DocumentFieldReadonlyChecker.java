package de.metas.ui.web.window.model;

import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.LogicExpressionResultWithReason;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DocumentFieldReadonlyChecker
{
	public static final DocumentFieldReadonlyChecker ALWAYS_READ_WRITE = new DocumentFieldReadonlyChecker(true, null);
	public static final DocumentFieldReadonlyChecker DEFAULT = new DocumentFieldReadonlyChecker(false, null);

	private static final Logger logger = LogManager.getLogger(DocumentFieldReadonlyChecker.class);

	private static final LogicExpressionResultWithReason READONLY_FALSE_BECAUSE_ALWAYS_READWRITE = new LogicExpressionResultWithReason(
			LogicExpressionResult.FALSE,
			TranslatableStrings.constant("Always read-write")
	);

	private final boolean alwaysReadWrite;
	@Nullable private final IUserRolePermissions userRolePermissions;

	private DocumentFieldReadonlyChecker(
			final boolean alwaysReadWrite,
			@Nullable final IUserRolePermissions userRolePermissions)
	{
		this.alwaysReadWrite = alwaysReadWrite;
		this.userRolePermissions = userRolePermissions;
	}

	public static DocumentFieldReadonlyChecker using(@NonNull final IUserRolePermissions userRolePermissions)
	{
		return new DocumentFieldReadonlyChecker(false, userRolePermissions);
	}

	public boolean isReadonly(@NonNull final IDocumentField documentField)
	{
		return revaluate(documentField.getReadonly()).booleanValue();
	}

	public LogicExpressionResult revaluate(final LogicExpressionResult readonly)
	{
		if (alwaysReadWrite)
		{
			if (readonly.isFalse())
			{
				return readonly;
			}
			else
			{
				return READONLY_FALSE_BECAUSE_ALWAYS_READWRITE;
			}
		}

		if (userRolePermissions == null)
		{
			return readonly;
		}

		final Map<String, String> newParameters = computeNewParametersIfChanged(readonly.getUsedParameters(), userRolePermissions);
		return newParameters != null ? revaluate(readonly, newParameters) : readonly;
	}

	@Nullable
	private static Map<String, String> computeNewParametersIfChanged(
			@NonNull final Map<CtxName, String> usedParameters,
			@NonNull final IUserRolePermissions userRolePermissions)
	{
		HashMap<String, String> newParameters = null; // lazy, instantiated only if needed

		for (final Map.Entry<CtxName, String> usedParameterEntry : usedParameters.entrySet())
		{
			final String name = usedParameterEntry.getKey().getName();
			if (Env.CTXNAME_AD_Role_Group.equals(name))
			{
				final String usedValue = StringUtils.trimBlankToNull(usedParameterEntry.getValue());
				final String newValue = userRolePermissions.getRoleGroup() != null ? userRolePermissions.getRoleGroup().getName() : null;
				if (!Objects.equals(usedValue, newValue))
				{
					newParameters = newParameters == null ? copyToNewParameters(usedParameters) : newParameters;
					newParameters.put(Env.CTXNAME_AD_Role_Group, newValue);
				}
			}
		}

		return newParameters;
	}

	@NonNull
	private static HashMap<String, String> copyToNewParameters(final @NonNull Map<CtxName, String> usedParameters)
	{
		final HashMap<String, String> newParameters = new HashMap<>(usedParameters.size());
		for (Map.Entry<CtxName, String> entry : usedParameters.entrySet())
		{
			newParameters.put(entry.getKey().getName(), entry.getValue());
		}
		return newParameters;
	}

	private static LogicExpressionResult revaluate(
			@NonNull final LogicExpressionResult result,
			@NonNull final Map<String, String> newParameters)
	{
		try
		{
			return result.getExpression().evaluateToResult(Evaluatees.ofMap(newParameters), IExpressionEvaluator.OnVariableNotFound.Fail);
		}
		catch (final Exception ex)
		{
			// shall not happen
			logger.warn("Failed evaluating expression using `{}`. Returning previous result: {}", newParameters, result, ex);
			return result;
		}
	}
}
