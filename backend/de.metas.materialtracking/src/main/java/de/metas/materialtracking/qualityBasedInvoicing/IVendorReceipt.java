package de.metas.materialtracking.qualityBasedInvoicing;

/*
 * #%L
 * de.metas.materialtracking
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

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nonnull;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.IHandlingUnitsInfo;

/**
 * Material receipt from Vendor.
 *
 * Contains informations about vendor material receipt. These informations will be used a reference when projected quantities will be calculated.
 *
 * @param <T> the type of the instances to add using the {@link #add(Object)} method and to be returned by {@link #getModels()}.
 * @author tsa
 */
public interface IVendorReceipt<T>
{
	/**
	 * @return received product
	 */
	@Nonnull
	I_M_Product getM_Product();

	/**
	 * @return the full qty that we received from the farmer/vendor
	 */
	@Nonnull
	BigDecimal getQtyReceived();

	/**
	 * @return UOM of {@link #getQtyReceived()}
	 */
	@Nonnull
	I_C_UOM getQtyReceivedUOM();

	/**
	 * @return handling units infos about received quantity
	 */
	IHandlingUnitsInfo getHandlingUnitsInfo();

	/**
	 * Add another model. Needs to be consistent with previously added models.
	 *
	 * @param model
	 */
	void add(T model);

	/**
	 * Return the records that were added, see {@link #add(Object)}.
	 *
	 * @return
	 */
	List<T> getModels();

	/**
	 *
	 * @return the price list version that was valid while this vendor receipt happened.
	 */
	I_M_PriceList_Version getPLV();
}
