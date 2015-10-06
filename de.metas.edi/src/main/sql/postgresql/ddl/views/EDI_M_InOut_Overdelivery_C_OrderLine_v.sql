-- View: EDI_M_InOut_Overdelivery_C_OrderLine_v
DROP VIEW IF EXISTS EDI_M_InOut_Overdelivery_C_OrderLine_v;

CREATE OR REPLACE VIEW EDI_M_InOut_Overdelivery_C_OrderLine_v AS
SELECT
 io.M_InOut_ID,
 ol.C_OrderLine_ID
FROM C_OrderLine ol
	LEFT JOIN M_InOut io ON ol.C_Order_ID=io.C_Order_ID
	LEFT JOIN M_InOutLine iol ON ol.C_OrderLine_ID=iol.C_OrderLine_ID
WHERE TRUE
AND ol.IsPackagingMaterial='N'
AND io.M_InOut_ID IS NOT NULL
AND iol.M_InOutLine_ID IS NULL 
AND NOT EXISTS (
	SELECT 1 FROM M_InOut ioOther
	LEFT JOIN M_InOutLine iolOther ON ol.C_OrderLine_ID=iolOther.C_OrderLine_ID
	WHERE 
	ol.C_Order_ID=ioOther.C_Order_ID
	AND ioOther.M_InOut_ID != io.M_InOut_ID -- Not same InOut (redundant)
	-- no OTHER M_InOut/Line with EXP_Status=S will be sent
	AND ioOther.EDI_ExportStatus='S'
);

ALTER TABLE EDI_M_InOut_Overdelivery_C_OrderLine_v
  OWNER TO adempiere;

COMMENT ON VIEW adempiere.edi_m_inout_overdelivery_c_orderline_v
  IS 'selects those order lines which have *no* delivery (note that the view''s name is misleading in that respect)';
