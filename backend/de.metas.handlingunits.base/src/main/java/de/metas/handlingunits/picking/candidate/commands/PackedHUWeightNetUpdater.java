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

package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public class PackedHUWeightNetUpdater
{
	@NonNull private final IUOMConversionBL uomConversionBL;
	@NonNull private final IHUContext huContext;
	@NonNull private final ProductId productId;
	@Nullable private final Quantity weightToTransfer;

	@NonNull private final HashMap<HuId, CapturedHUInfo> capturedHUInfos = new HashMap<>();

	public PackedHUWeightNetUpdater(
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IHUContext huContext,
			@NonNull final ProductId productId,
			@Nullable final Quantity weightToTransfer)
	{
		this.uomConversionBL = uomConversionBL;
		this.huContext = huContext;
		this.productId = productId;
		this.weightToTransfer = weightToTransfer;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isApplicable() {return weightToTransfer != null;}

	public void capturePickFromHUBeforeTransfer(final I_M_HU hu)
	{
		if (!isApplicable())
		{
			return;
		}

		final CapturedHUInfo capturedHUInfo = extractHUInfo(hu);
		capturedHUInfos.put(capturedHUInfo.getHuId(), capturedHUInfo);
	}

	private CapturedHUInfo extractHUInfo(final I_M_HU hu)
	{
		final IWeightable weightable = Weightables.wrap(huContext.getHUAttributeStorageFactory().getAttributeStorage(hu));
		final Quantity weightNet = Quantity.of(weightable.getWeightNet(), weightable.getWeightNetUOM());

		return CapturedHUInfo.builder()
				.huId(HuId.ofRepoId(hu.getM_HU_ID()))
				.weightNet(weightNet)
				.build();
	}

	void updatePickFromHUs(final List<I_M_HU> hus)
	{
		if (!isApplicable())
		{
			return;
		}

		// NOTE: we update only the HUs for whom we have captured info
		final ImmutableList<I_M_HU> husToUpdate = retainOnlyHUsWithCapturedInfo(hus);
		if (husToUpdate.isEmpty())
		{
			return;
		}
		else if (husToUpdate.size() > 1)
		{
			throw new AdempiereException("Updating weight of more than one pick from HUs is not supported")
					.setParameter("husToUpdate", husToUpdate);
		}

		final I_M_HU hu = husToUpdate.get(0);

		final CapturedHUInfo capturedHUInfo = getCapturedHUInfo(hu);

		final Quantity weightNetNew = capturedHUInfo.getWeightNet().subtract(weightToTransfer).toZeroIfNegative();
		setWeightNet(hu, weightNetNew);
	}

	private ImmutableList<I_M_HU> retainOnlyHUsWithCapturedInfo(final List<I_M_HU> hus)
	{
		return hus.stream()
				.filter(hu -> getCapturedHUInfoOrNull(hu) != null)
				.collect(ImmutableList.toImmutableList());
	}

	private CapturedHUInfo getCapturedHUInfo(@NonNull final I_M_HU hu)
	{
		return Check.assumeNotNull(getCapturedHUInfoOrNull(hu), "capturedHUInfos contains {}: {}", hu, capturedHUInfos);
	}

	private CapturedHUInfo getCapturedHUInfoOrNull(@NonNull final I_M_HU hu)
	{
		return capturedHUInfos.get(HuId.ofRepoId(hu.getM_HU_ID()));
	}

	public void updatePackToHU(@NonNull final I_M_HU hu)
	{
		updatePickFromHUs(ImmutableList.of(hu));
	}

	public void updatePackToHUs(@NonNull final List<I_M_HU> hus)
	{
		if (!isApplicable())
		{
			return;
		}

		if (hus.isEmpty())
		{
			return;
		}

		if (hus.size() == 1)
		{
			final I_M_HU hu = hus.get(0);
			setWeightNet(hu, weightToTransfer);
		}
		else
		{
			throw new AdempiereException("Updating weight of more than than one packed HU is not supported")
					.setParameter("hus", hus);
		}
	}

	private void setWeightNet(@NonNull final I_M_HU hu, @NonNull final Quantity weightNet)
	{
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		final IWeightable weightable = Weightables.wrap(huAttributes);

		final Quantity catchWeightConv = uomConversionBL.convertQuantityTo(weightNet, productId, weightable.getWeightNetUOM());
		weightable.setWeightNet(catchWeightConv.toBigDecimal());
	}

	//
	//
	//
	@Value
	@Builder
	private static class CapturedHUInfo
	{
		@NonNull HuId huId;
		@NonNull Quantity weightNet;
	}
}
