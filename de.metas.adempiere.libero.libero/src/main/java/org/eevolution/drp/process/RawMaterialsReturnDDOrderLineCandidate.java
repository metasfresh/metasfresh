package org.eevolution.drp.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.IDistributionNetworkDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.product.IProductBL;
import de.metas.storage.IStorageRecord;
import lombok.NonNull;

/**
 *
 * @author tsa
 * @see http://dewiki908/mediawiki/index.php/08118_Wie_geht_das_zur%C3%BCck%2C_was_noch_bei_der_Linie_steht_%28Prozess%29_%28107566315908%29
 */
/* package */final class RawMaterialsReturnDDOrderLineCandidate
{
	// Services
	private final transient IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final transient IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final transient IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	// Parameters
	private final Properties ctx;
	private final IAttributeSetInstanceAware attributeSetInstanceAware;

	/** Current locator */
	private final I_M_Locator locator;
	private final Timestamp dateOrdered;

	//
	// Accumulated values
	private BigDecimal qty = BigDecimal.ZERO;
	private final I_C_UOM uom;

	// Planning data
	private boolean loaded = false;
	private final Set<String> notValidReasons = new LinkedHashSet<>();
	private I_PP_Product_Planning productPlanning;
	private I_DD_NetworkDistributionLine networkLine;
	// Raw Materials Warehouse / Locator
	private I_S_Resource rawMaterialsPlant;
	private I_M_Warehouse rawMaterialsWarehouse;
	private I_M_Locator rawMaterialsLocator;
	// Org, Organization BP, In Transit Warehouse /Locator
	//
	private I_AD_Org org;
	private I_C_BPartner orgBPartner;
	private I_C_BPartner_Location orgBPLocation;
	private I_M_Warehouse inTransitWarehouse;

	public RawMaterialsReturnDDOrderLineCandidate(
			@NonNull final Properties ctx,
			@NonNull final IAttributeSetInstanceAware attributeSetInstanceAware,
			@NonNull final I_M_Locator locator)
	{
		this.ctx = ctx;

		this.attributeSetInstanceAware = attributeSetInstanceAware;
		this.uom = productBL.getStockingUOM(attributeSetInstanceAware.getM_Product());

		this.locator = locator;

		this.dateOrdered = SystemTime.asTimestamp();
	}

	private final void loadIfNeeded()
	{
		if (loaded)
		{
			return;
		}
		loaded = true;

		try
		{
			load();
		}
		catch (final Exception e)
		{
			notValidReasons.add(e.getLocalizedMessage());
		}
	}

	private final void load()
	{
		notValidReasons.clear();

		if (attributeSetInstanceAware.getM_Product().isBOM())
		{
			notValidReasons.add("@IsBOM@=@Y@");
			return;
		}

		//
		// Retrieve Plant of current warehouse (where our Quantitites currently are)
		final I_M_Warehouse warehouse = locator.getM_Warehouse();
		final I_S_Resource warehousePlant = productPlanningDAO.findPlant(warehouse.getAD_Org_ID(),
				warehouse,
				attributeSetInstanceAware.getM_Product_ID(),
				attributeSetInstanceAware.getM_AttributeSetInstance_ID());

		//
		// Retrieve Product Planning
		productPlanning = productPlanningDAO.find(
				warehouse.getAD_Org_ID(),
				warehouse.getM_Warehouse_ID(),
				warehousePlant == null ? 0 : warehousePlant.getS_Resource_ID(),
				attributeSetInstanceAware.getM_Product_ID(),
				attributeSetInstanceAware.getM_AttributeSetInstance_ID());
		if (productPlanning == null)
		{
			notValidReasons.add("@NotFound@ @PP_Product_Planning_ID@");
			return;
		}

		//
		// Retrieve Distribution Network line
		if (productPlanning.getDD_NetworkDistribution_ID() <= 0)
		{
			notValidReasons.add("@NotFound@ @DD_NetworkDistribution_ID@");
			return;
		}
		final I_DD_NetworkDistribution network = productPlanning.getDD_NetworkDistribution();
		final List<I_DD_NetworkDistributionLine> networkLines = distributionNetworkDAO.retrieveNetworkLinesByTargetWarehouse(network, warehouse.getM_Warehouse_ID());
		if (Check.isEmpty(networkLines))
		{
			notValidReasons.add("@NotFound@ @DD_NetworkDistributionLine_ID@ ("
					+ "@DD_NetworkDistribution_ID@=" + network.getName()
					+ ", @M_Warehouse_ID@=" + warehouse.getName()
					+ ", @PP_Product_Planning_ID@=" + productPlanning.getPP_Product_Planning_ID()
					+ ")");
			return;
		}
		networkLine = networkLines.get(0);

		//
		// Retrieve Raw materials Warehouse (where we will send our quantitites back)
		rawMaterialsWarehouse = networkLine.getM_WarehouseSource();
		if (rawMaterialsWarehouse == null)
		{
			notValidReasons.add("@NotFound@ @M_Warehouse_ID@");
			return;
		}
		rawMaterialsLocator = Services.get(IWarehouseBL.class).getDefaultLocator(rawMaterialsWarehouse);
		if (rawMaterialsLocator == null)
		{
			notValidReasons.add("@NotFound@ @M_Locator_ID@ (@M_Warehouse_ID@:" + rawMaterialsWarehouse.getName() + ")");
			return;
		}

		//
		// Retrive Raw materials Plant
		try
		{
			rawMaterialsPlant = productPlanningDAO.findPlant(
					rawMaterialsWarehouse.getAD_Org_ID(),
					warehouse,
					attributeSetInstanceAware.getM_Product_ID(),
					attributeSetInstanceAware.getM_AttributeSetInstance_ID());
		}
		catch (final NoPlantForWarehouseException ex)
		{
			notValidReasons.add("@NotFound@ @PP_Plant_ID@: " + ex.getLocalizedMessage());
			return;
		}
		if (rawMaterialsPlant == null)
		{
			notValidReasons.add("@NotFound@ @PP_Plant_ID@: " + rawMaterialsWarehouse.getName());
			return;
		}

		//
		// Org / Linked BPartner
		org = rawMaterialsWarehouse.getAD_Org();
		orgBPartner = bpartnerOrgBL.retrieveLinkedBPartner(org);
		if (orgBPartner == null)
		{
			notValidReasons.add("@NotFound@ @AD_OrgBP_ID@: " + org.getName());
			return;
		}
		orgBPLocation = bpartnerOrgBL.retrieveOrgBPLocation(ctx, org.getAD_Org_ID(), ITrx.TRXNAME_None);

		//
		// InTransit Warehouse
		inTransitWarehouse = warehouseDAO.retrieveWarehouseInTransitForOrg(ctx, org.getAD_Org_ID());
		if (inTransitWarehouse == null)
		{
			notValidReasons.add("@NotFound@ @M_Warehouse_ID@ @IsInTransit@: " + org.getName());
			return;
		}
	}

	public void addStorageRecord(final IStorageRecord storageRecord)
	{
		Check.assumeNotNull(storageRecord, "storageRecord not null");
		// TODO: validate if applies

		final BigDecimal storageQty = storageRecord.getQtyOnHand();
		qty = qty.add(storageQty);
	}

	public String getSummary()
	{
		return "@M_Locator_ID@:" + locator.getValue() + " - " + (rawMaterialsWarehouse == null ? "?" : rawMaterialsWarehouse.getName())
				+ ", @M_Product_ID@:" + attributeSetInstanceAware.getM_Product().getName()
				+ ", @Qty@:" + qty + " " + uom.getUOMSymbol();
	}

	public boolean isValid()
	{
		loadIfNeeded();
		return notValidReasons.isEmpty();
	}

	public String getNotValidReasons()
	{
		return StringUtils.toString(notValidReasons, "; ");
	}

	public IAttributeSetInstanceAware getM_Product()
	{
		return attributeSetInstanceAware;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public Timestamp getDateOrdered()
	{
		return dateOrdered;
	}

	/** @return locator where Quantity currently is */
	public I_M_Locator getM_Locator()
	{
		return locator;
	}

	public I_DD_NetworkDistributionLine getDD_NetworkDistributionLine()
	{
		loadIfNeeded();
		return networkLine;
	}

	public I_S_Resource getRawMaterialsPlant()
	{
		loadIfNeeded();
		return rawMaterialsPlant;
	}

	public I_M_Warehouse getRawMaterialsWarehouse()
	{
		loadIfNeeded();
		return rawMaterialsWarehouse;
	}

	public I_M_Locator getRawMaterialsLocator()
	{
		loadIfNeeded();
		return rawMaterialsLocator;
	}

	public I_AD_Org getAD_Org()
	{
		loadIfNeeded();
		return org;
	}

	public I_C_BPartner getOrgBPartner()
	{
		loadIfNeeded();
		return orgBPartner;
	}

	public I_C_BPartner_Location getOrgBPLocation()
	{
		loadIfNeeded();
		return orgBPLocation;
	}

	public int getPlanner_ID()
	{
		loadIfNeeded();
		return productPlanning == null ? 0 : productPlanning.getPlanner_ID();
	}

	public I_M_Warehouse getInTransitWarehouse()
	{
		loadIfNeeded();
		return inTransitWarehouse;
	}
}
