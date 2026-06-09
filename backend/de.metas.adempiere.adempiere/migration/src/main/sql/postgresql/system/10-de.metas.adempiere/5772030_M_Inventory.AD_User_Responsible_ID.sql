-- Run mode: SWING_CLIENT

-- Column: M_Inventory.AD_User_Responsible_ID
-- 2025-10-01T19:24:36.071Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591238,542007,0,30,110,321,'XX','AD_User_Responsible_ID',TO_TIMESTAMP('2025-10-01 19:24:35.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.workflow',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verantwortlicher Benutzer',0,0,TO_TIMESTAMP('2025-10-01 19:24:35.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-01T19:24:36.082Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-01T19:24:36.093Z
/* DDL */  select update_Column_Translation_From_AD_Element(542007)
;

-- 2025-10-01T19:24:37.539Z
/* DDL */ SELECT public.db_alter_table('M_Inventory','ALTER TABLE public.M_Inventory ADD COLUMN AD_User_Responsible_ID NUMERIC(10)')
;

-- 2025-10-01T19:24:37.907Z
ALTER TABLE M_Inventory ADD CONSTRAINT ADUserResponsible_MInventory FOREIGN KEY (AD_User_Responsible_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Inventory.AD_User_Responsible_ID
-- 2025-10-01T19:30:25.498Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-10-01 19:30:25.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591238
;

