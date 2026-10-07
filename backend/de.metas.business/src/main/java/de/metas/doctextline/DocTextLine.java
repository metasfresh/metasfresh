package de.metas.doctextline;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class DocTextLine
{
	@NonNull DocTextLineId id;
	@NonNull DocTextLineDocumentRef documentRef;
	@Nullable String textLine;
	@NonNull BigDecimal line;
	@NonNull TextLineScope scope;
}
