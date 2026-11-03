package org.adempiere.mmovement;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

/**
 * Filters for {@link org.adempiere.mmovement.api.IMovementDAO#retrieveLinesByQuery(MovementLineQuery)}.
 * Every filter is optional; an unset/empty one is simply not applied — but at least one must be set
 * (see {@link #isEmpty()}), else the query would match an arbitrary line of the system.
 */
@Value
@Builder
public class MovementLineQuery
{
	@Nullable ProductId productId;

	/** Restricts {@code M_MovementLine.M_Locator_ID} (the source locator). */
	@NonNull @Singular ImmutableSet<LocatorId> fromLocatorIds;

	/** Restricts {@code M_MovementLine.M_LocatorTo_ID} (the target locator). */
	@NonNull @Singular ImmutableSet<LocatorId> toLocatorIds;

	/** Restricts the DocStatus of the line's {@code M_Movement} header. */
	@Nullable DocStatus movementDocStatus;

	/** True if no filter at all is set, i.e. this query would match every movement line. */
	public boolean isEmpty()
	{
		return productId == null
				&& fromLocatorIds.isEmpty()
				&& toLocatorIds.isEmpty()
				&& movementDocStatus == null;
	}
}
