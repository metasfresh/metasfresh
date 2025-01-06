package org.adempiere.ad.table.api;

import de.metas.ad_reference.ReferenceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.validationRule.AdValRuleId;

import javax.annotation.Nullable;

/**
 * Minimal AD_Column information.
 *
 * @implSpec IMPORTANT: instances of this class are retrieved directly from database and not from persistence layer.
 */
@Value
@Builder
public class MinimalColumnInfo
{
	@NonNull String columnName;
	@Nullable String columnSql;
	@NonNull AdColumnId adColumnId;
	@NonNull AdTableId adTableId;
	boolean isActive;
	boolean isParent;
	boolean isGenericZoomOrigin;
	int displayType;
	@Nullable ReferenceId adReferenceValueId;
	@Nullable AdValRuleId adValRuleId;
	@NonNull String entityType;
	int fieldLength;
	boolean isDLMPartitionBoundary;

	public boolean isColumnNameMatching(final String columnName)
	{
		return this.columnName.equalsIgnoreCase(columnName);
	}

	public boolean isPhysicalColumn() {return Check.isBlank(columnSql);}
}
