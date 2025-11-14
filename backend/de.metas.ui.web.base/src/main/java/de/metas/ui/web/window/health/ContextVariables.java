package de.metas.ui.web.window.health;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

class ContextVariables
{
	private static final ImmutableSet<String> GLOBAL_CONTEXT_VARIABLES = ImmutableSet.<String>builder()
			.addAll(ImmutableSet.of(
					"#AD_User_ID",
					"#AD_Role_ID",
					"#AD_Client_ID",
					"#Date",
					"#IsLiberoEnabled",
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
				.parent(null)
				.contextVariables(GLOBAL_CONTEXT_VARIABLES)
				.build();
	}

	@Nullable private final ContextVariables parent;
	@NonNull private final ImmutableSet<String> contextVariables;
	@NonNull private final ImmutableSet<String> knownMissing;

	@Builder(access = AccessLevel.PRIVATE, toBuilder = true)
	private ContextVariables(
			@Nullable ContextVariables parent,
			@Nullable Set<String> contextVariables,
			@Nullable Set<String> knownMissing)
	{
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
		return newChildContext(extractFieldNames(entityDescriptor));
	}

	private static ImmutableSet<String> extractFieldNames(final DocumentEntityDescriptor entityDescriptor)
	{
		return entityDescriptor.getFields().stream().map(DocumentFieldDescriptor::getFieldName).collect(ImmutableSet.toImmutableSet());
	}

	public ContextVariables newChildContext(final Set<String> contextVariables)
	{
		return ContextVariables.builder()
				.parent(this)
				.contextVariables(contextVariables != null ? ImmutableSet.copyOf(contextVariables) : ImmutableSet.of())
				.build();
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
		sb.append("Context variables: ").append(String.join(",", contextVariables));

		if (!knownMissing.isEmpty())
		{
			sb.append("\nKnown missing context variables: ").append(String.join(",", knownMissing));
		}

		if (parent != null)
		{
			sb.append("\n--- parent: ----\n");
			sb.append(parent.toSummaryString());
		}

		return sb.toString();
	}
}
