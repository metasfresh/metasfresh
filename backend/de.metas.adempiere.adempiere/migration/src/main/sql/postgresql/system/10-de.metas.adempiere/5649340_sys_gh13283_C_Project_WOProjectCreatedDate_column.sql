-- Column: C_Project.WOProjectCreatedDate
-- 2022-08-03T16:21:10.442Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583870,581100,0,15,203,'WOProjectCreatedDate',TO_TIMESTAMP('2022-08-03 19:21:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem das Prüfprojekt erzeugt wurde.','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt erstellt',0,0,TO_TIMESTAMP('2022-08-03 19:21:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-03T16:21:10.448Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583870 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-03T16:21:10.496Z
/* DDL */  select update_Column_Translation_From_AD_Element(581100) 
;

-- 2022-08-03T16:21:22.040Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN WOProjectCreatedDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-08-04T06:07:35.106Z
INSERT INTO t_alter_column values('c_project_wo_resource','DurationUnit','CHAR(1)',null,'h')
;

-- 2022-08-04T06:07:35.153Z
UPDATE C_Project_WO_Resource SET DurationUnit='h' WHERE DurationUnit IS NULL
;

-- 2022-08-04T09:26:31.847Z
INSERT INTO t_alter_column values('c_project','Specialist_Consultant_ID','VARCHAR(255)',null,null)
;

