package de.metas.handlingunits.grai;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DummyGRAITemplateTest
{
	@Test
	void buildGRAI_normalSerialPrefix()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.buildGRAI(1))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789001"));
	}

	@Test
	void buildGRAI_shortSerialPrefix_padded()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("12345678");
		assertThat(template.buildGRAI(3))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.001234567803"));
	}

	@Test
	void buildGRAI_counter99()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.buildGRAI(99))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789099"));
	}

	@Test
	void buildGRAI_counterOverLimit_throws()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThatThrownBy(() -> template.buildGRAI(100))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void migros_tooLongSerialPrefix_throws()
	{
		assertThatThrownBy(() -> DummyGRAITemplate.migros("12345678901"))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void migros_padsShortSerialPrefix()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("123");
		assertThat(template.getSerialPrefix()).isEqualTo("0000000123");
	}

	@Test
	void extractCounter_matchingDummy()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.extractCounter(GRAI.ofCanonicalString("7613204.00307.123456789003")))
				.isEqualTo(3);
	}

	@Test
	void extractCounter_notADummy()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.extractCounter(GRAI.ofCanonicalString("7613204.00307.9876543210"))).isZero();
	}

	@Test
	void extractCounter_nullGrai()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.extractCounter(null)).isZero();
	}

	@Test
	void extractCounter_realGrai()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.extractCounter(GRAI.ofCanonicalString("7613204.00307.1234567890"))).isZero();
	}
}
