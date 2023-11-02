-- 2023-10-29T18:25:14.726Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540772,0,540637,TO_TIMESTAMP('2023-10-29 19:25:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Pro Arbeitsplatz und Hostkey ist nur ein aktiver Datensatz erlaubt.','Y','Y','AD_Printer_Config_UC_Workspace_HostKey','N',TO_TIMESTAMP('2023-10-29 19:25:14','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2023-10-29T18:25:14.728Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540772 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-10-29T18:26:58.801Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active record is permitted per workspace and host key', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-29 19:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540772 AND AD_Language='en_US'
;

-- 2023-10-29T18:27:52.251Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587610,541366,540772,0,TO_TIMESTAMP('2023-10-29 19:27:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-10-29 19:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-29T18:28:10.889Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551593,541367,540772,0,'COALESCE(ConfigHostKey,'''')',TO_TIMESTAMP('2023-10-29 19:28:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-10-29 19:28:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-29T18:29:14.047Z
CREATE UNIQUE INDEX AD_Printer_Config_UC_Workspace_HostKey ON AD_Printer_Config (C_Workplace_ID,COALESCE(ConfigHostKey,'')) WHERE IsActive='Y'
;

