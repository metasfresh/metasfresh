-- Element: Lookup_Label
-- 2024-07-05T07:28:00.869Z
UPDATE AD_Element_Trl SET Description='Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.', Name='Unterscheidungs-Label', PrintName='Unterscheidungs-Label',Updated=TO_TIMESTAMP('2024-07-05 07:28:00.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583167 AND AD_Language='de_CH'
;

-- 2024-07-05T07:28:00.908Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583167,'de_CH') 
;

-- Element: Lookup_Label
-- 2024-07-05T07:28:19.097Z
UPDATE AD_Element_Trl SET Description='Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.', IsTranslated='Y', Name='Unterscheidungs-Label', PrintName='Unterscheidungs-Label',Updated=TO_TIMESTAMP('2024-07-05 07:28:19.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583167 AND AD_Language='de_DE'
;

-- 2024-07-05T07:28:19.101Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583167,'de_DE') 
;

-- 2024-07-05T07:28:19.106Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583167,'de_DE') 
;

-- Element: Lookup_Label
-- 2024-07-05T07:29:34.033Z
UPDATE AD_Element_Trl SET Description='Cand be used to differentiate when different data records otherwise have the same lookup characteristics.', IsTranslated='Y', Name='Lookup Label', PrintName='Lookup Label',Updated=TO_TIMESTAMP('2024-07-05 07:29:34.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583167 AND AD_Language='en_US'
;

-- 2024-07-05T07:29:34.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583167,'en_US') 
;

-- Column: C_BPartner.Lookup_Label
-- Column: C_BPartner.Lookup_Label
-- 2024-07-05T07:30:26.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588669,583167,0,10,291,'Lookup_Label',TO_TIMESTAMP('2024-07-05 07:30:26.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Unterscheidungs-Label',0,0,TO_TIMESTAMP('2024-07-05 07:30:26.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-07-05T07:30:26.544Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588669 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T07:30:26.552Z
/* DDL */  select update_Column_Translation_From_AD_Element(583167) 
;

-- 2024-07-05T07:30:29.737Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Lookup_Label VARCHAR(255)')
;

-- Element: Lookup_Label
-- 2024-07-05T07:32:32.610Z
UPDATE AD_Element_Trl SET Description='Can be used to differentiate when different data records otherwise have the same lookup characteristics.',Updated=TO_TIMESTAMP('2024-07-05 07:32:32.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583167 AND AD_Language='en_US'
;

-- 2024-07-05T07:32:32.616Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583167,'en_US') 
;

-- Table: C_BPartner
-- Table: C_BPartner
-- 2024-07-05T07:33:12.929Z
UPDATE AD_Table SET PersonalDataCategory='P',Updated=TO_TIMESTAMP('2024-07-05 07:33:12.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=291
;

-- Field: Geschäftspartner -> Geschäftspartner -> Unterscheidungs-Label
-- Column: C_BPartner.Lookup_Label
-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Unterscheidungs-Label
-- Column: C_BPartner.Lookup_Label
-- 2024-07-05T07:34:03.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588669,729027,0,220,0,TO_TIMESTAMP('2024-07-05 07:34:03.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N',0,'Unterscheidungs-Label',0,0,400,0,1,1,TO_TIMESTAMP('2024-07-05 07:34:03.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-07-05T07:34:03.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T07:34:03.579Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583167) 
;

-- 2024-07-05T07:34:03.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729027
;

-- 2024-07-05T07:34:03.599Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729027)
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Lookup_Label
-- Column: C_BPartner.Lookup_Label
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Lookup_Label
-- Column: C_BPartner.Lookup_Label
-- 2024-07-05T07:38:43.281Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729027,0,220,1000013,624965,'F',TO_TIMESTAMP('2024-07-05 07:38:43.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.','Y','N','N','Y','N','N','N',0,'Lookup_Label',80,0,0,TO_TIMESTAMP('2024-07-05 07:38:43.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: EDI_C_BPartner_Lookup_BPL_GLN_v.Lookup_Label
-- Column: EDI_C_BPartner_Lookup_BPL_GLN_v.Lookup_Label
-- 2024-07-05T07:41:39.783Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588670,583167,0,10,540552,'Lookup_Label',TO_TIMESTAMP('2024-07-05 07:41:39.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Dient der Unterscheidung, wenn verschiedene Datensätze ansonsten gleiche Lookup-Merkmale haben.','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Unterscheidungs-Label',0,0,TO_TIMESTAMP('2024-07-05 07:41:39.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-07-05T07:41:39.786Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588670 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T07:41:39.790Z
/* DDL */  select update_Column_Translation_From_AD_Element(583167) 
;

-- 2024-07-05T07:42:16.261Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588670,0,TO_TIMESTAMP('2024-07-05 07:42:16.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540402,550768,'Y','N','N','Lookup_Label',50,'E',TO_TIMESTAMP('2024-07-05 07:42:16.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lookup_Label')
;


-----

DROP VIEW IF EXISTS EDI_C_BPartner_Lookup_BPL_GLN_v;

CREATE OR REPLACE VIEW EDI_C_BPartner_Lookup_BPL_GLN_v AS
SELECT *
FROM (SELECT bp.C_BPartner_ID,
             bp.IsActive,
             --
             -- Note: The GLN is unique per BPL.
             -- We're just filtering by two locations here and will add them to the whereclause in the EXP_Format_Line filter.
             --
             REGEXP_REPLACE(l_main.GLN, '\s+$', '')      AS GLN,      -- The Selector's GLN
             REGEXP_REPLACE(l_store.GLN, '\s+$', '')     AS StoreGLN, -- The Store's GLN
             REGEXP_REPLACE(bp.Lookup_Label, '\s+$', '') AS Lookup_Label
      FROM C_BPartner bp
               -- Many-to-many
               LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID = bp.C_BPartner_ID
          AND l_main.IsActive = 'Y'
               LEFT JOIN C_BPartner_Location l_store ON l_store.C_BPartner_ID = bp.C_BPartner_ID
          AND l_store.IsActive = 'Y'
          AND l_store.GLN IS NOT NULL
          AND TRIM(BOTH ' ' FROM l_store.GLN) != ''
          AND l_store.IsShipTo = 'Y' --- without this, the case of two partners sharing the same location-GLN still wouldn't work
      --
      -- support the case of an empty StoreNumber
      UNION
      SELECT bp.C_BPartner_ID,
             bp.IsActive,
             l_main.GLN                                  AS GLN,      -- The Selector's GLN
             NULL                                        AS StoreGLN, -- The Store's GLN
             REGEXP_REPLACE(bp.Lookup_Label, '\s+$', '') AS Lookup_Label
      FROM C_BPartner bp
               -- Many-to-many with NULL StoreGLN
               LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID = bp.C_BPartner_ID
          AND l_main.IsActive = 'Y'
      --
      -- support the case of an empty lookup-label
      UNION
      SELECT bp.C_BPartner_ID,
             bp.IsActive,
             l_main.GLN                              AS GLN,      -- The Selector's GLN
             REGEXP_REPLACE(l_store.GLN, '\s+$', '') AS StoreGLN, -- The Store's GLN
             NULL
      FROM C_BPartner bp
               -- Many-to-many
               LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID = bp.C_BPartner_ID
          AND l_main.IsActive = 'Y'
               LEFT JOIN C_BPartner_Location l_store ON l_store.C_BPartner_ID = bp.C_BPartner_ID
          AND l_store.IsActive = 'Y'
          AND l_store.GLN IS NOT NULL
          AND TRIM(BOTH ' ' FROM l_store.GLN) != ''
          AND l_store.IsShipTo = 'Y' --- without this, the case of two partners sharing the same location-GLN still wouldn't work
      --
      -- support the case of an empty StoreNumber AND lookup-label
      UNION
      SELECT bp.C_BPartner_ID,
             bp.IsActive,
             l_main.GLN AS GLN, -- The Selector's GLN
             NULL,              -- The Store's GLN
             NULL
      FROM C_BPartner bp
               -- Many-to-many with NULL StoreGLN
               LEFT JOIN C_BPartner_Location l_main ON l_main.C_BPartner_ID = bp.C_BPartner_ID
          AND l_main.IsActive = 'Y') master
WHERE master.GLN IS NOT NULL
  AND TRIM(BOTH ' ' FROM master.GLN) != ''
GROUP BY C_BPartner_ID, IsActive, GLN, StoreGLN, Lookup_Label
;
