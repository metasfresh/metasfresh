package org.eevolution.mrp.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;

import de.metas.material.planning.IMRPSegment;

/**
 * Immutable MRP Segment
 * 
 * @author tsa
 *
 */
/* package */final class MRPSegment implements IMRPSegment
{
	private final int adClientId;
	private final int adOrgId;
	private final I_AD_Org adOrg;
	private int warehouseId;
	private I_M_Warehouse warehouse;
	private int plantId;
	private I_S_Resource plant;
	private int productId;
	private I_M_Product product;

	//
	private String _toStringBuilt = null;

	public MRPSegment(final int adClientId,
			final I_AD_Org adOrg,
			final I_M_Warehouse warehouse,
			final I_S_Resource plant,
			final I_M_Product product)
	{
		super();
		this.adClientId = adClientId < 0 ? -1 : adClientId;
		//
		if (adOrg == null || adOrg.getAD_Org_ID() <= 0)
		{
			this.adOrgId = -1;
			this.adOrg = null;
		}
		else
		{
			this.adOrgId = adOrg.getAD_Org_ID();
			this.adOrg = adOrg;
		}
		//
		if (warehouse == null || warehouse.getM_Warehouse_ID() <= 0)
		{
			this.warehouseId = -1;
			this.warehouse = null;
		}
		else
		{
			this.warehouseId = warehouse.getM_Warehouse_ID();
			this.warehouse = warehouse;
		}
		//
		if (plant == null || plant.getS_Resource_ID() <= 0)
		{
			this.plantId = -1;
			this.plant = null;
		}
		else
		{
			this.plantId = plant.getS_Resource_ID();
			this.plant = plant;
		}
		//
		if (product == null || product.getM_Product_ID() <= 0)
		{
			this.productId = -1;
			this.product = null;
		}
		else
		{
			this.productId = product.getM_Product_ID();
			this.product = product;
		}
	}

	private MRPSegment(final MRPSegment mrpSegment)
	{
		super();
		this.adClientId = mrpSegment.adClientId;
		this.adOrgId = mrpSegment.adOrgId;
		this.adOrg = mrpSegment.adOrg;
		this.plantId = mrpSegment.plantId;
		this.plant = mrpSegment.plant;
		this.warehouseId = mrpSegment.warehouseId;
		this.warehouse = mrpSegment.warehouse;
		this.productId = mrpSegment.productId;
		this.product = mrpSegment.product;
	}

	@Override
	public String toString()
	{
		if (_toStringBuilt == null)
		{
			final String orgStr = adOrg == null ? "-" : adOrg.getValue();
			final String warehouseStr = warehouse == null ? "-" : (warehouse.getValue() + "_" + warehouse.getName());
			final String plantStr = plant == null ? "-" : plant.getValue();
			final String productStr = product == null ? "-" : product.getValue();

			_toStringBuilt = getClass().getSimpleName() + "["
					+ "adClientId=" + adClientId
					+ ", org=" + orgStr
					+ ", plant=" + plantStr
					+ ", warehouse=" + warehouseStr
					+ ", product=" + productStr
					+ "]";
		}
		return _toStringBuilt;
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(this.adClientId)
				.append(this.adOrgId)
				.append(this.plantId)
				.append(this.warehouseId)
				.append(this.productId)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final MRPSegment other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.adClientId, other.adClientId)
				.append(this.adOrgId, other.adOrgId)
				.append(this.plantId, other.plantId)
				.append(this.warehouseId, other.warehouseId)
				.append(this.productId, other.productId)
				.isEqual();
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public I_AD_Org getAD_Org()
	{
		return adOrg;
	}

	@Override
	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	@Override
	public I_M_Warehouse getM_Warehouse()
	{
		return warehouse;
	}

	@Override
	public int getM_Warehouse_ID()
	{
		return warehouseId;
	}

	@Override
	public IMRPSegment setM_Warehouse(final I_M_Warehouse warehouse)
	{
		final int warehouseIdNew;
		if (warehouse == null || warehouse.getM_Warehouse_ID() <= 0)
		{
			warehouseIdNew = -1;
		}
		else
		{
			warehouseIdNew = warehouse.getM_Warehouse_ID();
		}

		// No change => return this
		if (warehouseIdNew == this.warehouseId)
		{
			return this;
		}

		final MRPSegment mrpSegmentNew = new MRPSegment(this);
		mrpSegmentNew.warehouseId = warehouseIdNew;
		mrpSegmentNew.warehouse = warehouse;
		return mrpSegmentNew;
	}

	@Override
	public I_S_Resource getPlant()
	{
		return plant;
	}

	@Override
	public int getPlant_ID()
	{
		return plantId;
	}

	@Override
	public IMRPSegment setPlant(final I_S_Resource plant)
	{
		final int plantIdNew;
		if (plant == null || plant.getS_Resource_ID() <= 0)
		{
			plantIdNew = -1;
		}
		else
		{
			plantIdNew = plant.getS_Resource_ID();
		}

		// No change => return this
		if (plantIdNew == this.plantId)
		{
			return this;
		}

		final MRPSegment mrpSegmentNew = new MRPSegment(this);
		mrpSegmentNew.plantId = plantIdNew;
		mrpSegmentNew.plant = plant;
		return mrpSegmentNew;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public IMRPSegment setM_Product(final I_M_Product product)
	{
		final int productIdNew;
		if (product == null || product.getM_Product_ID() <= 0)
		{
			productIdNew = -1;
		}
		else
		{
			productIdNew = product.getM_Product_ID();
		}

		// No change => return this
		if (productIdNew == this.productId)
		{
			return this;
		}

		final MRPSegment mrpSegmentNew = new MRPSegment(this);
		mrpSegmentNew.productId = productIdNew;
		mrpSegmentNew.product = product;
		return mrpSegmentNew;
	}

}
