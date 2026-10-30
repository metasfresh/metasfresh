package de.metas.frontend_testing.masterdata.externalreference;

import de.metas.bpartner.BPartnerId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.IExternalSystem;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;

public class CreateExternalReferenceCommand
{
	@NonNull private final ExternalReferenceRepository externalReferenceRepository;
	@NonNull private final ExternalReferenceTypes externalReferenceTypes;
	@NonNull private final ExternalSystems externalSystems;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateExternalReferenceRequest request;

	@Builder
	private CreateExternalReferenceCommand(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalReferenceTypes externalReferenceTypes,
			@NonNull final ExternalSystems externalSystems,
			@NonNull final MasterdataContext context,
			@NonNull final JsonCreateExternalReferenceRequest request)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalReferenceTypes = externalReferenceTypes;
		this.externalSystems = externalSystems;
		this.context = context;
		this.request = request;
	}

	public void execute()
	{
		final IExternalReferenceType type = externalReferenceTypes.ofCode(request.getType())
				.orElseThrow(() -> new AdempiereException("Unknown external reference type `" + request.getType() + "`"));
		final IExternalSystem externalSystem = externalSystems.ofCode(request.getExternalSystem())
				.orElseThrow(() -> new AdempiereException("Unknown external system `" + request.getExternalSystem() + "`"));

		if (!I_C_BPartner.Table_Name.equals(type.getTableName()))
		{
			throw new AdempiereException("Only external reference types of table " + I_C_BPartner.Table_Name + " are supported; got `" + request.getType() + "`");
		}
		final BPartnerId bpartnerId = context.getId(Identifier.ofString(request.getReferencedRecord()), BPartnerId.class);

		externalReferenceRepository.save(ExternalReference.builder()
				.orgId(OrgId.MAIN)
				.externalSystem(externalSystem)
				.externalReferenceType(type)
				.externalReference(request.getValue())
				.recordId(bpartnerId.getRepoId())
				.build());
	}
}
