package de.metas.handlingunits.inout;

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.inout.impl.HUShipmentPackingMaterialLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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

public interface IHUInOutBL extends ISingletonService
{
	de.metas.handlingunits.model.I_M_InOut getById(InOutId inoutId);

	<T extends I_M_InOut> T getById(InOutId inoutId, @NonNull Class<T> type);

	I_M_InOutLine getLineById(@NonNull InOutLineId inoutLineId);

	<T extends org.compiere.model.I_M_InOutLine> List<T> retrieveLines(I_M_InOut inOut, Class<T> inoutLineClass);

	/**
	 * Create a packing material line. i.e. updates a new inout line and sets all informations from <code>candidate</code>. At the end, <code>inoutLine</code> will be saved
	 *
	 * @param inoutLine new drafted inout line which will be updated from <code>candidate</code>.
	 */
	void updatePackingMaterialInOutLine(de.metas.inout.model.I_M_InOutLine inoutLine, HUPackingMaterialDocumentLineCandidate candidate);

	/**
	 * Create additional inout lines for packing materials (TU and LU) based on the qtyTUs.
	 * <p>
	 * The lines are taken one by one, the packing materials are collected from the assigned HUs.
	 * <p>
	 * Afterwards, they are counted, and the qty TU of the current line is incremented with the new value (see {@link I_M_InOutLine#setQtyEnteredTU(java.math.BigDecimal)}.
	 * <p>
	 * <b>IMPORTANT:</b> if there is already at least one existing packing material line, then this method will do nothing. If you want the packing materials to be re-generated, please use
	 * {@link #recreatePackingMaterialLines(I_M_InOut)}.
	 */
	void createPackingMaterialLines(I_M_InOut inout);

	/**
	 * Deletes all existing {@link I_M_InOutLine}s with {@link I_M_InOutLine#COLUMNNAME_IsPackagingMaterial IsPackagingMaterial} <code>='Y'</code>, then calls
	 * {@link #createPackingMaterialLines(I_M_InOut)}.
	 */
	void recreatePackingMaterialLines(I_M_InOut inout);

	HUShipmentPackingMaterialLinesBuilder createHUShipmentPackingMaterialLinesBuilder(I_M_InOut shipment);

	/**
	 * Gets TU PI from inout line.
	 *
	 * @return TU PI or <code>null</code>
	 */
	@Nullable
	I_M_HU_PI getTU_HU_PI(I_M_InOutLine inoutLine);

	/**
	 * Destroy all HUs which are assigned to this receipt.
	 */
	void destroyHUs(I_M_InOut inout);

	/**
	 * Set shipment line's QtyEntered, QtyEnteredTU, M_HU_PI_Item_Product from calculated or from overrides, based on {@link I_M_InOutLine#isManualPackingMaterial()}.
	 */
	void updateEffectiveValues(I_M_InOutLine shipmentLine);

	IDocumentLUTUConfigurationManager createLUTUConfigurationManager(List<I_M_InOutLine> inOutLines);

	/**
	 * @return true if the given inOut is a Customer Return, false otherwise
	 */
	boolean isCustomerReturn(I_M_InOut inOut);

	/**
	 * @return true if the given inOut is a Vendor Return, false otherwise
	 */
	boolean isVendorReturn(I_M_InOut inOut);

	/**
	 * @return true if the given inOut is an empties return (e.g. of just pallets or boxes), false otherwise.
	 */
	boolean isEmptiesReturn(I_M_InOut inout);

	void setAssignedHandlingUnits(I_M_InOut inout, List<I_M_HU> hus);

	void addAssignedHandlingUnits(I_M_InOut inout, List<I_M_HU> hus);

	void setAssignedHandlingUnits(final org.compiere.model.I_M_InOutLine inoutLine, final List<I_M_HU> hus);

	void copyAssignmentsToReversal(I_M_InOut inoutRecord);

	ImmutableSetMultimap<InOutLineId, HuId> getHUIdsByInOutLineIds(@NonNull Set<InOutLineId> inoutLineIds);

	Set<HuId> getHUIdsByInOutIds(@NonNull Set<InOutId> inoutIds);

	boolean isValidHuForReturn(InOutId inOutId, HuId huId);

	void validateMandatoryOnShipmentAttributes(I_M_InOut shipment);
	/**
	 * @return true if the given inOut is a Service Repair/Service annahme, false otherwise
	 */
	boolean isServiceRepair(@NonNull final org.compiere.model.I_M_InOut inOut);
}
