-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Material
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2022-12-22T16:15:28.086Z
UPDATE AD_Field SET AD_Name_ID=581748, Description=NULL, Help=NULL, Name='Material',Updated=TO_TIMESTAMP('2022-12-22 18:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710043
;

-- 2022-12-22T16:15:28.126Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581748) 
;

-- 2022-12-22T16:15:28.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710043
;

-- 2022-12-22T16:15:28.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710043)
;

