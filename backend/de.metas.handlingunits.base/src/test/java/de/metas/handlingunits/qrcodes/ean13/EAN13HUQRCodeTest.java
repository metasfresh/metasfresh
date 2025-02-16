package de.metas.handlingunits.qrcodes.ean13;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EAN13HUQRCodeTest
{
	@Test
	void happyCase()
	{
		final EAN13HUQRCode ean13 = EAN13HUQRCode.fromStringOrNullIfNotHandled("2859414004825");
		assertThat(ean13).isNotNull();

		assertThat(ean13.getPrefix()).contains(EAN13HUQRCode.PREFIX_VariableWeight);
		assertThat(ean13.getProductNo()).contains("59414");
		assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.482"));
		assertThat(ean13.getBestBeforeDate()).isEmpty();
		assertThat(ean13.getLotNumber()).isEmpty();
	}

	@Test
	void happyCase2()
	{
		final EAN13HUQRCode ean13 = EAN13HUQRCode.fromStringOrNullIfNotHandled("2800027002616");
		assertThat(ean13).isNotNull();

		assertThat(ean13.getPrefix()).contains(EAN13HUQRCode.PREFIX_VariableWeight);
		assertThat(ean13.getProductNo()).contains("00027");
		assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.261"));
		assertThat(ean13.getBestBeforeDate()).isEmpty();
		assertThat(ean13.getLotNumber()).isEmpty();
	}

}