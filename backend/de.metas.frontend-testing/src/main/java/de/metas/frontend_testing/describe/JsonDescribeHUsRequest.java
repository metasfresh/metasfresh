package de.metas.frontend_testing.describe;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDescribeHUsRequest
{
	@NonNull @Singular ImmutableList<Integer> huIds;
}
