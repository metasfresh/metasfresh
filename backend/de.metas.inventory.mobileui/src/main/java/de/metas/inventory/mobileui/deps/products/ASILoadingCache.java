package de.metas.inventory.mobileui.deps.products;

import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;

import java.util.HashMap;

@Builder
public class ASILoadingCache
{
	@NonNull private final IAttributeSetInstanceBL attributeSetInstanceBL;
	private final HashMap<AttributeSetInstanceId, Attributes> byId = new HashMap<>();

	@NonNull
	public Attributes getById(@NonNull final AttributeSetInstanceId asiId)
	{
		return byId.computeIfAbsent(asiId, this::retrieveByIds);
	}

	private Attributes retrieveByIds(@NonNull final AttributeSetInstanceId asiId)
	{
		return Attributes.of(attributeSetInstanceBL.getImmutableAttributeSetById(asiId));
	}
}
