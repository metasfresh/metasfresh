package de.metas.scannable_code.format.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.scannable_code.format.ScannableCodeFormat;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.scannable_code.format.ScannableCodeFormatPart;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonScannableCodeFormatTest
{
	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		final ScannableCodeFormat format = ScannableCodeFormat.builder()
				.id(ScannableCodeFormatId.ofRepoId(1))
				.name("Test")
				.parts(ImmutableList.of(
						ScannableCodeFormatPart.builder().startPosition(1).endPosition(4).type(ScannableCodeFormatPartType.ProductCode).build(),
						ScannableCodeFormatPart.builder().startPosition(5).endPosition(10).type(ScannableCodeFormatPartType.WeightInKg).build(),
						ScannableCodeFormatPart.builder().startPosition(11).endPosition(18).type(ScannableCodeFormatPartType.LotNo).build(),
						ScannableCodeFormatPart.builder().startPosition(19).endPosition(24).type(ScannableCodeFormatPartType.Ignored).build(),
						ScannableCodeFormatPart.builder().startPosition(25).endPosition(30).type(ScannableCodeFormatPartType.BestBeforeDate).build()
				))
				.build();

		final JsonScannableCodeFormat json = JsonScannableCodeFormat.of(format);

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonString = objectMapper.writeValueAsString(json);
		final JsonScannableCodeFormat jsonDeserialized = objectMapper.readValue(jsonString, JsonScannableCodeFormat.class);
		assertThat(jsonDeserialized).usingRecursiveComparison().isEqualTo(json);
		assertThat(jsonDeserialized).isEqualTo(json);
	}
}