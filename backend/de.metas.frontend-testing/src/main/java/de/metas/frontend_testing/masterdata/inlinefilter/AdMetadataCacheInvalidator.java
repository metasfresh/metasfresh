package de.metas.frontend_testing.masterdata.inlinefilter;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Makes other processes (e.g. the webapi, which caches the window and view layouts) pick up AD changes made by this one.
 * <p>
 * The platform broadcasts cache invalidations over the event bus, but only for the table names enabled for it,
 * and the AD tables are not. So we enable the layout-relevant names here and send a reset for them: the normal {@link CacheMgt} flow.
 * A cache is addressed by its label, which is its table name or, for caches without one, its name.
 */
@Component
public class AdMetadataCacheInvalidator
{
	/**
	 * Labels of the webapi's view caches, which have no table name and are therefore not reset by AD table changes:
	 * the view bindings (they hold the filter descriptors) and the view layouts.
	 */
	private static final String CACHE_LABEL_ViewBindings = "SqlViewBindings";
	private static final String CACHE_LABEL_ViewLayouts = "SqlViewLayouts";

	private static final String[] CACHE_LABELS = {
			I_AD_Window.Table_Name, // window descriptors
			I_AD_Column.Table_Name,
			I_AD_UI_Element.Table_Name,
			CACHE_LABEL_ViewBindings,
			CACHE_LABEL_ViewLayouts
	};

	public void invalidateWindowLayouts()
	{
		final CacheMgt cacheMgt = CacheMgt.get();

		for (final String label : CACHE_LABELS)
		{
			cacheMgt.enableRemoteCacheInvalidationForTableName(label);
		}
		cacheMgt.reset(CacheInvalidateMultiRequest.of(Stream.of(CACHE_LABELS)
				.map(CacheInvalidateRequest::allRecordsForTable)
				.toArray(CacheInvalidateRequest[]::new)));
	}
}
