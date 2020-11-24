-- 2017-10-31T11:12:05.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543468,0,'IsForeignCustomer',TO_TIMESTAMP('2017-10-31 11:12:05','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.printing','','Y','Foreign Customer','Foreign Customer',TO_TIMESTAMP('2017-10-31 11:12:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-31T11:12:05.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543468 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-10-31T11:12:19.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-31 11:12:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543468 AND AD_Language='en_US'
;

-- 2017-10-31T11:12:19.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543468,'en_US') 
;

-- 2017-10-31T11:12:41.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Ausländer',Updated=TO_TIMESTAMP('2017-10-31 11:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543468
;

-- 2017-10-31T11:12:41.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ausländer', Name='Foreign Customer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543468)
;

-- 2017-10-31T11:12:56.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557804,543468,0,20,540435,'N','IsForeignCustomer','CASE WHEN (coalesce(C_Printing_Queue.Bill_BPartner_ID,0) = coalesce(C_Printing_Queue.C_BPartner_ID,0) AND (coalesce(C_Printing_Queue.Bill_Location_ID,0) = coalesce(C_Printing_Queue.C_BPartner_Location_ID,0))) THEN ''N'' ELSE ''Y'' END',TO_TIMESTAMP('2017-10-31 11:12:56','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.printing',1,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Foreign Customer',0,0,TO_TIMESTAMP('2017-10-31 11:12:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-10-31T11:12:56.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557804 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;




-- 2017-10-31T11:18:53.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='CASE WHEN 
(
(select count(bpl.C_Bpartner_location_ID) from C_Bpartner_location bpl 
		join C_Location l on l.C_Location_ID= bpl.C_Location_ID
		join C_Country c on c.C_Country_ID=l.C_Country_ID
		join AD_Client cl on (C_Printing_Queue.AD_Client_ID=cl.AD_CLient_ID)
		join AD_Language lang on (lang.AD_Language = cl.ad_language)
		where bpl.C_BPartner_Location_ID=C_Printing_Queue.Bill_Location_ID and c.CountryCode=lang.countrycode
)>0
)
THEN ''N'' ELSE ''Y'' END',Updated=TO_TIMESTAMP('2017-10-31 11:18:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557804
;

-- 2017-10-31T11:19:31.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557804,560466,0,540460,0,TO_TIMESTAMP('2017-10-31 11:19:30','YYYY-MM-DD HH24:MI:SS'),100,'',0,'de.metas.printing','',0,'Y','Y','Y','Y','N','N','N','Y','Y','Foreign Customer',93,93,1,1,TO_TIMESTAMP('2017-10-31 11:19:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-31T11:19:31.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-10-31T11:21:44.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560466,0,540460,549159,541033,'F',TO_TIMESTAMP('2017-10-31 11:21:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Ausländer',61,0,140,TO_TIMESTAMP('2017-10-31 11:21:43','YYYY-MM-DD HH24:MI:SS'),100)
;
