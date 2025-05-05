package de.metas.distribution.ddorder.process;

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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.IDistributionNetworkDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.storage.IStorageRecord;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * See http://dewiki908/mediawiki/index.php/08118_Wie_geht_das_zur%C3%BCck%2C_was_noch_bei_der_Linie_steht_%28Prozess%29_%28107566315908%29
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
	private ProductPlanning productPlanning;
	private I_DD_NetworkDistributionLine networkLine;
	// Raw Materials Warehouse / Locator
	private ResourceId rawMaterialsPlantId;
	private I_M_Warehouse rawMaterialsWarehouse;
	private I_M_Locator rawMaterialsLocator;
	// Org, Organization BP, In Transit Warehouse /Locator
	//
	private OrgId orgId;
	private BPartnerLocationId orgBPLocationId;
	private WarehouseId inTransitWarehouseId;

	public RawMaterialsReturnDDOrderLineCandidate(
			@NonNull final IAttributeSetInstanceAware attributeSetInstanceAware,
			@NonNull final I_M_Locator locator)
	{
		this.attributeSetInstanceAware = attributeSetInstanceAware;
		this.uom = productBL.getStockUOM(attributeSetInstanceAware.getM_Product_ID());

		this.locator = locator;

		this.dateOrdered = SystemTime.asTimestamp();
	}

	private void loadIfNeeded()
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

	private void load()
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
		final ResourceId warehousePlantId = productPlanningDAO.findPlantId(warehouse.getAD_Org_ID(),
				warehouse,
				attributeSetInstanceAware.getM_Product_ID(),
				attributeSetInstanceAware.getM_AttributeSetInstance_ID());

		//
		// Retrieve Product Planning
		final ProductPlanningQuery query = ProductPlanningQuery
				.builder()
				.orgId(OrgId.ofRepoId(warehouse.getAD_Org_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.plantId(warehousePlantId)
				.productId(ProductId.ofRepoId(attributeSetInstanceAware.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(attributeSetInstanceAware.getM_AttributeSetInstance_ID()))
				.build();
		productPlanning = productPlanningDAO.find(query).orElse(null);
		if (productPlanning == null)
		{
			notValidReasons.add("@NotFound@ @PP_Product_Planning_ID@");
			return;
		}

		//
		// Retrieve Distribution Network line
		if (productPlanning.getDistributionNetworkId() == null)
		{
			notValidReasons.add("@NotFound@ @DD_NetworkDistribution_ID@");
			return;
		}
		final I_DD_NetworkDistribution network = distributionNetworkDAO.getById(productPlanning.getDistributionNetworkId());
		final List<I_DD_NetworkDistributionLine> networkLines = distributionNetworkDAO.retrieveNetworkLinesByTargetWarehouse(network, WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
		if (Check.isEmpty(networkLines))
		{
			notValidReasons.add("@NotFound@ @DD_NetworkDistributionLine_ID@ ("
					+ "@DD_NetworkDistribution_ID@=" + network.getName()
					+ ", @M_Warehouse_ID@=" + warehouse.getName()
					+ ", @PP_Product_Planning_ID@=" + productPlanning.getId()
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
		rawMaterialsLocator = Services.get(IWarehouseBL.class).getOrCreateDefaultLocator(rawMaterialsWarehouse);
		if (rawMaterialsLocator == null)
		{
			notValidReasons.add("@NotFound@ @M_Locator_ID@ (@M_Warehouse_ID@:" + rawMaterialsWarehouse.getName() + ")");
			return;
		}

		//
		// Retrive Raw materials Plant
		try
		{
			rawMaterialsPlantId = productPlanningDAO.findPlantId(
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
		if (rawMaterialsPlantId == null)
		{
			notValidReasons.add("@NotFound@ @PP_Plant_ID@: " + rawMaterialsWarehouse.getName());
			return;
		}

		//
		// Org / Linked BPartner
		orgId = OrgId.ofRepoId(rawMaterialsWarehouse.getAD_Org_ID());
		orgBPLocationId = bpartnerOrgBL.retrieveOrgBPLocationId(orgId);

		//
		// InTransit Warehouse
		inTransitWarehouseId = warehouseDAO.getInTransitWarehouseId(orgId);
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

	public ResourceId getRawMaterialsPlantId()
	{
		loadIfNeeded();
		return rawMaterialsPlantId;
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

	public OrgId getOrgId()
	{
		loadIfNeeded();
		return orgId;
	}

	public BPartnerLocationId getOrgBPLocationId()
	{
		loadIfNeeded();
		return orgBPLocationId;
	}

	public UserId getPlannerId()
	{
		loadIfNeeded();
		return productPlanning == null ? null : productPlanning.getPlannerId();
	}

	public WarehouseId getInTransitWarehouseId()
	{
		loadIfNeeded();
		return inTransitWarehouseId;
	}
}
