package de.metas.acct.gljournal_sap;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostingSignTest
{
	private static BigDecimal bd(String value) {return new BigDecimal(value);}

	@Test
	void isDebit()
	{
		assertThat(PostingSign.DEBIT.isDebit()).isTrue();
		assertThat(PostingSign.CREDIT.isDebit()).isFalse();
	}
	@Test
	void isCredit()
	{
		assertThat(PostingSign.DEBIT.isCredit()).isFalse();
		assertThat(PostingSign.CREDIT.isCredit()).isTrue();
	}

	@Test
	void reverse()
	{
		assertThat(PostingSign.DEBIT.reverse()).isSameAs(PostingSign.CREDIT);
		assertThat(PostingSign.CREDIT.reverse()).isSameAs(PostingSign.DEBIT);
	}

	@Nested
	class ofAmtDrAndCr
	{
		@Test
		void debit() {assertThat(PostingSign.ofAmtDrAndCr(bd("123"), bd("0"))).isSameAs(PostingSign.DEBIT);}

		@Test
		void credit() {assertThat(PostingSign.ofAmtDrAndCr(bd("0"), bd("123"))).isSameAs(PostingSign.CREDIT);}

		@Test
		void zero() {assertThat(PostingSign.ofAmtDrAndCr(bd("0"), bd("0"))).isSameAs(PostingSign.DEBIT);}

		@Test
		void debit_and_credit()
		{
			assertThatThrownBy(() -> PostingSign.ofAmtDrAndCr(bd("1"), bd("-1")))
					.hasMessageStartingWith("Cannot determine posting sign");
		}
	}
}