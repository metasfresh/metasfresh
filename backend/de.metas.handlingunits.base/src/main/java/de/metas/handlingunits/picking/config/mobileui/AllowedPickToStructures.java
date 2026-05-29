package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(of = "map")
public class AllowedPickToStructures
{
	public static final AllowedPickToStructures EMPTY = new AllowedPickToStructures(ImmutableMap.of());
	public static final AllowedPickToStructures DEFAULT = new AllowedPickToStructures(ImmutableMap.<PickToStructure, Boolean>builder()
			.put(PickToStructure.LU_TU, true)
			.build());

	@NonNull private final ImmutableMap<PickToStructure, Boolean> map;
	@NonNull private final ImmutableSet<PickToStructure> allowed;

	private AllowedPickToStructures(@NonNull final ImmutableMap<PickToStructure, Boolean> map)
	{
		this.map = map;
		this.allowed = extractAllowed(map);
	}

	private static ImmutableSet<PickToStructure> extractAllowed(final @NotNull ImmutableMap<PickToStructure, Boolean> map)
	{
		final ImmutableSet.Builder<PickToStructure> resultBuilder = ImmutableSet.builder();
		map.forEach((pickToStructure, allowedFlag) -> {
			if (allowedFlag)
			{
				resultBuilder.add(pickToStructure);
			}
		});

		return resultBuilder.build();
	}

	public static AllowedPickToStructures ofMap(@NonNull final Map<PickToStructure, Boolean> map)
	{
		if (map.isEmpty())
		{
			return EMPTY;
		}

		return new AllowedPickToStructures(ImmutableMap.copyOf(map));
	}

	public static AllowedPickToStructures ofAllowed(@NonNull final Set<PickToStructure> allowed)
	{
		if (allowed.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableMap.Builder<PickToStructure, Boolean> mapBuilder = ImmutableMap.builder();
		allowed.forEach(pickToStructure -> mapBuilder.put(pickToStructure, true));

		return ofMap(mapBuilder.build());
	}

	public boolean isEmpty() {return map.isEmpty();}

	public @NonNull AllowedPickToStructures fallbackTo(final @NonNull AllowedPickToStructures fallback)
	{
		final HashMap<PickToStructure, Boolean> newMap = new HashMap<>();
		for (final PickToStructure pickToStructure : PickToStructure.values())
		{
			if (map.containsKey(pickToStructure))
			{
				newMap.put(pickToStructure, map.get(pickToStructure));
			}
			else if (fallback.map.containsKey(pickToStructure))
			{
				newMap.put(pickToStructure, fallback.map.get(pickToStructure));
			}
		}

		final AllowedPickToStructures result = ofMap(newMap);
		return Objects.equals(this, result) ? this : result;
	}

	public boolean isStrictlyAllowed(@NonNull final PickToStructure pickToStructure)
	{
		return map.getOrDefault(pickToStructure, false);
	}

	public @NonNull ImmutableSet<PickToStructure> toAllowedSet() {return allowed;}
}
