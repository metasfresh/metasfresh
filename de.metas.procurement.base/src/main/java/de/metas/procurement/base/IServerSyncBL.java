package de.metas.procurement.base;

import java.util.List;

import javax.ws.rs.core.Response;

import org.adempiere.util.ISingletonService;

import de.metas.javaclasses.AD_JavaClass;
import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
/**
 * {@link ISingletonService} implementation of the {@link IServerSync} interface.
 * <p>
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@AD_JavaClass(ignore = true)
public interface IServerSyncBL extends IServerSync, ISingletonService
{
	/**
	 * TODO: document the mapping & unit-test
	 *
	 * Get all partners that have at least one contract, with all their contracts and products. Also get all products that do not belong to a particular contract, but can be offered by the vendor none
	 * the less.
	 *
	 * @return
	 * @see IServerSync#getAllBPartners() for a more high-level description
	 */
	@Override
	public List<SyncBPartner> getAllBPartners();

	/**
	 * Send those products to the caller that
	 * <ul>
	 * <li>are generic, i.e. don't have a C_BPartner
	 * <li>do have a packing instruction
	 * <li>are currently valid (or have no valid date!)
	 * </ul>
	 */
	@Override
	public List<SyncProduct> getAllNotContractedProducts();

	/**
	 * TODO: document the mapping & unit-test
	 *
	 * @param request
	 * @return {@link Response#ok()}
	 * @see IServerSync#reportProductSupplies(SyncProductSuppliesRequest)
	 */
	@Override
	public Response reportProductSupplies(SyncProductSuppliesRequest request);


	/**
	 *
	 * @param request
	 * @return {@link Response#ok()}
	 * @see IServerSync#reportWeekSupply(SyncWeeklySupplyRequest)
	 */
	@Override
	public Response reportWeekSupply(SyncWeeklySupplyRequest request);
}
