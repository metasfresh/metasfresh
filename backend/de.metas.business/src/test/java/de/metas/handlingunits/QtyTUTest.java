package de.metas.handlingunits;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QtyTUTest
{
	private I_C_UOM uomPCE;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomPCE = BusinessTestHelper.createUomPCE();
	}

	@Test
	void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(QtyTU.ofInt(5));
	}

	void testSerializeDeserialize(final Object obj) throws IOException
	{
		final ObjectMapper maper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = maper.writeValueAsString(obj);
		final Object objDeserialized = maper.readValue(json, obj.getClass());
		assertThat(objDeserialized).isEqualTo(obj);
	}

	@Value
	@Builder
	@Jacksonized
	public static class QtyTUContainer
	{
		@NonNull QtyTU value;
	}

	@Test
	void testDeserializeFromString() throws JsonProcessingException
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = "{ \"value\": \"5\" }";
		// System.out.println("JSON: " + json);
		// System.out.println("AAAA: " + jsonObjectMapper.writeValueAsString(QtyTUContainer.builder().value(QtyTU.ofInt(5)).build()));

		final QtyTUContainer objDeserialized = jsonObjectMapper.readValue(json, QtyTUContainer.class);
		assertThat(objDeserialized).isEqualTo(QtyTUContainer.builder()
				.value(QtyTU.ofInt(5))
				.build());
	}

	@Nested
	class ofInt
	{
		@Test
		void zero() {assertThat(QtyTU.ofInt(0)).isSameAs(QtyTU.ZERO);}

		@Test
		void one() {assertThat(QtyTU.ofInt(1)).isSameAs(QtyTU.ONE);}

		@Test
		void two() {assertThat(QtyTU.ofInt(2)).isSameAs(QtyTU.TWO);}

		@Test
		void three() {assertThat(QtyTU.ofInt(3)).isSameAs(QtyTU.THREE);}

		@Test
		void four() {assertThat(QtyTU.ofInt(4)).isSameAs(QtyTU.FOUR);}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 })
		void testCached(int intValue)
		{
			final QtyTU qtyTU = QtyTU.ofInt(intValue);
			assertThat(qtyTU).isSameAs(QtyTU.ofInt(intValue));
			assertThat(qtyTU.toInt()).isEqualTo(intValue);
		}
	}

	@Nested
	class add
	{
		@Test
		void test_12345_and_0()
		{
			final QtyTU qtyTU = QtyTU.ofInt(12345);
			assertThat(qtyTU.add(QtyTU.ofInt(0))).isSameAs(qtyTU);
			assertThat(QtyTU.ofInt(0).add(qtyTU)).isSameAs(qtyTU);
		}

		@Test
		void test_12345_and_10000()
		{
			assertThat(QtyTU.ofInt(12345).add(QtyTU.ofInt(10000))).isEqualTo(QtyTU.ofInt(22345));
		}
	}

	@Nested
	class subtractOrZero
	{
		@Test
		void test_12345_minus_0()
		{
			final QtyTU qtyTU = QtyTU.ofInt(12345);
			assertThat(qtyTU.subtractOrZero(QtyTU.ofInt(0))).isSameAs(qtyTU);
		}

		@Test
		void test_12345_and_10000()
		{
			assertThat(QtyTU.ofInt(12345).subtractOrZero(QtyTU.ofInt(10000))).isEqualTo(QtyTU.ofInt(2345));
		}

		@Test
		void test_12345_and_20000()
		{
			assertThat(QtyTU.ofInt(12345).subtractOrZero(QtyTU.ofInt(20000))).isSameAs(QtyTU.ZERO);
		}
	}

	@Nested
	class computeQtyCUsPerTUUsingTotalQty
	{
		@Test
		void qtyTU_is_zero()
		{
			assertThatThrownBy(() -> QtyTU.ZERO.computeQtyCUsPerTUUsingTotalQty(Quantity.of(123, uomPCE)))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Cannot determine Qty CUs/TU when QtyTU is zero");

		}

		@Test
		void qtyTU_is_one()
		{
			assertThat(QtyTU.ONE.computeQtyCUsPerTUUsingTotalQty(Quantity.of(123, uomPCE)))
					.isEqualTo(Quantity.of(123, uomPCE));
		}

		@Test
		void of_14CUs_3TUs()
		{
			assertThat(QtyTU.ofInt(3).computeQtyCUsPerTUUsingTotalQty(Quantity.of(14, uomPCE)))
					.isEqualTo(Quantity.of(5, uomPCE));
		}
	}

	@Nested
	class computeTotalQtyCUsUsingQtyCUsPerTU
	{
		@Test
		void qtyTU_is_zero()
		{
			assertThat(QtyTU.ZERO.computeTotalQtyCUsUsingQtyCUsPerTU(Quantity.of(123, uomPCE)))
					.isEqualTo(Quantity.zero(uomPCE));
		}

		@Test
		void qtyTU_is_one()
		{
			assertThat(QtyTU.ONE.computeTotalQtyCUsUsingQtyCUsPerTU(Quantity.of(123, uomPCE)))
					.isEqualTo(Quantity.of(123, uomPCE));
		}

		@Test
		void of_3TUs_5CUsPerTU()
		{
			assertThat(QtyTU.ofInt(3).computeTotalQtyCUsUsingQtyCUsPerTU(Quantity.of(5, uomPCE)))
					.isEqualTo(Quantity.of(15, uomPCE));
		}
	}
}
