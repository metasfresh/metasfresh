package de.metas.handlingunits.materialtracking;

import java.util.Map;

import org.compiere.model.I_M_Attribute;

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

/**
 * Implementations of this interface are objects (usually HUs) which can be flagged as "scheduled for Quality Inspection".
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task 08639
 */
public interface IQualityInspectionSchedulable
{
	/** @return true if scheduled for quality inspection */
	boolean isQualityInspection();

	/** Sets if this item shall be scheduled for quality inspection */
	void setQualityInspection(boolean qualityInspectionFlag);

	/** @return relevant attributes as immutable map */
	Map<I_M_Attribute, Object> getAttributesAsMap();

}
