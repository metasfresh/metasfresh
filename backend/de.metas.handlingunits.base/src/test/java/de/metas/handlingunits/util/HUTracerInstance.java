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

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.impl.HUAttributesDAO;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationEntry;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.project.ProjectId;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HUTracerInstance
{
	// Services
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final HUQRCodesRepository huQRCodeRepository = new HUQRCodesRepository();
	private final IHUTrxDAO huTrxDAO = Services.get(IHUTrxDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// Params
	private final IHUContext huContext;
	private final String linePrefixIncrement = "  ";

	private boolean dumpAttributes = false;
	private boolean dumpItemStorage = false;
	private HUReservationService huReservationService;

	public HUTracerInstance()
	{
		huContext = null;
	}

	public HUTracerInstance dumpAttributes(final boolean dumpAttributes)
	{
		this.dumpAttributes = dumpAttributes;
		return this;
	}

	public HUTracerInstance dumpItemStorage(boolean dumpItemStorage)
	{
		this.dumpItemStorage = dumpItemStorage;
		return this;
	}

	public HUTracerInstance dumpHUReservations(@NonNull final HUReservationService huReservationService)
	{
		this.huReservationService = huReservationService;
		return this;
	}

	private IHUStorageDAO getHUStorageDAO()
	{
		final IHUStorageFactory storageFactory = getHUStorageFactory();
		return storageFactory.getHUStorageDAO();
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
		final List<I_M_HU> hus = retrieveTopLevelHUs();
		dump(hus);
	}

	public void dumpAllHUs(final String title)
	{
		printTitle(title);
		dumpAllHUs();
	}

	public Object dumpAllHUsToJson()
	{
		final List<I_M_HU> hus = retrieveTopLevelHUs();
		final String str = toString(out -> dump(out, "", hus));

		final List<String> lines = Splitter.on("\n").splitToList(str.trim());
		final ImmutableMap.Builder<Integer, String> result = ImmutableMap.builder();
		for (int i = 0, size = lines.size(); i < size; i++)
		{
			result.put(i + 1, lines.get(i));
		}

		return result.build();
	}

	private static String toString(final Consumer<PrintStream> consumer)
	{
		try
		{
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8.name()))
			{
				consumer.accept(ps);
			}

			return baos.toString(StandardCharsets.UTF_8.name());
		}
		catch (Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private List<I_M_HU> retrieveTopLevelHUs()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_HU.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU.COLUMNNAME_M_HU_Item_Parent_ID, null)
				.create()
				.list(I_M_HU.class);
	}

	public void dump(final List<I_M_HU> hus)
	{
		final PrintStream out = System.out;
		dump(out, "", hus);
		out.flush();
	}

	private void dump(final PrintStream out, final String linePrefix, final List<I_M_HU> hus)
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

	public void dump(final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		dump(hu);
	}

	public void dump(final I_M_HU hu)
	{
		final String linePrefix = "";
		dump(System.out, linePrefix, hu, -1, -1);
	}

	public void dump(final String title, final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		dump(title, hu);
	}

	public void dump(final String title, final I_M_HU hu)
	{
		printTitle(title);
		dump(hu);
	}

	private void printTitle(final String title)
	{
		System.out.println("==============[ " + title + " ]==================================");
	}

	private void dump(final PrintStream out, final String linePrefix, final I_M_HU hu, final int index, final int lastIndex)
	{
		//
		// HU Info
		{
			out.append(linePrefix);

			if (index > 0 && lastIndex != 1)
			{
				out.append("").append(String.valueOf(index));
				if (lastIndex > 0)
				{
					out.append("/").append(String.valueOf(lastIndex));
				}
				out.append(". ");
			}

			out.append(toStringName(hu));
			out.append(" [").append(toHUStorageString(hu)).append("]");

			//
			// HU Reservations
			final String huReservations = toHUReservationsString(hu);
			if (Check.isNotBlank(huReservations))
			{
				out.append(" [").append(huReservations).append("]");
			}

			//
			// QR Code
			huQRCodeRepository.getFirstQRCodeByHuId(HuId.ofRepoId(hu.getM_HU_ID()))
					.map(HUQRCode::toRenderedJson)
					.ifPresent(qrCode -> out.append(" [QR ...").append(qrCode.getDisplayable()).append("]"));

			out.append("\n");
		}

		final String linePrefix2 = linePrefix + linePrefixIncrement;

		//
		// HU Attributes
		if (dumpAttributes)
		{
			final IHUAttributesDAO huAttributesDAO = getHUAttributesDAO();
			final List<I_M_HU_Attribute> attrs = huAttributesDAO.retrieveAttributesOrdered(hu).getHuAttributes();
			if (!attrs.isEmpty())
			{
				out.append(linePrefix2).append("Attributes: \n");
				for (final I_M_HU_Attribute attr : attrs)
				{
					out.println(linePrefix2 + linePrefixIncrement + toString(attr));
				}
			}
		}

		//
		// HU Items (+included HUs)
		final List<I_M_HU_Item> items = handlingUnitsDAO.retrieveItems(hu);
		for (final I_M_HU_Item item : items)
		{
			dump(out, linePrefix2, item);
		}
	}

	private String toHUStorageString(final I_M_HU hu)
	{
		final IHUStorageDAO storageDAO = getHUStorageDAO();
		final List<I_M_HU_Storage> storages = storageDAO.retrieveStorages(hu);
		if (!storages.isEmpty())
		{
			return storages.stream().map(this::toString).collect(Collectors.joining(", "));
		}
		else
		{
			return "empty storage";
		}
	}

	private String toHUReservationsString(final I_M_HU hu)
	{
		if (huReservationService == null)
		{
			return "";
		}
		else if (handlingUnitsBL.isVirtual(hu))
		{
			final HuId vhuId = HuId.ofRepoId(hu.getM_HU_ID());
			return huReservationService.getEntriesByVHUIds(ImmutableSet.of(vhuId))
					.stream()
					.map(HUTracerInstance::toString)
					.collect(Collectors.joining(", "));
		}
		else
		{
			return "";
		}
	}

	private static String toString(final HUReservationEntry huReservationEntry)
	{
		return toString(huReservationEntry.getDocumentRef());
	}

	private static String toString(final HUReservationDocRef documentRef)
	{
		return documentRef.map(new HUReservationDocRef.CaseMappingFunction<String>()
		{
			@Override
			public String salesOrderLineId(@NonNull final OrderLineId salesOrderLineId) {return salesOrderLineId.toString();}

			@Override
			public String projectId(@NonNull final ProjectId projectId) {return projectId.toString();}

			@Override
			public String pickingJobStepId(@NonNull final PickingJobStepId pickingJobStepId) {return pickingJobStepId.toString();}

			@Override
			public String ddOrderLineId(@NonNull final DDOrderLineId ddOrderLineId) {return ddOrderLineId.toString();}
		});
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

		return attrName
				+ ":"
				+ huAttr.getValue() + "(S)"
				+ "/"
				+ huAttr.getValueNumber() + "(N)"
				+ ", "
				+ "Seed: "
				+ huAttr.getValueInitial() + "(S)"
				+ "/"
				+ huAttr.getValueNumberInitial() + "(N)";
	}

	private String toString(final I_M_HU_Storage storage)
	{
		final I_M_Product storageProduct = productDAO.getById(storage.getM_Product_ID());
		final String productStr = storageProduct.getName();
		final BigDecimal qty = storage.getQty();
		final I_C_UOM uom = IHUStorageBL.extractUOM(storage);
		final String uomStr = uom.getUOMSymbol();
		return "" + productStr + " x " + qty + " " + uomStr;
	}

	private void dump(final PrintStream out, final String linePrefix, final I_M_HU_Item item)
	{
		final IHUStorageFactory storageFactory = getHUStorageFactory();
		final IHUItemStorage storage = storageFactory.getStorage(item);

		out.append(linePrefix).append(toStringName(item));

		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(item);
		if (!includedHUs.isEmpty())
		{
			out.append(" (").append(String.valueOf(includedHUs.size())).append(" included HUs)");
		}

		if (storage.getHUCapacity() > 0)
		{
			out.append(", HU_Qty=").append(String.valueOf(storage.getHUCount())).append("/").append(String.valueOf(storage.getHUCapacity()));
		}
		out.append("\n");

		//
		// Product level storages
		if (dumpItemStorage)
		{
			final List<IProductStorage> productStorages = storage.getProductStorages(SystemTime.asZonedDateTimeAtStartOfDay());
			for (final IProductStorage productStorage : productStorages)
			{
				dump(out, linePrefix + linePrefixIncrement, productStorage);
			}
		}

		//
		// Included HUs
		if (!includedHUs.isEmpty())
		{
			dump(out, linePrefix + linePrefixIncrement, includedHUs);
		}
	}

	private void dump(final PrintStream out, final String linePrefix, final IProductStorage productStorage)
	{
		out.println(linePrefix
				+ "S: "
				+ Services.get(IProductBL.class).getProductValueAndName(productStorage.getProductId())
				+ " x " + productStorage.getQty());
	}

	public void dumpTransactions()
	{
		final PrintStream out = System.out;

		final List<I_M_HU_Trx_Hdr> trxHdrs = retrieveAllTrxHdr();
		if (trxHdrs.isEmpty())
		{
			return;
		}

		out.println("\nTransactions: ");
		for (final I_M_HU_Trx_Hdr trxHdr : trxHdrs)
		{
			dump(out, trxHdr);
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

	private void dump(final PrintStream out, final I_M_HU_Trx_Hdr trxHdr)
	{
		out.println("Hdr_ID=" + trxHdr.getM_HU_Trx_Hdr_ID());

		final List<I_M_HU_Trx_Line> trxLines = huTrxDAO.retrieveTrxLines(trxHdr);
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			dump(out, linePrefixIncrement, trxLine);
		}
	}

	public String toString(final I_M_HU_Trx_Line trxLine)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(trxLine.getM_Product_ID());

		final I_M_HU_Trx_Line parentTrxLine = trxLine.getParent_HU_Trx_Line();
		return "ID=" + trxLine.getM_HU_Trx_Line_ID()
				+ ", Item=" + toStringPath(trxLine.getM_HU_Item()) // NOPMD no need for toString warnings to fire up, due to it being a custom toString
				+ ", Product=" + product.getName()
				+ ", Qty=" + trxLine.getQty()
				+ ", Parent_ID=" + (parentTrxLine == null ? "-" : parentTrxLine.getM_HU_Trx_Line_ID())
				+ ", Table/Record_ID=" + trxLine.getAD_Table_ID() + "/" + trxLine.getRecord_ID();
	}

	@SuppressWarnings("SameParameterValue")
	private void dump(final PrintStream out, final String linePrefix, final I_M_HU_Trx_Line trxLine)
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
		final StringBuilder name = new StringBuilder();

		final String itemType = handlingUnitsBL.getItemType(item);
		name.append("Item:").append(toItemTypeDisplayName(itemType));
		//name.append("; M_HU_Item_ID=").append(item.getM_HU_Item_ID()) // usually this is not relevant

		if (item.getQty().signum() != 0)
		{
			name.append(", Qty=").append(item.getQty());
		}

		return name.toString();
	}

	private static String toItemTypeDisplayName(final String itemType)
	{
		if (X_M_HU_Item.ITEMTYPE_Material.equals(itemType))
		{
			return "Material";
		}
		else if (X_M_HU_Item.ITEMTYPE_HandlingUnit.equals(itemType))
		{
			return "IncludedHU";
		}
		else if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(itemType))
		{
			return "Aggregate";
		}
		else
		{
			return "" + itemType;
		}
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
		final StringBuilder name = new StringBuilder("HU[" + hui.getM_HU_ID() + "]: ");

		final I_M_HU_PI pi = handlingUnitsBL.getPI(hui);
		if (pi != null)
		{
			name.append(pi.getName());
		}

		final String instanceName = POJOWrapper.getInstanceName(hui);
		if (!Check.isEmpty(instanceName))
		{
			name.append(" [").append(instanceName).append("]");
		}

		name.append(" HUStatus=").append(hui.getHUStatus());

		if (hui.getM_Locator_ID() > 0)
		{
			name.append(" WH=").append(IHandlingUnitsBL.extractWarehouse(hui).getName());
		}

		return name.toString();
	}

}
