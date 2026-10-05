package de.metas.contracts.refund.packaging;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.ConditionsId;
import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Decides whether a sales line takes part in the refund of a contract condition that is restricted to some packaging options.
 */
@Service
public class RefundPackagingFilter
{
	private final ImmutableList<RefundPackagingMaterialProvider> providers;

	public RefundPackagingFilter(@NonNull final Optional<List<RefundPackagingMaterialProvider>> providers)
	{
		this.providers = ImmutableList.copyOf(providers.orElseGet(ImmutableList::of));
	}

	/**
	 * @return {@code true} if the conditions have no packaging options (every line is in), or if the packing material of the order line is one of them.
	 *         {@code false} otherwise, in particular if the line has no order line or no packing instruction.
	 */
	public boolean isIncluded(@NonNull final ConditionsId conditionsId, @Nullable final OrderLineId orderLineId)
	{
		return true; // TODO
	}
}
