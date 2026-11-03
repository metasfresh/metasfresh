package de.metas.cost.classification;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CostClassification
{
	@NonNull CostClassificationId id;
	@NonNull CostClassificationCategoryId costClassificationCategoryId;
}
