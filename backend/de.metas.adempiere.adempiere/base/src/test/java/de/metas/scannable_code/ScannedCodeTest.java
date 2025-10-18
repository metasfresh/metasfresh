package de.metas.scannable_code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ScannedCodeTest
{
	ObjectMapper jsonObjectMapper;

	@BeforeEach
	void beforeEach()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	static Stream<Arguments> testDeserializeCases()
	{
		return Stream.of(
				Arguments.of("{ \"scannedCode\": 123 }", "123"),
				Arguments.of("{ \"scannedCode\": \"123\" }", "123"),
				Arguments.of("{ \"scannedCode\": \"\" }", null),
				Arguments.of("{ \"scannedCode\": \"      \" }", null),
				Arguments.of("{ \"scannedCode\": null }", null)
		);
	}

	@ParameterizedTest
	@MethodSource("testDeserializeCases")
	void testDeserialize(String scannedCodeContainerStr, String expectedScannedCodeStr) throws JsonProcessingException
	{
		final ScannedCodeContainer scannedCodeContainer = jsonObjectMapper.readValue(scannedCodeContainerStr, ScannedCodeContainer.class);
		assertThat(scannedCodeContainer.getScannedCode()).isEqualTo(ScannedCode.ofNullableString(expectedScannedCodeStr));
	}

	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(null);
		testSerializeDeserialize(ScannedCode.ofString("123"));
		testSerializeDeserialize(ScannedCode.ofString("ABC"));
	}

	void testSerializeDeserialize(ScannedCode scannedCode) throws JsonProcessingException
	{
		final ScannedCodeContainer scannedCodeContainer = ScannedCodeContainer.builder()
				.scannedCode(scannedCode)
				.build();

		final String json = jsonObjectMapper.writeValueAsString(scannedCodeContainer);
		final ScannedCodeContainer scannedCodeContainer2 = jsonObjectMapper.readValue(json, ScannedCodeContainer.class);
		assertThat(scannedCodeContainer2.getScannedCode()).isEqualTo(scannedCodeContainer.getScannedCode());
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	private static class ScannedCodeContainer
	{
		@Nullable ScannedCode scannedCode;
	}
}