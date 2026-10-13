package de.metas.handlingunits.grai;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DummyGRAIProviderTest
{
	@Test
	@SuppressWarnings("JoinAssertThatStatements")
	void nextGRAI_incrementsCounter()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final DummyGRAIProvider provider = new DummyGRAIProvider(template, 1);

		assertThat(provider.nextGRAI()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789001"));
		assertThat(provider.nextGRAI()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789002"));
		assertThat(provider.nextGRAI()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789003"));
		assertThat(provider.getNextCounter()).isEqualTo(4);
	}

	@Test
	void nextGRAI_startAtArbitraryCounter()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final DummyGRAIProvider provider = new DummyGRAIProvider(template, 50);

		assertThat(provider.nextGRAI()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789050"));
		assertThat(provider.getNextCounter()).isEqualTo(51);
	}

	@Test
	void nextGRAI_throwsWhenCounterExceedsLimit()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final DummyGRAIProvider provider = new DummyGRAIProvider(template, 100);

		assertThatThrownBy(provider::nextGRAI)
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void nextGRAI_counter99_isLastValid()
	{
		final DummyGRAITemplate template = DummyGRAITemplate.migros("1234567890");
		final DummyGRAIProvider provider = new DummyGRAIProvider(template, 99);

		assertThat(provider.nextGRAI()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.123456789099"));
		assertThatThrownBy(provider::nextGRAI)
				.isInstanceOf(AdempiereException.class);
	}
}
