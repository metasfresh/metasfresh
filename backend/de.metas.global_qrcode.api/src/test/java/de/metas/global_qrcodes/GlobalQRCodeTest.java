package de.metas.global_qrcodes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GlobalQRCodeTest
{
	@Test
	void test_ofString_getAsString()
	{
		final GlobalQRCode qrCode = GlobalQRCode.of(
				GlobalQRCodeType.ofString("type"),
				GlobalQRCodeVersion.ofString("123"),
				"JSON_DATA");

		final GlobalQRCode qrCodeCopy = GlobalQRCode.ofString(qrCode.getAsString());
		assertThat(qrCodeCopy).isEqualTo(qrCode);
		assertThat(qrCodeCopy.getType()).isSameAs(qrCode.getType());
		assertThat(qrCodeCopy.getVersion()).isSameAs(qrCode.getVersion());
	}

	@Test
	void test_SerializeDeserialize() throws JsonProcessingException
	{
		final GlobalQRCode qrCode = GlobalQRCode.of(
				GlobalQRCodeType.ofString("type"),
				GlobalQRCodeVersion.ofString("123"),
				"JSON_DATA");

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String json = objectMapper.writeValueAsString(qrCode);
		final GlobalQRCode qrCodeDeserialized = objectMapper.readValue(json, GlobalQRCode.class);
		assertThat(qrCodeDeserialized).isEqualTo(qrCode);
		assertThat(qrCodeDeserialized.getType()).isSameAs(qrCode.getType());
		assertThat(qrCodeDeserialized.getVersion()).isSameAs(qrCode.getVersion());
	}

	@Nested
	class parse
	{
		@Test
		void orNullIfError_invalidQRCode()
		{
			assertThat(GlobalQRCode.parse("blabla").orNullIfError()).isNull();
		}

		@Test
		void orThrow_invalidQRCode()
		{
			assertThatThrownBy(() -> GlobalQRCode.parse("blabla").orThrow())
					.isInstanceOf(RuntimeException.class)
					.hasMessageContaining("blabla");
		}
	}
}