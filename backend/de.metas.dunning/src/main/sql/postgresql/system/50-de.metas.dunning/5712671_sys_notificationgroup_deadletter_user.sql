-- Field: Benachrichtigungsgruppe -> Notification Group -> Nur Benutzer der Partner der Organisation benachrichtigen
-- Column: AD_NotificationGroup.IsNotifyOrgBPUsersOnly
-- 2023-12-12T23:21:11.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587669,723200,0,541061,0,TO_TIMESTAMP('2023-12-13 01:21:11','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Nur Benutzer der Partner der Organisation benachrichtigen',0,60,0,1,1,TO_TIMESTAMP('2023-12-13 01:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-12T23:21:11.490Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-12T23:21:11.492Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582827)
;

-- 2023-12-12T23:21:11.495Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723200
;

-- 2023-12-12T23:21:11.496Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723200)
;

-- Field: Benachrichtigungsgruppe -> Notification Group -> Unzustellbare E-Mails weiterleiten an
-- Column: AD_NotificationGroup.Deadletter_User_ID
-- 2023-12-12T23:21:23.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587668,723201,0,541061,0,TO_TIMESTAMP('2023-12-13 01:21:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Unzustellbare E-Mails weiterleiten an',0,70,0,1,1,TO_TIMESTAMP('2023-12-13 01:21:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-12-12T23:21:23.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-12T23:21:23.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582825)
;

-- 2023-12-12T23:21:23.170Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723201
;

-- 2023-12-12T23:21:23.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723201)
;

-- Field: Benachrichtigungsgruppe -> Notification Group -> Entitäts-Art
-- Column: AD_NotificationGroup.EntityType
-- 2023-12-12T23:22:25.747Z
UPDATE AD_Field SET SeqNo=70,Updated=TO_TIMESTAMP('2023-12-13 01:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563171
;

-- Field: Benachrichtigungsgruppe -> Notification Group -> Nur Benutzer der Partner der Organisation benachrichtigen
-- Column: AD_NotificationGroup.IsNotifyOrgBPUsersOnly
-- 2023-12-12T23:22:28.648Z
UPDATE AD_Field SET SeqNo=50,Updated=TO_TIMESTAMP('2023-12-13 01:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723200
;

-- Field: Benachrichtigungsgruppe -> Notification Group -> Unzustellbare E-Mails weiterleiten an
-- Column: AD_NotificationGroup.Deadletter_User_ID
-- 2023-12-12T23:22:43.112Z
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2023-12-13 01:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=723201
;

