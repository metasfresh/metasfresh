-- Column: AD_Field.ReadOnlyLogic
-- 2022-10-22T05:45:20.895Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584777,1663,0,14,107,'ReadOnlyLogic',TO_TIMESTAMP('2022-10-22 07:45:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Logic to determine if field is read only (applies only when field is read-write)','D',0,2000,'Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten können (optional) zwischen einfachen Anführungszeichen stehen.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Read Only Logic','NP',0,0,TO_TIMESTAMP('2022-10-22 07:45:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-22T05:45:20.899Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-22T05:45:20.938Z
/* DDL */  select update_Column_Translation_From_AD_Element(1663) 
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Read Only Logic
-- Column: AD_Field.ReadOnlyLogic
-- 2022-10-22T05:48:17.798Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584777,707816,0,107,0,TO_TIMESTAMP('2022-10-22 07:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Logic to determine if field is read only (applies only when field is read-write)',0,'D','Format := {expression} [{logic} {expression}]<br> expression := @{context}@{operand}{value} oder @{context}@{operand}{value}<br> logic := {|}|{&}<br> context := jeglicher globaler oder Fenster-Kontext <br> value := Zeichenketten oder Zahlen<br> logic operators := AND oder OR mit dem bisherigen Ergebnis von Links nach Rechts <br> operand := eq{=}, gt{&gt;}, le{&lt;}, not{~^!} <br> Beispiele: <br> @AD_Table_ID@=14 | @Language@!de_DE <br> @PriceLimit@>10 | @PriceList@>@PriceActual@<br> @Name@>J<br> Zeichenketten können (optional) zwischen einfachen Anführungszeichen stehen.',0,'Y','Y','Y','N','N','N','N','N','Read Only Logic',0,280,0,1,1,TO_TIMESTAMP('2022-10-22 07:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-22T05:48:17.800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-22T05:48:17.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1663) 
;

-- 2022-10-22T05:48:17.817Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707816
;

-- 2022-10-22T05:48:17.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707816)
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Displayed in Grid
-- Column: AD_Field.IsDisplayedGrid
-- 2022-10-22T05:49:37.877Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553824
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Anzeigelogik
-- Column: AD_Field.DisplayLogic
-- 2022-10-22T05:49:37.888Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=133
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Anzeigelänge
-- Column: AD_Field.DisplayLength
-- 2022-10-22T05:49:37.897Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=136
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Schreibgeschützt
-- Column: AD_Field.IsReadOnly
-- 2022-10-22T05:49:37.908Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=924
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Read Only Logic
-- Column: AD_Field.ReadOnlyLogic
-- 2022-10-22T05:49:37.919Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=190,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707816
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Encrypted
-- Column: AD_Field.IsEncrypted
-- 2022-10-22T05:49:37.928Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=200,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=142
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Column Display Length
-- Column: AD_Field.ColumnDisplayLength
-- 2022-10-22T05:49:37.939Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58062
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Column span
-- Column: AD_Field.SpanX
-- 2022-10-22T05:49:37.949Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556350
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Row Span
-- Column: AD_Field.SpanY
-- 2022-10-22T05:49:37.958Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556351
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Same Line
-- Column: AD_Field.IsSameLine
-- 2022-10-22T05:49:37.968Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=139
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Reihenfolge
-- Column: AD_Field.SeqNo
-- 2022-10-22T05:49:37.978Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=137
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Reihenfolge (grid)
-- Column: AD_Field.SeqNoGrid
-- 2022-10-22T05:49:37.988Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553825
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Record Sort No
-- Column: AD_Field.SortNo
-- 2022-10-22T05:49:37.997Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2022-10-22 07:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=138
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Obscure
-- Column: AD_Field.ObscureType
-- 2022-10-22T05:49:38.006Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8343
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Heading only
-- Column: AD_Field.IsHeading
-- 2022-10-22T05:49:38.016Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=140
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Field Only
-- Column: AD_Field.IsFieldOnly
-- 2022-10-22T05:49:38.025Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=141
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Referenz
-- Column: AD_Field.AD_Reference_ID
-- 2022-10-22T05:49:38.033Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13425
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Dynamische Validierung
-- Column: AD_Field.AD_Val_Rule_ID
-- 2022-10-22T05:49:38.043Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54401
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Referenzschlüssel
-- Column: AD_Field.AD_Reference_Value_ID
-- 2022-10-22T05:49:38.052Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54402
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Filter Validation Rule
-- Column: AD_Field.Filter_Val_Rule_ID
-- 2022-10-22T05:49:38.062Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706892
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Mandatory Overwrite
-- Column: AD_Field.IsMandatory
-- 2022-10-22T05:49:38.070Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13424
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Feld
-- Column: AD_Field.AD_Field_ID
-- 2022-10-22T05:49:38.079Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=125
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Standardwert-Logik
-- Column: AD_Field.DefaultValue
-- 2022-10-22T05:49:38.090Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53280
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Color Logic
-- Column: AD_Field.ColorLogic
-- 2022-10-22T05:49:38.098Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542584
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Exclude from Zoom Targets
-- Column: AD_Field.IsExcludeFromZoomTargets
-- 2022-10-22T05:49:38.106Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2022-10-22 07:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=643998
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Read Only Logic
-- Column: AD_Field.ReadOnlyLogic
-- 2022-10-22T05:49:49.608Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707816
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Sektion
-- Column: AD_Field.AD_Org_ID
-- 2022-10-22T05:49:49.622Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1990
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Register
-- Column: AD_Field.AD_Tab_ID
-- 2022-10-22T05:49:49.631Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=126
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Spalte
-- Column: AD_Field.AD_Column_ID
-- 2022-10-22T05:49:49.641Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=130
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Name
-- Column: AD_Field.Name
-- 2022-10-22T05:49:49.651Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=127
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Reihenfolge
-- Column: AD_Field.SeqNo
-- 2022-10-22T05:49:49.660Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=137
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Anzeigelogik
-- Column: AD_Field.DisplayLogic
-- 2022-10-22T05:49:49.669Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=133
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Same Line
-- Column: AD_Field.IsSameLine
-- 2022-10-22T05:49:49.679Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=139
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Feld-Gruppe
-- Column: AD_Field.AD_FieldGroup_ID
-- 2022-10-22T05:49:49.688Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4259
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Displayed
-- Column: AD_Field.IsDisplayed
-- 2022-10-22T05:49:49.696Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=132
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Schreibgeschützt
-- Column: AD_Field.IsReadOnly
-- 2022-10-22T05:49:49.704Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=924
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Anzeigelänge
-- Column: AD_Field.DisplayLength
-- 2022-10-22T05:49:49.712Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=136
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Entitäts-Art
-- Column: AD_Field.EntityType
-- 2022-10-22T05:49:49.720Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5808
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Abw. Feldbenennung
-- Column: AD_Field.AD_Name_ID
-- 2022-10-22T05:49:49.728Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560802
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Column Display Length
-- Column: AD_Field.ColumnDisplayLength
-- 2022-10-22T05:49:49.737Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58062
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Column span
-- Column: AD_Field.SpanX
-- 2022-10-22T05:49:49.745Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556350
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Row Span
-- Column: AD_Field.SpanY
-- 2022-10-22T05:49:49.753Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556351
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Displayed in Grid
-- Column: AD_Field.IsDisplayedGrid
-- 2022-10-22T05:49:49.762Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553824
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Reihenfolge (grid)
-- Column: AD_Field.SeqNoGrid
-- 2022-10-22T05:49:49.770Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553825
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Record Sort No
-- Column: AD_Field.SortNo
-- 2022-10-22T05:49:49.782Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=138
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Obscure
-- Column: AD_Field.ObscureType
-- 2022-10-22T05:49:49.790Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8343
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Heading only
-- Column: AD_Field.IsHeading
-- 2022-10-22T05:49:49.798Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=140
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Field Only
-- Column: AD_Field.IsFieldOnly
-- 2022-10-22T05:49:49.806Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=141
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Referenz
-- Column: AD_Field.AD_Reference_ID
-- 2022-10-22T05:49:49.813Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13425
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Dynamische Validierung
-- Column: AD_Field.AD_Val_Rule_ID
-- 2022-10-22T05:49:49.823Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54401
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Referenzschlüssel
-- Column: AD_Field.AD_Reference_Value_ID
-- 2022-10-22T05:49:49.831Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54402
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Mandatory Overwrite
-- Column: AD_Field.IsMandatory
-- 2022-10-22T05:49:49.842Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=13424
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Feld
-- Column: AD_Field.AD_Field_ID
-- 2022-10-22T05:49:49.850Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=125
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Standardwert-Logik
-- Column: AD_Field.DefaultValue
-- 2022-10-22T05:49:49.859Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53280
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Color Logic
-- Column: AD_Field.ColorLogic
-- 2022-10-22T05:49:49.868Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=310,Updated=TO_TIMESTAMP('2022-10-22 07:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542584
;

