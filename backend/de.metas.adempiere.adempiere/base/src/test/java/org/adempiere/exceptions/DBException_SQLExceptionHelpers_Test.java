package org.adempiere.exceptions;

import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DBException_SQLExceptionHelpers_Test
{
	private static PSQLException postgresError(final String sqlState, final String constraintName)
	{
		final String constraintField = constraintName != null ? "n" + constraintName + "\0" : "";
		return new PSQLException(new ServerErrorMessage("SERROR\0C" + sqlState + "\0Msome error\0" + constraintField));
	}

	@Test
	void findSQLExceptionInCauseChainOrNull_deepChain_returnsFirstMatch()
	{
		final SQLException notMatching = new SQLException("other", "57014");
		final PSQLException matching = postgresError("55P03", null);
		notMatching.initCause(matching);
		final RuntimeException chain = new AdempiereException("outer", new IllegalStateException("middle", new DBForeignKeyConstraintException(notMatching)));

		assertThat((Throwable)DBException.findSQLExceptionInCauseChainOrNull(chain, DBException::isLockNotAvailable)).isSameAs(matching);
	}

	@Test
	void findSQLExceptionInCauseChainOrNull_noMatch_returnsNull()
	{
		final RuntimeException chain = new AdempiereException("outer", new SQLException("other", "57014"));

		assertThat((Throwable)DBException.findSQLExceptionInCauseChainOrNull(chain, DBException::isLockNotAvailable)).isNull();
		assertThat((Throwable)DBException.findSQLExceptionInCauseChainOrNull(new AdempiereException("no SQL exception at all"), sqlException -> true)).isNull();
		assertThat((Throwable)DBException.findSQLExceptionInCauseChainOrNull(null, sqlException -> true)).isNull();
	}

	@Test
	void extractConstraintNameOrNull()
	{
		assertThat(DBException.extractConstraintNameOrNull(postgresError("23503", "corderline_corderline"))).isEqualTo("corderline_corderline");
		assertThat(DBException.extractConstraintNameOrNull(postgresError("23503", null))).as("PostgreSQL error without constraint").isNull();
		assertThat(DBException.extractConstraintNameOrNull(new SQLException("not from PostgreSQL", "23503"))).as("not a PSQLException").isNull();
	}

	@Test
	void isLockNotAvailable()
	{
		assertThat(DBException.isLockNotAvailable(new SQLException("lock timeout", "55P03"))).isTrue();
		assertThat(DBException.isLockNotAvailable(new SQLException("deadlock", "40P01"))).isFalse();
	}

	@Test
	void isForeignKeyViolation_byConstraintName()
	{
		assertThat(DBException.isForeignKeyViolation(postgresError("23503", "corderline_corderline"), "CORDERLINE_CORDERLINE")).isTrue();
		assertThat(DBException.isForeignKeyViolation(postgresError("23503", "other_fk"), "corderline_corderline")).as("other constraint").isFalse();
		assertThat(DBException.isForeignKeyViolation(postgresError("23505", "corderline_corderline"), "corderline_corderline")).as("not a foreign key violation").isFalse();
		assertThat(DBException.isForeignKeyViolation(new SQLException("not from PostgreSQL", "23503"), "corderline_corderline")).as("no constraint name").isFalse();
	}
}
