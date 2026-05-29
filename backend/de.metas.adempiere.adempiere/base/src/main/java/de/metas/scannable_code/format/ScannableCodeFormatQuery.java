package de.metas.scannable_code.format;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ScannableCodeFormatQuery
{
	@Nullable String description;
}
