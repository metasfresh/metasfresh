
-- 2019-09-24T13:25:33.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-24 16:25:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543753
;





-- 2019-09-24T13:30:59.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GlobalId',Updated=TO_TIMESTAMP('2019-09-24 16:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568818
;

-- 2019-09-24T13:31:25.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='GlobalId',Updated=TO_TIMESTAMP('2019-09-24 16:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543753
;

-- 2019-09-24T13:31:25.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543753
;

-- 2019-09-24T13:31:25.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL, AD_Element_ID=543753 WHERE UPPER(ColumnName)='GLOBALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-24T13:31:25.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='GlobalId', Name='Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543753 AND IsCentrallyMaintained='Y'
;

DELETE FROM ad_impformat_row where ad_column_ID = (select ad_column_ID from ad_column where AD_Table_ID=533 and columnname ilike 'globalid');

DELETE FROM AD_UI_Element where ad_field_ID in ( select ad_field_ID from ad_field where ad_column_ID = (select ad_column_ID from ad_column where AD_Table_ID=533 and columnname ilike 'globalid'));

DELETE FROM AD_Field where ad_column_ID = (select ad_column_ID from ad_column where AD_Table_ID=533 and columnname ilike 'globalid');



delete from ad_column where AD_Table_ID=533 and columnname ilike 'globalid';



-- 2019-09-25T12:40:38.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568963,543753,0,10,533,'GlobalId',TO_TIMESTAMP('2019-09-25 15:40:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Global ID',0,0,TO_TIMESTAMP('2019-09-25 15:40:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-25T12:40:38.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568963 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-25T12:40:38.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543753) 
;




 DO $$ 
    BEGIN
        BEGIN
            -- 2019-09-24T13:27:17.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ perform public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN GlobalId VARCHAR(255)')
;
        EXCEPTION
            WHEN duplicate_column THEN RAISE NOTICE 'column globalid already exists in I_BPartner.';
        END;
    END;
$$;







-- 2019-09-25T12:52:32.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568963,588093,0,441,0,TO_TIMESTAMP('2019-09-25 15:52:32','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Global ID',1280,1230,0,1,1,TO_TIMESTAMP('2019-09-25 15:52:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T12:52:32.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=588093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-25T12:52:32.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543753) 
;

-- 2019-09-25T12:52:32.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=588093
;

-- 2019-09-25T12:52:32.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(588093)
;

-- 2019-09-25T12:53:15.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,588093,0,441,562682,541263,'F',TO_TIMESTAMP('2019-09-25 15:53:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'globalId',17,0,0,TO_TIMESTAMP('2019-09-25 15:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-25T12:54:26.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=61,Updated=TO_TIMESTAMP('2019-09-25 15:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562682
;










