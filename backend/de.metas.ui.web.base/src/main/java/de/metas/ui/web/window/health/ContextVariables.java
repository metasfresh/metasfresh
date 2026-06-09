package de.metas.ui.web.window.health;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookups;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.validationRule.IValidationContext;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

class ContextVariables
{
	private static final ImmutableSet<String> GLOBAL_CONTEXT_VARIABLES = ImmutableSet.<String>builder()
			.addAll(ImmutableSet.of(
					"#AD_Client_ID",
					"#AD_Org_ID",
					"#AD_User_ID",
					"#AD_Role_ID",
					"#Date",
					"#IsLiberoEnabled",
					"#AD_Session.HostKey",
					"$C_AcctSchema_ID",
					"$C_Currency_ID",
					"$HasAlias"
			))
			.addAll(Stream.of(AcctSchemaElementType.values())
					.map(elementType -> "$Element_" + elementType.getCode())
					.collect(ImmutableSet.toImmutableSet()))
			.build();

	public static ContextVariables newGlobalContext()
	{
		return ContextVariables.builder()
				.name("GLOBAL CONTEXT")
				.parent(null)
				.contextVariables(GLOBAL_CONTEXT_VARIABLES)
				.build();
	}

	@NonNull private final String name;
	@Nullable private final ContextVariables parent;
	@NonNull private final ImmutableSet<String> contextVariables;
	@NonNull private final ImmutableSet<String> knownMissing;

	@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
	private ContextVariables(
			@NonNull final String name,
			@Nullable ContextVariables parent,
			@Nullable Set<String> contextVariables,
			@Nullable Set<String> knownMissing)
	{
		this.name = name;
		this.parent = parent;
		this.contextVariables = contextVariables != null ? ImmutableSet.copyOf(contextVariables) : ImmutableSet.of();
		this.knownMissing = knownMissing != null ? ImmutableSet.copyOf(knownMissing) : ImmutableSet.of();
	}

	public ContextVariables withKnownMissing(@Nullable final Set<String> knownMissingContextVariables)
	{
		final ImmutableSet<String> knownMissingContextVariablesNorm = knownMissingContextVariables != null
				? ImmutableSet.copyOf(knownMissingContextVariables)
				: ImmutableSet.of();

		return Objects.equals(this.knownMissing, knownMissingContextVariablesNorm)
				? this
				: toBuilder().knownMissing(knownMissingContextVariablesNorm).build();
	}

	public ContextVariables newChildContext(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		return ContextVariables.builder()
				.name("Entity: " + ContextPath.extractName(entityDescriptor))
				.parent(this)
				.contextVariables(extractFieldNames(entityDescriptor))
				.build();
	}

	public ContextVariables newLookupContext(String name)
	{
		return ContextVariables.builder()
				.name(name)
				.parent(this)
				.contextVariables(ImmutableSet.of(
						AccessSqlStringExpression.PARAM_UserRolePermissionsKey.getName(),
						IValidationContext.PARAMETER_ContextTableName,
						SqlForFetchingLookups.SQL_PARAM_ShowInactive.getName(),
						SqlForFetchingLookups.PARAM_Limit.getName(),
						SqlForFetchingLookups.PARAM_Offset.getName(),
						LookupDataSourceContext.PARAM_AD_Language.getName(),
						LookupDataSourceContext.PARAM_UserRolePermissionsKey.getName(),
						LookupDataSourceContext.PARAM_OrgAccessSql.getName(),
						LookupDataSourceContext.PARAM_Filter.getName(),
						LookupDataSourceContext.PARAM_FilterSql.getName(),
						LookupDataSourceContext.PARAM_FilterSqlWithoutWildcards.getName(),
						LookupDataSourceContext.PARAM_ViewId.getName(),
						LookupDataSourceContext.PARAM_ViewSize.getName()
				))
				.build();
	}

	private static ImmutableSet<String> extractFieldNames(final DocumentEntityDescriptor entityDescriptor)
	{
		return entityDescriptor.getFields().stream().map(DocumentFieldDescriptor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

	public Set<String> findSimilar(String contextVariable)
	{
		final ImmutableSet.Builder<String> result = ImmutableSet.builder();

		contextVariables.stream()
				.filter(item -> isSimilar(item, contextVariable))
				.forEach(result::add);

		if (parent != null)
		{
			result.addAll(parent.findSimilar(contextVariable));
		}

		return result.build();
	}

	private static boolean isSimilar(@NonNull String contextVariable1, @NonNull String contextVariable2)
	{
		return contextVariable1.equalsIgnoreCase(contextVariable2);
	}

	public boolean contains(@NonNull final String contextVariable)
	{
		if (contextVariables.contains(contextVariable))
		{
			return true;
		}

		if (knownMissing.contains(contextVariable))
		{
			return true;
		}

		return parent != null && parent.contains(contextVariable);
	}

	@Override
	@Deprecated
	public String toString() {return toSummaryString();}

	public String toSummaryString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(":");
		sb.append("\n\tContext variables: ").append(String.join(",", contextVariables));

		if (!knownMissing.isEmpty())
		{
			sb.append("\n\tKnown missing context variables: ").append(String.join(",", knownMissing));
		}

		if (parent != null)
		{
			sb.append("\n--- parent: ----\n");
			sb.append(parent.toSummaryString());
		}

		return sb.toString();
	}
}

