package de.metas.handlingunits.picking.job.service.commands.grai;

import static org.assertj.core.api.Assertions.*;

import de.metas.handlingunits.grai.GRAI;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

class DummyGRAIGeneratorTest
{
	@Test
	void testBuildDummyGRAI_normalPOReference()
	{
		// POReference "1234567890", counter 1
		assertThat(DummyGRAIGenerator.buildDummyGRAI("1234567890", 1))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789001"));
	}

	@Test
	void testBuildDummyGRAI_shortPOReference_padded()
	{
		// POReference "12345678" -> padded to "0012345678", counter 3
		final String padded = DummyGRAIGenerator.padPOReference("12345678");
		assertThat(DummyGRAIGenerator.buildDummyGRAI(padded, 3))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.001234567803"));
	}

	@Test
	void testBuildDummyGRAI_counter99()
	{
		assertThat(DummyGRAIGenerator.buildDummyGRAI("1234567890", 99))
				.isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789099"));
	}

	@Test
	void testPadPOReference_tooLong()
	{
		// 11 chars -> should throw
		assertThatThrownBy(() -> DummyGRAIGenerator.padPOReference("12345678901"))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void testPadPOReference_exact10()
	{
		assertThat(DummyGRAIGenerator.padPOReference("1234567890"))
				.isEqualTo("1234567890");
	}

	@Test
	void testPadPOReference_short()
	{
		assertThat(DummyGRAIGenerator.padPOReference("123"))
				.isEqualTo("0000000123");
	}

	@Test
	void testExtractDummyCounter_matchingDummy()
	{
		final String prefix = DummyGRAIGenerator.buildDummyPrefix("1234567890");
		assertThat(DummyGRAIGenerator.extractDummyCounter(GRAI.ofCanonicalString("7613204.00307.123456789003"), prefix))
				.isEqualTo(3);
	}

	@Test
	void testExtractDummyCounter_notADummy()
	{
		final String prefix = DummyGRAIGenerator.buildDummyPrefix("1234567890");
		assertThat(DummyGRAIGenerator.extractDummyCounter(GRAI.ofCanonicalString("7613204.00307.9876543210"), prefix)).isZero();
	}

	@Test
	void testExtractDummyCounter_nullGrai()
	{
		final String prefix = DummyGRAIGenerator.buildDummyPrefix("1234567890");
		assertThat(DummyGRAIGenerator.extractDummyCounter(null, prefix)).isZero();
	}

	@Test
	void testExtractDummyCounter_realGrai()
	{
		// A real GRAI (not dummy) should return 0
		final String prefix = DummyGRAIGenerator.buildDummyPrefix("1234567890");
		assertThat(DummyGRAIGenerator.extractDummyCounter(GRAI.ofCanonicalString("7613204.00307.1234567890"), prefix)).isZero();
	}
}
