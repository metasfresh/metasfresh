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
	}

	void setM_Product_ID(int productId);

	int getM_Product_ID();

	void setC_BPartner_ID(int bpartnerId);

	int getC_BPartner_ID();

	void setProcessed(Boolean processed);

	Boolean getProcessed();

	/**
	 * Retrieve only those material trackings which are linked to given documents.
	 *
	 * @param linkedModels
	 */
	void setWithLinkedDocuments(List<?> linkedModels);

	List<?> getWithLinkedDocuments();

	/**
	 * Sets what shall happen when API was searching for one material tracking but more then one were found.
	 *
	 * @param onMoreThanOneFound
	 */
	void setOnMoreThanOneFound(OnMoreThanOneFound onMoreThanOneFound);

	OnMoreThanOneFound getOnMoreThanOneFound();
}
