package de.metas.procurement.base;

import java.util.List;

import javax.ws.rs.core.Response;

import org.adempiere.util.ISingletonService;

import de.metas.javaclasses.AD_JavaClass;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.sync.IServerSync;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncProductSupply;
import de.metas.procurement.sync.protocol.SyncRfQChangeRequest;
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
 * Mapping:
 * <b>SyncProductSupply</b>
 * <ul>
 * <li><code>Bpartner_uuid <=> C_BPartner_ID</code>. Using <code>C_BPartner</code>, we get the <code>M_Pricing_system</code>, <code>AD_Org</code> and in case of uncontracted products we also get the
 * shipping location and thus the <code>M_PriceList</code>.</li>
 * <li><code>Product_uuid <=> PMM_Product_ID</code>. from the <code>PMM_Product</code>, we get the <code>M_Product</code>, <code>M_HU_PI_Item_Product</code> and <code>M_Warehouse</code></li>
 * <li><code>ContractLine_uuid <=> C_flatrate_Term</code>. Note that both <code>SyncProductSupply.ContractLine_uuid</code> and <code>SyncContract.Uuid</code> contain the flatrate term, because we have
 * only one product per term</li>
 * </ul>
 *
 *
 * @author metas-dev <dev@metasfresh.com>
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
	public List<SyncProduct> getAllProducts();

	/**
	 * Create {@link I_PMM_QtyReport_Event}s from the given <code>request</code>'s {@link SyncProductSupply} instances.<br>
	 * Don't update the pricing information, that is done one the fly, when a {@link I_PMM_PurchaseCandidate} is created or updated.
	 * <p>
	 * <b>Important:</b> Make sure that each record is saved.
	 *
	 * @param request
	 * @return {@link Response#ok()}
	 * @see IServerSync#reportProductSupplies(SyncProductSuppliesRequest)
	 */
	@Override
	public void reportProductSupplies(SyncProductSuppliesRequest request);

	/**
	 *
	 * @param request
	 * @return {@link Response#ok()}
	 * @see IServerSync#reportWeekSupply(SyncWeeklySupplyRequest)
	 */
	@Override
	public void reportWeekSupply(SyncWeeklySupplyRequest request);

	/**
	 * @param request
	 * @see IServerSync#reportRfQChanges(SyncRfQChangeRequest)
	 */
	@Override
	public void reportRfQChanges(SyncRfQChangeRequest request);

}
