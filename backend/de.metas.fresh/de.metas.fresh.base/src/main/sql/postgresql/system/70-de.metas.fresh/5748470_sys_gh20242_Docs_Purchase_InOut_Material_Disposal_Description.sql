DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Description (IN p_Record_ID   numeric,
                                                                                                              IN p_AD_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Material_Disposal_Description(IN p_Record_ID   numeric,
                                                                                                                IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                printname     character varying(60),
                io_printname  character varying(60),
                documentno    character varying(30),
                io_documentno text,
                bp_name       character varying(60),
                bp_value      character varying(40),
                inventorydate timestamp with time zone,
                movementdate  timestamp without time zone
            )
AS
$$
SELECT COALESCE(dtt.printName, dt.printName)       AS printname,
       COALESCE(io_dtt.printName, io_dt.printName) AS io_printname,
       i.DocumentNo                                AS documentNo,
       CASE
           WHEN il.DocNo_hi = il.DocNo_lo THEN il.DocNo_lo
                                          ELSE il.DocNo_lo || ' ff.'
       END                                         AS io_documentno,
       bp.Name                                     AS BP_Name,
       bp.Value                                    AS BP_Value,
       i.movementDate                              AS inventorydate,
       il.movementDate                             AS movementdate

FROM M_Inventory i

         -- data from inventory
         INNER JOIN C_DocType dt ON i.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_AD_Language

    --data from inout
         INNER JOIN (SELECT il.M_Inventory_ID,
                            MAX(io.movementdate) AS movementdate,
                            MIN(io.Documentno)   AS Docno_lo,
                            MAX(io.Documentno)   AS Docno_hi,
                            MAX(io.C_DocType_ID) AS C_DocType_ID,
                            io.C_Bpartner_ID
                     FROM M_InventoryLine il
                              INNER JOIN M_InOutLine iol ON il.M_InOutLine_ID = iol.M_InOutLine_ID
                              INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID

                     GROUP BY M_Inventory_ID, C_Bpartner_ID) il ON i.M_Inventory_ID = il.M_Inventory_ID

         INNER JOIN C_BPartner bp ON il.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_DocType io_dt ON il.C_DocType_ID = io_dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl io_dtt ON io_dt.C_DocType_ID = io_dtt.C_DocType_ID AND io_dtt.AD_Language = p_AD_Language

WHERE i.M_Inventory_ID = p_Record_ID

ORDER BY bp_value, io_documentno, movementdate
$$
    LANGUAGE sql STABLE
;
