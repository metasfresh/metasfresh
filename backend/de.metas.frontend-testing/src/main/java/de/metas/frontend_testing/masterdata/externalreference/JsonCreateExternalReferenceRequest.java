package de.metas.frontend_testing.masterdata.externalreference;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Creates one {@code S_ExternalReference} pointing at a business partner created in the same request.
 */
@Value
@Builder
@Jacksonized
public class JsonCreateExternalReferenceRequest
{
	/** {@code S_ExternalReference.Type} code, e.g. {@code "BPartner"}. */
	@NonNull String type;

	/** {@code S_ExternalReference.ExternalSystem} code, e.g. {@code "Other"}. */
	@NonNull String externalSystem;

	/** {@code S_ExternalReference.ExternalReference}, i.e. the value in the external system. */
	@NonNull String value;

	/** Identifier (map key of the {@code bpartners} section) of the referenced business partner. */
	@NonNull String referencedRecord;
}
