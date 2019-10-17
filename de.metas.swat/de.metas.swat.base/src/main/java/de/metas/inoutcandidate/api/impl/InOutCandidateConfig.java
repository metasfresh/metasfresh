package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.api.IInOutCandidateConfig;
import de.metas.util.Check;

public class InOutCandidateConfig implements IInOutCandidateConfig
{
	private Boolean supportForSchedsWithoutOrderLine = null;

	@Override
	public void setSupportForSchedsWithoutOrderLines(final boolean support)
	{
		Check.assume(supportForSchedsWithoutOrderLine == null, "Method is called only once");
		supportForSchedsWithoutOrderLine = support;
	}

	@Override
	public boolean isSupportForSchedsWithoutOrderLine()
	{
		return supportForSchedsWithoutOrderLine == null
				? false
				: supportForSchedsWithoutOrderLine;
	}
}
