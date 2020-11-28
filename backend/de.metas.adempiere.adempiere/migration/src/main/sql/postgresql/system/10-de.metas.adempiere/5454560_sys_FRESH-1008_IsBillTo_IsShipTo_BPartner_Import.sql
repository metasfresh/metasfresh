-- 15.12.2016 15:40
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-12-15 15:40:50','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-15 15:40:50','YYYY-MM-DD HH24:MI:SS'),100,916,'Y','N','N','N','N','N','Y','N','D','Vorbelegung Rechnung','N',533,'N','N',0,'N','Wenn "Rechnungs-Adresse" selektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','IsBillTo','N','N',555696,'Rechnungs-Adresse für diesen Geschäftspartner','N','Y','N','N')
;

-- 15.12.2016 15:40
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555696 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 15.12.2016 15:41
-- URL zum Konzept
ALTER TABLE I_BPartner ADD IsBillTo CHAR(1) DEFAULT 'N' CHECK (IsBillTo IN ('Y','N')) NOT NULL
;

-- 15.12.2016 15:41
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-12-15 15:41:38','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-12-15 15:41:38','YYYY-MM-DD HH24:MI:SS'),100,929,'Y','N','N','N','N','N','Y','N','D','Lieferstandard','N',533,'N','N',0,'N','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','IsShipTo','N','N',555697,'Liefer-Adresse für den Geschäftspartner','N','Y','N','N')
;

-- 15.12.2016 15:41
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555697 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 15.12.2016 15:41
-- URL zum Konzept
ALTER TABLE I_BPartner ADD IsShipTo CHAR(1) DEFAULT 'N' CHECK (IsShipTo IN ('Y','N')) NOT NULL
;

-- 16.12.2016 11:19
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555697,557474,0,441,0,TO_TIMESTAMP('2016-12-16 11:19:35','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner',0,'D','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.',0,'Y','Y','Y','Y','N','N','N','N','N','Lieferstandard',391,391,0,1,1,TO_TIMESTAMP('2016-12-16 11:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 11:19
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557474 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 16.12.2016 11:20
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555696,557475,0,441,0,TO_TIMESTAMP('2016-12-16 11:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungs-Adresse für diesen Geschäftspartner',0,'D','Wenn "Rechnungs-Adresse" selektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.',0,'Y','Y','Y','Y','N','N','N','N','N','Vorbelegung Rechnung',392,392,0,1,1,TO_TIMESTAMP('2016-12-16 11:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 16.12.2016 11:20
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557475 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

