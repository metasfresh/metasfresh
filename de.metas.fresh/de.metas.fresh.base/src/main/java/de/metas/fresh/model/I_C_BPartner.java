package de.metas.fresh.model;

/*
 * #%L
 * de.metas.fresh.base
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



public interface I_C_BPartner extends de.metas.interfaces.I_C_BPartner
{
	public static final String COLUMNNAME_IsProducerAllotment = "IsProducerAllotment";

	public void setIsProducerAllotment(boolean IsProducerAllotment);

	public boolean isProducerAllotment();
	
	
	public static final String COLUMNNAME_IsADRcustomer = "IsADRcustomer";
	
	public void setIsADRcustomer (boolean IsADRcustomer);
	
	public boolean isADRcustomer();
	
	
	public static final String COLUMNNAME_Fresh_AdRRegion = "Fresh_AdRRegion";
	
	public void setFresh_AdRRegion(String Fresh_AdRRegion);
	
	public String getFresh_AdRRegion();
	
	
	public static final String COLUMNNAME_IsADRVendor = "IsADRVendor";
	
	public void setIsADRVendor (boolean IsADRVendor);
	
	public boolean isADRVendor();
	
	
	public static final String COLUMNNAME_Fresh_AdRVendorRegion = "Fresh_AdRVendorRegion";
	
	public void setFresh_AdRVendorRegion(String Fresh_AdRVendorRegion);
	
	public String getFresh_AdRVendorRegion();
	
	
	
	public static final int ADRZertifizierung_L_AD_Reference_ID=540414;


	public static final String ADRZertifizierung_L_Keine = "01";

	public static final String ADRZertifizierung_L_GMAA = "02";

	public static final String ADRZertifizierung_L_GMNF = "03";

	public static final String ADRZertifizierung_L_GMVD = "04";

	public static final String ADRZertifizierung_L_GMAA_GMNF = "05";
	
	public static final String ADRZertifizierung_L_GMAA_GMNF_GMVD = "06";
	
	// fresh_07849
	// Add column Fresh_Produzentenabrechnung
	
	public static final String COLUMNNAME_Fresh_Produzentenabrechnung = "Fresh_Produzentenabrechnung";
	
	public void setFresh_Produzentenabrechnung(boolean Fresh_Produzentenabrechnung);
	
	public boolean isFresh_Produzentenabrechnung();
	
}
