package de.metas.ui.web.address;

import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Postal;
import org.compiere.model.I_C_Region;

/*
 * #%L
 * metasfresh-webui-api
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

public interface IAddressModel
{
	//@formatter:off
	String COLUMNNAME_Address1 = "Address1";
	public String getAddress1();
	public void setAddress1(String address);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Address2 = "Address2";
	public String getAddress2();
	public void setAddress2(String address);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Address3 = "Address3";
	public String getAddress3();
	public void setAddress3(String address);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Address4 = "Address4";
	public String getAddress4();
	public void setAddress4(String address);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Postal = "Postal";
	public String getPostal();
	public void setPostal(String postal);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_City = "City";
	void setCity(String City);
	String getCity();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_C_Region_ID = "C_Region_ID";
	void setC_Region_ID(int C_Region_ID);
	int getC_Region_ID();
	I_C_Region getC_Region();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_C_Country_ID = "C_Country_ID";
	void setC_Country_ID(int C_Country_ID);
	int getC_Country_ID();
	I_C_Country getC_Country();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HasRegion = "HasRegion";
	void setHasRegion(boolean HasRegion);
	boolean isHasRegion();
	//@formatter:on
	

	//@formatter:off
	String COLUMNNAME_C_Postal_ID = "C_Postal_ID";
	void setC_Postal_ID(int C_Postal_ID);
	int getC_Postal_ID();
	I_C_Postal getC_Postal();
	//@formatter:on

}
