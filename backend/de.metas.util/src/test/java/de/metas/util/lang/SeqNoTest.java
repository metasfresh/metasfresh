package de.metas.util.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeqNoTest
{
	@Test
	void ofInt_next()
	{
		assertThat(SeqNo.ofInt(10).next().toInt()).isEqualTo(20);
		assertThat(SeqNo.ofInt(11).next().toInt()).isEqualTo(20);
		assertThat(SeqNo.ofInt(20).next().toInt()).isEqualTo(30);
	}
}