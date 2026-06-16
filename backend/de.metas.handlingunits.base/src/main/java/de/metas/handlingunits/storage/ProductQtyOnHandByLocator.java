package de.metas.handlingunits.storage;

import com.google.common.collect.ImmutableMap;
import de.metas.quantity.Quantity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import java.util.Map;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class ProductQtyOnHandByLocator
{
	public static final ProductQtyOnHandByLocator EMPTY = new ProductQtyOnHandByLocator(ImmutableMap.of());

	@NonNull private final ImmutableMap<LocatorId, Quantity> map;

	private ProductQtyOnHandByLocator(@NonNull final Map<LocatorId, Quantity> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	public static ProductQtyOnHandByLocator ofMap(@NonNull Map<LocatorId, Quantity> map)
	{
		return map.isEmpty() ? EMPTY : new ProductQtyOnHandByLocator(map);
	}

	public Map<LocatorId, Quantity> toMap() {return map;}

	public Stream<LocatorId> streamNonEmptyLocatorIds()
	{
		return map.entrySet().stream()
				.filter(ProductQtyOnHandByLocator::hasStock)
				.map(Map.Entry::getKey);
	}

	private static boolean hasStock(final Map.Entry<LocatorId, Quantity> entry)
	{
		return entry.getValue().signum() > 0;
	}

	public boolean hasStock(@NonNull final LocatorId locatorId)
	{
		final Quantity quantity = getQtyOrNull(locatorId);
		return quantity != null && quantity.signum() > 0;
	}

	@NonNull
	public Quantity getQty(@NonNull final LocatorId locatorId)
	{
		final Quantity quantity = getQtyOrNull(locatorId);
		if (quantity == null)
		{
			throw new AdempiereException("No quantity found for " + locatorId + " in " + this);
		}
		return quantity;
	}

	public Quantity getQtyOrNull(@NonNull final LocatorId locatorId)
	{
		return map.get(locatorId);
	}

}
