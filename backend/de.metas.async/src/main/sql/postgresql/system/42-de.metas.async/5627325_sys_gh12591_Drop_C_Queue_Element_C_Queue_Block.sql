-- 2022-02-22T22:18:14.266Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551119
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Element -> Queue Block
-- Column: C_Queue_Element.C_Queue_Block_ID
-- 2022-02-22T22:18:14.271Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551119
;

-- 2022-02-22T22:18:14.283Z
DELETE FROM AD_Field WHERE AD_Field_ID=551119
;

-- 2022-02-22T22:18:14.285Z
/* DDL */ SELECT public.db_alter_table('C_Queue_Element','ALTER TABLE C_Queue_Element DROP COLUMN IF EXISTS C_Queue_Block_ID')
;

-- Column: C_Queue_Element.C_Queue_Block_ID
-- 2022-02-22T22:18:14.424Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=547867
;

-- 2022-02-22T22:18:14.426Z
DELETE FROM AD_Column WHERE AD_Column_ID=547867
;
