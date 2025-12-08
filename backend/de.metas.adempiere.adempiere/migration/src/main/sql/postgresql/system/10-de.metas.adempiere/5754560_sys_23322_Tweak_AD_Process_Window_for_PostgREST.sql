-- Run mode: SWING_CLIENT

-- Column: AD_Process.Classname
-- 2025-05-14T11:41:22.922Z
UPDATE AD_Column SET FilterOperator='E', ReadOnlyLogic='@Type@!Java & @Type@!PostgREST', TechnicalNote='Column is writable if Type is either Java or PostgREST. 
If Java, then ClassName needs to be a JavaProcess
If PostgREST, then ClassName need to be a PostgRESTProcessExecutor',Updated=TO_TIMESTAMP('2025-05-14 11:41:22.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4656
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> JSON Path
-- Column: AD_Process.JSONPath
-- 2025-05-14T11:42:34.792Z
UPDATE AD_Field SET IsSameLine='N', SpanX=2, SpanY=2,Updated=TO_TIMESTAMP('2025-05-14 11:42:34.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=582014
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Ausgabeformat
-- Column: AD_Process.PostgrestResponseFormat
-- 2025-05-14T11:44:07.931Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2025-05-14 11:44:07.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=622298
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Ausgabeformat
-- Column: AD_Process.PostgrestResponseFormat
-- 2025-05-14T11:44:17.312Z
UPDATE AD_Field SET SeqNo=295,Updated=TO_TIMESTAMP('2025-05-14 11:44:17.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=622298
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> JSON Path
-- Column: AD_Process.JSONPath
-- 2025-05-14T11:45:46.394Z
UPDATE AD_Field SET SpanY=1,Updated=TO_TIMESTAMP('2025-05-14 11:45:46.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=582014
;

-- Column: AD_Process.JSONPath
-- 2025-05-14T11:46:19.370Z
UPDATE AD_Column SET AD_Reference_ID=14, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-05-14 11:46:19.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568302
;

-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.postgrest.process.PostgRESTProcessExecuto
-- 2025-05-14T11:46:53.424Z
UPDATE AD_Process SET Classname='de.metas.postgrest.process.PostgRESTProcessExecuto',Updated=TO_TIMESTAMP('2025-05-14 11:46:53.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

-- Value: C_Invoice_EDI_Export_JSON
-- Classname: de.metas.edi.process.C_Invoice_EDI_Export_JSON
-- 2025-05-14T11:47:28.519Z
UPDATE AD_Process SET Classname='de.metas.edi.process.C_Invoice_EDI_Export_JSON',Updated=TO_TIMESTAMP('2025-05-14 11:47:28.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585460
;

