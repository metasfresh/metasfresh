package org.adempiere.warehouse.qrcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocatorQRCodeTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@SuppressWarnings("SameParameterValue")
	private I_M_Locator createLocator(final LocatorId locatorId, final String value)
	{
		final I_M_Locator record = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		record.setM_Locator_ID(locatorId.getRepoId());
		record.setM_Warehouse_ID(locatorId.getWarehouseId().getRepoId());
		record.setValue(value);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	@Test
	void ofGlobalQRCodeJsonString_toGlobalQRCodeJsonString()
	{
		final LocatorId locatorId = LocatorId.ofRepoId(1000001, 1000002);
		final I_M_Locator locator = createLocator(locatorId, "locator1");
		final LocatorQRCode qrCode = LocatorQRCode.ofLocator(locator);

		Assertions.assertThat(LocatorQRCode.ofGlobalQRCodeJsonString(qrCode.toGlobalQRCodeJsonString()))
				.usingRecursiveComparison()
				.isEqualTo(qrCode);
	}

	/**
	 * IMPORTANT: Makes sure that already printed QR codes are still valid.
	 */
	@Test
	void fromGlobalQRCode_v1()
	{
		Assertions.assertThat(LocatorQRCode.ofGlobalQRCodeJsonString("LOC#1#{\"warehouseId\":1000001,\"locatorId\":1000002,\"caption\":\"locator1\"}"))
				.usingRecursiveComparison()
				.isEqualTo(LocatorQRCode.builder()
						.locatorId(LocatorId.ofRepoId(1000001, 1000002))
						.caption("locator1")
						.build());
	}

	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final LocatorId locatorId = LocatorId.ofRepoId(1000001, 1000002);
		final I_M_Locator locator = createLocator(locatorId, "locator1");
		final LocatorQRCode qrCode = LocatorQRCode.ofLocator(locator);
		testSerializeDeserialize(qrCode);
	}

	void testSerializeDeserialize(final LocatorQRCode obj) throws JsonProcessingException
	{
		System.out.println("Object: " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final String jsonString = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("JSON: " + obj);

		final LocatorQRCode objDeserialized = jsonObjectMapper.readValue(jsonString, LocatorQRCode.class);
		assertThat(objDeserialized).isEqualTo(obj);
		assertThat(objDeserialized).usingRecursiveComparison().isEqualTo(obj);
	}
}