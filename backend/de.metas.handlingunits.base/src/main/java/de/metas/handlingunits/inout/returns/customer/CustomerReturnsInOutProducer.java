/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns.customer;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.inout.returns.AbstractReturnsInOutProducer;
import de.metas.handlingunits.inout.returns.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector;
import de.metas.handlingunits.spi.impl.HUPackingMaterialsCollector.HUpipToHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.spi.impl.InOutLineHUPackingMaterialCollectorSource;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

class CustomerReturnsInOutProducer extends AbstractReturnsInOutProducer
{

	public static CustomerReturnsInOutProducer newInstance()
	{
		return new CustomerReturnsInOutProducer();
	}

	/**
	 * Builder for lines with products that are not packing materials
	 */
	private final CustomerReturnsInOutLinesBuilder inoutLinesBuilder = CustomerReturnsInOutLinesBuilder.newBuilder(inoutRef);

	/**
	 * Builder for packing material lines
	 */
	private final EmptiesInOutLinesProducer packingMaterialInoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);

	// services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUPackingMaterialDAO huPackingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);

	/**
	 * List of handling units that have to be returned from customer
	 */
	private final Set<HUToReturn> _husToReturn = new TreeSet<>(Comparator.comparing(HUToReturn::getM_HU_ID).thenComparing(HUToReturn::getOriginalShipmentLineId));

	private final Map<HUpipToHUPackingMaterialCollectorSource, Integer> huPIPToInOutLines = new TreeMap<>(Comparator.comparing(HUpipToHUPackingMaterialCollectorSource::getM_HU_PI_Item_Product_ID)
			.thenComparing(HUpipToHUPackingMaterialCollectorSource::getOriginalSourceID));

	Map<Object, Collection<HUPackingMaterialDocumentLineCandidate>> pmCandidates = new HashMap<>();

	public CustomerReturnsInOutProducer()
	{
		super(Env.getCtx());
	}

	@Override
	public I_M_InOut create() {return InterfaceWrapperHelper.create(super.create(), I_M_InOut.class);}

	@Override
	protected void createLines()
	{
		final Map<ClientAndOrgId, IHUContext> clientOrg2huContext = new HashMap<>();

		for (final HUToReturn huToReturnInfo : getHUsToReturn())
		{
			// we know for sure the huAssignments are for inoutlines
			final I_M_InOutLine originalShipmentLine = InterfaceWrapperHelper.create(getCtx(), huToReturnInfo.getOriginalShipmentLineId().getRepoId(), I_M_InOutLine.class, ITrx.TRXNAME_None);

			// make sure to have the right client and org IDs each time
			final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(originalShipmentLine.getAD_Client_ID(), originalShipmentLine.getAD_Org_ID());
			final IHUContext huContext = clientOrg2huContext.computeIfAbsent(clientAndOrgId, key -> handlingUnitsBL.createMutableHUContext(getCtx(), key));

			final HUPackingMaterialsCollector collector = new HUPackingMaterialsCollector(huContext);
			collector.setisCollectTUNumberPerOrigin(true);
			collector.setisCollectAggregatedHUs(true);

			final I_M_HU hu = huToReturnInfo.getHu();

			final InOutLineHUPackingMaterialCollectorSource inOutLineSource = InOutLineHUPackingMaterialCollectorSource.builder()
					.inoutLine(originalShipmentLine)
					.collectHUPipToSource(false)
					.build();
			collector.releasePackingMaterialForHURecursively(hu, inOutLineSource);

			// Create product (non-packing material) lines
			{
				// Iterate the product storages of this HU and create/update the inout lines
				final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();
				final IHUStorage huStorage = huStorageFactory.getStorage(hu);
				final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
				for (final IHUProductStorage productStorage : productStorages)
				{
					inoutLinesBuilder.addHUProductStorage(productStorage, originalShipmentLine);
				}
			}

			final Map<HUpipToHUPackingMaterialCollectorSource, Integer> huPIPToOriginInOutLinesMap = collector.getHuPIPToSource();
			final Map<Object, HUPackingMaterialDocumentLineCandidate> key2candidates = collector.getKey2candidates();

			for (final Entry<Object, HUPackingMaterialDocumentLineCandidate> entry : key2candidates.entrySet())
			{
				Collection<HUPackingMaterialDocumentLineCandidate> list = pmCandidates.get(entry.getKey());

				if (list == null)
				{
					// set = new TreeSet<HUPackingMaterialDocumentLineCandidate>(Comparator.comparing(HUPackingMaterialDocumentLineCandidate::getM_Product_ID));
					// list if customer return from HU
					if (_manualReturnInOut == null)
					{
						list = new ArrayList<>();
					}

					// set if manual customer return
					else
					{
						list = new HashSet<>();
						// new TreeSet<HUPackingMaterialDocumentLineCandidate>(Comparator.comparing(HUPackingMaterialDocumentLineCandidate::getM_Product_ID));
					}
					pmCandidates.put(entry.getKey(), list);
				}

				list.add(entry.getValue());
			}

			for (final HUpipToHUPackingMaterialCollectorSource key : huPIPToOriginInOutLinesMap.keySet())
			{
				final int newQtyTU = huPIPToOriginInOutLinesMap.get(key);

				huPIPToInOutLines.merge(key, newQtyTU, Integer::sum);
			}

			huSnapshotProducer.addModel(hu);

		}

		for (final Object key : pmCandidates.keySet())
		{
			for (final HUPackingMaterialDocumentLineCandidate pmCandidate : pmCandidates.get(key))
			{

				final I_M_HU_PackingMaterial packingMaterial = huPackingMaterialDAO.retrivePackingMaterialOfProduct(pmCandidate.getM_Product());

				addPackingMaterial(packingMaterial, pmCandidate.getQty().intValueExact());
			}
		}
		// Create the packing material lines that were prepared
		packingMaterialInoutLinesBuilder.create();

		// update the qtyTU and M_HU_PI_Item_Product in the newly created lines
		for (final I_M_InOutLine newInOutLine : inoutLinesBuilder.getInOutLines())
		{
			final de.metas.inout.model.I_M_InOutLine returnOriginInOutLine = newInOutLine.getReturn_Origin_InOutLine();

			if (returnOriginInOutLine == null)
			{
				continue;
			}

			for (final HUpipToHUPackingMaterialCollectorSource huPipToInOutLine : huPIPToInOutLines.keySet())
			{
				if (huPipToInOutLine.getOriginalSourceID() == returnOriginInOutLine.getM_InOutLine_ID())
				{
					final Integer qtyTU = huPIPToInOutLines.get(huPipToInOutLine);
					if (qtyTU == null)
					{
						continue;
					}

					final BigDecimal qtyEnteredTU = newInOutLine.getQtyEnteredTU() == null ? BigDecimal.ZERO : newInOutLine.getQtyEnteredTU();
					newInOutLine.setQtyEnteredTU(new BigDecimal(qtyTU).add(qtyEnteredTU));
					newInOutLine.setM_HU_PI_Item_Product(huPipToInOutLine.getHupip());
				}
			}

			InterfaceWrapperHelper.save(newInOutLine);

		}
	}

	@Override
	protected void afterInOutProcessed(final org.compiere.model.I_M_InOut inout)
	{
		huInOutBL.setAssignedHandlingUnits(inout, getHUsReturned());
	}

	@Override
	public boolean isEmpty()
	{
		// only empty if there are no product lines.
		return inoutLinesBuilder.isEmpty();
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
		return _husToReturn.stream()
				.map(HUToReturn::getHu)
				.collect(Collectors.toList());
	}

	public void addHUToReturn(@NonNull final I_M_HU hu, @NonNull final InOutLineId originalShipmentLineId)
	{
		_husToReturn.add(new HUToReturn(hu, originalShipmentLineId));
	}

	@Value
	private static class HUToReturn
	{
		@NonNull I_M_HU hu;
		@NonNull InOutLineId originalShipmentLineId;

		private int getM_HU_ID()
		{
			return hu.getM_HU_ID();
		}
	}
}
