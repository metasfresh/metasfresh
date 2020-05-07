package de.metas.picking.legacy.form;

/*
 * #%L
 * de.metas.swat.base
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
 * Implementations of this interface are used in Kommissionierung Terminal when searching by barcode.
 * 
 * Specific implementations are used, based on what type of barcode we search (e.g. Product UPC, HU's SSCC18 etc).
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06821_Kommissionier_Terminal_Extension_%28104171338645%29
 */
public interface ITableRowSearchSelectionMatcher
{
	/**
	 * Gets matcher name.
	 * 
	 * A short name which will be displayed to user in table rows table, just to know how a line was matched
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * 
	 * @param row
	 * @return true if given row was matched
	 */
	boolean match(TableRowKey key);

	/**
	 * Returns <code>true</code> if this is a valid rule (i.e. all underlying informations can be loaded).
	 * 
	 * NOTE: a rule can be valid but {@link #isNull()} can return false which means that rule is correctly initialized but it will match no results. While, if a rule is not valid it means that the
	 * rule is not even appliable.
	 * 
	 * @return true if this is a valid rule (i.e. all underlying informations can be loaded)
	 */
	boolean isValid();

	/**
	 * Checks if this matcher is empty (i.e. {@link #match(TableRow)} will always return false)
	 * 
	 * @return
	 */
	boolean isNull();

	/**
	 * 
	 * @return true if more then one matched rows is allowed
	 */
	boolean isAllowMultipleResults();

	/**
	 * Check if given matcher is functionally the same as this matcher.
	 * 
	 * Two matchers are functionally equal when one of the followings is true:
	 * <ul>
	 * <li>if given matcher is null or it's {@link #isNull()} returns true and this matcher's {@link #isNull()} returns true
	 * <li>{@link #equals(Object)} method returns true
	 * </ul>
	 * 
	 * @param matcher
	 * @return true if given matcher is functionally the same as this matcher.
	 */
	boolean equalsOrNull(ITableRowSearchSelectionMatcher matcher);

	/**
	 * Gets HU's BPartner ID
	 * 
	 * @return C_BPartner_ID or -1 if does not apply
	 */
	int getC_BPartner_ID();
}
