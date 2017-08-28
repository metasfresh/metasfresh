package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.Preconditions;

import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialsCollector;
import de.metas.inoutcandidate.spi.impl.HUPackingMaterialsCollector.HUpipToInOutLine;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Producer for vendor returns.
 * Used when some products were received from a vendor and it was decided they are not respecting the quality standards they can be sent back to the vendor.
 *
 * Introduced in #1062
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class VendorReturnsInOutProducer extends AbstractReturnsInOutProducer
{
	
	
	public static final VendorReturnsInOutProducer newInstance()
	{
		return new VendorReturnsInOutProducer();
	}

	private HUPackingMaterialsCollector collector = null;

	/**
	 * Builder for lines with products that are not packing materials
	 */
	private final VendorReturnsInOutLinesBuilder inoutLinesBuilder = VendorReturnsInOutLinesBuilder.newBuilder(inoutRef);

	/**
	 * Builder for packing material lines
	 */
	private final EmptiesInOutLinesProducer packingMaterialInoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);

	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUPackingMaterialDAO huPackingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);

	/**
	 * List of handling units that have to be returned to vendor
	 */
	private final Set<HUToReturn> _husToReturn = new TreeSet<>(Comparator.comparing(HUToReturn::getM_HU_ID)
			.thenComparing(HUToReturn::getOriginalReceiptInOutLineId));

	private final Map<HUpipToInOutLine, Integer> huPIPToInOutLines = new TreeMap<>(Comparator.comparing(HUpipToInOutLine::getM_HU_PI_Item_Product_ID)
			.thenComparing(HUpipToInOutLine::getOriginalInOutLineID));

	private Map<ArrayKey, HUPackingMaterialDocumentLineCandidate> pmCandidates = new HashMap<>();

	public Map<HUpipToInOutLine, Integer> getHuPIPToInOutLines()
	{
		return huPIPToInOutLines;
	}

	public VendorReturnsInOutProducer()
	{
		super(Env.getCtx());
	}

	@Override
	protected void createLines()
	{

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(getCtx());

		for (final HUToReturn huToReturnInfo : getHUsToReturn())
		{
			collector = new HUPackingMaterialsCollector(huContext);
			collector.setisCollectTUNumberPerOrigin(true);
			collector.setisCollectAggregatedHUs(true);
			final I_M_HU hu = huToReturnInfo.getHu();
			
		

			// we know for sure the huAssignments are for inoutlines
			final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(getCtx(), huToReturnInfo.getOriginalReceiptInOutLineId(), I_M_InOutLine.class, ITrx.TRXNAME_None);

			collector.addHURecursively(hu, inOutLine);

			// Create product (non-packing material) lines
			{
				// Iterate the product storages of this HU and create/update the inout lines
				final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();
				final IHUStorage huStorage = huStorageFactory.getStorage(hu);
				final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
				for (final IHUProductStorage productStorage : productStorages)
				{
					inoutLinesBuilder.addHUProductStorage(productStorage, inOutLine);
				}
			}
			final Map<HUpipToInOutLine, Integer> huPIPToOriginInOutLinesMap = collector.getHuPIPToInOutLine();
			final Map<Object, HUPackingMaterialDocumentLineCandidate> key2candidates = collector.getKey2candidates();

			for (final Entry<Object, HUPackingMaterialDocumentLineCandidate> entry : key2candidates.entrySet())
			{
				final Object entryKey = entry.getKey();

				final ArrayKey huArrayKey = Util.mkKey(
						entryKey,
						hu.getM_HU_ID());

				pmCandidates.put(huArrayKey, entry.getValue());
			}

			huPIPToInOutLines.putAll(huPIPToOriginInOutLinesMap);
			
			huSnapshotProducer.addModel(hu);
		}

		for (final HUPackingMaterialDocumentLineCandidate pmCandidate : pmCandidates.values())
		{
			final I_M_HU_PackingMaterial packingMaterial = huPackingMaterialDAO.retrivePackingMaterialOfProduct(pmCandidate.getM_Product());

			addPackingMaterial(packingMaterial, pmCandidate.getQty().intValueExact());
		}
		// Create the packing material lines that were prepared
		packingMaterialInoutLinesBuilder.create();

		final Map<ArrayKey, I_M_InOutLine> newInOutLinesMap = inoutLinesBuilder.get_inOutLines();

		// update the qtyTU and M_HU_PI_Item_Product in the newly created lines
		for (final ArrayKey key : newInOutLinesMap.keySet())
		{
			final I_M_InOutLine newInOutLine = newInOutLinesMap.get(key);

			de.metas.inout.model.I_M_InOutLine returnOriginInOutLine = newInOutLine.getReturn_Origin_InOutLine();

			if (returnOriginInOutLine == null)
			{
				continue;
			}

			for (HUpipToInOutLine huPipToInOutLine : huPIPToInOutLines.keySet())
			{
				if (huPipToInOutLine.getOriginalInOutLineID() == returnOriginInOutLine.getM_InOutLine_ID())
				{
					final Integer qtyTU = huPIPToInOutLines.get(huPipToInOutLine);
					if (qtyTU == null)
					{
						continue;
					}
					newInOutLine.setQtyEnteredTU(new BigDecimal(qtyTU));
					newInOutLine.setM_HU_PI_Item_Product(huPipToInOutLine.getHupip());
				}
			}

			InterfaceWrapperHelper.save(newInOutLine);

		}
	}

	@Override
	protected void afterInOutProcessed(final I_M_InOut inout)
	{
		huAssignmentBL.setAssignedHandlingUnits(inout, getHUsReturned(), ITrx.TRXNAME_ThreadInherited);
		
		createHUSnapshots();
	}

	@Override
	public boolean isEmpty()
	{
		// only empty if there are no product lines.
		return inoutLinesBuilder.isEmpty();
	}

	@Override
	protected int getReturnsDocTypeId(final String docBaseType, final boolean isSOTrx, final int adClientId, final int adOrgId)
	{
		// in the case of returns the docSubType is null
		final String docSubType = IDocTypeDAO.DOCSUBTYPE_NONE;

		final I_C_DocType returnsDocType = Services.get(IDocTypeDAO.class)
				.getDocTypeOrNullForSOTrx(
						Env.getCtx() // ctx
						, docBaseType // doc basetype
						, docSubType // doc subtype
						, isSOTrx // isSOTrx
						, adClientId // client
						, adOrgId // org
						, ITrx.TRXNAME_None // trx
		);

		return returnsDocType == null ? -1 : returnsDocType.getC_DocType_ID();
	}

	@Override
	public IReturnsInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		packingMaterialInoutLinesBuilder.addSource(packingMaterial, qty);
		return this;
	}

	private Set<HUToReturn> getHUsToReturn()
	{
		Check.assumeNotEmpty(_husToReturn, "husToReturn is not empty");
		return _husToReturn;
	}

	public List<I_M_HU> getHUsReturned()
	{
		return _husToReturn.stream().map(HUToReturn::getHu).collect(Collectors.toList());
	}

	public VendorReturnsInOutProducer addHUToReturn(@NonNull final I_M_HU hu, final int originalReceiptInOutLineId)
	{
		Preconditions.checkArgument(originalReceiptInOutLineId > 0, "originalReceiptInOutLineId > 0");
		_husToReturn.add(new HUToReturn(hu, originalReceiptInOutLineId));
		return this;
	}

	@Value
	private static final class HUToReturn
	{
		private @NonNull final I_M_HU hu;
		private final int originalReceiptInOutLineId;

		private int getM_HU_ID()
		{
			return hu.getM_HU_ID();
		}
	}
}
