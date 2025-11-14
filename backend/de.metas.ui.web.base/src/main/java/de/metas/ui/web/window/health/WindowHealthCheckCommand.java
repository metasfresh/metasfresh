package de.metas.ui.web.window.health;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.ui.web.window.health.json.JsonWindowHealthCheckRequest;
import de.metas.ui.web.window.health.json.JsonWindowsHealthCheckResponse;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;

public class WindowHealthCheckCommand
{
	//
	// Services
	@NonNull private static final Logger logger = LogManager.getLogger(WindowHealthCheckCommand.class);
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final DocumentDescriptorFactory documentDescriptorFactory;

	//
	// Params
	@NonNull private final String adLanguage;
	@NonNull private final ImmutableSet<AdWindowId> onlyAdWindowIds;

	//
	// State
	@NonNull private final ContextVariables contextVariables;
	@NonNull private final MissingContextVariables missingContextVariables;
	private AdWindowId currentWindowId;
	private String currentWindowName;
	private final ArrayList<JsonWindowsHealthCheckResponse.Entry> errors = new ArrayList<>();

	@Builder
	private WindowHealthCheckCommand(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final JsonWindowHealthCheckRequest request)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.adLanguage = Env.getADLanguageOrBaseLanguage();
		this.onlyAdWindowIds = request.getOnlyAdWindowIds() != null && !request.getOnlyAdWindowIds().isEmpty()
				? ImmutableSet.copyOf(request.getOnlyAdWindowIds())
				: ImmutableSet.of();
		this.contextVariables = ContextVariables.newGlobalContext()
				.withKnownMissing(request.getKnownContextVariables());
		this.missingContextVariables = MissingContextVariables.builder()
				.knownMissing(request.getKnownMissingContextVariables())
				.build();
	}

	public JsonWindowsHealthCheckResponse execute()
	{
		final AdWindowIdSelection adWindowIdSelection = retrieveAdWindowIdSelection();
		missingContextVariables.setAdWindowIdsInScope(adWindowIdSelection);

		final int countTotal = adWindowIdSelection.size();
		int countCurrent = 0;
		final Stopwatch stopwatch = Stopwatch.createStarted();
		for (final AdWindowId adWindowId : adWindowIdSelection)
		{
			this.currentWindowId = adWindowId;
			countCurrent++;

			final WindowId windowId = WindowId.of(adWindowId);
			try
			{
				if (!adWindowIdSelection.isExistingActiveWindow(adWindowId))
				{
					throw new AdempiereException("Not an existing/active window");
				}

				documentDescriptorFactory.invalidateForWindow(windowId);
				final DocumentDescriptor documentDescriptor = documentDescriptorFactory.getDocumentDescriptor(windowId);
				documentDescriptorFactory.invalidateForWindow(windowId);

				final DocumentEntityDescriptor entityDescriptor = documentDescriptor.getEntityDescriptor();
				checkExpressions(null, entityDescriptor, contextVariables);

				final String windowName = this.currentWindowName = documentDescriptor.getEntityDescriptor().getCaption().translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is OK", countCurrent, countTotal, windowName, windowId);
			}
			catch (final Exception ex)
			{
				final String windowName = this.currentWindowName = adWindowDAO.retrieveWindowName(adWindowId).translate(adLanguage);
				logger.info("testWindows [{}/{}] Window `{}` ({}) is NOK: {}", countCurrent, countTotal, windowName, windowId, ex.getLocalizedMessage());

				collectError(DocumentLayoutBuildException.extractCause(ex));
			}
		}

		stopwatch.stop();

		return JsonWindowsHealthCheckResponse.builder()
				.took(stopwatch.toString())
				.countTotal(countTotal)
				.errors(errors)
				.contextVariables(missingContextVariables.toJsonContextVariablesResponse())
				.build();
	}

	private AdWindowIdSelection retrieveAdWindowIdSelection()
	{
		return AdWindowIdSelection.builder()
				.allAdWidowIds(adWindowDAO.retrieveAllActiveAdWindowIds())
				.onlyAdWindowIds(onlyAdWindowIds)
				.build();
	}

	private void collectError(final Throwable exception)
	{
		final String windowName = this.currentWindowName != null
				? this.currentWindowName
				: adWindowDAO.retrieveWindowName(currentWindowId).translate(adLanguage);

		errors.add(JsonWindowsHealthCheckResponse.Entry.builder()
				.windowId(WindowId.of(currentWindowId))
				.windowName(windowName)
				.error(JsonErrors.ofThrowable(exception, adLanguage))
				.build());

	}

	private void checkExpressions(
			@Nullable final ContextPath parentPath,
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final ContextVariables parentContextVariables)
	{
		try
		{
			final ContextPath path = parentPath == null ? ContextPath.root(entityDescriptor) : parentPath.newChild(entityDescriptor);

			//
			// Entity check:
			{
				checkExpression(path, "AllowCreateNewLogic", entityDescriptor.getAllowCreateNewLogic(), parentContextVariables);
				checkExpression(path, "AllowDeleteLogic", entityDescriptor.getAllowDeleteLogic(), parentContextVariables);
				checkExpression(path, "ReadonlyLogic", entityDescriptor.getReadonlyLogic(), parentContextVariables);
				checkExpression(path, "DisplayLogic", entityDescriptor.getDisplayLogic(), parentContextVariables);
			}

			final ContextVariables contextVariables = parentContextVariables.newChildContext(entityDescriptor);

			//
			// Fields check:
			{
				for (final DocumentFieldDescriptor field : entityDescriptor.getFields())
				{
					final ContextPath fieldPath = path.newChild(field.getFieldName());
					checkExpression(fieldPath, "ReadonlyLogic", field.getReadonlyLogic(), contextVariables);
					checkExpression(fieldPath, "DisplayLogic", field.getDisplayLogic(), contextVariables);
					checkExpression(fieldPath, "MandatoryLogic", field.getMandatoryLogic(), contextVariables);
				}
			}

			//
			// Included tabs
			for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
			{
				checkExpressions(path, includedEntityDescriptor, contextVariables);
			}
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("TableName", entityDescriptor.getTableNameOrNull())
					.setParameter("AD_Tab_ID", entityDescriptor.getAdTabId());
		}
	}

	private void checkExpression(
			@NonNull final ContextPath parentPath,
			@NonNull final String expressionName,
			@Nullable final ILogicExpression expression,
			@NonNull final ContextVariables contextVariables)
	{
		if (expression == null || expression.isConstant())
		{
			return;
		}

		try
		{
			final ContextPath path = parentPath.newChild(expressionName);

			for (final String contextVariable : expression.getParameterNames())
			{
				final boolean isFound = contextVariables.contains(contextVariable);
				missingContextVariables.recordContextVariableUsed(path, contextVariable, isFound);

				if (isFound || missingContextVariables.isKnownAsMissing(path, contextVariable))
				{
					continue;
				}

				String message = "No context variable `" + contextVariable + "` found in " + path + ".";
				final Set<String> suggestedParameterNames = contextVariables.findSimilar(contextVariable);
				if (!suggestedParameterNames.isEmpty())
				{
					message += " You might want to use one of " + suggestedParameterNames + " instead.";
				}
				throw new AdempiereException(message)
						.setParameter("expression", expression)
						.setParameter("contextVariables", contextVariables.toSummaryString());
			}
		}
		catch (Exception ex)
		{
			collectError(ex);
		}
	}

}
