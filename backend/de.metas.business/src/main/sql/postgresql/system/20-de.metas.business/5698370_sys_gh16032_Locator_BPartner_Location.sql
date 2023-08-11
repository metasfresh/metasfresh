-- Name: C_Bpartner_Location_ID
-- 2023-08-08T16:26:07.424616400Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540654,'C_BPartner_Location.C_BPartner_ID=@C_BPartner_ID@ AND C_BPartner_Location.IsActive=''Y''',TO_TIMESTAMP('2023-08-08 19:26:07.286','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','C_Bpartner_Location_ID','S',TO_TIMESTAMP('2023-08-08 19:26:07.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Locator.C_BPartner_Location_ID
-- 2023-08-08T16:27:19.365580900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587255,189,0,19,207,540654,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-08-08 19:27:19.121','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2023-08-08 19:27:19.121','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-08T16:27:19.369621100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587255 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-08T16:27:19.925361600Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-08-08T16:27:21.735796300Z
/* DDL */ SELECT public.db_alter_table('M_Locator','ALTER TABLE public.M_Locator ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-08-08T16:27:22.192338600Z
ALTER TABLE M_Locator ADD CONSTRAINT CBPartnerLocation_MLocator FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Locator.C_BPartner_Location_ID
-- 2023-08-08T16:31:29.503030400Z
UPDATE AD_Column SET DefaultValue='@C_BPartner_Location_ID@',Updated=TO_TIMESTAMP('2023-08-08 19:31:29.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587255
;

