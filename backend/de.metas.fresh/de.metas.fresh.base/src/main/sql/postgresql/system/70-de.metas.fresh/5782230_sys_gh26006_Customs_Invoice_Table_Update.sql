-- Run mode: SWING_CLIENT

-- 2025-12-18T17:26:42.607Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584381,0,'ContainerNumber',TO_TIMESTAMP('2025-12-18 17:26:41.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Containernummer','Containernummer',TO_TIMESTAMP('2025-12-18 17:26:41.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T17:26:42.676Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584381 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ContainerNumber
-- 2025-12-18T17:27:04.141Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Container Number', PrintName='Container Number',Updated=TO_TIMESTAMP('2025-12-18 17:27:04.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584381 AND AD_Language='en_US'
;

-- 2025-12-18T17:27:04.200Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T17:27:08.857Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584381,'en_US')
;

-- 2025-12-18T17:27:41.417Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584382,0,'SealNumber',TO_TIMESTAMP('2025-12-18 17:27:40.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Plombennummer','Plombennummer',TO_TIMESTAMP('2025-12-18 17:27:40.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T17:27:41.477Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584382 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SealNumber
-- 2025-12-18T17:27:56.830Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Seal Number', PrintName='Seal Number',Updated=TO_TIMESTAMP('2025-12-18 17:27:56.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584382 AND AD_Language='en_US'
;

-- 2025-12-18T17:27:56.887Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T17:28:06.261Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584382,'en_US')
;

-- 2025-12-18T17:28:59.781Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584383,0,'ModeOfTransport',TO_TIMESTAMP('2025-12-18 17:28:59.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Transportmittel','Transportmittel',TO_TIMESTAMP('2025-12-18 17:28:59.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T17:28:59.842Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584383 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ModeOfTransport
-- 2025-12-18T17:29:21.050Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mode Of Transport', PrintName='Mode Of Transport',Updated=TO_TIMESTAMP('2025-12-18 17:29:21.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584383 AND AD_Language='en_US'
;

-- 2025-12-18T17:29:21.111Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T17:29:25.897Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584383,'en_US')
;

-- Column: C_Customs_Invoice.ContainerNumber
-- 2025-12-18T17:30:25.668Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591809,584381,0,10,541360,'XX','ContainerNumber',TO_TIMESTAMP('2025-12-18 17:30:24.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Containernummer',0,0,TO_TIMESTAMP('2025-12-18 17:30:24.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-18T17:30:25.728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591809 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-18T17:30:25.852Z
/* DDL */  select update_Column_Translation_From_AD_Element(584381)
;

-- 2025-12-18T17:30:40.729Z
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN ContainerNumber VARCHAR(30)')
;

-- Column: C_Customs_Invoice.SealNumber
-- 2025-12-18T17:31:03.105Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591810,584382,0,10,541360,'XX','SealNumber',TO_TIMESTAMP('2025-12-18 17:31:02.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Plombennummer',0,0,TO_TIMESTAMP('2025-12-18 17:31:02.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-18T17:31:03.167Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-18T17:31:05.616Z
/* DDL */  select update_Column_Translation_From_AD_Element(584382)
;

-- 2025-12-18T17:31:35.440Z
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN SealNumber VARCHAR(30)')
;

-- Column: C_Customs_Invoice.C_Incoterms_ID
-- 2025-12-18T17:32:14.746Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591811,579927,0,30,541534,541360,'XX','C_Incoterms_ID',TO_TIMESTAMP('2025-12-18 17:32:11.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms',0,0,TO_TIMESTAMP('2025-12-18 17:32:11.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-18T17:32:14.809Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-18T17:32:14.931Z
/* DDL */  select update_Column_Translation_From_AD_Element(579927)
;

-- 2025-12-18T17:32:30.341Z
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

-- 2025-12-18T17:32:30.402Z
ALTER TABLE C_Customs_Invoice ADD CONSTRAINT CIncoterms_CCustomsInvoice FOREIGN KEY (C_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- Name: Mode Of Transport
-- 2025-12-18T17:34:00.692Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542040,TO_TIMESTAMP('2025-12-18 17:34:00.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Mode Of Transport',TO_TIMESTAMP('2025-12-18 17:34:00.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-12-18T17:34:00.752Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542040 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Mode Of Transport
-- Value: ROD
-- ValueName: Straßenverkehr
-- 2025-12-18T17:34:55.196Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542040,544087,TO_TIMESTAMP('2025-12-18 17:34:54.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Straßenverkehr',TO_TIMESTAMP('2025-12-18 17:34:54.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ROD','Straßenverkehr')
;

-- 2025-12-18T17:34:55.258Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544087 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Mode Of Transport -> ROD_Straßenverkehr
-- 2025-12-18T17:35:09.910Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Road transport',Updated=TO_TIMESTAMP('2025-12-18 17:35:09.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544087
;

-- 2025-12-18T17:35:09.971Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: Mode Of Transport
-- Value: RAL
-- ValueName: Schienentransport
-- 2025-12-18T17:35:52.725Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542040,544088,TO_TIMESTAMP('2025-12-18 17:35:51.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Schienentransport',TO_TIMESTAMP('2025-12-18 17:35:51.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'RAL','Schienentransport')
;

-- 2025-12-18T17:35:52.787Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544088 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Mode Of Transport -> RAL_Schienentransport
-- 2025-12-18T17:36:08.405Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Rail transport',Updated=TO_TIMESTAMP('2025-12-18 17:36:08.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544088
;

-- 2025-12-18T17:36:08.465Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: Mode Of Transport
-- Value: AIR
-- ValueName: Luftfracht
-- 2025-12-18T17:36:25.672Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542040,544089,TO_TIMESTAMP('2025-12-18 17:36:24.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Luftfracht',TO_TIMESTAMP('2025-12-18 17:36:24.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AIR','Luftfracht')
;

-- 2025-12-18T17:36:25.733Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544089 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Mode Of Transport -> AIR_Luftfracht
-- 2025-12-18T17:36:35.708Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Air freight',Updated=TO_TIMESTAMP('2025-12-18 17:36:35.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544089
;

-- 2025-12-18T17:36:35.768Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: Mode Of Transport
-- Value: SEA
-- ValueName: Seefracht
-- 2025-12-18T17:36:54.580Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542040,544090,TO_TIMESTAMP('2025-12-18 17:36:53.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Seefracht',TO_TIMESTAMP('2025-12-18 17:36:53.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SEA','Seefracht')
;

-- 2025-12-18T17:36:54.639Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544090 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Mode Of Transport -> SEA_Seefracht
-- 2025-12-18T17:37:05.422Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Sea freight',Updated=TO_TIMESTAMP('2025-12-18 17:37:05.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544090
;

-- 2025-12-18T17:37:05.482Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: Mode Of Transport
-- Value: CMB
-- ValueName: Kombinierter Transport
-- 2025-12-18T17:37:26.769Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542040,544091,TO_TIMESTAMP('2025-12-18 17:37:26.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Kombinierter Transport',TO_TIMESTAMP('2025-12-18 17:37:26.061000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CMB','Kombinierter Transport')
;

-- 2025-12-18T17:37:26.828Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544091 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Mode Of Transport -> CMB_Kombinierter Transport
-- 2025-12-18T17:37:33.731Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Combined transport',Updated=TO_TIMESTAMP('2025-12-18 17:37:33.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544091
;

-- 2025-12-18T17:37:33.791Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Column: C_Customs_Invoice.ModeOfTransport
-- 2025-12-18T17:37:53.969Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591812,584383,0,17,542040,541360,'XX','ModeOfTransport',TO_TIMESTAMP('2025-12-18 17:37:52.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Transportmittel',0,0,TO_TIMESTAMP('2025-12-18 17:37:52.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-18T17:37:54.030Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-18T17:37:54.164Z
/* DDL */  select update_Column_Translation_From_AD_Element(584383)
;

-- 2025-12-18T17:38:07.748Z
/* DDL */ SELECT public.db_alter_table('C_Customs_Invoice','ALTER TABLE public.C_Customs_Invoice ADD COLUMN ModeOfTransport VARCHAR(3)')
;

