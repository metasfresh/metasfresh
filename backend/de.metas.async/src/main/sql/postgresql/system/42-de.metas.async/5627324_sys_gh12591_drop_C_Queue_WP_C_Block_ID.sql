delete from dlm_partition_config_reference where dlm_referencing_column_id = 547852
;

-- 2022-02-23T10:13:39.817Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=555695
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Arbeitspaket Warteschlange -> Erstellt durch Prozess-Instanz
-- Column: C_Queue_WorkPackage.AD_PInstance_Creator_ID
-- 2022-02-23T10:13:39.831Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=555695
;

-- 2022-02-23T10:13:39.844Z
DELETE FROM AD_Field WHERE AD_Field_ID=555695
;

-- Column: C_Queue_WorkPackage.AD_PInstance_Creator_ID
-- 2022-02-23T10:13:39.859Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551937
;

-- 2022-02-23T10:13:39.863Z
DELETE FROM AD_Column WHERE AD_Column_ID=551937
;

-- UI Element: Asynchrone Verarbeitungswarteschlange -> Block.Queue Block
-- Column: C_Queue_Block.C_Queue_Block_ID
-- 2022-02-22T22:47:05.933Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546433
;

-- UI Element: Asynchrone Verarbeitungswarteschlange -> Block.WorkPackage Processor
-- Column: C_Queue_Block.C_Queue_PackageProcessor_ID
-- 2022-02-22T22:47:10.418Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546434
;

-- UI Element: Asynchrone Verarbeitungswarteschlange -> Block.Erstellt durch Prozess-Instanz
-- Column: C_Queue_Block.AD_PInstance_Creator_ID
-- 2022-02-22T22:47:16.602Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546435
;

-- UI Element: Asynchrone Verarbeitungswarteschlange -> Block.Sektion
-- Column: C_Queue_Block.AD_Org_ID
-- 2022-02-22T22:47:16.632Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546432
;

-- UI Element: Asynchrone Verarbeitungswarteschlange -> Block.Mandant
-- Column: C_Queue_Block.AD_Client_ID
-- 2022-02-22T22:47:16.648Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546431
;

-- 2022-02-22T22:47:21.531Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540802
;

-- 2022-02-22T22:47:24.731Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540465
;

-- 2022-02-22T22:47:30.051Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=540340
;

-- 2022-02-22T22:47:30.053Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=540340
;

-- 2022-02-22T22:47:39.613Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551098
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Mandant
-- Column: C_Queue_Block.AD_Client_ID
-- 2022-02-22T22:47:39.620Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551098
;

-- 2022-02-22T22:47:39.622Z
DELETE FROM AD_Field WHERE AD_Field_ID=551098
;

-- 2022-02-22T22:47:39.670Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551101
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Sektion
-- Column: C_Queue_Block.AD_Org_ID
-- 2022-02-22T22:47:39.675Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551101
;

-- 2022-02-22T22:47:39.678Z
DELETE FROM AD_Field WHERE AD_Field_ID=551101
;

-- 2022-02-22T22:47:39.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551094
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Queue Block
-- Column: C_Queue_Block.C_Queue_Block_ID
-- 2022-02-22T22:47:39.720Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551094
;

-- 2022-02-22T22:47:39.722Z
DELETE FROM AD_Field WHERE AD_Field_ID=551094
;

-- 2022-02-22T22:47:39.761Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551797
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> WorkPackage Processor
-- Column: C_Queue_Block.C_Queue_PackageProcessor_ID
-- 2022-02-22T22:47:39.765Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551797
;

-- 2022-02-22T22:47:39.766Z
DELETE FROM AD_Field WHERE AD_Field_ID=551797
;

-- 2022-02-22T22:47:39.798Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551099
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Erstellt durch Prozess-Instanz
-- Column: C_Queue_Block.AD_PInstance_Creator_ID
-- 2022-02-22T22:47:39.800Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551099
;

-- 2022-02-22T22:47:39.801Z
DELETE FROM AD_Field WHERE AD_Field_ID=551099
;

-- 2022-02-22T22:47:39.829Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551096
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Erstellt durch
-- Column: C_Queue_Block.CreatedBy
-- 2022-02-22T22:47:39.831Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551096
;

-- 2022-02-22T22:47:39.833Z
DELETE FROM AD_Field WHERE AD_Field_ID=551096
;

-- 2022-02-22T22:47:39.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551091
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Aktiv
-- Column: C_Queue_Block.IsActive
-- 2022-02-22T22:47:39.866Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551091
;

-- 2022-02-22T22:47:39.867Z
DELETE FROM AD_Field WHERE AD_Field_ID=551091
;

-- 2022-02-22T22:47:39.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551092
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Aktualisiert
-- Column: C_Queue_Block.Updated
-- 2022-02-22T22:47:39.898Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551092
;

-- 2022-02-22T22:47:39.900Z
DELETE FROM AD_Field WHERE AD_Field_ID=551092
;

-- 2022-02-22T22:47:39.934Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551093
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Aktualisiert durch
-- Column: C_Queue_Block.UpdatedBy
-- 2022-02-22T22:47:39.935Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551093
;

-- 2022-02-22T22:47:39.937Z
DELETE FROM AD_Field WHERE AD_Field_ID=551093
;

-- 2022-02-22T22:47:39.968Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551095
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Block -> Erstellt
-- Column: C_Queue_Block.Created
-- 2022-02-22T22:47:39.969Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551095
;

-- 2022-02-22T22:47:39.972Z
DELETE FROM AD_Field WHERE AD_Field_ID=551095
;

-- Tab: Asynchrone Verarbeitungswarteschlange -> Block
-- Table: C_Queue_Block
-- 2022-02-22T22:47:48.549Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=540455
;

-- 2022-02-22T22:47:48.551Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=540455
;


-- UI Element: Asynchrone Verarbeitungswarteschlange -> Arbeitspaket Warteschlange.Queue Block
-- Column: C_Queue_WorkPackage.C_Queue_Block_ID
-- 2022-02-22T22:54:48.279Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546463
;

-- 2022-02-22T22:54:48.280Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=551105
;

-- Field: Asynchrone Verarbeitungswarteschlange -> Arbeitspaket Warteschlange -> Queue Block
-- Column: C_Queue_WorkPackage.C_Queue_Block_ID
-- 2022-02-22T22:54:48.282Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551105
;

-- 2022-02-22T22:54:48.284Z
DELETE FROM AD_Field WHERE AD_Field_ID=551105
;

-- 2022-02-22T22:54:48.285Z
/* DDL */ SELECT public.db_alter_table('C_Queue_WorkPackage','ALTER TABLE C_Queue_WorkPackage DROP COLUMN IF EXISTS C_Queue_Block_ID')
;

-- Column: C_Queue_WorkPackage.C_Queue_Block_ID
-- 2022-02-22T22:54:48.465Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=547852
;

-- 2022-02-22T22:54:48.467Z
DELETE FROM AD_Column WHERE AD_Column_ID=547852
;
