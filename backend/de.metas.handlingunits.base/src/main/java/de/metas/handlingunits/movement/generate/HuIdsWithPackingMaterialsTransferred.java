package de.metas.handlingunits.movement.generate;

import de.metas.handlingunits.HuId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;

@EqualsAndHashCode
@ToString
public final class HuIdsWithPackingMaterialsTransferred
{
	private final HashSet<HuId> huIds = new HashSet<>();

	/**
	 * @return true if first time added
	 */
	public boolean addHuId(final @NonNull HuId huId)
	{
		return huIds.add(huId);
	}
}
