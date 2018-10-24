package de.metas.handlingunits.util;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.product.IProductBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class HUTracerInstance
{
	// Services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUTrxDAO huTrxDAO = Services.get(IHUTrxDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// Params
	private final IHUContext huContext;

	private final String linePrefixIncrement = "  ";

	public HUTracerInstance()
	{
		super();
		huContext = null;
	}

	public HUTracerInstance(final IHUContext huContext)
	{
		super();
		this.huContext = huContext;
	}

	private IHUStorageDAO getHUStorageDAO()
	{
		final IHUStorageFactory storageFactory = getHUStorageFactory();
		final IHUStorageDAO storageDAO = storageFactory.getHUStorageDAO();
		return storageDAO;
	}

	private IHUStorageFactory getHUStorageFactory()
	{
		if (huContext != null)
		{
			return huContext.getHUStorageFactory();
		}
		else
		{
			return handlingUnitsBL.getStorageFactory();
		}
	}

	private IHUAttributesDAO getHUAttributesDAO()
	{
		if (huContext != null)
		{
			return huContext.getHUAttributeStorageFactory().getHUAttributesDAO();
		}
		else
		{
			return HUAttributesDAO.instance;
		}
	}

	public void dumpAllHUs()
	{
		final List<I_M_HU> hus = retrieveAllHUs();
		dump(hus);
	}

	public void dumpAllHUs(final String title)
	{
		printTitle(title);
		dumpAllHUs();
	}

	private List<I_M_HU> retrieveAllHUs()
	{
		final List<I_M_HU> hus = queryBL
				.createQueryBuilder(I_M_HU.class, Env.getCtx(), ITrx.TRXNAME_None)
				.create()
				.list(I_M_HU.class);
		return hus;

	}

	public void dump(final List<I_M_HU> hus)
	{
		final String linePrefix = "";
		dump(System.out, linePrefix, hus);
	}

	public void dump(final PrintStream out, final String linePrefix, final List<I_M_HU> hus)
	{
		if (hus == null || hus.isEmpty())
		{
			return;
		}

		final int lastIndex = hus.size();
		for (int index = 1; index <= lastIndex; index++)
		{
			final I_M_HU hu = hus.get(index - 1);
			dump(out, linePrefix, hu, index, lastIndex);
		}
	}

	public void dump(final I_M_HU hu)
	{
		final String linePrefix = "";
		dump(System.out, linePrefix, hu, -1, -1);
	}

	public void dump(final String title, final I_M_HU hu)
	{
		printTitle(title);
		dump(hu);
	}

	private final void printTitle(final String title)
	{
		System.out.println("==============[ " + title + " ]==================================");
	}

	public void dump(final PrintStream out, final String linePrefix, final I_M_HU hu, final int index, final int lastIndex)
	{
		//
		// HU Info: Display Name, Index/Count
		out.append(linePrefix).append(toStringName(hu));
		if (index > 0)
		{
			out.append(", Index=" + index);
			if (lastIndex > 0)
			{
				out.append("/" + lastIndex);
			}
		}
		out.append("\n");

		//
		// HU Attributes
		final String linePrefix2 = linePrefix + linePrefixIncrement;
		final IHUAttributesDAO huAttributesDAO = getHUAttributesDAO();
		final List<I_M_HU_Attribute> attrs = huAttributesDAO.retrieveAttributesOrdered(hu);
		if (attrs != null && !attrs.isEmpty())
		{
			out.append(linePrefix2).append("Attributes: \n");
			for (final I_M_HU_Attribute attr : attrs)
			{
				out.println(linePrefix2 + linePrefixIncrement + toString(attr));
			}
		}

		//
		// HU Items (+included HUs)
		final List<I_M_HU_Item> items = handlingUnitsDAO.retrieveItems(hu);
		for (final I_M_HU_Item item : items)
		{
			dump(out, linePrefix2, item);
		}

		//
		// HU Storage
		final IHUStorageDAO storageDAO = getHUStorageDAO();
		final List<I_M_HU_Storage> storages = storageDAO.retrieveStorages(hu);
		if (!storages.isEmpty())
		{
			out.append(linePrefix2).append("HU Storage:" + toStringName(hu) + "\n"); // NOPMD no need for toString warnings to fire up, due to it being a custom toString
			for (final I_M_HU_Storage storage : storages)
			{
				dump(out, linePrefix2, storage);
			}
		}
		else
		{
			out.append(linePrefix2).append("(no HU Storages)" + "\n");
		}
	}

	private static String toString(final I_M_HU_Attribute huAttr)
	{
		if (huAttr == null)
		{
			return "(null attribute)";
		}
		
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		final I_M_Attribute attribute = attributesRepo.getAttributeById(huAttr.getM_Attribute_ID());
		final String attrName = attribute == null ? "(no name?)" : attribute.getName();

		final StringBuilder sb = new StringBuilder();
		sb.append(attrName)
				.append(":")
				.append(huAttr.getValue()).append("(S)")
				.append("/")
				.append(huAttr.getValueNumber()).append("(N)")
				.append(", ")
				.append("Seed: ")
				.append(huAttr.getValueInitial()).append("(S)")
				.append("/")
				.append(huAttr.getValueNumberInitial()).append("(N)");

		return sb.toString();
	}

	public void dump(final PrintStream out, final String linePrefix, final I_M_HU_Storage storage)
	{
		final String productStr = storage.getM_Product().getName();
		final BigDecimal qty = storage.getQty();
		final String uomStr = storage.getC_UOM().getUOMSymbol();
		out.append(linePrefix).append("Product: " + productStr + ", Qty: " + qty + " " + uomStr).append("\n");
	}

	public void dump(final PrintStream out, final String linePrefix, final I_M_HU_Item item)
	{
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(item);

		final IHUStorageFactory storageFactory = getHUStorageFactory();
		final IHUItemStorage storage = storageFactory.getStorage(item);

		out.append(linePrefix).append("Item: " + toStringName(item))
				.append(" (" + includedHUs.size() + " included HUs)");

		if (storage.getHUCapacity() != 0)
		{
			out.append(", HU_Qty=" + storage.getHUCount() + "/" + storage.getHUCapacity());
		}
		out.append("\n");

		//
		// Product level storages
		final List<IProductStorage> productStorages = storage.getProductStorages(SystemTime.asDayTimestamp());
		for (final IProductStorage productStorage : productStorages)
		{
			dump(out, linePrefix + linePrefixIncrement, productStorage);
		}

		//
		// Included HUs
		dump(out, linePrefix + linePrefixIncrement, includedHUs);
	}

	public void dump(final PrintStream out, final String linePrefix, final IProductStorage productStorage)
	{
		out.println(linePrefix
				+ "Product=" + Services.get(IProductBL.class).getProductValueAndName(productStorage.getProductId())
				+ ", Qty=" + productStorage.getQty()
				+ ", UOM=" + productStorage.getC_UOM().getUOMSymbol());
	}

	public void dumpTransactions()
	{
		final PrintStream out = System.out;
		final String linePrefix = "";

		final List<I_M_HU_Trx_Hdr> trxHdrs = retrieveAllTrxHdr();
		if (trxHdrs.isEmpty())
		{
			return;
		}

		out.println("\nTransactions: ");
		for (final I_M_HU_Trx_Hdr trxHdr : trxHdrs)
		{
			dump(out, linePrefix, trxHdr);
		}
	}

	private List<I_M_HU_Trx_Hdr> retrieveAllTrxHdr()
	{
		final IQueryBuilder<I_M_HU_Trx_Hdr> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Trx_Hdr.class, Env.getCtx(), ITrx.TRXNAME_None);
		queryBuilder.orderBy()
				.addColumn(I_M_HU_Trx_Hdr.COLUMNNAME_M_HU_Trx_Hdr_ID);

		return queryBuilder.create()
				.list(I_M_HU_Trx_Hdr.class);
	}

	public void dump(final PrintStream out, final String linePrefix, final I_M_HU_Trx_Hdr trxHdr)
	{
		out.println(linePrefix
				+ "Hdr_ID=" + trxHdr.getM_HU_Trx_Hdr_ID());

		final List<I_M_HU_Trx_Line> trxLines = huTrxDAO.retrieveTrxLines(trxHdr);
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			dump(out, linePrefix + linePrefixIncrement, trxLine);
		}
	}

	public String toString(final I_M_HU_Trx_Line trxLine)
	{
		final I_M_HU_Trx_Line parentTrxLine = trxLine.getParent_HU_Trx_Line();
		return "ID=" + trxLine.getM_HU_Trx_Line_ID()
				+ ", Item=" + toStringPath(trxLine.getM_HU_Item()) // NOPMD no need for toString warnings to fire up, due to it being a custom toString
				+ ", Product=" + trxLine.getM_Product().getName()
				+ ", Qty=" + trxLine.getQty()
				+ ", Parent_ID=" + (parentTrxLine == null ? "-" : parentTrxLine.getM_HU_Trx_Line_ID())
				+ ", Table/Record_ID=" + trxLine.getAD_Table_ID() + "/" + trxLine.getRecord_ID();
	}

	public void dump(final PrintStream out, final String linePrefix, final I_M_HU_Trx_Line trxLine)
	{
		out.println(linePrefix + toString(trxLine)); // NOPMD no need for toString warnings to fire up, due to it being a custom toString
	}

	public String toStringPath(final I_M_HU_Item item)
	{
		if (item == null)
		{
			return "";
		}

		final StringBuilder path = new StringBuilder();
		final I_M_HU hui = item.getM_HU();
		if (hui != null)
		{
			path.append(toStringPath(hui)); // NOPMD no need for toString warnings to fire up, due to it being a custom toString
		}

		if (path.length() > 0)
		{
			path.append("/");
		}

		path.append(toStringName(item)); // NOPMD no need for toString warnings to fire up, due to it being a custom toString

		return path.toString();
	}

	public String toStringName(final I_M_HU_Item item)
	{
		final StringBuilder name = new StringBuilder("item");

		final String instanceName = POJOWrapper.getInstanceName(item);
		if (!Check.isEmpty(instanceName))
		{
			name.append("-").append(instanceName).append(";");
		}

		final String itemType = handlingUnitsBL.getItemType(item);
		name.append(" ItemType=").append(itemType)
				.append("; M_HU_Item_ID=").append(item.getM_HU_Item_ID())
				.append("; Qty=").append(item.getQty());

		return name.toString();
	}

	public String toStringPath(final I_M_HU hui)
	{
		if (hui == null)
		{
			return "";
		}

		final StringBuilder path = new StringBuilder();

		final I_M_HU parent = handlingUnitsDAO.retrieveParent(hui);
		if (parent != null)
		{
			path.append(toStringPath(parent)); // NOPMD no need for toString warnings to fire up, due to it being a custom toString
		}

		if (path.length() > 0)
		{
			path.append("/");
		}

		path.append(toStringName(hui)); // NOPMD no need for toString warnings to fire up, due to it being a custom toString

		return path.toString();
	}

	public String toStringName(final I_M_HU hui)
	{
		final StringBuilder name = new StringBuilder("HU_").append(Services.get(IHandlingUnitsBL.class).getPI(hui).getName());

		final String instanceName = POJOWrapper.getInstanceName(hui);
		if (!Check.isEmpty(instanceName))
		{
			name.append("_").append(instanceName).append(";");
		}

		name.append(" HUStatus=\"").append(hui.getHUStatus()).append("\"");

		if (hui.getM_Locator_ID() > 0)
		{
			name.append("-WH=").append(hui.getM_Locator().getM_Warehouse().getName()).append(";");
		}

		name.append(" M_HU_ID=").append(hui.getM_HU_ID());

		return name.toString();
	}

}
