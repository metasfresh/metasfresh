-- 2019-07-29T13:45:52.330Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568516,187,0,19,540961,'C_BPartner_ID',TO_TIMESTAMP('2019-07-29 15:45:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','U',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2019-07-29 15:45:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T13:45:52.333Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568516 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T13:45:52.335Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2019-07-29T13:46:04.530Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2019-07-29T13:46:04.542Z
-- URL zum Konzept
ALTER TABLE C_Project_User ADD CONSTRAINT CBPartner_CProjectUser FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;


-- 2019-07-29T13:50:15.192Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-07-29 15:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568516
;

-- 2019-07-29T13:53:22.673Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=123,Updated=TO_TIMESTAMP('2019-07-29 15:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559666
;

-- 2019-07-29T13:55:27.329Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2019-07-29 15:55:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559666
;
