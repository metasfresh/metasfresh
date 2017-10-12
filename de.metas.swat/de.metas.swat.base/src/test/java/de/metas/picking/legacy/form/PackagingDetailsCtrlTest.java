package de.metas.picking.legacy.form;

import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Test;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.impl.ShipmentSchedulePA;

/*
 * #%L
 * de.metas.swat.base
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


import mockit.Mocked;
import mockit.Verifications;

public class PackagingDetailsCtrlTest
{

	@Mocked
	PackingDetailsMd model;
	
	@Mocked
	PackingDetailsV view;
	
	@Mocked ShipmentSchedulePA shipmentSchedulePA;
	
	/**
	 * When the packaging details window is closed without a shipment run having been performed, then the respective locks
	 * must be removed instantly.
	 */
	@Test
	public void resetShipmentRunLockOnClose()
	{
		// set up
		final int adUserId = 10;
		Env.setContext(Env.getCtx(), "#AD_User_ID", adUserId);
				
		Services.registerService(IShipmentSchedulePA.class, shipmentSchedulePA);
		final PackingDetailsCtrl ctrl = new PackingDetailsCtrl(model);
		ctrl.setView(Env.getCtx(), view);
		
		// invocation
		ctrl.cancel();
		
		// checks
		new Verifications()
		{{
			shipmentSchedulePA.deleteUnprocessedLocksForShipmentRun(0, 10, (String)any);
		}};
	}

}
