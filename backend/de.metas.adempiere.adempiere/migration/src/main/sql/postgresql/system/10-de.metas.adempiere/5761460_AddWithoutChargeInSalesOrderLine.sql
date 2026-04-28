-- Run mode: SWING_CLIENT

-- 2025-07-27T08:20:57.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583822,0,'IsWithoutCharge',TO_TIMESTAMP('2025-07-27 08:20:57.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Without Charge','Without Charge',TO_TIMESTAMP('2025-07-27 08:20:57.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-27T08:20:57.260Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583822 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsWithoutCharge
-- 2025-07-27T08:22:52.640Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ohne Berechnung', PrintName='Ohne Berechnung',Updated=TO_TIMESTAMP('2025-07-27 08:22:52.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583822 AND AD_Language='de_CH'
;

-- 2025-07-27T08:22:52.642Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-27T08:22:52.975Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583822,'de_CH')
;

-- Element: IsWithoutCharge
-- 2025-07-27T08:23:21.413Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ohne Berechnung', PrintName='Ohne Berechnung',Updated=TO_TIMESTAMP('2025-07-27 08:23:21.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583822 AND AD_Language='de_DE'
;

-- 2025-07-27T08:23:21.415Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-27T08:23:22.232Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583822,'de_DE')
;

-- 2025-07-27T08:23:22.233Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583822,'de_DE')
;

-- Element: IsWithoutCharge
-- 2025-07-27T08:23:31.739Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:23:31.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583822 AND AD_Language='en_US'
;

-- 2025-07-27T08:23:31.742Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583822,'en_US')
;

-- Column: C_OrderLine.IsWithoutCharge
-- 2025-07-27T08:24:03.783Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590577,583822,0,20,260,'XX','IsWithoutCharge',TO_TIMESTAMP('2025-07-27 08:24:03.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ohne Berechnung',0,0,TO_TIMESTAMP('2025-07-27 08:24:03.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-27T08:24:03.786Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590577 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-27T08:24:03.789Z
/* DDL */  select update_Column_Translation_From_AD_Element(583822)
;

-- 2025-07-27T08:24:10.438Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-07-27 08:24:10.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583822
;

-- 2025-07-27T08:24:10.441Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583822,'de_DE')
;

-- 2025-07-27T08:24:15.738Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsWithoutCharge CHAR(1) DEFAULT ''N'' CHECK (IsWithoutCharge IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_OrderLine.Reason
-- 2025-07-27T08:25:56.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590578,576788,0,14,260,'XX','Reason',TO_TIMESTAMP('2025-07-27 08:25:56.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Grund',0,0,TO_TIMESTAMP('2025-07-27 08:25:56.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-27T08:25:56.243Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-27T08:25:56.344Z
/* DDL */  select update_Column_Translation_From_AD_Element(576788)
;

-- Name: Reason for without charge
-- 2025-07-27T08:26:32.887Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541968,TO_TIMESTAMP('2025-07-27 08:26:32.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Reason for without charge',TO_TIMESTAMP('2025-07-27 08:26:32.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-07-27T08:26:32.894Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541968 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Reason for without charge
-- Value: W
-- ValueName: Warranty
-- 2025-07-27T08:27:20.423Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541968,543932,TO_TIMESTAMP('2025-07-27 08:27:20.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Warranty Case',TO_TIMESTAMP('2025-07-27 08:27:20.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'W','Warranty')
;

-- 2025-07-27T08:27:20.425Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543932 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Reason for without charge -> W_Warranty
-- 2025-07-27T08:27:26.659Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Garantiefall',Updated=TO_TIMESTAMP('2025-07-27 08:27:26.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543932
;

-- 2025-07-27T08:27:26.660Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> W_Warranty
-- 2025-07-27T08:27:30.423Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Garantiefall',Updated=TO_TIMESTAMP('2025-07-27 08:27:30.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543932
;

-- 2025-07-27T08:27:30.424Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> W_Warranty
-- 2025-07-27T08:27:32.618Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:27:32.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543932
;

-- Reference: Reason for without charge
-- Value: G
-- ValueName: Goodwill
-- 2025-07-27T08:28:02.794Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541968,543933,TO_TIMESTAMP('2025-07-27 08:28:02.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kulanz',TO_TIMESTAMP('2025-07-27 08:28:02.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'G','Goodwill')
;

-- 2025-07-27T08:28:02.796Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543933 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Reason for without charge -> G_Goodwill
-- 2025-07-27T08:28:08.628Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Goodwill',Updated=TO_TIMESTAMP('2025-07-27 08:28:08.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543933
;

-- 2025-07-27T08:28:08.629Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> G_Goodwill
-- 2025-07-27T08:28:12.336Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:28:12.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543933
;

-- Reference Item: Reason for without charge -> G_Goodwill
-- 2025-07-27T08:28:15.855Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:28:15.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543933
;

-- Reference: Reason for without charge
-- Value: F
-- ValueName: FullService
-- 2025-07-27T08:29:38.352Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541968,543934,TO_TIMESTAMP('2025-07-27 08:29:38.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Vollservice',TO_TIMESTAMP('2025-07-27 08:29:38.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'F','FullService')
;

-- 2025-07-27T08:29:38.353Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543934 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Reason for without charge -> F_FullService
-- 2025-07-27T08:29:42.679Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:29:42.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543934
;

-- Reference Item: Reason for without charge -> F_FullService
-- 2025-07-27T08:29:45.004Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:29:45.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543934
;

-- Reference Item: Reason for without charge -> F_FullService
-- 2025-07-27T08:29:52.840Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Full Service',Updated=TO_TIMESTAMP('2025-07-27 08:29:52.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543934
;

-- 2025-07-27T08:29:52.841Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: Reason for without charge
-- Value: P
-- ValueName: PromotionalCampaign
-- 2025-07-27T08:30:42.065Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541968,543935,TO_TIMESTAMP('2025-07-27 08:30:41.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Werbeaktion',TO_TIMESTAMP('2025-07-27 08:30:41.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'P','PromotionalCampaign')
;

-- 2025-07-27T08:30:42.067Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543935 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Reason for without charge -> P_PromotionalCampaign
-- 2025-07-27T08:30:51.564Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Promotional Campaign',Updated=TO_TIMESTAMP('2025-07-27 08:30:51.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543935
;

-- 2025-07-27T08:30:51.565Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> P_PromotionalCampaign
-- 2025-07-27T08:30:55.166Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:30:55.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543935
;

-- Reference Item: Reason for without charge -> P_PromotionalCampaign
-- 2025-07-27T08:30:57.511Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:30:57.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543935
;

-- Reference: Reason for without charge
-- Value: I
-- ValueName: InternalUse
-- 2025-07-27T08:32:40.734Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541968,543936,TO_TIMESTAMP('2025-07-27 08:32:40.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Interne Verwendung',TO_TIMESTAMP('2025-07-27 08:32:40.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'I','InternalUse')
;

-- 2025-07-27T08:32:40.736Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543936 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Reason for without charge -> I_InternalUse
-- 2025-07-27T08:32:47.878Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Internal Use',Updated=TO_TIMESTAMP('2025-07-27 08:32:47.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543936
;

-- 2025-07-27T08:32:47.879Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Reason for without charge -> I_InternalUse
-- 2025-07-27T08:32:50.432Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:32:50.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543936
;

-- Reference Item: Reason for without charge -> I_InternalUse
-- 2025-07-27T08:32:52.635Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-27 08:32:52.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543936
;

-- Column: C_OrderLine.Reason
-- 2025-07-27T08:33:08.840Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541968,Updated=TO_TIMESTAMP('2025-07-27 08:33:08.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590578
;

-- Column: C_OrderLine.Reason
-- 2025-07-27T08:34:08.298Z
UPDATE AD_Column SET MandatoryLogic='@IsWithoutCharge@=Y',Updated=TO_TIMESTAMP('2025-07-27 08:34:08.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590578
;

-- 2025-07-27T08:34:11.425Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN Reason VARCHAR(4000)')
;

-- Column: C_OrderLine.Reason
-- 2025-07-27T08:34:17.581Z
UPDATE AD_Column SET FieldLength=1,Updated=TO_TIMESTAMP('2025-07-27 08:34:17.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590578
;

-- 2025-07-27T08:34:20.311Z
INSERT INTO t_alter_column values('c_orderline','Reason','CHAR(1)',null,null)
;

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Ohne Berechnung
-- Column: C_OrderLine.IsWithoutCharge
-- 2025-07-27T08:35:04.045Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590577,750507,0,187,0,TO_TIMESTAMP('2025-07-27 08:35:03.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Ohne Berechnung',0,0,380,0,1,1,TO_TIMESTAMP('2025-07-27 08:35:03.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-27T08:35:04.048Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-27T08:35:04.051Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583822)
;

-- 2025-07-27T08:35:04.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750507
;

-- 2025-07-27T08:35:04.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750507)
;

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Grund
-- Column: C_OrderLine.Reason
-- 2025-07-27T08:35:12.107Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590578,750508,0,187,0,TO_TIMESTAMP('2025-07-27 08:35:11.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Grund',0,0,390,0,1,1,TO_TIMESTAMP('2025-07-27 08:35:11.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-27T08:35:12.113Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-27T08:35:12.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576788)
;

-- 2025-07-27T08:35:12.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750508
;

-- 2025-07-27T08:35:12.133Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750508)
;

-- Field: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> Grund
-- Column: C_OrderLine.Reason
-- 2025-07-27T08:35:44.456Z
UPDATE AD_Field SET DisplayLogic='@IsWithoutCharge@=Y',Updated=TO_TIMESTAMP('2025-07-27 08:35:44.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=750508
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Ohne Berechnung
-- Column: C_OrderLine.IsWithoutCharge
-- 2025-07-27T08:38:24.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750507,0,187,1000005,635382,'F',TO_TIMESTAMP('2025-07-27 08:38:24.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Ohne Berechnung',72,0,0,TO_TIMESTAMP('2025-07-27 08:38:24.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Grund
-- Column: C_OrderLine.Reason
-- 2025-07-27T08:38:35.171Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750508,0,187,1000005,635383,'F',TO_TIMESTAMP('2025-07-27 08:38:35.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Grund',73,0,0,TO_TIMESTAMP('2025-07-27 08:38:35.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Ohne Berechnung
-- Column: C_OrderLine.IsWithoutCharge
-- 2025-07-27T08:39:11.536Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635382
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Grund
-- Column: C_OrderLine.Reason
-- 2025-07-27T08:39:11.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635383
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preis
-- Column: C_OrderLine.PriceEntered
-- 2025-07-27T08:39:11.555Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000040
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preiseinheit
-- Column: C_OrderLine.Price_UOM_ID
-- 2025-07-27T08:39:11.564Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000041
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.InvoicableQtyBasedOn
-- Column: C_OrderLine.InvoicableQtyBasedOn
-- 2025-07-27T08:39:11.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560381
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Menge in Preiseinheit
-- Column: C_OrderLine.QtyEnteredInPriceUOM
-- 2025-07-27T08:39:11.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000042
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Rabatt %
-- Column: C_OrderLine.Discount
-- 2025-07-27T08:39:11.580Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000043
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Manueller Rabatt
-- Column: C_OrderLine.IsManualDiscount
-- 2025-07-27T08:39:11.586Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=578118
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Netto
-- Column: C_OrderLine.PriceActual
-- 2025-07-27T08:39:11.591Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000044
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zeilensumme
-- Column: C_OrderLine.LineNetAmt
-- 2025-07-27T08:39:11.595Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000045
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Ertrag netto
-- Column: C_OrderLine.ProfitPriceActual
-- 2025-07-27T08:39:11.600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552431
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Marge %
-- Column: C_OrderLine.ProfitPercent
-- 2025-07-27T08:39:11.604Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552430
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Auszeichnungspreis
-- Column: C_OrderLine.PriceList
-- 2025-07-27T08:39:11.615Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000046
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Steuer
-- Column: C_OrderLine.C_Tax_ID
-- 2025-07-27T08:39:11.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000047
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Beschreibung
-- Column: C_OrderLine.Description
-- 2025-07-27T08:39:11.624Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549097
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Abo-Vertragsbedingungen
-- Column: C_OrderLine.C_Flatrate_Conditions_ID
-- 2025-07-27T08:39:11.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000039
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Pauschale - Vertragsperiode
-- Column: C_OrderLine.C_Flatrate_Term_ID
-- 2025-07-27T08:39:11.633Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=601388
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppen Zeile
-- Column: C_OrderLine.IsGroupCompensationLine
-- 2025-07-27T08:39:11.638Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549127
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppe
-- Column: C_OrderLine.C_Order_CompensationGroup_ID
-- 2025-07-27T08:39:11.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549126
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preisminderung Art
-- Column: C_OrderLine.GroupCompensationType
-- 2025-07-27T08:39:11.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549129
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Preisminderung Betrag Typ
-- Column: C_OrderLine.GroupCompensationAmtType
-- 2025-07-27T08:39:11.651Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549130
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Gruppen Rabatt
-- Column: C_OrderLine.GroupCompensationPercentage
-- 2025-07-27T08:39:11.655Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=320,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=549128
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.No Price Conditions
-- Column: C_OrderLine.NoPriceConditionsColor_ID
-- 2025-07-27T08:39:11.660Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=330,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=551414
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Zahlungsbedingung
-- Column: C_OrderLine.C_PaymentTerm_Override_ID
-- 2025-07-27T08:39:11.665Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=340,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=550143
;

-- UI Element: Auftrag_OLD(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Skonto %
-- Column: C_OrderLine.PaymentDiscount
-- 2025-07-27T08:39:11.669Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=350,Updated=TO_TIMESTAMP('2025-07-27 08:39:11.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=552486
;

