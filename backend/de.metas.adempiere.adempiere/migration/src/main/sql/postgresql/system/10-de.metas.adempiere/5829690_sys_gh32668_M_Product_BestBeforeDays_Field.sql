-- Shows M_Product.BestBeforeDays on the Produkt window and I_Product.BestBeforeDays on the Import - Produkt window
-- (AD_Window 247). Both go on the main tab of window 140 / 247 respectively, in the same element group as
-- the minimum-shelf-life field GuaranteeDaysMin. Creates the field and UI element only if the tab has no field for
-- the column yet (an instance that already has one is left untouched). The column itself comes from the script
-- 5829680_sys_gh32668_M_Product_BestBeforeDays.sql.

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,Updated,UpdatedBy)
SELECT 0,(SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product')),785689 /*From ID Server*/,0,(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=140 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1),TO_TIMESTAMP('2026-10-19 15:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Gesamthaltbarkeit des Produkts in Tagen.',10,'D','Anzahl der Tage, die das Produkt ab Produktion insgesamt haltbar ist (Mindesthaltbarkeitsdatum minus Produktionsdatum).','Y','Y','N','N','N','N','N','N','Gesamthaltbarkeit',290,TO_TIMESTAMP('2026-10-19 15:00:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE (SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product')) IS NOT NULL AND (SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=140 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) IS NOT NULL AND NOT EXISTS (SELECT 1 FROM AD_Field f2 WHERE f2.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=140 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) AND f2.AD_Column_ID=(SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product')))
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=785689
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(AD_Element_ID) FROM AD_Element WHERE ColumnName='BestBeforeDays'
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
SELECT 0,f.AD_Field_ID,0,f.AD_Tab_ID,(SELECT e.AD_UI_ElementGroup_ID FROM AD_UI_Element e JOIN AD_Field f ON f.AD_Field_ID=e.AD_Field_ID JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID WHERE c.ColumnName='GuaranteeDaysMin' AND e.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=140 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) ORDER BY e.AD_UI_Element_ID LIMIT 1),655047 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-19 15:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesamthaltbarkeit',52,0,0,TO_TIMESTAMP('2026-10-19 15:00:30','YYYY-MM-DD HH24:MI:SS'),100
FROM AD_Field f
WHERE f.AD_Field_ID=785689 AND (SELECT e.AD_UI_ElementGroup_ID FROM AD_UI_Element e JOIN AD_Field f ON f.AD_Field_ID=e.AD_Field_ID JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID WHERE c.ColumnName='GuaranteeDaysMin' AND e.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=140 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) ORDER BY e.AD_UI_Element_ID LIMIT 1) IS NOT NULL
AND NOT EXISTS (SELECT 1 FROM AD_UI_Element e2 WHERE e2.AD_Field_ID=f.AD_Field_ID)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=785689
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(785689)
;

-- Import - Produkt window (AD_Window 247, tab Import - Produkt): I_Product.BestBeforeDays next to GuaranteeDaysMin
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
SELECT 0,(SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product')),785691 /*From ID Server*/,0,(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=247 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1),TO_TIMESTAMP('2026-10-19 15:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Gesamthaltbarkeit des Produkts in Tagen.',10,'D','Anzahl der Tage, die das Produkt ab Produktion insgesamt haltbar ist (Mindesthaltbarkeitsdatum minus Produktionsdatum).','Y','N','N','N','N','N','N','N','Gesamthaltbarkeit',TO_TIMESTAMP('2026-10-19 15:01:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE (SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product')) IS NOT NULL AND (SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=247 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) IS NOT NULL AND NOT EXISTS (SELECT 1 FROM AD_Field f2 WHERE f2.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=247 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) AND f2.AD_Column_ID=(SELECT c.AD_Column_ID FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product')))
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=785691
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(AD_Element_ID) FROM AD_Element WHERE ColumnName='BestBeforeDays'
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
SELECT 0,f.AD_Field_ID,0,f.AD_Tab_ID,(SELECT e.AD_UI_ElementGroup_ID FROM AD_UI_Element e JOIN AD_Field f ON f.AD_Field_ID=e.AD_Field_ID JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID WHERE c.ColumnName='GuaranteeDaysMin' AND e.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=247 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) ORDER BY e.AD_UI_Element_ID LIMIT 1),655048 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-19 15:01:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesamthaltbarkeit',75,0,0,TO_TIMESTAMP('2026-10-19 15:01:30','YYYY-MM-DD HH24:MI:SS'),100
FROM AD_Field f
WHERE f.AD_Field_ID=785691 AND (SELECT e.AD_UI_ElementGroup_ID FROM AD_UI_Element e JOIN AD_Field f ON f.AD_Field_ID=e.AD_Field_ID JOIN AD_Column c ON c.AD_Column_ID=f.AD_Column_ID WHERE c.ColumnName='GuaranteeDaysMin' AND e.AD_Tab_ID=(SELECT t.AD_Tab_ID FROM AD_Tab t WHERE t.AD_Window_ID=247 AND t.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product') AND t.TabLevel=0 ORDER BY t.SeqNo LIMIT 1) ORDER BY e.AD_UI_Element_ID LIMIT 1) IS NOT NULL
AND NOT EXISTS (SELECT 1 FROM AD_UI_Element e2 WHERE e2.AD_Field_ID=f.AD_Field_ID)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=785691
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(785691)
;
