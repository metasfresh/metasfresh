package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Expectation on the stored value of one column of one record, e.g. a password column the UI never shows.
 * The check is pass/fail only: a failure never reports the stored or the expected value.
 */
@Value
@Builder
@Jacksonized
public class JsonRecordColumnExpectation
{
	@NonNull String tableName;
	int recordId;
	@NonNull String columnName;
	/** the value expected to be stored, compared as String; {@code null} = expect NULL */
	@Nullable String expectedValue;
}
