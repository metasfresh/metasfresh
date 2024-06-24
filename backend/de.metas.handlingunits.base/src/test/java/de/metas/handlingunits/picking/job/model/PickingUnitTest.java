package de.metas.handlingunits.picking.job.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

class PickingUnitTest
{
	@ParameterizedTest
	@EnumSource(PickingUnit.class)
	void toJson_ofJson(PickingUnit pickingUnit)
	{
		assertThat(PickingUnit.ofJson(pickingUnit.toJson())).isSameAs(pickingUnit);
	}

	@ParameterizedTest
	@EnumSource(PickingUnit.class)
	void serialize_deserialize(PickingUnit pickingUnit) throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
		final String json = mapper.writeValueAsString(pickingUnit);
		assertThat(json).isEqualTo("\"" + pickingUnit.toJson() + "\"");

		final PickingUnit pickingUnit2 = mapper.readValue(json, PickingUnit.class);
		assertThat(pickingUnit2).isSameAs(pickingUnit);
	}

	@ParameterizedTest
	@EnumSource(PickingUnit.class)
	void isCU(PickingUnit pickingUnit)
	{
		assertThat(pickingUnit.isCU()).isEqualTo(pickingUnit == PickingUnit.CU);
	}

	@ParameterizedTest
	@EnumSource(PickingUnit.class)
	void isTU(PickingUnit pickingUnit)
	{
		assertThat(pickingUnit.isTU()).isEqualTo(pickingUnit == PickingUnit.TU);
	}
}

