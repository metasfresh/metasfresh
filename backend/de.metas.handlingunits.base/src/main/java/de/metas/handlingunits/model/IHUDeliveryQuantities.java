package de.metas.handlingunits.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public interface IHUDeliveryQuantities
{
	// @formatter:off
	public void setQtyDelivered_TU (java.math.BigDecimal QtyDelivered_TU);
	public java.math.BigDecimal getQtyDelivered_TU();
    public static final String COLUMNNAME_QtyDelivered_TU = "QtyDelivered_TU";
    // @formatter:on

	// @formatter:off
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);
	public java.math.BigDecimal getQtyOrdered_TU();
    public static final String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";
    // @formatter:on

	// @formatter:off
	public void setQtyDelivered_LU (java.math.BigDecimal QtyDelivered_LU);
	public java.math.BigDecimal getQtyDelivered_LU();
    public static final String COLUMNNAME_QtyDelivered_LU = "QtyDelivered_LU";
    // @formatter:on

	// @formatter:off
	public void setQtyOrdered_LU (java.math.BigDecimal QtyOrdered_LU);
	public java.math.BigDecimal getQtyOrdered_LU();
    public static final String COLUMNNAME_QtyOrdered_LU = "QtyOrdered_LU";
    // @formatter:on
}
