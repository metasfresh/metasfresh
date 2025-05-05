DROP FUNCTION IF EXISTS set_C_Project_TimeBooking_to_invoiced (varchar,varchar,numeric);
CREATE OR REPLACE FUNCTION set_C_Project_TimeBooking_to_invoiced(IN p_C_Project_TimeBooking_IDs varchar, IN p_uuid varchar, p_AD_User_ID numeric) returns void
    language plpgsql
AS
$$
BEGIN

    IF 'all' = p_C_Project_TimeBooking_IDs THEN
        UPDATE C_Project_TimeBooking
        SET isInvoiced='Y',
            processed='Y',
            updated=now(),
            updatedby=p_AD_User_ID
        WHERE exists(select 1 from t_webui_viewselection s where s.uuid = p_uuid and C_Project_TimeBooking.C_Project_TimeBooking_ID = s.intkey1);
    ELSE
        UPDATE C_Project_TimeBooking
        SET isInvoiced='Y',
			processed='Y',
			updated=now(),
            updatedby=p_AD_User_ID
        WHERE C_Project_TimeBooking_ID = ANY (regexp_split_to_array(p_C_Project_TimeBooking_IDs, ',')::int[]);
    END IF;
END;
$$;