package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Set;

/**
 * Contains a collection of {@link IGridTabRowBuilder}s to be applied to several records.
 * 
 * @author tsa
 * 
 */
public interface IInfoWindowGridRowBuilders
{
	/**
	 * Gets a {@link Set} of Record_IDs where we have builders which are able to create new records (see {@link IGridTabRowBuilder#isCreateNewRecord()}).
	 * 
	 * NOTE: this object is created by some Info Windows which are triggered from some lookup fields. Those Info Windows can have multi-selection and we need to add custom builders for each Selected
	 * row.
	 * 
	 * e.g. If we call a custom Info Product window from a Product lookup field and user selects several products that list of M_Product_ID will be contained here.
	 * 
	 * @return set of Record_IDs
	 */
	Set<Integer> getRecordIds();

	/**
	 * Gets record customizer ({@link IGridTabRowBuilder}) to be used for given recordId.
	 * 
	 * NOTE: Record_IDs are fetched by using {@link #getRecordIds()}.
	 * 
	 * @param recordId
	 * @return
	 */
	IGridTabRowBuilder getGridTabRowBuilder(final int recordId);

	/**
	 * Used by Info Windows pluggable code in order to register custom builders for each selected item.
	 * 
	 * @param recordId
	 * @param builder
	 */
	void addGridTabRowBuilder(final int recordId, final IGridTabRowBuilder builder);

}
