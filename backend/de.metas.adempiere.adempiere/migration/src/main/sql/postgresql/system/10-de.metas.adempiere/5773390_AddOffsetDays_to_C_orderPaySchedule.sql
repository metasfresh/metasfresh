-- Run mode: SWING_CLIENT

-- Column: C_OrderPaySchedule.OffsetDays
-- 2025-10-15T16:05:50.403Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591340,577683,0,11,542539,'XX','OffsetDays',TO_TIMESTAMP('2025-10-15 16:05:50.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','0','de.metas.serviceprovider',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Offset days',0,0,TO_TIMESTAMP('2025-10-15 16:05:50.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-15T16:05:50.410Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591340 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-15T16:05:50.441Z
/* DDL */  select update_Column_Translation_From_AD_Element(577683)
;

-- 2025-10-15T16:05:57.670Z
/* DDL */ SELECT public.db_alter_table('C_OrderPaySchedule','ALTER TABLE public.C_OrderPaySchedule ADD COLUMN OffsetDays NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Column: C_OrderPaySchedule.OffsetDays
-- 2025-10-15T16:08:41.560Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-15 16:08:41.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591340
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Verbuchungsfehler
-- Column: C_Order.PostingError_Issue_ID
-- 2025-10-16T09:51:56.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570876,754969,0,294,TO_TIMESTAMP('2025-10-16 09:51:56.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2025-10-16 09:51:56.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:56.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:56.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755)
;

-- 2025-10-16T09:51:56.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754969
;

-- 2025-10-16T09:51:56.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754969)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Lieferinformationen
-- Column: C_Order.DeliveryInfo
-- 2025-10-16T09:51:57.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571551,754970,0,294,TO_TIMESTAMP('2025-10-16 09:51:56.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Lieferinformationen',TO_TIMESTAMP('2025-10-16 09:51:56.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.102Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.105Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578126)
;

-- 2025-10-16T09:51:57.111Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754970
;

-- 2025-10-16T09:51:57.113Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754970)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Sales Responsible
-- Column: C_Order.SalesRepIntern_ID
-- 2025-10-16T09:51:57.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572014,754971,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sales Responsible Internal',10,'D','','Y','N','N','N','N','N','N','N','Sales Responsible',TO_TIMESTAMP('2025-10-16 09:51:57.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385)
;

-- 2025-10-16T09:51:57.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754971
;

-- 2025-10-16T09:51:57.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754971)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Gesamtbeträge drucken
-- Column: C_Order.PRINTER_OPTS_IsPrintTotals
-- 2025-10-16T09:51:57.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572179,754972,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Gesamtbeträge drucken',TO_TIMESTAMP('2025-10-16 09:51:57.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.381Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578552)
;

-- 2025-10-16T09:51:57.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754972
;

-- 2025-10-16T09:51:57.388Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754972)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Rahmenvertrag
-- Column: C_Order.C_FrameAgreement_Order_ID
-- 2025-10-16T09:51:57.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573002,754973,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rahmenvertrag Referenz',10,'D','Rahmenvertrag Referenz','Y','N','N','N','N','N','N','N','Rahmenvertrag',TO_TIMESTAMP('2025-10-16 09:51:57.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783)
;

-- 2025-10-16T09:51:57.495Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754973
;

-- 2025-10-16T09:51:57.497Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754973)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Standort (Address)
-- Column: C_Order.C_BPartner_Location_Value_ID
-- 2025-10-16T09:51:57.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573510,754974,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','','Y','N','N','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2025-10-16 09:51:57.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023)
;

-- 2025-10-16T09:51:57.619Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754974
;

-- 2025-10-16T09:51:57.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754974)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Rechnungsstandort (Address)
-- Column: C_Order.Bill_Location_Value_ID
-- 2025-10-16T09:51:57.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573511,754975,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','Y','N','N','N','N','N','N','N','Rechnungsstandort (Address)',TO_TIMESTAMP('2025-10-16 09:51:57.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579024)
;

-- 2025-10-16T09:51:57.734Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754975
;

-- 2025-10-16T09:51:57.735Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754975)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Lieferadresse (Address)
-- Column: C_Order.DropShip_Location_Value_ID
-- 2025-10-16T09:51:57.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573512,754976,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Business Partner Location for shipping to',10,'D','Y','N','N','N','N','N','N','N','Lieferadresse (Address)',TO_TIMESTAMP('2025-10-16 09:51:57.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.841Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.844Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579025)
;

-- 2025-10-16T09:51:57.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754976
;

-- 2025-10-16T09:51:57.850Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754976)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Übergabeadresse (Address)
-- Column: C_Order.HandOver_Location_Value_ID
-- 2025-10-16T09:51:57.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573513,754977,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Übergabeadresse (Address)',TO_TIMESTAMP('2025-10-16 09:51:57.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:57.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:57.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579026)
;

-- 2025-10-16T09:51:57.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754977
;

-- 2025-10-16T09:51:57.961Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754977)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Async Batch
-- Column: C_Order.C_Async_Batch_ID
-- 2025-10-16T09:51:58.065Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575255,754978,0,294,TO_TIMESTAMP('2025-10-16 09:51:57.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Async Batch',TO_TIMESTAMP('2025-10-16 09:51:57.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.068Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.071Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542581)
;

-- 2025-10-16T09:51:58.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754978
;

-- 2025-10-16T09:51:58.089Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754978)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Apotheke
-- Column: C_Order.C_BPartner_Pharmacy_ID
-- 2025-10-16T09:51:58.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577420,754979,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Apotheke',TO_TIMESTAMP('2025-10-16 09:51:58.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579045)
;

-- 2025-10-16T09:51:58.201Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754979
;

-- 2025-10-16T09:51:58.202Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754979)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Name Geschäftspartner
-- Column: C_Order.BPartnerName
-- 2025-10-16T09:51:58.305Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578892,754980,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2025-10-16 09:51:58.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.308Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.309Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350)
;

-- 2025-10-16T09:51:58.319Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754980
;

-- 2025-10-16T09:51:58.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754980)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> eMail
-- Column: C_Order.EMail
-- 2025-10-16T09:51:58.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578893,754981,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2025-10-16 09:51:58.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2025-10-16T09:51:58.465Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754981
;

-- 2025-10-16T09:51:58.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754981)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Telefon
-- Column: C_Order.Phone
-- 2025-10-16T09:51:58.574Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578894,754982,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beschreibt eine Telefon Nummer',40,'D','','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2025-10-16 09:51:58.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.577Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.579Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505)
;

-- 2025-10-16T09:51:58.603Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754982
;

-- 2025-10-16T09:51:58.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754982)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Doc Sub Type
-- Column: C_Order.DocSubType
-- 2025-10-16T09:51:58.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579337,754983,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document Sub Type',100,'D','The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','N','N','N','N','N','N','Doc Sub Type',TO_TIMESTAMP('2025-10-16 09:51:58.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1018)
;

-- 2025-10-16T09:51:58.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754983
;

-- 2025-10-16T09:51:58.724Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754983)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Gültig bis
-- Column: C_Order.ValidUntil
-- 2025-10-16T09:51:58.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589447,754984,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Gültig bis',TO_TIMESTAMP('2025-10-16 09:51:58.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.833Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583376)
;

-- 2025-10-16T09:51:58.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754984
;

-- 2025-10-16T09:51:58.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754984)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Versions-Nr.
-- Column: C_Order.VersionNo
-- 2025-10-16T09:51:58.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589448,754985,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Versionsnummer',10,'D','Y','N','N','N','N','N','N','N','Versions-Nr.',TO_TIMESTAMP('2025-10-16 09:51:58.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:58.960Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:58.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1949)
;

-- 2025-10-16T09:51:58.980Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754985
;

-- 2025-10-16T09:51:58.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754985)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Versand nach obigem Datum
-- Column: C_Order.IsFixedDatePromised
-- 2025-10-16T09:51:59.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590501,754986,0,294,TO_TIMESTAMP('2025-10-16 09:51:58.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stellt sicher, dass der Versand nicht vor dem zugesagten Termin erfolgt. Wird verwendet, wenn der Kunde eine genaue Einhaltung des Lieferzeitpunkts verlangt.',1,'D','Y','N','N','N','N','N','N','N','Versand nach obigem Datum',TO_TIMESTAMP('2025-10-16 09:51:58.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:59.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:59.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583783)
;

-- 2025-10-16T09:51:59.093Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754986
;

-- 2025-10-16T09:51:59.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754986)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Kommissionierung nach obigem Datum
-- Column: C_Order.IsFixedPreparationDate
-- 2025-10-16T09:51:59.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590502,754987,0,294,TO_TIMESTAMP('2025-10-16 09:51:59.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verhindert die Kommissionierung vor dem Bereitstellungsdatum. Wird verwendet, wenn Materialien oder Waren nicht vor dem geplanten Zeitpunkt bereitgestellt werden dürfen.',1,'D','Y','N','N','N','N','N','N','N','Kommissionierung nach obigem Datum',TO_TIMESTAMP('2025-10-16 09:51:59.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:59.199Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:59.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583784)
;

-- 2025-10-16T09:51:59.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754987
;

-- 2025-10-16T09:51:59.208Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754987)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Auf Kommission bis
-- Column: C_Order.Commission_DateFrom
-- 2025-10-16T09:51:59.313Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590904,754988,0,294,TO_TIMESTAMP('2025-10-16 09:51:59.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Auf Kommission bis',TO_TIMESTAMP('2025-10-16 09:51:59.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:59.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:59.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583965)
;

-- 2025-10-16T09:51:59.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754988
;

-- 2025-10-16T09:51:59.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754988)
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Akkreditivdatum
-- Column: C_Order.LC_Date
-- 2025-10-16T09:51:59.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591278,754989,0,294,TO_TIMESTAMP('2025-10-16 09:51:59.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Akkreditivdatum',TO_TIMESTAMP('2025-10-16 09:51:59.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T09:51:59.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T09:51:59.431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584099)
;

-- 2025-10-16T09:51:59.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754989
;

-- 2025-10-16T09:51:59.437Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754989)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> main -> 20 -> dates.Akkreditivdatum
-- Column: C_Order.LC_Date
-- 2025-10-16T09:52:45.523Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754989,0,294,540076,637868,'F',TO_TIMESTAMP('2025-10-16 09:52:45.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Akkreditivdatum',60,0,0,TO_TIMESTAMP('2025-10-16 09:52:45.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

