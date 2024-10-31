package de.metas.handlingunits.qrcodes.gs1;

import de.metas.gs1.GTIN;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class GS1HUQRCodeTest
{
	@Test
	void happyCase()
	{
		final GS1HUQRCode gs1 = GS1HUQRCode.fromStringOrNullIfNotHandled("019731187634181131030075201527080910501");
		assertThat(gs1).isNotNull();

		assertThat(gs1.getWeightInKg()).contains(new BigDecimal("7.520"));
		assertThat(gs1.getBestBeforeDate()).contains(LocalDate.parse("2027-08-09"));
		assertThat(gs1.getLotNumber()).contains("501");
		assertThat(gs1.getGTIN()).contains(GTIN.ofString("97311876341811"));
	}

}