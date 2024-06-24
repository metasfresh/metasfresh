-- Field: Table and Column(100,D) -> Table(100,D) -> Cloning Enabled
-- Column: AD_Table.CloningEnabled
-- 2023-06-14T09:12:02.602Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586815,716364,0,100,TO_TIMESTAMP('2023-06-14 12:12:02','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Cloning Enabled',TO_TIMESTAMP('2023-06-14 12:12:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T09:12:02.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-14T09:12:02.811Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582437) 
;

-- 2023-06-14T09:12:02.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716364
;

-- 2023-06-14T09:12:02.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716364)
;

-- Field: Table and Column(100,D) -> Table(100,D) -> When Child Cloning Strategy
-- Column: AD_Table.WhenChildCloningStrategy
-- 2023-06-14T09:12:20.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586813,716365,0,100,TO_TIMESTAMP('2023-06-14 12:12:20','YYYY-MM-DD HH24:MI:SS'),100,'The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)',1,'D','Y','N','N','N','N','N','N','N','When Child Cloning Strategy',TO_TIMESTAMP('2023-06-14 12:12:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T09:12:20.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-14T09:12:20.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582438) 
;

-- 2023-06-14T09:12:20.791Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716365
;

-- 2023-06-14T09:12:20.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716365)
;

-- Field: Table and Column(100,D) -> Table(100,D) -> Downline Cloning Strategy
-- Column: AD_Table.DownlineCloningStrategy
-- 2023-06-14T09:12:26.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586812,716366,0,100,TO_TIMESTAMP('2023-06-14 12:12:26','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Downline Cloning Strategy',TO_TIMESTAMP('2023-06-14 12:12:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T09:12:26.343Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716366 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-14T09:12:26.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582435) 
;

-- 2023-06-14T09:12:26.354Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716366
;

-- 2023-06-14T09:12:26.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716366)
;

-- Field: Table and Column(100,D) -> Table(100,D) -> Cloning Enabled
-- Column: AD_Table.CloningEnabled
-- 2023-06-14T09:12:46.747Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2023-06-14 12:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716364
;

-- Field: Table and Column(100,D) -> Table(100,D) -> When Child Cloning Strategy
-- Column: AD_Table.WhenChildCloningStrategy
-- 2023-06-14T09:12:46.764Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2023-06-14 12:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716365
;

-- Field: Table and Column(100,D) -> Table(100,D) -> Downline Cloning Strategy
-- Column: AD_Table.DownlineCloningStrategy
-- 2023-06-14T09:12:46.778Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2023-06-14 12:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716366
;

-- UI Column: Table and Column(100,D) -> Table(100,D) -> main -> 20
-- UI Element Group: org
-- 2023-06-14T09:13:13.256Z
UPDATE AD_UI_ElementGroup SET SeqNo=90,Updated=TO_TIMESTAMP('2023-06-14 12:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541575
;

-- UI Column: Table and Column(100,D) -> Table(100,D) -> main -> 20
-- UI Element Group: cloning
-- 2023-06-14T09:13:23.566Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540951,550786,TO_TIMESTAMP('2023-06-14 12:13:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','cloning',20,TO_TIMESTAMP('2023-06-14 12:13:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table and Column(100,D) -> Table(100,D) -> main -> 20 -> cloning.Cloning Enabled
-- Column: AD_Table.CloningEnabled
-- 2023-06-14T09:13:36.575Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716364,0,100,550786,618006,'F',TO_TIMESTAMP('2023-06-14 12:13:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cloning Enabled',10,0,0,TO_TIMESTAMP('2023-06-14 12:13:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table and Column(100,D) -> Table(100,D) -> main -> 20 -> cloning.When Child Cloning Strategy
-- Column: AD_Table.WhenChildCloningStrategy
-- 2023-06-14T09:13:48.375Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716365,0,100,550786,618007,'F',TO_TIMESTAMP('2023-06-14 12:13:48','YYYY-MM-DD HH24:MI:SS'),100,'The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)','Y','N','Y','N','N','When Child Cloning Strategy',20,0,0,TO_TIMESTAMP('2023-06-14 12:13:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table and Column(100,D) -> Table(100,D) -> main -> 20 -> cloning.Downline Cloning Strategy
-- Column: AD_Table.DownlineCloningStrategy
-- 2023-06-14T09:13:55.390Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716366,0,100,550786,618008,'F',TO_TIMESTAMP('2023-06-14 12:13:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Downline Cloning Strategy',30,0,0,TO_TIMESTAMP('2023-06-14 12:13:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Cloning Strategy
-- Column: AD_Column.CloningStrategy
-- 2023-06-14T09:14:12.046Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586811,716367,0,101,TO_TIMESTAMP('2023-06-14 12:14:11','YYYY-MM-DD HH24:MI:SS'),100,2,'D','Y','N','N','N','N','N','N','N','Cloning Strategy',TO_TIMESTAMP('2023-06-14 12:14:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T09:14:12.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716367 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-14T09:14:12.054Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582434) 
;

-- 2023-06-14T09:14:12.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716367
;

-- 2023-06-14T09:14:12.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716367)
;

-- Field: Table and Column(100,D) -> Column(101,D) -> REST-API Custom Column
-- Column: AD_Column.IsRestAPICustomColumn
-- 2023-06-14T09:14:30.056Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=540,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=703948
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Selection Column
-- Column: AD_Column.IsSelectionColumn
-- 2023-06-14T09:14:30.072Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=550,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4940
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Filter Operator
-- Column: AD_Column.FilterOperator
-- 2023-06-14T09:14:30.084Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=560,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628574
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Filter +/- buttons
-- Column: AD_Column.IsShowFilterIncrementButtons
-- 2023-06-14T09:14:30.095Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=570,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560614
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Filter Default Value
-- Column: AD_Column.FilterDefaultValue
-- 2023-06-14T09:14:30.107Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=580,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560615
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Show filter inline
-- Column: AD_Column.IsShowFilterInline
-- 2023-06-14T09:14:30.117Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=590,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628504
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Facet Filter
-- Column: AD_Column.IsFacetFilter
-- 2023-06-14T09:14:30.127Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=600,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=596166
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Maximum Facets
-- Column: AD_Column.MaxFacetsToFetch
-- 2023-06-14T09:14:30.136Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=610,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=596167
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Filter Validation Rule
-- Column: AD_Column.Filter_Val_Rule_ID
-- 2023-06-14T09:14:30.147Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=620,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706891
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Cloning Strategy
-- Column: AD_Column.CloningStrategy
-- 2023-06-14T09:14:30.156Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=630,Updated=TO_TIMESTAMP('2023-06-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716367
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Filter Validation Rule
-- Column: AD_Column.Filter_Val_Rule_ID
-- 2023-06-14T09:14:56.540Z
UPDATE AD_Field SET AD_FieldGroup_ID=540091,Updated=TO_TIMESTAMP('2023-06-14 12:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=706891
;

-- 2023-06-14T09:15:20.730Z
INSERT INTO AD_FieldGroup (AD_Client_ID,AD_FieldGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,FieldGroupType,IsActive,IsCollapsedByDefault,Name,Updated,UpdatedBy) VALUES (0,540101,0,TO_TIMESTAMP('2023-06-14 12:15:20','YYYY-MM-DD HH24:MI:SS'),100,'D','C','Y','N','Cloning',TO_TIMESTAMP('2023-06-14 12:15:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-14T09:15:20.736Z
INSERT INTO AD_FieldGroup_Trl (AD_Language,AD_FieldGroup_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_FieldGroup_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_FieldGroup t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_FieldGroup_ID=540101 AND NOT EXISTS (SELECT 1 FROM AD_FieldGroup_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_FieldGroup_ID=t.AD_FieldGroup_ID)
;

-- Field: Table and Column(100,D) -> Column(101,D) -> Cloning Strategy
-- Column: AD_Column.CloningStrategy
-- 2023-06-14T09:15:36.349Z
UPDATE AD_Field SET AD_FieldGroup_ID=540101,Updated=TO_TIMESTAMP('2023-06-14 12:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716367
;

-- UI Column: Table and Column(100,D) -> Column(101,D) -> main -> 10
-- UI Element Group: cloning
-- 2023-06-14T09:15:57.859Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540953,550787,TO_TIMESTAMP('2023-06-14 12:15:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','cloning',20,TO_TIMESTAMP('2023-06-14 12:15:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Table and Column(100,D) -> Column(101,D) -> main -> 10 -> cloning.Cloning Strategy
-- Column: AD_Column.CloningStrategy
-- 2023-06-14T09:16:12.320Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716367,0,101,550787,618009,'F',TO_TIMESTAMP('2023-06-14 12:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cloning Strategy',10,0,0,TO_TIMESTAMP('2023-06-14 12:16:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Table and Column(100,D) -> Table(100,D) -> Cloning Enabled
-- Column: AD_Table.CloningEnabled
-- 2023-06-14T09:16:33.868Z
UPDATE AD_Field SET AD_FieldGroup_ID=540101,Updated=TO_TIMESTAMP('2023-06-14 12:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716364
;

-- Field: Table and Column(100,D) -> Table(100,D) -> When Child Cloning Strategy
-- Column: AD_Table.WhenChildCloningStrategy
-- 2023-06-14T09:16:37.718Z
UPDATE AD_Field SET AD_FieldGroup_ID=540101,Updated=TO_TIMESTAMP('2023-06-14 12:16:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716365
;

-- Field: Table and Column(100,D) -> Table(100,D) -> Downline Cloning Strategy
-- Column: AD_Table.DownlineCloningStrategy
-- 2023-06-14T09:16:41.570Z
UPDATE AD_Field SET AD_FieldGroup_ID=540101,Updated=TO_TIMESTAMP('2023-06-14 12:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716366
;

