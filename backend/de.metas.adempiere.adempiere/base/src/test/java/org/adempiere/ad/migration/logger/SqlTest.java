package org.adempiere.ad.migration.logger;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlTest
{
	@BeforeEach
	void beforeEach()
	{
		SystemTime.setFixedTimeSource("2023-01-02T04:05:06Z");
	}

	@Test
	void ofComment()
	{
		Assertions.assertThat(Sql.ofComment("bla bla").toSql())
				.isEqualTo("-- bla bla\n");
	}

	@Test
	void ofSql()
	{
		Assertions.assertThat(Sql.ofSql("SELECT 1 FROM MyTable").toSql())
				.isEqualTo("""
						-- 2023-01-02T04:05:06Z
						SELECT 1 FROM MyTable
						;
						      
						""");
	}

	@Test
	void ofSqlAndParams()
	{
		Assertions.assertThat(Sql.ofSql("SELECT 1 FROM MyTable WHERE A=?", ImmutableMap.of(1, "key1")).toSql())
				.isEqualTo("""
						-- 2023-01-02T04:05:06Z
						SELECT 1 FROM MyTable WHERE A='key1'
						;
						      
						""");
	}

}