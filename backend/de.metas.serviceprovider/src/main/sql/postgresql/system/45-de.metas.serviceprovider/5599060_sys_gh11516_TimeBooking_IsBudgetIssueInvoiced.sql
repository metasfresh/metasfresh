-- 2021-07-19T09:57:44.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575123,579439,0,20,541443,'IsBudgetIssueInvoiced','(select (WITH RECURSIVE issue_hierarchy AS (SELECT s_issue_id, s_parent_issue_id, status                                     from s_issue startingPoint                                     where startingPoint.s_issue_id = s_issue.s_issue_id                                     UNION ALL                                     SELECT iss.s_issue_id, iss.s_parent_issue_id, iss.status                                     from s_issue iss                                              INNER JOIN issue_hierarchy eh                                                         on iss.s_issue_id = eh.s_parent_issue_id and iss.status != ''Invoiced'')  select case             when exists(SELECT 1                         from issue_hierarchy resultHierarchy                                  inner join s_issue parentIssue                                             on resultHierarchy.s_parent_issue_id = parentIssue.s_issue_id                         where parentIssue.status = ''Invoiced'') then ''Y''             else ''N'' end) from s_issue where s_issue_id = s_timebooking.s_issue_id)',TO_TIMESTAMP('2021-07-19 12:57:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.serviceprovider',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Bugdet-Issue abgerechnet',0,0,TO_TIMESTAMP('2021-07-19 12:57:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-19T09:57:44.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575123 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-19T09:57:44.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579439) 
;

-- 2021-07-19T10:00:23.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575123,650398,0,542445,0,TO_TIMESTAMP('2021-07-19 13:00:23','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.serviceprovider',0,'Y','N','N','N','N','N','N','N','Bugdet-Issue abgerechnet',1,1,TO_TIMESTAMP('2021-07-19 13:00:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-19T10:00:23.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650398 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-19T10:00:23.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579439) 
;

-- 2021-07-19T10:00:23.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650398
;

-- 2021-07-19T10:00:23.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(650398)
;

-- 2021-07-19T10:01:46.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,650398,0,542445,587130,543654,'F',TO_TIMESTAMP('2021-07-19 13:01:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Bugdet-Issue abgerechnet',25,0,0,TO_TIMESTAMP('2021-07-19 13:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-19T10:01:56.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-07-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=587130
;

-- 2021-07-19T10:01:56.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-07-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567563
;

-- 2021-07-19T10:01:56.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-07-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567565
;

-- 2021-07-19T10:01:56.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-07-19 13:01:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567562
;


-- 2021-07-19T12:28:45.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select (WITH RECURSIVE issue_hierarchy AS (SELECT s_issue_id, s_parent_issue_id, status                                                    from s_issue startingPoint                                                    where startingPoint.s_issue_id = s_issue.s_issue_id                                                    UNION ALL                                                    SELECT iss.s_issue_id, iss.s_parent_issue_id, iss.status                                                    from s_issue iss                                                             INNER JOIN issue_hierarchy eh                                                                        on iss.s_issue_id = eh.s_parent_issue_id and eh.status != ''Invoiced'')                 select case                            when exists(SELECT 1                                        from issue_hierarchy resultHierarchy                                        where resultHierarchy.status = ''Invoiced'') then ''Y''                            else ''N'' end)         from s_issue         where s_issue_id = s_timebooking.s_issue_id)',Updated=TO_TIMESTAMP('2021-07-19 15:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575123
;

-- 2021-07-19T14:39:29.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Table_ID,SQL_GetTargetRecordIdBySourceRecordId,Updated,UpdatedBy) VALUES (0,575123,0,540045,541443,TO_TIMESTAMP('2021-07-19 17:39:29','YYYY-MM-DD HH24:MI:SS'),100,'S','Y',541468,'WITH RECURSIVE issue_hierarchy AS (SELECT startingPoint.s_issue_id, s_parent_issue_id, status
                                   from s_issue startingPoint
                                            inner join s_timebooking tb on startingPoint.s_issue_id  = tb.s_issue_id and tb.s_timebooking_id = @Record_ID / -1@
                                   UNION ALL
                                   SELECT iss.s_issue_id, iss.s_parent_issue_id, iss.status
                                   from s_issue iss
                                            INNER JOIN issue_hierarchy eh
                                                       on iss.s_issue_id = eh.s_parent_issue_id and eh.status != ''Invoiced'')
select resultHierarchy.s_issue_id
from issue_hierarchy resultHierarchy
where resultHierarchy.status = ''Invoiced''',TO_TIMESTAMP('2021-07-19 17:39:29','YYYY-MM-DD HH24:MI:SS'),100)
;
