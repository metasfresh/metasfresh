package de.metas.shippingnotification.model	;

public interface I_M_ShipmentSchedule extends I_M_ShipmentSchedule
{
	String COLUMNNAME_PhysicalClearanceDate = "PhysicalClearanceDate";

	void setPhysicalClearanceDate (java.sql.Timestamp PhysicalClearanceDate);

	java.sql.Timestamp getPhysicalClearanceDate();
}
