package de.metas.ui.web.view;

import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.NonNull;

/**
 * Shall be implemented by {@link IView}s that have zoom into field support.
 * Keep in mind, this feature goes hand-in-hand with {@link ViewColumn#zoomInto()}
 */
public interface IViewZoomIntoFieldSupport
{
	DocumentZoomIntoInfo getZoomIntoInfo(@NonNull DocumentId rowId, @NonNull String fieldName);
}
