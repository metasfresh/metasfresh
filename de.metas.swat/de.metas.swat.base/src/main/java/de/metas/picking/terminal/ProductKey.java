/**
 * 
 */
package de.metas.picking.terminal;

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


import java.awt.Color;
import java.math.BigDecimal;
import java.util.Properties;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKeyStatus;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_C_POSKey;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * @author cg
 * 
 */
public class ProductKey extends TerminalKey
{
	private final String id;
	private final I_C_POSKey key;
	private final KeyNamePair value;
	private final String tableName;
	private final I_M_Product product;
	private final I_C_BPartner bpartner;
	private final I_C_BPartner_Location bpLocation;

	private int boxNo;
	private IPackingItem pck;
	private DefaultMutableTreeNode usedBin;

	public DefaultMutableTreeNode getUsedBin()
	{
		return usedBin;
	}

	/*package*/void setUsedBin(final DefaultMutableTreeNode usedBin)
	{
		this.usedBin = usedBin;
	}

	public IPackingItem getPackingItem()
	{
		return pck;
	}

	public void setPackingItem(final IPackingItem pck)
	{
		this.pck = pck;
	}

	/**
	 * Wraps {@link PackingStates} enumeration
	 * 
	 * @author tsa
	 * 
	 */
	private class ProductStatus implements ITerminalKeyStatus
	{
		private final PackingStates packStatus;
		private final Color color;

		private ProductStatus()
		{
			this(PackingStates.packed);
		}

		public ProductStatus(final PackingStates state)
		{
			super();
			packStatus = state;
			color = packStatus.getColor();
		}

		public PackingStates getPackStatus()
		{
			return packStatus;
		}

		@Override
		public int getAD_Image_ID()
		{
			return key.getAD_Image_ID();
		}

		@Override
		public int getAD_PrintColor_ID()
		{
			return key.getAD_PrintColor_ID();
		}

		@Override
		public int getAD_PrintFont_ID()
		{
			return key.getAD_PrintFont_ID();
		}

		@Override
		public String getName()
		{
			return getPackStatus().name();
		}

		@Override
		public Color getColor()
		{
			return color;
		}
	}

	private static I_C_POSKey createPOSKey(final I_M_Product product, final I_C_BPartner partner, final I_C_BPartner_Location bpLoc)
	{
		final I_C_POSKey key = InterfaceWrapperHelper.newInstance(I_C_POSKey.class, product);
		InterfaceWrapperHelper.setSaveDeleteDisabled(key, true);

		// Max text length
		// NOTE: please think twice before changing this number because it was tuned for 1024x740 resolution
		// see http://dewiki908/mediawiki/index.php/05863_Fenster_Kommissionierung_-_bessere_Ausnutzung_Kn%C3%B6pfefelder_f%C3%BCr_Textausgabe_%28102244669218%29
		final int maxLength = 25;
		
		final String pValue = Utils.mkTruncatedstring(product.getValue(), maxLength);
		final String pName = Utils.mkTruncatedstring(product.getName(), maxLength); 
		final String bpName = Utils.mkTruncatedstring(partner.getName(), maxLength);
		final String bplName = Utils.mkTruncatedstring(bpLoc.getName(), maxLength);
		final String name = pValue
				+ "<br>"
				+ pName
				+ "<br>"
				+ bpName
				+ "<br>"
				+ bplName;
		key.setName(name);
		return key;
	}

	//

	protected ProductKey(
			final ITerminalContext terminalContext, 
			final IPackingItem pck,
			final int boxNo,
			final I_C_BPartner bpartner,
			final I_C_BPartner_Location bpLocation)
	{
		super(terminalContext);

		Check.assumeNotNull(pck, "PackingItem");
		this.pck = pck;
		this.tableName = I_M_Product.Table_Name;
		this.product = pck.getM_Product();
		this.bpartner = bpartner;
		this.bpLocation = bpLocation;
		this.key = createPOSKey(product, bpartner, bpLocation);

		this.value = new KeyNamePair(product.getM_Product_ID(), key.getName());
		this.status = new ProductStatus();
		setBoxNo(boxNo);

		this.id = buildId();
	}

	@Deprecated
	ProductKey(final ITerminalContext terminalContext, final I_C_POSKey key, final String tableName, final int productId)
	{
		super(terminalContext);

		this.key = key;
		this.tableName = tableName;
		this.value = new KeyNamePair(productId, key.getName());
		this.status = new ProductStatus();

		final Properties ctx = InterfaceWrapperHelper.getCtx(key);
		this.product = InterfaceWrapperHelper.create(ctx, value.getKey(), I_M_Product.class, ITrx.TRXNAME_None);
		this.bpartner = null;
		this.bpLocation = null;
		
		this.id = buildId();
	}

	/**
	 * Builds the key ID.
	 * @return key ID
	 */
	protected final String buildId()
	{
		return Util.mkKey(
				value == null ? "" : value.getKey()
				, product == null ? -1 : product.getM_Product_ID()
				// , boxNo // i.e. the picking slot => not needed
				, bpartner == null ? -1 : bpartner.getC_BPartner_ID()
				, bpLocation == null ? -1 : bpLocation.getC_BPartner_Location_ID()
				)
				.toString();
		// id = "" + key.getC_POSKey_ID()
		// + (value != null ? "#" + value.getKey() : "")
		//		+ "-" + UUID.randomUUID();
		
	}

	void setBoxNo(final int no)
	{
		boxNo = no;
	}

	public int getBoxNo()
	{
		return boxNo;
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		final StringBuilder sb = new StringBuilder();
		sb
				.append("<font size=\"7\">")
				.append(pck.getQtySum()).append("  ")
				.append("</font><br>")
				.append("<font size=\"5\">")
				.append(value.getName())
				.append("</font>");
		return sb.toString();
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public String getText()
	{
		return key.getText();
	}

	@Override
	public int getSpanX()
	{
		return key.getSpanX();
	}

	@Override
	public int getSpanY()
	{
		return key.getSpanY();
	}

	@Override
	public boolean isActive()
	{
		return key.isActive();
	}

	@Override
	public ProductStatus getStatus()
	{
		return (ProductStatus)status;
	}

	@Override
	public IKeyLayout getSubKeyLayout()
	{
		return null;
	}

	public void setStatus(final PackingStates packStatus)
	{
		if (packStatus == null)
		{
			return;
		}

		final ProductStatus statusOld = getStatus();
		if (statusOld.getPackStatus() == packStatus)
		{
			// nothing changed
			return;
		}

		final ProductStatus statusNew = new ProductStatus(packStatus);
		setStatus(statusNew);
	}

	@Override
	public String toString()
	{
		return "ProductKey [id=" + id + ", key=" + key + ", value=" + value + ", tableName=" + tableName + ", boxNo=" + boxNo + "]";
	}

	public int getM_Product_ID()
	{
		return value == null ? -1 : value.getKey();
	}

	public I_M_Product getM_Product()
	{
		return product;
	}
	
	public int getC_BPartner_ID()
	{
		return bpartner == null ? -1 : bpartner.getC_BPartner_ID();
	}

	public I_C_BPartner getC_BPartner()
	{
		return bpartner;
	}

	public I_C_BPartner_Location getC_BPartner_Location()
	{
		return bpLocation;
	}
	
	public int getC_BPartner_Location_ID()
	{
		return bpLocation == null ? -1 : bpLocation.getC_BPartner_Location_ID();
	}

	/**
	 * Gets Qty inside this Product key (i.e. sum of Qtys from underlying shipment schedules)
	 * @return
	 */
	public BigDecimal getQty()
	{
		if (pck == null)
		{
			return BigDecimal.ZERO;
		}
		return pck.getQtySum();		
	}
}
