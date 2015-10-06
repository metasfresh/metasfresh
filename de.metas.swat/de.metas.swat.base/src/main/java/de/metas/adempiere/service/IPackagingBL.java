package de.metas.adempiere.service;

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


import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.util.ISingletonService;
import org.compiere.model.Lookup;
import org.compiere.model.MPackage;

import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public interface IPackagingBL extends ISingletonService
{
	/**
	 * 
	 * @return true if we shall display non items
	 * @see de.metas.shipping.util.Constants#SYSCONFIG_SHIPMENT_PACKAGE_NON_ITEMS
	 */
	boolean isDisplayNonItemsEnabled(Properties ctx);

	Lookup createPackgagingContainerLookup();

	Lookup createShipperLookup();

	Map<I_M_InOut, Collection<MPackage>> createInOutAndPackages(
			Properties ctx,
			DefaultMutableTreeNode root, 
			int shipperId,
			Collection<I_M_ShipmentSchedule> nonItems, 
			String trxName);
}
