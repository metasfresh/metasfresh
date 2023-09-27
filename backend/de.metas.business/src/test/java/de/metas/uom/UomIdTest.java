package de.metas.uom;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UomIdTest
{
	@Value(staticConstructor = "of")
	private static class UOMAware
	{
		@NonNull UomId uomId;

		// setting it here just to make sure 2 instances with same uom are not equal
		@NonNull UUID unique = UUID.randomUUID();
	}

	private static UOMAware qtyOfUOM(final int uomRepoId) {return UOMAware.of(UomId.ofRepoId(uomRepoId));}

	@Nested
	class getCommonUomIdOfAll
	{
		void assertFails(String messageStartingWith, UOMAware... amts)
		{
			assertThatThrownBy(() -> UomId.getCommonUomIdOfAll(UOMAware::getUomId, "UOMAware", amts))
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith(messageStartingWith);
		}

		@SuppressWarnings("SameParameterValue")
		void assertSuccess(int expectedUomRepoId, UOMAware... amts)
		{
			assertThat(UomId.getCommonUomIdOfAll(UOMAware::getUomId, "UOMAware", amts))
					.isEqualTo(UomId.ofRepoId(expectedUomRepoId));
		}

		@Test
		void nullParam()
		{
			assertFails("No UOMAware provided",
					(UOMAware[])null);
		}

		@Test
		void singleNullParam()
		{
			assertFails("At least one non null UOMAware instance was expected",
					(UOMAware)null);
		}

		@Test
		void multipleNullParams()
		{
			assertFails(
					"At least one non null UOMAware instance was expected",
					null, null, null);
		}

		@Test
		void singleQty() {assertSuccess(1, qtyOfUOM(1));}

		@Test
		void singleQtyAndNullFirst() {assertSuccess(1, null, qtyOfUOM(1));}

		@Test
		void singleQtyAndNulls() {assertSuccess(1, null, qtyOfUOM(1), null);}

		@Test
		void multipleQtysSameUOM() {assertSuccess(1, qtyOfUOM(1), qtyOfUOM(1));}

		@Test
		void multipleQtysSameUOMAndSomeNulls() {assertSuccess(1, qtyOfUOM(1), null, qtyOfUOM(1), null);}

		@Test
		void multipleQtysDifferentUOM()
		{
			assertFails("All given UOMAware(s) shall have the same UOM",
					qtyOfUOM(1), qtyOfUOM(2));
		}

		@Test
		void multipleQtysDifferentUOMsAndSomeNulls()
		{
			assertFails("All given UOMAware(s) shall have the same UOM",
					qtyOfUOM(1), null, qtyOfUOM(2), null);
		}
	}

}