/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.util;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAndPIAttributes;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class CatchWeightLoader
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
	final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final HashMap<HuId, I_M_HU> husCache = new HashMap<>();

	private final AttributeId weightNetAttributeId;

	public CatchWeightLoader()
	{
		this.weightNetAttributeId = attributeDAO.retrieveAttributeIdByValue(Weightables.ATTR_WeightNet);
	}

	public Optional<Quantity> getCatchWeight(@NonNull final PickingJobStepPickedTo pickedTo)
	{
		final ImmutableSet<HuId> huIds = pickedTo.getActualPickedHUIds();
		return getCatchWeight(huIds, pickedTo.getProductId());
	}

	private Optional<Quantity> getCatchWeight(@NonNull final Set<HuId> huIds, @NonNull final ProductId productId)
	{
		if (huIds.isEmpty())
		{
			return Optional.empty();
		}

		final UomId catchUomId = productBL.getCatchUOMId(productId).orElse(null);
		if (catchUomId == null)
		{
			return Optional.empty();
		}
		assertUomWeightable(catchUomId);

		final Collection<I_M_HU> hus = getHUByIds(huIds);
		final Collection<Quantity> weightNets = getWeightNets(hus).values();
		return uomConversionBL.sumAndConvertQuantitiesTo(weightNets, productId, catchUomId);
	}

	private Collection<I_M_HU> getHUByIds(final Set<HuId> huIds)
	{
		return CollectionUtils.getAllOrLoad(husCache, huIds, handlingUnitsBL::getByIdsReturningMap);
	}

	public StockQtyAndUOMQty extractQtys(
			final IHUContext huContext,
			final ProductId productId,
			final Quantity pickedQty,
			final I_M_HU huRecord)
	{
		final UomId stockUOMId = productBL.getStockUOMId(productId);

		final Quantity stockQty = uomConversionBL.convertQuantityTo(pickedQty, UOMConversionContext.of(productId), stockUOMId);

		final StockQtyAndUOMQty.StockQtyAndUOMQtyBuilder qtyPicked = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty);

		final UomId catchUomId = productBL.getCatchUOMId(productId).orElse(null);
		if (catchUomId != null)
		{
			assertUomWeightable(catchUomId);

			final Quantity weight = getWeightNet(huContext, huRecord);
			final Quantity catchQty = uomConversionBL.convertQuantityTo(weight, UOMConversionContext.of(productId), catchUomId);

			qtyPicked.uomQty(catchQty);
		}
		return qtyPicked.build();
	}

	@NonNull
	private static Quantity getWeightNet(final IHUContext huContext, final I_M_HU huRecord)
	{
		final IAttributeStorage attributeStorage = huContext
				.getHUAttributeStorageFactory()
				.getAttributeStorage(huRecord);

		final IWeightable weightable = Weightables.wrap(attributeStorage);
		return Quantity.of(weightable.getWeightNet(), weightable.getWeightNetUOM());
	}

	private void assertUomWeightable(@NonNull final UomId catchUomId)
	{
		final UOMType catchUOMType = uomDAO.getUOMTypeById(catchUomId);
		if (!catchUOMType.isWeight())
		{
			throw new AdempiereException("CatchUomId=" + catchUomId + " needs to be weightable");
		}
	}

	private Map<HuId, Quantity> getWeightNets(final Collection<I_M_HU> hus)
	{
		final Map<HuId, HUAndPIAttributes> attributes = huAttributesDAO.retrieveAttributesOrdered(hus);

		final HashMap<HuId, Quantity> result = new HashMap<>();
		for (Map.Entry<HuId, HUAndPIAttributes> entry : attributes.entrySet())
		{
			final HuId huId = entry.getKey();
			final HUAndPIAttributes huAndPIAttributes = entry.getValue();
			getWeightNet(huAndPIAttributes)
					.ifPresent(weightNet -> result.put(huId, weightNet));
		}

		return result;
	}

	private Optional<Quantity> getWeightNet(final HUAndPIAttributes attributes)
	{
		final I_M_HU_Attribute huAttribute = attributes.getHUAttributeById(weightNetAttributeId).orElse(null);
		if (huAttribute == null)
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Attribute piAttribute = attributes.getPiAttributes().getByAttributeIdIfExists(weightNetAttributeId).orElse(null);
		if (piAttribute == null)
		{
			return Optional.empty();
		}

		final UomId uomId = UomId.ofRepoIdOrNull(piAttribute.getC_UOM_ID());
		if (uomId == null)
		{
			return Optional.empty();
		}

		final Quantity weightNet = Quantitys.create(huAttribute.getValueNumber(), uomId);
		return Optional.of(weightNet);
	}
}
