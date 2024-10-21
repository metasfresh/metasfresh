package de.metas.handlingunits.qrcodes.gs1;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class GS1HUQRCodeTest
{
	@Test
	void happyCase()
	{
		final GS1HUQRCode gs1 = GS1HUQRCode.fromStringOrNullIfNotHandled("0197311876341811310300752015170809");
		assertThat(gs1).isNotNull();

		assertThat(gs1.getWeightInKg()).contains(new BigDecimal("7.520"));
		assertThat(gs1.getBestBeforeDate()).contains(LocalDate.parse("2017-08-09"));
		assertThat(gs1.getLotNumber()).isEmpty();
	}

}