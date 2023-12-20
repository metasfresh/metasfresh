-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> EDI-Importfehler
-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-30T10:52:17.628Z
UPDATE AD_Field SET AD_Name_ID=581956, Description=NULL, Help=NULL, Name='EDI-Importfehler',Updated=TO_TIMESTAMP('2023-01-30 12:52:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710530
;

-- 2023-01-30T10:52:17.631Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581956) 
;

-- 2023-01-30T10:52:17.648Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710530
;

-- 2023-01-30T10:52:17.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710530)
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> EDI-Import abgeschlossen
-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-30T10:52:32.552Z
UPDATE AD_Field SET AD_Name_ID=581955, Description=NULL, Help=NULL, Name='EDI-Import abgeschlossen',Updated=TO_TIMESTAMP('2023-01-30 12:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710527
;

-- 2023-01-30T10:52:32.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581955) 
;

-- 2023-01-30T10:52:32.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710527
;

-- 2023-01-30T10:52:32.565Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710527)
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> EDI Fehlermeldung
-- Column: EXP_ReplicationTrx.ErrorMsg
-- 2023-01-30T10:52:44.014Z
UPDATE AD_Field SET AD_Name_ID=542003, Description=NULL, Help=NULL, Name='EDI Fehlermeldung',Updated=TO_TIMESTAMP('2023-01-30 12:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710531
;

-- 2023-01-30T10:52:44.015Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542003) 
;

-- 2023-01-30T10:52:44.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710531
;

-- 2023-01-30T10:52:44.027Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710531)
;

