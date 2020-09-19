-- 2020-09-05T19:15:15.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571153,542232,0,29,540645,'QtyItemCapacity',TO_TIMESTAMP('2020-09-05 21:15:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verpackungskapazität',0,0,TO_TIMESTAMP('2020-09-05 21:15:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-09-05T19:15:15.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-09-05T19:15:15.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542232) 
;

-- 2020-09-05T19:15:17.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN QtyItemCapacity NUMERIC')
;

-- 2020-09-05T20:32:21.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571153,616973,0,540663,TO_TIMESTAMP('2020-09-05 22:32:20','YYYY-MM-DD HH24:MI:SS'),100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Verpackungskapazität',TO_TIMESTAMP('2020-09-05 22:32:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:32:21.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=616973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-09-05T20:32:21.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542232) 
;

-- 2020-09-05T20:32:21.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=616973
;

-- 2020-09-05T20:32:21.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(616973)
;

-- 2020-09-05T20:33:26.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616965,0,540663,541228,571151,TO_TIMESTAMP('2020-09-05 22:33:26','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit in der die betreffende Zeile abgerechnet wird','Y','N','Y','N','N','Abrechnungseinheit',180,0,0,TO_TIMESTAMP('2020-09-05 22:33:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:33:37.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616966,0,540663,541228,571152,TO_TIMESTAMP('2020-09-05 22:33:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Liefermenge (Abrechnungseinheit)',190,0,0,TO_TIMESTAMP('2020-09-05 22:33:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:33:49.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616967,0,540663,541228,571153,TO_TIMESTAMP('2020-09-05 22:33:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','EanCom_Invoice_UOM',200,0,0,TO_TIMESTAMP('2020-09-05 22:33:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:33:56.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616968,0,540663,541228,571154,TO_TIMESTAMP('2020-09-05 22:33:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auftragsreferenz',210,0,0,TO_TIMESTAMP('2020-09-05 22:33:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:34:05.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616970,0,540663,541228,571155,TO_TIMESTAMP('2020-09-05 22:34:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Auftragszeile',220,0,0,TO_TIMESTAMP('2020-09-05 22:34:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:34:18.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,616973,0,540663,541228,571156,TO_TIMESTAMP('2020-09-05 22:34:18','YYYY-MM-DD HH24:MI:SS'),100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','Y','N','Y','N','N','Verpackungskapazität',230,0,0,TO_TIMESTAMP('2020-09-05 22:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-09-05T20:34:48.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2020-09-05 22:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549247
;

-- 2020-09-05T20:34:55.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2020-09-05 22:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549248
;

