package de.metas.handlingunits.spi.impl;

import java.math.BigDecimal;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;

import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPackingMaterialsCollector;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

/**
 * Class used to collect the packing material products from HUs
 */
public class HUPackingMaterialsCollector implements IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource>
{
	//
	// Services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

	/**
	 * List of M_HU_IDs to ADD which were already collected
	 */
	private Set<Integer> seenM_HU_IDs_ToAdd = new HashSet<>();

	/**
	 * List of M_HU_IDs to REMOVE which were already collected
	 */
	private Set<Integer> seenM_HU_IDs_ToRemove = new HashSet<>();

	/**
	 * Maps M_Product_ID to {@link HUPackingMaterialDocumentLineCandidate}s
	 */
	private final Map<Object, HUPackingMaterialDocumentLineCandidate> key2candidates = new HashMap<>();
	private int countTUs = 0;

	/**
	 *
	 */
	private final Map<HUpipToHUPackingMaterialCollectorSource, Integer> huPIPToSource = new TreeMap<>(Comparator.comparing(HUpipToHUPackingMaterialCollectorSource::getM_HU_PI_Item_Product_ID)
			.thenComparing(HUpipToHUPackingMaterialCollectorSource::getOriginalSourceID));

	public Map<HUpipToHUPackingMaterialCollectorSource, Integer> getHuPIPToSource()
	{
		return huPIPToSource;
	}

	private final IHUContext huContext;
	private HUPackingMaterialsCollector parent;
	private boolean collectIfOwnPackingMaterialsOnly = false;

	/**
	 * memorize how many TUs were set per source, in case this number is needed later in the code. Also check the aggregated HUs for LU type
	 */
	private boolean isCollectTUNumberPerOrigin = false;

	private boolean isCollectAggregatedHUs = false;

	private Comparator<HUPackingMaterialDocumentLineCandidate> candidatesSortComparator = null;

	private boolean disabled = false;
	private boolean errorIfHuIsAdded = false;

	/**
	 *
	 * @param huContext may be <code>null</code>.<br>
	 *            Provide a HU context if you need to keep track of the HU's {@link I_M_Material_Tracking#COLUMNNAME_M_Material_Tracking_ID M_Material_Tracking_ID} while collecting.
	 */
	public HUPackingMaterialsCollector(final IHUContext huContext)
	{
		this.huContext = huContext;
	}

	/** Copy constructor */
	private HUPackingMaterialsCollector(@NonNull final HUPackingMaterialsCollector other)
	{
		this.seenM_HU_IDs_ToAdd = other.seenM_HU_IDs_ToAdd;
		this.seenM_HU_IDs_ToRemove = other.seenM_HU_IDs_ToRemove;
		this.countTUs = other.countTUs;
		this.huContext = other.huContext;
		this.parent = other.parent;
		this.collectIfOwnPackingMaterialsOnly = other.collectIfOwnPackingMaterialsOnly;
		this.isCollectTUNumberPerOrigin = other.isCollectTUNumberPerOrigin;
		this.isCollectAggregatedHUs = other.isCollectAggregatedHUs;
		this.candidatesSortComparator = other.candidatesSortComparator;
		this.disabled = other.disabled;
		this.errorIfHuIsAdded = other.errorIfHuIsAdded;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("key2candidates", key2candidates)
				.add("countTUs", countTUs)
				.add("collectIfOwnPackingMaterialsOnly", collectIfOwnPackingMaterialsOnly)
				.add("parent", parent)
				.toString();
	}

	@Override
	public HUPackingMaterialsCollector setProductIdSortComparator(final Comparator<Integer> productIdsSortComparator)
	{
		candidatesSortComparator = HUPackingMaterialDocumentLineCandidate.comparatorFromProductIds(productIdsSortComparator);
		return this;
	}

	@Override
	public void releasePackingMaterialForTU(final I_M_HU tuHU, final IHUPackingMaterialCollectorSource source)
	{
		final boolean remove = false;
		addOrRemoveHU(remove, tuHU, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, source);
	}

	@Override
	public void requirePackingMaterialForTU(final I_M_HU tuHU)
	{
		final boolean remove = true;
		final IHUPackingMaterialCollectorSource source = null; // N/A
		addOrRemoveHU(remove, tuHU, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, source);
	}

	@Override
	public void releasePackingMaterialForLU(final I_M_HU luHU, final IHUPackingMaterialCollectorSource source)
	{
		final boolean remove = false;
		addOrRemoveHU(remove, luHU, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, source);
	}

	@Override
	public void requirePackingMaterialForLU(final I_M_HU luHU)
	{
		final boolean remove = true;
		final IHUPackingMaterialCollectorSource source = null; // N/A
		addOrRemoveHU(remove, luHU, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, source);
	}

	/**
	 * Add/Remove HU
	 *
	 * NOTE: this is the main method for collecting packing materials. All other helper methods are converging to this one.
	 *
	 * @param remove
	 * @param hu
	 * @param huUnitTypeOverride
	 * @param source
	 * @return true if the packing materials were collected
	 */
	private boolean addOrRemoveHU(final boolean remove,
			final I_M_HU hu,
			final String huUnitTypeOverride,
			final IHUPackingMaterialCollectorSource huPackingMaterialCollectorsource)
	{
		if (disabled)
		{
			return false;
		}

		Check.errorIf(errorIfHuIsAdded,
				"errorIfHuIsAdded=true, so addOrRemoveHU may not be called; parameters: hu={}; huUnitTypeOverride={}; huPackingMaterialCollectorsource={}",
				hu, huUnitTypeOverride, huPackingMaterialCollectorsource);

		// Make sure we are dealing with an existing handling unit
		if (hu == null)
		{
			return false;
		}
		final int huId = hu.getM_HU_ID();
		if (huId <= 0)
		{
			return false;
		}
		//
		// Make sure we were not already collected packing materials from this HU
		// In case of removal, check if the HU was already added, in which case, do nothing
		if (remove)
		{
			if (!seenM_HU_IDs_ToRemove.add(huId))
			{
				return false;
			}
		}
		// In case of adding, check if the HU was already added, in which case, do nothing
		else
		{
			if (!seenM_HU_IDs_ToAdd.add(huId))
			{
				return false;
			}
		}
		//
		// Collect only if the packing material is ours and not vendor's (08480)
		if (collectIfOwnPackingMaterialsOnly && !hu.isHUPlanningReceiptOwnerPM())
		{
			return false;
		}

		//
		// Iterate all M_HU_PackingMaterials and add their products
		final List<IPair<I_M_HU_PackingMaterial, Integer>> packingMaterialsAndQtys = handlingUnitsDAO.retrievePackingMaterialAndQtys(hu);
		for (final IPair<I_M_HU_PackingMaterial, Integer> huPackingMaterialAndQty : packingMaterialsAndQtys)
		{
			final I_M_HU_PackingMaterial huPackingMaterial = huPackingMaterialAndQty.getLeft();
			if (huPackingMaterial == null)
			{
				// note: some old HUs might have no packing material set.
				// In this case there is no product, so there is no point to go forward.
				continue;
			}
			final int productId = huPackingMaterial.getM_Product_ID();
			if (productId <= 0)
			{
				continue;
			}

			final int materialTrackingId = retrieveMaterialTrackingId(hu); // 07734
			final ArrayKey key = mkCandidateKey(huPackingMaterial, materialTrackingId, hu);
			final HUPackingMaterialDocumentLineCandidate candidate = key2candidates.computeIfAbsent(key, k -> createHUPackingMaterialDocumentLineCandidate(huPackingMaterial, materialTrackingId, hu));
			HUPackingMaterialDocumentLineCandidate innerCandidate = null;
			I_M_HU_PI_Item_Product materialItemProduct = IHandlingUnitsBL.extractPIItemProductOrNull(hu);

			// Check if the material item product does have a different packing material, and make a candidate for it too in case it does
			if (materialItemProduct != null && isCollectAggregatedHUs)
			{

				final List<I_M_HU_PackingMaterial> includedPackingMaterials = Services.get(IHUPackingMaterialDAO.class).retrievePackingMaterials(materialItemProduct);

				for (final I_M_HU_PackingMaterial includedPackingMaterial : includedPackingMaterials)
				{
					if (includedPackingMaterial != null && includedPackingMaterial.getM_HU_PackingMaterial_ID() != huPackingMaterial.getM_HU_PackingMaterial_ID())
					{
						final ArrayKey includedKey = mkCandidateKey(includedPackingMaterial, materialTrackingId, hu);
						innerCandidate = key2candidates.computeIfAbsent(includedKey, k -> createHUPackingMaterialDocumentLineCandidate(includedPackingMaterial, materialTrackingId, hu));
					}

				}
			}

			final int qty = huPackingMaterialAndQty.getRight();
			if (remove)
			{
				candidate.subtractQty(qty);

			}
			else
			{
				candidate.addSourceIfNotNull(huPackingMaterialCollectorsource);
				candidate.addQty(qty);

				if (materialItemProduct == null || huPackingMaterialCollectorsource == null)
				{
					continue;
				}

				if (materialItemProduct.getM_Product_ID() != huPackingMaterialCollectorsource.getM_Product_ID())
				{
					continue;
				}

				final HUpipToHUPackingMaterialCollectorSource currentHUPipToOrigin = new HUpipToHUPackingMaterialCollectorSource(materialItemProduct, huPackingMaterialCollectorsource.getRecord_ID());

				Integer qtyTU = huPIPToSource.get(currentHUPipToOrigin);
				if (qtyTU == null)
				{
					qtyTU = 0;
				}

				if (isCollectTUNumberPerOrigin && !handlingUnitsBL.isLoadingUnit(hu))
				{
					huPIPToSource.put(currentHUPipToOrigin, qtyTU + qty);

				}

				if (isCollectAggregatedHUs)
				{
					if (!handlingUnitsBL.isLoadingUnit(hu))
					{

						if (innerCandidate != null)
						{
							innerCandidate.addSourceIfNotNull(huPackingMaterialCollectorsource);
							innerCandidate.addQty(qty);
						}
					}

					else
					{

						if (huPackingMaterialCollectorsource.isCollectHUPipToSource())
						{

							// check for the qty in the aggregated HU (if exists)
							final I_M_HU_Item huItem = handlingUnitsDAO.retrieveAggregatedItemOrNull(hu, currentHUPipToOrigin.getHupip().getM_HU_PI_Item());

							if (huItem != null)
							{
								final BigDecimal includedQty = huItem.getQty();
								huPIPToSource.put(currentHUPipToOrigin, qtyTU + includedQty.intValueExact());

								if (innerCandidate != null && !innerCandidate.getSources().contains(huPackingMaterialCollectorsource))
								{
									innerCandidate.addSourceIfNotNull(huPackingMaterialCollectorsource);
									innerCandidate.addQty(includedQty.intValueExact());
								}
							}
						}
					}

				}

			}
		}

		//
		// Extract the TUs
		final String huUnitTypeToUse = huUnitTypeOverride == null ? handlingUnitsBL.getHU_UnitType(hu) : huUnitTypeOverride;
		final boolean aggregateHU = handlingUnitsBL.isAggregateHU(hu);
		final int countTUsAbs;
		if (aggregateHU)
		{
			// gh #640: 'hu' is a "bag" of homogeneous HUs. Take into account the number of TUs it represents.
			countTUsAbs = handlingUnitsDAO.retrieveParentItem(hu).getQty().intValueExact();
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitTypeToUse))
		{
			countTUsAbs = 1; // pure TU.. that counts ONE
		}

		else
		{
			countTUsAbs = 0;
		}

		//
		// Update the TUs counter
		if (remove)
		{
			countTUs -= countTUsAbs;
		}
		else
		{
			countTUs += countTUsAbs;
		}

		return true;
	}

	/**
	 * Blindly adds the packing materials of given PI.
	 *
	 * NOTE: mainly use this method for BLs which are about adding packing materials of user overrides.
	 *
	 * @param huPI PI for packing materials
	 * @param count how many to add
	 * @param huPackingMaterialCollectorSource
	 */
	public void addM_HU_PI(final I_M_HU_PI huPI, final int count, IHUPackingMaterialCollectorSource huPackingMaterialCollectorSource)
	{
		if (disabled)
		{
			return;
		}

		Check.errorIf(errorIfHuIsAdded,
				"errorIfHuIsAdded=true, so addM_HU_PI may not be called; parameters: huPI={}; count={}; huPackingMaterialCollectorSource={}",
				huPI, count, huPackingMaterialCollectorSource);

		if (count <= 0)
		{
			return;
		}

		final I_C_BPartner bpartner = null;
		for (final I_M_HU_PI_Item piItem : handlingUnitsDAO.retrievePIItems(huPI, bpartner))
		{
			final I_M_HU_PackingMaterial huPackingMaterial = piItem.getM_HU_PackingMaterial();
			if (huPackingMaterial == null || huPackingMaterial.getM_HU_PackingMaterial_ID() <= 0)
			{
				continue;
			}

			final int materialTrackingId = -1; // N/A
			final I_M_HU hu = null; // N/A
			final ArrayKey key = mkCandidateKey(huPackingMaterial, materialTrackingId, hu);

			final HUPackingMaterialDocumentLineCandidate candidate = key2candidates.computeIfAbsent(key, k -> createHUPackingMaterialDocumentLineCandidate(huPackingMaterial, materialTrackingId, hu));

			candidate.addSourceIfNotNull(huPackingMaterialCollectorSource);
			candidate.addQty(count);
		}

		//
		// Increase the TU counter
		final String huUnitType = handlingUnitsBL.getHU_UnitType(huPI);
		if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			countTUs += count;
		}
	}

	private ArrayKey mkCandidateKey(
			final I_M_HU_PackingMaterial huPackingMaterial,
			final int materialTrackingId,
			final I_M_HU hu)
	{
		final int productId = huPackingMaterial.getM_Product_ID();
		final int locatorId = hu == null ? -1 : hu.getM_Locator_ID();

		return Util.mkKey(
				productId <= 0 ? -1 : productId,
				locatorId <= 0 ? -1 : locatorId,
				materialTrackingId);
	}

	/**
	 * @return <code>-1</code> if we don't track attributes or if the given HU doesn't have a <code>M_Material_Tracking_ID</code> attribute.
	 */
	// 07734
	private int retrieveMaterialTrackingId(final I_M_HU hu)
	{
		if (huContext == null)
		{
			// huContext is null => don't consider the material tracking ID in our collecting efforts
			// FIXME: introduce some setter or something to make this more obvious,
			// because "don't set huContext if u don't want material tracking to be collected" it's not obvious at all!
			return -1;
		}

		// retrieve the attribute
		final AttributeId trackingAttributeId = attributeDAO.retrieveAttributeIdByValueOrNull(I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID);
		if (trackingAttributeId == null)
		{
			return -1;
		}

		final I_M_HU_Attribute huAttribute = huContext.getHUAttributeStorageFactory().getHUAttributesDAO().retrieveAttribute(hu, trackingAttributeId);
		final String materialTrackingIdStr = huAttribute != null ? huAttribute.getValue() : null;
		if(Check.isEmpty(materialTrackingIdStr, true))
		{
			return -1;
		}

		return Integer.parseInt(materialTrackingIdStr);
	}

	private HUPackingMaterialDocumentLineCandidate createHUPackingMaterialDocumentLineCandidate(
			final I_M_HU_PackingMaterial huPackingMaterial,
			final int materialTrackingId,
			final I_M_HU hu)
	{
		final I_M_Product product = IHUPackingMaterialDAO.extractProductOrNull(huPackingMaterial);

		// 07734: also include the M_Material_Tracking if one was collected.
		final I_M_Material_Tracking material_Tracking;
		if (materialTrackingId <= -1)
		{
			material_Tracking = null;
		}
		else
		{
			material_Tracking = InterfaceWrapperHelper.create(huContext.getCtx(), materialTrackingId, I_M_Material_Tracking.class, huContext.getTrxName());
		}

		final I_M_Locator locator = hu == null ? null : IHandlingUnitsBL.extractLocatorOrNull(hu);

		// hu without locator is OK sometimes (e.g. in InOutProducerFromReceiptScheduleHUTest), so we can't assert this here.
		// Check.errorIf(hu != null && hu.getM_Locator() == null, "The given hu has no locator; hu={}", hu);

		final HUPackingMaterialDocumentLineCandidate candidate = new HUPackingMaterialDocumentLineCandidate(product, material_Tracking, locator);
		return candidate;
	}

	@Override
	public void releasePackingMaterialForHURecursively(final Collection<I_M_HU> hus, final IHUPackingMaterialCollectorSource source)
	{
		if (hus == null || hus.isEmpty() || disabled)
		{
			return;
		}

		for (final I_M_HU hu : hus)
		{
			releasePackingMaterialForHURecursively(hu, source);
		}
	}

	@Override
	public void releasePackingMaterialForHURecursively(final IQueryBuilder<I_M_HU_Assignment> huAssignmentsQueryBuilder)
	{
		if (disabled)
		{
			return; // don't bother making the database query
		}

		final List<I_M_HU_Assignment> huAssignments = huAssignmentsQueryBuilder
				// Retrieve only those HUs where the assignment allows to transfer packing materials
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_IsTransferPackingMaterials, true)
				.create()
				.list();

		for (final I_M_HU_Assignment huAssignment : huAssignments)
		{
			if (seenM_HU_IDs_ToAdd.contains(huAssignment.getM_HU_ID()))
			{
				// don't retrieve again those HUs which were already added
				continue;
			}

			final Object referencedModel = TableRecordCacheLocal.getReferencedValue(huAssignment, Object.class);
			final IHUPackingMaterialCollectorSource source = HUPackingMaterialCollectorSourceFactory.fromNullable(referencedModel);

			final I_M_HU hu = huAssignment.getM_HU();
			if (hu != null)
			{
				releasePackingMaterialForHURecursively(hu, source);
			}
			addLUIfNotAlreadyAssignedElsewhere(huAssignment, source);

		}
	}

	@Override
	public void releasePackingMaterialForTUHUsRecursively(@NonNull final IQueryBuilder<I_M_HU_Assignment> tuAssignmentsQuery)
	{
		if (disabled)
		{
			return; // don't bother making the database query
		}

		final List<I_M_HU_Assignment> tuAssignments = tuAssignmentsQuery
				// Retrieve only those TUs where the assignment allows to transfer packing materials
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_IsTransferPackingMaterials, true)
				.addNotInArrayFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, seenM_HU_IDs_ToAdd) // don't retrieve again those HUs which were already added
				.create()
				.list();

		for (final I_M_HU_Assignment tuAssignment : tuAssignments)
		{
			final Object referencedModel = TableRecordCacheLocal.getReferencedValue(tuAssignment, Object.class);
			final IHUPackingMaterialCollectorSource source = HUPackingMaterialCollectorSourceFactory.fromNullable(referencedModel);

			final I_M_HU tuHU = tuAssignment.getM_TU_HU();
			if (tuHU != null)
			{
				releasePackingMaterialForHURecursively(tuHU, source);
			}

			addLUIfNotAlreadyAssignedElsewhere(tuAssignment, source);
		}
	}

	/**
	 * Collects the LU from given assignment, ONLY if it was not already assigned to a previous document line
	 *
	 * @param luAssignment
	 */
	public void addLUIfNotAlreadyAssignedElsewhere(final I_M_HU_Assignment luAssignment, final IHUPackingMaterialCollectorSource source)
	{
		final I_M_HU luHU = luAssignment.getM_LU_HU();
		if (luHU == null)
		{
			return;
		}

		// Check if the LU was already assigned elsewhere
		if (huAssignmentDAO.hasMoreLUAssigmentsForSameModelType(luAssignment))
		{
			return;
		}

		// Collect the LU, but don't to it recursively
		// because we don't want to collect the other TUs which are included in this LU
		releasePackingMaterialForLU(luHU, source);
	}

	@Override
	public void releasePackingMaterialForHURecursively(final I_M_HU hu, final IHUPackingMaterialCollectorSource source)
	{
		final boolean remove = false;
		addOrRemoveHURecursively(hu, source, remove);
	}

	@Override
	public void requirePackingMaterialForHURecursively(final I_M_HU hu)
	{
		final boolean remove = true;
		addOrRemoveHURecursively(hu, null, remove);
	}

	/**
	 * Add or Remove given HU (and recursively its children).
	 *
	 * @param hu if null, nothing is done
	 * @param source
	 * @param remove In case the boolean value is true, the HU will be removed (decremented qty); otherwise, it will be added (its qty will be incremented)
	 */
	private void addOrRemoveHURecursively(
			@Nullable final I_M_HU hu,
			final IHUPackingMaterialCollectorSource source,
			final boolean remove)
	{
		if (hu == null || disabled)
		{
			return;
		}

		final int huId = hu.getM_HU_ID();
		if (huId <= 0)
		{
			return;
		}

		new HUIterator()
				.setEnableStorageIteration(false)
				.setCtx(InterfaceWrapperHelper.getCtx(hu))
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result afterHU(final I_M_HU hu)
					{
						final String huUnitTypeOverride = null; // use the actual HU's UnitType
						addOrRemoveHU(remove, hu, huUnitTypeOverride, source);

						return Result.CONTINUE;
					}

				})
				.iterate(hu);
	}

	/**
	 * Gets how many TUs were collected.
	 *
	 * NOTE: this counter will be reset on {@link #getAndClearCandidates()}.
	 *
	 * @return how many TUs were collected.
	 */
	public int getCountTUs()
	{
		return countTUs;
	}

	/**
	 * Gets how many TUs were collected.
	 *
	 * NOTE: after calling this method the counter will be reset ZERO.
	 *
	 * @return how many TUs were collected.
	 */
	public int getAndResetCountTUs()
	{
		final int countTUsToReturn = countTUs;
		countTUs = 0;
		return countTUsToReturn;
	}

	@Override
	public List<HUPackingMaterialDocumentLineCandidate> getAndClearCandidates()
	{
		final List<HUPackingMaterialDocumentLineCandidate> candidates = new ArrayList<>(key2candidates.values());
		key2candidates.clear();
		countTUs = 0;

		// NOTE: we are not clearing seenM_HU_IDs because those HUs were already considered and their packing materials
		// were already receipt somewhere, even if not on this round
		// seenM_HU_IDs.clear();

		//
		// Sort candidates (if required)
		if (candidatesSortComparator != null)
		{
			Collections.sort(candidates, candidatesSortComparator);
		}

		return candidates;

	}

	public Map<Object, HUPackingMaterialDocumentLineCandidate> getKey2candidates()
	{
		return key2candidates;
	}

	@Override
	public void setSeenM_HU_IDs_ToAdd(final Set<Integer> seenM_HU_IDs_ToAdd)
	{
		Check.assumeNotNull(seenM_HU_IDs_ToAdd, "seenM_HU_IDs_ToAdd not null");
		Check.assume(this.seenM_HU_IDs_ToAdd.isEmpty(), "Cannot change the seen list if the old one is not empty");

		// NOTE: we are seting it directly instead of copying because we want to share the same list
		this.seenM_HU_IDs_ToAdd = seenM_HU_IDs_ToAdd;
	}

	@Override
	public void setCollectIfOwnPackingMaterialsOnly(final boolean collectIfOwnPackingMaterialsOnly)
	{
		this.collectIfOwnPackingMaterialsOnly = collectIfOwnPackingMaterialsOnly;
	}

	@Override
	public void setisCollectTUNumberPerOrigin(final boolean isCollectTUNumberPerOrigin)
	{
		this.isCollectTUNumberPerOrigin = isCollectTUNumberPerOrigin;
	}

	@Override
	public void setisCollectAggregatedHUs(final boolean isCollectAggregatedHUs)
	{
		this.isCollectAggregatedHUs = isCollectAggregatedHUs;
	}

	/**
	 * Creates a new collector starting from this point.
	 *
	 * For the new collector, collected packing materials are empty/zero but the "seen" lists will be copied.
	 *
	 * At the end, if you decide, you can merge back the new collector to this collector, by invoking {@link #mergeBackToParentAndClear()}.
	 *
	 * @return the new collector
	 */
	public HUPackingMaterialsCollector splitNew()
	{
		final HUPackingMaterialsCollector collectorNew = new HUPackingMaterialsCollector(huContext);
		collectorNew.parent = this;

		// we are just sharing the "seen"s list to prevent adding to new collector, HUs which were already added to this (aka parent) collector.
		collectorNew.seenM_HU_IDs_ToAdd = new HashSet<>(seenM_HU_IDs_ToAdd);
		collectorNew.seenM_HU_IDs_ToRemove = new HashSet<>(seenM_HU_IDs_ToRemove);

		return collectorNew;
	}

	/**
	 * Merge the collected values from this collector, merge back to it's parent. After that, the collector is cleared but the "seen" lists are kept.
	 *
	 * NOTE: we assume this collector was created with methods like {@link #splitNew()}.
	 */
	public void mergeBackToParentAndClear()
	{
		Check.assumeNotNull(parent, "parent not null");

		// Merge this back to parent
		merge(parent, this);

		// Clear collected values
		key2candidates.clear();
		countTUs = 0;

		// NOTE: we are not clearing the "seen" lists because
		// * we shall remember what we have seen, no matter what.
		// * it could be that the seen list is shared with other collectors and we don't know
	}

	/**
	 * Merge collector
	 *
	 * NOTE:
	 * <ul>
	 * <li>this method is not handling (because it can't) the case when the seen lists contain common M_HU_IDs (which could effect the number of TUs, and other counters)
	 * </ul>
	 *
	 * @param collectorTo collector in which we merge
	 * @param collectorFrom collector from which we merge
	 */
	private static final void merge(final HUPackingMaterialsCollector collectorTo, final HUPackingMaterialsCollector collectorFrom)
	{
		//
		// Add/Merge line candidates
		for (final Map.Entry<Object, HUPackingMaterialDocumentLineCandidate> key2candidate : collectorFrom.key2candidates.entrySet())
		{
			final Object key = key2candidate.getKey();
			final HUPackingMaterialDocumentLineCandidate candidateToAdd = key2candidate.getValue();

			final HUPackingMaterialDocumentLineCandidate candidateExisting = collectorTo.key2candidates.get(key);
			if (candidateExisting == null)
			{
				collectorTo.key2candidates.put(key, candidateToAdd);
			}
			else if (candidateExisting == candidateToAdd)
			{
				// same candidate, do nothing
			}
			else
			{
				candidateExisting.add(candidateToAdd);
			}
		}

		//
		// Add countTUs
		collectorTo.countTUs += collectorFrom.countTUs;

		//
		// Add seen added/removed M_HU_IDs
		collectorTo.seenM_HU_IDs_ToAdd.addAll(collectorFrom.seenM_HU_IDs_ToAdd);
		collectorTo.seenM_HU_IDs_ToRemove.addAll(collectorFrom.seenM_HU_IDs_ToRemove);
	}

	@Override
	public IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> errorIfAnyHuIsAdded()
	{
		this.errorIfHuIsAdded = true;
		return this;
	}

	@Value
	public static final class HUpipToHUPackingMaterialCollectorSource
	{
		private @NonNull final I_M_HU_PI_Item_Product hupip;
		private final int originalSourceID;

		public int getM_HU_PI_Item_Product_ID()
		{
			return hupip.getM_HU_PI_Item_Product_ID();
		}
	}

	@Override
	public IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> disable()
	{
		this.disabled = true;
		return this;

	}

	@Override
	public IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> copy()
	{
		return new HUPackingMaterialsCollector(this);
	}
}
