-- Narrow the Add-to / Move-to target picker to the delivery instructions the selection can actually join.
--
-- The rule filtered on the direction alone, so the picker offered drafts the subsequent admissibility check
-- then refused -- the planner picked a target and was told no. It now compares every field the instruction
-- header can hold one of, which is the same set DeliveryPlanningList.AggregationKeyField declares.
--
-- Each field's value arrives as a hidden parameter, filled from the selection by the process's
-- getParameterDefaultValue: a value rule sees process parameters and nothing else, so condensing the
-- selection into parameters is the only way to get it into the rule's context.
--
-- An EMPTY value counts as a value, matching aggregationKeyViolations(): a selection with no incoterms
-- belongs on an instruction with none either, and never on one that has them. Hence
-- COALESCE(column, 0) = COALESCE(@Param/0@, 0) rather than the @Param/-1@ = -1 OR ... idiom used where an
-- unset parameter means "do not filter" (AD_Val_Rule 199, 540351) -- here an unset parameter is a value to
-- match, not an absence of a condition.
--
-- IDs allocated from idserver.metas.de on 2026-08-31:
--   AD_MigrationScript 5821360 (this file)
--   AD_Val_Rule        540797 (the aggregation-key comparisons)
--   AD_Val_Rule        540798 (the composite the picker parameter points at)
--   AD_Val_Rule_Included 540044, 540045
--   AD_Process_Para    543282..543288 (the seven hidden key fields on Add to,  585654)
--   AD_Process_Para    543289..543295 (the seven hidden key fields on Move to, 585656)
--
-- Reused, NOT newly created -- the elements the M_ShipperTransportation columns already own, so each
-- parameter carries that field's label in every language:
--   AD_Element 113    AD_Org_ID
--   AD_Element 455    M_Shipper_ID
--   AD_Element 579927 C_Incoterms_ID
--   AD_Element 501608 IncotermLocation
--   AD_Element 581776 M_MeansOfTransportation_ID
--   AD_Element 581899 C_BPartner_Location_Loading_ID
--   AD_Element 581901 C_BPartner_Location_Delivery_ID

-- ===========================================================================================
-- 1) three rules instead of one, because the picker answers two unrelated questions:
--    "can this document be a target at all" (drafted, and a delivery instruction rather than a
--    transport order) and "does it match the selection" (the aggregation key). Splitting them
--    keeps each name honest, and 540796 goes back to describing only what it does. The eligibility
--    half is also the DI-side counterpart of M_ShipperTransportation_Open, which is the same test
--    inverted for transport orders.
--    A composite ANDs its children's where-clauses (CompositeValidationRule), so the picker sees
--    both conditions.
-- ===========================================================================================

-- 1a) eligibility: drafted, and a delivery instruction. Direction moves out -- it is a key field.
UPDATE AD_Val_Rule
   SET Name        = 'M_ShipperTransportation_DraftDI',
       Description = 'Delivery instructions still in draft. DocStatus=DR rather than Processed=N, so an in-progress document is not offered.',
       Code        = 'M_ShipperTransportation.DocStatus = ''DR''
AND EXISTS (SELECT 1
            FROM C_DocType dt
            WHERE dt.C_DocType_ID = M_ShipperTransportation.C_DocType_ID
              AND dt.DocSubType = ''DI'')',
       Updated     = TO_TIMESTAMP('2026-08-31 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
 WHERE AD_Val_Rule_ID = 540796
;

-- 1b) the aggregation key: every field the instruction header can hold one of, compared against the
--     selection's condensed values. AN EMPTY VALUE IS A VALUE -- a selection with no incoterms belongs
--     on an instruction with none either, and never on one that has them. That is what
--     aggregationKeyViolations() does when it judges the selection, and the picker has to agree with it.
--     Hence COALESCE(column, 0) = COALESCE(@Param/0@, 0), and NOT the @Param/-1@ = -1 OR ... idiom of
--     AD_Val_Rule 199 / 540351, where an unset parameter means "do not filter": here an unset parameter
--     is a value to match.
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, Description, Type, Code, EntityType)
VALUES (540797 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 22:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 22:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'M_ShipperTransportation_AggregationKeyFields',
        'Matches an instruction header against the selection''s aggregation key, an empty value counting as a value.',
        'S',
        'M_ShipperTransportation.TransportDirection = ''@TransportDirection/-@''
AND COALESCE(M_ShipperTransportation.AD_Org_ID, 0)                       = COALESCE(@AD_Org_ID/0@, 0)
AND COALESCE(M_ShipperTransportation.M_Shipper_ID, 0)                    = COALESCE(@M_Shipper_ID/0@, 0)
AND COALESCE(M_ShipperTransportation.C_Incoterms_ID, 0)                  = COALESCE(@C_Incoterms_ID/0@, 0)
AND COALESCE(M_ShipperTransportation.M_MeansOfTransportation_ID, 0)      = COALESCE(@M_MeansOfTransportation_ID/0@, 0)
AND COALESCE(M_ShipperTransportation.C_BPartner_Location_Loading_ID, 0)  = COALESCE(@C_BPartner_Location_Loading_ID/0@, 0)
AND COALESCE(M_ShipperTransportation.C_BPartner_Location_Delivery_ID, 0) = COALESCE(@C_BPartner_Location_Delivery_ID/0@, 0)
AND COALESCE(NULLIF(TRIM(M_ShipperTransportation.IncotermLocation), ''''), ''-'') = TRIM(''@IncotermLocation/-@'')',
        'D')
;

-- 1c) the composite the picker parameter points at
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Name, Description, Type, EntityType)
VALUES (540798 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 22:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 22:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Delivery Instruction aggregation key matching',
        'The target list for Add to / Move to: a drafted delivery instruction whose header matches the selection''s aggregation key.',
        'C',
        'D')
;

INSERT INTO AD_Val_Rule_Included (AD_Val_Rule_Included_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                                  AD_Val_Rule_ID, Included_Val_Rule_ID, SeqNo, EntityType)
VALUES (540044 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 22:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 22:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540798, 540796, 10, 'D'),
       (540045 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-31 22:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-31 22:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540798, 540797, 20, 'D')
;

-- 1d) point both target parameters at the composite
UPDATE AD_Process_Para
   SET AD_Val_Rule_ID = 540798,
       Updated        = TO_TIMESTAMP('2026-08-31 22:00:04', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy      = 100
 WHERE AD_Process_Para_ID IN (543278 /* Add to */, 543281 /* Move to */)
;

-- ===========================================================================================
-- 2) the seven hidden key parameters, on both processes.
--    DisplayLogic '1=0' keeps them out of the dialog; the planner still sees only the target.
--    SeqNo continues from the existing pair (10 direction, 20 target).
-- ===========================================================================================
INSERT INTO AD_Process_Para (AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                             AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
                             AD_Reference_ID, AD_Reference_Value_ID, FieldLength, DisplayLogic,
                             IsCentrallyMaintained, IsMandatory, IsRange, IsEncrypted, ShowInActiveValues, EntityType)
SELECT p.AD_Process_Para_ID, 0, 0, 'Y',
       TO_TIMESTAMP('2026-08-31 22:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-08-31 22:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       p.AD_Process_ID, p.AD_Element_ID, p.ColumnName, p.Name, p.SeqNo,
       p.AD_Reference_ID, p.AD_Reference_Value_ID, p.FieldLength, '1=0',
       'Y', 'N', 'N', 'N', 'N', 'D'
  FROM (VALUES
        -- Add to (585654)
        (543282, 585654, 113,    'AD_Org_ID',                       'Sektion',            30, 30, NULL::numeric, 22),
        (543283, 585654, 455,    'M_Shipper_ID',                    'Spediteur',          40, 19, NULL,          22),
        (543284, 585654, 579927, 'C_Incoterms_ID',                  'Incoterms',          50, 30, NULL,          10),
        (543285, 585654, 581776, 'M_MeansOfTransportation_ID',      'Transportmittel',    60, 30, NULL,          10),
        (543286, 585654, 581899, 'C_BPartner_Location_Loading_ID',  'Verladeadresse',     70, 30, 159,           10),
        (543287, 585654, 581901, 'C_BPartner_Location_Delivery_ID', 'Lieferadresse',      80, 30, 159,           10),
        (543288, 585654, 501608, 'IncotermLocation',                'Incoterm Ort',       90, 10, NULL,          500),
        -- Move to (585656)
        (543289, 585656, 113,    'AD_Org_ID',                       'Sektion',            30, 30, NULL,          22),
        (543290, 585656, 455,    'M_Shipper_ID',                    'Spediteur',          40, 19, NULL,          22),
        (543291, 585656, 579927, 'C_Incoterms_ID',                  'Incoterms',          50, 30, NULL,          10),
        (543292, 585656, 581776, 'M_MeansOfTransportation_ID',      'Transportmittel',    60, 30, NULL,          10),
        (543293, 585656, 581899, 'C_BPartner_Location_Loading_ID',  'Verladeadresse',     70, 30, 159,           10),
        (543294, 585656, 581901, 'C_BPartner_Location_Delivery_ID', 'Lieferadresse',      80, 30, 159,           10),
        (543295, 585656, 501608, 'IncotermLocation',                'Incoterm Ort',       90, 10, NULL,          500)
       ) AS p(AD_Process_Para_ID, AD_Process_ID, AD_Element_ID, ColumnName, Name, SeqNo,
              AD_Reference_ID, AD_Reference_Value_ID, FieldLength)
 WHERE NOT EXISTS (SELECT 1 FROM AD_Process_Para existing WHERE existing.AD_Process_Para_ID = p.AD_Process_Para_ID)
;

-- ===========================================================================================
-- 3) translations: seed a row per active system language, then let each element fill its own label
-- ===========================================================================================
INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Process_Para_ID BETWEEN 543282 AND 543295
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(113);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(455);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(579927);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(501608);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581776);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581899);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581901);
