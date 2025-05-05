package de.metas.handlingunits.inout.returns.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
@Builder
public class MultiCustomerHUReturnsResult
{
	@NonNull @Singular ImmutableList<CustomerHUReturnsResult> items;

	public List<I_M_InOut> getCustomerReturns()
	{
		return items.stream().map(CustomerHUReturnsResult::getCustomerReturn).collect(Collectors.toList());
	}

	public Set<HuId> getReturnedHUIds()
	{
		return items.stream()
				.flatMap(CustomerHUReturnsResult::streamReturnedHUIds)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Value
	@Builder
	public static class CustomerHUReturnsResult
	{
		@NonNull I_M_InOut customerReturn;
		@NonNull List<I_M_HU> returnedHUs;

		private Stream<HuId> streamReturnedHUIds()
		{
			return streamReturnedHUs().map(returnedHU -> HuId.ofRepoId(returnedHU.getM_HU_ID()));
		}

		private Stream<I_M_HU> streamReturnedHUs()
		{
			return returnedHUs.stream();
		}

	}
}
