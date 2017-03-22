package org.adempiere.tools;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.compiere.Adempiere.RunMode;
import org.compiere.model.ModelValidationEngine;
import org.compiere.util.Env;

import de.metas.adempiere.addon.impl.AddonStarter;
import de.metas.event.EventBusConstants;
import de.metas.logging.LogManager;

/**
 * Helper class used by tools build on top of ADempiere, which require only a minimal set of functionalities.
 * 
 * @author tsa
 *
 */
public final class AdempiereToolsHelper
{
	public static final transient AdempiereToolsHelper instance = new AdempiereToolsHelper();
	
	public static final AdempiereToolsHelper getInstance()
	{
		return instance;
	}
	
	/**
	 * starts up in backend mode. Suitable for the little server-site-tools that we run during rollout
	 */
	public void startupMinimal()
	{
		startupMinimal(RunMode.BACKEND);
	}
	
	/**
	 * Minimal adempiere system startup.
	 */
	public void startupMinimal(RunMode runMode)
	{
		// Disable distributed events because we don't want to broadcast events to network.
		EventBusConstants.disableDistributedEvents();
		
		AddonStarter.warnIfPropertiesFileMissing = false; // don't warn because it we know it's missing.
		
		//
		// Adempiere system shall be started with a minimal set of entity types.
		// In particular, we don't want async, btw, because it doesn't stop when this process is already finished
		ModelValidationEngine.setInitEntityTypes(ModelValidationEngine.INITENTITYTYPE_Minimal);
		ModelValidationEngine.setFailOnMissingModelInteceptors(false);
		
		//
		// Initialize logging
		LogManager.initialize(true); // running it here to make sure we get the client side config

		//
		// Start Adempiere system
		Env.getSingleAdempiereInstance().startup(runMode);
		System.out.println("ADempiere system started in tools minimal mode.");
	}
	
	private AdempiereToolsHelper()
	{
		super();
	}
}
