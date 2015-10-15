package de.metas.storage;

/*
 * #%L
 * de.metas.storage
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


import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

public interface IStorageSegmentBuilder
{
	IStorageSegment build();

	IStorageSegmentBuilder addM_Product_ID(int productId);

	IStorageSegmentBuilder addC_BPartner_ID(int bpartnerId);

	IStorageSegmentBuilder addM_Locator_ID(int locatorId);

	IStorageSegmentBuilder addM_Locator(I_M_Locator locator);

	IStorageSegmentBuilder addM_Warehouse(I_M_Warehouse warehouse);

	IStorageSegmentBuilder addM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID);
}
