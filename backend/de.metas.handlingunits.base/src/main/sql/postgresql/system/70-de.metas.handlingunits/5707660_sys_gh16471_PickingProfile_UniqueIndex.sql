-- 2023-10-18T15:11:09.523Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540756,0,542373,TO_TIMESTAMP('2023-10-18 18:11:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Nur ein aktives Mobile UI Kommissionierprofil ist erlaubt','Y','Y','IDX_unique_UserProfilePicking','N',TO_TIMESTAMP('2023-10-18 18:11:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-18T15:11:09.537Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540756 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-10-18T15:12:00.774Z
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2023-10-18 18:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540756
;

-- 2023-10-18T15:12:22.386Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587558,541350,540756,0,TO_TIMESTAMP('2023-10-18 18:12:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-10-18 18:12:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-18T15:12:37.538Z
CREATE UNIQUE INDEX IDX_unique_UserProfilePicking ON MobileUI_UserProfile_Picking (IsActive) WHERE IsActive='Y'
;

-- 2023-10-18T15:13:05.652Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active Mobile UI Picking Profile is allowed',Updated=TO_TIMESTAMP('2023-10-18 18:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540756 AND AD_Language='en_US'
;

