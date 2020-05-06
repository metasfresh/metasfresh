
-- 01.07.2015 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552570,542219,0,10,320,'N','ProductNo',TO_TIMESTAMP('2015-07-01 11:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',250,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produktnummer',0,TO_TIMESTAMP('2015-07-01 11:23:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 01.07.2015 11:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552570 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 01.07.2015 11:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (SELECT bpp.ProductNo from C_BPartner_Product bpp where bpp.C_BPartner_ID = @C_BPartner_ID@ AND bpp.M_Product_ID = @M_Product_ID@)', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-07-01 11:28:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552570
;


-- 01.07.2015 11:29
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-07-01 11:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552570
;



-- 01.07.2015 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552570,556211,0,258,0,TO_TIMESTAMP('2015-07-01 11:30:41','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.inout',0,'Y','Y','Y','Y','N','N','N','N','N','Produktnummer',25,15,0,TO_TIMESTAMP('2015-07-01 11:30:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.07.2015 11:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556211 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 01.07.2015 11:37
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' (SELECT bpp.ProductNo from C_BPartner_Product bpp where bpp.C_BPartner_ID =  ( SELECT C_BPartner_ID FROM M_InOut io WHERE io.M_InOut_ID = M_InOutLine.M_InOut_ID)  AND bpp.M_Product_ID = M_InOutLine.M_Product_ID)',Updated=TO_TIMESTAMP('2015-07-01 11:37:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552570
;

-- added by ts 12.10.2015: limit 1, just to be sure..
UPDATE AD_Column SET ColumnSQL=' (SELECT bpp.ProductNo from C_BPartner_Product bpp where bpp.C_BPartner_ID =  ( SELECT C_BPartner_ID FROM M_InOut io WHERE io.M_InOut_ID = M_InOutLine.M_InOut_ID)  AND bpp.M_Product_ID = M_InOutLine.M_Product_ID limit 1)',Updated=now(),UpdatedBy=100 WHERE AD_Column_ID=552570
;


