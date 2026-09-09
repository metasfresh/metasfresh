package de.metas.frontend_testing.masterdata.hu_package;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.shipping.IHUPackageBL;
import de.metas.handlingunits.shipping.InOutPackageRepository;
import de.metas.shipping.mpackage.Package;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_Package;

/**
 * Assigns a package ({@code M_Package} + {@code M_Package_HU}) to an already-created HU, so the HU is
 * "assigned to a package" for test scenarios that need that state (e.g. an empty-HU write-off refusal).
 * <p>
 * The package is created standalone (no {@code M_InOut}, no {@code C_BPartner}/{@code C_BPartner_Location}):
 * masterdata-created HUs normally carry none of that, and only the package assignment itself — not real
 * shipping data — is needed here.
 */
@Builder
public class PackageCommand
{
	@NonNull private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);

	@NonNull private final InOutPackageRepository inOutPackageRepository;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPackageRequest request;
	@NonNull private final Identifier identifier;

	public JsonPackageResponse execute()
	{
		final HuId huId = context.getId(request.getHu(), HuId.class);

		final I_M_Package packageRecord = inOutPackageRepository.createM_Package(
				MasterdataContext.ORG_ID,
				context.getDefaultShipperId(),
				identifier.toUniqueString());
		final PackageId packageId = PackageId.ofRepoId(packageRecord.getM_Package_ID());

		huPackageBL.assignPackageToHuId(
				Package.builder()
						.id(packageId)
						.orgId(MasterdataContext.ORG_ID)
						.build(),
				huId);

		context.putIdentifier(identifier, packageId);

		return JsonPackageResponse.builder()
				.packageId(packageId)
				.documentNo(packageRecord.getDocumentNo())
				.build();
	}
}
