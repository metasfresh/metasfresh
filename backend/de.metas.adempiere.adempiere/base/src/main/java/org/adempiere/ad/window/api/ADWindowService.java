package org.adempiere.ad.window.api;

import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.springframework.stereotype.Service;

@Service
public class ADWindowService
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	public ADWindowService(
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	@NonNull
	public AdWindowId getEffectiveWindowIdByInternalName(@NonNull final String internalName)
	{
		final AdWindowId adWindowId = adWindowDAO.getWindowIdByInternalName(internalName);
		return customizedWindowInfoMapRepository.getEffectiveWindowId(adWindowId);
	}

	public void copyWindow(@NonNull final WindowCopyRequest request)
	{
		final WindowCopyResult windowCopyResult = adWindowDAO.copyWindow(request);
		adProcessDAO.copyWindowRelatedProcesses(windowCopyResult);
	}

}
