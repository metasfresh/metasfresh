package de.metas.inout.model;

import de.metas.shipping.model.I_M_ShipperTransportation;

public interface I_M_InOut extends org.compiere.model.I_M_InOut
{
	// @formatter:off
	String COLUMNNAME_M_ShipperTransportation = "M_ShipperTransportation_ID";
	I_M_ShipperTransportation getM_ShipperTransportation();
	void setM_ShipperTransportation_ID(int M_ShipperTransportation_ID);
	int getM_ShipperTransportation_ID();
	// @formatter:on

	// @formatter:off
	// 08524
	// this one is only used in MRAs
	String COLUMNNAME_IsManual = "IsManual";
	void setIsManual(boolean IsManual);
	boolean isManual();
	// @formatter:on
}
