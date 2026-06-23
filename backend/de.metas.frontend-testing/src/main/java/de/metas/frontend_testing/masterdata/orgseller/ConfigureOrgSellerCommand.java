package de.metas.frontend_testing.masterdata.orgseller;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Wires a BR-DE-conformant BPartner as the org's seller identity for ZUGFeRD / EN16931 CII.
 *
 * <p>Sets {@code AD_OrgInfo.Org_BPartner_ID} and {@code AD_OrgInfo.OrgBP_Location_ID} for the
 * given org (defaulting to the main org / test-user's session org if none specified).
 *
 * <p>The CII mapper ({@code CIIMapper}) reads the seller from
 * {@code bPartnerDAO.retrieveOrgBPartner(invoice.AD_Org_ID)}, which resolves via
 * {@code AD_OrgInfo.Org_BPartner_ID}. By configuring the main org here, a Playwright test
 * using the default login session will produce CII XML with a valid seller without
 * requiring a dedicated org or role-access change.
 */
@Builder
public class ConfigureOrgSellerCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonOrgSellerRequest request;

	public void execute()
	{
		final OrgId orgId = resolveOrgId();
		final BPartnerLocationId bplId = context.getBPartnerLocationId(
				Identifier.ofString(request.getBpartnerLocationIdentifier()));

		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
				.orgId(orgId)
				.orgBPartnerLocationId(Optional.of(bplId))
				.build());
	}

	private OrgId resolveOrgId()
	{
		final String orgIdentifier = request.getOrgIdentifier();
		if (orgIdentifier == null)
		{
			return MasterdataContext.ORG_ID;
		}
		// Try to resolve from context first; fall back to parsing as numeric repo-id
		final OrgId fromContext = context.getIdOfTypeIfUnique(OrgId.class).orElse(null);
		if (fromContext != null)
		{
			return fromContext;
		}
		return OrgId.ofRepoId(Integer.parseInt(orgIdentifier));
	}
}
