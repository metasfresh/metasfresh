
-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-06T11:58:29.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572537,758717,0,540279,0,TO_TIMESTAMP('2025-12-06 11:58:28.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',0,'U',0,'A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Projekt',0,0,590,0,1,1,TO_TIMESTAMP('2025-12-06 11:58:28.825000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-06T11:58:29.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-06T11:58:29.898Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-12-06T11:58:29.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758717
;

-- 2025-12-06T11:58:29.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758717)
;

-- UI Element: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-06T11:59:53.263Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758717,0,540279,540056,639753,'F',TO_TIMESTAMP('2025-12-06 11:59:53.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','Y','N','Y','N','N','N',0,'Projekt',1050,0,0,TO_TIMESTAMP('2025-12-06 11:59:53.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: C_Invoice.C_Project_ID
-- 2025-12-06T12:07:36.118Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2775,0,263,541214,639754,'F',TO_TIMESTAMP('2025-12-06 12:07:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Project','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','Y','N','Y','N','N','N',0,'Position',110,0,0,TO_TIMESTAMP('2025-12-06 12:07:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Position
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-06T12:08:49.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572537,758718,573543,0,543052,0,TO_TIMESTAMP('2025-12-06 12:08:49.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position Project',0,'U',0,'The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Position',0,0,540,0,1,1,TO_TIMESTAMP('2025-12-06 12:08:49.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-06T12:08:49.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-06T12:08:49.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(573543)
;

-- 2025-12-06T12:08:49.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758718
;

-- 2025-12-06T12:08:49.558Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758718)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Position
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-06T12:09:24.795Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758718,0,543052,544370,639755,'F',TO_TIMESTAMP('2025-12-06 12:09:24.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position Project','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','Y','N','Y','N','N','N',0,'Position',1030,0,0,TO_TIMESTAMP('2025-12-06 12:09:24.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferung(169,D) -> Lieferung(257,D) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: M_InOut.C_Project_ID
-- 2025-12-06T12:10:48.506Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,7831,0,257,540128,639756,'F',TO_TIMESTAMP('2025-12-06 12:10:48.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','Y','N','Y','N','N','N',0,'Projekt',130,0,0,TO_TIMESTAMP('2025-12-06 12:10:48.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Projekt
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2025-12-06T12:16:33.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572534,758719,0,540526,0,TO_TIMESTAMP('2025-12-06 12:16:33.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',0,'U',0,'A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Projekt',0,0,250,0,1,1,TO_TIMESTAMP('2025-12-06 12:16:33.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-06T12:16:33.975Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-06T12:16:33.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-12-06T12:16:33.985Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758719
;

-- 2025-12-06T12:16:33.987Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758719)
;


-- UI Element: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Position
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2025-12-06T12:17:15.436Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,758719,0,540526,540129,639757,'F',TO_TIMESTAMP('2025-12-06 12:17:15.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Project','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','N','N','Y','N','N','N',0,'Position',430,0,0,TO_TIMESTAMP('2025-12-06 12:17:15.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;




-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Position
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-07T10:41:10.108Z
UPDATE AD_Field SET DisplayLogic='@$Element_PJ/''X''@=''Y''',Updated=TO_TIMESTAMP('2025-12-07 10:41:10.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758718
;

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Position
-- Column: C_Invoice_Candidate.C_Project_ID
-- 2025-12-07T10:41:35.701Z
UPDATE AD_Field SET DisplayLogic='@$Element_PJ/''X''@=''Y''',Updated=TO_TIMESTAMP('2025-12-07 10:41:35.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758717
;

-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Position
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2025-12-07T10:42:03.472Z
UPDATE AD_Field SET DisplayLogic='@$Element_PJ/''X''@=''Y''',Updated=TO_TIMESTAMP('2025-12-07 10:42:03.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=758719
;




-- Element: C_Job_ID
-- 2025-12-07T10:56:51.384Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Position dans l''entreprise', PrintName='Position dans l''entreprise',Updated=TO_TIMESTAMP('2025-12-07 10:56:51.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2761 AND AD_Language='fr_CH'
;

-- 2025-12-07T10:56:51.388Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-07T10:56:51.641Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2761,'fr_CH')
;

-- Element: C_Job_ID
-- 2025-12-07T10:56:57.595Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Position in der Firma', PrintName='Position in der Firma',Updated=TO_TIMESTAMP('2025-12-07 10:56:57.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2761 AND AD_Language='it_CH'
;

-- 2025-12-07T10:56:57.596Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-07T10:56:57.828Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2761,'it_CH')
;

-- Element: C_Job_ID
-- 2025-12-07T10:57:22.561Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Position in der Firma', PrintName='Position in der Firma',Updated=TO_TIMESTAMP('2025-12-07 10:57:22.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2761 AND AD_Language='de_CH'
;

-- 2025-12-07T10:57:22.562Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-07T10:57:22.786Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2761,'de_CH')
;

-- Element: C_Job_ID
-- 2025-12-07T10:57:30.429Z
UPDATE AD_Element_Trl SET Name='Job Position', PrintName='Job Position',Updated=TO_TIMESTAMP('2025-12-07 10:57:30.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2761 AND AD_Language='en_US'
;

-- 2025-12-07T10:57:30.430Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-07T10:57:30.668Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2761,'en_US')
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Position
-- Column: C_OrderLine.C_Project_ID
-- 2025-12-07T15:22:11.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,12745,0,187,1000005,639760,'F',TO_TIMESTAMP('2025-12-07 15:22:11.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Position',450,0,0,TO_TIMESTAMP('2025-12-07 15:22:11.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Position
-- Column: C_OrderLine.C_Project_ID
-- 2025-12-07T15:22:16.133Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-12-07 15:22:16.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639760
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Project
-- Column: C_OrderLine.C_Project_ID
-- 2025-12-07T15:22:23.728Z
UPDATE AD_UI_Element SET Name='Project',Updated=TO_TIMESTAMP('2025-12-07 15:22:23.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=639760
;
