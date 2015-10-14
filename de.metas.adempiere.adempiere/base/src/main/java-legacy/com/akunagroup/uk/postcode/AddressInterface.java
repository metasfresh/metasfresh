/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Akuna Ltd.                                           * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Michael Judd (michael.judd@akunagroup.com)                      * 
 **********************************************************************/
package com.akunagroup.uk.postcode;


/**
 * Interface for Address Lookup Web Service.
 * http://sourceforge.net/tracker/index.php?func=detail&aid=1741222&group_id=176962&atid=879335
 * The Address Structure
 */
public interface AddressInterface
{
	public int size();

	public String getStreet1();
	public void setStreet1(String newStreet1);
	public String getStreet2();
	public void setStreet2(String newStreet2);
	public String getStreet3();
	public void setStreet3(String newStreet3);
	public String getStreet4();
	public void setStreet4(String newStreet4);
	public String getCity();
	public void setCity(String newCity);
	public String getRegion();
	public void setRegion(String newRegion);
	public String getPostcode();
	public void setPostcode(String newPostcode);
	public String getCountry();
	public void setCountry(String newCountry);
	public String getCountryCode();
	public void setCountryCode(String newCountryCode);
}