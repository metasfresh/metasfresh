package de.metas.materialtracking;

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


import java.util.List;

public interface IMaterialTrackingQuery
{
	enum OnMoreThanOneFound
	{
		ThrowException,
		ReturnNull,
		ReturnFirst
	}

	IMaterialTrackingQuery setM_Product_ID(int productId);

	int getM_Product_ID();

	IMaterialTrackingQuery setC_BPartner_ID(int bpartnerId);

	int getC_BPartner_ID();

	IMaterialTrackingQuery setProcessed(Boolean processed);

	Boolean getProcessed();

	/**
	 * Set this parameter on true if you want to only retrieve the material tracking entries with completed flatrate terms
	 * ATM, if set on false, it will work as if not set.
	 * @param completeFlatrateTerm
	 */
	IMaterialTrackingQuery setCompleteFlatrateTerm(Boolean completeFlatrateTerm);

	Boolean getCompleteFlatrateTerm();

	/**
	 * Retrieve only those material trackings which are linked to given documents.
	 *
	 * @param linkedModels
	 */
	IMaterialTrackingQuery setWithLinkedDocuments(List<?> linkedModels);

	List<?> getWithLinkedDocuments();

	/**
	 * Sets what shall happen when API was searching for one material tracking but more then one were found.
	 *
	 * @param onMoreThanOneFound
	 */
	IMaterialTrackingQuery setOnMoreThanOneFound(OnMoreThanOneFound onMoreThanOneFound);

	OnMoreThanOneFound getOnMoreThanOneFound();

	IMaterialTrackingQuery setLot(String lot);

	String getLot();

	IMaterialTrackingQuery setReturnReadOnlyRecords(boolean returnReadOnlyRecords);

	boolean isReturnReadOnlyRecords();
}
