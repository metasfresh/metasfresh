package de.metas.util.lang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

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

	@Test
	void compareTo()
	{
		final ArrayList<SeqNo> list = new ArrayList<>();
		list.add(SeqNo.ofInt(30));
		list.add(SeqNo.ofInt(20));
		list.add(SeqNo.ofInt(10));

		Collections.sort(list);

		assertThat(list).containsExactly(SeqNo.ofInt(10), SeqNo.ofInt(20), SeqNo.ofInt(30));
	}
}