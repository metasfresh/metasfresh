package de.metas.quickinput.config;

import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuickInputConfigService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public Optional<QuickInputConfigLayout> getLayoutBySysconfig(@NonNull final String sysconfigName)
	{
		final String layoutString = StringUtils.trimBlankToNull(sysConfigBL.getValue(sysconfigName));

		try
		{
			return QuickInputConfigLayout.parse(layoutString);
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("sysconfigName", sysconfigName)
					.setParameter("layoutString", layoutString);
		}
	}
}
