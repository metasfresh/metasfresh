package de.metas.handlingunits.inout;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.inout.impl.HUShipmentPackingMaterialLinesBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.spi.impl.HUPackingMaterialDocumentLineCandidate;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_InOut;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

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
	/**
	 * Create a packing material line. i.e. updates a new inout line and sets all informations from <code>candidate</code>. At the end, <code>inoutLine</code> will be saved
	 *
	 * @param inoutLine new drafted inout line which will be updated from <code>candidate</code>.
	 * @param candidate
	 */
	void updatePackingMaterialInOutLine(de.metas.inout.model.I_M_InOutLine inoutLine, HUPackingMaterialDocumentLineCandidate candidate);

	/**
	 * Create additional inout lines for packing materials (TU and LU) based on the qtyTUs.
	 *
	 * The lines are taken one by one, the packing materials are collected from the assigned HUs.
	 *
	 * Afterwards, they are counted, and the qty TU of the current line is incremented with the new value (see {@link I_M_InOutLine#setQtyEnteredTU(java.math.BigDecimal)}.
	 * <p>
	 * <b>IMPORTANT:</b> if there is already at least one existing packing material line, then this method will do nothing. If you want the packing materials to be re-generated, please use
	 * {@link #recreatePackingMaterialLines(I_M_InOut)}.
	 *
	 * @param inout
	 */
	void createPackingMaterialLines(I_M_InOut inout);

	/**
	 * Deletes all existing {@link I_M_InOutLine}s with {@link I_M_InOutLine#COLUMNNAME_IsPackagingMaterial IsPackagingMaterial} <code>='Y'</code>, then calls
	 * {@link #createPackingMaterialLines(I_M_InOut)}.
	 *
	 * @param inout
	 */
	void recreatePackingMaterialLines(I_M_InOut inout);

	HUShipmentPackingMaterialLinesBuilder createHUShipmentPackingMaterialLinesBuilder(I_M_InOut shipment);

	/**
	 * Create an instance <code>of de.metas.handlingunits.inout.impl.EmptiesInOutProducer</code>
	 *
	 * @param ctx
	 * @return
	 */
	IReturnsInOutProducer createEmptiesInOutProducer(Properties ctx);

	/**
	 * Gets TU PI from inout line.
	 *
	 * @param inoutLine
	 * @return TU PI or <code>null</code>
	 */
	I_M_HU_PI getTU_HU_PI(I_M_InOutLine inoutLine);

	/**
	 * Destroy all HUs which are assigned to this receipt.
	 *
	 * @param inout
	 */
	void destroyHUs(I_M_InOut inout);

	/**
	 * Set shipment line's QtyEntered, QtyEnteredTU, M_HU_PI_Item_Product from calculated or from overrides, based on {@link I_M_InOutLine#isManualPackingMaterial()}.
	 *
	 * @param shipmentLine
	 */
	void updateEffectiveValues(I_M_InOutLine shipmentLine);

	/**
	 * Create vendor return inouts for products of precarious quality based on the details of the given HUs
	 *
	 * @param hus
	 * @param movementDate
	 * @return
	 */
	List<de.metas.handlingunits.model.I_M_InOut> createVendorReturnInOutForHUs(List<I_M_HU> hus, Timestamp movementDate);

	IDocumentLUTUConfigurationManager createLUTUConfigurationManager(List<I_M_InOutLine> inOutLines);

	IDocumentLUTUConfigurationManager createLUTUConfigurationManager(I_M_InOutLine inOutLine);

	/**
	 * @param inOut
	 * @return True if the given inOut is a Customer Return, False otherwise
	 */
	boolean isCustomerReturn(I_M_InOut inOut);

	/**
	 * @param inOut
	 * @return True if the given inOut is a Vendor Return, False otherwise
	 */
	boolean isVendorReturn(I_M_InOut inOut);

	/**
	 * Create HUs for manual customer return inout.
	 *
	 * @param customerReturn
	 * @return
	 */
	List<I_M_HU> createHUsForCustomerReturn(final de.metas.handlingunits.model.I_M_InOut customerReturn);

	/**
	 * Create Return From Vendor documents for the given HUs
	 *
	 * @param hus
	 * @return
	 */
	List<de.metas.handlingunits.model.I_M_InOut> createCustomerReturnInOutForHUs(Collection<I_M_HU> hus);

	/**
	 * Create movements to QualityIssue warehouse for the given hus
	 *
	 * @param ctx
	 * @param husToReturn
	 */
	void moveHUsForCustomerReturn(Properties ctx, List<I_M_HU> husToReturn);

	void copyAssignmentsToReversal(I_M_InOut inoutRecord);

}
