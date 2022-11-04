package org.adempiere.ad.table.api;

import de.metas.reflist.ReferenceId;
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
}
