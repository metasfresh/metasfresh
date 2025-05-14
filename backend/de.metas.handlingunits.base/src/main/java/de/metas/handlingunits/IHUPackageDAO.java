package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.mpackage.PackageId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import java.util.Collection;
import java.util.List;

public interface IHUPackageDAO extends ISingletonService
{

	List<I_M_Package_HU> retrievePackageHUs(org.compiere.model.I_M_Package mpackage);

	List<I_M_HU> retrieveHUs(org.compiere.model.I_M_Package mpackage);

	List<I_M_Package> retrievePackages(Collection<PackageId> packageIds);

	/**
	 * @param hu
	 * @return true if given hu is assigned to any {@link I_M_Package}
	 */
	boolean isHUAssignedToPackage(I_M_HU hu);

	/**
	 * Retrieve all {@link I_M_Package}s where given <code>hu</code> is assigned.
	 *
	 * @param hu
	 * @param trxName
	 * @return
	 */
	List<I_M_Package> retrievePackages(I_M_HU hu, String trxName);

	/**
	 * Retrieve {@link I_M_Package}s where given <code>hu</code> is assigned.
	 *
	 * @param hu
	 * @param trxName
	 * @return package or null
	 * @throws HUException if HU is assigned to more then one package
	 */
	I_M_Package retrievePackage(I_M_HU hu);

	/**
	 * Retrieve all packages which are assigned to given shipment.
	 *
	 * @param shipment
	 * @return packages
	 */
	List<I_M_Package> retrievePackagesForShipment(I_M_InOut shipment);

}
