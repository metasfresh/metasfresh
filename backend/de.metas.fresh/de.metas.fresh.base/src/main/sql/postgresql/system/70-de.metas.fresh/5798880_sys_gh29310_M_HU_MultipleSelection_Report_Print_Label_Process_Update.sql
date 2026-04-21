-- Run mode: SWING_CLIENT

-- Name: AD_Process (HU Label reports) - M_HU_Report_QRCode have separate process class de.metas.handlingunits.process.M_HU_Report_QRCode
-- 2026-04-21T16:42:11.677Z
UPDATE AD_Val_Rule SET Code='(AD_Process.JasperReport IS NOT NULL AND AD_Process.Value ilike ''%HU_Labels%'') OR AD_Process.Value=''Picking TU Label (Jasper)'' OR AD_Process.Value=''Wareneingangsetikett LU (Jasper)'' OR AD_Process.Value=''Finishedproduct Label''',Updated=TO_TIMESTAMP('2026-04-21 16:42:11.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540604
;

