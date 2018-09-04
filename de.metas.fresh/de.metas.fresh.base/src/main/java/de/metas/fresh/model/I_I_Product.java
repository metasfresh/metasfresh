package de.metas.fresh.model;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_I_Product
{

	// @formatter:off
	public static final String COLUMNNAME_AllergenName = "AllergenName";
	public void setAllergenName (java.lang.String AllergenName);
	public java.lang.String getAllergenName();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_AllergenName_FR = "AllergenName_FR";
	public void setAllergenName_FR (java.lang.String AllergenName_FR);
	public java.lang.String getAllergenName_FR();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_CustomerLabelName = "CustomerLabelName";
	public void setCustomerLabelName (java.lang.String CustomerLabelName);
	public java.lang.String getCustomerLabelName();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_CustomerLabelName_FR = "CustomerLabelName_FR";
	public void setCustomerLabelName_FR (java.lang.String CustomerLabelName_FR);
	public java.lang.String getCustomerLabelName_FR();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_CustomerLabelName_IT = "CustomerLabelName_IT";
	public void setCustomerLabelName_IT (java.lang.String CustomerLabelName_IT);
	public java.lang.String getCustomerLabelName_IT();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Ingredients = "Ingredients";
	public void setIngredients (java.lang.String Ingredients);
	public java.lang.String getIngredients();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Ingredients_FR = "Ingredients_FR";
	public void setIngredients_FR (java.lang.String Ingredients_FR);
	public java.lang.String getIngredients_FR();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Ingredients_IT = "Ingredients_IT";
	public void setIngredients_IT (java.lang.String Ingredients_IT);
	public java.lang.String getIngredients_IT();
	// @formatter:on

}
