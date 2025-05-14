-- TODO DO NOT merge to master
DELETE
FROM AD_UI_Element
WHERE AD_Field_ID IN (716379, 716380, 716381, 716382, 716383, 716384, 716385, 716386, 716387, 716388)
;

DELETE
FROM AD_Element_Link
WHERE AD_Field_ID IN (716379, 716380, 716381, 716382, 716383, 716384, 716385, 716386, 716387, 716388)
;

DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID IN (716379, 716380, 716381, 716382, 716383, 716384, 716385, 716386, 716387, 716388)
;

DELETE
FROM AD_Field
WHERE AD_Field_ID IN (716379, 716380, 716381, 716382, 716383, 716384, 716385, 716386, 716387, 716388)
;

DELETE
FROM ad_ref_list_trl
WHERE ad_ref_list_id IN (543505, 543506)
;

DELETE
FROM ad_ref_list
WHERE ad_ref_list_id IN (543505, 543506)
;

SELECT *
FROM ad_field
WHERE ad_column_id IN (586824, 586825, 586826, 586827, 586828, 586829, 586832, 586833, 586835, 586836)
;

DELETE
FROM ad_column
WHERE ad_column_id IN (586824, 586825, 586826, 586827, 586828, 586829, 586832, 586833, 586835, 586836)
;

DELETE
FROM AD_Element_Trl
WHERE AD_Element_ID IN (582450, 582451)
;

DELETE
FROM AD_Element
WHERE AD_Element_ID IN (582450, 582451)
;

DO
$$
    BEGIN

        /* DDL */ PERFORM public.db_alter_table('C_Invoice_Candidate', 'ALTER TABLE public.C_Invoice_Candidate DROP COLUMN UserElementDate2');

        /* DDL */ PERFORM public.db_alter_table('C_Invoice_Candidate', 'ALTER TABLE public.C_Invoice_Candidate DROP COLUMN UserElementDate1');

        /* DDL */ PERFORM public.db_alter_table('C_InvoiceLine', 'ALTER TABLE public.C_InvoiceLine DROP COLUMN UserElementDate1');

        /* DDL */ PERFORM public.db_alter_table('C_InvoiceLine', 'ALTER TABLE public.C_InvoiceLine DROP COLUMN UserElementDate2');

        /* DDL */ PERFORM public.db_alter_table('Fact_Acct', 'ALTER TABLE public.Fact_Acct DROP COLUMN UserElementDate1');

        /* DDL */ PERFORM public.db_alter_table('Fact_Acct', 'ALTER TABLE public.Fact_Acct DROP COLUMN UserElementDate2');

        /* DDL */ PERFORM public.db_alter_table('C_ValidCombination', 'ALTER TABLE public.C_ValidCombination DROP COLUMN UserElementDate1');

        /* DDL */ PERFORM public.db_alter_table('C_ValidCombination', 'ALTER TABLE public.C_ValidCombination DROP COLUMN UserElementDate2');
    EXCEPTION
        WHEN UNDEFINED_COLUMN THEN
            RAISE NOTICE 'UserElementDate1/UserElementDate2 do not exist in the current schema';
    END
$$
;