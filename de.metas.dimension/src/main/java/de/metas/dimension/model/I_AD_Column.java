package de.metas.dimension.model;

/*
 * #%L
 * de.metas.dimension
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


public interface I_AD_Column extends org.compiere.model.I_AD_Column
{
	//@formatter:off
	public static final String COLUMNNAME_IsDimension = "IsDimension";
	public boolean isDimension();
	public void setIsDimension(boolean isDimension);
	//@formatter:on
}
