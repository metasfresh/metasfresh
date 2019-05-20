-- 2019-04-12T18:07:33.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567668,439,0,10,540998,'Line',TO_TIMESTAMP('2019-04-12 18:07:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Einzelne Zeile in dem Dokument','D',10,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Zeile Nr.',0,0,TO_TIMESTAMP('2019-04-12 18:07:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-12T18:07:33.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567668 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-12T18:07:33.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- 2019-04-12T18:07:53.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-04-12 18:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567668
;

-- 2019-04-12T18:10:35.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_Product_Nutrition WHERE M_Product_Nutrition=@M_Product_Nutrition/0@',Updated=TO_TIMESTAMP('2019-04-12 18:10:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567668
;



-- 2019-04-12T18:17:22.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=566, ColumnName='SeqNo', DefaultValue='@SQL=SELECT COALESCE(MAX(seqno),0)+10 AS DefaultValue FROM M_Product_Nutrition WHERE M_Product_Nutrition_ID=@M_Product_Nutrition_ID/0@', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', Name='Reihenfolge',Updated=TO_TIMESTAMP('2019-04-12 18:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567668
;

-- 2019-04-12T18:17:22.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reihenfolge', Description='Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Column_ID=567668
;

-- 2019-04-12T18:17:22.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;


