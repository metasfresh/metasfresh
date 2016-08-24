package de.metas.materialtracking.model;


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

/**
 * Use {@link org.adempiere.model.InterfaceWrapperHelper#asColumnReferenceAwareOrNull(Object, Class)} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IMaterialTrackingAware
{
	// @formatter:off
	public static final String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";

	public void setM_Material_Tracking_ID(int M_HU_PackingMaterial_ID);
	public int getM_Material_Tracking_ID();
	public void setM_Material_Tracking(I_M_Material_Tracking M_Material_Tracking) throws RuntimeException;
	public I_M_Material_Tracking getM_Material_Tracking() throws RuntimeException;
	// @formatter:on
}
