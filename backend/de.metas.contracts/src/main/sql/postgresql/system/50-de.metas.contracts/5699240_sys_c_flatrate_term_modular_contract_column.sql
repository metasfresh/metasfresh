-- Column: C_Flatrate_Term.Modular_Flatrate_Term_ID
-- 2023-08-16T06:10:58.973651200Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch,
                       Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 587278, 582523, 0, 30, 541780, 540320, 'Modular_Flatrate_Term_ID', TO_TIMESTAMP('2023-08-16 08:10:58.812', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'N', 'Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.', 'de.metas.contracts', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Modularer Vertrag', 0, 0, TO_TIMESTAMP('2023-08-16 08:10:58.812', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 0)
;

-- 2023-08-16T06:10:58.980109700Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 587278
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2023-08-16T06:10:59.530749100Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582523)
;

-- 2023-08-16T06:11:11.196556800Z
/* DDL */

SELECT public.db_alter_table('C_Flatrate_Term', 'ALTER TABLE public.C_Flatrate_Term ADD COLUMN Modular_Flatrate_Term_ID NUMERIC(10)')
;

-- 2023-08-16T06:11:11.466673900Z
ALTER TABLE C_Flatrate_Term
    ADD CONSTRAINT ModularFlatrateTerm_CFlatrateTerm FOREIGN KEY (Modular_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

