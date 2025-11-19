package de.metas.ui.web.window.health;

import com.google.common.collect.ImmutableList;
import de.metas.rest_api.utils.v2.JsonErrors;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.health.json.JsonWindowsHealthCheckResponse;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

class ErrorsCollector
{
	@NonNull private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	@NonNull private final String adLanguage;
	@NonNull private final ArrayList<JsonWindowsHealthCheckResponse.Entry> errors = new ArrayList<>();
	@Nullable @Getter private AdWindowId currentWindowId;
	@Nullable private String _currentWindowName;

	@Builder
	private ErrorsCollector(@NonNull final String adLanguage)
	{
		this.adLanguage = adLanguage;
	}

	public List<JsonWindowsHealthCheckResponse.Entry> getCollectedErrors()
	{
		return ImmutableList.copyOf(errors);
	}

	public int getCollectedErrorsCount()
	{
		return errors.size();
	}

	public void setCurrentWindow(@NonNull final AdWindowId windowId)
	{
		setCurrentWindow(windowId, null);
	}

	public void setCurrentWindow(@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		setCurrentWindow(entityDescriptor.getWindowId().toAdWindowId(), entityDescriptor.getCaption().translate(adLanguage));
	}

	public void setCurrentWindow(@NonNull final AdWindowId windowId, @Nullable final String windowName)
	{
		this.currentWindowId = windowId;
		this._currentWindowName = windowName;
	}

	public void clearCurrentWindow()
	{
		this.currentWindowId = null;
		this._currentWindowName = null;
	}

	@Nullable
	public String getCurrentWindowName()
	{
		if (this._currentWindowName == null && this.currentWindowId != null)
		{
			this._currentWindowName = adWindowDAO.retrieveWindowName(currentWindowId).translate(adLanguage);
		}
		return this._currentWindowName;
	}

	public void collectError(@NonNull final String errorMessage)
	{
		errors.add(JsonWindowsHealthCheckResponse.Entry.builder()
				.windowId(WindowId.of(currentWindowId))
				.windowName(getCurrentWindowName())
				.errorMessage(errorMessage)
				.build());
	}

	public void collectError(@NonNull final Throwable exception)
	{
		errors.add(JsonWindowsHealthCheckResponse.Entry.builder()
				.windowId(WindowId.of(currentWindowId))
				.windowName(getCurrentWindowName())
				.error(JsonErrors.ofThrowable(exception, adLanguage))
				.build());
	}
}
