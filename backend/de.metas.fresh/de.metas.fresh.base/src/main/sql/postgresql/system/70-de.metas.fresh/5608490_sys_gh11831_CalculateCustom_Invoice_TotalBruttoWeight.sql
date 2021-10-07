select
    SUM(il.InvoicedQty) as catchweight,
    COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOM,
    CASE
        WHEN uom.StdPrecision = 0
            THEN '#,##0'
            ELSE Substring('#,##0.0000' FROM 0 FOR 7 + uom.StdPrecision :: integer) END AS QtyPattern

from C_Customs_Invoice_Line il
         LEFT OUTER JOIN C_UOM uom ON uom.C_UOM_ID = il.c_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomt on uomt.c_UOM_ID = uom.C_UOM_ID and uomt.AD_Language = $P{ad_language}
where il.C_Customs_Invoice_Id =  $P{C_Customs_Invoice_Id}
group by uomt.UOMSymbol, uom.UOMSymbol, uom.StdPrecision;