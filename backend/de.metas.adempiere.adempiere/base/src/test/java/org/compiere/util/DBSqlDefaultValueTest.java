package org.compiere.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DBSqlDefaultValueTest
{
	@Test
	void isSqlDefaultValue_null_returnsFalse()
	{
		assertThat(DB.isSqlDefaultValue(null)).isFalse();
	}

	@Test
	void isSqlDefaultValue_emptyString_returnsFalse()
	{
		assertThat(DB.isSqlDefaultValue("")).isFalse();
	}

	@Test
	void isSqlDefaultValue_plainValue_returnsFalse()
	{
		assertThat(DB.isSqlDefaultValue("Y")).isFalse();
	}

	@Test
	void isSqlDefaultValue_contextVariable_returnsFalse()
	{
		assertThat(DB.isSqlDefaultValue("@AD_Client_ID@")).isFalse();
	}

	@Test
	void isSqlDefaultValue_sqlPrefix_returnsTrue()
	{
		assertThat(DB.isSqlDefaultValue("@SQL=SELECT 1")).isTrue();
	}

	@Test
	void isSqlDefaultValue_sqlPrefixWithVariable_returnsTrue()
	{
		assertThat(DB.isSqlDefaultValue("@SQL=SELECT 1 WHERE AD_Client_ID=@AD_Client_ID@")).isTrue();
	}

	@Test
	void resolveSqlDefaultValue_null_returnsNull()
	{
		assertThat(DB.resolveSqlDefaultValue(null)).isNull();
	}

	@Test
	void resolveSqlDefaultValue_plainValue_returnsAsIs()
	{
		assertThat(DB.resolveSqlDefaultValue("Y")).isEqualTo("Y");
	}

	@Test
	void resolveSqlDefaultValue_emptyString_returnsEmptyString()
	{
		assertThat(DB.resolveSqlDefaultValue("")).isEqualTo("");
	}

	@Test
	void resolveSqlDefaultValue_contextVariable_returnsAsIs()
	{
		assertThat(DB.resolveSqlDefaultValue("@AD_Client_ID@")).isEqualTo("@AD_Client_ID@");
	}
}
