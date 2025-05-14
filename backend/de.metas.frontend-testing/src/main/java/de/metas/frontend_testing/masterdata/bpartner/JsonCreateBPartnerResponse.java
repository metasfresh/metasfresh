package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.GLN;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateBPartnerResponse
{
	@NonNull BPartnerId id;
	@NonNull String bpartnerCode;
	@Nullable GLN gln;
}
