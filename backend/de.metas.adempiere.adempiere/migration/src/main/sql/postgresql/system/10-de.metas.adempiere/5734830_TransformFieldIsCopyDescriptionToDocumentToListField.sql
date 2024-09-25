-- 2024-09-25T14:57:45.935Z
UPDATE AD_Element SET ColumnName='CopyDescriptionAndDocumentNote',Updated=TO_TIMESTAMP('2024-09-25 17:57:45.929','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282
;

-- 2024-09-25T14:57:45.967Z
UPDATE AD_Column SET ColumnName='CopyDescriptionAndDocumentNote' WHERE AD_Element_ID=583282
;

-- 2024-09-25T14:57:45.992Z
UPDATE AD_Process_Para SET ColumnName='CopyDescriptionAndDocumentNote' WHERE AD_Element_ID=583282
;

-- 2024-09-25T14:57:46.144Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_DE') 
;

-- Element: CopyDescriptionAndDocumentNote
-- 2024-09-25T14:58:23.057Z
UPDATE AD_Element_Trl SET Name='Beschreibung und Dokumentnotiz kopieren', PrintName='Beschreibung und Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-25 17:58:23.056','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='de_CH'
;

-- 2024-09-25T14:58:23.103Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_CH') 
;

-- Element: CopyDescriptionAndDocumentNote
-- 2024-09-25T14:58:27.498Z
UPDATE AD_Element_Trl SET Name='Beschreibung und Dokumentnotiz kopieren', PrintName='Beschreibung und Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-25 17:58:27.498','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='de_DE'
;

-- 2024-09-25T14:58:27.542Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583282,'de_DE') 
;

-- 2024-09-25T14:58:27.573Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_DE') 
;

-- Element: CopyDescriptionAndDocumentNote
-- 2024-09-25T14:58:41.304Z
UPDATE AD_Element_Trl SET Name='Copy Description And Document Note', PrintName='Copy Description And Document Note',Updated=TO_TIMESTAMP('2024-09-25 17:58:41.304','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='en_US'
;

-- 2024-09-25T14:58:41.349Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'en_US') 
;

-- 2024-09-25T14:59:08.224Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN CopyDescriptionAndDocumentNote VARCHAR(2) DEFAULT ''CD'' NOT NULL')
;

-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- 2024-09-25T14:59:41.353Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2024-09-25 17:59:41.352','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589158
;

-- 2024-09-25T14:59:45.479Z
INSERT INTO t_alter_column values('c_doctype','CopyDescriptionAndDocumentNote','VARCHAR(2)',null,null)
;

-- 2024-09-25T14:59:45.507Z
INSERT INTO t_alter_column values('c_doctype','CopyDescriptionAndDocumentNote',null,'NULL',null)
;

-- Reference: CopyDocumentNote
-- Value: CD
-- ValueName: Copy Description And Document Note to document
-- 2024-09-25T15:00:01.344Z
UPDATE AD_Ref_List SET Name='Copy Description And Document Note to document', ValueName='Copy Description And Document Note to document',Updated=TO_TIMESTAMP('2024-09-25 18:00:01.343','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543725
;

-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- 2024-09-25T15:00:24.428Z
UPDATE AD_Column SET FieldLength=3,Updated=TO_TIMESTAMP('2024-09-25 18:00:24.428','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589158
;

-- 2024-09-25T15:00:28.158Z
INSERT INTO t_alter_column values('c_doctype','CopyDescriptionAndDocumentNote','VARCHAR(3)',null,null)
;

-- Reference: CopyDocumentNote
-- Value: CDD
-- ValueName: Copy Description And Document Note to document
-- 2024-09-25T15:00:38.810Z
UPDATE AD_Ref_List SET Value='CDD',Updated=TO_TIMESTAMP('2024-09-25 18:00:38.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543725
;

-- Reference: CopyDocumentNote
-- Value: CO
-- ValueName: Copy document note from Order
-- 2024-09-25T15:00:54.343Z
UPDATE AD_Ref_List SET Name='Copy Description And Document Note to document from Order',Updated=TO_TIMESTAMP('2024-09-25 18:00:54.343','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543726
;

-- Reference: CopyDocumentNote
-- Value: CD
-- ValueName: Copy Description And Document Note to document
-- 2024-09-25T15:01:00.769Z
UPDATE AD_Ref_List SET Value='CD',Updated=TO_TIMESTAMP('2024-09-25 18:01:00.769','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543725
;

-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- 2024-09-25T15:01:07.340Z
UPDATE AD_Column SET FieldLength=2,Updated=TO_TIMESTAMP('2024-09-25 18:01:07.34','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589158
;

-- 2024-09-25T15:01:12.887Z
INSERT INTO t_alter_column values('c_doctype','CopyDescriptionAndDocumentNote','VARCHAR(2)',null,null)
;

-- Reference Item: CopyDocumentNote -> CD_Copy Description And Document Note to document
-- 2024-09-25T15:01:47.658Z
UPDATE AD_Ref_List_Trl SET Name='Beschreibung und Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-25 18:01:47.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543725
;

-- Reference Item: CopyDocumentNote -> CD_Copy Description And Document Note to document
-- 2024-09-25T15:01:51.713Z
UPDATE AD_Ref_List_Trl SET Name='Beschreibung und Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-25 18:01:51.713','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543725
;

-- Reference Item: CopyDocumentNote -> CD_Copy Description And Document Note to document
-- 2024-09-25T15:01:56.431Z
UPDATE AD_Ref_List_Trl SET Name='Copy Description And Document Note',Updated=TO_TIMESTAMP('2024-09-25 18:01:56.431','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543725
;

-- Reference: CopyDocumentNote
-- Value: CO
-- ValueName: Copy Description And Document Note to document from Order
-- 2024-09-25T15:02:09.389Z
UPDATE AD_Ref_List SET ValueName='Copy Description And Document Note to document from Order',Updated=TO_TIMESTAMP('2024-09-25 18:02:09.389','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543726
;

-- Reference: CopyDocumentNote
-- Value: CO
-- ValueName: Copy Description And Document Note from Order
-- 2024-09-25T15:02:28.260Z
UPDATE AD_Ref_List SET Name='Copy Description And Document Note from Order', ValueName='Copy Description And Document Note from Order',Updated=TO_TIMESTAMP('2024-09-25 18:02:28.259','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543726
;

-- Reference Item: CopyDocumentNote -> CO_Copy Description And Document Note from Order
-- 2024-09-25T15:02:42.560Z
UPDATE AD_Ref_List_Trl SET Name='Copy Description And Document Note from Order',Updated=TO_TIMESTAMP('2024-09-25 18:02:42.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543726
;

-- Reference Item: CopyDocumentNote -> CO_Copy Description And Document Note from Order
-- 2024-09-25T15:02:50.784Z
UPDATE AD_Ref_List_Trl SET Name='Beschreibung und Dokumentnotiz aus der Bestellung kopieren',Updated=TO_TIMESTAMP('2024-09-25 18:02:50.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543726
;

-- Reference Item: CopyDocumentNote -> CO_Copy Description And Document Note from Order
-- 2024-09-25T15:02:53.444Z
UPDATE AD_Ref_List_Trl SET Name='Beschreibung und Dokumentnotiz aus der Bestellung kopieren',Updated=TO_TIMESTAMP('2024-09-25 18:02:53.444','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543726
;

ALTER TABLE c_doctype
    DROP COLUMN CopyDocumentNote
;



-- Reference: CopyDocumentNote
-- Value: CD
-- ValueName: CopyDescriptionAndDocumentNote
-- 2024-09-25T15:07:01.891Z
UPDATE AD_Ref_List SET ValueName='CopyDescriptionAndDocumentNote',Updated=TO_TIMESTAMP('2024-09-25 18:07:01.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543725
;

-- Reference: CopyDocumentNote
-- Value: CO
-- ValueName: CopyDescriptionAndDocumentNotefromOrder
-- 2024-09-25T15:07:26.625Z
UPDATE AD_Ref_List SET ValueName='CopyDescriptionAndDocumentNotefromOrder',Updated=TO_TIMESTAMP('2024-09-25 18:07:26.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543726
;


select backup_table('c_doctype');
update c_doctype set CopyDescriptionAndDocumentNote=null where  iscopydescriptiontodocument='N';
update c_doctype set CopyDescriptionAndDocumentNote='CD' where  iscopydescriptiontodocument='Y';


ALTER TABLE c_doctype
    DROP COLUMN iscopydescriptiontodocument
;

-- UI Element: Belegart -> Belegart.Copy description to document_IsCopyDescriptionToDocument_Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- UI Element: Belegart(135,D) -> Belegart(167,D) -> main -> 10 -> description.Copy description to document_IsCopyDescriptionToDocument_Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- 2024-09-25T15:20:22.725Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=550130
;

-- 2024-09-25T15:20:22.799Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=561403
;

-- Field: Belegart -> Belegart -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- Field: Belegart(135,D) -> Belegart(167,D) -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- 2024-09-25T15:20:22.924Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561403
;

-- 2024-09-25T15:20:23.064Z
DELETE FROM AD_Field WHERE AD_Field_ID=561403
;

-- Column: C_DocType.IsCopyDescriptionToDocument
-- Column: C_DocType.IsCopyDescriptionToDocument
-- 2024-09-25T15:20:23.237Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558575
;

-- 2024-09-25T15:20:23.369Z
DELETE FROM AD_Column WHERE AD_Column_ID=558575
;

-- UI Element: Belegart -> Belegart.Dokumentnotiz kopieren
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- UI Element: Belegart(135,D) -> Belegart(167,D) -> main -> 10 -> description.Dokumentnotiz kopieren
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- 2024-09-25T15:25:21.392Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2024-09-25 18:25:21.391','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625986
;

-- Field: Belegart -> Belegart -> Beschreibung und Dokumentnotiz kopieren
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- Field: Belegart(135,D) -> Belegart(167,D) -> Beschreibung und Dokumentnotiz kopieren
-- Column: C_DocType.CopyDescriptionAndDocumentNote
-- 2024-09-25T15:25:48.715Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-09-25 18:25:48.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731501
;

