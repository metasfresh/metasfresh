package de.metas.material.cockpit.availableforsales;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class AvailableForSalesQueryTest
{
	@Test
	public void testSerializeDeserializeObject() throws IOException
	{
		testSerializeDeserializeObject(
				AvailableForSalesQuery.builder()
						.productId(ProductId.ofRepoId(1))
						.warehouseId(WarehouseId.ofRepoId(2))
						.storageAttributesKeyPattern(AttributesKeyPattern.ALL)
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(4, 5))
						.dateOfInterest(SystemTime.asInstant())
						.shipmentDateLookAheadHours(12)
						.salesOrderLookBehindHours(13)
						.build()
		);
	}

	private void testSerializeDeserializeObject(final AvailableForSalesQuery value) throws IOException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

}
