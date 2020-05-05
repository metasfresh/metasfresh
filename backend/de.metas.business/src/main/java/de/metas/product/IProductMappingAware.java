package de.metas.product;

import de.metas.product.model.I_M_Product_Mapping;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 *
 * Implementors can reference an {@link I_M_Product_Mapping} record.<br>
 * To obtain an instance, use {@link org.adempiere.model.InterfaceWrapperHelper#asColumnReferenceAwareOrNull(Object, Class)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09700_Counter_Documents_%28100691234288%29
 */
public interface IProductMappingAware
{
	// @formatter:off
	String COLUMNNAME_M_Product_Mapping_ID = "M_Product_Mapping_ID";

	void setM_Product_Mapping_ID (int _M_Product_Mapping_ID);
	int getM_Product_Mapping_ID();

	void setM_Product_Mapping (I_M_Product_Mapping M_Product_Mapping);
	I_M_Product_Mapping getM_Product_Mapping();
	// @formatter:on
}
