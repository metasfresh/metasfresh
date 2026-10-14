package de.metas.distribution.ddorder.replenishment.event;

import de.metas.distribution.ddorder.replenishment.DDOrderReplenishmentGroupKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import org.adempiere.warehouse.LocatorId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DDOrderReplenishmentRequestTest
{
	private static final DDOrderReplenishmentGroupKey GROUP_KEY = DDOrderReplenishmentGroupKey.builder()
			.productId(ProductId.ofRepoId(1))
			.locatorToId(LocatorId.ofRepoId(2, 3))
			.uomId(UomId.ofRepoId(4))
			.build();
	private static final ClientAndOrgId CLIENT_AND_ORG = ClientAndOrgId.ofClientAndOrg(1000000, 1000000);

	private static DDOrderReplenishmentRequest request(final int triggeredByRepoId)
	{
		return DDOrderReplenishmentRequest.builder()
				.groupKey(GROUP_KEY)
				.clientAndOrgId(CLIENT_AND_ORG)
				.triggeredBy(PickingJobScheduleId.ofRepoId(triggeredByRepoId))
				.build();
	}

	@Test
	void twoRequestsDifferingOnlyInTriggeredBy_areEqual()
	{
		assertThat(request(10)).isEqualTo(request(20));
		assertThat(request(10)).hasSameHashCodeAs(request(20));
	}

	@Test
	void requestsOfDifferentGroups_areNotEqual()
	{
		final DDOrderReplenishmentRequest other = request(10).toBuilder()
				.groupKey(GROUP_KEY.toBuilder().productId(ProductId.ofRepoId(999)).build())
				.build();
		assertThat(request(10)).isNotEqualTo(other);
	}

	@Test
	void converter_roundTripsAllFields()
	{
		final DDOrderReplenishmentRequest original = request(10);
		assertThat(DDOrderReplenishmentRequestConverter.fromEvent(DDOrderReplenishmentRequestConverter.toEvent(original)))
				.isEqualTo(original)
				.extracting(DDOrderReplenishmentRequest::getTriggeredBy)
				.isEqualTo(PickingJobScheduleId.ofRepoId(10));
	}
}
