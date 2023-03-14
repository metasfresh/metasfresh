package de.metas.handlingunits.qrcodes;

import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import lombok.NonNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HUQRCodeUniqueIdTest
{
	@Disabled("Disabled because this test fails anyways but the aim is how far it gets")
	// usually i got a duplicate after 300 iterations but in some cases i got duplicates after 85 iterations
	@RepeatedTest(50)
	void poorRandomnessTestOfDisplayableSuffix()
	{
		final HashMap<String, HUQRCodeUniqueId> generatedIds = new HashMap<>();
		for (int i = 1; i <= 10000; i++)
		{
			final HUQRCodeUniqueId uniqueId = HUQRCodeUniqueId.ofUUID(UUID.randomUUID());
			@NonNull final String suffix = uniqueId.getDisplayableSuffix();
			final HUQRCodeUniqueId existingId = generatedIds.put(suffix, uniqueId);
			assertThat(existingId)
					.withFailMessage("Got duplicate suffix `" + suffix + "` after " + generatedIds.size() + " iterations: " + existingId + ", " + uniqueId)
					.isNull();
		}
	}
}