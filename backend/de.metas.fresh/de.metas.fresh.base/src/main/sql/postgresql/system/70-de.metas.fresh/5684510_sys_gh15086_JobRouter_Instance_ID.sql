-- 2023-04-06T20:43:40.596Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582231,0,TO_TIMESTAMP('2023-04-06 21:43:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','JobRouter Instance ID','JobRouter Instance ID',TO_TIMESTAMP('2023-04-06 21:43:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-06T20:43:40.735Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582231 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> SAP PayT ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T20:50:31.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572375,713823,0,290,0,TO_TIMESTAMP('2023-04-06 21:50:30','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','SAP PayT ID',0,340,0,1,1,TO_TIMESTAMP('2023-04-06 21:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-06T20:50:31.966Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-06T20:50:32.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-04-06T20:50:32.213Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713823
;

-- 2023-04-06T20:50:32.329Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713823)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T20:51:11.139Z
UPDATE AD_Field SET AD_Name_ID=582231, Description=NULL, Help=NULL, Name='JobRouter Instance ID',Updated=TO_TIMESTAMP('2023-04-06 21:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713823
;

-- 2023-04-06T20:51:11.270Z
UPDATE AD_Field_Trl trl SET Name='JobRouter Instance ID' WHERE AD_Field_ID=713823 AND AD_Language='en_US'
;

-- 2023-04-06T20:51:11.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582231) 
;

-- 2023-04-06T20:51:11.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713823
;

-- 2023-04-06T20:51:11.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713823)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> main -> 20 -> doc.JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T20:53:16.849Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,713823,0,290,540226,616643,'F',TO_TIMESTAMP('2023-04-06 21:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'JobRouter Instance ID',50,0,0,TO_TIMESTAMP('2023-04-06 21:53:15','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:01:04.970Z
UPDATE AD_Field SET DisplayLogic='@ExternalId@!NULL',Updated=TO_TIMESTAMP('2023-04-06 22:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713823
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:03:11.364Z
UPDATE AD_Field SET DisplayLogic='', ReadOnlyLogic='@ExternalId@!NULL',Updated=TO_TIMESTAMP('2023-04-06 22:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713823
;

-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:04:08.565Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2023-04-06 22:04:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572375
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:09:05.735Z
UPDATE AD_Field SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-04-06 22:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713823
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice(290,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T21:13:51.918Z
UPDATE AD_Field SET IsAlwaysUpdateable='', ReadOnlyLogic='@ExternalId@!NULL',Updated=TO_TIMESTAMP('2023-04-06 22:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713823
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> SAP PayT ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:07:02.324Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572375,713824,0,263,0,TO_TIMESTAMP('2023-04-06 23:06:59','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','SAP PayT ID',0,530,0,1,1,TO_TIMESTAMP('2023-04-06 23:06:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-06T22:07:02.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-06T22:07:02.827Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-04-06T22:07:02.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713824
;

-- 2023-04-06T22:07:03.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713824)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:08:06.744Z
UPDATE AD_Field SET AD_Name_ID=582231, Description=NULL, Help=NULL, Name='JobRouter Instance ID',Updated=TO_TIMESTAMP('2023-04-06 23:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713824
;

-- 2023-04-06T22:08:06.860Z
UPDATE AD_Field_Trl trl SET Name='JobRouter Instance ID' WHERE AD_Field_ID=713824 AND AD_Language='en_US'
;

-- 2023-04-06T22:08:06.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582231) 
;

-- 2023-04-06T22:08:07.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713824
;

-- 2023-04-06T22:08:07.193Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713824)
;

-- UI Element: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> main -> 20 -> dates.JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:11:37.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713824,0,263,540027,616644,'F',TO_TIMESTAMP('2023-04-06 23:11:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'JobRouter Instance ID',32,0,0,TO_TIMESTAMP('2023-04-06 23:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Invoice (Customer)_OLD(167,D) -> Invoice(263,D) -> JobRouter Instance ID
-- Column: C_Invoice.ExternalId
-- 2023-04-06T22:12:26.180Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2023-04-06 23:12:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713824
;