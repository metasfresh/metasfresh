package de.metas.frontend_testing.masterdata.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.grai.GRAIRequired;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Updates an existing business partner after the fact (test support), e.g. to flip {@code C_BPartner.GRAIRequired}
 * after picking.
 */
@Value
@Builder
@Jacksonized
public class JsonUpdateBPartnerRequest
{
	/** The {@code C_BPartner_ID} from the createMasterdata response ({@code bpartners.<key>.id}). */
	@NonNull BPartnerId bpartnerId;

	/** New {@code C_BPartner.GRAIRequired}, by code: {@code 'Y'}, {@code 'N'} or {@code 'D'}. */
	@NonNull GRAIRequired graiRequired;
}
