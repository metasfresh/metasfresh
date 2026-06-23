package de.metas.frontend_testing.masterdata.orgseller;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Configures an org's seller identity for ZUGFeRD / EN16931 CII by wiring a
 * BR-DE-conformant BPartner as {@code AD_OrgInfo.Org_BPartner_ID} (the CII mapper
 * reads the seller from the invoice org's org-bpartner).
 *
 * <p>If {@code orgId} is null, defaults to {@link de.metas.frontend_testing.masterdata.MasterdataContext#ORG_ID}
 * (the main org that the test user's login session operates in — no extra org or role change needed).
 *
 * <p>The referenced BPartner and location must already have been created in the same
 * masterdata request (i.e. listed in the {@code bpartners} section).
 */
@Value
@Builder
@Jacksonized
public class JsonOrgSellerRequest
{
	/**
	 * Identifier of the org to configure (references an org already in context,
	 * or the string representation of an {@code OrgId} repo-id).
	 * If null, defaults to the main org ({@code OrgId.MAIN} = org 0).
	 */
	@Nullable String orgIdentifier;

	/**
	 * Identifier of the BPartner to set as {@code AD_OrgInfo.Org_BPartner_ID}.
	 * Must have been created in the same masterdata request.
	 * Required.
	 */
	String bpartnerIdentifier;

	/**
	 * Identifier of the BPartner location to set as {@code AD_OrgInfo.OrgBP_Location_ID}.
	 * Must have been created in the same masterdata request.
	 * Required.
	 */
	String bpartnerLocationIdentifier;
}
