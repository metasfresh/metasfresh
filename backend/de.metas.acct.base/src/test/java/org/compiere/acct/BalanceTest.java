package org.compiere.acct;

import de.metas.money.CurrencyId;
import de.metas.util.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

class BalanceTest
{
	static Balance euroBalance(final String debit, final String credit)
	{
		return Balance.of(CurrencyId.EUR, bd(debit), bd(credit));
	}

	static BigDecimal bd(@Nullable final String value)
	{
		return StringUtils.trimBlankToOptional(value).map(BigDecimal::new).orElse(BigDecimal.ZERO);
	}

	@Nested
	class computeDiffToBalance
	{
		@Test
		void zero()
		{
			final Balance zero = euroBalance("0", "0");
			Assertions.assertThat(zero.computeDiffToBalance()).isEqualTo(zero);
		}

		@Test
		void DR100_CR100()
		{
			final Balance balance = euroBalance("100", "100");
			Assertions.assertThat(balance.computeDiffToBalance()).isEqualTo(euroBalance("0", "0"));
		}

		@Test
		void DR100_CR80()
		{
			final Balance balance = euroBalance("100", "80");
			Assertions.assertThat(balance.computeDiffToBalance()).isEqualTo(euroBalance("0", "20"));
		}

		@Test
		void DR80_CR100()
		{
			final Balance balance = euroBalance("80", "100");
			Assertions.assertThat(balance.computeDiffToBalance()).isEqualTo(euroBalance("20", "0"));
		}

		@Test
		void DRminus100_CRminus80()
		{
			final Balance balance = euroBalance("-100", "-80");
			Assertions.assertThat(balance.computeDiffToBalance()).isEqualTo(euroBalance("0", "-20"));
		}

		@Test
		void DRminus80_CRminus100()
		{
			final Balance balance = euroBalance("-80", "-100");
			Assertions.assertThat(balance.computeDiffToBalance()).isEqualTo(euroBalance("-20", "0"));
		}
	}

	@Nested
	class isDebit
	{
		@Test
		void zero()
		{
			final Balance balance = euroBalance("0", "0");
			Assertions.assertThat(balance.isDebit()).isTrue();
		}

		@Test
		void DR100_CR100()
		{
			final Balance balance = euroBalance("100", "100");
			Assertions.assertThat(balance.isDebit()).isTrue();
		}

		@Test
		void DR100_CR80()
		{
			final Balance balance = euroBalance("100", "80");
			Assertions.assertThat(balance.isDebit()).isTrue();
		}

		@Test
		void DR80_CR100()
		{
			final Balance balance = euroBalance("80", "100");
			Assertions.assertThat(balance.isDebit()).isFalse();
		}

		@Test
		void DRminus100_CRminus80()
		{
			final Balance balance = euroBalance("-100", "-80");
			Assertions.assertThat(balance.isDebit()).isTrue();
		}

		@Test
		void DRminus80_CRminus100()
		{
			final Balance balance = euroBalance("-80", "-100");
			Assertions.assertThat(balance.isDebit()).isFalse();
		}
	}
}
