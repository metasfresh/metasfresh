package de.metas.ui.web.window.health;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.UnaryOperator;

class ContextVariablesCheckCommand
{
	// Params
	@NonNull private final MissingContextVariables missingContextVariables;
	@NonNull private final ErrorsCollector errorsCollector;
	@NonNull private final ContextVariables rootContextVariables;
	@NonNull private final DocumentEntityDescriptor rootEntityDescriptor;

	// State
	private ContextPath _currentPath;
	private ContextVariables _currentContextVariables;

	@Builder
	private ContextVariablesCheckCommand(
			@NonNull final MissingContextVariables missingContextVariables,
			@NonNull final ErrorsCollector errorsCollector,
			@NonNull final ContextVariables rootContextVariables,
			@NonNull final DocumentEntityDescriptor rootEntityDescriptor)
	{
		this.missingContextVariables = missingContextVariables;
		this.errorsCollector = errorsCollector;
		this.rootContextVariables = rootContextVariables;
		this.rootEntityDescriptor = rootEntityDescriptor;
	}

	public void execute()
	{
		withContextVariables((ignored) -> rootContextVariables, () -> checkEntity(rootEntityDescriptor));
	}

	private void withPath(@NonNull UnaryOperator<ContextPath> pathSupplier, @NonNull Runnable runnable)
	{
		final ContextPath previousPath = this._currentPath;
		this._currentPath = pathSupplier.apply(previousPath);
		try
		{
			runnable.run();
		}
		finally
		{
			this._currentPath = previousPath;
		}
	}

	private ContextPath getCurrentPath() {return _currentPath;}

	private void withContextVariables(@NonNull UnaryOperator<ContextVariables> contextSupplier, @NonNull Runnable runnable)
	{
		final ContextVariables previousContextVariables = this._currentContextVariables;
		this._currentContextVariables = contextSupplier.apply(previousContextVariables);
		try
		{
			runnable.run();
		}
		finally
		{
			this._currentContextVariables = previousContextVariables;
		}
	}

	private ContextVariables getCurrentContextVariables() {return _currentContextVariables;}

	private void checkEntity(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		withPath(
				parentPath -> parentPath == null ? ContextPath.root(entityDescriptor) : parentPath.newChild(entityDescriptor),
				() -> {
					try
					{
						//
						// Entity check:
						{
							final ContextPath path = getCurrentPath();
							checkLogicExpression(path.newChild("AllowCreateNewLogic"), entityDescriptor.getAllowCreateNewLogic());
							checkLogicExpression(path.newChild("AllowDeleteLogic"), entityDescriptor.getAllowDeleteLogic());
							checkLogicExpression(path.newChild("ReadonlyLogic"), entityDescriptor.getReadonlyLogic());
							checkLogicExpression(path.newChild("DisplayLogic"), entityDescriptor.getDisplayLogic());
						}

						//
						// Fields & included tabs check
						withContextVariables(
								parentContextVariables -> parentContextVariables.newChildContext(entityDescriptor),
								() -> {
									checkFields(entityDescriptor);
									checkIncludedTabs(entityDescriptor);
								}
						);
					}
					catch (Exception ex)
					{
						throw wrapException(ex)
								.setParameter("TableName", entityDescriptor.getTableNameOrNull())
								.setParameter("AD_Tab_ID", entityDescriptor.getAdTabIdOrNull());
					}
				}
		);
	}

	private void checkIncludedTabs(final @NotNull DocumentEntityDescriptor entityDescriptor)
	{
		for (final DocumentEntityDescriptor includedEntityDescriptor : entityDescriptor.getIncludedEntities())
		{
			checkEntity(includedEntityDescriptor);
		}
	}

	private void checkFields(final @NonNull DocumentEntityDescriptor entityDescriptor)
	{
		for (final DocumentFieldDescriptor field : entityDescriptor.getFields())
		{
			checkField(field);
		}
	}

	private void checkField(@NonNull final DocumentFieldDescriptor field)
	{
		final ContextPath parentPath = getCurrentPath();
		final ContextPath fieldPath = parentPath.newChild(field.getFieldName());
		checkLogicExpression(fieldPath.newChild("ReadonlyLogic"), field.getReadonlyLogic());
		checkLogicExpression(fieldPath.newChild("DisplayLogic"), field.getDisplayLogic());
		checkLogicExpression(fieldPath.newChild("MandatoryLogic"), field.getMandatoryLogic());
		checkLookupDescriptor(fieldPath.newChild("LookupDescriptor"), field.getLookupDescriptor().orElse(null));
	}

	private void checkLogicExpression(@NonNull final ContextPath path, @NonNull final ILogicExpression expression)
	{
		if (expression.isConstant()) {return;}
		checkExpression(path, ContextVariablesExpression.ofLogicExpression(expression));
	}

	private void checkLookupDescriptor(@NonNull final ContextPath path, @Nullable final LookupDescriptor lookupDescriptor)
	{
		if (lookupDescriptor == null) {return;}

		final ContextVariablesExpression expression = ContextVariablesExpression.ofLookupDescriptor(lookupDescriptor);
		if (expression.isConstant()) {return;}

		withContextVariables(
				parentContext -> parentContext.newLookupContext("Lookup of " + path),
				() -> checkExpression(path, expression));
	}

	private void checkExpression(@NonNull final ContextPath path, @NonNull final ContextVariablesExpression expression)
	{
		if (expression.isConstant()) {return;}

		try
		{
			for (final String contextVariable : expression.getRequiredContextVariables())
			{
				if (checkContextVariableResolved(path, contextVariable))
				{
					continue;
				}

				String message = "No context variable `" + contextVariable + "` found in " + path + ".";
				final ContextVariables contextVariables = getCurrentContextVariables();
				final Set<String> suggestedParameterNames = contextVariables.findSimilar(contextVariable);
				if (!suggestedParameterNames.isEmpty())
				{
					message += " You might want to use one of " + suggestedParameterNames + " instead.";
				}

				errorsCollector.collectError(
						newException(message).setParameter("expression", expression)
				);
			}
		}
		catch (Exception ex)
		{
			errorsCollector.collectError(ex);
		}
	}

	private boolean checkContextVariableResolved(@NonNull final ContextPath path, @NonNull final String contextVariable)
	{
		final ContextVariables contextVariables = getCurrentContextVariables();
		final boolean isFound = contextVariables.contains(contextVariable);
		missingContextVariables.recordContextVariableUsed(path, contextVariable, isFound);

		return isFound || missingContextVariables.isKnownAsMissing(path, contextVariable);
	}

	private AdempiereException newException(final String message)
	{
		final AdempiereException exception = new AdempiereException(message);
		fillContextInfo(exception);
		return exception;
	}

	private AdempiereException wrapException(final Exception exception)
	{
		final AdempiereException wrappedException = AdempiereException.wrapIfNeeded(exception);
		fillContextInfo(wrappedException);
		return wrappedException;
	}

	private void fillContextInfo(final AdempiereException exception)
	{
		exception.setParameter("contextPath", getCurrentPath());

		final ContextVariables contextVariables = getCurrentContextVariables();
		if (contextVariables != null)
		{
			exception.setParameter("contextVariables", contextVariables.toSummaryString());
		}
	}

}
