package de.metas.product;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

/**
 * Dimension calculation mode for a TU packing-instruction version.
 * Controls how L/W/H is derived when multiple items with differing dimensions
 * are packed into one Transport Unit (HU_UnitType=TU).
 *
 * <p>Values map to the {@code PackageDimensionCalcMethod} AD_Reference (ID 542122).</p>
 *
 * @see de.metas.handlingunits.shipping.HUPackageBL
 */
@Getter
@RequiredArgsConstructor
public enum PackageDimensionCalcMethod implements ReferenceListAwareEnum
{
	/**
	 * Items are strapped together: the stacking-axis edge is the sum of each unit's smallest edge;
	 * the two larger TU edges are the maximum of those edges across all contained products.
	 */
	Strapping("S"),

	/**
	 * Items are repacked into a box: TU volume = sum(L*W*H per unit) * 1.05;
	 * TU shape derived from volume via the height/width/length formula.
	 */
	Repacking("R"),

	/**
	 * Items are nested: TU dimensions equal those of the contained item with the largest single edge.
	 */
	Nesting("N"),
	;

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<PackageDimensionCalcMethod> index =
			ReferenceListAwareEnums.index(values());

	public static PackageDimensionCalcMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static PackageDimensionCalcMethod ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
