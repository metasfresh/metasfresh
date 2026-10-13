package de.metas.frontend_testing.masterdata;

import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Component;

/**
 * Services needed by the masterdata commands; grows together with the supported masterdata types.
 */
@Component
public class CreateMasterdataCommandSupportingServices
{
	@NonNull public final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull public final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull public final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	@NonNull public final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	@NonNull public final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	@NonNull public final ExternalReferenceRepository externalReferenceRepository;
	@NonNull public final ExternalReferenceTypes externalReferenceTypes;
	@NonNull public final ExternalSystems externalSystems;

	public CreateMasterdataCommandSupportingServices(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalReferenceTypes externalReferenceTypes,
			@NonNull final ExternalSystems externalSystems)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalReferenceTypes = externalReferenceTypes;
		this.externalSystems = externalSystems;
	}
}
