package de.metas.materialtracking.spi.impl;

import java.util.Objects;

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

import org.compiere.model.I_C_Invoice_Detail;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.materialtracking.impl.PlainHandlingUnitsInfo;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Default implementation of {@link IHandlingUnitsInfoFactory} which does nothing.
 *
 * @author tsa
 *
 */
public class HandlingUnitsInfoFactory implements IHandlingUnitsInfoFactory
{
	/**
	 * @return <code>null</code>
	 */
	@Override
	public IHandlingUnitsInfo createFromModel(final Object model)
	{
		return null;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void updateInvoiceDetail(final I_C_Invoice_Detail invoiceDetail, final IHandlingUnitsInfo handlingUnitsInfo)
	{
		// nothing
	}

	/**
	 * @return and ad-hoc implementation that actually works; TODO: fuse with the one in de.metas.materialtracking.
	 */
	@Override
	public IHandlingUnitsInfoWritableQty createHUInfoWritableQty(@NonNull final IHandlingUnitsInfo wrappedHUInfo)
	{
		return new IHandlingUnitsInfoWritableQty()
		{
			private int qtyTU = wrappedHUInfo.getQtyTU();

			@Override
			public String getTUName()
			{
				return wrappedHUInfo.getTUName();
			}

			@Override
			public int getQtyTU()
			{
				return qtyTU;
			}

			@Override
			public IHandlingUnitsInfo add(@NonNull final IHandlingUnitsInfo infoToAdd)
			{
				Check.assume(Objects.equals(infoToAdd.getTUName(), this.getTUName()), "infoToAdd {} has a TUName that differs from ours {}", infoToAdd, this);

				return new PlainHandlingUnitsInfo(
						wrappedHUInfo.getTUName(),
						wrappedHUInfo.getQtyTU() + infoToAdd.getQtyTU());
			}

			@Override
			public void setQtyTU(int qtyTU)
			{
				this.qtyTU = qtyTU;
			}

			@Override
			public String toString()
			{
				return ObjectUtils.toString(this);
			}
		};
	}
}
