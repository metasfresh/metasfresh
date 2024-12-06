package de.metas.invoice.matchinv;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_MatchInv;

@AllArgsConstructor
public enum MatchInvType implements ReferenceListAwareEnum
{
	Material(X_M_MatchInv.TYPE_Material),
	Cost(X_M_MatchInv.TYPE_Cost);

	@Getter private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<MatchInvType> index = ReferenceListAwareEnums.index(values());

	public static MatchInvType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isMaterial() {return Material.equals(this);}
	public boolean isCost() {return Cost.equals(this);}
}
