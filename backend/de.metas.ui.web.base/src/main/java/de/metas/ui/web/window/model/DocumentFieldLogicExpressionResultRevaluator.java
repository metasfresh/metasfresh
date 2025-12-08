package de.metas.ui.web.window.model;

import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.LogicExpressionResultWithReason;
import org.adempiere.ad.expression.api.impl.LogicExpressionEvaluator;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Re-evaluates DocumentField's logic expressions based on user role permissions and some other flags.
 */
public class DocumentFieldLogicExpressionResultRevaluator
{
	public static final DocumentFieldLogicExpressionResultRevaluator ALWAYS_RETURN_FALSE = new DocumentFieldLogicExpressionResultRevaluator(
			new LogicExpressionResultWithReason(LogicExpressionResult.FALSE, TranslatableStrings.constant("Always false")),
			null);

	public static final DocumentFieldLogicExpressionResultRevaluator DEFAULT = new DocumentFieldLogicExpressionResultRevaluator(null, null);

	private static final Logger logger = LogManager.getLogger(DocumentFieldLogicExpressionResultRevaluator.class);

	private static final CtxName CTXNAME_AD_Role_Group = CtxNames.parse("#AD_Role_Group");

	private final LogicExpressionResultWithReason alwaysReturnResult;
	@Nullable private final IUserRolePermissions userRolePermissions;

	private DocumentFieldLogicExpressionResultRevaluator(
			@Nullable final LogicExpressionResultWithReason alwaysReturnResult,
			@Nullable final IUserRolePermissions userRolePermissions)
	{
		this.alwaysReturnResult = alwaysReturnResult;
		this.userRolePermissions = userRolePermissions;
	}

	public static DocumentFieldLogicExpressionResultRevaluator using(@NonNull final IUserRolePermissions userRolePermissions)
	{
		return new DocumentFieldLogicExpressionResultRevaluator(null, userRolePermissions);
	}

	public boolean isReadonly(@NonNull final IDocumentField documentField)
	{
		return revaluate(documentField.getReadonly()).isTrue();
	}

	public LogicExpressionResult revaluate(final LogicExpressionResult readonly)
	{
		if (alwaysReturnResult != null)
		{
			return alwaysReturnResult;
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
		if (usedParameters.isEmpty())
		{
			return null;
		}

		HashMap<String, String> newParameters = null; // lazy, instantiated only if needed
		for (final Map.Entry<CtxName, String> usedParameterEntry : usedParameters.entrySet())
		{
			final CtxName usedParameterName = usedParameterEntry.getKey();
			if (CTXNAME_AD_Role_Group.equalsByName(usedParameterName))
			{
				final String usedValue = normalizeRoleGroupValue(usedParameterEntry.getValue());
				final String newValue = normalizeRoleGroupValue(userRolePermissions.getRoleGroup() != null ? userRolePermissions.getRoleGroup().getName() : null);
				if (!Objects.equals(usedValue, newValue))
				{
					newParameters = newParameters == null ? copyToNewParameters(usedParameters) : newParameters;
					newParameters.put(CTXNAME_AD_Role_Group.getName(), newValue);
				}
			}
		}

		return newParameters;
	}

	private static String normalizeRoleGroupValue(String roleGroupValue)
	{
		String result = LogicExpressionEvaluator.stripQuotes(roleGroupValue);
		result = StringUtils.trimBlankToNull(result);
		return result != null ? result : "";
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
