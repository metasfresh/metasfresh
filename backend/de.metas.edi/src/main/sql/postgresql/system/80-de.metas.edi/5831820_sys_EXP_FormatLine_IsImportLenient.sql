-- Opt-in flag for lenient lookups in the EXP_Format import: when a lenient line's value finds no row, the lookup
-- is retried with that line's condition replaced by "IS NULL". Default 'N' leaves every existing format line unchanged.
-- Also adds the message used when the lenient retry stays ambiguous and no replication-lookup default exists.

-- Element: IsImportLenient
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585568 /*From ID Server*/,0,'IsImportLenient',TO_TIMESTAMP('2026-10-28 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Wenn die Suche in einem Import keinen Treffer liefert, wird sie ohne diese Zeile wiederholt.','EE05','Wenn aktiv und der Wert dieser Zeile in den Stammdaten nicht gefunden wird, wird die Suche erneut ausgeführt und diese Zeile dabei nur auf "nicht gesetzt" geprüft. Mehrdeutige Treffer werden wie üblich über den Standard der Replikationssuche aufgelöst.','Y','Import tolerant','Import tolerant',TO_TIMESTAMP('2026-10-28 14:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.Help,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585568 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Import lenient',PrintName='Import lenient',Description='If the lookup of an import finds no match, it is repeated without this line.',Help='If active and this line''s value is not found in the masterdata, the lookup is repeated with this line checked only for "not set". Ambiguous matches are resolved as usual via the replication-lookup default.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 14:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585568 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 14:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585568 AND AD_Language IN ('de_DE','de_CH')
;

-- Column: EXP_FormatLine.IsImportLenient
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593764 /*From ID Server*/,585568,0,20,53073,'IsImportLenient',TO_TIMESTAMP('2026-10-28 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE05',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Import tolerant','NP',0,0,TO_TIMESTAMP('2026-10-28 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585568)
;

/* DDL */ SELECT public.db_alter_table('EXP_FormatLine','ALTER TABLE public.EXP_FormatLine ADD COLUMN IsImportLenient CHAR(1) DEFAULT ''N''')
;

-- ADD COLUMN ... DEFAULT already populated all existing rows with 'N', so NOT NULL is safe
INSERT INTO t_alter_column values('exp_formatline','IsImportLenient',null,'NOT NULL',null)
;

-- Field: Export Format -> Export Format Line -> Import tolerant
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593764,786268 /*From ID Server*/,0,53086,0,TO_TIMESTAMP('2026-10-28 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,0,'EE05',0,0,'Y','Y','Y','N','N','N','N','N','Y',0,'Import tolerant',0,177,187,0,1,1,TO_TIMESTAMP('2026-10-28 14:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=786268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- only meaningful on lookup-relevant line types, same as the Filter Operator field
UPDATE AD_Field SET DisplayLogic='@Type@=''E'' | @Type@=''R''',Updated=TO_TIMESTAMP('2026-10-28 14:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=786268
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585568)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=786268
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(786268)
;

-- Message: EDI_BPartnerLookup_AmbiguousNoDefault
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545967 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-28 14:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','EDI-Suche für GLN {0}: Das Lookup_Label "{1}" ist in den Stammdaten nicht vorhanden und die GLN passt zu {2} Geschäftspartnern, von denen keiner als Standard der Replikationssuche markiert ist - Auflösung nicht möglich.','E',TO_TIMESTAMP('2026-10-28 14:03:00','YYYY-MM-DD HH24:MI:SS'),100,'EDI_BPartnerLookup_AmbiguousNoDefault')
;

UPDATE AD_Message SET ErrorCode='BPARTNER_LOOKUP_AMBIGUOUS_NO_DEFAULT',Updated=TO_TIMESTAMP('2026-10-28 14:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545967
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545967 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='EDI lookup for GLN {0}: the Lookup_Label "{1}" is not in the masterdata and the GLN matches {2} business partners, none flagged as replication-lookup default - cannot resolve.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 14:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545967
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 14:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545967
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-28 14:03:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545967
;
