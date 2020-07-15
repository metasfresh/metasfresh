package de.metas.handlingunits.client.terminal.lutuconfig.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Collection;
import java.util.List;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.handlingunits.model.I_M_HU_PI;

public interface ILUTUCUKey extends ITerminalKey
{
	@Override
	String getId();

	/**
	 * Gets {@link I_M_HU_PI} or null.
	 *
	 * @return
	 */
	I_M_HU_PI getM_HU_PI();

	List<ILUTUCUKey> getChildren();

	void setChildren(Collection<? extends ILUTUCUKey> children);

	void setChildren(ILUTUCUKey child);

	/**
	 * Merge given <code>chidlrenToAdd</code> to current existing children.
	 *
	 * The matching will be done by {@link #getId()} and it will be recursivelly.
	 *
	 * In other words, we are merging the trees or <code>childrenToAdd</code> to the trees of our current children.
	 *
	 * @param childrenToAdd
	 */
	void mergeChildren(Collection<? extends ILUTUCUKey> childrenToAdd);

	/**
	 *
	 * @return suggested Qty to be set in corresponding Qty Field (if any) of this LU/TU/CU key.
	 */
	BigDecimal getSuggestedQty();

	void setSuggestedQty(BigDecimal suggestedQty);

}
