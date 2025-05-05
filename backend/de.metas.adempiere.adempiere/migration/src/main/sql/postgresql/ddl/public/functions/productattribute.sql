-- legacy function, basically it's used in some legacy views, but nothing more

create or replace function productattribute(p_m_attributesetinstance_id numeric) returns character varying
    language plpgsql
as $$

    /*************************************************************************
     * The contents of this file are subject to the Compiere License.  You may
     * obtain a copy of the License at    http://www.compiere.org/license.html
     * Software is on an  "AS IS" basis,  WITHOUT WARRANTY OF ANY KIND, either
     * express or implied. See the License for details. Code: Compiere ERP+CRM
     * Copyright (C) 1999-2001 Jorg Janke, ComPiere, Inc. All Rights Reserved.
     *
     * converted to postgreSQL by Karsten Thiemann (Schaeffer AG),
     * kthiemann@adempiere.org
     *************************************************************************
     * Title: Return Instance Attribute Info
     * Description:
     *
     * Test:
        SELECT ProductAttribute (M_AttributeSetInstance_ID)
        FROM M_InOutLine WHERE M_AttributeSetInstance_ID > 0
        --
        SELECT p.Name
        FROM C_InvoiceLine il LEFT OUTER JOIN M_Product p ON (il.M_Product_ID=p.M_Product_ID);
        SELECT p.Name || ProductAttribute (il.M_AttributeSetInstance_ID)
        FROM C_InvoiceLine il LEFT OUTER JOIN M_Product p ON (il.M_Product_ID=p.M_Product_ID);

     ************************************************************************/


DECLARE

    v_Name          VARCHAR(2000) := '';
    v_NameAdd       VARCHAR(2000) := '';

    r   RECORD;
    --

BEGIN
    --  Get Product Attribute Set Instance
    IF (p_M_AttributeSetInstance_ID > 0) THEN
        FOR r IN
            SELECT ai.Value, a.Name
            FROM M_AttributeInstance ai
                     INNER JOIN M_Attribute a ON (ai.M_Attribute_ID=a.M_Attribute_ID AND a.IsInstanceAttribute='Y')
            WHERE ai.M_AttributeSetInstance_ID=p_M_AttributeSetInstance_ID
            LOOP
                v_NameAdd := v_NameAdd || r.Name || ':' || r.Value || ' ';
            END LOOP;
        --
        IF (LENGTH(v_NameAdd) > 0) THEN
            v_Name := v_Name || ' (' || TRIM(v_NameAdd) || ')';
        ELSE
            v_Name := NULL;
        END IF;
    END IF;
    RETURN v_Name;
END;
$$;


