DROP FUNCTION IF EXISTS report.pickingslot_label(IN M_PickingSlot_ID numeric);

CREATE OR REPLACE FUNCTION report.pickingslot_label(IN M_PickingSlot_ID numeric) 
RETURNS TABLE
(
	PickingSlot Character Varying,
	Value Character Varying(40),
	Name Character Varying(60),
	Location Character Varying(60)
)
AS
$$
SELECT PickingSlot, bp.Value, bp.Name, bpl.Name

FROM M_PickingSlot ps

LEFT OUTER JOIN C_BPartner bp ON ps.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
LEFT OUTER JOIN C_BPartner_Location bpl ON ps.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID AND bpl.isActive = 'Y'

WHERE ps.M_PickingSlot_ID = $1;
$$

LANGUAGE sql STABLE;