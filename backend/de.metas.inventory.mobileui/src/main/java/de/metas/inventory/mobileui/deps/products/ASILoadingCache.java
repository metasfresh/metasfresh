package de.metas.inventory.mobileui.deps.products;

import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.HashMap;

@Builder
public class ASILoadingCache
{
	@NonNull private final IAttributeSetInstanceBL attributeSetInstanceBL;
	private final HashMap<AttributeSetInstanceId, ImmutableAttributeSet> byId = new HashMap<>();

	@NonNull
	public ImmutableAttributeSet getById(@NonNull final AttributeSetInstanceId asiId)
	{
		return byId.computeIfAbsent(asiId, attributeSetInstanceBL::getImmutableAttributeSetById);
	}
}
