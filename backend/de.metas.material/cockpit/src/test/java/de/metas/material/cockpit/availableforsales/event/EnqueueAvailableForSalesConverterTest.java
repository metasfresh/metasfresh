package de.metas.material.cockpit.availableforsales.event;

import de.metas.common.util.time.SystemTime;
import de.metas.event.Event;
import de.metas.material.cockpit.availableforsales.AvailableForSalesQuery;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnqueueAvailableForSalesConverterTest
{
	@Test
	public void testSerializeDeserialize()
	{
		final AvailableForSalesQuery query = AvailableForSalesQuery.builder()
				.productId(ProductId.ofRepoId(1))
				.warehouseId(WarehouseId.ofRepoId(2))
				.storageAttributesKeyPattern(AttributesKeyPattern.ALL)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(4, 5))
				.dateOfInterest(SystemTime.asInstant())
				.shipmentDateLookAheadHours(12)
				.salesOrderLookBehindHours(13)
				.build();

		testSerializeDeserialize(
				EnqueueAvailableForSalesRequest.builder()
						.availableForSalesQuery(query)
						.build());
		testSerializeDeserialize(
				EnqueueAvailableForSalesRequest.builder()
						.availableForSalesQuery(query)
						.contextUserId(UserId.ofRepoId(1000))
						.contextRoleId(RoleId.ofRepoId(1001))
						.build());
	}

	public void testSerializeDeserialize(EnqueueAvailableForSalesRequest request)
	{
		final Event event = EnqueueAvailableForSalesConverter.toEvent(request);
		final EnqueueAvailableForSalesRequest request2 = EnqueueAvailableForSalesConverter.fromEvent(event);
		assertThat(request2).isEqualTo(request);
	}

}