package de.metas.contracts.modular.settings;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
class ModularContractTypeMap
{
	private final ImmutableMap<ModularContractTypeId, ModularContractType> byId;

	private ModularContractTypeMap(@NonNull final List<ModularContractType> list)
	{
		this.byId = Maps.uniqueIndex(list, ModularContractType::getId);
	}

	public static Collector<ModularContractType, ?, ModularContractTypeMap> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ModularContractTypeMap::new);
	}

	@NonNull
	public ModularContractType getById(@NonNull final ModularContractTypeId id)
	{
		final ModularContractType type = byId.get(id);
		if (type == null)
		{
			throw new AdempiereException("No contract type found for " + id);
		}
		return type;
	}
}
