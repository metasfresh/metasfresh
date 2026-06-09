package de.metas.frontend_testing.masterdata.inventory;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonInventoryRequest
{
	@NonNull Identifier warehouse;
	@Nullable ZonedDateTime date;
	@NonNull Set<Identifier> products;
}
