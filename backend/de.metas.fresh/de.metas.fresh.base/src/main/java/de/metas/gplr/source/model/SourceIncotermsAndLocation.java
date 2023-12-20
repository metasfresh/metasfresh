package de.metas.gplr.source.model;

import de.metas.incoterms.Incoterms;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceIncotermsAndLocation
{
	@NonNull Incoterms incoterms;
	@Nullable String location;
}
