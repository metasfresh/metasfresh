package de.metas.frontend_testing.masterdata.hu_package;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.mpackage.PackageId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Package;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Assigns a package ({@code M_Package} + {@code M_Package_HU}) to an already-created HU, so the HU is
 * "assigned to a package" for test scenarios that need that state (e.g. an empty-HU write-off refusal).
 * <p>
 * Kept deliberately minimal: unlike the production {@code IHUPackageBL#createM_Package}, this does NOT
 * require the HU to carry a {@code C_BPartner}/{@code C_BPartner_Location} (masterdata-created HUs
 * normally don't), since only the package assignment itself — not real shipping data — is needed here.
 */
@Builder
public class PackageCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPackageRequest request;
	@NonNull private final Identifier identifier;

	public JsonPackageResponse execute()
	{
		final HuId huId = context.getId(request.getHu(), HuId.class);

		final I_M_Package packageRecord = InterfaceWrapperHelper.newInstance(I_M_Package.class);
		packageRecord.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		packageRecord.setDocumentNo(identifier.toUniqueString());
		packageRecord.setM_Shipper_ID(context.getDefaultShipperId().getRepoId());
		saveRecord(packageRecord);
		final PackageId packageId = PackageId.ofRepoId(packageRecord.getM_Package_ID());

		final I_M_Package_HU packageHURecord = InterfaceWrapperHelper.newInstance(I_M_Package_HU.class);
		packageHURecord.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		packageHURecord.setM_Package_ID(packageId.getRepoId());
		packageHURecord.setM_HU_ID(huId.getRepoId());
		saveRecord(packageHURecord);

		context.putIdentifier(identifier, packageId);

		return JsonPackageResponse.builder()
				.packageId(packageId.getRepoId())
				.documentNo(packageRecord.getDocumentNo())
				.build();
	}
}
