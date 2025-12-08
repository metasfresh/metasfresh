package de.metas.util.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeqNoProviderTest
{
	@Test
	void getAndIncrement()
	{
		final SeqNoProvider seqNo = SeqNoProvider.ofInt(10);
		assertThat(seqNo.getAndIncrement()).isEqualTo(SeqNo.ofInt(10));
		assertThat(seqNo.getAndIncrement()).isEqualTo(SeqNo.ofInt(20));
		assertThat(seqNo.getAndIncrement()).isEqualTo(SeqNo.ofInt(30));
		assertThat(seqNo.getAndIncrement()).isEqualTo(SeqNo.ofInt(40));
	}
}