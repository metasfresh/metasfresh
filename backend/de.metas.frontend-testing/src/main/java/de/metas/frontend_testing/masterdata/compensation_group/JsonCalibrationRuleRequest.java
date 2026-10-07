package de.metas.frontend_testing.masterdata.compensation_group;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * One {@code C_CompensationGroup_CalibrationRule}. At least one of {@link #bpartner} / {@link #bpGroup} is required
 * (the table's own check); the other selectors are optional and narrow the rule. All identifiers are masterdata map
 * keys. Created AFTER {@code bpartners}, {@code bpGroups}, {@code productCategories}, {@code products} and
 * {@code compensationGroupSchemas}.
 */
@Value
@Builder
@Jacksonized
public class JsonCalibrationRuleRequest
{
	/** {@code SeqNo}; when null, the rule is appended after the highest existing one (step 10). */
	@Nullable Integer seqNo;

	@Nullable Identifier bpartner;
	@Nullable Identifier bpGroup;
	@Nullable Identifier product;
	@Nullable Identifier productCategory;
	@Nullable Identifier schema;

	/** {@code GroupCompensationCalibrationFactor}: a 100-based percent (80 = 80 %, 100 = unchanged, 0 = Qty 0); must be &gt;= 0. */
	@NonNull BigDecimal factor;
}
