-- Run mode: SWING_CLIENT

-- 2025-10-18T12:40:19.365Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584122,0,TO_TIMESTAMP('2025-10-18 12:40:19.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Fehlermeldung zur Lieferwegauswahl','Fehlermeldung zur Lieferwegauswahl',TO_TIMESTAMP('2025-10-18 12:40:19.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-18T12:40:19.367Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584122 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-18T12:40:28.539Z
UPDATE AD_Element_Trl SET Name='Carrier Advice Error Message', PrintName='Carrier Advice Error Message',Updated=TO_TIMESTAMP('2025-10-18 12:40:28.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584122 AND AD_Language='en_US'
;

-- 2025-10-18T12:40:28.540Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-18T12:40:28.853Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584122,'en_US')
;

-- 2025-10-18T14:19:10.346Z
UPDATE AD_Element SET ColumnName='CarrierAdviceErrorMsg',Updated=TO_TIMESTAMP('2025-10-18 14:19:10.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584122
;

-- 2025-10-18T14:19:10.348Z
UPDATE AD_Column SET ColumnName='CarrierAdviceErrorMsg' WHERE AD_Element_ID=584122
;

-- 2025-10-18T14:19:10.349Z
UPDATE AD_Process_Para SET ColumnName='CarrierAdviceErrorMsg' WHERE AD_Element_ID=584122
;

-- 2025-10-18T14:19:10.352Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584122,'de_DE')
;

-- Column: M_ShipmentSchedule.CarrierAdviceErrorMsg
-- 2025-10-18T14:20:07.426Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591388,584122,0,14,500221,'XX','CarrierAdviceErrorMsg',TO_TIMESTAMP('2025-10-18 14:20:07.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.inoutcandidate',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehlermeldung zur Lieferwegauswahl',0,0,TO_TIMESTAMP('2025-10-18 14:20:07.259000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-18T14:20:07.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-18T14:20:07.539Z
/* DDL */  select update_Column_Translation_From_AD_Element(584122)
;

-- 2025-10-18T14:20:09.370Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN CarrierAdviceErrorMsg VARCHAR(2000)')
;

-- Reference: Carrier_Advising_Status
-- Value: REQ
-- ValueName: Requested
-- 2025-10-18T14:31:55.809Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=544002
;

-- 2025-10-18T14:31:55.814Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=544002
;

