package de.metas.cucumber.stepdefs.util;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.compiere.util.Evaluatee2;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class IdentifiersEvaluatee implements Evaluatee2
{
	@NonNull private final ImmutableList<StepDefDataGetIdAware<?, ?>> tables;

	@Builder
	private IdentifiersEvaluatee(@Singular final List<StepDefDataGetIdAware<?, ?>> tables)
	{
		Check.assumeNotEmpty(tables, "tables is not empty");
		this.tables = ImmutableList.copyOf(tables);
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return resolveIdentifier(StepDefDataIdentifier.ofString(variableName)).isPresent();
	}

	@Override
	public @Nullable String get_ValueOldAsString(final String variableName)
	{
		return get_ValueAsString(variableName);
	}

	@Override
	public @Nullable String get_ValueAsString(final String variableName)
	{
		return resolveIdentifier(StepDefDataIdentifier.ofString(variableName))
				.map(id -> String.valueOf(id.getRepoId()))
				.orElse(null);
	}

	public Optional<RepoIdAware> resolveIdentifier(final StepDefDataIdentifier identifier)
	{
		for (StepDefDataGetIdAware<?, ?> table : tables)
		{
			//noinspection unchecked
			final Optional<RepoIdAware> idOptional = (Optional<RepoIdAware>)table.getIdOptional(identifier);
			if (idOptional.isPresent())
			{
				return idOptional;
			}
		}

		return Optional.empty();
	}
}
