package de.metas.frontend_testing.masterdata.role;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * One {@code AD_Table_Access} row of a {@link JsonCreateRoleRequest}.
 * <p>
 * Every flag is a plain {@code boolean} defaulting to its column's own NON-RESTRICTING value
 * (see {@code AD_Table_Access}, {@code AD_Column.DefaultValue}). A spec that configures nothing therefore
 * produces a row equivalent to no row at all.
 */
@Value
@Builder
@Jacksonized
public class JsonRoleTableAccessRequest
{
	/** e.g. {@code C_BPartner} */
	@NonNull String tableName;

	/** {@code IsReadOnly}, default {@code false} */
	@Builder.Default boolean readOnly = false;

	/** {@code IsCanReport}, default {@code true} */
	@Builder.Default boolean canReport = true;

	/** {@code IsCanExport}, default {@code true} */
	@Builder.Default boolean canExport = true;

	/** {@code IsCanCreateNewRecords}, default {@code true} */
	@Builder.Default boolean canCreateNewRecords = true;
}
