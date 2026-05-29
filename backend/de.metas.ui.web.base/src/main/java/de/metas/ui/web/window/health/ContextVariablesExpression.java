package de.metas.ui.web.window.health;

import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.GenericSqlLookupDataSourceFetcher;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.CtxNames;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
class ContextVariablesExpression
{
	@NonNull private final Object actualExpression;
	@NonNull @Getter private final ImmutableSet<String> requiredContextVariables;

	private ContextVariablesExpression(
			@NonNull final Object actualExpression,
			@NonNull final Set<String> requiredContextVariables)
	{
		this.actualExpression = actualExpression;
		this.requiredContextVariables = ImmutableSet.copyOf(requiredContextVariables);
	}

	@NonNull
	public static ContextVariablesExpression ofLogicExpression(@NonNull final ILogicExpression expression)
	{
		return new ContextVariablesExpression(expression, expression.getParameterNames());
	}

	@NonNull
	public static ContextVariablesExpression ofLookupDescriptor(@NonNull final LookupDescriptor lookupDescriptor)
	{
		final HashSet<String> requiredContextVariables = new HashSet<>();

		if (lookupDescriptor instanceof SqlLookupDescriptor)
		{
			// NOTE: don't add lookupDescriptor.getDependsOnFieldNames() because that collection contains the postQueryPredicate's required params,
			// which in case they are missing it's hard to determine if that's a problem or not.
			// e.g. FilterWarehouseByDocTypeValidationRule requires C_DocType_ID but behaves OK/expected when the C_DocType_ID context var is missing.
			
			final SqlLookupDescriptor sqlLookupDescriptor = (SqlLookupDescriptor)lookupDescriptor;
			final GenericSqlLookupDataSourceFetcher lookupDataSourceFetcher = sqlLookupDescriptor.getLookupDataSourceFetcher();
			requiredContextVariables.addAll(CtxNames.toNames(lookupDataSourceFetcher.getSqlForFetchingLookups().getParameters()));
			requiredContextVariables.addAll(CtxNames.toNames(lookupDataSourceFetcher.getSqlForFetchingLookupById().getParameters()));
		}
		else
		{
			requiredContextVariables.addAll(lookupDescriptor.getDependsOnFieldNames());
		}

		return new ContextVariablesExpression(lookupDescriptor, requiredContextVariables);
	}

	@Override
	public String toString()
	{
		return String.valueOf(actualExpression);
	}

	public boolean isConstant()
	{
		return requiredContextVariables.isEmpty();
	}
}
