-- old value (AD_Process.JasperReport IS NOT NULL AND AD_Process.Value ilike '%HU_Labels%') OR AD_Process.Value='M_HU_Report_QRCode'
UPDATE ad_val_rule
SET code='(AD_Process.JasperReport IS NOT NULL AND AD_Process.Value ilike ''%HU_Labels%'') OR AD_Process.Value=''M_HU_Report_QRCode'' OR AD_Process.Value=''Picking TU Label (Jasper)'' OR AD_Process.Value=''Wareneingangsetikett LU (Jasper)'' OR AD_Process.Value=''Finishedproduct Label''', updated='2025-03-07 09:00', updatedby=99
WHERE ad_val_rule_id = 540604
;
