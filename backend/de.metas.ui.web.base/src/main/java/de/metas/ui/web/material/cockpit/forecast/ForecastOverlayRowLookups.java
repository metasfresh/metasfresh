package de.metas.ui.web.material.cockpit.forecast;

import com.google.common.annotations.VisibleForTesting;
import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Lookups for the {@code Sprung zu Prognose} forecast overlay ({@link ForecastOverlayRow}):
 * the forecast Name (also the zoom target) and the organisation, both resolved from search-in-table lookups.
 * Mirrors {@link de.metas.ui.web.material.cockpit.MaterialCockpitRowLookups}.
 */
@Component
public class ForecastOverlayRowLookups
{
	@NonNull private final LookupDataSource forecastLookup;
	@NonNull private final LookupDataSource orgLookup;

	@Autowired
	public ForecastOverlayRowLookups(final @NonNull LookupDataSourceFactory lookupFactory)
	{
		this.forecastLookup = lookupFactory.searchInTableLookup(I_M_Forecast.Table_Name);
		this.orgLookup = lookupFactory.searchInTableLookup(I_AD_Org.Table_Name);
	}

	@VisibleForTesting
	@Builder
	private ForecastOverlayRowLookups(
			@NonNull final LookupDataSource forecastLookup,
			@NonNull final LookupDataSource orgLookup)
	{
		this.forecastLookup = forecastLookup;
		this.orgLookup = orgLookup;
	}

	@Nullable
	public LookupValue lookupForecastById(@Nullable final ForecastId forecastId) {return forecastLookup.findById(forecastId);}

	@Nullable
	public LookupValue lookupOrgById(@Nullable final OrgId orgId) {return orgLookup.findById(orgId);}

	public DocumentZoomIntoInfo getForecastZoomInto(@Nullable final ForecastId forecastId) {return forecastLookup.getDocumentZoomInto(ForecastId.toRepoId(forecastId));}
}
