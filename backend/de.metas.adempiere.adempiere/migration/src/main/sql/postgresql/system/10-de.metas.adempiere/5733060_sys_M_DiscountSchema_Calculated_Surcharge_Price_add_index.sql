-- 2024-09-11T10:43:16.583Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540804,0,542431,TO_TIMESTAMP('2024-09-11 10:43:16.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Es darf nur einen aktiven Eintrag pro Region geben.','Y','Y','M_DiscountSchema_Calculated_Surcharge_Price_Unique_Region','N',TO_TIMESTAMP('2024-09-11 10:43:16.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y''')
;

-- 2024-09-11T10:43:16.586Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540804 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-09-11T10:48:25.803Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active record per region is allowed', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 10:48:25.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540804 AND AD_Language='en_US'
;

-- 2024-09-11T10:48:29.937Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 10:48:29.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540804 AND AD_Language='de_DE'
;

-- 2024-09-11T10:48:31.569Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 10:48:31.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540804 AND AD_Language='de_CH'
;

-- 2024-09-11T12:56:53.996Z
CREATE UNIQUE INDEX M_DiscountSchema_Calculated_Surcharge_Price_Unique_Region ON M_DiscountSchema_Calculated_Surcharge_Price (C_Region_ID) WHERE IsActive='Y'
;

-- 2024-09-11T12:46:32.944Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588960,541429,540804,0,TO_TIMESTAMP('2024-09-11 12:46:32.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2024-09-11 12:46:32.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T12:56:04.792Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540806,0,542431,TO_TIMESTAMP('2024-09-11 12:56:04.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Es darf nur einen aktiven Eintrag ohne Region geben.','Y','Y','M_DiscountSchema_Calculated_Surcharge_Price_Unique_No_Region','N',TO_TIMESTAMP('2024-09-11 12:56:04.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y'' AND C_Region_ID IS  NULL')
;

-- 2024-09-11T12:56:04.794Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540806 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-09-11T12:56:42.988Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one active record without region is allowed.', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 12:56:42.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540806 AND AD_Language='en_US'
;

-- 2024-09-11T12:56:43.731Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 12:56:43.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540806 AND AD_Language='de_DE'
;

-- 2024-09-11T12:56:45.423Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 12:56:45.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540806 AND AD_Language='de_CH'
;

-- 2024-09-11T13:07:09.001Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588953,541430,540806,0,TO_TIMESTAMP('2024-09-11 13:07:08.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2024-09-11 13:07:08.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T13:07:39.575Z
CREATE UNIQUE INDEX M_DiscountSchema_Calculated_Surcharge_Price_Unique_No_Region ON M_DiscountSchema_Calculated_Surcharge_Price (IsActive) WHERE IsActive='Y' AND C_Region_ID IS  NULL
;

-- 2024-09-11T13:13:06.391Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540807,0,542431,TO_TIMESTAMP('2024-09-11 13:13:06.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Der Name muss eindeutig sein.','Y','Y','M_DiscountSchema_Calculated_Surcharge_Price_Unique_Name','N',TO_TIMESTAMP('2024-09-11 13:13:06.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'')
;

-- 2024-09-11T13:13:06.392Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540807 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-09-11T13:13:25.862Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='The Name needs to be unique.',Updated=TO_TIMESTAMP('2024-09-11 13:13:25.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540807 AND AD_Language='en_US'
;

-- 2024-09-11T13:13:32.822Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 13:13:32.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540807 AND AD_Language='en_US'
;

-- 2024-09-11T13:13:33.982Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 13:13:33.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540807 AND AD_Language='de_CH'
;

-- 2024-09-11T13:13:34.741Z
UPDATE AD_Index_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 13:13:34.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540807 AND AD_Language='de_DE'
;

-- 2024-09-11T13:14:21.704Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,588957,541431,540807,0,TO_TIMESTAMP('2024-09-11 13:14:21.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2024-09-11 13:14:21.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T13:15:31.539Z
CREATE UNIQUE INDEX M_DiscountSchema_Calculated_Surcharge_Price_Unique_Name ON M_DiscountSchema_Calculated_Surcharge_Price (Name)
;
