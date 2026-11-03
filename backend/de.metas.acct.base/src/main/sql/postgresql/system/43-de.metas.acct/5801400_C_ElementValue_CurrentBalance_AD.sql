-- AD metadata for the C_ElementValue.CurrentBalance virtual column.
-- Reuses existing AD_Element 858 ("Aktueller Saldo" / "Current Balance"),
-- which is fully translated and currently unused by any AD_Column.
-- Adds:
--   AD_Column        592498  C_ElementValue.CurrentBalance (virtual, AD_Reference_ID=22 Amount)
--   AD_Field         778075  on AD_Tab_ID=542127 (Konten -> Kontenart)
--   AD_UI_Element    650506  in AD_UI_ElementGroup_ID=543186 (primary group "default"),
--                            SeqNo=30 (last in primary group)
--
-- Also reorders the grid so CurrentBalance + IsOpenItem appear before Org/Client:
-- final SeqNoGrid layout (after the Swing-export UPDATEs at the bottom):
--   ... AccountSign=70, CurrentBalance=80, IsOpenItem=90, AD_Org_ID=100, AD_Client_ID=110
-- IsOpenItem is also flagged AllowFiltering=Y for the user-facing filter panel.

-- ---------------------------------------------------------------------------
-- 1) AD_Column on C_ElementValue (virtual: ColumnSQL set, no physical column)
--    Flags mirror AD_Column callouts (which don't fire on raw INSERT):
--      IsLazyLoading='Y'             ColumnSQL set → callout updateIsLazyLoading
--      IsExcludeFromZoomTargets='Y'  Ref 22 not TableDir/Search → callout
--      IsAllowLogging='Y'            default per getDefaultAllowLoggingByColumnName
--      IsCalculated='N'              "CurrentBalance" not in document-workflow whitelist
--      IsUpdateable='N'              virtual
-- ---------------------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,ColumnSQL)
VALUES (0,592498,858,0,22,188,'CurrentBalance',TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Current Balance',0,0,TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,
       '(de_metas_acct.C_ElementValue_CurrentBalance(C_ElementValue.C_ElementValue_ID))')
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
  FROM AD_Language l, AD_Column t
 WHERE l.IsActive='Y'
   AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
   AND t.AD_Column_ID=592498
   AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(858)
;

-- ---------------------------------------------------------------------------
-- 2) AD_Field on AD_Tab_ID=542127 (Konten -> Kontenart)
-- ---------------------------------------------------------------------------
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592498,778075,0,542127,TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','Y','Y','N','N','N','Y','N','Current Balance',TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
  FROM AD_Language l, AD_Field t
 WHERE l.IsActive='Y'
   AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
   AND t.AD_Field_ID=778075
   AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(858)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=778075
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(778075)
;

-- ---------------------------------------------------------------------------
-- 3) AD_UI_Element appended to primary group 543186 ("default") of section 541655
--    Position: SeqNo=30 (last in primary group), SeqNoGrid=80 (after AccountSign=70,
--              before IsOpenItem=90, Org=100, Client=110)
-- ---------------------------------------------------------------------------
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,778075,0,542127,650506,543186,'F',TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','Y','N',0,'Current Balance',30,0,80,TO_TIMESTAMP('2026-05-06 17:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- ---------------------------------------------------------------------------
-- 4) Make IsOpenItem filterable + visible in the grid after CurrentBalance
--    (per design rule: filterable fields should also be displayed in the grid).
--    NOTE: filter config lives on AD_Column.IsSelectionColumn + FilterOperator,
--    not on AD_UI_Element.IsAllowFiltering or AD_Field.IsFilterField (the latter
--    is only consulted for per-window overrides in custom windows).
-- ---------------------------------------------------------------------------
UPDATE AD_Column SET IsSelectionColumn='Y',SelectionColumnSeqNo = 20,FilterOperator='E',Updated=TO_TIMESTAMP('2026-05-06 18:08:00.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=587038
;

-- Move IsOpenItem (618219) into the grid at SeqNoGrid=90 (after CurrentBalance=80)
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-05-06 17:49:53.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=618219
;

-- Push AD_Org_ID and AD_Client_ID to the rightmost positions
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-05-06 17:49:53.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564083
;

UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-05-06 17:49:53.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564084
;
