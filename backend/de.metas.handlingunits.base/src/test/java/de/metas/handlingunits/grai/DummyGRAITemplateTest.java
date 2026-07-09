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
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(DummyGRAITemplate.MSG_DUMMY_GRAI_SERIAL_PREFIX_TOO_LONG.toAD_Message());
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

	@Test
	void matches_samePOReference_true()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final GRAI grai = template.buildGRAI(1);
		assertThat(template.matches(grai)).isTrue();
		assertThat(DummyGRAITemplate.isMigrosStructure(grai)).isTrue();
	}

	@Test
	void matches_differentPOReference_false()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final GRAI otherOrderGrai = DummyGRAITemplate.migros("9999999999").buildGRAI(1);
		assertThat(template.matches(otherOrderGrai)).isFalse();
		// still Migros-structured, just the wrong order
		assertThat(DummyGRAITemplate.isMigrosStructure(otherOrderGrai)).isTrue();
	}

	@Test
	void isMigrosStructure_nonMigrosGrai_false()
	{
		final GRAI nonMigrosGrai = GRAI.ofCanonicalString("1234567.99999.1234567890");
		assertThat(DummyGRAITemplate.isMigrosStructure(nonMigrosGrai)).isFalse();

		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.matches(nonMigrosGrai)).isFalse();
	}

	@Test
	void matches_null_false()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		assertThat(template.matches(null)).isFalse();
	}

	@Test
	void isMigrosStructure_null_false()
	{
		assertThat(DummyGRAITemplate.isMigrosStructure(null)).isFalse();
	}
}
