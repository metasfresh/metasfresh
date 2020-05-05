package de.metas.inout.api;

import java.util.List;

import de.metas.inout.spi.IMaterialBalanceConfigMatcher;
import de.metas.util.ISingletonService;

public interface IMaterialBalanceConfigBL extends ISingletonService
{
	void addMaterialBalanceConfigMather(final IMaterialBalanceConfigMatcher matcher);

	List<IMaterialBalanceConfigMatcher> retrieveMatchers();
}
