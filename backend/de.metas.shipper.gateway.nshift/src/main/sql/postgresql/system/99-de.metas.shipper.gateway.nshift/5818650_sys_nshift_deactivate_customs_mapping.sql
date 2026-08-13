-- 2026-08-12T10:00:00.000Z
-- Deactivate the default nShift customs mapping rules on the nShift shipper (M_Shipper_ID=540019):
--   SeqNo 100 (ShipperEORI, customs-info detail group) and SeqNo 110-170 + 190 (customs-article
--   line-detail-groups). These build customs detail groups into every nShift request; customs
--   handling is not wanted by default and the (partly empty) customs lists make nShift reject the
--   OrderAdvice booking ("list index out of range"). SeqNo 180 (ProductValue line reference) is a
--   non-customs rule and stays active. Rules can be re-activated per instance when customs is needed.
SELECT backup_table('m_shipper_mapping_config', '_nshift_deactivate_customs');

UPDATE M_Shipper_Mapping_Config
   SET IsActive='N',
       Updated=TO_TIMESTAMP('2026-08-12 10:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=99
 WHERE M_Shipper_ID=540019
   AND M_Shipper_Mapping_Config_ID IN (540009,540010,540011,540012,540013,540014,540015,540016,540020)
;
