package de.metas.adempiere.form;

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


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_M_PackagingTree;
import org.compiere.model.PackagingTreeItemComparable;
import org.compiere.model.PackingTreeBL;
import org.compiere.model.X_M_PackagingTreeItem;
import org.compiere.model.X_M_PackagingTreeItemSched;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackingDetailsMd implements IPackingDetailsModel
{
	public static final String PACK_PROD = "packProd";

	public static final String PACK_VOLUME = "packVolume";

	public static final String PACK_VOLUME_FILLED_PERCENT = "packVolumeFilledPercent";

	public static final String PACK_VOLUME_MAX = "packMaxVolume";

	public static final String PACK_WEIGHT = "packWeight";

	public static final String PACK_WEIGHT_FILLED_PERCENT = "packWeightFilledPercent";

	public static final String PACK_WEIGHT_MAX = "packMaxWeight";

	public static final String PI_LOCATION = "plLocation";

	public static final String PI_PROD = "plProd";

	/**
	 * Product's description from the order line (relevant for a div/misc-Product).
	 */
	public static final String PI_OL_PROD_DESC = "piOlProdDesc";

	public static final String PI_QTY = "plQty";

	public static final String PI_VOLUME = "plVolume";

	public static final String PI_WEIGHT = "plWeight";

	/**
	 * Specifies if the weight of a single packing line is editable (required for div products)
	 */
	public static final String PI_WEIGHT_EDITABLE = "plWeightEditable";

	public static final int STATE_INVALID = 30;

	public static final int STATE_OK = 10;

	public static final int STATE_WARNING = 20;

	public IBinPacker packer;

	private String packProd;

	private String piOlProdDesc;

	// private String piOlComment;

	private BigDecimal packVolume;

	private BigDecimal packVolumeFilledPercent;

	private BigDecimal packVolumeMax;

	private BigDecimal packWeight;

	private boolean piWeightEditable;

	private BigDecimal packWeightFilledPercent;

	private BigDecimal packWeightMax;

	private final PropertyChangeSupport pcs;

	private String piLocation;
	private String piProd;
	private BigDecimal piQty;
	private BigDecimal piVolume;
	private BigDecimal piWeight;

	int selectedPackagingContainerId;

	public int selectedShipperId;
	
	@Override
	public int getSelectedShipper()
	{
		return selectedShipperId;
	}

	private final PackingTreeModel treeModel;
	
	@Override
	public PackingTreeModel getPackingTreeModel()
	{
		return treeModel;
	}

	private final List<I_M_ShipmentSchedule> nonItems;

	public Collection<IPackingItem> unallocatedLines;
	public Collection<AvailableBins> avalaiableContainers;

	/**
	 * iA <code>false</code> value means that there will be no shipper involved.
	 */
	final public boolean useShipper;

	private int validState;

	public PackingDetailsMd(
			final Collection<IPackingItem> unallocatedLines,
			final Collection<AvailableBins> avalaiableContainers,
			final boolean useShipper,
			final List<I_M_ShipmentSchedule> nonItems,
			final int C_BPartner_ID, 
			final int C_BPartnerLocation_ID,
			final int M_Warehouse_Dest_ID,
			final boolean isGroupingByWarehouseDest)
	{
		this.unallocatedLines = unallocatedLines;
		this.avalaiableContainers = avalaiableContainers;
		treeModel = new PackingTreeModel(unallocatedLines, avalaiableContainers, C_BPartner_ID, C_BPartnerLocation_ID, M_Warehouse_Dest_ID, isGroupingByWarehouseDest);
		pcs = new PropertyChangeSupport(this);
		this.useShipper = useShipper;
		if (!useShipper)
		{
			selectedShipperId = 1000001; // TODO: HARDCODED
		}
		this.nonItems = nonItems;
	}

	public PackingDetailsMd(final Properties ctx, I_M_PackagingTree savedTree, final boolean useShipper, final int C_BPartnerLocation_ID, final int M_Warehouse_Dest_ID, final boolean isGroupByWarehouseDest)
	{
		List<PackagingTreeItemComparable> avail = PackingTreeBL.getItems(savedTree.getM_PackagingTree_ID(), X_M_PackagingTreeItem.TYPE_AvailableBox);
		Collection<AvailableBins> avalaiableContainers = new ArrayList<AvailableBins>();
		for (X_M_PackagingTreeItem item : avail)
		{
			avalaiableContainers.add(new AvailableBins(ctx, item.getM_PackagingContainer(), item.getQty().intValue(), null));
		}
		this.avalaiableContainers = avalaiableContainers;
		
		treeModel = new PackingTreeModel(ctx, savedTree, avalaiableContainers, C_BPartnerLocation_ID, M_Warehouse_Dest_ID, isGroupByWarehouseDest);
		pcs = new PropertyChangeSupport(this);
		this.useShipper = useShipper;
		if (!useShipper)
		{
			selectedShipperId = 1000001; // TODO: HARDCODED
		}
		//
		List<X_M_PackagingTreeItemSched> nonItems = PackingTreeBL.getNonItems(savedTree.getM_PackagingTree_ID());
		List<I_M_ShipmentSchedule> scheds = new ArrayList<I_M_ShipmentSchedule>();
		for (X_M_PackagingTreeItemSched sched : nonItems)
		{
			scheds.add(sched.getM_ShipmentSchedule());
		}
		this.nonItems = scheds;
	}

	public void addPropertyChangeListener(PropertyChangeListener l)
	{
		pcs.addPropertyChangeListener(l);
	}

	public BigDecimal getPackMaxVolume()
	{
		return packVolumeMax;
	}

	public BigDecimal getPackMaxWeight()
	{
		return packWeightMax;
	}

	public String getPackProd()
	{
		return packProd;
	}

	public BigDecimal getPackVolume()
	{
		return packVolume;
	}

	public BigDecimal getPackVolumeFilledPercent()
	{
		return packVolumeFilledPercent;
	}

	public BigDecimal getPackWeight()
	{
		return packWeight;
	}

	public BigDecimal getPackWeightFilledPercent()
	{
		return packWeightFilledPercent;
	}

	public String getPiLocation()
	{
		return piLocation;
	}

	public String getPiProd()
	{
		return piProd;
	}

	public BigDecimal getPiQty()
	{
		return piQty;
	}

	public BigDecimal getPiVolume()
	{
		return piVolume;
	}

	public BigDecimal getPiWeight()
	{
		return piWeight;
	}

	@Override
	public int getValidState()
	{
		return validState;
	}

	public void setPackMaxVolume(BigDecimal packMaxVolume)
	{
		pcs.firePropertyChange(PACK_VOLUME_MAX, this.packVolumeMax, packMaxVolume);
		this.packVolumeMax = packMaxVolume;
	}

	public void setPackMaxWeight(BigDecimal packMaxWeight)
	{
		pcs.firePropertyChange(PACK_WEIGHT_MAX, this.packWeightMax, packMaxWeight);
		this.packWeightMax = packMaxWeight;
	}

	public void setPackProd(String packProd)
	{
		pcs.firePropertyChange(PACK_PROD, this.packProd, packProd);
		this.packProd = packProd;
	}

	public void setPackVolume(BigDecimal packVolume)
	{
		pcs.firePropertyChange(PACK_VOLUME, this.packVolume, packVolume);
		this.packVolume = packVolume;
	}

	public void setPackVolumeFilledPercent(BigDecimal packVolumeFilledPercent)
	{
		pcs.firePropertyChange(PACK_VOLUME_FILLED_PERCENT, this.packVolumeFilledPercent, packVolumeFilledPercent);
		this.packVolumeFilledPercent = packVolumeFilledPercent;
	}

	public void setPackWeight(BigDecimal packWeight)
	{
		pcs.firePropertyChange(PACK_WEIGHT, this.packWeight, packWeight);
		this.packWeight = packWeight;
	}

	public boolean isPiWeightEditable()
	{
		return piWeightEditable;
	}

	public void setPiWeightEditable(boolean piWeightEditable)
	{
		pcs.firePropertyChange(PI_WEIGHT_EDITABLE, this.piWeightEditable, piWeightEditable);
		this.piWeightEditable = piWeightEditable;
	}

	public void setPackWeightFilledPercent(BigDecimal packWeightFilledPercent)
	{
		pcs.firePropertyChange(PACK_WEIGHT_FILLED_PERCENT, this.packWeightFilledPercent, packWeightFilledPercent);
		this.packWeightFilledPercent = packWeightFilledPercent;
	}

	public void setPiLocation(String piLocation)
	{
		pcs.firePropertyChange(PI_LOCATION, this.piLocation, piLocation);
		this.piLocation = piLocation;
	}

	public void setPiProd(String piProd)
	{
		pcs.firePropertyChange(PI_PROD, this.piProd, piProd);
		this.piProd = piProd;
	}

	public String getPiOlProdDesc()
	{
		return piOlProdDesc;
	}

	public void setPiOlProdDesc(String piOlProdDesc)
	{
		pcs.firePropertyChange(PI_OL_PROD_DESC, this.piOlProdDesc, piOlProdDesc);
		this.piOlProdDesc = piOlProdDesc;
	}

	public void setPiQty(BigDecimal piQty)
	{
		pcs.firePropertyChange(PI_QTY, this.piQty, piQty);
		this.piQty = piQty;
	}

	public void setPiVolume(BigDecimal piVolume)
	{
		pcs.firePropertyChange(PI_VOLUME, this.piVolume, piVolume);
		this.piVolume = piVolume;
	}

	public void setPiWeight(BigDecimal piWeight)
	{
		pcs.firePropertyChange(PI_WEIGHT, this.piWeight, piWeight);
		this.piWeight = piWeight;
	}

	public void setValidState(int validState)
	{
		this.validState = validState;
	}

	@Override
	public List<I_M_ShipmentSchedule> getNonItems()
	{
		return nonItems;
	}
}
