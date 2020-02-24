package de.metas.location.geocoding.process;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerLocationInfoRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentQuery;
import de.metas.ui.web.document.geo_location.GeoLocationDocumentService;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import lombok.NonNull;

public class C_BPartner_Window_AreaSearchProcess extends JavaProcess
{
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final DocumentCollection documentCollection = SpringContextHolder.instance.getBean(DocumentCollection.class);
	private final BPartnerLocationInfoRepository bpartnerLocationInfoRepo = SpringContextHolder.instance.getBean(BPartnerLocationInfoRepository.class);
	private final GeoLocationDocumentService geoLocationDocumentService = SpringContextHolder.instance.getBean(GeoLocationDocumentService.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countriesRepo = Services.get(ICountryDAO.class);
	private final ILocationDAO locationsRepo = Services.get(ILocationDAO.class);

	@Param(parameterName = "Distance", mandatory = true)
	private BigDecimal distanceInKm;

	@Param(parameterName = "VisitorsAddress")
	private boolean visitorsAddress;

	@Override
	protected String doIt()
	{
		final I_C_Location location = getSelectedLocationOrFirstAvailable();

		final DocumentFilter filter = createAreaSearchFilter(location);

		final IView view = createViewWithFilter(filter);

		getResult().setWebuiViewToOpen(
				ProcessExecutionResult.WebuiViewToOpen.builder()
						.viewId(view.getViewId().toJson())
						.target(ProcessExecutionResult.ViewOpenTarget.NewBrowserTab)
						.build());

		return MSG_OK;
	}

	private IView createViewWithFilter(final DocumentFilter filter)
	{
		final WindowId windowId = getWindowId();
		return viewsRepo.createView(CreateViewRequest.builder(windowId)
				.setFilters(DocumentFilterList.of(filter))
				.build());
	}

	@NonNull
	private WindowId getWindowId()
	{
		return WindowId.of(getProcessInfo().getAdWindowId().getRepoId());
	}

	@NonNull
	private DocumentFilter createAreaSearchFilter(final I_C_Location location)
	{
		final DocumentEntityDescriptor entityDescriptor = documentCollection.getDocumentEntityDescriptor(getWindowId());

		final GeoLocationDocumentQuery query = createGeoLocationQuery(location);
		return geoLocationDocumentService.createDocumentFilter(entityDescriptor, query);
	}

	private GeoLocationDocumentQuery createGeoLocationQuery(final I_C_Location location)
	{
		final CountryId countryId = CountryId.ofRepoId(location.getC_Country_ID());
		final ITranslatableString countryName = countriesRepo.getCountryNameById(countryId);

		return GeoLocationDocumentQuery.builder()
				.country(IntegerLookupValue.of(countryId, countryName))
				.address1(location.getAddress1())
				.city(location.getCity())
				.postal(location.getPostal())
				.distanceInKm(distanceInKm)
				.visitorsAddress(visitorsAddress)
				.build();
	}

	private I_C_Location getSelectedLocationOrFirstAvailable()
	{
		final Set<Integer> bpLocationIds = getSelectedIncludedRecordIds(I_C_BPartner_Location.class);
		if (!bpLocationIds.isEmpty())
		{
			// retrieve the selected location
			final LocationId locationId = bpartnerLocationInfoRepo.getByBPartnerLocationId(BPartnerLocationId.ofRepoId(getRecord_ID(), bpLocationIds.iterator().next())).getLocationId();
			return locationsRepo.getById(LocationId.ofRepoId(locationId.getRepoId()));
		}
		else
		{
			// retrieve the first bpartner location available
			final List<I_C_BPartner_Location> partnerLocations = bpartnersRepo.retrieveBPartnerLocations(BPartnerId.ofRepoId(getRecord_ID()));
			if (!partnerLocations.isEmpty())
			{
				return locationsRepo.getById(LocationId.ofRepoId(partnerLocations.get(0).getC_Location_ID()));
			}
		}

		throw new AdempiereException("@NotFound@ @C_Location_ID@");
	}
}
