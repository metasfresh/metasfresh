package org.adempiere.ad.service;

import de.metas.util.ISingletonService;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * Developer Model BL
 * 
 * @author tsa
 * See mediawiki/index.php/02664:_Introduce_ADempiere_Developer_Mode_%282012040510000121%29
 */
public interface IDeveloperModeBL extends ISingletonService
{
	/**
	 * Checks if developer mode is enabled.
	 * <p>
	 * This method NEVER fails. In case there is an internal failure, it will return false and and error will be logged.
	 * 
	 * @return true if developer mode is enabled
	 */
	boolean isEnabled();

	Optional<File> getDevelopmentWorkspaceDir();

	interface ContextRunnable
	{
		void run(Properties sysCtx);
	}

	/**
	 * Execute {@link ContextRunnable} in SysAdm context
	 */
	void executeAsSystem(ContextRunnable runnable);

}
