package de.metas.handlingunits.qrcodes;

import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import org.assertj.core.api.AbstractStringAssert;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HUQRCodeUniqueIdTest
{
	@Nested
	class ofUUID
	{
		AbstractStringAssert<?> assertFromUUIDToString(String uuidString)
		{
			return assertThat(HUQRCodeUniqueId.ofUUID(UUID.fromString(uuidString)).getAsString());
		}

		@Test
		void suffix_76b2()
		{
			assertFromUUIDToString("53c5f490-f46d-4aae-a357-fefc2c0d76b2")
					.isEqualTo("53c5f490f46d4aaea357fefc2c0d-30386");
		}

		@Test
		void suffix_0000()
		{
			assertFromUUIDToString("53c5f490-f46d-4aae-a357-fefc2c0d0000")
					.isEqualTo("53c5f490f46d4aaea357fefc2c0d-00000");
		}

		@Test
		void suffix_0010()
		{
			assertFromUUIDToString("53c5f490-f46d-4aae-a357-fefc2c0d0010")
					.isEqualTo("53c5f490f46d4aaea357fefc2c0d-00016");
		}

		@Test
		void suffix_FFFF()
		{
			assertFromUUIDToString("53c5f490-f46d-4aae-a357-fefc2c0dffff")
					.isEqualTo("53c5f490f46d4aaea357fefc2c0d-65535");
		}
	}
}