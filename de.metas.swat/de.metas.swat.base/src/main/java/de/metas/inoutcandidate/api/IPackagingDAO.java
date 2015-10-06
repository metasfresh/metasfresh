package de.metas.inoutcandidate.api;

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


import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.util.ISingletonService;

import de.metas.adempiere.exception.NoContainerException;

/**
 * Packaging related DAO
 * 
 * @author ts
 * @author tsa
 * 
 * @see <a href="http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29">(2009_0022_G61)</a>
 */
public interface IPackagingDAO extends ISingletonService
{
	/**
	 * Retrieves a packaging container available at warehouse #M_Warehouse_ID
	 * 
	 * @param trxName
	 * @return
	 */
	I_M_PackagingContainer retrieveContainer(int containerId, String trxName);

	/**
	 * 
	 * 
	 * @param volume
	 * @param trxName
	 * @return the container with the smallest value that is still bigger than the given <code>volume</code>. If there is no such container, it returns the biggest available container. If there is no
	 *         container at all, it throws a {@link NoContainerException}.
	 */
	I_M_PackagingContainer retrieveContainer(BigDecimal volume, int warehouseId, String trxName);

	List<I_M_PackagingContainer> retrieveContainers(int wareHouseId, String trxName);

	/**
	 * Creates an empty {@link IPackageableQuery}.
	 * 
	 * @return
	 */
	IPackageableQuery createPackageableQuery();

	/**
	 * Retrieve all {@link IPackageable} items for given <code>query</code>
	 * 
	 * @param ctx
	 * @param query
	 * @return
	 */
	Iterator<IPackageable> retrievePackableLines(Properties ctx, IPackageableQuery query);

}
