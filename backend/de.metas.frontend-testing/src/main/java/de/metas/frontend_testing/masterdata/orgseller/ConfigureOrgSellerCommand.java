package de.metas.frontend_testing.masterdata.orgseller;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
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
 * given org (defaulting to the main org / test-user's session org if none specified), and sets
 * the reverse {@code C_BPartner.AD_OrgBP_ID = orgId} link on the seller BPartner.
 *
 * <p>The CII mapper ({@code CiiMapper}) reads the seller from
 * {@code bPartnerDAO.retrieveOrgBPartner(invoice.AD_Org_ID)}, which queries
 * {@code C_BPartner WHERE AD_OrgBP_ID = AD_Org_ID} (NOT {@code AD_OrgInfo.Org_BPartner_ID}) and
 * throws {@code OrgHasNoBPartnerLinkException} if no such BPartner exists. The same resolver also
 * backs the standard document archive (via {@code LanguageBL}), so without this link every
 * document under the org — invoice, order confirmation, shipment — fails to archive. By
 * configuring the main org here, a Playwright test using the default login session produces a
 * completable e-invoice with a valid seller without a dedicated org or role-access change.
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

		// Set the reverse link the seller resolver actually queries (C_BPartner.AD_OrgBP_ID = orgId).
		// Without it retrieveOrgBPartner finds no seller and every document under the org (e-invoice
		// CII + standard archive) fails — see class Javadoc. setLinkedBPartner also takes over the
		// per-org unique link, so the shared main org can be reused across runs and the en_US/de_DE cases.
		final BPartnerId sellerBPartnerId = context.getId(
				Identifier.ofString(request.getBpartnerIdentifier()), BPartnerId.class);
		Services.get(IBPartnerOrgBL.class).setLinkedBPartner(orgId, sellerBPartnerId);
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
