package de.metas.frontend_testing.masterdata.role;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * One {@code AD_Table_Access} row of a {@link JsonCreateRoleRequest}.
 * <p>
 * Every flag is three-state: leaving it out means the row says nothing about that aspect and the role's
 * own default keeps standing - which is not the same as setting it to {@code false}.
 */
@Value
@Builder
@Jacksonized
public class JsonRoleTableAccessRequest
{
	/** e.g. {@code C_BPartner} */
	@NonNull String tableName;

	/** {@code IsExclude} */
	@Nullable Boolean exclude;

	/** {@code IsReadOnly} */
	@Nullable Boolean readOnly;

	/** {@code IsCanReport} */
	@Nullable Boolean canReport;

	/** {@code IsCanExport} */
	@Nullable Boolean canExport;

	/** {@code IsCanCreateNewRecords} */
	@Nullable Boolean canCreateNewRecords;
}
